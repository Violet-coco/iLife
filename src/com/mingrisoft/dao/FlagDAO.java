package com.mingrisoft.dao;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mingrisoft.model.Tb_flag;

public class FlagDAO {
	private DBOpenHelper helper;// ����DBOpenHelper����
	private SQLiteDatabase db;// ����SQLiteDatabase����

	public FlagDAO(Context context){// ���幹�캯��
		helper = new DBOpenHelper(context);// ��ʼ��DBOpenHelper����
		db = helper.getWritableDatabase();// ��ʼ��SQLiteDatabase����
	}

	/**
	 * ��ӱ�ǩ��Ϣ
	 * 
	 * @param tb_flag
	 */
	public void add(Tb_flag tb_flag) {
		db.execSQL("insert into tb_flag (_id,flag) values (?,?)", new Object[] {
				tb_flag.getid(), tb_flag.getFlag() });// ִ����ӱ�ǩ��Ϣ����
	}

	/**
	 * ���±�ǩ��Ϣ
	 * 
	 * @param tb_flag
	 */
	public void update(Tb_flag tb_flag) {
		db.execSQL("update tb_flag set flag = ? where _id = ?", new Object[] {
				tb_flag.getFlag(), tb_flag.getid() });// ִ���޸ı�ǩ��Ϣ����
	}

	/**
	 * ���ұ�ǩ��Ϣ
	 * 
	 * @param id
	 * @return
	 */
	public Tb_flag find(int id) {
		Cursor cursor = db.rawQuery(
				"select _id,flag from tb_flag where _id = ?",
				new String[] { String.valueOf(id) });// ���ݱ�Ų��ұ�ǩ��Ϣ�����洢��Cursor����
		if (cursor.moveToNext()){// �������ҵ��ı�ǩ��Ϣ
			// ���������ı�ǩ��Ϣ�洢��Tb_flag����
			return new Tb_flag(cursor.getInt(cursor.getColumnIndex("_id")),
					cursor.getString(cursor.getColumnIndex("flag")));
		}
		cursor.close();// �ر��α�
		return null;// ���û����Ϣ���򷵻�null
	}

	/**
	 * �h����ǩ��Ϣ
	 * 
	 * @param ids
	 */
	public void detele(Integer... ids) {
		if (ids.length > 0){// �ж��Ƿ����Ҫɾ����id
			StringBuffer sb = new StringBuffer();// ����StringBuffer����
			for (int i = 0; i < ids.length; i++){// ����Ҫɾ����id����
				sb.append('?').append(',');// ��ɾ��������ӵ�StringBuffer������
			}
			sb.deleteCharAt(sb.length() - 1);// ȥ�����һ����,���ַ�
			// ִ��ɾ����ǩ��Ϣ����
			db.execSQL("delete from tb_flag where _id in (" + sb + ")",
					(Object[]) ids);
		}
	}

	/**
	 * ��ȡ��ǩ��Ϣ
	 * 
	 * @param start
	 *            ��ʼλ��
	 * @param count
	 *            ÿҳ��ʾ����
	 * @return
	 */
	public List<Tb_flag> getScrollData(int start, int count) {
		List<Tb_flag> lisTb_flags = new ArrayList<Tb_flag>();// �������϶���
		// ��ȡ���б�ǩ��Ϣ
		Cursor cursor = db.rawQuery("select * from tb_flag limit ?,?",
				new String[] { String.valueOf(start), String.valueOf(count) });
		while (cursor.moveToNext()){// �������еı�ǩ��Ϣ
			// ���������ı�ǩ��Ϣ��ӵ�������
			lisTb_flags.add(new Tb_flag(cursor.getInt(cursor
					.getColumnIndex("_id")), cursor.getString(cursor
					.getColumnIndex("flag"))));
		}
		cursor.close();// �ر��α�
		return lisTb_flags;// ���ؼ���
	}

	/**
	 * ��ȡ�ܼ�¼��
	 * 
	 * @return
	 */
	public long getCount() {
		Cursor cursor = db.rawQuery("select count(_id) from tb_flag", null);// ��ȡ��ǩ��Ϣ�ļ�¼��
		if (cursor.moveToNext()){// �ж�Cursor���Ƿ�������
			return cursor.getLong(0);// �����ܼ�¼��
		}
		cursor.close();// �ر��α�
		return 0;// ���û�����ݣ��򷵻�0
	}

	/**
	 * ��ȡ��ǩ�����
	 * 
	 * @return
	 */
	public int getMaxId() {
		Cursor cursor = db.rawQuery("select max(_id) from tb_flag", null);// ��ȡ��ǩ��Ϣ���е������
		while (cursor.moveToLast()) {// ����Cursor�е����һ������
			return cursor.getInt(0);// ��ȡ���ʵ������ݣ��������
		}
		cursor.close();// �ر��α�
		return 0;// ���û�����ݣ��򷵻�0
	}
}
