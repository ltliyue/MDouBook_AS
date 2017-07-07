package com.doubook.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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

import com.doubook.R;
import com.doubook.activity.BookInfo_APIActivity;
import com.doubook.adapter.UserBookListAdapter;
import com.doubook.bean.BaseCollection;
import com.doubook.bean.Collections;
import com.doubook.data.CacheData;
import com.doubook.data.ContextData;
import com.doubook.interf.Tab2PageInfoCallback;
import com.doubook.utiltools.LogsUtils;
import com.doubook.utiltools.ToastUtils;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.AbsCallback;

import java.util.List;

import okhttp3.Response;

public class Tab2Fragment extends Fragment implements Tab2PageInfoCallback {

	private int scrollPos = 1;
	private int scrollTop = 0;

	private String userBookInfo, paramUrl;
	private PullToRefreshListView pullToRefreshList = null;
	private UserBookListAdapter dataAdapter = null;
	private List<Collections> contacters;
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {

			switch (msg.what) {
			case 1:
				// contacters =
				// JSON.parseArray(JSONUtils.getString(userBookInfo,
				// "collections", ""),
				// BookInfoBean_Api.class);
				dataAdapter = new UserBookListAdapter(getActivity(), contacters);
				pullToRefreshList.setAdapter(dataAdapter);
				pullToRefreshList.getRefreshableView().smoothScrollToPosition(scrollPos, scrollTop);
				// pullToRefreshList.setSecondPositionVisible();
				inintListener();
				break;

			case 2:
				// Toast.makeText(getActivity(), "刷新成功~", ContextData.toastTime)
				// .show();
				dataAdapter = null;
				// contacters =
				// JSON.parseArray(JSONUtils.getString(userBookInfo,
				// "collections", ""),
				// BookInfoBean_Api.class);
				dataAdapter = new UserBookListAdapter(getActivity(), contacters);
				dataAdapter.notifyDataSetChanged();
				pullToRefreshList.setAdapter(dataAdapter);
				pullToRefreshList.onRefreshComplete();
				inintListener();
				break;
			case 3:
				Toast.makeText(getActivity(), "没有更多啦~", Toast.LENGTH_SHORT).show();
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
		paramUrl = args != null ? args.getString("url") : "";
		return view;
	}

	private void inintListener() {
		pullToRefreshList.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				refreshThreadGetInfo();
			}
		});
		pullToRefreshList.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

			@Override
			public void onLastItemVisible() {
				Toast.makeText(getActivity(), "到底啦~(>_<)~", Toast.LENGTH_SHORT).show();
			}
		});
		pullToRefreshList.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub
				if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
					// scrollPos记录当前可见的List顶端的一行的位置
					scrollPos = pullToRefreshList.getFirstVisiblePosition();
				}
				View v = pullToRefreshList.getChildAt(0);
				scrollTop = (v == null) ? 0 : v.getTop();
			}

			@Override
			public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub

			}
		});
		// pullToRefreshList.setOnScrollListener(new OnScrollListener() {
		//
		// @Override
		// public void onScrollStateChanged(AbsListView , int scrollState) {
		// if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
		// // scrollPos记录当前可见的List顶端的一行的位置
		// scrollPos = pullToRefreshList.getFirstVisiblePosition();
		// }
		// View v = pullToRefreshList.getChildAt(0);
		// scrollTop = (v == null) ? 0 : v.getTop();
		// }
		//
		// @Override
		// public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3)
		// {
		// // TODO Auto-generated method stub2
		//
		// }
		// });
		//
		// pullToRefreshList.setOnDropDownListener(new OnDropDownListener() {
		//
		// @Override
		// public void onDropDown() {
		// refreshThreadGetInfo();
		// }
		// });
		pullToRefreshList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent mIntent = new Intent(getActivity(), BookInfo_APIActivity.class);
				mIntent.putExtra("linkUrl", contacters.get(position - 1).getBook().getAlt());
				startActivity(mIntent);
			}
		});
	}

	@Override
	public void onStart() {
		super.onStart();
		if (contacters != null && contacters.size() > 0) {
			mHandler.sendEmptyMessage(1);
			return;
		}
		CacheData.getInstence().getTab2PageInfo(this, paramUrl, false);
	}

	private void openThreadGetInfo() {
		OkGo.<BaseCollection>post(ContextData.GetAccessToken).execute(new AbsCallback<BaseCollection>() {
		    @Override
		    public void onSuccess(com.lzy.okgo.model.Response<BaseCollection> response) {
				contacters = response.body().getCollections();
				LogsUtils.e("--contacters.size()->"+contacters.size());
				if (contacters != null) {
					mHandler.sendEmptyMessage(1);
				} else {
					ToastUtils.show(getActivity(), "没有数据");
				}
		    }

		    @Override
		    public BaseCollection convertResponse(Response response) throws Throwable {
		        return null;
		    }

		    @Override
		    public void onError(com.lzy.okgo.model.Response<BaseCollection> response) {
		        super.onError(response);
		        LogsUtils.e(response.message());
		    }
		});
	}

	private void refreshThreadGetInfo() {
		// new Thread() {
		// @Override
		// public void run() {
		// contacters = null;
		// userBookInfo = HttpUtils.httpGetString(ContextData.UserBookSave
		// + PreferencesUtils.getString(getActivity(), "douban_user_id", "") +
		// "/collections" + paramUrl);
		//
		// if (userBookInfo != null) {
		// mHandler.sendEmptyMessageDelayed(2, 800);
		// }
		// }
		// }.start();
	}

	@Override
	public void page2Info(List<Collections> getFirtPageBookInfo, boolean isrefresh) {
		// TODO Auto-generated method stub
		if (getFirtPageBookInfo != null) {
			contacters = getFirtPageBookInfo;
			mHandler.sendEmptyMessage(1);
		} else {
			ToastUtils.show(getActivity(), "没有数据");
		}
		
	}

}
