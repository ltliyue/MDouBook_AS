package com.doubook.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.doubook.R;
import com.doubook.MyBaseAdapter;
import com.doubook.bean.BookInfoBean;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * 主页列表适配器类
 *
 * @version 2.12.0
 * @Copyright Copyright (c) 2012 - 2100
 * @create at 2016-08-26
 */
public class ContentListAdapter extends MyBaseAdapter<BookInfoBean, ListView> {
    private final int TYPE1 = 1;
    private final int TYPE2 = 2;

    public ContentListAdapter(Context context, List<BookInfoBean> list) {
        super(context, list);
    }

//	@Override
//	public int getItemViewType(int position) {
//		if (position % 2 == 0) {// 右边
//			return TYPE1;
//		} else {// 左边
//			return TYPE2;
//		}
//	}

//	@Override
//	public int getViewTypeCount() {
//		// TODO Auto-generated method stub
//		return 3;
//	}

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;

//        int type = getItemViewType(position);
//		if (type == TYPE1) {
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(context, R.layout.contact_list_item, null);

            holder.ratingBar = (RatingBar) convertView.findViewById(R.id.ratingBar);
            holder.point = ((TextView) convertView.findViewById(R.id.point));
            holder.imageView = (ImageView) convertView.findViewById(R.id.portrait);
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.bookinfo = (TextView) convertView.findViewById(R.id.bookinfo);
            holder.up = (TextView) convertView.findViewById(R.id.up);
            holder.up_num = (TextView) convertView.findViewById(R.id.up_num);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.name.setText("《"+list.get(position).getName()+"》");
        Picasso.with(context).load(list.get(position).getImageUrl()).into(holder.imageView);
        if (list.get(position).getStarpoint() != null && !list.get(position).getStarpoint().equalsIgnoreCase("")) {
            holder.ratingBar.setRating(Float.parseFloat(list.get(position).getStarpoint()) / 2);
            holder.point.setText(list.get(position).getStarpoint() + " " + list.get(position).getEvaluateNum());
        } else {
            holder.ratingBar.setVisibility(View.GONE);
            holder.point.setText(list.get(position).getEvaluateNum());
        }
        holder.bookinfo.setText(list.get(position).getBookinfo());
        holder.up.setText(position + 1+"");
        holder.up_num.setText(list.get(position).getUp());

//		}else {
//			if (convertView == null) {
//				holder = new ViewHolder();
//				convertView = View.inflate(context, R.layout.contact_list_item_right, null);
//
//				holder.ratingBar = (RatingBar) convertView.findViewById(R.id.ratingBar);
//				holder.point = ((TextView) convertView.findViewById(R.id.point));
//				holder.imageView = (ImageView) convertView.findViewById(R.id.portrait);
//				holder.name = (TextView) convertView.findViewById(R.id.name);
//				holder.bookinfo = (TextView) convertView.findViewById(R.id.bookinfo);
//
//				convertView.setTag(holder);
//			} else {
//				holder = (ViewHolder) convertView.getTag();
//			}
//			holder.name.setText(list.get(position).getName());
//			Picasso.with(context).load(list.get(position).getImageUrl()).into(holder.imageView);
//			if (list.get(position).getStarpoint() != null && !list.get(position).getStarpoint().equalsIgnoreCase("")) {
//				holder.ratingBar.setRating(Float.parseFloat(list.get(position).getStarpoint()) / 2);
//				holder.point.setText(list.get(position).getStarpoint() + " " + list.get(position).getEvaluateNum());
//			} else {
//				holder.ratingBar.setVisibility(View.GONE);
//				holder.point.setText(list.get(position).getEvaluateNum());
//			}
//			holder.bookinfo.setText(list.get(position).getBookinfo());
//		}
        return convertView;

    }

    class ViewHolder {
        RatingBar ratingBar;
        ImageView imageView;
        TextView point;
        TextView name;
        TextView bookinfo;
        TextView up_num;
        TextView up;
    }
}
