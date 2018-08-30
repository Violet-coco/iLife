package com.example.class_24_c4_0508_01_memo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.class_24_c4_0508_01_DBUtil.DBUtil;
import com.example.class_24_c4_0508_01_MyDBHeole.Globle;
import com.example.class_24_c4_0508_01_MyDBHeole.MyDBHelper;
import com.example.class_24_c4_0508_01_Property.Property;
import com.uangel.suishouji.R;

public class MainActivity extends Activity {

	private ListView lv_show;
	private List<Property> list;
	private MyDBHelper helper;
	private MyAdapter adapter;
	private TextView tv_count;
	private int i;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_two);
		lv_show = (ListView) findViewById(R.id.lv_show);
		tv_count = (TextView) findViewById(R.id.tv_count);

		final String[] items = { "修改", "查看", "删除" };
		System.out.println(Globle.TITLE + "like '%" + "neir" + "%'");
		System.out
				.println("create table "
						+ Globle.TABLE_NAME
						+ " (_id integer primary key autoincrement,titel,caontent,set_tiem,attack_tiem)");
		list = new ArrayList<Property>();
		
		helper = new MyDBHelper(MainActivity.this, Globle.DB_NAME, null,
				Globle.VERSION);

		lv_show.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					final int arg2, long arg3) {
				// TODO Auto-generated method stub
				android.app.AlertDialog.Builder builder = new AlertDialog.Builder(
						MainActivity.this);
				builder.setTitle(list.get(arg2).getTitle());
				builder.setItems(items, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						// 长按 "修改","查看","删除"；
						switch (which) {
						case 0:
							Intent intent = new Intent(MainActivity.this,
									Update_Activity.class);
							intent.putExtra(Globle._ID, list.get(arg2).get_id());
							startActivity(intent);
							break;
						case 1:
							Intent intent1 = new Intent(MainActivity.this,
									See_Activity.class);
							intent1.putExtra(Globle._ID, list.get(arg2)
									.get_id());
							startActivity(intent1);
							break;
						case 2:
							android.app.AlertDialog.Builder builder = new AlertDialog.Builder(
									MainActivity.this);
							builder.setIcon(R.drawable.png0652);
							builder.setTitle("删除？");
							builder.setMessage("是否删除？");
							builder.setPositiveButton("是",
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											// TODO Auto-generated method stub
											delect(list.get(arg2).get_id());
											loadData();
											adapter.notifyDataSetChanged();

										}
									});
							builder.setNegativeButton("取消", null);
							builder.create().show();
							break;

						}
					}
				});
				builder.create().show();
				return false;
			}
		});
		// 查看信息
		lv_show.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this,
						See_Activity.class);
				intent.putExtra(Globle._ID, list.get(arg2).get_id());
				System.out.println("传之前的_id" + list.get(arg2).get_id());
				startActivity(intent);
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		MenuItem item1 = menu.add("添加");
		item1.setIcon(R.drawable.png0670);
		MenuItem item2 = menu.add("搜索");
		item2.setIcon(R.drawable.png_0515);
		MenuItem item3 = menu.add("排序");
		item3.setIcon(R.drawable.png_0338);
		MenuItem item4 = menu.add("删除");
		item4.setIcon(R.drawable.png0669);
		// 添加
		item1.setOnMenuItemClickListener(new OnMenuItemClickListener() {

			@Override
			public boolean onMenuItemClick(MenuItem item) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this,
						New_Activity.class);
				startActivity(intent);
				return false;
			}
		});
		// 删除
		item4.setOnMenuItemClickListener(new OnMenuItemClickListener() {

			@Override
			public boolean onMenuItemClick(MenuItem item) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this,
						Delete_Activty.class);
				startActivity(intent);
				return false;
			}
		});
		// 搜索
		item2.setOnMenuItemClickListener(new OnMenuItemClickListener() {

			@Override
			public boolean onMenuItemClick(MenuItem item) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this,
						Seek_Activity.class);
				startActivity(intent);

				return false;
			}
		});
		// 排序
		item3.setOnMenuItemClickListener(new OnMenuItemClickListener() {

			@Override
			public boolean onMenuItemClick(MenuItem item) {
				// TODO Auto-generated method stub
				android.app.AlertDialog.Builder builder = new AlertDialog.Builder(
						MainActivity.this);
				builder.setTitle("请选择排序类型");
				builder.setSingleChoiceItems(new String[] { "按设置时间排序",
						"按提醒时间排序" }, 0, new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						i = which;
					}
				});
				//排序的条件
				builder.setPositiveButton("确定", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						switch (i) {
						case 0:
							loadDataBySetTime();
							adapter.notifyDataSetChanged();
							break;
						case 1:
							loadDataByAttactTime();
							adapter.notifyDataSetChanged();
							break;

						}
					}
				});
				builder.setNegativeButton("取消", null);
				builder.create().show();
				return false;

			}
		});
		return true;
	}
	//根据设定时的时间排序（方法）
	public void loadDataBySetTime() {
		list.clear();
		SQLiteDatabase db = helper.getWritableDatabase();
		Cursor cursor = DBUtil.selectWithOrder(db, Globle.TABLE_NAME,
				new String[] { Globle._ID, Globle.TITLE, Globle.CONTENT,
						Globle.SET_ITEM, Globle.ATTACK_TIEM }, Globle.SET_ITEM);
		// 构建数据源

		while (cursor.moveToNext()) {
			Property pre = new Property(cursor.getLong(0), cursor.getString(1),
					cursor.getString(2), cursor.getLong(3), cursor.getLong(4));
			list.add(pre);
		}
		tv_count.setText("总共有  " + list.size() + " 条记录");
		cursor.close();
		db.close();
	}
	//根据触发的时间排序
	public void loadDataByAttactTime() {
		list.clear();
		SQLiteDatabase db = helper.getWritableDatabase();
		Cursor cursor = DBUtil.selectWithOrder(db, Globle.TABLE_NAME,
				new String[] { Globle._ID, Globle.TITLE, Globle.CONTENT,
						Globle.SET_ITEM, Globle.ATTACK_TIEM },
				Globle.ATTACK_TIEM);
		// 构建数据源

		while (cursor.moveToNext()) {
			Property pre = new Property(cursor.getLong(0), cursor.getString(1),
					cursor.getString(2), cursor.getLong(3), cursor.getLong(4));
			list.add(pre);
		}
		tv_count.setText("总共有  " + list.size() + " 条记录");
		cursor.close();
		db.close();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		loadData();
		adapter = new MyAdapter();
		lv_show.setAdapter(adapter);
		super.onResume();
	}
	
	public void loadData() {
		list.clear();
		SQLiteDatabase db = helper.getWritableDatabase();
		Cursor cursor = DBUtil.select(db, Globle.TABLE_NAME, new String[] {
				Globle._ID, Globle.TITLE, Globle.CONTENT, Globle.SET_ITEM,
				Globle.ATTACK_TIEM });
		while (cursor.moveToNext()) {
			Property pre = new Property(cursor.getLong(0), cursor.getString(1),
					cursor.getString(2), cursor.getLong(3), cursor.getLong(4));
			list.add(pre);
		}
		tv_count.setText("总共有  " + list.size() + " 条记录");
		cursor.close();
		db.close();
	}
	//按_id删除数据（方法）
	public void delect(long _id) {
		SQLiteDatabase db = helper.getWritableDatabase();
		String where = _id + "=" + Globle._ID;
		DBUtil.delete(db, Globle.TABLE_NAME, where);
		db.close();
	}

	class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View view = LayoutInflater.from(MainActivity.this).inflate(
					R.layout.listview, null);
			TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
			TextView tv_date = (TextView) view.findViewById(R.id.tv_date);

			tv_title.setText(list.get(position).getTitle());
			tv_date.setText(new SimpleDateFormat("yyyy/MM/dd  hh:mm")
					.format(list.get(position).getAttack_tiem()));
			return view;
		}
	}

}
