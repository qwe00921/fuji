package com.fsti.fjdicClient.activity;

import java.io.Serializable;
import java.util.List;

import com.fsti.fjdicClient.R;
//import com.fsti.fjdicClient.activity.shoppingMall.GoodsListNoButtonActivity;
//import com.fsti.fjdicClient.activity.shoppingMall.ShoppingmallGoodsDetailActivity;
import com.fsti.fjdicClient.bean.GoodsDetailInfoEntity;
import com.fsti.fjdicClient.bean.SearchGoodsEntity;
import com.fsti.fjdicClient.component.PhotoView;
import com.fsti.fjdicClient.util.SyncImageLoadUtil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.FloatMath;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class MagnifyImageviewActivity extends Activity{
	private ViewPager mViewPager;
	public List<String> urls;
	public int i;
	public static SyncImageLoadUtil syncImageLoader = new SyncImageLoadUtil();
	private Context context;
	protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Bundle bundle = this.getIntent().getExtras();
		urls = (List<String>) this.getIntent().getSerializableExtra("url");
		i = this.getIntent().getExtras().getInt("position");
		mViewPager = new com.fsti.fjdicClient.component.HackyViewPager(this);
		setContentView(mViewPager);
		context = getBaseContext();
		mViewPager.setAdapter(new SamplePagerAdapter(urls , context));
		mViewPager.setCurrentItem(i);
	}

	static class SamplePagerAdapter extends PagerAdapter {
		private List<String> urls ;
		private Context context;
		public SamplePagerAdapter(List<String> urls , Context context) {
			this.urls = urls;
			this.context = context;
		}

//		private static int[] sDrawables = { R.drawable.wallpaper,
//				R.drawable.a1, R.drawable.a2, R.drawable.a3,
//				R.drawable.wallpaper, R.drawable.wallpaper };

		@Override
		public int getCount() {
			return urls.size();
		}

		@Override
		public View instantiateItem(ViewGroup container, int position) {
			PhotoView photoView = new PhotoView(container.getContext());
			photoView.setImageResource(R.drawable.image_load);
			syncImageLoader.displayImage(urls.get(position), photoView, context);

			// Now just add PhotoView to ViewPager and return it
			container.addView(photoView, LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT);

			return photoView;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}
		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}
	}
	public static void StartMagnifyImageviewActivity(Context context, List<String> urls ,int i){
	Intent intentToMagnify = new Intent(context,MagnifyImageviewActivity.class);
	Bundle bundle = new Bundle();
	bundle.putSerializable("url", (Serializable) urls);
	intentToMagnify.putExtras(bundle);
	intentToMagnify.putExtra("position", i);
	
	context.startActivity(intentToMagnify);
	
	}

//public class MagnifyImageviewActivity extends BaseActivity{
//	private ImageView ivGoods;
//	public  SyncImageLoadUtil syncImageLoader = new SyncImageLoadUtil();
//	private String url;
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		// TODO Auto-generated method stub
//		super.onCreate(savedInstanceState);
////		设置无标题  
//		requestWindowFeature(Window.FEATURE_NO_TITLE);  
//		//设置全屏  
//		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,   
//				WindowManager.LayoutParams.FLAG_FULLSCREEN); 
//		setContentView(R.layout.layout_activity_image);
//		init();
//		syncImageLoader.displayImage(url, ivGoods, getBaseContext());
//		
//	}
//	public static void StartMagnifyImageviewActivity(Context context, List<String> urls ,int i){
//		Intent intentToMagnify = new Intent(context,MagnifyImageviewActivity.class);
//		Bundle bundle = new Bundle();
//		bundle.putSerializable("url", (Serializable) urls);
//		intentToMagnify.putExtras(bundle);
//		intentToMagnify.putExtra("position", i);
//		
//		context.startActivity(intentToMagnify);
//		
//	}
//	@Override
//	public void initValue() {
//		// TODO Auto-generated method stub
//		ivGoods = (ImageView) findViewById(R.id.iv_magnify);
//		Bundle bundle = this.getIntent().getExtras();
//		url = bundle.getString("url");
//	}
//
//	@Override
//	public void bindEvent() {
//		// TODO Auto-generated method stub
//		ivGoods.setOnTouchListener(new TounchListener());  
//	}
//	 private class TounchListener implements OnTouchListener{
//
//		 private PointF startPoint = new PointF();
//	    	private Matrix matrix = new Matrix();
//	    	private Matrix currentMaritx = new Matrix();
//	    	
//	    	private int mode = 0;//用于标记模式
//	    	private static final int DRAG = 1;//拖动
//	    	private static final int ZOOM = 2;//放大
//	    	private float startDis = 0;
//	    	private PointF midPoint;//中心点
//
//			public boolean onTouch(View v, MotionEvent event) {
//				switch (event.getAction() & MotionEvent.ACTION_MASK) {
//				case MotionEvent.ACTION_DOWN:
//					mode = DRAG;
//					currentMaritx.set(ivGoods.getImageMatrix());//记录ImageView当期的移动位置
//					startPoint.set(event.getX(),event.getY());//开始点
//					break;
//
//				case MotionEvent.ACTION_MOVE://移动事件
//					ivGoods.setScaleType(ScaleType.MATRIX);
//					if (mode == DRAG) {//图片拖动事件
//						float dx = event.getX() - startPoint.x;//x轴移动距离
//						float dy = event.getY() - startPoint.y;
//						matrix.set(currentMaritx);//在当前的位置基础上移动
//						matrix.postTranslate(dx, dy);
//						
//					} else if(mode == ZOOM){//图片放大事件
//						float endDis = distance(event);//结束距离
//						if(endDis > 10f){
//							float scale = endDis / startDis;//放大倍数
//							matrix.set(currentMaritx);
//							matrix.postScale(scale, scale, midPoint.x, midPoint.y);
//						}
//						
//						
//					}
//
//					break;
//					
//				case MotionEvent.ACTION_UP:
//					mode = 0;
//					break;
//				//有手指离开屏幕，但屏幕还有触点(手指)
//				case MotionEvent.ACTION_POINTER_UP:
//					mode = 0;
//					break;
//				//当屏幕上已经有触点（手指）,再有一个手指压下屏幕
//				case MotionEvent.ACTION_POINTER_DOWN:
//					mode = ZOOM;
//					startDis = distance(event);
//					
//					if(startDis > 10f){//避免手指上有两个茧
//						midPoint = mid(event);
//						currentMaritx.set(ivGoods.getImageMatrix());//记录当前的缩放倍数
//					}
//					
//					break;
//
//
//				}
//				ivGoods.setImageMatrix(matrix);
//				return true;
//			}
//
//	 }
//	 /**
//	     * 两点之间的距离
//	     * @param event
//	     * @return
//	     */
//	    private static float distance(MotionEvent event){
//	    	//两根线的距离
//	    	float dx = event.getX(1) - event.getX(0);
//	    	float dy = event.getY(1) - event.getY(0);
//	    	return FloatMath.sqrt(dx*dx + dy*dy);
//	    }
//	    /**
//	     * 计算两点之间中心点的距离
//	     * @param event
//	     * @return
//	     */
//	    private static PointF mid(MotionEvent event){
//	    	float midx = event.getX(1) + event.getX(0);
//	    	float midy = event.getY(1) - event.getY(0);
//	    	
//	    	return new PointF(midx/2, midy/2);
//	    }

}
