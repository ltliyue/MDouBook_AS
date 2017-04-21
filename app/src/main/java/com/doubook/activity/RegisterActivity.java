package com.doubook.activity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.doubook.BaseActivty;
import com.doubook.R;
import com.doubook.bean.User;
import com.doubook.bean.UserInfoBean;
import com.doubook.utiltools.NetWorkUtil;
import com.doubook.utiltools.PreferencesUtils;
import com.doubook.utiltools.Tools;

import cn.bmob.v3.BmobInstallation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * 注册界面
 *
 * @author Stone
 * @date 2014-4-24
 */
public class RegisterActivity extends BaseActivty {

    @SuppressWarnings("unused")
    private static final String TAG = "RegisterActivity";

    private Button btnReg;
    private EditText etUsername;
    private EditText etPassword;
    private EditText etComfirmPsd;
    private EditText etPhone;

    private String username = null;
    private String password = null;
    private String comfirmPsd = null;
    //	private String phone = null;
    private String imei;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_reg);
    }

    @Override
    protected void initData() {
        imei = Tools.getImeiCode(RegisterActivity.this);
        etUsername = (EditText) findViewById(R.id.et_username);
        etPassword = (EditText) findViewById(R.id.et_password);
        etComfirmPsd = (EditText) findViewById(R.id.et_comfirm_psd);
        etPhone = (EditText) findViewById(R.id.et_phone);

        btnReg = (Button) findViewById(R.id.btn_reg_now);
        btnReg.setOnClickListener(this);
    }

    @Override
    protected void processClick(View v) {
        switch (v.getId()) {
            case R.id.btn_reg_now:
                username = etUsername.getText().toString();
                password = etPassword.getText().toString();
                comfirmPsd = etComfirmPsd.getText().toString();
//			phone = etPhone.getText().toString();
                if (!NetWorkUtil.isNetWorkConnected(RegisterActivity.this)) {
                    showToast("木有网络 ( ⊙ o ⊙ ) ");
                } else if (username.equals("") || password.equals("") || comfirmPsd.equals("")) {
                    showToast("信息不填完整 ");
                } else if (!comfirmPsd.equals(password)) {
                    showToast("两次密码输入不一致");
//			} else if (!Tools.isMobileNO(phone)) {
//				toast("请输入正确的手机号码");
                } else {
                    // 开始提交注册信息
                    User bu = new User();
                    bu.setUsername(username);
                    bu.setPassword(password);
                    bu.setInstallationId(BmobInstallation.getInstallationId(RegisterActivity.this));
                    bu.signUp(new SaveListener<UserInfoBean>() {
                        @Override
                        public void done(UserInfoBean userInfoBean, BmobException e) {
                            if (e == null) {
                                showToast("注册成功");
                                PreferencesUtils.putString(RegisterActivity.this, "imei", imei);
                                finish();
                            } else {
                                showToast(e.getMessage());
                            }
                        }
                    });
                }
                break;
            default:
                break;
        }
    }

}
