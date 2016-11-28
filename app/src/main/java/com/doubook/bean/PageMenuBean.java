package com.doubook.bean;

import java.io.Serializable;

public class PageMenuBean implements Serializable {
	private static final long serialVersionUID = -6465237897027410019L;
	public int id;
	public String title;
	public String url;
	private int typeD;
	private int typeX;
	private int type;

	public int getTypeD() {
		return typeD;
	}

	public void setTypeD(int typeD) {
		this.typeD = typeD;
	}

	public int getTypeX() {
		return typeX;
	}

	public void setTypeX(int typeX) {
		this.typeX = typeX;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}