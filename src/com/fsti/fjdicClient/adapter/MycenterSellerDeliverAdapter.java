package com.fsti.fjdicClient.adapter;

import java.util.List;

import android.R.color;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fsti.fjdicClient.R;
import com.fsti.fjdicClient.activity.mycenter.Orderinfo;
import com.fsti.fjdicClient.activity.mycenter.RemindSellerDeliverListActivity;
import com.fsti.fjdicClient.bean.Order;
import com.fsti.fjdicClient.bean.OrderGoodsInfoEntity;
import com.fsti.fjdicClient.util.ImageLoaderHelper;

public class MycenterSellerDeliverAdapter extends BaseAdapter {
    private LayoutInflater                  mInflater;

    private Context                         myContext;
    private List<Order>                     myList;
    private int                             first        = 0; // 第一次调用加载更多图片
    private int                             old_position = 0; // 保存上次加载的地方，防止多次加载
    private RemindSellerDeliverListActivity activity;

    // private SyncImageLoadUtil syncImageLoad = new SyncImageLoadUtil();
    public MycenterSellerDeliverAdapter(Context context, List<Order> List, RemindSellerDeliverListActivity activity) {
        this.myContext = context;
        this.myList = List;
        this.mInflater = LayoutInflater.from(context);
        this.activity = activity;
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
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.layout_activity_item_mycentenr_sellerdeliver, null);
            holder.tvTime = (TextView) convertView.findViewById(R.id.tv_item_list_mycenter_sellerdeliver_time);
            holder.tvPrice = (TextView) convertView.findViewById(R.id.tv_item_list_mycenter_sellerdeliver_price);
            holder.tvCounts = (TextView) convertView.findViewById(R.id.tv_item_list_mycenter_sellerdeliver_counts);
            holder.tvFreight = (TextView) convertView.findViewById(R.id.tv_item_list_mycenter_sellerdeliver_freight);
            holder.tvflIntro = (TextView) convertView.findViewById(R.id.tv_fl_item_list_mycenter_sellerdeliver_describe);
            holder.tvflPrice = (TextView) convertView.findViewById(R.id.tv_fl_item_list_mycenter_sellerdeliver_price);
            holder.ivflgoods = (ImageView) convertView.findViewById(R.id.iv_fl_item_list_mycenter_sellerdeliver_goods);
            holder.rlflGoods = (RelativeLayout) convertView.findViewById(R.id.rl_fl_item_list_mycenter_sellerdeliver_main);
            holder.gridview = (GridView) convertView.findViewById(R.id.gv_item_list_mycenter_gridview2);
            holder.deliver = (Button) convertView.findViewById(R.id.btn_item_list_mycenter_sellerdeliver_deliver);
            holder.gvlayout = (LinearLayout) convertView.findViewById(R.id.linear_item_list_mycenter_waitbuyerpay_click2);
            holder.scrollview = (HorizontalScrollView) convertView.findViewById(R.id.sc_item_scrollview2);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(myList.get(position).getList().size() * 140, LinearLayout.LayoutParams.WRAP_CONTENT);
        // LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        holder.gridview.setLayoutParams(params);
        holder.gridview.setNumColumns(myList.get(position).getList().size());
        holder.adapter2 = new OrderGoodsImageviewAdapter(myContext, myList.get(position), myList.get(position).getList(), 0);
        holder.adapter2.set_activity(2, activity);
        holder.gridview.setAdapter(holder.adapter2);
        float freight = Integer.parseInt(myList.get(position).getFreightPrice());
        float totalprice = freight;
        OrderGoodsInfoEntity ordergoodsinfo;
        for (int q = 0; q < myList.get(position).getList().size(); q++) {
            ordergoodsinfo = myList.get(position).getList().get(q);
            // 测试用，测试数据有空值
            if (ordergoodsinfo.getPrice() == null || ordergoodsinfo.getPrice().equals("null")) {
                totalprice = 0000;
                break;
            }
            // yyy
            // totalprice=totalprice+Float.parseFloat(ordergoodsinfo.getPrice())*ordergoodsinfo.getAmount();
            totalprice = ShoppingcartMainAdapter.toFloatTwo(totalprice + Float.parseFloat(ordergoodsinfo.getPrice()) * ordergoodsinfo.getAmount());// wang...
        }

        holder.tvPrice.setText(String.valueOf(totalprice) + "元");
        holder.tvFreight.setText("运费：" + String.valueOf(freight) + "元");

        holder.tvCounts.setText("共" + myList.get(position).getList().size() + "类商品");

        holder.tvTime.setText("确认付款时间:" + myList.get(position).getOrderTime());
        if (myList.get(position).getList().size() == 1) {
            ImageLoaderHelper.displayImage(holder.ivflgoods, myList.get(position).getList().get(0).getImageUrl());
            // syncImageLoad.displayImage(myList.get(position).getList().get(0).getImageUrl() ,holder.ivflgoods, myContext);
            holder.gridview.setVisibility(View.GONE);
            holder.rlflGoods.setVisibility(View.VISIBLE);
            holder.tvflIntro.setText(myList.get(position).getList().get(0).getName());
            holder.tvflPrice.setText(myList.get(position).getList().get(0).getPrice() + "元");

        } else {

            holder.rlflGoods.setVisibility(View.GONE);
            holder.gridview.setVisibility(View.VISIBLE);
        }

        holder.scrollview.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                // TODO Auto-generated method stub
                if (first == 0 || old_position != position) {
                    first = 1;
                    old_position = position;
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(myList.get(position).getList().size() * 140, LinearLayout.LayoutParams.WRAP_CONTENT);
                    // LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                    holder.gridview.setLayoutParams(params);
                    holder.gridview.setNumColumns(myList.get(position).getList().size());
                    // OrderGoodsImageviewAdapter adapter2 = new OrderGoodsImageviewAdapter(myContext,myList.get(position),myList.get(position).getList(),1);
                    holder.adapter2.set_activity(2, activity);
                }
                return false;
            }
        });

        holder.gvlayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(myContext, Orderinfo.class);
                Bundle bundle = new Bundle();
                intent.putExtra("buttonname", "提醒\n发货");
                bundle.putSerializable("order", myList.get(position));
                intent.putExtras(bundle);
                arg0.getContext().startActivity(intent);
            }
        });
        convertView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                System.out.println("position22222222222rrrrrr=" + position);
                Intent intent = new Intent(myContext, Orderinfo.class);
                Bundle bundle = new Bundle();
                intent.putExtra("buttonname", "提醒\n发货");
                bundle.putSerializable("order", myList.get(position));
                intent.putExtras(bundle);
                arg0.getContext().startActivity(intent);
            }
        });
        holder.rlflGoods.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                holder.rlflGoods.setBackgroundColor(0);
                activity.get_goodsinfo(myList.get(position), myList.get(position).getList().get(0));
            }
        });
        holder.rlflGoods.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                // TODO Auto-generated method stub

                if (arg1.getAction() == MotionEvent.ACTION_DOWN) {
                    holder.rlflGoods.setBackgroundColor(color.background_dark);
                } else if (arg1.getAction() == MotionEvent.ACTION_CANCEL) {
                    holder.rlflGoods.setBackgroundColor(0);
                }

                return false;
            }
        });

        holder.deliver.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                System.out.println("position1=" + position);
                activity.manage_order(myList.get(position).getOrderID(), myList.get(position).getOrderType());
            }
        });
        return convertView;

    }

    static class ViewHolder {
        TextView                   tvTime;
        TextView                   tvPrice;
        TextView                   tvCounts;
        TextView                   tvFreight;
        LinearLayout               gvlayout;
        GridView                   gridview;
        List<ImageView>            imageList;
        TextView                   tvflIntro;
        TextView                   tvflPrice;
        ImageView                  ivflgoods;
        RelativeLayout             rlflGoods;
        Button                     deliver;
        HorizontalScrollView       scrollview;
        OrderGoodsImageviewAdapter adapter2;
    }

}
