package com.fsti.fjdicClient.adapter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;



import com.fsti.fjdicClient.R;
import com.fsti.fjdicClient.activity.login.RegisterActivity;
import com.fsti.fjdicClient.activity.shoppingcart.ShoppingcartGoodsDetailActivity;
import com.fsti.fjdicClient.bean.ShoppingcartInfoEntity;
import com.fsti.fjdicClient.bean.ShopsDetailInfoEntity;
import com.fsti.fjdicClient.dao.BusinessDao;
import com.fsti.fjdicClient.util.GlobalVarUtil;
import com.fsti.fjdicClient.util.HttpUtil;
import com.fsti.fjdicClient.util.SyncImageLoadUtil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;

public class ShoppingcartMainAdapter extends BaseExpandableListAdapter {

//	private List<List<GoodsDetailInfoEntity>> goodsAll;//二级数据总和
	private List<List<ShoppingcartInfoEntity>> shoppingcartInfoAll;//二级数据总和
	private List<ShopsDetailInfoEntity> shopList;
	private Context context;
	private LayoutInflater mInflater;
	private TextView tvListGoodscount;
	private TextView tvListTotalPrice;
	private SyncImageLoadUtil syncImageLoad;
	
	private boolean isFree = false;
	private boolean netIng = false;
	public List<ShopsDetailInfoEntity> getShopList() {
		return shopList;
	}
	public void setShopList(List<ShopsDetailInfoEntity> shopList) {
		this.shopList = shopList;
	}

	public List<List<ShoppingcartInfoEntity>> getShoppingcartInfoAll() {
		return shoppingcartInfoAll;
	}
	public void setShoppingcartInfoAll(
			List<List<ShoppingcartInfoEntity>> shoppingcartInfoAll) {
		this.shoppingcartInfoAll = shoppingcartInfoAll;
	}
	public ShoppingcartMainAdapter(
			Context context,
			List<ShopsDetailInfoEntity> shopList,
			List<List<ShoppingcartInfoEntity>> shoppingcartInfoAll) {
		// TODO Auto-generated constructor stub
		this.shoppingcartInfoAll = shoppingcartInfoAll;
		this.shopList = shopList;
		this.context = context;
		this.mInflater = LayoutInflater.from(context);
		this.syncImageLoad = new SyncImageLoadUtil();
	}

	//多了总价和总数两个控件
	public ShoppingcartMainAdapter(Context context,
			List<ShopsDetailInfoEntity> shopList,
			List<List<ShoppingcartInfoEntity>> shoppingcartInfoAll,
			TextView tvListGoodscount, TextView tvListTotalPrice) {
		// TODO Auto-generated constructor stub
		this.shoppingcartInfoAll = shoppingcartInfoAll;
		this.shopList = shopList;
		this.context = context;
		this.mInflater = LayoutInflater.from(context);
		this.tvListGoodscount = tvListGoodscount;
		this.tvListTotalPrice = tvListTotalPrice;
		this.syncImageLoad = new SyncImageLoadUtil();
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
			convertView = mInflater.inflate(R.layout.layout_activity_item_shoppingcart_main_child, null);
			
			holder.tvFreightSelect = (TextView) convertView.findViewById(R.id.tv_item_shoppingcart_freight_money);
			holder.tvShowWhichFreight = (TextView) convertView.findViewById(R.id.tv_item_shoppingcart_freight_type);
			
			holder.flChildLeft = (FrameLayout) convertView.findViewById(R.id.fl_item_shoppingcart_child_left);
			holder.goodsIntro = (TextView) convertView.findViewById(R.id.tv_item_shoppingcart_main_child_describe);
			holder.goodsprice = (TextView) convertView.findViewById(R.id.tv_item_shoppingcart_main_child_price);
//			holder.goodscounts = (TextView) convertView.findViewById(R.id.tv_item_shoppingcart_main_child_counts);
			holder.countsandtotalprice = (TextView) convertView.findViewById(R.id.tv_item_shoppingcart_main_child_countsandtotalprice);
			holder.ivselect = (ImageView) convertView.findViewById(R.id.iv_item_shoppingcart_main_child_select);
			
			holder.ivGoods = (ImageView) convertView.findViewById(R.id.iv_item_shoppingcart_main_child_goods);
			
			holder.rlShoppingcartEnd = (RelativeLayout) convertView.findViewById(R.id.rl_item_shoppingcart_main_child_end);
			holder.rlShoppingcartMainRight = (RelativeLayout) convertView.findViewById(R.id.rl_item_shoppingcart_main_child_right);
			
			holder.btnAdd = (Button) convertView.findViewById(R.id.btn_item_shoppingcart_add);
			holder.btnRedu = (Button) convertView.findViewById(R.id.btn_item_shoppingcart_reduction);
			holder.etCounts = (EditText) convertView.findViewById(R.id.et_item_shoppingcart_counts);
			
			holder.rlFrightShow = (RelativeLayout) convertView.findViewById(R.id.rl_shoppingcart_freight);
			holder.llFrightChoice = (LinearLayout) convertView.findViewById(R.id.ll_item_shoppingcart_freight_choice);
			holder.rlFreightChoiceRight = (RelativeLayout) convertView.findViewById(R.id.rl_item_shoppingcart_freight_choice_right);
			holder.llFreight = (LinearLayout) convertView.findViewById(R.id.ll_item_shoppingcart_freightlist);
			
			holder.tvMinFee = (TextView) convertView.findViewById(R.id.tv_item_shoppingcart_main_child_minfee);
			
			convertView.setTag(holder);
		} 
		else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		if(childPosition == shoppingcartInfoAll.get(groupPosition).size()-1){
			if(holder.llFreight!=null){
				holder.llFreight.removeViews(0, holder.llFreight.getChildCount());
			}
			for(int q=0;q<freightTypeArray.length;q++){
				holder.view = new View(holder.llFreight.getContext());
				holder.view = LayoutInflater.from(holder.llFreight.getContext())
				.inflate(R.layout.layout_list_item_shoppingcart_freightchoice, null);
				holder.rlFreightInsert = (RelativeLayout) holder.view.findViewById(R.id.rl_shoppingcart_freight_insert);
				holder.ivchoice = (ImageView) holder.view.findViewById(R.id.iv_shoppingcart_freightchose);
				holder.tvName=(TextView) holder.view.findViewById(R.id.tv_shoppingcart_freightname);
				holder.tvFreight=(TextView) holder.view.findViewById(R.id.tv_shoppingcart_freight);
				
				holder.tvName.setText(freightTypeArray[q]);
				holder.tvFreight.setText(freightArray[q]+"元");
				if(shoppingcartInfoAll.get(groupPosition).get(childPosition).getFreightTypeSelect()==q){
					holder.ivchoice.setImageResource(R.drawable.input_lable01_selected);
				}
				final int m=q;
				holder.rlFreightInsert.setOnClickListener(new ChildCheck(groupPosition, childPosition ,m));
				holder.llFreight.addView(holder.view);
			}
		}
		
		
		holder.tvShowWhichFreight.setText("运送方式："+ 
				freightTypeArray[shoppingcartInfoAll.get(groupPosition).get(childPosition).getFreightTypeSelect()] );
		holder.tvFreightSelect.setText(freightArray[shoppingcartInfoAll.get(groupPosition).get(childPosition).getFreightTypeSelect()]+"元");
		
		holder.goodsIntro.setText(((ShoppingcartInfoEntity)getChild(groupPosition, childPosition)).getName());
		holder.goodsprice.setText(((ShoppingcartInfoEntity)getChild(groupPosition, childPosition)).getPrice()+"元");
//		holder.goodscounts.setText("x"+(shoppingcartInfoAll.get(groupPosition).get(childPosition).getCounts()));
		
		holder.etCounts.setText(""+(shoppingcartInfoAll.get(groupPosition).get(childPosition).getCounts()));
		//该分组的商品个数及总价
//		int counts = shoppingcartInfoAll.get(groupPosition).size();
		int counts = 0;
		float price = 0.00f;
		for (int i = 0; i < shoppingcartInfoAll.get(groupPosition).size(); i++) {
			ShoppingcartInfoEntity entity = shoppingcartInfoAll.get(groupPosition).get(i);
			if(entity.isSelect()){
				counts = counts + entity.getCounts();
				String temp = entity.getPrice();
				price += Float.parseFloat(temp)*entity.getCounts(); 
			}
		}
		if(Float.parseFloat(GlobalVarUtil.minPurchasemoney) == -1.0){
			if(toFloatTwo(price)>= 100.00f){
				isFree = true;
				holder.tvMinFee.setText("商家包邮");
			}else{
				isFree = false;
				holder.tvMinFee.setText("满100元包邮" );
			}
		}else{
			if(toFloatTwo(price)>=Float.parseFloat(GlobalVarUtil.minPurchasemoney) || toFloatTwo(price)>= 100.00f){
				isFree = true;
				holder.tvMinFee.setText("商家包邮");
			}else{
				isFree = false;
				if(Float.parseFloat(GlobalVarUtil.minPurchasemoney)<=100.00f){
					holder.tvMinFee.setText("满"+Float.parseFloat(GlobalVarUtil.minPurchasemoney)+"元包邮" );
				}else{
					holder.tvMinFee.setText("满100元包邮" );
				}
			}
		}
		holder.countsandtotalprice.setText("共"+ counts +"件,"+toFloatTwo(price)+"元");
		String url =shoppingcartInfoAll.get(groupPosition).get(childPosition).getImageUrl();
		syncImageLoad.displayImage(url , holder.ivGoods,context );
		
		if(((ShoppingcartInfoEntity)getChild(groupPosition, childPosition)).isSelect()){
			holder.ivselect.setImageResource(R.drawable.input_lable_selected);	
		}else{
			holder.ivselect.setImageResource(R.drawable.input_lable_noselected);	
		}
		
		
		holder.rlFrightShow.setVisibility(View.GONE);
		holder.llFrightChoice.setVisibility(View.GONE);
		if(shoppingcartInfoAll.get(groupPosition).size()-1 ==childPosition ){
			holder.rlFrightShow.setVisibility(View.VISIBLE);
		}
		
		
		
		
		//监听
//		holder.rlShoppingcartMainRight.setOnClickListener(new ChildCheck(groupPosition,childPosition));
		holder.flChildLeft.setOnClickListener(new ChildCheck(groupPosition,childPosition,holder.ivselect));
		
		holder.rlFrightShow.setOnClickListener(new ChildCheck(groupPosition, childPosition , holder.llFrightChoice ,holder.rlFrightShow));
		holder.rlFreightChoiceRight.setOnClickListener(new ChildCheck(groupPosition, childPosition, holder.llFrightChoice ,holder.rlFrightShow));
		
		holder.btnAdd.setOnClickListener(new ChildCheck(groupPosition, childPosition ,holder.etCounts,context));
		holder.btnRedu.setOnClickListener(new ChildCheck(groupPosition, childPosition ,holder.etCounts,context));
		holder.etCounts.setOnClickListener(new ChildCheck(groupPosition, childPosition ,holder.etCounts,context));
		
		//以下这行如果不加 会出现问题 getChildView会执行多次 某一次 传入的convertView 为null
		holder.rlShoppingcartEnd.setVisibility(View.VISIBLE);
		if(childPosition!=getChildrenCount(groupPosition)-1){
			holder.rlShoppingcartEnd.setVisibility(View.GONE);
		}
		return convertView;
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
			viewShop.ivselect.setOnClickListener(new GroupCheck(groupPosition));
			convertView.setTag(viewShop);
		}else{
			viewShop = (ViewShop)convertView.getTag();
		}
		viewShop.tvshopsName.setText("卖家："+shopList.get(groupPosition).getName());
		if(shopList.get(groupPosition).isSelect()){
			viewShop.ivselect.setImageResource(R.drawable.input_lable_selected);
		}else{
			viewShop.ivselect.setImageResource(R.drawable.input_lable_noselected);
		}
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
	
	static class ViewHolder {
		TextView tvMinFee;
		TextView goodsIntro;
		TextView goodsprice;
//		TextView goodscounts;
		ImageView ivselect;
		ImageView ivGoods;
		TextView countsandtotalprice;
		RelativeLayout rlShoppingcartEnd;
		RelativeLayout rlShoppingcartMainRight;
		TextView tvListGoodscount;
		TextView tvListTotalPrice;
		FrameLayout flChildLeft;
		Button btnAdd;
		Button btnRedu;
		EditText etCounts;
		RelativeLayout rlFrightShow;
		LinearLayout llFrightChoice;
		RelativeLayout rlFreightChoiceRight;
		RelativeLayout rlFreightInsert;
		LinearLayout llFreight;
		View view;
		ImageView ivchoice;
		TextView tvName;
		TextView tvFreight;
		TextView tvFreightSelect;
		TextView tvShowWhichFreight;
	}
	static class ViewShop {
		TextView tvshopsName;
		ImageView ivselect;
		ImageView ivRight;
	}
	class GroupCheck implements	OnClickListener{
		private int groupPosition;
		public GroupCheck(int groupPosition) {
			// TODO Auto-generated constructor stub
			this.groupPosition = groupPosition;	
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			int childrenCount = shoppingcartInfoAll.get(groupPosition).size(); 
			if(shopList.get(groupPosition).isSelect()){
				shopList.get(groupPosition).setSelect(false);
				for (int i = 0; i < childrenCount; i++){
					shoppingcartInfoAll.get(groupPosition).get(i).setSelect(false); 
					shoppingcartBoolean(shoppingcartInfoAll.get(groupPosition).get(i));
				}
			}else{
				shopList.get(groupPosition).setSelect(true);
				for (int i = 0; i < childrenCount; i++){
					shoppingcartInfoAll.get(groupPosition).get(i).setSelect(true);
					shoppingcartBoolean(shoppingcartInfoAll.get(groupPosition).get(i));
				}
			}
			totalPriceAndCounts(shoppingcartInfoAll);
			notifyDataSetChanged();//此处通知更新 ，在虚拟机中groupPosition会错位，而实体机正常。
			
		}
		
	}
	class ChildCheck implements OnClickListener,TextWatcher{
		private int groupPosition;
		private int childPosition;
		private EditText etCounts;
		private Context context;
		private ImageView ivSelect;
		private boolean isdirectorEdit = true;
		private RelativeLayout rlShow;
		private LinearLayout llChoice;
		private int which_choice;
		
		public ChildCheck(int groupPosition, int childPosition) {
			// TODO Auto-generated constructor stub
			this.groupPosition = groupPosition;	
			this.childPosition = childPosition;	
		}

		public ChildCheck(int groupPosition, int childPosition,
				EditText etCounts,Context context) {
			// TODO Auto-generated constructor stub
			this.groupPosition = groupPosition;	
			this.childPosition = childPosition;	
			this.etCounts = etCounts;
			this.context = context;
		}

		public ChildCheck(int groupPosition, int childPosition,
				LinearLayout rlFrightChoice, RelativeLayout rlFrightShow) {
			// TODO Auto-generated constructor stub
			this.groupPosition = groupPosition;	
			this.childPosition = childPosition;
			this.llChoice = rlFrightChoice;
			this.rlShow = rlFrightShow;
			
		}

		public ChildCheck(int groupPosition, int childPosition,
				ImageView ivselect) {
			// TODO Auto-generated constructor stub
			this.groupPosition = groupPosition;	
			this.childPosition = childPosition;	
			this.ivSelect = ivselect;
		}

		public ChildCheck(int groupPosition, int childPosition, int q) {
			// TODO Auto-generated constructor stub
			this.groupPosition = groupPosition;	
			this.childPosition = childPosition;	
			this.which_choice = q;
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			switch(v.getId()){
			case R.id.rl_shoppingcart_freight_insert:
				
				for (int i = 0; i < shoppingcartInfoAll.get(groupPosition).size(); i++) {
					shoppingcartInfoAll.get(groupPosition).get(i).setFreightTypeSelect(which_choice);
					BusinessDao.updateShoppingcartTableData(BusinessDao.Table_Name_ShoppingcartInfo, 
							shoppingcartInfoAll.get(groupPosition).get(i), 
							GlobalVarUtil.account.getUID());
				}
				totalPriceAndCounts(shoppingcartInfoAll);
				notifyDataSetChanged();
				break;
			case R.id.fl_item_shoppingcart_child_left:
				childSelectChange();
				break;
			case R.id.rl_item_shoppingcart_main_child_right:
				//跳转到购物车的商品详情
				Intent intent = new Intent(context ,ShoppingcartGoodsDetailActivity.class);
				Bundle bundle = new Bundle();
//				bundle.putSerializable("shoppingcartEntity", shoppingcartInfoAll.get(groupPosition).get(childPosition));
				bundle.putSerializable("shoppingcartInfoAll", (Serializable) shoppingcartInfoAll);
				intent.putExtras(bundle);
				intent.putExtra("groupPosition", groupPosition);
				intent.putExtra("childPosition", childPosition);
				v.getContext().startActivity(intent);
				break;
			case R.id.btn_item_shoppingcart_add:
				if(netIng){
					Toast.makeText(context, "访问网络中，稍后操作", Toast.LENGTH_SHORT).show();
					break;
				}else{
					int countAdd = shoppingcartInfoAll.get(groupPosition).get(childPosition).getCounts()+1;
					overStepCounts(countAdd);
				}
				break;
			case R.id.btn_item_shoppingcart_reduction:
				if(netIng){
					Toast.makeText(context, "访问网络中，稍后操作", Toast.LENGTH_SHORT).show();
					break;
				}else{
					if(shoppingcartInfoAll.get(groupPosition).get(childPosition).getCounts()>=2){
						int countRedu = shoppingcartInfoAll.get(groupPosition).get(childPosition).getCounts()-1;
						overStepCounts(countRedu);
					}else{
						etCounts.setText("1");
					}
				}
				break;
			case R.id.rl_item_shoppingcart_freight_choice_right:
				llChoice.setVisibility(View.GONE);
				rlShow.setVisibility(View.VISIBLE);
				break;
			case R.id.rl_shoppingcart_freight:
				rlShow.setVisibility(View.GONE);
				llChoice.setVisibility(View.VISIBLE);
				break;
			}
		}
		//等待接口-----------------------------------------------------------------
		private void overStepCounts(int counts) {
			// TODO Auto-generated method stub
			netIng = true;
			String reqUrl = ShoppingcartMainAdapter.this.context.getString(R.string.CheckStorageCount_php);//库存接口
			Map<String, Object> parameters = new LinkedHashMap<String, Object>();
			parameters.put("ID",shoppingcartInfoAll.get(groupPosition).get(childPosition).getID());	
			try {
				String jsonStr = HttpUtil.postData(reqUrl, parameters,
						GlobalVarUtil.ENCODING);
				if (jsonStr.equals("-1")) {
					Toast.makeText(context, "操作失败，请稍后", Toast.LENGTH_SHORT).show();
				} else {
					int storageCount = Integer.parseInt(RegisterActivity.replaceBlank(jsonStr));
					if(counts<=storageCount){
						shoppingcartInfoAll.get(groupPosition).get(childPosition).setCounts(counts);
						BusinessDao.updateShoppingcartTableData(BusinessDao.Table_Name_ShoppingcartInfo, 
								shoppingcartInfoAll.get(groupPosition).get(childPosition), 
								GlobalVarUtil.account.getUID());
						etCounts.setText(""+counts);
					}else{
						int over = counts-1-storageCount;
						Toast.makeText(context, "商品库存量:"+storageCount+"件"+",您超出了"+over+"件", Toast.LENGTH_SHORT).show();
					}
				}
				netIng = false;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				Log.e("CheckStorageCount", e.toString());
				Toast.makeText(context, "操作失败，请重新操作", 1).show();
				e.printStackTrace();
				netIng = false;
			}
		
			totalPriceAndCounts(shoppingcartInfoAll);
			notifyDataSetChanged();
			
		}
		//等待接口-----------------------------------------------------------------
		private void childSelectChange() {
			// TODO Auto-generated method stub
			int j = 0;
			int childrenCount = shoppingcartInfoAll.get(groupPosition).size();  
			if(shoppingcartInfoAll.get(groupPosition).get(childPosition).isSelect()){
				shoppingcartInfoAll.get(groupPosition).get(childPosition).setSelect(false);
				ivSelect.setImageResource(R.drawable.input_lable_noselected);
			}else{
				shoppingcartInfoAll.get(groupPosition).get(childPosition).setSelect(true);
				ivSelect.setImageResource(R.drawable.input_lable_selected);
			}
			shoppingcartBoolean(shoppingcartInfoAll.get(groupPosition).get(childPosition));
			for (int i = 0; i < childrenCount; i++) {
				++j;
				if (!shoppingcartInfoAll.get(groupPosition).get(i).isSelect()){
					shopList.get(groupPosition).setSelect(false);
					--j;
				}
				if(j == childrenCount){
					shopList.get(groupPosition).setSelect(true);
					
				}
			}
			System.out.println("onclick------"+shoppingcartInfoAll.get(groupPosition).get(childPosition).getName());
			totalPriceAndCounts(shoppingcartInfoAll);
			notifyDataSetChanged();
		}

		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
			if(isdirectorEdit){
				isdirectorEdit = true;
			}else{
				if("".equals(etCounts.getText().toString())){
					etCounts.setText("1");
				}else{
					int counts = Integer.parseInt(""+etCounts.getText());
					overStepCounts(counts);
				}
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
	//对listview进行更新
	public void updateList(List<List<ShoppingcartInfoEntity>> shoppingcartInfoAll, List<ShopsDetailInfoEntity> shopList) {
		this.shoppingcartInfoAll = shoppingcartInfoAll; 
		this.shopList = shopList; 
		totalPriceAndCounts(shoppingcartInfoAll);
		this.notifyDataSetChanged();
	}
	//对listview外的总价和总数进行操作
	public void totalPriceAndCounts(List<List<ShoppingcartInfoEntity>> shoppingcartInfoAll){
		float listTotalPrice = 0.00f;
		int listGoodsCount = 0;
		float shopPrice = 0.00f;
		for (Iterator iterator = shoppingcartInfoAll.iterator(); iterator
				.hasNext();) {
			List<ShoppingcartInfoEntity> list = (List<ShoppingcartInfoEntity>) iterator
					.next();
			int firstFreight = 1;
			for (ShoppingcartInfoEntity shoppingcartInfoEntity : list) {
				if(shoppingcartInfoEntity.isSelect()){
					listGoodsCount += shoppingcartInfoEntity.getCounts();
					listTotalPrice += Float.parseFloat(shoppingcartInfoEntity.getPrice())*shoppingcartInfoEntity.getCounts();
					shopPrice += Float.parseFloat(shoppingcartInfoEntity.getPrice())*shoppingcartInfoEntity.getCounts();
				}
			}
			if(Float.parseFloat(GlobalVarUtil.minPurchasemoney) == -1.0){
				if(ShoppingcartMainAdapter.toFloatTwo(shopPrice)>= 100){
				}else{
					if(firstFreight == 1){
						firstFreight++;
						int freightTypeSelect = list.get(0).getFreightTypeSelect();
						String freight = list.get(0).getFreight();
						String[] freightArray = freight.split("※");
						listTotalPrice += Float.parseFloat(freightArray[freightTypeSelect]);
					}
				}
			}else{
				if(ShoppingcartMainAdapter.toFloatTwo(shopPrice)>=Float.parseFloat(GlobalVarUtil.minPurchasemoney) || ShoppingcartMainAdapter.toFloatTwo(shopPrice)>= 100){
				}else{
					if(firstFreight == 1){
						firstFreight++;
						int freightTypeSelect = list.get(0).getFreightTypeSelect();
//						String freightType = shoppingcartInfoEntity.getFreightType();
//						String[] freightTypeArray = freightType.split("※");
						String freight = list.get(0).getFreight();
						String[] freightArray = freight.split("※");
						listTotalPrice += Float.parseFloat(freightArray[freightTypeSelect]);
					}
				}
			}
			shopPrice = 0.00f;
		}
		tvListGoodscount.setText(listGoodsCount+"件");
		tvListTotalPrice.setText(toFloatTwo(listTotalPrice)+"元");
	}
	//将选中与否的boolean型对应到int，保存到数据库中
	public static void shoppingcartBoolean(ShoppingcartInfoEntity entity ){
		if(entity.isSelect()){
			entity.setToSelectBoolean(0);
		}else{
			entity.setToSelectBoolean(1);
		}
		BusinessDao.updateShoppingcartTableData(BusinessDao.Table_Name_ShoppingcartInfo,entity, GlobalVarUtil.account.getUID());
	}
	//保留小数点后两位
	public static float toFloatTwo(float f){
		BigDecimal b = new BigDecimal(f);
		float f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();

		return f1;
		
	}

}
