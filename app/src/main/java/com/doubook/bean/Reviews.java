package com.doubook.bean;

import org.json.*;


public class Reviews {
	
    private Author author;
    private String updated;
    private String summary;
    private double votes;
    private double comments;
    private String title;
    private Rating rating;
    private double useless;
    private String published;
    private String alt;
    private String id;
    
    
	public Reviews () {
		
	}	
        
    public Reviews (JSONObject json) {
    
        this.author = new Author(json.optJSONObject("author"));
        this.updated = json.optString("updated");
        this.summary = json.optString("summary");
        this.votes = json.optDouble("votes");
        this.comments = json.optDouble("comments");
        this.title = json.optString("title");
        this.rating = new Rating(json.optJSONObject("rating"));
        this.useless = json.optDouble("useless");
        this.published = json.optString("published");
        this.alt = json.optString("alt");
        this.id = json.optString("id");

    }
    
    public Author getAuthor() {
        return this.author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getUpdated() {
        return this.updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getSummary() {
        return this.summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public double getVotes() {
        return this.votes;
    }

    public void setVotes(double votes) {
        this.votes = votes;
    }

    public double getComments() {
        return this.comments;
    }

    public void setComments(double comments) {
        this.comments = comments;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Rating getRating() {
        return this.rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public double getUseless() {
        return this.useless;
    }

    public void setUseless(double useless) {
        this.useless = useless;
    }

    public String getPublished() {
        return this.published;
    }

    public void setPublished(String published) {
        this.published = published;
    }

    public String getAlt() {
        return this.alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }


    
}
