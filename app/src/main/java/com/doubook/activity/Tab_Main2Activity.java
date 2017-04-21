package com.doubook.activity;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.doubook.BaseActivty;
import com.doubook.R;
import com.doubook.utiltools.ScreenUtils;

import java.util.Timer;
import java.util.TimerTask;

/**
 * tab four tabs
 * Created by MaryLee on 2016/10/7.
 */

public class Tab_Main2Activity extends BaseActivty {
    Intent mIntent;
    LinearLayout lin1;
    LinearLayout lin2;
    LinearLayout lin3;
    LinearLayout lin4;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_tabmain2);
        lin1 = (LinearLayout) findViewById(R.id.lin1);
        lin2 = (LinearLayout) findViewById(R.id.lin2);
        lin3 = (LinearLayout) findViewById(R.id.lin3);
        lin4 = (LinearLayout) findViewById(R.id.lin4);
    }

    @Override
    protected void initData() {
        LinearLayout.LayoutParams laParams = (LinearLayout.LayoutParams) lin1.getLayoutParams();
        double height = ScreenUtils.getScreenHeight(this) * 0.42;
        laParams.height = (int) height;
        lin1.setLayoutParams(laParams);

        LinearLayout.LayoutParams laParams2 = (LinearLayout.LayoutParams) lin2.getLayoutParams();
        double height2 = ScreenUtils.getScreenHeight(this) * 0.55;
        laParams2.height = (int) height2;
        lin2.setLayoutParams(laParams2);

        LinearLayout.LayoutParams laParams3 = (LinearLayout.LayoutParams) lin3.getLayoutParams();
        double height3 = ScreenUtils.getScreenHeight(this) * 0.45;
        laParams3.height = (int) height3;
        lin3.setLayoutParams(laParams3);

        LinearLayout.LayoutParams laParams4 = (LinearLayout.LayoutParams) lin4.getLayoutParams();
        double height4 = ScreenUtils.getScreenHeight(this) * 0.37;
        laParams4.height = (int) height4;
        lin4.setLayoutParams(laParams4);

        lin1.setOnClickListener(this);
        lin2.setOnClickListener(this);
        lin3.setOnClickListener(this);
        lin4.setOnClickListener(this);
    }

    @Override
    protected void processClick(View v) {
        switch (v.getId()) {
            case R.id.lin1:
                mIntent = new Intent(ct, Tab_2_NewActivity.class);
                startActivity(mIntent);
                break;
            case R.id.lin2:
                mIntent = new Intent(ct, Tab_2_TopActivity.class);
                startActivity(mIntent);
                break;
            case R.id.lin3:
                mIntent = new Intent(ct, Tab_2_SuperActivity.class);
                startActivity(mIntent);
                break;
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
