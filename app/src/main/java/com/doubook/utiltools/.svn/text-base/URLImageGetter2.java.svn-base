package com.doubook.utiltools;

import java.io.File;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Environment;
import android.text.Html.ImageGetter;
import android.widget.TextView;

public class URLImageGetter2 implements ImageGetter {
	Context context;
	TextView textView;

	public URLImageGetter2(Context context, TextView textView) {
		this.context = context;
		this.textView = textView;
	}

	@Override
	public Drawable getDrawable(String paramString) {
		final URLDrawable2 urlDrawable = new URLDrawable2(context);

		ImageGetterAsyncTask getterTask = new ImageGetterAsyncTask(urlDrawable);
		getterTask.execute(paramString);
		return urlDrawable;
	}

	public class ImageGetterAsyncTask extends AsyncTask<String, Void, Drawable> {
		URLDrawable2 urlDrawable;

		public ImageGetterAsyncTask(URLDrawable2 drawable) {
			this.urlDrawable = drawable;
		}

		@Override
		protected void onPostExecute(Drawable result) {
			if (result != null) {
				urlDrawable.drawable = result;

				URLImageGetter2.this.textView.requestLayout();
			}
		}

		@Override
		protected Drawable doInBackground(String... params) {
			String source = params[0];
			return fetchDrawable(source);
		}

		public Drawable fetchDrawable(String url) {

//			Rect bounds = SystemInfoUtils.getDefaultImageBounds(context);

			Drawable drawable;

			checkFileExists();
			File file = new File(Environment.getExternalStorageDirectory() + "/VRead/",
					SystemInfoUtils.handlePicUrl(url));
			LogsUtils.e("-->" + file.toString());
			if (file.exists()) {
				// 存在即获取drawable
				drawable = Drawable.createFromPath(file.getAbsolutePath());
			} else {
				// 不存在即开启异步任务加载网络图片
				SystemInfoUtils.savePic(url);
				drawable = Drawable.createFromPath(file.getAbsolutePath());
			}
//			drawable.setBounds(0, 0, drawable.getIntrinsicWidth() * 2, drawable.getIntrinsicHeight() * 2);

			return drawable;

		}

		private void checkFileExists() {
			File file = new File(Environment.getExternalStorageDirectory() + "/VRead");
			if (!file.exists()) {
				file.mkdir();
			}
		}
	}

}
