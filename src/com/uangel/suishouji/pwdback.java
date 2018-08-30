package com.uangel.suishouji;


import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class pwdback extends Activity implements OnClickListener{

	private EditText nameback,mailback;
	private Button back;
	private Spinner Qspinner1;
	private int flag;
	//private static String nameback1,mailback1;
	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.pwdback);
	        nameback = (EditText)findViewById(R.id.nameback);
	        mailback = (EditText)findViewById(R.id.mailback);
	        Qspinner1=  (Spinner)findViewById(R.id.Qspinner1);
	       back = (Button)findViewById(R.id.back);
	       List<String> Qlist1 = new ArrayList<String>();
			Qlist1.add("你的出生地在哪?");
			Qlist1.add("你的父亲叫什么?");
			Qlist1.add("你的母亲叫什么?");
			ArrayAdapter<String> Qadapter1 = new ArrayAdapter<String>(pwdback.this, android.R.layout.simple_spinner_item, Qlist1);
			Qspinner1.setAdapter(Qadapter1);
	        back.setOnClickListener(pwdback.this);
	        }
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		flag=0;
		String nameback1 = nameback.getText().toString();
		String mailback1 = mailback.getText().toString();
		SQLiteDatabase db = new DBHelper(pwdback.this, "Info.db").getReadableDatabase();
		Cursor  mcursor  = db.query("people",new String[] {"name","pwd","question","mail"},null,null, null, null, null);
		while(mcursor.moveToNext()){
			String name1 = mcursor.getString(mcursor.getColumnIndex("name"));
		       String name = new Security().Decrypt(name1);
			 if(name.equals(nameback1)){
				 String s1 = Qspinner1.getSelectedItem().toString();
				  String qs= mcursor.getString(mcursor.getColumnIndex("question"));
				  String mail = mcursor.getString(mcursor.getColumnIndex("mail"));
				  if(s1.equals(qs)){
					  if(mail.equals(mailback1)){
						  flag=1;
						  String pwd1 = mcursor.getString(mcursor.getColumnIndex("pwd"));
						  String pwd = new Security().Decrypt(pwd1);
						  Toast.makeText(this,"答案正确  你的密码是："+ pwd, Toast.LENGTH_SHORT).show();
						  Intent intent=new Intent(pwdback.this,Login.class);
						  startActivity(intent);
					  	}else{
					  		Toast.makeText(this,"答案错误 请重新输入" , Toast.LENGTH_SHORT).show();
					  		flag=1;
					  		 mailback.setText("");
					  	}
				  	} 
				  else{
					  Toast.makeText(this,"所选问题与注册时选择不同", Toast.LENGTH_SHORT).show();
					  flag=1;
					  mailback.setText("");
				  }
				  
			 	}
		}
		if(flag == 0){Toast.makeText(this, "用户名不存在 请重新输入", Toast.LENGTH_SHORT).show();
		nameback.setText("");
		}
		
		mcursor.close(); 
		
		}
		
	}

