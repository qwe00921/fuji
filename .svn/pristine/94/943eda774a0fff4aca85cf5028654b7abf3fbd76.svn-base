package com.fsti.fjdicClient.adapter;


import java.util.List;
import com.fsti.fjdicClient.R;
import com.fsti.fjdicClient.activity.mycenter.CollectMainListActivity;
import com.fsti.fjdicClient.adapter.WaitBuyerPayAdapter.ViewHolder;
import com.fsti.fjdicClient.bean.GoodsEntity;
import com.fsti.fjdicClient.util.SyncImageLoadUtil;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MycenterCollectMainAdapter extends BaseAdapter{
	
	private SyncImageLoadUtil syncImageLoad = new SyncImageLoadUtil();
	private List<GoodsEntity> list;
	private Context context;
	private LayoutInflater mInflater;
	private boolean visible = false;
    private int[] is_select;
    private int is_writting=0;//是否在编辑中，1表示正在编辑
	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	public MycenterCollectMainAdapter(Context context,
			List<GoodsEntity> list) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.list = list;
		mInflater = LayoutInflater.from(context);
	}


	public MycenterCollectMainAdapter(Context context,
			List<GoodsEntity> list,int is_select[],int is_writting) {
		// TODO Auto-generated constructor stub
		this.context = context;
		
		this.list = list;
		mInflater = LayoutInflater.from(context);
		this.is_select = is_select;
		this.is_writting = is_writting;
	}
	
	public void updata(List<GoodsEntity> list,int is_select[],int is_writting){
		this.list = list;
        this.is_select = is_select;
		this.is_writting = is_writting;
		this.notifyDataSetChanged();
	}
	

	@Override
	public int getCount() {
		// TODO Auto-generated method stub

		int m=0;
		for(int q=0;q<is_select.length;q++){//为标题添加占位数据，该数据不做显示
			if(is_select[q]==2){m=m+1;}
		}
		return list.size()+m;
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
		
		ViewHolder holder ;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.layout_activity_item_mycenter_collect_modify, null);
			holder = new ViewHolder();
			holder.tvSellerName = (TextView) convertView.findViewById(R.id.tv_item_mycenter_collect_sellername);
			holder.tvPrice = (TextView) convertView.findViewById(R.id.tv_item_mycenter_collect_modify_originalprice_show);
			holder.tvPrice.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG ); 
			holder.tvPricePre = (TextView) convertView.findViewById(R.id.tv_item_mycenter_collect_modify_originalprice_pre);
			holder.tvPricenow = (TextView) convertView.findViewById(R.id.tv_item_mycenter_collect_modify_discountedprice);
			holder.tvGoodsIntro = (TextView) convertView.findViewById(R.id.tv_item_mycenter_collect_modify_describe);
			holder.ivGoods = (ImageView) convertView.findViewById(R.id.iv_item_mycenter_collect_modify_goods);
			holder.ivSelect = (ImageView) convertView.findViewById(R.id.iv_item_mycenter_collect_modify_select);
			holder.ivMore = (ImageView) convertView.findViewById(R.id.iv_item_mycenter_collect_modify_more);
			holder.layout  =(RelativeLayout) convertView.findViewById(R.id.rl_item_mycenter_collect_modify);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		
		int m=0;
		for(int q=0;q<position;q++){//在当前位置之前有几个标题
			if(is_select[q]==2){m=m+1;}
		}
		
		if(is_select[position]==2){
			holder.tvSellerName.setVisibility(View.VISIBLE);
			holder. layout.setVisibility(View.GONE);
			
			holder.tvSellerName.setText(CollectMainListActivity.sellerName[m]);
			
		}else{
			
			holder.tvSellerName.setVisibility(View.GONE);
			holder. layout.setVisibility(View.VISIBLE);
			if(list.get(position-m).getOriginalCost()!=null&&!list.get(position-m).getOriginalCost().equals("")){
				holder.tvPrice.setText(list.get(position-m).getOriginalCost()+"元");
			}else{
				holder.tvPricePre.setVisibility(View.GONE);
				holder.tvPrice.setVisibility(View.GONE);
			}
			if(list.get(position-m).getDiscountedPrice()!=null&&!list.get(position-m).getDiscountedPrice().equals("")){
				holder.tvPricenow.setText(list.get(position-m).getDiscountedPrice()+"元");
			}else{
				holder.tvPricenow.setVisibility(View.GONE);
			}
			//holder.tvGoodsIntro.setText(list.get(position).getIntro());
//			holder.tvPricenow.setText(list.get(position-m).getDiscountedPrice()+"元");
//			holder.tvPrice.setText(list.get(position-m).getOriginalCost()+"元");
			holder.tvGoodsIntro.setText(list.get(position-m).getName());
			syncImageLoad.displayImage(list.get(position-m).getImageUrl() , holder.ivGoods, context);
			if(visible){
				if(is_select[position]==1)
				{  
					holder.ivSelect.setImageResource(R.drawable.input_lable_selected);
				}
				else{
					holder.ivSelect.setImageResource(R.drawable.input_lable_noselected);
				}
				holder.ivSelect.setVisibility(View.VISIBLE);
			}else{
				holder.ivSelect.setVisibility(View.GONE);
			}
			if(is_writting==0){
				holder.ivMore.setVisibility(View.VISIBLE);
			}else{
				holder.ivMore.setVisibility(View.GONE);
			}
		}
		return convertView;
	}
	static class ViewHolder{
		TextView tvGoodsIntro;
		TextView tvPrice;
		TextView tvPricePre;
		TextView tvPricenow;
		TextView tvSellerName;
		ImageView ivGoods;
		ImageView ivSelect;
		ImageView ivMore;
		RelativeLayout layout;
	}

}
