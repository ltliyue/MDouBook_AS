package com.doubook.bean;

import cn.bmob.v3.BmobObject;

public class MarketBook extends BmobObject {
    String item_link;
    String item_img;
    String bookface_img;
    String bookface_price;
    String bookface_brief;
    String bookface_quote;

    String book_infor;
    String book_content;

    public String getBook_infor() {
        return book_infor;
    }

    public void setBook_infor(String book_infor) {
        this.book_infor = book_infor;
    }

    public String getBook_content() {
        return book_content;
    }

    public void setBook_content(String book_content) {
        this.book_content = book_content;
    }

    public String getItem_link() {
        return item_link;
    }

    public void setItem_link(String item_link) {
        this.item_link = item_link;
    }

    public String getItem_img() {
        return item_img;
    }

    public void setItem_img(String item_img) {
        this.item_img = item_img;
    }

    public String getBookface_img() {
        return bookface_img;
    }

    public void setBookface_img(String bookface_img) {
        this.bookface_img = bookface_img;
    }

    public String getBookface_price() {
        return bookface_price;
    }

    public void setBookface_price(String bookface_price) {
        this.bookface_price = bookface_price;
    }

    public String getBookface_brief() {
        return bookface_brief;
    }

    public void setBookface_brief(String bookface_brief) {
        this.bookface_brief = bookface_brief;
    }

    public String getBookface_quote() {
        return bookface_quote;
    }

    public void setBookface_quote(String bookface_quote) {
        this.bookface_quote = bookface_quote;
    }

    @Override
    public String toString() {
        return "MarketBook [item_link=" + item_link + ", item_img=" + item_img + ", bookface_img=" + bookface_img
                + ", bookface_price=" + bookface_price + ", bookface_brief=" + bookface_brief + ", bookface_quote="
                + bookface_quote + "]";
    }

}
