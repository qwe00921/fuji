package com.fsti.fjdicClient.activity.mycenter;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
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
import com.fsti.fjdicClient.adapter.MycenterAddressAdapter;
import com.fsti.fjdicClient.bean.ConsigneeAddressEntity;
import com.fsti.fjdicClient.bean.ConsigneeAddressList;
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

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * 个人中心地址列表
 * 
 * @author 金涛
 * 
 */
public class AddressListActivity extends BaseActivity implements
		OnClickListener, OnItemClickListener {
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
	private ImageView ivLoading;
	private ListView listMycenterAddress;
	private List<ConsigneeAddressEntity> addressList;
	private Button btnaddresslistback;
	private Button btnaddresslistAddaddress;
	public static int is_finish = 0;// 设置是否结束本activity，2表示结束,1表示由团购模块切入，0表示由个人中心切入
	private View footer;
	private TextView no_addressinfo;
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

		Intent get_intent = this.getIntent();
		is_finish = get_intent.getIntExtra("is_finish", 0);
		setContentView(R.layout.layout_list_mycenter_address);
		// postaddresslistInfo();
		ExitApplication.getInstance().addActivity(this);
      init();
	}

	@Override
	public void bindEvent() {
		// TODO Auto-generated method stub
		btnaddresslistAddaddress.setOnClickListener(this);
		btnaddresslistback.setOnClickListener(this);
		listMycenterAddress.setOnItemClickListener(this);
		tohome.setOnClickListener(this);
		tosearch.setOnClickListener(this);
		toshoppingcart.setOnClickListener(this);
		tomycenter.setOnClickListener(this);
		tomore.setOnClickListener(this);
	}

	@Override
	public void initValue() {

		btnaddresslistAddaddress = (Button) findViewById(R.id.btn_mycenter_address_addaddress);
		btnaddresslistback = (Button) findViewById(R.id.btn_mycenter_address_back);

		listMycenterAddress = (ListView) findViewById(R.id.list_mycenter_address);
		footer = getLayoutInflater().inflate(R.layout.layout_activity_item_more, null);
		no_addressinfo=(TextView) footer.findViewById(R.id.tv_goodslist_more);
		ivLoading=(ImageView) findViewById(R.id.iv_addresslist_loading);
		ivLoading.setVisibility(View.VISIBLE);
		ViewUtil.addLoadingAnimation(ApplicationUtil.myContext,
				ivLoading);
		LayoutInflater	mInflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    View view2  = mInflater.inflate(R.layout.layout_item_bottom_menu,null);
	    tohome=(LinearLayout)view2.findViewById(R.id.ll_tohome);
	    tosearch=(LinearLayout) view2.findViewById(R.id.ll_tosearch);
	    toshoppingcart=(LinearLayout) view2.findViewById(R.id.ll_toshoppingcart);
	    tomycenter=(LinearLayout) view2.findViewById(R.id.ll_tomycenter);
	    tomore=(LinearLayout) view2.findViewById(R.id.ll_tomore);
	    bottommenu=(RelativeLayout) findViewById(R.id.rl_mycenter_addresslist_bottommenu);
	    bottommenu.addView(view2);
	    ViewGroup.LayoutParams lp =  view2.getLayoutParams();
	    lp.width = lp.FILL_PARENT;
	    view2.setLayoutParams(lp);
		showview();
	}
	private void showview(){
		BusinessDao.get_default_addressID();
		addressList = BusinessDao.get_addresslist();
		
		MycenterAddressAdapter adapter = new MycenterAddressAdapter(
				getBaseContext(), addressList, new Integer(BusinessDao
						.get_default_addressID()));
		listMycenterAddress.setAdapter(adapter);
		if (addressList.size()==0&&listMycenterAddress.getCount()==0) {
			no_addressinfo.setText("没有收获地址信息");
			listMycenterAddress.addFooterView(footer);
		} 
		
		System.out.println(listMycenterAddress.getCount()+"========"+addressList.size());
		 if(listMycenterAddress.getCount()>addressList.size()&&addressList.size()>0) {
				listMycenterAddress.removeFooterView(footer);
			}
		
			listMycenterAddress.setAdapter(adapter);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.btn_mycenter_address_back:
			this.finish();
			break;
		case R.id.btn_mycenter_address_addaddress:
			Intent intent2 = new Intent(this, AddressDetailActivity.class);

			startActivity(intent2);
			break;
		case R.id.ll_tohome :
			Intent intenthome =new Intent(AddressListActivity.this,HomeMainActivity.class);
			startActivity(intenthome);
			ExitApplication.getInstance().exit();
			break;
		case R.id.ll_tosearch :
			Intent intentsearch =new Intent(AddressListActivity.this,SearchMainActivity.class);
			startActivity(intentsearch);
			ExitApplication.getInstance().exit();
			break;
		case R.id.ll_toshoppingcart :
			Intent intentshoppingcart =new Intent(AddressListActivity.this,ShoppingcartMainListActivity.class);
			startActivity(intentshoppingcart);
			if(GlobalVarUtil.account.getUID()==null){
				Intent intentTolongin = new Intent(AddressListActivity.this,LoginActivity.class);
				startActivity(intentTolongin);
			}
//			ExitApplication.getInstance().exit();//购物车可以返回
			break;
		case R.id.ll_tomycenter :
			Intent intentmycenter =new Intent(AddressListActivity.this,MycenterMainActivity.class);
			startActivity(intentmycenter);
			if(GlobalVarUtil.account.getUID()==null){
				Intent intentTolongin = new Intent(AddressListActivity.this,LoginActivity.class);
				startActivity(intentTolongin);
			}
			ExitApplication.getInstance().exit();
			break;
		case R.id.ll_tomore :
			Intent intentmore =new Intent(AddressListActivity.this,MoreActivity.class);
			startActivity(intentmore);
			ExitApplication.getInstance().exit();
			break;
		

		}

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		if (!addressList.isEmpty()) {
			Intent intent = new Intent(this, AddressModifyActivity.class);
			System.out.println("......////////////////"+addressList.get(arg2).getID());
			intent.putExtra("address_ID", new Integer(addressList.get(arg2).getID()));

			startActivity(intent);
		}

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		if (is_finish == 2) {
			this.finish();

		} else {
			
			postaddresslistInfo();
			//init();

		}
		super.onResume();
	}

	// 获取地址列表
	private void postaddresslistInfo() {
		this.doAsync(new CallEarliest<Object>() {
			@Override
			public void onCallEarliest() throws Exception {
				// TODO Auto-generated method stub

			}

		}, new Callable<Object>() {
			@Override
			public String call() {
thread1=Thread.currentThread();
				String reqUrl = getString(R.string.getConsigneeAddress_http);

				Map<String, Object> parameters = new LinkedHashMap<String, Object>();
				parameters.put("UID", GlobalVarUtil.account.getUID());

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
				thread2=Thread.currentThread();
				ViewUtil.removeLoadingAnimation(ivLoading);
				System.out.println("code jsonStr=" + jsonStr);

				if (jsonStr.equals("-1")) {
					ViewUtil.showToast(myContext, myContext
							.getString(R.string.net_exception));
				} else {
					try {
						JSONObject jsonObject;
						jsonObject = new JSONObject(jsonStr);
			
						if(jsonStr.equals("{\"list\":[\"\"]}\n")){
							
						}
						else{
						JSONArray jsonArray = jsonObject.getJSONArray("list");
						// BusinessDao.clear_addressdata();
						for (int i = 0; i < jsonArray.length(); i++) {
							JSONObject jsonObject2 = (JSONObject) jsonArray.opt(i);
							ConsigneeAddressEntity addressinfo = new ConsigneeAddressEntity();

							addressinfo.setID(jsonObject2.getString("ID"));
							addressinfo.setName( jsonObject2.getString("name"));
							addressinfo.setTelephone(jsonObject2.getString("telephone"));
							addressinfo.setPostCode(jsonObject2.getString("postCode"));
							addressinfo.setAddress(jsonObject2.getString("Address"));
							addressinfo.setIsDefault(1);
		                    addressinfo.setUID(GlobalVarUtil.account.getUID());
		                   
							BusinessDao.insert_newaddress(addressinfo);
							
						}
						}

					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();

					}

				}
				showview();
			}
		});
	
	}

}
