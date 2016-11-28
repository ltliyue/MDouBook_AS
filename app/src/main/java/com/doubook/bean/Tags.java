package com.doubook.bean;

import org.json.*;


public class Tags {
	
    private double count;
    private String name;
    private String title;
    
    
	public Tags () {
		
	}	
        
    public Tags (JSONObject json) {
    
        this.count = json.optDouble("count");
        this.name = json.optString("name");
        this.title = json.optString("title");

    }
    
    public double getCount() {
        return this.count;
    }

    public void setCount(double count) {
        this.count = count;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    
}
