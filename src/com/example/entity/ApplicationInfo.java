package com.example.entity;

public class ApplicationInfo {

	private int versionCode;
	private String versionName;
	private String versionUrl;

	public ApplicationInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ApplicationInfo(int versionCode, String versionName,
			String versionUrl) {
		super();
		this.versionCode = versionCode;
		this.versionName = versionName;
		this.versionUrl = versionUrl;
	}

	public int getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(int versionCode) {
		this.versionCode = versionCode;
	}

	public String getVersionName() {
		return versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

	public String getVersionUrl() {
		return versionUrl;
	}

	public void setVersionUrl(String versionUrl) {
		this.versionUrl = versionUrl;
	}
}
