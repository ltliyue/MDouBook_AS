package com.doubook.bean;

import org.json.*;


public class Images {
	
    private String small;
    private String large;
    private String medium;
    
    
	public Images () {
		
	}	
        
    public Images (JSONObject json) {
    
        this.small = json.optString("small");
        this.large = json.optString("large");
        this.medium = json.optString("medium");

    }
    
    public String getSmall() {
        return this.small;
    }

    public void setSmall(String small) {
        this.small = small;
    }

    public String getLarge() {
        return this.large;
    }

    public void setLarge(String large) {
        this.large = large;
    }

    public String getMedium() {
        return this.medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }


    
}
