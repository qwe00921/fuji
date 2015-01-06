package com.fsti.fjdicClient.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fsti.fjdicClient.R;
import com.fsti.fjdicClient.bean.GoodsEntity;
import com.fsti.fjdicClient.util.DateUtils;
import com.fsti.fjdicClient.util.ImageLoaderHelper;
import com.fsti.fjdicClient.util.StringUtils;
import com.fsti.fjdicClient.util.ViewHolder;

public class GroupbuyNewAdapter extends BaseAdapter {

    private Context           myContext;
    private List<GoodsEntity> myList;

    public GroupbuyNewAdapter(Context context, List<GoodsEntity> list) {
        this.myContext = context;
        this.myList = list;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return myList.size();
    }

    @Override
    public GoodsEntity getItem(int position) {
        return myList.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    // public void updata(List<GoodsEntity> list) {
    // this.myList = list;
    // this.notifyDataSetChanged();
    // }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GoodsEntity line = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(myContext).inflate(R.layout.item_goods_new, parent, false);
        }
        ImageView img_pic = ViewHolder.get(convertView, R.id.iv_pic);
        TextView tv_goods_fold_num = ViewHolder.get(convertView, R.id.tv_goods_fold_num);
        TextView tv_goods_time = ViewHolder.get(convertView, R.id.tv_goods_time);
        if (!StringUtils.isEmpty(line.getImageUrl())) {
            ImageLoaderHelper.displayImage(img_pic, line.getImageUrl());
        }
        tv_goods_fold_num.setText(myContext.getString(R.string.str_test_code, line.getSelledCount()));
        tv_goods_time.setText(DateUtils.getTimeByThms(line.getTimes_long()));
        // txt_length.setText(NumberUtil.getDistance(line.getDistance(), 1));
        // txt_number.setText(String.valueOf(map.getLeftSpace()));
        // ratingbar_credit.setRating(map.getCredit() / 100f * 5f < 0.5f ? 0.5f : map.getCredit() / 100f * 5f);
        return convertView;
    }

    public void notifiy() {

    }
}
