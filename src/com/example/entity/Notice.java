package com.example.entity;

import java.io.Serializable;

public class Notice implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String advertisement;
	private String author;
	private String channelType;
	private String comment;
	private int commentcount;
	private String content;
	private int favor;
	private int favorTag;
	private int id;
	private String img;
	private String mainDate;
	private String mainpagetag;
	private int mark;
	private String maxDate;
	private int pagetag;
	private int pagetagflag;
	private String shopAddress;
	private String shopName;
	private int tag;
	private String time;
	private String title;
	private String xls;

	public Notice() {
		super();
	}

	public Notice(String advertisement, String author, String channelType,
			String comment, int commentcount, String content, int favor,
			int favorTag, int id, String img, String mainDate,
			String mainpagetag, int mark, String maxDate, int pagetag,
			int pagetagflag, String shopAddress, String shopName, int tag,
			String time, String title, String xls) {
		super();
		this.advertisement = advertisement;
		this.author = author;
		this.channelType = channelType;
		this.comment = comment;
		this.commentcount = commentcount;
		this.content = content;
		this.favor = favor;
		this.favorTag = favorTag;
		this.id = id;
		this.img = img;
		this.mainDate = mainDate;
		this.mainpagetag = mainpagetag;
		this.mark = mark;
		this.maxDate = maxDate;
		this.pagetag = pagetag;
		this.pagetagflag = pagetagflag;
		this.shopAddress = shopAddress;
		this.shopName = shopName;
		this.tag = tag;
		this.time = time;
		this.title = title;
		this.xls = xls;
	}

	public String getAdvertisement() {
		return advertisement;
	}

	public void setAdvertisement(String advertisement) {
		this.advertisement = advertisement;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getChannelType() {
		return channelType;
	}

	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public int getCommentcount() {
		return commentcount;
	}

	public void setCommentcount(int commentcount) {
		this.commentcount = commentcount;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getFavor() {
		return favor;
	}

	public void setFavor(int favor) {
		this.favor = favor;
	}

	public int getFavorTag() {
		return favorTag;
	}

	public void setFavorTag(int favorTag) {
		this.favorTag = favorTag;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getMainDate() {
		return mainDate;
	}

	public void setMainDate(String mainDate) {
		this.mainDate = mainDate;
	}

	public String getMainpagetag() {
		return mainpagetag;
	}

	public void setMainpagetag(String mainpagetag) {
		this.mainpagetag = mainpagetag;
	}

	public int getMark() {
		return mark;
	}

	public void setMark(int mark) {
		this.mark = mark;
	}

	public String getMaxDate() {
		return maxDate;
	}

	public void setMaxDate(String maxDate) {
		this.maxDate = maxDate;
	}

	public int getPagetag() {
		return pagetag;
	}

	public void setPagetag(int pagetag) {
		this.pagetag = pagetag;
	}

	public int getPagetagflag() {
		return pagetagflag;
	}

	public void setPagetagflag(int pagetagflag) {
		this.pagetagflag = pagetagflag;
	}

	public String getShopAddress() {
		return shopAddress;
	}

	public void setShopAddress(String shopAddress) {
		this.shopAddress = shopAddress;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public int getTag() {
		return tag;
	}

	public void setTag(int tag) {
		this.tag = tag;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getXls() {
		return xls;
	}

	public void setXls(String xls) {
		this.xls = xls;
	}
}
