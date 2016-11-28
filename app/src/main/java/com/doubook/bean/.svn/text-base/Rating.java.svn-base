package com.doubook.bean;

import org.json.*;


public class Rating {
	
    private double min;
    private String average;
    private double max;
    private double numRaters;
    
    
	public Rating () {
		
	}	
        
    public Rating (JSONObject json) {
    
        this.min = json.optDouble("min");
        this.average = json.optString("average");
        this.max = json.optDouble("max");
        this.numRaters = json.optDouble("numRaters");

    }
    
    public double getMin() {
        return this.min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public String getAverage() {
        return this.average;
    }

    public void setAverage(String average) {
        this.average = average;
    }

    public double getMax() {
        return this.max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public double getNumRaters() {
        return this.numRaters;
    }

    public void setNumRaters(double numRaters) {
        this.numRaters = numRaters;
    }


    
}
