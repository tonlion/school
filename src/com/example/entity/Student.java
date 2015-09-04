package com.example.entity;

import java.io.Serializable;

public class Student implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String uno;
	private String stuName;
	private String majorNO;
	private String classNO;
	private String majorName;
	private String className;
	private String role;
	private String pwd;
	private String img;
	private int isRead;
	private String smsright;

	public Student() {
		super();
	}

	public Student(String uno, String stuName, String majorNO, String classNO,
			String majorName, String className, String role, String pwd,
			String img, int isRead, String smsright) {
		super();
		this.uno = uno;
		this.stuName = stuName;
		this.majorNO = majorNO;
		this.classNO = classNO;
		this.majorName = majorName;
		this.className = className;
		this.role = role;
		this.pwd = pwd;
		this.img = img;
		this.isRead = isRead;
		this.smsright = smsright;
	}

	public String getUno() {
		return uno;
	}

	public void setUno(String uno) {
		this.uno = uno;
	}

	public String getStuName() {
		return stuName;
	}

	public void setStuName(String stuName) {
		this.stuName = stuName;
	}

	public String getMajorNO() {
		return majorNO;
	}

	public void setMajorNO(String majorNO) {
		this.majorNO = majorNO;
	}

	public String getClassNO() {
		return classNO;
	}

	public void setClassNO(String classNO) {
		this.classNO = classNO;
	}

	public String getMajorName() {
		return majorName;
	}

	public void setMajorName(String majorName) {
		this.majorName = majorName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public int getIsRead() {
		return isRead;
	}

	public void setIsRead(int isRead) {
		this.isRead = isRead;
	}

	public String getSmsright() {
		return smsright;
	}

	public void setSmsright(String smsright) {
		this.smsright = smsright;
	}

}
