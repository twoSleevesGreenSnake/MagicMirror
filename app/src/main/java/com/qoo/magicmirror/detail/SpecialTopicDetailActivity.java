package com.qoo.magicmirror.detail;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.qoo.magicmirror.R;

import java.util.ArrayList;

/**
 * Created by Giraffe on 16/3/29.
 */
public class SpecialTopicDetailActivity extends AppCompatActivity implements View.OnClickListener {
    private VerticalViewpagerAdapter verticalViewpagerAdapter;
    private ArrayList<View> views;
    private VerticalViewpager verticalViewpager;
    private int[] middlePic = {R.mipmap.m, R.mipmap.zhua};
    private int[] background = {R.mipmap.tiger, R.mipmap.cat};
    private ImageView specialNethermostIv, viewpagerIv,shareIv,closeIv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specialtopic_detail);
        initview();
    }

    private void initview() {
        views = new ArrayList<>();
        verticalViewpager = (VerticalViewpager) findViewById(R.id.activity_specialtopic_detail_viewpager);
        closeIv = (ImageView) findViewById(R.id.activity_specialtopic_detail_close_iv);
        shareIv = (ImageView) findViewById(R.id.activity_specialtopic_detail_share_iv);
        closeIv.setOnClickListener(this);
        shareIv.setOnClickListener(this);
        specialNethermostIv = (ImageView) findViewById(R.id.activity_specialtopic_detail_nethermost_iv);
        specialNethermostIv.setImageResource(background[0]);
        for (int i = 0; i < middlePic.length; i++) {
            View view = LayoutInflater.from(SpecialTopicDetailActivity.this).inflate(R.layout.activity_specialtopic_detail_viewpager, null);
            viewpagerIv = (ImageView) view.findViewById(R.id.activity_specialtopic_detail_viewpager_iv);
            viewpagerIv.setImageResource(middlePic[i]);
//            viewpagerIv.setOnLongClickListener(new View.OnLongClickListener() {
//                @Override
//                public boolean onLongClick(View v) {
//
//                    return true;
//                }
//            });
            views.add(view);
        }
        verticalViewpagerAdapter = new VerticalViewpagerAdapter(views);
        verticalViewpager.setAdapter(verticalViewpagerAdapter);

        verticalViewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                specialNethermostIv.setImageResource(background[position]);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.activity_specialtopic_detail_share_iv:
                break;
            case R.id.activity_specialtopic_detail_close_iv:
                finish();
                break;
        }
    }
}
