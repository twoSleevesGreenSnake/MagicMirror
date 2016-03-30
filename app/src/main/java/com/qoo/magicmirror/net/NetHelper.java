package com.qoo.magicmirror.net;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.qoo.magicmirror.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dllo on 16/3/29.
 */
public class NetHelper<T> {
    private RequestQueue requestQueue;
    private JsonObjectRequest jsonObjectRequest;
    private T t;
    private ImageLoader loaderder;
    private ImageLoader loader;
    private String diskPath;
    private Context context;

    public NetHelper(Context context) {
        super();
        requestQueue = SingleQueue.newSingleQueue(context).getRequestQueue();
//        loader = new ImageLoader(requestQueue, new MemoryCache());
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
    //外部调用给imageiew放置图片的方法
    public void setImage(ImageView imageView, String url) {
        ImageLoader.ImageListener listener = ImageLoader.getImageListener(imageView, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
        loader.get(url, listener);

    }

    public ImageLoader getImageLoader() {
        return loader;
    }

    public void getInfo(String url, final Class<T> cls, final NetListener listener) {
        StringRequest jsonObjectRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                t = new Gson().fromJson(response, cls);
                listener.getSucceed(t);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.getFailed("网络不好,请稍等");
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    public void getInfoArray(String url, final Class<T> cls, final NetListener listener) {
        JsonArrayRequest arrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                ArrayList<T> list = new ArrayList<>();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject o = response.getJSONObject(i);



                        t = new Gson().fromJson(o.toString(), cls);
                        list.add(t);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                listener.getSucceed(list);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(arrayRequest);
    }

    public void getInfo(String url, String headKey, String headValue, final Class<T> cls, final NetListener listener) {
        final Map<String, String> map = new HashMap<>();
        map.put(headKey, headValue);
        jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                t = new Gson().fromJson(response.toString(), cls);
                listener.getSucceed(t);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.getFailed("网络不好,请稍等");
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return map;
            }
        };

        requestQueue.add(jsonObjectRequest);
    }


    public void getPostInfor(){

    }








    public void getInfo(String head, final String id, String leg, String bottom, final Class<T> cls, final NetListener listener) {
        String url = head + id + leg + bottom;

        if (!id.equals("")) {
            final ArrayList<T> list = new ArrayList<>();
            jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONArray array = response.getJSONArray(id);
                        for (int i = 0; i < array.length(); i++) {
                            t = new Gson().fromJson(array.getJSONObject(i).toString(), cls);
                            list.add(t);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    listener.getSucceed(list);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    listener.getFailed("网络不好,请稍等");
                }
            });
            requestQueue.add(jsonObjectRequest);
        } else getInfo(url, cls, listener);
    }

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

//    public void initImageLoader() {
////         loader = new ImageLoader(queue,new MemoryCache());
//
//        //定义硬盘缓存的根路径
//        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
//            File file = Environment.getExternalStorageDirectory();
//            diskPath = file.getAbsolutePath();
//        } else {
//            File file = context.getFilesDir();
//            diskPath = file.getAbsolutePath();
//        }
//        File file = new File(diskPath + "/img");
//        if (file.exists())
//            diskPath = file.getAbsolutePath();
//        loader = new ImageLoader(requestQueue, new DiskCache());
//
//
//    }

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
