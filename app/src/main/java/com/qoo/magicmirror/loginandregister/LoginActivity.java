package com.qoo.magicmirror.loginandregister;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.qoo.magicmirror.R;
import com.qoo.magicmirror.base.BaseActivity;

/**
 * Created by dllo on 16/3/29.
 */
public class LoginActivity extends BaseActivity {
    private ImageView ivClose;
    private Button btnLogin, btnCreateAccount;
    private EditText etPhontNumber, etPassword;

    @Override
    protected int setLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initData() {
        listenEditText();
    }

    @Override
    protected void initView() {
        ivClose = bindView(R.id.activity_login_close_iv);
        btnLogin = bindView(R.id.activity_login_btn);
        btnCreateAccount = bindView(R.id.activity_login_create_account);
        etPhontNumber = bindView(R.id.activity_login_phone_number_et);
        etPassword = bindView(R.id.activity_login_password_et);


    }

    private void listenEditText() {
        etPhontNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (etPhontNumber.length() != 0 && etPassword.length() != 0) {
                    btnLogin.setBackgroundResource(R.drawable.selector_login_btn);

                } else {
                    btnLogin.setBackgroundResource(R.mipmap.enable_state_button);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (etPhontNumber.length() != 0 && etPassword.length() != 0) {
                    btnLogin.setBackgroundResource(R.drawable.selector_login_btn);
                } else if (etPhontNumber.length() ==0 || etPassword.length() == 0){
                    btnLogin.setBackgroundResource(R.mipmap.enable_state_button);
//                   if (btnLogin.isPressed() == true){
//                       btnLogin.setBackgroundResource(R.mipmap.login_button_pressed);
//                   }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
}

