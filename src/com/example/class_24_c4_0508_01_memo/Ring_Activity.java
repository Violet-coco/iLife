package com.example.class_24_c4_0508_01_memo;

import java.io.IOException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.Bundle;

public class Ring_Activity extends Activity{

 	private  MediaPlayer  player;
    private AssetManager  assetManager;
    
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		 assetManager=getAssets();
    	 
         player=new MediaPlayer();
      
         try {
 		    AssetFileDescriptor  afd=assetManager.openFd("music.mp3");
 		       player.setDataSource(afd.getFileDescriptor());
 		       player.prepare();
 		       player.start();
 		       
 		} catch (IOException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}
	   Intent intent=getIntent();
	   android.app.AlertDialog.Builder builder=new AlertDialog.Builder(Ring_Activity.this);
	   builder.setTitle(intent.getStringExtra("title"));
	   builder.setMessage(intent.getStringExtra("text"));
	   builder.setPositiveButton("确定",new OnClickListener() {
		
		@Override
		public void onClick(DialogInterface arg0, int arg1) {
			// TODO Auto-generated method stub
			player.stop();
			if(player!=null){
		    	   player.release();
		       }
			finish();
		}

		
	});
	   builder.create().show();

	}
}
