package com.doubook.bean;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobGeoPoint;

/**
 * Created by MaryLee on 30/3/17.
 */

public class User extends BmobUser {
    private String uid;
    private String desc;
    private String large_avatar;
    private String signature;
    private String name;

    private String dou_id;
    private String installationId;
    private BmobGeoPoint loc;
    private boolean isAuthorization;

    public String getDou_id() {
        return dou_id;
    }

    public void setDou_id(String dou_id) {
        this.dou_id = dou_id;
    }

    public String getDesc() {
        return desc;
    }

    public String getInstallationId() {
        return installationId;
    }

    public void setInstallationId(String installationId) {
        this.installationId = installationId;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public BmobGeoPoint getLoc() {
        return loc;
    }

    public void setLoc(BmobGeoPoint loc) {
        this.loc = loc;
    }

    public boolean isAuthorization() {
        return isAuthorization;
    }

    public void setAuthorization(boolean authorization) {
        isAuthorization = authorization;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getLarge_avatar() {
        return large_avatar;
    }

    public void setLarge_avatar(String large_avatar) {
        this.large_avatar = large_avatar;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
