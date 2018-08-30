package com.uangel.suishouji;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;


public class Login extends Activity implements OnClickListener{
    /** Called when the activity is first created. */
		//private static final String PREFS_NAME = "MyInfo";//用来保存用户信息
	 	public static EditText Num,Pwd;
	 	private static Button Login,Post,pwdback;
	 	//public static CheckBox RememberPwd,AutoLogin;
		private DBHelper dbHelper;
		public static SQLiteDatabase sdb;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
//        AppConnect.getInstance(this);
//		LinearLayout container =(LinearLayout)findViewById(R.id.AdLinearLayout);   
//		new AdView(this,container).DisplayAd();
      //获得实例对象
        Num=(EditText) findViewById(R.id.et_Num);
        Pwd=(EditText) findViewById(R.id.et_Pwd);
        Login=(Button) findViewById(R.id.btn_Login);
        Post=(Button) findViewById(R.id.Post);
        pwdback = (Button) findViewById(R.id.pwdback);
        Post.setOnClickListener(this);
        Login.setOnClickListener(this);
        pwdback.setOnClickListener(this);
        dbHelper=new DBHelper(this,"Info.db");
        //LoadUserDate();
   
    }
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
    	case R.id.btn_Login:
    		if(Num.getText().toString().equals("")){
				Toast.makeText(Login.this,"姓名不能为空 ", Toast.LENGTH_SHORT).show();
				return;
			}
			if(Pwd.getText().toString().equals("")){
				Toast.makeText(Login.this,"号码不能为空 ", Toast.LENGTH_SHORT).show();
				return;
			}
			//SaveUserDate();
			boolean bool=login(Num.getText().toString(),Pwd.getText().toString());
			if(bool){	
				Toast.makeText(Login.this, "登录成功！", Toast.LENGTH_LONG).show();
				Intent intent = new Intent();
				intent.putExtra("name",Num.getText().toString());
				intent.setClass(Login.this, IndexActivity.class);
  				Login.this.startActivity(intent);
		
			}else{
				Toast.makeText(Login.this, "用户名或密码有误！", Toast.LENGTH_LONG).show();
				return;
			}
    		break;
				
    	case R.id.Post:
    		Intent intent=new Intent(Login.this,Register.class);
    		startActivity(intent);

			break;
    	case R.id.pwdback:
    		//System.out.println("被响应");
    		Intent intent1 = new Intent();
			//intent.putExtra("name",Num.getText().toString());
			intent1.setClass(Login.this, pwdback.class);
				Login.this.startActivity(intent1);
				break;
		}
	}

	 //登录
  	public boolean login(String username,String password){
  		String name = new Security().Encryption(username);
  		String pwd = new Security().Encryption(password);
  		/*SQLiteDatabase*/ sdb=dbHelper.getReadableDatabase();
  		String sql="select * from people where name=? and pwd=?";
  		Cursor cursor=sdb.rawQuery(sql, new String[]{name,pwd});		
  		if(cursor.moveToFirst()==true){
  			cursor.close();
  			return true;
  		}
  		return false;
  	}
  	//退出
  	 public boolean onKeyDown(int keyCode, KeyEvent event)  {  
         if (keyCode == KeyEvent.KEYCODE_BACK )   {  
        	 AlertDialog.Builder builder = new Builder(Login.this); 
       		 builder.setIcon(android.R.drawable.ic_dialog_info);
       	        builder.setMessage("确定要退出?"); 
       	        builder.setTitle("提示"); 
       	        builder.setPositiveButton("确认", 
       	                new android.content.DialogInterface.OnClickListener() { 
       	                    public void onClick(DialogInterface dialog, int which) { 
       	                        dialog.dismiss(); 
       	                     Login.this.finish(); 
       	                    } 
       	                }); 
       	        builder.setNegativeButton("取消", 
       	                new android.content.DialogInterface.OnClickListener() { 
       	                    public void onClick(DialogInterface dialog, int which) { 
       	                        dialog.dismiss(); 
       	                    } 
       	                }); 
       	        		builder.create().show();  
         }    
         return false;       
     }
  	    
	 
}
