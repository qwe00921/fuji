package com.fsti.fjdicClient.activity.search;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.fsti.fjdicClient.R;
import com.fsti.fjdicClient.activity.BaseActivity;
import com.fsti.fjdicClient.activity.groupBuy.GroupBuySortListActivity;
import com.fsti.fjdicClient.activity.home.HomeMainActivity;
import com.fsti.fjdicClient.activity.more.MoreActivity;
import com.fsti.fjdicClient.activity.mycenter.MycenterMainActivity;
//import com.fsti.fjdicClient.activity.shoppingMall.GoodsListActivity;
//import com.fsti.fjdicClient.activity.shoppingMall.GoodsListNoButtonActivity;
import com.fsti.fjdicClient.activity.shoppingcart.ShoppingcartMainListActivity;
import com.fsti.fjdicClient.adapter.PopSortAdapter;
import com.fsti.fjdicClient.adapter.SearchHistoryListAdapter;
import com.fsti.fjdicClient.bean.ExitApplication;
import com.fsti.fjdicClient.bean.GoodsEntity;
import com.fsti.fjdicClient.bean.PopSortEntity;
import com.fsti.fjdicClient.bean.SearchGoodsEntity;
import com.fsti.fjdicClient.dao.BusinessDao;
import com.fsti.fjdicClient.dao.CommonDao;
import com.fsti.fjdicClient.util.GlobalVarUtil;
import com.fsti.fjdicClient.util.HttpUtil;
import com.fsti.fjdicClient.util.ViewUtil;
import com.fsti.fjdicClient.util.asyncUtil.CallEarliest;
import com.fsti.fjdicClient.util.asyncUtil.Callable;
import com.fsti.fjdicClient.util.asyncUtil.Callback;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.app.Activity;
import android.app.ActivityGroup;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

/**
 * 历史查询
 * 
 * @author 金涛
 * 
 */
public class SearchHistoryActivity extends BaseActivity implements OnItemClickListener{
	public static ListView listSearchHistory;
	private List<SearchGoodsEntity> listSearchGoods = new ArrayList<SearchGoodsEntity>();
	private List<SearchGoodsEntity> listSearchGoodsTemp = new ArrayList<SearchGoodsEntity>();
	private List<GoodsEntity> goodsList = new ArrayList<GoodsEntity>();
	private Context context;
	private SearchGoodsEntity searchGoodsEntity = new SearchGoodsEntity();
	private SearchHistoryListAdapter adapter;
	public static final String FLAG = "search";
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_activity_search_history);
		ExitApplication.getInstance().addActivity(this);
		context = getBaseContext();
		init();
		adapter = new SearchHistoryListAdapter(getBaseContext(),listSearchGoods);
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		listSearchHistory.setAdapter(adapter);
		getData();
	}
	private void getData(){
		listSearchGoods.clear();
		listSearchGoodsTemp.clear();
		listSearchGoodsTemp = BusinessDao.getSearchHistoryTableData();
		//超出10个搜索记录，则把最早的那一个删掉
//		if(listSearchGoodsTemp.size()>10){
//			BusinessDao.deleteSearchHistoryTableData(listSearchGoodsTemp.get(0));
//			listSearchGoodsTemp.remove(0);
//		}
//System.out.println("serchHistory list size" + listSearchGoods.size());
		if(listSearchGoodsTemp.size()!=0){
			for (int i = listSearchGoodsTemp.size()-1; i >= 0 ; --i) {
				listSearchGoods.add(listSearchGoodsTemp.get(i));
			}
		}
	}
	@Override
	public void bindEvent() {
		// TODO Auto-generated method stub
		listSearchHistory.setOnItemClickListener(this);
		
	}

	@Override
	public void initValue() {
		// TODO Auto-generated method stub
		listSearchHistory = (ListView) findViewById(R.id.list_search_history);
		listSearchHistory.setDivider(null);
		
		
		
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
//		searchGoodsEntity = listSearchGoods.get(arg2);
//		GoodsListNoButtonActivity.StartGoodsListActivity(context, searchGoodsEntity, SearchHistoryActivity.FLAG);
	}
	
	

}
