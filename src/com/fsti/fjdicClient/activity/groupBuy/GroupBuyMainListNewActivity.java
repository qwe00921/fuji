package com.fsti.fjdicClient.activity.groupBuy;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fsti.fjdicClient.ApplicationUtil;
import com.fsti.fjdicClient.R;
import com.fsti.fjdicClient.activity.BaseActivity;
import com.fsti.fjdicClient.activity.home.HomeMainActivity;
import com.fsti.fjdicClient.activity.login.LoginActivity;
import com.fsti.fjdicClient.activity.login.RegisterActivity;
import com.fsti.fjdicClient.activity.more.MoreActivity;
import com.fsti.fjdicClient.activity.mycenter.MycenterMainActivity;
import com.fsti.fjdicClient.activity.search.SearchMainActivity;
import com.fsti.fjdicClient.activity.shoppingcart.ShoppingcartMainListActivity;
import com.fsti.fjdicClient.adapter.GroupbuyNewAdapter;
import com.fsti.fjdicClient.bean.ExitApplication;
import com.fsti.fjdicClient.bean.GoodsEntity;
import com.fsti.fjdicClient.util.GlobalVarUtil;
import com.fsti.fjdicClient.util.HttpUtil;
import com.fsti.fjdicClient.util.ViewUtil;
import com.fsti.fjdicClient.util.asyncUtil.CallEarliest;
import com.fsti.fjdicClient.util.asyncUtil.Callable;
import com.fsti.fjdicClient.util.asyncUtil.Callback;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * @author kris 商城商品列表
 */
public class GroupBuyMainListNewActivity extends BaseActivity implements OnItemClickListener, OnClickListener, OnScrollListener {

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        if (thread1 != null && thread1.isAlive()) {
            thread1.interrupt();
        }
        if (thread2 != null && thread2.isAlive()) {
            thread2.interrupt();
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        handler.sendEmptyMessageDelayed(1, 1000);
    }

    private Thread                   thread1;
    private Thread                   thread2;
    private ListView                 listGroupbuyMain;
    private static List<GoodsEntity> goodsList;
    private int                      is_more         = 0;                       // 1表示获取更多商品，
    private int                      index           = 1;                       // 要求服务器返回第几页的数据
    private int                      perpage         = 10;                      // 要求服务端每页返回的数据条数
    private GroupbuyNewAdapter       adapter;
    private boolean                  isLastRow       = false;
    private int                      is_over         = 0;                       // 1表示数据已经全部加载完
    private View                     footer;
    private int                      is_loading      = 0;                       // 消除加载数据时，因振荡导致多次加载，1表示正在加载数据
    private int                      none_data       = 0;
    private TextView                 title;
    private Button                   back;
    private ImageView                ivLoading;
    private int                      goodsCategoryID = 0;
    private LinearLayout             tohome;
    private LinearLayout             tosearch;
    private LinearLayout             toshoppingcart;
    private LinearLayout             tomycenter;
    private LinearLayout             tomore;
    private RelativeLayout           bottommenu;
    private String[]                 test_img        = { "http://a.vpimg1.com/upload/brand/201412/20141212204626371.jpg", "http://a.vpimg1.com/upload/brand/201412/2014121218590795351.jpg",
            "http://a.vpimg1.com/upload/brand/201412/2014121214415223601.jpg" };

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            GroupBuyMainListNewActivity.this.finish();
        }

        return super.onKeyDown(keyCode, event);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_list_groupbuy_main_new);
        ExitApplication.getInstance().addActivity(this);
        goodsList = new ArrayList<GoodsEntity>();
        for (int i = 0; i < 3; i++) {
            GoodsEntity testgoods = new GoodsEntity();
            testgoods.setImageUrl(test_img[i]);
            testgoods.setSelledCount(i + 1);
            testgoods.setTimes_long(174967);
            goodsList.add(testgoods);
        }
        init();

    }

    private Handler handler = new Handler() {
                                public void handleMessage(android.os.Message msg) {
                                    for (GoodsEntity testgoods : goodsList) {
                                        if (testgoods.getTimes_long() - 1 > 0) {
                                            testgoods.setTimes_long(testgoods.getTimes_long() - 1);
                                        } else {
                                            testgoods.setTimes_long(0);
                                        }
                                    }
                                    adapter.notifyDataSetChanged();
                                    sendEmptyMessageDelayed(1, 1000);
                                };
                            };

    @Override
    public void bindEvent() {
        // TODO Auto-generated method stub
        listGroupbuyMain.setOnItemClickListener(this);// ...y
        listGroupbuyMain.setOnScrollListener(this);
        back.setOnClickListener(this);
        tohome.setOnClickListener(this);
        tosearch.setOnClickListener(this);
        toshoppingcart.setOnClickListener(this);
        tomycenter.setOnClickListener(this);
        tomore.setOnClickListener(this);

    }

    @Override
    public void initValue() {
        // TODO Auto-generated method stub
        listGroupbuyMain = (ListView) findViewById(R.id.list_groupbuy_main);
        back = (Button) findViewById(R.id.btn_groupbuy_goodslist_back);
        title = (TextView) findViewById(R.id.tv_groupbuy_goodslist_title);

        footer = getLayoutInflater().inflate(R.layout.layout_activity_item_more, null);
        ivLoading = (ImageView) findViewById(R.id.iv_goodlist_loading);
        ivLoading.setVisibility(View.VISIBLE);

        ViewUtil.addLoadingAnimation(ApplicationUtil.myContext, ivLoading);
        LayoutInflater mInflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view2 = mInflater.inflate(R.layout.layout_item_bottom_menu, null);
        tohome = (LinearLayout) view2.findViewById(R.id.ll_tohome);
        tosearch = (LinearLayout) view2.findViewById(R.id.ll_tosearch);
        toshoppingcart = (LinearLayout) view2.findViewById(R.id.ll_toshoppingcart);
        tomycenter = (LinearLayout) view2.findViewById(R.id.ll_tomycenter);
        tomore = (LinearLayout) view2.findViewById(R.id.ll_tomore);
        bottommenu = (RelativeLayout) findViewById(R.id.rl_groupbuy_mainlist_bottommenu);
        bottommenu.addView(view2);
        ViewGroup.LayoutParams lp = view2.getLayoutParams();
        lp.width = lp.FILL_PARENT;
        view2.setLayoutParams(lp);

        Intent gintent = this.getIntent();
        title.setText(gintent.getStringExtra("title"));
        goodsCategoryID = Integer.parseInt(gintent.getExtras().getString("goodsCategoryID"));
        // postgroupbuylistInfo();
        if (goodsList == null) {
            footer = getLayoutInflater().inflate(R.layout.layout_activity_none_data, null);
            listGroupbuyMain.addFooterView(footer);
            adapter = null;
            none_data = 1;
            listGroupbuyMain.setAdapter(adapter);
        } else {
            none_data = 0;
            if (goodsList.size() >= 10) {
                listGroupbuyMain.addFooterView(footer);
            }

            adapter = new GroupbuyNewAdapter(getBaseContext(), goodsList);
            listGroupbuyMain.setAdapter(adapter);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        // TODO Auto-generated method stub
        if (none_data == 0) {
            if (arg2 < goodsList.size()) {
                GoodsEntity goodsEntity = goodsList.get(arg2);
                Intent intent = new Intent(GroupBuyMainListNewActivity.this, GroupBuyDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("goodsEntity", goodsEntity);

                intent.putExtras(bundle);
                startActivity(intent);

            }
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        // TODO Auto-generated method stub
        // System.out.println("=====1====开始加载了哟======"+firstVisibleItem);
        // System.out.println("====2=====开始加载了哟======"+visibleItemCount);
        // System.out.println("====3=====开始加载了哟======"+totalItemCount);

        if (totalItemCount > 0 && totalItemCount <= firstVisibleItem + visibleItemCount) {
            System.out.println("=====4====开始加载了哟======");
            isLastRow = true;
        }

    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        // TODO Auto-generated method stub
        // System.out.println(scrollState+"=====6====开始加载了哟======"+isLastRow);
        if (isLastRow) {

            // System.out.println("=====5====开始加载了哟======");
            int lastVisiblePosition = view.getLastVisiblePosition();
            if (is_over == 1 && (is_loading == 0)) {

                Toast.makeText(GroupBuyMainListNewActivity.this, "共" + view.getCount() + "条，全部加载完！", Toast.LENGTH_SHORT).show();
            } else {

                if ((lastVisiblePosition == (view.getCount() - 1)) && (is_loading == 0)) {
                    is_loading = 1;
                    is_more = 1;
                    index = index + 1;
                    // postgroupbuylistInfo();

                }
            }

        }
        isLastRow = false;
    }

    @Override
    public void onClick(View arg0) {
        // TODO Auto-generated method stub
        switch (arg0.getId()) {
        case R.id.btn_groupbuy_goodslist_back:
            this.finish();
            break;
        case R.id.ll_tohome:
            Intent intenthome = new Intent(GroupBuyMainListNewActivity.this, HomeMainActivity.class);
            startActivity(intenthome);
            ExitApplication.getInstance().exit();
            break;
        case R.id.ll_tosearch:
            Intent intentsearch = new Intent(GroupBuyMainListNewActivity.this, SearchMainActivity.class);
            startActivity(intentsearch);
            ExitApplication.getInstance().exit();
            break;
        case R.id.ll_toshoppingcart:
            Intent intentshoppingcart = new Intent(GroupBuyMainListNewActivity.this, ShoppingcartMainListActivity.class);
            startActivity(intentshoppingcart);
            if (GlobalVarUtil.account.getUID() == null) {
                Intent intentTolongin = new Intent(GroupBuyMainListNewActivity.this, LoginActivity.class);
                startActivity(intentTolongin);
            }
            // ExitApplication.getInstance().exit();//购物车可以返回
            break;
        case R.id.ll_tomycenter:
            Intent intentmycenter = new Intent(GroupBuyMainListNewActivity.this, MycenterMainActivity.class);
            startActivity(intentmycenter);
            if (GlobalVarUtil.account.getUID() == null) {
                Intent intentTolongin = new Intent(GroupBuyMainListNewActivity.this, LoginActivity.class);
                startActivity(intentTolongin);
            }
            ExitApplication.getInstance().exit();
            break;
        case R.id.ll_tomore:
            Intent intentmore = new Intent(GroupBuyMainListNewActivity.this, MoreActivity.class);
            startActivity(intentmore);
            ExitApplication.getInstance().exit();
            break;
        }

    }

    // 获取商品列表信息

    private void postgroupbuylistInfo() {
        this.doAsync(new CallEarliest<Object>() {
            @Override
            public void onCallEarliest() throws Exception {
                // TODO Auto-generated method stub

            }

        }, new Callable<Object>() {
            @Override
            public String call() {
                thread1 = Thread.currentThread();
                String reqUrl;
                Map<String, Object> parameters = new LinkedHashMap<String, Object>();

                reqUrl = getString(R.string.getGroupBuyingGoogsListInfo_http);

                parameters.put("goodsCategoryID", goodsCategoryID);
                parameters.put("perPage", perpage);
                parameters.put("index", index);

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
                System.out.println("====================" + Thread.currentThread());
                System.out.println("code jsonStr=" + jsonStr);
                jsonStr = RegisterActivity.replaceBlank(jsonStr);
                if (jsonStr.equals("-1")) {
                    ViewUtil.showToast(myContext, myContext.getString(R.string.net_exception));
                } else {
                    if (!jsonStr.trim().equals("")) {
                        deal_groupbuylist_data(jsonStr);
                    } else {
                        ViewUtil.removeLoadingAnimation(ivLoading);
                    }
                }
            }
        });
    }

    private void deal_groupbuylist_data(String jsonStr) {
        ViewUtil.removeLoadingAnimation(ivLoading);
        try {
            JSONObject jsonObject;
            jsonObject = new JSONObject(jsonStr);

            List<GoodsEntity> List;

            List = new ArrayList<GoodsEntity>();

            System.out.println("ismore=" + is_more + "=========" + List);
            if (jsonStr.equals("{\"list\":[\"\"]}")) {
                is_over = 1;
                listGroupbuyMain.removeFooterView(footer);
                Toast.makeText(GroupBuyMainListNewActivity.this, "全部加载完！", Toast.LENGTH_SHORT).show();
            } else {
                String jsonList = jsonObject.getString("list");
                Type listType = new TypeToken<List<GoodsEntity>>() {
                }.getType();
                List = new Gson().fromJson(jsonList, listType);
                System.out.println("===new======" + List.get(0).getGoodsAttribute());
            }
            if (is_more == 0) {
                goodsList.clear();
            }
            goodsList.addAll(List);

            if (is_more == 1) {
                is_more = 0;
                // adapter.updata(goodsList);
            } else {
                listGroupbuyMain.setDivider(null);
                adapter = new GroupbuyNewAdapter(getBaseContext(), goodsList);
                listGroupbuyMain.setAdapter(adapter);
            }
            is_loading = 0;
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
