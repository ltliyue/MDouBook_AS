package com.doubook.activity;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.doubook.BaseActivty;
import com.doubook.R;
import com.doubook.widget.MyProgressWebView;

/**
 */
public class WebLoginActivity extends BaseActivty {
    private MyProgressWebView myWebView;

    @Override
    protected void initView() {
        setContentView(R.layout.webview);
        setTitle("登录");
    }

    @Override
    protected void initData() {


        showLonding();
        btn_back.setOnClickListener(this);

        WebSettings webSettings = myWebView.getSettings();
        webSettings.setBuiltInZoomControls(true);// 启用内置缩放装置
        webSettings.setSupportZoom(false);// 支持缩放
        webSettings.setUseWideViewPort(true);// 设置此属性，可任意比例缩
        myWebView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                System.out.println("系统出错");
            }

            @Override
            public void onPageFinished(WebView view, String url) {

            }
        });
        myWebView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype,
                                        long contentLength) {
                if (url != null && url.startsWith("http://"))
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            }
        });
        if (getIntent().getStringExtra("linkUrl") != null) {
            System.out.println("~~~~" + getIntent().getStringExtra("linkUrl"));
            myWebView.loadUrl(getIntent().getStringExtra("linkUrl"));
        } else {
            showToast("当前页面不存在");
        }

    }

    @Override
    protected void processClick(View v) {

    }


}
