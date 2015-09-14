package com.example.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.data.TableMessage.CollectionTable;
import com.example.database.DataBaseHelper;
import com.example.entity.Notice;
import com.example.entity.Student;

public class CollectionDao {
	private DataBaseHelper helper;

	public CollectionDao(Context context) {
		super();
		helper = new DataBaseHelper(context);
	}

	/**
	 * 添加用户信息
	 * 
	 * @param user
	 */
	public void addNotice(Notice notice, Student student) throws Exception {
		SQLiteDatabase database = helper.getWritableDatabase();
		// 操作user数据库
		String insertSQL = "insert into " + CollectionTable.TABLE_NAME
				+ " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		database.execSQL(
				insertSQL,
				new Object[] { notice.getAdvertisement(), notice.getAuthor(),
						notice.getChannelType(), notice.getComment(),
						notice.getCommentcount(), notice.getContent(),
						notice.getFavor(), notice.getFavorTag(),
						notice.getId(), student.getUno(), notice.getImg(),
						notice.getMainDate(), notice.getMainpagetag(),
						notice.getMark(), notice.getMaxDate(),
						notice.getPagetag(), notice.getPagetagflag(),
						notice.getShopAddress(), notice.getShopName(),
						notice.getTag(), notice.getTime(), notice.getTitle(),
						notice.getXls() });
		database.close();
	}

	/**
	 * 全表查询
	 * 
	 * @return
	 */
	public List<Notice> allNotice() {
		return findNotice(null, null);
	}

	/**
	 * and条件查询
	 * 
	 * @param select
	 *            查询条件
	 * @param values
	 *            条件对应的值
	 * @return
	 */
	public List<Notice> findNotice(String[] select, String[] values) {
		SQLiteDatabase database = helper.getReadableDatabase();
		List<Notice> users = new ArrayList<Notice>();
		String findSQL = "select * from " + CollectionTable.TABLE_NAME;
		if (!(select == null || select.length == 0)) {
			findSQL += " where ";
			for (int i = 0; i < select.length - 1; i++) {
				findSQL += select[i] + "=? and ";
			}
			findSQL += select[select.length - 1] + "=?";
		}
		Cursor cursor = database.rawQuery(findSQL, values);
		while (cursor.moveToNext()) {
			// 取出当前行的值
			String advertisement = cursor.getString(cursor
					.getColumnIndex(CollectionTable.COL_ADVER));
			String author = cursor.getString(cursor
					.getColumnIndex(CollectionTable.COL_AUTHOR));
			String channelType = cursor.getString(cursor
					.getColumnIndex(CollectionTable.COL_CHANNEL_TYPE));
			String comment = cursor.getString(cursor
					.getColumnIndex(CollectionTable.COL_COMMENT));
			int commentcount = cursor.getInt(cursor
					.getColumnIndex(CollectionTable.COL_C_COUNT));
			String content = cursor.getString(cursor
					.getColumnIndex(CollectionTable.COL_CONTENT));
			int favor = cursor.getInt(cursor
					.getColumnIndex(CollectionTable.COL_FAVOR));
			int favorTag = cursor.getInt(cursor
					.getColumnIndex(CollectionTable.COL_F_TAG));
			int id = cursor.getInt(cursor
					.getColumnIndex(CollectionTable.COL_ID));
			String img = cursor.getString(cursor
					.getColumnIndex(CollectionTable.COL_IMG));
			String mainDate = cursor.getString(cursor
					.getColumnIndex(CollectionTable.COL_MAIN_DATE));
			String mainpagetag = cursor.getString(cursor
					.getColumnIndex(CollectionTable.COL_M_P_TAG));
			int mark = cursor.getInt(cursor
					.getColumnIndex(CollectionTable.COL_MARK));
			String maxDate = cursor.getString(cursor
					.getColumnIndex(CollectionTable.COL_MAX_DATE));
			int pagetag = cursor.getInt(cursor
					.getColumnIndex(CollectionTable.COL_PAGE_TAG));
			int pagetagflag = cursor.getInt(cursor
					.getColumnIndex(CollectionTable.COL_P_T_FLAG));
			String shopAddress = cursor.getString(cursor
					.getColumnIndex(CollectionTable.COL_SHOP_ADDRESS));
			String shopName = cursor.getString(cursor
					.getColumnIndex(CollectionTable.COL_SHOP_NAME));
			int tag = cursor.getInt(cursor
					.getColumnIndex(CollectionTable.COL_TAG));
			String time = cursor.getString(cursor
					.getColumnIndex(CollectionTable.COL_TIME));
			String title = cursor.getString(cursor
					.getColumnIndex(CollectionTable.COL_TITLE));
			String xls = cursor.getString(cursor
					.getColumnIndex(CollectionTable.COL_XLS));
			users.add(new Notice(advertisement, author, channelType, comment,
					commentcount, content, favor, favorTag, id, img, mainDate,
					mainpagetag, mark, maxDate, pagetag, pagetagflag,
					shopAddress, shopName, tag, time, title, xls));
		}
		cursor.close();
		database.close();
		return users;
	}
}
