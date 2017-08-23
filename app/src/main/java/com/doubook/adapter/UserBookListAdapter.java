package com.doubook.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.doubook.R;
import com.doubook.bean.Book;
import com.doubook.bean.Collections;
import com.squareup.picasso.Picasso;

import java.util.List;


public class UserBookListAdapter extends RecyclerView.Adapter<UserBookListAdapter.ViewHolder> {

    List<Collections> list;
    private Context context;
    private LayoutInflater mLayoutInflater;

    public UserBookListAdapter(Context context, List<Collections> list) {
        this.context = context;
        this.list = list;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public UserBookListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = mLayoutInflater.inflate(
                R.layout.contact_list_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Book book = list.get(position).getBook();
        Picasso.with(context).load(book.getImage()).into(holder.imageView);
        holder.name.setText(book.getTitle());
        holder.ratingBar.setRating(Float.parseFloat(book.getRating().getAverage()) / 2);
        holder.point.setText(book.getRating().getAverage() + " (" + book.getRating().getNumRaters() + ")");
        holder.bookinfo.setText(book.getAuthor().toString() + "/" + book.getPubdate() + "/" + book.getPublisher() + "/"
                + book.getPrice() + "/" + book.getBinding());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        RatingBar ratingBar;
        TextView point;
        ImageView imageView;
        TextView name;
        TextView bookinfo;

        public ViewHolder(View itemView) {
            super(itemView);
            ratingBar = (RatingBar) itemView.findViewById(R.id.ratingBar);
            point = (TextView) itemView.findViewById(R.id.point);
            imageView = (ImageView) itemView.findViewById(R.id.portrait);
            name = (TextView) itemView.findViewById(R.id.name);
            bookinfo = (TextView) itemView.findViewById(R.id.bookinfo);
        }
    }

}
