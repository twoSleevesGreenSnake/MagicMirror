package com.qoo.magicmirror.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Created by dllo on 16/4/12.
 */
public class SYXRecycleView extends RecyclerView {

    private float downX, downY, moveX, moveY;

    public SYXRecycleView(Context context) {
        super(context);
    }

    public SYXRecycleView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SYXRecycleView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = e.getX();
                downY = e.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                moveX = e.getX();
                moveY = e.getY();
                if (Math.abs(moveX - downX) > Math.abs(moveY - downY)) {
                    Log.d("SYXRecycleView", "Math.abs(moveX - downX) > Math.abs(moveY - downY):" + (Math.abs(moveX - downX) > Math.abs(moveY - downY)));
                    return false;
                }
        }
        return super.onInterceptTouchEvent(e);
    }
}
