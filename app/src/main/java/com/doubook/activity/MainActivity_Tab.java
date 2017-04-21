package com.doubook.activity;

import android.annotation.SuppressLint;
import android.app.TabActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.cn.mey.slidingmenu.lib.SlidingMenu;
import com.doubook.R;
import com.doubook.activity.map.MarkerClickActivity;
import com.doubook.bean.BaseCollection;
import com.doubook.bean.BookInfoBean_Api;
import com.doubook.bean.User;
import com.doubook.bean.UserInfoBean;
import com.doubook.data.CacheData;
import com.doubook.data.ContextData;
import com.doubook.thread.ExecutorProcessFixedPool;
import com.doubook.utiltools.LogsUtils;
import com.doubook.utiltools.PreferencesUtils;
import com.doubook.utiltools.ScreenUtils;
import com.doubook.utiltools.ToastUtils;
import com.doubook.view.CircularImage;
import com.doubook.widget.SearchPopupWindow;
import com.squareup.picasso.Picasso;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import okhttp3.Call;
import okhttp3.Response;

public class MainActivity_Tab extends TabActivity {
    private TextView username, desc, txt_wish, txt_reading, txt_read;
    private CircularImage user_photo;
    private int wish = 0, reading = 0, read = 0;

    private TextView Title;
    private ImageView btn_search, btn_back;
    private SlidingMenu menu;

    private UserInfoBean userInfoBean;
    private User user;
    /**
     * 选项卡按钮ID数组
     **/
    private int radioButtonId[] = {R.id.tab_item_0, R.id.tab_item_1, R.id.tab_item_2, R.id.tab_item_3};
    /**
     * 选项卡对象
     */
    private TabHost tabhost;
    /**
     * 选项卡按钮
     */
    private RadioButton radioButton[] = new RadioButton[radioButtonId.length];
    private RadioGroup radgp_menu;
    private OnClickListener onClickListener;

    private SearchPopupWindow mSearchPopupWindow;
    public static Bitmap bimap;

    private List<BookInfoBean_Api> contacters;

    private boolean isLogin = false;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 1:
                    getUserInfo();
                    break;
                case 2:
                    Picasso.with(MainActivity_Tab.this).load(userInfoBean.getLarge_avatar()).into(user_photo);
                    username.setText(userInfoBean.getName());
                    desc.setText(userInfoBean.getDesc());
//                    getBookNum();
                    break;
                case 3:
                    User user = (User) msg.obj;
                    if (TextUtils.isEmpty(user.getLarge_avatar())){
                        Picasso.with(MainActivity_Tab.this).load(R.drawable.icon).into(user_photo);
                    }else {
                        Picasso.with(MainActivity_Tab.this).load(userInfoBean.getLarge_avatar()).into(user_photo);
                    }
                    username.setText(user.getUsername());
                    desc.setText(user.getDesc());
                    break;
                case 11:
                    txt_wish.setText("想读的书（" + wish + "）");
                    PreferencesUtils.putString(MainActivity_Tab.this, "wish", wish + "");
                    break;
                case 12:
                    txt_reading.setText("在读的书（" + reading + "）");
                    PreferencesUtils.putString(MainActivity_Tab.this, "reading", reading + "");
                    break;
                case 13:
                    txt_read.setText("读过的书（" + read + "）");
                    PreferencesUtils.putString(MainActivity_Tab.this, "read", read + "");
                    break;
                default:
                    break;
            }
        }
    };

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
        // WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        AppManager.getAppManager().addActivity(MainActivity_Tab.this);
        setContentView(R.layout.activity_main_tab);
        //获得用户信息
        getUserInfo();
        // ---------------------实例化滑动菜单对象--------------------------
        menu = new SlidingMenu(this);
        // 设置可以左右滑动菜单
        menu.setMode(SlidingMenu.LEFT);
        // 设置触摸屏幕的模式
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        // 设置滑动阴影的宽度
        menu.setShadowWidthRes(R.dimen.shadow_width);
        // 设置滑动菜单视图的宽度
        menu.setBehindOffset(2 * ScreenUtils.getScreenWidth(MainActivity_Tab.this) / 5);
        // 设置滑动菜单阴影的图像资源
        menu.setShadowDrawable(null);
        // 设置下方视图的在滚动时的缩放比例
        menu.setBehindScrollScale(0.0f);
        // 设置渐入渐出效果的值
        menu.setFadeDegree(0.35f);
        menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        menu.setMenu(R.layout.layout_menu_left);
        // -------------------------滑动菜单---------------------------
        initView();
        initListener();
        // ----------------------------------------------------------
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.doubook.mytab");
        this.registerReceiver(broadcastReceiver, intentFilter);
    }

    private void initView() {
        Title = (TextView) findViewById(R.id.Title);
        radgp_menu = (RadioGroup) findViewById(R.id.radgp_menu);
        btn_back = (ImageView) findViewById(R.id.btn_back);
        btn_search = (ImageView) findViewById(R.id.btn_search);

        user_photo = (CircularImage) findViewById(R.id.user_photo);

        username = (TextView) findViewById(R.id.username);
        desc = (TextView) findViewById(R.id.desc);
        txt_wish = (TextView) findViewById(R.id.txt_wish);
        txt_reading = (TextView) findViewById(R.id.txt_reading);
        txt_read = (TextView) findViewById(R.id.txt_read);

        tabhostInit();
    }

    private void initListener() {

        btn_back.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                menu.showMenu();
            }
        });
        btn_search.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mSearchPopupWindow = new SearchPopupWindow(MainActivity_Tab.this, null);
                mSearchPopupWindow.showAsDropDown(MainActivity_Tab.this.findViewById(R.id.divider_view));
            }
        });
        txt_wish.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (!isLogin) {
                    ToastUtils.show(MainActivity_Tab.this, "尚未登陆，点击头像登陆后可查看");
                } else {
                    stepToMyDou(0);
                }
            }
        });
        txt_reading.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (!isLogin) {
                    ToastUtils.show(MainActivity_Tab.this, "尚未登陆，点击头像登陆后可查看");
                } else {
                    stepToMyDou(1);
                }

            }
        });
        txt_read.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (!isLogin) {
                    ToastUtils.show(MainActivity_Tab.this, "尚未登陆，点击头像登陆后可查看");
                } else {
                    stepToMyDou(2);
                }
            }
        });
        user_photo.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (!isLogin) {
//                    Intent mIntent = new Intent(MainActivity_Tab.this, LoginActivity.class);
                    Intent mIntent = new Intent(MainActivity_Tab.this, LoginMeActivity.class);
                    startActivityForResult(mIntent, 100);
                }
            }
        });
        radgp_menu.setOnCheckedChangeListener(new android.widget.RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int currentTab = tabhost.getCurrentTab();
                switch (checkedId) {
                    case R.id.tab_item_0:
                        Title.setText("推荐");
                        setCurrentTabWithAnim(currentTab, 0, "index");
                        break;
                    case R.id.tab_item_1:
                        Title.setText("图书榜");
                        setCurrentTabWithAnim(currentTab, 1, "top");
                        break;
                    case R.id.tab_item_2:
                        Title.setText("我的豆瓣");
                        setCurrentTabWithAnim(currentTab, 2, "dou");
                        break;
                    case R.id.tab_item_3:
                        Title.setText("更多");
                        setCurrentTabWithAnim(currentTab, 3, "more");
                        break;
                    default:
                        break;
                }
            }
        });
        onClickListener = new OnClickListener() {
            public void onClick(View v) {
                int id = v.getId();
                if (id == R.id.tab_item_0) {
                    tabhost.setCurrentTabByTag("index");
                } else if (id == R.id.tab_item_1) {
                    tabhost.setCurrentTabByTag("top");
                } else if (id == R.id.tab_item_2) {
                    tabhost.setCurrentTabByTag("dou");
                } else if (id == R.id.tab_item_3) {
                    tabhost.setCurrentTabByTag("more");
                }
            }
        };
    }

    /***
     * 选项卡的初始化
     */
    public void tabhostInit() {
        tabhost = this.getTabHost();
        tabhost.addTab(tabhost.newTabSpec("index").setIndicator("00")
                .setContent(new Intent(this, Tab_Main1Activity.class)));
        tabhost.addTab(tabhost.newTabSpec("top").setIndicator("11")
                .setContent(new Intent(this, Tab_Main2Activity.class)));
        tabhost.addTab(tabhost.newTabSpec("dou").setIndicator("22")
                .setContent(new Intent(this, MarkerClickActivity.class)));
        tabhost.addTab(tabhost.newTabSpec("more").setIndicator("33")
                .setContent(new Intent(this, Tab_Main3Activity.class)));
        for (int i = 0; i < radioButton.length; i++) {
            radioButton[i] = (RadioButton) this.findViewById(radioButtonId[i]);
            radioButton[i].setOnClickListener(onClickListener);
        }
        tabhost.setCurrentTabByTag("index");
        radioButton[0].setChecked(true);
        Title.setText("推荐");
        // ---------------------------------------------
    }

    private void setCurrentTabWithAnim(int now, int next, String tag) {
        // 这个方法是关键，用来判断动画滑动的方向
        tabhost.setCurrentTabByTag(tag);
    }

    /**
     * 用户信息
     */
    private void getUserInfo() {
        User user = User.getCurrentUser(User.class);
        if (user != null) {
            if (user.isAuthorization()) {
                BmobQuery<UserInfoBean> bmobQuery = new BmobQuery<>();
                bmobQuery.addWhereEqualTo("id", user.getDou_id());
                bmobQuery.findObjects(new FindListener<UserInfoBean>() {
                    @Override
                    public void done(List<UserInfoBean> list, BmobException e) {
                        if (e == null) {
                            userInfoBean = list.get(0);
                            mHandler.sendEmptyMessage(2);
                        } else {
                            Toast.makeText(MainActivity_Tab.this, "获取用户信息失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }else {
                Message message = mHandler.obtainMessage();
                message.what = 3;
                message.obj = user;
                mHandler.sendMessage(message);
            }
        }
    }

    /**
     * 读书数量
     */
    private void getBookNum() {
        String url = ContextData.UserBookSave + PreferencesUtils.getString(MainActivity_Tab.this, "douban_user_id", "")
                + "/collections";
        ExecutorProcessFixedPool.getInstance().execute(new ExcuteTask(url + "?status=wish", 11));
        ExecutorProcessFixedPool.getInstance().execute(new ExcuteTask(url + "?status=reading", 12));
        ExecutorProcessFixedPool.getInstance().execute(new ExcuteTask(url + "?status=read", 13));
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        System.out.println("----onNewIntent--->");
        getUserInfo();
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            int tab = intent.getIntExtra("tab", 0);
            stepToMyDou(tab);
        }
    };

    public void stepToMyDou(int pageNum) {
        tabhost.setCurrentTabByTag("dou");
        radioButton[1].setChecked(true);
        menu.showContent();

        Intent intent = new Intent();
        intent.setAction("com.doubook.mydoupage");
        // 要发送的内容
        intent.putExtra("page", pageNum);
        // 发送 一个无序广播
        sendBroadcast(intent);
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (broadcastReceiver != null) {
            unregisterReceiver(broadcastReceiver);
        }
    }

    class ExcuteTask implements Runnable {
        private int handleMessage;
        private String url;

        public ExcuteTask(String url, int handleMessage) {
            this.handleMessage = handleMessage;
            this.url = url;
        }

        @Override
        public void run() {
            OkHttpUtils.get().url(url).build().execute(new Callback<BaseCollection>() {

                @Override
                public void onError(Call arg0, Exception arg1, int arg2) {
                    LogsUtils.e("-main->onError:" + arg1);
                }

                @Override
                public void onResponse(BaseCollection arg0, int arg1) {
                }

                @Override
                public BaseCollection parseNetworkResponse(Response arg0, int arg1) throws Exception {
                    BaseCollection baseCollection = JSON.parseObject(arg0.body().string(), BaseCollection.class);

                    switch (handleMessage) {
                        case 11:
                            wish = (int) baseCollection.getTotal();
                            CacheData.setCollections_wish(baseCollection.getCollections());
                            break;
                        case 12:
                            reading = (int) baseCollection.getTotal();
                            CacheData.setCollections_reading(baseCollection.getCollections());
                            break;
                        case 13:
                            read = (int) baseCollection.getTotal();
                            CacheData.setCollections_read(baseCollection.getCollections());
                            break;
                        default:
                            break;
                    }
                    mHandler.sendEmptyMessage(handleMessage);
                    return baseCollection;
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }
}
