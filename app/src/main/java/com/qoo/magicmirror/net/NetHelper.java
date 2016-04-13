package com.qoo.magicmirror.net;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceActivity;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.android.volley.RequestQueue;

import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.cache.disc.impl.BaseDiscCache;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.FileNameGenerator;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.decode.BaseImageDecoder;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.qoo.magicmirror.R;
import com.qoo.magicmirror.constants.NetConstants;
import com.qoo.magicmirror.db.MainPageHelper;
import com.qoo.magicmirror.detail.SpecialTopicDetailBean;
import com.qoo.magicmirror.homepage.GoodsListBean;
import com.qoo.magicmirror.homepage.ThematicBean;
import com.qoo.magicmirror.loginandregister.CreateCountBean;
import com.qoo.magicmirror.tools.CutBitmap;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.ArrayList;

/**
 * Created by dllo on 16/3/29.
 */
public class NetHelper<T> {

    private String diskPath;
    private final OkHttpClient mOkHttpClient;
    private Context context;
    private Handler handler;
    private NetListener listener;
    private ImageLoaderConfiguration configuration;
    private ImageLoader imageLoader;
    private final DisplayImageOptions options;
    private Class<T> cls;
    private static NetHelper helper;

    /**
     * 此构造方法并有没有彻底整完 有很大的问题 因为并没有了解okhttp的用法,只是强行使用而已
     * todo 优化代码
     *
     * @param context
     */

    public static NetHelper newNetHelper(Context context) {
        if (helper == null) {
            synchronized (SingleQueue.class) {
                if (helper == null) {
                    helper = new NetHelper(context);

                }
            }
        }
        return helper;
    }

    public NetHelper(Context context) {
        super();

        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File file = Environment.getExternalStorageDirectory();
            diskPath = file.getAbsolutePath();
        } else {
            File file = context.getFilesDir();
            diskPath = file.getAbsolutePath();
        }
        File file = new File(diskPath + "/img");
        if (file.exists()) {
            diskPath = file.getAbsolutePath();
        }
        this.context = context;
        mOkHttpClient = new OkHttpClient();
        configuration = new ImageLoaderConfiguration.Builder(context).diskCache(new UnlimitedDiscCache(file)).diskCacheFileNameGenerator(new Md5FileNameGenerator()).build();
        ImageLoader.getInstance().init(configuration);
        imageLoader = ImageLoader.getInstance();

        //设置imageloader
         options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.ic_launcher) // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.mipmap.ic_launcher) // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.mipmap.ic_launcher) // 设置图片加载或解码过程中发生错误显示的图片
                .resetViewBeforeLoading(false)  // default 设置图片在加载前是否重置、复位
                .delayBeforeLoading(1000)  // 下载前的延迟时间
                .cacheInMemory(true) // default  设置下载的图片是否缓存在内存中
                .cacheOnDisk(true) // default  设置下载的图片是否缓存在SD卡中
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2) // default 设置图片以如何的编码方式显示
                .bitmapConfig(Bitmap.Config.ARGB_4444) // default 设置图片的解码类型
                .displayer(new SimpleBitmapDisplayer()) // default  还可以设置圆角图片new RoundedBitmapDisplayer(20)
                .handler(new Handler()) // default
                .build();


        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (msg.what == 1) {
                    String result = (String) msg.obj;
                    Log.i("result", result);
                    T t = new Gson().fromJson(result, cls);
                    listener.onSuccess(t);
                }
                if (msg.what == 2) {
                    listener.onFailure();
                }
                return false;
            }
        });

    }

    /**
     * 从网络获取bitmap的方法
     * @param url 网址
     * @return 获取的bitmap
     */
  private Bitmap getBitmapFromNet(String url){
      return ImageLoader.getInstance().loadImageSync(url,options);
  }

    /**
     *给view 添加progressbar的方法
     * @param view 组件
     */
    private void addProgressBar(View view){
        ((ViewGroup)view.getParent()).addView(new ProgressBar(context),0);
    }

    /**
     * 切图的方法
     *
     * @param imageView 组件
     * @param url       网址
     * @param cutLength  切掉的高度,取下部分
     */
    public void setDrawable(final ImageView imageView, final String url, final int cutLength) {
        final Handler imageHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (msg.what == 3) {
                    Bitmap bitmap = (Bitmap) msg.obj;
                    if (bitmap == null) {
                        return false;
                    }
                    if (bitmap.getHeight() > 500) {

                        Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, cutLength, bitmap.getWidth(), bitmap.getHeight() - cutLength);
                        imageView.setImageBitmap(newBitmap);
                    } else imageView.setImageBitmap(bitmap);

                }
                return false;
            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = getBitmapFromNet(url);
                Message message = new Message();
                message.what = 3;
                message.obj = bitmap;
                imageHandler.sendMessage(message);
            }
        }).start();

    }

    /**
     * 佩戴图集 切图的方法
     * @param imageView  组件
     * @param url  图片请求网址
     * @param screenWidth 屏幕宽度
     * @return
     */
    public Bitmap setCutBitmap(final ImageView imageView, final String url, int screenWidth) {

        Bitmap bitmap = ImageLoader.getInstance().loadImageSync(url, options);
        if (bitmap == null) {
            return null;
        }
        Bitmap newBitmap = Bitmap.createBitmap(bitmap, 39, 0, bitmap.getWidth() - 78, bitmap.getHeight());
        imageView.setImageBitmap(newBitmap);

        return bitmap;
    }

    /**
     * 给view 网络拉取背景的方法
     *
     * @param v   组件
     * @param url 网址
     */
    public void setBackGround(View v, String url) {
        Bitmap bitmap = ImageLoader.getInstance().loadImageSync(url,options);

        v.setBackground(new BitmapDrawable(context.getResources(), bitmap));
    }

    /**
     * 给imageview 拉取图片并显示的方法
     *
     * @param imageView 组件
     * @param url       网址
     */
    public void setImage(ImageView imageView, String url) {

        imageLoader.displayImage(url, imageView, options);
    }

    /**
     * 加载图片并存入数据库的方法
     *
     * @param imageView
     * @param url
     * @param data
     */
    public void setImage(ImageView imageView, String url, GoodsListBean.DataEntity.ListEntity data, String type) {
        setImage(imageView, url);
        MainPageHelper.newHelper(context).addData(url, data.getGoods_name(), data.getProduct_area(), data.getGoods_price(), data.getBrand(), type);
    }


    /**
     * @param url      需要拼接的网址
     * @param keys     拼接参数的key集合
     * @param values   拼接参数的valve结合
     * @param cls      实体类的class对象
     * @param listener 请求之后的回调接口
     */
    public void getPostInfo(String url, ArrayList<String> keys, ArrayList<String> values, final Class<T> cls, final NetListener<T> listener) {
        FormEncodingBuilder builder = new FormEncodingBuilder();

        this.cls = cls;

        this.listener = listener;
        for (int i = 0; i < keys.size(); i++) {
            builder.add(keys.get(i), values.get(i));
        }
        RequestBody formBody = builder.build();

        Request request = new Request.Builder()

                .url(NetConstants.SERVIE_ADRESS + url)

                .post(formBody)

                .build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                handler.sendEmptyMessage(2);
            }

            @Override
            public void onResponse(com.squareup.okhttp.Response response) throws IOException {
                String body = response.body().string();
                Message message = new Message();
                message.what = 1;
                if (cls == null) {
                    listener.onSuccess((T) body);
                    return;
                }
                message.obj = body;
                handler.sendMessage(message);
            }
        });
    }

    /**
     * 注册界面注册
     *
     * @param url url
     * @param keys 参数key
     * @param values 参数value
     * @param cls  解析类型
     * @param listener 网络请求的接口
      */
    public void getPostInfoForRegister(String url, ArrayList<String> keys, ArrayList<String> values, final Class<T> cls, final NetListener<T> listener) {
        FormEncodingBuilder builder = new FormEncodingBuilder();
        this.cls = cls;
        this.listener = listener;
        for (int i = 0; i < keys.size(); i++) {
            builder.add(keys.get(i), values.get(i));
        }
        RequestBody formBody = builder.build();

        Request request = new Request.Builder()

                .url(NetConstants.SERVIE_ADRESS + url)

                .post(formBody)

                .build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                handler.sendEmptyMessage(2);
            }

            @Override
            public void onResponse(com.squareup.okhttp.Response response) throws IOException {
                String body = response.body().string();
                if (body.contains(context.getString(R.string.has_register))) {
                    listener.onFailure();
                    return;
                } else if (body.contains(context.getString(R.string.wrong_code))) {
                    listener.onFailure();
                    return;
                }
                Message message = new Message();
                message.what = 1;
                message.obj = body;
                handler.sendMessage(message);
            }
        });
    }

    /**
     * 登陆界面登陆
     *
     * @param url url
     * @param keys 参数key
     * @param values 参数value
     * @param cls  解析类型
     * @param listener 网络请求的接口
     */
    public void getPostInfoForLogin(String url, ArrayList<String> keys, ArrayList<String> values, final Class<T> cls, final NetListener<T> listener) {
        FormEncodingBuilder builder = new FormEncodingBuilder();
        this.cls = cls;
        this.listener = listener;
        for (int i = 0; i < keys.size(); i++) {
            builder.add(keys.get(i), values.get(i));
        }
        RequestBody formBody = builder.build();

        Request request = new Request.Builder()

                .url(NetConstants.SERVIE_ADRESS + url)

                .post(formBody)

                .build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                handler.sendEmptyMessage(2);
            }

            @Override
            public void onResponse(com.squareup.okhttp.Response response) throws IOException {
                String body = response.body().string();
                if (body.contains(context.getString(R.string.no_register))) {
                    listener.onFailure();
                    return;
                }
                Message message = new Message();
                message.what = 1;
                message.obj = body;
                handler.sendMessage(message);
            }
        });
    }


    // get
    public void getGetInfo(String url, final Class<T> clazz, final NetListener listener) {
        Request request = new Request.Builder()
                .url(NetConstants.SERVIE_ADRESS + url)
                .build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                listener.onFailure();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                T t = new Gson().fromJson(response.body().string(), clazz);
                listener.onSuccess(t);
            }
        });
    }

    /**
     * 用来区分拉取结果不同的 回调接口
     *
     * @param <T>
     */
    public interface NetListener<T> {
        void onSuccess(T t);

        void onFailure();
    }


}
