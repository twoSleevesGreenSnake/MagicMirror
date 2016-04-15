package com.qoo.magicmirror.view;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

/**
 * Created by dllo on 16/4/11.
 * 自定义拖动视图
 */
public class SYXDragViewGroup extends RelativeLayout {

    //
    private ViewDragHelper viewDragHelper;
    private ViewGroup mainView;

    public SYXDragViewGroup(Context context) {
        super(context);
        initView();
    }

    public SYXDragViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public SYXDragViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        // 定义mainView是Viewgroup中的0位置
        mainView = (ViewGroup) getChildAt(0);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 拦截 DragView的拦截方法
                viewDragHelper.processTouchEvent(ev);
                return false;
            case MotionEvent.ACTION_MOVE:
                return true;
            case MotionEvent.ACTION_UP:
                return false;
        }
        return viewDragHelper.shouldInterceptTouchEvent(ev);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 拦截 DragView的拦截方法
        viewDragHelper.processTouchEvent(event);
        return true;
    }

    /**
     * 创建ViewDragHelper
     */
    private void initView() {
        viewDragHelper = ViewDragHelper.create(this, callback);
    }

    private ViewDragHelper.Callback callback = new ViewDragHelper.Callback() {
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return mainView == child;
        }

        // 定义滑动类型
        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            return left;
        }

        // 当试图改变的时候发生改变
        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
            if (-mainView.getLeft() < 80) {
                viewDragHelper.smoothSlideViewTo(mainView, 0, 0);
                ViewCompat.postInvalidateOnAnimation(SYXDragViewGroup.this);
            } else {
                viewDragHelper.smoothSlideViewTo(mainView, -(mainView.getChildAt(1).getRight() - mainView.getChildAt(0).getRight() + 39 * 2), 0);
                ViewCompat.postInvalidateOnAnimation(SYXDragViewGroup.this);
            }
        }
    };

    @Override
    public void computeScroll() {
        if (viewDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }
}
