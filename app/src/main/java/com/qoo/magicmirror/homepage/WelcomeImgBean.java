package com.qoo.magicmirror.homepage;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by dllo on 16/4/1.
 */
public class WelcomeImgBean {


    /**
     * text : &quot;雪屋&quot;07号
     * img : http://pic1.zhimg.com/e1cc747cbf2076a378d2fe0f8c3b2e20.jpg
     */

    private String text;
    private String img;

    public static WelcomeImgBean objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(str), WelcomeImgBean.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getText() {
        return text;
    }

    public String getImg() {
        return img;
    }
}
