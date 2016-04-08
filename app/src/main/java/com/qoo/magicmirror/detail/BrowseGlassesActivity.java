package com.qoo.magicmirror.detail;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.qoo.magicmirror.R;
import com.qoo.magicmirror.alipay.H5PayDemoActivity;
import com.qoo.magicmirror.alipay.PayDemoActivity;
import com.qoo.magicmirror.alipay.PayResult;
import com.qoo.magicmirror.alipay.SignUtils;
import com.qoo.magicmirror.base.BaseActivity;
import com.qoo.magicmirror.base.BaseAdapter;
import com.qoo.magicmirror.constants.NetConstants;
import com.qoo.magicmirror.homepage.GoodsListBean;
import com.qoo.magicmirror.homepage.Value;
import com.qoo.magicmirror.net.NetHelper;
import com.qoo.magicmirror.order.AliPayData;
import com.qoo.magicmirror.order.PullOrderBean;
import com.qoo.magicmirror.wearatlas.WearAtlasActivity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

/**
 * Created by dllo on 16/3/29.
 */
public class BrowseGlassesActivity extends BaseActivity {
    private RecyclerView recyclerView;
    private NetHelper netHelper;
    private int screenHeight;
    private boolean btnNotShow = true;
    private ImageView backIv, buyIv, pircturesIv;
    private LinearLayout btnLayout;
    private ObjectAnimator animation;
    private ObjectAnimator animationBack;
    private TextView picturesTv;
    private static GoodsListBean.DataEntity.ListEntity data;
    private int screenWidth;
    private String orderinfo;

    public static String PARTNER = "";
    // 商户收款账号
    public static String SELLER = "";
    // 商户私钥，pkcs8格式
    public static String RSA_PRIVATE = "";
    // 支付宝公钥
    public static String RSA_PUBLIC = "";
    private final static int SDK_PAY_FLAG = 1;

    private boolean locationNotFinshed = true;


    /**
     * create the order info. 创建订单信息
     */


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
                        Toast.makeText(BrowseGlassesActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                    } else {
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(BrowseGlassesActivity.this, "支付结果确认中", Toast.LENGTH_SHORT).show();

                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(BrowseGlassesActivity.this, "支付失败", Toast.LENGTH_SHORT).show();

                        }
                    }
                    break;
                }
                default:
                    break;
            }
        }

        ;
    };


    private String getOrderInfo(String subject, String body, String price) {


        return orderinfo;
    }


    //序列化传不了 不知道为了点啥
    public static void setData(GoodsListBean.DataEntity.ListEntity data) {
        BrowseGlassesActivity.data = data;
    }

    /**
     * 实现浏览眼镜详情的activity
     *
     * @return
     */
    @Override
    protected int setLayout() {
        return R.layout.activity_detail_browse_glasses;
    }

    @Override
    protected void initView() {
        buyIv = bindView(R.id.activity_detail_browse_glasses_buy_iv);
        buyIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BrowseGlassesActivity.this, DetialActivity.class);
                intent.putExtra("token", "f7d565803fbdb8f9c0bc64122895eea3");
                intent.putExtra("goodsId", data.getGoods_id());
                intent.putExtra("price", data.getGoods_price());
                ArrayList<String> keys = new ArrayList<String>();
                ArrayList<String> values = new ArrayList<String>();
                keys.add("token");
                keys.add("goods_id");
                keys.add("goods_num");
                keys.add("price");
                keys.add("discout_id");
                keys.add("device_type");
                values.add("f7d565803fbdb8f9c0bc64122895eea3");
                values.add(data.getGoods_id());
                values.add("1");
                values.add(data.getGoods_price());
                values.add("");
                values.add("1");
                NetHelper.newNetHelper(BrowseGlassesActivity.this).getPostInfo(NetConstants.SUB_ORDER, keys, values, PullOrderBean.class, new NetHelper.NetListener<PullOrderBean>() {


                    @Override
                    public void onSuccess(PullOrderBean pullOrderBean) {
                        Log.i("response", pullOrderBean.toString());
                        ArrayList<String> keys = new ArrayList<String>();
                        ArrayList<String> values = new ArrayList<String>();
                        keys.add("token");

                        values.add("f7d565803fbdb8f9c0bc64122895eea3");
                        keys.add("order_no");
                        values.add(pullOrderBean.getData().getOrder_id());
                        keys.add("addr_id");
                        values.add(pullOrderBean.getData().getAddress().getAddr_id());
                        keys.add("goodsname");
                        values.add(pullOrderBean.getData().getGoods().getGoods_name());

                        NetHelper.newNetHelper(BrowseGlassesActivity.this).getPostInfo(NetConstants.ALI_PAY_SUB, keys, values, AliPayData.class, new NetHelper.NetListener<AliPayData>() {


                            @Override
                            public void onSuccess(AliPayData aliPayData) {
                                orderinfo = aliPayData.getData().getStr();
                                pay(buyIv);

                            }

                            @Override
                            public void onFailure() {

                            }
                        });
                    }

                    @Override
                    public void onFailure() {

                    }
                });


            }
        });
        recyclerView = bindView(R.id.activity_detail_browse_glasses_rv);
        picturesTv = bindView(R.id.activity_detail_browse_glasses_pictures_tv);
        netHelper = NetHelper.newNetHelper(this);
        screenHeight = getWindowManager().getDefaultDisplay().getHeight();
        btnLayout = bindView(R.id.activity_detail_browse_glasses_pictures_bottom_layout);
        //开始的时候先移出屏幕,好看不少
        screenWidth = getWindowManager().getDefaultDisplay().getWidth();
        ObjectAnimator animator = ObjectAnimator.ofFloat(btnLayout, "translationX", -1000);
        animator.setDuration(1);
        animator.start();
        //进场动画

        //出厂动画


    }

    private void visibleLayout() {
        if (locationNotFinshed) {
            animation = ObjectAnimator.ofFloat(btnLayout, "translationX", (screenWidth - btnLayout.getWidth()) / 2);
            animation.setDuration(500);
            locationNotFinshed = false;
        }
        animation.start();
    }

    private void goneLayout() {
        animationBack = ObjectAnimator.ofFloat(btnLayout, "translationX", -1000);
        animationBack.setDuration(500);
        animationBack.start();
        new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                return false;
            }
        }).sendEmptyMessageDelayed(50, 1000);

    }


    @Override
    protected void initData() {
//        Intent intent = getIntent();
//        data = intent.getParcelableExtra("GoodsListBean.DataEntity.ListEntity");
        picturesTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BrowseGlassesActivity.this, WearAtlasActivity.class);
                startActivity(intent);
            }
        });

        netHelper.setBackGround(bindView(R.id.activity_detail_browse_layout), data.getGoods_img());

        recyclerView.setAdapter(new BrowseGlassesAdapter());
        recyclerView.setLayoutManager(new LinearLayoutManager(BrowseGlassesActivity.this));

    }

    /**
     * 此activity的适配器
     */
    private class BrowseGlassesAdapter extends BaseAdapter {
        protected static final int HEAD_MODE = 1;
        protected static final int CONTENT_MODE = 2;
        protected static final int LAST_SECOND_MODE = 3;
        protected static final int LAST_MODE = 4;
        protected static final int HEAD_SECOND_MODE = 5;
        protected static final int EMPTY_MODE = 6;


        @Override
        public int getItemViewType(int position) {
            if (position == 0) {
                return HEAD_MODE;

            }
            if (position == 1) {
                return EMPTY_MODE;
            }
            if (position == 2) {
                return HEAD_SECOND_MODE;
            } else {
                return CONTENT_MODE;
            }
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            if (viewType == HEAD_MODE) {
                return new FirstItemHolder(createItemLayout(R.layout.item_detail_browse_glasses_rv_head_first, parent));
            }
            if (viewType == EMPTY_MODE) {
                return new EmptyHolder(createItemLayout(R.layout.item_detail_browse_glasses_rv_content_empty, parent));
            }

            if (viewType == HEAD_SECOND_MODE) {
                return new HeadSecondItemHolder(createItemLayout(R.layout.item_detail_browse_glasses_title_second, parent));

            } else {
                return new BrowseGlassesHolder(createItemLayout(R.layout.item_detail_browse_glasses_rv_content, parent));
            }

        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (getItemViewType(position) == CONTENT_MODE) {
                ((BrowseGlassesHolder) holder).contentPosition = position;
                if (data.getGoods_data().size() > position - 3) {
                    ((BrowseGlassesHolder) holder).srcLayout.setVisibility(View.VISIBLE);
                    ((BrowseGlassesHolder) holder).contentTv.setText(data.getGoods_data().get(position - 3).getIntroContent());
                    ((BrowseGlassesHolder) holder).locationTv.setText(data.getGoods_data().get(position - 3).getCountry());
                    ((BrowseGlassesHolder) holder).titleTv.setText(data.getGoods_data().get(position - 3).getLocation());
                    if (data.getGoods_data().get(position - 3).getLocation().equals("")) {
                        ((BrowseGlassesHolder) holder).locationTv.setText(data.getGoods_data().get(position - 3).getName());
                    }
                    netHelper.setDrawable(((BrowseGlassesHolder) holder).backImg, data.getDesign_des().get(position - 3).getImg(), 150);

                    ((BrowseGlassesHolder) holder).eTitleTv.setText(data.getGoods_data().get(position - 3).getEnglish());

                } else {
                    ((BrowseGlassesHolder) holder).srcLayout.setVisibility(View.INVISIBLE);
                    netHelper.setImage(((BrowseGlassesHolder) holder).backImg, data.getDesign_des().get(position - 3).getImg());


                }

                if (data.getGoods_data().size() > position - 3) {
                } else {
                }

            }
            if (getItemViewType(position) == HEAD_MODE) {
                ((FirstItemHolder) holder).eTitleTv.setText(data.getGoods_name());
                ((FirstItemHolder) holder).contentTv.setText(data.getInfo_des());
                ((FirstItemHolder) holder).priceTv.setText(data.getGoods_price());
                ((FirstItemHolder) holder).typeTitleTv.setText(data.getModel());

            }
            if (getItemViewType(position) == HEAD_SECOND_MODE) {
                ((HeadSecondItemHolder) holder).titleTv.setText(data.getBrand());
            }
//
        }

        @Override
        public int getItemCount() {
            return data.getDesign_des().size() + 3;
        }

        class BrowseGlassesHolder extends BaseHolder {
            private LinearLayout srcLayout;
            private RelativeLayout relativeLayout;
            private int contentPosition;
            private TextView titleTv, eTitleTv, locationTv, contentTv;
            private ImageView backImg;


            /**
             * 浏览眼镜详情的缓存类
             *
             * @param itemView
             */
            public BrowseGlassesHolder(final View itemView) {
                super(itemView);
                titleTv = (TextView) itemView.findViewById(R.id.item_detail_brose_glasses_rv_content_title_tv);
                eTitleTv = (TextView) itemView.findViewById(R.id.item_detail_brose_glasses_rv_content_etitle_tv);
                locationTv = (TextView) itemView.findViewById(R.id.item_detail_brose_glasses_rv_content_location_tv);
                contentTv = (TextView) itemView.findViewById(R.id.item_detail_brose_glasses_rv_content_src_content_tv);
                srcLayout = (LinearLayout) itemView.findViewById(R.id.item_detail_brose_glasses_rv_content_src_content_layout);
                relativeLayout = (RelativeLayout) itemView.findViewById(R.id.item_detail_brose_glasses_rv_content_src_layout);
                backImg = (ImageView) itemView.findViewById(R.id.item_detail_brose_glasses_rv_content_back_img);
                recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);
                    }

                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);

                        //实现滑动的核心代码
                        if (BrowseGlassesAdapter.this.getItemViewType(contentPosition) == CONTENT_MODE) {
                            relativeLayout.scrollTo((int) itemView.getX(), -((int) itemView.getY() / 10 - 50));

                        }

                    }
                });

            }

            @Override
            protected void initView() {

            }
        }

        /**
         * 第一个行布局的缓存类
         */
        class FirstItemHolder extends RecyclerView.ViewHolder {
            private TextView eTitleTv, typeTitleTv, contentTv, priceTv;
            private RelativeLayout layout;
            private float height;
            private boolean isFirst = true;

            public FirstItemHolder(final View itemView) {
                super(itemView);
                eTitleTv = (TextView) itemView.findViewById(R.id.item_detail_browse_glasses_rv_head_first_e_title_tv);
                typeTitleTv = (TextView) itemView.findViewById(R.id.item_detail_brose_glasses_rv_head_first_type_title);
                contentTv = (TextView) itemView.findViewById(R.id.item_detail_brose_glasses_rv_head_first_content_tv);
                priceTv = (TextView) itemView.findViewById(R.id.item_detail_brose_glasses_rv_head_first_price_tv);
                layout = (RelativeLayout) itemView.findViewById(R.id.item_detail_browse_glasses_rv_head_first_layout);
                recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                        if (isFirst) {
                            height = itemView.getHeight();
                            if (height != 0) {
                                isFirst = false;
                            }

                        }
                        //改变透明的的方法
                        layout.setAlpha((float) ((0.5 / height) * itemView.getBottom()));
                    }
                });
            }
        }

        /**
         * 最后一行的缓存类
         */
        class LastItemHolder extends RecyclerView.ViewHolder {
            public LastItemHolder(View itemView) {
                super(itemView);
            }
        }

        /**
         * 倒数第二行的缓存类
         */
        class LastSecondItemHolder extends BaseHolder {
            private ImageView imageView;

            public LastSecondItemHolder(View itemView) {
                super(itemView);
            }

            @Override
            protected void initView() {
                imageView = bindView(R.id.item_detail_browse_last_second_content_iv);

            }
        }

        class HeadSecondItemHolder extends RecyclerView.ViewHolder {
            private TextView titleTv;
            private ImageView line;

            public HeadSecondItemHolder(final View itemView) {
                super(itemView);
                line = (ImageView) itemView.findViewById(R.id.item_detail_browse_glasses_rv_title_second_line);
                //按钮动画的方法
                recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                        if (btnNotShow && itemView.getY() <= 0) {
                            visibleLayout();
                            btnNotShow = false;

                        }
                        if (itemView.getY() > 0 && !btnNotShow) {
                            goneLayout();
                            btnNotShow = true;
                        }
                    }
                });
                titleTv = (TextView) itemView.findViewById(R.id.item_detail_browse_glasses_rv_title_second_tv);
            }
        }
    }

    /**
     * 类如其名
     */
    class EmptyHolder extends RecyclerView.ViewHolder {
        public EmptyHolder(View itemView) {
            super(itemView);
        }
    }

    /**
     * call alipay sdk pay. 调用SDK支付
     */
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
        String orderInfo = getOrderInfo("测试的商品", "该测试商品的详细描述", "0.01");

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
                PayTask alipay = new PayTask(BrowseGlassesActivity.this);
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
