package com.qoo.magicmirror.homepage;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Parcel;
import android.util.Log;
import android.widget.ImageView;

import com.qoo.magicmirror.R;
import com.qoo.magicmirror.base.BaseActivity;
import com.qoo.magicmirror.constants.NetConstants;
import com.qoo.magicmirror.db.MainPageData;
import com.qoo.magicmirror.db.MainPageHelper;
import com.qoo.magicmirror.net.NetHelper;
import com.qoo.magicmirror.net.NetService;

import java.util.ArrayList;

/**
 * Created by dllo on 16/4/1.
 */
public class WelcomeActivity extends BaseActivity {

    private ImageView imageView;
    private Handler handler;
    private NetHelper netHelper;
    private WelcomeImgBean data;
    private boolean requestSuccessed = false;
    private int time  = 0;

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
                data = welcomeImgBean;
                handler.sendEmptyMessage(1);
            }

            @Override
            public void onFailure() {
               handler.sendEmptyMessage(2);
            }
        });

    }

    @Override
    protected void initData() {

        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (msg.what==1) {

                    GoodsListBean.DataEntity.ListEntity entity = new GoodsListBean.DataEntity.ListEntity();
                    entity.setGoods_img(data.getImg());
                    netHelper.setImage(imageView, data.getImg(), entity, getString(R.string.welcome));
                }
                if (msg.what==2){
                    MainPageData entity = MainPageHelper.newHelper(WelcomeActivity.this).show(getString(R.string.welcome)).get(0);
                    netHelper.setImage(imageView,entity.getPath());

                }

                return false;
            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    if (requestSuccessed){
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        finish();
                        break;
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    l(time+"");
                    time++;
                    if (time>8){
                        setResult(101);
                        finish();
                        break;

                    }
                }

            }
        }).start();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        requestSuccessed = intent.getBooleanExtra("success",true);

    }

    //    子线程通常执行耗时操作
//    1. Message
//    Message消息，理解为线程间交流的信息，处理数据后台线程需要更新UI，则发送Message内含一些数据给UI线程。
}
