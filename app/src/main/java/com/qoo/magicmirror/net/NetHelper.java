package com.qoo.magicmirror.net;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v4.util.LruCache;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.google.gson.Gson;
import com.qoo.magicmirror.constants.NetConstants;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by dllo on 16/3/29.
 */
public class NetHelper {
    private RequestQueue requestQueue;
    private ImageLoader loader;
    private String diskPath;
    private final OkHttpClient mOkHttpClient;
    private Context context;

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
        loader = new ImageLoader(requestQueue, new MemoryCache());
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
//            loader = new ImageLoader(requestQueue, new DiskCache());
        }
        loader = new ImageLoader(requestQueue, new DoubleCache());
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
                listener.onFailure(request, e);
            }

            @Override
            public void onResponse(com.squareup.okhttp.Response response) throws IOException {
                Log.d("NetHelper", response.body().string());
                T t = new Gson().fromJson(response.body().string(), cls);
                listener.onSuccess(t);
            }
        });
    }

    public interface NetListener<T> {
        void onSuccess(T t);
        void onFailure(Request request, IOException e);
    }

    // 内存缓存
    public class MemoryCache implements ImageLoader.ImageCache {
        private LruCache<String, Bitmap> cache;

        public MemoryCache() {
            long maxSize = Runtime.getRuntime().maxMemory() / 1024;
            int cacheSize = (int) (maxSize / 4);
            cache = new LruCache<String, Bitmap>(cacheSize) {
                @Override
                protected int sizeOf(String key, Bitmap value) {

                    return value.getRowBytes() * value.getHeight() / 1024;
                }
            };
        }

        @Override
        public Bitmap getBitmap(String url) {
            return cache.get(url);
        }

        @Override
        public void putBitmap(String url, Bitmap bitmap) {
            cache.put(url, bitmap);
        }
    }

    // 硬盘缓存
    public class DiskCache implements ImageLoader.ImageCache {

        @Override
        public Bitmap getBitmap(String url) {
            String fileName = url.substring(url.lastIndexOf("/") + 1, url.length());
            //用文件名拼接出实际文件存储路径
            String filePath = diskPath + "/" + fileName;
            Bitmap bitmap = BitmapFactory.decodeFile(filePath);
            return bitmap;
        }

        @Override
        public void putBitmap(String url, Bitmap bitmap) {
            //获取url中的图片名称
            String fileName = url.substring(url.lastIndexOf("/") + 1, url.length());
            //用文件名拼接出实际文件存储路径
            String filePath = diskPath + "/" + fileName;
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(filePath);
                //将bitmap对象写入文件中
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                //关闭文件流
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    // 双重缓存的方式
    public class DoubleCache implements ImageLoader.ImageCache {
        private MemoryCache memoryCache;
        private DiskCache diskCache;

        public DoubleCache() {
            memoryCache = new MemoryCache();
            diskCache = new DiskCache();
        }

        @Override
        public Bitmap getBitmap(String url) {
            Bitmap bitmap = memoryCache.getBitmap(url);
            if (bitmap == null) {
                bitmap = diskCache.getBitmap(url);
            }
            return bitmap;
        }

        @Override
        public void putBitmap(String url, Bitmap bitmap) {
            memoryCache.putBitmap(url, bitmap);
            diskCache.putBitmap(url, bitmap);
        }
    }
}
