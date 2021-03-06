package com.doubook.activity.tab;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.doubook.R;
import com.doubook.activity.AppManager;
import com.doubook.adapter.NewsFragmentPagerAdapter;
import com.doubook.bean.PageMenuBean;
import com.doubook.fragment.Tab2Fragment;
import com.doubook.utiltools.ToastUtils;
import com.doubook.utiltools.Tools;
import com.doubook.view.ColumnHorizontalScrollView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import cn.bmob.v3.BmobUser;

/**
 * tab2我的豆瓣
 * <br>读过的书，想读的书，在读的书</br>
 */
public class Tab_MyDouActivity extends FragmentActivity {
    private ViewPager mViewPager;
    private List<PageMenuBean> pageMenuBeen;
    /**
     * 自定义HorizontalScrollView
     */
    private ColumnHorizontalScrollView mColumnHorizontalScrollView;
    LinearLayout mRadioGroup_content;
    // LinearLayout ll_more_columns;
    RelativeLayout rl_column;
    // private ImageView button_more_columns;
    /** 用户选择的新闻分类列表 */
    /**
     * 当前选中的栏目
     */
    private int columnSelectIndex = 0;
    /**
     * 左阴影部分
     */
    public ImageView shade_left;
    /**
     * 右阴影部分
     */
    public ImageView shade_right;
    /**
     * 屏幕宽度
     */
    private int mScreenWidth = 0;
    /**
     * Item宽度
     */
    private int mItemWidth = 0;
    private ArrayList<Fragment> fragments = new ArrayList<Fragment>();
    /**
     * 请求CODE
     */
    public final static int CHANNELREQUEST = 1;
    /**
     * 调整返回的RESULTCODE
     */
    public final static int CHANNELRESULT = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // --------------------
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.doubook.mydoupage");
        this.registerReceiver(broadcastReceiver, intentFilter);

        if (BmobUser.getCurrentUser()==null){
            ToastUtils.show(this, "您未登录");
            return;
        }
//        if ("".equals(PreferencesUtils.getString(Tab_MyDouActivity.this, "access_token", ""))) {
//            ToastUtils.show(this, "您未登录");
//            return;
//        }
        setContentView(R.layout.activity_first_tab);
        mScreenWidth = Tools.getWindowsWidth(this);
        mItemWidth = mScreenWidth / 5;// 一个Item宽度为屏幕的1/4
        initView();

    }

    /**
     * 初始化layout控件
     */
    private void initView() {
        mColumnHorizontalScrollView = (ColumnHorizontalScrollView) findViewById(R.id.mColumnHorizontalScrollView);
        mRadioGroup_content = (LinearLayout) findViewById(R.id.mRadioGroup_content);
        rl_column = (RelativeLayout) findViewById(R.id.rl_column);
        shade_left = (ImageView) findViewById(R.id.shade_left);
        shade_right = (ImageView) findViewById(R.id.shade_right);
        mViewPager = (ViewPager) findViewById(R.id.mViewPager);

        initColumnData();
    }

    /**
     * 当栏目项发生变化时候调用
     */
    private void setChangelView() {
        initTabColumn();
        initFragment();
    }

    /**
     * 获取Column栏目 数据
     */
    private void initColumnData() {
        pageMenuBeen = new ArrayList<PageMenuBean>();
        PageMenuBean menu1 = new PageMenuBean();
        menu1.setTitle("想读的书");
        menu1.setUrl("wish");
        PageMenuBean menu2 = new PageMenuBean();
        menu2.setTitle("在读的书");
        menu2.setUrl("reading");
        PageMenuBean menu3 = new PageMenuBean();
        menu3.setTitle("读过的书");
        menu3.setUrl("read");

        pageMenuBeen.add(menu1);
        pageMenuBeen.add(menu2);
        pageMenuBeen.add(menu3);

        setChangelView();

    }

    /**
     * 初始化Column栏目项
     */
    private void initTabColumn() {
        mRadioGroup_content.removeAllViews();
        mColumnHorizontalScrollView.setParam(this, mScreenWidth, mRadioGroup_content, shade_left, shade_right, null,
                rl_column);
        for (int i = 0; i < pageMenuBeen.size(); i++) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(mItemWidth, LayoutParams.MATCH_PARENT);
            params.leftMargin = 5;
            params.rightMargin = 5;
            TextView columnTextView = new TextView(this);
            columnTextView.setTextAppearance(this, R.style.top_category_scroll_view_item_text);
            columnTextView.setBackgroundResource(R.drawable.radio_buttong_bg);
            columnTextView.setGravity(Gravity.CENTER);
            columnTextView.setPadding(5, 5, 5, 5);
            columnTextView.setId(i);
            columnTextView.setSingleLine();
            columnTextView.setText(pageMenuBeen.get(i).getTitle());
            columnTextView.setTextColor(getResources().getColorStateList(R.drawable.tab_textcolor));
            if (columnSelectIndex == i) {
                columnTextView.setSelected(true);
            }
            columnTextView.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    for (int i = 0; i < mRadioGroup_content.getChildCount(); i++) {
                        View localView = mRadioGroup_content.getChildAt(i);
                        if (localView != v)
                            localView.setSelected(false);
                        else {
                            localView.setSelected(true);
                            mViewPager.setCurrentItem(i);
                        }
                    }
                }
            });
            mRadioGroup_content.addView(columnTextView, i, params);
        }
    }

    /**
     * 初始化Fragment
     */
    private void initFragment() {
        fragments.clear();// 清空
        for (int i = 0; i < pageMenuBeen.size(); i++) {
            Bundle data = new Bundle();
            data.putString("url", pageMenuBeen.get(i).getUrl());

            Tab2Fragment newfragment = new Tab2Fragment();
            newfragment.setArguments(data);
            fragments.add(newfragment);
        }
        NewsFragmentPagerAdapter mAdapetr = new NewsFragmentPagerAdapter(getSupportFragmentManager(), fragments);
        mViewPager.setOffscreenPageLimit(0);
        mViewPager.setAdapter(mAdapetr);
        mViewPager.setOnPageChangeListener(pageListener);
    }

    /**
     * ViewPager切换监听方法
     */
    public OnPageChangeListener pageListener = new OnPageChangeListener() {

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageSelected(int position) {
            selectTab(position);
        }
    };

    /**
     * 选择的Column里面的Tab
     */
    private void selectTab(int tab_postion) {
        mViewPager.setCurrentItem(tab_postion);// 页面切换
        columnSelectIndex = tab_postion;// 选项卡切换
        for (int i = 0; i < mRadioGroup_content.getChildCount(); i++) {
            View checkView = mRadioGroup_content.getChildAt(tab_postion);
            int k = checkView.getMeasuredWidth();
            int l = checkView.getLeft();
            int i2 = l + k / 2 - mScreenWidth / 2;
            mColumnHorizontalScrollView.smoothScrollTo(i2, 0);
        }
        // 判断是否选中
        for (int j = 0; j < mRadioGroup_content.getChildCount(); j++) {
            View checkView = mRadioGroup_content.getChildAt(j);
            boolean ischeck;
            if (j == tab_postion) {
                ischeck = true;
            } else {
                ischeck = false;
            }
            checkView.setSelected(ischeck);
        }
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            int page = intent.getIntExtra("page", 0);
            selectTab(page);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (broadcastReceiver != null) {
            unregisterReceiver(broadcastReceiver);
        }
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
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
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
}
