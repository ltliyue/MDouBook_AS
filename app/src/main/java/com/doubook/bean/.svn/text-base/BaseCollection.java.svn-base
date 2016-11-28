package com.doubook.bean;

import org.json.*;
import java.util.ArrayList;

public class BaseCollection {
	
    private double start;
    private double count;
    private double total;
    private ArrayList<Collections> collections;
    
    
	public BaseCollection () {
		
	}	
        
    public BaseCollection (JSONObject json) {
    
        this.start = json.optDouble("start");
        this.count = json.optDouble("count");
        this.total = json.optDouble("total");

        this.collections = new ArrayList<Collections>();
        JSONArray arrayCollections = json.optJSONArray("collections");
        if (null != arrayCollections) {
            int collectionsLength = arrayCollections.length();
            for (int i = 0; i < collectionsLength; i++) {
                JSONObject item = arrayCollections.optJSONObject(i);
                if (null != item) {
                    this.collections.add(new Collections(item));
                }
            }
        }
        else {
            JSONObject item = json.optJSONObject("collections");
            if (null != item) {
                this.collections.add(new Collections(item));
            }
        }


    }
    
    public double getStart() {
        return this.start;
    }

    public void setStart(double start) {
        this.start = start;
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

    public ArrayList<Collections> getCollections() {
        return this.collections;
    }

    public void setCollections(ArrayList<Collections> collections) {
        this.collections = collections;
    }


    
}
