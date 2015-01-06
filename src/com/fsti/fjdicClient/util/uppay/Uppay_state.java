package com.fsti.fjdicClient.util.uppay;

import java.util.LinkedHashMap;
import java.util.Map;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.fsti.fjdicClient.R;
import com.fsti.fjdicClient.ApplicationUtil;
import com.fsti.fjdicClient.activity.BaseActivity;
import com.fsti.fjdicClient.activity.mycenter.MycenterMainActivity;
import com.fsti.fjdicClient.bean.ExitApplication;
import com.fsti.fjdicClient.util.GlobalVarUtil;
import com.fsti.fjdicClient.util.HttpUtil;
import com.fsti.fjdicClient.util.ViewUtil;
import com.fsti.fjdicClient.util.asyncUtil.CallEarliest;
import com.fsti.fjdicClient.util.asyncUtil.Callable;
import com.fsti.fjdicClient.util.asyncUtil.Callback;
import com.google.gson.Gson;

public class Uppay_state extends BaseActivity implements OnClickListener{
private Button back;
private ImageView paystate;
private Button confirm;
private ImageView ivLoading;
private TextView tvtext;
private String pay_result;
private String orderID;	

public boolean onKeyDown(int keyCode, KeyEvent event) {
	if (keyCode == KeyEvent.KEYCODE_BACK) {
		Intent intent = new Intent(Uppay_state.this,MycenterMainActivity.class);
		startActivity(intent);
		ExitApplication.getInstance().exit();
	}

	return super.onKeyDown(keyCode, event);
}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_uppay_state);
		ExitApplication.getInstance().addActivity(this);
		init();
	}

	@Override
	public void bindEvent() {
		// TODO Auto-generated method stub
		back.setOnClickListener(this);
		confirm.setOnClickListener(this);
	}

	@Override
	public void initValue() {
		// TODO Auto-generated method stub
		back = (Button) this.findViewById(R.id.btn_uppay_state_back);
		paystate = (ImageView) this.findViewById(R.id.img_uppay_state);
		confirm = (Button) this.findViewById(R.id.btn_uppay_state_confirm);
		ivLoading=(ImageView) findViewById(R.id.iv_uppay_state_loading);
		tvtext = (TextView) findViewById(R.id.tv_uppay_state_text);
		ivLoading.setVisibility(View.VISIBLE);
		ViewUtil.addLoadingAnimation(ApplicationUtil.myContext,
				ivLoading);
		Intent intent = this.getIntent();
		orderID = intent.getStringExtra("orderID");
		pay_result = intent.getStringExtra("pay_result");
		
		CheckPayState();
		
		
	}
	
	private void reload_view(){
		ViewUtil.removeLoadingAnimation(ivLoading);
		tvtext.setVisibility(View.GONE);
		paystate.setVisibility(View.VISIBLE);
		confirm.setVisibility(View.VISIBLE);
		if(pay_result.equalsIgnoreCase("success")){
			paystate.setImageResource(R.drawable.iv_uppay_success);	
		}
		else if(pay_result.equalsIgnoreCase("fail")){
			paystate.setImageResource(R.drawable.iv_uppay_fail);	
		}
		else if(pay_result.equalsIgnoreCase("cancel")){
			paystate.setImageResource(R.drawable.iv_uppay_cancle);	
		}
		
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch(arg0.getId()){
		case R.id.btn_uppay_state_back:
		case R.id.btn_uppay_state_confirm:
			Intent intent = new Intent(Uppay_state.this,MycenterMainActivity.class);
			startActivity(intent);
			ExitApplication.getInstance().exit();
			break;
		}
	}
	//校验支付状态
	
	private void CheckPayState() {
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
             
				 reqUrl = getString(R.string.CheckPayState_php);
			
	        String result="0";
	        if (pay_result.equalsIgnoreCase("success")) {
	        	result="1";
	        } 
				parameters.put("orderID",orderID);
				parameters.put("paystate", result);
				 
	
               
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
            if(jsonStr.trim().equals("1")){
            	pay_result="success";
            }
           
            reload_view();
            
			}
		});
	}
	
}
