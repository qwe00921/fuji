package com.fsti.fjdicClient.activity.mycenter;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
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
import com.fsti.fjdicClient.adapter.PopSortAdapter;
import com.fsti.fjdicClient.adapter.WaitBuyerPayAdapter;
import com.fsti.fjdicClient.bean.ExitApplication;
import com.fsti.fjdicClient.bean.GoodsEntity;
import com.fsti.fjdicClient.bean.Order;
import com.fsti.fjdicClient.bean.OrderGoodsInfoEntity;
import com.fsti.fjdicClient.bean.PopSortEntity;
import com.fsti.fjdicClient.dao.BusinessDao;
import com.fsti.fjdicClient.util.GlobalVarUtil;
import com.fsti.fjdicClient.util.HttpUtil;
import com.fsti.fjdicClient.util.ViewUtil;
import com.fsti.fjdicClient.util.asyncUtil.CallEarliest;
import com.fsti.fjdicClient.util.asyncUtil.Callable;
import com.fsti.fjdicClient.util.asyncUtil.Callback;
import com.fsti.fjdicClient.util.uppay.Uppay;
import com.fsti.fjdicClient.util.uppay.Uppay_state;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 个人中心地址列表
 * 
 * @author 金涛
 * 
 */
public class WaitBuyerPayListActivity extends BaseActivity implements
		OnClickListener {
	


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
	private ProgressDialog m_Dialog;
	private final int SUCCESS = 0;// 成功
	private final int FAILD = 1;// 失败
	private MyHandler mHandler;
	
	private boolean isPay = false;
	private boolean isall = true;
	private Button btnDelete;
	private Button btnSelect;
	private LinearLayout llbottom;
	private Thread thread1;
	private Thread thread2;
	private PopupWindow pop;
	private PopSortAdapter myPopSortAdapter;
	private List<PopSortEntity> mySortList = new ArrayList<PopSortEntity>();
	private int Sortkey = 0;
	private ImageButton btnsort;
	private ListView listMycenterwaitbuyerpay;
	private Button btnback;
	private WaitBuyerPayAdapter adapter;
	private List<Order> myList = new ArrayList<Order>();
	private List<Order> new_myList = new ArrayList<Order>();//分类时使用
	private List<Order> listTemp = new ArrayList<Order>();//删除时使用
	private LinearLayout tohome;
	private LinearLayout tosearch;
	private LinearLayout toshoppingcart;
	private LinearLayout tomycenter;
	private LinearLayout tomore;
	private RelativeLayout bottommenu;
	private ImageView ivLoading;
	private String pay_result;
	private String orderID;
	private Order orderDelete;
	private boolean deleteList = true;
//	private String orderFlag = "";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_list_mycenter_waitbuyerpay);
//		orderFlag = getIntent().getExtras().getString("OrderFlag");
		ExitApplication.getInstance().addActivity(this);
		init();
		get_orderlist();
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		if(adapter==null){
			
		}
		else{
			show_sorted_order() ;
		}
		super.onResume();
	}
	@Override
	public void bindEvent() {
		// TODO Auto-generated method stub
		btnback.setOnClickListener(this);
		btnsort.setOnClickListener(this);
		tohome.setOnClickListener(this);
		tosearch.setOnClickListener(this);
		toshoppingcart.setOnClickListener(this);
		tomycenter.setOnClickListener(this);
		tomore.setOnClickListener(this);
		btnDelete.setOnClickListener(this);
		btnSelect.setOnClickListener(this);
		btnDelete.setOnClickListener(this);
	}

	@Override
	public void initValue() {
		// TODO Auto-generated method stub
		mHandler = new MyHandler(getMainLooper());
		btnDelete = (Button) findViewById(R.id.btn_list_mycenter_waitbuyerpay_bottom_delete);
		btnSelect = (Button) findViewById(R.id.btn_list_mycenter_waitbuyerpay_allselect);
		llbottom = (LinearLayout) findViewById(R.id.ll_list_mycenter_waitbuyerpay_bottom);
		
		listMycenterwaitbuyerpay = (ListView) findViewById(R.id.listview_list_mycenter_waitbuyerpay);
		btnback = (Button) findViewById(R.id.btn_list_mycenter_waitbuyerpay_back);
		//listMycenterwaitbuyerpay.setFocusable(true);
		btnsort = (ImageButton) findViewById(R.id.btn_list_mycenter_waitbuyerpay_sort);
		LayoutInflater	mInflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    View view2  = mInflater.inflate(R.layout.layout_item_bottom_menu,null);
	    tohome=(LinearLayout)view2.findViewById(R.id.ll_tohome);
	    tosearch=(LinearLayout) view2.findViewById(R.id.ll_tosearch);
	    toshoppingcart=(LinearLayout) view2.findViewById(R.id.ll_toshoppingcart);
	    tomycenter=(LinearLayout) view2.findViewById(R.id.ll_tomycenter);
	    tomore=(LinearLayout) view2.findViewById(R.id.ll_tomore);
	    bottommenu=(RelativeLayout) findViewById(R.id.rl_mycenter_waitbuyerpaylist_bottommenu);
	    bottommenu.addView(view2);
	    ViewGroup.LayoutParams lp =  view2.getLayoutParams();
	    lp.width = lp.FILL_PARENT;
	    view2.setLayoutParams(lp);
		ivLoading=(ImageView) findViewById(R.id.iv_mycenter_waitbuyerpay_loading);
		ivLoading.setVisibility(View.VISIBLE);
		ViewUtil.addLoadingAnimation(ApplicationUtil.myContext,
				ivLoading); 
	    
		get_pop_data();
	}
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.btn_list_mycenter_waitbuyerpay_allselect:
			int j = 0;
			List<Order> listTemp = new ArrayList<Order>(); 
			for (int i = 0; i < new_myList.size(); i++) {
				new_myList.get(i).setCheck(!new_myList.get(i).isCheck());
				listTemp.add(new_myList.get(i));
				if(new_myList.get(i).isCheck()){
					j++;
				}
			}
			if(j == 0){
				btnSelect.setText("全     选");
			}else{
				btnSelect.setText("反     选");
			}
			new_myList = listTemp;
			adapter.up_data(myContext, new_myList, WaitBuyerPayListActivity.this);
			break;
		
		case R.id.btn_list_mycenter_waitbuyerpay_bottom_delete:
			int k = 0;
			for (int i = 0; i < myList.size(); i++) {
				if(new_myList.get(i).isCheck()){
					k++;
					break;
				}
			}
			if(k == 0){
				Toast.makeText(myContext, "请先选中商品，再删除。。", 1).show();
			}else{
				Builder builder = new AlertDialog.Builder(WaitBuyerPayListActivity.this);
				builder.setTitle("是否删除");
				builder.setMessage("选中订单");
				builder.setPositiveButton("确定",
						new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						postDeleteOrder();
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
			break;
		case R.id.btn_list_mycenter_waitbuyerpay_back:
			this.finish();
			break;
		case R.id.btn_list_mycenter_waitbuyerpay_sort:
			showPopWindow();
			break;
		case R.id.ll_tohome :
			Intent intenthome =new Intent(WaitBuyerPayListActivity.this,HomeMainActivity.class);
			startActivity(intenthome);
			ExitApplication.getInstance().exit();
			break;
		case R.id.ll_tosearch :
			Intent intentsearch =new Intent(WaitBuyerPayListActivity.this,SearchMainActivity.class);
			startActivity(intentsearch);
			ExitApplication.getInstance().exit();
			break;
		case R.id.ll_toshoppingcart :
			Intent intentshoppingcart =new Intent(WaitBuyerPayListActivity.this,ShoppingcartMainListActivity.class);
			startActivity(intentshoppingcart);
			if(GlobalVarUtil.account.getUID()==null){
				Intent intentTolongin = new Intent(WaitBuyerPayListActivity.this,LoginActivity.class);
				startActivity(intentTolongin);
			}
//			ExitApplication.getInstance().exit();//购物车可以回退
			break;
		case R.id.ll_tomycenter :
			Intent intentmycenter =new Intent(WaitBuyerPayListActivity.this,MycenterMainActivity.class);
			startActivity(intentmycenter);
			if(GlobalVarUtil.account.getUID()==null){
				Intent intentTolongin = new Intent(WaitBuyerPayListActivity.this,LoginActivity.class);
				startActivity(intentTolongin);
			}
			ExitApplication.getInstance().exit();
			break;
		case R.id.ll_tomore :
			Intent intentmore =new Intent(WaitBuyerPayListActivity.this,MoreActivity.class);
			startActivity(intentmore);
			ExitApplication.getInstance().exit();
			break;
		}

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
		new_myList=List2;
		if(new_myList.size() == 0){
			llbottom.setVisibility(View.GONE);
		}else{
			llbottom.setVisibility(View.VISIBLE);
		}
	 adapter = new WaitBuyerPayAdapter(
				WaitBuyerPayListActivity.this.getBaseContext(),
				List2, WaitBuyerPayListActivity.this);
		listMycenterwaitbuyerpay.setAdapter(adapter);
		

	}

	

	// 获取订单列表

	private void get_orderlist() {
		this.doAsync(new CallEarliest<Object>() {
			@Override
			public void onCallEarliest() throws Exception {
				// TODO Auto-generated method stub
				
			}

		}, new Callable<Object>() {
			@Override
			public String call() {
				thread1= Thread.currentThread();
				String reqUrl = "";
//				if(orderFlag.equals("")){
//					
//				}else if(orderFlag.equals("historyorder")){
//					
//				}else if(orderFlag.equals("waitbuyerpay")){
//					
//				}else if(orderFlag.equals("sellerdeliever")){
//					
//				}else if(orderFlag.equals("confirmreceiving")){
//					
//				}
				reqUrl = getString(R.string.GetOrderList_php);

				Map<String, Object> parameters = new LinkedHashMap<String, Object>();

		 parameters.put("UID", GlobalVarUtil.account.getUID());
			//parameters.put("UID", 1);// 测试用的
					parameters.put("OrderListType", "PayOrderList");

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
				Log.e("waitpay json", jsonStr);
				thread2= Thread.currentThread();
				ViewUtil.removeLoadingAnimation(ivLoading);	
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
						new_myList = myList;
					 adapter = new WaitBuyerPayAdapter(
							WaitBuyerPayListActivity.this.getBaseContext(),
							myList, WaitBuyerPayListActivity.this);
					listMycenterwaitbuyerpay.setAdapter(adapter);
					llbottom.setVisibility(View.VISIBLE);
					}
				}
			}
		});
	}
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        /************************************************* 
         * 
         *  步骤3：处理银联手机支付控件返回的支付结果 
         *  
         ************************************************/
    	System.out.println( " 处理银联手机支付控件返回的支付结果");
        if (data == null) {
            return;
        }
        /*
         * 支付控件返回字符串:success、fail、cancel
         *      分别代表支付成功，支付失败，支付取消
         */
     
        pay_result=data.getExtras().getString("pay_result");
        Intent intent =new Intent(WaitBuyerPayListActivity.this,Uppay_state.class);
        
       // String ss = "[\""+orderID+"\"]";//测试
        
      
        intent.putExtra("orderID",orderID);
        intent.putExtra("pay_result", pay_result);
        
	        startActivity(intent);
	        ExitApplication.getInstance().exit();
     
    }
	//删除订单
	public void postDeleteOrder(){
		this.doAsync(new CallEarliest<Object>() {
			@Override
			public void onCallEarliest() throws Exception {
				// TODO Auto-generated method stub
				m_Dialog = ProgressDialog.show(WaitBuyerPayListActivity.this, "请等待...",
						"正在删除中，请稍候...", true);
			}

		}, new Callable<Object>() {
			@Override
			public String call() {
				String reqUrl = getString(R.string.DeleteOrder_http);
				Map<String, Object> parameters = new LinkedHashMap<String, Object>();
				String str = "";
				listTemp.clear();
				int j = new_myList.size();
				for (int i = 0; i < j; i++) {
					if(new_myList.get(i).isCheck()){
						listTemp.add(new_myList.get(i));
					}
				}
				
				str = new Gson().toJson(listTemp);
				parameters.put("list",str);
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
				String str = RegisterActivity.replaceBlank(jsonStr);
	            if(str.equals("-1")||str.equals("")||str.equals("faile")){
	            	Message msg = mHandler.obtainMessage(FAILD);
					mHandler.sendMessage(msg);
	            	deleteList = true;
	            }
	            if(str.equals("success")){
	            	for (int j2 = 0; j2 < listTemp.size(); j2++) {
						new_myList.remove(listTemp.get(j2));
						myList.remove(listTemp.get(j2));
					}
	            	Message msg = mHandler.obtainMessage(SUCCESS);
					mHandler.sendMessage(msg);
            		adapter.up_data(myContext, new_myList, WaitBuyerPayListActivity.this);
            		if(myList.size()==0){
            			WaitBuyerPayListActivity.this.finish();
	            	}
            		if(new_myList.size() == 0){
            			llbottom.setVisibility(View.GONE);
            		}
	            }
			}
		});
	}
	private class MyHandler extends Handler {

		@Override
		public void handleMessage(Message msg) {
			if (m_Dialog != null)
				m_Dialog.dismiss();
			switch (msg.what) {
			case SUCCESS:
				Toast.makeText(myContext, "删除订单成功...", 1).show();
				break;
			case FAILD:
				Toast.makeText(myContext, "删除订单失败...", 1).show();
				break;
			}

		}

		private MyHandler(Looper looper) {
			super(looper);
		}
	}
//获取TN号
	private void getTN() {
		this.doAsync(new CallEarliest<Object>() {
			@Override
			public void onCallEarliest() throws Exception {
				// TODO Auto-generated method stub
				
			}

		}, new Callable<Object>() {
			@Override
			public String call() {
				 String reqUrl;
					Map<String, Object> parameters = new LinkedHashMap<String, Object>();
             
				 reqUrl = getString(R.string.GetTN_php);
		      
			 String ss= "[\""+orderID+"\"]";//测试
		
				parameters.put("orderID",ss);
				parameters.put("type",new_myList.get(0).getOrderType());
			    
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
			isPay = false;	
			jsonStr = RegisterActivity.replaceBlank(jsonStr);
            System.out.println("code jsonStr=" + jsonStr);
            if(jsonStr.equals("-1")||jsonStr.trim().equals("")){
            	 new Uppay().pay("0",WaitBuyerPayListActivity.this,WaitBuyerPayListActivity.this);	
            }
            else{
            	new Uppay().pay(jsonStr,WaitBuyerPayListActivity.this,WaitBuyerPayListActivity.this);
            }
			}
		});
	}
	 
	// 订单操作

	public void manage_order(String orderID) {
		this.orderID=orderID;
		if(!isPay){
			isPay = true;
			getTN();
		}else{
			Toast.makeText(myContext, "已提交，请稍候。", 1).show();
		}
	}
	
	public void manage_order(Order orderDelete) {
		this.orderDelete = orderDelete;
		this.deleteList = false;
		postDeleteOrder();
	}
	
	public void manage_orderList(List<Order> myList2) {
		// TODO Auto-generated method stub
		myList = myList2;
		new_myList = myList2;
		int j = 0;
		for (int i = 0; i < myList2.size(); i++) {
			if(myList2.get(i).isCheck()){
				j++;
			}
		}
		if(j == 0){
			btnSelect.setText("全     选");
		}else{
			btnSelect.setText("反     选");
		}
	}
	
	//获取商品详情
	
	public void get_goodsinfo(Order order,OrderGoodsInfoEntity ordergoodsinfo) {
		if(Integer.parseInt(order.getOrderType())==2){
			 Intent intent = new Intent(WaitBuyerPayListActivity.this,GroupBuyGoodsDetailActivity.class);
				intent.putExtra("goodsID", ordergoodsinfo.getID());
				 startActivity(intent);
		}
//		else{
//			 Intent intent = new Intent(WaitBuyerPayListActivity.this,ShoppingmallGoodsDetailActivity.class);
//				intent.putExtra("ID", ordergoodsinfo.getID());
//				intent.putExtra("type", 1);
//				 startActivity(intent);
//		}
	}


}
