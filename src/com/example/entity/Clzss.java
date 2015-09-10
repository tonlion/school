package com.example.entity;

import java.io.Serializable;

public class Clzss implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String className;
	private int id;
	private int majorId;
	private String majorIdEncode;
	private String majorName;
	private int yearLevel;

	public Clzss() {
		super();
	}

	public Clzss(String className, int id, int majorId, String majorIdEncode,
			String majorName, int yearLevel) {
		super();
		this.className = className;
		this.id = id;
		this.majorId = majorId;
		this.majorIdEncode = majorIdEncode;
		this.majorName = majorName;
		this.yearLevel = yearLevel;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getMajorId() {
		return majorId;
	}

	public void setMajorId(int majorId) {
		this.majorId = majorId;
	}

	public String getMajorIdEncode() {
		return majorIdEncode;
	}

	public void setMajorIdEncode(String majorIdEncode) {
		this.majorIdEncode = majorIdEncode;
	}

	public String getMajorName() {
		return majorName;
	}

	public void setMajorName(String majorName) {
		this.majorName = majorName;
	}

	public int getYearLevel() {
		return yearLevel;
	}

	public void setYearLevel(int yearLevel) {
		this.yearLevel = yearLevel;
	}

}
