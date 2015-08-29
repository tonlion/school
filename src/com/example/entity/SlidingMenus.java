package com.example.entity;

public class SlidingMenus {

	private int itemId;
	private String itemName;
	private int menuId;

	public SlidingMenus() {
		super();
	}

	public SlidingMenus(int itemId, String itemName, int menuId) {
		super();
		this.itemId = itemId;
		this.itemName = itemName;
		this.menuId = menuId;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public int getMenuId() {
		return menuId;
	}

	public void setMenuId(int menuId) {
		this.menuId = menuId;
	}

}
