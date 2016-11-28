package com.doubook.utiltools;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Environment;
import android.view.Display;

public class SystemInfoUtils {
	public static Rect getDefaultImageBounds(Context context) {
		Display display = ((Activity) context).getWindowManager().getDefaultDisplay();
		int width = display.getWidth();
		int height = (int) (width * 9 / 16);

		Rect bounds = new Rect(0, 0, width, height);
		return bounds;
	}

	// 处理url
	public static String handlePicUrl(String url) {
		String[] l = url.split("/");
		return l[l.length - 1];
	}

	/** 保存网络图片 */
	public static void savePic(String source) {

		URL url;
		File file = new File(Environment.getExternalStorageDirectory() + "/VRead", handlePicUrl(source));

		InputStream in = null;
		FileOutputStream out = null;

		try {
			url = new URL(source);

			in = url.openStream();

			HttpURLConnection connUrl = (HttpURLConnection) url.openConnection();

			connUrl.setConnectTimeout(3000);

			connUrl.setRequestMethod("GET");

			if (connUrl.getResponseCode() == 200) {

				in = connUrl.getInputStream();

				out = new FileOutputStream(file);

				byte[] buffer = new byte[1024];

				int len;

				while ((len = in.read(buffer)) != -1) {
					out.write(buffer, 0, len);
				}
			} else {
				// Log.i(TAG, connUrl.getResponseCode() + "");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
