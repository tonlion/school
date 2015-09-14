package com.example.data;

public class TableMessage {

	public static class StudentTable {
		public static final String TABLE_NAME = "student";
		public static final String COL_NUO = "uno";
		public static final String COL_STU_NAME = "stuName";
		public static final String COL_MAJOR_NO = "majorNO";
		public static final String COL_CLASS_NO = "classNO";
		public static final String COL_MAJOR_NAME = "majorName";
		public static final String COL_CLASS_NAME = "className";
		public static final String COL_ROLE = "role";
		public static final String COL_PWD = "pwd";
		public static final String COL_IMG = "img";
		public static final String COL_ISREAD = "isRead";
		public static final String COL_SMSRIGHT = "smsright";

		public static String createTable() {
			StringBuilder builder = new StringBuilder();
			builder.append("create table if not exists ");
			builder.append(TABLE_NAME);
			builder.append("(");
			builder.append(COL_NUO + " varchar(20) not null primary key,");
			builder.append(COL_STU_NAME + " varchar(20) null,");
			builder.append(COL_MAJOR_NO + " int(11) null,");
			builder.append(COL_CLASS_NO + " int(11) null,");
			builder.append(COL_MAJOR_NAME + " varchar(30) null,");
			builder.append(COL_CLASS_NAME + " varchar(30) null,");
			builder.append(COL_ROLE + " varchar(20) null,");
			builder.append(COL_PWD + " varchar(100) null default 111111,");
			builder.append(COL_IMG + " varchar(100) null,");
			builder.append(COL_ISREAD + " int(1) null default 0,");
			builder.append(COL_SMSRIGHT + " varchar(10) null default 0");
			builder.append(")");
			return builder.toString();
		}
	}

	public static class NoticeTable {
		public static final String TABLE_NAME = "notice";
		public static final String COL_ADVER = "advertisement";
		public static final String COL_AUTHOR = "author";
		public static final String COL_CHANNEL_TYPE = "channelType";
		public static final String COL_COMMENT = "comment";
		public static final String COL_C_COUNT = "commentcount";
		public static final String COL_CONTENT = "content";
		public static final String COL_FAVOR = "favor";
		public static final String COL_F_TAG = "favorTag";
		public static final String COL_ID = "id";
		public static final String COL_IMG = "img";
		public static final String COL_MAIN_DATE = "mainDate";
		public static final String COL_M_P_TAG = "mainpagetag";
		public static final String COL_MARK = "mark";
		public static final String COL_MAX_DATE = "maxDate";
		public static final String COL_PAGE_TAG = "pagetag";
		public static final String COL_P_T_FLAG = "pagetagflag";
		public static final String COL_SHOP_ADDRESS = "shopAddress";
		public static final String COL_SHOP_NAME = "shopName";
		public static final String COL_TAG = "tag";
		public static final String COL_TIME = "time";
		public static final String COL_TITLE = "title";
		public static final String COL_XLS = "xls";

		public static String createTable() {
			StringBuilder builder = new StringBuilder();
			builder.append("create table if not exists ");
			builder.append(TABLE_NAME);
			builder.append("(");
			builder.append(COL_ADVER + " varchar(20) not null,");
			builder.append(COL_AUTHOR + " varchar(20) null,");
			builder.append(COL_CHANNEL_TYPE + " varchar(20) null,");
			builder.append(COL_COMMENT + " varchar(20) null,");
			builder.append(COL_C_COUNT + " int(10) null,");
			builder.append(COL_CONTENT + " text null,");
			builder.append(COL_FAVOR + " int(10) null,");
			builder.append(COL_F_TAG + " int(10) null,");
			builder.append(COL_ID
					+ " integer not null primary key autoincrement,");
			builder.append(COL_IMG + " varchar(100) null,");
			builder.append(COL_MAIN_DATE + " varchar(20) null,");
			builder.append(COL_M_P_TAG + " varchar(20) null,");
			builder.append(COL_MARK + " int(10) null,");
			builder.append(COL_MAX_DATE + " varchar(20) null,");
			builder.append(COL_PAGE_TAG + " int(10) null,");
			builder.append(COL_P_T_FLAG + " int(10) null,");
			builder.append(COL_SHOP_ADDRESS + " varchar(100) null,");
			builder.append(COL_SHOP_NAME + " varchar(20) null,");
			builder.append(COL_TAG + " int(10) null,");
			builder.append(COL_TIME + " varchar(20) null,");
			builder.append(COL_TITLE + " varchar(100) null,");
			builder.append(COL_XLS + " varchar(100) null");
			builder.append(")");
			return builder.toString();
		}
	}

	public static class TopicTable {
		public static final String TABLE_NAME = "topic";
		public static final String COL_S_DATA = "subject_date";
		public static final String COL_S_DETAIL = "subject_detail";
		public static final String COL_S_ID = "subject_id";
		public static final String COL_S_TITLE = "subject_title";
		public static final String COL_S_URL = "subject_url";

		public static String createTable() {
			StringBuilder builder = new StringBuilder();
			builder.append("create table if not exists ");
			builder.append(TABLE_NAME);
			builder.append("(");
			builder.append(COL_S_DATA + " date null,");
			builder.append(COL_S_DETAIL + " varchar(1000) null,");
			builder.append(COL_S_ID
					+ " integer not null primary key autoincrement,");
			builder.append(COL_S_TITLE + " varchar(1000) null,");
			builder.append(COL_S_URL + " varchar(1000) null");
			builder.append(")");
			return builder.toString();
		}
	}

	public static class CollectionTable {
		public static final String TABLE_NAME = "collect";
		public static final String COL_UNO = "uno";
		public static final String COL_ADVER = "advertisement";
		public static final String COL_AUTHOR = "author";
		public static final String COL_CHANNEL_TYPE = "channelType";
		public static final String COL_COMMENT = "comment";
		public static final String COL_C_COUNT = "commentcount";
		public static final String COL_CONTENT = "content";
		public static final String COL_FAVOR = "favor";
		public static final String COL_F_TAG = "favorTag";
		public static final String COL_ID = "id";
		public static final String COL_IMG = "img";
		public static final String COL_MAIN_DATE = "mainDate";
		public static final String COL_M_P_TAG = "mainpagetag";
		public static final String COL_MARK = "mark";
		public static final String COL_MAX_DATE = "maxDate";
		public static final String COL_PAGE_TAG = "pagetag";
		public static final String COL_P_T_FLAG = "pagetagflag";
		public static final String COL_SHOP_ADDRESS = "shopAddress";
		public static final String COL_SHOP_NAME = "shopName";
		public static final String COL_TAG = "tag";
		public static final String COL_TIME = "time";
		public static final String COL_TITLE = "title";
		public static final String COL_XLS = "xls";

		public static String createTable() {
			StringBuilder builder = new StringBuilder();
			builder.append("create table if not exists ");
			builder.append(TABLE_NAME);
			builder.append("(");
			builder.append(COL_ADVER + " varchar(20) not null,");
			builder.append(COL_AUTHOR + " varchar(20) null,");
			builder.append(COL_CHANNEL_TYPE + " varchar(20) null,");
			builder.append(COL_COMMENT + " varchar(20) null,");
			builder.append(COL_C_COUNT + " int(10) null,");
			builder.append(COL_CONTENT + " text null,");
			builder.append(COL_FAVOR + " int(10) null,");
			builder.append(COL_F_TAG + " int(10) null,");
			builder.append(COL_ID + " integer not null,");
			builder.append(COL_UNO + " varchar(10) not null,");
			builder.append(COL_IMG + " varchar(100) null,");
			builder.append(COL_MAIN_DATE + " varchar(20) null,");
			builder.append(COL_M_P_TAG + " varchar(20) null,");
			builder.append(COL_MARK + " int(10) null,");
			builder.append(COL_MAX_DATE + " varchar(20) null,");
			builder.append(COL_PAGE_TAG + " int(10) null,");
			builder.append(COL_P_T_FLAG + " int(10) null,");
			builder.append(COL_SHOP_ADDRESS + " varchar(100) null,");
			builder.append(COL_SHOP_NAME + " varchar(20) null,");
			builder.append(COL_TAG + " int(10) null,");
			builder.append(COL_TIME + " varchar(20) null,");
			builder.append(COL_TITLE + " varchar(100) null,");
			builder.append(COL_XLS + " varchar(100) null,");
			builder.append("primary key(");
			builder.append(COL_UNO + ",");
			builder.append(COL_ID);
			builder.append(")");
			builder.append(")");
			return builder.toString();
		}
	}
}
