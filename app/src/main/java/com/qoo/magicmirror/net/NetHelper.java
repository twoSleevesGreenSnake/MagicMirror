package com.qoo.magicmirror.net;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.RequestQueue;

import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.decode.BaseImageDecoder;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.qoo.magicmirror.R;
import com.qoo.magicmirror.constants.NetConstants;
import com.qoo.magicmirror.tools.CutBitmap;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by dllo on 16/3/29.
 */
public class NetHelper<T> {
    private RequestQueue requestQueue;
    private String diskPath;
    private final OkHttpClient mOkHttpClient;
    private Context context;
    private Handler handler;
    private NetListener listener;
    private ImageLoaderConfiguration configuration;
    private ImageLoader imageLoader;
    private final DisplayImageOptions options;

    /**
     * 此构造方法并有没有彻底整完 有很大的问题 因为并没有了解okhttp的用法,只是强行使用而已
     * todo 优化代码
     *
     * @param context
     */
    public NetHelper(Context context) {
        super();
        this.context = context;
        mOkHttpClient = new OkHttpClient();
        configuration = new ImageLoaderConfiguration.Builder(context).build();
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

//
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
        //handler回调之后的方法,用来刷新UI主线程
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (msg.what == 1) {
                    listener.onSuccess(msg.obj);
                }
                if (msg.what == 2) {
                    listener.onFailure();
                }


                return false;
            }
        });

    }

    /**
     * 切图的方法
     * @param imageView 组件
     * @param url   网址
     * @param cutLenth 切掉的高度,取下部分
     */
    public void setDrawable(ImageView imageView,String url,int cutLenth){
        Bitmap bitmap = ImageLoader.getInstance().loadImageSync(url);
        if (bitmap==null){
            return;
        }
        if (bitmap.getHeight()>500) {

            Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, cutLenth, bitmap.getWidth(), bitmap.getHeight() - cutLenth);
            imageView.setImageBitmap(newBitmap);
        }
        else imageView.setImageBitmap(bitmap);
    }

    /**
     * 给view 网络拉取背景的方法
     * @param v 组件
     * @param url 网址
     */
    public void setBackGround(View v,String url){
        Bitmap bitmap = ImageLoader.getInstance().loadImageSync(url);

        v.setBackground(new BitmapDrawable(context.getResources(),bitmap));
    }

    /**
     * 给imageview 拉取图片并显示的方法
     * @param imageView 组件
     * @param url 网址
     */
    public void setImage(ImageView imageView, String url) {
        imageLoader.displayImage(url, imageView, options);
    }

    /**
     * @param url      需要拼接的网址
     * @param keys     拼接参数的key集合
     * @param values   拼接参数的valve结合
     * @param cls      实体类的class对象
     * @param listener 请求之后的回调接口
     * @param <T>      不知道需要解析何种类型的泛型
     */
    public <T> void getPostInfo(String url, ArrayList<String> keys, ArrayList<String> values, final Class<T> cls, final NetListener<T> listener) {
        FormEncodingBuilder builder = new FormEncodingBuilder();
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
                T t = new Gson().fromJson(response.body().string(), cls);
                Message message = new Message();
                message.what = 1;
                message.obj = t;
                handler.sendMessage(message);

            }
        });
    }

    /**
     * 用来区分拉取结果不同的 回调接口
     * @param <T>
     */
    public interface NetListener<T> {
        void onSuccess(T t);

        void onFailure();
    }

}
