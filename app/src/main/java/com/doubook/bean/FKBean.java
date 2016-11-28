package com.doubook.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobDate;

public class FKBean extends BmobObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String fkInfo;
	private String relationType;
	private String appName;

	private BmobDate uploadTime;

	public String getFkInfo() {
		return fkInfo;
	}

	public void setFkInfo(String fkInfo) {
		this.fkInfo = fkInfo;
	}

	public String getRelationType() {
		return relationType;
	}

	public void setRelationType(String relationType) {
		this.relationType = relationType;
	}

	public BmobDate getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(BmobDate uploadTime) {
		this.uploadTime = uploadTime;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

}
