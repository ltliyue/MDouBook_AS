package com.doubook.activity;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.doubook.BaseActivty;
import com.doubook.R;
import com.doubook.activity.tab.MainActivity_Tab;
import com.doubook.bean.BaseToken;
import com.doubook.bean.User;
import com.doubook.bean.UserInfoBean;
import com.doubook.data.ContextData;
import com.doubook.utiltools.LogsUtils;
import com.doubook.utiltools.PreferencesUtils;
import com.doubook.widget.MyProgressWebView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.callback.Callback;

import java.util.HashMap;
import java.util.Map;

import cn.bmob.v3.BmobInstallation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import okhttp3.Call;
import okhttp3.Response;

/**
 * /登陆界面Web
 */
public class LoginActivity extends BaseActivty {
    private MyProgressWebView myWebView;
    //	private Intent intent;
    private String resultURL;
    private String code = "";

    @Override
    protected void initView() {
        setContentView(R.layout.webloginview);
        PreferencesUtils.PREFERENCE_NAME = this.getPackageName();
    }

    @Override
    protected void initData() {
        myWebView = (MyProgressWebView) findViewById(R.id.webviewid);
        initWebView();
//        DoubookCrawlTest doubookCrawlTest = new DoubookCrawlTest();
//        String codes = doubookCrawlTest.doLoginByHtmlUnit("ltliyue@gmail.com", "liyue0016");
//        LogsUtils.e("--->code::" + codes);
    }

    @Override
    protected void processClick(View v) {

    }

    private void initWebView() {
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
                resultURL = myWebView.getUrl();
                if (resultURL != null) {
                    resultURL = myWebView.getUrl().split("&")[0];
                    LogsUtils.e("-->resultURL:" + resultURL);
                    if (resultURL.contains("code=")) {
                        code = resultURL.substring(resultURL.indexOf("code=") + 5, resultURL.length());
                        LogsUtils.e("-->code:" + code);
                        // 如果获得的code不为空，那么跳到httppost
                        if (code != "") {
                            showProgressDialog();

                            Map<String, String> parasMap = new HashMap<String, String>();
                            parasMap.put("client_id", ContextData.APIKey);
                            parasMap.put("client_secret", ContextData.Secret);
                            parasMap.put("redirect_uri", ContextData.redirect_uri);
                            parasMap.put("grant_type", "authorization_code");
                            parasMap.put("code", code);


                            OkGo.<BaseToken>post(ContextData.GetAccessToken).params(parasMap).execute(new AbsCallback<BaseToken>() {
                                @Override
                                public void onSuccess(com.lzy.okgo.model.Response<BaseToken> response) {
                                    getUserInfo(response.body());

                                    response.body().save(new SaveListener<String>() {
                                        @Override
                                        public void done(String s, BmobException e) {
                                            if (e == null) {
                                                LogsUtils.i("baseToken saved !");
                                            } else {
                                                LogsUtils.i("baseToken save failed !");
                                            }
                                        }
                                    });
                                }

                                @Override
                                public BaseToken convertResponse(Response response) throws Throwable {
                                    return null;
                                }

                                @Override
                                public void onError(com.lzy.okgo.model.Response<BaseToken> response) {
                                    super.onError(response);
                                }
                            });
                        } else {
                            Toast.makeText(LoginActivity.this, "系统错误", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
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
        if (ContextData.LoginURL != null) {
            myWebView.loadUrl(ContextData.LoginURL);
            LogsUtils.e("ContextData.LoginURL::" + ContextData.LoginURL);
        } else {
            Toast.makeText(LoginActivity.this, "当前文件不存在", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 用户信息
     */
    private void getUserInfo(BaseToken baseToken) {

//        final BaseToken baseToken_f = baseToken;

        String url = ContextData.GetUserInfo + baseToken.getDouban_user_id() + "?Authorization=" + baseToken.getAccess_token();
        LogsUtils.e("-->url:" + url);

        OkGo.<UserInfoBean>post(ContextData.GetAccessToken).execute(new AbsCallback<UserInfoBean>() {
            @Override
            public void onSuccess(com.lzy.okgo.model.Response<UserInfoBean> response) {
                saveNewAccount(response.body());
            }

            @Override
            public UserInfoBean convertResponse(Response response) throws Throwable {
                return null;
            }

            @Override
            public void onError(com.lzy.okgo.model.Response<UserInfoBean> response) {
                super.onError(response);
                LogsUtils.e(response.message());
            }
        });
    }

    private void login(User user) {
        //登录本地账户
        user.login(new SaveListener<UserInfoBean>() {
            @Override
            public void done(UserInfoBean userInfoBean, BmobException e) {
                if (e == null) {
                    closeProgressDialog();
                    showToast("登录成功~");
                    Intent mIntent = new Intent(LoginActivity.this, MainActivity_Tab.class);
                    startActivity(mIntent);
                    finish();
                } else {
                    showToast(e.getMessage());
                }
            }
        });
    }

    private void saveDouAccount(UserInfoBean userInfoBean_dou) {
        //保存豆瓣账户&&关联本地账户
//        userInfoBean_dou.setInstallationId(BmobInstallation.getInstallationId(LoginActivity.this));
        userInfoBean_dou.save(new SaveListener<String>() {
            @Override
            public void done(String o, BmobException e) {
                if (e == null) {
                    LogsUtils.i("getUserInfo-->成功保存豆瓣账户");
                } else {
                    LogsUtils.i("getUserInfo-->失败");
                }
            }
        });
    }

    /**
     * 保存本地账户
     *
     * @param userInfoBean_dou
     */
    private void saveNewAccount(final UserInfoBean userInfoBean_dou) {

        User user = new User();
        user.setUsername(userInfoBean_dou.getName());
        user.setPassword(userInfoBean_dou.getName());
        user.setDou_id(userInfoBean_dou.getId());
        user.setAuthorization(true);//豆瓣账号
        user.setInstallationId(BmobInstallation.getInstallationId(LoginActivity.this));
        user.signUp(new SaveListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (e == null) {
                    LogsUtils.i("注册本地账号成功");
                    //登录
                    login(user);

                    userInfoBean_dou.setUser(user);
                    //保存豆瓣账户&&关联本地账户
                    saveDouAccount(userInfoBean_dou);
                } else {
                    LogsUtils.e(e.getMessage());
                }
            }
        });
    }
}
