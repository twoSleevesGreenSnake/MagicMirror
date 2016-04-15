package com.qoo.magicmirror.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

/**
 * Created by dllo on 16/4/1.
 */
public class SpecialLayout extends RelativeLayout {

    public SpecialLayout(Context context) {
        super(context);
    }

    public SpecialLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SpecialLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SpecialLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    /**
     * 计算自己的宽高
     * onlayout计算父类布局的
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        ViewGroup parentView = (ViewGroup) getParent();
        View brotherView = parentView.getChildAt(0);
        setMeasuredDimension(brotherView.getMeasuredWidth(), brotherView.getMeasuredHeight());
    }
}
