package cn.jinian;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.util.Log;

/**
 * �����������
 * 
 * @author Hesky_Fly Date:2014-09-27
 */
public class ToDoItem implements Serializable {

	private static final long serialVersionUID = 3199251197683853468L;
	private long _id;
	private String task; // ������������
	private Date createTime; // ����ʱ��(���ڼ�ʱ��)
	private Date time; // ��������ʱ��
	private int duplicate; // �ظ����ڣ�0��ʾ������,1��ʾÿ���ظ���2��ʾÿ���ظ���
							// 3��ʾÿ���ظ���4��ʾÿ���ظ�
	private String alertTime; // ����ʱ��,���������ʱ���м��ö��ŷָ�����
	private int priority; // ���ȼ�,0��ʾ�����ȼ���1��ʾ�����ȼ���2��ʾ�����ȼ�

	public ToDoItem(String task) {
		this(task, new Date(java.lang.System.currentTimeMillis()));
	}

	public ToDoItem(String task, Date createTime) {
		super();
		this.task = task;
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
		return "(" + sdf.format(createTime) + ")" + task;
	}

	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}

	public Date getCreateDate() {
		return createTime;
	}

	public void setCreateDate(Date createTime) {
		this.createTime = createTime;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public void setTime(int year, int month, int day) {
		Calendar c = Calendar.getInstance();
		if (time != null)
			c.setTime(time);
		c.set(year, month, day);
		time = c.getTime();
	}

	public String getAlertTime() {
		return alertTime;
	}

	public void setAlertTime(String alertTime) {
			this.alertTime = alertTime;
	}

	// ��������һ������ʱ��
	public void addAlertTime(Date date) {
		if (alertTime == null)
			alertTime = String.valueOf(date.getTime());
		else
			alertTime = alertTime + "," + String.valueOf(date.getTime());

		Log.i("Hwd", alertTime);
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public int getDuplicate() {
		return duplicate;
	}

	public void setDuplicate(int duplicate) {
		this.duplicate = duplicate;
	}

	public long get_id() {
		return _id;
	}

	public void set_id(long _id) {
		this._id = _id;
	}
}
