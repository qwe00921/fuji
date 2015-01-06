package com.fsti.fjdicClient.activity.groupBuy;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.json.JSONArray;
import org.json.JSONObject;
import com.fsti.fjdicClient.R;
import com.fsti.fjdicClient.ApplicationUtil;
import com.fsti.fjdicClient.activity.BaseActivity;
import com.fsti.fjdicClient.activity.home.GalleryFlow;
import com.fsti.fjdicClient.activity.home.HomeMainActivity;
import com.fsti.fjdicClient.activity.login.LoginActivity;
import com.fsti.fjdicClient.activity.more.MoreActivity;
import com.fsti.fjdicClient.activity.mycenter.MycenterMainActivity;
import com.fsti.fjdicClient.activity.search.SearchMainActivity;
//import com.fsti.fjdicClient.activity.shoppingMall.FirstLevelSortActivity;
//import com.fsti.fjdicClient.activity.shoppingMall.GoodsListNoButtonActivity;
//import com.fsti.fjdicClient.activity.shoppingMall.ShoppingmallGoodsDetailActivity;
import com.fsti.fjdicClient.activity.shoppingcart.ShoppingcartMainListActivity;
import com.fsti.fjdicClient.adapter.AdvViewPagerAdapter;
import com.fsti.fjdicClient.adapter.GroupBuySortListAdapter;
import com.fsti.fjdicClient.bean.AdvEntity;
import com.fsti.fjdicClient.bean.CommunityGoodsCategoryEntity;
import com.fsti.fjdicClient.bean.CommunityGoodsUpdateCategoryList;
import com.fsti.fjdicClient.bean.ExitApplication;
import com.fsti.fjdicClient.bean.GoodsEntity;
import com.fsti.fjdicClient.bean.GroupBuyingGoodsCategoryEntity;
import com.fsti.fjdicClient.bean.PopSortEntity;
import com.fsti.fjdicClient.component.AdvertiseViewPagerService;
import com.fsti.fjdicClient.dao.BusinessDao;
import com.fsti.fjdicClient.dao.CommonDao;
import com.fsti.fjdicClient.listener.ViewPagerChangeListener;
import com.fsti.fjdicClient.util.FixedSpeedScroller;
import com.fsti.fjdicClient.util.GlobalVarUtil;
import com.fsti.fjdicClient.util.HttpUtil;
import com.fsti.fjdicClient.util.ScreenManager;
import com.fsti.fjdicClient.util.SyncImageLoadUtil;
import com.fsti.fjdicClient.util.ViewUtil;
import com.fsti.fjdicClient.util.asyncUtil.CallEarliest;
import com.fsti.fjdicClient.util.asyncUtil.Callable;
import com.fsti.fjdicClient.util.asyncUtil.Callback;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.GestureDetector.OnGestureListener;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;
import android.widget.AdapterView.OnItemClickListener;

/**
 *生活服务分类列表
 * 
 *
 */
public class GroupBuySortListActivity extends BaseActivity implements OnItemClickListener, OnClickListener {
	private ImageView ivspline;
	private LinearLayout lladv; 
	private TextView tvOpenAdv;
	private boolean isShowAdv = true;
	private LinearLayout llContain;
	
	//采用自定义gallery
	public static String groupstart= "start";
	public static GalleryFlow advGallery;
	public static Timer timer;
	public static TimerTask task;
	public static Timer timers;
	public static TimerTask tasks;
	public static int interval;
	public static int index = 0;
	private static int advcounts = 0;
	public static boolean isStop = true ;
	public static String FLAGTOSHOPPING = "";
	public static String FLAGTOGROUP = "";
	public  SyncImageLoadUtil syncImageLoader =null;
	
	private Button back;
	public static ScreenManager screenM = new ScreenManager();
	private ImageView ivLoading;
	private ListView myListView;
	private String lastUpdatetimeSort;
	private String currentUpdatetimeSort;
	private String lastUpdatetimeAdv;
	private String currentUpdatetimeAdv;
	private TextView tvLoad;
	private List<AdvEntity> myAdvList = new ArrayList<AdvEntity>(); 
	private SyncImageLoadUtil.OnImageLoadListener imageLoadListener;
	
	private List<GroupBuyingGoodsCategoryEntity> currentLevelList;

	private LinearLayout tohome;
	private LinearLayout tosearch;
	private LinearLayout toshoppingcart;
	private LinearLayout tomycenter;
	private LinearLayout tomore;
	private RelativeLayout bottommenu;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		groupstart= "start";
		HomeMainActivity.homestart = "stop";
//		FirstLevelSortActivity.fisrststart = "stop";
		setContentView(R.layout.layout_activity_groupbuy_sortlist);
		ExitApplication.getInstance().addActivity(this);
		init();
		postSortInfo();
		postAdvInfo();
	}

	@Override
	public void bindEvent() {
		// TODO Auto-generated method stub
		back.setOnClickListener(this);
		myListView.setOnItemClickListener(this);
		tohome.setOnClickListener(this);
		tosearch.setOnClickListener(this);
		toshoppingcart.setOnClickListener(this);
		tomycenter.setOnClickListener(this);
		tomore.setOnClickListener(this);
		lladv.setOnClickListener(this);
		advGallery.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				arg2 = arg2%myAdvList.size();
				AdvEntity advEntity = myAdvList.get(arg2);
				Log.e("firsthome","广告点击： "+advEntity.getIdentifier()+"  "+advEntity.getImageUrl());
				switch(myAdvList.get(arg2).getIdentifier()){
				case 4:
					break;
//				case 3:
//					GoodsListNoButtonActivity.advToGoodsListActivity(myContext, advEntity, "adv");
//					break;
				case 2:
					HomeMainActivity.FLAGTOGROUP = "ADV";
					Intent toGroup = new Intent(myContext ,GroupBuyDetailActivity.class);
					Bundle bundle2 = new Bundle();
					bundle2.putSerializable("advEntity", advEntity);
					toGroup.putExtras(bundle2);
					System.out.println("广告ID  "+ advEntity.getAdvID());
					arg1.getContext().startActivity(toGroup);
					break;
//				case 1:
//					HomeMainActivity.FLAGTOSHOPPING = "ADV";
//					Intent toShoppingmall = new Intent(myContext ,ShoppingmallGoodsDetailActivity.class);
//					Bundle bundle1 = new Bundle();
//					bundle1.putSerializable("advEntity", advEntity);
//					toShoppingmall.putExtras(bundle1);
//					System.out.println("广告ID  "+ advEntity.getAdvID());
//					arg1.getContext().startActivity(toShoppingmall);
//					break;
				}
				Log.e("item ", "click item========");
			}
		});
	}

	@Override
	public void initValue() {
		// TODO Auto-generated method stub
		ivspline = (ImageView) findViewById(R.id.iv_groupbuysort_spline);
		lladv = (LinearLayout) findViewById(R.id.ll_groupbuy_sort);
		tvOpenAdv = (TextView) findViewById(R.id.tv_groupbuy_sort1);
		llContain = (LinearLayout) findViewById(R.id.ll_viewpager_container_sort_first_level1);
		
		RelativeLayout.LayoutParams lp1 = (android.widget.RelativeLayout.LayoutParams) llContain.getLayoutParams();
		int height = HomeMainActivity.screenWidth*1/2;
		lp1.height = height;
		llContain.setLayoutParams(lp1);
		
		isStop = false;
		interval = 6000;
		back= (Button) findViewById(R.id.btn_groupbuy_sortlist_back);
		ivLoading = (ImageView) findViewById(R.id.iv_loading1);
		myListView = (ListView) findViewById(R.id.list_sort_first_level1);
		myListView.setDivider(null);
		tvLoad = (TextView) findViewById(R.id.tv_sort_first_load1);
		 
		advGallery = (com.fsti.fjdicClient.activity.home.GalleryFlow)findViewById(R.id.gall_groupbuysort_screenGallery);
		
		LayoutInflater	mInflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    View view2  = mInflater.inflate(R.layout.layout_item_bottom_menu,null);
	    tohome=(LinearLayout)view2.findViewById(R.id.ll_tohome);
	    tosearch=(LinearLayout) view2.findViewById(R.id.ll_tosearch);
	    toshoppingcart=(LinearLayout) view2.findViewById(R.id.ll_toshoppingcart);
	    tomycenter=(LinearLayout) view2.findViewById(R.id.ll_tomycenter);
	    tomore=(LinearLayout) view2.findViewById(R.id.ll_tomore);
	    bottommenu=(RelativeLayout) findViewById(R.id.rl_groupbuy_sortlist_bottommenu);
	    bottommenu.addView(view2);
	    
	    ViewGroup.LayoutParams lp =  view2.getLayoutParams();
	    lp.width = lp.FILL_PARENT;
	    view2.setLayoutParams(lp);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		groupstart= "start";
		screenM.popAllActivity();
		if(isStop){
			autoSwitch();
		}
		super.onResume();
		
	}
	@Override
	protected void onDestroy() {
		groupstart= "stop";
        stopSwitch();
		super.onDestroy();
	}
	@Override
	public void onStart(){
		groupstart= "start";
		if(isStop){
			autoSwitch();
		}

		super.onStart();
	}
	
	@Override
	public void onStop(){
		groupstart= "stop";
		stopSwitch();
		super.onStop();

	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		groupstart= "stop";
		stopSwitch();
		super.onPause();
	}


	/**
	 * 请求分类信息接口
	 */
	private void postSortInfo() {
		this.doAsync(new CallEarliest<Object>() {
			@Override
			public void onCallEarliest() throws Exception {
				// TODO Auto-generated method stub
//				ivLoading.setVisibility(View.VISIBLE);
//				ViewUtil.addLoadingAnimation(ApplicationUtil.myContext,
//						ivLoading);
				
			}

		}, new Callable<Object>() {
			@Override
			public String call() {
				String reqUrl =  getString(R.string.getGroupBuyingGoodsCategoryInfo_http);

				Map<String, Object> parameters = new LinkedHashMap<String, Object>();
				String updatetime = "";
 				parameters.put("updatetime",updatetime );
				//parameters.put("updateTime", lastUpdatetimeSort);
				String jsonStr = "";
				try {
					jsonStr = HttpUtil.postData(reqUrl, parameters,
							GlobalVarUtil.ENCODING);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					Log.e("FirstLevelSortActivity", e.toString());
					e.printStackTrace();
					return "-1";
				}

				return jsonStr;
			}
		}, new Callback<String>() {
			@Override
			public void onCallback(String jsonStr) {
				// TODO Auto-generated method stub
System.out.println("firstLevel code jsonStr =" + jsonStr);
				tvLoad.setVisibility(View.GONE);//隐藏提示文字
				if (jsonStr.equals("-1")) {
					ViewUtil.showToast(myContext, myContext.getString(R.string.net_exception));
				} else {
					try {   
						JSONObject jsonObject ;
						jsonObject = new JSONObject(jsonStr);
						String time = jsonObject.getString("updatetime");
						 List<GroupBuyingGoodsCategoryEntity> List = new ArrayList<GroupBuyingGoodsCategoryEntity>();
						String sortlist =jsonObject.getString("list");
						
						 Type listType = new TypeToken<List<GroupBuyingGoodsCategoryEntity>>(){}.getType();
							List = new Gson().fromJson(sortlist, listType);
							int num=0;
						for(int q=0 ; q<List.size();q++){
							num=num+List.get(q).getGoodsCount();
						}
						currentLevelList=new ArrayList<GroupBuyingGoodsCategoryEntity>();
							GroupBuyingGoodsCategoryEntity entity = new GroupBuyingGoodsCategoryEntity();
							entity.setID("0");
							entity.setLevel(1);
							entity.setName("全部");
							entity.setOrderPosition(0);
							entity.setParentID("");
							entity.setGoodsCount(num);
							currentLevelList.add(entity);
							for(int q=0 ; q<List.size();q++){
								currentLevelList.add(List.get(q));
							}
							
							GroupBuySortListAdapter sAdapter = new GroupBuySortListAdapter(myContext, currentLevelList,1);
							myListView.setAdapter(sAdapter);
					} catch (Exception e) {   
						Log.e("FirstLevelSortActivity", e.toString());
						e.printStackTrace();   
					} 
				}
			}
		});
	}

	
	
	/*
	
	/**
	 * 初始化分类信息
	 * @param cg 分类信息对象
	 */
	private void initSortInfo(CommunityGoodsUpdateCategoryList cg){
		//测试，使用服务其返回数据，if为false
		if(lastUpdatetimeSort.equals(currentUpdatetimeSort)){
//		if(false){
			GlobalVarUtil.CommunityGoodsCategoryList = BusinessDao.getShoppingMallSortList(0);
			System.out.println("firstLevel 从数据库中提取");
		}
		else{
			GlobalVarUtil.CommunityGoodsCategoryList = cg.getList();
			CommonDao.deleteTableData(BusinessDao.Table_Name_ShoppingMallSortInfo);
			List<ContentValues> cList = new ArrayList<ContentValues>();
			for(CommunityGoodsCategoryEntity entity : GlobalVarUtil.CommunityGoodsCategoryList){
				ContentValues cValue = new ContentValues();				
				cValue.put("ID", entity.getID());
				cValue.put("level", entity.getLevel());
				cValue.put("name", entity.getName());
				cValue.put("parentID", entity.getParentID());
				cValue.put("orderPosition", entity.getOrderPosition());
				cValue.put("updatetime", currentUpdatetimeSort);
				cValue.put("goodsCount", entity.getGoodsCount());
//				cValue.put("imgUrl", entity.getImgUrl());
				cList.add(cValue);
			}
			CommonDao.insert(BusinessDao.Table_Name_ShoppingMallSortInfo, cList);
		}
	}
	
	/**
	 * 请求广告信息接口
	 */
	private void postAdvInfo() {
		this.doAsync(new CallEarliest<Object>() {
			@Override
			public void onCallEarliest() throws Exception {
				// TODO Auto-generated method stub
				ivLoading.setVisibility(View.VISIBLE);
				ViewUtil.addLoadingAnimation(ApplicationUtil.myContext,
						ivLoading);
			}

		}, new Callable<Object>() {
			@Override
			public String call() {
				String reqUrl = getString(R.string.getadv_http);
				//对方要求不要updatetime 所以不存储=====================================================
//				lastUpdatetime = BusinessDao.getAdvUpdatetime(EnumAdvType.main.ordinal(), null);
				Map<String, Object> parameters = new LinkedHashMap<String, Object>();
//				parameters.put("type", EnumAdvType.main.ordinal());
//				parameters.put("updatetime", lastUpdatetime);
				String jsonStr = "";
				try {
					jsonStr = HttpUtil.postData(reqUrl, parameters,
							GlobalVarUtil.ENCODING);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					Log.e("HomeMainActivity",e.toString());
					e.printStackTrace();
					return "-1";
				}
				return jsonStr;
			}
		}, new Callback<String>() {
			@Override
			public void onCallback(String jsonStr) {
				// TODO Auto-generated method stub
System.out.println("homemain code jsonStr=" + jsonStr);
				ivspline.setVisibility(View.VISIBLE);
				ViewUtil.removeLoadingAnimation(ivLoading);
				if (jsonStr.equals("-1")) {
					ViewUtil.showToast(myContext, myContext.getString(R.string.net_exception));
				} else {
					JSONObject jsonObj;
					try {
//						String str;
						jsonObj = new JSONObject(jsonStr);
						String jsonList = jsonObj.getString("AdvList");
						Type listType = new TypeToken<List<AdvEntity>>(){}.getType();
						myAdvList = new Gson().fromJson(jsonList, listType);
System.out.println("广告的长度"+myAdvList.size());
//						for (int i = 0; i < myAdvList.size(); i++) {
//							str = myAdvList.get(i).getName();
//						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
 //测试使用=================================================================================================
//					myAdvList = new ArrayList<AdvEntity>();
//					AdvEntity a1 = new AdvEntity();
//					a1.setAdvID("1");
//					a1.setImageUrl("upload_files/qb_shop_/329/40_20130328110308_ijvvi.jpg");
//					myAdvList.add(a1);
//					AdvEntity a2 = new AdvEntity();
//					a2.setAdvID("2");
//					a2.setImageUrl("upload_files/qb_shop_/30/40_20130514130536_3hhou.jpg.gif");
//					myAdvList.add(a2);
//					AdvEntity a3 = new AdvEntity();
//					a3.setAdvID("3");
//					a3.setImageUrl("upload_files/qb_shop_/30/1936_20121103051139_2pujy.jpg.gif");
////					a3.setImageUrl("android/images/adv3.jpg");
//					myAdvList.add(a3);
//					AdvEntity a4 = new AdvEntity();
//					a4.setAdvID("4");
//					a4.setImageUrl("upload_files/qb_shop_/30/40_20130514140508_1kcld.gif.gif");
////					a3.setImageUrl("android/images/adv3.jpg");
//					myAdvList.add(a4);
//测试使用=================================================================================================
					initGallery();
					ViewUtil.removeLoadingAnimation(ivLoading);
				}
			}
		});
	}
	
	public void initGallery(){
		if(myAdvList.size()==0){
			
		}else{
			
			ScreenSaverGalleryAdapter adapter = null;
			syncImageLoader = new SyncImageLoadUtil(GlobalVarUtil.MAIN_ADV_IMAGE_SAVE_PATH);
			adapter = new ScreenSaverGalleryAdapter(getBaseContext(),myAdvList,syncImageLoader);
			advGallery.setAdapter(adapter);
			advGallery.setSelection(myAdvList.size()*100);
			autoSwitch();
		}
	}
	/** 图片定时自动切换 */  
	public static void autoSwitch() {  
		if(task!=null&&timer!=null){
    		task.cancel();
    		timer.cancel();
    	}
        timer = new Timer();  
        task = new TimerTask() {
  
            @Override  
            public void run() {
                Message message = new Message();  
                message.what = 2;
                index = advGallery.getSelectedItemPosition();
                if(index == advcounts-1){
                	index = 0;
                }else{
                	index++;
                }
                handler.sendMessage(message);  
            }  
        };  
        timer.schedule(task, interval, interval);
        isStop = false;
    }
    
    /**停止自动播放图片**/
    public static void stopSwitch(){
    	if(task!=null&&timer!=null){
    		task.cancel();
    		timer.cancel();
    	}
    	isStop = true;
    }
    
    public static Handler handler = new Handler() {  
    	  
        @Override  
        public void handleMessage(Message msg) {
        	super.handleMessage(msg);
            switch (msg.what) {
            case 2:
            	advGallery.setSelection(index);
                break;
            case 4:
        		try {
        		} catch (Exception e) {
        			e.printStackTrace();
        		}
            	break;
            default:
                break;
            }
            super.handleMessage(msg);  
        }
    };
	class ScreenSaverGalleryAdapter extends BaseAdapter {

		int mGalleryItemBackground;
		private Context mContext;
		private LayoutInflater myInflater;
		private SyncImageLoadUtil mySilu;
		private List<AdvEntity> advList;

		public ScreenSaverGalleryAdapter(Context c,List<AdvEntity> advList,SyncImageLoadUtil mySilu){
			this.mContext = c;
			this.advList = advList;
			this.mySilu = mySilu;
			myInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			
		}

		public int getCount() {
			return Integer.MAX_VALUE;
		}

		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			final ViewHolder holder;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = myInflater.inflate(R.layout.layout_advgallery_item, null);
				holder.sImage = (ImageView)convertView.findViewById(R.id.iv_advgallery_item_sImage);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.sImage.setScaleType(ScaleType.CENTER_CROP);	
			mySilu.displayImage(advList.get(position%advList.size()).getImageUrl(),holder.sImage,mContext);
			return convertView;
		}
		
		public class ViewHolder {
			private ImageView sImage;
		}

		/**
		 * Returns the size (0.0f to 1.0f) of the views depending on the 'offset' to
		 * the center.
		 */
		public float getScale(boolean focused, int offset) {
			/* Formula: 1 / (2 ^ offset) */
			return Math.max(0, 1.0f / (float) Math.pow(2, Math.abs(offset)));
		}

	}
	
	
	
	

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, final int arg2, long arg3) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(GroupBuySortListActivity.this,GroupBuyMainListActivity.class);
		intent.putExtra("goodsCategoryID", currentLevelList.get(arg2).getID());
		intent.putExtra("title", currentLevelList.get(arg2).getName());
		startActivity(intent);
		
	}

	
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch(arg0.getId()){
		case R.id.ll_groupbuy_sort:
			if(isShowAdv){
				llContain.setVisibility(View.GONE);
				tvOpenAdv.setText("开启");
				isShowAdv = false;
			}else{
				llContain.setVisibility(View.VISIBLE);
				tvOpenAdv.setText("关闭");
				isShowAdv = true;
			}
			break;
		case R.id.btn_groupbuy_sortlist_back:
			this.finish();
			break;
		case R.id.ll_tohome :
			Intent intenthome =new Intent(GroupBuySortListActivity.this,HomeMainActivity.class);
			startActivity(intenthome);
			ExitApplication.getInstance().exit();
			break;
		case R.id.ll_tosearch :
			Intent intentsearch =new Intent(GroupBuySortListActivity.this,SearchMainActivity.class);
			startActivity(intentsearch);
			ExitApplication.getInstance().exit();
			break;
		case R.id.ll_toshoppingcart :
			Intent intentshoppingcart =new Intent(GroupBuySortListActivity.this,ShoppingcartMainListActivity.class);
			startActivity(intentshoppingcart);
			if(GlobalVarUtil.account.getUID()==null){
				Intent intentTolongin = new Intent(GroupBuySortListActivity.this,LoginActivity.class);
				startActivity(intentTolongin);
			}
//			ExitApplication.getInstance().exit();//购物车可以返回
			break;
		case R.id.ll_tomycenter :
			Intent intentmycenter =new Intent(GroupBuySortListActivity.this,MycenterMainActivity.class);
			startActivity(intentmycenter);
			if(GlobalVarUtil.account.getUID()==null){
				Intent intentTolongin = new Intent(GroupBuySortListActivity.this,LoginActivity.class);
				startActivity(intentTolongin);
			}
			ExitApplication.getInstance().exit();
			break;
		case R.id.ll_tomore :
			Intent intentmore =new Intent(GroupBuySortListActivity.this,MoreActivity.class);
			startActivity(intentmore);
			ExitApplication.getInstance().exit();
			break;
		
		
		}
	}

}
