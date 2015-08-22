package com.example.entity;

public class Notice {
	private int image;
	private String tilte;
	private String createTime;
	private int noticeType;

	public Notice() {
		super();
	}

	public Notice(int image, String tilte, String createTime) {
		super();
		this.image = image;
		this.tilte = tilte;
		this.createTime = createTime;
	}

	public Notice(int image, String tilte, String createTime, int noticeType) {
		super();
		this.image = image;
		this.tilte = tilte;
		this.createTime = createTime;
		this.noticeType = noticeType;
	}

	public int getImage() {
		return image;
	}

	public void setImage(int image) {
		this.image = image;
	}

	public String getTilte() {
		return tilte;
	}

	public void setTilte(String tilte) {
		this.tilte = tilte;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public int getNoticeType() {
		return noticeType;
	}

	public void setNoticeType(int noticeType) {
		this.noticeType = noticeType;
	}
}
