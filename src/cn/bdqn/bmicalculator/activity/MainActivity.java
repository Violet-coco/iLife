package cn.bdqn.bmicalculator.activity;

import com.uangel.suishouji.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import cn.bdqn.bmicalculator.utils.MyUtils;

/**
 * 主界面，输入计算页面
 * 
 * @author 舒浩然
 * @since JDK 1.6
 * @version 1.0
 * @date 2013-7-24 下午12:49:25
 */
public class MainActivity extends Activity {
	/**
	 * 控件属性
	 */
	private EditText etStature;// 身高
	private EditText etWeight;// 体重
	private RadioGroup radiogroup;// 单选按钮分组
	private Button btninput_clear;// 输入清空
	private Button btnCalculate_bmi;// 计算BMI值

	// 主入口
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_main);
		// 实例化控件信息
		init();
		// 处理事件
		btninput_clear.setOnClickListener(listener);// 输入清空点击事件
		btnCalculate_bmi.setOnClickListener(listener);// 计算BMI值
	}

	/**
	 * 实例化控件信息
	 */
	private void init() {
		etStature = (EditText) findViewById(R.id.etStature);// 身高
		etWeight = (EditText) findViewById(R.id.etWeight);// 体重
		radiogroup = (RadioGroup) findViewById(R.id.radiogroup);
		btninput_clear = (Button) findViewById(R.id.btninput_clear);// 输入清空
		btnCalculate_bmi = (Button) findViewById(R.id.btncalculate_bmi);// 计算BMI值
	}

	private View.OnClickListener listener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			// 判断点击的是哪一个按钮
			switch (v.getId()) {
			case R.id.btninput_clear:// 输入清空
				etStature.setText("");
				etStature.requestFocus();// 获得焦点
				etWeight.setText("");
				radiogroup.check(R.id.man);
				break;
			case R.id.btncalculate_bmi:// 计算BMI值
				// 判断输入不能为空
				if (etStature.getText() == null
						|| etStature.getText().toString().trim().length() == 0
						|| etWeight.getText() == null
						|| etWeight.getText().toString().trim().length() == 0) {
					//提示不能为空
					MyUtils.showMsg(MainActivity.this, getString(R.string.not_null));
				}else{
					//保存输入的值
					double stature = Double.parseDouble(etStature.getText().toString().trim());//身高
					double weight = Double.parseDouble(etWeight.getText().toString().trim());//体重
					//判断输入的数字是否小于0
					if(stature<=0 || weight<=0){
						//提示输入的数据不能小于0
						MyUtils.showMsg(MainActivity.this, getString(R.string.not_low_0));
					}else{
						//跳转界面到显示结果界面
						Intent intent = new Intent(MainActivity.this,ShowResultActivity.class);
						//传递值
						intent.putExtra("result", MyUtils.CalculateBMI(stature, weight));//结算结果
						intent.putExtra("sex", radiogroup.getCheckedRadioButtonId());//性别
						startActivity(intent);
					}
				}
				break;
			}
		}
	};
	/**
	 * 点击了返回按键后结束当前应用
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		//判断用户是否点击了返回按键
		if(keyCode == KeyEvent.KEYCODE_BACK){
			//关闭当前窗体
			finish();
			//结束应用
			System.exit(0);
//			android.os.Process.killProcess(android.os.Process.myPid());
		}
		return super.onKeyDown(keyCode, event);
	}
	
}
