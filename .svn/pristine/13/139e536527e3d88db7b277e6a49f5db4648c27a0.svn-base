package com.fsti.fjdicClient.adapter;

import java.util.List;

import com.fsti.fjdicClient.R;
import com.fsti.fjdicClient.bean.CommunityGoodsCategoryEntity;
import com.fsti.fjdicClient.bean.GroupBuyingGoodsCategoryEntity;
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

public class GroupBuySortListAdapter extends BaseAdapter {


	private LayoutInflater mInflater;
	private Context myContext;
	private List<GroupBuyingGoodsCategoryEntity> myList;
	private int myLevel;

	/**
	 * 社区商城分类信息适配器
	 * @param context 上下文
	 * @param list 列表集合
	 * @param level 分类等级
	 */
	public GroupBuySortListAdapter(Context context,List<GroupBuyingGoodsCategoryEntity> list,int level){
		this.mInflater = LayoutInflater.from(context);
		this.myContext = context;
		this.myList = list;
		this.myLevel = level;
	}


	public int getCount() {
		// TODO Auto-generated method stub
		return myList.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if(convertView == null){
			convertView = mInflater.inflate(R.layout.layout_list_item_shoppingmall_sort, null);
			holder = new ViewHolder();
//			holder.ivSortIcon = (ImageView) convertView.findViewById(R.id.iv_sort_first_level);
			holder.tvSortName = (TextView)convertView.findViewById(R.id.tv_shopping_sort_name);
			holder.tvSortnum = (TextView)convertView.findViewById(R.id.tv_item_shopping_right_counts);
			convertView.setTag(holder);
		}		
		else{
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tvSortName.setText(myList.get(position).getName());
		if(myList.get(position).getGoodsCount()>0){
		holder.tvSortnum.setText(String.valueOf(myList.get(position).getGoodsCount()));
		holder.tvSortnum.setVisibility(View.VISIBLE);
		}
//		if(myList.get(position).getLevel() == 1){
//			holder.ivSortIcon.setVisibility(View.VISIBLE);
//		}
//		else{
//			holder.ivSortIcon.setVisibility(View.GONE);
//		}
		
//		holder.leftName.setVisibility(View.GONE);
//		holder.leftTelephone.setVisibility(View.GONE);
//		holder.leftName.setVisibility(View.GONE);
//		if(myList.get(position).isSelected()){
//			holder.ivSelectedIcon.setVisibility(View.VISIBLE);
//		}
//		else{
//			holder.ivSelectedIcon.setVisibility(View.GONE);
//		}
//		
//		holder.tvSortName.setText(myList.get(position).getPopSortName());
		return convertView;	
	}
	
	
	static class ViewHolder {
//		ImageView ivSortIcon;
		TextView tvSortName;
		TextView tvSortnum;
	}
}
