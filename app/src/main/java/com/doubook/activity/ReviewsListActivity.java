package com.doubook.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.doubook.BaseActivty;
import com.doubook.R;
import com.doubook.adapter.PLListNewAdapter;
import com.doubook.bean.BaseReviews;
import com.doubook.bean.Reviews;
import com.doubook.data.ContextData;
import com.doubook.utiltools.LogsUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class ReviewsListActivity extends BaseActivty {
	private ListView pullToRefreshList = null;
	private PLListNewAdapter plListAdapter = null;
	private List<Reviews> reviews;

	Handler mHandler = new Handler() {
		public void handleMessage(Message message) {
			if (message.what == 1) {
				missLonding();
				plListAdapter = new PLListNewAdapter(ReviewsListActivity.this, reviews);
				pullToRefreshList.setAdapter(plListAdapter);
				initListener();
			}
		}
	};

	@Override
	protected void initView() {
		setContentView(R.layout.review);
	}

	@Override
	protected void initData() {
		pullToRefreshList = (ListView) findViewById(R.id.list_review);
		setTitle("书籍评论");
		btn_back.setOnClickListener(this);
		showLonding();
		getUserinfo();

	}

	@Override
	protected void processClick(View v) {
		// TODO Auto-generated method stub

	}

	private void initListener() {
		// TODO Auto-generated method stub
		btn_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		pullToRefreshList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				Intent mIntent = new Intent(ReviewsListActivity.this, ReviewsInfo.class);
				mIntent.putExtra("reviewInfoUrl", reviews.get(position).getId());
				startActivity(mIntent);
			}
		});
	}

	public void getUserinfo() {
		// https://api.douban.com/v2/book/26782094/reviews
		// System.out.println(ContextData.UserBookReview + "26782094/reviews");
		// loadData(true, ContextData.UserBookReview + "26782094/reviews", null,
		loadData(true, ContextData.UserBookReview + getIntent().getStringExtra("bookid") + "/reviews", null,
				new Callback<BaseReviews>() {

					@Override
					public void onError(Call arg0, Exception arg1, int arg2) {
						LogsUtils.e(arg2 + "-->" + arg1);
					}

					@Override
					public void onResponse(BaseReviews arg0, int arg1) {
					}

					@Override
					public BaseReviews parseNetworkResponse(Response response, int arg1) throws Exception {
						BaseReviews fastJson = JSON.parseObject(response.body().string(), BaseReviews.class);
						reviews = fastJson.getReviews();
						mHandler.sendEmptyMessage(1);

						return fastJson;
					}
				});
	}
}