package com.fsti.fjdicClient.activity;

import java.util.ArrayList;

import com.fsti.fjdicClient.R;
import com.fsti.fjdicClient.activity.home.HomeMainActivity;
import com.fsti.fjdicClient.util.PersistenceVarUtil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * 欢迎导航页
 * @author 金涛
 *
 */
public class WelcomeViewPager extends Activity implements OnClickListener {

	
	private ViewPager viewPager;  
    private ArrayList<View> pageViews;  
    private ViewGroup main, group;  
    private ImageView imageView;  
    private ImageView[] imageViews; 
    private Context myContext;
	private View lastview;
	private TextView bt_in;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        myContext = getBaseContext();
        String isShowed = PersistenceVarUtil.getApplicationStringValue(myContext, "welcome_viewpage", "isShowed", "false");
System.out.println("isShowed=" + isShowed);
        if(isShowed.equals("true")){
        	Intent intent= new Intent(WelcomeViewPager.this,SplashActivity.class);
			startActivity(intent);
			finish();
        }
        else{
        	LayoutInflater inflater = getLayoutInflater();  
            pageViews = new ArrayList<View>();  
            pageViews.add(inflater.inflate(R.layout.layout_activity_welcome_viewpager_item_01, null));  
            
            pageViews.add(inflater.inflate(R.layout.layout_activity_welcome_viewpager_item_02, null));  
            lastview = inflater.inflate(R.layout.layout_activity_welcome_viewpager_item_03, null);
            pageViews.add(lastview);  
//          pageViews.add(inflater.inflate(R.layout.layout_activity_welcome_viewpager_item_04, null));
//          pageViews.add(new View(this));
            main = (ViewGroup)inflater.inflate(R.layout.layout_activity_welcome_viewpager_main, null);
            viewPager = (ViewPager)main.findViewById(R.id.guidePages);
            viewPager.setAdapter(new GuidePageAdapter());  
            viewPager.setOnPageChangeListener(new GuidePageChangeListener());
            setContentView(main);
        }	
        
    	LayoutInflater inflater = getLayoutInflater();  
        pageViews = new ArrayList<View>();  
        View dd = inflater.inflate(R.layout.layout_activity_welcome_viewpager_item_01, null);
        LinearLayout.LayoutParams ll =  new LinearLayout.LayoutParams(200,200);
        dd.setLayoutParams(ll);
        pageViews.add(dd);  
        pageViews.add(inflater.inflate(R.layout.layout_activity_welcome_viewpager_item_02, null));  
//        pageViews.add(inflater.inflate(R.layout.layout_activity_welcome_viewpager_item_03, null));  
        lastview = inflater.inflate(R.layout.layout_activity_welcome_viewpager_item_03, null);
        pageViews.add(lastview);  
//      pageViews.add(inflater.inflate(R.layout.layout_activity_welcome_viewpager_item_04, null));
//        pageViews.add(new View(this));
        main = (ViewGroup)inflater.inflate(R.layout.layout_activity_welcome_viewpager_main, null);
        viewPager = (ViewPager)main.findViewById(R.id.guidePages);
        viewPager.setAdapter(new GuidePageAdapter());  
        viewPager.setOnPageChangeListener(new GuidePageChangeListener());
        bt_in = (TextView) lastview.findViewById(R.id.bt_in);
        bt_in.setOnClickListener(this);
        setContentView(main);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}
	
	/** 指引页面Adapter */
    class GuidePageAdapter extends PagerAdapter {  
    	  
        @Override  
        public int getCount() {  
            return pageViews.size();  
        }  
  
        @Override  
        public boolean isViewFromObject(View arg0, Object arg1) {  
            return arg0 == arg1;  
        }  
  
        @Override  
        public int getItemPosition(Object object) {  
            // TODO Auto-generated method stub  
            return super.getItemPosition(object);  
        }  
  
        @Override  
        public void destroyItem(View arg0, int arg1, Object arg2) {  
            // TODO Auto-generated method stub  
            ((ViewPager) arg0).removeView(pageViews.get(arg1));  
        }  
  
        @Override  
        public Object instantiateItem(View arg0, int arg1) {  
            // TODO Auto-generated method stub  
            ((ViewPager) arg0).addView(pageViews.get(arg1));  
            return pageViews.get(arg1);  
        }  
  
        @Override  
        public void restoreState(Parcelable arg0, ClassLoader arg1) {  
            // TODO Auto-generated method stub  
  
        }  
  
        @Override  
        public Parcelable saveState() {  
            // TODO Auto-generated method stub  
            return null;  
        }  
  
        @Override  
        public void startUpdate(View arg0) {  
            // TODO Auto-generated method stub  
  
        }  
  
        @Override  
        public void finishUpdate(View arg0) {  
            // TODO Auto-generated method stub  
        }  
    } 
    
    
    /** 指引页面改监听器 */
    class GuidePageChangeListener implements OnPageChangeListener {  
  
        @Override  
        public void onPageScrollStateChanged(int arg0) {  
            // TODO Auto-generated method stub  
  
        }  
  
        @Override  
        public void onPageScrolled(int arg0, float arg1, int arg2) {  
            // TODO Auto-generated method stub  
  
        }  
  
        @Override  
        public void onPageSelected(int arg0) {
        	//如果到最后一页，则跳到应用程序首页
//        	if(arg0 == pageViews.size()-1){       
//        		PersistenceVarUtil.setApplicationStringValue(myContext, "welcome_viewpage", "isShowed", "true");
//        		Intent intent= new Intent(WelcomeViewPager.this,HomeMainActivity.class);
//    			startActivity(intent);
//    			finish();
//        	}
        }  
    }


	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch(arg0.getId()){
		case R.id.bt_in:
    		PersistenceVarUtil.setApplicationStringValue(myContext, "welcome_viewpage", "isShowed", "true");
    		Intent intent= new Intent(WelcomeViewPager.this,SplashActivity.class);
			startActivity(intent);
			finish();
			break;
		}
	}      
}

