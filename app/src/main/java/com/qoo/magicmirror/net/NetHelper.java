package com.qoo.magicmirror.net;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.RequestQueue;

import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
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
        requestQueue = SingleQueue.newSingleQueue(context).getRequestQueue();
//        loader = new ImageLoader(requestQueue, new MemoryCache());


//        File cacheDir = StorageUtils.getCacheDirectory(context);  //缓存文件夹路径
        //初始化这个configuration
        configuration = new ImageLoaderConfiguration.Builder(context).build();
        ImageLoader.getInstance().init(configuration);
        imageLoader = ImageLoader.getInstance();
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

    public interface NetListener<T> {
        void onSuccess(T t);

        void onFailure();
    }
//
//    public void setImage(ImageView imageView, String url) {
//        ImageLoader.ImageListener listener = ImageLoader.getImageListener(imageView, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
//        loader.get(url, listener);
//
//    }
//
//    // 内存缓存
//    public class MemoryCache implements ImageLoader.ImageCache {
//        private LruCache<String, Bitmap> cache;
//
//        public MemoryCache() {
//            long maxSize = Runtime.getRuntime().maxMemory() / 1024;
//            int cacheSize = (int) (maxSize / 4);
//            cache = new LruCache<String, Bitmap>(cacheSize) {
//                @Override
//                protected int sizeOf(String key, Bitmap Value) {
//
//                    return Value.getRowBytes() * Value.getHeight() / 1024;
//                }
//            };
//        }
//
//        @Override
//        public Bitmap getBitmap(String url) {
//            return cache.get(url);
//        }
//
//        @Override
//        public void putBitmap(String url, Bitmap bitmap) {
//            cache.put(url, bitmap);
//        }
//    }
//
//    // 硬盘缓存
//    public class DiskCache implements ImageLoader.ImageCache {
//
//        @Override
//        public Bitmap getBitmap(String url) {
//            String fileName = url.substring(url.lastIndexOf("/") + 1, url.length());
//            //用文件名拼接出实际文件存储路径
//            String filePath = diskPath + "/" + fileName;
//            Bitmap bitmap = BitmapFactory.decodeFile(filePath);
//            return bitmap;
//        }
//
//        @Override
//        public void putBitmap(String url, Bitmap bitmap) {
//            //获取url中的图片名称
//            String fileName = url.substring(url.lastIndexOf("/") + 1, url.length());
//            //用文件名拼接出实际文件存储路径
//            String filePath = diskPath + "/" + fileName;
//            FileOutputStream fos = null;
//            try {
//                fos = new FileOutputStream(filePath);
//                //将bitmap对象写入文件中
//                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            } finally {
//                //关闭文件流
//                if (fos != null) {
//                    try {
//                        fos.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }
//    }
//
//    // 双重缓存的方式
//    public class DoubleCache implements ImageLoader.ImageCache {
//        private MemoryCache memoryCache;
//        private DiskCache diskCache;
//
//        public DoubleCache() {
//            memoryCache = new MemoryCache();
//            diskCache = new DiskCache();
//        }
//
//        @Override
//        public Bitmap getBitmap(String url) {
//            Bitmap bitmap = memoryCache.getBitmap(url);
//            if (bitmap == null) {
//                bitmap = diskCache.getBitmap(url);
//            }
//            return bitmap;
//        }
//
//        @Override
//        public void putBitmap(String url, Bitmap bitmap) {
//            memoryCache.putBitmap(url, bitmap);
//            diskCache.putBitmap(url, bitmap);
//        }
//    }
}
