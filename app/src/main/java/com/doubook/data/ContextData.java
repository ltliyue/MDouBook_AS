package com.doubook.data;

public class ContextData {
	public static final String BmobAPPID = "d6dd4b493aa1019abd241f1e14fad578";
	// 这是我的APP
	public static final String APIKey = "0998613904a8f79a1cc923ab0dfae6ce";
	public static final String Secret = "63324041aaf91126";
	// public static final String redirect_uri = "https://book.douban.com/";
	public static final String redirect_uri = "http://meyao.bmob.cn/";

	public static final String auth2_url = "https://www.douban.com/service/auth2/auth";

	/**
	 * 登陆 https://www.douban.com/service/auth2/auth?client_id=0998613904
	 * a8f79a1cc923ab0dfae6ce
	 * &redirect_uri=http://book.douban.com/&response_type=code
	 */
	public static String LoginURL = auth2_url + "?client_id=" + APIKey + "&redirect_uri=" + redirect_uri
			+ "&response_type=code";
	/**
	 * 最受关注图书榜 非虚构类作品
	 */
	public static String best1 = "http://book.douban.com/chart?subcat=I";
	/**
	 * 最受关注图书榜 虚构类作品
	 */
	public static String best2 = "http://book.douban.com/chart?subcat=F";
	/**
	 * 最受关注新书榜 非虚构类作品
	 */
	public static String newbook = "http://book.douban.com/latest?icn=index-latestbook-all";
	/**
	 * 
	 */
	public static String bookinfo = "http://api.douban.com/book/subject/isbn/";
	/**
	 * http://book.douban.com/top250
	 */
	public static String top250 = "http://book.douban.com/top250";
	/**
	 * search
	 */
	public static String Search = "http://book.douban.com/subject_search?search_text=";

	/**
	 * GetAccessToken
	 */
	public static String GetAccessToken = "https://www.douban.com/service/auth2/token";
	/**
	 * GetUserInfos
	 */
	public static String GetUserInfo = "https://api.douban.com/v2/user/";
	/**
	 * 用户收藏 https://api.douban.com/v2/book/user/78160294/collections
	 */
	public static String UserBookSave = "https://api.douban.com/v2/book/user/";
	/**
	 * 书籍评论
	 */
	public static String UserBookReview = "https://api.douban.com/v2/book/";
	/**
	 * 书籍信息
	 */
	public static String BooksInfo = "https://api.douban.com/v2/book/";

	public static int toastTime = 2000;
	public static int refreshTime = 800;

	// -------------------------------------

}
