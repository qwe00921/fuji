package com.fsti.fjdicClient.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fsti.fjdicClient.R;
import com.fsti.fjdicClient.bean.MoreOrderInfoEntity;

public class MoreOrderGoodsInfoAdapter extends BaseAdapter{
	private LayoutInflater mInflater;
	private Context myContext;
	private List<MoreOrderInfoEntity> myList;
	
	public MoreOrderGoodsInfoAdapter(Context context,List<MoreOrderInfoEntity> list){
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
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.layout_item_mycenter_more_orderinfo, null);
			holder = new ViewHolder();
			holder.tvIntro = (TextView) convertView.findViewById(R.id.tv_mycenter_orderinfo_tvmoreorderinfo1);
			holder.tvprice = (TextView) convertView.findViewById(R.id.tv_mycenter_orderinfo_tvmoreorderinfo2);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tvIntro.setText(myList.get(position).getName());
		holder.tvprice.setText(myList.get(position).getValue());
		
		return convertView;
	}
	
	static class ViewHolder {
		TextView tvprice;
		TextView tvIntro;
	}

}
