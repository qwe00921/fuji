package com.fsti.fjdicClient.activity.groupBuy;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fsti.fjdicClient.R;
import com.fsti.fjdicClient.activity.BaseActivity;
import com.fsti.fjdicClient.activity.home.HomeMainActivity;
import com.fsti.fjdicClient.activity.login.LoginActivity;
import com.fsti.fjdicClient.activity.login.RegisterActivity;
import com.fsti.fjdicClient.activity.more.MoreActivity;
import com.fsti.fjdicClient.activity.mycenter.AddressListActivity;
import com.fsti.fjdicClient.activity.mycenter.MycenterMainActivity;
import com.fsti.fjdicClient.activity.search.SearchMainActivity;
import com.fsti.fjdicClient.activity.shoppingcart.ShoppingcartMainListActivity;
import com.fsti.fjdicClient.bean.ConsigneeAddressEntity;
import com.fsti.fjdicClient.bean.ExitApplication;
import com.fsti.fjdicClient.bean.GoodsDetailInfoEntity;
import com.fsti.fjdicClient.bean.GoodsEntity;
import com.fsti.fjdicClient.bean.orderInfoEntity;
import com.fsti.fjdicClient.bean.persellerorderEntity;
import com.fsti.fjdicClient.dao.BusinessDao;
import com.fsti.fjdicClient.util.GlobalVarUtil;
import com.fsti.fjdicClient.util.HttpUtil;
import com.fsti.fjdicClient.util.ViewUtil;
import com.fsti.fjdicClient.util.asyncUtil.CallEarliest;
import com.fsti.fjdicClient.util.asyncUtil.Callable;
import com.fsti.fjdicClient.util.asyncUtil.Callback;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
/**
 * 生活团  提交订单
 * @author 
 *
 */

public class SubmitOrderActivity extends BaseActivity implements OnClickListener, TextWatcher{
	private RelativeLayout rlsubmitorderAdderss ;
	private RelativeLayout rlreceivephone;
	private Button btsubmitorderBack;
	private TextView tvsubmitorderGoodsname;
	private TextView tvsubmitorderPerprice;
	private EditText etsubmitorderCounts;//商品数量
	private Button btsubmitorderReduction;
	private Button btsubmitorderAdd;
	private TextView tvsubmitorderTotalprice;
	private TextView tvsubmitorderAddress;
	private TextView tvsubmitorderbuyername;
	private TextView tvsubmitorderbuyerphone;
	private EditText etsubmitorderNotedetail;
	private EditText etsubmitorderSetphone;
	private Button btsubmitorderUporder;
	private static  ConsigneeAddressEntity addressEntity=null;
	private GoodsEntity goodsEntity;
	private LinearLayout tohome;
	private LinearLayout tosearch;
	private LinearLayout toshoppingcart;
	private LinearLayout tomycenter;
	private LinearLayout tomore;
	private RelativeLayout bottommenu;
	private GoodsDetailInfoEntity goodsinfo;
	private String mgoodsAttribute="0";
	private String orderID;
	private boolean isbuy = false;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_activity_groupbuy_submitorder);
		ExitApplication.getInstance().addActivity(this);
		etsubmitorderCounts = (EditText)findViewById(R.id.et_groupbuy_submitorder_counts);
		
		init();
	}

	@Override
	public void bindEvent() {
		rlsubmitorderAdderss.setOnClickListener(this);
    btsubmitorderBack.setOnClickListener(this);
    btsubmitorderAdd.setOnClickListener(this);
    btsubmitorderReduction.setOnClickListener(this);
    btsubmitorderUporder.setOnClickListener(this);
    etsubmitorderCounts.addTextChangedListener(this);
    tohome.setOnClickListener(this);
	tosearch.setOnClickListener(this);
	toshoppingcart.setOnClickListener(this);
	tomycenter.setOnClickListener(this);
	tomore.setOnClickListener(this);
	}

	@Override
	public void initValue() {
		
		rlreceivephone=(RelativeLayout)findViewById(R.id.rl_groupbuy_submitorder_receivephone);
		rlsubmitorderAdderss=(RelativeLayout)findViewById(R.id.rl_groupbuy_firmorder_address2);
		btsubmitorderBack = (Button)findViewById(R.id.btn_groupbuy_submitorder_back);
		btsubmitorderReduction = (Button)findViewById(R.id.btn_groupbuy_submitorder_reduction);
		btsubmitorderAdd = (Button)findViewById(R.id.btn_groupbuy_submitorder_add);
		btsubmitorderUporder = (Button)findViewById(R.id.btn_groupbuy_submitorder_uporder);
		tvsubmitorderGoodsname = (TextView)findViewById(R.id.tv_groupbuy_submitorder_goodsname);
		tvsubmitorderPerprice = (TextView)findViewById(R.id.tl_groupbuy_submitorder_perprice);
		tvsubmitorderTotalprice=(TextView)findViewById(R.id.tl_groupbuy_submitorder_totalprice);
		tvsubmitorderAddress = (TextView)findViewById(R.id.tv_groupbuy_address_detail);
		tvsubmitorderbuyername = (TextView)findViewById(R.id.tv_groupbuy_address_buyername);
		tvsubmitorderbuyerphone = (TextView)findViewById(R.id.tv_groupbuy_address_buyerphone);
		
		etsubmitorderNotedetail = (EditText)findViewById(R.id.et_groupbuy_submitorder_notedetail);
		etsubmitorderSetphone = (EditText)findViewById(R.id.et_groupbuy_submitorder_setphone);
	
		LayoutInflater	mInflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    View view2  = mInflater.inflate(R.layout.layout_item_bottom_menu,null);
	    tohome=(LinearLayout)view2.findViewById(R.id.ll_tohome);
	    tosearch=(LinearLayout) view2.findViewById(R.id.ll_tosearch);
	    toshoppingcart=(LinearLayout) view2.findViewById(R.id.ll_toshoppingcart);
	    tomycenter=(LinearLayout) view2.findViewById(R.id.ll_tomycenter);
	    tomore=(LinearLayout) view2.findViewById(R.id.ll_tomore);
	    bottommenu=(RelativeLayout) findViewById(R.id.rl_groupbuy_submitorder_bottommenu);
	    bottommenu.addView(view2);
	    ViewGroup.LayoutParams lp =  view2.getLayoutParams();
	    lp.width = lp.FILL_PARENT;
	    view2.setLayoutParams(lp);
		 Intent intent = getIntent();
		  goodsEntity =(GoodsEntity) intent.getSerializableExtra("goodsEntity");
	      goodsinfo=(GoodsDetailInfoEntity) intent.getSerializableExtra("goodsinfo");
	     tvsubmitorderGoodsname.setText(goodsEntity.getName());
	     tvsubmitorderPerprice.setText(goodsEntity.getDiscountedPrice()+"元");
	     etsubmitorderCounts.setText("1");
	     tvsubmitorderTotalprice.setText(count_totalprice(goodsEntity)+"元");
	    // mgoodsAttribute = goodsEntity.getGoodsAttribute();
	     if(mgoodsAttribute.equals("0")){
	    	 rlsubmitorderAdderss.setVisibility(View.VISIBLE);
	    	 rlreceivephone.setVisibility(View.GONE);
	     }
	     else{
	    	 rlsubmitorderAdderss.setVisibility(View.GONE);
	    	 rlreceivephone.setVisibility(View.VISIBLE);
	     }
	     
	     addressEntity =null;
	     etsubmitorderSetphone.setText("");
	     if(!BusinessDao.get_default_addressID().equals("0")){
			  addressEntity =BusinessDao.get_default_addressinfo();	
			tvsubmitorderAddress.setText(addressEntity.getAddress());
			tvsubmitorderbuyername.setText(addressEntity.getName());
			tvsubmitorderbuyerphone.setText(addressEntity.getTelephone());
		}
	}
	
	
	private String count_totalprice(GoodsEntity gEntity){
		
		return  String .valueOf((float) Math.round(Float.parseFloat(gEntity.getDiscountedPrice())*
		new Integer(etsubmitorderCounts.getText().toString())*100)/100);
	}

	@Override
	public void afterTextChanged(Editable arg0) {
		// TODO Auto-generated method stub
		
		if(etsubmitorderCounts.getText().toString()==null||etsubmitorderCounts.getText().toString().equals("")){
			etsubmitorderCounts.setText("1");
			 tvsubmitorderTotalprice.setText(count_totalprice(goodsEntity)+"元");
			
		}
		else{
		 tvsubmitorderTotalprice.setText(count_totalprice(goodsEntity)+"元");
		}
	}

	@Override
	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
			int arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}

	
	


	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch(arg0.getId()){
		case R.id.rl_groupbuy_firmorder_address2:
			Intent intent1 = new Intent(SubmitOrderActivity.this,AddressListActivity.class);
			intent1.putExtra("is_finish", 1);
			startActivity(intent1);
			break;
		case R.id.btn_groupbuy_submitorder_back:
//			Intent intent2 = new Intent(this,GroupBuyDetailActivity.class);
//			startActivity(intent2);
			this.finish();
			break;
		case R.id.btn_groupbuy_submitorder_reduction:
			int count = Integer.parseInt(etsubmitorderCounts.getText().toString()) ;
			if(count>1){
				count = count-1;
			}
			etsubmitorderCounts.setText(String.valueOf(count));
			
			
			break;
		case R.id.btn_groupbuy_submitorder_add:
			int count2 =Integer.parseInt(etsubmitorderCounts.getText().toString()) ;
				count2 = count2+1;
				etsubmitorderCounts.setText(String.valueOf(count2));
			
				
			break;
		
		case R.id.btn_groupbuy_submitorder_uporder:
			if(mgoodsAttribute.equals("0")){
				
				if(addressEntity==null){
					Toast.makeText(SubmitOrderActivity.this,"收货地址不能为空", 1).show();
				}
				else{
					if(!isbuy){
						isbuy = true;
						upload_order();
					}else{
						Toast.makeText(SubmitOrderActivity.this,"已提交，请稍候。", 1).show();
					}
				}
			}
			else{
				if(etsubmitorderSetphone.getText().toString().equals("")){
					Toast.makeText(SubmitOrderActivity.this,"联系电话不能为空", 1).show();
				}
				else{
					upload_order();
				}
			}
			
			
			
			break;
		case R.id.ll_tohome :
			Intent intenthome =new Intent(SubmitOrderActivity.this,HomeMainActivity.class);
			startActivity(intenthome);
			ExitApplication.getInstance().exit();
			break;
		case R.id.ll_tosearch :
			Intent intentsearch =new Intent(SubmitOrderActivity.this,SearchMainActivity.class);
			startActivity(intentsearch);
			ExitApplication.getInstance().exit();
			break;
		case R.id.ll_toshoppingcart :
			Intent intentshoppingcart =new Intent(SubmitOrderActivity.this,ShoppingcartMainListActivity.class);
			startActivity(intentshoppingcart);
			if(GlobalVarUtil.account.getUID()==null){
				Intent intentTolongin = new Intent(SubmitOrderActivity.this,LoginActivity.class);
				startActivity(intentTolongin);
			}
//			ExitApplication.getInstance().exit();//购物车可以返回
			break;
		case R.id.ll_tomycenter :
			Intent intentmycenter =new Intent(SubmitOrderActivity.this,MycenterMainActivity.class);
			startActivity(intentmycenter);
			if(GlobalVarUtil.account.getUID()==null){
				Intent intentTolongin = new Intent(SubmitOrderActivity.this,LoginActivity.class);
				startActivity(intentTolongin);
			}
			ExitApplication.getInstance().exit();
			break;
		case R.id.ll_tomore :
			Intent intentmore =new Intent(SubmitOrderActivity.this,MoreActivity.class);
			startActivity(intentmore);
			ExitApplication.getInstance().exit();
			break;
		
		}
		
		
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		//更新收获地址信息
		init();
		super.onResume();
	}
	
	
	//提交订单
	
	private void upload_order() {
		this.doAsync(new CallEarliest<Object>() {
			@Override
			public void onCallEarliest() throws Exception {
				// TODO Auto-generated method stub
				
			}

		}, new Callable<Object>() {
			@Override
			public String call() {

				String reqUrl = getString(R.string.UploadOrder_php);

				Map<String, Object> parameters = new LinkedHashMap<String, Object>();
				
				
				parameters.put("UID",GlobalVarUtil.account.getUID());
				parameters.put("nickName",GlobalVarUtil.account.getNickName());
			
			parameters.put("type",goodsEntity.getType());
			//	parameters.put("type","1");
			//	parameters.put("remarks",etsubmitorderNotedetail.getText().toString());
				
				
				List<persellerorderEntity> lists =new ArrayList<persellerorderEntity>();
				//合成单个商家订单
				persellerorderEntity persellerorder = new persellerorderEntity();
				persellerorder.setFreight(goodsEntity.getFreight());
//				persellerorder.setShopsID("2");
				persellerorder.setShopsID(goodsEntity.getShopsID());
				persellerorder.setRemarks(etsubmitorderNotedetail.getText().toString());
				List<orderInfoEntity> list =new ArrayList<orderInfoEntity>();
				orderInfoEntity orderinfo = new orderInfoEntity();
				orderinfo.setID(goodsEntity.getID());
				orderinfo.setName(goodsEntity.getName());
				orderinfo.setCounts(Integer.parseInt( etsubmitorderCounts.getText().toString()));
				orderinfo.setPrice(goodsEntity.getDiscountedPrice());
				orderinfo.setFID(goodsEntity.getFID());
				list.add(orderinfo);
				/*orderInfoEntity orderinfo3 = new orderInfoEntity();
				orderinfo3.setID(goodsEntity.getID());
				orderinfo3.setName(goodsEntity.getName());
				orderinfo3.setCounts(Integer.parseInt( etsubmitorderCounts.getText().toString()));
				orderinfo3.setPrice(goodsEntity.getDiscountedPrice());
				orderinfo3.setFID(goodsEntity.getFID());
				list.add(orderinfo3);*/
				
				
				persellerorder.setList(list);
				
				
				
				
				/*List<orderInfoEntity> list2 =new ArrayList<orderInfoEntity>();
				//合成单个商家订单
				persellerorderEntity persellerorder2 = new persellerorderEntity();
				persellerorder2.setFreight(goodsEntity.getFreight());
				persellerorder2.setShopsID("3");
				persellerorder2.setRemarks(etsubmitorderNotedetail.getText().toString());
				orderInfoEntity orderinfo2 = new orderInfoEntity();
				orderinfo2.setID(goodsEntity.getID());
				orderinfo2.setName(goodsEntity.getName());
				orderinfo2.setCounts(Integer.parseInt( etsubmitorderCounts.getText().toString()));
				orderinfo2.setPrice(goodsEntity.getDiscountedPrice());
				orderinfo2.setFID(goodsEntity.getFID());
				list2.add(orderinfo2);
				orderInfoEntity orderinfo4 = new orderInfoEntity();
				orderinfo4.setID(goodsEntity.getID());
				orderinfo4.setName(goodsEntity.getName());
				orderinfo4.setCounts(Integer.parseInt( etsubmitorderCounts.getText().toString()));
				orderinfo4.setPrice(goodsEntity.getDiscountedPrice());
				orderinfo4.setFID(goodsEntity.getFID());
				list2.add(orderinfo4);
				persellerorder2.setList(list2);*/
				
				//将单个商家订单添加到订单合集中
				lists.add(persellerorder);
			//	lists.add(persellerorder2);
				
				
				
				
				 Gson gson=new Gson();  
			        String listString=gson.toJson(lists); 
				parameters.put("list",listString);
				String addressEntityString=gson.toJson(addressEntity); 
				parameters.put("address",addressEntityString);
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
            
            isbuy = false;
            jsonStr=RegisterActivity.replaceBlank(jsonStr);
            if(jsonStr.trim().equals("-1")||jsonStr.trim().equals("")){
            	Toast.makeText(SubmitOrderActivity.this,"提交订单失败",1).show();
            }
            else{
				orderID=jsonStr.trim();
					 System.out.println("	orderID===========" + 	orderID);
				Intent intent3 = new Intent(SubmitOrderActivity.this,GroupBuyFirmOrderActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("goodsEntity",  goodsEntity);
				intent3.putExtra("phone", etsubmitorderSetphone.getText().toString());
				intent3.putExtra("note", etsubmitorderNotedetail.getText().toString());
				intent3.putExtra("num", etsubmitorderCounts.getText().toString());
				intent3.putExtra("orderID", orderID);
				intent3.putExtras(bundle);
				startActivity(intent3);
            }
				
			}
			
		});
	}
	
	
	
	
}
