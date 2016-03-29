package com.qoo.magicmirror.detail;

import android.animation.ObjectAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.qoo.magicmirror.R;
import com.qoo.magicmirror.base.BaseActivity;

import java.util.ArrayList;

/**
 * Created by dllo on 16/3/29.
 */
public class BrowseGlassesActivity extends BaseActivity {
    private RecyclerView recyclerView;
    private ArrayList<String> strings;

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
        Log.i("size", strings.size() + "");
        recyclerView.setAdapter(new BrowseGlassesAdapter());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private class BrowseGlassesAdapter extends RecyclerView.Adapter {
        protected static final int HEAD_MODE = 1;
        protected static final int CONTENT_MODE = 2;

        @Override
        public int getItemViewType(int position) {
            if (position == 0) {
                return HEAD_MODE;
            } else {
                return CONTENT_MODE;
            }
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new BrowseGlassesHolder(LayoutInflater.from(parent.getContext()).
                    inflate(R.layout.item_detail_brose_glasses_rv_content, parent, false));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ((BrowseGlassesHolder) holder).position = position;
//
        }

        @Override
        public int getItemCount() {
            return strings.size();
        }

        class BrowseGlassesHolder extends RecyclerView.ViewHolder {
            private int y = 0;
            private LinearLayout linearLayout;
            int position;
            LinearLayout srcLayout;

            public BrowseGlassesHolder(final View itemView) {
                super(itemView);
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
                       linearLayout.scrollTo((int) itemView.getX(), (int) itemView.getY() / 5);

                   }
               });

            }
        }

    }


}
