package com.doubook.data;

import com.doubook.bean.Collections;
import com.doubook.getinfotools.JsoupOkHttpGetInfo;
import com.doubook.interf.Tab2PageInfoCallback;
import com.doubook.thread.ExecutorProcessPool;

import java.util.List;

public class CacheData {

    private static CacheData cacheData;

    public static CacheData getInstence() {
        if (cacheData == null) {
            cacheData = new CacheData();
        }
        return cacheData;
    }

    public static String createdAt="";

    private static List<Collections> collections_wish;
    private static List<Collections> collections_read;
    private static List<Collections> collections_reading;

    public void getTab2PageInfo(Tab2PageInfoCallback tab2PageInfoCallback, String url, boolean isRefresh) {
        ExecutorProcessPool.getInstance().execute(new MTab2Runable(tab2PageInfoCallback, url, isRefresh));
    }

    /**
     * my books
     *
     * @author MaryLee
     */
    static class MTab2Runable implements Runnable {
        private String url;
        private Tab2PageInfoCallback tab2PageInfoCallback;
        private boolean isRefresh;

        public MTab2Runable(Tab2PageInfoCallback tab2PageInfoCallback, String url, boolean isRefresh) {
            this.url = url;
            this.tab2PageInfoCallback = tab2PageInfoCallback;
            this.isRefresh = isRefresh;
        }

        @Override
        public void run() {
            List<Collections> tempBookInfo = getPageMyBookInfo(url);
            if (tempBookInfo != null && tempBookInfo.size() > 0 && !isRefresh) {
                tab2PageInfoCallback.page2Info(tempBookInfo, false);
            } else {
                JsoupOkHttpGetInfo.getInstence().getinfo(url, tab2PageInfoCallback);
                // tab2PageInfoCallback.page2Info(getPageMyBookInfo(url),
                // isRefresh);
            }
        }

        private List<Collections> getPageMyBookInfo(String type) {
            switch (type) {
                case "wish":
                    return getCollections_wish();
                case "read":
                    return getCollections_read();
                case "reading":
                    return getCollections_reading();
                default:
            }
            return null;
        }

    }

    public static void setPageMyBookInfo(String type, List<Collections> collections_) {
        switch (type) {
            case "wish":
                setCollections_wish(collections_);
            case "read":
                setCollections_read(collections_);
            case "reading":
                setCollections_reading(collections_);
            default:
        }
    }

    public static List<Collections> getCollections_wish() {
        return collections_wish;
    }

    public static void setCollections_wish(List<Collections> collections_wish) {
        CacheData.collections_wish = collections_wish;
    }

    public static List<Collections> getCollections_read() {
        return collections_read;
    }

    public static void setCollections_read(List<Collections> collections_read) {
        CacheData.collections_read = collections_read;
    }

    public static List<Collections> getCollections_reading() {
        return collections_reading;
    }

    public static void setCollections_reading(List<Collections> collections_reading) {
        CacheData.collections_reading = collections_reading;
    }

}
