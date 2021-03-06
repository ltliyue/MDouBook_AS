package com.doubook.data;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.doubook.bean.Book;
import com.doubook.interf.BookInfoSuperCallback;
import com.doubook.thread.ExecutorProcessPool;
import com.doubook.utiltools.FileCache;
import com.doubook.utiltools.FileUtils;
import com.doubook.utiltools.LogsUtils;
import com.doubook.utiltools.TimeCompare;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class CacheDataBmob_super {
    private static String TAG = "CacheDataBmob_super";
    private static CacheDataBmob_super cacheData;

    public static CacheDataBmob_super getInstence() {
        if (cacheData == null) {
            cacheData = new CacheDataBmob_super();
        }
        return cacheData;
    }

    // 刷新
    public void getPapeInfo(BookInfoSuperCallback bookInfoSuperCallback, int plantform, boolean isRefresh) {
        ExecutorProcessPool.getInstance().execute(new MRunable(bookInfoSuperCallback, plantform, isRefresh));
    }

    // 首次
    public void getPapeInfo(BookInfoSuperCallback bookInfoSuperCallback, int plantform) {
        if (getBookInfo(plantform) != null) {
            bookInfoSuperCallback.bookInfo(getBookInfo(plantform), false);
        } else {
            ExecutorProcessPool.getInstance().execute(new MRunable(bookInfoSuperCallback, plantform, false));
        }
    }

    class MRunable implements Runnable {
        private BookInfoSuperCallback bookInfoSuperCallback;
        private int plantform;
        private boolean isRefresh;


        public MRunable(BookInfoSuperCallback bookInfoSuperCallback, int plantform, boolean isRefresh) {
            this.bookInfoSuperCallback = bookInfoSuperCallback;
            this.plantform = plantform;
            this.isRefresh = isRefresh;
        }

        @Override
        public void run() {
            String result;
            if (FileUtils.isFileExist(FileCache.getCaheDir() + "/superBookInfo"+plantform) && !isRefresh) {
                result = FileUtils.readFile(
                        FileCache.getCaheDir() + "/superBookInfo"+plantform,
                        "utf-8").toString();

                List<Book> list = JSON.parseArray(result, Book.class);

                setBookInfo(list,plantform);
                bookInfoSuperCallback.bookInfo(list, isRefresh);

            } else {
                //去bmob查找
                getInfoFromBmob();
            }
        }

        public void getInfoFromBmob() {
            BmobQuery<Book> bookInfoBeanBmobQuery = new BmobQuery<Book>();
            bookInfoBeanBmobQuery.addWhereEqualTo("plantform", plantform);
            if (!TextUtils.isEmpty(CacheData.createdAt)) {
                LogsUtils.e("-->CacheData.createdAt-->"+CacheData.createdAt);
                LogsUtils.e(TAG, "-->文件不存在||isRefresh=true createdAt-->" + CacheData.createdAt);
                bookInfoBeanBmobQuery.addWhereGreaterThan("createdAt", new BmobDate(TimeCompare.StringToDate(CacheData.createdAt)));
            }
            bookInfoBeanBmobQuery.findObjects(new FindListener<Book>() {
                @Override
                public void done(List<Book> list, BmobException e) {

                    if (e == null) {
                        setBookInfo(list,plantform);
                        bookInfoSuperCallback.bookInfo(list, isRefresh);
                        boolean isDelete = FileUtils.deleteFile(FileCache.getCaheDir() + "/superBookInfo"+plantform);
                        LogsUtils.e(TAG, "-->文件是否删除成功" + isDelete);
                        boolean isSave = FileUtils.writeFile(
                                FileCache.getCaheDir() + "/superBookInfo"+plantform,
                                JSON.toJSONString(list));
                        LogsUtils.e(TAG, "-->文件是否保存成功" + isSave);
                    }else {
                        LogsUtils.e(e.getMessage());
                    }
                }
            });
        }
    }

    private static List<Book> bookInfo_jd;
    private static List<Book> bookInfo_dd;
    private static List<Book> bookInfo_amazon;

    public static void setBookInfo(List<Book> bookInfo,int plantform) {
            switch (plantform) {
                case 1:
                    bookInfo_jd = new ArrayList<>();
                    bookInfo_jd = bookInfo;
                    break;
                case 2:
                    bookInfo_dd = new ArrayList<>();
                    bookInfo_dd = bookInfo;
                    break;
                case 3:
                    bookInfo_amazon = new ArrayList<>();
                    bookInfo_amazon = bookInfo;
                    break;
            }
    }

    public static List<Book> getBookInfo(int plantfrom) {
        switch (plantfrom) {
            case 1:
                return bookInfo_jd;
            case 2:
                return bookInfo_dd;
            case 3:
                return bookInfo_amazon;
            default:
                LogsUtils.e("--->default-->");
                return null;
        }
    }

}
