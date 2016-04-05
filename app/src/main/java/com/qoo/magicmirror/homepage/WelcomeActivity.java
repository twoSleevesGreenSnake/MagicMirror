package com.qoo.magicmirror.homepage;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;

import com.qoo.magicmirror.R;
import com.qoo.magicmirror.base.BaseActivity;
import com.qoo.magicmirror.constants.NetConstants;
import com.qoo.magicmirror.net.NetHelper;

import java.util.ArrayList;

/**
 * Created by dllo on 16/4/1.
 */
public class WelcomeActivity extends BaseActivity {

    private ImageView imageView;
    private Handler handler;
    private NetHelper netHelper;
    private WelcomeImgBean data;

    @Override
    protected int setLayout() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void initView() {
        imageView = bindView(R.id.activity_welcome_img);
        netHelper = new NetHelper(this);
        netHelper.getGetInfo(NetConstants.STARTED_IMG, WelcomeImgBean.class, new NetHelper.NetListener<WelcomeImgBean>() {

            @Override
            public void onSuccess(WelcomeImgBean welcomeImgBean) {
                Log.d("WelcomeActivity", "welcomeImgBean:" + welcomeImgBean);
                data = welcomeImgBean;
                Log.d("WelcomeActivity", "data:" + data);
                handler.sendEmptyMessage(1);
            }
            @Override
            public void onFailure() {

            }
        });

    }

    @Override
    protected void initData() {

        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                
                netHelper.setImage(imageView, data.getImg());

                new Handler(new Handler.Callback() {
                    @Override
                    public boolean handleMessage(Message msg) {
                        finish();
                        return false;
                    }
                }).sendEmptyMessageDelayed(1, 3000);
                return false;
            }
        });
    }
//    子线程通常执行耗时操作
//    1. Message
//    Message消息，理解为线程间交流的信息，处理数据后台线程需要更新UI，则发送Message内含一些数据给UI线程。
}
