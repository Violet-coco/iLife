package cn.jinian;

import java.util.Date;

import com.uangel.suishouji.R;


import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;

public class ToDoListAlarm extends Activity {
	private Cursor todoItemCursor; // ������������¼��

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.todo_alarm);
		ContentResolver cr = this.getContentResolver();
		todoItemCursor = cr.query(
				ToDoContentProvider.CONTENT_TODOLIST_URI,
				null,
				ToDoContentProvider.KEY_TIME + " >"
						+ new Date(System.currentTimeMillis()).getTime(), null,
				null);
	}

}
