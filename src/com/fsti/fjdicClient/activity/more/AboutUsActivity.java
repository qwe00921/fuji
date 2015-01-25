package com.fsti.fjdicClient.activity.more;

import java.util.LinkedHashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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

import com.fsti.fjdicClient.ApplicationUtil;
import com.fsti.fjdicClient.R;
import com.fsti.fjdicClient.activity.BaseActivity;
import com.fsti.fjdicClient.activity.home.HomeMainActivity;
import com.fsti.fjdicClient.activity.login.RegisterActivity;
import com.fsti.fjdicClient.activity.mycenter.MycenterMainActivity;
import com.fsti.fjdicClient.activity.search.SearchMainActivity;
import com.fsti.fjdicClient.activity.shoppingcart.ShoppingcartMainListActivity;
import com.fsti.fjdicClient.bean.ExitApplication;
import com.fsti.fjdicClient.util.GlobalVarUtil;
import com.fsti.fjdicClient.util.HttpUtil;
import com.fsti.fjdicClient.util.ImageLoaderHelper;
import com.fsti.fjdicClient.util.ViewUtil;
import com.fsti.fjdicClient.util.asyncUtil.CallEarliest;
import com.fsti.fjdicClient.util.asyncUtil.Callable;
import com.fsti.fjdicClient.util.asyncUtil.Callback;

/**
 * 关于我们
 * 
 * @author 王久叶
 */

public class AboutUsActivity extends BaseActivity implements OnClickListener {
    private ImageView      imgcall;
    private ImageView      imgIcon;
    private TextView       tvappName;
    private TextView       tvversion;
    private TextView       tvCompany;
    private TextView       tvaddress;
    private ImageView      ivLoading;
    private Button         btnAboutusBack;
    private Button         about_phone_btn;
    private LinearLayout   tohome;
    private LinearLayout   tosearch;
    private LinearLayout   toshoppingcart;
    private LinearLayout   tomycenter;
    private LinearLayout   tomore;
    private RelativeLayout bottommenu;

    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.layout_activity_more_aboutus);
        ExitApplication.getInstance().addActivity(this);
        init();
        postAboutUs();

    }

    private void postAboutUs() {
        // TODO Auto-generated method stub
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

                reqUrl = getString(R.string.AboutUs_php);

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
                jsonStr = RegisterActivity.replaceBlank(jsonStr);
                if (jsonStr.equals("-1") || jsonStr.equals("") || jsonStr.trim().equals("null") || jsonStr == null) {
                    // ViewUtil.showToast(myContext, myContext.getString(R.string.net_exception));

                } else {
                    JSONObject jsonObj = new JSONObject();
                    try {
                        jsonObj = new JSONObject(jsonStr);
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    try {
                        String imgurl = jsonObj.getString("imgurl");
                        if (!imgurl.equals("") && imgurl != null) {
                            imgIcon.setImageResource(R.drawable.image_load);
                            ImageLoaderHelper.displayImage(imgIcon, imgurl);
                            // new SyncImageLoadUtil().displayImage(imgurl, imgIcon, myContext);
                        }
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    try {
                        String appName = jsonObj.getString("appName");
                        if (!appName.equals("") && appName != null) {
                            tvappName.setText(appName);
                        }
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    try {
                        String version = jsonObj.getString("version");
                        String makeTime = jsonObj.getString("makeTime");
                        if (!version.equals("") && version != null && !makeTime.equals("") && makeTime != null) {
                            tvversion.setText("Version " + version + "(Build " + makeTime + ")");
                        }
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    try {
                        String phone = jsonObj.getString("phone");
                        if (!phone.equals("") && phone != null) {
                            about_phone_btn.setText(phone);
                        }
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    try {
                        String company = jsonObj.getString("company");
                        if (!company.equals("") && company != null) {
                            tvCompany.setText(company);
                        }
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    try {
                        String address = jsonObj.getString("address");
                        if (!address.equals("") && address != null) {
                            tvaddress.setText(address);
                        }
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                imgIcon.setVisibility(View.VISIBLE);
                tvappName.setVisibility(View.VISIBLE);
                tvversion.setVisibility(View.VISIBLE);
                tvCompany.setVisibility(View.VISIBLE);
                tvaddress.setVisibility(View.VISIBLE);
                about_phone_btn.setVisibility(View.VISIBLE);
                imgcall.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void bindEvent() {
        // TODO Auto-generated method stub
        btnAboutusBack.setOnClickListener(this);
        about_phone_btn.setOnClickListener(this);
        tohome.setOnClickListener(this);
        tosearch.setOnClickListener(this);
        toshoppingcart.setOnClickListener(this);
        tomycenter.setOnClickListener(this);
        tomore.setOnClickListener(this);
    }

    @Override
    public void initValue() {
        // TODO Auto-generated method stub
        imgcall = (ImageView) findViewById(R.id.iv_aboutus_call);
        imgIcon = (ImageView) findViewById(R.id.imageView1);
        ivLoading = (ImageView) findViewById(R.id.iv_aboutus_loading);
        tvCompany = (TextView) findViewById(R.id.tv_company);
        tvappName = (TextView) findViewById(R.id.textView1);
        tvversion = (TextView) findViewById(R.id.textView2);
        tvaddress = (TextView) findViewById(R.id.about_addr);
        btnAboutusBack = (Button) findViewById(R.id.btn_aboutus_back);
        about_phone_btn = (Button) findViewById(R.id.about_phone_btn);
        LayoutInflater mInflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view2 = mInflater.inflate(R.layout.layout_item_bottom_menu, null);
        tohome = (LinearLayout) view2.findViewById(R.id.ll_tohome);
        tosearch = (LinearLayout) view2.findViewById(R.id.ll_tosearch);
        toshoppingcart = (LinearLayout) view2.findViewById(R.id.ll_toshoppingcart);
        tomycenter = (LinearLayout) view2.findViewById(R.id.ll_tomycenter);
        tomore = (LinearLayout) view2.findViewById(R.id.ll_tomore);
        bottommenu = (RelativeLayout) findViewById(R.id.rl_more_aboutus_bottommenu);
        bottommenu.addView(view2);
        ViewGroup.LayoutParams lp = view2.getLayoutParams();
        lp.width = lp.FILL_PARENT;
        view2.setLayoutParams(lp);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
        case R.id.btn_aboutus_back:
            this.finish();
            break;
        case R.id.about_phone_btn:
            // Builder builder = new AlertDialog.Builder(AboutUsActivity.this);
            // builder.setTitle("是否拨打");
            // builder.setMessage("按确定键拨打！");
            // builder.setPositiveButton("确定",
            // new DialogInterface.OnClickListener() {
            //
            // @Override
            // public void onClick(DialogInterface arg0, int arg1) {
            // // TODO Auto-generated method stub
            // String phone = "4000036539";//客服号码
            // Intent intent=new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+phone));
            // startActivity(intent);
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
            String phone = "4000036539";// 客服号码
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
            startActivity(intent);
            break;
        case R.id.ll_tohome:
            Intent intenthome = new Intent(AboutUsActivity.this, HomeMainActivity.class);
            startActivity(intenthome);
            ExitApplication.getInstance().exit();
            break;
        case R.id.ll_tosearch:
            Intent intentsearch = new Intent(AboutUsActivity.this, SearchMainActivity.class);
            startActivity(intentsearch);
            ExitApplication.getInstance().exit();
            break;
        case R.id.ll_toshoppingcart:
            Intent intentshoppingcart = new Intent(AboutUsActivity.this, ShoppingcartMainListActivity.class);
            startActivity(intentshoppingcart);
            // ExitApplication.getInstance().exit();//购物车可以返回
            break;
        case R.id.ll_tomycenter:
            Intent intentmycenter = new Intent(AboutUsActivity.this, MycenterMainActivity.class);
            startActivity(intentmycenter);
            ExitApplication.getInstance().exit();
            break;
        case R.id.ll_tomore:
            Intent intentmore = new Intent(AboutUsActivity.this, MoreActivity.class);
            startActivity(intentmore);
            ExitApplication.getInstance().exit();
            break;
        }
    }

}
