package com.example.class_24_c4_0508_01_memo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
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
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.TextView;

import com.example.class_24_c4_0508_01_DBUtil.DBUtil;
import com.example.class_24_c4_0508_01_MyDBHeole.Globle;
import com.example.class_24_c4_0508_01_MyDBHeole.MyDBHelper;
import com.example.class_24_c4_0508_01_Property.User;
import com.uangel.suishouji.R;

public class Delete_Activty extends Activity{
	
	private ListView lv_delete;
	private CheckBox cb01;
	private List<User> list;
	private MyDBHelper helper;
	private MyAdapter adapter;
	private boolean flog=true;
	private AlarmManager a_manager;
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.delete);
		lv_delete=(ListView) findViewById(R.id.lv_delete);
		cb01=(CheckBox) findViewById(R.id.cb01);
		a_manager=(AlarmManager) getSystemService(ALARM_SERVICE);
		helper=new MyDBHelper(this,Globle.DB_NAME, null,Globle.VERSION);
		
		list=new ArrayList<User>();
		 cb01.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					// TODO Auto-generated method stub
					if(flog){
					if(isChecked){
						for(int i=0;i<list.size();i++){
							list.get(i).setFlog(true);
						}
					}else{
						for(int i=0;i<list.size();i++){
							list.get(i).setFlog(false);
						}
						
					}
					}
					adapter.notifyDataSetChanged();

				}
			});
	        
	        lv_delete.setOnItemClickListener(new OnItemClickListener() {

				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
						long arg3) {
					// TODO Auto-generated method stub
					if(list.get(arg2).isFlog()==true){
						list.get(arg2).setFlog(false);
						flog=false;
						cb01.setChecked(false);
					}else{
						list.get(arg2).setFlog(true);
						if(ssss()){
							cb01.setChecked(true);
						}
					}
					flog=true;
					adapter.notifyDataSetChanged();
				}
			});
		  
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		loadData();
		adapter=new MyAdapter();
		lv_delete.setAdapter(adapter);
		
		super.onResume();
	}
	public boolean ssss(){
    	for(int i=0;i<list.size();i++){
    		if(list.get(i).isFlog()==false){
    			return false;
    		}
    	}
		return true;
	}
	
	public void loadData(){
		list.clear();
		SQLiteDatabase db=helper.getWritableDatabase();
		Cursor cursor=DBUtil.select(db,Globle.TABLE_NAME, new String[]{Globle._ID,Globle.TITLE,Globle.CONTENT,Globle.SET_ITEM,Globle.ATTACK_TIEM});
		while(cursor.moveToNext()){
		User pre=new User(cursor.getLong(0),cursor.getString(1),cursor.getString(2),cursor.getLong(3),cursor.getLong(4),false);
			list.add(pre);
		}
		cursor.close();
		db.close();
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
		public View getView(final int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View view=LayoutInflater.from(Delete_Activty.this).inflate(R.layout.delete_1,null);
			TextView tv_title=(TextView) view.findViewById(R.id.tv_title);
			tv_title.setText(list.get(position).getTitle());
			TextView tv_01=(TextView) view.findViewById(R.id.tv_01);
			tv_01.setText(new SimpleDateFormat("yyyy/MM/dd  hh:mm").format(list.get(position).getSet_tiem()));
			final CheckBox cb02=(CheckBox) view.findViewById(R.id.cb02);
			cb02.setFocusable(false);
			if(list.get(position).isFlog()==true){
				cb02.setChecked(true);
			}else{
				cb02.setChecked(false);
			}
			
			cb02.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					// TODO Auto-generated method stub
					if(list.get(position).isFlog()==true){
						list.get(position).setFlog(false);
						flog=false;
						cb02.setChecked(false);
					}else{
						list.get(position).setFlog(true);
						if(ssss()==true){
							cb02.setChecked(true);
						}
						
					}
					flog=true;
					adapter.notifyDataSetChanged();
				}
			});
				
			return view;
		}
		
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
				android.app.AlertDialog.Builder builder=new AlertDialog.Builder(Delete_Activty.this);
				builder.setTitle("删除？");
				builder.setMessage("是否删除？");
				builder.setIcon(R.drawable.png0652);
				builder.setPositiveButton("是",new OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						SQLiteDatabase db=helper.getWritableDatabase();
						for(int i=0;i<list.size();i++){
							if(list.get(i).isFlog()==true){
								String where=list.get(i).get_id()+"="+Globle._ID;
								DBUtil.delete(db, Globle.TABLE_NAME, where);
								AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
								Intent intent=new Intent(Delete_Activty.this,Ring_Activity.class);
								intent.putExtra("text",list.get(0).getContent());
								PendingIntent pintent=PendingIntent.getActivity(Delete_Activty.this,(int)list.get(i).get_id(),intent, PendingIntent.FLAG_UPDATE_CURRENT);
								alarmManager.cancel(pintent);
							}
							
						}
						db.close();
						finish();
					}
				});
				builder.setNegativeButton("否",null);
				builder.create().show();
				return false;
			}
		});
		
		return super.onCreateOptionsMenu(menu);
	}
	
}
