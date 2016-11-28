package com.doubook.widget;

import android.net.http.SslError;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by MaryLee on 16/9/27.
 */
public class MWebViewClient extends WebViewClient {
    public MWebViewClient() {
        super();
    }

    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        //handler.cancel(); // Android默认的处理方式
        handler.proceed();  // 接受所有网站的证书
        //handleMessage(Message msg); // 进行其他处理
    }

}
