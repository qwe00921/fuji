package com.fsti.fjdicClient.activity.groupBuy;

import java.net.URI;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.fsti.fjdicClient.R;
import com.fsti.fjdicClient.ApplicationUtil;
import com.fsti.fjdicClient.activity.BaseActivity;
import com.fsti.fjdicClient.activity.home.HomeMainActivity;
import com.fsti.fjdicClient.activity.login.LoginActivity;
import com.fsti.fjdicClient.activity.more.MoreActivity;
import com.fsti.fjdicClient.activity.mycenter.MycenterMainActivity;
import com.fsti.fjdicClient.activity.search.SearchMainActivity;
//import com.fsti.fjdicClient.activity.shoppingMall.ShoppingmallGoodsDetailActivity;
import com.fsti.fjdicClient.activity.shoppingcart.ShoppingcartMainListActivity;
import com.fsti.fjdicClient.bean.ExitApplication;
import com.fsti.fjdicClient.util.GlobalVarUtil;
import com.fsti.fjdicClient.util.HttpUtil;
import com.fsti.fjdicClient.util.SyncImageLoadUtil;
import com.fsti.fjdicClient.util.ViewUtil;
import com.fsti.fjdicClient.util.asyncUtil.CallEarliest;
import com.fsti.fjdicClient.util.asyncUtil.Callable;
import com.fsti.fjdicClient.util.asyncUtil.Callback;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
/**
 * 生活团  卖家信息
 * @author 
 *
 */

public class SellerInformationActivity extends BaseActivity implements OnClickListener{
	private Button btsellerinformationBack;
	private TextView tvsellerinfoName ;
	private TextView tvsellerinfoAddress;
	private TextView tvsellerinfoPhone;
	private ScrollView scrollmid;
	private ImageView imgsellerlogo;
	private Button btncall;
	private String goodsID;
	private ImageView ivLoading;
	private SyncImageLoadUtil syncImageLoad = new SyncImageLoadUtil(); 
	
	private  LinearLayout llphonenumlist;
	private 	LayoutInflater	mInflater;
	
	
	private LinearLayout tohome;
	private LinearLayout tosearch;
	private LinearLayout toshoppingcart;
	private LinearLayout tomycenter;
	private LinearLayout tomore;
	private RelativeLayout bottommenu;
	private String type="2";
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_activity_groupbuy_sellerinformation);
		ExitApplication.getInstance().addActivity(this);
		Intent intent = this.getIntent();
		goodsID = intent.getStringExtra("goodsID");
		type= intent.getStringExtra("type");
		init();
		get_sellerinfo();
		//String phonenum="1234567,4567890,6780000,900000000";

		//add_phonenum_view( phonenum);
	}

	@Override
	public void bindEvent() {
btsellerinformationBack.setOnClickListener(this);
/*tvsellerinfoPhone.setOnClickListener(this);
btncall.setOnClickListener(this);*/

tohome.setOnClickListener(this);
tosearch.setOnClickListener(this);
toshoppingcart.setOnClickListener(this);
tomycenter.setOnClickListener(this);
tomore.setOnClickListener(this);

	}

	@Override
	public void initValue() {
		ivLoading=(ImageView) findViewById(R.id.iv_sellerinfo_loading);
		imgsellerlogo= (ImageView) findViewById(R.id.img_groupbuy_sellerinfo_sellerlogo);
		btsellerinformationBack = (Button)findViewById(R.id.btn_groupbuy_sellerinformation_back);
		tvsellerinfoName = (TextView)findViewById(R.id.tv_groupbuy_sellerinformation_sellername);
		tvsellerinfoAddress = (TextView)findViewById(R.id.tv_groupbuy_sellerinformation_addressdetails);
		ivLoading.setVisibility(View.VISIBLE);
		
		scrollmid = (ScrollView) findViewById(R.id.scroll_seller_mid);
		
		llphonenumlist = (LinearLayout) findViewById(R.id.ll_groupbuy_sellerinfo_phonenumlist);
		
		ViewUtil.addLoadingAnimation(ApplicationUtil.myContext,
				ivLoading);
			mInflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view2  = mInflater.inflate(R.layout.layout_item_bottom_menu,null);
	    tohome=(LinearLayout)view2.findViewById(R.id.ll_tohome);
	    tosearch=(LinearLayout) view2.findViewById(R.id.ll_tosearch);
	    toshoppingcart=(LinearLayout) view2.findViewById(R.id.ll_toshoppingcart);
	    tomycenter=(LinearLayout) view2.findViewById(R.id.ll_tomycenter);
	    tomore=(LinearLayout) view2.findViewById(R.id.ll_tomore);
	    bottommenu=(RelativeLayout) findViewById(R.id.rl_groupbuy_sellerinfo_bottommenu);
	    bottommenu.addView(view2);
	    
	    ViewGroup.LayoutParams lp =  view2.getLayoutParams();
	    lp.width = lp.FILL_PARENT;
	    view2.setLayoutParams(lp);

		
	}
	private void add_phonenum_view(String phonenum){
		phonenum=phonenum+",";
		int q1=-1;
		 View view3;
		while((q1=phonenum.indexOf(","))!=-1){
			view3=new View(SellerInformationActivity.this);
			 view3  = mInflater.inflate(R.layout.layout_sellerinfo_phonenum,null);
				tvsellerinfoPhone= (TextView)view3.findViewById(R.id.tv_groupbuy_sellerinformation_phonedetails);
				btncall = (Button)view3.findViewById(R.id.btn_groupbuy_sellerinfo_call);
				String newstring = phonenum.substring(0,q1);

				 phonenum =phonenum.substring(q1+1,phonenum.length());
				tvsellerinfoPhone.setText(newstring);
				llphonenumlist.addView(view3);
				 final String calling_num = newstring;
				view3.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						 call(calling_num);
					}
				});
		}
	}
	private void call(final String phonenum){
		Builder builder = new AlertDialog.Builder(SellerInformationActivity.this);
		builder.setTitle("是否拨打");
		builder.setMessage(phonenum);
		builder.setPositiveButton("确定",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						
						
						Intent intent3=new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+phonenum));
						startActivity(intent3);

					}
				});
		builder.setNegativeButton("取消",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub

					}

				});
		builder.show();
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch(arg0.getId()){
		case R.id.btn_groupbuy_sellerinformation_back:
			
			this.finish();
			break;
		/*case R.id.tv_groupbuy_sellerinformation_phonedetails:
		case R.id.btn_groupbuy_sellerinfo_call :*/
	
		case R.id.ll_tohome :
			Intent intenthome =new Intent(SellerInformationActivity.this,HomeMainActivity.class);
			startActivity(intenthome);
			ExitApplication.getInstance().exit();
			break;
		case R.id.ll_tosearch :
			Intent intentsearch =new Intent(SellerInformationActivity.this,SearchMainActivity.class);
			startActivity(intentsearch);
			ExitApplication.getInstance().exit();
			break;
		case R.id.ll_toshoppingcart :
			Intent intentshoppingcart =new Intent(SellerInformationActivity.this,ShoppingcartMainListActivity.class);
			startActivity(intentshoppingcart);
			if(GlobalVarUtil.account.getUID()==null){
				Intent intentTolongin = new Intent(SellerInformationActivity.this,LoginActivity.class);
				startActivity(intentTolongin);
			}
//			ExitApplication.getInstance().exit();//购物车可以返回
			break;
		case R.id.ll_tomycenter :
			Intent intentmycenter =new Intent(SellerInformationActivity.this,MycenterMainActivity.class);
			startActivity(intentmycenter);
			if(GlobalVarUtil.account.getUID()==null){
				Intent intentTolongin = new Intent(SellerInformationActivity.this,LoginActivity.class);
				startActivity(intentTolongin);
			}
			ExitApplication.getInstance().exit();
			break;
		case R.id.ll_tomore :
			Intent intentmore =new Intent(SellerInformationActivity.this,MoreActivity.class);
			startActivity(intentmore);
			ExitApplication.getInstance().exit();
			break;
		
		}
		
		
	}
	
	 //获取商家详情
	
	private void get_sellerinfo() {
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
				
				
				parameters.put("ID",goodsID);
				parameters.put("type",type);
			
				String jsonStr = "";
				try {
					jsonStr = HttpUtil.postData(reqUrl, parameters,
							GlobalVarUtil.ENCODING);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					ViewUtil.removeLoadingAnimation(ivLoading);
					 
					return "-1";
				}

				return jsonStr;
			}
		}, new Callback<String>() {
			@Override
			public void onCallback(String jsonStr) {
				// TODO Auto-generated method stub
				ViewUtil.removeLoadingAnimation(ivLoading);
            System.out.println("code jsonStr=" + jsonStr);
			
           

				if (jsonStr.equals("-1")) {
					ViewUtil.showToast(myContext, myContext.getString(R.string.net_exception));
				} else {
					try {
						JSONObject json = new JSONObject(jsonStr);
						System.out.println(json.getString("imageUrl")+"===================test"+json.getString("name"));
					
						if(json.equals("")){
							tvsellerinfoName.setText("永安电子商务有限公司");
							tvsellerinfoAddress.setText("福州市台江区鳌峰路186号海峡电子商务产业基地A区12层");
							imgsellerlogo.setImageResource(R.drawable.icon);
							add_phonenum_view("4000036539");
							scrollmid.setVisibility(View.VISIBLE);
						}else{
							tvsellerinfoName.setText(json.getString("name"));
							tvsellerinfoAddress.setText(json.getString("address"));
							syncImageLoad.displayImage(json.getString("imageUrl"), imgsellerlogo, SellerInformationActivity.this);
							String phonenum=json.getString("telephone");
							add_phonenum_view( phonenum);
							scrollmid.setVisibility(View.VISIBLE);
						}
					//phonenum="1234567,4567890,6780000,900000000";

					} catch (JSONException e) {
						// TODO Auto-generated catch block
						scrollmid.setVisibility(View.VISIBLE);
						tvsellerinfoName.setText("永安电子商务有限公司");
						tvsellerinfoAddress.setText("福州市台江区鳌峰路186号海峡电子商务产业基地A区12层");
						imgsellerlogo.setImageResource(R.drawable.icon);
						add_phonenum_view("4000036539");
						e.printStackTrace();
					}
				}
			}
		});
	}

	
	
}
