package com.fsti.fjdicClient.component;

import com.fsti.fjdicClient.R;
import com.fsti.fjdicClient.dialog.TestDialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;

/**
 * 自定义弹窗
 * 使用说明:此为抽象类，需要继承该类，并实现initValue,bindEvent方法
 * 调用示例：
 * Dialog dia = new TestDialog(this, R.style.pop_dialog, R.layout.window_layout_desktop_config);
   dia.show();
 * @author 金涛
 *
 */
public abstract class CustomDialog extends Dialog {

	protected Activity myActivity;
	protected int myLayoutFlag;
	protected View myLayoutView;
	
	/**
	 * 
	 * @param act
	 * @param theme
	 * @param layout
	 */
	public CustomDialog(Activity act, int theme,int layout) {
		super(act, theme);
		this.myActivity = act;
		this.myLayoutFlag = layout;
		initDialog();
		initValue();
		bindEvent();
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 初始化对话框控件
	 */
	public abstract void initValue();
	
	/**
	 * 绑定控件事件
	 */
	public abstract void bindEvent();
	

	
	private void initDialog(){
		myLayoutView = myActivity.getLayoutInflater().inflate(myLayoutFlag, null);
	 	requestWindowFeature(Window.FEATURE_NO_TITLE);
    	DisplayMetrics dm=new DisplayMetrics();
    	(myActivity).getWindowManager().getDefaultDisplay().getMetrics(dm);
    	int width = dm.widthPixels;
    	int height = dm.heightPixels;
    	
        int w = dm.widthPixels/10*9  ;
        int h = dm.heightPixels;
        this.setCancelable(false) ;
      
        if(myLayoutView!=null)
           this.setContentView(myLayoutView, new LinearLayout.LayoutParams(w,LinearLayout.LayoutParams.FILL_PARENT)) ;
	}
	

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			dismissDialog();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	private void dismissDialog() {
		if (CustomDialog.this.isShowing()) {
			CustomDialog.this.dismiss();
		}
	}
}
