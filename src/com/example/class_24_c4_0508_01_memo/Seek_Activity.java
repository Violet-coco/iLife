package com.example.class_24_c4_0508_01_memo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.class_24_c4_0508_01_DBUtil.DBUtil;
import com.example.class_24_c4_0508_01_MyDBHeole.Globle;
import com.example.class_24_c4_0508_01_MyDBHeole.MyDBHelper;
import com.example.class_24_c4_0508_01_Property.Property;
import com.uangel.suishouji.R;

public class Seek_Activity extends Activity{

	private Button but_01;
	private Button but_02;
	private ImageButton ib_soso;
	private TextView tv_show,tv_show1,tv_count1;
	private ListView lv_02;
	private EditText et_tags;
	private RadioGroup rg01;
	private MyDBHelper helper;
	List<Property> list=new ArrayList<Property>();
	private MyAdapter adapter;
	private Calendar calendar;
	private Calendar calendar1;
	private String da;
	private String tiem;
	private long l;
	private long o;
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.seek);
		but_01=(Button) findViewById(R.id.but_01);
		but_02=(Button) findViewById(R.id.but_02);
		lv_02=(ListView) findViewById(R.id.lv_01);
		ib_soso=(ImageButton) findViewById(R.id.ib_soso);
		tv_show=(TextView) findViewById(R.id.tv_show);
		tv_show1=(TextView) findViewById(R.id.tv_show1);
		et_tags=(EditText) findViewById(R.id.et_tags);
		rg01=(RadioGroup) findViewById(R.id.rg01);
		tv_count1=(TextView) findViewById(R.id.tv_count1);
		
		//查看；
		lv_02.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(Seek_Activity.this,See_Activity.class);
				intent.putExtra(Globle._ID,list.get(arg2).get_id());
				System.out.println("seek_id"+list.get(arg2).get_id());
				startActivity(intent);
			}
		});
		//修改：
		lv_02.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(Seek_Activity.this,Update_Activity.class);
				intent.putExtra(Globle._ID,list.get(arg2).get_id());
				startActivity(intent);
				return false;
			}
		});
		
		final Calendar c=Calendar.getInstance();
		calendar=Calendar.getInstance();
		calendar1=Calendar.getInstance();
		
		helper=new MyDBHelper(this,Globle.DB_NAME,null,Globle.VERSION);
		
		ib_soso.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				list.clear();
				String content=et_tags.getText().toString();
				SQLiteDatabase db=helper.getWritableDatabase();
				String where=Globle.TITLE+" like '%"+content+"%'";
				
				Cursor cursor=DBUtil.select(db, Globle.TABLE_NAME,new String[]{Globle._ID,Globle.TITLE,Globle.CONTENT,Globle.SET_ITEM,Globle.ATTACK_TIEM}, where);
				while(cursor.moveToNext()){
					Property pre=new Property(cursor.getLong(0),cursor.getString(1),cursor.getString(2),cursor.getLong(3),cursor.getLong(4));
					list.add(pre);
				}
				tv_count1.setText("共有  "+list.size()+" 条搜索结果");
				System.out.println("wwwww");
				cursor.close();
				db.close();
				adapter=new MyAdapter(); 
				lv_02.setAdapter(adapter);
			} 
		});
		but_01.setOnClickListener(new OnClickListener() {
			 
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				tv_show.setText("");
DatePickerDialog dialog=new DatePickerDialog(Seek_Activity.this,new OnDateSetListener() {
					
					@Override
					public void onDateSet(DatePicker view, int year, int monthOfYear,
							int dayOfMonth) {
						// TODO Auto-generated method stub
						//把用户设置的时间存储
						
						calendar.set(Calendar.YEAR,year);
						calendar.set(Calendar.MONTH,monthOfYear);
						calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
//						date=year+"/"+monthOfYear+"/"+dayOfMonth;
						TimePickerDialog time_dialog=new TimePickerDialog(Seek_Activity.this,new OnTimeSetListener() {
							
							@Override
							public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
								// TODO Auto-generated method stub
								calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
								calendar.set(Calendar.MINUTE,minute);
								calendar.set(Calendar.SECOND,0);
								da=new SimpleDateFormat("yyyy/MM/dd  hh:mm").format(calendar.getTimeInMillis());
								tv_show.setText(da);
							}
						},c.get(Calendar.HOUR_OF_DAY),c.get(Calendar.MINUTE),true);
						time_dialog.show();
						
					}
				}, c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH));
				dialog.show();
				
			}
		});
		but_02.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				tv_show1.setText("");
DatePickerDialog dialog=new DatePickerDialog(Seek_Activity.this,new OnDateSetListener() {
					
					@Override
					public void onDateSet(DatePicker view, int year, int monthOfYear,
							int dayOfMonth) {
						// TODO Auto-generated method stub
						//把用户设置的时间存储
						calendar1.set(Calendar.YEAR,year);
						calendar1.set(Calendar.MONTH,monthOfYear);
						calendar1.set(Calendar.DAY_OF_MONTH,dayOfMonth);
//						date=year+"/"+monthOfYear+"/"+dayOfMonth;
						TimePickerDialog time_dialog=new TimePickerDialog(Seek_Activity.this,new OnTimeSetListener() {
							
							@Override
							public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
								// TODO Auto-generated method stub
								calendar1.set(Calendar.HOUR_OF_DAY,hourOfDay);
								calendar1.set(Calendar.MINUTE,minute);
								calendar1.set(Calendar.SECOND,0);
								tiem=new SimpleDateFormat("yyyy/MM/dd  hh:mm").format(calendar1.getTimeInMillis());
								tv_show1.setText(tiem);
							}
						},c.get(Calendar.HOUR_OF_DAY),c.get(Calendar.MINUTE),true);
						time_dialog.show();
						
					}
				}, c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DATE));
				dialog.show();
			}
		});
	}
	
	class MyAdapter extends BaseAdapter{

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
			View view=LayoutInflater.from(Seek_Activity.this).inflate(R.layout.seek1,null);
			TextView tv_title=(TextView) view.findViewById(R.id.tv_title1);
			TextView tv_date=(TextView) view.findViewById(R.id.tv_date2);
			
			tv_title.setText(list.get(position).getTitle());
			tv_date.setText(new SimpleDateFormat("yyyy/MM/dd  hh:mm").format(list.get(position).getAttack_tiem()));
			return view;
		}
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuItem item1=menu.add("搜索");
		MenuItem item2=menu.add("取消");
		
		item1.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				// TODO Auto-generated method stub
	  
				int id=rg01.getCheckedRadioButtonId();
				list.clear();
				SQLiteDatabase db=helper.getWritableDatabase();
				l=calendar.getTimeInMillis();
				o=calendar1.getTimeInMillis();
				switch(id){
					case R.id.rb01:
				if(l<=o){
					String where=Globle.SET_ITEM+" >= "+l+" and "+Globle.SET_ITEM+" <= "+o;
					Cursor cursor=DBUtil.select(db, Globle.TABLE_NAME,new String[]{Globle._ID,Globle.TITLE,Globle.CONTENT,Globle.SET_ITEM,Globle.ATTACK_TIEM},where);
					while(cursor.moveToNext()){
						Property pre=new Property(cursor.getLong(0),cursor.getString(1),cursor.getString(2),cursor.getLong(3),cursor.getLong(4));
						list.add(pre);
					}
					cursor.close();
			}else{
					String where=Globle.SET_ITEM+" <= "+l+" and "+Globle.SET_ITEM+" >= "+o;
					Cursor cursor=DBUtil.select(db, Globle.TABLE_NAME,new String[]{Globle._ID,Globle.TITLE,Globle.CONTENT,Globle.SET_ITEM,Globle.ATTACK_TIEM},where);
					while(cursor.moveToNext()){
						Property pre=new Property(cursor.getLong(0),cursor.getString(1),cursor.getString(2),cursor.getLong(3),cursor.getLong(4));
						list.add(pre);
				}
					tv_count1.setText("共有  "+list.size()+" 条搜索结果");
					cursor.close();
					
				}
					db.close();
				adapter=new MyAdapter();
				lv_02.setAdapter(adapter);break;
					case R.id.rb02:	
						if(l<=o){
						String where=Globle.ATTACK_TIEM+" >= "+l+" and "+Globle.ATTACK_TIEM+" <= "+o;
						Cursor cursor=DBUtil.select(db, Globle.TABLE_NAME,new String[]{Globle._ID,Globle.TITLE,Globle.CONTENT,Globle.SET_ITEM,Globle.ATTACK_TIEM},where);
						while(cursor.moveToNext()){
							Property pre=new Property(cursor.getLong(0),cursor.getString(1),cursor.getString(2),cursor.getLong(3),cursor.getLong(4));
							list.add(pre);
						}
						cursor.close();
				}else{
						String where=Globle.ATTACK_TIEM+" <= "+l+" and "+Globle.ATTACK_TIEM+" >= "+o;
						Cursor cursor=DBUtil.select(db, Globle.TABLE_NAME,new String[]{Globle._ID,Globle.TITLE,Globle.CONTENT,Globle.SET_ITEM,Globle.ATTACK_TIEM},where);
						while(cursor.moveToNext()){
							Property pre=new Property(cursor.getLong(0),cursor.getString(1),cursor.getString(2),cursor.getLong(3),cursor.getLong(4));
							list.add(pre);
					}
						cursor.close();
						
					}
						db.close();
					adapter=new MyAdapter();
					lv_02.setAdapter(adapter);break;
				}
				return false;
			
			}
		});
		return super.onCreateOptionsMenu(menu);
	}
	public void loadData(){
		list.clear();
		SQLiteDatabase db=helper.getWritableDatabase();
		Cursor cursor=DBUtil.select(db,Globle.TABLE_NAME, new String[]{Globle._ID,Globle.TITLE,Globle.CONTENT,Globle.SET_ITEM,Globle.ATTACK_TIEM});
		while(cursor.moveToNext()){
			Property pre=new Property(cursor.getLong(0),cursor.getString(1),cursor.getString(2),cursor.getLong(3),cursor.getLong(4));
			list.add(pre);
		}
		tv_count1.setText("共有  "+list.size()+" 条搜索结果");
		cursor.close();
		db.close();
	}
} 
	
