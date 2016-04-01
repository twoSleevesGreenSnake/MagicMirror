package com.qoo.magicmirror.tools;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;

/**
 * Created by dllo on 16/3/31.
 */
public class CutBitmap extends Drawable{
    private Paint paint;
    private Bitmap bitmap;
    private int pheight;
    private int cutLenth;
    private int pWidth;

    public CutBitmap(Bitmap bitmap,int cutLenth) {
        this.bitmap = bitmap;
        paint = new Paint();
        BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        paint.setShader(shader);
        pheight = bitmap.getHeight()-200;
        this.cutLenth = cutLenth;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawRect(0,500,bitmap.getWidth(),bitmap.getHeight(),paint);
    }

    @Override
    public void setAlpha(int alpha) {
        paint.setAlpha(alpha);
    }



    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        paint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    @Override
    public int getIntrinsicWidth() {
        return pheight;
    }

    @Override
    public int getIntrinsicHeight() {
        return bitmap.getWidth();
    }

}
