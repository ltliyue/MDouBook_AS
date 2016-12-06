package com.doubook.data;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.doubook.bean.MarketBook;
import com.doubook.interf.Tab0PageInfoCallback;
import com.doubook.thread.ExecutorProcessPool;
import com.doubook.utiltools.CalendarUtils;
import com.doubook.utiltools.FileCache;
import com.doubook.utiltools.FileUtils;
import com.doubook.utiltools.LogsUtils;
import com.doubook.utiltools.TimeCompare;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class CacheDataBmob_market {
    private static String TAG = "CacheDataBmob_market";

    private static CacheDataBmob_market cacheData;

    public static CacheDataBmob_market getInstence() {
        if (cacheData == null) {
            cacheData = new CacheDataBmob_market();
        }
        return cacheData;
    }

    private static List<MarketBook> marketBooks;

    // 刷新
    public void getMarketBookInfo(Tab0PageInfoCallback tab1PageInfoCallback, boolean isRefresh) {
        ExecutorProcessPool.getInstance().execute(new MRunable(tab1PageInfoCallback, isRefresh));
    }

    // 首次
    public void getMarketBookInfo(Tab0PageInfoCallback tab0PageInfoCallback) {
        if (getMarketBooks() != null) {
            tab0PageInfoCallback.page0Info(getMarketBooks(), false);
        } else {
            ExecutorProcessPool.getInstance().execute(new MRunable(tab0PageInfoCallback, false));
        }
    }

    // 第一次开APP 配合下面的使用
    public void getMarketBookInfo() {
        if (marketBooks != null && marketBooks.size() > 0) {
            LogsUtils.e(TAG, "-->内存中获得Info");
            return;
        } else {
            LogsUtils.e(TAG, "-->去线程中获得Info-->");
            ExecutorProcessPool.getInstance().execute(new MRunable(null, false));
        }
    }

//    int times = 5;
//
//    // 第一次开APP
//    public void getPapeInfoFirstComeIn(Tab0PageInfoCallback tab0PageInfoCallback) {
//        if (getMarketBooks() != null) {
//            tab0PageInfoCallback.page0Info(getMarketBooks(), false);
//        } else {
//            times--;
//            LogsUtils.e(TAG, "-->getMarketBooks->"+times);
//            SystemClock.sleep(500);
//            getPapeInfoFirstComeIn(tab0PageInfoCallback);
//        }
//        if (times==0){
//            LogsUtils.e(TAG, "-->getMarketBooks->囧");
//            ExecutorProcessPool.getInstance().execute(new MRunable(tab0PageInfoCallback, false));
//        }
//    }

    class MRunable implements Runnable {
        private Tab0PageInfoCallback tab0PageInfoCallback;
        private boolean isRefresh;

        public MRunable(Tab0PageInfoCallback tab0PageInfoCallback, boolean isRefresh) {
            this.tab0PageInfoCallback = tab0PageInfoCallback;
            this.isRefresh = isRefresh;
        }

        @Override
        public void run() {
            String result;
            if (FileUtils.isFileExist(FileCache.getCaheDir() + "/getMarketBookInfo") && !isRefresh) {
                LogsUtils.e(TAG, "-->文件存在&isRefresh=false 在文件中获得Info-->");
                // 取io缓存
                result = FileUtils.readFile(
                        FileCache.getCaheDir() + "/getMarketBookInfo",
                        "utf-8").toString();

                List<MarketBook> list = JSON.parseArray(result, MarketBook.class);
//                if (list.size() < 2) {
//                    // 如果io缓存数据错误，删除io缓存
//                    FileUtils.deleteFile(FileCache.getCaheDir() + "/getMarketBookInfo");
//                    //去bmob查找
//                    getInfoFromBmob();
//
//                    return;
//                }
                LogsUtils.e(TAG, "-->文件存在&isRefresh=false 在文件中获得Info size-->" + list.size());
                if (tab0PageInfoCallback != null) {
                    tab0PageInfoCallback.page0Info(list, isRefresh);
                }
                setMarketBooks(list);
            } else {
                //去bmob查找
                getInfoFromBmob();
            }
        }

        /**
         * @param time
         * @deprecated not used
         */
        private void checkTime(String time) {
            if (CalendarUtils.getDaySplite(Long.parseLong(time)) > 2) {
                getInfoFromBmob();
            }
        }

        public void getInfoFromBmob() {
            LogsUtils.e(TAG, "-->文件不存在||isRefresh=true 去服务器获得Info-->");
            BmobQuery<MarketBook> bookInfoBeanBmobQuery = new BmobQuery<>();
            if (!TextUtils.isEmpty(CacheData.createdAt)) {
                LogsUtils.e(TAG, "-->文件不存在||isRefresh=true createdAt-->" + CacheData.createdAt);
                bookInfoBeanBmobQuery.addWhereGreaterThan("createdAt", new BmobDate(TimeCompare.StringToDate(CacheData.createdAt)));
            }

            bookInfoBeanBmobQuery.findObjects(new FindListener<MarketBook>() {
                @Override
                public void done(List<MarketBook> list, BmobException e) {

                    if (tab0PageInfoCallback != null) {
                        tab0PageInfoCallback.page0Info(list, isRefresh);
                    }
                    LogsUtils.e(TAG, "-->文件不存在||isRefresh=true 去服务器获得info size-->" + list.size());

                    if (list.size() > 2) { // 数据正确再存io
                        setMarketBooks(list);

                        boolean isDelete = FileUtils.deleteFile(FileCache.getCaheDir() + "/getMarketBookInfo");
                        LogsUtils.e(TAG, "-->文件是否删除成功" + isDelete);
                        boolean isSave = FileUtils.writeFile(
                                FileCache.getCaheDir() + "/getMarketBookInfo",
                                JSON.toJSONString(list));
                        LogsUtils.e(TAG, "-->文件是否保存成功" + isSave);

                    }
                }
            });
        }
    }

    public static List<MarketBook> getMarketBooks() {
        return marketBooks;
    }

    public static void setMarketBooks(List<MarketBook> marketBooks) {
        CacheDataBmob_market.marketBooks = marketBooks;
    }

}


