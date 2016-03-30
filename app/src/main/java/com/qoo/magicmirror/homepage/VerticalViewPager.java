package com.qoo.magicmirror.homepage;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by dllo on 16/3/29.
 */
public class VerticalViewPager extends ViewPager {

    //
//    private float xDistance,yDistance,xLast,yLast;
//
//    public VerticalViewPager(Context context) {
//        super(context);
//        init();
//    }
//
//    public VerticalViewPager(Context context, AttributeSet attrs) {
//        super(context, attrs);
//        init();
//    }
//
//    private void init() {
//        setPageTransformer(true, new VerticalPageTransformer());
//        setOverScrollMode(OVER_SCROLL_NEVER);
//    }
//
//    private class VerticalPageTransformer implements ViewPager.PageTransformer{
//
//        @Override
//        public void transformPage(View page, float position) {
//            if (position < -1) {
//                page.setAlpha(0);
//            } else if (position <= 1) {
//                page.setAlpha(1);
//
//                page.setTranslationX(page.getWidth() * -position);
//
//                float yPosition = position * page.getHeight();
//                page.setTranslationY(yPosition);
//
//            } else {
//                page.setAlpha(0);
//            }
//        }
//    }
//
//    private MotionEvent swapXY(MotionEvent event) {
//        float width = getWidth();
//        float height = getHeight();
//
//        float newX = (event.getY() / height) * width;
//        float newY = (event.getX() / width) * height;
//
//        event.setLocation(newX, newY);
//
//        return event;
//    }
//
//    @Override
//    public boolean onTouchEvent(MotionEvent ev) {
////        switch (ev.getAction()){
////            case MotionEvent.ACTION_DOWN: // 按下
////                xDistance = yDistance = 0;
////                xLast = ev.getX();
////                yLast = ev.getY();
////                break;
////            case MotionEvent.ACTION_MOVE: // 松开
////                final float moveX = ev.getX();
////                final float moveY = ev.getY();
////                xDistance = Math.abs(moveX - xLast);
////                yDistance = Math.abs(moveY - yLast);
////                Log.d("VerticalViewPager", "yDistance:" + yDistance);
////                if (xDistance >yDistance) {
////                    Log.d("VerticaPager44444", "yDistance:" + yDistance);
////                    return super.onTouchEvent(swapXY(ev));
////                }
////        }
////        Log.d("VerticaPager666666666", "yDistance:" + yDistance);
//        return super.onTouchEvent(swapXY(ev));
//
//
//    }
//
//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        switch (ev.getAction()){
//            case MotionEvent.ACTION_DOWN: // 按下
//                xDistance = yDistance = 0;
//                xLast = ev.getX();
//                yLast = ev.getY();
//                break;
//            case MotionEvent.ACTION_MOVE: // 松开
//                final float moveX = ev.getX();
//                final float moveY = ev.getY();
//                xDistance = Math.abs(moveX - xLast);
//                yDistance = Math.abs(moveY - yLast);
////                Log.d("VerticalViewPager", "yDistance:" + yDistance);
//                if (xDistance < yDistance) {
//                    return true;
//                }
//        }
////                        Log.d("VerticalViewPager", "yDistance:" + yDistance);
//
//        return false;
//
//    }
//    public boolean onInterceptHoverEvent(MotionEvent event) {
//        boolean intercepted = super.onInterceptHoverEvent(swapXY(event));
//        swapXY(event);
//        return intercepted;
//    }
    private float xDistance, yDistance, xLast, yLast;

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
            } else if (position <= 1) {
                page.setAlpha(1);
                page.setTranslationX(page.getWidth() * -position);
                float yPosition = position * page.getHeight();
                page.setTranslationY(yPosition);

            } else {
                page.setAlpha(0);
            }
        }
    }

    private MotionEvent swapXY(MotionEvent event) {
        float width = getWidth();
        float height = getHeight();
        float newX = (event.getY() / height) * width;
        float newY = (event.getX() / width) * height;
        event.setLocation(newX, newY);
        return event;
    }

    @Override
    public boolean onInterceptHoverEvent(MotionEvent event) {
        boolean intercepted = super.onInterceptHoverEvent(swapXY(event));
        swapXY(event);
        return intercepted;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(swapXY(event));
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean flag = false;
        switch (ev.getAction()) {
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
                Log.d("VerticalViewPager", "yDistance:" + yDistance);
                if (xDistance < yDistance) {
                    flag =true;
                    Log.d("VerticalViewPager", "flag:" + flag);
                    return flag;
                }
        }
        Log.d("VerticalViewPagerflag", "flag:" + flag);
      return flag;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN: // 按下
                xDistance = yDistance = 0;
                xLast = ev.getX();
                yLast = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE: // 松开
                final float moveXm = ev.getX();
                final float moveYm = ev.getY();
                xDistance = Math.abs(moveXm - xLast);
                yDistance = Math.abs(moveYm - yLast);
                Log.d("VerticalViewPager", "yDistance:" + yDistance);
                if (xDistance < yDistance) {
                    if (moveYm - yLast<-300){
                        View v = (View) getParent();
                         if ( getChildAt(getCurrentItem())!=null) {
                             getChildAt(getCurrentItem()).scrollTo((int) v.getX(), (int) moveYm);

                         }
                        break;
                    }
                    if (moveYm - yLast>300){
                        View v = (View) getParent();
                        if ( getChildAt(getCurrentItem())!=null) {
                            getChildAt(getCurrentItem()).scrollTo((int) v.getX(), (int) moveYm);
                        }
                        break;
                    }
                }
            case MotionEvent.ACTION_UP: // 松开
                final float moveX = ev.getX();
                final float moveY = ev.getY();
                xDistance = Math.abs(moveX - xLast);
                yDistance = Math.abs(moveY - yLast);
                Log.d("VerticalViewPager", "yDistance:" + yDistance);
                if (xDistance < yDistance) {
                    if (moveY - yLast<-300){

                    setCurrentItem(getCurrentItem()+1);

                    return true;}
                    if (moveY - yLast>300){
                        setCurrentItem(getCurrentItem()-1);
                    }
                }
        }
        return super.dispatchTouchEvent(ev);

    }
}
