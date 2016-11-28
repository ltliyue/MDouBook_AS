package com.doubook.activity;

import android.graphics.Bitmap;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.doubook.BaseActivty;
import com.doubook.R;

/**
 */
public class WebActivity extends BaseActivty {
    private WebView webView;

    @Override
    protected void initView() {
        setContentView(R.layout.webview);

    }

    @Override
    protected void initData() {
        setTitle("详情");
        showLonding();
        btn_back.setOnClickListener(this);
        webView = (WebView) findViewById(R.id.webviewid);
        webView.getSettings().setJavaScriptEnabled(true);
//        webView.getSettings().setBuiltInZoomControls(true);// 启用内置缩放装置
//        webView.getSettings().setSupportZoom(false);// 支持缩放
        webView.getSettings().setUseWideViewPort(true);// 设置此属性，可任意比例缩
        webView.loadUrl(getIntent().getStringExtra("linkUrl"));
        webView.setEnabled(false);
        webView.setClickable(false);
//        webView.loadUrl("http://www.baidu.com");

        // 覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                missLonding();
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // 返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });
    }

    @Override
    protected void processClick(View v) {

    }


}
