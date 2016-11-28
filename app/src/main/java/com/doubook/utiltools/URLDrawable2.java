package com.doubook.utiltools;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.doubook.R;

public class URLDrawable2 extends BitmapDrawable {
	protected Drawable drawable;

	@SuppressWarnings("deprecation")
	public URLDrawable2(Context context) {
		this.setBounds(SystemInfoUtils.getDefaultImageBounds(context));
		// drawable = context.getResources().getDrawable(R.drawable.icon);
		// drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
		// drawable.getIntrinsicHeight());
	}

	@Override
	public void draw(Canvas canvas) {
		Log.d("test", "this=" + this.getBounds());
		if (drawable != null) {
			drawable.setBounds(0, 0, drawable.getIntrinsicWidth() * 2, drawable.getIntrinsicHeight() * 2);
			Log.d("test", "draw=" + drawable.getBounds());
			drawable.draw(canvas);
		}
	}

}
