package com.doubook.bean;

import cn.bmob.v3.BmobInstallation;

/**
 * Created by MaryLee on 20/4/17.
 */

public class MyBmobInstallation extends BmobInstallation {

    /**
     * 用户id-这样可以将设备与用户之间进行绑定
     */
    private String uid;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

}
