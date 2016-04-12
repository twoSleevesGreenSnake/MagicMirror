package com.qoo.magicmirror.order;

import android.content.Intent;
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
import com.qoo.magicmirror.constants.NetConstants;
import com.qoo.magicmirror.constants.Value;
import com.qoo.magicmirror.net.NetHelper;
import com.qoo.magicmirror.view.SYXDragViewGroup;

import java.util.ArrayList;

/**
 * Created by dllo on 16/4/7.
 */
public class DetailAddressActivity extends BaseActivity {
    private RecyclerView recyclerView;
    private TextView addAddress;
    private DetailAddressBean data;
    private int clickPosition;
    private DetailAddressAdapter adapter;
    private SYXDragViewGroup syxDragViewGroup;
    @Override
    protected int setLayout() {
        return R.layout.activity_address_detail;
    }

    @Override
    protected void initData() {
        addAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(DetailAddressActivity.this,AddAddressActivity.class),303);
            }
        });
       startNet();
        syxDragViewGroup = bindView(R.id.syxdragviewgroup);
    }

    @Override
    protected void initView() {
        recyclerView  = bindView(R.id.activity_address_detail_rv);
        addAddress = bindView(R.id.activity_address_detail_add_tv);

    }

    class DetailAddressAdapter extends RecyclerView.Adapter<DetailAddressAdapter.DetailAddressHolder>{
        @Override
        public DetailAddressHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new DetailAddressHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_address_detail,parent,false));
        }

        @Override
        public void onBindViewHolder(DetailAddressHolder holder, int position) {
            holder.address.setText(getString(R.string.adress)+data.getData().getList().get(position).getAddr_info());
            holder.name.setText(getString(R.string.name)+data.getData().getList().get(position).getUsername());
            holder.number.setText(getString(R.string.cellnumber)+data.getData().getList().get(position).getCellphone());
            holder.adrId = data.getData().getList().get(position).getAddr_id();
            holder.position = position;
        }

        @Override
        public int getItemCount() {
            return data.getData().getList().size();
        }

        class DetailAddressHolder extends RecyclerView.ViewHolder{
            TextView name,address,number;
            LinearLayout linearLayout;
            String adrId;
            ImageView editIv;
            int position;
            public DetailAddressHolder(View itemView) {
                super(itemView);
                name = (TextView) itemView.findViewById(R.id.item_address_detail_name);
                address = (TextView) itemView.findViewById(R.id.item_address_detail_address);
                number = (TextView) itemView.findViewById(R.id.item_address_detail_number);
                linearLayout = (LinearLayout) itemView.findViewById(R.id.item_address_detail_layout);
                editIv = (ImageView) itemView.findViewById(R.id.item_address_detail_edit_iv);
                editIv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clickPosition = position;
                        Intent intent = new Intent(DetailAddressActivity.this,EditAddressActivity.class);
                        intent.putExtra(Value.PUT_OLD_ADDRESS,data.getData().getList().get(position));
                        DetailAddressActivity.this.startActivityForResult(intent,301);
                    }
                });
                linearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ArrayList<String> keys = new ArrayList<String>();
                        ArrayList<String> values = new ArrayList<String>();
                        keys.add(getString(R.string.token));
                        values.add(token);
                        keys.add(getString(R.string.addr_id));
                        values.add(data.getData().getList().get(position).getAddr_id());
                        NetHelper.newNetHelper(DetailAddressActivity.this).getPostInfo(NetConstants.DEFAULT_ADDRESS, keys, values, null, new NetHelper.NetListener() {
                            @Override
                            public void onSuccess(Object o) {
                                Intent intent = new Intent();
                                intent.putExtra(Value.PUT_ADDRESS_DATA,data.getData().getList().get(position));
                                setResult(298, intent);
                                finish();
                            }

                            @Override
                            public void onFailure() {
                               netFailed();
                            }
                        });

                    }
                });
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        startNet();
    }
    private void startNet(){
        ArrayList<String> keys = new ArrayList<>();
        ArrayList<String> valves = new ArrayList<>();
        keys.add(getString(R.string.token));
        keys.add(getString(R.string.device_type));
        keys.add(getString(R.string.page));
        keys.add(getString(R.string.last_time));
        valves.add(token);
        valves.add("1");
        valves.add(Value.NOTHING);
        valves.add(Value.NOTHING);


        NetHelper.newNetHelper(this).getPostInfo(NetConstants.ADDRESS_LIST, keys, valves, DetailAddressBean.class, new NetHelper.NetListener<DetailAddressBean>() {
            @Override
            public void onSuccess(DetailAddressBean detailAddressBean) {
                data = detailAddressBean;
                adapter = new DetailAddressAdapter();
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(DetailAddressActivity.this));
            }

            @Override
            public void onFailure() {

            }
        });
    }
}
