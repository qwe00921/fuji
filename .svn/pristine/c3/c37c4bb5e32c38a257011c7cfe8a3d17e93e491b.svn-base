package com.fsti.fjdicClient.activity.shoppingcart;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fsti.fjdicClient.R;
import com.fsti.fjdicClient.activity.BaseActivity;
import com.fsti.fjdicClient.activity.groupBuy.GroupBuySortListActivity;
import com.fsti.fjdicClient.activity.home.HomeMainActivity;
import com.fsti.fjdicClient.activity.login.LoginActivity;
import com.fsti.fjdicClient.activity.more.MoreActivity;
import com.fsti.fjdicClient.activity.mycenter.MycenterMainActivity;
import com.fsti.fjdicClient.activity.search.SearchMainActivity;
//import com.fsti.fjdicClient.activity.shoppingMall.ShoppingmallGoodsDetailActivity;
import com.fsti.fjdicClient.bean.ExitApplication;
import com.fsti.fjdicClient.bean.GoodsEntity;
import com.fsti.fjdicClient.bean.ShoppingcartInfoEntity;
import com.fsti.fjdicClient.dao.BusinessDao;
import com.fsti.fjdicClient.util.GlobalVarUtil;
import com.fsti.fjdicClient.util.HttpUtil;
import com.fsti.fjdicClient.util.SyncImageLoadUtil;
import com.fsti.fjdicClient.util.ViewUtil;
import com.fsti.fjdicClient.util.asyncUtil.CallEarliest;
import com.fsti.fjdicClient.util.asyncUtil.Callable;
import com.fsti.fjdicClient.util.asyncUtil.Callback;


public class ShoppingcartGoodsDetailActivity extends BaseActivity implements OnClickListener,TextWatcher{
	private GoodsEntity goodsEntity=new GoodsEntity();
	
	private Button btnBack;
	private Button btnUpGoods;
	private Button btnDownGoods;
	private Button btnAdd;
	private Button btnReduce;
	private Button btnDelete;
	private Button btnToCollection;
	private EditText etChangeCounts;
	private ImageView imageGoods;
	private TextView tvGoodsname;
	private TextView tvCounts;
	private TextView tvShop;
	private TextView tvPrice;
	private SyncImageLoadUtil syncImageLoad = new SyncImageLoadUtil();
	
	private List<List<ShoppingcartInfoEntity>> shoppingcartInfoAll;
	private ShoppingcartInfoEntity shoppingcartInfoEntity;
	private int groupPosition = 0;
	private int childPosition = 0;
	private LinearLayout tohome;
	private LinearLayout tosearch;
	private LinearLayout toshoppingcart;
	private LinearLayout tomycenter;
	private LinearLayout tomore;
	private RelativeLayout bottommenu;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_activity_shoppingcart_goodsdetail);
		ExitApplication.getInstance().addActivity(this);
		shoppingcartInfoAll = (List<List<ShoppingcartInfoEntity>>)this.getIntent().getSerializableExtra("shoppingcartInfoAll");
		Bundle bundle = this.getIntent().getExtras();
		childPosition = bundle.getInt("childPosition");
		groupPosition = bundle.getInt("groupPosition");
		shoppingcartInfoEntity = shoppingcartInfoAll.get(groupPosition).get(childPosition);
		init();
		}
	@Override
	public void bindEvent() {
		// TODO Auto-generated method stub
		btnAdd.setOnClickListener(this);
		btnReduce.setOnClickListener(this);
		btnBack.setOnClickListener(this);
		btnDownGoods.setOnClickListener(this);
		btnUpGoods.setOnClickListener(this);
		btnDelete.setOnClickListener(this);
		btnToCollection.setOnClickListener(this);
		
		etChangeCounts.addTextChangedListener(this);
		
		tohome.setOnClickListener(this);
		tosearch.setOnClickListener(this);
		toshoppingcart.setOnClickListener(this);
		tomycenter.setOnClickListener(this);
		tomore.setOnClickListener(this);
	}

	@Override
	public void initValue() {
		// TODO Auto-generated method stub
		btnAdd = (Button) findViewById(R.id.btn_shoppingcart_goodsdetail_add);
		btnReduce = (Button) findViewById(R.id.btn_shoppingcart_goodsdetail_reduction);
		btnBack = (Button) findViewById(R.id.btn_shoppingcart_goodsdetail_back);
		btnDownGoods = (Button) findViewById(R.id.btn_shoppingcart_goodsdetail_down);
		btnUpGoods = (Button) findViewById(R.id.btn_shoppingcart_goodsdetail_up);
		btnDelete = (Button) findViewById(R.id.btn_shoppingcart_goodsdetail_delete);
		btnToCollection = (Button) findViewById(R.id.btn_shoppingcart_goodsdetail_movetocollection);
		
		etChangeCounts = (EditText) findViewById(R.id.et_shoppingcart_goodsdetail_counts);
		tvCounts = (TextView) findViewById(R.id.tv_shoppingcart_goodsdetail_counts);
		tvGoodsname = (TextView) findViewById(R.id.tv_shoppingcart_goodsdetail_goodsdescribe);
		tvShop = (TextView) findViewById(R.id.tv_shoppingcart_goodsdetail_seller);
		tvPrice = (TextView) findViewById(R.id.tv_shoppingcart_goodsdetail_price);
		imageGoods = (ImageView) findViewById(R.id.iv_shoppingcart_goodsdetail_image);

		LayoutInflater	mInflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    View view2  = mInflater.inflate(R.layout.layout_item_bottom_menu,null);
	    tohome=(LinearLayout)view2.findViewById(R.id.ll_tohome);
	    tosearch=(LinearLayout) view2.findViewById(R.id.ll_tosearch);
	    toshoppingcart=(LinearLayout) view2.findViewById(R.id.ll_toshoppingcart);
	    tomycenter=(LinearLayout) view2.findViewById(R.id.ll_tomycenter);
	    tomore=(LinearLayout) view2.findViewById(R.id.ll_tomore);
	    bottommenu=(RelativeLayout) findViewById(R.id.rl_shoppingcart_goodsdetail_bottommenu);
	    bottommenu.addView(view2);
		initShowGoods();
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.btn_shoppingcart_goodsdetail_add:
			addCounts();
			break;
		case R.id.btn_shoppingcart_goodsdetail_reduction:
			reduceCounts();
			break;
		case R.id.btn_shoppingcart_goodsdetail_back:
			this.finish();
			break;
		case R.id.btn_shoppingcart_goodsdetail_down:
			showDownGoods();
			break;
		case R.id.btn_shoppingcart_goodsdetail_up:
			showUpGoods();
			break;
		case R.id.btn_shoppingcart_goodsdetail_delete:
			BusinessDao.deleteShoppingcartTableData(shoppingcartInfoEntity);
			this.finish();
			break;
		case R.id.btn_shoppingcart_goodsdetail_movetocollection:
			//收藏夹
			
			goodsEntity.setID(shoppingcartInfoEntity.getID());
			goodsEntity.setName(shoppingcartInfoEntity.getName());
			goodsEntity.setType(shoppingcartInfoEntity.getType());
			goodsEntity.setOriginalCost(shoppingcartInfoEntity.getPrice());
			if(GlobalVarUtil.account.getUID()==null||GlobalVarUtil.account.getUID().equals("")){
				Toast.makeText(ShoppingcartGoodsDetailActivity.this, "收藏失败，请先登陆系统",1).show();
				
				Intent intentTolongin = new Intent(ShoppingcartGoodsDetailActivity.this,LoginActivity.class);
				startActivity(intentTolongin);
			}
			else{
			insert_collect();
			insert_sellerinfo();
			}
//			BusinessDao.deleteShoppingcartTableData(shoppingcartInfoEntity);
//			this.finish();
			
			
			break;
		case R.id.ll_tohome :
			Intent intenthome =new Intent(ShoppingcartGoodsDetailActivity.this,HomeMainActivity.class);
			startActivity(intenthome);
			ExitApplication.getInstance().exit();
			break;
		case R.id.ll_tosearch :
			Intent intentsearch =new Intent(ShoppingcartGoodsDetailActivity.this,SearchMainActivity.class);
			startActivity(intentsearch);
			ExitApplication.getInstance().exit();
			break;
		case R.id.ll_toshoppingcart :
			Intent intentshoppingcart =new Intent(ShoppingcartGoodsDetailActivity.this,ShoppingcartMainListActivity.class);
			startActivity(intentshoppingcart);
			ExitApplication.getInstance().exit();
			break;
		case R.id.ll_tomycenter :
			Intent intentmycenter =new Intent(ShoppingcartGoodsDetailActivity.this,MycenterMainActivity.class);
			startActivity(intentmycenter);
			ExitApplication.getInstance().exit();
			break;
		case R.id.ll_tomore :
			Intent intentmore =new Intent(ShoppingcartGoodsDetailActivity.this,MoreActivity.class);
			startActivity(intentmore);
			ExitApplication.getInstance().exit();
			break;
		}
	}
	//初始化页面数据
	private void initShowGoods() {
		// TODO Auto-generated method stub
		tvShop.setText("卖家："+shoppingcartInfoEntity.getShopsName());
		tvGoodsname.setText(shoppingcartInfoEntity.getName());
		tvCounts.setText("x"+shoppingcartInfoEntity.getCounts());
		tvPrice.setText("¥"+Float.parseFloat(shoppingcartInfoEntity.getPrice()));
		etChangeCounts.setText(shoppingcartInfoEntity.getCounts()+"");
		//图片
		String url = shoppingcartInfoEntity.getImageUrl();
		syncImageLoad.displayImage(url , imageGoods,this);
	}
	private void showUpGoods() {//获取列表显示页面的上一个商品，也就是childPosition-1
		// TODO Auto-generated method stub
		if(childPosition > 0 && childPosition <= shoppingcartInfoAll.get(groupPosition).size()-1){
			--childPosition;
			shoppingcartInfoEntity = shoppingcartInfoAll.get(groupPosition).get(childPosition);
		}else if(childPosition == 0 ){
			if(groupPosition > 0 && groupPosition <= shoppingcartInfoAll.size()){
				--groupPosition;
				childPosition = shoppingcartInfoAll.get(groupPosition).size()-1;
				shoppingcartInfoEntity = shoppingcartInfoAll.get(groupPosition).get(childPosition);
			}else if(groupPosition == 0){
				shoppingcartInfoEntity = shoppingcartInfoAll.get(groupPosition).get(childPosition);
				
			}
		}
		initShowGoods();
	}
	private void showDownGoods() {//获取列表显示页面的下一个商品，也就是childPosition+1
		// TODO Auto-generated method stub
		if(childPosition >= 0 && childPosition < shoppingcartInfoAll.get(groupPosition).size()-1){
			++childPosition;
			shoppingcartInfoEntity = shoppingcartInfoAll.get(groupPosition).get(childPosition);
		}else if(childPosition == shoppingcartInfoAll.get(groupPosition).size()-1 ){
			if(groupPosition >= 0 && groupPosition < shoppingcartInfoAll.size()-1){
				childPosition = 0;
				++groupPosition;
				shoppingcartInfoEntity = shoppingcartInfoAll.get(groupPosition).get(childPosition);
			}else if(groupPosition == shoppingcartInfoAll.size()-1){
				shoppingcartInfoEntity = shoppingcartInfoAll.get(groupPosition).get(childPosition);
			}
		}
		initShowGoods();
	}
	private void reduceCounts() {
		// TODO Auto-generated method stub
		if("".equals(etChangeCounts.getText().toString())){
			etChangeCounts.setText("1");
			updateCounts(1);
		}else{
			int i = Integer.parseInt(etChangeCounts.getText().toString());
			if(i > 1){
				etChangeCounts.setText(--i+"");
			}else{
				etChangeCounts.setText("1");
			}
			updateCounts(i);
		}
	}
	private void addCounts() {
		// TODO Auto-generated method stub
		if("".equals(etChangeCounts.getText().toString())){
			etChangeCounts.setText("1");
			updateCounts(1);
		}else{
			int i = Integer.parseInt(etChangeCounts.getText().toString());
			etChangeCounts.setText(++i+"");
			updateCounts(i);
		}
	}
	private void updateCounts(int counts){
		shoppingcartInfoEntity.setCounts(counts);
		BusinessDao.updateShoppingcartTableData(BusinessDao.Table_Name_ShoppingcartInfo, shoppingcartInfoEntity, GlobalVarUtil.account.getUID());
	}
	@Override
	public void afterTextChanged(Editable arg0) {
		// TODO Auto-generated method stub
		if("".equals(etChangeCounts.getText().toString())){
			etChangeCounts.setText("1");
		}else{
			tvCounts.setText("x"+etChangeCounts.getText().toString());
			float f = 0.00f;
//			f = Float.parseFloat(shoppingcartInfoEntity.getPrice())*Integer.parseInt(etChangeCounts.getText().toString());
			f = Float.parseFloat(shoppingcartInfoEntity.getPrice());//只显示单价
			tvPrice.setText("¥"+f);
		}
	}
	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub
	}
	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub
		
	}
	
//加入收藏夹
	
	private void insert_collect() {
		this.doAsync(new CallEarliest<Object>() {
			@Override
			public void onCallEarliest() throws Exception {
				// TODO Auto-generated method stub
				
			}

		}, new Callable<Object>() {
			@Override
			public String call() {

				String reqUrl =getString(R.string.insertCollectGoods_http);

				Map<String, Object> parameters = new LinkedHashMap<String, Object>();
				
				parameters.put("UID", GlobalVarUtil.account.getUID());
				parameters.put("ID",goodsEntity.getID());
				parameters.put("type",goodsEntity.getType());
			    
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
			
				if (jsonStr.equals("-1")) {
					ViewUtil.showToast(myContext, myContext.getString(R.string.net_exception));
					
				} else {
					if (jsonStr.equals("1")) {
						Toast.makeText(ShoppingcartGoodsDetailActivity.this, "收藏失败，该商品已在收藏中",1).show();
					} else {
					
					
					if(BusinessDao.insertCollectionTableData(goodsEntity.getID(),goodsEntity.getType())){
					Toast.makeText(ShoppingcartGoodsDetailActivity.this, "收藏成功",1).show();
					}
					else{	Toast.makeText(ShoppingcartGoodsDetailActivity.this, "收藏失败，该商品已在收藏中",1).show();}
					}
				
				}
			}
		});
	}

	
	 //获取商家详情
	
	private void insert_sellerinfo() {
		this.doAsync(new CallEarliest<Object>() {
			@Override
			public void onCallEarliest() throws Exception {
				// TODO Auto-generated method stub
				
			}

		}, new Callable<Object>() {
			@Override
			public String call() {

				String reqUrl = getString(R.string.getShopsDetailInfo_php);

				Map<String, Object> parameters = new LinkedHashMap<String, Object>();
				
				
				parameters.put("ID",goodsEntity.getID());
				parameters.put("type","2");
			
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
			
				if (jsonStr.equals("-1")) {
					ViewUtil.showToast(myContext, myContext.getString(R.string.net_exception));
				} else {
					try {
						JSONObject json = new JSONObject(jsonStr);
						System.out.println("===================test"+json.getString("name"));
					    BusinessDao.updata_sellerinfo(goodsEntity, json.getString("ID"), json.getString("name"));
						
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
					
				
				}
			}
		});
	}

	
	

}
