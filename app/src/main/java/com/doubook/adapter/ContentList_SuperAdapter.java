package com.doubook.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.doubook.MyBaseAdapter;
import com.doubook.R;
import com.doubook.bean.Book;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * 主页列表适配器类
 *
 * @version 2.12.0
 * @Copyright Copyright (c) 2012 - 2100
 * @create at 2016-08-26
 */
public class ContentList_SuperAdapter extends MyBaseAdapter<Book, ListView> {

    public ContentList_SuperAdapter(Context context, List<Book> list) {
        super(context, list);
    }


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
        holder.name.setText("《" + list.get(position).getTitle() + "》");
        Picasso.with(context).load(list.get(position).getImage()).into(holder.imageView);
        if (list.get(position).getRating() != null) {
            holder.ratingBar.setRating(Float.parseFloat(list.get(position).getRating().getAverage()) / 2);
            holder.point.setText(list.get(position).getRating().getAverage() + " (" + list.get(position).getRating().getNumRaters() + "人评价)");
        } else {
            holder.ratingBar.setVisibility(View.GONE);
            holder.point.setVisibility(View.GONE);
//            holder.point.setText(list.get(position).getEvaluateNum());
        }
        StringBuffer sf_bookinfo = new StringBuffer();
        if (!list.get(position).getOrigin_title().equals("")) {
            sf_bookinfo.append("别名：" + list.get(position).getOrigin_title() + "\n");
        }
        if (list.get(position).getAuthor().size() > 0) {
            sf_bookinfo.append("作者：");
            if (list.get(position).getAuthor().size() == 1) {
                sf_bookinfo.append(list.get(position).getAuthor().get(0));
            } else {
                for (String author : list.get(position).getAuthor()) {
                    sf_bookinfo.append(author + "/");
                }
            }
            sf_bookinfo.append("\n");
        }

        sf_bookinfo.append("出版：" + list.get(position).getPublisher() + " (" + list.get(position).getPubdate() + ")\n");
        sf_bookinfo.append("定价：" + list.get(position).getPrice() + " (" + list.get(position).getBinding() + ")\n");
        if (!list.get(position).getPages().equals("")) {
            sf_bookinfo.append("页数：" + list.get(position).getPages() + "\n");
        }
        if (!list.get(position).getIsbn13().equals("")) {
            sf_bookinfo.append("ISBN：" + list.get(position).getIsbn13() + "\n");
        }

        holder.bookinfo.setText(sf_bookinfo.toString());
//        holder.up.setText(position + 1+"");
        holder.up.setVisibility(View.GONE);
//        holder.up_num.setText(list.get(position).getUp());
        holder.up_num.setVisibility(View.GONE);

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
