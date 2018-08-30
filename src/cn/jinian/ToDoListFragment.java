package cn.jinian;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.uangel.suishouji.R;


import android.app.AlertDialog;
import android.app.ListFragment;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class ToDoListFragment extends ListFragment implements
		ActionMode.Callback, LoaderCallbacks<Cursor> {
	private ActionMode myActionMode;
	private Cursor todoItemCursor; // 待办事项对象数组
	private SimpleCursorAdapter aa; // 扩展ArrayAdapter的适配器

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		this.getListView().setHeaderDividersEnabled(true);
		this.getListView().setFooterDividersEnabled(true);
		this.getListView().setDivider(
				new ColorDrawable(R.color.notepad_lines_color));
		this.getListView().setDividerHeight(1);
		this.getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		this.setHasOptionsMenu(true);

		registerForContextMenu(this.getListView());
		getListView().setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				view.setSelected(true);
				view.showContextMenu();
			}
		});
		getListView().setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (myActionMode != null)
					return false;
				myActionMode = getActivity().startActionMode(
						ToDoListFragment.this);
				view.setSelected(true);
				return true;
			}
		});

		this.getLoaderManager().initLoader(0, null, this);
		// Create the array adapter to bind the array to the ListView
		int resID = R.layout.todolist_item;
		aa = new SimpleCursorAdapter(this.getActivity(), resID, todoItemCursor,
				new String[] { ToDoContentProvider.KEY_TASK,
						ToDoContentProvider.KEY_TIME }, new int[] {
						R.id.row, R.id.rowDate },
				SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER) {
			@Override
			public void bindView(View view, Context context, Cursor cursor) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd",
						Locale.CHINA);
				String task = cursor.getString(cursor
						.getColumnIndexOrThrow(ToDoContentProvider.KEY_TASK));
				Date createTime = new Date(
						cursor.getLong(cursor
								.getColumnIndexOrThrow(ToDoContentProvider.KEY_TIME)));
				String dateString = sdf.format(createTime); // 将创建日期的毫秒数转换为年月日格式
				((TextView) view.findViewById(R.id.row)).setText(task);
				((TextView) view.findViewById(R.id.rowDate))
						.setText(dateString);
			}
		};
		// Bind the array adapter to the ListView.
		this.setListAdapter(aa);
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onResume() {
		super.onResume();
		this.getLoaderManager().restartLoader(0, null, this);

	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.todo_list_menu, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_new:
			startActivity(new Intent(getActivity(),
					cn.jinian.ToDoItemEditActivity.class));
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	// Called when the action mode is created; startActionMode() was called
	@Override
	public boolean onCreateActionMode(ActionMode mode, Menu menu) {
		// Inflate a menu resource providing context menu items
		MenuInflater inflater = mode.getMenuInflater();
		inflater.inflate(R.menu.todo_list_context_menu, menu);
		return true;
	}

	// Called each time the action mode is shown. Always called after
	// onCreateActionMode
	// may be called multiple times if the mode is invalidated.
	@Override
	public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
		return false;
	}

	@Override
	public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
		long id = getListView().getCheckedItemIds()[0];
		Log.i("Hwd", "position=" + id);

		final Uri uri = ContentUris.withAppendedId(
				ToDoContentProvider.CONTENT_TODOLIST_URI, id);
		final ContentResolver cr = this.getActivity().getContentResolver();
		switch (item.getItemId()) {
		case R.id.menu_delete:
			new AlertDialog.Builder(this.getActivity())
					.setIcon(android.R.drawable.ic_delete)
					.setMessage("确认删除该事项吗？")
					.setNegativeButton("取消", new OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
						}
					}).setPositiveButton("确认", new OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							cr.delete(uri, null, null);
							ToDoListFragment.this.getLoaderManager()
									.restartLoader(0, null,
											ToDoListFragment.this);
						}
					}).create().show();

			mode.finish(); // Action picked, so close the CAB
			return true;
		case R.id.menu_edit:
			Cursor cursor = cr.query(uri, null, null, null, null);
			cursor.moveToFirst();
			ToDoItem toDoItem = new ToDoItem("");
			toDoItem.set_id(cursor.getLong(cursor
					.getColumnIndexOrThrow(ToDoContentProvider.KEY_ID)));
			toDoItem.setCreateDate(new Date(
					cursor.getLong(cursor
							.getColumnIndexOrThrow(ToDoContentProvider.KEY_CREATION_DATE))));
			toDoItem.setTime(new Date(cursor.getLong(cursor
					.getColumnIndexOrThrow(ToDoContentProvider.KEY_TIME))));
			toDoItem.setAlertTime(cursor.getString(cursor
					.getColumnIndexOrThrow(ToDoContentProvider.KEY_ALERT_TIME)));
			toDoItem.setDuplicate(cursor.getInt(cursor
					.getColumnIndexOrThrow(ToDoContentProvider.KEY_DUPLICATE)));
			toDoItem.setPriority(cursor.getInt(cursor
					.getColumnIndexOrThrow(ToDoContentProvider.KEY_PRIORITY)));
			toDoItem.setTask(cursor.getString(cursor
					.getColumnIndexOrThrow(ToDoContentProvider.KEY_TASK)));
			Intent intent = new Intent(this.getActivity(),
					ToDoItemEditActivity.class);
			intent.putExtra("todoItem", toDoItem);
			this.startActivity(intent);
			mode.finish(); // Action picked, so close the CAB
			return true;
		default:
			return false;
		}
	}

	// Called when the user exits the action mode
	@Override
	public void onDestroyActionMode(ActionMode mode) {
		myActionMode = null;
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		CursorLoader loader = new CursorLoader(this.getActivity(),
				ToDoContentProvider.CONTENT_TODOLIST_URI, null, null, null, ToDoContentProvider.KEY_TIME);
		return loader;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		aa.swapCursor(data);
		aa.notifyDataSetChanged();
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		aa.swapCursor(null);
	}
}
