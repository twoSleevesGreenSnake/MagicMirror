package com.qoo.magicmirror.wearatlas;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import com.android.volley.toolbox.ImageLoader;
import com.qoo.magicmirror.R;
import com.qoo.magicmirror.base.BaseActivity;
import com.qoo.magicmirror.constants.NetConstants;
import com.qoo.magicmirror.homepage.GoodsListBean;
import com.qoo.magicmirror.net.NetHelper;

import java.util.ArrayList;
import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;


/**
 * Created by Giraffe on 16/4/5.
 */
public class WearAtlasActivity extends BaseActivity {
    private Context context;
    private ListView listView;
    private WearAtlasListViewAdapter wearAtlasListViewAdapter;
    private static ArrayList<GoodsListBean.DataEntity.ListEntity.WearVideoEntity> data;
    private VideoView vv;
    private ImageView ivThumbnail, showBigImg;
    private JCVideoPlayer vp;
    private RelativeLayout showBigLayout;
    private int screenHeight;
    private int screenWidth;
    private AnimatorSet set = new AnimatorSet();
    private RelativeLayout layout;
    private float ratio;
    int startY;
    int endY;
    float a;

    public static void setData(GoodsListBean.DataEntity.ListEntity data) {
        WearAtlasActivity.data = (ArrayList<GoodsListBean.DataEntity.ListEntity.WearVideoEntity>) data.getWear_video();
    }

    @Override
    protected int setLayout() {
        return R.layout.activity_wearatlas;
    }

    @Override
    protected void initData() {
        MediaController mc = new MediaController(this);
        vp.setUp(data.get(0).getData(), null);

        wearAtlasListViewAdapter = new WearAtlasListViewAdapter(data, context);
        listView.setAdapter(wearAtlasListViewAdapter);
        context = this;
        showBigLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backAnimation(endY, startY, 1 / a);
                new Handler(new Handler.Callback() {
                    @Override
                    public boolean handleMessage(Message msg) {
                        showBigLayout.setVisibility(View.GONE);
                        layout.setVisibility(View.VISIBLE);
                        return false;
                    }
                }).sendEmptyMessageDelayed(1, 200);

            }
        });
//        ArrayList<String> token = new ArrayList<>();
//        token.add(getString(R.string.token));
//        token.add(getString(R.string.device_type));
//        token.add(getString(R.string.page));
//        token.add(getString(R.string.last_time));
//        token.add(getString(R.string.category_id));
//        token.add(getString(R.string.version));
//        ArrayList<String> value = new ArrayList<>();
//        value.add("");
//        value.add("1");
//        value.add("");
//        value.add("");
//        value.add("");
//        value.add("1.0.1");
//        NetHelper netHelper = new NetHelper(this);
//
//        netHelper.getPostInfo(NetConstants.GOODS_TYPE, token, value, GoodsListBean.class, new NetHelper.NetListener<GoodsListBean>() {
//
//            @Override
//            public void onSuccess(GoodsListBean goodsListBean) {
////
//                Log.d("11111", "onSuccess: " + goodsListBean.toString());
//
////
//            }
//
//            @Override
//            public void onFailure() {
//
//            }
//        });
    }

    @Override
    protected void initView() {
        listView = (ListView) findViewById(R.id.activity_wearatlas_listview);
        layout = bindView(R.id.activity_wearatlas_layout);
        View view = LayoutInflater.from(WearAtlasActivity.this).inflate(R.layout.head_layout_of_wear_atlas, null);
        listView.addHeaderView(view);
        vp = (JCVideoPlayer) view.findViewById(R.id.head_layout_of_wear_atlas_vp);
        ivThumbnail = (ImageView) view.findViewById(R.id.head_layout_of_wear_atlas_thumbnail);
        listView.setDivider(null);
        showBigImg = bindView(R.id.show_big_img);
        showBigLayout = bindView(R.id.show_big_layout);
        screenHeight = getWindowManager().getDefaultDisplay().getHeight();
        screenWidth = getWindowManager().getDefaultDisplay().getWidth();

    }


    private void initAnimation(int startY, int endY, float scale) {
        set.playTogether(
                ObjectAnimator.ofFloat(showBigImg, "translationY", startY, endY),
                ObjectAnimator.ofFloat(showBigImg, "scaleX", 1, scale),
                ObjectAnimator.ofFloat(showBigImg, "scaleY", 1, scale),
                ObjectAnimator.ofFloat(showBigLayout, "alpha", 0.5f, 1)


        );
        set.setDuration(200).start();

    }


    private void backAnimation(int startY, int endY, float scale) {
        set.playTogether(
                ObjectAnimator.ofFloat(showBigImg, "translationY", startY, endY),
                ObjectAnimator.ofFloat(showBigImg, "scaleX", 1, scale),
                ObjectAnimator.ofFloat(showBigImg, "scaleY", 1, scale),
                ObjectAnimator.ofFloat(showBigLayout, "alpha", 1, 0.5f)


        );
        set.setDuration(200).start();

    }


    public class WearAtlasListViewAdapter extends BaseAdapter {
        private List<GoodsListBean.DataEntity.ListEntity.WearVideoEntity> data;
        private Context context;

        public WearAtlasListViewAdapter(List<GoodsListBean.DataEntity.ListEntity.WearVideoEntity> data, Context context) {
            this.data = data;
            this.context = context;
        }

        @Override
        public int getCount() {
            return data != null && data.size() > 0 ? data.size() - 2 : 0;
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_wear_atlas_rv, null);
                viewHolder = new ViewHolder(convertView);

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            GoodsListBean.DataEntity.ListEntity.WearVideoEntity wearVideoEntity = data.get(position + 1);
            if (wearVideoEntity != null) {
                if (wearVideoEntity.getData() == null || wearVideoEntity.getData() == "") {
                    return convertView;
                }
                ratio = (float) screenWidth / (float) screenHeight;
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) viewHolder.imageView.getLayoutParams();
                params.topMargin = (100);
                viewHolder.imageView.setLayoutParams(params);
                viewHolder.bitmap = new NetHelper<GoodsListBean.DataEntity.ListEntity.WearVideoEntity>(parent.getContext()).setCutBitmap(viewHolder.imageView, wearVideoEntity.getData(), screenWidth - 78);

            }
            return convertView;
        }


        public class ViewHolder {
            ImageView imageView;
            Bitmap bitmap;

            public ViewHolder(final View convertView) {
                imageView = (ImageView) convertView.findViewById(R.id.item_wear_atlas_rv_iv);

                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        a = (float) (1.0 + 39.0 / (float) bitmap.getWidth());
                        WearAtlasActivity.this.l(String.valueOf(a));
                        showBigImg.setImageBitmap(bitmap);
                        showBigLayout.setVisibility(View.VISIBLE);
                        WearAtlasActivity.this.l(String.valueOf(layout.getBottom()));
                        WearAtlasActivity.this.l(String.valueOf(imageView.getHeight() * a));
                        endY = (int) ((layout.getBottom() - imageView.getHeight() * a) / 50);
                        startY = (int) (convertView.getY() - listView.getY());
                        initAnimation(startY, endY, a);
                        layout.setVisibility(View.GONE);
                    }
                });

            }


        }
    }
}
