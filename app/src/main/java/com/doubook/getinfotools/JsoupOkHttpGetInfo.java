package com.doubook.getinfotools;

import com.alibaba.fastjson.JSON;
import com.doubook.bean.BaseCollection;
import com.doubook.bean.Collections;
import com.doubook.data.CacheData;
import com.doubook.interf.Tab2PageInfoCallback;
import com.doubook.utiltools.LogsUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 
 * @Copyright Copyright (c) 2012 - 2100
 * @author Administrator
 * @version 1.1.0
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
		OkHttpUtils.get().url(url).build().execute(new Callback<BaseCollection>() {

			@Override
			public void onError(Call arg0, Exception arg1, int arg2) {
				LogsUtils.e("-main->onError:" + arg1);
			}

			@Override
			public void onResponse(BaseCollection arg0, int arg1) {
			}

			@Override
			public BaseCollection parseNetworkResponse(Response arg0, int arg1) throws Exception {
				BaseCollection baseCollection = JSON.parseObject(arg0.body().string(), BaseCollection.class);
				List<Collections>  getFirtPageBookInfo = baseCollection.getCollections();
				tab2PageInfoCallback.page2Info(getFirtPageBookInfo, false);
				CacheData.setPageMyBookInfo(url, getFirtPageBookInfo);
//				switch (url) {
//				case "wish":
//					CacheData.setCollections_wish(getFirtPageBookInfo);
//				case "read":
//					CacheData.setCollections_read(getFirtPageBookInfo);
//				case "reading":
//					CacheData.setCollections_reading(getFirtPageBookInfo);
//				default:
//				}
				return baseCollection;
			}
		});
	}
}
