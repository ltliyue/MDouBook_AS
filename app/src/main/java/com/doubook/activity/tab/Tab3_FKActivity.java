package com.doubook.activity.tab;

import android.annotation.SuppressLint;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.doubook.BaseActivty;
import com.doubook.R;
import com.doubook.bean.FKBean;

import java.util.Date;

import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * 反馈信息
 * 
 * @author Administrator
 * 
 */
@SuppressLint("NewApi")
public class Tab3_FKActivity extends BaseActivty {

	private ImageView btn_back;
	private TextView setting;
	private EditText edit_description, edit_relationType;

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_fb_main_layout);
	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub
		// img_menu = (ImageView) findViewById(R.id.img_menu);
		setting = (TextView) findViewById(R.id.setting);
		setting.setText("反馈");
		// text_title = (TextView) findViewById(R.id.text_title);
		// text_title.setText("问题反馈");
		btn_back = (ImageView) findViewById(R.id.btn_back);
		edit_description = (EditText) findViewById(R.id.edit_description);
		edit_relationType = (EditText) findViewById(R.id.edit_relationType);
	}

	@Override
	protected void processClick(View v) {
		// TODO Auto-generated method stub

		btn_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}
		});

		setting.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if ("".equals(edit_description.getText().toString().trim())) {
					edit_description.setError("反馈信息不能为空！");
					return;
				}
				FKBean fkBean = new FKBean();
				fkBean.setAppName("VRead");
				fkBean.setFkInfo(edit_description.getText().toString());
				fkBean.setRelationType(edit_relationType.getText().toString());
				fkBean.setUploadTime(new BmobDate(new Date()));
				fkBean.save(new SaveListener<String>() {

					@Override
					public void done(String objectId, BmobException e) {
						if (e == null) {
							showToast("反馈成功，感谢您提出的宝贵意见~");
						} else {
							showToast("反馈失败");
						}
					}

				});
			}
		});
	}

}
