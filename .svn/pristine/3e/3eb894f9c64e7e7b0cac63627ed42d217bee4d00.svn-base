package com.fsti.fjdicClient.activity.mycenter;


import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


import com.fsti.fjdicClient.R;
import com.fsti.fjdicClient.activity.BaseActivity;
import com.fsti.fjdicClient.activity.groupBuy.GroupBuySortListActivity;
import com.fsti.fjdicClient.activity.home.HomeMainActivity;
import com.fsti.fjdicClient.activity.login.LoginActivity;
import com.fsti.fjdicClient.activity.login.RegisterActivity;
import com.fsti.fjdicClient.activity.more.MoreActivity;
import com.fsti.fjdicClient.activity.search.SearchMainActivity;
import com.fsti.fjdicClient.activity.shoppingcart.ShoppingcartMainListActivity;
import com.fsti.fjdicClient.bean.ConsigneeAddressEntity;
import com.fsti.fjdicClient.bean.ExitApplication;
import com.fsti.fjdicClient.bean.RegisterEntity;
import com.fsti.fjdicClient.dao.BusinessDao;
import com.fsti.fjdicClient.util.GlobalVarUtil;
import com.fsti.fjdicClient.util.HttpUtil;
import com.fsti.fjdicClient.util.ViewUtil;
import com.fsti.fjdicClient.util.asyncUtil.CallEarliest;
import com.fsti.fjdicClient.util.asyncUtil.Callable;
import com.fsti.fjdicClient.util.asyncUtil.Callback;
import com.fsti.fjdicClient.util.timecount.Timecount;
import com.google.gson.Gson;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TextView.OnEditorActionListener;

/**
 * 个人中心 收货地址添加
 * @author 
 *
 */
public class AddressDetailActivity extends BaseActivity implements OnClickListener {
	private Button btaddressmodifyBack;
	private Button btaddressmodifySave;
	private EditText etaddressmodifyConsigneename;
	private EditText etaddressmodifyPhone;
	private EditText etaddressmodifyPostalcode;
	private EditText etaddressmodifyAddress;
	private LinearLayout tohome;
	private LinearLayout tosearch;
	private LinearLayout toshoppingcart;
	private LinearLayout tomycenter;
	private LinearLayout tomore;
	private RelativeLayout bottommenu;
	private boolean isSave = true;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_activity_mycenter_addressmodify);
		ExitApplication.getInstance().addActivity(this);
		
		init();
	}


	@Override
	public void bindEvent() {
		// TODO Auto-generated method stub
		btaddressmodifyBack.setOnClickListener(this);
		btaddressmodifySave.setOnClickListener(this);
		tohome.setOnClickListener(this);
		tosearch.setOnClickListener(this);
		toshoppingcart.setOnClickListener(this);
		tomycenter.setOnClickListener(this);
		tomore.setOnClickListener(this);
		etaddressmodifyConsigneename.setOnEditorActionListener(new OnEditorActionListener() {
			
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				// TODO Auto-generated method stub
				return (event.getKeyCode() == KeyEvent.KEYCODE_ENTER);
			}
		});
		etaddressmodifyAddress.setOnEditorActionListener(new OnEditorActionListener() {
			
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				// TODO Auto-generated method stub
				return (event.getKeyCode() == KeyEvent.KEYCODE_ENTER);
			}
		});
	}

	@Override
	public void initValue() {
		// TODO Auto-generated method stub
		btaddressmodifyBack = (Button)findViewById(R.id.btn_mycenter_addressmodify_modify_back);
		btaddressmodifySave = (Button)findViewById(R.id.btn_mycenter_addressmodify_modify_save);
		etaddressmodifyConsigneename= (EditText) findViewById(R.id.et_mycenter_addressmodify_consigneename2);
		etaddressmodifyPhone= (EditText) findViewById(R.id.et_mycenter_addressmodify_phone2);
		etaddressmodifyPostalcode= (EditText) findViewById(R.id.et_mycenter_addressmodify_postalcode2);
		etaddressmodifyAddress= (EditText) findViewById(R.id.et_mycenter_addressmodify_address2);
		LayoutInflater	mInflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    View view2  = mInflater.inflate(R.layout.layout_item_bottom_menu,null);
	    tohome=(LinearLayout)view2.findViewById(R.id.ll_tohome);
	    tosearch=(LinearLayout) view2.findViewById(R.id.ll_tosearch);
	    toshoppingcart=(LinearLayout) view2.findViewById(R.id.ll_toshoppingcart);
	    tomycenter=(LinearLayout) view2.findViewById(R.id.ll_tomycenter);
	    tomore=(LinearLayout) view2.findViewById(R.id.ll_tomore);
	    bottommenu=(RelativeLayout) findViewById(R.id.rl_mycenter_addressdetail_bottommenu);
	    bottommenu.addView(view2);
	    ViewGroup.LayoutParams lp =  view2.getLayoutParams();
	    lp.width = lp.FILL_PARENT;
	    view2.setLayoutParams(lp);
	}


	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch(arg0.getId()){
		case R.id.btn_mycenter_addressmodify_modify_back:
			
			this.finish();
			break;
		case R.id.btn_mycenter_addressmodify_modify_save:
			if(isSave){
				if(etaddressmodifyConsigneename.getText().toString().equals("")||etaddressmodifyConsigneename.getText().toString().equals(" ")){
					Toast.makeText(AddressDetailActivity.this, "收货人姓名不能为空", 1).show();
				}
				else if(etaddressmodifyPhone.getText().toString().equals("")||etaddressmodifyPhone.getText().toString().equals(" ")){
					Toast.makeText(AddressDetailActivity.this, "收货人手机号不能为空", 1).show();
				}
				else if(!RegisterActivity.isPhoneNumberValid(etaddressmodifyPhone.getText().toString())){
					Toast.makeText(AddressDetailActivity.this, "手机号码不正确！请重新输入", 1).show();
				}
				else if(etaddressmodifyPostalcode.getText().toString().equals("")||etaddressmodifyPostalcode.getText().toString().equals(" ")){
					Toast.makeText(AddressDetailActivity.this, "收货人邮编不能为空", 1).show();
				}
				else if(etaddressmodifyAddress.getText().toString().equals("")||etaddressmodifyAddress.getText().toString().equals(" ")){
					Toast.makeText(AddressDetailActivity.this, "收货人地址不能为空", 1).show();
				}else{
					postaddaddressresult();
				}
			}else{
				Toast.makeText(AddressDetailActivity.this, "保存中，请稍候。", 1).show();
			}
			break;
		case R.id.ll_tohome :
			Intent intenthome =new Intent(AddressDetailActivity.this,HomeMainActivity.class);
			startActivity(intenthome);
			ExitApplication.getInstance().exit();
			break;
		case R.id.ll_tosearch :
			Intent intentsearch =new Intent(AddressDetailActivity.this,SearchMainActivity.class);
			startActivity(intentsearch);
			ExitApplication.getInstance().exit();
			break;
		case R.id.ll_toshoppingcart :
			Intent intentshoppingcart =new Intent(AddressDetailActivity.this,ShoppingcartMainListActivity.class);
			startActivity(intentshoppingcart);
			if(GlobalVarUtil.account.getUID()==null){
				Intent intentTolongin = new Intent(AddressDetailActivity.this,LoginActivity.class);
				startActivity(intentTolongin);
			}
//			ExitApplication.getInstance().exit();//购物车可以返回
			break;
		case R.id.ll_tomycenter :
			Intent intentmycenter =new Intent(AddressDetailActivity.this,MycenterMainActivity.class);
			startActivity(intentmycenter);
			if(GlobalVarUtil.account.getUID()==null){
				Intent intentTolongin = new Intent(AddressDetailActivity.this,LoginActivity.class);
				startActivity(intentTolongin);
			}
			ExitApplication.getInstance().exit();
			break;
		case R.id.ll_tomore :
			Intent intentmore =new Intent(AddressDetailActivity.this,MoreActivity.class);
			startActivity(intentmore);
			ExitApplication.getInstance().exit();
			break;
		
		}
		
	}
	
	//获取增加地址结果
	private void postaddaddressresult() {
		
		this.doAsync(new CallEarliest<Object>() {
			@Override
			public void onCallEarliest() throws Exception {
				// TODO Auto-generated method stub
				isSave = false;
			}

		}, new Callable<Object>() {
			@Override
			public String call() {

				String reqUrl = getString(R.string.insertConsigneeAddress_http);
		     
		      ConsigneeAddressEntity  addressentity = new ConsigneeAddressEntity();
		      addressentity.setUID(GlobalVarUtil.account.getUID());
		     
		      addressentity.setName(etaddressmodifyConsigneename.getText().toString());
		      addressentity.setAddress(etaddressmodifyAddress.getText().toString());
		      addressentity.setTelephone(etaddressmodifyPhone.getText().toString());
		      addressentity.setPostCode( etaddressmodifyPostalcode.getText().toString());
		     
		      
		      Gson gson=new Gson();  
		        String reString=gson.toJson(addressentity);  
		        
				Map<String, Object> parameters = new LinkedHashMap<String, Object>();
				parameters.put("ConsigneeAddressEntity", reString);
				
				String jsonStr = "";
				try {
					jsonStr = HttpUtil.postData(reqUrl, parameters,
							GlobalVarUtil.ENCODING);
					  System.out.println(" jsonStr=" + jsonStr);
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
            System.out.println("code jsonStr=" + jsonStr);
				jsonStr = RegisterActivity.replaceBlank(jsonStr);
				if (jsonStr.equals("-1")) {
					isSave = true;	
					ViewUtil.showToast(myContext, myContext.getString(R.string.net_exception));
				} else {
					ConsigneeAddressEntity addressinfoentity = new ConsigneeAddressEntity();
					addressinfoentity.setAddress(etaddressmodifyAddress.getText().toString());
					addressinfoentity.setID(jsonStr.trim());
					addressinfoentity.setIsDefault(1);
					addressinfoentity.setName(etaddressmodifyConsigneename.getText().toString());
					addressinfoentity.setPostCode(etaddressmodifyPostalcode.getText().toString());
					addressinfoentity.setTelephone( etaddressmodifyPhone.getText().toString());
					addressinfoentity.setUID(GlobalVarUtil.account.getUID());
					
					BusinessDao.insert_newaddress(addressinfoentity);
					isSave = true;	
					
					Intent intent2= new Intent(AddressDetailActivity.this,AddressModifyActivity.class);
					intent2.putExtra("address_ID", new Integer( jsonStr.trim()));
					startActivity(intent2);
					AddressDetailActivity.this.finish();
				}
			}
		});
		
	}



}
