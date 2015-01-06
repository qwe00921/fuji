package com.fsti.fjdicClient.activity.mycenter;


import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fsti.fjdicClient.R;
import com.fsti.fjdicClient.ApplicationUtil;
import com.fsti.fjdicClient.activity.BaseActivity;
import com.fsti.fjdicClient.activity.groupBuy.GroupBuyGoodsDetailActivity;
import com.fsti.fjdicClient.activity.groupBuy.GroupBuySortListActivity;
import com.fsti.fjdicClient.activity.home.HomeMainActivity;
import com.fsti.fjdicClient.activity.login.LoginActivity;
import com.fsti.fjdicClient.activity.login.RegisterActivity;
import com.fsti.fjdicClient.activity.more.MoreActivity;
import com.fsti.fjdicClient.activity.search.SearchMainActivity;
//import com.fsti.fjdicClient.activity.shoppingMall.ShoppingmallGoodsDetailActivity;
import com.fsti.fjdicClient.activity.shoppingcart.ShoppingcartMainListActivity;
import com.fsti.fjdicClient.adapter.ConfirmReceivingAdapter;
import com.fsti.fjdicClient.adapter.HistoryOrderAdapter;
import com.fsti.fjdicClient.adapter.MycenterSellerDeliverAdapter;
import com.fsti.fjdicClient.adapter.PopSortAdapter;
import com.fsti.fjdicClient.adapter.WaitBuyerPayAdapter;
import com.fsti.fjdicClient.bean.ExitApplication;
import com.fsti.fjdicClient.bean.GoodsDetailInfoEntity;
import com.fsti.fjdicClient.bean.GoodsEntity;
import com.fsti.fjdicClient.bean.Order;
import com.fsti.fjdicClient.bean.OrderGoodsInfoEntity;
import com.fsti.fjdicClient.bean.PopSortEntity;
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
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

/**
 * 确认收货
 * @author 
 *
 */
public class ConfirmReceivingListActivity extends BaseActivity implements OnClickListener, OnItemClickListener {
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		if(thread1!=null&&thread1.isAlive()){
			thread1.interrupt();
		}
		if(thread2!=null&&thread2.isAlive()){
			thread2.interrupt();
		}
		super.onPause();
	}
	private Thread thread1;
	private Thread thread2;
	private PopupWindow pop;
	private PopSortAdapter myPopSortAdapter;
	private List<PopSortEntity> mySortList = new ArrayList<PopSortEntity>();
	private int Sortkey = 0;
	private ImageButton btnsort;
	private ListView listMycenterconfirmreceiving;
	private Button btnback;
	private List<Order> myList=new ArrayList<Order>();
	private LinearLayout tohome;
	private LinearLayout tosearch;
	private LinearLayout toshoppingcart;
	private LinearLayout tomycenter;
	private LinearLayout tomore;
	private RelativeLayout bottommenu;
	private ImageView ivLoading;
	private ConfirmReceivingAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_list_mycenter_confirmreceiving);
		ExitApplication.getInstance().addActivity(this);
		init();
		get_orderlist();
	}


	@Override
	public void bindEvent() {
		// TODO Auto-generated method stub
		btnback.setOnClickListener(this);
		btnsort.setOnClickListener(this);
		listMycenterconfirmreceiving.setOnItemClickListener(this);
		tohome.setOnClickListener(this);
		tosearch.setOnClickListener(this);
		toshoppingcart.setOnClickListener(this);
		tomycenter.setOnClickListener(this);
		tomore.setOnClickListener(this);
	}

	@Override
	public void initValue() {
		// TODO Auto-generated method stub
		listMycenterconfirmreceiving = (ListView) findViewById(R.id.listview_list_mycenter_confirmreceiving);
		btnback=(Button) findViewById(R.id.btn_list_mycenter_confirmreceiving_back);
		btnsort=(ImageButton) findViewById(R.id.btn_list_mycenter_confirmorder_sort);
		LayoutInflater	mInflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    View view2  = mInflater.inflate(R.layout.layout_item_bottom_menu,null);
	    tohome=(LinearLayout)view2.findViewById(R.id.ll_tohome);
	    tosearch=(LinearLayout) view2.findViewById(R.id.ll_tosearch);
	    toshoppingcart=(LinearLayout) view2.findViewById(R.id.ll_toshoppingcart);
	    tomycenter=(LinearLayout) view2.findViewById(R.id.ll_tomycenter);
	    tomore=(LinearLayout) view2.findViewById(R.id.ll_tomore);
	    bottommenu=(RelativeLayout) findViewById(R.id.rl_mycenter_confirmreceivinglist_bottommenu);
	    bottommenu.addView(view2);
	    ViewGroup.LayoutParams lp =  view2.getLayoutParams();
	    lp.width = lp.FILL_PARENT;
	    view2.setLayoutParams(lp);
		ivLoading=(ImageView) findViewById(R.id.iv_mycenter_confirmreceiving_loading);
		ivLoading.setVisibility(View.VISIBLE);
		ViewUtil.addLoadingAnimation(ApplicationUtil.myContext,
				ivLoading);
	    
		get_pop_data();
	}
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch(arg0.getId()){
		case R.id.btn_list_mycenter_confirmreceiving_back:
			this.finish();
			break;
		case R.id.btn_list_mycenter_confirmorder_sort:
			showPopWindow();
			break;
		case R.id.ll_tohome :
			Intent intenthome =new Intent(ConfirmReceivingListActivity.this,HomeMainActivity.class);
			startActivity(intenthome);
			ExitApplication.getInstance().exit();
			break;
		case R.id.ll_tosearch :
			Intent intentsearch =new Intent(ConfirmReceivingListActivity.this,SearchMainActivity.class);
			startActivity(intentsearch);
			ExitApplication.getInstance().exit();
			break;
		case R.id.ll_toshoppingcart :
			Intent intentshoppingcart =new Intent(ConfirmReceivingListActivity.this,ShoppingcartMainListActivity.class);
			startActivity(intentshoppingcart);
			if(GlobalVarUtil.account.getUID()==null){
				Intent intentTolongin = new Intent(ConfirmReceivingListActivity.this,LoginActivity.class);
				startActivity(intentTolongin);
			}
//			ExitApplication.getInstance().exit();//购物车可以返回
			break;
		case R.id.ll_tomycenter :
			Intent intentmycenter =new Intent(ConfirmReceivingListActivity.this,MycenterMainActivity.class);
			startActivity(intentmycenter);
			if(GlobalVarUtil.account.getUID()==null){
				Intent intentTolongin = new Intent(ConfirmReceivingListActivity.this,LoginActivity.class);
				startActivity(intentTolongin);
			}
			ExitApplication.getInstance().exit();
			break;
		case R.id.ll_tomore :
			Intent intentmore =new Intent(ConfirmReceivingListActivity.this,MoreActivity.class);
			startActivity(intentmore);
			ExitApplication.getInstance().exit();
			break;
		
		
		}
	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		System.out.println("position2=" + arg2);
		Intent intent = new Intent(this, Orderinfo.class);
		Bundle bundle = new Bundle();
		intent.putExtra("buttonname", "确认收货");
		bundle.putSerializable("order", myList.get(arg2));
		intent.putExtras(bundle);
		startActivity(intent);

	}

	
	public void showPopWindow() {
		int with = this.getWindowManager().getDefaultDisplay().getWidth();
		System.out.println("with=" + with);
		if (pop != null && !pop.isShowing()) {
			pop.showAsDropDown(btnsort, with / 2 - 220, 0);
		} else if (pop != null && pop.isShowing()) {
			pop.dismiss();
		} else {
			initPop();
			pop.showAsDropDown(btnsort, with / 2 - 220, 0);
		}
	}

	private void get_pop_data() {
		PopSortEntity sort1 = new PopSortEntity();
		sort1.setPopSortID("0");
		sort1.setPopSortName("全部");
		sort1.setSelected(true);
		mySortList.add(sort1);
		PopSortEntity sort2 = new PopSortEntity();
		sort2.setPopSortID("1");
		sort2.setPopSortName("社区商城");
		mySortList.add(sort2);

		PopSortEntity sort3 = new PopSortEntity();
		sort3.setPopSortID("2");
		sort3.setPopSortName("生活服务");
		mySortList.add(sort3);
	}

	public void initPop() {
		LayoutInflater inflater = getLayoutInflater();
		View view = inflater.inflate(R.layout.layout_fn_list_pop_sort, null);
		ListView listView = (ListView) view.findViewById(R.id.lv_sort);

		myPopSortAdapter = new PopSortAdapter(myContext, mySortList);
		listView.setAdapter(myPopSortAdapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				changeSortSelectState(arg2);
				myPopSortAdapter.notifyDataSetChanged();
				show_sorted_order();
				pop.dismiss();
				pop = null;
				
			}

		});
		pop = new PopupWindow(view, 150, LayoutParams.WRAP_CONTENT);
		pop.setBackgroundDrawable(new BitmapDrawable());
		pop.setTouchable(true);
		pop.setFocusable(true);

	}

	private void changeSortSelectState(int position) {
		for (int i = 0; i < mySortList.size(); i++) {
			PopSortEntity tempEntity = mySortList.get(i);
			if (i == position) {
				// mySortList.get(position)
				tempEntity.setSelected(true);
				Sortkey = i;
				// mySortList.get(position).setSelected(true);
			} else {
				tempEntity.setSelected(false);
			}
			mySortList.set(i, tempEntity);
		}
	}

	private void show_sorted_order() {
		List<Order> List2 = new ArrayList<Order>();
		if (Sortkey == 0) {
			List2 = myList;
		} else {
			for (int q = 0; q < myList.size(); q++) {
				if (Integer.parseInt(myList.get(q).getOrderType()) == Sortkey) {
					List2.add(myList.get(q));
				}
			}

		}
		ConfirmReceivingAdapter adapter = new ConfirmReceivingAdapter(ConfirmReceivingListActivity.this.getBaseContext(),List2,ConfirmReceivingListActivity.this);
		listMycenterconfirmreceiving.setAdapter(adapter);

	}
	
	
	//获取订单列表

	private void get_orderlist() {
		this.doAsync(new CallEarliest<Object>() {
			@Override
			public void onCallEarliest() throws Exception {
				// TODO Auto-generated method stub
				
			}

		}, new Callable<Object>() {
			@Override
			public String call() {
 thread1 =Thread.currentThread();
				String reqUrl = getString(R.string.GetOrderList_php);

				Map<String, Object> parameters = new LinkedHashMap<String, Object>();
				
				 parameters.put("UID", GlobalVarUtil.account.getUID());//正式使用时用
				//parameters.put("UID", 1);//测试用的
			    
				parameters.put("OrderListType","ConfirmOrderList");
			
				String jsonStr = "";
				try {
					jsonStr = HttpUtil.postData(reqUrl, parameters,
							GlobalVarUtil.ENCODING);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					ViewUtil.removeLoadingAnimation(ivLoading);	

					return "-1";
				}

				return jsonStr;
			}
		}, new Callback<String>() {
			@Override
			public void onCallback(String jsonStr) {
				// TODO Auto-generated method stub
				thread2= Thread.currentThread();
				ViewUtil.removeLoadingAnimation(ivLoading);	

		        System.out.println("code jsonStr=" + jsonStr);
		        if (jsonStr.trim().equals("-1")) {
					ViewUtil.showToast(myContext, myContext.getString(R.string.net_exception));
				} else {
			    	try {
						JSONObject jsonObject =new JSONObject(jsonStr);
						Type listType = new TypeToken<List<Order>>(){}.getType();
						myList = new Gson().fromJson(jsonObject.getString("list"), listType);
						btnsort.setVisibility(View.VISIBLE);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						Toast.makeText(myContext, "很抱歉，返回数据异常，请重新加载。", 1).show();
						e.printStackTrace();
					}
					if(myList!=null){
						adapter = new ConfirmReceivingAdapter(ConfirmReceivingListActivity.this.getBaseContext(),myList,ConfirmReceivingListActivity.this);
				listMycenterconfirmreceiving.setAdapter(adapter);
					}
			    }	
				}
			});
		}


	//订单操作

	public void manage_order(final String orderID,final String orderType) {
	 this.doAsync(new CallEarliest<Object>() {
			@Override
			public void onCallEarliest() throws Exception {
				// TODO Auto-generated method stub
				
			}

		}, new Callable<Object>() {
			@Override
			public String call() {

				String reqUrl = myContext.getString(R.string.OrderManage_php);

				Map<String, Object> parameters = new LinkedHashMap<String, Object>();
				
				
			   parameters.put("UID", GlobalVarUtil.account.getUID());
//				parameters.put("UID", 1);//测试用的
				parameters.put("orderID",orderID);
				parameters.put("orderType",orderType);
				parameters.put("ManageType", "ConfirmOrder");
			
				String jsonStr = "";
				try {
					jsonStr = HttpUtil.postData(reqUrl, parameters,
							GlobalVarUtil.ENCODING);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return "-1";
				}

				return jsonStr;
			}
		}, new Callback<String>() {
			@Override
			public void onCallback(String jsonStr) {
				// TODO Auto-generated method stub
				jsonStr = RegisterActivity.replaceBlank(jsonStr);
						
	       if(jsonStr.equals("")){//??
	    	   manage_order(orderID, orderType);
	       }
	        else if(jsonStr.equals("1")){
	        	Toast.makeText(ConfirmReceivingListActivity.this, "确认成功", 1).show();
	        	for (int i = 0; i < myList.size(); i++) {
					if(myList.get(i).getOrderID().equals(orderID)){
						myList.remove(i);
					}
				}
	        	 adapter = new ConfirmReceivingAdapter(
							ConfirmReceivingListActivity.this.getBaseContext(),
							myList, ConfirmReceivingListActivity.this);
	        	 listMycenterconfirmreceiving.setAdapter(adapter);
	        }
	        else{
	        	Toast.makeText(ConfirmReceivingListActivity.this, "确认失败", 1).show();
	        }
	        
			}
		});
	}

//获取商品详情
	
	public void get_goodsinfo(Order order,OrderGoodsInfoEntity ordergoodsinfo) {
		if(Integer.parseInt(order.getOrderType())==2){
			 Intent intent = new Intent(ConfirmReceivingListActivity.this,GroupBuyGoodsDetailActivity.class);
				intent.putExtra("goodsID", ordergoodsinfo.getID());
				 startActivity(intent);
		}
//		else{
//			 Intent intent = new Intent(ConfirmReceivingListActivity.this,ShoppingmallGoodsDetailActivity.class);
//				intent.putExtra("ID", ordergoodsinfo.getID());
//				intent.putExtra("type", order.getOrderType());
//				 startActivity(intent);
//		}
	}
		


	
}
