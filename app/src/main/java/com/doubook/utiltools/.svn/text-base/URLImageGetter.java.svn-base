package com.doubook.utiltools;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.squareup.picasso.Picasso;

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
public class URLImageGetter implements ImageGetter {
	TextView textView;
	Context context;

	public URLImageGetter(Context contxt, TextView textView) {
		this.context = contxt;
		this.textView = textView;
	}

	@Override
	public Drawable getDrawable(String paramString) {
		URLDrawable urlDrawable = new URLDrawable(context);

		ImageGetterAsyncTask getterTask = new ImageGetterAsyncTask(urlDrawable);
		getterTask.execute(paramString);
		return urlDrawable;
	}

	public class ImageGetterAsyncTask extends AsyncTask<String, Void, Drawable> {
		URLDrawable urlDrawable;

		public ImageGetterAsyncTask(URLDrawable drawable) {
			this.urlDrawable = drawable;
		}

		@Override
		protected void onPostExecute(Drawable result) {
			if (result != null) {
				urlDrawable.drawable = result;

				URLImageGetter.this.textView.requestLayout();
			}
		}

		@Override
		protected Drawable doInBackground(String... params) {
			Drawable drawable;
			String source = params[0];
			checkFileExists();
			File file = new File(Environment.getExternalStorageDirectory() + "/VRead/",
					SystemInfoUtils.handlePicUrl(source));
			LogsUtils.e("-->" + file.toString());
			if (file.exists()) {
				// 存在即获取drawable
				drawable = Drawable.createFromPath(file.getAbsolutePath());
			} else {
				// 不存在即开启异步任务加载网络图片
				SystemInfoUtils.savePic(source);
				drawable = Drawable.createFromPath(file.getAbsolutePath());
			}
			drawable.setBounds(0, 0, drawable.getIntrinsicWidth() * 2, drawable.getIntrinsicHeight() * 2);

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

}