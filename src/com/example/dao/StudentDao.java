package com.example.dao;

import java.util.ArrayList;
import java.util.List;

import com.example.data.TableMessage.StudentTable;
import com.example.database.DataBaseHelper;
import com.example.entity.Student;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class StudentDao {
	private DataBaseHelper helper;
	private Context context;

	public StudentDao(Context context) {
		super();
		helper = new DataBaseHelper(context);
		this.context = context;
	}

	/**
	 * ����û���Ϣ
	 * 
	 * @param user
	 */
	public void addUser(Student student) {
		SQLiteDatabase database = helper.getWritableDatabase();
		// ����user���ݿ�
		String insertSQL = "insert into " + StudentTable.TABLE_NAME
				+ " values(?,?,?,?,?,?,?,?,?,?,?)";
		try {
			database.execSQL(
					insertSQL,
					new Object[] { student.getUno(), student.getStuName(),
							student.getMajorNO(), student.getClassNO(),
							student.getMajorName(), student.getClassName(),
							student.getRole(), student.getPwd(),
							student.getImg(), student.getIsRead(),
							student.getSmsright() });
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
	public List<Student> AllStudent() {
		return findStudent(null, null);
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
	public List<Student> findStudent(String[] select, String[] values) {
		SQLiteDatabase database = helper.getReadableDatabase();
		List<Student> users = new ArrayList<Student>();
		String findSQL = "select * from " + StudentTable.TABLE_NAME;
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
			String uno = cursor.getString(cursor
					.getColumnIndex(StudentTable.COL_NUO));
			String stuName = cursor.getString(cursor
					.getColumnIndex(StudentTable.COL_STU_NAME));
			int majorNo = cursor.getInt(cursor
					.getColumnIndex(StudentTable.COL_MAJOR_NO));
			int classNo = cursor.getInt(cursor
					.getColumnIndex(StudentTable.COL_CLASS_NO));
			String majorName = cursor.getString(cursor
					.getColumnIndex(StudentTable.COL_MAJOR_NAME));
			String className = cursor.getString(cursor
					.getColumnIndex(StudentTable.COL_CLASS_NAME));
			String role = cursor.getString(cursor
					.getColumnIndex(StudentTable.COL_ROLE));
			String pwd = cursor.getString(cursor
					.getColumnIndex(StudentTable.COL_PWD));
			String img = cursor.getString(cursor
					.getColumnIndex(StudentTable.COL_IMG));
			int isRead = cursor.getInt(cursor
					.getColumnIndex(StudentTable.COL_ISREAD));
			String smsRight = cursor.getString(cursor
					.getColumnIndex(StudentTable.COL_SMSRIGHT));
			users.add(new Student(uno, stuName, majorNo, classNo, majorName,
					className, role, pwd, img, isRead, smsRight));
		}
		cursor.close();
		database.close();
		return users;
	}

	/**
	 * ͨ��UNO�õ�ָ���û�
	 * 
	 * @param student
	 * @return
	 */
	public List<Student> findByUno(String unoStr) {
		return findStudent(new String[] { StudentTable.COL_NUO },
				new String[] { unoStr });
	}

	/**
	 * ���»����
	 * 
	 * @param student
	 */
	public void saveOrUpdate(Student student) {
		GlobalDao dao = new GlobalDao(context, "student");
		if (!(findByUno(student.getUno()).size() == 0 || findByUno(student.getUno()) == null)) {
			dao.deleteUser(new String[] { StudentTable.COL_NUO },
					new Object[] { student.getUno() });
		}
		addUser(student);
	}
}
