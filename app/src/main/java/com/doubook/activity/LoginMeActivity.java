package com.doubook.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.doubook.BaseActivty;
import com.doubook.R;
import com.doubook.activity.tab.MainActivity_Tab;
import com.doubook.bean.BaseToken;
import com.doubook.bean.User;
import com.doubook.bean.UserInfoBean;
import com.doubook.utiltools.PreferencesUtils;

import java.util.List;

import cn.bmob.v3.BmobInstallation;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

import static com.doubook.R.id.user;

/**
 * /登陆界面
 * 用户名
 * 密码
 */
public class LoginMeActivity extends BaseActivty {

    private EditText et_username;
    private EditText et_pwd;
    private TextView tv_register;
    private Button btn_login;
    private ImageView dou_login_btn;
    private String code = "";
    private String username;
    private String pwd;
    private Intent mIntent;


    @Override
    protected void initView() {
        setContentView(R.layout.activity_loginme);
        PreferencesUtils.PREFERENCE_NAME = this.getPackageName();
    }

    @Override
    protected void initData() {
        et_username = (EditText) findViewById(R.id.et_username);
        et_pwd = (EditText) findViewById(R.id.et_pwd);

        tv_register = (TextView) findViewById(R.id.tv_register);
        btn_login = (Button) findViewById(R.id.btn_login);
        dou_login_btn = (ImageView) findViewById(R.id.dou_login_btn);
        tv_register.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        dou_login_btn.setOnClickListener(this);
    }

    protected void doLogin() {

        username = et_username.getText().toString();
        pwd = et_pwd.getText().toString();
        if ("".equals(user)) {
            showToast("抱歉，用户名为空");
            return;
        }
        if ("".equals(pwd)) {
            showToast("抱歉，密码为空");
            return;
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(pwd);
        user.login(new SaveListener<UserInfoBean>() {
            @Override
            public void done(UserInfoBean userInfoBean, BmobException e) {
                if (e == null) {
                    showToast("登录成功:");
                    //通过BmobUser user = BmobUser.getCurrentUser()获取登录成功后的本地用户信息
                    //如果是自定义用户对象MyUser，可通过MyUser user = BmobUser.getCurrentUser(MyUser.class)获取自定义用户信息
                    Intent mIntent = new Intent(LoginMeActivity.this, MainActivity_Tab.class);
                    startActivity(mIntent);
                    finish();
                } else {
                    showToast(e.getMessage());
                }
            }
        });
//        Intent mIntent = new Intent(LoginMeActivity.this, LoginMe2Activity.class);
//        startActivity(mIntent);
//        AppManager.getAppManager().addActivity(this);
    }

    @Override
    protected void processClick(View v) {

        switch (v.getId()) {
            case R.id.btn_login:
                doLogin();
                break;

            //豆瓣账号登录
            case R.id.dou_login_btn:
                showProgressDialog();
                //当前逻辑：
                //查看当前手机是否注册过 并且已授权，
                //注册过：取出bmob账号，登录（问题：无法获得token）-->解决：另外一张表存token，通过douid获取
                //没注册过：授权登录
                BmobQuery<User> bmobQuery = new BmobQuery<>();
                bmobQuery.addWhereEqualTo("installationId", BmobInstallation.getInstallationId(LoginMeActivity.this));
                bmobQuery.addWhereEqualTo("isAuthorization", true);
                bmobQuery.findObjects(new FindListener<User>() {
                    @Override
                    public void done(List<User> list, BmobException e) {
                        if (e == null) {
                            final User user = list.get(0);
                            user.setPassword(user.getUsername());
                            list.get(0).login(new SaveListener<UserInfoBean>() {
                                @Override
                                public void done(UserInfoBean userInfoBean, BmobException e) {
                                    if (e == null) {
                                        closeProgressDialog();
                                        getUserToken(user.getDou_id());
                                        showToast("登录成功~");
                                        mIntent = new Intent(LoginMeActivity.this, MainActivity_Tab.class);
                                        startActivity(mIntent);
                                        finish();
                                    } else {
                                        showToast(e.getMessage());
                                    }
                                }
                            });
                        } else {
                            mIntent = new Intent(LoginMeActivity.this, LoginActivity.class);
                            startActivity(mIntent);
                            finish();
                        }
                    }
                });

                break;
            case R.id.tv_register:
                mIntent = new Intent(this, RegisterActivity.class);
                startActivity(mIntent);
                break;
        }
    }

    /**
     * 或得用户token
     */
    private void getUserToken(String douID) {
        BmobQuery<BaseToken> baseTokenBmobQuery = new BmobQuery<>();
        baseTokenBmobQuery.addWhereEqualTo("douban_user_id", douID);
        baseTokenBmobQuery.findObjects(new FindListener<BaseToken>() {
            @Override
            public void done(List<BaseToken> list, BmobException e) {
                if (e == null) {
                    BaseToken baseToken = list.get(0);

                }
            }
        });
    }


}
