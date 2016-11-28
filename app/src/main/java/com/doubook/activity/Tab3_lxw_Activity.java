package com.doubook.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.cn.mey.swipebacklayout.lib.app.SwipeBackActivity;
import com.doubook.R;

public class Tab3_lxw_Activity extends SwipeBackActivity {
	private TextView Title;
	private ImageView btn_back;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab3_lxw);
		initView();
		initListener();
	}

	public void initView() {
		Title = (TextView) findViewById(R.id.Title);
		Title.setText("图书信息");
		btn_back = (ImageView) findViewById(R.id.btn_back);
	}

	private void initListener() {
		btn_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
}