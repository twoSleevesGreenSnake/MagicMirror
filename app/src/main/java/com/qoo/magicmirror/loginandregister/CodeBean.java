package com.qoo.magicmirror.loginandregister;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by dllo on 16/4/6.
 */
public class CodeBean {


    /**
     * result : 1
     * msg :
     * data :
     */

    private String result;
    private String msg;
    private String data;

    public static CodeBean objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(str), CodeBean.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getResult() {
        return result;
    }

    public String getMsg() {
        return msg;
    }

    public String getData() {
        return data;
    }
}
