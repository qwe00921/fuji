package com.fsti.fjdicClient.util;

import java.util.List;

import com.fsti.fjdicClient.R;
import com.fsti.fjdicClient.activity.home.HomeMainActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RemoteViews;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TabHost.OnTabChangeListener;

public class ViewUtil {

	private static final String TAG = ViewUtil.class.getCanonicalName();

	/**
	 * toast弹出框
	 * 
	 * @param context
	 *            上下文对象
	 * @param info
	 *            弹出框内容
	 */
	public static void showToast(Context context, String info) {
		Toast appToast = Toast.makeText(context, info, Toast.LENGTH_LONG);
		appToast.show();
	}

	public static void showShortToast(Context context, String info) {
		Toast appToast = Toast.makeText(context, info, Toast.LENGTH_SHORT);
		appToast.show();
	}

	/**
	 * 去掉系统默认的色白的底角
	 * @param context
	 * @param tabHost
	 * @param tabWidget
	 */
	public static void removeTabHostBottomLine(Context context,
			TabHost tabHost, TabWidget tabWidget) {
		for (int i = 0; i < tabWidget.getChildCount(); i++) {
			/**
			 * 
			 * 此方法是为了去掉系统默认的色白的底角
			 */
			tabHost.setPadding(tabHost.getPaddingLeft(), tabHost
					.getPaddingTop(), tabHost.getPaddingRight(), tabHost
					.getPaddingBottom() - 20);

		}
	}
	
	/**
	 * 添加加载动画
	 * @param myContext 上下文
	 * @param iv 加载图片
	 */
	public static void addLoadingAnimation(Context myContext,ImageView iv){
		iv.setVisibility(View.VISIBLE);
		Animation hyperspaceJumpAnimation = AnimationUtils
				.loadAnimation(myContext, R.anim.loading_animation);
		// 使用ImageView显示动画
		iv.startAnimation(hyperspaceJumpAnimation);
	}
	
	/**
	 * 移除加载动画
	 * @param iv 加载图片
	 */
	public static void removeLoadingAnimation(ImageView iv){
		iv.clearAnimation();
		iv.setVisibility(View.GONE);
	}


}
