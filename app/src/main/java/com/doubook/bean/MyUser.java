package com.doubook.bean;

import cn.bmob.v3.BmobUser;

/**
 * Created by MaryLee on 5/2/17.
 */

public class MyUser extends BmobUser {
    private String imei;
    private String nick;
    private Boolean loginByDou;
    private String dou_photo;
    private String dou_username;
    private String dou_desc;

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public Boolean getLoginByDou() {
        return loginByDou;
    }

    public void setLoginByDou(Boolean loginByDou) {
        this.loginByDou = loginByDou;
    }

    public String getDou_photo() {
        return dou_photo;
    }

    public void setDou_photo(String dou_photo) {
        this.dou_photo = dou_photo;
    }

    public String getDou_username() {
        return dou_username;
    }

    public void setDou_username(String dou_username) {
        this.dou_username = dou_username;
    }

    public String getDou_desc() {
        return dou_desc;
    }

    public void setDou_desc(String dou_desc) {
        this.dou_desc = dou_desc;
    }
}
