package com.fsti.fjdicClient.activity.home;

import com.fsti.fjdicClient.activity.groupBuy.GroupBuyMainListActivity;
import com.fsti.fjdicClient.activity.groupBuy.GroupBuySortListActivity;
//import com.fsti.fjdicClient.activity.shoppingMall.FirstLevelSortActivity;
import com.fsti.fjdicClient.adapter.GroupBuySortListAdapter;
import com.umeng.common.Log;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.Gallery;

public class GalleryFlow extends Gallery {

	public GalleryFlow(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
    
    private boolean isScrollingLeft(MotionEvent e1, MotionEvent e2) {
		return e2.getX() > e1.getX();
	}
    
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
    		float velocityY) {
    	android.util.Log.e("gallery", "gallery ---start");
    	int keyCode;
		if (isScrollingLeft(e1, e2)) {
			keyCode = KeyEvent.KEYCODE_DPAD_LEFT;
		} else {
			keyCode = KeyEvent.KEYCODE_DPAD_RIGHT;
		}
		onKeyDown(keyCode, null);
		if(HomeMainActivity.homestart.equals("start")){
			HomeMainActivity.stopSwitch();
			HomeMainActivity.autoSwitch();
		}
//		if(FirstLevelSortActivity.fisrststart.equals("start")){
//			FirstLevelSortActivity.stopSwitch();
//			FirstLevelSortActivity.autoSwitch();
//		}
		if(GroupBuySortListActivity.groupstart.equals("start")){
			GroupBuySortListActivity.stopSwitch();
			GroupBuySortListActivity.autoSwitch();
		}
		android.util.Log.e("gallery", "gallery ---end");
    	return false;
    }
}