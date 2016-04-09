package com.qoo.magicmirror.order;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.qoo.magicmirror.R;
import com.qoo.magicmirror.alipay.H5PayDemoActivity;
import com.qoo.magicmirror.alipay.PayResult;
import com.qoo.magicmirror.alipay.SignUtils;
import com.qoo.magicmirror.base.BaseActivity;
import com.qoo.magicmirror.constants.NetConstants;
import com.qoo.magicmirror.net.NetHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

/**
 * Created by dllo on 16/4/7.
 * <p/>
 * 订单界面
 */
public class OrderDetailActivity extends BaseActivity implements View.OnClickListener {

    private ImageView closeIv, goodsIv;
    private TextView showNameTv,showAddressTv, showNumberTv, addAddressTv, goodsNameTv, goodsDesTv, goodsPriceTv;
    private Button sureBtn;
    private NetHelper netHelper;
    private String price;
    private String goodsId;
    private PullOrderBean pullOrderBean;
    private final static int SDK_PAY_FLAG = 1;
    private String orderInfo;
    public static String RSA_PRIVATE = "";
    private boolean orderHasSure = false;


    //支付宝之后的回调 判断是否支付成功

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((String) msg.obj);
                    /**
                     * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
                     * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
                     * docType=1) 建议商户依赖异步通知
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息

                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Toast.makeText(OrderDetailActivity.this, R.string.pay_success, Toast.LENGTH_SHORT).show();
                    } else {
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(OrderDetailActivity.this, R.string.pay_wait, Toast.LENGTH_SHORT).show();

                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(OrderDetailActivity.this, "支付失败", Toast.LENGTH_SHORT).show();


                        }
                    }
                    break;
                }
                default:
                    break;
            }
        }


    };


    private String getOrderInfo() {


        return orderInfo;
    }

    @Override
    protected int setLayout() {
        return R.layout.activity_orderdetail;
    }

    @Override
    protected void initView() {
        netHelper = NetHelper.newNetHelper(this);
        closeIv = bindView(R.id.activity_orderdetail_close_iv);
        showNameTv = bindView(R.id.activity_orderdetail_name_tv);
        showAddressTv = bindView(R.id.activity_orderdetail_address_tv);
        showNumberTv = bindView(R.id.activity_orderdetail_number_tv);
        goodsIv = bindView(R.id.activity_orderdetail_goods_iv);
        goodsNameTv = bindView(R.id.activity_orderdetail_goods_name_tv);
        goodsDesTv = bindView(R.id.activity_orderdetail_goods_content_tv);
        goodsPriceTv = bindView(R.id.activity_orderdetail_goods_price_tv);
        addAddressTv = bindView(R.id.activity_orderdetail_add_address_click_tv);
        sureBtn = bindView(R.id.activity_orderdetail_btn);
        closeIv.setOnClickListener(this);
        addAddressTv.setOnClickListener(this);
        sureBtn.setOnClickListener(this);
    }

    /**
     * 获取intent里面的值并设置监听
     */
    @Override
    protected void initData() {
        Intent intent = getIntent();
        price = intent.getStringExtra("price");
        Log.i("price", price + goodsId);
        goodsId = intent.getStringExtra("goodsId");
        sureOrder();
        addAddressTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(OrderDetailActivity.this, DetailAddressActivity.class), 299);
            }
        });
    }

    // 点击 地址跳转编辑完成回来改变地址内容
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
            DetailAddressBean.DataBean.ListBean detailAddressBean = data.getParcelableExtra("addressData");
            showNameTv.setText(getString(R.string.name) + detailAddressBean.getUsername());
            showAddressTv.setText(getString(R.string.adress) + detailAddressBean.getAddr_info());
            showNumberTv.setText(getString(R.string.cellnumber) + detailAddressBean.getCellphone());
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (showNumberTv.length() != 0) {
            addAddressTv.setText(R.string.change_address);
        } else {
            addAddressTv.setText(R.string.add_address);
        }
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
                showPayDialog();// 显示dialog
                break;
            default:
                break;
        }
    }

    private void showPayDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.activity_orderdetail_sure_dialog, null);
        builder.setView(view);
        view.findViewById(R.id.activity_orderdetail_sure_dialog_wechat_rl).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        view.findViewById(R.id.activity_orderdetail_sure_dialog_zfb_rl).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (orderHasSure == false) {
                    netFailed();
                    return;
                }
                ArrayList<String> keys = new ArrayList<String>();
                ArrayList<String> values = new ArrayList<String>();

                keys.add(getString(R.string.token));
                values.add(token);
                keys.add(getString(R.string.order_no));
                values.add(pullOrderBean.getData().getOrder_id());
                keys.add(getString(R.string.addr_id));
                values.add(pullOrderBean.getData().getAddress().getAddr_id());
                keys.add(getString(R.string.goods_name));
                values.add(pullOrderBean.getData().getGoods().getGoods_name());
                NetHelper.newNetHelper(OrderDetailActivity.this).getPostInfo(NetConstants.ALI_PAY_SUB, keys, values, AliPayData.class, new NetHelper.NetListener<AliPayData>() {
                    @Override
                    public void onSuccess(AliPayData aliPayData) {
                        orderInfo = aliPayData.getData().getStr();
                        pay(sureBtn);
                    }

                    @Override
                    public void onFailure() {

                    }
                });

            }
        });
        builder.show();
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
        value.add(goodsId);
        value.add("1");
        value.add(price);
        value.add("");
        value.add("1");

        netHelper.getPostInfo(NetConstants.SUB_ORDER, token, value, PullOrderBean.class, new NetHelper.NetListener<PullOrderBean>() {


            @Override
            public void onSuccess(PullOrderBean pullOrderBean) {
                orderHasSure = true;
                OrderDetailActivity.this.pullOrderBean = pullOrderBean;
                showNameTv.setText(getString(R.string.name) + pullOrderBean.getData().getAddress().getUsername());
                showAddressTv.setText(getString(R.string.adress) + pullOrderBean.getData().getAddress().getAddr_info());
                showNumberTv.setText(getString(R.string.cellnumber) + pullOrderBean.getData().getAddress().getCellphone());
                netHelper.setImage(goodsIv, pullOrderBean.getData().getGoods().getPic());
                goodsNameTv.setText(pullOrderBean.getData().getGoods().getGoods_name());
                goodsDesTv.setText(pullOrderBean.getData().getGoods().getDes());
                goodsPriceTv.setText(pullOrderBean.getData().getGoods().getPrice());
            }

            @Override
            public void onFailure() {

            }
        });
    }


    public void pay(View v) {
//		if (TextUtils.isEmpty(PARTNER) || TextUtils.isEmpty(RSA_PRIVATE) || TextUtils.isEmpty(SELLER)) {
//			new AlertDialog.Builder(this).setTitle("警告").setMessage("需要配置PARTNER | RSA_PRIVATE| SELLER")
//					.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//						public void onClick(DialogInterface dialoginterface, int i) {
//							//
//							finish();
//						}
//					}).show();
//			return;
//		}
        String orderInfo = getOrderInfo();

//		/**
//		 * 特别注意，这里的签名逻辑需要放在服务端，切勿将私钥泄露在代码中！
//		 */
        String sign = sign(orderInfo);
        //		try {
//			/**
//			 * 仅需对sign 做URL编码
//			 */
//			sign = URLEncoder.encode(sign, "UTF-8");
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}

//
        /**
         * 完整的符合支付宝参数规范的订单信息
         */
        final String payInfo = orderInfo + "&sign=\"" + sign + "\"&" + getSignType();

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask(OrderDetailActivity.this);
                // 调用支付接口，获取支付结果
                String result = alipay.pay(payInfo, true);

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    /**
     * get the sdk version. 获取SDK版本号
     */
    public void getSDKVersion() {
        PayTask payTask = new PayTask(this);
        String version = payTask.getVersion();
        Toast.makeText(this, version, Toast.LENGTH_SHORT).show();
    }

    /**
     * 原生的H5（手机网页版支付切natvie支付） 【对应页面网页支付按钮】
     *
     * @param v
     */
    public void h5Pay(View v) {
        Intent intent = new Intent(this, H5PayDemoActivity.class);
        Bundle extras = new Bundle();
        /**
         * url是测试的网站，在app内部打开页面是基于webview打开的，demo中的webview是H5PayDemoActivity，
         * demo中拦截url进行支付的逻辑是在H5PayDemoActivity中shouldOverrideUrlLoading方法实现，
         * 商户可以根据自己的需求来实现
         */
        String url = "http://m.meituan.com";
        // url可以是一号店或者美团等第三方的购物wap站点，在该网站的支付过程中，支付宝sdk完成拦截支付
        extras.putString("url", url);
        intent.putExtras(extras);
        startActivity(intent);

    }


    /**
     * get the out_trade_no for an order. 生成商户订单号，该值在商户端应保持唯一（可自定义格式规范）
     */
    private String getOutTradeNo() {
        SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss", Locale.getDefault());
        Date date = new Date();
        String key = format.format(date);

        Random r = new Random();
        key = key + r.nextInt();
        key = key.substring(0, 15);
        return key;
    }

    /**
     * sign the order info. 对订单信息进行签名
     *
     * @param content 待签名订单信息
     */
    private String sign(String content) {
        return SignUtils.sign(content, RSA_PRIVATE);
    }

    /**
     * get the sign type we use. 获取签名方式
     */
    private String getSignType() {
        return "sign_type=\"RSA\"";
    }
}
