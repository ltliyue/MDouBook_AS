package com.doubook.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.doubook.BaseActivty;
import com.doubook.R;
import com.doubook.bean.MyUser;
import com.doubook.utiltools.PreferencesUtils;
import com.doubook.utiltools.Tools;

import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * /登陆界面
 */
public class LoginMe2Activity extends BaseActivty {

    private EditText et_nickname;
    private EditText et_code;
    private Button getcode;
    private Button btn_login;
    String nickname;
    String code;
    String phoneNumber;
    int a = 10;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    getcode.setText(a + "秒后重新发送");
                    getcode.setEnabled(false);
                    a--;
                    if (a >= 0) {
                        mHandler.sendEmptyMessageDelayed(1, 1000);
                    } else {
                        getcode.setEnabled(true);
                        getcode.setText("重新发送验证码");
                    }
                    break;
            }
        }
    };

    @Override
    protected void initView() {
        setContentView(R.layout.activity_loginme2);
        PreferencesUtils.PREFERENCE_NAME = this.getPackageName();
        phoneNumber = getIntent().getStringExtra("phoneNumber");
    }

    @Override
    protected void initData() {
        et_nickname = (EditText) findViewById(R.id.et_nickname);
        et_code = (EditText) findViewById(R.id.et_code);
        getcode = (Button) findViewById(R.id.getcode);
        btn_login = (Button) findViewById(R.id.btn_login);

        getcode.setOnClickListener(this);
        btn_login.setOnClickListener(this);

        BmobSMS.requestSMSCode(phoneNumber, "doubook", new QueryListener<Integer>() {
            @Override
            public void done(Integer integer, cn.bmob.v3.exception.BmobException e) {
                if (e == null) {//验证码发送成功
                    Log.i("bmob", "短信id：" + integer);//用于查询本次短信发送详情
                    mHandler.sendEmptyMessage(1);
                }
            }
        });
    }

    protected void doLogin() {

        nickname = et_nickname.getText().toString();
        code = et_code.getText().toString();
        if ("".equals(nickname)) {
            showToast("用户名昵称为空");
            return;
        }
        if ("".equals(code)) {
            showToast("验证码为空");
            return;
        }
        BmobSMS.verifySmsCode(phoneNumber, code.trim(), new UpdateListener() {
            @Override
            public void done(cn.bmob.v3.exception.BmobException e) {
                if (e == null) {//短信验证码已验证成功
                    Log.i("bmob", "验证通过");
                    MyUser user = new MyUser();
                    user.setImei(Tools.getImeiCode(LoginMe2Activity.this));
                    user.setUsername(phoneNumber);
                    user.setLoginByDou(false);
                    user.setPassword("000");
                    user.setNick(nickname);
                    user.signUp(new SaveListener<MyUser>() {
                        @Override
                        public void done(MyUser s, BmobException e) {
                            if(e==null){
                                showToast("注册成功");
                                PreferencesUtils.putString(LoginMe2Activity.this, "phoneNumber", phoneNumber);
                                Intent mIntent = new Intent(LoginMe2Activity.this,MainActivity_Tab.class);
                                startActivity(mIntent);
                                AppManager.getAppManager().finishActivity(LoginMeActivity.class);
                                finish();
                            }else{
                                showToast("注册失败");
                            }
                        }
                    });

                } else {
                    showToast("验证失败：code =" + e.getErrorCode() + ",msg = " + e.getLocalizedMessage());
                }
            }
        });

    }

    @Override
    protected void processClick(View v) {

        switch (v.getId()) {
            case R.id.btn_login:
                doLogin();
                break;
            case R.id.getcode:
                BmobSMS.requestSMSCode(phoneNumber, "doubook", new QueryListener<Integer>() {
                    @Override
                    public void done(Integer integer, cn.bmob.v3.exception.BmobException e) {
                        if (e == null) {//验证码发送成功
                            Log.i("bmob", "短信id：" + integer);//用于查询本次短信发送详情
                            mHandler.sendEmptyMessage(1);
                        }
                    }
                });
                break;
        }
    }


}
