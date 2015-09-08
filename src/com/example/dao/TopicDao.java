package com.example.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.data.TableMessage.TopicTable;
import com.example.database.DataBaseHelper;
import com.example.entity.Topic;

public class TopicDao {
	private DataBaseHelper helper;

	public TopicDao(Context context) {
		super();
		helper = new DataBaseHelper(context);
	}

	/**
	 * ��Ӷ�����¼
	 * 
	 * @param topics
	 */
	public void addMoreTopic(List<Topic> topics) {
		for (Topic t : topics) {
			addTopic(t);
		}
	}

	/**
	 * ����û���Ϣ
	 * 
	 * @param user
	 */
	public void addTopic(Topic topic) {
		SQLiteDatabase database = helper.getWritableDatabase();
		// ����user���ݿ�
		String insertSQL = "insert into " + TopicTable.TABLE_NAME
				+ " values(?,?,?,?,?)";
		try {
			database.execSQL(
					insertSQL,
					new Object[] { topic.getSubject_date(),
							topic.getSubject_detail(), topic.getSubject_id(),
							topic.getSubject_title(), topic.getSubject_url() });
		} catch (Exception e) {
			e.printStackTrace();
		}
		database.close();
	}

	/**
	 * ȫ���ѯ
	 * 
	 * @return
	 */
	public List<Topic> allTopic() {
		return findTopic(null, null);
	}

	/**
	 * and������ѯ
	 * 
	 * @param select
	 *            ��ѯ����
	 * @param values
	 *            ������Ӧ��ֵ
	 * @return
	 */
	public List<Topic> findTopic(String[] select, String[] values) {
		SQLiteDatabase database = helper.getReadableDatabase();
		List<Topic> users = new ArrayList<Topic>();
		String findSQL = "select * from " + TopicTable.TABLE_NAME;
		if (!(select == null || select.length == 0)) {
			findSQL += " where ";
			for (int i = 0; i < select.length - 1; i++) {
				findSQL += select[i] + "=? and ";
			}
			findSQL += select[select.length - 1] + "=?";
		}
		Cursor cursor = database.rawQuery(findSQL, values);
		while (cursor.moveToNext()) {
			// ȡ����ǰ�е�ֵ
			String subjectData = cursor.getString(cursor
					.getColumnIndex(TopicTable.COL_S_DATA));
			String subjectDetail = cursor.getString(cursor
					.getColumnIndex(TopicTable.COL_S_DETAIL));
			int subjectId = cursor.getInt(cursor
					.getColumnIndex(TopicTable.COL_S_ID));
			String subjectTitle = cursor.getString(cursor
					.getColumnIndex(TopicTable.COL_S_TITLE));
			String subjectUrl = cursor.getString(cursor
					.getColumnIndex(TopicTable.COL_S_URL));
			users.add(new Topic(subjectData, subjectDetail, subjectId,
					subjectTitle, subjectUrl));
		}
		cursor.close();
		database.close();
		return users;
	}
}
