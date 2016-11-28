package com.doubook.getinfotools;

import com.doubook.bean.BookInfoBean;
import com.doubook.utiltools.FileCache;
import com.doubook.utiltools.FileUtils;
import com.doubook.utiltools.LogsUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * 
 * @Copyright Copyright (c) 2012 - 2100
 * @author Administrator
 * @version 1.1.0
 */
public class JsoupGetInfo {

	static JsoupGetInfo jsoupGetInfo;

	public static JsoupGetInfo getInstence() {
		if (jsoupGetInfo == null) {
			return new JsoupGetInfo();
		}
		return jsoupGetInfo;
	}

	public ArrayList<BookInfoBean> getinfo(String url, int mark) {
		ArrayList<BookInfoBean> contacters = new ArrayList<BookInfoBean>();
		switch (mark) {
		case 1:
			contacters = getBookInfo_Top(url);
			break;
		case 2:
			contacters = getBookInfo_Top(url);
			break;
		case 3:
			contacters = getBookInfo_New(url, 1);
			break;
		case 4:
			contacters = getBookInfo_New(url, 2);
			break;
		default:
		}
		return contacters;
	}

	/**
	 * 新书
	 * 
	 * @param url
	 * @return
	 */
	private ArrayList<BookInfoBean> getBookInfo_New(String url, int type) {
		ArrayList<BookInfoBean> contacters = new ArrayList<BookInfoBean>();
		int temp = 1;

		String html = "";
		if (FileUtils.isFileExist(FileCache.getCaheDir() + "/getBookInfo_New"
				+ Calendar.getInstance().get(Calendar.DAY_OF_YEAR))) {
			html = FileUtils.readFile(
					FileCache.getCaheDir() + "/getBookInfo_New" + Calendar.getInstance().get(Calendar.DAY_OF_YEAR),
					"utf-8").toString();
		} else {
			html = GetInfoByHttpUtils.getInstence().httpGet(url);

			if (html.length()>100){FileUtils.writeFile(
					FileCache.getCaheDir() + "/getBookInfo_New" + Calendar.getInstance().get(Calendar.DAY_OF_YEAR),
					html);
			}
		}
		Document doc = Jsoup.parse(html);
		Elements info_article;

		if (type == 1) {
			info_article = doc.getElementsByClass("article").select("li");
		} else {
			info_article = doc.getElementsByClass("aside").select("li");
		}

		for (Element weatherTab : info_article) {
			if (temp++ % 5 == 0) {
				continue;
			}
			BookInfoBean bookInfoBean = new BookInfoBean();

			String name = weatherTab.select("h2").text();// shuming
			String imgSrc = weatherTab.select("img").attr("src");// ͼƬ
			String pl = weatherTab.select("p.color-gray").text();// xinxi
			String info = weatherTab.select("p").get(1).text();// xinxi;
			String linkUrl = weatherTab.select("a").attr("href");// shuming

			bookInfoBean.setName(name);
			bookInfoBean.setImageUrl(imgSrc);
			bookInfoBean.setLinkUrl(linkUrl);
			bookInfoBean.setEvaluateNum(pl);
			bookInfoBean.setBookinfo(info);

			contacters.add(bookInfoBean);
		}

		return contacters;
	}

	/**
	 * 排行榜
	 * 
	 * @param url
	 * @return
	 */
	private ArrayList<BookInfoBean> getBookInfo_Top(String url) {
		ArrayList<BookInfoBean> contacters = new ArrayList<BookInfoBean>();
		String html = "";
		String urlType = url.substring(url.length() - 1);
		if (FileUtils.isFileExist(FileCache.getCaheDir() + "/getBookInfo_Top_" + urlType
				+ Calendar.getInstance().get(Calendar.DAY_OF_YEAR))) {
			LogsUtils.e("--->if");
			html = FileUtils.readFile(
					FileCache.getCaheDir() + "/getBookInfo_Top_" + urlType
							+ Calendar.getInstance().get(Calendar.DAY_OF_YEAR), "utf-8").toString();
		} else {
			LogsUtils.e("--->else");
			html = GetInfoByHttpUtils.getInstence().httpGet(url);
			if (html.length()>100){
			FileUtils.writeFile(
					FileCache.getCaheDir() + "/getBookInfo_Top_" + urlType
							+ Calendar.getInstance().get(Calendar.DAY_OF_YEAR), html);
			}
		}

		Document doc = Jsoup.parse(html);
		LogsUtils.e("--->doc"+doc.title());
		Elements infoElements = doc.select("li.clearfix");

		for (Element weatherTab : infoElements) {
			BookInfoBean bookInfoBean = new BookInfoBean();

			String name = weatherTab.select("a.fleft").text();// shuming
			String up = weatherTab.select("span.font-normal").text();// shuming
			String imgSrc = weatherTab.select("img").attr("src");// img
			String linkUrl = weatherTab.select("a").attr("href");// link
			String info = weatherTab.select("p.subject-abstract").text();// xinxi
			String rating_nums = weatherTab.select("span.font-small").text(); // pingfen
			String pl = weatherTab.select("span.ml8").text();//
			String buyInfo = weatherTab.select("span.buy-info").select("a").attr("href");// link

			bookInfoBean.setName(name);
			bookInfoBean.setUp(up);
			bookInfoBean.setImageUrl(imgSrc);
			bookInfoBean.setLinkUrl(linkUrl);
			bookInfoBean.setStarpoint(rating_nums);
			bookInfoBean.setEvaluateNum(pl);
			bookInfoBean.setBookinfo(info);
			bookInfoBean.setBuyInfo(buyInfo);

			contacters.add(bookInfoBean);
		}
		LogsUtils.e(contacters.size()+"--->");
		return contacters;
	}
}
