package com.fsti.fjdicClient.activity.groupBuy;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.fsti.fjdicClient.ApplicationUtil;
import com.fsti.fjdicClient.R;
import com.fsti.fjdicClient.activity.BaseActivity;
import com.fsti.fjdicClient.activity.MagnifyImageviewActivity;
import com.fsti.fjdicClient.activity.home.HomeMainActivity;
import com.fsti.fjdicClient.activity.login.LoginActivity;
import com.fsti.fjdicClient.activity.more.MoreActivity;
import com.fsti.fjdicClient.activity.mycenter.MycenterMainActivity;
import com.fsti.fjdicClient.activity.search.SearchMainActivity;
import com.fsti.fjdicClient.activity.shoppingcart.ShoppingcartMainListActivity;
import com.fsti.fjdicClient.bean.AdvEntity;
import com.fsti.fjdicClient.bean.ExitApplication;
import com.fsti.fjdicClient.bean.GoodsDetailInfoEntity;
import com.fsti.fjdicClient.bean.GoodsEntity;
import com.fsti.fjdicClient.dao.BusinessDao;
import com.fsti.fjdicClient.util.GlobalVarUtil;
import com.fsti.fjdicClient.util.HttpUtil;
import com.fsti.fjdicClient.util.ImageLoaderHelper;
import com.fsti.fjdicClient.util.ViewUtil;
import com.fsti.fjdicClient.util.asyncUtil.CallEarliest;
import com.fsti.fjdicClient.util.asyncUtil.Callable;
import com.fsti.fjdicClient.util.asyncUtil.Callback;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

//商品详情
public class GroupBuyGoodsDetailActivity extends BaseActivity implements OnClickListener, OnGestureListener {
    // #...yyy
    private List<GoodsEntity>        goodsList   = new ArrayList<GoodsEntity>();
    private Button                   btnGoodsdetailCollect;
    private Button                   btgoodsdetailBack;
    private TextView                 tvgoodsdetailDiscribe;
    private TextView                 tvgoodsdetailPrice;
    private TextView                 tvgoodsdetailFreight;
    private TextView                 tvgoodsdetailLatelyselled;
    private TextView                 tvgoodsdetailPopularity;
    private TextView                 tvgoodsdetailGoodstype;
    private TextView                 tvgoodsdetailStocks;
    private GoodsDetailInfoEntity    goodsinfo;
    private String                   goodsID;
    private String                   imageUrl;
    private ImageView                ivLoading;

    private List<AdvEntity>          myAdvList   = new ArrayList<AdvEntity>();
    private ScheduledExecutorService scheduledExecutorService;
    private static ViewPager         myViewPager;
    // private SyncImageLoadUtil.OnImageLoadListener imageLoadListener;
    // public static SyncImageLoadUtil syncImageLoader = null;
    private LinearLayout             llViewpagerContainer;
    private LinearLayout             llyDotViewGroup;
    private FrameLayout              frameView;
    private static int               currentItem = 0;                           // 当前图片的索引号

    private LinearLayout             tohome;
    private LinearLayout             tosearch;
    private LinearLayout             toshoppingcart;
    private LinearLayout             tomycenter;
    private LinearLayout             tomore;
    private RelativeLayout           bottommenu;
    // 手势切换图片
    private ViewFlipper              viewflipImage;
    private int                      indexTop    = 0;
    private GestureDetector          detector    = new GestureDetector(this);
    // private SyncImageLoadUtil syncImageLoad = new SyncImageLoadUtil();
    private List<String>             urls        = new ArrayList<String>();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_groupbuy_goodsdetail);
        ExitApplication.getInstance().addActivity(this);

        Intent intent = this.getIntent();
        try {
            goodsID = intent.getStringExtra("goodsID");
        } catch (Exception e) {
            e.printStackTrace();
        }
        get_goodsinfo();
        init();

    }

    @Override
    public void bindEvent() {
        // TODO Auto-generated method stub
        btnGoodsdetailCollect.setOnClickListener(this);
        btgoodsdetailBack.setOnClickListener(this);
        tohome.setOnClickListener(this);
        tosearch.setOnClickListener(this);
        toshoppingcart.setOnClickListener(this);
        tomycenter.setOnClickListener(this);
        tomore.setOnClickListener(this);
    }

    @Override
    public void initValue() {
        // TODO Auto-generated method stub
        detector = new GestureDetector(this);
        detector.setIsLongpressEnabled(true);
        viewflipImage = (ViewFlipper) findViewById(R.id.viewflipper_main_container_groupbuygoodsdetail);

        btnGoodsdetailCollect = (Button) findViewById(R.id.btn_groupbuy_goodsdetail_collect);
        btgoodsdetailBack = (Button) findViewById(R.id.btn_groupbuy_goodsdetail_back);
        // BusinessDao.deleteCollectionTableData("1");

        LayoutInflater mInflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = mInflater.inflate(R.layout.layout_fn_viewpager_navigation, null);
        frameView = (FrameLayout) view.findViewById(R.id.viewpager_navigation_layout);
        // myViewPager = (ViewPager)view.findViewById(R.id.viewpager);
        // myViewPager.setVisibility(View.GONE);
        llyDotViewGroup = (LinearLayout) view.findViewById(R.id.dotViewGroup);
        llViewpagerContainer = (LinearLayout) findViewById(R.id.linear_groupbuy_goodsdetaile_viewpager_container);

        tvgoodsdetailDiscribe = (TextView) findViewById(R.id.tv_groupbuy_goodsdetail_describe);
        tvgoodsdetailPrice = (TextView) findViewById(R.id.tv_groupbuy_goodsdetail_price);
        tvgoodsdetailFreight = (TextView) findViewById(R.id.tv_groupbuy_goodsdetail_freight);
        tvgoodsdetailLatelyselled = (TextView) findViewById(R.id.tv_groupbuy_goodsdetail_latelyselled);
        tvgoodsdetailPopularity = (TextView) findViewById(R.id.tv_groupbuy_goodsdetail_popularity);
        tvgoodsdetailGoodstype = (TextView) findViewById(R.id.tv_groupbuy_goodsdetail_goodstype);
        tvgoodsdetailStocks = (TextView) findViewById(R.id.tv_groupbuy_goodsdetail_stocks);
        ivLoading = (ImageView) findViewById(R.id.iv_groupbuy_goodsdetail_loading);
        ivLoading.setVisibility(View.VISIBLE);

        View view2 = mInflater.inflate(R.layout.layout_item_bottom_menu, null);
        tohome = (LinearLayout) view2.findViewById(R.id.ll_tohome);
        tosearch = (LinearLayout) view2.findViewById(R.id.ll_tosearch);
        toshoppingcart = (LinearLayout) view2.findViewById(R.id.ll_toshoppingcart);
        tomycenter = (LinearLayout) view2.findViewById(R.id.ll_tomycenter);
        tomore = (LinearLayout) view2.findViewById(R.id.ll_tomore);
        bottommenu = (RelativeLayout) findViewById(R.id.rl_groupbuy_gooddetail_bottommenu);
        bottommenu.addView(view2);
        ViewGroup.LayoutParams lp = view2.getLayoutParams();
        lp.width = lp.FILL_PARENT;
        view2.setLayoutParams(lp);
        ViewUtil.addLoadingAnimation(ApplicationUtil.myContext, ivLoading);
    }

    private void updata_view() {
        ViewUtil.removeLoadingAnimation(ivLoading);
        if (goodsinfo != null) {
            String url = goodsinfo.getImageUrl();
            String[] urlArray = url.split("※");
            for (int i = 0; i < urlArray.length; i++) {
                urls.add(urlArray[i]);
            }

            if (urlArray.length == 1) {
                urls.add(urlArray[0]);
            }
            tvgoodsdetailDiscribe.setText(goodsinfo.getName());
            tvgoodsdetailPrice.setText(String.valueOf(goodsinfo.getPrice()) + "元");
            String freighttype = goodsinfo.getFreightType();
            String freightprice = goodsinfo.getFreightPrice();
            System.out.println("------------" + freighttype);
            System.out.println("------------" + freightprice);
            String freight = "";
            int i = -1;
            int j = -1;
            freighttype = freighttype + "※";
            freightprice = freightprice + "※";
            while (((i = freighttype.indexOf("※")) != -1) && ((j = freightprice.indexOf("※")) != -1)) {
                freight = freight + freighttype.substring(0, i) + freightprice.substring(0, j) + ",";
                freighttype = freighttype.substring(i + 1, freighttype.length());
                freightprice = freightprice.substring(j + 1, freightprice.length());
            }
            freight = freight.substring(0, freight.length() - 1);
            initImages();
            tvgoodsdetailFreight.setText(freight);
            tvgoodsdetailLatelyselled.setText(String.valueOf(goodsinfo.getSelledCount()) + "件");
            tvgoodsdetailPopularity.setText(String.valueOf(goodsinfo.getPopularity()));
            // 团购专用
            tvgoodsdetailGoodstype.setText("团购");

            tvgoodsdetailStocks.setText(String.valueOf(goodsinfo.getStorageCount()));
        }

    }

    public void initImages() {
        int advCounts = urls.size();
        if (urls.size() > 0) {
            ImageView[] iv = new ImageView[advCounts];
            for (int i = 0; i < advCounts; i++) {
                iv[i] = new ImageView(this);
                iv[i].setScaleType(ImageView.ScaleType.FIT_CENTER);
                viewflipImage.addView(iv[i]);
                indexTop = i;
                ImageLoaderHelper.displayImage(iv[i], urls.get(i));
                // syncImageLoad.displayImage(urls.get(i), iv[i], this);
                iv[i].setOnClickListener(new ImageOnClickListener(i));
            }
        }

    }

    class ImageOnClickListener implements OnClickListener {
        private int i;

        public ImageOnClickListener(int i) {
            // TODO Auto-generated constructor stub
            this.i = i;
        }

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            if (urls.size() == 2) {
                urls.get(0).equals(urls.get(1));
                urls.remove(1);
                MagnifyImageviewActivity.StartMagnifyImageviewActivity(GroupBuyGoodsDetailActivity.this, urls, 0);
            } else {
                MagnifyImageviewActivity.StartMagnifyImageviewActivity(GroupBuyGoodsDetailActivity.this, urls, i);
            }
        }

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
        case R.id.btn_groupbuy_goodsdetail_collect:
            if (GlobalVarUtil.account.getUID() == null || GlobalVarUtil.account.getUID().equals("")) {
                Toast.makeText(GroupBuyGoodsDetailActivity.this, "收藏失败，请先登陆系统", 1).show();
                Intent intentTolongin = new Intent(GroupBuyGoodsDetailActivity.this, LoginActivity.class);
                startActivity(intentTolongin);
            } else {
                insert_collect();
            }
            break;
        case R.id.btn_groupbuy_goodsdetail_back:
            this.finish();
            break;
        case R.id.ll_tohome:
            Intent intenthome = new Intent(GroupBuyGoodsDetailActivity.this, HomeMainActivity.class);
            startActivity(intenthome);
            ExitApplication.getInstance().exit();
            break;
        case R.id.ll_tosearch:
            Intent intentsearch = new Intent(GroupBuyGoodsDetailActivity.this, SearchMainActivity.class);
            startActivity(intentsearch);
            ExitApplication.getInstance().exit();
            break;
        case R.id.ll_toshoppingcart:
            Intent intentshoppingcart = new Intent(GroupBuyGoodsDetailActivity.this, ShoppingcartMainListActivity.class);
            startActivity(intentshoppingcart);
            if (GlobalVarUtil.account.getUID() == null) {
                Intent intentTolongin = new Intent(GroupBuyGoodsDetailActivity.this, LoginActivity.class);
                startActivity(intentTolongin);
            }
            // ExitApplication.getInstance().exit();//购物车可以返回
            break;
        case R.id.ll_tomycenter:
            Intent intentmycenter = new Intent(GroupBuyGoodsDetailActivity.this, MycenterMainActivity.class);
            startActivity(intentmycenter);
            if (GlobalVarUtil.account.getUID() == null) {
                Intent intentTolongin = new Intent(GroupBuyGoodsDetailActivity.this, LoginActivity.class);
                startActivity(intentTolongin);
            }
            ExitApplication.getInstance().exit();
            break;
        case R.id.ll_tomore:
            Intent intentmore = new Intent(GroupBuyGoodsDetailActivity.this, MoreActivity.class);
            startActivity(intentmore);
            ExitApplication.getInstance().exit();
            break;
        }

    }

    // 添加商品详情信息
    private void insert_collect() {
        this.doAsync(new CallEarliest<Object>() {
            @Override
            public void onCallEarliest() throws Exception {
                // TODO Auto-generated method stub

            }

        }, new Callable<Object>() {
            @Override
            public String call() {

                String reqUrl = getString(R.string.insertCollectGoods_http);

                Map<String, Object> parameters = new LinkedHashMap<String, Object>();

                parameters.put("UID", GlobalVarUtil.account.getUID());
                parameters.put("ID", goodsinfo.getID());
                parameters.put("type", goodsinfo.getType());

                String jsonStr = "";
                try {
                    jsonStr = HttpUtil.postData(reqUrl, parameters, GlobalVarUtil.ENCODING);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    return "-1";
                }

                return jsonStr;
            }
        }, new Callback<String>() {
            @Override
            public void onCallback(String jsonStr) {
                // TODO Auto-generated method stub

                System.out.println("code jsonStr=" + jsonStr);

                if (jsonStr.equals("-1")) {
                    ViewUtil.showToast(myContext, myContext.getString(R.string.net_exception));

                } else {
                    if (jsonStr.equals("1")) {
                        Toast.makeText(GroupBuyGoodsDetailActivity.this, "收藏失败，该商品已在收藏中", 1).show();
                    } else {

                        if (BusinessDao.insertCollectionTableData(goodsinfo.getID(), goodsinfo.getType())) {
                            Toast.makeText(GroupBuyGoodsDetailActivity.this, "收藏成功", 1).show();
                        } else {
                            Toast.makeText(GroupBuyGoodsDetailActivity.this, "收藏失败，该商品已在收藏中", 1).show();
                        }
                    }

                }
            }
        });
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        viewflipImage.setAutoStart(true);
        viewflipImage.setFlipInterval(6000);
        super.onResume();

    }

    // 获取商品详情

    private void get_goodsinfo() {
        this.doAsync(new CallEarliest<Object>() {
            @Override
            public void onCallEarliest() throws Exception {
                // TODO Auto-generated method stub

            }

        }, new Callable<Object>() {
            @Override
            public String call() {

                String reqUrl = getString(R.string.getGoodsDetail_http);

                Map<String, Object> parameters = new LinkedHashMap<String, Object>();

                parameters.put("goodsID", goodsID);
                parameters.put("type", "2");

                String jsonStr = "";
                try {
                    jsonStr = HttpUtil.postData(reqUrl, parameters, GlobalVarUtil.ENCODING);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    return "-1";
                }

                return jsonStr;
            }
        }, new Callback<String>() {
            @Override
            public void onCallback(String jsonStr) {
                // TODO Auto-generated method stub

                System.out.println("code jsonStr=" + jsonStr);

                // if(jsonStr.equals("")){ get_goodsinfo(goodsEntity);}
                // else
                if (jsonStr.equals("-1")) {
                    ViewUtil.showToast(myContext, myContext.getString(R.string.net_exception));
                } else {
                    try {

                        JSONObject jsonObject = new JSONObject(jsonStr);

                        String jsonList = jsonStr.trim();
                        Type listType = new TypeToken<GoodsDetailInfoEntity>() {
                        }.getType();
                        goodsinfo = new Gson().fromJson(jsonList, listType);

                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    updata_view();

                }

            }

        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // System.out.println("h activity onTouchEvent");
        // System.out.println("LinearLayout::onTouchEvent::action=" + Tool.getTouchAction(event.getAction()));
        // return super.onTouchEvent(event);
        // return false;
        return detector.onTouchEvent(event);

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        // TODO Auto-generated method stub
        detector.onTouchEvent(ev);
        // System.out.println("dispatchTouchEvent...1");
        if (super.dispatchTouchEvent(ev)) {
            // System.out.println("dispatchTouchEvent if ++ true");
            return true;
        } else {
            // System.out.println("dispatchTouchEvent else += false");
            return false;
        }
    }

    @Override
    public boolean onDown(MotionEvent arg0) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        // TODO Auto-generated method stub
        if (e1.getX() - e2.getX() > 120) {
            this.viewflipImage.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_left_in));
            this.viewflipImage.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_left_out));
            this.viewflipImage.showNext();
            if (urls.size() > 0) {
                indexTop++;
                indexTop = Math.abs(indexTop % urls.size());
            }
            viewflipImage.setAutoStart(false);
            viewflipImage.setAutoStart(true);
            viewflipImage.setFlipInterval(5000);
            return true;
        } else if (e1.getX() - e2.getX() < -120) {
            this.viewflipImage.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_right_in));
            this.viewflipImage.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_right_out));
            this.viewflipImage.showPrevious();
            if (urls.size() > 0) {
                indexTop--;
                indexTop = Math.abs(indexTop % urls.size());
            }
            viewflipImage.setAutoStart(false);
            viewflipImage.setAutoStart(true);
            viewflipImage.setFlipInterval(5000);
            return true;
        }
        viewflipImage.setAutoStart(false);
        viewflipImage.setAutoStart(true);
        viewflipImage.setFlipInterval(5000);
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        // TODO Auto-generated method stub
        // isClick = false;
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        // TODO Auto-generated method stub
        return false;
    }

}
