package com.doubook.activity;

import android.content.Intent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.LinearLayout;

import com.doubook.BaseActivty;
import com.doubook.R;
import com.doubook.activity.tab.MainActivity_Tab;
import com.doubook.bean.BaseToken;
import com.doubook.bean.Logs;
import com.doubook.data.CacheData;
import com.doubook.data.CacheDataBmob_market;
import com.doubook.data.ContextData;
import com.doubook.utiltools.LogsUtils;
import com.doubook.utiltools.NetWorkUtils;
import com.doubook.utiltools.PreferencesUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.AbsCallback;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobInstallation;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import okhttp3.Response;

public class WelcomeActivity extends BaseActivty {

    private LinearLayout iv;
    private AlphaAnimation alphaAnimation;
    private Intent intent;
    private static String TAG = "WelcomeActivity";

    @Override
    protected void initView() {
        this.setContentView(R.layout.welcome);
        Bmob.initialize(this, ContextData.BmobAPPID);
        // 使用推送服务时的初始化操作
        BmobInstallation.getCurrentInstallation().save();

        PreferencesUtils.PREFERENCE_NAME = this.getPackageName();
    }

    @Override
    protected void initData() {
        if ("unknown".equals(NetWorkUtils.getNetWorkType(WelcomeActivity.this))
                || "disconnect".equals(NetWorkUtils.getNetWorkType(WelcomeActivity.this))) {
            showToast("当前网络不可用，请连接网络后重试");
            return;
        }
//        UserInfoBean userInfoBean = new UserInfoBean();
//        userInfoBean.setObjectId(Tools.getImeiCode(this));
//        userInfoBean.update(new UpdateListener() {
//            @Override
//            public void done(BmobException e) {
//                if (e==null){
//                    LogsUtils.i("bmobObjectId","更新成功");
//                }
//            }
//        });


//        CacheData.setCreatedAt(PreferencesUtils.getString(ct, "updateTime", ""));
//        ExecutorProcessFixedPool.getInstance().execute(new Runnable() {
//            @Override
//            public void run() {

                BmobQuery<Logs> bookInfoBeanBmobQuery = new BmobQuery<Logs>();
                bookInfoBeanBmobQuery.order("-date");
                bookInfoBeanBmobQuery.findObjects(new FindListener<Logs>() {
                    @Override
                    public void done(List<Logs> list, BmobException e) {
                        if (e == null) {
                            LogsUtils.e(TAG,"updateTime-->"+list.get(0).getDate());
                            PreferencesUtils.putString(ct, "updateTime", list.get(0).getDate());
                            CacheData.createdAt = list.get(0).getDate();
                            LogsUtils.e(TAG,"updateTime-PreferencesUtils->"+PreferencesUtils.getString(ct,"updateTime"));
                            CacheDataBmob_market.getInstence().getMarketBookInfo();
                        }
                    }
                });

//            }
//        });


//		openThreadToGetInfo();

//		if (!PreferencesUtils.getString(this, "refresh_token", "").equalsIgnoreCase("")) {
//			int needRefreshTime = Calendar.getInstance().get(Calendar.DAY_OF_YEAR)
//					- PreferencesUtils.getInt(this, "DAY_OF_YEAR", 0);
//			LogsUtils.e("-->更新needRefreshTime:" + needRefreshTime);
//			if (needRefreshTime > 7 || needRefreshTime < 0) {
//				getTokenData();
//			}
//		}
        iv = (LinearLayout) this.findViewById(R.id.weatherRLayout);
        alphaAnimation = new AlphaAnimation(0.1f, 1.0f);
        alphaAnimation.setDuration(500);
        iv.startAnimation(alphaAnimation);
        alphaAnimation.setAnimationListener(new AnimationListener() {
            @Override
            public void onAnimationEnd(Animation arg0) {
                intent = new Intent(WelcomeActivity.this, MainActivity_Tab.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationStart(Animation animation) {
            }
        });
    }

    /**
     * 提前下载
     */
//    private void openThreadToGetInfo() {
//        doSomethingInThread(new Runnable() {
//
//            @Override
//            public void run() {
//                try {
//                    CacheData.setPage1BookInfo(JsoupGetInfo.getInstence().getinfo(ContextData.best1, 1));
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//    }

    @Override
    protected void processClick(View v) {

    }

    /**
     * 更新Token
     */
    private void getTokenData() {
        LogsUtils.e("-->更新getTokenData");

        Map<String, String> parasMap = new HashMap<String, String>();
        parasMap.put("client_id", ContextData.APIKey);
        parasMap.put("client_secret", ContextData.Secret);
        parasMap.put("redirect_uri", ContextData.redirect_uri);
        parasMap.put("grant_type", "refresh_token");
        parasMap.put("refresh_token", PreferencesUtils.getString(ct, "refresh_token"));

        OkGo.<BaseToken>post(ContextData.GetAccessToken).params(parasMap).execute(new AbsCallback<BaseToken>() {
            @Override
            public void onSuccess(com.lzy.okgo.model.Response<BaseToken> response) {
                BaseToken baseToken = response.body();
                PreferencesUtils.putString(WelcomeActivity.this, "access_token", baseToken.getAccess_token());
                PreferencesUtils.putString(WelcomeActivity.this, "refresh_token", baseToken.getRefresh_token());

                PreferencesUtils.putInt(ct, "DAY_OF_YEAR", Calendar.getInstance().get(Calendar.DAY_OF_YEAR));
            }

            @Override
            public BaseToken convertResponse(Response response) throws Throwable {
                return null;
            }

            @Override
            public void onError(com.lzy.okgo.model.Response<BaseToken> response) {
                super.onError(response);
                LogsUtils.e(response.message());
            }
        });
    }
}
