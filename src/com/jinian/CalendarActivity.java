package com.jinian;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.uangel.suishouji.R;


import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

/**
 * 日历显示activity
 * 
 * 
 */
public class CalendarActivity extends Activity {

	private GestureDetector gestureDetector = null;
	private CalendarAdapter calV = null;
	private GridView gridView = null;
	private TextView topText = null;
	private static int jumpMonth = 0; // 每次滑动，增加或减去�?���?默认�?（即显示当前月）
	private static int jumpYear = 0; // 滑动跨越�?��，则增加或�?减去�?��,默认�?(即当前年)
	private int year_c = 0;
	private int month_c = 0;
	private int day_c = 0;
	private String currentDate = "";
	private Bundle bd = null;// 发�?参数
	private String ruzhuTime;
	private String lidianTime;
	private String state = "";

	private TextView nextMonth; // 下一月文本框
	private TextView preMonth; // 上一月文本框
	private TextView selectDateAndTime;
	private TimePicker timePicker;
	public CalendarActivity() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d", Locale.CHINA);
		currentDate = sdf.format(date); // 当期日期
		year_c = Integer.parseInt(currentDate.split("-")[0]);
		month_c = Integer.parseInt(currentDate.split("-")[1]);
		day_c = Integer.parseInt(currentDate.split("-")[2]);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.calendar);
	
		gestureDetector = new GestureDetector(this, new MyGestureListener());
		calV = new CalendarAdapter(this, getResources(), new Date(),jumpMonth, jumpYear,
				year_c, month_c, day_c);
		addGridView();
		gridView.setAdapter(calV);

		topText = (TextView) findViewById(R.id.tv_month);
		addTextToTopTextView(topText);
		nextMonth = (TextView) this.findViewById(R.id.right_img);
		preMonth = (TextView) this.findViewById(R.id.left_img);
		nextMonth.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				addGridView(); // 添加�?��gridView
				jumpMonth++; // 下一个月

				calV = new CalendarAdapter(CalendarActivity.this,
						getResources(), new Date(),jumpMonth, jumpYear, year_c, month_c,
						day_c);
				gridView.setAdapter(calV);
				addTextToTopTextView(topText);
			}			
		});
	
		preMonth.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				addGridView(); // 添加�?��gridView
				jumpMonth--; // 上一个月

				calV = new CalendarAdapter(CalendarActivity.this,
						getResources(), new Date(),jumpMonth, jumpYear, year_c, month_c,
						day_c);
				gridView.setAdapter(calV);
				// gvFlag++;
				addTextToTopTextView(topText);
			}			
		});
	}

	/**
	 * 创建菜单
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		menu.add(0, Menu.FIRST, Menu.FIRST, "今天");
		return super.onCreateOptionsMenu(menu);
	}

	/**
	 * 选择菜单
	 */
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
		case Menu.FIRST:
			// 跳转到今�?			jumpMonth = 0;
			jumpYear = 0;
			addGridView(); // 添加�?��gridView
			year_c = Integer.parseInt(currentDate.split("-")[0]);
			month_c = Integer.parseInt(currentDate.split("-")[1]);
			day_c = Integer.parseInt(currentDate.split("-")[2]);
			calV = new CalendarAdapter(CalendarActivity.this, getResources(), new Date(),jumpMonth,
					jumpYear, year_c, month_c, day_c);
			gridView.setAdapter(calV);
			addTextToTopTextView(topText);
			break;
		}
		return super.onMenuItemSelected(featureId, item);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		return this.gestureDetector.onTouchEvent(event);
	}

	// 添加头部的年�?闰哪月等信息
	public void addTextToTopTextView(TextView view) {
		StringBuffer textDate = new StringBuffer();
		textDate.append(calV.getShowYear()).append("�?")
				.append(calV.getShowMonth()).append("�?").append("\t");
		view.setText(textDate);
		view.setTextColor(Color.WHITE);
		view.setTypeface(Typeface.DEFAULT_BOLD);
	}

	// 添加gridview
	private void addGridView() {

		gridView = (GridView) findViewById(R.id.gridview);
		gridView.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				v.performClick();
				// 将Gridview中的触摸事件回传给gestureDetector
				return CalendarActivity.this.gestureDetector
						.onTouchEvent(event);
			}
		});

		gridView.setOnItemClickListener(new OnItemClickListener() {
			// gridView中的每一个item的点击事�?
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// 点击任何�?��item，得到这个item的日�?排除点击的是周日到周�?点击不响�?)
				int startPosition = calV.getStartPositon();
				int endPosition = calV.getEndPosition();
				if (startPosition <= position + 7
						&& position <= endPosition - 7) {
					String scheduleDay = calV.getDateByClickItem(position)
							.split("\\.")[0]; // 这一天的阳历
					// String scheduleLunarDay =
					// calV.getDateByClickItem(position).split("\\.")[1];
					// //这一天的阴历
					// String scheduleYear = calV.getShowYear();
					String scheduleMonth = calV.getShowMonth();
					Toast.makeText(
							CalendarActivity.this,
							calV.getShowYear() + "-" + scheduleMonth + "-"
									+ scheduleDay, Toast.LENGTH_LONG).show();
					ruzhuTime = scheduleMonth + "�?" + scheduleDay + "�?";
					lidianTime = scheduleMonth + "�?" + scheduleDay + "�?";
					// Intent intent=new Intent();
					if (state.equals("ruzhu")) {

						bd.putString("ruzhu", ruzhuTime);
						System.out.println("ruzhuuuuuu" + bd.getString("ruzhu"));
					} else if (state.equals("lidian")) {

						bd.putString("lidian", lidianTime);
					}
					// intent.setClass(CalendarActivity.this,
					// HotelActivity.class);
					// intent.putExtras(bd);
					// startActivity(intent);
					// finish();
				}
			}
		});
	}

	private class MyGestureListener extends
			GestureDetector.SimpleOnGestureListener {
		// 用户（轻触触摸屏后）松开，由�?��1个MotionEvent ACTION_UP触发
		@Override
		public boolean onSingleTapUp(MotionEvent e) {
			return super.onSingleTapUp(e);
		}

		// 用户长按触摸屏，由多个MotionEvent ACTION_DOWN触发
		@Override
		public void onLongPress(MotionEvent e) {
			super.onLongPress(e);
		}

		// 用户按下触摸屏，并拖动，�?个MotionEvent ACTION_DOWN,
		// 多个ACTION_MOVE触发
		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2,
				float distanceX, float distanceY) {
			return super.onScroll(e1, e2, distanceX, distanceY);
		}

		// 用户按下触摸屏�?快�?移动后松�?���?个MotionEvent ACTION_DOWN,
		// 多个ACTION_MOVE, 1个ACTION_UP触发
		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			// int gvFlag = 0; //每次添加gridview到viewflipper中时给的标记
			if (e1.getX() - e2.getX() > 120) {
				// 向左滑动
				nextMonth.performClick();
				return true;
			} else if (e1.getX() - e2.getX() < -120) {
				// 向右滑动
				preMonth.performClick();
				return true;
			}
			return false;
		}

		// 用户轻触触摸屏，尚未松开或拖动，由一�?个MotionEvent ACTION_DOWN触发
		// 注意和onDown()的区别，强调的是没有松开或�?拖动的状�?		@Override
		public void onShowPress(MotionEvent e) {
			super.onShowPress(e);
		}

		// 用户轻触触摸屏，�?个MotionEvent ACTION_DOWN触发
		@Override
		public boolean onDown(MotionEvent e) {
			return super.onDown(e);
		}

		// 双击的第二下Touch down时触�?		@Override
		public boolean onDoubleTap(MotionEvent e) {
			return super.onDoubleTap(e);
		}

		// 双击的第二下Touch down和up都会触发，可用e.getAction()区分
		@Override
		public boolean onDoubleTapEvent(MotionEvent e) {
			return super.onDoubleTapEvent(e);
		}

		// 点击�?��稍微慢点�?不滑�?Touch Up:
		// onDown->onShowPress->onSingleTapUp->onSingleTapConfirmed
		@Override
		public boolean onSingleTapConfirmed(MotionEvent e) {
			return super.onSingleTapConfirmed(e);
		}

	}
}