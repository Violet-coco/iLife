package com.uangel.suishouji;

import com.uangel.suishouji.R;
import com.uangel.suishouji.R.color;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class IndexActivity extends Activity {
	GridView gvInfo;// 创建GridView对象
	// 定义字符串数组，存储系统功能
	String[] titles = new String[] { "i记账", "i闹钟", "i涂鸦", "i日记", 
			"i日历","i纪念日", "iBMI", "iplan","i帮助" };
	// 定义int数组，存储功能对应的图标
	int[] images = new int[] { R.drawable.note,
			R.drawable.clock, R.drawable.tuya,
			R.drawable.dairy, R.drawable.date, R.drawable.memory,
			R.drawable.bmi, R.drawable.plan,R.drawable.help };

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
        
		gvInfo = (GridView) findViewById(R.id.gvInfo);// 获取布局文件中的gvInfo组件
		pictureAdapter adapter = new pictureAdapter(titles, images, this);// 创建pictureAdapter对象
		gvInfo.setAdapter(adapter);// 为GridView设置数据源
		gvInfo.setOnItemClickListener(new OnItemClickListener() {// 为GridView设置项单击事件
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent = null;// 创建Intent对象
				switch (arg2) {
				case 0:
					intent = new Intent(IndexActivity.this, MainActivity.class);// 使用MainActivity窗口初始化Intent
					startActivity(intent);// 打开MainActivity
					break;
				case 1:
					intent = new Intent(IndexActivity.this, DeskClockMainActivity.class);// 使用DeskClockMainActivity窗口初始化Intent
					startActivity(intent);// 打开 DeskClockMainActivity
					break;
				case 2:
					intent = new Intent(IndexActivity.this, com.example.yh.scrawl.MainActivity.class);// 使用Outaccountinfo窗口初始化Intent
					startActivity(intent);// 打开Outaccountinfo
					break;
				case 3:
					intent = new Intent(IndexActivity.this, com.mingrisoft.activity.Accountflag.class);
					startActivity(intent);
					break;
				case 4:
					intent = new Intent(IndexActivity.this, com.ecology.calenderproj.ui.CalendarActivity.class);
					startActivity(intent);
					break;
				case 5:
					intent = new Intent(IndexActivity.this, cn.jinian.ToDoActivity.class);// 使用Sysset窗口初始化Intent
					startActivity(intent);// 打开Sysset
					break;
				case 6:
					intent = new Intent(IndexActivity.this, cn.bdqn.bmicalculator.activity.MainActivity.class);
					startActivity(intent);
					break;
				case 7:
					intent = new Intent(IndexActivity.this, com.example.class_24_c4_0508_01_memo.MainActivity.class);// 使用Help窗口初始化Intent
					startActivity(intent);// 打开Help
					break;
				case 8:
					intent = new Intent(IndexActivity.this, com.example.help.Help.class);// 使用Help窗口初始化Intent
					startActivity(intent);// 打开Help
					break;
				}
			}
		});
	}
}

class pictureAdapter extends BaseAdapter{// 创建基于BaseAdapter的子类
	private LayoutInflater inflater;// 创建LayoutInflater对象
	private List<Picture> pictures;// 创建List泛型集合

	// 为类创建构造函数
	public pictureAdapter(String[] titles, int[] images, Context context) {
		super();
		pictures = new ArrayList<Picture>();// 初始化泛型集合对象
		inflater = LayoutInflater.from(context);// 初始化LayoutInflater对象
		for (int i = 0; i < images.length; i++){// 遍历图像数组
			Picture picture = new Picture(titles[i], images[i]);// 使用标题和图像生成Picture对象
			pictures.add(picture);// 将Picture对象添加到泛型集合中
		}
	}

	@Override
	public int getCount() {// 获取泛型集合的长度
		if (null != pictures) {// 如果泛型集合不为空
			return pictures.size();// 返回泛型长度
		} else {
			return 0;// 返回0
		}
	}

	@Override
	public Object getItem(int arg0) {
		return pictures.get(arg0);// 获取泛型集合指定索引处的项
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;// 返回泛型集合的索引
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		ViewHolder viewHolder;// 创建ViewHolder对象
		if (arg1 == null){// 判断图像标识是否为空
			arg1 = inflater.inflate(R.layout.gvitem, null);// 设置图像标识
			viewHolder = new ViewHolder();// 初始化ViewHolder对象
			viewHolder.title = (TextView) arg1.findViewById(R.id.ItemTitle);// 设置图像标题
			viewHolder.image = (ImageView) arg1.findViewById(R.id.ItemImage);// 设置图像的二进制值
			arg1.setTag(viewHolder);// 设置提示
		} else {
			viewHolder = (ViewHolder) arg1.getTag();// 设置提示
		}
		viewHolder.title.setText(pictures.get(arg0).getTitle());// 设置图像标题
		viewHolder.image.setImageResource(pictures.get(arg0).getImageId());// 设置图像的二进制值
		return arg1;// 返回图像标识
	}
}

class ViewHolder{// 创建ViewHolder类
	public TextView title;// 创建TextView对象
	public ImageView image;// 创建ImageView对象
}

class Picture{// 创建Picture类
	private String title;// 定义字符串，表示图像标题
	private int imageId;// 定义int变量，表示图像的二进制值

	public Picture(){// 默认构造函数
		super();
	}

	public Picture(String title, int imageId){// 定义有参构造函数
		super();
		this.title = title;// 为图像标题赋值
		this.imageId = imageId;// 为图像的二进制值赋值
	}

	public String getTitle() {// 定义图像标题的可读属性
		return title;
	}

	public void setTitle(String title) {// 定义图像标题的可写属性
		this.title = title;
	}

	public int getImageId() {// 定义图像二进制值的可读属性
		return imageId;
	}

	public void setimageId(int imageId) {// 定义图像二进制值的可写属性
		this.imageId = imageId;
	}
}