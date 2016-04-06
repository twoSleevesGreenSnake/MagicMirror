package com.qoo.magicmirror.homepage;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by dllo on 16/4/6.
 */
public class CategoryListBean {


    /**
     * result : 1
     * msg :
     * data : [{"category_name":"手工眼镜","category_id":"271"},{"category_name":"平光镜","category_id":"269"},{"category_name":"太阳镜","category_id":"268"}]
     */

    private String result;
    private String msg;
    /**
     * category_name : 手工眼镜
     * category_id : 271
     */

    private List<DataEntity> data;

    public static CategoryListBean objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(str), CategoryListBean.class);
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

    public void setData(List<DataEntity> data) {
        this.data = data;
    }

    public String getResult() {
        return result;
    }

    public String getMsg() {
        return msg;
    }

    public List<DataEntity> getData() {
        return data;
    }

    public static class DataEntity {
        private String category_name;
        private String category_id;

        public static DataEntity objectFromData(String str, String key) {

            try {
                JSONObject jsonObject = new JSONObject(str);

                return new Gson().fromJson(jsonObject.getString(str), DataEntity.class);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        public void setCategory_name(String category_name) {
            this.category_name = category_name;
        }

        public void setCategory_id(String category_id) {
            this.category_id = category_id;
        }

        public String getCategory_name() {
            return category_name;
        }

        public String getCategory_id() {
            return category_id;
        }
    }
}
