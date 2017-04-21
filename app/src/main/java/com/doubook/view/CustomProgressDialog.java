package com.doubook.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import com.doubook.R;

/**
 * @类描述:自定义圆形进度条对话框 <br>
 * @修改时间 2016年8月30日<br>
 */
public class CustomProgressDialog extends ProgressDialog {

	private String content="";
	private TextView progress_dialog_content;

	public CustomProgressDialog(Context context, String content) {
		super(context);
		this.content = content;
		setCanceledOnTouchOutside(false);
	}

	public CustomProgressDialog(Context context) {
		super(context);
		setCanceledOnTouchOutside(false);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		initView();
		initData();
	}

	private void initData() {
		progress_dialog_content.setText(content);
	}

	public void setContent(String str) {
		progress_dialog_content.setText(str);
	}

	private void initView() {
		setContentView(R.layout.custom_progress_dialog_me);
		progress_dialog_content = (TextView) findViewById(R.id.progress_dialog_content);
	}

}