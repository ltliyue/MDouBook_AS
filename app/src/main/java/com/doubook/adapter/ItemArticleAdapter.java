package com.doubook.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.doubook.R;
import com.doubook.activity.WebActivity;
import com.doubook.bean.MarketBook;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by MaryLee on 16/9/28.
 */
public class ItemArticleAdapter extends RecyclerView.Adapter<ItemArticleAdapter.ImageItemArticleViewHolder> {

    private List<MarketBook> articleList;
    private Context context;
    private LayoutInflater mLayoutInflater;


    public ItemArticleAdapter(Context context, List<MarketBook> articleList) {
        this.context = context;
        this.articleList = articleList;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public ItemArticleAdapter.ImageItemArticleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(
                R.layout.booknav_list_item, parent, false);

        return new ImageItemArticleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ImageItemArticleViewHolder holder, final int position) {
        MarketBook article = articleList.get(position);
        Picasso.with(context).load(article.getItem_img()).into(holder.img_bg);
        Picasso.with(context).load(article.getBookface_img()).into(holder.img);
        holder.name.setText(article.getBookface_brief());
        holder.info.setText(article.getBookface_quote());
        holder.price.setText(article.getBookface_price());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(context, WebActivity.class);
                mIntent.putExtra("linkUrl", "https://market.douban.com" + articleList.get(position).getItem_link());
                context.startActivity(mIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }

    class ImageItemArticleViewHolder extends RecyclerView.ViewHolder {

        ImageView img_bg;
        ImageView img;
        TextView name;
        TextView info;
        TextView price;

        public ImageItemArticleViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            img_bg = (ImageView) itemView.findViewById(R.id.bg);
            img = (ImageView) itemView.findViewById(R.id.img);
            name = (TextView) itemView.findViewById(R.id.name);
            info = ((TextView) itemView.findViewById(R.id.info));
            price = (TextView) itemView.findViewById(R.id.price);

        }
    }

}