package com.doubook.activity;

import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.doubook.BaseActivty;
import com.doubook.R;
import com.doubook.utiltools.URLImageGetter2;

public class MarketBookInfoActivity extends BaseActivty {
	private String imagUrl;
	private String linkUrl, titleString1;
	private TextView title1, title2;
	private static String book_infor,book_content;
	URLImageGetter2 reviewImgGetter;


	@Override
	protected void initView() {
		setContentView(R.layout.review_info);

	}

	@Override
	protected void initData() {
		book_infor = getIntent().getStringExtra("book_infor");
		book_content = getIntent().getStringExtra("book_content");
		title1 = (TextView) findViewById(R.id.title1);
		title2 = (TextView) findViewById(R.id.title2);

//		showLonding();
		setTitle("navbook详情");
		btn_back.setOnClickListener(this);

		reviewImgGetter = new URLImageGetter2(MarketBookInfoActivity.this, title1);
		title1.setText(Html.fromHtml(book_infor, reviewImgGetter, null));

		reviewImgGetter = new URLImageGetter2(MarketBookInfoActivity.this, title2);
		title2.setText(Html.fromHtml(book_content, reviewImgGetter, null));

	}

	@Override
	protected void processClick(View v) {

	}




}
