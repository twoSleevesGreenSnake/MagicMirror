package com.qoo.magicmirror.order;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.qoo.magicmirror.R;
import com.qoo.magicmirror.base.BaseActivity;
import com.qoo.magicmirror.constants.NetConstants;
import com.qoo.magicmirror.net.NetHelper;

import java.util.ArrayList;

/**
 * Created by dllo on 16/4/7.
 */
public class OrderDetailActivity extends BaseActivity implements View.OnClickListener {

    private ImageView closeIv;
    private TextView showAddressTv, addAddressTv;
    private Button sureBtn;
    private NetHelper netHelper;

    @Override
    protected int setLayout() {
        return R.layout.activity_orderdetail;
    }

    @Override
    protected void initView() {
        netHelper = NetHelper.newNetHelper(this);
        closeIv = bindView(R.id.activity_orderdetail_close_iv);
        showAddressTv = bindView(R.id.activity_orderdetail_add_address_tv);
        addAddressTv = bindView(R.id.activity_orderdetail_add_address_click_tv);
        sureBtn = bindView(R.id.activity_orderdetail_btn);
        closeIv.setOnClickListener(this);
        addAddressTv.setOnClickListener(this);
        sureBtn.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_orderdetail_close_iv:
                finish();
                break;
            case R.id.activity_orderdetail_add_address_click_tv:
                break;
            case R.id.activity_orderdetail_btn:
                sureOrder();// 确认下单
                break;
            default:
                break;
        }
    }

    private void sureOrder() {
        ArrayList<String> token = new ArrayList<>();
        token.add(getString(R.string.token));
        token.add(getString(R.string.goods_id));
        token.add(getString(R.string.goods_num));
        token.add(getString(R.string.price));
        token.add(getString(R.string.discout_id));
        token.add(getString(R.string.device_type));
        ArrayList<String> value = new ArrayList<>();
        value.add(BaseActivity.token);
        // TODO 等待传值
//        value.add("2");
//        value.add("1");
//        value.add("3850");
//        value.add("");
//        value.add("1");
        netHelper.getPostInfo(NetConstants.SUB_ORDER, token, value, SureOrderBean.class, new NetHelper.NetListener<SureOrderBean>() {
            @Override
            public void onSuccess(SureOrderBean sureOrderBean) {

            }

            @Override
            public void onFailure() {

            }
        });

    }
}
