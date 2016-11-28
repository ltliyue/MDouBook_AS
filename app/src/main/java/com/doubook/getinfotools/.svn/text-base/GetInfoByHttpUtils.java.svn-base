package com.doubook.getinfotools;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class GetInfoByHttpUtils {
    // ---------------------------------
    static GetInfoByHttpUtils getInfoThread;

    public static GetInfoByHttpUtils getInstence() {
        if (getInfoThread == null) {
            getInfoThread = new GetInfoByHttpUtils();
        }
        return getInfoThread;
    }

    private static void config(HttpRequestBase httpRequestBase) {
        httpRequestBase.setHeader("Connection", "keep-alive");
        httpRequestBase.setHeader("X-Requested-With", "XMLHttpRequest");
        httpRequestBase.setHeader("User-Agent", "Mozilla/5.0");
        httpRequestBase.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        httpRequestBase.setHeader("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");// "en-US,en;q=0.5");
        httpRequestBase.setHeader("Accept-Charset", "ISO-8859-1,utf-8,gbk,gb2312;q=0.7,*;q=0.7");
        httpRequestBase.setHeader("Cookie", "access_log=03ec35b9a7aa1520bd65fe8568fd94da; Hm_lvt_a028c07bf31ffce4b2d21dd85b0b8907=1464683117; PHPSESSID=rl8f3sdudb1q0d88dq24f94515; SID=479; BAIDUID=355A6B7B6B961E1858210316BE21E76B:FG=1; BDUSS=ZZVDJqeVNmcEdJVlRxdXBVS1lGdEE4aUExTTgtMXRmWVRqMVRNTVc5Z2p2R0JYQVFBQUFBJCQAAAAAAAAAAAEAAAB8SdkYbHRsaXl1ZTU1AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAACMvOVcjLzlXcG; STOKEN=d349a901f9ac59103f2b2438e690483bafcabf6e2c95345bbe78082e921d1d9f; channel=002301%7C%7Ct10_jn_pc; areaCode=1400020000");
    }


    public String httpGet(String url) {

        String returnStr = null;
        // 参数检测
        if (url == null || "".equals(url)) {
            return returnStr;
        }
//        LogUtils.e(getClass()+"2:"+url);
        HttpGet httpRequest = new HttpGet(url);
        try {
            config(httpRequest);
            HttpClient httpClient = new DefaultHttpClient();

            HttpClientParams.setCookiePolicy(httpClient.getParams(), CookiePolicy.BROWSER_COMPATIBILITY);

            HttpResponse httpResponse = httpClient.execute(httpRequest);

            int status = httpResponse.getStatusLine().getStatusCode();
            if (status >= 200 && status < 300) {

                HttpEntity entity = httpResponse.getEntity();
                String resopnse = "";
                if (entity != null) {
                    resopnse = EntityUtils.toString(entity, "utf-8");
                }
                return resopnse;
            } else {
                return "noinfo";
            }

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnStr;
    }

}