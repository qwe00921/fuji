package com.fsti.fjdicClient.adapter;

import java.io.ObjectInputStream.GetField;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;

import com.fsti.fjdicClient.R;
import com.fsti.fjdicClient.activity.groupBuy.GroupBuyFirmOrderActivity;
import com.fsti.fjdicClient.activity.login.RegisterActivity;
//import com.fsti.fjdicClient.activity.shoppingMall.BuyNowActivity;
//import com.fsti.fjdicClient.activity.shoppingMall.FirmOrderActivity;
import com.fsti.fjdicClient.activity.shoppingcart.ShoppingcartOrderListActivity;
import com.fsti.fjdicClient.adapter.ShoppingcartMainAdapter.ChildCheck;
import com.fsti.fjdicClient.adapter.ShoppingcartMainAdapter.GroupCheck;
import com.fsti.fjdicClient.adapter.ShoppingcartMainAdapter.ViewHolder;
import com.fsti.fjdicClient.adapter.ShoppingcartMainAdapter.ViewShop;
import com.fsti.fjdicClient.bean.ConsigneeAddressEntity;
import com.fsti.fjdicClient.bean.ExitApplication;
import com.fsti.fjdicClient.bean.ShoppingcartInfoEntity;
import com.fsti.fjdicClient.bean.ShopsDetailInfoEntity;
import com.fsti.fjdicClient.bean.orderInfoEntity;
import com.fsti.fjdicClient.bean.persellerorderEntity;
import com.fsti.fjdicClient.util.GlobalVarUtil;
import com.fsti.fjdicClient.util.HttpUtil;
import com.fsti.fjdicClient.util.SyncImageLoadUtil;
import com.fsti.fjdicClient.util.asyncUtil.AsyncTaskUtils;
import com.fsti.fjdicClient.util.asyncUtil.CallEarliest;
import com.fsti.fjdicClient.util.asyncUtil.Callable;
import com.fsti.fjdicClient.util.asyncUtil.Callback;
import com.fsti.fjdicClient.util.uppay.Uppay;
import com.fsti.fjdicClient.util.uppay.Uppay_state;
import com.google.gson.Gson;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.test.IsolatedContext;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ShoppingcartOrderAdapter extends BaseExpandableListAdapter{
	private List<List<ShoppingcartInfoEntity>> shoppingcartInfoAll;//二级数据总和
	private List<ShopsDetailInfoEntity> shopList;
	private Context context;
	private SyncImageLoadUtil syncImageLoad;
	private LayoutInflater mInflater;
	private List<String> noteList = new ArrayList<String>();
	private ConsigneeAddressEntity address;
	private ShoppingcartOrderListActivity ac;
	
	public static boolean isPay = false;
	
	public ConsigneeAddressEntity getAddress() {
		return address;
	}

	public void setAddress(ConsigneeAddressEntity address) {
		this.address = address;
	}

	public ShoppingcartOrderAdapter(Context baseContext,
			List<ShopsDetailInfoEntity> shopList,
			List<List<ShoppingcartInfoEntity>> shoppingcartInfoAll, ConsigneeAddressEntity addressEntity) {
		// TODO Auto-generated constructor stub
		this.shoppingcartInfoAll = shoppingcartInfoAll;
		this.shopList = shopList;
		this.context = baseContext;
		this.address = addressEntity;
		this.mInflater = LayoutInflater.from(context);
		this.syncImageLoad = new SyncImageLoadUtil();
	}

	public ShoppingcartOrderAdapter(Context baseContext,
			List<ShopsDetailInfoEntity> shopList,
			List<List<ShoppingcartInfoEntity>> shoppingcartInfoAll, ConsigneeAddressEntity addressEntity,
			ShoppingcartOrderListActivity shoppingcartOrderListActivity , List<String> noteList) {
		// TODO Auto-generated constructor stub
		this.shoppingcartInfoAll = shoppingcartInfoAll;
		this.shopList = shopList;
		this.context = baseContext;
		this.address = addressEntity;
		this.mInflater = LayoutInflater.from(context);
		this.syncImageLoad = new SyncImageLoadUtil();
		this.ac = shoppingcartOrderListActivity;
		this.noteList = noteList;
	}
	

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return shoppingcartInfoAll.get(groupPosition).get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		String freightType = ((ShoppingcartInfoEntity)getChild(groupPosition, childPosition)).getFreightType();
		String freight = ((ShoppingcartInfoEntity)getChild(groupPosition, childPosition)).getFreight();
		String[] freightTypeArray = freightType.split("※");
		String[] freightArray = freight.split("※");
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.layout_activity_item_shoppingcart_order_child, null);

			holder.goodsprice = (TextView) convertView.findViewById(R.id.tv_item_shoppingcart_order_child_price);
			holder.tvcount = (TextView) convertView.findViewById(R.id.tv_item_shoppingcart_order_child_counts);
			holder.tvName = (TextView) convertView.findViewById(R.id.tv_item_shoppingcart_order_child_describe);
			holder.tvFreightType = (TextView) convertView.findViewById(R.id.tv_item_shoppingcart_order_freight_type);
			holder.tvFreightMoney = (TextView) convertView.findViewById(R.id.tv_item_shoppingcart_order_freight_money);
			holder.ivGoods = (ImageView) convertView.findViewById(R.id.iv_item_shoppingcart_order_child_goods);
			holder.llNext =  (LinearLayout) convertView.findViewById(R.id.ll_item_shoppingcart_order_next);
			holder.etNote =  (EditText) convertView.findViewById(R.id.et_item_shoppingcart_order_notedetail);
			convertView.setTag(holder);
		} 
		else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.tvFreightType.setText(""+freightTypeArray[shoppingcartInfoAll.get(groupPosition).get(childPosition).getFreightTypeSelect()]);
		
		float price = 0.00f;
		for (int i = 0; i < shoppingcartInfoAll.get(groupPosition).size(); i++) {
			ShoppingcartInfoEntity entity = shoppingcartInfoAll.get(groupPosition).get(i);
			String temp = entity.getPrice();
			price += Float.parseFloat(temp)*entity.getCounts(); 
		}
		if(Float.parseFloat(GlobalVarUtil.minPurchasemoney) == -1.0){
			if(ShoppingcartMainAdapter.toFloatTwo(price)>= 100.00f){
				holder.tvFreightMoney.setText("商家包邮");
			}else{
				holder.tvFreightMoney.setText(""+freightArray[shoppingcartInfoAll.get(groupPosition).get(childPosition).getFreightTypeSelect()]+"元");
			}
		}else{
			if(ShoppingcartMainAdapter.toFloatTwo(price)>=Float.parseFloat(GlobalVarUtil.minPurchasemoney) || ShoppingcartMainAdapter.toFloatTwo(price)>= 100.00f){
				holder.tvFreightMoney.setText("商家包邮");
			}else{
				holder.tvFreightMoney.setText(""+freightArray[shoppingcartInfoAll.get(groupPosition).get(childPosition).getFreightTypeSelect()]);
			}
		}
		
		holder.goodsprice.setText(((ShoppingcartInfoEntity)getChild(groupPosition, childPosition)).getPrice()+"元");
		holder.tvName.setText(((ShoppingcartInfoEntity)getChild(groupPosition, childPosition)).getName());
		holder.tvcount.setText("x"+((ShoppingcartInfoEntity)getChild(groupPosition, childPosition)).getCounts());
		holder.llNext.setVisibility(View.GONE);
		holder.etNote.setText(noteList.get(groupPosition));
		System.out.println("第"+groupPosition+"个商家：现有留言"+noteList.get(groupPosition));
		holder.etNote.setVisibility(View.GONE);
		if(shoppingcartInfoAll.get(groupPosition).size()-1==childPosition  ){
			if(shopList.size() -1 == groupPosition){
				holder.llNext.setVisibility(View.VISIBLE);
				System.out.println(((ShoppingcartInfoEntity)getChild(groupPosition, childPosition)).getName()+"下一步： group:"+groupPosition+"child:"+childPosition);
			}
			if(!noteList.get(groupPosition).equals(holder.etNote.getText().toString())){
				holder.etNote.setText(noteList.get(groupPosition));
			}
			holder.etNote.setVisibility(View.VISIBLE);
			System.out.println(((ShoppingcartInfoEntity)getChild(groupPosition, childPosition)).getName()+"留言   group:"+groupPosition+"child:"+childPosition);
			System.out.println("购物车订单 "+ groupPosition + "  "+childPosition);
		}else{
			holder.llNext.setVisibility(View.GONE);
			holder.etNote.setVisibility(View.GONE);
			System.out.println(((ShoppingcartInfoEntity)getChild(groupPosition, childPosition)).getName()+"隐藏的   group:"+groupPosition+"child:"+childPosition);
		}
		
		syncImageLoad.displayImage(shoppingcartInfoAll.get(groupPosition).get(childPosition).getImageUrl() , holder.ivGoods,context );
		
		holder.etNote.addTextChangedListener(new ChildTextChange(groupPosition, childPosition ,holder.etNote,context));
		holder.llNext.setOnClickListener(new ChildClick(groupPosition, childPosition ,context));
		holder.etNote.addTextChangedListener(new ChildTextChange(groupPosition, childPosition ,holder.etNote,context));
		
		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		return shoppingcartInfoAll.get(groupPosition).size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return shopList.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return shopList.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewShop viewShop;
		if(convertView ==null){
			viewShop = new ViewShop();
			convertView = mInflater.inflate(R.layout.layout_activity_item_shoppingcart_main, null);
			
			viewShop.ivRight = (ImageView) convertView.findViewById(R.id.iv_item_shoppingcart_shop_right);
			viewShop.tvshopsName = (TextView) convertView.findViewById(R.id.tv_item_shoppingcart_main_goodsname);
			viewShop.ivselect = (ImageView) convertView.findViewById(R.id.iv_item_shoppingcart_main_select);
			viewShop.ivselect.setVisibility(View.GONE);
			convertView.setTag(viewShop);
		}else{
			viewShop = (ViewShop)convertView.getTag();
		}
		viewShop.tvshopsName.setText("卖家："+shopList.get(groupPosition).getName());
		if(shopList.get(groupPosition).isExpandable()){
			viewShop.ivRight.setImageResource(R.drawable.ic_up_right);
		}else{
			viewShop.ivRight.setImageResource(R.drawable.ic_down_right);
		}
		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return true;
	}
	static class ViewHolder{
		TextView goodsprice;
		TextView tvName;
		TextView tvFreightType;
		TextView tvFreightMoney;
		TextView tvcount;
		ImageView ivGoods;
		EditText etNote;
		LinearLayout llNext;
	}
	static class ViewShop{
		TextView tvshopsName;
		ImageView ivselect;
		ImageView ivRight;
	}
	
	class ChildClick implements OnClickListener{
		private int groupPosition;
		private int childPosition;
		private Context context;

		public ChildClick(int groupPosition, int childPosition, Context context) {
			// TODO Auto-generated constructor stub
			this.groupPosition = groupPosition;	
			this.childPosition = childPosition;	
			this.context = context;
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.ll_item_shoppingcart_order_next:
				if(address.getAddress().equals("")){
					Toast.makeText(context, "地址不能为空，请选择地址", 1).show();
				}else{
					if(!isPay){
						isPay = true;
						ShoppingcartOrderListActivity.shopList = shopList;
						ShoppingcartOrderListActivity.shoppingcartInfoAll = shoppingcartInfoAll;
						ac.upload_order();
					}else{
						Toast.makeText(context, "已提交，请稍候。", 1).show();
					}
				}
				break;
			}
		}
		
	}
//	public void setActivity(Activity ac){
//		this.ac = ac;
//	}
	class ChildTextChange implements TextWatcher{
		private int groupPosition;
		private int childPosition;
		private EditText etNote;
		private Context context;

		public ChildTextChange(int groupPosition, int childPosition,
				EditText etNote, Context context) {
			// TODO Auto-generated constructor stub
			this.groupPosition = groupPosition;	
			this.childPosition = childPosition;	
			this.etNote = etNote;
			this.context = context;
		}

		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
			String note = s.toString();
			if(!noteList.get(groupPosition).equals(note)){
				noteList.set(groupPosition, note);
				shopList.get(groupPosition).setNote(note);
				System.out.println("第"+groupPosition+"个商家：留言变为"+noteList.get(groupPosition));
			}
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// TODO Auto-generated method stub
		}
		
	}
}
