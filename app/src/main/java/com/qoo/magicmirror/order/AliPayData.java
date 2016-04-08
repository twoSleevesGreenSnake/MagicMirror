package com.qoo.magicmirror.order;

/**
 * Created by dllo on 16/4/8.
 */
public class AliPayData {

    /**
     * msg :
     * data : {"str":"service=\"mobile.securitypay.pay\"&partner=\"2088021758262531\"&_input_charset=\"utf-8\"&notify_url=\"http%3A%2F%2Fapi.mirroreye.cn%2Findex.php%2Fali_notify\"&out_trade_no=\"14600880647AP\"&subject=\"KAREN WALKER\"&payment_type=\"1\"&seller_id=\"2088021758262531\"&total_fee=\"1538.00\"&body=\"KAREN WALKER\"&it_b_pay =\"30m\"&sign=\"kSYvB%2BQQDuDGhBoac90evc%2BpUi5LRxatdrtWAylnmKk6KitUAkMmlKLkZmWOLXsjA4jELnrICVPyg6o7PT1tCqHmDEOAnAqPu1gdINQuQ7n2DaVrWHT3WLf0S%2Fr3uuDp6QnbPuV3DdKDagc1Abf9Ej%2F%2B%2FPgdZYvqaZFFnR%2BfUto%3D\"&sign_type=\"RSA\""}
     */

    private String msg;
    /**
     * str : service="mobile.securitypay.pay"&partner="2088021758262531"&_input_charset="utf-8"&notify_url="http%3A%2F%2Fapi.mirroreye.cn%2Findex.php%2Fali_notify"&out_trade_no="14600880647AP"&subject="KAREN WALKER"&payment_type="1"&seller_id="2088021758262531"&total_fee="1538.00"&body="KAREN WALKER"&it_b_pay ="30m"&sign="kSYvB%2BQQDuDGhBoac90evc%2BpUi5LRxatdrtWAylnmKk6KitUAkMmlKLkZmWOLXsjA4jELnrICVPyg6o7PT1tCqHmDEOAnAqPu1gdINQuQ7n2DaVrWHT3WLf0S%2Fr3uuDp6QnbPuV3DdKDagc1Abf9Ej%2F%2B%2FPgdZYvqaZFFnR%2BfUto%3D"&sign_type="RSA"
     */

    private DataBean data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private String str;

        public String getStr() {
            return str;
        }

        public void setStr(String str) {
            this.str = str;
        }
    }
}
