package com.fsti.fjdicClient.activity.search;

import java.lang.reflect.Type;
import java.util.ArrayList;
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
import com.fsti.fjdicClient.adapter.SearchHistoryListAdapter;
import com.fsti.fjdicClient.adapter.SearchHotListAdapter;
import com.fsti.fjdicClient.bean.ExitApplication;
import com.fsti.fjdicClient.bean.GoodsEntity;
import com.fsti.fjdicClient.bean.SearchGoodsEntity;
import com.fsti.fjdicClient.util.GlobalVarUtil;
import com.fsti.fjdicClient.util.HttpUtil;
import com.fsti.fjdicClient.util.ViewUtil;
import com.fsti.fjdicClient.util.asyncUtil.CallEarliest;
import com.fsti.fjdicClient.util.asyncUtil.Callable;
import com.fsti.fjdicClient.util.asyncUtil.Callback;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * 热点查询
 * 
 * @author 金涛
 * 
 */
public class SearchHotActivity extends BaseActivity implements OnItemClickListener, OnClickListener{
	public static ListView listSearchHot;
	private TextView tvLoad;
	private List<SearchGoodsEntity> listSearchGoods = new ArrayList<SearchGoodsEntity>();
	private List<GoodsEntity> goodsList = new ArrayList<GoodsEntity>();
	private Context context;
	private SearchGoodsEntity searchGoodsEntity = new SearchGoodsEntity();
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_activity_search_hot);
		ExitApplication.getInstance().addActivity(this);
		context = getBaseContext();
		init();
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	public void bindEvent() {
		// TODO Auto-generated method stub
		listSearchHot.setOnItemClickListener(this);
	}

	@Override
	public void initValue() {
		// TODO Auto-generated method stub
		listSearchHot = (ListView) findViewById(R.id.list_search_hot);
		listSearchHot.setDivider(null);
		tvLoad = (TextView) findViewById(R.id.tv_search_hot_load);
		postSearchInfo();
		
	}
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch(arg0.getId()){
		case R.id.ll_tohome :
			Intent intenthome =new Intent(SearchHotActivity.this,HomeMainActivity.class);
			startActivity(intenthome);
			this.finish();
			break;
		
		}
	}
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
//		searchGoodsEntity = listSearchGoods.get(arg2);
//		GoodsListNoButtonActivity.StartGoodsListActivity(context, searchGoodsEntity, SearchHistoryActivity.FLAG);
	}


	private void postSearchInfo() {
		// TODO Auto-generated method stub
		doAsync(new CallEarliest<Object>() {
			
			@Override
			public void onCallEarliest() throws Exception {
				// TODO Auto-generated method stub
				
			}
		}, new Callable<Object>() {
			
			@Override
			public String call() {
				// TODO Auto-generated method stub
				String reqUrl =  getString(R.string.getHotKeyword_http);
				Map<String, Object> parameters = new LinkedHashMap<String, Object>();
//				parameters.put("", "");
				String jsonStr = "";
				try {
					jsonStr = HttpUtil.postData(reqUrl, parameters,
							GlobalVarUtil.ENCODING);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					Log.e("SearchHotActivity", e.toString());
					e.printStackTrace();
					return "-1";
				}
				return jsonStr;
			}
		}, new Callback<String>() {
			
			@Override
			public void onCallback(String jsonStr) {
				// TODO Auto-generated method stub
				tvLoad.setVisibility(View.GONE);
				if (jsonStr.equals("-1")) {
					ViewUtil.showToast(myContext, myContext.getString(R.string.net_exception));
				}else{
					try {
//+++++++++++++++++++++++++++++++正式使用++++++++++++++++++++++++++++++++++++++
//					JSONObject jsonObj;
//					jsonObj = new JSONObject(jsonStr);
//					String jsonList = jsonObj.getString("list");
//					Type typeObj = new TypeToken<List<SearchGoodsEntity>>() {}.getType(); 
//					listSearchGoods = new Gson().fromJson(jsonList, typeObj); 
					
					Type typeObj = new TypeToken<List<SearchGoodsEntity>>() {}.getType(); 
					listSearchGoods = new Gson().fromJson(jsonStr, typeObj); 
					
//+++++++++++++++++++++++++++++++正式使用++++++++++++++++++++++++++++++++++++++
//+++++++++++++++++++++++++++++++测试使用++++++++++++++++++++++++++++++++++++++
//						SearchGoodsEntity s1 = new SearchGoodsEntity();
//						s1.setKeyword("s1");
//						s1.setType(1);
//						listSearchGoods.add(s1);
//						SearchGoodsEntity s2 = new SearchGoodsEntity();
//						s2.setKeyword("s2");
//						s2.setType(1);
//						listSearchGoods.add(s2);
//						SearchGoodsEntity s3 = new SearchGoodsEntity();
//						s3.setKeyword("s3");
//						s3.setType(1);
//						listSearchGoods.add(s3);
//						SearchGoodsEntity s4 = new SearchGoodsEntity();
//						s4.setKeyword("s4");
//						s4.setType(1);
//						listSearchGoods.add(s4);
//+++++++++++++++++++++++++++++++测试使用++++++++++++++++++++++++++++++++++++++
						
						if(listSearchGoods.size()!=0){
							
							SearchHotListAdapter adapter = new SearchHotListAdapter(getBaseContext(),listSearchGoods);
							listSearchHot.setAdapter(adapter);
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						Log.e("SearchHotActivity", e.toString());
						e.printStackTrace();
					} 
				}
			}
		});
	}
}
