package com.doubook.bean;

import org.json.*;
import java.util.ArrayList;

public class Collections {

	private String status;
	private Rating rating;
	private String updated;
	private double id;
	private Book book;
	private String book_id;
	private String user_id;
	private String comment;
	private ArrayList<String> tags;

	public Collections() {

	}

	public Collections(JSONObject json) {

		this.status = json.optString("status");
		this.rating = new Rating(json.optJSONObject("rating"));
		this.updated = json.optString("updated");
		this.id = json.optDouble("id");
		this.book = new Book(json.optJSONObject("book"));
		this.book_id = json.optString("book_id");
		this.user_id = json.optString("user_id");
		this.comment = json.optString("comment");

		this.tags = new ArrayList<String>();
		JSONArray arrayTags = json.optJSONArray("tags");
		if (null != arrayTags) {
			int tagsLength = arrayTags.length();
			for (int i = 0; i < tagsLength; i++) {
				String item = arrayTags.optString(i);
				if (null != item) {
					this.tags.add(item);
				}
			}
		} else {
			String item = json.optString("tags");
			if (null != item) {
				this.tags.add(item);
			}
		}

	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Rating getRating() {
		return this.rating;
	}

	public void setRating(Rating rating) {
		this.rating = rating;
	}

	public String getUpdated() {
		return this.updated;
	}

	public void setUpdated(String updated) {
		this.updated = updated;
	}

	public double getId() {
		return this.id;
	}

	public void setId(double id) {
		this.id = id;
	}

	public Book getBook() {
		return this.book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public String getBook_id() {
		return book_id;
	}

	public void setBook_id(String book_id) {
		this.book_id = book_id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public ArrayList<String> getTags() {
		return this.tags;
	}

	public void setTags(ArrayList<String> tags) {
		this.tags = tags;
	}

}
