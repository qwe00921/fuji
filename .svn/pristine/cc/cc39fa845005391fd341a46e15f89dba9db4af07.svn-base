package com.fsti.fjdicClient.adapter;

import java.util.List;

import com.fsti.fjdicClient.R;
import com.fsti.fjdicClient.bean.ConsigneeAddressEntity;
import com.fsti.fjdicClient.bean.SearchGoodsEntity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SearchHotListAdapter extends BaseAdapter{
	private LayoutInflater mInflater;
	private Context myContext;
	private List<SearchGoodsEntity> myList;
	public SearchHotListAdapter(Context baseContext,
			List<SearchGoodsEntity> listSearchGoods) {
		// TODO Auto-generated constructor stub
		this.myContext = baseContext;
		this.myList = listSearchGoods;
		this.mInflater= LayoutInflater.from(baseContext);
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
		if(convertView ==null){
			convertView = mInflater.inflate(R.layout.layout_activity_item_search_hot, null);
			holder = new ViewHolder();
			holder.tvKeyword = (TextView) convertView.findViewById(R.id.tv_search_hot_key);
			holder.tvOrder = (TextView) convertView.findViewById(R.id.tv_search_hot_order);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		String keyword = myList.get(position).getKeyword();
		holder.tvKeyword.setText(keyword);
		int i = position+1;
		holder.tvOrder.setText(i+":");
		
		
		return convertView;
	}
	static class ViewHolder {
		TextView tvKeyword;
		TextView tvOrder;
	}
	public void updateList(List<SearchGoodsEntity> list){
		this.myList = list;
		this.notifyDataSetChanged();
	}
}
