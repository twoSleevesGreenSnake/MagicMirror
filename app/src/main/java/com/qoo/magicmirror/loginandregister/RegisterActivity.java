package com.qoo.magicmirror.loginandregister;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
 * Created by dllo on 16/4/6.
 * <p/>
 * 注册界面
 */
public class RegisterActivity extends BaseActivity implements View.OnClickListener {

    private Button sendCodeBtn, createCountBtn;
    private EditText phoneNumberEt, codeEt, passwordEt;
    private ImageView closeIv;
    private NetHelper netHelper;
    private String phoneNumber;
    private Handler handler;
    private int result = 102;
    private boolean flag;

    @Override
    protected int setLayout() {
        return R.layout.activity_register;
    }

    @Override
    protected void initView() {
        sendCodeBtn = bindView(R.id.activity_register_send_code_btn);
        createCountBtn = bindView(R.id.activity_register_create_account_btn);
        phoneNumberEt = bindView(R.id.activity_register_phone_number_et);
        codeEt = bindView(R.id.activity_register_code_et);
        passwordEt = bindView(R.id.activity_register_password_et);
        closeIv = bindView(R.id.activity_register_close_iv);

        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (msg.what == 1) {
                    Toast.makeText(RegisterActivity.this, R.string.was_registered, Toast.LENGTH_SHORT).show();
                }
                if (msg.what == 2) {
                    sendCodeBtn.setText(String.valueOf(msg.arg1) + getString(R.string.send_again));
                }
                if (msg.what == 3) {
                    sendCodeBtn.setText(R.string.send_code);
                }
                return false;
            }
        });
    }

    @Override
    protected void initData() {
        netHelper = new NetHelper(this);
        sendCodeBtn.setOnClickListener(this);
        createCountBtn.setOnClickListener(this);
        closeIv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        phoneNumber = phoneNumberEt.getText().toString();
        switch (v.getId()) {
            case R.id.activity_register_send_code_btn:
                sendCode();// 获取验证码
                break;
            case R.id.activity_register_create_account_btn:
                createCount();// 创建账号
                break;
            case R.id.activity_register_close_iv:
                finish();
                break;
        }
    }

    private void createCount() {
        String code = codeEt.getText().toString();
        String password = passwordEt.getText().toString();
        if (code.length() != 5) {
            Toast.makeText(RegisterActivity.this, R.string.please_write_your_code, Toast.LENGTH_SHORT).show();
        } else if (code.length() == 5) {
            ArrayList<String> token = new ArrayList<>();
            token.add("phone_number");
            token.add("number");
            token.add("password");
            token.add("");
            ArrayList<String> value = new ArrayList<>();
            value.add(phoneNumber);
            value.add(code);
            value.add(password);
            value.add("");
            netHelper.getPostInfoForRegister(NetConstants.USER_SIGN_UP, token, value, CreateCountBean.class, new NetHelper.NetListener<CreateCountBean>() {

                @Override
                public void onSuccess(CreateCountBean createCountBean) {
                    Intent intent = new Intent();
                    intent.putExtra(getString(R.string.uid), createCountBean.getData().getUid());
                    intent.putExtra(getString(R.string.intent_phonenumber), phoneNumber);
                    setResult(result, intent);
                    Toast.makeText(RegisterActivity.this, getString(R.string.register_success), Toast.LENGTH_SHORT).show();
                    finish();
                }

                @Override
                public void onFailure() {
                    handler.sendEmptyMessage(1);
                }
            });
        }
    }

    public void sendCode() {
        if (phoneNumber.length() != 11) {
            Toast.makeText(RegisterActivity.this, R.string.please_write_your_phone_number, Toast.LENGTH_SHORT).show();
        } else if (phoneNumber.length() == 11) {
            ArrayList<String> token = new ArrayList<>();
            token.add(getString(R.string.phone_number));
            token.add("");
            token.add("");
            token.add("");
            ArrayList<String> value = new ArrayList<>();
            value.add(phoneNumber);
            value.add("");
            value.add("");
            value.add("");
            netHelper.getPostInfo(NetConstants.SEND_CODE, token, value, CodeBean.class, new NetHelper.NetListener<CodeBean>() {

                @Override
                public void onSuccess(CodeBean codeBean) {
                    flag = true;
                    if (flag) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                for (int i = 60; i >= 0; i--) {
                                    Message message = new Message();
                                    message.arg1 = i;
                                    message.what = 2;
                                    handler.sendMessage(message);
                                    try {
                                        Thread.sleep(1000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    if (i == 0) {
                                        handler.sendEmptyMessage(3);
                                    }
                                }
                            }
                        }).start();
                    }
                }

                @Override
                public void onFailure() {

                }
            });
        }
    }


}
