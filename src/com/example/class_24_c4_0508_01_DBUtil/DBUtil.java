package com.example.class_24_c4_0508_01_DBUtil;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBUtil {
	/**
	 * ��ѯ�������ݱ�����Ϣ
	 * 
	 * @param db
	 *            �����ݿ����
	 * @param tableName
	 *            ������
	 * @param columns
	 *            ��Ҫ��ѯ���У����Ϊnull�����ʾ��ѯ������
	 * @return ��ѯ���
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
	 * ��ѯ���ݿ�������ָ�������ļ�¼
	 * @param db �����ݿ����
	 * @param tableName �����ݱ���
	 * @param columns ��Ҫ��ѯ������
	 * @param where ����ѯ����
	 * @return ����ѯ���
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
	 * ��ѯ�������ݣ�������ָ���ֶ�����
	 * @param db�����ݿ����
	 * @param tableName������
	 * @param columns������
	 * @param order�������ֶ�
	 * @return ��ѯ���
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
	 * �������ݿ�
	 * @param db�����ݿ����
	 * @param tableName�����ݱ���
	 * @param values��Ҫ��ѯ������
	 * @return ��ǰ�������ݵ�_id
	 */
	public static long insert(SQLiteDatabase db,String tableName,ContentValues values){
		return db.insert(tableName, null, values);
		
	}
	
	/**
	 * �޸�ָ���ļ�¼
	 * @param db������¼����
	 * @param tableName������
	 * @param values��Ҫ�޸ĵ�ֵ
	 * @param where������
	 * @return ��Ӱ��ļ�¼��
	 */
	public static int update(SQLiteDatabase db,String tableName,ContentValues values,String where){
		return db.update(tableName, values, where, null);
	}
/**
 * ɾ��ָ�������ļ�¼
 * @param db�����ݿ����
 * @param tableName������
 * @param where������
 * @return ��Ӱ��ļ�¼��
 */
	public static int delete(SQLiteDatabase db,String tableName,String where){
		return db.delete(tableName, where, null);
	}

}
