package cn.jinian;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class ToDoListInitReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Toast.makeText(context, "ToDoList Receiver is Walked!", Toast.LENGTH_LONG).show();
	}
}
