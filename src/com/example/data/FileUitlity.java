package com.example.data;

import java.io.File;

import android.content.Context;
import android.os.Environment;

public class FileUitlity {

	public final static String USER_HAED = "head";

	private static String ROOT_CACHE;
	public static String ROOT_DIR = "xyt";// ����ĸ�Ŀ¼
	private static FileUitlity instance = null;

	private FileUitlity() {
	}

	public static FileUitlity getInstance(Context context) {
		if (instance == null) {
			if (Environment.getExternalStorageState().equals(
					Environment.MEDIA_MOUNTED)) {
				ROOT_CACHE = (Environment.getExternalStorageDirectory() + "/"
						+ ROOT_DIR + "/");
			} else {
				ROOT_CACHE = (context.getFilesDir().getAbsolutePath() + "/"
						+ ROOT_DIR + "/");
			}
			File dir = new File(ROOT_CACHE);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			instance = new FileUitlity();
		}
		return instance;
	}

	public File makeDir(String dir) {
		File fileDir = new File(ROOT_CACHE + dir);
		if (fileDir.exists()) {
			return fileDir;
		} else {
			fileDir.mkdirs();
			return fileDir;
		}
	}

}
