package com.fsti.fjdicClient.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Paint;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fsti.fjdicClient.R;
import com.fsti.fjdicClient.bean.GoodsEntity;
import com.fsti.fjdicClient.util.ImageLoaderHelper;

public class GroupbuyAdapter extends BaseAdapter {

    private LayoutInflater    mInflater;
    private Context           myContext;
    private List<GoodsEntity> myList;

    public GroupbuyAdapter(Context context, List<GoodsEntity> list) {
        this.myContext = context;
        this.myList = list;
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

    public void updata(List<GoodsEntity> list) {
        this.myList = list;
        this.notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.layout_activity_item_groupbuy_main, null);
            holder = new ViewHolder();
            holder.tvBuyingCount = (TextView) convertView.findViewById(R.id.tv_item_groupbuy_main_buyingcounts);
            holder.tvOriginalCost = (TextView) convertView.findViewById(R.id.tv_item_groupbuy_main_originalprice_show);
            holder.tvDiscountPrice = (TextView) convertView.findViewById(R.id.tv_item_groupbuy_main_discountedprice);
            // holder.tvGoodsIntro = (TextView) convertView.findViewById(R.id.tv_item_groupbuy_main_goodsdescribe);
            holder.tvGoodsName = (TextView) convertView.findViewById(R.id.tv_item_groupbuy_main_goodsname);
            holder.ivGoods = (ImageView) convertView.findViewById(R.id.iv_item_groupbuy_main);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tvBuyingCount.setText("" + myList.get(position).getSelledCount() + "人");
        holder.tvDiscountPrice.setText(myList.get(position).getDiscountedPrice() + "元");
        if (TextUtils.isEmpty(myList.get(position).getOriginalCost())) {
            holder.tvOriginalCost.setVisibility(View.GONE);
        } else {
            holder.tvOriginalCost.setVisibility(View.VISIBLE);
            // holder.tvOriginalCost.setText(myList.get(position).getOriginalCost() + "元");
            holder.tvOriginalCost.setText(myContext.getResources().getString(R.string.str_yuanjia, myList.get(position).getOriginalCost()));
            holder.tvOriginalCost.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        }

        // holder.tvGoodsIntro.setText(myList.get(position).getIntro());
        holder.tvGoodsName.setText(myList.get(position).getName());
        ImageLoaderHelper.displayImage(holder.ivGoods, myList.get(position).getImageUrl());
        // new SyncImageLoadUtil().displayImage(myList.get(position).getImageUrl(), holder.ivGoods, myContext);

        return convertView;
    }

    static class ViewHolder {
        TextView  tvBuyingCount;
        TextView  tvOriginalCost;
        TextView  tvDiscountPrice;
        TextView  tvGoodsIntro;
        TextView  tvGoodsName;
        ImageView ivGoods;
    }

}
