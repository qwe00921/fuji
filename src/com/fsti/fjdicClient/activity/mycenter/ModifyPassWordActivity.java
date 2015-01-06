package com.fsti.fjdicClient.activity.mycenter;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
import com.fsti.fjdicClient.util.ViewUtil;
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
 * 个人中心 修改密码
 * @author 
 *
 */
public class ModifyPassWordActivity extends BaseActivity implements OnClickListener {
	private Button  btmodifypasswordBack;
	private EditText etmodifypasswordOld;
	private EditText etmodifypasswordNew;
	private EditText etmodifypasswordNew2;
	private Button btmodifypasswordFinish;
	private String oldpassword;
	private String newpassword;
	private  int  is_click=0;//过滤多次点击修改密码，1表示已点击
	private LinearLayout tohome;
	private LinearLayout tosearch;
	private LinearLayout toshoppingcart;
	private LinearLayout tomycenter;
	private LinearLayout tomore;
	private RelativeLayout bottommenu;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_activity_mycenter_modifypassword);
		ExitApplication.getInstance().addActivity(this);
		init();
		
	}


	@Override
	public void bindEvent() {
		// TODO Auto-generated method stub
		btmodifypasswordBack.setOnClickListener(this);
		btmodifypasswordFinish.setOnClickListener(this);
		tohome.setOnClickListener(this);
		tosearch.setOnClickListener(this);
		toshoppingcart.setOnClickListener(this);
		tomycenter.setOnClickListener(this);
		tomore.setOnClickListener(this);	
	}

	@Override
	public void initValue() {
		// TODO Auto-generated method stub
		btmodifypasswordBack= (Button)findViewById(R.id.btn_mycenter_forgetpassword_back);
		etmodifypasswordOld = (EditText)findViewById(R.id.et_mycenter_forgetpassword_oldpassword);
		etmodifypasswordNew = (EditText)findViewById(R.id.et_mycenter_forgetpassword_newpassword);
		etmodifypasswordNew2 = (EditText)findViewById(R.id.et_mycenter_forgetpassword_newpassword2);
		btmodifypasswordFinish= (Button)findViewById(R.id.btn_mycenter_forgetpassword_finish);
		LayoutInflater	mInflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    View view2  = mInflater.inflate(R.layout.layout_item_bottom_menu,null);
	    tohome=(LinearLayout)view2.findViewById(R.id.ll_tohome);
	    tosearch=(LinearLayout) view2.findViewById(R.id.ll_tosearch);
	    toshoppingcart=(LinearLayout) view2.findViewById(R.id.ll_toshoppingcart);
	    tomycenter=(LinearLayout) view2.findViewById(R.id.ll_tomycenter);
	    tomore=(LinearLayout) view2.findViewById(R.id.ll_tomore);
	    bottommenu=(RelativeLayout) findViewById(R.id.rl_mycenter_modifypassword_bottommenu);
	    bottommenu.addView(view2);
	    ViewGroup.LayoutParams lp =  view2.getLayoutParams();
	    lp.width = lp.FILL_PARENT;
	    view2.setLayoutParams(lp);
	}


	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch(arg0.getId()){
		case R.id.btn_mycenter_forgetpassword_back:
			this.finish();
			break;
		case R.id.btn_mycenter_forgetpassword_finish:
			if(is_click==0){
                 is_click=1;
				oldpassword = etmodifypasswordOld.getText().toString();
			    newpassword = etmodifypasswordNew.getText().toString();
				String newpassword2 = etmodifypasswordNew2.getText().toString();
				if(oldpassword.trim().equals("")){
			Toast.makeText(ModifyPassWordActivity.this, "旧密码不能为空", 1).show();
			is_click=0;
				}
				else if(newpassword.trim().equals("")){
					Toast.makeText(ModifyPassWordActivity.this, "新密码不能为空", 1).show();
					is_click=0;
				}
				else if(newpassword.equals(newpassword2)){
					ModifyPassword();
				}
				else{
					is_click=0;
					Toast.makeText(ModifyPassWordActivity.this, "两次输入的新密码不相同，请重新输入密码", 1).show();
				}
				
				
					
			}
			
			
			break;
		case R.id.ll_tohome :
			Intent intenthome =new Intent(ModifyPassWordActivity.this,HomeMainActivity.class);
			startActivity(intenthome);
			ExitApplication.getInstance().exit();
			break;
		case R.id.ll_tosearch :
			Intent intentsearch =new Intent(ModifyPassWordActivity.this,SearchMainActivity.class);
			startActivity(intentsearch);
			ExitApplication.getInstance().exit();
			break;
		case R.id.ll_toshoppingcart :
			Intent intentshoppingcart =new Intent(ModifyPassWordActivity.this,ShoppingcartMainListActivity.class);
			startActivity(intentshoppingcart);
			if(GlobalVarUtil.account.getUID()==null){
				Intent intentTolongin = new Intent(ModifyPassWordActivity.this,LoginActivity.class);
				startActivity(intentTolongin);
			}
//			ExitApplication.getInstance().exit();//购物车可以回退
			break;
		case R.id.ll_tomycenter :
			Intent intentmycenter =new Intent(ModifyPassWordActivity.this,MycenterMainActivity.class);
			startActivity(intentmycenter);
			if(GlobalVarUtil.account.getUID()==null){
				Intent intentTolongin = new Intent(ModifyPassWordActivity.this,LoginActivity.class);
				startActivity(intentTolongin);
			}
			ExitApplication.getInstance().exit();
			break;
		case R.id.ll_tomore :
			Intent intentmore =new Intent(ModifyPassWordActivity.this,MoreActivity.class);
			startActivity(intentmore);
			ExitApplication.getInstance().exit();
			break;
		
		}
	}

	
	
	
//修改密码
	
	private void ModifyPassword() {
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
               
            	   reqUrl = getString(R.string.ModifyPassWord_php);
     		   
     				parameters.put("UID",GlobalVarUtil.account.getUID());
     				parameters.put("oldPassword", oldpassword);
     				parameters.put("newPassword", newpassword);
     			
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
            	
            	Toast.makeText(ModifyPassWordActivity.this, "修改密码成功", 1).show();
            	ModifyPassWordActivity.this.finish();
            }
            else{
            	Toast.makeText(ModifyPassWordActivity.this, "修改密码失败", 1).show();
            	etmodifypasswordOld.setText("");
            	etmodifypasswordNew.setText("");
            	etmodifypasswordNew2.setText("");
            }
			}
		});
	}
}
