package com.doubook.activity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.doubook.BaseActivty;
import com.doubook.R;
import com.doubook.getinfotools.GetInfoByHttpUtils;
import com.doubook.utiltools.LogsUtils;
import com.doubook.utiltools.URLImageGetter2;
import com.squareup.picasso.Picasso;

public class ReviewsInfo extends BaseActivty {
	private ImageView imageview_head;
	private String imagUrl;
	private String linkUrl, titleString1, titleString3, titleString4;
	private TextView title1, title2, title3;
	TextView title4;
	private static String bookid;
	URLImageGetter2 reviewImgGetter;

	Handler handler = new Handler() {
		public void handleMessage(Message message) {
			if (message.what == 0) {

				title1.setText(titleString1);
				title3.setText(Html.fromHtml(titleString3));

				// 实例化URLImageGetter类
				reviewImgGetter = new URLImageGetter2(ReviewsInfo.this, title4);
				title4.setText(Html.fromHtml(titleString4, reviewImgGetter, null));

				Picasso.with(ct).load(imagUrl).into(imageview_head);
				missLonding();
			}
		}
	};

	@Override
	protected void initView() {
		setContentView(R.layout.review_info);

	}

	@Override
	protected void initData() {
		bookid = getIntent().getStringExtra("reviewInfoUrl");
		linkUrl = "http://book.douban.com/review/" + bookid + "/";

		imageview_head = (ImageView) findViewById(R.id.imageview_head);
		title1 = (TextView) findViewById(R.id.title1);
		title2 = (TextView) findViewById(R.id.title2);
		title3 = (TextView) findViewById(R.id.title3);
		title4 = (TextView) findViewById(R.id.title4);

		showLonding();
		setTitle("书籍评论详情");
		btn_back.setOnClickListener(this);

		getUserinfo();
	}

	@Override
	protected void processClick(View v) {

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	public void getUserinfo() {
		doSomethingInThread(new Runnable() {

			@Override
			public void run() {
				getInfoFromUrl();
			}
		});
	}

	private void getInfoFromUrl() {
		Document doc = Jsoup.parse(GetInfoByHttpUtils.getInstence().httpGet(linkUrl));
		LogsUtils.e("linkUrl:::" + linkUrl);

		titleString1 = doc.select("h1").text();

		Element title11 = doc.getElementById(bookid);
		imagUrl = title11.select("img").attr("src");

		titleString3 = title11.select("header").html();

		Elements bodys = title11.select("div.main-bd");

		bodys.select("script").remove();
		titleString4 = bodys.html();

		handler.sendEmptyMessage(0);
	}
}