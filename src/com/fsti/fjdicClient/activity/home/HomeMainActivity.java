package com.fsti.fjdicClient.activity.home;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fsti.fjdicClient.ApplicationUtil;
import com.fsti.fjdicClient.R;
import com.fsti.fjdicClient.activity.BaseActivity;
import com.fsti.fjdicClient.activity.groupBuy.GroupBuyDetailActivity;
import com.fsti.fjdicClient.activity.groupBuy.GroupBuyMainListActivity;
import com.fsti.fjdicClient.activity.groupBuy.GroupBuySortListActivity;
import com.fsti.fjdicClient.activity.login.LoginActivity;
import com.fsti.fjdicClient.activity.more.MoreActivity;
import com.fsti.fjdicClient.activity.mycenter.MycenterMainActivity;
import com.fsti.fjdicClient.activity.search.SearchMainActivity;
import com.fsti.fjdicClient.activity.shoppingcart.ShoppingcartMainListActivity;
import com.fsti.fjdicClient.adapter.GroupbuyNewAdapter;
import com.fsti.fjdicClient.adapter.HomeMainActivityGVMenuAdapter;
import com.fsti.fjdicClient.bean.AdvEntity;
import com.fsti.fjdicClient.bean.ExitApplication;
import com.fsti.fjdicClient.bean.GoodsEntity;
import com.fsti.fjdicClient.bean.GroupBuyingGoodsCategoryEntity;
import com.fsti.fjdicClient.bean.HomemaininfoEntity;
import com.fsti.fjdicClient.dao.BusinessDao;
import com.fsti.fjdicClient.util.GlobalVarUtil;
import com.fsti.fjdicClient.util.HttpUtil;
import com.fsti.fjdicClient.util.ImageLoaderHelper;
import com.fsti.fjdicClient.util.ViewUtil;
import com.fsti.fjdicClient.util.asyncUtil.CallEarliest;
import com.fsti.fjdicClient.util.asyncUtil.Callable;
import com.fsti.fjdicClient.util.asyncUtil.Callback;
import com.fsti.fjdicClient.view.CustomListview;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UmengUpdateListener;
import com.umeng.update.UpdateResponse;

/**
 * 首页
 * 
 * @author 金涛
 */
public class HomeMainActivity extends BaseActivity implements OnClickListener, OnItemClickListener {
    private static final String          TAG            = "HomeMainActivity";
    public static int                    i              = 0;
    private boolean                      isFirstAdv     = true;
    // 采用自定义gallery
    public static int                    advcounts      = 0;
    public static String                 homestart      = "start";
    public static GalleryFlow            advGallery;
    public static Timer                  timer;
    public static TimerTask              task;
    public static int                    interval       = 6000;
    public static int                    index          = 0;
    public static boolean                isStop         = false;
    public static String                 FLAGTOSHOPPING = "";
    public static String                 FLAGTOGROUP    = "";
    // public SyncImageLoadUtil syncImageLoader = null;
    public Context                       context;
    private LinearLayout                 linearMainViewpagerContainer;

    private ImageView                    ivLoading;
    private List<AdvEntity>              myActivityList = new ArrayList<AdvEntity>();
    private HomemaininfoEntity           entity         = new HomemaininfoEntity();
    private ImageView                    iv_online_img;
    private TextView                     tv_online_text;
    private TextView                     tv_online_text2;
    public static int                    screenWidth    = 0;
    private int                          screenHeight   = 0;
    private LinearLayout                 tohome;
    private LinearLayout                 tosearch;
    private LinearLayout                 toshoppingcart;
    private LinearLayout                 tomycenter;
    private LinearLayout                 tomore;
    private RelativeLayout               bottommenu;
    // 后面添加
    // private LinearLayout chooseaddress;
    private LinearLayout                 ll_main_call;
    private GridView                     gridviewmenu;
    // private GroupbuyAdapter adapter;
    private static GroupbuyNewAdapter    adapter;

    // 分类
    // List<GroupBuyingGoodsCategoryEntity> currentLevelList;

    List<GroupBuyingGoodsCategoryEntity> temp;
    // 测试数据
    // List<Integer> imglocalresid = new ArrayList<Integer>();
    // 团购
    private Thread                       thread1;
    private Thread                       thread2;
    private CustomListview               listGroupbuyMain;
    private static List<GoodsEntity>     goodsList      = new ArrayList<GoodsEntity>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("home", "home creat ----");
        setContentView(R.layout.layout_activity_home_main);
        homestart = "start";
        GroupBuySortListActivity.groupstart = "stop";
        getMinPurchasemoney();// 获取最小包邮
        getString(R.string.GetGroupBuyingGoogsListLevel_php);
        // 获取手机屏幕的宽高
        Display display = getWindowManager().getDefaultDisplay();
        // 手机屏幕的高度
        // 手机屏幕的宽度
        screenWidth = display.getWidth();

        ExitApplication.getInstance().addActivity(this);
        SharedPreferences logininfo = getSharedPreferences("logininfo", this.MODE_WORLD_WRITEABLE);
        GlobalVarUtil.account.setUID(logininfo.getString("UID", null));
        GlobalVarUtil.account.setNickName(logininfo.getString("nickname", ""));
        init();
        // ---------------------------------友盟更新---------------------------------
        if (GlobalVarUtil.firstUpdateApp.equals("update")) {
            GlobalVarUtil.firstUpdateApp = "";
            UmengUpdateAgent.setUpdateOnlyWifi(true);// 是否仅在wifi情况下更新
            UmengUpdateAgent.setUpdateAutoPopup(true);// 自动跳出窗口提示更新
            UmengUpdateAgent.update(this);
            UmengUpdateAgent.setUpdateListener(new UmengUpdateListener() {
                public void onUpdateReturned(int updateStatus, UpdateResponse updateInfo) {
                    System.out.println("-----------------onUpdateReturned" + updateStatus);
                    switch (updateStatus) {
                    case 0:
                        UmengUpdateAgent.showUpdateDialog(myContext, updateInfo);
                        break;
                    case 1: // has no update
                        // Toast.makeText(myContext, "没有更新", Toast.LENGTH_SHORT)
                        // .show();
                        break;
                    case 2: // none wifi
                        // Toast.makeText(myContext, "没有wifi连接， 只在wifi下更新", Toast.LENGTH_SHORT)
                        // .show();
                        break;
                    case 3: // time out
                        Toast.makeText(myContext, "超时", Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
            });

        }
        // ---------------------------------友盟更新---------------------------------

        // 获取分类
        this.postSortInfo();
        getmainListgoods();
        // postgroupbuylistInfo();
    }

    private String[] test_img = { "http://a.vpimg1.com/upload/brand/201412/20141212204626371.jpg", "http://a.vpimg1.com/upload/brand/201412/2014121218590795351.jpg",
            "http://a.vpimg1.com/upload/brand/201412/2014121214415223601.jpg" };

    private void getmainListgoods() {
        goodsList.clear();
        for (int i = 0; i < 3; i++) {
            GoodsEntity testgoods = new GoodsEntity();
            testgoods.setImageUrl(test_img[i]);
            testgoods.setSelledCount(i + 1);
            testgoods.setTimes_long(174967);
            goodsList.add(testgoods);
        }
        adapter = new GroupbuyNewAdapter(getBaseContext(), goodsList);
        listGroupbuyMain.setAdapter(adapter);
        // setListViewHeightBasedOnChildren(listGroupbuyMain);
        // handler.sendEmptyMessageDelayed(5, 1000);

    }

    private void getMinPurchasemoney() {
        // TODO Auto-generated method stub
        this.doAsync(new CallEarliest<Object>() {
            @Override
            public void onCallEarliest() throws Exception {
                // TODO Auto-generated method stub
            }

        }, new Callable<Object>() {
            @Override
            public String call() {
                String reqUrl = getString(R.string.GetminPurchasemoney_php);

                Map<String, Object> parameters = new LinkedHashMap<String, Object>();
                parameters.put("CityID", "0");// zhengshidaima
                String jsonStr = "";
                try {
                    jsonStr = HttpUtil.postData(reqUrl, parameters, GlobalVarUtil.ENCODING);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    Log.e("HomeMainActivity cityID", e.toString());
                    e.printStackTrace();
                    return "-1";
                }
                return jsonStr;
            }
        }, new Callback<String>() {
            @Override
            public void onCallback(String jsonStr) {
                // TODO Auto-generated method stub
                if (jsonStr.equals("-1")) {
                    // ViewUtil.showToast(myContext, myContext.getString(R.string.net_exception));
                } else {
                    JSONObject jsonObj;
                    try {
                        // jsonObj = new JSONObject(jsonStr);
                        // String minPurchasemoney = jsonObj.getString("minPurchasemoney");
                        String minPurchasemoney = replaceBlank(jsonStr);
                        if (!minPurchasemoney.equals("")) {
                            GlobalVarUtil.minPurchasemoney = minPurchasemoney;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public String replaceBlank(String str) {
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

    @Override
    public void bindEvent() {
        // TODO Auto-generated method stub
        tohome.setOnClickListener(this);
        tosearch.setOnClickListener(this);
        toshoppingcart.setOnClickListener(this);
        tomycenter.setOnClickListener(this);
        tomore.setOnClickListener(this);
        ll_main_call.setOnClickListener(this);
        // 主页ListView Item的点击事件
        listGroupbuyMain.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                // TODO Auto-generated method stub
                if (arg2 < goodsList.size()) {
                    // GoodsEntity goodsEntity = goodsList.get(arg2);
                    // Intent intent = new Intent(HomeMainActivity.this, GroupBuyDetailActivity.class);
                    // Bundle bundle = new Bundle();
                    // bundle.putSerializable("goodsEntity", goodsEntity);
                    // intent.putExtras(bundle);
                    // startActivity(intent);
                    Intent intent = new Intent(HomeMainActivity.this, GroupBuyMainListActivity.class);
                    // Intent intent = new Intent(this, GroupBuyMainListNewActivity.class);
                    intent.putExtra("goodsCategoryID", temp.get(0).getID());
                    intent.putExtra("title", temp.get(0).getName());
                    startActivity(intent);
                }
            }
        });
        advGallery.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                arg2 = arg2 % myActivityList.size();
                AdvEntity advEntity = myActivityList.get(arg2);
                Log.e("firsthome", "广告点击： " + advEntity.getIdentifier() + "  " + advEntity.getImageUrl());
                switch (myActivityList.get(arg2).getIdentifier()) {
                case 4:
                    Log.e("adv", "广告类型为4,不跳转");
                    break;
                case 2:
                    FLAGTOGROUP = "ADV";
                    Intent toGroup = new Intent(context, GroupBuyDetailActivity.class);
                    Bundle bundle2 = new Bundle();
                    bundle2.putSerializable("advEntity", advEntity);
                    toGroup.putExtras(bundle2);
                    arg1.getContext().startActivity(toGroup);
                    break;

                }
                Log.e("item ", "click item========");
            }
        });
    }

    @Override
    public void initValue() {
        // TODO Auto-generated method stub
        context = getBaseContext();
        listGroupbuyMain = (CustomListview) findViewById(R.id.list_groupbuy_main);
        // adapter = new GroupbuyAdapter(getBaseContext(), goodsList);
        // listGroupbuyMain.setAdapter(adapter);
        gridviewmenu = (GridView) this.findViewById(R.id.gridviewmenu);
        gridviewmenu.setSelector(new ColorDrawable(Color.TRANSPARENT));
        ll_main_call = (LinearLayout) this.findViewById(R.id.ll_main_call);

        ivLoading = (ImageView) findViewById(R.id.iv_loading);
        ivLoading.setOnClickListener(this);
        LayoutInflater mInflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        advGallery = (com.fsti.fjdicClient.activity.home.GalleryFlow) findViewById(R.id.gall_homemain_screenGallery);
        linearMainViewpagerContainer = (LinearLayout) findViewById(R.id.linear_main_viewpager_container);

        RelativeLayout.LayoutParams lp1 = (android.widget.RelativeLayout.LayoutParams) linearMainViewpagerContainer.getLayoutParams();
        int height = screenWidth * 1 / 2;
        lp1.height = height;
        linearMainViewpagerContainer.setLayoutParams(lp1);

        View view2 = mInflater.inflate(R.layout.layout_item_bottom_menu, null);
        tohome = (LinearLayout) view2.findViewById(R.id.ll_tohome);
        tosearch = (LinearLayout) view2.findViewById(R.id.ll_tosearch);
        toshoppingcart = (LinearLayout) view2.findViewById(R.id.ll_toshoppingcart);
        tomycenter = (LinearLayout) view2.findViewById(R.id.ll_tomycenter);
        tomore = (LinearLayout) view2.findViewById(R.id.ll_tomore);
        bottommenu = (RelativeLayout) findViewById(R.id.rl_homemain_bottommenu);
        bottommenu.addView(view2);
        ViewGroup.LayoutParams lp = view2.getLayoutParams();
        lp.width = lp.FILL_PARENT;
        view2.setLayoutParams(lp);
        getHomemaininfo();
    }

    private void loading_view_content() {// 加载页面内容
        // SyncImageLoadUtil syncImageLoad = new SyncImageLoadUtil();
        if (!entity.getOnline_img().equals("") && entity.getOnline_img() != null) {
            ImageLoaderHelper.displayImage(iv_online_img, entity.getOnline_img());
            // syncImageLoad.displayImage(entity.getOnline_img(), iv_online_img, HomeMainActivity.this);
        }
        if (!entity.getOnline_text().equals("") && entity.getOnline_text() != null) {
            tv_online_text.setText(entity.getOnline_text());
        }
        if (!entity.getOnline_text2().equals("") && entity.getOnline_text2() != null) {
            tv_online_text2.setText(entity.getOnline_text2());
        }
        // }
    }

    /**
     * 请求广告信息接口
     */
    private void postAdvInfo() {
        this.doAsync(new CallEarliest<Object>() {
            @Override
            public void onCallEarliest() throws Exception {
                // TODO Auto-generated method stub
                ivLoading.setVisibility(View.VISIBLE);
                ViewUtil.addLoadingAnimation(ApplicationUtil.myContext, ivLoading);
            }

        }, new Callable<Object>() {
            @Override
            public String call() {
                String reqUrl = getString(R.string.getadv_http);

                Map<String, Object> parameters = new LinkedHashMap<String, Object>();
                String jsonStr = "";
                try {
                    jsonStr = HttpUtil.postData(reqUrl, parameters, GlobalVarUtil.ENCODING);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    Log.e("HomeMainActivity", e.toString());
                    e.printStackTrace();
                    return "-1";
                }

                return jsonStr;
            }
        }, new Callback<String>() {
            @Override
            public void onCallback(String jsonStr) {
                // TODO Auto-generated method stub
                Log.e("广告-----homemain code jsonStr=", jsonStr);
                ViewUtil.removeLoadingAnimation(ivLoading);
                if (jsonStr.equals("-1")) {
                    ViewUtil.showToast(myContext, myContext.getString(R.string.net_exception));
                } else {
                    JSONObject jsonObj;
                    try {
                        jsonObj = new JSONObject(jsonStr);
                        String jsonList = jsonObj.getString("AdvList");
                        Type listType = new TypeToken<List<AdvEntity>>() {
                        }.getType();
                        myActivityList = new Gson().fromJson(jsonList, listType);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    advcounts = myActivityList.size();
                    initGallery();
                }
            }
        });
    }

    public void initGallery() {
        TextView tv_using;
        tv_using = new TextView(myContext);
        if (myActivityList.size() == 0) {
            if (isFirstAdv) {
                isFirstAdv = false;
                advGallery.setVisibility(View.GONE);
                tv_using.setText("暂时没有活动商品");
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                tv_using.setLayoutParams(params);
                linearMainViewpagerContainer.addView(tv_using);
            }
        } else {
            try {
                linearMainViewpagerContainer.removeView(tv_using);
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
            ScreenSaverGalleryAdapter adapter = null;
            // syncImageLoader = new SyncImageLoadUtil(GlobalVarUtil.MAIN_ADV_IMAGE_SAVE_PATH);
            adapter = new ScreenSaverGalleryAdapter(getBaseContext(), myActivityList);
            advGallery.setAdapter(adapter);
            advGallery.setSelection(myActivityList.size() * 100);
            advGallery.setVisibility(View.VISIBLE);
            autoSwitch();
        }

    }

    /** 图片定时自动切换 */
    public static void autoSwitch() {
        if (task != null && timer != null) {
            task.cancel();
            timer.cancel();
        }
        timer = new Timer();
        task = new TimerTask() {

            @Override
            public void run() {
                Message message = new Message();
                message.what = 2;
                index = advGallery.getSelectedItemPosition();
                if (index == advcounts - 1) {
                    index = 0;
                } else {
                    index++;
                }
                handler.sendMessage(message);
            }
        };
        timer.schedule(task, interval, interval);
        isStop = false;
    }

    /** 停止自动播放图片 **/
    public static void stopSwitch() {
        if (task != null && timer != null) {
            task.cancel();
            timer.cancel();
        }
        isStop = true;
    }

    public static Handler handler = new Handler() {
                                      @Override
                                      public void handleMessage(Message msg) {
                                          super.handleMessage(msg);
                                          switch (msg.what) {
                                          case 2:
                                              i++;
                                              Log.e("schedul", "schedul-----------------" + i + " index = " + index);
                                              advGallery.setSelection(index);
                                              break;
                                          case 4:
                                              try {
                                              } catch (Exception e) {
                                                  e.printStackTrace();
                                              }
                                              break;
                                          case 5:
                                              for (GoodsEntity testgoods : goodsList) {
                                                  if (testgoods.getTimes_long() - 1 > 0) {
                                                      testgoods.setTimes_long(testgoods.getTimes_long() - 1);
                                                  } else {
                                                      testgoods.setTimes_long(0);
                                                  }
                                              }
                                              adapter.notifyDataSetChanged();
                                              sendEmptyMessageDelayed(5, 1000);
                                              break;
                                          default:
                                              break;
                                          }
                                          super.handleMessage(msg);
                                      }
                                  };

    class ScreenSaverGalleryAdapter extends BaseAdapter {

        int                     mGalleryItemBackground;
        private Context         mContext;
        private LayoutInflater  myInflater;
        // private SyncImageLoadUtil mySilu;
        private List<AdvEntity> advList;

        public ScreenSaverGalleryAdapter(Context c, List<AdvEntity> advList) {
            this.mContext = c;
            this.advList = advList;
            // this.mySilu = mySilu;
            myInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public int getCount() {
            return Integer.MAX_VALUE;
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = myInflater.inflate(R.layout.layout_advgallery_item, null);
                holder.sImage = (ImageView) convertView.findViewById(R.id.iv_advgallery_item_sImage);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.sImage.setScaleType(ScaleType.CENTER_CROP);
            ImageLoaderHelper.displayImage(holder.sImage, advList.get(position % advList.size()).getImageUrl());
            // mySilu.displayImage(advList.get(position % advList.size()).getImageUrl(), holder.sImage, mContext);
            return convertView;
        }

        public class ViewHolder {
            private ImageView sImage;
        }

        /**
         * Returns the size (0.0f to 1.0f) of the views depending on the 'offset' to the center.
         */
        public float getScale(boolean focused, int offset) {
            /* Formula: 1 / (2 ^ offset) */
            return Math.max(0, 1.0f / (float) Math.pow(2, Math.abs(offset)));
        }

    }

    @Override
    protected void onDestroy() {
        homestart = "stop";
        stopSwitch();
        Log.e("home", "home destroy ----");
        super.onDestroy();
    }

    @Override
    public void onStart() {
        homestart = "start";
        if (isStop) {
            autoSwitch();
            Log.e("home", "home start ----");
        }

        super.onStart();
    }

    @Override
    public void onStop() {
        homestart = "stop";
        stopSwitch();
        Log.e("home", "home stop ----");
        super.onStop();

    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        homestart = "stop";
        stopSwitch();
        Log.e("home", "home pause ----");
        super.onPause();
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        postAdvInfo();
        Log.e("home", "home resume ----");
        if (isStop) {
            homestart = "start";
            autoSwitch();
        }
        super.onResume();
    }

    @Override
    public void onClick(View arg0) {
        // TODO Auto-generated method stub
        switch (arg0.getId()) {
        case R.id.ll_main_call:
        case R.id.iv_loading:
            Builder builder = new AlertDialog.Builder(HomeMainActivity.this);
            final String new_phone = BusinessDao.find_from_Homemaininfo("online_num");// 客服号码
            builder.setTitle("是否拨打");
            builder.setMessage("客服电话");
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    // TODO Auto-generated method stub
                    String phone = "4000036539";
                    if (!new_phone.equals("")) {
                        phone = new_phone;
                    }
                    System.out.println("--------------------------" + phone + "-------");
                    Intent intent3 = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
                    startActivity(intent3);
                }
            });
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    // TODO Auto-generated method stub
                }
            });
            builder.show();

            break;
        case R.id.ll_tohome:

            break;
        case R.id.ll_tosearch:
            Intent intentsearch = new Intent(HomeMainActivity.this, SearchMainActivity.class);
            startActivity(intentsearch);
            ExitApplication.getInstance().exit();
            break;
        case R.id.ll_toshoppingcart:
            Intent intentshoppingcart = new Intent(HomeMainActivity.this, ShoppingcartMainListActivity.class);
            startActivity(intentshoppingcart);

            if (GlobalVarUtil.account.getUID() == null) {
                Intent intentTolongin = new Intent(HomeMainActivity.this, LoginActivity.class);
                startActivity(intentTolongin);
            }
            // ExitApplication.getInstance().exit();//购物车可以返回

            break;
        case R.id.ll_tomycenter:
            Intent intentmycenter = new Intent(HomeMainActivity.this, MycenterMainActivity.class);
            startActivity(intentmycenter);

            if (GlobalVarUtil.account.getUID() == null) {
                Intent intentTolongin = new Intent(HomeMainActivity.this, LoginActivity.class);
                startActivity(intentTolongin);

            }
            ExitApplication.getInstance().exit();

            break;
        case R.id.ll_tomore:
            Intent intentmore = new Intent(HomeMainActivity.this, MoreActivity.class);
            startActivity(intentmore);
            ExitApplication.getInstance().exit();
            break;

        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            Builder builder = new AlertDialog.Builder(HomeMainActivity.this);
            builder.setTitle("是否退出");
            builder.setMessage("按确定键退出！");
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    // TODO Auto-generated method stub
                    GlobalVarUtil.firstUpdateApp = "update";
                    ExitApplication.getInstance().exit();
                    android.os.Process.killProcess(android.os.Process.myPid());
                }
            });
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    // TODO Auto-generated method stub
                }
            });
            builder.show();
        }

        return super.onKeyDown(keyCode, event);
    }

    // 实时更新首页内容

    private void getHomemaininfo() {
        this.doAsync(new CallEarliest<Object>() {
            @Override
            public void onCallEarliest() throws Exception {
                // TODO Auto-generated method stub
                ViewUtil.addLoadingAnimation(ApplicationUtil.myContext, ivLoading);
            }

        }, new Callable<Object>() {
            @Override
            public String call() {
                String reqUrl;
                Map<String, Object> parameters = new LinkedHashMap<String, Object>();

                reqUrl = getString(R.string.GetHomemaininfo_php);

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

                if (jsonStr.equals("-1") || jsonStr.trim().equals("") || jsonStr.trim().equals("null") || jsonStr == null) {
                    // ViewUtil.showToast(myContext, myContext.getString(R.string.net_exception));
                } else {
                    try {
                        Type listType = new TypeToken<HomemaininfoEntity>() {
                        }.getType();
                        entity = new Gson().fromJson(jsonStr, listType);
                    } catch (Exception e) {
                        // TODO: handle exception
                        e.printStackTrace();
                    }
                    loading_view_content();
                }
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        // TODO Auto-generated method stub

        if (arg2 == temp.size() - 1) {
            Intent intent2 = new Intent(this, GroupBuySortListActivity.class);//
            startActivity(intent2);
        } else {
            Intent intent = new Intent(this, GroupBuyMainListActivity.class);
            // Intent intent = new Intent(this, GroupBuyMainListNewActivity.class);
            intent.putExtra("goodsCategoryID", temp.get(arg2).getID());
            intent.putExtra("title", temp.get(arg2).getName());
            startActivity(intent);
        }

    }

    /**
     * 请求分类信息接口
     */
    private void postSortInfo() {
        this.doAsync(new CallEarliest<Object>() {
            @Override
            public void onCallEarliest() throws Exception {
                ivLoading.setVisibility(View.VISIBLE);
                ViewUtil.addLoadingAnimation(ApplicationUtil.myContext, ivLoading);
            }

        }, new Callable<Object>() {
            @Override
            public String call() {
                String reqUrl = getString(R.string.getGroupBuyingGoodsCategoryInfo_http);

                Map<String, Object> parameters = new LinkedHashMap<String, Object>();
                String updatetime = "";
                parameters.put("updatetime", updatetime);
                // parameters.put("updateTime", lastUpdatetimeSort);
                String jsonStr = "";
                try {
                    jsonStr = HttpUtil.postData(reqUrl, parameters, GlobalVarUtil.ENCODING);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    Log.e("FirstLevelSortActivity", e.toString());
                    e.printStackTrace();
                    return "-1";
                }
                return jsonStr;
            }
        }, new Callback<String>() {
            @Override
            public void onCallback(String jsonStr) {
                // TODO Auto-generated method stub
                System.out.println("firstLevel code jsonStr =" + jsonStr);

                // tvLoad.setVisibility(View.GONE);//隐藏提示文字
                if (jsonStr.equals("-1")) {
                    ViewUtil.showToast(myContext, myContext.getString(R.string.net_exception));
                } else {
                    try {
                        JSONObject jsonObject;
                        jsonObject = new JSONObject(jsonStr);
                        String time = jsonObject.getString("updatetime");
                        List<GroupBuyingGoodsCategoryEntity> List = new ArrayList<GroupBuyingGoodsCategoryEntity>();
                        String sortlist = jsonObject.getString("list");
                        Type listType = new TypeToken<List<GroupBuyingGoodsCategoryEntity>>() {
                        }.getType();
                        List = new Gson().fromJson(sortlist, listType);
                        int num = 0;
                        for (int q = 0; q < List.size(); q++) {
                            num = num + List.get(q).getGoodsCount();
                        }
                        // currentLevelList=new ArrayList<GroupBuyingGoodsCategoryEntity>();
                        // for(int q=0 ; q<List.size();q++){
                        // currentLevelList.add(List.get(q));
                        // }
                        // Log.i(TAG,"size：" +currentLevelList.toString());
                        if (List.size() > 7) {
                            temp = List.subList(0, 7);
                        } else {
                            temp = List;
                        }
                        GroupBuyingGoodsCategoryEntity a = new GroupBuyingGoodsCategoryEntity();
                        a.setID("0");
                        a.setLevel(1);
                        a.setOrderPosition(0);
                        a.setParentID("");
                        a.setGoodsCount(num);
                        a.setName("更多分类");
                        a.setCategoryImage("");
                        temp.add(a);
                        // for(int i=0;i<temp.size();i++){
                        // imglocalresid.add(R.drawable.ic_launcher);
                        // }

                        if (temp.size() <= 6) {
                            gridviewmenu.setNumColumns(3);
                        } else {
                            gridviewmenu.setNumColumns(4);
                        }
                        gridviewmenu.setAdapter(new HomeMainActivityGVMenuAdapter(HomeMainActivity.this, temp));
                        gridviewmenu.setOnItemClickListener(HomeMainActivity.this);
                        ViewUtil.removeLoadingAnimation(ivLoading);
                    } catch (Exception e) {
                        Log.e("FirstLevelSortActivity", e.toString());
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    /**
     * 请求团购信息接口
     */
    // private void postgroupbuylistInfo() {
    // this.doAsync(new CallEarliest<Object>() {
    // @Override
    // public void onCallEarliest() throws Exception {
    // // TODO Auto-generated method stub
    // ivLoading.setVisibility(View.VISIBLE);
    // ViewUtil.addLoadingAnimation(ApplicationUtil.myContext, ivLoading);
    // }
    //
    // }, new Callable<Object>() {
    // @Override
    // public String call() {
    // thread1 = Thread.currentThread();
    // String reqUrl;
    // Map<String, Object> parameters = new LinkedHashMap<String, Object>();
    // reqUrl = getString(R.string.GetGroupBuyingGoogsListLevel_php);
    // String jsonStr = "";
    // try {
    // jsonStr = HttpUtil.postData(reqUrl, parameters, GlobalVarUtil.ENCODING);
    // } catch (Exception e) {
    // // TODO Auto-generated catch block
    // e.printStackTrace();
    // return "-1";
    // }
    //
    // return jsonStr;
    // }
    // }, new Callback<String>() {
    // @Override
    // public void onCallback(String jsonStr) {
    // // TODO Auto-generated method stub
    // thread2 = Thread.currentThread();
    // System.out.println("code jsonStr=" + jsonStr);
    // jsonStr = RegisterActivity.replaceBlank(jsonStr);
    // if (jsonStr.equals("-1")) {
    // ViewUtil.showToast(myContext, myContext.getString(R.string.net_exception));
    // } else {
    // if (!jsonStr.trim().equals("")) {
    // deal_groupbuylist_data(jsonStr);
    // } else {
    // ViewUtil.removeLoadingAnimation(ivLoading);
    // }
    // }
    // }
    // });
    // }

    // private void deal_groupbuylist_data(String jsonStr) {
    // ViewUtil.removeLoadingAnimation(ivLoading);
    // try {
    // JSONObject jsonObject;
    // jsonObject = new JSONObject(jsonStr);
    //
    // // List<GoodsEntity> List;
    //
    // // goodsList =new ArrayList<GoodsEntity>();
    //
    // if (jsonStr.equals("{\"list\":[\"\"]}")) {
    // Toast.makeText(HomeMainActivity.this, "推荐团购返回值为空！", Toast.LENGTH_SHORT).show();
    // } else {
    // String jsonList = jsonObject.getString("list");
    // Type listType = new TypeToken<List<GoodsEntity>>() {
    // }.getType();
    // goodsList = new Gson().fromJson(jsonList, listType);
    // // System.out.println("===new======"+List.get(0).getGoodsAttribute());
    // }
    //
    // // Log.e(TAG, "————————————————返回的数据——————————————");
    // // Log.i(TAG, goodsList.toString());
    //
    // adapter = new GroupbuyAdapter(getBaseContext(), goodsList);
    // listGroupbuyMain.setAdapter(adapter);
    // // adapter.notifyDataSetChanged();
    // setListViewHeightBasedOnChildren(listGroupbuyMain);
    // } catch (JSONException e) {
    // // TODO Auto-generated catch block
    // e.printStackTrace();
    // }
    // }

    public void setListViewHeightBasedOnChildren(ListView listView) {
        int height = bottommenu.getHeight();
        ListAdapter listAdapter = listView.getAdapter();

        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1)) + height;
        ((MarginLayoutParams) params).setMargins(10, 10, 10, 10); // 可删除
        listView.setLayoutParams(params);
    }
}
