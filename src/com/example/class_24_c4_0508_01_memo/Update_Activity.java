package com.example.class_24_c4_0508_01_memo;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.example.class_24_c4_0508_01_DBUtil.DBUtil;
import com.example.class_24_c4_0508_01_MyDBHeole.Globle;
import com.example.class_24_c4_0508_01_MyDBHeole.MyDBHelper;
import com.uangel.suishouji.R;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class Update_Activity extends Activity{

	private EditText et_title;
	private EditText et_content;
	private TextView tiem;
	private TextView tv_tiem;
	private Button but_set;
	private String set_date="";
	private String date="";
	private Calendar calendar;
	private MyDBHelper helper;
	private long _id;
	private int count=0;
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.update);
		setContentView(R.layout.new_activity);
		et_title=(EditText) findViewById(R.id.et_title);
		et_content=(EditText) findViewById(R.id.et_content);
		tiem=(TextView) findViewById(R.id.tiem);
		tv_tiem=(TextView) findViewById(R.id.tv_tiem);
		but_set=(Button) findViewById(R.id.but_set);
	
	final Calendar c=Calendar.getInstance();
			
			calendar=Calendar.getInstance();
			
		
		helper = new MyDBHelper(Update_Activity.this,Globle.DB_NAME, null,Globle.VERSION);
		
		set_date=new SimpleDateFormat("yyyy/MM/dd  hh:mm").format(new Date());
		tiem.setText(set_date);
		//查詢  獲取時間段
		but_set.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
DatePickerDialog dialog=new DatePickerDialog(Update_Activity.this,new OnDateSetListener() {
					
					@Override
					public void onDateSet(DatePicker view, int year, int monthOfYear,
							int dayOfMonth) {
						// TODO Auto-generated method stub
						//把用户设置的时间存储
						calendar.set(Calendar.YEAR,year);
						calendar.set(Calendar.MONTH,monthOfYear);
						calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
						date=year+"/"+monthOfYear+"/"+dayOfMonth;
						TimePickerDialog time_dialog=new TimePickerDialog(Update_Activity.this,new OnTimeSetListener() {
							
							@Override
							public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
								// TODO Auto-generated method stub
								calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
								calendar.set(Calendar.MINUTE,minute);
								calendar.set(Calendar.SECOND,0);
								date=new SimpleDateFormat("yyyy/MM/dd  hh:mm").format(calendar.getTimeInMillis());
								tv_tiem.setText(date);
							}
						},c.get(Calendar.HOUR_OF_DAY),c.get(Calendar.MINUTE),true);
						time_dialog.show();
						
					}
				}, c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DATE));
				dialog.show();
				
			
			}
		});
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuItem item1=menu.add("保存");
		MenuItem item2=menu.add("取消");
		item1.setIcon(R.drawable.png_0044);
		item2.setIcon(R.drawable.png_0652);
		item1.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				// TODO Auto-generated method stub
				SQLiteDatabase db = helper.getWritableDatabase();
				ContentValues values = new ContentValues();
				values.put(Globle.TITLE,et_title.getText().toString());
				values.put(Globle.CONTENT,et_content.getText().toString());
				values.put(Globle.SET_ITEM,set_date);
				values.put(Globle.ATTACK_TIEM,date);
				String where=Globle._ID+"="+_id;
				int i=DBUtil.update(db, Globle.TABLE_NAME,values, where);
				
				
				if(i>=1){
					Toast.makeText(Update_Activity.this,"修改成功 ",Toast.LENGTH_SHORT).show();
//					
					AlarmManager alarmManager=(AlarmManager) getSystemService(ALARM_SERVICE);
					Intent intent=new Intent(Update_Activity.this,Ring_Activity.class);
					intent.putExtra("text",et_content.getText().toString());
					count++;
					System.out.println(""+count);
					PendingIntent p_intent=PendingIntent.getActivity(Update_Activity.this,count,intent,PendingIntent.FLAG_UPDATE_CURRENT);
					alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),p_intent);
					Intent intent1=new Intent(Update_Activity.this,MainActivity.class);
					startActivity(intent1);
					
				}else{
					Toast.makeText(Update_Activity.this,"修改失败",Toast.LENGTH_SHORT).show();
					Intent intent1=new Intent(Update_Activity.this,MainActivity.class);
					startActivity(intent1);
				}
				
				return false;
			}
		});
		
		return super.onCreateOptionsMenu(menu);
		
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		_id=getIntent().getLongExtra(Globle._ID,0);
		System.out.println(""+_id);
		loadData(_id);
		super.onResume();
	}
	public void loadData(long _id){
		SQLiteDatabase db=helper.getWritableDatabase();
		String where=Globle._ID+"="+_id;
		Cursor cursor=DBUtil.select(db, Globle.TABLE_NAME,new String[]{Globle._ID,Globle.TITLE,Globle.CONTENT,Globle.SET_ITEM,Globle.ATTACK_TIEM}, where);
		while(cursor.moveToNext()){
			et_title.setText(cursor.getString(1));
			et_content.setText(cursor.getString(2));
//			tiem.setText(cursor.getString(3));
		tv_tiem.setText(new SimpleDateFormat("yyyy/MM/dd  hh:mm").format(cursor.getLong(4)));
		}
	}
	
	
}
