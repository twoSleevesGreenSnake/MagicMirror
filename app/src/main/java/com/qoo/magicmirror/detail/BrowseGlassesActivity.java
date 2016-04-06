package com.qoo.magicmirror.detail;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.qoo.magicmirror.R;
import com.qoo.magicmirror.base.BaseActivity;
import com.qoo.magicmirror.base.BaseAdapter;
import com.qoo.magicmirror.constants.NetConstants;
import com.qoo.magicmirror.homepage.GoodsListBean;
import com.qoo.magicmirror.homepage.Value;
import com.qoo.magicmirror.net.NetHelper;
import com.qoo.magicmirror.wearatlas.WearAtlasActivity;

import java.util.ArrayList;

/**
 * Created by dllo on 16/3/29.
 */
public class BrowseGlassesActivity extends BaseActivity {
    private RecyclerView recyclerView;
    private ArrayList<String> strings;
    private NetHelper netHelper;
    private int screenHeight;
    private boolean btnNotShow = true;
    private ImageView backIv,buyIv,pircturesIv;
    private LinearLayout btnLayout;
    private ObjectAnimator animation;
    private ObjectAnimator animationBack;
    private TextView picturesTv;
    private static GoodsListBean.DataEntity.ListEntity data;
    public static void setData (GoodsListBean.DataEntity.ListEntity data){
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
        recyclerView = bindView(R.id.activity_detail_browse_glasses_rv);
        picturesTv = bindView(R.id.activity_detail_browse_glasses_pictures_tv);
        netHelper = new NetHelper(this);
        screenHeight = getWindowManager().getDefaultDisplay().getHeight();
        btnLayout = bindView(R.id.activity_detail_browse_glasses_pictures_bottom_layout);
        ObjectAnimator animator =ObjectAnimator.ofFloat(btnLayout, "translationX", -500);
        animator.setDuration(1);
        animator.start();

        animation = ObjectAnimator.ofFloat(btnLayout, "translationX", 500);
        animation.setDuration(1000);
        animationBack = ObjectAnimator.ofFloat(btnLayout, "translationX", -500);
        animationBack.setDuration(1000);




    }
    private void visibleLayout(){

        animation.start();
    }
    private void goneleLayout(){
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
        final NetHelper helper = new NetHelper(this);
        helper.setBackGround(bindView(R.id.activity_detail_browse_layout), data.getGoods_img());

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
            }
            if (position == getItemCount() - 1) {
                return LAST_MODE;
            }

            else {
                return CONTENT_MODE;
            }
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == CONTENT_MODE) {
                return new BrowseGlassesHolder(createItemLayout(R.layout.item_detail_browse_glasses_rv_content,parent));
            }
            if (viewType == HEAD_MODE) {
                return new FirstItemHolder(createItemLayout(R.layout.item_detail_browse_glasses_rv_head_first,parent));
            }
            if (viewType == EMPTY_MODE) {
                return new EmptyHolder(createItemLayout(R.layout.item_detail_browse_glasses_rv_content_empty,parent));
            }

            if (viewType == HEAD_SECOND_MODE) {
                return new HeadSecondItemHolder(createItemLayout(R.layout.item_detail_browse_glasses_title_second,parent));
            } else {
                return new LastItemHolder(createItemLayout(R.layout.item_detail_browse_last_content,parent));
            }

        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (getItemViewType(position) == CONTENT_MODE) {
                ((BrowseGlassesHolder) holder).contentPosition = position;
                ((BrowseGlassesHolder) holder).contentTv.setText(data.getGoods_data().get(position - 3).getIntroContent());
                ((BrowseGlassesHolder) holder).titleTv.setText(data.getGoods_data().get(position - 3).getLocation());
                ((BrowseGlassesHolder) holder).eTitleTv.setText(data.getGoods_data().get(position - 3).getEnglish());
                ((BrowseGlassesHolder) holder).locationTv.setText(data.getGoods_data().get(position - 3).getCountry());
                if (data.getDesign_des().size() < position - 3) {
                    return;
                }
                if (position != getItemCount() - 2) {
                    netHelper.setDrawable(((BrowseGlassesHolder) holder).backImg, data.getDesign_des().get(position - 3).getImg(), 150);
                } else {
                    RelativeLayout.LayoutParams params= (RelativeLayout.LayoutParams) ((BrowseGlassesHolder) holder).backImg.getLayoutParams();
                    params.setMargins(0, 0, 0, 500);
                    ((BrowseGlassesHolder) holder).backImg.setLayoutParams(params);
                    netHelper.setImage(((BrowseGlassesHolder) holder).backImg, data.getDesign_des().get(position - 3).getImg());
                }

            }
            if (getItemViewType(position) == HEAD_MODE) {
                ((FirstItemHolder) holder).eTitleTv.setText(data.getGoods_name());
                ((FirstItemHolder) holder).contentTv.setText(data.getInfo_des());
                ((FirstItemHolder) holder).priceTv.setText(data.getGoods_price());
                ((FirstItemHolder) holder).typeTitleTv.setText(data.getModel());

            }
//
        }

        @Override
        public int getItemCount() {
            return data.getGoods_data().size() + 4;
        }

        class BrowseGlassesHolder extends BaseHolder {
            private LinearLayout srcLayout;
            private RelativeLayout relativeLayout;
            private int contentPosition;
            private TextView titleTv, eTitleTv, locationTv, contentTv;
            private ImageView backImg;
            private int height;
            private int i = 0;

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
                srcLayout = (LinearLayout) itemView.findViewById(R.id.item_detail_brose_glasses_rv_content_src_content_layout1);
                relativeLayout = (RelativeLayout) itemView.findViewById(R.id.item_detail_brose_glasses_rv_content_src_layout);
                backImg = (ImageView) itemView.findViewById(R.id.item_detail_brose_glasses_rv_content_back_img);
//                final ViewTreeObserver viewTreeObserver = srcLayout.getViewTreeObserver();
//                final ViewTreeObserver.OnPreDrawListener listener = new ViewTreeObserver.OnPreDrawListener() {
//                    @Override
//                    public boolean onPreDraw() {
//                        height = srcLayout.getHeight();
//                        if (height!=0){
//                            Log.i("height",height+"");
//                        }
//                        return false;
//                    }
//                };
//                viewTreeObserver.addOnPreDrawListener(listener);
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        while (true){
//                            if (height!=0){
//                                srcLayout.getViewTreeObserver().removeOnPreDrawListener(listener);
//                                break;
//                            }
//                        }
//                    }
//                }).start();
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
                            relativeLayout.scrollTo((int) itemView.getX(),-((int) itemView.getY()/10-50));

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

            public FirstItemHolder(View itemView) {
                super(itemView);
                eTitleTv = (TextView) itemView.findViewById(R.id.item_detail_browse_glasses_rv_head_first_e_title_tv);
                typeTitleTv = (TextView) itemView.findViewById(R.id.item_detail_brose_glasses_rv_head_first_type_title);
                contentTv = (TextView) itemView.findViewById(R.id.item_detail_brose_glasses_rv_head_first_content_tv);
                priceTv = (TextView) itemView.findViewById(R.id.item_detail_brose_glasses_rv_head_first_price_tv);
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
        class LastSecondItemHolder extends BaseHolder{
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
            private float itemY;


            public HeadSecondItemHolder(final View itemView) {
                super(itemView);
//                recyclerView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
//                    @Override
//                    public void onChildViewAttachedToWindow(View view) {
//                        if (view.getId() ==itemView.getId());{
//                            Log.i("to","sssssss");
//                        }
//
//
//                    }
//
//                    @Override
//                    public void onChildViewDetachedFromWindow(View view) {
//                        if (view.getId() ==itemView.getId());{
//                            Log.i("from","ggsdfsdfsdf");
//
//                        }
//
//                    }
//                });
                recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);

                        if (btnNotShow&&itemView.getY()<=0){
                            Toast.makeText(BrowseGlassesActivity.this, "按钮出现", Toast.LENGTH_SHORT).show();
                            visibleLayout();
                            btnNotShow = false;

                        }
                        if (itemView.getY()>0&&!btnNotShow){
                            Toast.makeText(BrowseGlassesActivity.this, "按钮消失", Toast.LENGTH_SHORT).show();
                            goneleLayout();
                            btnNotShow = true;
                        }
                    }
                });
                titleTv = (TextView) itemView.findViewById(R.id.item_detail_browse_glasses_rv_title_second_tv);
            }
        }
    }

    class EmptyHolder extends RecyclerView.ViewHolder {
        public EmptyHolder(View itemView) {
            super(itemView);
        }
    }


}
