package com.qoo.magicmirror.detail;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qoo.magicmirror.R;
import com.qoo.magicmirror.base.BaseActivity;

import java.util.ArrayList;

/**
 * Created by dllo on 16/3/29.
 */
public class BrowseGlassesActivity extends BaseActivity {
    private RecyclerView recyclerView;
    private ArrayList<String> strings;

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
    }

    @Override
    protected void initData() {
        strings = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            strings.add("1");
        }

        recyclerView.setAdapter(new BrowseGlassesAdapter());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
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
            if (position == 1){
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
                return new BrowseGlassesHolder(LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.item_detail_browse_glasses_rv_head_first, parent, false));
            }
            if (viewType == LAST_SECOND_MODE) {
                return new BrowseGlassesHolder(LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.item_detail_browse_last_second_content, parent, false));
            }

            if (viewType == HEAD_SECOND_MODE){
                return new BrowseGlassesHolder(LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.item_detail_browse_glasses_title_second, parent, false));
            }

            else {
                return new LastItemHolder(LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.item_detail_browse_last_content, parent, false));
            }

        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (getItemViewType(position) == CONTENT_MODE) {
                ((BrowseGlassesHolder) holder).contentPosition = position;
            }
//
        }

        @Override
        public int getItemCount() {
            return strings.size() + 4;
        }

        class BrowseGlassesHolder extends RecyclerView.ViewHolder {
            private LinearLayout linearLayout, srcLayout;
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
                titleTv  = (TextView) itemView.findViewById(R.id.item_detail_brose_glasses_rv_content_title_tv);
                eTitleTv = (TextView) itemView.findViewById(R.id.item_detail_brose_glasses_rv_content_etitle_tv);
                locationTv = (TextView) itemView.findViewById(R.id.item_detail_brose_glasses_rv_content_location_tv);
                contentTv = (TextView) itemView.findViewById(R.id.item_detail_brose_glasses_rv_content_src_content_tv);
                srcLayout = (LinearLayout) itemView.findViewById(R.id.item_detail_brose_glasses_rv_content_src_content_layout);
                linearLayout = (LinearLayout) itemView.findViewById(R.id.item_detail_brose_glasses_rv_content_src_layout);

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
                            linearLayout.scrollTo((int) itemView.getX(), -(int) itemView.getY() / 2);
                        }

                    }
                });

            }
        }

        /**
         * 第一个行布局的缓存类
         */
        class FirstItemHolder extends RecyclerView.ViewHolder {
            private TextView eTitleTv,typeTitleTv,contentTv,priceTv;
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
