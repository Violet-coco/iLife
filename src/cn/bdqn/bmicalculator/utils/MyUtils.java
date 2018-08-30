package cn.bdqn.bmicalculator.utils;

import java.math.BigDecimal;

import android.content.Context;
import android.widget.Toast;

/**
 * ������
 * @author ���Ȼ
 * @since JDK 1.6
 * @version 1.0
 * @date 2013-7-24 ����1:41:07
 */
public class MyUtils {
	/**
	 * ʹ��Toast������ʾ��Ϣ
	 * @param context
	 * @param text
	 */
	public static void showMsg(Context context,String text){
		//ʹ��Toast������ʾ��Ϣ
		Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
	}
	public static double CalculateBMI(double stature,double weight){
		/*
		 * BMIֵ�ļ��㹫ʽΪ������/��ߵ�ƽ��
		 */
		double result = weight/(Math.pow(stature/100,2));
		//����һ��BigDecimal������ȷ��С�������λ
		BigDecimal bd = new BigDecimal(result);
		//��ȷ��С�������λ
		result = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		return result;
	}
}
