package cn.jinian;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.jinian.CalendarAdapter;
import com.uangel.suishouji.R;

import android.app.DatePickerDialog.OnDateSetListener;
import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class CalendarFragment extends DialogFragment implements
OnDateSetListener {
	private GestureDetector gestureDetector = null;
	private CalendarAdapter calV = null;
	private GridView gridView = null;
	private TextView topText = null;
	private int jumpMonth = 0; // ÿ�λ��������ӻ��ȥһ����,Ĭ��Ϊ0������ʾ��ǰ�£�
	private int jumpYear = 0; // ������Խһ�꣬�����ӻ��߼�ȥһ��,Ĭ��Ϊ0(����ǰ��)
	private int year_c = 0;
	private int month_c = 0;
	private int day_c = 0;

	private Date currentDate;

	private TextView nextMonth; // ��һ���ı���
	private TextView preMonth; // ��һ���ı���
	private Button cancelBtn; // ȡ����ť
	//private Calendar calendar_c; // ��ǰ����

	public CalendarFragment(Date date) {
		super();
		currentDate = date;
//		calendar_c = Calendar.getInstance();
//		calendar_c.setTime(date);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d", Locale.CHINA);
		String currentDateString = sdf.format(date); // ��������
		year_c = Integer.parseInt(currentDateString.split("-")[0]);
		month_c = Integer.parseInt(currentDateString.split("-")[1]);
		day_c = Integer.parseInt(currentDateString.split("-")[2]);
		// year_c=calendar_c.get(Calendar.YEAR);
		// month_c=calendar_c.get(Calendar.MONTH)+1;
		// day_c=calendar_c.get(Calendar.DAY_OF_MONTH);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		final View calendarV = inflater.inflate(R.layout.calendar, container,
				false);
		gestureDetector = new GestureDetector(this.getActivity(),
				new MyGestureListener());
		calV = new CalendarAdapter(this.getActivity(), getResources(),
				currentDate, jumpMonth, jumpYear, year_c, month_c, day_c);
		addGridView(calendarV);
		gridView.setAdapter(calV);

		topText = (TextView) calendarV.findViewById(R.id.tv_month);
		addTextToTopTextView(topText);
		nextMonth = (TextView) calendarV.findViewById(R.id.right_img);
		preMonth = (TextView) calendarV.findViewById(R.id.left_img);
		nextMonth.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.i("Hwd", "kjkkjk");
				addGridView(calendarV); // ���һ��gridView
				jumpMonth++; // ��һ����

				calV = new CalendarAdapter(CalendarFragment.this.getActivity(),
						getResources(), currentDate, jumpMonth, jumpYear,
						year_c, month_c, day_c);
				gridView.setAdapter(calV);
				addTextToTopTextView(topText);
			}
		});

		preMonth.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				addGridView(calendarV); // ���һ��gridView
				jumpMonth--; // ��һ����

				calV = new CalendarAdapter(CalendarFragment.this.getActivity(),
						getResources(), currentDate, jumpMonth, jumpYear,
						year_c, month_c, day_c);
				gridView.setAdapter(calV);
				addTextToTopTextView(topText);
			}
		});
		cancelBtn = (Button) calendarV.findViewById(R.id.cancelBtn);
		cancelBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				CalendarFragment.this.dismiss();
			}
		});
		return calendarV;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ���setCancelable()�в���Ϊtrue�������dialog���ǲ�����activity�Ŀհ׻��߰����ؼ��������cancel��״̬�������onCancel()��onDismiss()�������Ϊfalse���򰴿հ״��򷵻ؼ��޷�Ӧ��ȱʡΪtrue
		setCancelable(false);
		// ��������dialog����ʾ�����styleΪSTYLE_NO_TITLE��������ʾtitle��themeΪ0����ʾ��ϵͳѡ����ʵ�theme��
		int style = DialogFragment.STYLE_NO_TITLE, theme = 0;
		setStyle(style, theme);
	}

	// ���ͷ������� �����µ���Ϣ
	public void addTextToTopTextView(TextView view) {
		StringBuffer textDate = new StringBuffer();
		textDate.append(calV.getShowYear()).append("��")
				.append(calV.getShowMonth()).append("��").append("\t");
		view.setText(textDate);
		view.setTextColor(Color.WHITE);
		view.setTypeface(Typeface.DEFAULT_BOLD);
	}

	// ���gridview
	private void addGridView(View view) {

		gridView = (GridView) view.findViewById(R.id.gridview);
		gridView.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				v.performClick();
				// ��GridView�еĴ����¼��ش���gestureDetector
				return CalendarFragment.this.gestureDetector
						.onTouchEvent(event);
			}
		});
		gridView.setOnItemClickListener(new OnItemClickListener() {
			// gridView�е�ÿһ��item�ĵ���¼�

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// ����κ�һ��item���õ����item������(�ų�����������յ�����(�������Ӧ))
				int startPosition = calV.getStartPositon();
				int endPosition = calV.getEndPosition();
				if (startPosition <= position + 7
						&& position <= endPosition - 7) {
					String scheduleDay = calV.getDateByClickItem(position)
							.split("\\.")[0]; // ��һ�������
					// String scheduleLunarDay =
					// calV.getDateByClickItem(position).split("\\.")[1];
					// //��һ�������
					// String scheduleYear = calV.getShowYear();
					String scheduleMonth = calV.getShowMonth();

//					calendar_c.set(Calendar.YEAR,
//							Integer.parseInt(calV.getShowYear()));
//					calendar_c.set(Calendar.MONTH,
//							Integer.parseInt(scheduleMonth) - 1);
//					calendar_c.set(Calendar.DAY_OF_MONTH,
//							Integer.parseInt(scheduleDay));
					onDateSet(null, Integer.parseInt(calV.getShowYear()), Integer.parseInt(scheduleMonth) - 1, Integer.parseInt(scheduleDay));
					CalendarFragment.this.dismiss();
				}
			}
		});
	}

	private class MyGestureListener extends
			GestureDetector.SimpleOnGestureListener {
		// �û����ᴥ���������ɿ�����һ��1��MotionEvent ACTION_UP����
		@Override
		public boolean onSingleTapUp(MotionEvent e) {
			return super.onSingleTapUp(e);
		}

		// �û��������������ɶ��MotionEvent ACTION_DOWN����
		@Override
		public void onLongPress(MotionEvent e) {
			super.onLongPress(e);
		}

		// �û����´����������϶�����1��MotionEvent ACTION_DOWN,
		// ���ACTION_MOVE����
		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2,
				float distanceX, float distanceY) {
			return super.onScroll(e1, e2, distanceX, distanceY);
		}

		// �û����´������������ƶ����ɿ�����1��MotionEvent ACTION_DOWN,
		// ���ACTION_MOVE, 1��ACTION_UP����
		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			if (e1.getX() - e2.getX() > 120) {
				// ���󻬶�
				nextMonth.performClick();
				return true;
			} else if (e1.getX() - e2.getX() < -120) {
				// ���һ���
				preMonth.performClick();
				return true;
			}
			return false;
		}

		// �û��ᴥ����������δ�ɿ����϶�����һ��1��MotionEvent ACTION_DOWN����
		// ע���onDown()������ǿ������û���ɿ������϶���״̬
		@Override
		public void onShowPress(MotionEvent e) {
			super.onShowPress(e);
		}

		// �û��ᴥ����������1��MotionEvent ACTION_DOWN����
		@Override
		public boolean onDown(MotionEvent e) {
			return super.onDown(e);
		}

		// ˫���ĵڶ���Touch downʱ����
		@Override
		public boolean onDoubleTap(MotionEvent e) {
			return super.onDoubleTap(e);
		}

		// ˫���ĵڶ���Touch down��up���ᴥ��������e.getAction()����
		@Override
		public boolean onDoubleTapEvent(MotionEvent e) {
			return super.onDoubleTapEvent(e);
		}

		// ���һ����΢�����(������)Touch Up:
		// onDown->onShowPress->onSingleTapUp->onSingleTapConfirmed
		@Override
		public boolean onSingleTapConfirmed(MotionEvent e) {
			return super.onSingleTapConfirmed(e);
		}
	}

	@Override
	public void onDateSet(DatePicker view, int year, int monthOfYear,
			int dayOfMonth) {
		
	}
}
