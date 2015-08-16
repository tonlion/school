package com.example.entity;

public class Notice {
	private int image;
	private String tilte;
	private String createTime;

	public Notice() {
		super();
	}

	public Notice(int image, String tilte, String createTime) {
		super();
		this.image = image;
		this.tilte = tilte;
		this.createTime = createTime;
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
}
