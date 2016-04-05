package com.qoo.magicmirror.homepage;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by dllo on 16/3/29.
 * <p/>
 * over_scroll_always滚动模式（默认），over_scroll_if_content_scrolls（允许在滚动只有查看内容大于容器），over_scroll_never，只有当视图是能够滚动，设置滚动模式的视图将有一个效果。
 * <p/>
 * 用于实现主页面纵向滑动的Viewpager的自定义类
 *
 * 触摸事件从父布局向子布局传递，再由子布局返回给父布局
 */
public class VerticalViewPager extends ViewPager {

    // X,Y调换后新的X,Y的坐标
    private float newX;
    private float newY;
    // 鼠标按下后的X,Y坐标
    private float startX;
    private float startY;
    // 鼠标移动
    private float moveX;
    private float moveY;

    public VerticalViewPager(Context context) {
        super(context);
        init();
    }

    public VerticalViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setPageTransformer(true, new VerticalPageTransformer());
        setOverScrollMode(OVER_SCROLL_NEVER);
    }

    private class VerticalPageTransformer implements ViewPager.PageTransformer {

        @Override
        public void transformPage(View page, float position) {
            if (position < -1) {
                page.setAlpha(0);
            } else if (position <= 1) {// 已经存在的视图，和即将出现的视图
                page.setAlpha(1);
                // 显示的页面相对于整个页面的大小
                page.setTranslationX(page.getWidth() * -position);
                float yPosition = position * page.getHeight();
                page.setTranslationY(yPosition);
            } else {
                page.setAlpha(0);
            }
        }
    }

    /**
     * 改变点击的X,Y坐标
     *
     * @param event 点击滑动事件
     * @return 改变后的X, Y坐标
     */
    private MotionEvent swapXY(MotionEvent event) {
        float width = getWidth();
        float height = getHeight();
        newX = (event.getY() / height) * width;
        newY = (event.getX() / width) * height;
        event.setLocation(newX, newY);
        return event;
    }

    /**
     * onInterceptTouchEvent()是ViewGroup的一个方法,目的是在系统向该ViewGroup及其各个childView触发onTouchEvent()之前对相关事件进行一次拦截
     * 即父布局拦截子布局的tuoch事件
     * @param event 事件
     * @return 是否拦截子布局的tuoch事件
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        boolean intercepted = super.onInterceptTouchEvent(swapXY(event));
        swapXY(event);
        int action = event.getAction();
        switch (action){
            case MotionEvent.ACTION_DOWN:
                startX = event.getX();
                startY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float moveX = event.getX();
                float moveY = event.getY();
                if(Math.abs(moveY - startY) - Math.abs(moveX - startX) > 0){
                    return true;
                }
                break;
        }
        return intercepted;
    }

    /**
     * 是定义于View中的一个方法，处理传递到View的手势触摸事件。手势事件类型包括ACTION_DOWN,ACTION_MOVE,ACTION_UP,ACTION_CANCEL等事件；
     * 子布局的tuoch事件
     * @param event 事件
     * @return 子布局执行的事件
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(swapXY(event));
    }
}