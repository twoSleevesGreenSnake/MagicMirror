package com.qoo.magicmirror.view;

import com.qoo.magicmirror.R;
import com.qoo.magicmirror.base.BaseActivity;

/**
 * Created by dllo on 16/4/14.
 */
public class TestActivity extends BaseActivity{
    private SYXImageLayout layout;
    @Override
    protected int setLayout() {
        return R.layout.test;
    }

    @Override
    protected void initData() {
        layout.setImage("http://banbao.chazidian.com/uploadfile/2016-01-25/s145368924044608.jpg");
    }

    @Override
    protected void initView() {
       layout = bindView(R.id.layout);
    }
}
