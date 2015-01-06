package com.fsti.fjdicClient.activity.login;

import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
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
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;
import com.fsti.fjdicClient.R;
import com.fsti.fjdicClient.activity.BaseActivity;
import com.fsti.fjdicClient.activity.groupBuy.GroupBuySortListActivity;
import com.fsti.fjdicClient.activity.home.HomeMainActivity;
import com.fsti.fjdicClient.activity.more.MoreActivity;
import com.fsti.fjdicClient.activity.mycenter.MycenterMainActivity;
import com.fsti.fjdicClient.activity.search.SearchMainActivity;
import com.fsti.fjdicClient.activity.shoppingcart.ShoppingcartMainListActivity;
import com.fsti.fjdicClient.bean.AccountInfo;
import com.fsti.fjdicClient.bean.ExitApplication;
import com.fsti.fjdicClient.util.GlobalVarUtil;
import com.fsti.fjdicClient.util.HttpUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 登陆
 * 
 * @author 王久叶
 * 
 */
public class LoginActivity extends BaseActivity implements OnClickListener ,OnEditorActionListener{
	

	private Button btnLoginForgetpwd;
	private Button btnLoginRegister;
	private Button btnLogin;
	private EditText etLoginUsername;
	private EditText etLoginPassword;
	private ProgressDialog m_Dialog;
	private MyHandler mHandler;
	private final int LOGIN_SUCCESS = 0;// 登录成功
	private final int LOGIN_ERROR = 1;// 登录错误
	private final int LOGIN_FAILD = 2;// 登录错误
	private Toast _mToast = null;
	private TextView tvloginPrompt;
	private Context context;
	private LinearLayout tohome;
	private LinearLayout tosearch;
	private LinearLayout toshoppingcart;
	private LinearLayout tomycenter;
	private LinearLayout tomore;
	private RelativeLayout bottommenu;

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {

			Builder builder = new AlertDialog.Builder(LoginActivity.this);
			builder.setTitle("是否退出");
			builder.setMessage("按确定键退出！");
			builder.setPositiveButton("确定",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface arg0, int arg1) {
							// TODO Auto-generated method stub
							GlobalVarUtil.firstUpdateApp="update";
							ExitApplication.getInstance().exit();
							System.exit(0);
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

		return super.onKeyDown(keyCode, event);
	}
	public void onCreate(Bundle saveInstanceState) {
		super.onCreate(saveInstanceState);
		setContentView(R.layout.layout_activity_login_main);
		ExitApplication.getInstance().addActivity(this);
		init();
	}

	@Override
	public void bindEvent() {
		// TODO Auto-generated method stub
		btnLoginForgetpwd.setOnClickListener(this);
		btnLoginRegister.setOnClickListener(this);
		btnLogin.setOnClickListener(this);
		tohome.setOnClickListener(this);
		tosearch.setOnClickListener(this);
		toshoppingcart.setOnClickListener(this);
		tomycenter.setOnClickListener(this);
		tomore.setOnClickListener(this);
		
		etLoginPassword.setOnEditorActionListener(this);
		etLoginUsername.setOnEditorActionListener(this);
	}

	@Override
	public void initValue() {
		// TODO Auto-generated method stub
		mHandler = new MyHandler(getMainLooper());
		btnLoginForgetpwd = (Button) findViewById(R.id.btn_login_forgetpwd);
		btnLoginRegister = (Button) findViewById(R.id.btn_login_register);
		btnLogin = (Button) findViewById(R.id.btn_login_login);
		etLoginPassword = (EditText) findViewById(R.id.et_login_password);
		etLoginUsername = (EditText) findViewById(R.id.et_login_username);
		tvloginPrompt = (TextView) findViewById(R.id.tv_login_prompt);
		context = getBaseContext();
		LayoutInflater	mInflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		 View view2  = mInflater.inflate(R.layout.layout_item_bottom_menu,null);
		    tohome=(LinearLayout)view2.findViewById(R.id.ll_tohome);
		    tosearch=(LinearLayout) view2.findViewById(R.id.ll_tosearch);
		    toshoppingcart=(LinearLayout) view2.findViewById(R.id.ll_toshoppingcart);
		    tomycenter=(LinearLayout) view2.findViewById(R.id.ll_tomycenter);
		    tomore=(LinearLayout) view2.findViewById(R.id.ll_tomore);
		    bottommenu=(RelativeLayout) findViewById(R.id.rl_login_login_bottommenu);
		    bottommenu.addView(view2);
		    ViewGroup.LayoutParams lp =  view2.getLayoutParams();
		    lp.width = lp.FILL_PARENT;
		    view2.setLayoutParams(lp);
	}

	private class MyHandler extends Handler {

		@Override
		public void handleMessage(Message msg) {
			if (m_Dialog != null)
				m_Dialog.dismiss();
			switch (msg.what) {
			case LOGIN_SUCCESS:
				SharedPreferences logininfo=getSharedPreferences("logininfo", LoginActivity.this.MODE_WORLD_WRITEABLE) ;
			    Editor editor=  logininfo.edit();
			    editor.putString("nickname", GlobalVarUtil.account.getNickName());
			    editor.putString("UID", GlobalVarUtil.account.getUID());
			    editor.commit();
				//System.out.println("UID==============="+GlobalVarUtil.account.getUID());
				
					System.out.println("成功");
					
					LoginActivity.this.finish();
				
				break;
			case LOGIN_FAILD:
				System.out.println("失败");
				tvloginPrompt.setText("用户名或密码错误" + "请重新输入");
				tvloginPrompt.setVisibility(View.VISIBLE);
				break;
			case LOGIN_ERROR:
				System.out.println("失败");
				Toast.makeText(LoginActivity.this, "网络或连接错误",
						Toast.LENGTH_SHORT).show();
				break;
			
			}

		}

		private MyHandler(Looper looper) {
			super(looper);
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent;
		switch (v.getId()) {
		case R.id.btn_login_login:
			postAccountInfo();
			break;
		case R.id.btn_login_forgetpwd:
			intent = new Intent(LoginActivity.this,
					ForgetPasswordActivity.class);
			startActivity(intent);
			break;
		case R.id.btn_login_register:
			intent = new Intent(LoginActivity.this, RegisterActivity.class);
			startActivity(intent);
			break;
		case R.id.ll_tohome :
			Intent intenthome =new Intent(LoginActivity.this,HomeMainActivity.class);
			startActivity(intenthome);
			ExitApplication.getInstance().exit();
			break;
		case R.id.ll_tosearch :
			Intent intentsearch =new Intent(LoginActivity.this,SearchMainActivity.class);
			startActivity(intentsearch);
			ExitApplication.getInstance().exit();
			break;
		case R.id.ll_toshoppingcart :
			Intent intentshoppingcart =new Intent(LoginActivity.this,ShoppingcartMainListActivity.class);
			startActivity(intentshoppingcart);
			if(GlobalVarUtil.account.getUID()==null){
				Intent intentTolongin = new Intent(LoginActivity.this,LoginActivity.class);
				startActivity(intentTolongin);
			}
//			ExitApplication.getInstance().exit();//购物车可以返回
			break;
		case R.id.ll_tomycenter :
			Intent intentmycenter =new Intent(LoginActivity.this,MycenterMainActivity.class);
			startActivity(intentmycenter);
			if(GlobalVarUtil.account.getUID()==null){
				Intent intentTolongin = new Intent(LoginActivity.this,LoginActivity.class);
				startActivity(intentTolongin);
			
			}
			ExitApplication.getInstance().exit();
			break;
		case R.id.ll_tomore :
			Intent intentmore =new Intent(LoginActivity.this,MoreActivity.class);
			startActivity(intentmore);
			ExitApplication.getInstance().exit();
			break;
		
		}
	}

	private void postAccountInfo() {
		// TODO Auto-generated method stub
		final String password = replaceBlank(etLoginPassword.getText()
				.toString());
		final String username = replaceBlank(etLoginUsername.getText()
				.toString());
		if (!isvalid(username, password)) {
			tvloginPrompt.setText("用户名或密码不能为空");
			tvloginPrompt.setVisibility(View.VISIBLE);
		} else {
			m_Dialog = ProgressDialog.show(LoginActivity.this, "请等待...",
					"正在登录中，请稍候...", true);
			new Thread() {
				public void run() {
					String reqUrl = getString(R.string.login_http);

					Map<String, Object> parameters = new LinkedHashMap<String, Object>();
					parameters.put("accountName", etLoginUsername.getText()
							.toString());
					parameters.put("accountPassword", etLoginPassword.getText()
							.toString());
					try {
						String jsonStr = HttpUtil.postData(reqUrl, parameters,
								GlobalVarUtil.ENCODING);
						// String jsonStr = "";//测试
						System.out.println("------>" + jsonStr);
						if ((replaceBlank(jsonStr)).equals("1")) {
							Message msg = mHandler.obtainMessage(LOGIN_FAILD);
							mHandler.sendMessage(msg);
						} else if ((replaceBlank(jsonStr)).equals("-1")) {
							Message msg = mHandler.obtainMessage(LOGIN_FAILD);
							mHandler.sendMessage(msg);
						} else {// 正式使用
							System.out.println("json 解析");
							AccountInfo accountInfo = new AccountInfo();
							Type type = new TypeToken<AccountInfo>() {
							}.getType();
							accountInfo = new Gson().fromJson(jsonStr, type);
							// 测试使用====================================================================
							// AccountInfo accountInfo = new AccountInfo();
							// accountInfo.setIsLogin(0);
							// accountInfo.setNickname("测试");
							// accountInfo.setUID("1001");
							// 测试使用====================================================================
							GlobalVarUtil.account = accountInfo;
							System.out.println("accountInfo getIsLogin:"+GlobalVarUtil.account.getIsLogin());
							System.out.println("accountInfo getEmail: "+GlobalVarUtil.account.getEmail());
							System.out.println("accountInfo getNickname:"+GlobalVarUtil.account.getNickName());
							System.out.println("accountInfo getTelephone:"+GlobalVarUtil.account.getTelephone());
							System.out.println("accountInfo getUID:"+GlobalVarUtil.account.getUID());
							
							Message msg = mHandler.obtainMessage(LOGIN_SUCCESS);
							mHandler.sendMessage(msg);
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						Log.e("LoginActivity", e.toString());
						e.printStackTrace();
						System.out.println("报错");
						Message msgError = mHandler.obtainMessage(LOGIN_ERROR);
						mHandler.sendMessage(msgError);
					}
				}
			}.start();
		}
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

	private boolean isvalid(String username, String password) {
		// TODO Auto-generated method stub
		if (username == "" || password == "") {
			System.out.println("false");
			return false;
		} else {
			System.out.println("true");
			return true;
		}
	}
	@Override
	public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		// TODO Auto-generated method stub
		return (event.getKeyCode() == KeyEvent.KEYCODE_ENTER);
	}

}
