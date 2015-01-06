package com.fsti.fjdicClient.activity.mycenter;

import java.util.LinkedHashMap;
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
import com.fsti.fjdicClient.dao.BusinessDao;
import com.fsti.fjdicClient.util.GlobalVarUtil;
import com.fsti.fjdicClient.util.HttpUtil;
import com.fsti.fjdicClient.util.ViewUtil;
import com.fsti.fjdicClient.util.asyncUtil.CallEarliest;
import com.fsti.fjdicClient.util.asyncUtil.Callable;
import com.fsti.fjdicClient.util.asyncUtil.Callback;
import com.google.gson.Gson;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TextView.OnEditorActionListener;

/**
 * 个人中心 收货地址详情
 * 
 * @author
 * 
 */
public class AddressModifyActivity extends BaseActivity implements
		OnClickListener, OnFocusChangeListener {
	private Button btaddressdetailBack;
	private Button btaddressdetailModify;
	private Button btaddressdetailDelete;
	private Button btaddressdetailTohome;
	private TextView tvaddressmodifyName;
	private TextView tvaddressmodifyPhone;
	private TextView tvaddressmodifyPostalcode;
	private TextView tvaddressmodifyAddrress;
	private EditText etaddressmodifyName;
	private EditText etaddressmodifyPhone;
	private EditText etaddressmodifyPostalcode;
	private EditText etaddressmodifyAddrress;
	private int is_modifying = 0;// 1表示正在修改中
	private int address_ID = 0;
	private int run_key =0 ;//0表示完成按键，1表示删除按键操作
	private LinearLayout tohome;
	private LinearLayout tosearch;
	private LinearLayout toshoppingcart;
	private LinearLayout tomycenter;
	private LinearLayout tomore;
	private RelativeLayout bottommenu;
	// 接口相关
	private String reqUrl = null;
	private Map<String, Object> parameters;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_activity_mycenter_addressdetail);
		ExitApplication.getInstance().addActivity(this);
		Intent intent = this.getIntent();
		address_ID = intent.getIntExtra("address_ID", 0);
	
		init();
	}

	@Override
	public void bindEvent() {
		// TODO Auto-generated method stub
		btaddressdetailBack.setOnClickListener(this);
		btaddressdetailDelete.setOnClickListener(this);
		btaddressdetailModify.setOnClickListener(this);
		btaddressdetailTohome.setOnClickListener(this);

		etaddressmodifyName.setOnFocusChangeListener(this);
		etaddressmodifyName.setOnEditorActionListener(new OnEditorActionListener() {
			
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				// TODO Auto-generated method stub
				return (event.getKeyCode() == KeyEvent.KEYCODE_ENTER);
			}
		});
		etaddressmodifyPhone.setOnFocusChangeListener(this);
		etaddressmodifyPostalcode.setOnFocusChangeListener(this);
		etaddressmodifyAddrress.setOnFocusChangeListener(this);
		etaddressmodifyAddrress.setOnEditorActionListener(new OnEditorActionListener() {
			
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				// TODO Auto-generated method stub
				return (event.getKeyCode() == KeyEvent.KEYCODE_ENTER);
			}
		});
		tohome.setOnClickListener(this);
		tosearch.setOnClickListener(this);
		toshoppingcart.setOnClickListener(this);
		tomycenter.setOnClickListener(this);
		tomore.setOnClickListener(this);
	}

	@Override
	public void initValue() {
		// TODO Auto-generated method stub
		btaddressdetailBack = (Button) findViewById(R.id.btn_mycenter_addressdetail_back);
		btaddressdetailModify = (Button) findViewById(R.id.btn_mycenter_addressdetail_modify);
		btaddressdetailDelete = (Button) findViewById(R.id.btn_deliveryaddressdetails_delete);
		btaddressdetailTohome = (Button) findViewById(R.id.btn_deliveryaddressdetails_tohome);
		tvaddressmodifyName = (TextView) findViewById(R.id.tv_addressmodify_name1);
		tvaddressmodifyPhone = (TextView) findViewById(R.id.tv_addressmodify_phone1);
		tvaddressmodifyPostalcode = (TextView) findViewById(R.id.tv_addressmodify_Postalcode1);
		tvaddressmodifyAddrress = (TextView) findViewById(R.id.tv_addressmodify_address1);
		etaddressmodifyName = (EditText) findViewById(R.id.et_addressmodify_name2);
		etaddressmodifyPhone = (EditText) findViewById(R.id.et_addressmodify_phone2);
		etaddressmodifyPostalcode = (EditText) findViewById(R.id.et_addressmodify_Postalcode2);
		etaddressmodifyAddrress = (EditText) findViewById(R.id.et_addressmodify_address2);

		
		ConsigneeAddressEntity addressEntity = BusinessDao.get_addressinfo(String.valueOf(address_ID));
		tvaddressmodifyName.setText(addressEntity.getName());
		tvaddressmodifyPhone.setText(addressEntity.getTelephone());
		tvaddressmodifyPostalcode.setText(addressEntity.getPostCode());
		tvaddressmodifyAddrress.setText(addressEntity.getAddress());
		
		LayoutInflater	mInflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    View view2  = mInflater.inflate(R.layout.layout_item_bottom_menu,null);
	    tohome=(LinearLayout)view2.findViewById(R.id.ll_tohome);
	    tosearch=(LinearLayout) view2.findViewById(R.id.ll_tosearch);
	    toshoppingcart=(LinearLayout) view2.findViewById(R.id.ll_toshoppingcart);
	    tomycenter=(LinearLayout) view2.findViewById(R.id.ll_tomycenter);
	    tomore=(LinearLayout) view2.findViewById(R.id.ll_tomore);
	    bottommenu=(RelativeLayout) findViewById(R.id.rl_mycenter_addressmodify_bottommenu);
	    bottommenu.addView(view2);
	    ViewGroup.LayoutParams lp =  view2.getLayoutParams();
	    lp.width = lp.FILL_PARENT;
	    view2.setLayoutParams(lp);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.btn_mycenter_addressdetail_back:
			if(is_modifying==1){
				no_modifying();
			}
			else{
			this.finish();
			}
			break;
		case R.id.btn_mycenter_addressdetail_modify:
			if (is_modifying == 0) {
				is_modifying = 1;
				btaddressdetailTohome.setVisibility(View.GONE);
				btaddressdetailDelete.setVisibility(View.GONE);
				btaddressdetailModify.setText("确认修改");

				tvaddressmodifyName.setVisibility(TextView.GONE);
				tvaddressmodifyPhone.setVisibility(TextView.GONE);
				tvaddressmodifyPostalcode.setVisibility(TextView.GONE);
				tvaddressmodifyAddrress.setVisibility(TextView.GONE);
				etaddressmodifyName.setVisibility(TextView.VISIBLE);
				etaddressmodifyPhone.setVisibility(TextView.VISIBLE);
				etaddressmodifyPostalcode.setVisibility(TextView.VISIBLE);
				etaddressmodifyAddrress.setVisibility(TextView.VISIBLE);
				etaddressmodifyName.setText(tvaddressmodifyName.getText()
						.toString());
				etaddressmodifyPhone.setText(tvaddressmodifyPhone.getText()
						.toString());
				etaddressmodifyPostalcode.setText(tvaddressmodifyPostalcode
						.getText().toString());
				etaddressmodifyAddrress.setText(tvaddressmodifyAddrress
						.getText().toString());

				etaddressmodifyName.setFocusable(true);
				etaddressmodifyName.requestFocus();
				etaddressmodifyName.setSelection(etaddressmodifyName.length());

			} else {
				
				if(etaddressmodifyName.getText()==null||etaddressmodifyName.getText().toString().equals("")||etaddressmodifyName.getText().toString().equals(" ")){
					Toast.makeText(AddressModifyActivity.this, "收货人姓名不能为空", 1).show();
				}
				else if(etaddressmodifyPhone.getText()==null||etaddressmodifyPhone.getText().toString().equals("")||etaddressmodifyPhone.getText().toString().equals(" ")){
					Toast.makeText(AddressModifyActivity.this, "收货人手机号不能为空", 1).show();
				}
				else if(!RegisterActivity.isPhoneNumberValid(etaddressmodifyPhone.getText().toString())){
					Toast.makeText(AddressModifyActivity.this, "手机号码不正确！请重新输入", 1).show();
				}
				else if(etaddressmodifyPostalcode.getText()==null||etaddressmodifyPostalcode.getText().toString().equals("")||etaddressmodifyPostalcode.getText().toString().equals(" ")){
					Toast.makeText(AddressModifyActivity.this, "收货人邮编不能为空", 1).show();
				}
				else if(etaddressmodifyAddrress.getText()==null||etaddressmodifyAddrress.getText().toString().equals("")||etaddressmodifyAddrress.getText().toString().equals(" ")){
					Toast.makeText(AddressModifyActivity.this, "收货人地址不能为空", 1).show();
				}else{
					no_modifying();
					postaddressdetailInfo();
				}
				
			}

			break;
		case R.id.btn_deliveryaddressdetails_delete:
			run_key=1;
			if (is_modifying == 1) {
				Toast.makeText(AddressModifyActivity.this, "请先点击完成按键，结束地址详情编辑",
						1).show();
			} else {
				
				
					postaddressdetailInfo();
				
			}
			break;
		case R.id.btn_deliveryaddressdetails_tohome:
			if (is_modifying == 1) {
				Toast.makeText(AddressModifyActivity.this, "请先点击完成按键，结束地址详情编辑",
						1).show();
			} else {
				
					BusinessDao.set_defaultaddress(String.valueOf(address_ID));
						
					if (AddressListActivity.is_finish == 1) {
						AddressListActivity.is_finish = 2;
					}

					this.finish();
				}
			
			break;
		case R.id.ll_tohome :
			Intent intenthome =new Intent(AddressModifyActivity.this,HomeMainActivity.class);
			startActivity(intenthome);
			ExitApplication.getInstance().exit();
			break;
		case R.id.ll_tosearch :
			Intent intentsearch =new Intent(AddressModifyActivity.this,SearchMainActivity.class);
			startActivity(intentsearch);
			ExitApplication.getInstance().exit();
			break;
		case R.id.ll_toshoppingcart :
			Intent intentshoppingcart =new Intent(AddressModifyActivity.this,ShoppingcartMainListActivity.class);
			startActivity(intentshoppingcart);
			if(GlobalVarUtil.account.getUID()==null){
				Intent intentTolongin = new Intent(AddressModifyActivity.this,LoginActivity.class);
				startActivity(intentTolongin);
			}
//			ExitApplication.getInstance().exit();//购物车可以返回
			break;
		case R.id.ll_tomycenter :
			Intent intentmycenter =new Intent(AddressModifyActivity.this,MycenterMainActivity.class);
			startActivity(intentmycenter);
			if(GlobalVarUtil.account.getUID()==null){
				Intent intentTolongin = new Intent(AddressModifyActivity.this,LoginActivity.class);
				startActivity(intentTolongin);
			}
			ExitApplication.getInstance().exit();
			break;
		case R.id.ll_tomore :
			Intent intentmore =new Intent(AddressModifyActivity.this,MoreActivity.class);
			startActivity(intentmore);
			ExitApplication.getInstance().exit();
			break;
		

		}
	}

	private void no_modifying(){//取消编辑
		run_key=0;
		is_modifying = 0;
		btaddressdetailTohome.setVisibility(View.VISIBLE);
		btaddressdetailDelete.setVisibility(View.VISIBLE);
		btaddressdetailModify.setText("编辑收货地址");
		etaddressmodifyName.setVisibility(TextView.GONE);
		etaddressmodifyPhone.setVisibility(TextView.GONE);
		etaddressmodifyPostalcode.setVisibility(TextView.GONE);
		etaddressmodifyAddrress.setVisibility(TextView.GONE);
		tvaddressmodifyName.setVisibility(TextView.VISIBLE);
		tvaddressmodifyPhone.setVisibility(TextView.VISIBLE);
		tvaddressmodifyPostalcode.setVisibility(TextView.VISIBLE);
		tvaddressmodifyAddrress.setVisibility(TextView.VISIBLE);
		tvaddressmodifyName.setText(etaddressmodifyName.getText()
				.toString());
		tvaddressmodifyPhone.setText(etaddressmodifyPhone.getText()
				.toString());
		tvaddressmodifyPostalcode.setText(etaddressmodifyPostalcode
				.getText().toString());
		tvaddressmodifyAddrress.setText(etaddressmodifyAddrress
				.getText().toString());
	
		
	}
	
	
	@Override
	public void onFocusChange(View arg0, boolean arg1) {
		// TODO Auto-generated method stub

		switch (arg0.getId()) {
		case R.id.et_addressmodify_name2:

			//System.out.println(".......................onfo1....");

			etaddressmodifyName.setSelection(etaddressmodifyName.length());
			break;
		case R.id.et_addressmodify_phone2:

			//System.out.println(".......................onfo2....");

			etaddressmodifyPhone.setSelection(etaddressmodifyPhone.length());
			break;
		case R.id.et_addressmodify_Postalcode2:

			//System.out.println(".......................onfo3....");

			etaddressmodifyPostalcode.setSelection(etaddressmodifyPostalcode
					.length());

			break;
		case R.id.et_addressmodify_address2:

			//System.out.println(".......................onfo4....");

			etaddressmodifyAddrress.setSelection(etaddressmodifyAddrress
					.length());
			break;
		}

	}

	// 获取修改地址详情
	private void postaddressdetailInfo() {
		this.doAsync(new CallEarliest<Object>() {
			@Override
			public void onCallEarliest() throws Exception {
				// TODO Auto-generated method stub

			}

		}, new Callable<Object>() {
			@Override
			public String call() {
                 if(run_key==1){
                	 reqUrl = getString(R.string.deleteConsigneeAddress_http);
     				parameters = new LinkedHashMap<String, Object>();
     				parameters.put("ID", address_ID );
                	 
                 }
                 else{

    				 ConsigneeAddressEntity  addressentity = new ConsigneeAddressEntity();
    			      addressentity.setID(String.valueOf(address_ID));
    			      addressentity.setName(etaddressmodifyName.getText()
    							.toString());
    			      addressentity.setAddress(etaddressmodifyAddrress
    							.getText().toString());
    			      addressentity.setTelephone(etaddressmodifyPhone.getText()
    							.toString());
    			      addressentity.setPostCode( etaddressmodifyPostalcode
    							.getText().toString());
    			     
    			      Gson gson=new Gson();  
    			        String reString=gson.toJson(addressentity);  
    			        
                	 reqUrl = getString(R.string.updateConsigneeAddress_http);
     				parameters = new LinkedHashMap<String, Object>();
     				parameters.put("ConsigneeAddressEntity",reString); 
                 }
				
				
				
				
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

				System.out.println("code jsonStr=" + jsonStr);
				
				
				if(run_key==1){
				
				if (jsonStr.equals("-1")) {
					ViewUtil.showToast(myContext, myContext
							.getString(R.string.net_exception));
					
					
				} else {
					
					if(jsonStr.equals("1")){
						Toast.makeText(AddressModifyActivity.this, "删除失败", 1).show();
					}
					else{
						Toast.makeText(AddressModifyActivity.this, "删除成功", 1).show();
						BusinessDao.delete_one_address(String.valueOf(address_ID));
						AddressModifyActivity.this.finish();
					}
					
				}	

				}
				else{
					if (jsonStr.equals("-1")) {
						ViewUtil.showToast(myContext, myContext
								.getString(R.string.net_exception));
						
					} else {
						
						if(jsonStr.equals("1")){
							Toast.makeText(AddressModifyActivity.this, "修改失败", 1).show();
						}
						else{
							Toast.makeText(AddressModifyActivity.this, "修改成功", 1).show();
							
							ContentValues setdefaultaddress = new ContentValues();
							setdefaultaddress.put("name", etaddressmodifyName.getText().toString());
							setdefaultaddress.put("telephone", etaddressmodifyPhone.getText().toString());
							setdefaultaddress.put("postCode", etaddressmodifyPostalcode.getText().toString());
							setdefaultaddress.put("Address", etaddressmodifyAddrress.getText().toString());
							BusinessDao.updata_address(String.valueOf(address_ID), setdefaultaddress);
						}
						
					}	
				}
				
				
			}
		});
	}

}
