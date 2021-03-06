package com.doubook.activity;

import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.doubook.BaseActivty;
import com.doubook.R;
import com.doubook.bean.Book;
import com.doubook.data.ContextData;
import com.doubook.utiltools.LogsUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.AbsCallback;
import com.squareup.picasso.Picasso;

import okhttp3.Response;

public class BookInfo_APIActivity extends BaseActivty {
    private Book books;
    private ImageView image;
    private TextView name, point, infos, txt_intro_jianjie, txt_intro_writer, txt_booklist;
    private RatingBar ratingBar;
    private LinearLayout lin_context_jianjie, lin_context_writer, lin_context_booklist;
    private String bookid;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {

            switch (msg.what) {
                case 1:
                    missLonding();

                    ratingBar.setVisibility(View.VISIBLE);

                    // 作者信息
                    lin_context_writer.setVisibility(View.VISIBLE);
                    txt_intro_writer.setText(books.getAuthor_intro());
                    // 书简介
                    lin_context_jianjie.setVisibility(View.VISIBLE);

                    txt_intro_jianjie.setText("    " + books.getSummary());
                    // 目录
                    if (!"".equals(books.getCatalog()) && books.getCatalog() != null) {
                        lin_context_booklist.setVisibility(View.VISIBLE);
                        txt_booklist.setText(books.getCatalog());
                    }

                    LogsUtils.e(books.getImages().getLarge() + "::" + books.getImages().getMedium());
                    Picasso.with(ct).load(books.getImages().getLarge()).into(image);
                    // IMAGE_CACHE.get(books.getImages().getLarge(), image);// 图片

                    // 书名
                    name.setText(books.getTitle());
                    // 五角星
                    ratingBar.setRating(Float.parseFloat(books.getRating().getAverage()) / 2);
                    // 评价
                    point.setText(books.getRating().getAverage() + "(" + books.getRating().getNumRaters() + "人评价)");

                    StringBuffer stringBuffer = new StringBuffer();// 作者
                    for (int i = 0; i < books.getAuthor().size(); i++) {
                        stringBuffer.append(books.getAuthor().get(i) + " ");
                    }

                    infos.setText("作者：" + stringBuffer + "\n出版社：" + books.getPublisher() + "\n出版年：" + books.getPubdate()
                            + "\n页数：" + books.getPages() + "\n定价：" + books.getPrice() + "\n装帧：" + books.getBinding()
                            + "\nISBN：" + books.getIsbn13());
                    break;
            }
        }
    };

    @Override
    public void initView() {
        setContentView(R.layout.book_info);

    }

    @Override
    protected void initData() {
        image = (ImageView) findViewById(R.id.image);
        name = (TextView) findViewById(R.id.name);
        point = (TextView) findViewById(R.id.point);
        infos = (TextView) findViewById(R.id.infos);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        txt_intro_jianjie = (TextView) findViewById(R.id.txt_intro_jianjie);
        txt_intro_writer = (TextView) findViewById(R.id.txt_intro_writer);
        txt_booklist = (TextView) findViewById(R.id.txt_booklist);
        lin_context_jianjie = (LinearLayout) findViewById(R.id.lin_context_jianjie);
        lin_context_writer = (LinearLayout) findViewById(R.id.lin_context_writer);
        lin_context_booklist = (LinearLayout) findViewById(R.id.lin_context_booklist);

        bookid = getIntent().getStringExtra("linkUrl");
        if (bookid.contains("subject/")) {
            bookid = bookid.split("subject/")[1].replace("/", "");
        }
        showLonding();

        btn_back.setOnClickListener(this);
        setting.setOnClickListener(this);

        setTitle("图书信息");
        setting.setVisibility(View.VISIBLE);
        setting.setText("评价");
        getUserinfo();
    }

    @Override
    protected void processClick(View v) {
        switch (v.getId()) {
            case R.id.setting:
                Intent mIntent = new Intent(BookInfo_APIActivity.this, ReviewsListActivity.class);
                mIntent.putExtra("bookid", bookid);
                startActivity(mIntent);
                break;
            default:
                break;
        }
    }

    public void getUserinfo() {
        LogsUtils.e(ContextData.BooksInfo + bookid);

        OkGo.<Book>get(ContextData.BooksInfo + bookid).execute(new AbsCallback<Book>() {
            @Override
            public void onSuccess(com.lzy.okgo.model.Response<Book> response) {
            }

            @Override
            public Book convertResponse(Response response) throws Throwable {
                books = JSON.parseObject(response.body().string(),Book.class);
                mHandler.sendEmptyMessage(1);
                return books;
            }

            @Override
            public void onError(com.lzy.okgo.model.Response<Book> response) {
                super.onError(response);
                LogsUtils.e(response.message());
            }
        });

    }
}
