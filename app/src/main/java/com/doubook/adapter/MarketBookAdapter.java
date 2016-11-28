package com.doubook.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.doubook.R;
import com.doubook.MyBaseAdapter;
import com.doubook.bean.MarketBook;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * 主页列表适配器类
 *
 * @version 2.12.0
 * @Copyright Copyright (c) 2012 - 2100
 * @create at 2016-08-26
 */
public class MarketBookAdapter extends MyBaseAdapter<MarketBook, ListView> {

    public MarketBookAdapter(Context context, List<MarketBook> list) {
        super(context, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(context, R.layout.booknav_list_item, null);

            holder.img_bg = (ImageView) convertView.findViewById(R.id.bg);
            holder.img = (ImageView) convertView.findViewById(R.id.img);
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.info = ((TextView) convertView.findViewById(R.id.info));
            holder.price = (TextView) convertView.findViewById(R.id.price);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Picasso.with(context).load(list.get(position).getItem_img()).into(holder.img_bg);
        Picasso.with(context).load(list.get(position).getBookface_img()).into(holder.img);
        holder.name.setText(list.get(position).getBookface_brief());
        holder.info.setText(list.get(position).getBookface_quote());
        holder.price.setText(list.get(position).getBookface_price());
        return convertView;

    }

    class ViewHolder {
        ImageView img_bg;
        ImageView img;
        TextView name;
        TextView info;
        TextView price;
    }
}
