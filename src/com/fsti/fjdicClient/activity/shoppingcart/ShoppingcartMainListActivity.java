package com.fsti.fjdicClient.activity.shoppingcart;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fsti.fjdicClient.R;
import com.fsti.fjdicClient.activity.BaseActivity;
import com.fsti.fjdicClient.activity.home.HomeMainActivity;
import com.fsti.fjdicClient.activity.login.LoginActivity;
import com.fsti.fjdicClient.activity.login.RegisterActivity;
import com.fsti.fjdicClient.activity.more.MoreActivity;
import com.fsti.fjdicClient.activity.mycenter.MycenterMainActivity;
import com.fsti.fjdicClient.activity.search.SearchMainActivity;
//import com.fsti.fjdicClient.activity.shoppingMall.ShoppingmallGoodsDetailActivity;
import com.fsti.fjdicClient.adapter.ShoppingcartMainAdapter;
import com.fsti.fjdicClient.bean.ExitApplication;
import com.fsti.fjdicClient.bean.GoodsEntity;
import com.fsti.fjdicClient.bean.ShoppingcartInfoEntity;
import com.fsti.fjdicClient.bean.ShopsDetailInfoEntity;
import com.fsti.fjdicClient.dao.BusinessDao;
import com.fsti.fjdicClient.util.GlobalVarUtil;
import com.fsti.fjdicClient.util.HttpUtil;
import com.fsti.fjdicClient.util.ViewUtil;
import com.fsti.fjdicClient.util.asyncUtil.CallEarliest;
import com.fsti.fjdicClient.util.asyncUtil.Callable;
import com.fsti.fjdicClient.util.asyncUtil.Callback;

public class ShoppingcartMainListActivity extends BaseActivity implements OnClickListener {

    private Context                            context;
    // 收藏商品使用
    private GoodsEntity                        goodsEntity          = new GoodsEntity();
    private int                                collectCounts        = 0;
    private int                                collectCountsForShop = 0;
    private int                                collectCountsSyc     = 0;
    private int                                successCounts        = 0;
    private int                                failCounts           = 0;
    private int                                selectPosition[];
    private int                                selectCounts         = 0;
    // private ShoppingcartInfoEntity shoppingcartInfoEntityCollect = new ShoppingcartInfoEntity();

    private List<ShoppingcartInfoEntity>       listShoppingcart     = new ArrayList<ShoppingcartInfoEntity>();
    private ExpandableListView                 eListShoppingcartMain;
    private List<List<ShoppingcartInfoEntity>> shoppingcartInfoAll  = new ArrayList<List<ShoppingcartInfoEntity>>(); // 二级数据总和
    private List<ShopsDetailInfoEntity>        shopList             = new ArrayList<ShopsDetailInfoEntity>();

    private boolean                            isAllSelect          = true;
    private LinearLayout                       llShoppingcartModifyBottom;
    private Button                             btnBottomPay;
    private Button                             btnBottomAll;
    private Button                             btnBottomCollect;
    private Button                             btnBottomDelete;
    private LinearLayout                       tohome;
    private LinearLayout                       tosearch;
    private LinearLayout                       toshoppingcart;
    private LinearLayout                       tomycenter;
    private LinearLayout                       tomore;
    private RelativeLayout                     bottommenu;
    private TextView                           tvListGoodscount;
    private TextView                           tvListTotalPrice;
    private ShoppingcartMainAdapter            adapter;
    private float                              listTotalPrice       = 0.00f;
    private int                                listGoodsCount       = 0;

    // 第一次进入购物车，默认全选
    private boolean                            first                = true;

    // 模拟支付的测试数据---------------------------------------------------------
    private String                             goodsID              = "0";                                           // 商品号，用来从网络获取物品信息
    private int                                market               = 0;                                            // 0表示未安装银联插件，1表示安装银联插件成功
    private static boolean                     registerReceiver     = true;

    // 模拟支付的测试数据---------------------------------------------------------
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        // if (keyCode == KeyEvent.KEYCODE_BACK) {
        // Builder builder = new AlertDialog.Builder(ShoppingcartMainListActivity.this);
        // builder.setTitle("是否退出");
        // builder.setMessage("按确定键退出！");
        // builder.setPositiveButton("确定",
        // new DialogInterface.OnClickListener() {
        //
        // @Override
        // public void onClick(DialogInterface arg0, int arg1) {
        // // TODO Auto-generated method stub
        // GlobalVarUtil.firstUpdateApp="update";
        // ExitApplication.getInstance().exit();
        // android.os.Process.killProcess(android.os.Process.myPid());
        // }
        // });
        // builder.setNegativeButton("取消",
        // new DialogInterface.OnClickListener() {
        // @Override
        // public void onClick(DialogInterface arg0, int arg1) {
        // // TODO Auto-generated method stub
        //
        // }
        //
        // });
        // builder.show();
        // }

        this.finish();
        return super.onKeyDown(keyCode, event);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_list_shoppingcart_main);
        ExitApplication.getInstance().addActivity(this);
        init();
        eListShoppingcartMain.setDivider(null);// 去除Item分割线
        eListShoppingcartMain.setGroupIndicator(null);// 去掉箭头

        adapter = new ShoppingcartMainAdapter(this.getBaseContext(), shopList, shoppingcartInfoAll, tvListGoodscount, tvListTotalPrice);
        expandList();
    }

    // 展开listview
    private void expandList() {
        // TODO Auto-generated method stub
        eListShoppingcartMain.setAdapter(adapter);// 不在这里会造成回到该页时崩溃
        int groupCount = eListShoppingcartMain.getCount();
        for (int i = 0; i < groupCount; i++) {
            eListShoppingcartMain.expandGroup(i);
        }
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        if (GlobalVarUtil.account.getUID() == null) {
            this.finish();
        }
        getShoppingcartData();
        if (shoppingcartInfoAll.size() == 0) {
            llShoppingcartModifyBottom.setVisibility(View.GONE);
        } else {
            llShoppingcartModifyBottom.setVisibility(View.VISIBLE);
        }
        expandList();
        super.onResume();
    }

    @Override
    public void bindEvent() {
        // TODO Auto-generated method stub
        eListShoppingcartMain.setOnGroupClickListener(new OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                // TODO Auto-generated method stub
                Log.d("isExpandable", "前" + shopList.get(groupPosition).isExpandable());
                boolean isExpandable = !shopList.get(groupPosition).isExpandable();
                shopList.get(groupPosition).setExpandable(isExpandable);
                Log.d("isExpandable", "后" + shopList.get(groupPosition).isExpandable());
                adapter.updateList(shoppingcartInfoAll, shopList);
                return false;
            }
        });
        btnBottomAll.setOnClickListener(this);
        btnBottomCollect.setOnClickListener(this);
        btnBottomDelete.setOnClickListener(this);
        btnBottomPay.setOnClickListener(this);
        tohome.setOnClickListener(this);
        tosearch.setOnClickListener(this);
        toshoppingcart.setOnClickListener(this);
        tomycenter.setOnClickListener(this);
        tomore.setOnClickListener(this);

        // 模拟支付的测试数据---------------------------------------------------------
    }

    @Override
    public void initValue() {
        // TODO Auto-generated method stub
        context = getBaseContext();
        llShoppingcartModifyBottom = (LinearLayout) findViewById(R.id.ll_list_shoppingcart_modify_bottom);

        btnBottomPay = (Button) findViewById(R.id.btn_list_shoppingcart_modify_pay);
        btnBottomAll = (Button) findViewById(R.id.btn_list_shoppingcart_modify_selectall);
        btnBottomDelete = (Button) findViewById(R.id.btn_list_shoppingcart_modify_delete);
        btnBottomCollect = (Button) findViewById(R.id.btn_list_shoppingcart_modify_movetocollect);
        eListShoppingcartMain = (ExpandableListView) findViewById(R.id.elist_shoppingcart_main);
        tvListGoodscount = (TextView) findViewById(R.id.tv_list_shoppingcart_main_goodscount);
        tvListTotalPrice = (TextView) findViewById(R.id.tv_list_shoppingcart_main_toatalprice);

        LayoutInflater mInflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view2 = mInflater.inflate(R.layout.layout_item_bottom_menu, null);
        tohome = (LinearLayout) view2.findViewById(R.id.ll_tohome);
        tosearch = (LinearLayout) view2.findViewById(R.id.ll_tosearch);
        toshoppingcart = (LinearLayout) view2.findViewById(R.id.ll_toshoppingcart);
        tomycenter = (LinearLayout) view2.findViewById(R.id.ll_tomycenter);
        tomore = (LinearLayout) view2.findViewById(R.id.ll_tomore);
        bottommenu = (RelativeLayout) findViewById(R.id.rl_shoppingcart_mainlist_bottommenu);

        bottommenu.addView(view2);
        ViewGroup.LayoutParams lp = view2.getLayoutParams();
        lp.width = lp.FILL_PARENT;
        view2.setLayoutParams(lp);
        getShoppingcartData();

    }

    private void getShoppingcartData() {
        // TODO Auto-generated method stub
        listShoppingcart = BusinessDao.getShoppingcartTableData(GlobalVarUtil.account.getUID());// 正式使用
        for (int i = 0; i < listShoppingcart.size(); i++) {
            if (listShoppingcart.get(i).getToSelectBoolean() == 0) {
                listShoppingcart.get(i).setSelect(true);
            } else {
                listShoppingcart.get(i).setSelect(false);
            }
        }
        shoppingcartInfoAll.clear();
        shopList.clear();
        listTotalPrice = 0.00f;
        listGoodsCount = 0;

        // 在for循环中确定有多少商家
        for (Iterator iterator = listShoppingcart.iterator(); iterator.hasNext();) {
            ShoppingcartInfoEntity entity = (ShoppingcartInfoEntity) iterator.next();
            ShopsDetailInfoEntity shopsEntity = new ShopsDetailInfoEntity();
            shopsEntity.setId(entity.getShopsID());
            shopsEntity.setName(entity.getShopsName());
            // 测试时 state恒为false ？？？
            // boolean state = shopList.contains(shopsEntity);
            // if(!state){
            // shopList.add(shopsEntity);
            // }
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
        System.out.println("商家数量：   " + shopList.size());
        // 确定每个商家有哪些商品
        for (int i = 0; i < shopList.size(); i++) {
            List<ShoppingcartInfoEntity> shoppingcartInfoList = new ArrayList<ShoppingcartInfoEntity>();
            for (Iterator iterator = listShoppingcart.iterator(); iterator.hasNext();) {
                ShoppingcartInfoEntity entity = (ShoppingcartInfoEntity) iterator.next();
                if (entity.getShopsID().equals(shopList.get(i).getId())) {
                    shoppingcartInfoList.add(entity);
                    if (!entity.isSelect()) {
                        shopList.get(i).setSelect(false);
                    }
                }
                // }
            }
            try {
                shoppingcartInfoAll.add(shoppingcartInfoList);

            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }
        if (first) {
            for (int i = 0; i < shoppingcartInfoAll.size(); i++) {
                for (int j = 0; j < shoppingcartInfoAll.get(i).size(); j++) {
                    shoppingcartInfoAll.get(i).get(j).setSelect(true);
                    ShoppingcartMainAdapter.shoppingcartBoolean(shoppingcartInfoAll.get(i).get(j));
                }
            }
            for (int k = 0; k < shopList.size(); k++) {
                shopList.get(k).setSelect(true);
            }
            first = false;
        }

        totalPriceAndCounts(shoppingcartInfoAll);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
        case R.id.btn_list_shoppingcart_modify_pay:
            if (tvListGoodscount.getText().toString().equals("0件")) {
                Toast.makeText(myContext, "请选中商品，再付款", Toast.LENGTH_SHORT).show();
            } else {
                Intent toOrder = new Intent(ShoppingcartMainListActivity.this, ShoppingcartOrderListActivity.class);
                startActivity(toOrder);

            }
            break;
        case R.id.btn_list_shoppingcart_modify_movetocollect:
            if (tvListGoodscount.getText().toString().equals("0件")) {
                Toast.makeText(myContext, "请选中商品，再收藏", Toast.LENGTH_SHORT).show();
            } else {
                collectCounts = 0;
                successCounts = 0;
                failCounts = 0;
                selectCounts = 0;
                for (int i = 0; i < listShoppingcart.size(); i++) {
                    if (listShoppingcart.get(i).isSelect()) {
                        selectCounts++;
                    }
                }
                selectPosition = new int[selectCounts];
                int position = -1;
                for (int i = 0; i < listShoppingcart.size(); i++) {
                    if (listShoppingcart.get(i).isSelect()) {
                        position++;
                        selectPosition[position] = i;
                    }
                }
                Toast.makeText(myContext, "开始收藏商品，总计" + selectCounts + "件", Toast.LENGTH_SHORT).show();
                wait_for_collect();
            }
            break;
        case R.id.btn_list_shoppingcart_modify_delete:
            if (tvListGoodscount.getText().toString().equals("0件")) {
                Toast.makeText(myContext, "请选中商品，再操作", Toast.LENGTH_SHORT).show();
            } else {

                Builder builder = new AlertDialog.Builder(ShoppingcartMainListActivity.this);
                builder.setTitle("是否删除");
                builder.setMessage("按确定键删除！");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        // TODO Auto-generated method stub
                        deleteGoods();
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
            break;
        case R.id.btn_list_shoppingcart_modify_selectall:
            selectAll();
            break;
        case R.id.ll_tohome:
            Intent intenthome = new Intent(ShoppingcartMainListActivity.this, HomeMainActivity.class);
            startActivity(intenthome);
            ExitApplication.getInstance().exit();
            break;
        case R.id.ll_tosearch:
            Intent intentsearch = new Intent(ShoppingcartMainListActivity.this, SearchMainActivity.class);
            startActivity(intentsearch);
            ExitApplication.getInstance().exit();
            break;
        case R.id.ll_toshoppingcart:
            // Intent intentshoppingcart =new Intent(ShoppingcartMainListActivity.this,ShoppingcartMainListActivity.class);
            // startActivity(intentshoppingcart);
            // if(GlobalVarUtil.account.getUID()==null){
            // Intent intentTolongin = new Intent(ShoppingcartMainListActivity.this,LoginActivity.class);
            // startActivity(intentTolongin);
            // }
            // ExitApplication.getInstance().exit();
            break;
        case R.id.ll_tomycenter:
            Intent intentmycenter = new Intent(ShoppingcartMainListActivity.this, MycenterMainActivity.class);
            startActivity(intentmycenter);
            if (GlobalVarUtil.account.getUID() == null) {
                Intent intentTolongin = new Intent(ShoppingcartMainListActivity.this, LoginActivity.class);
                startActivity(intentTolongin);
            }
            ExitApplication.getInstance().exit();
            break;
        case R.id.ll_tomore:
            Intent intentmore = new Intent(ShoppingcartMainListActivity.this, MoreActivity.class);
            startActivity(intentmore);
            ExitApplication.getInstance().exit();
            break;
        }
    }

    private void wait_for_collect() {
        // TODO Auto-generated method stub
        insert_collect();
        insert_sellerinfo();
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

                String reqUrl = getString(R.string.insertCollectGoods_http);

                Map<String, Object> parameters = new LinkedHashMap<String, Object>();

                parameters.put("UID", GlobalVarUtil.account.getUID());
                parameters.put("ID", listShoppingcart.get(selectPosition[collectCounts]).getID());
                parameters.put("type", listShoppingcart.get(selectPosition[collectCounts]).getType());

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
                collectCounts++;
                if (collectCounts < selectCounts) {
                    insert_collect();
                }
                System.out.println("code jsonStr 购物车的收藏=" + "商品名：" + listShoppingcart.get(selectPosition[collectCounts - 1]).getName() + " --jsonstt " + jsonStr);
                jsonStr = RegisterActivity.replaceBlank(jsonStr);
                if (jsonStr.equals("-1")) {
                    ViewUtil.showToast(myContext, myContext.getString(R.string.net_exception));

                } else {
                    if (jsonStr.equals("1") || jsonStr.equals("1\n")) {
                        ++failCounts;
                        // Toast.makeText(ShoppingcartMainListActivity.this, "收藏失败，已有"+failCounts+"件商品收藏失败",1).show();
                    } else {
                        successCounts++;
                        if (BusinessDao.insertCollectionTableData(goodsEntity.getID(), goodsEntity.getType())) {
                            // if(collectCounts == selectCounts){
                            // Toast.makeText(ShoppingcartMainListActivity.this, "收藏成功，共"+successCounts+"件商品",1).show();
                            // }else{
                            // Toast.makeText(ShoppingcartMainListActivity.this, "正在收藏，已收藏"+collectCounts+"件商品",1).show();
                            // }
                        } else {
                            // int i = collectCounts - successCounts-failCounts;
                            // Toast.makeText(ShoppingcartMainListActivity.this, "收藏重复，有"+i+"件商品已在收藏中",1).show();
                        }
                    }
                }
                if (collectCounts == selectCounts) {
                    // int j = selectCounts - failCounts;
                    Toast.makeText(ShoppingcartMainListActivity.this, "收藏结束，成功收藏" + successCounts + "件商品", 1).show();
                }
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

                String reqUrl = getString(R.string.getShopsDetailInfo_php);

                Map<String, Object> parameters = new LinkedHashMap<String, Object>();

                parameters.put("ID", listShoppingcart.get(selectPosition[collectCountsForShop]).getID());
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
                collectCountsForShop++;
                if (collectCountsForShop < selectCounts) {
                    insert_sellerinfo();
                }
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

    // 将选中的商品从购物车中删除
    private void deleteGoods() {
        // TODO Auto-generated method stub
        for (int i = 0; i < shoppingcartInfoAll.size(); i++) {
            int delete = shoppingcartInfoAll.get(i).size();
            for (int k = 0; k < delete; k++) {
                if (shoppingcartInfoAll.get(i).get(k).isSelect()) {
                    BusinessDao.deleteShoppingcartTableData(shoppingcartInfoAll.get(i).get(k));
                }
            }
        }
        getShoppingcartData();
        for (int i = 0; i < shoppingcartInfoAll.size(); i++) {
            for (int j = 0; j < shoppingcartInfoAll.get(i).size(); j++) {
                shoppingcartInfoAll.get(i).get(j).setSelect(false);
            }
            shopList.get(i).setSelect(false);
        }
        adapter.updateList(shoppingcartInfoAll, shopList);

    }

    // 选中所有商品
    private void selectAll() {
        // TODO Auto-generated method stub
        if (isAllSelect) {
            for (int i = 0; i < shoppingcartInfoAll.size(); i++) {
                for (int j = 0; j < shoppingcartInfoAll.get(i).size(); j++) {
                    shoppingcartInfoAll.get(i).get(j).setSelect(true);
                    shoppingcartInfoAll.get(i).get(j).setToSelectBoolean(0);
                    BusinessDao.updateShoppingcartTableData(BusinessDao.Table_Name_ShoppingcartInfo, shoppingcartInfoAll.get(i).get(j), GlobalVarUtil.account.getUID());
                }
            }
            for (int k = 0; k < shopList.size(); k++) {
                shopList.get(k).setSelect(true);
            }
            isAllSelect = false;
            btnBottomAll.setText("反    选");
        } else {
            for (int i = 0; i < shoppingcartInfoAll.size(); i++) {
                for (int j = 0; j < shoppingcartInfoAll.get(i).size(); j++) {
                    if (shoppingcartInfoAll.get(i).get(j).isSelect()) {
                        shoppingcartInfoAll.get(i).get(j).setSelect(false);
                        shoppingcartInfoAll.get(i).get(j).setToSelectBoolean(1);
                    } else {
                        shoppingcartInfoAll.get(i).get(j).setSelect(true);
                        shoppingcartInfoAll.get(i).get(j).setToSelectBoolean(0);
                    }
                    BusinessDao.updateShoppingcartTableData(BusinessDao.Table_Name_ShoppingcartInfo, shoppingcartInfoAll.get(i).get(j), GlobalVarUtil.account.getUID());
                }
            }
            for (int k = 0; k < shopList.size(); k++) {
                shopList.get(k).setSelect(true);
                for (int i = 0; i < shoppingcartInfoAll.size(); i++) {
                    for (int j = 0; j < shoppingcartInfoAll.get(i).size(); j++) {
                        if (!shoppingcartInfoAll.get(i).get(j).isSelect()) {
                            shopList.get(k).setSelect(false);
                        }
                    }
                }

            }
            isAllSelect = true;
            btnBottomAll.setText("全    选");

        }
        adapter.updateList(shoppingcartInfoAll, shopList);

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
}
