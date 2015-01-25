package com.fsti.fjdicClient.adapter;

import java.util.List;

import android.R.color;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fsti.fjdicClient.R;
import com.fsti.fjdicClient.activity.mycenter.GoodsCommentsDetailActivity;
import com.fsti.fjdicClient.activity.mycenter.Orderinfo;
import com.fsti.fjdicClient.bean.Order;
import com.fsti.fjdicClient.bean.OrderGoodsInfoEntity;
import com.fsti.fjdicClient.util.ImageLoaderHelper;

public class MycenterGoodsCommentsAdapter extends BaseAdapter {
    private LayoutInflater             mInflater;
    private Context                    myContext;
    private Order                      order;
    private List<OrderGoodsInfoEntity> myList;
    private Activity                   activity;
    private int                        activity_key = 0;

    public void set_activity(int activity_key, Activity activity) {
        this.activity = activity;
        this.activity_key = activity_key;
    }

    public MycenterGoodsCommentsAdapter(Context context, Order order) {
        this.myContext = context;
        this.order = order;
        this.myList = order.getList();
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        final ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.layout_activity_item_mycenter_goodscomments, null);
            holder = new ViewHolder();
            holder.tvIntro = (TextView) convertView.findViewById(R.id.tv_item_mycenter_goodscomments_main_describe);
            holder.tvprice = (TextView) convertView.findViewById(R.id.tv_item_mycenter_goodscomments_main_price);
            holder.ivGoods = (ImageView) convertView.findViewById(R.id.iv_item_mycenter_goodscomments_goods);
            holder.ivSpline = (ImageView) convertView.findViewById(R.id.iv_item_mycenter_goodscomments_spline);
            holder.rlayout = (RelativeLayout) convertView.findViewById(R.id.rl_item_mycenter_goodscomments_main);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tvIntro.setText(myList.get(position).getName());
        holder.tvprice.setText(myList.get(position).getPrice());
        ImageLoaderHelper.displayImage(holder.ivGoods, myList.get(position).getImageUrl());
        // new SyncImageLoadUtil().displayImage(myList.get(position).getImageUrl() , holder.ivGoods, myContext);
        holder.ivSpline.setVisibility(View.VISIBLE);
        if (position == myList.size() - 1) {
            holder.ivSpline.setVisibility(View.INVISIBLE);
        }
        convertView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                holder.rlayout.setBackgroundColor(0);
                switch (activity_key) {
                case 6:
                    ((Orderinfo) activity).get_goodsinfo(myList.get(position));
                    break;
                case 7:
                    Intent intent = new Intent(myContext, GoodsCommentsDetailActivity.class);
                    intent.putExtra("ID", myList.get(position).getID());
                    intent.putExtra("type", order.getOrderType());
                    arg0.getContext().startActivity(intent);
                    break;

                }

            }
        });

        convertView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                // TODO Auto-generated method stub
                if (arg1.getAction() == MotionEvent.ACTION_DOWN) {
                    holder.rlayout.setBackgroundColor(color.background_dark);
                } else if (arg1.getAction() == MotionEvent.ACTION_CANCEL) {
                    holder.rlayout.setBackgroundColor(0);
                }
                return false;
            }
        });

        return convertView;
    }

    static class ViewHolder {
        TextView       tvprice;
        TextView       tvIntro;
        ImageView      ivGoods;
        ImageView      ivSpline;
        RelativeLayout rlayout;
    }

}
