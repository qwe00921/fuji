package com.fsti.fjdicClient.activity.mycenter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fsti.fjdicClient.ApplicationUtil;
import com.fsti.fjdicClient.R;
import com.fsti.fjdicClient.activity.BaseActivity;
import com.fsti.fjdicClient.activity.groupBuy.GroupBuyGoodsDetailActivity;
import com.fsti.fjdicClient.activity.home.HomeMainActivity;
import com.fsti.fjdicClient.activity.login.LoginActivity;
import com.fsti.fjdicClient.activity.login.RegisterActivity;
import com.fsti.fjdicClient.activity.more.MoreActivity;
import com.fsti.fjdicClient.activity.search.SearchMainActivity;
//import com.fsti.fjdicClient.activity.shoppingMall.ShoppingmallGoodsDetailActivity;
import com.fsti.fjdicClient.activity.shoppingcart.ShoppingcartMainListActivity;
import com.fsti.fjdicClient.adapter.ShoppingcartMainAdapter;
import com.fsti.fjdicClient.bean.ExitApplication;
import com.fsti.fjdicClient.bean.MoreOrderInfoEntity;
import com.fsti.fjdicClient.bean.Order;
import com.fsti.fjdicClient.bean.OrderGoodsInfoEntity;
import com.fsti.fjdicClient.util.GlobalVarUtil;
import com.fsti.fjdicClient.util.HttpUtil;
import com.fsti.fjdicClient.util.ImageLoaderHelper;
import com.fsti.fjdicClient.util.ViewUtil;
import com.fsti.fjdicClient.util.asyncUtil.CallEarliest;
import com.fsti.fjdicClient.util.asyncUtil.Callable;
import com.fsti.fjdicClient.util.asyncUtil.Callback;
import com.fsti.fjdicClient.util.uppay.Uppay;
import com.fsti.fjdicClient.util.uppay.Uppay_state;

//订单详情
public class Orderinfo extends BaseActivity implements OnClickListener {
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

    private Thread                     thread1;
    private Thread                     thread2;
    private Button                     btnback;
    private Button                     btnbutton;
    private TextView                   tvtotalprice;
    private TextView                   tvfreightprrice;
    private TextView                   tvfreighttype;
    private LinearLayout               lvordergoods;
    private TextView                   tvbuytime;
    private TextView                   tvmessage;
    private Order                      order;
    private String                     buttonname;
    private List<OrderGoodsInfoEntity> goodsList;
    private LinearLayout               lvmoreorderinfo;
    private LinearLayout               tohome;
    private LinearLayout               tosearch;
    private LinearLayout               toshoppingcart;
    private LinearLayout               tomycenter;
    private LinearLayout               tomore;
    private RelativeLayout             bottommenu;
    private String                     manageType   = "";
    private ImageView                  ivLoading;
    private String                     pay_result;
    private boolean                    isPay        = false;
    private List<MoreOrderInfoEntity>  moreinfolist = new ArrayList<MoreOrderInfoEntity>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_mycentenr_orderinfo);
        ExitApplication.getInstance().addActivity(this);
        order = (Order) this.getIntent().getSerializableExtra("order");
        buttonname = this.getIntent().getStringExtra("buttonname");
        init();

    }

    @Override
    public void bindEvent() {
        // TODO Auto-generated method stub
        btnback.setOnClickListener(this);
        btnbutton.setOnClickListener(this);
        tohome.setOnClickListener(this);
        tosearch.setOnClickListener(this);
        toshoppingcart.setOnClickListener(this);
        tomycenter.setOnClickListener(this);
        tomore.setOnClickListener(this);
    }

    @Override
    public void initValue() {
        // TODO Auto-generated method stub
        btnback = (Button) findViewById(R.id.btn_mycenter_orderinfo_back);
        btnbutton = (Button) findViewById(R.id.btn_mycentenr_orderinfo_button);
        tvtotalprice = (TextView) findViewById(R.id.tv_mycentenr_orderinfo_totalprice);
        tvfreightprrice = (TextView) findViewById(R.id.tv_mycentenr_orderinfo_freightprice);
        tvfreighttype = (TextView) findViewById(R.id.tv_mycenter_orderinfo_freighttype);
        lvordergoods = (LinearLayout) findViewById(R.id.list_mycenter_orderinfo_order);
        tvbuytime = (TextView) findViewById(R.id.tv_mycenter_orderinfo_buytime2);

        lvmoreorderinfo = (LinearLayout) findViewById(R.id.list_mycenter_orderinfo_lvmoreorderinfo);

        tvmessage = (TextView) findViewById(R.id.tv_mycenter_orderinfo_message2);
        LayoutInflater mInflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view2 = mInflater.inflate(R.layout.layout_item_bottom_menu, null);
        tohome = (LinearLayout) view2.findViewById(R.id.ll_tohome);
        tosearch = (LinearLayout) view2.findViewById(R.id.ll_tosearch);
        toshoppingcart = (LinearLayout) view2.findViewById(R.id.ll_toshoppingcart);
        tomycenter = (LinearLayout) view2.findViewById(R.id.ll_tomycenter);
        tomore = (LinearLayout) view2.findViewById(R.id.ll_tomore);
        bottommenu = (RelativeLayout) findViewById(R.id.rl_mycenter_orderinfo_bottommenu);
        bottommenu.addView(view2);
        ViewGroup.LayoutParams lp = view2.getLayoutParams();
        lp.width = lp.FILL_PARENT;
        view2.setLayoutParams(lp);
        ivLoading = (ImageView) findViewById(R.id.iv_mycenter_orderinfo_loading);
        ivLoading.setVisibility(View.VISIBLE);
        ViewUtil.addLoadingAnimation(ApplicationUtil.myContext, ivLoading);
        showview();
        get_orderinfo();

    }

    private void showview() {
        btnbutton.setText(buttonname);
        tvtotalprice.setText(String.valueOf(count_totalprice()) + "元");
        tvfreightprrice.setText("(快递:" + order.getFreightPrice() + "元)");
        tvbuytime.setText(order.getOrderTime());
        // tvfreighttype.setText(order.getFreightType());

        /*
         * lvordergoods.setDivider(null); MycenterGoodsCommentsAdapter adapter = new MycenterGoodsCommentsAdapter(getBaseContext(), order); adapter.set_activity(6,Orderinfo.this); int totalHeight = adapter.getCount()*100;//100为事先计算好的，每个item的高度 ViewGroup.LayoutParams params = lvordergoods.getLayoutParams(); params.height = totalHeight + (lvordergoods.getDividerHeight() * (adapter.getCount() - 1));
         * lvordergoods.setLayoutParams(params); lvordergoods.setAdapter(adapter);
         */
        creat_img_view();
        // creat_newUI();//显示订单其他信息

    }

    private void creat_img_view() {
        /*
         * goodsList = new ArrayList<OrderGoodsInfoEntity>(); OrderGoodsInfoEntity goodsEntity ; for(int q=0;q<order.getList().size();q++){ goodsEntity = new OrderGoodsInfoEntity(); goodsEntity.setID(order.getList().get(q).getID()); goodsEntity.setName(order.getList().get(q).getName()); goodsEntity.setPrice(order.getList().get(q).getPrice()); goodsList.add(goodsEntity); }
         */
        goodsList = order.getList();
        LayoutInflater mInflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view;
        for (int q = 0; q < goodsList.size(); q++) {
            view = new View(this);
            view = mInflater.inflate(R.layout.layout_activity_item_mycenter_goodscomments, null);
            TextView tvIntro = (TextView) view.findViewById(R.id.tv_item_mycenter_goodscomments_main_describe);
            TextView tvprice = (TextView) view.findViewById(R.id.tv_item_mycenter_goodscomments_main_price);
            TextView tvAmount = (TextView) view.findViewById(R.id.tv_item_mycenter_goodscomments_main_amount);
            ImageView ivGoods = (ImageView) view.findViewById(R.id.iv_item_mycenter_goodscomments_goods);
            ImageView ivSpline = (ImageView) view.findViewById(R.id.iv_item_mycenter_goodscomments_spline);
            RelativeLayout rlayout = (RelativeLayout) view.findViewById(R.id.rl_item_mycenter_goodscomments_main);
            tvIntro.setText(goodsList.get(q).getName());
            tvprice.setText(goodsList.get(q).getPrice() + "元");
            tvAmount.setText("x" + goodsList.get(q).getAmount());
            tvAmount.setVisibility(View.VISIBLE);
            ImageLoaderHelper.displayImage(ivGoods, goodsList.get(q).getImageUrl());
            // new SyncImageLoadUtil().displayImage(goodsList.get(q).getImageUrl() , ivGoods, myContext);
            ivSpline.setVisibility(View.VISIBLE);
            if (q == (goodsList.size() - 1)) {
                ivSpline.setVisibility(View.INVISIBLE);
            }
            lvordergoods.addView(view);
        }
    }

    /*
     * private void creat_newUI(){ View view; for(int q=0;q<10;q++){ view = new View(this); this.getLayoutInflater(); view = LayoutInflater.from(this).inflate(R.layout.layout_item_mycenter_more_orderinfo, null); TextView name=(TextView) view.findViewById(R.id.tv_mycenter_orderinfo_tvmoreorderinfo1); TextView value=(TextView) view.findViewById(R.id.tv_mycenter_orderinfo_tvmoreorderinfo2);
     * name.setText("fttert"+q); value.setText("siuhs"+q); lvmoreorderinfo.addView(view); } }
     */

    private float count_totalprice() {
        float freight = Integer.parseInt(order.getFreightPrice());
        float totalprice = freight;
        OrderGoodsInfoEntity ordergoodsinfo = new OrderGoodsInfoEntity();
        for (int q = 0; q < order.getList().size(); q++) {
            ordergoodsinfo = order.getList().get(q);
            if (ordergoodsinfo.getPrice() == null || ordergoodsinfo.getPrice().equals("null") || ordergoodsinfo.getPrice().equals("")) {
                ordergoodsinfo.setPrice("0");
                totalprice += Float.parseFloat(ordergoodsinfo.getPrice());
            } else {
                totalprice += Float.parseFloat(ordergoodsinfo.getPrice()) * ordergoodsinfo.getAmount();
            }
        }
        return ShoppingcartMainAdapter.toFloatTwo(totalprice);
    }

    @Override
    public void onClick(View arg0) {
        // TODO Auto-generated method stub
        switch (arg0.getId()) {
        case R.id.btn_mycenter_orderinfo_back:
            this.finish();
            break;
        case R.id.btn_mycentenr_orderinfo_button:

            if (buttonname.equals("确认\n付款")) {
                if (!isPay) {
                    isPay = true;
                    getTN();
                } else {
                    Toast.makeText(myContext, "已提交，请稍候。", 1).show();
                }
            } else if (buttonname.equals("提醒\n发货")) {
                manageType = "DeliverOrder";
                manage_order(order.getOrderID(), order.getOrderType());
            } else if (buttonname.equals("确认\n收货")) {
                manageType = "ConfirmOrder";
                manage_order(order.getOrderID(), order.getOrderType());
            } else {
                Intent intent = new Intent(myContext, GoodsCommentsListActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("order", order);
                intent.putExtras(bundle);
                startActivity(intent);
            }
            break;
        case R.id.ll_tohome:
            Intent intenthome = new Intent(Orderinfo.this, HomeMainActivity.class);
            startActivity(intenthome);
            ExitApplication.getInstance().exit();
            break;
        case R.id.ll_tosearch:
            Intent intentsearch = new Intent(Orderinfo.this, SearchMainActivity.class);
            startActivity(intentsearch);
            ExitApplication.getInstance().exit();
            break;
        case R.id.ll_toshoppingcart:
            Intent intentshoppingcart = new Intent(Orderinfo.this, ShoppingcartMainListActivity.class);
            startActivity(intentshoppingcart);
            if (GlobalVarUtil.account.getUID() == null) {
                Intent intentTolongin = new Intent(Orderinfo.this, LoginActivity.class);
                startActivity(intentTolongin);
            }
            // ExitApplication.getInstance().exit();//购物车可以回退
            break;
        case R.id.ll_tomycenter:
            Intent intentmycenter = new Intent(Orderinfo.this, MycenterMainActivity.class);
            startActivity(intentmycenter);
            if (GlobalVarUtil.account.getUID() == null) {
                Intent intentTolongin = new Intent(Orderinfo.this, LoginActivity.class);
                startActivity(intentTolongin);
            }
            ExitApplication.getInstance().exit();
            break;
        case R.id.ll_tomore:
            Intent intentmore = new Intent(Orderinfo.this, MoreActivity.class);
            startActivity(intentmore);
            ExitApplication.getInstance().exit();
            break;

        }
    }

    // 订单操作

    public void manage_order(final String orderID, final String orderType) {
        this.doAsync(new CallEarliest<Object>() {
            @Override
            public void onCallEarliest() throws Exception {
                // TODO Auto-generated method stub

            }

        }, new Callable<Object>() {
            @Override
            public String call() {

                String reqUrl = myContext.getString(R.string.OrderManage_php);

                Map<String, Object> parameters = new LinkedHashMap<String, Object>();

                parameters.put("UID", GlobalVarUtil.account.getUID());
                // parameters.put("UID", 1);// 测试用的
                parameters.put("orderID", orderID);
                parameters.put("orderType", orderType);
                parameters.put("ManageType", manageType);

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
                RegisterActivity.replaceBlank(jsonStr);
                if (jsonStr.trim().equals("")) {
                    manage_order(orderID, orderType);
                } else if (jsonStr.trim().equals("1")) {
                    Toast.makeText(Orderinfo.this, buttonname.substring(0, 2) + buttonname.substring(3, buttonname.length()) + "成功", 1).show();
                } else {
                    Toast.makeText(Orderinfo.this, buttonname.substring(0, 2) + buttonname.substring(3, buttonname.length()) + "失败", 1).show();
                }

            }
        });
    }

    // 获取商品详情

    public void get_goodsinfo(OrderGoodsInfoEntity ordergoodsinfo) {
        if (Integer.parseInt(order.getOrderType()) == 2) {
            Intent intent = new Intent(Orderinfo.this, GroupBuyGoodsDetailActivity.class);
            intent.putExtra("goodsID", ordergoodsinfo.getID());
            startActivity(intent);
        }
        // else{
        // Intent intent = new Intent(Orderinfo.this,ShoppingmallGoodsDetailActivity.class);
        // intent.putExtra("ID", ordergoodsinfo.getID());
        // intent.putExtra("type",Integer.parseInt(order.getOrderType()));
        // startActivity(intent);
        // }
    }

    // 获取订单详情

    private void get_orderinfo() {
        this.doAsync(new CallEarliest<Object>() {
            @Override
            public void onCallEarliest() throws Exception {
                // TODO Auto-generated method stub

            }

        }, new Callable<Object>() {
            @Override
            public String call() {
                thread1 = Thread.currentThread();
                String reqUrl = getString(R.string.GetOrderInfo_php);

                Map<String, Object> parameters = new LinkedHashMap<String, Object>();

                parameters.put("orderID", order.getOrderID());
                parameters.put("orderType", order.getOrderType());

                String jsonStr = "";
                try {
                    jsonStr = HttpUtil.postData(reqUrl, parameters, GlobalVarUtil.ENCODING);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    ViewUtil.showToast(myContext, "很抱歉，返回数据异常。");
                    tvfreighttype.setText("获取失败");
                    tvmessage.setText("获取失败");
                    ViewUtil.removeLoadingAnimation(ivLoading);
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
                ViewUtil.removeLoadingAnimation(ivLoading);
                System.out.println("code jsonStr=" + jsonStr);
                jsonStr = RegisterActivity.replaceBlank(jsonStr);
                if (jsonStr.equals("") || jsonStr == null || jsonStr.equals("-1") || jsonStr == "null") {

                } else {

                    try {
                        JSONObject json = new JSONObject(jsonStr);
                        tvmessage.setText(json.getString("orderContent"));
                        tvfreighttype.setText(json.getString("freightType") + "元");
                        // JSONArray array =json.getJSONArray("orderInfo");
                        // String arrayString ="{\"1\":\"1\",\"2\":\"2\"}";
                        String arrayString = json.getString("orderInfo");
                        // System.out.println("tttttttttttttttttttt" );
                        if ((!arrayString.equals("")) && arrayString != null && (!arrayString.equals("null"))) {
                            lvmoreorderinfo.setVisibility(View.VISIBLE);
                            View view;
                            int q = -1;
                            arrayString = arrayString.substring(1, arrayString.length() - 1);
                            while ((q = arrayString.indexOf(",")) != -1) {
                                String rstring = arrayString.substring(0, q);
                                arrayString = arrayString.substring(q + 1, arrayString.length());
                                // System.out.println("tttttttttttttttttttt"+arrayString );

                                int qq = rstring.indexOf(":");
                                String name = rstring.substring(1, qq - 1);
                                String value = rstring.substring(qq + 2, rstring.length() - 1);
                                view = new View(Orderinfo.this);
                                Orderinfo.this.getLayoutInflater();
                                view = LayoutInflater.from(Orderinfo.this).inflate(R.layout.layout_item_mycenter_more_orderinfo, null);

                                TextView tvname = (TextView) view.findViewById(R.id.tv_mycenter_orderinfo_tvmoreorderinfo1);
                                TextView tvvalue = (TextView) view.findViewById(R.id.tv_mycenter_orderinfo_tvmoreorderinfo2);

                                tvname.setText(name);
                                tvvalue.setText(value);
                                lvmoreorderinfo.addView(view);
                            }
                            int p = arrayString.indexOf(":");
                            String name2 = arrayString.substring(1, p - 1);
                            String value2 = arrayString.substring(p + 2, arrayString.length() - 1);
                            view = new View(Orderinfo.this);
                            Orderinfo.this.getLayoutInflater();
                            view = LayoutInflater.from(Orderinfo.this).inflate(R.layout.layout_item_mycenter_more_orderinfo, null);

                            TextView tvname = (TextView) view.findViewById(R.id.tv_mycenter_orderinfo_tvmoreorderinfo1);
                            TextView tvvalue = (TextView) view.findViewById(R.id.tv_mycenter_orderinfo_tvmoreorderinfo2);

                            tvname.setText(name2);
                            tvvalue.setText(value2);
                            lvmoreorderinfo.addView(view);
                        }

                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }

            }

        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        /*************************************************
         * 步骤3：处理银联手机支付控件返回的支付结果
         ************************************************/
        System.out.println(" 处理银联手机支付控件返回的支付结果");
        if (data == null) {
            return;
        }
        /*
         * 支付控件返回字符串:success、fail、cancel 分别代表支付成功，支付失败，支付取消
         */

        pay_result = data.getExtras().getString("pay_result");
        Intent intent = new Intent(Orderinfo.this, Uppay_state.class);

        // String ss = "[\""+orderID+"\"]";//测试

        intent.putExtra("orderID", "[\"" + order.getOrderID() + "\"]");
        intent.putExtra("pay_result", pay_result);

        startActivity(intent);
        ExitApplication.getInstance().exit();
    }

    // 获取TN号

    private void getTN() {
        this.doAsync(new CallEarliest<Object>() {
            @Override
            public void onCallEarliest() throws Exception {
                // TODO Auto-generated method stub

            }

        }, new Callable<Object>() {
            @Override
            public String call() {
                String reqUrl;
                Map<String, Object> parameters = new LinkedHashMap<String, Object>();

                reqUrl = getString(R.string.GetTN_php);

                parameters.put("orderID", "[\"" + order.getOrderID() + "\"]");
                parameters.put("type", order.getOrderType());

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
                isPay = false;
                System.out.println("code jsonStr=" + jsonStr);
                if (jsonStr.equals("-1") || jsonStr.trim().equals("")) {
                    Toast.makeText(Orderinfo.this, "获取支付流水号失败", 1).show();
                } else {
                    new Uppay().pay(jsonStr.trim(), Orderinfo.this, Orderinfo.this);
                }
            }
        });
    }

}
