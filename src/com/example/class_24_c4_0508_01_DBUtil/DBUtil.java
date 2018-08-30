package com.example.class_24_c4_0508_01_DBUtil;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBUtil {
	/**
	 * 查询所有数据表中信息
	 * 
	 * @param db
	 *            ：数据库对象
	 * @param tableName
	 *            ：表名
	 * @param columns
	 *            ：要查询的列，如果为null，则表示查询所有列
	 * @return 查询结果
	 */
	public static Cursor select(SQLiteDatabase db, String tableName,
			String[] columns) {
		Cursor cursor;
		if (columns == null) {
			cursor = db.query(tableName, null, null, null, null, null, null);
		} else {
			cursor = db.query(tableName, columns, null, null, null, null, null);
		}
		return cursor;
	}

	/**
	 * 查询数据库中满足指定条件的记录
	 * @param db ：数据库对象
	 * @param tableName ：数据表名
	 * @param columns ：要查询的列名
	 * @param where ：查询条件
	 * @return ：查询结果
	 */
	public static Cursor select(SQLiteDatabase db, String tableName,
			String[] columns, String where) {
		Cursor cursor;
		if (columns == null) {
			cursor = db.query(tableName, null, where, null, null, null, null);
		} else {
			cursor = db
					.query(tableName, columns, where, null, null, null, null);
		}
		return cursor;
	}
	/**
	 * 查询所有数据，并按照指定字段排序
	 * @param db：数据库对象
	 * @param tableName：表名
	 * @param columns：列名
	 * @param order：排序字段
	 * @return 查询结果
	 */
	public static Cursor selectWithOrder(SQLiteDatabase db, String tableName,
			String[] columns,String order) {
		Cursor cursor;
		if (columns == null) {
			cursor = db.query(tableName, null, null, null, null, null, order);
		} else {
			cursor = db.query(tableName, columns, null, null, null, null, order);
		}
		return cursor;
	}
	
	/**
	 * 插入数据库
	 * @param db：数据库对象
	 * @param tableName：数据表名
	 * @param values：要查询的数据
	 * @return 当前插入数据的_id
	 */
	public static long insert(SQLiteDatabase db,String tableName,ContentValues values){
		return db.insert(tableName, null, values);
		
	}
	
	/**
	 * 修改指定的记录
	 * @param db：数据录对象
	 * @param tableName：表名
	 * @param values：要修改的值
	 * @param where：条件
	 * @return 受影响的记录数
	 */
	public static int update(SQLiteDatabase db,String tableName,ContentValues values,String where){
		return db.update(tableName, values, where, null);
	}
/**
 * 删除指定条件的记录
 * @param db：数据库对象
 * @param tableName：表名
 * @param where：条件
 * @return 受影响的记录数
 */
	public static int delete(SQLiteDatabase db,String tableName,String where){
		return db.delete(tableName, where, null);
	}

}
