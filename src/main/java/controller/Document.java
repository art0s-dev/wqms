package controller;

import java.util.List;

public class Document {
	private String body;
	private String title;
	private String description;
	private List<String> keywords;
	
	public String getBody() { return this.body; }
	void setBody(String body) { this.body = body; }
	public String getTitle() { return this.title; }
	void setTitle(String title) { this.title = title; }
	public String getDescription() { return this.description; }
	void setDescription(String description) { this.description = description; }
	public List<String> getKeywords(){ return this.keywords; }
	void setKeywords(List<String> keywords) { this.keywords = keywords; }
}
