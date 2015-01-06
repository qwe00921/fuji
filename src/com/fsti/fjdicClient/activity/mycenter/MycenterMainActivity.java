package com.fsti.fjdicClient.activity.mycenter;



import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.fsti.fjdicClient.R;
import com.fsti.fjdicClient.ApplicationUtil;
import com.fsti.fjdicClient.activity.BaseActivity;
import com.fsti.fjdicClient.activity.groupBuy.GroupBuySortListActivity;
import com.fsti.fjdicClient.activity.home.HomeMainActivity;
import com.fsti.fjdicClient.activity.login.LoginActivity;
import com.fsti.fjdicClient.activity.more.MoreActivity;
import com.fsti.fjdicClient.activity.search.SearchMainActivity;
import com.fsti.fjdicClient.activity.shoppingcart.ShoppingcartMainListActivity;
import com.fsti.fjdicClient.adapter.PopSortAdapter;
import com.fsti.fjdicClient.bean.ExitApplication;
import com.fsti.fjdicClient.bean.PopSortEntity;
import com.fsti.fjdicClient.util.GlobalVarUtil;
import com.fsti.fjdicClient.util.HttpUtil;
import com.fsti.fjdicClient.util.ViewUtil;
import com.fsti.fjdicClient.util.asyncUtil.CallEarliest;
import com.fsti.fjdicClient.util.asyncUtil.Callable;
import com.fsti.fjdicClient.util.asyncUtil.Callback;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager.LayoutParams;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

/**
 * 个人中心  主页
 * @author 
 *
 */ 
public class MycenterMainActivity extends BaseActivity implements OnClickListener{
	
	


	private RelativeLayout rlMycenterMainCollect;
	private RelativeLayout rlMycenterMainAddressManager;
	private RelativeLayout rlMycenterMainWaitBuyerPay;
	private RelativeLayout rlMycenterMainSellerDeliver;
	private RelativeLayout rlMycenterMainConfirmReceiving;
	private RelativeLayout rlMycenterMainHistoryOrder;
	private ImageView ivMycenterSet;
	private RelativeLayout rlMycenterSetExit;
	private RelativeLayout rlMycenterSetModifyPassword;
	private RelativeLayout rlMycenterSetModifyEmail;
	private RelativeLayout rlMycenterSetModifyPhone;
	private Dialog dialog;
	private ImageView ivLoading;
	private TextView tvUserName;
	private PopupWindow pop;
	
	private TextView tvPayOrderNum;
	private TextView tvDeliverOrderNum;
	private TextView tvConfirmOrderNum;
	private TextView tvHistoryOrderNum;
	private TextView tvCollectNum;
	
	private int is_starting=0;//避免多次打开该界面，虽然对用户透明
	private LinearLayout tohome;
	private LinearLayout tosearch;
	private LinearLayout toshoppingcart;
	private LinearLayout tomycenter;
	private LinearLayout tomore;
	private RelativeLayout bottommenu;
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {

			Builder builder = new AlertDialog.Builder(MycenterMainActivity.this);
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
		if(GlobalVarUtil.account.getUID()==null){
		this.finish();
		}
		init();
		super.onResume();
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_activity_mycenter_main);
		ExitApplication.getInstance().addActivity(this);
//		if(GlobalVarUtil.account.getTelephone()==null||GlobalVarUtil.account.getTelephone().equals("")){
//			Toast.makeText(MycenterMainActivity.this, "您还未绑定手机，请在设置中绑定手机号。", 1).show();
//		}
     
	}


	@Override
	public void bindEvent() {
		// TODO Auto-generated method stub
		rlMycenterMainAddressManager.setOnClickListener(this);
		rlMycenterMainCollect.setOnClickListener(this);
		ivMycenterSet.setOnClickListener(this);
		
		rlMycenterSetExit.setOnClickListener(this);
		rlMycenterSetModifyPassword.setOnClickListener(this);
		rlMycenterSetModifyEmail.setOnClickListener(this);
		rlMycenterSetModifyPhone.setOnClickListener(this);
		
		rlMycenterMainConfirmReceiving.setOnClickListener(this);
		rlMycenterMainHistoryOrder.setOnClickListener(this);
		rlMycenterMainSellerDeliver.setOnClickListener(this);
		rlMycenterMainWaitBuyerPay.setOnClickListener(this);
		
		tohome.setOnClickListener(this);
		tosearch.setOnClickListener(this);
		toshoppingcart.setOnClickListener(this);
		tomycenter.setOnClickListener(this);
		tomore.setOnClickListener(this);
	}

	@Override
	public void initValue() {
		// TODO Auto-generated method stub
		setDialog();
		
		rlMycenterSetExit = (RelativeLayout) dialog.findViewById(R.id.rl_mycenter_set_exit);
		rlMycenterSetModifyPassword = (RelativeLayout) dialog.findViewById(R.id.rl_mycenter_set_modifypassword);
		rlMycenterSetModifyEmail = (RelativeLayout) dialog.findViewById(R.id.rl_mycenter_set_modifyemail);
		rlMycenterSetModifyPhone = (RelativeLayout) dialog.findViewById(R.id.rl_mycenter_set_modifyphone);
		
		rlMycenterMainCollect = (RelativeLayout) findViewById(R.id.rl_mycenter_main_collect);
		rlMycenterMainAddressManager = (RelativeLayout) findViewById(R.id.rl_mycenter_main_addressmanager);
		ivMycenterSet = (ImageView) findViewById(R.id.iv_mycenter_main_set);
		
		rlMycenterMainConfirmReceiving = (RelativeLayout) findViewById(R.id.rl_mycenter_main_confirmreceiving);
		rlMycenterMainHistoryOrder = (RelativeLayout) findViewById(R.id.rl_mycenter_main_historyorder);
		rlMycenterMainWaitBuyerPay = (RelativeLayout) findViewById(R.id.rl_mycenter_main_waitbuyerpay);
		rlMycenterMainSellerDeliver = (RelativeLayout) findViewById(R.id.rl_mycenter_main_sellerdeliever);
		LayoutInflater	mInflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    View view2  = mInflater.inflate(R.layout.layout_item_bottom_menu,null);
	    tohome=(LinearLayout)view2.findViewById(R.id.ll_tohome);
	    tosearch=(LinearLayout) view2.findViewById(R.id.ll_tosearch);
	    toshoppingcart=(LinearLayout) view2.findViewById(R.id.ll_toshoppingcart);
	    tomycenter=(LinearLayout) view2.findViewById(R.id.ll_tomycenter);
	    tomore=(LinearLayout) view2.findViewById(R.id.ll_tomore);
	    bottommenu=(RelativeLayout) findViewById(R.id.rl_mycenter_main_bottommenu);
	    bottommenu.addView(view2);
	    ViewGroup.LayoutParams lp =  view2.getLayoutParams();
	    lp.width = lp.FILL_PARENT;
	    view2.setLayoutParams(lp);
		tvPayOrderNum = (TextView) findViewById(R.id.tv_mycenter_main_PayOrderNum);
		tvDeliverOrderNum = (TextView) findViewById(R.id.tv_mycenter_main_DeliverOrderNum);
		tvConfirmOrderNum= (TextView) findViewById(R.id.tv_mycenter_main_ConfirmOrderNum);
		tvHistoryOrderNum = (TextView) findViewById(R.id.tv_mycenter_main_HistoryOrderNum);
		tvCollectNum = (TextView) findViewById(R.id.tv_mycenter_main_collectnum);
		ivLoading=(ImageView) findViewById(R.id.iv_mycentermain_loading);
		ivLoading.setVisibility(View.VISIBLE);
		ViewUtil.addLoadingAnimation(ApplicationUtil.myContext,
				ivLoading);
		tvUserName = (TextView) findViewById(R.id.tv_mycenter_main_user);
		  tvUserName.setText(GlobalVarUtil.account.getNickName());
		
		get_ordernum();
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()){
		case R.id.rl_mycenter_main_collect:
			if(Integer.parseInt(tvCollectNum.getText().toString())!=0){
			Intent intentToCollect = new Intent(MycenterMainActivity.this, CollectMainListActivity.class);
			startActivity(intentToCollect);
			}
			else{
				Toast.makeText(MycenterMainActivity.this, "没有收藏的商品", 1).show();
			}
			break;
		case R.id.rl_mycenter_main_addressmanager:
			Intent intentToAddress = new Intent(MycenterMainActivity.this, AddressListActivity.class);
			startActivity(intentToAddress);
			break;
		case R.id.iv_mycenter_main_set:
			showPopWindow();
			//dialog.show();
			break;
		case R.id.rl_mycenter_set_exit:
			
			break;
		case R.id.rl_mycenter_set_modifypassword:
			
			break;
		case R.id.rl_mycenter_set_modifyemail:
			
			break;
		case R.id.rl_mycenter_set_modifyphone:
			
			break;
//			case R.id.rl_mycenter_main_historyorder:
//				if(Integer.parseInt(tvHistoryOrderNum.getText().toString())!=0){
//				Intent intentToHistoryOrder = new Intent(MycenterMainActivity.this, WaitBuyerPayListActivity.class);
//				intentToHistoryOrder.putExtra("OrderFlag", "historyorder");
//				startActivity(intentToHistoryOrder);
//				}
//				else{
//					Toast.makeText(MycenterMainActivity.this, "没有订单", 1).show();
//				}
//				break;
//			case R.id.rl_mycenter_main_waitbuyerpay:
//				if(Integer.parseInt(tvPayOrderNum.getText().toString())!=0){
//				Intent intentToWaitBuyerPay = new Intent(MycenterMainActivity.this, WaitBuyerPayListActivity.class);
//				intentToWaitBuyerPay.putExtra("OrderFlag", "waitbuyerpay");
//				startActivity(intentToWaitBuyerPay);
//				}
//				else{
//					Toast.makeText(MycenterMainActivity.this, "没有订单", 1).show();
//				}
//				break;
//			case R.id.rl_mycenter_main_sellerdeliever:
//				if(Integer.parseInt(tvDeliverOrderNum.getText().toString())!=0){
//				Intent intentToSellerDeliever = new Intent(MycenterMainActivity.this, WaitBuyerPayListActivity.class);
//				intentToSellerDeliever.putExtra("OrderFlag", "sellerdeliever");
//				startActivity(intentToSellerDeliever);
//			 }
//				else{
//					Toast.makeText(MycenterMainActivity.this, "没有订单", 1).show();
//				}
//				break;
//			case R.id.rl_mycenter_main_confirmreceiving:
//				if(Integer.parseInt(tvConfirmOrderNum.getText().toString())!=0){
//				Intent intentToConfirmReceiving = new Intent(MycenterMainActivity.this, WaitBuyerPayListActivity.class);
//				intentToConfirmReceiving.putExtra("OrderFlag", "confirmreceiving");
//				startActivity(intentToConfirmReceiving);
//				}
//				else{
//					Toast.makeText(MycenterMainActivity.this, "没有订单", 1).show();
//				}
//				break;
		case R.id.rl_mycenter_main_historyorder:
			if(Integer.parseInt(tvHistoryOrderNum.getText().toString())!=0){
			Intent intentToHistoryOrder = new Intent(MycenterMainActivity.this, HistoryOrderListActivity.class);
			startActivity(intentToHistoryOrder);
			}
			else{
				Toast.makeText(MycenterMainActivity.this, "没有订单", 1).show();
			}
			break;
		case R.id.rl_mycenter_main_waitbuyerpay:
			if(Integer.parseInt(tvPayOrderNum.getText().toString())!=0){
			Intent intentToWaitBuyerPay = new Intent(MycenterMainActivity.this, WaitBuyerPayListActivity.class);
			startActivity(intentToWaitBuyerPay);
			}
			else{
				Toast.makeText(MycenterMainActivity.this, "没有订单", 1).show();
			}
			break;
		case R.id.rl_mycenter_main_sellerdeliever:
			if(Integer.parseInt(tvDeliverOrderNum.getText().toString())!=0){
			Intent intentToSellerDeliever = new Intent(MycenterMainActivity.this, RemindSellerDeliverListActivity.class);
			startActivity(intentToSellerDeliever);
		 }
			else{
				Toast.makeText(MycenterMainActivity.this, "没有订单", 1).show();
			}
			break;
		case R.id.rl_mycenter_main_confirmreceiving:
			if(Integer.parseInt(tvConfirmOrderNum.getText().toString())!=0){
			Intent intentToConfirmReceiving = new Intent(MycenterMainActivity.this, ConfirmReceivingListActivity.class);
			startActivity(intentToConfirmReceiving);
			}
			else{
				Toast.makeText(MycenterMainActivity.this, "没有订单", 1).show();
			}
			break;
		case R.id.ll_tohome :
			Intent intenthome =new Intent(MycenterMainActivity.this,HomeMainActivity.class);
			startActivity(intenthome);
			ExitApplication.getInstance().exit();
			break;
		case R.id.ll_tosearch :
			Intent intentsearch =new Intent(MycenterMainActivity.this,SearchMainActivity.class);
			startActivity(intentsearch);
			ExitApplication.getInstance().exit();
			break;
		case R.id.ll_toshoppingcart :
			Intent intentshoppingcart =new Intent(MycenterMainActivity.this,ShoppingcartMainListActivity.class);
			startActivity(intentshoppingcart);
			if(GlobalVarUtil.account.getUID()==null){
				Intent intentTolongin = new Intent(MycenterMainActivity.this,LoginActivity.class);
				startActivity(intentTolongin);
			}
//			ExitApplication.getInstance().exit();//购物车可以回退
			break;
		case R.id.ll_tomycenter :
		
			break;
		case R.id.ll_tomore :
			Intent intentmore =new Intent(MycenterMainActivity.this,MoreActivity.class);
			startActivity(intentmore);
			ExitApplication.getInstance().exit();
			break;
	}
		
	}
	public void showPopWindow() {
		int with = this.getWindowManager().getDefaultDisplay().getWidth();
			initPop();
		pop.showAsDropDown(ivMycenterSet, with / 2-220, 0);
	}
	private void initPop() {
		// TODO Auto-generated method stub
		LayoutInflater inflater = getLayoutInflater();		
		View view  = inflater.inflate(R.layout.layout_fn_list_pop_sort,null);
	     pop= new PopupWindow(view,  this.getWindowManager().getDefaultDisplay().getWidth()/3, LayoutParams.WRAP_CONTENT);
			pop.setBackgroundDrawable(new BitmapDrawable());
			pop.setTouchable(true);
			pop.setFocusable(true);
		ListView listView = (ListView) view.findViewById(R.id.lv_sort);
		PopSortAdapter myPopSortAdapter = new PopSortAdapter(myContext, getSortData());
		listView.setAdapter(myPopSortAdapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

		
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				pop.dismiss();
				switch(arg2){
				case 0:
					GlobalVarUtil.account.setUID(null);
					GlobalVarUtil.account.setNickName("");
					GlobalVarUtil.account.setIsLogin(1);
					SharedPreferences logininfo=getSharedPreferences("logininfo", MycenterMainActivity.this.MODE_WORLD_WRITEABLE) ;
				    Editor editor=  logininfo.edit();
				    editor.putString("nickname", GlobalVarUtil.account.getNickName());
				    editor.putString("UID", GlobalVarUtil.account.getUID());
				    editor.commit();
					dialog.cancel();
					//MycenterMainActivity.this.finish();
					Intent intentTolongin = new Intent(MycenterMainActivity.this,LoginActivity.class);
					startActivity(intentTolongin);
					
					break;
				case 1:
					Intent intentToPassword = new Intent(MycenterMainActivity.this, ModifyPassWordActivity.class);
					startActivity(intentToPassword);
					break;
				case 2:
					Intent intentToEmail = new Intent(MycenterMainActivity.this, ModifyEmailActivity.class);
					startActivity(intentToEmail);
					break;
				case 3:
					Intent intentToPhone = new Intent(MycenterMainActivity.this, ModifyPhoneActivity.class);
					startActivity(intentToPhone);
					break;
				
				
				
				}
				
				
			}
			
		});
		 
		
	}




	private List<PopSortEntity> getSortData(){
		List<PopSortEntity> list = new ArrayList<PopSortEntity>();
		PopSortEntity entity1 = new PopSortEntity();
		entity1.setPopSortName("注销登录");
		entity1.setPopSortValue("all");
		entity1.setSelected(false);
		PopSortEntity entity2 = new PopSortEntity();
		entity2.setPopSortName("修改密码");
		entity2.setPopSortValue("foods");
		entity2.setSelected(false);
		PopSortEntity entity3 = new PopSortEntity();
		entity3.setPopSortName("修改邮箱");
		entity3.setPopSortValue("relax");
		entity3.setSelected(false);
		PopSortEntity entity4 = new PopSortEntity();
		entity4.setPopSortName("绑定手机号");
		entity4.setPopSortValue("life");
		entity4.setSelected(false);
		list.add(entity1);
		list.add(entity2);
		list.add(entity3);
		list.add(entity4);
		return list;
	}

	private void setDialog() {
		// TODO Auto-generated method stub

		//获取屏幕宽度
		WindowManager windowManager = getWindowManager();
		Display display = windowManager.getDefaultDisplay();
		//实例dialog
		dialog = new Dialog(this,R.style.pop_dialog);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉dialog的标题 
		dialog.setContentView(R.layout.layout_activity_mycenter_set); 
		Window dialogWindow = dialog.getWindow();
		WindowManager.LayoutParams lp = dialogWindow.getAttributes();
		//dialog与屏幕等宽
		lp.width = (int)(display.getWidth());
		dialog.getWindow().setAttributes(lp);
		
		dialogWindow.setGravity(Gravity.BOTTOM);
		dialog.setCanceledOnTouchOutside(true);
	}
	
	
	//获取订单数量

	private void get_ordernum() {
		this.doAsync(new CallEarliest<Object>() {
			@Override
			public void onCallEarliest() throws Exception {
				// TODO Auto-generated method stub
				
			}

		}, new Callable<Object>() {
			@Override
			public String call() {

				String reqUrl = getString(R.string.GetOrderNumber_php);

				Map<String, Object> parameters = new LinkedHashMap<String, Object>();
				
				   parameters.put("UID", GlobalVarUtil.account.getUID());
			   // parameters.put("UID", "1");
		
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
		  ViewUtil.removeLoadingAnimation(ivLoading);	
	        System.out.println("code jsonStr=" + jsonStr);
			try {
				JSONObject json = new JSONObject(jsonStr);
			
				JSONObject json2 = new JSONObject(json.getString("OrderNum"));
				String t =json2.getString("PayOrderNum") ;
				tvPayOrderNum.setText(json2.getString("PayOrderNum"));
				tvDeliverOrderNum.setText(json2.getString("DeliverOrderNum"));
				tvConfirmOrderNum.setText(json2.getString("ConfirmOrderNum"));
				tvHistoryOrderNum.setText(json2.getString("HistoryOrderNum"));
				tvCollectNum.setText(json2.getString("CollectionsNum"));//正式时使用
           
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
				
			}
		});
	}
	

}
