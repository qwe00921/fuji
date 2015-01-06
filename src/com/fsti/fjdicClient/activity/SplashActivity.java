/**
 * 
 */
package com.fsti.fjdicClient.activity;

import com.fsti.fjdicClient.R;
import com.fsti.fjdicClient.activity.groupBuy.GroupBuySortListActivity;
import com.fsti.fjdicClient.activity.home.HomeMainActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

/**
 * @author wls
 *
 */
public class SplashActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.layout_activity_welcome_viewpager_item_04);
 	    Handler handler = new Handler();
 	    handler.postDelayed(new Runnable()
		{
			@Override
			public void run()
			{
//				Intent intent2 = new Intent(this,.class);//...y
				
				Intent intent = new Intent(SplashActivity.this, HomeMainActivity.class);
				startActivity(intent);
				SplashActivity.this.finish();
			}
		}, 4000);
    }
}
