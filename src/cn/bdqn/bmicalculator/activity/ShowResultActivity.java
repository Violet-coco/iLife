package cn.bdqn.bmicalculator.activity;

import com.uangel.suishouji.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TextView;
/**
 * 显示最终结果界面
 * @author 舒浩然
 * @since JDK 1.6
 * @version 1.0
 * @date 2013-7-24 下午3:34:51
 */
public class ShowResultActivity extends Activity{
	private double result;//计算结果
	private int sexId;//性别
	private TextView textview1;
	private TextView textview2;
	//主入口
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_show_result);//关联布局文件
		//得到传递的值
		result = getIntent().getExtras().getDouble("result");//结算结果
		sexId = getIntent().getExtras().getInt("sex");//性别
		//实例化控件信息
		init();
		//声明一个StringBuffer
		StringBuffer sb = new StringBuffer("(BMI指数)：");
		sb.append(result);
		//设置显示结果
		textview1.setText(sb);
		showMessage(result,sexId);
	}
	/**
	 * 实例化控件信息
	 */
	private void init(){
		textview1 = (TextView) findViewById(R.id.textview1);
		textview2 = (TextView) findViewById(R.id.textview2);
	}
	/**
	 * 根据计算结果和性别判断显示结果
	 * @param result
	 * @param sexId
	 */
	private void showMessage(double result,int sexId){
		//判断性别
		switch(sexId){
		case R.id.man://男
			//判断结果
			if(result < 20){//过轻
				textview2.setText(R.string.text1);
			}else if(result >=20 && result < 25){//适中
				textview2.setText(R.string.text2);
			}else if(result>=25 &&result<30){//过重
				textview2.setText(R.string.text3);
			}else{//肥胖
				textview2.setText(R.string.text4);
			}
			break;
		case R.id.woman://女
			//判断结果
			if(result < 19){//过轻
				textview2.setText(R.string.text1);
			}else if(result >=19 && result < 24){//适中
				textview2.setText(R.string.text2);
			}else if(result>=24 &&result<29){//过重
				textview2.setText(R.string.text3);
			}else{//肥胖
				textview2.setText(R.string.text4);
			}
			break;
		}
	}
	/**
	 * 点击返回按键后返回主界面
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK){
			finish();//关闭当前界面
		}
		return super.onKeyDown(keyCode, event);
	}
}
