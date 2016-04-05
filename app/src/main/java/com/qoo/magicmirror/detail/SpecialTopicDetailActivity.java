package com.qoo.magicmirror.detail;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.qoo.magicmirror.R;
import com.qoo.magicmirror.base.BaseActivity;
import com.qoo.magicmirror.constants.NetConstants;
import com.qoo.magicmirror.homepage.Value;
import com.qoo.magicmirror.net.NetHelper;

import java.util.ArrayList;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by Giraffe on 16/3/29.
 */
public class SpecialTopicDetailActivity extends BaseActivity implements View.OnClickListener {
    private VerticalViewpagerAdapter verticalViewpagerAdapter;
    private ArrayList<View> views;
    private VerticalViewpager verticalViewpager;
    private SpecialTopicDetailBean.DataEntity.StoryDataEntity data;
    private ImageView specialNethermostIv, viewpagerIv, shareIv, closeIv;
    private TextView smallTitleTv, titleTv, subTitleTv;
    private String storyId;
    private FrameLayout frameLayoutMiddle, frameLayoutUppermost, frameLayoutMain;
    private static final int COMPLETED = 0;
    private int time = 0;

    private boolean isLongClick;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == COMPLETED) {
                frameLayoutMiddle.setVisibility(View.INVISIBLE);
                Animation animation = AnimationUtils.loadAnimation(SpecialTopicDetailActivity.this, R.anim.special_topic_anim_invisible);
                frameLayoutMiddle.setAnimation(animation);
                frameLayoutUppermost.setAnimation(animation);
                frameLayoutUppermost.setVisibility(View.INVISIBLE);
            }
        }
    };

    @Override
    protected int setLayout() {
        return R.layout.activity_specialtopic_detail;
    }

    @Override
    protected void initData() {

        // 接收首页的storyId
        Intent intent = getIntent();
        storyId = intent.getStringExtra(Value.putStoryId);
        Log.d("SpecialTopicDetailActiv", storyId);

        ArrayList<String> token = new ArrayList<>();
        token.add(getString(R.string.token));
        token.add(getString(R.string.device_type));
        token.add(getString(R.string.stroy_id));
        ArrayList<String> value = new ArrayList<>();
        value.add("");
        value.add("1");
        value.add("2");
        final NetHelper netHelper = new NetHelper(this);

        netHelper.getPostInfo(NetConstants.STORY_INFO, token, value, SpecialTopicDetailBean.class, new NetHelper.NetListener<SpecialTopicDetailBean>() {
            @Override
            public void onSuccess(SpecialTopicDetailBean specialTopicDetailBean) {
                data = specialTopicDetailBean.getData().getStory_data();

                for (int i = 0; i < data.getImg_array().size(); i++) {
                    View view = LayoutInflater.from(SpecialTopicDetailActivity.this).inflate(R.layout.activity_specialtopic_detail_viewpager, null);

                    smallTitleTv = (TextView) view.findViewById(R.id.activity_specialtopic_detail_viewpager_little_title);
                    titleTv = (TextView) view.findViewById(R.id.activity_specialtopic_detail_viewpager_main_title);
                    subTitleTv = (TextView) view.findViewById(R.id.activity_specialtopic_detail_viewpager_content);
                    smallTitleTv.setText(data.getText_array().get(i).getSmallTitle());
                    titleTv.setText(data.getText_array().get(i).getTitle());
                    subTitleTv.setText(data.getText_array().get(i).getSubTitle());

                    views.add(view);
//
                }
                netHelper.setImage(specialNethermostIv, data.getImg_array().get(0));


//                Log.d("！！！！！！", data.getImg_array().get(0));
                verticalViewpagerAdapter = new VerticalViewpagerAdapter(views);
                verticalViewpager.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            isLongClick = true;
                            new onTouchThread().start();

                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            time = 0;
                            isLongClick = false;
                            Animation animation = AnimationUtils.loadAnimation(SpecialTopicDetailActivity.this, R.anim.special_topic_anim_visible);
                            frameLayoutMiddle.setAnimation(animation);
                            frameLayoutUppermost.setAnimation(animation);
                            frameLayoutMiddle.setVisibility(View.VISIBLE);
                            frameLayoutUppermost.setVisibility(View.VISIBLE);
                        }
                        return false;
                    }
                });
                verticalViewpager.setAdapter(verticalViewpagerAdapter);
                verticalViewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    }

                    @Override
                    public void onPageSelected(int position) {
                        netHelper.setImage(specialNethermostIv, data.getImg_array().get(position));

                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                });


            }

            @Override
            public void onFailure() {

            }
        });

    }

    @Override
    protected void initView() {
        views = new ArrayList<>();
        verticalViewpager = (VerticalViewpager) findViewById(R.id.activity_specialtopic_detail_viewpager);
        closeIv = (ImageView) findViewById(R.id.activity_specialtopic_detail_close_iv);
        shareIv = (ImageView) findViewById(R.id.activity_specialtopic_detail_share_iv);
        specialNethermostIv = (ImageView) findViewById(R.id.activity_specialtopic_detail_nethermost_iv);
        frameLayoutMiddle = (FrameLayout) findViewById(R.id.framelayout_middle);
        frameLayoutUppermost = (FrameLayout) findViewById(R.id.framelayout_uppermost);

//        frameLayoutMain = (FrameLayout) findViewById(R.id.activity_specialtopic_detail_main_framelayout);
//                frameLayoutUppermost = (FrameLayout) findViewById(R.id.framelayout_uppermost);

        closeIv.setOnClickListener(this);
        shareIv.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_specialtopic_detail_share_iv:
//
                ShareSDK.initSDK(this);
                OnekeyShare oks = new OnekeyShare();
                //关闭sso授权 
                // titleUrl是标题的网络链接，仅在人人网和QQ空间使用 
                oks.disableSSOWhenAuthorize();
                oks.setTitle("mob分享");
                oks.setTitleUrl("http://sharesdk.cn");
                // text是分享文本，所有平台都需要这个字段
                oks.setText("我是分享文本");
                // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数 //oks.setImagePath("/sdcard/test.jpg");
                // 确保SDcard下面存在此张图片 // url仅在微信（包括好友和朋友圈）中使用 
                oks.setImagePath("/sdcard/test.jpg");
                oks.setUrl("http://sharesdk.cn");
                // comment是我对这条分享的评论，仅在人人网和QQ空间使
                oks.setComment("我是测试评论文本");
                // site是分享此内容的网站名称，仅在QQ空间使用  
                oks.setSite(getString(R.string.app_name));
                // siteUrl是分享此内容的网站地址，仅在
                oks.setSiteUrl("http://sharesdk.cn");
                // 启动分享GUI
                oks.show(this);
                break;
            case R.id.activity_specialtopic_detail_close_iv:
                finish();
                break;
        }


    }

    private class onTouchThread extends Thread {
        @Override
        public void run() {
//            try {
//                Thread.sleep(2000);
//                Message msg = new Message();
//                msg.what = COMPLETED;
//                handler.sendMessage(msg);
//
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
            while (isLongClick) {
                time++;
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (time >= 2) {

                    handler.sendEmptyMessage(0);
                    break;
                }
            }


        }
    }

}
