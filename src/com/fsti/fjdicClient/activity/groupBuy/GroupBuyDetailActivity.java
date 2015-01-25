package com.fsti.fjdicClient.activity.groupBuy;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
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
import android.widget.ScrollView;
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
//import com.fsti.fjdicClient.activity.shoppingMall.CommentsListActivity;
//import com.fsti.fjdicClient.activity.shoppingMall.GoodsDetailActivity;
//import com.fsti.fjdicClient.activity.shoppingMall.ShoppingmallGoodsDetailActivity;
import com.fsti.fjdicClient.activity.shoppingcart.ShoppingcartMainListActivity;
import com.fsti.fjdicClient.adapter.AdvViewPagerAdapter;
import com.fsti.fjdicClient.bean.AdvEntity;
import com.fsti.fjdicClient.bean.ExitApplication;
import com.fsti.fjdicClient.bean.GoodsDetailInfoEntity;
import com.fsti.fjdicClient.bean.GoodsEntity;
import com.fsti.fjdicClient.bean.ShoppingcartInfoEntity;
import com.fsti.fjdicClient.component.AdvertiseViewPagerService;
import com.fsti.fjdicClient.dao.BusinessDao;
import com.fsti.fjdicClient.listener.ViewPagerChangeListener;
import com.fsti.fjdicClient.util.GlobalVarUtil;
import com.fsti.fjdicClient.util.HttpUtil;
import com.fsti.fjdicClient.util.ImageLoaderHelper;
import com.fsti.fjdicClient.util.ViewUtil;
import com.fsti.fjdicClient.util.asyncUtil.CallEarliest;
import com.fsti.fjdicClient.util.asyncUtil.Callable;
import com.fsti.fjdicClient.util.asyncUtil.Callback;
import com.fsti.fjdicClient.util.timecount.Groupbuy_lasttime;
import com.fsti.fjdicClient.util.timecount.Timecount;
import com.fsti.fjdicClient.util.timecount.Timedowncount;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 生活团 团购详情
 * 
 * @author
 */

public class GroupBuyDetailActivity extends BaseActivity implements OnClickListener, OnGestureListener {

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        if (thread1 != null && thread1.isAlive()) {
            thread1.interrupt();
        }
        if (thread2 != null && thread2.isAlive()) {
            thread2.interrupt();
        }
        scheduledExecutorService.shutdown();
        super.onPause();
    }

    private Thread                   thread1;
    private Thread                   thread2;
    // 从广告处进入该页面
    private AdvEntity                advEntity;
    private List<GoodsEntity>        goodsList   = new ArrayList<GoodsEntity>();

    // 手势切换图片
    private ViewFlipper              viewflipImage;
    private int                      indexTop    = 0;
    private GestureDetector          detector    = new GestureDetector(this);
    // private SyncImageLoadUtil syncImageLoad = new SyncImageLoadUtil();
    private boolean                  isClick     = true;
    private List<String>             urls        = new ArrayList<String>();

    private Button                   btgroupbuyBack;
    private Button                   btgroupbuyCollect;
    private TextView                 tvgroupbuyNowprice;
    private TextView                 tvgroupbuyOldprice;
    private Button                   btgroupbuyBuy;
    private Button                   btn_groupbuy_groupbuydetail_add;
    private TextView                 tvgroupbuyGoodsname;
    private TextView                 tvgroupbuyBuyercount;
    private static TextView          tvgroupbuyTimeday;
    private static TextView          tvgroupbuyTimehour;
    private static TextView          tvgroupbuyTimeminute;
    private static TextView          tvgroupbuyTimesecond;
    private RelativeLayout           llgroupbuyGoodsdetail;
    private RelativeLayout           llgroupbuySellerdetail;
    private RelativeLayout           llgroupbuycommentsdetail;
    private GoodsEntity              goodsEntity;
    private GoodsDetailInfoEntity    goodsinfo;
    private ImageView                ivLoading;
    private ScrollView               srcollMain;
    private TextView                 tvLoaderror;

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
    private boolean                  isAdv       = false;
    private boolean                  isGetInfo   = false;

    // 从订单处来
    private int                      type        = 0;
    private String                   goodsID     = "";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_groupbuy_groupbuydetail);
        ExitApplication.getInstance().addActivity(this);
        ivLoading = (ImageView) findViewById(R.id.iv_groupbuydetail_loading);
        ivLoading.setVisibility(View.VISIBLE);
        ViewUtil.addLoadingAnimation(ApplicationUtil.myContext, ivLoading);
        Intent intent = getIntent();
        if (HomeMainActivity.FLAGTOGROUP.equals("ADV")) {
            isAdv = true;
            advEntity = (AdvEntity) intent.getSerializableExtra("advEntity");
            init();

        }
        if (HomeMainActivity.FLAGTOGROUP.equals("ORDER")) {
            goodsID = intent.getExtras().getString("goodsID");
            type = intent.getExtras().getInt("type");
            init();
        } else {
            goodsEntity = (GoodsEntity) intent.getSerializableExtra("goodsEntity");
            init();
            get_goodsinfo();
        }
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        new Timedowncount().countdowntimer_cancle();
        super.onDestroy();
    }

    /*
     * @Override protected void onPause() { // TODO Auto-generated method stub super.onPause(); scheduledExecutorService.shutdown(); }
     */

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        viewflipImage.setAutoStart(true);
        viewflipImage.setFlipInterval(6000);
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        // 当Activity显示出来后，每两秒钟切换一次图片显示
        if (myAdvList != null) {
            scheduledExecutorService.scheduleAtFixedRate(new ScrollTask(), 1, 3, TimeUnit.SECONDS);
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
                MagnifyImageviewActivity.StartMagnifyImageviewActivity(GroupBuyDetailActivity.this, urls, 0);
            } else {
                MagnifyImageviewActivity.StartMagnifyImageviewActivity(GroupBuyDetailActivity.this, urls, i);
            }
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
                // syncImageLoad.displayImage(urls.get(i), iv[i], this);
                ImageLoaderHelper.displayImage(iv[i], urls.get(i));
                iv[i].setOnClickListener(new ImageOnClickListener(i));
            }
        }

    }

    public void initViewPager() {
        AdvertiseViewPagerService advService = new AdvertiseViewPagerService(myViewPager, myAdvList, this);
        ArrayList<ImageView> viewList = advService.getViewList();
        ImageView[] imageArray = advService.getPageViewDotImage(llyDotViewGroup);
        // syncImageLoader = new SyncImageLoadUtil(GlobalVarUtil.MAIN_ADV_IMAGE_SAVE_PATH);
        // imageLoadListener = advService.imageLoadListener;
        myViewPager.setAdapter(new AdvViewPagerAdapter(viewList, myAdvList));
        myViewPager.setOnPageChangeListener(new ViewPagerChangeListener(imageArray));
        llViewpagerContainer.addView(frameView);
        try {
            scheduledExecutorService.scheduleAtFixedRate(new ScrollTask(), 1, 3, TimeUnit.SECONDS);
        } catch (RejectedExecutionException e) {
            System.out.println("线程池溢出了");
        }
    }

    /**
     * 换行切换任务
     * 
     * @author Administrator
     */
    public class ScrollTask implements Runnable {

        public void run() {
            synchronized (myViewPager) {
                System.out.println("currentItem: " + currentItem);
                currentItem = (currentItem + 1) % myAdvList.size();
                handler.obtainMessage().sendToTarget(); // 通过Handler切换图片
            }
        }
    }

    // 切换当前显示的图片
    private Handler handler = new Handler() {
                                public void handleMessage(android.os.Message msg) {
                                    myViewPager.setCurrentItem(currentItem);// 切换当前显示的图片
                                };
                            };

    @Override
    public void bindEvent() {

        btgroupbuyBack.setOnClickListener(this);
        btgroupbuyCollect.setOnClickListener(this);
        btgroupbuyBuy.setOnClickListener(this);
        btn_groupbuy_groupbuydetail_add.setOnClickListener(this);
        llgroupbuyGoodsdetail.setOnClickListener(this);
        llgroupbuySellerdetail.setOnClickListener(this);
        llgroupbuycommentsdetail.setOnClickListener(this);
        tohome.setOnClickListener(this);
        tosearch.setOnClickListener(this);
        toshoppingcart.setOnClickListener(this);
        tomycenter.setOnClickListener(this);
        tomore.setOnClickListener(this);
    }

    @Override
    public void initValue() {

        detector = new GestureDetector(this);
        detector.setIsLongpressEnabled(true);
        viewflipImage = (ViewFlipper) findViewById(R.id.viewflipper_main_container_groupbuy);

        btgroupbuyBack = (Button) findViewById(R.id.btn_groupbuy_groupbuydetail_back);
        btgroupbuyCollect = (Button) findViewById(R.id.btn_groupbuy_groupbuydetail_collect);
        tvgroupbuyNowprice = (TextView) findViewById(R.id.tv_groupbuy_groupbuydetail_nowprice);
        tvgroupbuyOldprice = (TextView) findViewById(R.id.tv_groupbuy_groupbuydetail_oldprice);
        btgroupbuyBuy = (Button) findViewById(R.id.btn_groupbuy_groupbuydetail_buy);
        btn_groupbuy_groupbuydetail_add = (Button) findViewById(R.id.btn_groupbuy_groupbuydetail_add);
        tvgroupbuyGoodsname = (TextView) findViewById(R.id.tv_groupbuy_groupbuydetail_goodsname);
        tvgroupbuyBuyercount = (TextView) findViewById(R.id.tv_groupbuy_groupbuydetail_buyercount);
        tvgroupbuyTimeday = (TextView) findViewById(R.id.tv_groupbuy_groupbuydetail_timeday);
        tvgroupbuyTimehour = (TextView) findViewById(R.id.tv_groupbuy_groupbuydetail_timehour);
        tvgroupbuyTimeminute = (TextView) findViewById(R.id.tv_groupbuy_groupbuydetail_timeminute);
        tvgroupbuyTimesecond = (TextView) findViewById(R.id.tv_groupbuy_groupbuydetail_timesecond);
        llgroupbuyGoodsdetail = (RelativeLayout) findViewById(R.id.ll_groupbuy_groupbuydetail_goodsdetail);
        llgroupbuySellerdetail = (RelativeLayout) findViewById(R.id.ll_groupbuy_groupbuydetail_sellerdetail);
        llgroupbuycommentsdetail = (RelativeLayout) findViewById(R.id.ll_groupbuy_groupbuydetail_commentsdetail);

        srcollMain = (ScrollView) findViewById(R.id.scroll_groupdetail);
        tvLoaderror = (TextView) findViewById(R.id.tv_group_loaderror);
        LayoutInflater mInflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = mInflater.inflate(R.layout.layout_fn_viewpager_navigation, null);
        frameView = (FrameLayout) view.findViewById(R.id.viewpager_navigation_layout);
        // myViewPager = (ViewPager)view.findViewById(R.id.viewpager);
        // myViewPager.setVisibility(View.GONE);
        llyDotViewGroup = (LinearLayout) view.findViewById(R.id.dotViewGroup);
        llViewpagerContainer = (LinearLayout) findViewById(R.id.ll_groupbuy_groupbuydetail_viewpager_container);

        View view2 = mInflater.inflate(R.layout.layout_item_bottom_menu, null);
        tohome = (LinearLayout) view2.findViewById(R.id.ll_tohome);
        tosearch = (LinearLayout) view2.findViewById(R.id.ll_tosearch);
        toshoppingcart = (LinearLayout) view2.findViewById(R.id.ll_toshoppingcart);
        tomycenter = (LinearLayout) view2.findViewById(R.id.ll_tomycenter);
        tomore = (LinearLayout) view2.findViewById(R.id.ll_tomore);
        bottommenu = (RelativeLayout) findViewById(R.id.rl_groupbuy_groupbuydetail_bottommenu);
        bottommenu.addView(view2);
        ViewGroup.LayoutParams lp = view2.getLayoutParams();
        lp.width = lp.FILL_PARENT;
        view2.setLayoutParams(lp);
        //
        if (HomeMainActivity.FLAGTOGROUP.equals("ADV")) {
            get_goodsEntity();
        } else if (HomeMainActivity.FLAGTOGROUP.equals("ORDER")) {
            get_goodsinfo();
        } else {
            tvgroupbuyGoodsname.setText(goodsEntity.getName());
            tvgroupbuyNowprice.setText(goodsEntity.getDiscountedPrice());

            if ("".equals(goodsEntity.getOriginalCost()) || goodsEntity.getOriginalCost() == null) {
                tvgroupbuyOldprice.setText("");
            } else {
                tvgroupbuyOldprice.setText("原价：" + goodsEntity.getOriginalCost());
            }

            tvgroupbuyOldprice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            tvgroupbuyBuyercount.setText(String.valueOf(goodsEntity.getSelledCount()) + "人");
        }

    }

    private void get_goodsEntity() {
        // TODO Auto-generated method stub
        this.doAsync(new CallEarliest<Object>() {
            @Override
            public void onCallEarliest() throws Exception {
                // TODO Auto-generated method stub

            }

        }, new Callable<Object>() {
            @Override
            public String call() {
                thread1 = Thread.currentThread();
                String reqUrl = getString(R.string.getadvGoodsList_http);
                Map<String, Object> parameters = new LinkedHashMap<String, Object>();

                parameters.put("advID", advEntity.getAdvID());
                String jsonStr = "";
                try {
                    jsonStr = HttpUtil.postData(reqUrl, parameters, GlobalVarUtil.ENCODING);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    HomeMainActivity.FLAGTOGROUP = "";
                    e.printStackTrace();
                    return "-1";
                }

                return jsonStr;
            }
        }, new Callback<String>() {
            @Override
            public void onCallback(String jsonStr) {
                // TODO Auto-generated method stub
                thread2 = Thread.currentThread();
                if (jsonStr.equals("-1")) {
                    ViewUtil.showToast(myContext, myContext.getString(R.string.net_exception));
                    isAdv = false;
                } else {
                    try {
                        JSONObject jsonObj;
                        jsonObj = new JSONObject(jsonStr);
                        String jsonList = "";
                        jsonList = jsonObj.getString("goodsList");
                        Type typeObj = new TypeToken<List<GoodsEntity>>() {
                        }.getType();
                        goodsList = new Gson().fromJson(jsonList, typeObj);
                        goodsEntity = goodsList.get(0);
                        isAdv = false;
                        AdvViewPagerAdapter.FLAGTOSHOPPING = "";
                        tvgroupbuyGoodsname.setText(goodsEntity.getName());
                        tvgroupbuyNowprice.setText(goodsEntity.getDiscountedPrice());
                        tvgroupbuyOldprice.setText("原价：" + goodsEntity.getOriginalCost());
                        tvgroupbuyOldprice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                        tvgroupbuyBuyercount.setText(String.valueOf(goodsEntity.getSelledCount()) + "人");
                        get_goodsinfo();
                    } catch (JSONException e) {
                        isAdv = false;
                        // TODO Auto-generated catch block
                        AdvViewPagerAdapter.FLAGTOSHOPPING = "";
                        e.printStackTrace();
                    }

                }
            }
        });
    }

    private void reload_view() {// 更新页面显示
        // 倒计时
        Date date1 = null;
        Date date2 = null;
        if (goodsinfo != null) {
            System.out.println(goodsinfo.getExpireDate() + "==" + goodsinfo.getNowDate());

            String url = goodsEntity.getImageUrl();
            String[] urlArray = url.split("※");
            goodsinfo.setImageUrl(urlArray[0]);// 在购物车，后续页面显示第一张图片
            for (int i = 0; i < urlArray.length; i++) {
                // urls.add("http://yads.gnway.net:81/" + urlArray[i]);
                // urls.add(this.getString(R.string.base_url) + urlArray[i]);
                urls.add(urlArray[i]);
            }
            if (urlArray.length == 1) {
                urls.add(urlArray[0]);
            }
            initImages();
        }

        try {
            if (goodsinfo != null) {
                date1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(goodsinfo.getExpireDate());
                date2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(goodsinfo.getNowDate());
            }
        } catch (java.text.ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        int seconds = 0;
        if (date1 == null || date2 == null) {

        } else {
            seconds = new Timecount().timetoseconds(date1) - new Timecount().timetoseconds(date2);

        }

        if (seconds > 0) {
            new Timedowncount(date1, date2);
            tvgroupbuyTimeday.setText(String.valueOf(new Groupbuy_lasttime().get_day_num(seconds)));
            tvgroupbuyTimehour.setText(String.valueOf(new Groupbuy_lasttime().get_hour_num(seconds)));
            tvgroupbuyTimeminute.setText(String.valueOf(new Groupbuy_lasttime().get_minute_num(seconds)));
            tvgroupbuyTimesecond.setText(String.valueOf(new Groupbuy_lasttime().get_second_num(seconds)));

        } else {
            tvgroupbuyTimeday.setText("00");
            tvgroupbuyTimehour.setText("00");
            tvgroupbuyTimeminute.setText("00");
            tvgroupbuyTimesecond.setText("00");
        }

    }

    public void tvupdata(int seconds) {
        String sday = String.valueOf(new Groupbuy_lasttime().get_day_num(seconds));
        int ihour = new Groupbuy_lasttime().get_hour_num(seconds);
        int iminute = new Groupbuy_lasttime().get_minute_num(seconds);
        int isecond = new Groupbuy_lasttime().get_second_num(seconds);
        String shour = "0";
        String sminute = "0";
        String ssecond = "0";
        if (ihour < 10) {
            shour = "0" + ihour;
        } else {
            shour = ihour + "";
        }
        if (iminute < 10) {
            sminute = "0" + iminute;
        } else {
            sminute = iminute + "";
        }
        if (isecond < 10) {
            ssecond = "0" + isecond;
        } else {
            ssecond = isecond + "";
        }

        tvgroupbuyTimeday.setText(sday);
        tvgroupbuyTimehour.setText(shour);
        tvgroupbuyTimeminute.setText(sminute);
        tvgroupbuyTimesecond.setText(ssecond);
    }

    // %...y
    @Override
    public void onClick(View arg0) {
        // TODO Auto-generated method stub

        switch (arg0.getId()) {
        case R.id.btn_groupbuy_groupbuydetail_back:
            // Intent intent1 = new Intent(this,GroupBuyMainListActivity.class);
            // startActivity(intent1);
            this.finish();
            break;
        case R.id.btn_groupbuy_groupbuydetail_collect:
            // Intent intent2 = new Intent(this,GroupBuyMainListActivity.class);
            // startActivity(intent2);
            if (!isAdv) {
                if (GlobalVarUtil.account.getUID() == null || GlobalVarUtil.account.getUID().equals("")) {
                    Toast.makeText(GroupBuyDetailActivity.this, "收藏失败，请先登陆系统", 1).show();

                    Intent intentTolongin = new Intent(GroupBuyDetailActivity.this, LoginActivity.class);
                    startActivity(intentTolongin);
                } else {
                    insert_collect();
                    insert_sellerinfo();
                }
            } else {
                Toast.makeText(myContext, "页面初始化中，请稍候。", 1).show();
            }

            break;
        case R.id.btn_groupbuy_groupbuydetail_buy:
            if (!isAdv) {
                if (GlobalVarUtil.account.getUID() == null || GlobalVarUtil.account.getUID().equals("")) {
                    Toast.makeText(GroupBuyDetailActivity.this, "请先登陆系统", 1).show();

                    Intent intentTolongin = new Intent(GroupBuyDetailActivity.this, LoginActivity.class);
                    startActivity(intentTolongin);
                } else {
                    Intent intent3 = new Intent(this, SubmitOrderActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("goodsEntity", goodsEntity);
                    bundle.putSerializable("goodsinfo", goodsinfo);
                    intent3.putExtras(bundle);
                    startActivity(intent3);
                }
            } else {
                Toast.makeText(myContext, "页面初始化中，请稍候。", 1).show();
            }
            break;
        case R.id.btn_groupbuy_groupbuydetail_add:// 加入购物车
            if (!isAdv) {
                if (GlobalVarUtil.account.getUID() == null || GlobalVarUtil.account.getUID().equals("")) {
                    Toast.makeText(GroupBuyDetailActivity.this, "请先登陆系统", 1).show();

                    Intent intentTolongin = new Intent(GroupBuyDetailActivity.this, LoginActivity.class);
                    startActivity(intentTolongin);
                } else {
                    ShoppingcartInfoEntity carinfo = new ShoppingcartInfoEntity();
                    carinfo.setFID(goodsinfo.getFID());
                    carinfo.setCounts(1);
                    carinfo.setID(goodsinfo.getID());
                    carinfo.setUID(GlobalVarUtil.account.getUID());
                    carinfo.setShopsID(goodsinfo.getShopsID());
                    carinfo.setType(goodsinfo.getType());
                    carinfo.setName(goodsinfo.getName());
                    carinfo.setIntro(goodsinfo.getIntro());
                    carinfo.setPrice(goodsinfo.getPrice());
                    carinfo.setFreightTypeSelect(0);
                    carinfo.setShopsName(goodsinfo.getName());
                    carinfo.setFreight(goodsinfo.getFreightPrice());
                    carinfo.setFreightType(goodsinfo.getFreightType());
                    carinfo.setImageUrl(goodsinfo.getImageUrl());
                    carinfo.setType(goodsinfo.getType());
                    BusinessDao.insertShoppingcartTableData(carinfo);
                    // Intent intent3 = new Intent(this, SubmitOrderActivity.class);
                    // Bundle bundle = new Bundle();
                    // bundle.putSerializable("goodsEntity", goodsEntity);
                    // bundle.putSerializable("goodsinfo", goodsinfo);
                    // intent3.putExtras(bundle);
                    // startActivity(intent3);
                }
            } else {
                Toast.makeText(myContext, "页面初始化中，请稍候。", 1).show();
            }
            break;
        case R.id.ll_groupbuy_groupbuydetail_goodsdetail:
            if (!isAdv) {
                if (!isGetInfo) {
                    if (goodsinfo != null) {
                        if (goodsinfo.getIntro().equals("") || goodsinfo.getIntro() == null) {
                            Toast.makeText(GroupBuyDetailActivity.this, "很抱歉，该商品暂无详情。", 1).show();
                        } else {
                            Intent intent4 = new Intent(this, GroupBuyGoodsDetailActivitynew.class);
                            intent4.putExtra("goodsID", goodsinfo.getID());
                            startActivity(intent4);
                        }
                    } else {
                        Toast.makeText(GroupBuyDetailActivity.this, "很抱歉，该商品暂无详情。", 1).show();
                    }

                } else {
                    Toast.makeText(GroupBuyDetailActivity.this, "正在获取详情，请稍候。", 1).show();
                }
                /*
                 * Intent intent4 = new Intent(this,GroupBuyGoodsDetailActivity.class); intent4.putExtra("goodsID", goodsEntity.getID()); startActivity(intent4);
                 */
            } else {
                Toast.makeText(myContext, "页面初始化中，请稍候。", 1).show();
            }

            break;
        case R.id.ll_groupbuy_groupbuydetail_commentsdetail:
            if (!isAdv) {
                // Intent intentToCommentsList = new Intent(this, CommentsListActivity.class);
                // intentToCommentsList.putExtra("ID", goodsEntity.getID());
                // intentToCommentsList.putExtra("type", goodsEntity.getType());
                // startActivity(intentToCommentsList);
            } else {
                Toast.makeText(myContext, "页面初始化中，请稍候。", 1).show();
            }
            break;
        case R.id.ll_groupbuy_groupbuydetail_sellerdetail:
            if (!isAdv) {
                Intent intent5 = new Intent(this, SellerInformationActivity.class);
                intent5.putExtra("goodsID", goodsEntity.getID());
                intent5.putExtra("type", String.valueOf(goodsEntity.getType()));
                startActivity(intent5);
            } else {
                Toast.makeText(myContext, "页面初始化中，请稍候。", 1).show();
            }
            break;
        case R.id.ll_tohome:
            Intent intenthome = new Intent(GroupBuyDetailActivity.this, HomeMainActivity.class);
            startActivity(intenthome);
            ExitApplication.getInstance().exit();
            break;
        case R.id.ll_tosearch:
            Intent intentsearch = new Intent(GroupBuyDetailActivity.this, SearchMainActivity.class);
            startActivity(intentsearch);
            ExitApplication.getInstance().exit();
            break;
        case R.id.ll_toshoppingcart:
            Intent intentshoppingcart = new Intent(GroupBuyDetailActivity.this, ShoppingcartMainListActivity.class);
            startActivity(intentshoppingcart);
            if (GlobalVarUtil.account.getUID() == null) {
                Intent intentTolongin = new Intent(GroupBuyDetailActivity.this, LoginActivity.class);
                startActivity(intentTolongin);

            }
            // ExitApplication.getInstance().exit();//购物车可以返回
            break;
        case R.id.ll_tomycenter:
            Intent intentmycenter = new Intent(GroupBuyDetailActivity.this, MycenterMainActivity.class);
            startActivity(intentmycenter);
            if (GlobalVarUtil.account.getUID() == null) {
                Intent intentTolongin = new Intent(GroupBuyDetailActivity.this, LoginActivity.class);
                startActivity(intentTolongin);

            }
            ExitApplication.getInstance().exit();
            break;
        case R.id.ll_tomore:
            Intent intentmore = new Intent(GroupBuyDetailActivity.this, MoreActivity.class);
            startActivity(intentmore);
            ExitApplication.getInstance().exit();
            break;

        }

    }

    // 加入收藏夹
    private void insert_collect() {
        this.doAsync(new CallEarliest<Object>() {
            @Override
            public void onCallEarliest() throws Exception {
                // TODO Auto-generated method stub

            }

        }, new Callable<Object>() {
            @Override
            public String call() {
                thread1 = Thread.currentThread();
                String reqUrl = getString(R.string.insertCollectGoods_http);

                Map<String, Object> parameters = new LinkedHashMap<String, Object>();

                parameters.put("UID", GlobalVarUtil.account.getUID());
                parameters.put("ID", goodsEntity.getID());
                parameters.put("type", goodsEntity.getType());

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

                thread2 = Thread.currentThread();
                System.out.println("code jsonStr=" + jsonStr);

                if (jsonStr.equals("-1")) {
                    ViewUtil.showToast(myContext, myContext.getString(R.string.net_exception));

                } else {
                    if (jsonStr.equals("1")) {
                        Toast.makeText(GroupBuyDetailActivity.this, "收藏失败，该商品已在收藏中", 1).show();
                    } else {

                        if (BusinessDao.insertCollectionTableData(goodsEntity.getID(), goodsEntity.getType())) {// 团购类型
                            Toast.makeText(GroupBuyDetailActivity.this, "收藏成功", 1).show();
                        } else {
                            Toast.makeText(GroupBuyDetailActivity.this, "收藏失败，该商品已在收藏中", 1).show();
                        }
                    }

                }
            }
        });
    }

    private void postAdvInfo() {
        this.doAsync(new CallEarliest<Object>() {
            @Override
            public void onCallEarliest() throws Exception {
                // TODO Auto-generated method stub

            }

        }, new Callable<Object>() {
            @Override
            public String call() {

                return null;
            }
        }, new Callback<String>() {
            @Override
            public void onCallback(String jsonStr) {
                // TODO Auto-generated method stub
                /**
                 * 测试用，2013/06/14 by yewang
                 */
                /*
                 * if(goodsinfo!=null&&goodsinfo.getImageUrl()!=null){ String url=goodsinfo.getImageUrl(); myAdvList=new ArrayList<AdvEntity>(); AdvEntity a; while(url.indexOf("※")!=-1){ a= new AdvEntity(); a.setImageUrl(url.substring(0,url.indexOf("※"))); myAdvList.add(a); url=url.substring(url.indexOf("※")+1,url.length()); } }
                 */
                if (goodsEntity != null && goodsEntity.getImageUrl() != null && !goodsEntity.getImageUrl().equals("")) {
                    // String url="http://yads.gnway.net:81/"+goodsEntity.getImageUrl();
                    String url = GroupBuyDetailActivity.this.getString(R.string.base_url) + goodsEntity.getImageUrl();
                    myAdvList = new ArrayList<AdvEntity>();
                    AdvEntity a = new AdvEntity();
                    a.setImageUrl(url);
                    myAdvList.add(a);
                }

                initViewPager();
                // initAdvInfoList(aList);
            }

        });
    }

    // 获取商家详情

    private void insert_sellerinfo() {
        this.doAsync(new CallEarliest<Object>() {
            @Override
            public void onCallEarliest() throws Exception {
                // TODO Auto-generated method stub

            }

        }, new Callable<Object>() {
            @Override
            public String call() {
                thread1 = Thread.currentThread();
                String reqUrl = getString(R.string.getShopsDetailInfo_php);

                Map<String, Object> parameters = new LinkedHashMap<String, Object>();

                parameters.put("ID", goodsEntity.getID());
                parameters.put("type", goodsEntity.getType());

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
                thread2 = Thread.currentThread();
                System.out.println("code jsonStr=" + jsonStr);

                if (jsonStr.equals("-1")) {
                    ViewUtil.showToast(myContext, myContext.getString(R.string.net_exception));
                } else {
                    try {
                        JSONObject json = new JSONObject(jsonStr);
                        System.out.println("===================test" + json.getString("name"));
                        BusinessDao.updata_sellerinfo(goodsEntity, json.getString("ID"), json.getString("name"));

                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                }
            }
        });
    }

    // 获取商品详情

    private void get_goodsinfo() {
        this.doAsync(new CallEarliest<Object>() {
            @Override
            public void onCallEarliest() throws Exception {
                // TODO Auto-generated method stub
                isGetInfo = true;
            }

        }, new Callable<Object>() {
            @Override
            public String call() {
                thread1 = Thread.currentThread();
                String reqUrl = getString(R.string.getGoodsDetail_http);

                Map<String, Object> parameters = new LinkedHashMap<String, Object>();

                if (HomeMainActivity.FLAGTOGROUP.equals("ORDER")) {
                    parameters.put("goodsID", goodsID);
                    parameters.put("type", type);
                } else {
                    parameters.put("goodsID", goodsEntity.getID());
                    parameters.put("type", goodsEntity.getType());
                }

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
                ViewUtil.removeLoadingAnimation(ivLoading);
                isGetInfo = false;
                thread2 = Thread.currentThread();
                System.out.println("code jsonStr=" + jsonStr);

                if (jsonStr.equals("-1")) {
                    ViewUtil.showToast(myContext, myContext.getString(R.string.net_exception));
                } else {
                    try {

                        JSONObject jsonObject = new JSONObject(jsonStr);

                        String jsonList = jsonStr.trim();
                        Type listType = new TypeToken<GoodsDetailInfoEntity>() {
                        }.getType();
                        goodsinfo = new Gson().fromJson(jsonList, listType);
                        if (HomeMainActivity.FLAGTOGROUP.equals("ORDER")) {
                            tvgroupbuyGoodsname.setText(goodsinfo.getName());
                            tvgroupbuyNowprice.setText(goodsinfo.getPrice());
                            tvgroupbuyOldprice.setVisibility(View.GONE);
                            tvgroupbuyBuyercount.setText(String.valueOf(goodsEntity.getSelledCount()) + "人");
                        }
                        String intros = goodsinfo.getIntro();
                        File files = new File(Environment.getExternalStorageDirectory() + "/com.fsti.communityClient/");
                        if (!files.exists()) {
                            files.mkdirs();
                        }
                        File file = new File(Environment.getExternalStorageDirectory() + "/com.fsti.communityClient/intro.html");
                        try {
                            FileWriter writer = new FileWriter(file);
                            BufferedWriter bw = new BufferedWriter(writer);
                            bw.write(intros);
                            bw.close();
                            writer.close();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                        srcollMain.setVisibility(View.VISIBLE);
                        btgroupbuyCollect.setVisibility(View.VISIBLE);
                        reload_view();
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        tvLoaderror.setVisibility(View.VISIBLE);
                    }
                    // postAdvInfo();
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
    public boolean onDown(MotionEvent e) {
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
        viewflipImage.setFlipInterval(6000);
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        // TODO Auto-generated method stub
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
