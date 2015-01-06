package com.fsti.fjdicClient.activity.groupBuy;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.fsti.fjdicClient.R;
import com.fsti.fjdicClient.activity.BaseActivity;
import com.fsti.fjdicClient.activity.home.HomeMainActivity;
import com.fsti.fjdicClient.activity.login.LoginActivity;
import com.fsti.fjdicClient.activity.more.MoreActivity;
import com.fsti.fjdicClient.activity.mycenter.MycenterMainActivity;
import com.fsti.fjdicClient.activity.search.SearchMainActivity;
//import com.fsti.fjdicClient.activity.shoppingMall.FirmOrderActivity;
import com.fsti.fjdicClient.activity.shoppingcart.ShoppingcartMainListActivity;
import com.fsti.fjdicClient.bean.ConsigneeAddressEntity;
import com.fsti.fjdicClient.bean.ExitApplication;
import com.fsti.fjdicClient.bean.GoodsEntity;
import com.fsti.fjdicClient.bean.PayType;
import com.fsti.fjdicClient.dao.BusinessDao;
import com.fsti.fjdicClient.util.GlobalVarUtil;
import com.fsti.fjdicClient.util.HttpUtil;
import com.fsti.fjdicClient.util.ViewUtil;
import com.fsti.fjdicClient.util.asyncUtil.CallEarliest;
import com.fsti.fjdicClient.util.asyncUtil.Callable;
import com.fsti.fjdicClient.util.asyncUtil.Callback;
import com.fsti.fjdicClient.util.uppay.Uppay;
import com.fsti.fjdicClient.util.uppay.Uppay_state;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 生活团 确定订单 提交订单 选择支付
 * 
 * @author
 */
public class GroupBuyFirmOrderActivity extends BaseActivity implements OnClickListener {

    private String   orderID;

    private Button   btgroupbuyfirmorderBack;
    private TextView tvgroupbuyfirmorderGoodsname;
    private TextView tvgroupbuyfirmorderPerprice;
    private TextView tvgroupbuyfirmorderAmount;
    private TextView tvgroupbuyfirmorderTotalprice;
    private TextView tvgroupbuyfirmorderBuyername;
    private TextView tvgroupbuyfirmorderAddress;
    private TextView tvgroupbuyfirmorderPhone;
    private TextView tvgroupbuyfirmorderPostcode;
    private TextView tvgroupbuyfirmorderState;
    private Button   btgroupbuyfirmorderNextstep;
    private String   goodsID         = "0";        // 商品号，用来从网络获取物品信息
    private TableRow tablerow1;
    private TableRow tablerow2;
    private TableRow tablerow3;
    private TableRow tablerow4;
    private String   mgoodsAttribute = "0";
    private LinearLayout lyCashPay, lyOnlinePay, lyUnionPay;
    private LinearLayout tohome;
    private LinearLayout tosearch;
    private LinearLayout toshoppingcart;
    private LinearLayout tomycenter;
    private LinearLayout tomore;
    // private RelativeLayout bottommenu;
    private String       pay_result;
    private boolean      isBuy = false;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_groupbuy_firmorder);
        ExitApplication.getInstance().addActivity(this);

        init();
    }

    @Override
    public void bindEvent() {
        btgroupbuyfirmorderBack.setOnClickListener(this);
        btgroupbuyfirmorderNextstep.setOnClickListener(this);
        lyUnionPay.setOnClickListener(this);
        lyOnlinePay.setOnClickListener(this);
        lyCashPay.setOnClickListener(this);
        // tohome.setOnClickListener(this);
        // tosearch.setOnClickListener(this);
        // toshoppingcart.setOnClickListener(this);
        // tomycenter.setOnClickListener(this);
        // tomore.setOnClickListener(this);
    }

    @Override
    public void initValue() {
        btgroupbuyfirmorderBack = (Button) findViewById(R.id.btn_groupbuy_firmorder_back);
        btgroupbuyfirmorderNextstep = (Button) findViewById(R.id.btn_groupbuy_firmorder_nextstep);
        tvgroupbuyfirmorderGoodsname = (TextView) findViewById(R.id.tv_groupbuy_firmorder_goodsname);
        tvgroupbuyfirmorderPerprice = (TextView) findViewById(R.id.tv_groupbuy_firmorder_perprice);
        tvgroupbuyfirmorderAmount = (TextView) findViewById(R.id.tv_groupbuy_firmorder_amount);
        tvgroupbuyfirmorderTotalprice = (TextView) findViewById(R.id.tv_groupbuy_firmorder_totalprice);
        tvgroupbuyfirmorderBuyername = (TextView) findViewById(R.id.tv_groupbuy_firmorder_buyername);
        tvgroupbuyfirmorderAddress = (TextView) findViewById(R.id.tv_groupbuy_firmorder_address);
        tvgroupbuyfirmorderPhone = (TextView) findViewById(R.id.tv_groupbuy_firmorder_phone);
        tvgroupbuyfirmorderPostcode = (TextView) findViewById(R.id.tv_groupbuy_firmorder_postcode);
        tvgroupbuyfirmorderState = (TextView) findViewById(R.id.tv_groupbuy_firmorder_state);
        tablerow1 = (TableRow) findViewById(R.id.tr_groupbuy_firmorder_tablerow1);
        tablerow2 = (TableRow) findViewById(R.id.tr_groupbuy_firmorder_tablerow2);
        tablerow3 = (TableRow) findViewById(R.id.tr_groupbuy_firmorder_tablerow3);
        tablerow4 = (TableRow) findViewById(R.id.tr_groupbuy_firmorder_tablerow4);

        Intent intent = this.getIntent();
        orderID = intent.getStringExtra("orderID");
        GoodsEntity goodsEntity = (GoodsEntity) intent.getSerializableExtra("goodsEntity");
        goodsID = goodsEntity.getID();
        tvgroupbuyfirmorderGoodsname.setText(goodsEntity.getName());
        tvgroupbuyfirmorderPerprice.setText(goodsEntity.getDiscountedPrice() + "元");
        tvgroupbuyfirmorderAmount.setText(intent.getStringExtra("num"));
        tvgroupbuyfirmorderTotalprice.setText(count_totalprice(goodsEntity) + "元");

        // mgoodsAttribute=goodsEntity.getGoodsAttribute();
        if (mgoodsAttribute.equals("1")) {
            tablerow1.setVisibility(View.GONE);
            tablerow2.setVisibility(View.GONE);
            tvgroupbuyfirmorderPhone.setText(intent.getStringExtra("phone"));
            tablerow4.setVisibility(View.GONE);
        } else {
            ConsigneeAddressEntity addressEntity = BusinessDao.get_addressinfo(BusinessDao.get_default_addressID());
            tvgroupbuyfirmorderBuyername.setText(addressEntity.getName());
            tvgroupbuyfirmorderAddress.setText(addressEntity.getAddress());
            tvgroupbuyfirmorderPhone.setText(addressEntity.getTelephone());
            tvgroupbuyfirmorderPostcode.setText(addressEntity.getPostCode());
        }

        tvgroupbuyfirmorderState.setText(intent.getStringExtra("note"));
        lyUnionPay = (LinearLayout) findViewById(R.id.ly_space3);
        lyOnlinePay = (LinearLayout) findViewById(R.id.ly_space4);
        lyCashPay = (LinearLayout) findViewById(R.id.ly_space5);
        cbUnionPay = (RadioButton) findViewById(R.id.rb_pay_yl);
        cbAliPay = (RadioButton) findViewById(R.id.rb_pay_zfb);
        cbCashPay = (RadioButton) findViewById(R.id.rb_pay_hdfk);
        // LayoutInflater mInflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // View view2 = mInflater.inflate(R.layout.layout_item_bottom_menu, null);
        // tohome = (LinearLayout) view2.findViewById(R.id.ll_tohome);
        // tosearch = (LinearLayout) view2.findViewById(R.id.ll_tosearch);
        // toshoppingcart = (LinearLayout) view2.findViewById(R.id.ll_toshoppingcart);
        // tomycenter = (LinearLayout) view2.findViewById(R.id.ll_tomycenter);
        // tomore = (LinearLayout) view2.findViewById(R.id.ll_tomore);
        // bottommenu = (RelativeLayout) findViewById(R.id.rl_groupbuy_firmorder_bottommenu);
        // bottommenu.addView(view2);
        // ViewGroup.LayoutParams lp = view2.getLayoutParams();
        // lp.width = lp.FILL_PARENT;
        // view2.setLayoutParams(lp);
        getpaytype();

    }

    private String count_totalprice(GoodsEntity gEntity) {

        return String.valueOf((float) Math.round(Float.parseFloat(gEntity.getDiscountedPrice()) * new Integer(tvgroupbuyfirmorderAmount.getText().toString()) * 100) / 100);
    }

    @Override
    public void onClick(View arg0) {
        // TODO Auto-generated method stub
        switch (arg0.getId()) {
        case R.id.btn_groupbuy_firmorder_back:
            // Intent intent = new Intent(this , SubmitOrderActivity.class);
            // startActivity(intent);
            this.finish();
            break;

        case R.id.btn_groupbuy_firmorder_nextstep:
            if (!isBuy) {
                isBuy = true;
                getTN();
            } else {
                Toast.makeText(GroupBuyFirmOrderActivity.this, "已提交，请稍候。", 1).show();
            }
            break;
        case R.id.ll_tohome:
            Intent intenthome = new Intent(GroupBuyFirmOrderActivity.this, HomeMainActivity.class);
            startActivity(intenthome);
            ExitApplication.getInstance().exit();
            break;
        case R.id.ll_tosearch:
            Intent intentsearch = new Intent(GroupBuyFirmOrderActivity.this, SearchMainActivity.class);
            startActivity(intentsearch);
            ExitApplication.getInstance().exit();
            break;
        case R.id.ll_toshoppingcart:
            Intent intentshoppingcart = new Intent(GroupBuyFirmOrderActivity.this, ShoppingcartMainListActivity.class);
            startActivity(intentshoppingcart);
            if (GlobalVarUtil.account.getUID() == null) {
                Intent intentTolongin = new Intent(GroupBuyFirmOrderActivity.this, LoginActivity.class);
                startActivity(intentTolongin);

            }
            // ExitApplication.getInstance().exit();//购物车可以返回
            break;
        case R.id.ll_tomycenter:
            Intent intentmycenter = new Intent(GroupBuyFirmOrderActivity.this, MycenterMainActivity.class);
            startActivity(intentmycenter);
            if (GlobalVarUtil.account.getUID() == null) {
                Intent intentTolongin = new Intent(GroupBuyFirmOrderActivity.this, LoginActivity.class);
                startActivity(intentTolongin);

            }
            ExitApplication.getInstance().exit();
            break;
        case R.id.ll_tomore:
            Intent intentmore = new Intent(GroupBuyFirmOrderActivity.this, MoreActivity.class);
            startActivity(intentmore);
            ExitApplication.getInstance().exit();
            break;
        case R.id.ly_space4:
            payType = 3;
            clearCheckState();
            cbAliPay.setChecked(true);
            break;
        case R.id.ly_space3:
            payType = 2;
            clearCheckState();
            cbUnionPay.setChecked(true);
            break;
        case R.id.ly_space5:
            payType = 1;
            clearCheckState();
            cbCashPay.setChecked(true);
            break;

        }

    }

    // 1=货到付款 3=支付宝 2=银联在线
    private int payType = 2;
    private RadioButton cbUnionPay, cbAliPay, cbCashPay;

    private void clearCheckState() {
        cbUnionPay.setChecked(false);
        cbAliPay.setChecked(false);
        cbCashPay.setChecked(false);
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
        Intent intent = new Intent(GroupBuyFirmOrderActivity.this, Uppay_state.class);
        intent.putExtra("orderID", orderID);
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

                parameters.put("orderID", orderID);
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
                isBuy = false;
                System.out.println("code jsonStr=" + jsonStr);
                if (jsonStr.equals("-1") || jsonStr.trim().equals("")) {
                    Toast.makeText(GroupBuyFirmOrderActivity.this, "获取支付流水号失败", 1).show();
                } else {
                    new Uppay().pay(jsonStr.trim(), GroupBuyFirmOrderActivity.this, GroupBuyFirmOrderActivity.this);
                    // AlixPay alixPay = new AlixPay(GroupBuyFirmOrderActivity.this);
                    // alixPay.pay(product, orderNumber, price, notifyUrl);
                }
            }
        });
    }

    private void getpaytype() {
        this.doAsync(new CallEarliest<Object>() {
            @Override
            public void onCallEarliest() throws Exception {
            }

        }, new Callable<Object>() {
            @Override
            public String call() {
                String reqUrl = getString(R.string.GetPaytype_php);

                Map<String, Object> parameters = new LinkedHashMap<String, Object>();
                // String updatetime = "";
                // parameters.put("updatetime", updatetime);
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
                        TypeToken<List<PayType>> token = new TypeToken<List<PayType>>() {
                        };
                        List<PayType> paylist = new Gson().fromJson(jsonStr, token.getType());
                        if (paylist != null) {
                            for (int i = 0; i < paylist.size(); i++) {
                                // int id = (int)(paylist.get(i).getId());
                                switch (paylist.get(i).getId().intValue()) {
                                case 1://
                                    lyCashPay.setVisibility(View.VISIBLE);
                                    break;
                                case 2:
                                    lyUnionPay.setVisibility(View.VISIBLE);
                                    break;
                                case 3:
                                    lyOnlinePay.setVisibility(View.VISIBLE);
                                    break;

                                default:
                                    break;
                                }
                            }
                        }

                    } catch (Exception e) {
                        Log.e("FirstLevelSortActivity", e.toString());
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
