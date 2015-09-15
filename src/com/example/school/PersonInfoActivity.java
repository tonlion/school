package com.example.school;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.example.application.SchoolApplication;
import com.example.dao.GlobalDao;
import com.example.dao.StudentDao;
import com.example.data.DataManager;
import com.example.data.ImageLoaderUtils;
import com.example.data.TableMessage.StudentTable;
import com.example.entity.Student;
import com.example.volley.PostRequest;

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

public class PersonInfoActivity extends Activity implements OnClickListener {
	private ImageView stuImg;
	private TextView stuInfo;
	private String photoPath;
	private Bitmap bitmap;
	private static final int TAKE_PHPTO = 1;// 拍照
	private static final int FROM_ALBUM = 2;// 相册
	private static final int RESULT = 3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_person_info);
		Student student = SchoolApplication.getInstance().getStudent();
		stuImg = (ImageView) findViewById(R.id.person_image);
		// MyAsyncTask task = new MyAsyncTask(stuImg);
		// task.execute(student.getImg());
		ImageLoaderUtils.display(student.getImg(), stuImg);
		stuImg.setOnClickListener(this);
		// 修改菜单栏
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setTitle("个人信息");
		getActionBar().setDisplayShowHomeEnabled(false);
		// 请求数据得到姓名以及其余个人信息
		stuInfo = (TextView) findViewById(R.id.person_role);
		stuInfo.setText(student.getRole());
		stuInfo = (TextView) findViewById(R.id.person_class);
		stuInfo.setText(student.getClassName());
		stuInfo = (TextView) findViewById(R.id.person_domain);
		stuInfo.setText(student.getMajorName());
		stuInfo = (TextView) findViewById(R.id.person_role);
		stuInfo.setText(student.getRole());
		stuInfo = (TextView) findViewById(R.id.person_role);
		stuInfo.setText(student.getRole());
	}

	@SuppressLint("NewApi")
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.person_image:
			View view = LayoutInflater.from(this).inflate(
					R.layout.toast_change_image, null);
			final PopupWindow window = new PopupWindow(view,
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
			// 此方法以后要提取出，因为有三个点击事件
			view.findViewById(R.id.exit).setOnClickListener(
					new OnClickListener() {
						@Override
						public void onClick(View v) {
							window.dismiss();
						}
					});
			view.findViewById(R.id.photo).setOnClickListener(
					new OnClickListener() {
						@Override
						public void onClick(View v) {
							// 跳转到照相机
							Intent intent = new Intent(
									MediaStore.ACTION_IMAGE_CAPTURE);
							// 创建存放拍照照片的位置
							File parent = com.example.data.FileUitlity
									.getInstance(getApplicationContext())
									.makeDir("head");
							// 创建照片的位置，以及照片的命名
							photoPath = parent.getPath() + File.separator
									+ System.currentTimeMillis() + ".jpg";
							intent.putExtra(MediaStore.EXTRA_OUTPUT,
									Uri.fromFile(new File(photoPath)));
							startActivityForResult(intent, TAKE_PHPTO);
						}
					});
			view.findViewById(R.id.album).setOnClickListener(
					new OnClickListener() {
						@Override
						public void onClick(View v) {
							// 跳转到相册
							Intent intent = new Intent(Intent.ACTION_PICK);
							intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
							startActivityForResult(intent, FROM_ALBUM);
						}
					});
			window.setFocusable(true);
			window.setOutsideTouchable(true);
			window.setBackgroundDrawable(new ColorDrawable());
			window.setAnimationStyle(R.style.popupstyle);
			window.showAtLocation(view, Gravity.BOTTOM, 0, 0);
			break;
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode != RESULT_OK) {
			return;
		}
		switch (requestCode) {
		case TAKE_PHPTO:
			if (photoPath != null) {
				// 得到拍照的数据
				startPhotoZoom(Uri.fromFile(new File(photoPath)));
			}
			break;
		case FROM_ALBUM:
			// 得到相册数据
			Cursor cursor = this.getContentResolver().query(data.getData(),
					new String[] { MediaStore.Images.Media.DATA }, null, null,
					null);
			cursor.moveToFirst();
			photoPath = cursor.getString(cursor
					.getColumnIndex(MediaStore.Images.Media.DATA));
			cursor.close();
			startPhotoZoom(Uri.fromFile(new File(photoPath)));
			break;
		case RESULT:
			// 得到照片剪切的数据
			Bundle bundle = data.getExtras();
			if (bundle != null) {
				bitmap = bundle.getParcelable("data");
				sendImage(convertBitMap(bitmap));
			}
			break;
		}
	}

	private void startPhotoZoom(Uri fromFile) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(fromFile, "image/*");
		// 设置裁剪
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", 150);
		intent.putExtra("outputY", 150);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, RESULT);
	}

	/**
	 * 将bitmap转换成base64位编码的字符串
	 * 
	 * @param b
	 * @return
	 */
	public String convertBitMap(Bitmap b) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		b.compress(Bitmap.CompressFormat.JPEG, 100, out);
		try {
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		byte[] te = out.toByteArray();
		byte[] tes = Base64.encode(te, Base64.DEFAULT);
		return new String(tes);
	}

	/**
	 * 上传照片到服务器
	 * 
	 * @param uriStr
	 */
	public void sendImage(String uriStr) {
		final ProgressDialog dialog = ProgressDialog.show(this, "",
				"图片上传中，请稍候......");
		PostRequest post = new PostRequest(DataManager.ROOT_URL
				+ "LoginServlet", new Listener<String>() {

			@Override
			public void onResponse(String arg0) {
				GlobalDao dao = new GlobalDao(PersonInfoActivity.this,
						StudentTable.TABLE_NAME);
				dao.updateMes(new String[] { StudentTable.COL_IMG },
						new String[] { StudentTable.COL_NUO }, new Object[] {
								arg0,
								SchoolApplication.getInstance().getStudent()
										.getUno() });
				StudentDao d = new StudentDao(PersonInfoActivity.this);
				Student s = d.AllStudent().get(0);
				SchoolApplication.getInstance().setStudent(s);
				stuImg.setImageBitmap(bitmap);
				Toast.makeText(getApplicationContext(), "上传成功",
						Toast.LENGTH_SHORT).show();
				dialog.dismiss();
			}
		}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError arg0) {
				Toast.makeText(getApplicationContext(), "联网超时，请检查之后在上传",
						Toast.LENGTH_SHORT).show();
				dialog.dismiss();
			}
		});
		Student s = SchoolApplication.getInstance().getStudent();
		post.setParams("userName", s.getUno());
		post.setParams("pwd", s.getPwd());
		post.setParams("headImage", "1");
		post.setParams("uhead", uriStr);
		SchoolApplication.getInstance().getRequestQueue().add(post);
	}
}
