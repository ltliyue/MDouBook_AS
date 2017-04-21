package com.doubook.view;

import android.app.Dialog;
import android.content.Context;

/**
 * @类描述: 对话框创建工具类 <br>
 */
public class DialogUtil {

	/**
	 * 创建进度对话框
	 * 
	 * @param context
	 * @param content
	 * @return
	 */
	public static Dialog createProgressDialog(Context context, String content) {
		return new CustomProgressDialog(context, content);
	}

	public static Dialog createProgressDialog(Context context) {
		return new CustomProgressDialog(context);
	}

}
