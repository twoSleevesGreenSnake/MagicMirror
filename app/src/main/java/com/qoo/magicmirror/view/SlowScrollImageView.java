package com.qoo.magicmirror.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Scroller;

/**
 * Created by dllo on 16/3/29.
 */
public class SlowScrollImageView  extends ImageView{
    private Scroller scroller;
    private ViewGroup group;
    public SlowScrollImageView(Context context) {
        super(context);
        initScroll(context);
    }

    public SlowScrollImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initScroll(context);
    }

    public SlowScrollImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initScroll(context);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (scroller.computeScrollOffset()){
            group = (ViewGroup) getParent();
            group.scrollTo(scroller.getCurrX(), scroller.getCurrY());
            invalidate();
        }
    }
    private void initScroll(Context context){
        scroller = new Scroller(context);
        group = (ViewGroup) getParent();
    }
    public void starScroll(int x,int y){
        scroller.startScroll(x/10,(int)getY(),-x/10,-(int)getY(),1000);
        invalidate();
    }
}
