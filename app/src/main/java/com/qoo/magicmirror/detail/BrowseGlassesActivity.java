package com.qoo.magicmirror.detail;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qoo.magicmirror.R;
import com.qoo.magicmirror.base.BaseActivity;
import com.qoo.magicmirror.constants.NetConstants;
import com.qoo.magicmirror.homepage.GoodsListBean;
import com.qoo.magicmirror.net.NetHelper;

import java.util.ArrayList;

/**
 * Created by dllo on 16/3/29.
 */
public class BrowseGlassesActivity extends BaseActivity {
    private RecyclerView recyclerView;
    private ArrayList<String> strings;
    private NetHelper netHelper;
    private GoodsListBean.DataEntity.ListEntity data;

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
        netHelper = new NetHelper(this);
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        data = intent.getParcelableExtra("GoodsListBean.DataEntity.ListEntity");
        recyclerView.setAdapter(new BrowseGlassesAdapter());
        recyclerView.setLayoutManager(new LinearLayoutManager(BrowseGlassesActivity.this));
//        strings = new ArrayList<>();
//        for (int i = 0; i < 30; i++) {
//            strings.add("1");
//        }
//        ArrayList<String> token = new ArrayList<>();
//        token.add(getString(R.string.token));
//        token.add(getString(R.string.device_type));
//        token.add(getString(R.string.page));
//        token.add(getString(R.string.last_time));
//        token.add(getString(R.string.category_id));
//        token.add(getString(R.string.version));
//        ArrayList<String> value = new ArrayList<>();
//        value.add("");
//        value.add(getString(R.string.one));
//        value.add("");
//        value.add("");
//        value.add("");
//        value.add(getString(R.string.one_point_zero_point_zero));
//        netHelper.getPostInfo(NetConstants.GOODS_TYPE, token, value, GoodsListBean.class, new NetHelper.NetListener<GoodsListBean>() {
//            @Override
//            public void onSuccess(GoodsListBean goodsListBean) {
//                data = goodsListBean.getData().getList().get(0);
//                recyclerView.setAdapter(new BrowseGlassesAdapter());
//                recyclerView.setLayoutManager(new LinearLayoutManager(BrowseGlassesActivity.this));
//            }
//
//            @Override
//            public void onFailure() {
//
//            }
//        });


    }


    private class BrowseGlassesAdapter extends RecyclerView.Adapter {
        protected static final int HEAD_MODE = 1;
        protected static final int CONTENT_MODE = 2;
        protected static final int LAST_SECOND_MODE = 3;
        protected static final int LAST_MODE = 4;
        protected static final int HEAD_SECOND_MODE = 5;


        @Override
        public int getItemViewType(int position) {
            if (position == 0) {
                return HEAD_MODE;

            }
            if (position == 1) {
                return HEAD_SECOND_MODE;
            }
            if (position == getItemCount() - 1) {
                return LAST_MODE;
            }
            if (position == getItemCount() - 2) {
                return LAST_SECOND_MODE;
            } else {
                return CONTENT_MODE;
            }
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == CONTENT_MODE) {
                return new BrowseGlassesHolder(LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.item_detail_browse_glasses_rv_content, parent, false));
            }
            if (viewType == HEAD_MODE) {
                return new FirstItemHolder(LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.item_detail_browse_glasses_rv_head_first, parent, false));
            }
            if (viewType == LAST_SECOND_MODE) {
                return new LastSecondItemHolder(LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.item_detail_browse_last_second_content, parent, false));
            }

            if (viewType == HEAD_SECOND_MODE) {
                return new HeadSecondItemHolder(LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.item_detail_browse_glasses_title_second, parent, false));
            } else {
                return new LastItemHolder(LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.item_detail_browse_last_content, parent, false));
            }

        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (getItemViewType(position) == CONTENT_MODE) {
                ((BrowseGlassesHolder) holder).contentPosition = position;
                ((BrowseGlassesHolder) holder).contentTv.setText(data.getGoods_data().get(position + 2).getIntroContent());
                ((BrowseGlassesHolder) holder).titleTv.setText(data.getGoods_data().get(position + 2).getLocation());
                ((BrowseGlassesHolder) holder).eTitleTv.setText(data.getGoods_data().get(position + 2).getEnglish());
                ((BrowseGlassesHolder) holder).locationTv.setText(data.getGoods_data().get(position + 2).getCountry());
                if (data.getDesign_des().size() < position - 2) {
                    return;
                }
                netHelper.setImage(((BrowseGlassesHolder) holder).backImg, data.getDesign_des().get(position - 2).getImg());

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

        class BrowseGlassesHolder extends RecyclerView.ViewHolder {
            private LinearLayout srcLayout;
            private RelativeLayout relativeLayout;
            private int contentPosition;
            private TextView titleTv, eTitleTv, locationTv, contentTv;
            private ImageView backImg;
            private int height;

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
                            relativeLayout.scrollTo((int) itemView.getX(), -((int) itemView.getY()) / 2);
                            Log.i("height", height + "");
                        }

                    }
                });

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
        class LastSecondItemHolder extends RecyclerView.ViewHolder {
            private ImageView imageView;

            public LastSecondItemHolder(View itemView) {
                super(itemView);
                imageView = (ImageView) itemView.findViewById(R.id.item_detail_browse_last_second_content_iv);
            }
        }

        class HeadSecondItemHolder extends RecyclerView.ViewHolder {
            private TextView titleTv;

            public HeadSecondItemHolder(View itemView) {
                super(itemView);

                titleTv = (TextView) itemView.findViewById(R.id.item_detail_browse_glasses_rv_title_second_tv);
            }
        }
    }


}
