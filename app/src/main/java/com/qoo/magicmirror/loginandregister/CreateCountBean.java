package com.qoo.magicmirror.loginandregister;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by dllo on 16/4/6.
 */
public class CreateCountBean {

    /**
     * result : 1
     * msg :
     * data : {"token":"196583627e4fccc83f100c43065b8d2d","uid":"67"}
     */

    private String result;
    private String msg;
    /**
     * token : 196583627e4fccc83f100c43065b8d2d
     * uid : 67
     */

    private DataEntity data;

    public static CreateCountBean objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(str), CreateCountBean.class);
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

    public void setData(DataEntity data) {
        this.data = data;
    }

    public String getResult() {
        return result;
    }

    public String getMsg() {
        return msg;
    }

    public DataEntity getData() {
        return data;
    }

    public static class DataEntity {
        private String token;
        private String uid;

        public static DataEntity objectFromData(String str, String key) {

            try {
                JSONObject jsonObject = new JSONObject(str);

                return new Gson().fromJson(jsonObject.getString(str), DataEntity.class);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getToken() {
            return token;
        }

        public String getUid() {
            return uid;
        }
    }


}
