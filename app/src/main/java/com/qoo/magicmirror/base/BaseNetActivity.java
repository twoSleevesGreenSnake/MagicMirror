package com.qoo.magicmirror.base;

import com.qoo.magicmirror.net.NetHelper;

import java.util.ArrayList;

/**
 * Created by dllo on 16/4/4.
 */
public abstract class BaseNetActivity<T> extends BaseActivity{

//    @Override
//    protected void initNet() {
//        NetHelper helper = new NetHelper(this);
//        helper.getPostInfo(setUrl(), setKey(), setValue(), setEntityType(), new NetHelper.NetListener<T>() {
//            @Override
//            public void onSuccess(T t) {
//            netHasSucceed(t);
//            }
//
//            @Override
//            public void onFailure() {
//
//            }
//        });
//    }

    protected abstract String setUrl();
    protected abstract ArrayList<String> setKey();
    protected abstract ArrayList<String> setValue();
    protected abstract Class<T> setEntityType();
    protected abstract void netHasSucceed (T t);
}
