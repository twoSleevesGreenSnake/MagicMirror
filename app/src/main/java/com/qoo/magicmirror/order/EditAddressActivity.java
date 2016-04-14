package com.qoo.magicmirror.order;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.qoo.magicmirror.R;
import com.qoo.magicmirror.base.BaseActivity;
import com.qoo.magicmirror.constants.NetConstants;
import com.qoo.magicmirror.net.NetHelper;

import java.util.ArrayList;

/**
 * Created by dllo on 16/4/8.
 */
public class EditAddressActivity extends BaseActivity{
    private EditText name,address,number;
    private Button submitBtn;
    private String addrId;
    @Override
    protected int setLayout() {
        return R.layout.activity_edit_address;
    }

    @Override
    protected void initData() {
        DetailAddressBean.DataBean.ListBean listBean= getIntent().getParcelableExtra(getString(R.string.oldAddress));
        addrId = listBean.getAddr_id();
        name.setText(listBean.getUsername());
        address.setText(listBean.getAddr_info());
        number.setText(listBean.getCellphone());


       submitBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               final ArrayList<String> keys = new ArrayList<>();
               final ArrayList<String> values = new ArrayList<>();
               keys.add("token");
               keys.add("username");
               keys.add("cellphone");
               keys.add("addr_info");
               keys.add("addr_id");
               values.add(BaseActivity.token);
               values.add(name.getText().toString());
               values.add(number.getText().toString());
               values.add(address.getText().toString());
               values.add(addrId);
               for (String string:values){
                   if (string.equals("")){
                       Toast.makeText(EditAddressActivity.this, getString(R.string.format_is_not_right), Toast.LENGTH_SHORT).show();
                       return;
                   }
               }
               NetHelper.newNetHelper(EditAddressActivity.this).getPostInfo(NetConstants.EDIT_ADDRESS, keys, values, null, new NetHelper.NetListener() {
                   @Override
                   public void onSuccess(Object o) {
                       setResult(304);
                       finish();

                   }

                   @Override
                   public void onFailure() {
                       Toast.makeText(EditAddressActivity.this, R.string.change_fauiled, Toast.LENGTH_SHORT).show();
                   }
               });
           }
       });
    }

    @Override
    protected void initView() {
        name = bindView(R.id.activity_edit_address_name_et);
        address = bindView(R.id.activity_edit_address_address_et);
        number = bindView(R.id.activity_edit_address_number_et);
        submitBtn = bindView(R.id.activity_edit_address_edit_btn);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        setResult(302);
    }
}
