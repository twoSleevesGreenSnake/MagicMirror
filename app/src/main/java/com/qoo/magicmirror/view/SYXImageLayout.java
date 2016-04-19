package com.qoo.magicmirror.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.qoo.magicmirror.homepage.GoodsListBean;
import com.qoo.magicmirror.net.NetHelper;

/**
 * Created by dllo on 16/4/14.
 * 没加载出来带转圈的东西
 * 此类真的好low啊
 *
 * 所以此类并没有什么注释
 */
public class SYXImageLayout extends RelativeLayout {
    private ImageView src;
    private ProgressBar back;

    public ImageView getSrc() {
        return src;
    }

    public SYXImageLayout(Context context) {
        super(context);
        initView();
    }

    public SYXImageLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public SYXImageLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SYXImageLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        RelativeLayout.LayoutParams srcParams = (LayoutParams) src.getLayoutParams();
        RelativeLayout.LayoutParams backParams = (LayoutParams) back.getLayoutParams();
        srcParams.width = LayoutParams.MATCH_PARENT;
        srcParams.height = LayoutParams.MATCH_PARENT;
        backParams.width = LayoutParams.WRAP_CONTENT;
        backParams.height = LayoutParams.WRAP_CONTENT;
        backParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        src.setLayoutParams(srcParams);
        src.setBackgroundColor(Color.TRANSPARENT);
        back.setLayoutParams(backParams);
    }
    private void onImageFinished(){
        post(new Runnable() {
            @Override
            public void run() {
                src.setVisibility(VISIBLE);
                back.setVisibility(GONE);
            }
        });
    }
    private void initView() {
        src = new ImageView(getContext());
        src.setScaleType(ImageView.ScaleType.FIT_XY);
        back = new ProgressBar(getContext());
        addView(back);
        addView(src);
//        handler = new Handler(new Handler.Callback() {
//            @Override
//            public boolean handleMessage(Message msg) {
//                src.setVisibility(VISIBLE);
//                back.setVisibility(GONE);
//                return false;
//            }
//        });
    }

    public void setImage(String url) {
        src.setVisibility(INVISIBLE);
        back.setVisibility(VISIBLE);
        NetHelper.newNetHelper(getContext()).setBitmap(src, url, new NetHelper.ImageListener() {
            @Override
            public void imageFished(Bitmap bitmap) {
                onImageFinished();
            }
        });
    }

    public void setImage(String url, GoodsListBean.DataEntity.ListEntity data, String type) {
        NetHelper.newNetHelper(getContext()).setImage(src, url, data, type);
    }

    public void setScale(ImageView.ScaleType type) {
        src.setScaleType(type);
    }
    public void setImage(String url, final NetHelper.ImageListener listener) {
        src.setVisibility(INVISIBLE);
        back.setVisibility(VISIBLE);
        NetHelper.newNetHelper(getContext()).setBitmap(src, url, new NetHelper.ImageListener() {
            @Override
            public void imageFished(Bitmap bitmap) {
                Bitmap newBitmap = Bitmap.createBitmap(bitmap, 39, 0, bitmap.getWidth() - 78, bitmap.getHeight());
                listener.imageFished(newBitmap);
                onImageFinished();
            }
        });
    }
}
