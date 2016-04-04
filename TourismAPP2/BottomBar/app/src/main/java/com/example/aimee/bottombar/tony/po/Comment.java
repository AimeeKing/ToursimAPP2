package com.example.aimee.bottombar.tony.po;

public class Comment {
	private String comment_id;
	private String publisher_id;
	private String content;
	private String pub_time;
	private String to_id;
	private String type;
	private int type_flag;
	private String reply_id;
	
	public String getReply_id() {
		return reply_id;
	}
	public void setReply_id(String reply_id) {
		this.reply_id = reply_id;
	}
	public String getComment_id() {
		return comment_id;
	}
	public void setComment_id(String comment_id) {
		this.comment_id = comment_id;
	}
	public String getPublisher_id() {
		return publisher_id;
	}
	public void setPublisher_id(String publisher_id) {
		this.publisher_id = publisher_id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getPub_time() {
		return pub_time;
	}
	public void setPub_time(String pub_time) {
		this.pub_time = pub_time;
	}
	public String getTo_id() {
		return to_id;
	}
	public void setTo_id(String to_id) {
		this.to_id = to_id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getType_flag() {
		return type_flag;
	}
	public void setType_flag(int type_flag) {
		this.type_flag = type_flag;
	}
	
	
}
