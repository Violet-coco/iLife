package com.example.class_24_c4_0508_01_Property;

public class User {
	
	private long _id;
	private String title;
	private String content;
	private long set_tiem;
	private long attack_tiem;
	private boolean flog=false;


	
	public User(long _id,String title,String content,long set_tiem,long attack_tiem,boolean flog){
		this._id=_id;
		this.title=title;
		this.content=content;
		this.set_tiem=set_tiem;
		this.attack_tiem=attack_tiem;
		this.flog=flog;
	}



	public long get_id() {
		return _id;
	}



	public void set_id(long _id) {
		this._id = _id;
	}



	public String getTitle() {
		return title;
	}



	public void setTitle(String title) {
		this.title = title;
	}



	public String getContent() {
		return content;
	}



	public void setContent(String content) {
		this.content = content;
	}



	public long getSet_tiem() {
		return set_tiem;
	}



	public void setSet_tiem(long set_tiem) {
		this.set_tiem = set_tiem;
	}



	public long getAttack_tiem() {
		return attack_tiem;
	}



	public void setAttack_tiem(long attack_tiem) {
		this.attack_tiem = attack_tiem;
	}



	public boolean isFlog() {
		return flog;
	}



	public void setFlog(boolean flog) {
		this.flog = flog;
	}
}
