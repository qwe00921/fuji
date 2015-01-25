package com.fsti.fjdicClient.activity.shoppingcart;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fsti.fjdicClient.R;
import com.fsti.fjdicClient.activity.BaseActivity;
import com.fsti.fjdicClient.activity.login.RegisterActivity;
import com.fsti.fjdicClient.activity.mycenter.AddressListActivity;
//import com.fsti.fjdicClient.activity.shoppingMall.ShoppingmallGoodsDetailActivity;
import com.fsti.fjdicClient.adapter.ShoppingcartMainAdapter;
import com.fsti.fjdicClient.adapter.ShoppingcartOrderAdapter;
import com.fsti.fjdicClient.bean.ConsigneeAddressEntity;
import com.fsti.fjdicClient.bean.ExitApplication;
import com.fsti.fjdicClient.bean.ShoppingcartInfoEntity;
import com.fsti.fjdicClient.bean.ShopsDetailInfoEntity;
import com.fsti.fjdicClient.bean.orderInfoEntity;
import com.fsti.fjdicClient.bean.persellerorderEntity;
import com.fsti.fjdicClient.dao.BusinessDao;
import com.fsti.fjdicClient.util.GlobalVarUtil;
import com.fsti.fjdicClient.util.HttpUtil;
import com.fsti.fjdicClient.util.ImageLoaderHelper;
import com.fsti.fjdicClient.util.asyncUtil.CallEarliest;
import com.fsti.fjdicClient.util.asyncUtil.Callable;
import com.fsti.fjdicClient.util.asyncUtil.Callback;
import com.fsti.fjdicClient.util.uppay.Uppay;
import com.fsti.fjdicClient.util.uppay.Uppay_state;
import com.google.gson.Gson;

public class ShoppingcartOrderListActivity extends BaseActivity implements OnClickListener {

    // private ExpandableListView eListShoppingcartMain;
    // private ShoppingcartOrderAdapter adapter;
    private boolean                                  isPay               = false;
    private LinearLayout                             llScroll;
    // private SyncImageLoadUtil syncImageLoadUtil = new SyncImageLoadUtil();
    private LinearLayout                             llNext;
    private RelativeLayout                           rlAddress;
    private TextView                                 tvAdderssShow;
    private Button                                   btnBack;
    private ConsigneeAddressEntity                   addressEntity       = new ConsigneeAddressEntity();

    private List<ShoppingcartInfoEntity>             listShoppingcart    = new ArrayList<ShoppingcartInfoEntity>();
    public static List<List<ShoppingcartInfoEntity>> shoppingcartInfoAll = new ArrayList<List<ShoppingcartInfoEntity>>(); // 二级数据总和
    public static List<ShopsDetailInfoEntity>        shopList            = new ArrayList<ShopsDetailInfoEntity>();

    private TextView                                 tvListGoodscount;
    private TextView                                 tvListTotalPrice;
    private float                                    listTotalPrice      = 0.00f;
    private int                                      listGoodsCount      = 0;

    private String                                   orderID;
    private String                                   pay_result;

    // private List<String> noteList = new ArrayList<String>();
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE |
        // WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.layout_list_shoppingcart_order);
        ExitApplication.getInstance().addActivity(this);
        init();
        getShoppingcartData();

        // eListShoppingcartMain.setDivider(null);//去除dicator(null);//去掉箭头
        // adapter = new ShoppingcartOrderAdapter(this.getBaseContext(),shopList,shoppingcartInfoAll,addressEntity);
        // adapter = new ShoppingcartOrderAdapter(this.getBaseContext(),shopList,shoppingcartInfoAll,addressEntity,ShoppingcartOrderListActivity.this , noteList);
        // expandList();
    }

    // 展开listview
    private void expandList() {
        // TODO Auto-generated method stub
        // eListShoppingcartMain.setAdapter(adapter);//不在这里会造成回到该页时崩溃
        // int groupCount = eListShoppingcartMain.getCount();
        // for (int i=0; i<groupCount; i++) {
        // eListShoppingcartMain.expandGroup(i);
        // }
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        if (GlobalVarUtil.account.getUID() == null) {
            this.finish();
        }
        // getShoppingcartData();
        // expandList();
        // 是否在上一页面进行判断
        if (BusinessDao.get_default_addressID().equals("0")) {
            tvAdderssShow.setText("你还未设定默认收货地址");

        } else {
            addressEntity = BusinessDao.get_default_addressinfo();
            tvAdderssShow.setText(addressEntity.getName() + "        " + addressEntity.getAddress());
            // adapter.setAddress(addressEntity);

        }
        super.onResume();
    }

    @Override
    public void bindEvent() {
        // TODO Auto-generated method stub
        rlAddress.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        // eListShoppingcartMain.setOnGroupClickListener(new OnGroupClickListener() {
        //
        // @Override
        // public boolean onGroupClick(ExpandableListView parent, View v,
        // int groupPosition, long id) {
        // // TODO Auto-generated method stub
        // Log.d("isExpandable", "前"+shopList.get(groupPosition).isExpandable());
        // boolean isExpandable = !shopList.get(groupPosition).isExpandable();
        // shopList.get(groupPosition).setExpandable(isExpandable);
        // Log.d("isExpandable", "后"+shopList.get(groupPosition).isExpandable());
        // adapter.notifyDataSetChanged();
        // return false;
        // }
        // });
    }

    @Override
    public void initValue() {
        // TODO Auto-generated method stub

        btnBack = (Button) findViewById(R.id.btn_shoppingcart_order_back);
        tvAdderssShow = (TextView) findViewById(R.id.tv_shoppingcart_order_address_show);
        rlAddress = (RelativeLayout) findViewById(R.id.rl_shoppingcart_order_address2);
        tvListGoodscount = (TextView) findViewById(R.id.tv_list_shoppingcart_order_goodscount);
        tvListTotalPrice = (TextView) findViewById(R.id.tv_list_shoppingcart_order_toatalprice);
        // 改为ScrollView+LinearLayout实现
        // eListShoppingcartMain = (ExpandableListView) findViewById(R.id.elist_shoppingcart_order);

        llScroll = (LinearLayout) findViewById(R.id.ll_shoppingcart_order);

    }

    private void initllSroll(List<List<ShoppingcartInfoEntity>> shoppingcartInfoAll, List<ShopsDetailInfoEntity> shopList) {
        // TODO Auto-generated method stub
        for (int i = 0; i < shopList.size(); i++) {
            // 此处添加商家布局文件
            LayoutInflater mInflater1 = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = mInflater1.inflate(R.layout.layout_activity_item_shoppingcart_main, null);
            ImageView ivselect = (ImageView) view.findViewById(R.id.iv_item_shoppingcart_main_select);
            ivselect.setVisibility(View.GONE);
            ImageView ivRight = (ImageView) view.findViewById(R.id.iv_item_shoppingcart_shop_right);
            ivRight.setVisibility(View.GONE);
            TextView tvshop = (TextView) view.findViewById(R.id.tv_item_shoppingcart_main_goodsname);
            tvshop.setText(shopList.get(i).getName());
            llScroll.addView(view);
            float price = 0.00f;
            for (int j = 0; j < shoppingcartInfoAll.get(i).size(); j++) {
                ShoppingcartInfoEntity entity = shoppingcartInfoAll.get(i).get(j);
                String freightType = entity.getFreightType();
                String freight = entity.getFreight();
                String[] freightTypeArray = freightType.split("※");
                String[] freightArray = freight.split("※");
                String temp = entity.getPrice();
                price += Float.parseFloat(temp) * entity.getCounts();
                // 此处添加商品布局文件
                LayoutInflater mInflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view2 = mInflater.inflate(R.layout.layout_activity_item_shoppingcart_order_child, null);
                TextView goodsprice = (TextView) view2.findViewById(R.id.tv_item_shoppingcart_order_child_price);
                TextView tvcount = (TextView) view2.findViewById(R.id.tv_item_shoppingcart_order_child_counts);
                TextView tvName = (TextView) view2.findViewById(R.id.tv_item_shoppingcart_order_child_describe);
                ImageView ivGoods = (ImageView) view2.findViewById(R.id.iv_item_shoppingcart_order_child_goods);
                ImageLoaderHelper.displayImage(ivGoods, entity.getImageUrl());
                // syncImageLoadUtil.displayImage(entity.getImageUrl(), ivGoods, myContext);
                if (j == shoppingcartInfoAll.get(i).size() - 1) {
                    RelativeLayout rlFreight = (RelativeLayout) view2.findViewById(R.id.rl_shoppingcart_order_freight);
                    rlFreight.setVisibility(View.VISIBLE);
                    EditText etNote = (EditText) view2.findViewById(R.id.et_item_shoppingcart_order_notedetail);
                    etNote.setVisibility(view.VISIBLE);
                    etNote.addTextChangedListener(new ChildTextChange(etNote, i));

                    TextView tvFreightType = (TextView) view2.findViewById(R.id.tv_item_shoppingcart_order_freight_type);
                    tvFreightType.setVisibility(view.VISIBLE);
                    TextView tvFreightMoney = (TextView) view2.findViewById(R.id.tv_item_shoppingcart_order_freight_money);
                    tvFreightMoney.setVisibility(view.VISIBLE);
                    tvFreightType.setText("" + freightTypeArray[entity.getFreightTypeSelect()]);
                    tvFreightType.setVisibility(view.VISIBLE);
                    if (Float.parseFloat(GlobalVarUtil.minPurchasemoney) == -1.0) {
                        if (ShoppingcartMainAdapter.toFloatTwo(price) >= 100.00f) {
                            tvFreightMoney.setText("商家包邮");
                        } else {
                            tvFreightMoney.setText("" + freightArray[entity.getFreightTypeSelect()] + "元");
                        }
                    } else {
                        if (ShoppingcartMainAdapter.toFloatTwo(price) >= Float.parseFloat(GlobalVarUtil.minPurchasemoney) || ShoppingcartMainAdapter.toFloatTwo(price) >= 100.00f) {
                            tvFreightMoney.setText("商家包邮");
                        } else {
                            tvFreightMoney.setText("" + freightArray[entity.getFreightTypeSelect()]);
                        }
                    }
                    if (i == shopList.size() - 1) {
                        llNext = (LinearLayout) view2.findViewById(R.id.ll_item_shoppingcart_order_next);
                        llNext.setOnClickListener(this);
                        llNext.setVisibility(view.VISIBLE);
                    }
                }
                goodsprice.setText(entity.getPrice() + "元");
                tvcount.setText("x" + entity.getCounts());
                tvName.setText(entity.getName());
                llScroll.addView(view2);
            }
        }
    }

    class ChildTextChange implements TextWatcher {
        private int      position;
        private EditText etNote;

        public ChildTextChange(EditText etNote, int position) {
            // TODO Auto-generated constructor stub
            this.position = position;
            this.etNote = etNote;
        }

        @Override
        public void afterTextChanged(Editable s) {
            // TODO Auto-generated method stub
            String note = s.toString();
            shopList.get(position).setNote(note);
        }

        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // TODO Auto-generated method stub

        }

    }

    // 对列表上方的总价和总数进行填写
    public void totalPriceAndCounts(List<List<ShoppingcartInfoEntity>> shoppingcartInfoAll) {
        listGoodsCount = 0;
        listTotalPrice = 0.00f;
        float shopPrice = 0.00f;
        for (Iterator iterator = shoppingcartInfoAll.iterator(); iterator.hasNext();) {
            List<ShoppingcartInfoEntity> list = (List<ShoppingcartInfoEntity>) iterator.next();
            int firstFreight = 1;
            for (ShoppingcartInfoEntity shoppingcartInfoEntity : list) {
                if (shoppingcartInfoEntity.isSelect()) {
                    listGoodsCount += shoppingcartInfoEntity.getCounts();
                    listTotalPrice += Float.parseFloat(shoppingcartInfoEntity.getPrice()) * shoppingcartInfoEntity.getCounts();
                    shopPrice += Float.parseFloat(shoppingcartInfoEntity.getPrice()) * shoppingcartInfoEntity.getCounts();
                }
            }
            if (Float.parseFloat(GlobalVarUtil.minPurchasemoney) == -1.0) {
                if (ShoppingcartMainAdapter.toFloatTwo(shopPrice) >= 100) {
                } else {
                    if (firstFreight == 1) {
                        firstFreight++;
                        int freightTypeSelect = list.get(0).getFreightTypeSelect();
                        // String freightType = shoppingcartInfoEntity.getFreightType();
                        // String[] freightTypeArray = freightType.split("※");
                        String freight = list.get(0).getFreight();
                        String[] freightArray = freight.split("※");
                        listTotalPrice += Float.parseFloat(freightArray[freightTypeSelect]);
                    }
                }

            } else {
                if (ShoppingcartMainAdapter.toFloatTwo(shopPrice) >= Float.parseFloat(GlobalVarUtil.minPurchasemoney) || ShoppingcartMainAdapter.toFloatTwo(shopPrice) >= 100) {
                } else {
                    if (firstFreight == 1) {
                        firstFreight++;
                        int freightTypeSelect = list.get(0).getFreightTypeSelect();
                        // String freightType = shoppingcartInfoEntity.getFreightType();
                        // String[] freightTypeArray = freightType.split("※");
                        String freight = list.get(0).getFreight();
                        String[] freightArray = freight.split("※");
                        listTotalPrice += Float.parseFloat(freightArray[freightTypeSelect]);
                    }
                }
            }
            shopPrice = 0.00f;
        }
        tvListGoodscount.setText(listGoodsCount + "件");
        tvListTotalPrice.setText(ShoppingcartMainAdapter.toFloatTwo(listTotalPrice) + "元");
    }

    private void getShoppingcartData() {
        // TODO Auto-generat;ed method stub
        shoppingcartInfoAll.clear();
        shopList.clear();
        listTotalPrice = 0.00f;
        listGoodsCount = 0;
        listShoppingcart = BusinessDao.getShoppingcartTableData(GlobalVarUtil.account.getUID());// 正式使用
        for (int i = 0; i < listShoppingcart.size(); i++) {
            if (listShoppingcart.get(i).getToSelectBoolean() == 0) {
                listShoppingcart.get(i).setSelect(true);
            } else {
                listShoppingcart.get(i).setSelect(false);
            }
        }

        // 在for循环中确定有多少商家
        for (Iterator iterator = listShoppingcart.iterator(); iterator.hasNext();) {
            ShoppingcartInfoEntity entity = (ShoppingcartInfoEntity) iterator.next();
            if (entity.isSelect()) {
                ShopsDetailInfoEntity shopsEntity = new ShopsDetailInfoEntity();
                shopsEntity.setId(entity.getShopsID());
                shopsEntity.setName(entity.getShopsName());
                int shopListSize = shopList.size();
                if (shopListSize != 0) {
                    for (int i = 0; i < shopListSize; i++) {
                        int j = i + 1;
                        if (shopList.get(i).getId().equals(shopsEntity.getId())) {
                            j--;
                            break;
                        }
                        if (j == shopListSize) {
                            shopList.add(shopsEntity);
                        }
                    }
                } else {
                    shopList.add(shopsEntity);
                }
            }
        }
        System.out.println("商家数量：   " + shopList.size());
        // 确定每个商家有哪些商品
        for (int i = 0; i < shopList.size(); i++) {

            // noteList.add("");
            List<ShoppingcartInfoEntity> shoppingcartInfoList = new ArrayList<ShoppingcartInfoEntity>();
            for (Iterator iterator = listShoppingcart.iterator(); iterator.hasNext();) {
                ShoppingcartInfoEntity entity = (ShoppingcartInfoEntity) iterator.next();
                if (entity.isSelect()) {
                    if (entity.getShopsID().equals(shopList.get(i).getId())) {
                        shoppingcartInfoList.add(entity);
                        if (!entity.isSelect()) {
                            shopList.get(i).setSelect(false);
                        }
                    }
                }
            }
            try {
                shoppingcartInfoAll.add(shoppingcartInfoList);

            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }
        initllSroll(shoppingcartInfoAll, shopList);
        totalPriceAndCounts(shoppingcartInfoAll);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
        case R.id.rl_shoppingcart_order_address2:
            Intent toAddress = new Intent(ShoppingcartOrderListActivity.this, AddressListActivity.class);
            startActivity(toAddress);
            break;
        case R.id.btn_shoppingcart_order_back:
            this.finish();
            break;
        case R.id.ll_item_shoppingcart_order_next:
            if (addressEntity.getAddress().equals("")) {
                Toast.makeText(myContext, "地址不能为空，请选择地址", 1).show();
            } else {
                if (!isPay) {
                    isPay = true;
                    upload_order();
                } else {
                    Toast.makeText(myContext, "已提交，请稍候。", 1).show();
                }
            }
        }
    }

    public void upload_order() {

        this.doAsync(new CallEarliest<Object>() {
            @Override
            public void onCallEarliest() throws Exception {
                // TODO Auto-generated method stub

            }

        }, new Callable<Object>() {
            @Override
            public String call() {

                String reqUrl = myContext.getString(R.string.UploadOrder_php);

                Map<String, Object> parameters = new LinkedHashMap<String, Object>();

                parameters.put("UID", GlobalVarUtil.account.getUID());
                parameters.put("nickName", GlobalVarUtil.account.getNickName());
                parameters.put("type", shoppingcartInfoAll.get(0).get(0).getType());// 都是商场类型
                Gson gson = new Gson();
                String addressEntityString = gson.toJson(addressEntity);
                parameters.put("address", addressEntityString);

                // 将单个商家订单添加到订单合集中
                List<persellerorderEntity> lists = new ArrayList<persellerorderEntity>();
                for (int i = 0; i < shoppingcartInfoAll.size(); i++) {
                    persellerorderEntity persellerorder = new persellerorderEntity();
                    persellerorder.setShopsID(shopList.get(i).getId());
                    persellerorder.setRemarks(shopList.get(i).getNote());
                    List<orderInfoEntity> list = new ArrayList<orderInfoEntity>();
                    float price = 0.00f;
                    int k = shoppingcartInfoAll.get(i).size();// 商家商品数量
                    for (int j = 0; j < k; j++) {
                        ShoppingcartInfoEntity entity = shoppingcartInfoAll.get(i).get(j);
                        orderInfoEntity orderinfo = new orderInfoEntity();
                        orderinfo.setID(entity.getID());
                        orderinfo.setName(entity.getName());
                        orderinfo.setCounts(entity.getCounts());
                        orderinfo.setPrice(entity.getPrice());
                        orderinfo.setFID(entity.getFID());
                        list.add(orderinfo);
                        String temp = entity.getPrice();
                        price += Float.parseFloat(temp) * entity.getCounts();
                    }
                    if (ShoppingcartMainAdapter.toFloatTwo(price) >= Float.parseFloat(GlobalVarUtil.minPurchasemoney) || ShoppingcartMainAdapter.toFloatTwo(price) >= 100.00f) {
                        persellerorder.setFreight("0");
                    } else {
                        String freightType = shoppingcartInfoAll.get(i).get(0).getFreightType();
                        // String[] freightTypeArray = freightType.split("※");
                        String freight = shoppingcartInfoAll.get(i).get(0).getFreight();
                        String[] freightArray = freight.split("※");
                        persellerorder.setFreight(freightArray[shoppingcartInfoAll.get(i).get(0).getFreightTypeSelect()]);
                    }
                    persellerorder.setList(list);
                    lists.add(persellerorder);
                }

                String listString = gson.toJson(lists);
                parameters.put("list", listString);

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
                ShoppingcartOrderAdapter.isPay = false;
                if (jsonStr.trim().equals("-1") || jsonStr.trim().equals("") || jsonStr.trim().equals(" ")) {
                    Toast.makeText(myContext, "提交订单失败", 1).show();
                    isPay = false;
                } else {
                    try {
                        String orderID = RegisterActivity.replaceBlank(jsonStr);
                        ShoppingcartOrderListActivity.this.orderID = orderID;
                        getTN(orderID);

                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        isPay = false;
                        e.printStackTrace();
                        ShoppingcartOrderAdapter.isPay = false;
                        Toast.makeText(myContext, "提交订单失败", 1).show();
                    }

                }
            }
        });
    }

    public void getTN(String orderIDlist) {
        final String orderIDlist1 = orderIDlist;
        doAsync(new CallEarliest<Object>() {
            @Override
            public void onCallEarliest() throws Exception {
                // TODO Auto-generated method stub

            }

        }, new Callable<Object>() {
            @Override
            public String call() {
                String reqUrl;
                Map<String, Object> parameters = new LinkedHashMap<String, Object>();
                reqUrl = myContext.getString(R.string.GetTN_php);

                // Gson gson =new Gson();
                // String ss = gson.toJson(orderIDlist1);

                parameters.put("orderID", orderIDlist1);
                parameters.put("type", "1");

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
                ShoppingcartOrderAdapter.isPay = false;
                if (jsonStr.equals("-1") || jsonStr.trim().equals("")) {
                    // new Uppay().pay("0",context,(Activity)context);
                    Toast.makeText(myContext, "获取流水号失败", 1).show();
                } else {
                    deleteGoods();// 测试时注销
                    new Uppay().pay(RegisterActivity.replaceBlank(jsonStr), myContext, ShoppingcartOrderListActivity.this);
                }
            }
        });
    }

    public void deleteGoods() {
        // TODO Auto-generated method stub
        for (int i = 0; i < shoppingcartInfoAll.size(); i++) {
            int delete = shoppingcartInfoAll.get(i).size();
            for (int k = 0; k < delete; k++) {
                if (shoppingcartInfoAll.get(i).get(k).isSelect()) {
                    BusinessDao.deleteShoppingcartTableData(shoppingcartInfoAll.get(i).get(k));
                }
            }
        }
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
        Intent intent = new Intent(ShoppingcartOrderListActivity.this, Uppay_state.class);
        intent.putExtra("orderID", orderID);
        intent.putExtra("pay_result", pay_result);
        startActivity(intent);
        ExitApplication.getInstance().exit();
    }
}
