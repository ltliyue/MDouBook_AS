package com.doubook.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.doubook.R;
import com.doubook.utiltools.NetWorkUtils;
import com.doubook.utiltools.PreferencesUtils;
import com.doubook.utiltools.ToastUtils;

/**
 * /登陆界面
 */
public class FirstActivity extends Activity {
	private TextView btn_login, btn_goin;
	private Intent mIntent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.first);
		AppManager.getAppManager().addActivity(this);
		PreferencesUtils.PREFERENCE_NAME = this.getPackageName();
		initView();
		initListener();
	}

	private void initView() {
		btn_login = (TextView) findViewById(R.id.btn_login);
		btn_goin = (TextView) findViewById(R.id.btn_goin);
	}

	private void initListener() {
		btn_goin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if ("unknown".equals(NetWorkUtils.getNetWorkType(FirstActivity.this))
						|| "disconnect".equals(NetWorkUtils.getNetWorkType(FirstActivity.this))) {
					ToastUtils.show(FirstActivity.this, "当前网络不可用，请连接网络后重试");
				} else {
					mIntent = new Intent(FirstActivity.this, MainActivity_Tab.class);
					PreferencesUtils.putBoolean(FirstActivity.this, "isgetin", true);
					startActivity(mIntent);
					finish();
				}
			}
		});
		btn_login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if ("unknown".equals(NetWorkUtils.getNetWorkType(FirstActivity.this))
						|| "disconnect".equals(NetWorkUtils.getNetWorkType(FirstActivity.this))) {
					ToastUtils.show(FirstActivity.this, "当前网络不可用，请连接网络后重试");
				} else {
					mIntent = new Intent(FirstActivity.this, LoginActivity.class);
					startActivity(mIntent);
					finish();
				}
			}
		});
	}
}
