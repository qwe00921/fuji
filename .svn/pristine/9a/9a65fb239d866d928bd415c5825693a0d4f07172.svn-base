package com.fsti.fjdicClient.adapter;

import java.util.ArrayList;
import java.util.List;

import com.fsti.fjdicClient.activity.home.HomeMainActivity;
import com.fsti.fjdicClient.bean.AdvEntity;
import com.fsti.fjdicClient.util.SyncImageLoadUtil;
import com.fsti.fjdicClient.util.SyncImageLoadUtil.OnImageLoadListener;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

public class GoodsViewPagerAdapter extends PagerAdapter {
	private ArrayList<View> imgViews ;
	private List<String> urls;
	private SyncImageLoadUtil mySilu;
	private OnImageLoadListener myListener;
	private Context context;
	public GoodsViewPagerAdapter(ArrayList<View> viewList,SyncImageLoadUtil silu, List<String> urls,
			OnImageLoadListener listener , Context context) {
		// TODO Auto-generated constructor stub
		this.imgViews = viewList;
		this.urls = urls;
		this.mySilu = silu;
		this.myListener = listener;
		this.context = context;
	}
	@Override
	public void destroyItem(View arg0, int arg1, Object arg2) {
		// TODO Auto-generated method stub
		((ViewPager) arg0).removeView((View) arg2);
	}

	@Override
	public void finishUpdate(View arg0) {
		// TODO Auto-generated method stub
		// pagerView = arg0;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return urls.size();
	}

	@Override
	public Object instantiateItem(View arg0, int arg1) {
		((ViewPager) arg0).addView(imgViews.get(arg1));
		// 设置标志位
		imgViews.get(arg1).setTag(arg1);
		mySilu.loadImage(arg1, urls.get(arg1),myListener , context);
		return imgViews.get(arg1);
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
//		return arg0 == arg1;
		
		return arg0.equals(arg1);//wang...
	}

	@Override
	public void restoreState(Parcelable arg0, ClassLoader arg1) {
	}

	@Override
	public Parcelable saveState() {
		return null;
	}

	@Override
	public void startUpdate(View arg0) {
	}
}
