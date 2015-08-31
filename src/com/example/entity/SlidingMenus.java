package com.example.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SlidingMenus implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int itemId;
	private String itemName;
	private int menuId;
	private List<SlidingMenus> menus;

	public SlidingMenus() {
		super();
		menus = new ArrayList<SlidingMenus>();
	}

	public SlidingMenus(int itemId, String itemName, int menuId) {
		super();
		this.itemId = itemId;
		this.itemName = itemName;
		this.menuId = menuId;
		menus = new ArrayList<SlidingMenus>();
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

	public List<SlidingMenus> getMenus() {
		return menus;
	}

}
