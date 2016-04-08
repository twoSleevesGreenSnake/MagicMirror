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
public class AddAddressActivity extends BaseActivity{
    private EditText name,address,number;
    private Button submitBtn;
    @Override
    protected int setLayout() {
        return R.layout.activity_add_address;
    }

    @Override
    protected void initData() {


       submitBtn.setOnClickListener(new View.OnClickListener() {

           @Override
           public void onClick(View v) {
               final ArrayList<String> keys = new ArrayList<>();
               final ArrayList<String> values = new ArrayList<>();
               keys.add("token");
               keys.add("username");
               keys.add("cellphone");
               keys.add("addr_info");
               values.add("f7d565803fbdb8f9c0bc64122895eea3");
               values.add(name.getText().toString());
               values.add(number.getText().toString());
               values.add(address.getText().toString());

               Log.i("values",values.toString());
               for (String string:values){
                   if (string.equals("")){
                       Toast.makeText(AddAddressActivity.this, "格式不正确", Toast.LENGTH_SHORT).show();
                       return;
                   }
               }

               NetHelper.newNetHelper(AddAddressActivity.this).getPostInfo(NetConstants.ADD_ADDRESS, keys, values, null, new NetHelper.NetListener() {
                   @Override
                   public void onSuccess(Object o) {
                       setResult(305);
                       finish();
                   }

                   @Override
                   public void onFailure() {

                   }
               });
           }
       });
    }

    @Override
    protected void initView() {
      name = bindView(R.id.activity_add_address_name_et);
        address = bindView(R.id.activity_add_address_address_et);
       number = bindView(R.id.activity_add_address_number_et);
        submitBtn = bindView(R.id.activity_add_address_submit_btn);

    }
}
