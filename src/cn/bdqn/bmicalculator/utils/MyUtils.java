package cn.bdqn.bmicalculator.utils;

import java.math.BigDecimal;

import android.content.Context;
import android.widget.Toast;

/**
 * 工具类
 * @author 舒浩然
 * @since JDK 1.6
 * @version 1.0
 * @date 2013-7-24 下午1:41:07
 */
public class MyUtils {
	/**
	 * 使用Toast工具显示信息
	 * @param context
	 * @param text
	 */
	public static void showMsg(Context context,String text){
		//使用Toast工具显示信息
		Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
	}
	public static double CalculateBMI(double stature,double weight){
		/*
		 * BMI值的计算公式为：体重/身高的平方
		 */
		double result = weight/(Math.pow(stature/100,2));
		//构建一个BigDecimal用来精确到小数点后两位
		BigDecimal bd = new BigDecimal(result);
		//精确到小数点后两位
		result = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		return result;
	}
}
