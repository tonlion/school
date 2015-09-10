package com.example.entity;

import java.io.Serializable;

public class Major implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String idEncode;
	private String name;

	public Major() {
		super();
	}

	public Major(int id, String idEncode, String name) {
		super();
		this.id = id;
		this.idEncode = idEncode;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIdEncode() {
		return idEncode;
	}

	public void setIdEncode(String idEncode) {
		this.idEncode = idEncode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
