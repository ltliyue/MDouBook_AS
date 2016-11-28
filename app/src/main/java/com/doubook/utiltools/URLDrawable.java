package com.doubook.utiltools;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.Display;

import com.doubook.R;

/**
 * html标签显示图片
 * 
 * @author MaryLee
 * 
 */
public class URLDrawable extends BitmapDrawable {
	protected Drawable drawable;

	@SuppressWarnings("deprecation")
	public URLDrawable(Context context) {
		this.setBounds(getDefaultImageBounds(context));
		drawable = context.getResources().getDrawable(R.drawable.icon);
		drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
	}

	@Override
	public void draw(Canvas canvas) {
		if (drawable != null) {
			drawable.draw(canvas);
		}
	}

	@SuppressWarnings("deprecation")
	public Rect getDefaultImageBounds(Context context) {
		Display display = ((Activity) context).getWindowManager().getDefaultDisplay();
		int width = display.getWidth();
		int height = display.getHeight() / 5;
		Rect bounds = new Rect(0, -100, width, height);
		return bounds;
	}
}