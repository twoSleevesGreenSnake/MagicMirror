package com.qoo.magicmirror.tools;

import android.content.Context;
import android.graphics.Point;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;

/**
 * Created by dllo on 16/4/11.
 */
public class ViewSizeTool {

    private static ViewSizeTool viewSizeTool;


    private ViewSizeTool() {
    }
   public static ViewSizeTool newViewTool(){
       Log.i("obobobobo111111","ssssss");


       if ( viewSizeTool==null){
           viewSizeTool = new ViewSizeTool();
       }
       return newViewTool();
   }


    public Point getViewSize(final View view){
        final Point point = new Point();

        ViewTreeObserver viewTreeObserver = view .getViewTreeObserver();

        ViewTreeObserver.OnPreDrawListener listener = new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                point.set(view.getWidth(), view.getHeight());
                if (view.getWidth()!=0||view.getHeight()!=0){
                    view.getViewTreeObserver().removeOnPreDrawListener(this);
                }

                return false;
            }
        };
        viewTreeObserver.addOnPreDrawListener(listener) ;
        return point;
    }


}
