package cn.jinian;

import java.util.ArrayList;
import java.util.Date;

import android.util.Log;

/**
 * 
 * @author Hesky_Fly
 * @version1.0 Date:2014-9-28
 * 
 */
public class ToDoListUtils {
	/**
	 * ���Զ��ŷָ�������ʱ���ַ���ת��Ϊ����,������ʱ���������
	 * 
	 * @param dateString
	 * @return
	 */
	public static ArrayList<Date> dateStringToArrayList(String dateString) {
		if (dateString == null || dateString.equals(""))
			return null;
		Log.i("Hwd", dateString);
		ArrayList<Date> dateArray = new ArrayList<Date>();
		String[] dateStrings = dateString.split(",");
		for (int i = 0; i < dateStrings.length; i++) {
			dateArray.add(i, new Date(Long.parseLong(dateStrings[i])));
		}
		return sortedDateArrayByDateAsc(dateArray);
	}

	/**
	 * ����ϡ����ת��Ϊ�Զ��ŷָ�������ʱ���ַ���(��ʱ�����)
	 * 
	 * @param sparseArray
	 * @return
	 */
	public static String dateArrayToDateString(ArrayList<Date> dateArray) {
		String dateString = "";
		if (dateArray == null || dateArray.size() == 0)
			dateString = null;
		else {
			dateArray=sortedDateArrayByDateAsc(dateArray); //��ʱ������
			String timeString = "";
			Date date;
			for (int i = 0; i < dateArray.size(); i++) {
				date = dateArray.get(i);
				timeString = String.valueOf(date.getTime());
				dateString = dateString.equals("") ? timeString : dateString
						+ "," + timeString;
			}
		}
		return dateString;
	}

	/**
	 * ������������ɾ��ĳһ���ڣ���һ�γ��ֵ����ڣ�
	 * @param dateArray
	 * @param date
	 * @return
	 */
	public static ArrayList<Date> deleteDateFromDateArray(ArrayList<Date> dateArray,Date date){
		if (dateArray == null || dateArray.size() == 0)
			return null;
		for(Date d:dateArray){
			if(d.equals(date))  {
				dateArray.remove(d);
				break;
			}
		}
		return dateArray;
	}
	/**
	 * �����������н���һ�γ��ֵ�ĳ�����ڸ���Ϊ��һ������
	 * @param dateArray
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public static ArrayList<Date> changeDateFromDateArray(ArrayList<Date> dateArray,Date dateFrom,Date dateTo){
		if (dateArray == null || dateArray.size() == 0)
			return null;
		for(int i=0;i<dateArray.size();i++){
			if(dateFrom.equals(dateArray.get(i))) {
				dateArray.set(i, dateTo);
				break;
			}
		}
		return dateArray;
	}
	/**
	 * ����ϡ���鰴ʱ�������˳������
	 * 
	 * @param dateSparseArray
	 * @return
	 */
	public static ArrayList<Date> sortedDateArrayByDateAsc(
			ArrayList<Date> dateArray) {
		if (dateArray == null || dateArray.size() == 0)
			return null;
		Date date_i, date_j, date_tmp;
		for (int i = 0; i < dateArray.size() - 1; i++) {
			date_i = dateArray.get(i);
			for (int j = i + 1; j < dateArray.size(); j++) {
				date_j = dateArray.get(j);
				if (date_i.after(date_j)) {
					date_tmp = date_i;
					dateArray.set(i, date_j);
					dateArray.set(j, date_tmp);
				}
			}
		}
		return dateArray;
	}
}
