package com.fsti.fjdicClient.activity.search;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.fsti.fjdicClient.R;
import com.fsti.fjdicClient.activity.BaseActivity;
import com.fsti.fjdicClient.activity.groupBuy.GroupBuySortListActivity;
import com.fsti.fjdicClient.activity.home.HomeMainActivity;
import com.fsti.fjdicClient.activity.login.LoginActivity;
import com.fsti.fjdicClient.activity.more.MoreActivity;
import com.fsti.fjdicClient.activity.mycenter.MycenterMainActivity;
//import com.fsti.fjdicClient.activity.shoppingMall.GoodsListActivity;
//import com.fsti.fjdicClient.activity.shoppingMall.GoodsListNoButtonActivity;
import com.fsti.fjdicClient.activity.shoppingcart.ShoppingcartMainListActivity;
import com.fsti.fjdicClient.adapter.PopSortAdapter;
import com.fsti.fjdicClient.bean.ExitApplication;
import com.fsti.fjdicClient.bean.GoodsEntity;
import com.fsti.fjdicClient.bean.PopSortEntity;
import com.fsti.fjdicClient.bean.SearchGoodsEntity;
import com.fsti.fjdicClient.dao.BusinessDao;
import com.fsti.fjdicClient.util.GlobalVarUtil;
import com.fsti.fjdicClient.util.HttpUtil;
import com.fsti.fjdicClient.util.ViewUtil;
import com.fsti.fjdicClient.util.asyncUtil.AsyncTaskUtils;
import com.fsti.fjdicClient.util.asyncUtil.CallEarliest;
import com.fsti.fjdicClient.util.asyncUtil.Callable;
import com.fsti.fjdicClient.util.asyncUtil.Callback;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import android.app.Activity;
import android.app.ActivityGroup;
import android.app.AlertDialog;
import android.app.LocalActivityManager;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

/**
 * 搜索
 * 
 * @author 金涛
 * 
 */
public class SearchMainActivity extends ActivityGroup implements OnClickListener {
	private List <GoodsEntity> goodsList = new ArrayList<GoodsEntity>();
	private PopupWindow pop;
	private Context context;
	private Button btnSearchSort;
	private PopSortAdapter myPopSortAdapter;
	private Context myContext;
	private ListView mySortListView;
	private List<PopSortEntity> mySortList;
	private LinearLayout popTopLinearLayout;
	private LinearLayout popBottomLinearLayout;
	private Button btnHistorySearch;
	private Button btnHotSearch;
	private Button btnSearch;
	private LocalActivityManager activityManager;
	private LinearLayout contain;
	private EditText etkeyword;
//	private ProgressDialog m_Dialog;
	
	private int type = 0;//获取spinner的类型
	
	private LinearLayout tohome;
	private LinearLayout tosearch;
	private LinearLayout toshoppingcart;
	private LinearLayout tomycenter;
	private LinearLayout tomore;
	private RelativeLayout bottommenu;
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {

			Builder builder = new AlertDialog.Builder(SearchMainActivity.this);
			builder.setTitle("是否退出");
			builder.setMessage("按确定键退出！");
			builder.setPositiveButton("确定",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface arg0, int arg1) {
							// TODO Auto-generated method stub
							GlobalVarUtil.firstUpdateApp="update";
							ExitApplication.getInstance().exit();
							android.os.Process.killProcess(android.os.Process.myPid());

						}
					});
			builder.setNegativeButton("取消",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface arg0, int arg1) {
							// TODO Auto-generated method stub

						}

					});
			builder.show();
		}

		return super.onKeyDown(keyCode, event);
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		
		super.onResume();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 隐藏标题
		setContentView(R.layout.layout_activity_search_main);
		ExitApplication.getInstance().addActivity(this);
		context = getBaseContext();
		init();
	}
	
	private void init(){
		initValue();
		bindEvent();
	}

	
	public void bindEvent() {
		// TODO Auto-generated method stub
		btnSearchSort.setOnClickListener(this);
		btnHistorySearch.setOnClickListener(this);
		btnHotSearch.setOnClickListener(this);
		btnSearch.setOnClickListener(this);
		
		tohome.setOnClickListener(this);
		tosearch.setOnClickListener(this);
		toshoppingcart.setOnClickListener(this);
		tomycenter.setOnClickListener(this);
		tomore.setOnClickListener(this);
		
		etkeyword.setOnEditorActionListener(new OnEditorActionListener() {
			
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				// TODO Auto-generated method stub
				return (event.getKeyCode() == KeyEvent.KEYCODE_ENTER);
			}
		});
	}

	
	public void initValue() {
		// TODO Auto-generated method stub
		etkeyword = (EditText) findViewById(R.id.et_search);
		myContext = getBaseContext();
		btnSearchSort = (Button) findViewById(R.id.btn_search_sort);
		mySortList = getSortData();
		btnHistorySearch = (Button) findViewById(R.id.btn_history_search);
		btnHotSearch = (Button) findViewById(R.id.btn_hot_search);
		activityManager = getLocalActivityManager();
		contain = (LinearLayout) findViewById(R.id.ll_search_contain);
		btnSearch = (Button) findViewById(R.id.btn_search);
		contain.removeAllViews();
		Intent in = new Intent(SearchMainActivity.this, SearchHistoryActivity.class);
		in.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		// in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

		Window mWindow = activityManager.startActivity("1", in);
		contain.addView(mWindow.getDecorView());
		
		LayoutInflater	mInflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		  View view2  = mInflater.inflate(R.layout.layout_item_bottom_menu,null);
		    tohome=(LinearLayout)view2.findViewById(R.id.ll_tohome);
		    tosearch=(LinearLayout) view2.findViewById(R.id.ll_tosearch);
		    toshoppingcart=(LinearLayout) view2.findViewById(R.id.ll_toshoppingcart);
		    tomycenter=(LinearLayout) view2.findViewById(R.id.ll_tomycenter);
		    tomore=(LinearLayout) view2.findViewById(R.id.ll_tomore);
		    bottommenu=(RelativeLayout) findViewById(R.id.rl_search_main_bottommenu);
		    bottommenu.addView(view2);
		    ViewGroup.LayoutParams lp =  view2.getLayoutParams();
		    lp.width = lp.FILL_PARENT;
		    view2.setLayoutParams(lp);
		
	}
	
	
    
	private List<PopSortEntity> getSortData(){
		List<PopSortEntity> list = new ArrayList<PopSortEntity>();
		PopSortEntity entity1 = new PopSortEntity();
		entity1.setPopSortName("全部");
		entity1.setPopSortValue("0");
		entity1.setSelected(true);
		PopSortEntity entity2 = new PopSortEntity();
		entity2.setPopSortName("商品");
		entity2.setPopSortValue("1");
		entity2.setSelected(false);
		PopSortEntity entity3 = new PopSortEntity();
		entity3.setPopSortName("服务");
		entity3.setPopSortValue("2");
		entity3.setSelected(false);
		list.add(entity1);
		list.add(entity2);
		list.add(entity3);
		return list;
	}

	/**
	 * popwindow初始化
	 */
	public void initPop() {
		LayoutInflater inflater = (LayoutInflater) this
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);		
		View view  = inflater.inflate(R.layout.layout_fn_list_pop_sort,null);
		
		ListView listView = (ListView) view.findViewById(R.id.lv_sort);
		myPopSortAdapter = new PopSortAdapter(myContext, mySortList);
		listView.setAdapter(myPopSortAdapter);

		listView.setFocusable(true);
		listView.setItemsCanFocus(true);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				type = arg2;
				changeSortSelectState(arg2);
				myPopSortAdapter.notifyDataSetChanged();
				pop.dismiss();
				pop = null;
			}
		});
		pop = new PopupWindow(view,  this.getWindowManager().getDefaultDisplay().getWidth()/4, LayoutParams.WRAP_CONTENT);
		pop.setBackgroundDrawable(new BitmapDrawable());
		pop.setTouchable(true);
		pop.setFocusable(true);
	}

	/**
	 * popwindow显示
	 */
	public void showPopWindow() {
		int with = this.getWindowManager().getDefaultDisplay().getWidth();
		if (pop != null && !pop.isShowing()) {
			pop.showAsDropDown(btnSearchSort, 0, 0);
		} else if (pop != null && pop.isShowing()) {
			pop.dismiss();
		} else {
			initPop();
			pop.showAsDropDown(btnSearchSort, 0, 0);
		}
	}
	
	private void changeSortSelectState(int position){
		for(int i= 0;i<mySortList.size();i++){
			PopSortEntity tempEntity = mySortList.get(i);
			if(i == position){
				//mySortList.get(position)
				tempEntity.setSelected(true);
				//mySortList.get(position).setSelected(true);
			}
			else{
				tempEntity.setSelected(false);
			}
			mySortList.set(i, tempEntity);
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.btn_search_sort:
			showPopWindow();
//			postSearchInfo();
			break;
		case R.id.btn_history_search:
			contain.removeAllViews();
			btnHistorySearch.setBackgroundResource(R.drawable.bg_lable_selected_horizontal);
			btnHotSearch.setBackgroundResource(R.drawable.bg_lable_noselected_horizontal);
			Intent historyIntent = new Intent(SearchMainActivity.this, SearchHistoryActivity.class);
			historyIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			Window historyWindow = activityManager.startActivity("1", historyIntent);
			contain.addView(historyWindow.getDecorView());
			break;
		case R.id.btn_hot_search:
			contain.removeAllViews();
			btnHistorySearch.setBackgroundResource(R.drawable.bg_lable_noselected_horizontal);
			btnHotSearch.setBackgroundResource(R.drawable.bg_lable_selected_horizontal);
			
			Intent hotIntent = new Intent(SearchMainActivity.this, SearchHotActivity.class);
			hotIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			// in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			Window hotWindow = activityManager.startActivity("2", hotIntent);
			contain.addView(hotWindow.getDecorView());
			break;
		case R.id.btn_search:
			String search = etkeyword.getText().toString();
			if("".equals(etkeyword.getText().toString())){
				Toast.makeText(myContext, "请输入关键字", Toast.LENGTH_SHORT).show();
			}else{
				SearchGoodsEntity searchGoodsEntity = new SearchGoodsEntity();
				searchGoodsEntity.setKeyword(etkeyword.getText().toString());
				searchGoodsEntity.setType(type);
				List<SearchGoodsEntity> listSearchGoodsTemp = BusinessDao.getSearchHistoryTableData();
				if(listSearchGoodsTemp.size() > 9){
					BusinessDao.deleteSearchHistoryTableData(listSearchGoodsTemp.get(0));
					listSearchGoodsTemp.remove(0);
				}
				//插入数据库,log中会报错，cursor未关闭
				BusinessDao.insertSearchHistoryTableData(searchGoodsEntity);
//				GoodsListNoButtonActivity.StartGoodsListActivity(context, searchGoodsEntity, SearchHistoryActivity.FLAG);
			}
			break;
		case R.id.ll_tohome :
			Intent intenthome =new Intent(SearchMainActivity.this,HomeMainActivity.class);
			startActivity(intenthome);
			ExitApplication.getInstance().exit();
			break;
		case R.id.ll_tosearch :
			
			break;
		case R.id.ll_toshoppingcart :
			Intent intentshoppingcart =new Intent(SearchMainActivity.this,ShoppingcartMainListActivity.class);
			startActivity(intentshoppingcart);
			if(GlobalVarUtil.account.getUID()==null){
				Intent intentTolongin = new Intent(SearchMainActivity.this,LoginActivity.class);
				startActivity(intentTolongin);
			}
//			ExitApplication.getInstance().exit();//购物车可以回退
			break;
		case R.id.ll_tomycenter :
			Intent intentmycenter =new Intent(SearchMainActivity.this,MycenterMainActivity.class);
			startActivity(intentmycenter);
			if(GlobalVarUtil.account.getUID()==null){
				Intent intentTolongin = new Intent(SearchMainActivity.this,LoginActivity.class);
				startActivity(intentTolongin);
			}
			ExitApplication.getInstance().exit();
			break;
		case R.id.ll_tomore :
			Intent intentmore =new Intent(SearchMainActivity.this,MoreActivity.class);
			startActivity(intentmore);
			ExitApplication.getInstance().exit();
			break;
		
		}
		
	}
	public <T> void doAsync(final CallEarliest<Object> callEarliest,  
			final Callable<Object> callable, final Callback<String> callback) {  
		AsyncTaskUtils.doAsync(callEarliest, callable, callback);  
	}

}
