package com.fsti.fjdicClient.adapter;
import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.fsti.fjdicClient.R;
import com.fsti.fjdicClient.bean.CommentEntity;
public class commentlistAdapter extends BaseAdapter {


		private LayoutInflater mInflater;
		private Context myContext;
		private List<CommentEntity> myList;
		private int is_chosed=0;

		/**
		 * 社区商城分类信息适配器
		 * @param context 上下文
		 * @param list 列表集合
		
		 */
		public commentlistAdapter(Context context,List<CommentEntity> list,int is_chosed){
			this.mInflater = LayoutInflater.from(context);
			this.myContext = context;
			this.myList = list;
			this.is_chosed=is_chosed;
			
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
				convertView = mInflater.inflate(R.layout.layout_commentitem, null);
				holder = new ViewHolder();
				holder.tvcommiter = (TextView)convertView.findViewById(R.id.tv_shoppingmall_commentslist_committer);
				holder.tvpoint = (TextView)convertView.findViewById(R.id.tv_shoppingmall_commentslist_point);
				holder.tvcontent = (TextView)convertView.findViewById(R.id.tv_shoppingmall_commentslist_content);
				holder.tvdate = (TextView)convertView.findViewById(R.id.tv_shoppingmall_commentslist_date);
				convertView.setTag(holder);
			}else{
				holder = (ViewHolder) convertView.getTag();
			}
			if(myList.get(position).getCommitter().equals("")||myList.get(position).getCommitter()==null){
				holder.tvcommiter.setText("匿名评价");
			}else{
				holder.tvcommiter.setText(myList.get(position).getCommitter());
			}
			int point1 = myList.get(position).getCommodityPoint();
			int point2 = myList.get(position).getServePoint();
			int point3 = myList.get(position).getSellerPoint();
			int point4 = myList.get(position).getLogisticsPonit();
			float point =(float) (point1+point2+point3+point4)/4;
			
			holder.tvpoint.setText(String.valueOf(point)+" 分");
			holder.tvcontent.setText(myList.get(position).getContent());
			holder.tvdate.setText(myList.get(position).getDate());
			
			return convertView;	
		}
		
		
		static class ViewHolder {
			TextView tvcommiter;
			TextView tvpoint;
			TextView tvcontent;
			TextView tvdate;
			
		}
	}


