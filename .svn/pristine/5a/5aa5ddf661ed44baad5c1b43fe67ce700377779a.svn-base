package com.fsti.fjdicClient.adapter;

import java.util.List;

import com.fsti.fjdicClient.R;
import com.fsti.fjdicClient.adapter.MycenterSellerDeliverAdapter.ViewHolder;
import com.fsti.fjdicClient.bean.ConsigneeAddressEntity;
import com.fsti.fjdicClient.bean.GoodsEntity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;

public class MycenterAddressAdapter extends BaseAdapter{

	private LayoutInflater mInflater;
	private Context myContext;
	private List<ConsigneeAddressEntity> myList;
	
	private int mainaddress=0;
	public MycenterAddressAdapter(Context context,List<ConsigneeAddressEntity> list,int mainaddress){
		
		this.mainaddress=mainaddress;
		this.myContext = context;
		this.myList = list;
		this.mInflater = LayoutInflater.from(context);
	
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return myList.size();
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
		
			 
			convertView = mInflater.inflate(R.layout.layout_activity_item_mycenter_address, null);
			holder = new ViewHolder();
			holder.tvbuyername = (TextView) convertView.findViewById(R.id.tv_item_mycenter_address_buyername);
			holder.tvbuyerphone = (TextView) convertView.findViewById(R.id.tv_item_mycenter_address_buyerphone);
			holder.tvaddressdetail = (TextView) convertView.findViewById(R.id.tv_item_mycenter_address_detail);
			holder.ivismainaddress = (ImageView)convertView.findViewById(R.id.iv_item_mycenter_address_tick);
			convertView.setTag(holder);
			holder.tvbuyername.setText(myList.get(position).getName());
			holder.tvbuyerphone.setText(myList.get(position).getTelephone());
			holder.tvaddressdetail.setText(myList.get(position).getAddress());
			//System.out.println("......position..................."+position);
			if(new Integer(myList.get(position).getID())==mainaddress){
				holder.ivismainaddress.setBackgroundResource(R.drawable.ic_lable_tick);
			}
			else{
				
			} 
	
		
		
		return convertView;
	}
	
	static class ViewHolder {

		TextView tvbuyername;
		TextView tvbuyerphone;
		TextView tvaddressdetail;
		ImageView ivismainaddress;
	}

}
