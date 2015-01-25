package com.fsti.fjdicClient.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.fsti.fjdicClient.activity.groupBuy.GroupBuyDetailActivity;
import com.fsti.fjdicClient.bean.AdvEntity;
import com.fsti.fjdicClient.util.ImageLoaderHelper;
//import com.fsti.fjdicClient.activity.shoppingMall.GoodsListNoButtonActivity;
//import com.fsti.fjdicClient.activity.shoppingMall.ShoppingmallGoodsDetailActivity;

public class AdvViewPagerAdapter extends PagerAdapter {
    private ArrayList<ImageView> imgViews;
    private List<AdvEntity>      adList;
    // private SyncImageLoadUtil mySilu;
    // private OnImageLoadListener myListener;
    private Context              context;
    private int                  mCounts;

    public static String         FLAGTOSHOPPING = "";
    public static String         FLAGTOGROUP    = "";

    public AdvViewPagerAdapter(ArrayList<ImageView> imgViews, List<AdvEntity> adList) {
        this.imgViews = imgViews;
        this.adList = adList;
        // this.mySilu = silu;
        // this.myListener = listener;
    }

    public AdvViewPagerAdapter(ArrayList<ImageView> imgViews, List<AdvEntity> adList, Context context) {
        this.context = context;
        this.imgViews = imgViews;
        this.adList = adList;
        this.mCounts = imgViews.size();
    }

    @Override
    public void destroyItem(View arg0, int arg1, Object arg2) {
        // TODO Auto-generated method stub
        // ((ViewPager) arg0).removeView((View) arg2);
        // if (arg1 >= imgViews.size()) {
        // int newPosition = arg1%imgViews.size();
        // arg1 = newPosition;
        // }
        // if(arg1 <0){
        // arg1 = -arg1;
        // }
    }

    @Override
    public void finishUpdate(View arg0) {
        // TODO Auto-generated method stub
        // pagerView = arg0;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        // return adList.size();
        // return mCounts+1;
        return Integer.MAX_VALUE;
    }

    @Override
    public Object instantiateItem(View arg0, int arg1) {
        // ((ViewPager) arg0).addView(imgViews.get(arg1));
        // // 设置标志位
        // imgViews.get(arg1).setTag(arg1);
        // mySilu.loadImage(arg1, adList.get(arg1).getImageUrl(),myListener,context);
        // System.out.println("viewpager 位置 index ： "+ arg1);
        // return imgViews.get(arg1);
        // if (arg1 >= imgViews.size()) {
        // int newPosition = arg1%imgViews.size();
        // arg1 = newPosition;
        // mCounts++;
        // }
        // if(arg1 <0){
        // arg1 = -arg1;
        // mCounts--;
        // }
        arg1 = arg1 % imgViews.size();
        try {
            ((ViewPager) arg0).addView(imgViews.get(arg1), 0);
        } catch (Exception e) {
        }
        imgViews.get(arg1).setTag(arg1);
        ImageLoaderHelper.displayImage(imgViews.get(arg1), adList.get(arg1).getImageUrl());
        // mySilu.loadImage(arg1, adList.get(arg1).getImageUrl(), myListener, context);
        imgViews.get(arg1).setOnClickListener(new ImageClick(adList.get(arg1)));
        return imgViews.get(arg1);
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == (arg1);

        // return arg0.equals(arg1);//wang...
    }

    @Override
    public void restoreState(Parcelable arg0, ClassLoader arg1) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

    @Override
    public void startUpdate(View arg0) {
    }

    class ImageClick implements OnClickListener {
        private AdvEntity advEntity;

        public ImageClick(AdvEntity advEntity) {
            this.advEntity = advEntity;
        }

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            // Intent intent = new Intent(myActivity ,GoodsListNoButtonActivity.class);
            switch (advEntity.getIdentifier()) {
            // case 3:
            // GoodsListNoButtonActivity.advToGoodsListActivity(context, advEntity, "adv");
            // break;
            case 2:
                FLAGTOGROUP = "ADV";
                Intent toGroup = new Intent(context, GroupBuyDetailActivity.class);
                Bundle bundle2 = new Bundle();
                bundle2.putSerializable("advEntity", advEntity);
                toGroup.putExtras(bundle2);
                System.out.println("广告ID  " + advEntity.getAdvID());
                v.getContext().startActivity(toGroup);
                break;
            // case 1:
            // FLAGTOSHOPPING = "ADV";
            // Intent toShoppingmall = new Intent(context ,ShoppingmallGoodsDetailActivity.class);
            // Bundle bundle1 = new Bundle();
            // bundle1.putSerializable("advEntity", advEntity);
            // toShoppingmall.putExtras(bundle1);
            // System.out.println("广告ID  "+ advEntity.getAdvID());
            // v.getContext().startActivity(toShoppingmall);
            // break;
            }

        }

    }
}
