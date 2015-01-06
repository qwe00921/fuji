package com.fsti.fjdicClient.adapter;

import java.util.List;

import com.fsti.fjdicClient.R;
import com.fsti.fjdicClient.bean.PopSortEntity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


/**
 * 弹窗分类信息适配器
 * @author 金涛
 *
 */

public class PopSortAdapter extends BaseAdapter {	
	private LayoutInflater mInflater;
	private Context myContext;
	private List<PopSortEntity> myList;

	
	public PopSortAdapter(Context context,List<PopSortEntity> list){
		this.mInflater = LayoutInflater.from(context);
		this.myContext = context;
		this.myList = list;
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
		ViewHolder holder;
		if(convertView == null){
			convertView = mInflater.inflate(R.layout.layout_fn_item_pop_sort, null);
			holder = new ViewHolder();
			holder.ivSelectedIcon = (ImageView)convertView.findViewById(R.id.iv_sort_selected);
			holder.tvSortName = (TextView)convertView.findViewById(R.id.tv_pop_sort_name);
			convertView.setTag(holder);
		}		
		else{
			holder = (ViewHolder) convertView.getTag();
		}
//		holder.leftName.setVisibility(View.GONE);
//		holder.leftTelephone.setVisibility(View.GONE);
//		holder.leftName.setVisibility(View.GONE);
		if(myList.get(position).isSelected()){
			holder.ivSelectedIcon.setVisibility(View.VISIBLE);
		}
		else{
			holder.ivSelectedIcon.setVisibility(View.GONE);
		}
		
		holder.tvSortName.setText(myList.get(position).getPopSortName());
		return convertView;	
	}
	
	
	static class ViewHolder {
		ImageView ivSelectedIcon;
		TextView tvSortName;
	}
}
