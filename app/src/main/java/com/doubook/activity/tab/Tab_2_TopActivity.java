package com.doubook.activity.tab;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import com.doubook.BaseActivty;
import com.doubook.R;
import com.doubook.adapter.NewsFragmentPagerAdapter;
import com.doubook.bean.PageMenuBean;
import com.doubook.fragment.Tab1_TopFragment;
import com.doubook.utiltools.Tools;
import com.doubook.view.ColumnHorizontalScrollView;

import java.util.ArrayList;
import java.util.List;

public class Tab_2_TopActivity extends BaseActivty {
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

    }

    /**
     * 初始化layout控件
     */
    @Override
    protected  void initView() {
        setContentView(R.layout.activity_first_tab);
        mScreenWidth = Tools.getWindowsWidth(this);
        mItemWidth = mScreenWidth / 4;// 一个Item宽度为屏幕的1/4

        mColumnHorizontalScrollView = (ColumnHorizontalScrollView) findViewById(R.id.mColumnHorizontalScrollView);
        mRadioGroup_content = (LinearLayout) findViewById(R.id.mRadioGroup_content);
        rl_column = (RelativeLayout) findViewById(R.id.rl_column);
        shade_left = (ImageView) findViewById(R.id.shade_left);
        shade_right = (ImageView) findViewById(R.id.shade_right);
        mViewPager = (ViewPager) findViewById(R.id.mViewPager);


    }

    @Override
    protected void initData() {
        setTitle("豆瓣本周排行榜");
        btn_back.setOnClickListener(this);
        initColumnData();
    }

    @Override
    protected void processClick(View v) {

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
        pageMenuBeen = new ArrayList<>();
        PageMenuBean menu1 = new PageMenuBean();
        menu1.setTitle("图书榜(非虚构)");
        menu1.setTypeD(1);
        menu1.setTypeX(1);
        PageMenuBean menu2 = new PageMenuBean();
        menu2.setTitle("图书榜(虚构)");
        menu2.setTypeD(1);
        menu2.setTypeX(0);

        pageMenuBeen.add(menu1);
        pageMenuBeen.add(menu2);

        setChangelView();

    }

    /**
     * 初始化Column栏目项
     */
    private void initTabColumn() {
        mRadioGroup_content.removeAllViews();
        int count = pageMenuBeen.size();
        mColumnHorizontalScrollView.setParam(this, mScreenWidth, mRadioGroup_content, shade_left, shade_right, null,
                rl_column);
        for (int i = 0; i < count; i++) {
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
            data.putInt("typeD", pageMenuBeen.get(i).getTypeD());
            data.putInt("typeX", pageMenuBeen.get(i).getTypeX());

            Tab1_TopFragment newfragment = new Tab1_TopFragment();
            newfragment.setArguments(data);
            fragments.add(newfragment);
        }
        NewsFragmentPagerAdapter mAdapetr = new NewsFragmentPagerAdapter(getSupportFragmentManager(), fragments);
        mViewPager.setOffscreenPageLimit(2);
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
            mViewPager.setCurrentItem(position);
            selectTab(position);
        }
    };

    /**
     * 选择的Column里面的Tab
     */
    private void selectTab(int tab_postion) {
        columnSelectIndex = tab_postion;
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
}
