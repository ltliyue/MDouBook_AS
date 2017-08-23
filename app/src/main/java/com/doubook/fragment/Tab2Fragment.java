package com.doubook.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.doubook.R;
import com.doubook.adapter.UserBookListAdapter;
import com.doubook.bean.BaseCollection;
import com.doubook.bean.Collections;
import com.doubook.data.CacheData;
import com.doubook.data.ContextData;
import com.doubook.interf.Tab2PageInfoCallback;
import com.doubook.utiltools.LogsUtils;
import com.doubook.utiltools.PreferencesUtils;
import com.doubook.utiltools.ToastUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.AbsCallback;

import java.util.List;

import okhttp3.Response;

/**
 * 我的豆瓣fragment
 */
public class Tab2Fragment extends Fragment implements Tab2PageInfoCallback {

    private int scrollPos = 1;
    private int scrollTop = 0;

    private String userBookInfo, paramUrl;

    private RecyclerView pullToRefreshList = null;
    private SwipeRefreshLayout mSwipeRefreshWidget;

    private UserBookListAdapter dataAdapter = null;
    private List<Collections> contacters;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {

            switch (msg.what) {
                case 1:
                    dataAdapter = new UserBookListAdapter(getActivity(), contacters);
                    pullToRefreshList.setAdapter(dataAdapter);

                    //pullToRefreshList.getRefreshableView().smoothScrollToPosition(scrollPos, scrollTop);
//                    inintListener();
                    break;

                case 2:
                    dataAdapter = null;
                    dataAdapter = new UserBookListAdapter(getActivity(), contacters);
                    dataAdapter.notifyDataSetChanged();
                    pullToRefreshList.setAdapter(dataAdapter);
                    //pullToRefreshList.onRefreshComplete();
//                    inintListener();
                    break;
                case 3:
                    Toast.makeText(getActivity(), "没有更多啦~", Toast.LENGTH_SHORT).show();
                    break;

                default:
                    break;
            }
        }

        ;
    };

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.list_layout_new, null);

        pullToRefreshList = (RecyclerView) view.findViewById(R.id.list_of_contact);
        pullToRefreshList.setLayoutManager(new LinearLayoutManager(getActivity()));//这里用线性显示 类似于listview

        mSwipeRefreshWidget = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_widget);

        mSwipeRefreshWidget.setColorSchemeColors(R.color.title_bg, R.color.viewfinder_laser,
                R.color.possible_result_points);
        mSwipeRefreshWidget.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                LogsUtils.e("刷新数据。。。。");
//                CacheDataBmob_market.getInstence().getMarketBookInfo(get, true);
            }
        });

        // 这句话是为了，第一次进入页面的时候显示加载进度条
        mSwipeRefreshWidget.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                        .getDisplayMetrics()));

        Bundle args = getArguments();
        paramUrl = args != null ? args.getString("url") : "";
        return view;
    }

//    private void inintListener() {
//        pullToRefreshList.setOnRefreshListener(new OnRefreshListener<ListView>() {
//            @Override
//            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
//                refreshThreadGetInfo();
//            }
//        });
//        pullToRefreshList.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {
//
//            @Override
//            public void onLastItemVisible() {
//                Toast.makeText(getActivity(), "到底啦~(>_<)~", Toast.LENGTH_SHORT).show();
//            }
//        });
//        pullToRefreshList.setOnScrollListener(new OnScrollListener() {
//
//            @Override
//            public void onScrollStateChanged(AbsListView view, int scrollState) {
//                // TODO Auto-generated method stub
//                if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
//                    // scrollPos记录当前可见的List顶端的一行的位置
//                    scrollPos = pullToRefreshList.getFirstVisiblePosition();
//                }
//                View v = pullToRefreshList.getChildAt(0);
//                scrollTop = (v == null) ? 0 : v.getTop();
//            }
//
//            @Override
//            public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
//                // TODO Auto-generated method stub
//
//            }
//        });
//
//        pullToRefreshList.setOnItemClickListener(new OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent mIntent = new Intent(getActivity(), BookInfo_APIActivity.class);
//                mIntent.putExtra("linkUrl", contacters.get(position - 1).getBook().getAlt());
//                startActivity(mIntent);
//            }
//        });
//    }

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
        OkGo.<BaseCollection>get(ContextData.UserBookSave + PreferencesUtils.getString(getActivity(), "douban_user_id", "")
                + "/collections" + paramUrl).execute(new AbsCallback<BaseCollection>() {
            @Override
            public void onSuccess(com.lzy.okgo.model.Response<BaseCollection> response) {
                contacters = response.body().getCollections();
                LogsUtils.e("--contacters.size()->" + contacters.size());
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
