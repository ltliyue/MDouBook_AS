package com.doubook.bean;

import org.json.*;
import java.util.ArrayList;

public class BaseReviews {
	
    private ArrayList<Reviews> reviews;
    private double count;
    private double total;
    private double start;
    
    
	public BaseReviews () {
		
	}	
        
    public BaseReviews (JSONObject json) {
    

        this.reviews = new ArrayList<Reviews>();
        JSONArray arrayReviews = json.optJSONArray("reviews");
        if (null != arrayReviews) {
            int reviewsLength = arrayReviews.length();
            for (int i = 0; i < reviewsLength; i++) {
                JSONObject item = arrayReviews.optJSONObject(i);
                if (null != item) {
                    this.reviews.add(new Reviews(item));
                }
            }
        }
        else {
            JSONObject item = json.optJSONObject("reviews");
            if (null != item) {
                this.reviews.add(new Reviews(item));
            }
        }

        this.count = json.optDouble("count");
        this.total = json.optDouble("total");
        this.start = json.optDouble("start");

    }
    
    public ArrayList<Reviews> getReviews() {
        return this.reviews;
    }

    public void setReviews(ArrayList<Reviews> reviews) {
        this.reviews = reviews;
    }

    public double getCount() {
        return this.count;
    }

    public void setCount(double count) {
        this.count = count;
    }

    public double getTotal() {
        return this.total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getStart() {
        return this.start;
    }

    public void setStart(double start) {
        this.start = start;
    }


    
}
