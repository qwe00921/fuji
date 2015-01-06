package com.fsti.fjdicClient.activity.login;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.fsti.fjdicClient.R;
import com.fsti.fjdicClient.ApplicationUtil;
import com.fsti.fjdicClient.activity.BaseActivity;
import com.fsti.fjdicClient.activity.groupBuy.GroupBuySortListActivity;
import com.fsti.fjdicClient.activity.home.HomeMainActivity;
import com.fsti.fjdicClient.activity.more.MoreActivity;
import com.fsti.fjdicClient.activity.mycenter.MycenterMainActivity;
import com.fsti.fjdicClient.activity.search.SearchMainActivity;
import com.fsti.fjdicClient.activity.shoppingcart.ShoppingcartMainListActivity;
import com.fsti.fjdicClient.bean.ExitApplication;
import com.fsti.fjdicClient.bean.RegisterEntity;
import com.fsti.fjdicClient.util.GlobalVarUtil;
import com.fsti.fjdicClient.util.HttpUtil;
import com.fsti.fjdicClient.util.ViewUtil;
import com.fsti.fjdicClient.util.asyncUtil.CallEarliest;
import com.fsti.fjdicClient.util.asyncUtil.Callable;
import com.fsti.fjdicClient.util.asyncUtil.Callback;
import com.google.gson.Gson;
/**
 * 注册
 * @author 王久叶
 *
 */
public class RegisterActivity extends BaseActivity implements OnClickListener,OnEditorActionListener{
	
//	private TextView tvUsername;
	
	private Button btnRegisterBack;
	private Button btnRegisterfinish;
	private EditText etRegisterUserName;
	private EditText etRegisterPassword;
	private EditText etRegisterPasswordagain;
	private EditText etRegisterphone;
	private EditText etRegisteremail;
	private ImageView ivRegisterload;
	private Handler myHandler = new MyHandler();
	private static final int REG_SUCCESS = 0x90001;
	private LinearLayout tohome;
	private LinearLayout tosearch;
	private LinearLayout toshoppingcart;
	private LinearLayout tomycenter;
	private LinearLayout tomore;
	private RelativeLayout bottommenu;
	private Context myContext;
	private RegisterEntity registerEntity = new RegisterEntity();
	public void onCreate(Bundle saveInstanceState){
		super.onCreate(saveInstanceState);
		setContentView(R.layout.layout_activity_login_register);
		ExitApplication.getInstance().addActivity(this);
		init();
	}

	@Override
	public void bindEvent() {
		// TODO Auto-generated method stub
		btnRegisterBack.setOnClickListener(this);
		btnRegisterfinish.setOnClickListener(this);
		tohome.setOnClickListener(this);
		tosearch.setOnClickListener(this);
		toshoppingcart.setOnClickListener(this);
		tomycenter.setOnClickListener(this);
		tomore.setOnClickListener(this);
		
		etRegisteremail.setOnEditorActionListener(this);
		etRegisterPassword.setOnEditorActionListener(this);
		etRegisterPasswordagain.setOnEditorActionListener(this);
		etRegisterUserName.setOnEditorActionListener(this);

//		etRegisteremail.addTextChangedListener(new CustomTextWatcher(etRegisteremail));
//		etRegisterPassword.addTextChangedListener(new CustomTextWatcher(etRegisterPassword));
//		etRegisterPasswordagain.addTextChangedListener(new CustomTextWatcher(etRegisterPasswordagain));
//		etRegisterUserName.addTextChangedListener(new CustomTextWatcher(etRegisterUserName));
//		etRegisterphone.addTextChangedListener(new CustomTextWatcher(etRegisterphone));
	}

	@Override
	public void initValue() {
		// TODO Auto-generated method stub
		
//		tvUsername = (TextView) findViewById(R.id.tv_register_username);
		
		btnRegisterBack = (Button)findViewById(R.id.btn_register_back);
		btnRegisterfinish = (Button)findViewById(R.id.btn_register_finish);
		
		etRegisteremail = (EditText) findViewById(R.id.et_register_email);
		etRegisterPassword= (EditText) findViewById(R.id.et_register_password);
		etRegisterPasswordagain = (EditText) findViewById(R.id.et_register_passwordagain);
		etRegisterphone = (EditText) findViewById(R.id.et_register_phone);
		etRegisterUserName = (EditText) findViewById(R.id.et_register_username);
		
		ivRegisterload = (ImageView) findViewById(R.id.iv_register_loading);
		myContext = getBaseContext();
		
		LayoutInflater	mInflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    View view2  = mInflater.inflate(R.layout.layout_item_bottom_menu,null);
	    tohome=(LinearLayout)view2.findViewById(R.id.ll_tohome);
	    tosearch=(LinearLayout) view2.findViewById(R.id.ll_tosearch);
	    toshoppingcart=(LinearLayout) view2.findViewById(R.id.ll_toshoppingcart);
	    tomycenter=(LinearLayout) view2.findViewById(R.id.ll_tomycenter);
	    tomore=(LinearLayout) view2.findViewById(R.id.ll_tomore);
	    bottommenu=(RelativeLayout) findViewById(R.id.rl_login_register_bottommenu);
	    bottommenu.addView(view2);
	    ViewGroup.LayoutParams lp =  view2.getLayoutParams();
	    lp.width = lp.FILL_PARENT;
	    view2.setLayoutParams(lp);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()){
		case R.id.btn_register_back:
			this.finish();
			break;
		case R.id.btn_register_finish:
			String errMsg = "";
			String emailStr = replaceBlank(etRegisteremail.getText().toString());
			String passwordStr = replaceBlank(etRegisterPassword.getText().toString());
			String passwordAgainStr = replaceBlank(etRegisterPasswordagain.getText().toString());
			String phoneStr = replaceBlank(etRegisterphone.getText().toString());
			String nameStr = replaceBlank(etRegisterUserName.getText().toString());
			if(!isNameValid(nameStr)){
				errMsg = "用户名3~15字符内";
				Toast.makeText(myContext, errMsg, Toast.LENGTH_LONG).show();
				break;
			}
			if(isPasswordValid(passwordStr)){
				if(!passwordStr.equals(passwordAgainStr)){
					errMsg = "密码输入不一致";
					Toast.makeText(myContext, errMsg, Toast.LENGTH_LONG).show();
					break;
				}
			}else{
				errMsg = "密码为6~30位，只包含字母数字";
				Toast.makeText(myContext, errMsg, Toast.LENGTH_LONG).show();
				break;
			}
			if(!isEmailValid(emailStr)){
				errMsg = "输入邮箱不符合格式！";
				Toast.makeText(myContext, errMsg, Toast.LENGTH_LONG).show();
				break;
			}
			if(phoneStr.equals("")){
				registerEntity.setTelephone(phoneStr);
			}else{
				if(!isPhoneNumberValid(phoneStr)){
					errMsg = "手机号码不正确！请重新输入";
					Toast.makeText(myContext, errMsg, Toast.LENGTH_LONG).show();
					break;
				}else{
					registerEntity.setTelephone(phoneStr);
				}
			}
			registerEntity.setNickName(nameStr);
			registerEntity.setPassword(passwordStr);
			registerEntity.setEmail(emailStr);
			
					
			
			
			psotRegisterEntity();
			break;
		case R.id.ll_tohome :
			Intent intenthome =new Intent(RegisterActivity.this,HomeMainActivity.class);
			startActivity(intenthome);
			ExitApplication.getInstance().exit();
			break;
		case R.id.ll_tosearch :
			Intent intentsearch =new Intent(RegisterActivity.this,SearchMainActivity.class);
			startActivity(intentsearch);
			ExitApplication.getInstance().exit();
			break;
		case R.id.ll_toshoppingcart :
			Intent intentshoppingcart =new Intent(RegisterActivity.this,ShoppingcartMainListActivity.class);
			startActivity(intentshoppingcart);
			if(GlobalVarUtil.account.getUID()==null){
				Intent intentTolongin = new Intent(RegisterActivity.this,LoginActivity.class);
				startActivity(intentTolongin);
			}
//			ExitApplication.getInstance().exit();//购物车可以返回
			break;
		case R.id.ll_tomycenter :
			Intent intentmycenter =new Intent(RegisterActivity.this,MycenterMainActivity.class);
			startActivity(intentmycenter);
			if(GlobalVarUtil.account.getUID()==null){
				Intent intentTolongin = new Intent(RegisterActivity.this,LoginActivity.class);
				startActivity(intentTolongin);
			}
			ExitApplication.getInstance().exit();
			break;
		case R.id.ll_tomore :
			Intent intentmore =new Intent(RegisterActivity.this,MoreActivity.class);
			startActivity(intentmore);
			ExitApplication.getInstance().exit();
			break;
		}
	}
	
	private void psotRegisterEntity() {
		this.doAsync(new CallEarliest<Object>() {
			@Override
			public void onCallEarliest() throws Exception {
				// TODO Auto-generated method stub
				//显示正在登陆页面
			    ivRegisterload.setVisibility(View.VISIBLE);
				ViewUtil.addLoadingAnimation(ApplicationUtil.myContext,
						ivRegisterload);
			}
		}, new Callable<Object>() {
			@Override
			public String call() {
				//向服务器发送数据
				String reqUrl = getString(R.string.register_http);
				
				Map<String, Object> parameters = new LinkedHashMap<String, Object>();
				String registerJson = toJSON(registerEntity);
				parameters.put("RegisterEntity", registerJson);
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
				ViewUtil.removeLoadingAnimation(ivRegisterload);
				String register=replaceBlank(jsonStr);
				System.out.println(register+"dfdf");
				if (register.equals("000")||register.equals("010")) {//电话号码重复也成功
					ViewUtil.showToast(myContext, myContext.getString(R.string.register_success));
					myHandler.sendEmptyMessage(REG_SUCCESS);
				} 
				if (register.equals("222")) {
					ViewUtil.showToast(myContext, myContext.getString(R.string.register_fail));
				} 
				if (register.equals("333")) {
					ViewUtil.showToast(myContext, myContext.getString(R.string.register_already));
				} 
				if (register.equals("100")) {
					ViewUtil.showToast(myContext, myContext.getString(R.string.register_name_repeat));
				} 
				if (register.equals("110")) {
					ViewUtil.showToast(myContext, myContext.getString(R.string.register_name_phone_repeat));
				} 
				if (register.equals("111")) {
					ViewUtil.showToast(myContext, myContext.getString(R.string.register_name_email_phone_repeat));
				} 
				if (register.equals("011")) {
					ViewUtil.showToast(myContext, myContext.getString(R.string.register_email_phone_repeat));
				} 
//				if (register.equals("010")) {
//					ViewUtil.showToast(myContext, myContext.getString(R.string.register_phone_repeat));
//				} 
				if (register.equals("001")) {
					ViewUtil.showToast(myContext, myContext.getString(R.string.register_email_repeat));
				} 
				if (register.equals("101")) {
					ViewUtil.showToast(myContext, myContext.getString(R.string.register_name_email_repeat));
				} 
			}
		});
	}
	public static String toJSON(Object obj) {  
        Gson gson = new Gson();  
        String js = gson.toJson(obj);
        return js.toString();  
    } 



	private boolean checkregisterEntity() {
		// TODO Auto-generated method stub
		boolean isOK = true;
		String errMsg = "";
//		String emailStr = etRegisteremail.getText().toString();
//		String passwordStr = etRegisterPassword.getText().toString();
//		String passwordAgainStr = etRegisterPasswordagain.getText().toString();
//		String phoneStr = etRegisterphone.getText().toString();
//		String nameStr = etRegisterUserName.getText().toString();
		String emailStr = replaceBlank(etRegisteremail.getText().toString());
		String passwordStr = replaceBlank(etRegisterPassword.getText().toString());
		String passwordAgainStr = replaceBlank(etRegisterPasswordagain.getText().toString());
		String phoneStr = replaceBlank(etRegisterphone.getText().toString());
		String nameStr = replaceBlank(etRegisterUserName.getText().toString());
		if(isNameValid(nameStr)){
			registerEntity.setNickName(nameStr);
			etRegisterUserName.setHint("请输入用户名");
		}else{
			etRegisterUserName.setHint("用户名3~15字符内");
			isOK=false;
		}
		if(isPasswordValid(passwordStr)){
			if(passwordStr.equals(passwordAgainStr)){
				registerEntity.setPassword(passwordStr);
				etRegisterPasswordagain.setHint("请再次输入登陆密码");
				etRegisterPassword.setHint("请输入登陆密码");
			}else{
				etRegisterPasswordagain.setHint("密码输入不一致");
				etRegisterPassword.setHint("密码输入不一致");
				isOK=false;
			}
		}else{
			etRegisterPasswordagain.setHint("密码为6~30位，只包含字母数字");
			etRegisterPassword.setHint("密码为6~30位，只包含字母数字");
			isOK=false;
		}
		if(isEmailValid(emailStr)){
			registerEntity.setEmail(emailStr);
			etRegisteremail.setHint("请输入您的电子邮箱");
		}else{
			etRegisteremail.setHint("输入邮箱不符合格式！");
			isOK=false;
		}
		if(isPhoneNumberValid(phoneStr)){
			registerEntity.setTelephone(phoneStr);
			etRegisterphone.setHint("请输入本机号码");
		}else{
			etRegisterphone.setHint("手机号码不正确！请重新输入");
			isOK=false;
		}
		return isOK;
	}

	public static boolean isEmailValid(String strEmail) {
	    String strPattern ="^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
//	    String strPattern ="^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/";
	    Pattern p = Pattern.compile(strPattern);
	    Matcher m = p.matcher(strEmail);
	    return m.matches();
	}
	public static boolean isPhoneNumberValid(String mobiles)  
	{  
		Pattern p = Pattern
			.compile("[1][358]\\d{9}");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	 } 
	public static boolean isPasswordValid(String password)  
	{  
		Pattern p = Pattern
		.compile("[a-zA-Z0-9]{6,30}");
		Matcher m = p.matcher(password);
		return m.matches();
	} 
	public static boolean isNameValid(String name)  
	{  
		int length = name.length();
		if(length >= 3 && length <= 15){
			return true;
		}else{
			return false;
		}
	
	} 
	public static String replaceBlank(String str) { 
		String dest = "";  
		if (str!=null) {  
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");  
			Matcher m = p.matcher(str);  
			dest = m.replaceAll("");  
		}
		return dest;
	} 
	
	class MyHandler extends Handler{
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case REG_SUCCESS:
				finish();
				break;

			default:
				break;
			}
		}
	}

	@Override
	public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2) {
		// TODO Auto-generated method stub
		return  (arg2.getKeyCode() == KeyEvent.KEYCODE_ENTER);
	}
	
//	class CustomTextWatcher implements TextWatcher{
//		private EditText e ;
//		public CustomTextWatcher(EditText e){
//			this.e = e;
//		}
//		@Override
//		public void afterTextChanged(Editable arg0) {
//			// TODO Auto-generated method stub
//			switch (e.getId()) {
//			case R.id.et_register_email:
//				
//				break;
//			case R.id.et_register_password:
//				
//				break;
//			case R.id.et_register_passwordagain:
//				
//				break;
//			case R.id.et_register_phone:
//				
//				break;
//			case R.id.et_register_username:
//				if (e.getText().toString().equals("")) {
//					tvUsername.setVisibility(View.GONE);
//				}else {
//					tvUsername.setVisibility(View.VISIBLE);
//				}
//				break;
//			}
//		}
//		
//		@Override
//		public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
//				int arg3) {
//			// TODO Auto-generated method stub
//			
//		}
//		
//		@Override
//		public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
//			// TODO Auto-generated method stub
//			
//		}
//		
//	}
}
