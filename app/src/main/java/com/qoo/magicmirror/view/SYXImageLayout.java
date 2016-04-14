package com.qoo.magicmirror.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.AttributeSet;
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
 */
public class SYXImageLayout extends RelativeLayout{
    private ImageView src;
    private ProgressBar back;
    private Bitmap bitmap;

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
        RelativeLayout.LayoutParams  srcParams = (LayoutParams) src.getLayoutParams();
        RelativeLayout.LayoutParams  backParams = (LayoutParams) back.getLayoutParams();
        srcParams.width = LayoutParams.MATCH_PARENT;
        srcParams.height = LayoutParams.WRAP_CONTENT;
        backParams.width = LayoutParams.MATCH_PARENT;
        backParams.height = LayoutParams.MATCH_PARENT;
        src.setScaleType(ImageView.ScaleType.CENTER_CROP);
        backParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        src.setLayoutParams(srcParams);
        back.setLayoutParams(backParams);
        back.setBackgroundColor(Color.WHITE);
    }

    private void initView(){
        src = new ImageView(getContext());
        back = new ProgressBar(getContext());
        addView(src);
        addView(back);
    }
    public void setImage(String url){
        src.setVisibility(INVISIBLE);
        back.setVisibility(VISIBLE);
        NetHelper.newNetHelper(getContext()).setImage(src, url, new NetHelper.ImageListener() {
            @Override
            public void imageFished(Bitmap bitmap) {
                SYXImageLayout.this.bitmap =bitmap;
                src.setVisibility(VISIBLE);
                back.setVisibility(GONE);
            }
        });
    }
    public void setImage(String url,GoodsListBean.DataEntity.ListEntity data,String type){
        NetHelper.newNetHelper(getContext()).setImage(src,url,data,type);
    }

}
