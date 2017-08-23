package com.doubook.getinfotools;

import com.doubook.bean.BaseCollection;
import com.doubook.bean.Collections;
import com.doubook.data.CacheData;
import com.doubook.interf.Tab2PageInfoCallback;
import com.doubook.utiltools.LogsUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.AbsCallback;

import java.util.List;

import okhttp3.Response;

/**
 * @author Administrator
 * @version 1.1.0
 * @Copyright Copyright (c) 2012 - 2100
 */
public class JsoupOkHttpGetInfo {

    static JsoupOkHttpGetInfo jsoupGetInfo;
    Tab2PageInfoCallback tab2PageInfoCallback;
    String url;

    public static JsoupOkHttpGetInfo getInstence() {
        if (jsoupGetInfo == null) {
            return new JsoupOkHttpGetInfo();
        }
        return jsoupGetInfo;
    }

    public void getinfo(String url, Tab2PageInfoCallback tab2PageInfoCallback) {
        this.url = url;
        this.tab2PageInfoCallback = tab2PageInfoCallback;
        getMyBookInfo();
    }

    private void getMyBookInfo() {
        OkGo.<BaseCollection>get(url).execute(new AbsCallback<BaseCollection>() {
            @Override
            public void onSuccess(com.lzy.okgo.model.Response<BaseCollection> response) {
                List<Collections> getFirtPageBookInfo = response.body().getCollections();
                tab2PageInfoCallback.page2Info(getFirtPageBookInfo, false);
                CacheData.setPageMyBookInfo(url, getFirtPageBookInfo);
            }

            @Override
            public BaseCollection convertResponse(Response response) throws Throwable {
                return null;
            }

            @Override
            public void onError(com.lzy.okgo.model.Response<BaseCollection> response) {
                super.onError(response);
                LogsUtils.e(response.message());
            }
        });
    }
}
