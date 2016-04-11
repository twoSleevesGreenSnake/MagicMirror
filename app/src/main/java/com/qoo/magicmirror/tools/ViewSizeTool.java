package com.qoo.magicmirror.tools;

import android.content.Context;
import android.graphics.Point;
import android.view.View;
import android.view.ViewTreeObserver;

/**
 * Created by dllo on 16/4/11.
 */
public class ViewSizeTool {

    private ViewSizeTool viewSizeTool;


    private ViewSizeTool() {
    }
   public static ViewSizeTool newViewTool(){
//       if ()
   }
    public Point getViewSize(final View view){
        ViewTreeObserver viewTreeObserver = view.getViewTreeObserver();
        final Point point = new Point();
        viewTreeObserver.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                point.set(view.getWidth(),view.getHeight());
                view.getViewTreeObserver().removeOnPreDrawListener(this);
                return false;
            }
        });
        return point;
    }


}
