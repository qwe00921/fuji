package com.fsti.fjdicClient.adapter;

import java.util.ArrayList;
import java.util.List;

import com.fsti.fjdicClient.R;
import com.fsti.fjdicClient.bean.ConsigneeAddressEntity;
import com.fsti.fjdicClient.bean.SearchGoodsEntity;
import com.fsti.fjdicClient.bean.ShoppingcartInfoEntity;
import com.fsti.fjdicClient.bean.ShopsDetailInfoEntity;
import com.fsti.fjdicClient.dao.BusinessDao;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SearchHistoryListAdapter extends BaseAdapter{
	private LayoutInflater mInflater;
	private Context myContext;
	private List<SearchGoodsEntity> myList;
	public SearchHistoryListAdapter(Context baseContext,
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
			convertView = mInflater.inflate(R.layout.layout_activity_item_search_history, null);
			holder = new ViewHolder();
			holder.tvKeyword = (TextView) convertView.findViewById(R.id.tv_search_history_key);
			holder.tvType = (TextView) convertView.findViewById(R.id.tv_search_history_type);
			holder.ivdelete = (ImageView) convertView.findViewById(R.id.iv_search_history_delete);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		String keyword = myList.get(position).getKeyword();
System.out.println("keyword" + keyword);
		holder.tvKeyword.setText(keyword);
		int type = myList.get(position).getType();
		if(type ==0){
			holder.tvType.setText("全部：");
		}
		else if(type ==1){
			holder.tvType.setText("商品：");
		}
		else if(type ==2){
			holder.tvType.setText("服务：");
		}
		
		holder.ivdelete.setOnClickListener(new OnDeleteListener(position)
		);
		return convertView;
	}
	static class ViewHolder {
		TextView tvKeyword;
		TextView tvType;
		ImageView ivdelete;
	}
	public void updateList (){
		myList.clear();
		List<SearchGoodsEntity> listSearchGoodsTemp = new ArrayList<SearchGoodsEntity>();
		listSearchGoodsTemp = BusinessDao.getSearchHistoryTableData();
		if(listSearchGoodsTemp.size()!=0){
			for (int i = listSearchGoodsTemp.size()-1; i >= 0 ; --i) {
				myList.add(listSearchGoodsTemp.get(i));
			}
		}
		notifyDataSetChanged();
	}
	class OnDeleteListener implements OnClickListener{
		private int position;
		public OnDeleteListener(int position){
			this.position = position;
		}
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			BusinessDao.deleteSearchHistoryTableData(myList.get(position));
			updateList();
		}
		
	}
}
