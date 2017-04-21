package com.doubook.bean;

import org.json.JSONObject;

import cn.bmob.v3.BmobObject;

public class UserInfoBean extends BmobObject{

	private String id;
	private String large_avatar;
	private boolean is_banned;
	private String uid;
	private String loc_id;
	private String type;
	private String desc;
	private String avatar;
	private String signature;
	private String created;
	private String loc_name;
	private boolean is_suicide;
	private String alt;
	private String name;

	private User user;
	private String loc;

	public UserInfoBean() {
		// TODO Auto-generated constructor stub
	}

	public UserInfoBean(JSONObject json) {

		this.id = json.optString("id");
		this.large_avatar = json.optString("large_avatar");
		this.is_banned = json.optBoolean("is_banned");
		this.uid = json.optString("uid");
		this.loc_id = json.optString("loc_id");
		this.type = json.optString("type");
		this.desc = json.optString("desc");
		this.avatar = json.optString("avatar");
		this.signature = json.optString("signature");
		this.created = json.optString("created");
		this.loc_name = json.optString("loc_name");
		this.is_suicide = json.optBoolean("is_suicide");
		this.alt = json.optString("alt");
		this.name = json.optString("name");

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLarge_avatar() {
		return large_avatar;
	}

	public void setLarge_avatar(String large_avatar) {
		this.large_avatar = large_avatar;
	}

	public boolean isIs_banned() {
		return is_banned;
	}

	public void setIs_banned(boolean is_banned) {
		this.is_banned = is_banned;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getLoc_id() {
		return loc_id;
	}

	public void setLoc_id(String loc_id) {
		this.loc_id = loc_id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public String getLoc_name() {
		return loc_name;
	}

	public void setLoc_name(String loc_name) {
		this.loc_name = loc_name;
	}

	public boolean isIs_suicide() {
		return is_suicide;
	}

	public void setIs_suicide(boolean is_suicide) {
		this.is_suicide = is_suicide;
	}

	public String getAlt() {
		return alt;
	}

	public void setAlt(String alt) {
		this.alt = alt;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLoc() {
		return loc;
	}

	public void setLoc(String loc) {
		this.loc = loc;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
