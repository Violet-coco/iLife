package com.uangel.suishouji;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class MACAdapter extends Activity{
	
	private static List<String> list;
	public List<String> data()
	{
		
		List<String> list = new ArrayList<String>();
		SQLiteDatabase db = Login.sdb;
		Cursor cursor = db.query("people",new String[] {"name"},null,null, null, null, null);
		while (cursor.moveToNext()) { 
	       String name1 = cursor.getString(cursor.getColumnIndex("name"));
	       System.out.println(name1);
	       String name = new Security().Decrypt(name1);
	       System.out.println(name);
	       if(name.equals("admin")){}
	       else{
	       list.add(name);
	       }
		}
			return list;
			
	}
	
}