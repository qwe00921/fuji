package com.fsti.fjdicClient.component;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.fsti.fjdicClient.R;
//import com.fsti.fjdicClient.activity.shoppingMall.GoodsListActivity;
//import com.fsti.fjdicClient.activity.shoppingMall.GoodsListNoButtonActivity;
//import com.fsti.fjdicClient.activity.shoppingMall.ShoppingmallGoodsDetailActivity;
import com.fsti.fjdicClient.bean.AdvEntity;

/**
 * 广告条服务类(viewpager)
 * 
 * @author 金涛
 */
public class AdvertiseViewPagerService {

    private List<AdvEntity> myList;
    private List<String>    urls;
    private Activity        myActivity;
    private ViewPager       myViewPager;

    /**
     * 广告条服务类
     * 
     * @param vp
     *            viewpager 控件
     * @param list
     *            广告集合
     * @param act
     *            页面
     */
    public AdvertiseViewPagerService(ViewPager vp, List<AdvEntity> list, Activity act) {
        this.myViewPager = vp;
        this.myList = list;
        this.myActivity = act;
    }

    // public AdvertiseViewPagerService(ViewPager vp,
    // List<String> urls, ShoppingmallGoodsDetailActivity act) {
    // // TODO Auto-generated constructor stub
    // this.myViewPager = vp;
    // this.urls = urls;
    // this.myActivity = act;
    // }

    /**
     * 是否开启默认图片
     */
    public boolean myEnabledDefaultImage;

    /**
     * 图片加载回调方法
     */
    // public OnImageLoadListener imageLoadListener = new OnImageLoadListener() {
    //
    // @Override
    // public void onImageLoad(Integer t, Drawable drawable) {
    // // BookModel model = (BookModel) getItem(t);
    // View view = myViewPager.findViewWithTag(t);
    // if (view != null) {
    // System.out.println("normal");
    // // ImageView iv = (ImageView) view.findViewById(R.id.sItemIcon);
    // ImageView iv = (ImageView) view;
    // iv.setScaleType(ScaleType.FIT_CENTER);
    // if (myEnabledDefaultImage) {
    // iv.setImageResource(R.drawable.img_default_480x290);
    // } else {
    // // iv.setBackgroundDrawable(drawable);
    // iv.setImageDrawable(drawable);
    // }
    // }
    // }
    //
    // @Override
    // public void onError(Integer t) {
    // System.out.println("error");
    // View view = myViewPager.findViewWithTag(t);
    // if (view != null) {
    // // ImageView iv = (ImageView) view.findViewById(R.id.sItemIcon);
    // ImageView iv = (ImageView) view;
    // iv.setScaleType(ScaleType.FIT_CENTER);
    // iv.setImageResource(R.drawable.image_load_err);
    // }
    // }
    // };
    /**
     * 通过urls获取viewpager视图列表
     * 
     * @return
     */
    // 商品详情中使用
    public ArrayList<View> getViewListByUrls() {
        // TODO Auto-generated method stub
        ArrayList<View> viewList = new ArrayList<View>();
        if (urls == null || urls.size() == 0) {
            String entity = "";
            urls.add(entity);
            myEnabledDefaultImage = true;
        } else {
            myEnabledDefaultImage = false;
        }
        for (int i = 0; i < urls.size(); i++) {
            ImageView contentImageView = new ImageView(myActivity);
            contentImageView.setBackgroundResource(R.drawable.img_default_480x290);

            if (!myEnabledDefaultImage) {
                String url = urls.get(i);
                contentImageView.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {

                    }
                });
            }
            viewList.add(contentImageView);
        }
        // syncImageLoader = new SyncImageLoadUtil(ConstantUtil.MAIN_ADV_IMAGE_SAVE_PATH);
        return viewList;
    }

    /**
     * 获取viewpager视图列表
     * 
     * @return
     */
    public ArrayList<ImageView> getViewList() {
        ArrayList<ImageView> viewList = new ArrayList<ImageView>();
        if (myList == null || myList.size() == 0) {
            AdvEntity entity = new AdvEntity();
            myList.add(entity);
            myEnabledDefaultImage = true;
        } else {
            myEnabledDefaultImage = false;
        }
        for (int i = 0; i < myList.size(); i++) {
            ImageView contentImageView = new ImageView(myActivity);
            // contentImageView.setBackgroundResource(R.drawable.img_default_480x290);

            if (!myEnabledDefaultImage) {
                AdvEntity advEntity = myList.get(i);
                // contentImageView.setOnClickListener(new View.OnClickListener() {
                // public void onClick(View v) {
                // // System.out.println("currentPosition=" + currentPosition);
                // // Uri uri =
                // // Uri.parse(myAdList.get((v.getTag()).getLinkUrl());
                // //跳转的是goods列表
                // // Uri uri = Uri.parse(myList.get((Integer) v.getTag())
                // // .getLinkUrl());
                // // Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                // // intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                // // myActivity.startActivity(intent);
                // Intent intent = new Intent(myActivity ,GoodsListActivity.class);
                // Bundle bundle = new Bundle();
                // bundle.putSerializable("advEntity", advEntity);
                // myActivity.startActivity(intent);
                // }
                // });
                /**
                 * 测试屏蔽
                 */
                // contentImageView.setOnClickListener(new ImageClick(advEntity));
            }
            viewList.add(contentImageView);
        }
        // syncImageLoader = new SyncImageLoadUtil(ConstantUtil.MAIN_ADV_IMAGE_SAVE_PATH);
        return viewList;
    }

    // 点击图片后的跳转 内部类
    class ImageClick implements OnClickListener {
        private AdvEntity advEntity;

        public ImageClick(AdvEntity advEntity) {
            this.advEntity = advEntity;
        }

        @Override
        public void onClick(View v) {
            // // TODO Auto-generated method stub
            // Intent intent = new Intent(myActivity ,GoodsListNoButtonActivity.class);
            // Bundle bundle = new Bundle();
            // bundle.putSerializable("advEntity", advEntity);
            // intent.putExtras(bundle);
            // intent.putExtra("FLAG", "adv");
            // myActivity.startActivity(intent);

        }

    }

    /**
     * 获取导航条
     * 
     * @param size
     * @param myActivity
     * @param ll
     * @return
     */
    public ImageView[] getPageViewDotImage(LinearLayout ll) {
        ImageView[] dotImageViews = new ImageView[myList.size()];
        for (int i = 0; i < myList.size(); i++) {
            ImageView dotImageView = new ImageView(myActivity);
            LinearLayout.LayoutParams dot_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            // dotImageView.setLayoutParams(new LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
            // LinearLayout.LayoutParams.WRAP_CONTENT));
            // dotImageView.setPadding(20, 0, 20, 0);

            // 设置导航点间距
            dot_params.leftMargin = 12;
            dotImageView.setLayoutParams(dot_params);
            dotImageViews[i] = dotImageView;
            if (i == 0) {
                dotImageViews[i].setBackgroundResource(R.drawable.dot_selected);
            } else {
                dotImageViews[i].setBackgroundResource(R.drawable.dot_noselected);
            }

            ll.addView(dotImageViews[i]);
        }
        return dotImageViews;
    }

    // 商品详情中使用
    public ImageView[] getPageViewDotImageByUrls(LinearLayout ll) {
        // TODO Auto-generated method stub
        ImageView[] dotImageViews = new ImageView[urls.size()];
        for (int i = 0; i < urls.size(); i++) {
            ImageView dotImageView = new ImageView(myActivity);
            LinearLayout.LayoutParams dot_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            // dotImageView.setLayoutParams(new LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
            // LinearLayout.LayoutParams.WRAP_CONTENT));
            // dotImageView.setPadding(20, 0, 20, 0);

            // 设置导航点间距
            dot_params.leftMargin = 12;
            dotImageView.setLayoutParams(dot_params);
            dotImageViews[i] = dotImageView;
            if (i == 0) {
                dotImageViews[i].setBackgroundResource(R.drawable.dot_selected);
            } else {
                dotImageViews[i].setBackgroundResource(R.drawable.dot_noselected);
            }

            ll.addView(dotImageViews[i]);
        }
        return dotImageViews;
    }

}
