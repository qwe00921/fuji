package com.fsti.fjdicClient.activity.mycenter;


import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.AdapterView.OnItemClickListener;

import com.fsti.fjdicClient.R;
import com.fsti.fjdicClient.activity.BaseActivity;
import com.fsti.fjdicClient.activity.groupBuy.GroupBuySortListActivity;
import com.fsti.fjdicClient.activity.home.HomeMainActivity;
import com.fsti.fjdicClient.activity.login.LoginActivity;
import com.fsti.fjdicClient.activity.more.MoreActivity;
import com.fsti.fjdicClient.activity.search.SearchMainActivity;
import com.fsti.fjdicClient.activity.shoppingcart.ShoppingcartMainListActivity;
import com.fsti.fjdicClient.adapter.MycenterGoodsCommentsAdapter;
import com.fsti.fjdicClient.bean.ExitApplication;
import com.fsti.fjdicClient.bean.Order;
import com.fsti.fjdicClient.bean.OrderGoodsInfoEntity;
import com.fsti.fjdicClient.util.GlobalVarUtil;

/**
 * 个人中心  评价商品列表
 * @author 
 *
 */
public class GoodsCommentsListActivity extends BaseActivity implements OnClickListener,OnItemClickListener{
	
	private ListView listGoodsComments;
	private List<OrderGoodsInfoEntity> goodsList;
	private Button btnback;
	private Order order;
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
		setContentView(R.layout.layout_list_mycenter_goodscomments);
		ExitApplication.getInstance().addActivity(this);
		order=(Order) this.getIntent().getSerializableExtra("order");
		init();
		
		listGoodsComments.setDivider(null);
		MycenterGoodsCommentsAdapter adapter = new MycenterGoodsCommentsAdapter(getBaseContext(), order);
		adapter.set_activity(7,GoodsCommentsListActivity.this);
		listGoodsComments.setAdapter(adapter);
	}


	@Override
	public void bindEvent() {
		// TODO Auto-generated method stub
		btnback.setOnClickListener(this);
		listGoodsComments.setOnItemClickListener(this);
		tohome.setOnClickListener(this);
		tosearch.setOnClickListener(this);
		toshoppingcart.setOnClickListener(this);
		tomycenter.setOnClickListener(this);
		tomore.setOnClickListener(this);
	}
	@Override
	public void initValue() {
		// TODO Auto-generated method stub
		btnback = (Button) findViewById(R.id.btn_list_mycenter_goodscomments_back);
		listGoodsComments = (ListView) findViewById(R.id.list_mycenter_goodscomments);
		
		LayoutInflater	mInflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    View view2  = mInflater.inflate(R.layout.layout_item_bottom_menu,null);
	    tohome=(LinearLayout)view2.findViewById(R.id.ll_tohome);
	    tosearch=(LinearLayout) view2.findViewById(R.id.ll_tosearch);
	    toshoppingcart=(LinearLayout) view2.findViewById(R.id.ll_toshoppingcart);
	    tomycenter=(LinearLayout) view2.findViewById(R.id.ll_tomycenter);
	    tomore=(LinearLayout) view2.findViewById(R.id.ll_tomore);
	    bottommenu=(RelativeLayout) findViewById(R.id.rl_mycenter_goodscommentslist_bottommenu);
	    bottommenu.addView(view2);
		
	    ViewGroup.LayoutParams lp =  view2.getLayoutParams();
	    lp.width = lp.FILL_PARENT;
	    view2.setLayoutParams(lp);
		goodsList = new ArrayList<OrderGoodsInfoEntity>();
		OrderGoodsInfoEntity goodsEntity ;
		for(int q=0;q<order.getList().size();q++){
			goodsEntity = new OrderGoodsInfoEntity();
			goodsEntity.setID(order.getList().get(q).getID());
			goodsEntity.setName(order.getList().get(q).getName());
			goodsEntity.setPrice(order.getList().get(q).getPrice());
			goodsList.add(goodsEntity);
			
		}
		
		
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.btn_list_mycenter_goodscomments_back:
			this.finish();
			break;
		case R.id.ll_tohome :
			Intent intenthome =new Intent(GoodsCommentsListActivity.this,HomeMainActivity.class);
			startActivity(intenthome);
			ExitApplication.getInstance().exit();
			break;
		case R.id.ll_tosearch :
			Intent intentsearch =new Intent(GoodsCommentsListActivity.this,SearchMainActivity.class);
			startActivity(intentsearch);
			ExitApplication.getInstance().exit();
			break;
		case R.id.ll_toshoppingcart :
			Intent intentshoppingcart =new Intent(GoodsCommentsListActivity.this,ShoppingcartMainListActivity.class);
			startActivity(intentshoppingcart);
			if(GlobalVarUtil.account.getUID()==null){
				Intent intentTolongin = new Intent(GoodsCommentsListActivity.this,LoginActivity.class);
				startActivity(intentTolongin);
			}
//			ExitApplication.getInstance().exit();//购物车可以回退
			break;
		case R.id.ll_tomycenter :
			Intent intentmycenter =new Intent(GoodsCommentsListActivity.this,MycenterMainActivity.class);
			startActivity(intentmycenter);
			if(GlobalVarUtil.account.getUID()==null){
				Intent intentTolongin = new Intent(GoodsCommentsListActivity.this,LoginActivity.class);
				startActivity(intentTolongin);
			}
			ExitApplication.getInstance().exit();
			break;
		case R.id.ll_tomore :
			Intent intentmore =new Intent(GoodsCommentsListActivity.this,MoreActivity.class);
			startActivity(intentmore);
			ExitApplication.getInstance().exit();
			break;
		
		}
	}


	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
	}

}
