package com.doubook.activity.tab;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.widget.Toast;

import com.doubook.BaseActivty;
import com.doubook.R;
import com.doubook.activity.AppManager;
import com.doubook.adapter.ItemArticleAdapter;
import com.doubook.bean.MarketBook;
import com.doubook.data.CacheDataBmob_market;
import com.doubook.interf.Tab0PageInfoCallback;
import com.doubook.utiltools.LogsUtils;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 首页
 */
public class Tab_Main1Activity extends BaseActivty implements Tab0PageInfoCallback {


    List<MarketBook> marketBooks;
    private RecyclerView list_of_booknav;
    SwipeRefreshLayout mSwipeRefreshWidget;
    ItemArticleAdapter adapter;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_tab01);
        setSwipeBackEnable(false);

    }

    @Override
    protected void initData() {
        showLonding();
        list_of_booknav = (RecyclerView) findViewById(R.id.list_of);
        list_of_booknav.setLayoutManager(new LinearLayoutManager(this));//这里用线性显示 类似于listview
        mSwipeRefreshWidget = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_widget);

        mSwipeRefreshWidget.setColorSchemeColors(R.color.title_bg, R.color.viewfinder_laser,
                R.color.possible_result_points);
        mSwipeRefreshWidget.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                LogsUtils.e("刷新数据。。。。");
                CacheDataBmob_market.getInstence().getMarketBookInfo(Tab_Main1Activity.this, true);
            }
        });

        // 这句话是为了，第一次进入页面的时候显示加载进度条
        mSwipeRefreshWidget.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                        .getDisplayMetrics()));
        initDataInfo();
    }

    @Override
    protected void processClick(View v) {
    }

    public void initDataInfo() {
//        if (0 == PreferencesUtils.getInt(ct, "isComeIn", 0)) {
//            CacheDataBmob_market.getInstence().getPapeInfoFirstComeIn(this);
//            PreferencesUtils.putInt(ct, "isComeIn", 1);
//        } else {
//        mHandler.sendEmptyMessageDelayed(0, 800);
        if (CacheDataBmob_market.getMarketBooks()!=null&&CacheDataBmob_market.getMarketBooks().size() > 0) {
            CacheDataBmob_market.getInstence().getMarketBookInfo(Tab_Main1Activity.this);
        }else {
            mHandler.sendEmptyMessageDelayed(0, 500);
        }
//            CacheDataBmob_market.getInstence().getMarketBookInfo(this);
//        }
    }

    @Override
    public void onBackPressed() {
        exitBy2Click();
    }

    /**
     * 双击退出函数
     */
    private static Boolean isExit = false;

    private void exitBy2Click() {
        Timer tExit = null;
        if (isExit == false) {
            isExit = true; // 准备退出
            showToast("再按一次退出程序");
//            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false; // 取消退出
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务
        } else {
            AppManager.getAppManager().AppExit(this);
        }
    }


    @Override
    public void page0Info(List<MarketBook> getMarketBook, boolean isrefresh) {
        LogsUtils.e("getMarketBook-->" + getMarketBook.size());
        missLonding();
        mSwipeRefreshWidget.setRefreshing(false);

        Message message = mHandler.obtainMessage();
        message.obj = getMarketBook;
        if (!isrefresh) {
            message.what = 1;
            mHandler.sendMessage(message);
        } else {
            message.what = 2;
            mHandler.sendMessage(message);
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unchecked")
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case 0:
                    CacheDataBmob_market.getInstence().getMarketBookInfo(Tab_Main1Activity.this);
                    break;
                case 1:
                    marketBooks = (List<MarketBook>) msg.obj;
                    adapter = new ItemArticleAdapter(ct, marketBooks);
                    list_of_booknav.setAdapter(adapter);
                    // pullToRefreshList.setSelection(scrollPos);
                    break;

                case 2:
                    marketBooks.clear();
                    marketBooks.addAll((List<MarketBook>) msg.obj);
                    Toast.makeText(ct, "刷新成功~", Toast.LENGTH_SHORT).show();
                    adapter.notifyDataSetChanged();
                    mSwipeRefreshWidget.setRefreshing(false);
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
        }
    };
}
