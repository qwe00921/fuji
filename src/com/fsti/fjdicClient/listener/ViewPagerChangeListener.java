package com.fsti.fjdicClient.listener;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.fsti.fjdicClient.R;
//import com.fsti.communityClient.activity.home.HomeMainActivity.ScrollTask;
import com.fsti.fjdicClient.activity.groupBuy.GroupBuySortListActivity;
import com.fsti.fjdicClient.activity.home.HomeMainActivity;
//import com.fsti.fjdicClient.activity.shoppingMall.FirstLevelSortActivity;

import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.widget.ImageView;

/**
 * viewpager控件页面改变监听器
 * @author 金涛
 *
 */
public class ViewPagerChangeListener implements OnPageChangeListener{

	ImageView[] dotImageViews ;
	ViewPager viewPager;
	public ViewPagerChangeListener(ImageView[] dotImageViews)
	{
		this.dotImageViews =dotImageViews;
	}
	public ViewPagerChangeListener(ImageView[] dotImageViews, ViewPager myViewPager) {
		// TODO Auto-generated constructor stub
		this.dotImageViews =dotImageViews;
		this.viewPager = myViewPager;
	}
	@Override
	public void onPageScrollStateChanged(int arg0) {
		if (arg0 ==ViewPager.SCROLL_STATE_IDLE) {
//            // 未拖动页面时执行此处
//			FirstLevelSortActivity.isScheul = true; 
//			GroupBuySortListActivity.isScheul = true;
//			HomeMainActivity.isScheul = true;
//			Log.e("onPageScrollStateChaged---idle", "未拖动时");
////			HomeMainActivity.scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
//        } else if (arg0 ==ViewPager.SCROLL_STATE_DRAGGING) {
//            // 正在拖动页面时执行此处
//        	GroupBuySortListActivity.isScheul = false;
//        	FirstLevelSortActivity.isScheul = false; 
//        	HomeMainActivity.isScheul = false;
//        	Log.e("onPageScrollStateChaged---dragging", "拖动时");
//        }else if (arg0 == ViewPager.SCROLL_STATE_SETTLING){
//        	GroupBuySortListActivity.isScheul = true;
//        	FirstLevelSortActivity.isScheul = true; 
//        	HomeMainActivity.isScheul = true;
//        	Log.e("onPageScrollStateChaged---settling", "拖动后");
////        	HomeMainActivity.scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        }
        	
	}
	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		Log.e("+++"	,"arg0 == "+arg0+" arg1 == "+arg1+" arg2 == "+arg2);
		if(arg0 == 0 && arg1 == 0.0 && arg2 == 0){
//			HomeMainActivity.currentItem = dotImageViews.length-1;
			
		}
	}

	@Override
	public void onPageSelected(int position) {
//		if (position >= dotImageViews.length) {
//            int newPosition = position%dotImageViews.length;   
//            position = newPosition;
//		}
//		if(position <0){
//			position = -position;
//		}
//		
//		for(int i=0;i<dotImageViews.length;i++){
//			dotImageViews[position].setBackgroundResource(R.drawable.dot_selected);
//			if(position !=i){
//				dotImageViews[i].setBackgroundResource(R.drawable.dot_noselected);
//			}
//		}
//		viewPager.setCurrentItem(position,true);
//		GroupBuySortListActivity.currentItem = position;
//		HomeMainActivity.currentItem = position;
//		FirstLevelSortActivity.currentItem = position;
//		Log.e("onPageSelected", "装载成功后");
//		HomeMainActivity.scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
	}
}