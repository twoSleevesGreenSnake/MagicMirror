package com.qoo.magicmirror.loginandregister;

import android.content.Intent;
import android.net.Uri;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.qoo.magicmirror.R;
import com.qoo.magicmirror.base.BaseActivity;
import com.qoo.magicmirror.constants.NetConstants;
import com.qoo.magicmirror.net.NetHelper;

import java.util.ArrayList;

/**
 * Created by dllo on 16/3/29.
 * 登陆的Activity
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private ImageView ivClose;
    private Button btnLogin, btnCreateAccount;
    private EditText etPhontNumber, etPassword;
    private int request = 101;
    private ImageView closeIv;
    private String phoneNumber;
    private String uid;
    private NetHelper netHelper;
    private String password;
    private String phone;


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
        closeIv = bindView(R.id.activity_login_close_iv);
        btnLogin = bindView(R.id.activity_login_btn);
        closeIv.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        btnCreateAccount.setOnClickListener(this);
        netHelper = NetHelper.newNetHelper(this);
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
                } else if (etPhontNumber.length() == 0 || etPassword.length() == 0) {
                    btnLogin.setBackgroundResource(R.mipmap.enable_state_button);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_login_create_account:
                Intent intent = new Intent(this, RegisterActivity.class);
                startActivityForResult(intent, request);// 创建账号按钮
                break;
            case R.id.activity_login_close_iv:
                finish();
                break;
            case R.id.activity_login_btn:
                login();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            phoneNumber = data.getStringExtra(getString(R.string.phonenumber));
            uid = data.getStringExtra(getString(R.string.uid));
            etPhontNumber.setText(phoneNumber);
        }
    }

    private void login() {
        phone = etPhontNumber.getText().toString();
        password = etPassword.getText().toString();
        if (phone.length() != 0) {
            if (password.length() != 0) {
                final ArrayList<String> token = new ArrayList<>();
                token.add(getString(R.string.phone_number));
                token.add(getString(R.string.password));
                token.add("");
                ArrayList<String> value = new ArrayList<>();
                value.add(phone);
                value.add(password);
                value.add("");
                netHelper.getPostInfoForLogin(NetConstants.USER_LOGIN_IN, token, value, LoginSuccessBean.class, new NetHelper.NetListener<LoginSuccessBean>() {

                    @Override
                    public void onSuccess(LoginSuccessBean loginSuccessBean) {
                        BaseActivity.setToken(loginSuccessBean.getData().getToken());
                        finish();
                    }
                    @Override
                    public void onFailure() {
                    }
                });
            } else {
                Toast.makeText(LoginActivity.this, R.string.please_write_yourpassword, Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(LoginActivity.this, R.string.please_write_your_username_and_password, Toast.LENGTH_SHORT).show();
        }
    }
}

