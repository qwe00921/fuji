package com.fsti.fjdicClient.adapter;

import java.io.IOException;
import java.util.List;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fsti.fjdicClient.R;
import com.fsti.fjdicClient.bean.ConsigneeAddressEntity;
import com.fsti.fjdicClient.bean.GoodsEntity;
import com.fsti.fjdicClient.util.SyncImageLoadUtil;
import com.fsti.fjdicClient.util.SyncImageLoadUtil.OnImageLoadListener;

public class ShoppingmallGoodsListAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	private Context myContext;
	private List<GoodsEntity> myList;
	private SyncImageLoadUtil syncImageLoad;
	private boolean isShoppingmall = true;
	public SyncImageLoadUtil getSyncImageLoad() {
		return syncImageLoad;
	}

	public ShoppingmallGoodsListAdapter(Context context,
			List<GoodsEntity> list) {
		// TODO Auto-generated constructor stub
		this.myContext = context;
		this.myList = list;
		this.mInflater = LayoutInflater.from(context);
		this.syncImageLoad = new SyncImageLoadUtil();
	}

	public ShoppingmallGoodsListAdapter(Context baseContext,
			List<GoodsEntity> pageData, boolean isShoppingmall2) {
		// TODO Auto-generated constructor stub
		this.myContext = baseContext;
		this.myList = pageData;
		this.mInflater = LayoutInflater.from(baseContext);
		this.syncImageLoad = new SyncImageLoadUtil();
		this.isShoppingmall = isShoppingmall2;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return  myList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		if(convertView == null){
			convertView = mInflater.inflate(R.layout.layout_activity_item_shoppingmall_goodslist, null);
			holder = new ViewHolder();
			holder.tvDiscountPrice = (TextView)convertView.findViewById(R.id.tv_item_shoppingmall_goodslist_discountprice);
			holder.tvOriginalCost = (TextView)convertView.findViewById(R.id.tv_item_shoppingmall_goodslist_originalprice);
			holder.tvIntro = (TextView)convertView.findViewById(R.id.tv_item_shoppingmall_goodslist_describe);
			holder.tvSelledCount = (TextView)convertView.findViewById(R.id.tv_item_shoppingmall_goodslist_selledcount);
			holder.tvFreight = (TextView) convertView.findViewById(R.id.tv_item_shoppingmall_goodslist_freight);
			holder.ivGoods = (ImageView) convertView.findViewById(R.id.iv_item_shoppingmall_goodslist_goods);
			holder.tvpopu = (TextView) convertView.findViewById(R.id.tv_item_shoppingmall_goodslist_popu);
			holder.tvType = (TextView) convertView.findViewById(R.id.tv_item_shoppingmall_goodslist_type);
			
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
//		holder.tvpopu.setText(myList.get(position).get)
		holder.tvDiscountPrice.setText(myList.get(position).getDiscountedPrice()+"元");
		holder.tvOriginalCost.setText(myList.get(position).getOriginalCost()+"元");
		holder.tvOriginalCost.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
//		holder.tvIntro.setText(myList.get(position).getIntro());//简介中数据有异议
		holder.tvIntro.setText(myList.get(position).getName());
		holder.tvSelledCount.setText("最近售出"+myList.get(position).getSelledCount()+"件");
		holder.tvFreight.setText("运费:"+myList.get(position).getFreight()+"元");
//		holder.ivGoods.setImageResource(R.drawable.icon);

		if(isShoppingmall){
			holder.tvType.setVisibility(View.GONE);
			holder.tvpopu.setText("人气："+myList.get(position).getHits());
		}else{
			holder.tvpopu.setVisibility(View.GONE);
			holder.tvType.setVisibility(View.VISIBLE);
			if(myList.get(position).getType()==1){
				holder.tvType.setText("商品");
			}else{
				holder.tvType.setText("服务");
			}
		}
		
		String url = myList.get(position).getImageUrl();
		syncImageLoad.displayImage(url , holder.ivGoods,myContext );
		return convertView;
	}
	public void updateList(List<GoodsEntity> myList){
		this.myList = myList;
		this.notifyDataSetChanged();
	}
	static class ViewHolder {
		TextView tvOriginalCost;
		TextView tvDiscountPrice;
		TextView tvFreight;
		TextView tvSelledCount;
		TextView tvIntro;
		ImageView ivGoods;
		TextView tvType;
		TextView tvpopu;
	}

}
