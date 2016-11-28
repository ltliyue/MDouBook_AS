package com.doubook.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.doubook.R;
import com.doubook.MyBaseAdapter;
import com.doubook.bean.Reviews;
import com.squareup.picasso.Picasso;

/**
 * 评论列表适配器类
 * 
 * @Copyright Copyright (c) 2012 - 2100
 * @create at 2016-08-26
 * @version 2.12.0
 */
public class PLListNewAdapter extends MyBaseAdapter<Reviews, ListView> {

	public PLListNewAdapter(Context context, List<Reviews> list) {
		super(context, list);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder = null;

		if (convertView == null) {
			holder = new ViewHolder();
			convertView = View.inflate(context, R.layout.pl_list_item, null);

			holder.img = ((ImageView) convertView.findViewById(R.id.img));
			holder.title = ((TextView) convertView.findViewById(R.id.title));
			holder.user = ((TextView) convertView.findViewById(R.id.user));
			holder.time = ((TextView) convertView.findViewById(R.id.time));
			holder.bookinfo = ((TextView) convertView.findViewById(R.id.info));
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		Picasso.with(context).load(list.get(position).getAuthor().getAvatar()).into(holder.img);

		holder.title.setText(list.get(position).getTitle());
		holder.user.setText(list.get(position).getAuthor().getName());
		holder.time.setText(list.get(position).getPublished() + "    " + list.get(position).getVotes() + "票");
		holder.bookinfo.setText(list.get(position).getSummary() + "(" + list.get(position).getComments() + "回应)");

		return convertView;
	}

	class ViewHolder {
		ImageView img;
		TextView title;
		TextView user;
		TextView time;
		TextView bookinfo;
	}
}
