package cn.jinian;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

public class ToDoContentProvider extends ContentProvider {
	// ΪToDoContentProvider����һ��Uri������Ӧ�ó���ͨ��ContentResolverʹ�����Uri������ToDoContentProvider
	public static final Uri CONTENT_TODOLIST_URI = Uri
			.parse("content://com.hesky.todoprovider/todoitems");
	// ���������Ĺ��о�̬��������Щ����������SQLiteOpenHelper���У������������ݿ����������Ӧ�ó�����ȡ��Ĳ�ѯ�����
	public static final String KEY_ID = "_id";
	public static final String KEY_TASK = "task";
	public static final String KEY_CREATION_DATE = "creation_time";
	public static final String KEY_DUPLICATE = "duplicate";
	public static final String KEY_TIME = "time";
	public static final String KEY_ALERT_TIME = "alert_time";
	public static final String KEY_PRIORITY = "priority";

	private MySQLiteOpenHelper myOpenHelper;

	public ToDoContentProvider() {
	}

	@Override
	public boolean onCreate() {
		// ����ײ�����ݿ�
		// �ӳٴ����ݿ⣬ֱ����Ҫִ��һ����ѯ������ʱ�ٴ�
		myOpenHelper = new MySQLiteOpenHelper(getContext(),
				MySQLiteOpenHelper.DATABASE_NAME, null,
				MySQLiteOpenHelper.DATABASE_VERSION);
		return true;
	}

	private static final int ALLROWS = 1;
	private static final int SINGLE_ROW = 2;
	// ����һ��UriMatcher��ʹ��ContentProvider�ܹ�������ȫ���ѯ��������ض��в�ѯ��
	// ��getType�����и��ݲ�ѯ�����ͷ�����ȡ��MIME����
	private static final UriMatcher uriMatcher;
	static {
		// ���UriMatcher�����ԡ�element����β��URI��Ӧ����ȫ�����ݣ�
		// �ԡ�elements/[rowID]����β��URI��Ӧ����������
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI("com.hesky.todoprovider", "todoitems", ALLROWS);
		uriMatcher.addURI("com.hesky.todoprovider", "todoitems/#", SINGLE_ROW);
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		// ��һ��ֻ�������ݿ�
		SQLiteDatabase db = myOpenHelper.getReadableDatabase();
		// ��Ҫ�Ļ���ʹ����Ч��SQL����滻��Щ���
		String groupBy = null;
		String having = null;
		SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
		queryBuilder.setTables(MySQLiteOpenHelper.DATABASE_TABLE);
		switch (uriMatcher.match(uri)) {
		case SINGLE_ROW:
			String rowID = uri.getPathSegments().get(1);
			queryBuilder.appendWhere(KEY_ID + "=" + rowID);
		default:
			break;
		}
		Cursor cursor = queryBuilder.query(db, projection, selection,
				selectionArgs, groupBy, having, sortOrder);
		return cursor;
	}

	@Override
	// Ϊһ��ContenProvider URI����һ���ַ���������ʶ��MIME����
	public String getType(Uri uri) {
		switch (uriMatcher.match(uri)) {
		case ALLROWS:
			return "vnd.android.cursor.dir/vnd.hesky.todos";
		case SINGLE_ROW:
			return "vnd.android.cursor.item/vnd.hesky.todos";
		default:
			throw new IllegalArgumentException("Unsupported URI: " + uri);
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// ��һ���ɶ�/��д�����ݿ�
		SQLiteDatabase db = myOpenHelper.getWritableDatabase();
		// Ҫ��ͨ������һ���յ�Content
		// Value����ķ�ʽ�����ݿ����һ�����У�����ʹ��nullColumnHack������ָ����������Ϊnull������
		String nullColumnHack = null;
		// ����в���ֵ
		long id = db.insert(MySQLiteOpenHelper.DATABASE_TABLE, nullColumnHack,
				values);
		// ���첢�����²����е�URI
		if (id > -1) {
			Uri inserrtID = ContentUris.withAppendedId(CONTENT_TODOLIST_URI, id);
			// ֪ͨ���еĹ۲��ߣ����ݼ��Ѿ��ı�
			getContext().getContentResolver().notifyChange(inserrtID, null);
			return inserrtID;
		} else
			return null;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// ��һ���ɶ�/��д�����ݿ�
		SQLiteDatabase db = myOpenHelper.getWritableDatabase();
		// �������URI���޶�ɾ����ΪΪָ������
		switch (uriMatcher.match(uri)) {
		case SINGLE_ROW:
			String rowID = uri.getPathSegments().get(1);
			selection = KEY_ID
					+ "="
					+ rowID
					+ (!TextUtils.isEmpty(selection) ? " and ( " + selection
							+ ")" : "");
		default:
			break;
		}
		// Ҫ�뷵��ɾ�����������������ָ��һ��where�Ӿ䡣Ҫɾ�����е��в�����һ��ֵ�����롰1��
		if (selection == null)
			selection = "1";
		// ִ��ɾ��
		int deleteCount = db.delete(MySQLiteOpenHelper.DATABASE_TABLE,
				selection, selectionArgs);
		// ֪ͨ���еĹ۲��ߣ����ݼ��Ѿ��ı�
		getContext().getContentResolver().notifyChange(uri, null);
		return deleteCount;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// ��һ���ɶ�/��д�����ݿ�
		SQLiteDatabase db = myOpenHelper.getWritableDatabase();
		// �������URI���޶�������ΪΪָ������
		switch (uriMatcher.match(uri)) {
		case SINGLE_ROW:
			String rowID = uri.getPathSegments().get(1);
			selection = KEY_ID
					+ "="
					+ rowID
					+ (!TextUtils.isEmpty(selection) ? " and (" + selection
							+ ")" : "");
		default:
			break;
		}
		// ִ�и���
		int updateCount = db.update(MySQLiteOpenHelper.DATABASE_TABLE, values,
				selection, selectionArgs);
		return updateCount;
	}

	private static class MySQLiteOpenHelper extends SQLiteOpenHelper {

		private static final String DATABASE_NAME = "tododatabase.db"; // ���ݿ�����
		private static final int DATABASE_VERSION = 4; // ���ݿ�汾��
		private static final String DATABASE_TABLE = "todoitemtable"; // To-Do����

		public MySQLiteOpenHelper(Context context, String name,
				CursorFactory factory, int version) {
			super(context, name, factory, version);
		}

		// �������ݿ��SQL���
		private static final String DATABASE_CREATE_SQL = "create table "
				+ DATABASE_TABLE + " (" + KEY_ID
				+ " integer primary key autoincrement, " + KEY_TASK
				+ " text not null, " + KEY_CREATION_DATE + " long, "
				+ KEY_DUPLICATE + " integer, " + KEY_TIME + " long, "
				+ KEY_ALERT_TIME + " text, " + KEY_PRIORITY + " integer" + ");";

		// ���ڴ�����û�����ݿ�ʱ���ø÷�����������ᴴ��һ���µ����ݿ�
		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(DATABASE_CREATE_SQL);
		}

		// �����ݿ�汾��ƥ��ʱ���ø÷�����������������������ǰ�汾
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// ��¼�汾����
			Log.w("TaskDBAdapter", "Upgrading from version " + oldVersion
					+ " to " + newVersion + ", which will destroy all old data");
			// �����е����ݿ��������°汾��ͨ���Ƚ�oldVersion��newVersion��ֵ���������汾��
			// ��򵥵ķ�ʽ����ɾ���ɵı��ٴ����±�
			db.execSQL("drop table if exists " + DATABASE_TABLE);
			// �����±�
			onCreate(db);
		}
	}
}
