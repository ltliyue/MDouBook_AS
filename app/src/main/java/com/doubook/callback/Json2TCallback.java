package com.doubook.callback;

import com.alibaba.fastjson.JSON;
import com.doubook.utiltools.LogsUtils;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.model.Response;

import okhttp3.ResponseBody;

/**
 * Created by MaryLee on 15/7/17.
 */

public class Json2TCallback<T> extends AbsCallback<T> {

    private Class<T> clazz;

    public Json2TCallback() {

    }

    public Json2TCallback(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public void onSuccess(Response<T> response) {
    }

    @Override
    public T convertResponse(okhttp3.Response response) throws Throwable {
        ResponseBody responseBody = response.body();
        if (responseBody == null) return null;
        T a = JSON.parseObject(responseBody.string(), clazz);
        return a;
    }

    @Override
    public void onError(Response<T> response) {
        super.onError(response);
        LogsUtils.e(response.message());
    }

}
