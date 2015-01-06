package com.fsti.fjdicClient.activity.login;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;

import android.R.string;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TextView.OnEditorActionListener;

import com.fsti.fjdicClient.R;
import com.fsti.fjdicClient.ApplicationUtil;
import com.fsti.fjdicClient.activity.BaseActivity;
import com.fsti.fjdicClient.activity.groupBuy.GroupBuySortListActivity;
import com.fsti.fjdicClient.activity.home.HomeMainActivity;
import com.fsti.fjdicClient.activity.more.MoreActivity;
import com.fsti.fjdicClient.activity.mycenter.ModifyPhoneActivity;
import com.fsti.fjdicClient.activity.mycenter.MycenterMainActivity;
import com.fsti.fjdicClient.activity.search.SearchMainActivity;
import com.fsti.fjdicClient.activity.shoppingcart.ShoppingcartMainListActivity;
import com.fsti.fjdicClient.bean.ExitApplication;
import com.fsti.fjdicClient.util.GlobalVarUtil;
import com.fsti.fjdicClient.util.HttpUtil;
import com.fsti.fjdicClient.util.ViewUtil;
import com.fsti.fjdicClient.util.asyncUtil.CallEarliest;
import com.fsti.fjdicClient.util.asyncUtil.Callable;
import com.fsti.fjdicClient.util.asyncUtil.Callback;
/**
 * 忘记密码
 * @author 王久叶
 *
 */
public class ForgetPasswordActivity extends BaseActivity implements OnClickListener,OnEditorActionListener{
	private Button btnForgetpwdBack;
	private LinearLayout tohome;
	private LinearLayout tosearch;
	private LinearLayout toshoppingcart;
	private LinearLayout tomycenter;
	private LinearLayout tomore;
	private RelativeLayout bottommenu;
	
	private LinearLayout llnamepwd;
	private RelativeLayout rlphone;
	private RelativeLayout rlcode;
	private SharedPreferences saveForgetpwd;
	private boolean isGotCode = false;
	
	private EditText etPhone;
	private EditText etCode;
	private EditText etUserName;
	private EditText etPass;
	
	private Button btnCode;
	private Button btnFinish;
	
	private String strPhone;
	private String msgCode;
	private String nickName;
	private String newKey;
	private int last_seconds = 0;// 表示还剩多少秒之后才能获取短信验证码
	private CountDownTimer countdowntimer; 	
	
	public void onCreate(Bundle saveInstanceState){
		super.onCreate(saveInstanceState);
		setContentView(R.layout.layout_activity_login_forgetpwd);
		ExitApplication.getInstance().addActivity(this);
		init();
	}

	@Override
	public void bindEvent() {
		// TODO Auto-generated method stub
		 etPhone.setOnEditorActionListener(this);
		 etCode.setOnEditorActionListener(this);
		 etUserName.setOnEditorActionListener(this);
		 etPass.setOnEditorActionListener(this);
			
		
		btnForgetpwdBack.setOnClickListener(this);
		btnCode.setOnClickListener(this);
		btnFinish.setOnClickListener(this);
		
		tohome.setOnClickListener(this);
		tosearch.setOnClickListener(this);
		toshoppingcart.setOnClickListener(this);
		tomycenter.setOnClickListener(this);
		tomore.setOnClickListener(this);
	}

	@Override
	public void initValue() {
		// TODO Auto-generated method stub
		rlphone = (RelativeLayout) findViewById(R.id.rl_forget11);
		rlcode = (RelativeLayout) findViewById(R.id.rl_forget12);
		llnamepwd = (LinearLayout) findViewById(R.id.ll_name_pwd);
		saveForgetpwd = getSharedPreferences("modifycode",
				ForgetPasswordActivity.this.MODE_WORLD_WRITEABLE);
		
		etUserName = (EditText) findViewById(R.id.et_forgetpwd_username);
		etCode = (EditText) findViewById(R.id.et_forgetpwd_checkcode);
		etPhone = (EditText) findViewById(R.id.et_forgetpwd_phonenum);
		etPass = (EditText) findViewById(R.id.et_forgetpwd_password);
		
		btnCode = (Button) findViewById(R.id.btn_forgetpwd_obtaincode);
		btnFinish = (Button) findViewById(R.id.btn_forgetpwd_finish);
		
		btnForgetpwdBack = (Button)findViewById(R.id.btn_forgetpwd_back);
		LayoutInflater	mInflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view2  = mInflater.inflate(R.layout.layout_item_bottom_menu,null);
	    tohome=(LinearLayout)view2.findViewById(R.id.ll_tohome);
	    tosearch=(LinearLayout) view2.findViewById(R.id.ll_tosearch);
	    toshoppingcart=(LinearLayout) view2.findViewById(R.id.ll_toshoppingcart);
	    tomycenter=(LinearLayout) view2.findViewById(R.id.ll_tomycenter);
	    tomore=(LinearLayout) view2.findViewById(R.id.ll_tomore);
	    bottommenu=(RelativeLayout) findViewById(R.id.rl_login_forgetpassword_bottommenu);
	    bottommenu.addView(view2);
	    ViewGroup.LayoutParams lp =  view2.getLayoutParams();
	    lp.width = lp.FILL_PARENT;
	    view2.setLayoutParams(lp);
	}
	private String replaceBlank(String str) {
		String dest = "";
		if (str != null) {
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()){
		case R.id.btn_forgetpwd_finish:
			if(!isGotCode){
				msgCode = replaceBlank(etCode.getText().toString());
				if(msgCode.equals("")){
					Toast.makeText(ForgetPasswordActivity.this,
							"请输入验证码", 1).show();
					break;
				}
				strPhone = etPhone.getText().toString();
				if(strPhone.equals("")){
					Toast.makeText(ForgetPasswordActivity.this,
							"请输入手机号", 1).show();
					break;
				}else{
					if(!RegisterActivity.isPhoneNumberValid(strPhone)){
						ViewUtil.showToast(myContext, "请输入正确手机号码");
						break;
					}
				}
				String code = saveForgetpwd.getString("Forgetpwdcode",
						null);
				String phone= saveForgetpwd.getString("Forgetpwdphone",
						null);
				if (phone == null || code == null) {
					Toast.makeText(ForgetPasswordActivity.this,
							"请先获取短信验证信息", 1).show();
				} else {
					if (phone.equals(strPhone)) {
						if (code.equals(msgCode)) {
							rlcode.setVisibility(View.GONE);
							rlphone.setVisibility(View.GONE);
							btnCode.setVisibility(View.GONE);
							llnamepwd.setVisibility(View.VISIBLE);
							isGotCode = true;
						} else {
							Toast.makeText(ForgetPasswordActivity.this,
									"短信验证码错误，请核对验证信息", 1).show();
						}
					} else {
						Toast.makeText(ForgetPasswordActivity.this,
								"要绑定的手机号与验证的手机号不一致，请核对手机号", 1).show();
					}
				}
				break;
			}
			if(isGotCode){
				nickName = replaceBlank(etUserName.getText().toString());
				if(!RegisterActivity.isNameValid(nickName)){
					ViewUtil.showToast(myContext, "用户名3~15字符内");
					break;
				}
				newKey = replaceBlank(etPass.getText().toString());
				if(!RegisterActivity.isPasswordValid(newKey)){
					ViewUtil.showToast(myContext, "密码为6~30位，只包含字母数字");
					break;
				}
				postNewKey();
			}
			
			break;
		case R.id.btn_forgetpwd_obtaincode:
			strPhone = replaceBlank(etPhone.getText().toString());
			if(strPhone.equals("")){
				ViewUtil.showToast(myContext, "请输入手机号");
				break;
			}else{
				if(!RegisterActivity.isPhoneNumberValid(strPhone)){
					ViewUtil.showToast(myContext, "手机号码不正确，请重新输入。");
					break;
				}else{
					if (last_seconds == 0) {
							last_seconds = 60;
							coundowntimer_start(last_seconds);
							postCode();
					} else {
						Toast.makeText(ForgetPasswordActivity.this,
								"请再等待" + last_seconds + "秒再操作。", 1).show();
					}
				}
			}
			break;
		case R.id.btn_forgetpwd_back:
			this.finish();
			break;
		case R.id.ll_tohome :
			Intent intenthome =new Intent(ForgetPasswordActivity.this,HomeMainActivity.class);
			startActivity(intenthome);
			ExitApplication.getInstance().exit();
			break;
		case R.id.ll_tosearch :
			Intent intentsearch =new Intent(ForgetPasswordActivity.this,SearchMainActivity.class);
			startActivity(intentsearch);
			ExitApplication.getInstance().exit();
			break;
		case R.id.ll_toshoppingcart :
			Intent intentshoppingcart =new Intent(ForgetPasswordActivity.this,ShoppingcartMainListActivity.class);
			startActivity(intentshoppingcart);
			if(GlobalVarUtil.account.getUID()==null){
				Intent intentTolongin = new Intent(ForgetPasswordActivity.this,LoginActivity.class);
				startActivity(intentTolongin);
			}
//			ExitApplication.getInstance().exit();//购物车可以返回
			break;
		case R.id.ll_tomycenter :
			Intent intentmycenter =new Intent(ForgetPasswordActivity.this,MycenterMainActivity.class);
			startActivity(intentmycenter);
			if(GlobalVarUtil.account.getUID()==null){
				Intent intentTolongin = new Intent(ForgetPasswordActivity.this,LoginActivity.class);
				startActivity(intentTolongin);
			}
			ExitApplication.getInstance().exit();
			break;
		case R.id.ll_tomore :
			Intent intentmore =new Intent(ForgetPasswordActivity.this,MoreActivity.class);
			startActivity(intentmore);
			ExitApplication.getInstance().exit();
			break;
		}
	}
	private void coundowntimer_start(int seconds) {
		countdowntimer = new CountDownTimer(seconds * 1000, 1000) {
			@Override
			public void onFinish() {
				// done
				last_seconds = last_seconds - 1;
				countdowntimer.cancel();
			}

			@Override
			public void onTick(long arg0) {
				// 每1000毫秒回调的方法
				last_seconds = last_seconds - 1;
			}

		}.start();

	}
	private void postCode() {
		// TODO Auto-generated method stub
		this.doAsync(new CallEarliest<Object>() {
			@Override
			public void onCallEarliest() throws Exception {
				// TODO Auto-generated method stub
			}
		}, new Callable<Object>() {
			@Override
			public String call() {
				//向服务器发送数据
				String reqUrl = getString(R.string.GetAuthCode_php);
				
				Map<String, Object> parameters = new LinkedHashMap<String, Object>();
//				String registerJson = toJSON(registerEntity);
//				parameters.put("UID", UID);
				parameters.put("telephone", strPhone);
				
				String jsonStr = "";
				try {
					jsonStr = HttpUtil.postData(reqUrl, parameters,
							GlobalVarUtil.ENCODING);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					Log.e("RegisterActivity", e.toString());
					e.printStackTrace();
					return "-1";
				}
				return jsonStr;
			}
		}, new Callback<String>() {
			@Override
			public void onCallback(String jsonStr) {
				//对服务器传回数据进行处理
				jsonStr = RegisterActivity.replaceBlank(jsonStr);
				if (jsonStr.trim().equals("1")||jsonStr.trim().equals("-1")) {
					Toast.makeText(ForgetPasswordActivity.this,
							"获取短信验证码失败，请重试", 1).show();
				} else {
					JSONObject json;
					try {
						json = new JSONObject(jsonStr);
						Editor editor = saveForgetpwd.edit();
						editor.putString("Forgetpwdcode", json.getString("msgCode"));
						editor.putString("Forgetpwdphone", strPhone);
						editor.commit();
						Toast.makeText(ForgetPasswordActivity.this,
								"成功发送申请，请等待验证码", 1).show();
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						Toast.makeText(ForgetPasswordActivity.this,
								"获取短信验证码失败，请重试", 1).show();
					}
				}
			}
		});
	}

	private void postNewKey() {
		// TODO Auto-generated method stub
		this.doAsync(new CallEarliest<Object>() {
			@Override
			public void onCallEarliest() throws Exception {
				// TODO Auto-generated method stub
			}
		}, new Callable<Object>() {
			@Override
			public String call() {
				//向服务器发送数据
				String reqUrl = getString(R.string.ForgetPassword_php);
				
				Map<String, Object> parameters = new LinkedHashMap<String, Object>();
//				String registerJson = toJSON(registerEntity);
				parameters.put("nickName", nickName);
				parameters.put("newPassword", newKey);
				parameters.put("telephone", saveForgetpwd.getString("Forgetpwdphone",null));
//				parameters.put("msgCode", msgCode);
				String jsonStr = "";
				try {
					jsonStr = HttpUtil.postData(reqUrl, parameters,
							GlobalVarUtil.ENCODING);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					Log.e("fprgetpwdActivity", e.toString());
					e.printStackTrace();
					return "-1";
				}
				return jsonStr;
			}
		}, new Callback<String>() {
			@Override
			public void onCallback(String jsonStr) {
				//对服务器传回数据进行处理
				String str = replaceBlank(jsonStr);
				if (str.equals("-1")||str.equals("")) {
					Toast.makeText(ForgetPasswordActivity.this,
							"网络访问失败，请重新修改", 1).show();
				} else if(str.equals("0")) {
					Toast.makeText(ForgetPasswordActivity.this,
							"密码修改成功", 1).show();
				}else if(str.equals("12")) {
					Toast.makeText(ForgetPasswordActivity.this,
							"修改失败，用户名错误", 1).show();
				}else if(str.equals("13")) {
					Toast.makeText(ForgetPasswordActivity.this,
							"修改失败，请重新修改", 1).show();
				}
			}
		});
	}

	@Override
	public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		// TODO Auto-generated method stub
		return (event.getKeyCode() == KeyEvent.KEYCODE_ENTER);
	}

}
