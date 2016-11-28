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
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Environment;
import android.text.Html.ImageGetter;
import android.view.Display;
import android.widget.TextView;

/**
 * html标签下载图片
 * 
 * @author MaryLee 20160825
 */
public class MURLImageGetter implements ImageGetter {
	TextView textView;
	Context context;

	public MURLImageGetter(Context contxt, TextView textView) {
		this.context = contxt;
		this.textView = textView;
	}

	@Override
	public Drawable getDrawable(String paramString) {
		Drawable urlDrawable = null;

		ImageGetterAsyncTask getterTask = new ImageGetterAsyncTask(urlDrawable);
		getterTask.execute(paramString);
		return urlDrawable;
	}

	public class ImageGetterAsyncTask extends AsyncTask<String, Void, Drawable> {
		Drawable urlDrawable;

		public ImageGetterAsyncTask(Drawable drawable) {
			this.urlDrawable = drawable;
		}

		@Override
		protected void onPostExecute(Drawable result) {
			if (result != null) {
				urlDrawable = result;
				MURLImageGetter.this.textView.requestLayout();
			}
		}

		@Override
		protected Drawable doInBackground(String... params) {
			Drawable drawable;
			String source = params[0];
			checkFileExists();
			File file = new File(Environment.getExternalStorageDirectory() + "/VRead/", handlePicUrl(source));
			LogsUtils.e("-->" + file.toString());
			if (file.exists()) {
				// 存在即获取drawable
				drawable = Drawable.createFromPath(file.getAbsolutePath());
			} else {
				// 不存在即开启异步任务加载网络图片
				savePic(source);
				drawable = Drawable.createFromPath(file.getAbsolutePath());
			}
			// drawable.setBounds(0, 0, drawable.getIntrinsicWidth() * 2,
			// drawable.getIntrinsicHeight() * 2);

			Rect bounds = getDefaultImageBounds(context);
			int newwidth = bounds.width();
			int newheight = bounds.height();
			double factor = 1;
			double fx = (double) drawable.getIntrinsicWidth() / (double) newwidth;
			double fy = (double) drawable.getIntrinsicHeight() / (double) newheight;
			factor = fx > fy ? fx : fy;
			if (factor < 1)
				factor = 1;
			newwidth = (int) (2 * drawable.getIntrinsicWidth() / factor);
			newheight = (int) (2 * drawable.getIntrinsicHeight() / factor);
			drawable.setBounds(0, 0, newwidth, newheight);
			return drawable;
		}

		// 预定图片宽高比例为 4:3
		@SuppressWarnings("deprecation")
		public Rect getDefaultImageBounds(Context context) {
			Display display = ((Activity) context).getWindowManager().getDefaultDisplay();
			int width = display.getWidth() * 2;
			int height = display.getHeight() * 2;
			Rect bounds = new Rect(0, 0, width, height);
			return bounds;
		}
	}

	private void checkFileExists() {
		File file = new File(Environment.getExternalStorageDirectory() + "/VRead");
		if (!file.exists()) {
			file.mkdir();
		}
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