package com.example.entity;

public class Topic {

	private int image;
	private String title;
	private String content;

	public Topic() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Topic(int image, String title, String content) {
		super();
		this.image = image;
		this.title = title;
		this.content = content;
	}

	public int getImage() {
		return image;
	}

	public void setImage(int image) {
		this.image = image;
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

}
