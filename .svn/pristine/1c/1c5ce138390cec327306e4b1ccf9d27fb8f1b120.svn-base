package com.fsti.fjdicClient.adapter;

import java.util.List;

import com.fsti.fjdicClient.R;
import com.fsti.fjdicClient.bean.CommunityGoodsCategoryEntity;
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
 * 社区商城分类信息适配器
 * @author 金涛
 *
 */

public class ShoppingmallSortAdapter extends BaseAdapter {	
	private LayoutInflater mInflater;
	private Context myContext;
	private List<CommunityGoodsCategoryEntity> myList;
	private int myLevel;

	/**
	 * 社区商城分类信息适配器
	 * @param context 上下文
	 * @param list 列表集合
	 * @param level 分类等级
	 */
	public ShoppingmallSortAdapter(Context context,List<CommunityGoodsCategoryEntity> list,int level){
		this.mInflater = LayoutInflater.from(context);
		this.myContext = context;
		this.myList = list;
		this.myLevel = level;
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
			convertView = mInflater.inflate(R.layout.layout_list_item_shoppingmall_sort, null);
			holder = new ViewHolder();
			holder.tvGoodsCounts = (TextView) convertView.findViewById(R.id.tv_item_shopping_right_counts);
			holder.tvSortName = (TextView)convertView.findViewById(R.id.tv_shopping_sort_name);
			convertView.setTag(holder);
		}		
		else{
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tvSortName.setText(myList.get(position).getName());
		if(myList.get(position).getGoodsCount()!=0){
			holder.tvGoodsCounts.setVisibility(View.VISIBLE);
			holder.tvGoodsCounts.setText(""+myList.get(position).getGoodsCount());
		}
		return convertView;	
	}
	
	
	static class ViewHolder {
//		ImageView ivSortIcon;
		TextView tvSortName;
		TextView tvGoodsCounts;
	}
}
