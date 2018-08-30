package com.example.class_24_c4_0508_01_memo;

import java.nio.channels.AlreadyConnectedException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.widget.TextView;

import com.example.class_24_c4_0508_01_DBUtil.DBUtil;
import com.example.class_24_c4_0508_01_MyDBHeole.Globle;
import com.example.class_24_c4_0508_01_MyDBHeole.MyDBHelper;
import com.example.class_24_c4_0508_01_Property.Property;
import com.uangel.suishouji.R;

public class See_Activity extends Activity{

	private TextView tv_see;
	private long _id;
	private MyDBHelper helper;
	private List<Property> list = new ArrayList<Property>();;
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.see);
		tv_see=(TextView) findViewById(R.id.tv_see);

		helper=new MyDBHelper(this,Globle.DB_NAME,null,Globle.VERSION);
	}
	@Override
	protected void onResume() {
	// TODO Auto-generated method stub
		_id=getIntent().getLongExtra(Globle._ID,0);
		loadData(_id);
	super.onResume();
	}
public void loadData(long _id){
	SQLiteDatabase db=helper.getWritableDatabase();
	String where=Globle._ID+"="+_id;
	Cursor cursor=DBUtil.select(db, Globle.TABLE_NAME,new String[]{Globle._ID,Globle.TITLE,Globle.CONTENT,Globle.SET_ITEM,Globle.ATTACK_TIEM}, where);
	
	while(cursor.moveToNext()){
		Property property = new Property(cursor.getLong(0), cursor.getString(1), cursor.getString(2), cursor.getLong(3), cursor.getLong(4));
		list.add(property);
		tv_see.setText("标题："+cursor.getString(1)+"\n内容："+cursor.getString(2)+"\n设定时的时间：\n"+new SimpleDateFormat("yyyy/MM/dd  hh:mm").format(cursor.getLong(3))+"\n触发时的时间：\n"+new SimpleDateFormat("yyyy/MM/dd  hh:mm").format(cursor.getLong(4)));
	}
	db.close();
	cursor.close();
}
@Override
public boolean onCreateOptionsMenu(Menu menu) {
// TODO Auto-generated method stub
	
MenuItem item1=menu.add("删除");
MenuItem item2=menu.add("取消");

item1.setOnMenuItemClickListener(new OnMenuItemClickListener() {
	
	@Override
	public boolean onMenuItemClick(MenuItem item) {
		// TODO Auto-generated method stub
		
		android.app.AlertDialog.Builder builder=new AlertDialog.Builder(See_Activity.this);
		builder.setIcon(R.drawable.png0652);
		builder.setTitle("删除？");
		builder.setMessage("是否删除？");
		
		builder.setPositiveButton("是",new DialogInterface.OnClickListener() {
			 
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				loadData(_id);
				AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
				Intent intent=new Intent(See_Activity.this,Ring_Activity.class);
				intent.putExtra("text",list.get(0).getContent());
				PendingIntent pintent=PendingIntent.getActivity(See_Activity.this,(int)_id,intent, PendingIntent.FLAG_UPDATE_CURRENT);
				alarmManager.cancel(pintent);
				SQLiteDatabase db=helper.getWritableDatabase();
		 		String where=_id+"="+Globle._ID;
				DBUtil.delete(db, Globle.TABLE_NAME, where);
				System.out.println("传过来的_id="+_id);
				finish();
			}
		});
		builder.setNegativeButton("取消",null);
		builder.create().show();
		return false;
	}
});
item2.setOnMenuItemClickListener(new OnMenuItemClickListener() {
	
	@Override
	public boolean onMenuItemClick(MenuItem item) {
		// TODO Auto-generated method stub
		finish();
		return false;
	}
});
return super.onCreateOptionsMenu(menu);
}
	
}
