package com.qoo.magicmirror.detail;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Point;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qoo.magicmirror.R;
import com.qoo.magicmirror.base.BaseActivity;
import com.qoo.magicmirror.base.BaseAdapter;
import com.qoo.magicmirror.homepage.GoodsListBean;
import com.qoo.magicmirror.net.NetHelper;
import com.qoo.magicmirror.order.OrderDetailActivity;
import com.qoo.magicmirror.tools.ViewSizeTool;
import com.qoo.magicmirror.view.SYXImageLayout;
import com.qoo.magicmirror.wearatlas.WearAtlasActivity;

/**
 * Created by dllo on 16/3/29.
 *
 * 浏览详情的Activity以及内部的适配器
 */
public class BrowseGlassesActivity extends BaseActivity {

    private RecyclerView recyclerView;
    private NetHelper netHelper;
    private int screenHeight;
    private boolean btnNotShow = true;
    private ImageView backIv, buyIv, pircturesIv;
    private RelativeLayout btnLayout;
    private ObjectAnimator animation;
    private ObjectAnimator animationBack;
    private TextView picturesTv;
    private static GoodsListBean.DataEntity.ListEntity data;
    private int screenWidth;
    private float itemHeight = 0;
    private boolean locationNotFinshed = true;
    private boolean notIsFirstItem = false;

    /**
     * 从主页获取数据的静态方法
     *
     * @param data 详情页的数据
     */
    public static void setData(GoodsListBean.DataEntity.ListEntity data) {
        BrowseGlassesActivity.data = data;
    }

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
                judgeToken(new TokenListener() {
                    @Override
                    public void tokenIsNothing() {

                    }
                    @Override
                    public void logInSuccess() {
                        Intent intent = new Intent(BrowseGlassesActivity.this, OrderDetailActivity.class);
                        intent.putExtra(getString(R.string.goodsId), data.getGoods_id());
                        intent.putExtra(getString(R.string.price), data.getGoods_price());
                        startActivity(intent);
                    }
                });//判断有没有token的方法
            }
        });
        backIv = bindView(R.id.activity_detail_browse_glasses_pictures_tv_back_iv);
        recyclerView = bindView(R.id.activity_detail_browse_glasses_rv);
        picturesTv = bindView(R.id.activity_detail_browse_glasses_pictures_tv);
        netHelper = NetHelper.newNetHelper(this);
        Point size = new Point();
        Display display = getWindowManager().getDefaultDisplay();
        display.getRealSize(size);

        screenHeight = size.y;
        screenWidth = size.x;
        btnLayout = bindView(R.id.activity_detail_browse_glasses_pictures_bottom_layout);
        //开始的时候先移出屏幕,好看不少
        screenWidth = getWindowManager().getDefaultDisplay().getWidth();
        ObjectAnimator animator = ObjectAnimator.ofFloat(btnLayout, "translationX", -1500);
        animator.setDuration(1);
        animator.start();


    }

    private void visibleLayout() {
        if (locationNotFinshed) {
            animation = ObjectAnimator.ofFloat(btnLayout, "translationX", (screenWidth - btnLayout.getWidth()) / 2 - 39);
            animation.setDuration(500);
            locationNotFinshed = false;
        }
        animation.start();
    }

    private void goneLayout() {
        animationBack = ObjectAnimator.ofFloat(btnLayout, "translationX", -1500);
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
        backIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        picturesTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WearAtlasActivity.setData(data);
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
                    ((BrowseGlassesHolder) holder).backImg.setImage(data.getDesign_des().get(position - 3).getImg());
                    ((BrowseGlassesHolder) holder).eTitleTv.setText(data.getGoods_data().get(position - 3).getEnglish());
                } else {
                    ((BrowseGlassesHolder) holder).srcLayout.setVisibility(View.INVISIBLE);
                    ((BrowseGlassesHolder) holder).backImg.setImage(data.getDesign_des().get(position - 3).getImg());
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
            private SYXImageLayout backImg;

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
                backImg = (SYXImageLayout) itemView.findViewById(R.id.item_detail_brose_glasses_rv_content_back_img);
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
            private int y;

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
                        if (btnNotShow && (itemView.getY() <= 0 || (itemHeight == itemView.getY() && itemView.getY() < 300))) {
                            visibleLayout();
                            btnNotShow = false;
                        }
                        if (itemView.getY() > 0 && !btnNotShow && (itemHeight != itemView.getY())) {
                            goneLayout();
                            btnNotShow = true;
                        }
                        itemHeight = itemView.getY();
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
}
