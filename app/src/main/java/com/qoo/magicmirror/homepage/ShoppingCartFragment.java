package com.qoo.magicmirror.homepage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qoo.magicmirror.R;

import java.util.ArrayList;

/**
 * Created by dllo on 16/3/29.
 */
public class ShoppingCartFragment extends Fragment implements View.OnClickListener {

    private ArrayList<String> shoppingTitle;
    private TextView titleTv;
    private RelativeLayout titleRl;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_shoppingcart, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        shoppingTitle = new ArrayList<>();
        titleTv = (TextView) view.findViewById(R.id.fragment_shopping_title_tv);
        titleRl = (RelativeLayout) view.findViewById(R.id.fragment_shopping_title_rl);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = getArguments();
        shoppingTitle = bundle.getStringArrayList("shoppingTitle");
        titleTv.setText(shoppingTitle.get(4));
        titleRl.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_shopping_title_rl:
//                GoodsFragment
                // TODO 点击PopupWindow
                break;
        }
    }
}
