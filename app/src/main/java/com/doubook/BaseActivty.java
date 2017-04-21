package com.doubook;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.cn.mey.swipebacklayout.lib.app.SwipeBackActivity;
import com.doubook.activity.AppManager;
import com.doubook.thread.ExecutorProcessFixedPool;
import com.doubook.thread.ExecutorProcessPool;
import com.doubook.utiltools.CommonUtil;
import com.doubook.utiltools.CustomToast;
import com.doubook.view.CustomProgressDialog;
import com.doubook.view.DialogUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.util.Map;

public abstract class BaseActivty extends SwipeBackActivity implements OnClickListener {

    protected Context ct;
    protected MApplication app;

    public MApplication getApp() {
        return app;
    }

    public void setApp(MApplication app) {
        this.app = app;
    }

    protected View loadingView;
    protected TextView title_title, setting;
    protected ImageView btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addActivity(this);
        app = (MApplication) getApplication();
        ct = this;
        ExecutorProcessPool.getInstance();
        ExecutorProcessFixedPool.getInstance();

        initView();
        loadingView = findViewById(R.id.loading_view);
        title_title = (TextView) findViewById(R.id.title_title);
        setting = (TextView) findViewById(R.id.setting);
        btn_back = (ImageView) findViewById(R.id.btn_back);
        initData();
    }

    public void setTitle(String title) {
        title_title.setText(title);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().finishActivity(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_back) {
            finish();
        }
        processClick(v);
    }

    protected void showToast(String msg) {
        CustomToast customToast = new CustomToast(ct, msg);
        customToast.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void showLonding() {
        if (loadingView != null)
            loadingView.setVisibility(View.VISIBLE);
    }

    public void missLonding() {
        if (loadingView != null)
            loadingView.setVisibility(View.GONE);
    }

    protected abstract void initView();

    protected abstract void initData();

    protected abstract void processClick(View v);

    protected void doSomethingInThread(Runnable runnable) {
        ExecutorProcessPool.getInstance().execute(runnable);
    }

    protected void loadData(boolean isGet, String url, Map<String, String> params, Callback callback) {
        if (0 == CommonUtil.isNetworkAvailable(ct)) {
            showToast("当前网络不可用，请检查网络设置！");
            // missLonding();
            return;
        }
        if (isGet) {
            OkHttpUtils.get().url(url).params(params).build().execute(callback);
        } else {
            OkHttpUtils.post().url(url).params(params).build().execute(callback);
        }
    }

    @Override
    protected void onActivityResult(int arg0, int arg1, Intent arg2) {
        super.onActivityResult(arg0, arg1, arg2);
    }

    @Override
    public void startActivity(Intent intent) {
        // TODO Auto-generated method stub
        super.startActivity(intent);
        // overridePendingTransition(R.anim.slide_in_right,
        // R.anim.slide_out_left);
    }

    // -----dialog---
    protected CustomProgressDialog dialog;

    protected void showProgressDialog(String content) {
        if (dialog == null && ct != null) {
            dialog = (CustomProgressDialog) DialogUtil.createProgressDialog(ct, content);
        }
        dialog.show();
    }

    protected void showProgressDialog() {
        if (dialog == null && ct != null) {
            dialog = (CustomProgressDialog) DialogUtil.createProgressDialog(ct);
        }
        dialog.show();
    }

    protected void closeProgressDialog() {
        if (dialog != null)
            dialog.dismiss();
    }

}
