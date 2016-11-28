package com.doubook.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.doubook.R;
import com.doubook.bean.BookInfoBean;
import com.squareup.picasso.Picasso;

/**
 * 展示联系人列表用的适配器类
 * 
 * @Copyright Copyright (c) 2012 - 2100
 * @create at 2013-11-4
 * @version 1.1.0
 */
public class ContactListAdapter extends BaseAdapter {
	// private ImageCache IMAGE_CACHE = CacheManager.getImageCache();
	private List<BookInfoBean> contacters = new ArrayList<BookInfoBean>();
	private LayoutInflater mInflater = null;
	Context context;

	public ContactListAdapter(Context context) {
		this.context = context;
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public void setData(List<BookInfoBean> contacters) {
		this.contacters = contacters;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return contacters == null ? 0 : contacters.size();
	}

	@Override
	public BookInfoBean getItem(int position) {
		return contacters.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.contact_list_item, null);
		}
		BookInfoBean info = contacters.get(position);

		RatingBar ratingBar = (RatingBar) convertView.findViewById(R.id.ratingBar);
		TextView point = ((TextView) convertView.findViewById(R.id.point));
		ImageView imageView = (ImageView) convertView.findViewById(R.id.portrait);

		Picasso.with(context).load(info.getImageUrl()).into(imageView);

		((TextView) convertView.findViewById(R.id.name)).setText(info.getName());
		if (info.getStarpoint() != null && !info.getStarpoint().equalsIgnoreCase("")) {
			ratingBar.setRating(Float.parseFloat(info.getStarpoint()) / 2);
			point.setText(info.getStarpoint() + " " + info.getEvaluateNum());
		} else {
			ratingBar.setVisibility(View.GONE);
			point.setText(info.getEvaluateNum());
		}
		((TextView) convertView.findViewById(R.id.bookinfo)).setText(info.getBookinfo());

		return convertView;
	}
}
