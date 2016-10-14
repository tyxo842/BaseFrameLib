package com.tyxo.baseframelib;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuBuilder;
import android.view.Menu;
import android.view.MenuItem;

import java.lang.reflect.Method;

/**
* @author tyxo
* @created at 2016/10/10 16:14
* @des :
*/
public abstract class BaseActivity extends AppCompatActivity {

	public String mPageName = this.getClass().getSimpleName();	// 获取类名
	/**当前Activity的上下文 */
	protected Context mContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mContext = this;
		setMContentView();

		initView();		// 初始化 view
		initListener();	// 初始化 监听
		initData();		// 初始化 数据
	}

	/**设置 Activity 使用的视图,在此方法实现里面setContentView(R.layout.xxx);*/
	protected abstract void setMContentView();

	protected void initView(){}

	protected void initListener(){}

	protected void initData(){}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				this.finish();
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		if (menu!=null) {
			if (menu.getClass()== MenuBuilder.class) {
				try{
					Method m = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
					m.setAccessible(true);
					m.invoke(menu, true);
				}catch (Exception e){
					e.printStackTrace();
				}
			}
		}
		return super.onPrepareOptionsMenu(menu);
	}

	/** activity的返回结果码 */
	private int resultCode;

	/** activity的返回结果数据 */
	private Intent intentData;

	/**  获得返回结果数据 */
	public Intent getIntentData() {
		return intentData;
	}

	/** 设置Activity返回结果 */
	public void setIntentData(Intent intentData) {
		this.intentData = intentData;
	}

	/** 获得Activity返回结果码 */
	public int getResultCode() {
		return resultCode;
	}

	/** 设置Activity返回结果码 */
	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}

	/** 结束Activity时，设置返回结果 */
	public void finishWithResult(){
		this.setResult(getResultCode(), getIntentData());
		finish();
	}

	/** 结束Activity */
	@Override
	public void finish() {
		super.finish();
		// 设置切换动画，从右边进入，右边退出
		this.overridePendingTransition(android.R.anim.fade_in,android.R.anim.slide_out_right);
		//this.overridePendingTransition(0,0);//去除动画效果
	}

	@Override
	public void startActivity(Intent intent) {
		super.startActivity(intent);
		// 设置切换动画，从右边进入，右边退出
		this.overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.fade_out);
	}
}
