package com.example.model;

public class EmailBody {

	private String username;
	private String toMail;
	private String subject;
	private String body;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getToMail() {
		return toMail;
	}
	public void setToMail(String toMail) {
		this.toMail = toMail;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public EmailBody(String username, String toMail, String subject, String body) {
		super();
		this.username = username;
		this.toMail = toMail;
		this.subject = subject;
		this.body = body;
	}
	public EmailBody() {
		super();
	}
	
	
}

//package com.example.model;
//
//public class EmailBody {
//
//	private String username;
//	private String toMail;
//	private String subject;
//	private String body;
//	public String getUsername() {
//		return username;
//	}
//	public void setUsername(String username) {
//		this.username = username;
//	}
//	public String getToMail() {
//		return toMail;
//	}
//	public void setToMail(String toMail) {
//		this.toMail = toMail;
//	}
//	public String getSubject() {
//		return subject;
//	}
//	public void setSubject(String subject) {
//		this.subject = subject;
//	}
//	public String getBody() {
//		return body;
//	}
//	public void setBody(String body) {
//		this.body = body;
//	}
//	public EmailBody(String username, String toMail, String subject, String body) {
//		super();
//		this.username = username;
//		this.toMail = toMail;
//		this.subject = subject;
//		this.body = body;
//	}
//	public EmailBody() {
//		super();
//	}
//	
//	
//}