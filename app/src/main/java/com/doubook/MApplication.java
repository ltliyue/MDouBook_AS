package com.doubook;

import android.app.Application;

import com.doubook.utiltools.LogsUtils;

public class MApplication extends Application {
	@Override
	public void onCreate() {
		super.onCreate();
		LogsUtils.isDebug = true;
	}

}
