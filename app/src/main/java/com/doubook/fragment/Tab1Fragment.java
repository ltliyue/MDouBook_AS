package com.doubook.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.doubook.activity.BookInfo_APIActivity;
import com.doubook.R;
import com.doubook.adapter.ContentListAdapter;
import com.doubook.bean.BookInfoBean;
import com.doubook.data.CacheDataBmob_topnew;
import com.doubook.interf.Tab1PageInfoCallback;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.List;

public class Tab1Fragment extends Fragment implements Tab1PageInfoCallback {

	private int scrollPos = 0;
	private int scrollTop = 5;

	private int text;
	private int type;

	private PullToRefreshListView pullToRefreshList = null;
	private ContentListAdapter dataAdapter = null;
	private List<BookInfoBean> contacters;

	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {
		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(android.os.Message msg) {

			switch (msg.what) {
			case 1:
				contacters = (List<BookInfoBean>) msg.obj;
				dataAdapter = new ContentListAdapter(getActivity(), contacters);
				pullToRefreshList.setAdapter(dataAdapter);
				// pullToRefreshList.setSelection(scrollPos);
				break;

			case 2:
				contacters.clear();
				contacters.addAll((List<BookInfoBean>) msg.obj);
				Toast.makeText(getActivity(), "刷新成功~", Toast.LENGTH_SHORT).show();
				dataAdapter.notifyDataSetChanged();
				pullToRefreshList.onRefreshComplete();
				break;
			case 3:
				// Toast.makeText(getActivity(), "没有更多啦~",
				// ContextData.toastTime).show();
				// pullToRefreshList.onBottomComplete();
				// pullToRefreshList.setSelection(contacters.size() - 2);
				break;

			default:
				break;
			}
		};
	};

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = LayoutInflater.from(getActivity()).inflate(R.layout.list_layout, null);
		pullToRefreshList = (PullToRefreshListView) view.findViewById(R.id.list_of_contact);
		Bundle args = getArguments();
		text = args != null ? args.getInt("typeD") : 1;
		type = args != null ? args.getInt("typeX") : 1;
		return view;
	}

	private void inintListener() {
		pullToRefreshList.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				// refreshThreadGetInfo();
				CacheDataBmob_topnew.getInstence().getPapeInfo(Tab1Fragment.this, text, type,true);
//				CacheData.getInstence().getPapeInfo(Tab1Fragment.this, text, type, true);
			}
		});
		pullToRefreshList.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

			@Override
			public void onLastItemVisible() {
				// Toast.makeText(getActivity(), "到底啦~(>_<)~",
				// Toast.LENGTH_SHORT).show();
			}
		});
		pullToRefreshList.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
					// scrollPos记录当前可见的List顶端的一行的位置
					scrollPos = pullToRefreshList.getFirstVisiblePosition();
				}
			}

			@Override
			public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {

			}
		});
		pullToRefreshList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent mIntent = new Intent(getActivity(), BookInfo_APIActivity.class);
				mIntent.putExtra("linkUrl", contacters.get(position - 1).getLinkUrl());
				startActivity(mIntent);
			}
		});
	}

	@Override
	public void onStart() {
		super.onStart();
//		CacheData.getInstence().getPapeInfo(this, text, type);
		CacheDataBmob_topnew.getInstence().getPapeInfo(this, text, type);
		inintListener();
	}

	@Override
	public void page1Info(List<BookInfoBean> firtPageBookInfo, boolean isrefresh) {
		Message message = mHandler.obtainMessage();
		message.obj = firtPageBookInfo;
		if (!isrefresh) {
			message.what = 1;
			mHandler.sendMessage(message);
		} else {
			message.what = 2;
			mHandler.sendMessage(message);
		}
	}
}
