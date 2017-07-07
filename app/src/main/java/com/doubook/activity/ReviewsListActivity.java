package com.doubook.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.doubook.BaseActivty;
import com.doubook.R;
import com.doubook.adapter.PLListNewAdapter;
import com.doubook.bean.BaseReviews;
import com.doubook.bean.Reviews;
import com.doubook.data.ContextData;
import com.doubook.utiltools.LogsUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.AbsCallback;

import java.util.List;

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
		OkGo.<BaseReviews>post(ContextData.GetAccessToken).execute(new AbsCallback<BaseReviews>() {
		    @Override
		    public void onSuccess(com.lzy.okgo.model.Response<BaseReviews> response) {
				reviews = response.body().getReviews();
				mHandler.sendEmptyMessage(1);
		    }

		    @Override
		    public BaseReviews convertResponse(Response response) throws Throwable {
		        return null;
		    }

		    @Override
		    public void onError(com.lzy.okgo.model.Response<BaseReviews> response) {
		        super.onError(response);
		        LogsUtils.e(response.message());
		    }
		});
	}
}
