package com.doubook.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.doubook.MyBaseAdapter;
import com.doubook.R;
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
public class ContentList_NewAdapter extends MyBaseAdapter<BookInfoBean, ListView> {

    public ContentList_NewAdapter(Context context, List<BookInfoBean> list) {
        super(context, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(context, R.layout.contact_list_item_new, null);

            holder.point = ((TextView) convertView.findViewById(R.id.point));
            holder.imageView = (ImageView) convertView.findViewById(R.id.portrait);
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.bookinfo = (TextView) convertView.findViewById(R.id.bookinfo);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.name.setText("《"+list.get(position).getName()+"》");
        Picasso.with(context).load(list.get(position).getImageUrl()).into(holder.imageView);
        holder.point.setText(list.get(position).getEvaluateNum());
        holder.bookinfo.setText(list.get(position).getBookinfo());
        return convertView;

    }

    class ViewHolder {
        ImageView imageView;
        TextView point;
        TextView name;
        TextView bookinfo;
    }
}
