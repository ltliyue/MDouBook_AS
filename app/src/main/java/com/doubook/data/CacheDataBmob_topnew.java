package com.doubook.data;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.doubook.bean.BookInfoBean;
import com.doubook.interf.Tab1PageInfoCallback;
import com.doubook.thread.ExecutorProcessPool;
import com.doubook.utiltools.FileCache;
import com.doubook.utiltools.FileUtils;
import com.doubook.utiltools.LogsUtils;
import com.doubook.utiltools.TimeCompare;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class CacheDataBmob_topnew {
    private static String TAG = "CacheDataBmob_topnew";
    private static CacheDataBmob_topnew cacheData;

    public static CacheDataBmob_topnew getInstence() {
        if (cacheData == null) {
            cacheData = new CacheDataBmob_topnew();
        }
        return cacheData;
    }

    private static List<BookInfoBean> page1BookInfo;
    private static List<BookInfoBean> page2BookInfo;
    private static List<BookInfoBean> page3BookInfo;
    private static List<BookInfoBean> page4BookInfo;

    // 刷新
    public void getPapeInfo(Tab1PageInfoCallback tab1PageInfoCallback, int typeD, int typeX, boolean isRefresh) {
        ExecutorProcessPool.getInstance().execute(new MRunable(tab1PageInfoCallback, typeD, typeX, isRefresh));
    }

    // 首次
    public void getPapeInfo(Tab1PageInfoCallback tab1PageInfoCallback, int typeD, int typeX) {
        int type = typeD + typeX * 2;
        if (getPageBookInfo(type) != null) {
            tab1PageInfoCallback.page1Info(getPageBookInfo(type), false);
        } else {
            ExecutorProcessPool.getInstance().execute(new MRunable(tab1PageInfoCallback, typeD, typeX, false));
        }
    }

    class MRunable implements Runnable {
        private int typeD;
        private int typeX;
        private Tab1PageInfoCallback tab1PageInfoCallback;
        private boolean isRefresh;

        public MRunable(Tab1PageInfoCallback tab1PageInfoCallback, int typeD, int typeX, boolean isRefresh) {
            this.typeD = typeD;
            this.typeX = typeX;
            this.tab1PageInfoCallback = tab1PageInfoCallback;
            this.isRefresh = isRefresh;
        }

        @Override
        public void run() {
            String result;
            if (FileUtils.isFileExist(FileCache.getCaheDir() + "/getBookInfo" + typeD + "" + typeX) && !isRefresh) {
                result = FileUtils.readFile(
                        FileCache.getCaheDir() + "/getBookInfo" + typeD + "" + typeX,
                        "utf-8").toString();
//                String[] results = result.split("@@");
//
//                String time = results[0];
//                LogsUtils.e("-->time-->" + time);
//
//                result = results[1];

                List<BookInfoBean> list = JSON.parseArray(result, BookInfoBean.class);
                tab1PageInfoCallback.page1Info(list, isRefresh);

                setPageBookInfo((typeD + typeX * 2), list);
//                checkTime(time);
            } else {
                //去bmob查找
                getInfoFromBmob();
            }
        }

//        private void checkTime(String time) {
//            if (CalendarUtils.getDaySplite(Long.parseLong(time)) > 2) {
//                getInfoFromBmob();
//            }
//        }

        public void getInfoFromBmob() {
            BmobQuery<BookInfoBean> bookInfoBeanBmobQuery = new BmobQuery<BookInfoBean>();
            bookInfoBeanBmobQuery.addWhereEqualTo("typeD", typeD);
            bookInfoBeanBmobQuery.addWhereEqualTo("typeX", typeX);
            if (!TextUtils.isEmpty(CacheData.createdAt)) {
                LogsUtils.e(TAG, "-->文件不存在||isRefresh=true createdAt-->" + CacheData.createdAt);
                bookInfoBeanBmobQuery.addWhereGreaterThan("createdAt", new BmobDate(TimeCompare.StringToDate(CacheData.createdAt)));
            }
            bookInfoBeanBmobQuery.findObjects(new FindListener<BookInfoBean>() {
                @Override
                public void done(List<BookInfoBean> list, BmobException e) {

                    tab1PageInfoCallback.page1Info(list, isRefresh);
                    setPageBookInfo((typeD + typeX * 2), list);
                    boolean isDelete = FileUtils.deleteFile(FileCache.getCaheDir() + "/getBookInfo" + typeD + "" + typeX);
                    LogsUtils.e(TAG, "-->文件是否删除成功" + isDelete);
                    boolean isSave = FileUtils.writeFile(
                            FileCache.getCaheDir() + "/getBookInfo" + typeD + "" + typeX,
                            JSON.toJSONString(list));
                    LogsUtils.e(TAG, "-->文件是否保存成功" + isSave);
                }
            });
        }
    }

    private List<BookInfoBean> getPageBookInfo(int type) {
        switch (type) {
            case 0:
                return getPage1BookInfo();
            case 2:
                return getPage2BookInfo();
            case 1:
                return getPage3BookInfo();
            case 3:
                return getPage4BookInfo();
            default:
        }
        return null;
    }

    private void setPageBookInfo(int type, List<BookInfoBean> bookInfoBeans) {
        switch (type) {
            case 0:
                setPage1BookInfo(bookInfoBeans);
                break;
            case 2:
                setPage2BookInfo(bookInfoBeans);
                break;
            case 1:
                setPage3BookInfo(bookInfoBeans);
                break;
            case 3:
                setPage4BookInfo(bookInfoBeans);
                break;
            default:
        }
    }

    public static List<BookInfoBean> getPage1BookInfo() {
        return page1BookInfo;
    }

    public static void setPage1BookInfo(List<BookInfoBean> firtPageBookInfo) {
        CacheDataBmob_topnew.page1BookInfo = firtPageBookInfo;
    }

    public static List<BookInfoBean> getPage2BookInfo() {
        return page2BookInfo;
    }

    public static void setPage2BookInfo(List<BookInfoBean> page2BookInfo) {
        CacheDataBmob_topnew.page2BookInfo = page2BookInfo;
    }

    public static List<BookInfoBean> getPage3BookInfo() {
        return page3BookInfo;
    }

    public static void setPage3BookInfo(List<BookInfoBean> page3BookInfo) {
        CacheDataBmob_topnew.page3BookInfo = page3BookInfo;
    }

    public static List<BookInfoBean> getPage4BookInfo() {
        return page4BookInfo;
    }

    public static void setPage4BookInfo(List<BookInfoBean> page4BookInfo) {
        CacheDataBmob_topnew.page4BookInfo = page4BookInfo;
    }
}
