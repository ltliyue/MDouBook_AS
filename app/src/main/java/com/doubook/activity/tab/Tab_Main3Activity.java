package com.doubook.activity.tab;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.doubook.BaseActivty;
import com.doubook.R;
import com.doubook.activity.AppManager;
import com.doubook.activity.FirstActivity;
import com.doubook.activity.LoginActivity;
import com.doubook.utiltools.FileCache;
import com.doubook.utiltools.PreferencesUtils;
import com.doubook.view.CircularImage;
import com.doubook.view.CustomDialog;
import com.squareup.picasso.Picasso;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 我的 页面
 */
public class Tab_Main3Activity extends BaseActivty {
    private CustomDialog.Builder newbuilder;
    private LinearLayout lin_lxw, lin_exit, lin_zd, lin_dg, lin_xd, lin_fk, lin_about;
    private CircularImage user_photo;
    private TextView username, txt_zd, txt_dg, txt_xd, txt_hc;
    private Intent mIntent;
    private  String cacheSize;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    if (!"".equals(PreferencesUtils.getString(Tab_Main3Activity.this, "imageSrc", ""))) {
                        Picasso.with(ct).load(PreferencesUtils.getString(Tab_Main3Activity.this, "imageSrc", "")).into(user_photo);
                    }
                    username.setText(PreferencesUtils.getString(Tab_Main3Activity.this, "userName", ""));
                    txt_zd.setText(PreferencesUtils.getString(Tab_Main3Activity.this, "reading", ""));
                    txt_dg.setText(PreferencesUtils.getString(Tab_Main3Activity.this, "read", ""));
                    txt_xd.setText(PreferencesUtils.getString(Tab_Main3Activity.this, "wish", ""));
                    initDataListener();
                    break;
                case 0:
                    txt_hc.setText(cacheSize);
                    break;
            }
        }
    };

    @Override
    protected void initView() {
        setContentView(R.layout.activity_first_tab3);

    }

    @Override
    protected void initData() {
        PreferencesUtils.PREFERENCE_NAME = this.getPackageName();
        lin_exit = (LinearLayout) findViewById(R.id.lin_exit);
        lin_lxw = (LinearLayout) findViewById(R.id.lin_lxw);
        user_photo = (CircularImage) findViewById(R.id.user_photo);
        username = (TextView) findViewById(R.id.username);
        txt_zd = (TextView) findViewById(R.id.txt_zd);
        txt_dg = (TextView) findViewById(R.id.txt_dg);
        txt_xd = (TextView) findViewById(R.id.txt_xd);

        lin_zd = (LinearLayout) findViewById(R.id.lin_zd);
        lin_dg = (LinearLayout) findViewById(R.id.lin_dg);
        lin_xd = (LinearLayout) findViewById(R.id.lin_xd);

        lin_lxw = (LinearLayout) findViewById(R.id.lin_lxw);
        lin_fk = (LinearLayout) findViewById(R.id.lin_fk);

        lin_about = (LinearLayout) findViewById(R.id.lin_about);

        txt_hc = (TextView) findViewById(R.id.txt_hc);

        doSomethingInThread(new Runnable() {
            @Override
            public void run() {
                FileCache fileCache = new FileCache(Tab_Main3Activity.this);
                cacheSize = fileCache.getCacheSize();
                mHandler.sendEmptyMessage(0);
            }
        });

        initUserData();
    }

    @Override
    protected void processClick(View v) {
        switch (v.getId()){
            case R.id.lin_lxw:
                mIntent = new Intent(Tab_Main3Activity.this, Tab3_lxw_Activity.class);
                startActivity(mIntent);
                break;
            case R.id.lin_fk:
                mIntent = new Intent(Tab_Main3Activity.this, Tab3_FKActivity.class);
                startActivity(mIntent);
                break;
            case R.id.lin_about:
                mIntent = new Intent(Tab_Main3Activity.this, Tab3_AboutActivity.class);
                startActivity(mIntent);
                break;
        }
    }

    private void initDataListener() {
        if ("立即登录".equals(username.getText())) {
            username.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent mIntent = new Intent(Tab_Main3Activity.this, LoginActivity.class);
                    startActivity(mIntent);
                }
            });
        }
        lin_exit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                newbuilder = new CustomDialog.Builder(Tab_Main3Activity.this);
                View textEntryView = LayoutInflater.from(Tab_Main3Activity.this).inflate(R.layout.dialog_normal_button_two, null);
                newbuilder.setTitle("是否要退出当前登录账号?");
                newbuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        PreferencesUtils.putString(Tab_Main3Activity.this, "access_token", "");
                        PreferencesUtils.putString(Tab_Main3Activity.this, "refresh_token", "");
                        Intent mIntent = new Intent(Tab_Main3Activity.this, FirstActivity.class);
                        finish();
                        startActivity(mIntent);
                    }
                });
                newbuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                    }
                });
                newbuilder.create(textEntryView).show();

            }
        });
        lin_zd.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                sendBroadInfo(1);
            }
        });
        lin_xd.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                sendBroadInfo(0);
            }
        });
        lin_dg.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                sendBroadInfo(2);
            }
        });
    }

    private void sendBroadInfo(int tab) {
        if (mIntent == null) {
            mIntent = new Intent();
        }
        mIntent.setAction("com.doubook.mytab");
        // 要发送的内容
        mIntent.putExtra("tab", tab);
        // 发送 一个无序广播
        sendBroadcast(mIntent);
    }

    private void initUserData() {
        if ("".equals(PreferencesUtils.getString(Tab_Main3Activity.this, "access_token", ""))) {
            username.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent mIntent = new Intent(Tab_Main3Activity.this, LoginActivity.class);
                    startActivity(mIntent);
                }
            });
            return;
        } else {
            if (PreferencesUtils.getString(Tab_Main3Activity.this, "reading", "").equals("")) {
                mHandler.sendEmptyMessageDelayed(1, 600);
                return;
            }
            mHandler.sendEmptyMessage(1);
        }
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        if (PreferencesUtils.getString(Tab_Main3Activity.this, "reading", "").equals("")) {
            mHandler.sendEmptyMessageDelayed(1, 300);
        }
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
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
//			ExecutorProcessPool.getInstance().shutdown();
        }
    }


}
