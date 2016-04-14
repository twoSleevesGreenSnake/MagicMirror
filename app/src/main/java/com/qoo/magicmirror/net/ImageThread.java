package com.qoo.magicmirror.net;

import android.graphics.Bitmap;

/**
 * Created by dllo on 16/4/14.
 */
public class ImageThread extends Thread{
    private Bitmap bitmap;
    private BitmapRequestFinsh bitmapListener;

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public void setBitmapListener(BitmapRequestFinsh bitmapListener) {
        this.bitmapListener = bitmapListener;
    }

    @Override
    public void run() {
        super.run();
        while (true){
            if (bitmap!=null&&bitmapListener!=null){
                bitmapListener.imgFishened();
                bitmap = null;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
    interface BitmapRequestFinsh{
        void imgFishened();
    }
}
