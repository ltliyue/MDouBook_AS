package com.doubook.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.alibaba.fastjson.JSON;
import com.doubook.BaseActivty;
import com.doubook.R;
import com.doubook.bean.BaseToken;
import com.doubook.data.ContextData;
import com.doubook.utiltools.LogsUtils;
import com.doubook.utiltools.PreferencesUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

/**
 * /登陆界面
 */
public class LoginMeActivity extends BaseActivty {

    private EditText et_username;
    private EditText et_password;
    private Button btn_login;
    private String code = "";
    String user;
    String pwd;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_loginme);
        PreferencesUtils.PREFERENCE_NAME = this.getPackageName();
    }

    @Override
    protected void initData() {
        et_username = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);
    }

    protected void doLogin() {

        user = et_username.getText().toString();
        pwd = et_password.getText().toString();
        if ("".equals(user)) {
            showToast("用户名为空");
            return;
        }
        if ("".equals(pwd)) {
            showToast("密码为空");
            return;
        }
        Map<String, String> params = new HashMap<String, String>();
        params.put("a", user);
        params.put("b", pwd);
        loadData(true, "http://meyao.uicp.net/Shopping/DouBookServlet", params, new Callback() {
            @Override
            public Object parseNetworkResponse(Response response, int id) throws Exception {
                code = response.body().string();
                LogsUtils.e("code::"+code);
                doSave();
                return code;
            }

            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(Object response, int id) {

            }
        });
    }

    private void doSave() {
        LogsUtils.e("dosave");
        if (code != "") {
            doSomethingInThread(new Runnable() {
                public void run() {

                    Map<String, String> parasMap = new HashMap<String, String>();
                    parasMap.put("client_id", ContextData.APIKey);
                    parasMap.put("client_secret", ContextData.Secret);
                    parasMap.put("redirect_uri", ContextData.redirect_uri);
                    parasMap.put("grant_type", "authorization_code");
                    parasMap.put("code", code);

                    loadData(false, ContextData.GetAccessToken, parasMap, new Callback<BaseToken>() {

                        @Override
                        public void onError(Call arg0, Exception arg1, int arg2) {
                            LogsUtils.e("-onError->arg1:" + arg1);

                        }

                        @Override
                        public void onResponse(BaseToken arg0, int arg1) {
                            LogsUtils.e("-onResponse->arg1:" + arg1);
                        }

                        @Override
                        public BaseToken parseNetworkResponse(Response response, int arg1)
                                throws Exception {
                            BaseToken baseToken = JSON.parseObject(response.body().string(),
                                    BaseToken.class);

                            LogsUtils.e("-->resultURL222:" + baseToken.getAccess_token());
                            PreferencesUtils.putString(ct, "access_token",
                                    baseToken.getAccess_token());
                            PreferencesUtils.putString(ct, "refresh_token",
                                    baseToken.getRefresh_token());
                            PreferencesUtils.putString(ct, "douban_user_name",
                                    baseToken.getDouban_user_name());
                            PreferencesUtils.putString(ct, "douban_user_id",
                                    baseToken.getDouban_user_id());

                            return baseToken;
                        }
                    });
                }
            });
            Intent mIntent = new Intent();
            setResult(101, mIntent);
            finish();
        } else {
            showToast("系统错误");
        }
    }

    @Override
    protected void processClick(View v) {

        switch (v.getId()) {
            case R.id.btn_login:
                doLogin();
                break;
        }
    }


}
