package com.qoo.magicmirror.base;

import com.qoo.magicmirror.net.NetHelper;

import java.util.ArrayList;

/**
 * Created by dllo on 16/4/4.
 *
 * 所有网络拉去的Activity
 */
public abstract class BaseNetActivity<T> extends BaseActivity{
    // TODO 未完成
    protected abstract String setUrl();
    protected abstract ArrayList<String> setKey();
    protected abstract ArrayList<String> setValue();
    protected abstract Class<T> setEntityType();
    protected abstract void netHasSucceed (T t);
}
