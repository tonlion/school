package com.example.entity;

public class Contection {

	private int id;
	private String itemValue;
	private String type;

	public Contection() {
		super();
	}

	public Contection(int id, String itemValue, String type) {
		super();
		this.id = id;
		this.itemValue = itemValue;
		this.type = type;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getItemValue() {
		return itemValue;
	}

	public void setItemValue(String itemValue) {
		this.itemValue = itemValue;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
