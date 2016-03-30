package com.qoo.magicmirror.homepage;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Created by dllo on 16/3/29.
 */
public class MyRecycleView extends RecyclerView {


    private float xDistance,yDistance,xLast,yLast;

    public MyRecycleView(Context context) {
        super(context);
    }

    public MyRecycleView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyRecycleView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN: // 按下
                xDistance = yDistance = 0;
                xLast = ev.getX();
                yLast = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE: // 松开
                final float moveX = ev.getX();
                final float moveY = ev.getY();
                xDistance = Math.abs(moveX - xLast);
                yDistance = Math.abs(moveY - yLast);
                Log.d("recy", "yDistance:" + yDistance);
                if (xDistance < yDistance) {
                    return false;
                }
        }
        return super.onTouchEvent(ev);
    }
}
