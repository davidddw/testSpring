package com.yunshan.testSpring.po;

import java.util.Date;

public class Poll {
	private int id;
	private String question;
	private Date pub_date;
	
	public Poll() {
		
	}
	
	public Poll(int id, String question, Date pub_date) {
		this.id = id;
		this.question = question;
		this.pub_date = pub_date;
	}
	
	public Poll(String question, Date pub_date) {
		this.question = question;
		this.pub_date = pub_date;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public Date getPub_date() {
		return pub_date;
	}
	public void setPub_date(Date pub_date) {
		this.pub_date = pub_date;
	}
	public String toString() {
		return "Poll [id = " + id + ", question = " + question + 
				", pub_date = " + pub_date + " ]";
	}
}
