package com.fsti.fjdicClient.activity.mycenter;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fsti.fjdicClient.R;
import com.fsti.fjdicClient.activity.BaseActivity;
import com.fsti.fjdicClient.activity.groupBuy.GroupBuySortListActivity;
import com.fsti.fjdicClient.activity.home.HomeMainActivity;
import com.fsti.fjdicClient.activity.login.LoginActivity;
import com.fsti.fjdicClient.activity.more.MoreActivity;
import com.fsti.fjdicClient.activity.search.SearchMainActivity;
import com.fsti.fjdicClient.activity.shoppingcart.ShoppingcartMainListActivity;
import com.fsti.fjdicClient.bean.ConsigneeAddressEntity;
import com.fsti.fjdicClient.bean.ExitApplication;
import com.fsti.fjdicClient.bean.RegisterEntity;
import com.fsti.fjdicClient.util.GlobalVarUtil;
import com.fsti.fjdicClient.util.HttpUtil;
import com.fsti.fjdicClient.util.asyncUtil.CallEarliest;
import com.fsti.fjdicClient.util.asyncUtil.Callable;
import com.fsti.fjdicClient.util.asyncUtil.Callback;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Toast;

/**
 * 个人中心 修改邮箱
 * @author 
 *
 */
public class ModifyEmailActivity extends BaseActivity implements OnClickListener {
	private Button  btmodifyemailBack;
	private EditText etmodifyemailNew;
	private Button btmodifyemailFinish;
	private String email;
	private LinearLayout tohome;
	private LinearLayout tosearch;
	private LinearLayout toshoppingcart;
	private LinearLayout tomycenter;
	private LinearLayout tomore;
	private RelativeLayout bottommenu;
	
	private int is_click=0;//过滤多次点击修改邮箱，1表示已点击
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_activity_mycenter_modifyemail);
		ExitApplication.getInstance().addActivity(this);
		init();
	}


	@Override
	public void bindEvent() {
		// TODO Auto-generated method stub
		btmodifyemailBack.setOnClickListener(this);
		btmodifyemailFinish.setOnClickListener(this);
		tohome.setOnClickListener(this);
		tosearch.setOnClickListener(this);
		toshoppingcart.setOnClickListener(this);
		tomycenter.setOnClickListener(this);
		tomore.setOnClickListener(this);
	}

	@Override
	public void initValue() {
		// TODO Auto-generated method stub
		btmodifyemailBack = (Button)findViewById(R.id.btn_mycenter_modifyemail_back);
		etmodifyemailNew = (EditText)findViewById(R.id.et_mycenter_modifyemail_inputemail);
		btmodifyemailFinish = (Button)findViewById(R.id.btn_mycenter_modifyemail_finish);

		LayoutInflater	mInflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    View view2  = mInflater.inflate(R.layout.layout_item_bottom_menu,null);
	    tohome=(LinearLayout)view2.findViewById(R.id.ll_tohome);
	    tosearch=(LinearLayout) view2.findViewById(R.id.ll_tosearch);
	    toshoppingcart=(LinearLayout) view2.findViewById(R.id.ll_toshoppingcart);
	    tomycenter=(LinearLayout) view2.findViewById(R.id.ll_tomycenter);
	    tomore=(LinearLayout) view2.findViewById(R.id.ll_tomore);
	    bottommenu=(RelativeLayout) findViewById(R.id.rl_mycenter_modifyemail_bottommenu);
	    bottommenu.addView(view2);
	    ViewGroup.LayoutParams lp =  view2.getLayoutParams();
	    lp.width = lp.FILL_PARENT;
	    view2.setLayoutParams(lp);
	}


	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
		switch(arg0.getId()){
		case R.id.btn_mycenter_modifyemail_back:
			this.finish();
			break;
		case R.id.btn_mycenter_modifyemail_finish :
			
			if(is_click==0){
				email = etmodifyemailNew.getText().toString();
				if(isEmailValid(email)){
					 ModifyModifyEmail();
				}
				else{
					is_click=0;
					Toast.makeText(ModifyEmailActivity.this, "邮箱格式错误，请确认输入无误", 1).show();
				}
						
			}
		
			break;
		case R.id.ll_tohome :
			Intent intenthome =new Intent(ModifyEmailActivity.this,HomeMainActivity.class);
			startActivity(intenthome);
			ExitApplication.getInstance().exit();
			break;
		case R.id.ll_tosearch :
			Intent intentsearch =new Intent(ModifyEmailActivity.this,SearchMainActivity.class);
			startActivity(intentsearch);
			ExitApplication.getInstance().exit();
			break;
		case R.id.ll_toshoppingcart :
			Intent intentshoppingcart =new Intent(ModifyEmailActivity.this,ShoppingcartMainListActivity.class);
			startActivity(intentshoppingcart);
			if(GlobalVarUtil.account.getUID()==null){
				Intent intentTolongin = new Intent(ModifyEmailActivity.this,LoginActivity.class);
				startActivity(intentTolongin);
			}
//			ExitApplication.getInstance().exit();//购物车可以回退
			break;
		case R.id.ll_tomycenter :
			Intent intentmycenter =new Intent(ModifyEmailActivity.this,MycenterMainActivity.class);
			startActivity(intentmycenter);
			if(GlobalVarUtil.account.getUID()==null){
				Intent intentTolongin = new Intent(ModifyEmailActivity.this,LoginActivity.class);
				startActivity(intentTolongin);
			}
			ExitApplication.getInstance().exit();
			break;
		case R.id.ll_tomore :
			Intent intentmore =new Intent(ModifyEmailActivity.this,MoreActivity.class);
			startActivity(intentmore);
			ExitApplication.getInstance().exit();
			break;
		}
		
	}
	
	public static boolean isEmailValid(String strEmail) {
	    String strPattern ="^[0-9a-zA-Z_]+@[0-9a-zA-Z]+\\.[a-zA-Z]+$";
//	    String strPattern ="^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/";
	    Pattern p = Pattern.compile(strPattern);
	    Matcher m = p.matcher(strEmail);
	    return m.matches();
	}
	
//修改邮箱
	
	private void ModifyModifyEmail() {
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
               
            	   reqUrl = getString(R.string.ModifyEmail_php);
     		   
     				parameters.put("UID",GlobalVarUtil.account.getUID());
     				parameters.put("email",email);
     			
     			
				String jsonStr = "";
				try {
					jsonStr = HttpUtil.postData(reqUrl, parameters,
							GlobalVarUtil.ENCODING);
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
		            is_click=0;
		            if(jsonStr.trim().equals("0")){
		            	
		            	Toast.makeText(ModifyEmailActivity.this, "修改邮箱成功", 1).show();
		            }
		            else{
		            	Toast.makeText(ModifyEmailActivity.this, "修改邮箱失败", 1).show();
		            }
			}
		});
	}

}
