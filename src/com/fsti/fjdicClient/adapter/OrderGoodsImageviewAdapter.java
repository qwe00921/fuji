package com.fsti.fjdicClient.adapter;

import java.util.List;

import com.fsti.fjdicClient.R;
import com.fsti.fjdicClient.activity.mycenter.ConfirmReceivingListActivity;
import com.fsti.fjdicClient.activity.mycenter.HistoryOrderListActivity;
import com.fsti.fjdicClient.activity.mycenter.Orderinfo;
import com.fsti.fjdicClient.activity.mycenter.RemindSellerDeliverListActivity;
import com.fsti.fjdicClient.activity.mycenter.WaitBuyerPayListActivity;
import com.fsti.fjdicClient.adapter.MycenterSellerDeliverAdapter.ViewHolder;
import com.fsti.fjdicClient.bean.GoodsEntity;
import com.fsti.fjdicClient.bean.Order;
import com.fsti.fjdicClient.bean.OrderGoodsInfoEntity;
import com.fsti.fjdicClient.util.SyncImageLoadUtil;

import android.R.color;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;


public class OrderGoodsImageviewAdapter  extends BaseAdapter{
	private SyncImageLoadUtil syncImageLoad = new SyncImageLoadUtil();
	private LayoutInflater mInflater;
	private Context myContext;
	private List<OrderGoodsInfoEntity> myList;
	private Activity activity;
	private int activity_key=0;
	private int first=1;//判断图片是否为预加载,1为预加载，0为正式加载
	private int max_load=20;//设置每个item一次最多加载的图片数
	private int add_more_img=0;//1表示全部加载
//	private int add_more_img=1;//1表示全部加载
	private Order order;
	public void set_activity(int activity_key ,Activity activity){
		this.activity=activity;	
		this.activity_key=activity_key;
		
	}

	public OrderGoodsImageviewAdapter(Context context,Order order,List<OrderGoodsInfoEntity> orderGoodsList,int add_more_img){
		this.myContext=context;
		this.myList = orderGoodsList;
		this.mInflater = LayoutInflater.from(context);
		this.order=order;
		this.add_more_img=add_more_img;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return myList.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView( int position, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		
		final ViewHolder holder;
		if(convertView==null){
			holder =new ViewHolder();
			convertView = mInflater.inflate(R.layout.layout_item_mycenter_ordergoodsimageview_show, null);
			holder.imageview =(ImageView) convertView.findViewById(R.id.iv_fl_item_list_mycenter_ordergoodsimage);	
			holder.imageview.setScaleType(ScaleType.CENTER_CROP);
			convertView.setTag(holder);
			
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
//		if(((first+position)>0)&&(max_load>position||(add_more_img==1&&myList.size()>max_load))){
//			first=0;
			syncImageLoad.displayImage(myList.get(position).getImageUrl() , holder.imageview, myContext);
//		}
		
		final int  new_position=position;
		 holder.imageview.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					 holder.imageview.setBackgroundColor(0);
					 switch(activity_key){
					 case 1:
						 ((WaitBuyerPayListActivity) activity).get_goodsinfo(order,myList.get(new_position));
						 break;
					 case 2:
						 ((RemindSellerDeliverListActivity) activity).get_goodsinfo(order,myList.get(new_position));
						 break;
					 case 3:
						 ((ConfirmReceivingListActivity) activity).get_goodsinfo(order,myList.get(new_position));
						 break;
					 case 4:
						 ((HistoryOrderListActivity) activity).get_goodsinfo(order,myList.get(new_position));
						 break;
					 case 5:
						 ((Orderinfo) activity).get_goodsinfo(myList.get(new_position));
						 break;
					 }
					
				}
			});
		 holder.imageview.setOnTouchListener(new View.OnTouchListener() {
				
				@Override
				public boolean onTouch(View arg0, MotionEvent arg1) {
					// TODO Auto-generated method stub
				
					if(arg1.getAction()== MotionEvent.ACTION_DOWN){
						 holder.imageview.setBackgroundColor(color.background_dark);
					}
					else if(arg1.getAction()== MotionEvent.ACTION_CANCEL){
						 holder.imageview.setBackgroundColor(0);
					}
					
					return false;
				}
			});
		 
		
		 
		return convertView;
	}
	
	static class ViewHolder {
		ImageView imageview;
		
	}
	
}
