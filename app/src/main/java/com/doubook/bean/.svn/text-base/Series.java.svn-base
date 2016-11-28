package com.doubook.bean;

import org.json.*;


public class Series {
	
    private String id;
    private String title;
    
    
	public Series () {
		
	}	
        
    public Series (JSONObject json) {
    
        this.id = json.optString("id");
        this.title = json.optString("title");

    }
    
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    
}
