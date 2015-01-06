package com.fsti.fjdicClient.activity.mycenter;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.fsti.fjdicClient.R;
import com.fsti.fjdicClient.activity.BaseActivity;
import com.fsti.fjdicClient.activity.groupBuy.GroupBuySortListActivity;
import com.fsti.fjdicClient.activity.home.HomeMainActivity;
import com.fsti.fjdicClient.activity.login.LoginActivity;
import com.fsti.fjdicClient.activity.login.RegisterActivity;
import com.fsti.fjdicClient.activity.more.MoreActivity;
import com.fsti.fjdicClient.activity.search.SearchMainActivity;
import com.fsti.fjdicClient.activity.shoppingcart.ShoppingcartMainListActivity;
import com.fsti.fjdicClient.bean.ExitApplication;
import com.fsti.fjdicClient.util.GlobalVarUtil;
import com.fsti.fjdicClient.util.HttpUtil;
import com.fsti.fjdicClient.util.asyncUtil.CallEarliest;
import com.fsti.fjdicClient.util.asyncUtil.Callable;
import com.fsti.fjdicClient.util.asyncUtil.Callback;

/**
 * 个人中心 修改手机号码
 * 
 * @author
 * 
 */
public class ModifyPhoneActivity extends BaseActivity implements
		OnClickListener {
	private Button btmodifyphoneBack;
	private EditText etmodifyphoneNewphonenum;
	private EditText etmodifyphoneInputcode;
	private Button btmodifyphoneGetcode;
	private Button btmodifyphoneFinish;
	private String reqUrl;
	private String telephone;
	private CountDownTimer countdowntimer;
	private int key = 0;// 0表示获取验证码，1表示绑定手机号
	private SharedPreferences save_modifyinfo;
	private int last_seconds = 0;// 表示还剩多少秒之后才能获取短信验证码
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
		setContentView(R.layout.layout_activity_mycenter_modifyphone);
		ExitApplication.getInstance().addActivity(this);
		init();
	}

	@Override
	public void bindEvent() {
		// TODO Auto-generated method stub
		btmodifyphoneBack.setOnClickListener(this);
		btmodifyphoneGetcode.setOnClickListener(this);
		btmodifyphoneFinish.setOnClickListener(this);
		tohome.setOnClickListener(this);
		tosearch.setOnClickListener(this);
		toshoppingcart.setOnClickListener(this);
		tomycenter.setOnClickListener(this);
		tomore.setOnClickListener(this);
	}

	@Override
	public void initValue() {
		// TODO Auto-generated method stub
		btmodifyphoneBack = (Button) findViewById(R.id.btn_mycenter_modifyphone_back);
		etmodifyphoneNewphonenum = (EditText) findViewById(R.id.et_mycenter_modifyphone_inputphone);
		etmodifyphoneInputcode = (EditText) findViewById(R.id.et_mycenter_modifyphone_inputcode);
		btmodifyphoneGetcode = (Button) findViewById(R.id.et_mycenter_modifyphone_getcode);
		btmodifyphoneFinish = (Button) findViewById(R.id.btn_mycenter_modifyphone_finish);
		save_modifyinfo = getSharedPreferences("modifycode",
				ModifyPhoneActivity.this.MODE_WORLD_WRITEABLE);
		LayoutInflater	mInflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    View view2  = mInflater.inflate(R.layout.layout_item_bottom_menu,null);
	    tohome=(LinearLayout)view2.findViewById(R.id.ll_tohome);
	    tosearch=(LinearLayout) view2.findViewById(R.id.ll_tosearch);
	    toshoppingcart=(LinearLayout) view2.findViewById(R.id.ll_toshoppingcart);
	    tomycenter=(LinearLayout) view2.findViewById(R.id.ll_tomycenter);
	    tomore=(LinearLayout) view2.findViewById(R.id.ll_tomore);
	    bottommenu=(RelativeLayout) findViewById(R.id.rl_mycenter_modifyphone_bottommenu);
	    bottommenu.addView(view2);
	    ViewGroup.LayoutParams lp =  view2.getLayoutParams();
	    lp.width = lp.FILL_PARENT;
	    view2.setLayoutParams(lp);
	    
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.btn_mycenter_modifyphone_back:
			this.finish();
			break;
		case R.id.et_mycenter_modifyphone_getcode:
			if (last_seconds == 0) {

				telephone = etmodifyphoneNewphonenum.getText().toString();
				if (isPhoneNumberValid(telephone)) {
					last_seconds = 60;
					coundowntimer_start(last_seconds);
					reqUrl = getString(R.string.GetAuthCode_php);
					key = 0;
					link();
				} else {
					Toast.makeText(ModifyPhoneActivity.this, "手机号格式错误，请确认输入无误",
							1).show();
				}
			} else {
				Toast.makeText(ModifyPhoneActivity.this,
						"请再等待" + last_seconds + "秒", 1).show();
			}
			break;
		case R.id.btn_mycenter_modifyphone_finish:
			telephone = etmodifyphoneNewphonenum.getText().toString();
			if (telephone == null || telephone.equals("")) {
				Toast.makeText(ModifyPhoneActivity.this, "手机号不能为空", 1).show();
			} else {
				if (isPhoneNumberValid(telephone)) {
					String Authcode = etmodifyphoneInputcode.getText()
							.toString();
					if (Authcode == null || Authcode.equals("")) {
						Toast.makeText(ModifyPhoneActivity.this,
										"短信验证码不能为空", 1).show();
					} else {

						String phone = save_modifyinfo.getString("Authphone",
								null);
						String code = save_modifyinfo.getString("Authcode",
								null);
						if (phone == null || code == null) {
							Toast.makeText(ModifyPhoneActivity.this,
									"请先获取短信验证信息", 1).show();
						} else {

							if (phone.equals(telephone)) {
								
								if (code.equals(Authcode)) {

									reqUrl = getString(R.string.ModifyPhone_php);
									key = 1;
									link();

								} else {
									Toast.makeText(ModifyPhoneActivity.this,
											"短信验证码错误，请核对验证信息", 1).show();
								}
							} else {
								Toast.makeText(ModifyPhoneActivity.this,
										"要绑定的手机号与验证的手机号不一致，请核对手机号", 1).show();
							}
						}
					}
				} else {
					Toast.makeText(ModifyPhoneActivity.this, "手机号格式错误，请确认输入无误",
							1).show();
				}
			}
			break;
		case R.id.ll_tohome :
			Intent intenthome =new Intent(ModifyPhoneActivity.this,HomeMainActivity.class);
			startActivity(intenthome);
			ExitApplication.getInstance().exit();
			break;
		case R.id.ll_tosearch :
			Intent intentsearch =new Intent(ModifyPhoneActivity.this,SearchMainActivity.class);
			startActivity(intentsearch);
			ExitApplication.getInstance().exit();
			break;
		case R.id.ll_toshoppingcart :
			Intent intentshoppingcart =new Intent(ModifyPhoneActivity.this,ShoppingcartMainListActivity.class);
			startActivity(intentshoppingcart);
			if(GlobalVarUtil.account.getUID()==null){
				Intent intentTolongin = new Intent(ModifyPhoneActivity.this,LoginActivity.class);
				startActivity(intentTolongin);
			}
//			ExitApplication.getInstance().exit();//购物车可以回退
			break;
		case R.id.ll_tomycenter :
			Intent intentmycenter =new Intent(ModifyPhoneActivity.this,MycenterMainActivity.class);
			startActivity(intentmycenter);
			if(GlobalVarUtil.account.getUID()==null){
				Intent intentTolongin = new Intent(ModifyPhoneActivity.this,LoginActivity.class);
				startActivity(intentTolongin);
			}
			ExitApplication.getInstance().exit();
			break;
		case R.id.ll_tomore :
			Intent intentmore =new Intent(ModifyPhoneActivity.this,MoreActivity.class);
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

	public static boolean isPhoneNumberValid(String mobiles) {
		Pattern p = Pattern.compile("^1(3|5|8)([0-9]{9})$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}

	// 获取短信验证码&绑定手机号

	private void link() {
		this.doAsync(new CallEarliest<Object>() {
			@Override
			public void onCallEarliest() throws Exception {
				// TODO Auto-generated method stub

			}

		}, new Callable<Object>() {
			@Override
			public String call() {

				Map<String, Object> parameters = new LinkedHashMap<String, Object>();

				parameters.put("UID", GlobalVarUtil.account.getUID());
				parameters.put("telephone", telephone);

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
				jsonStr = RegisterActivity.replaceBlank(jsonStr);
						
				System.out.println("code jsonStr=" + jsonStr);
				System.out.println("+++++++++++++++"+jsonStr.trim()+"++++++++"+key);
				try {
					
				
				if (key == 0) {
					JSONObject json = new JSONObject(jsonStr);
					if (jsonStr.trim().equals("1")) {
						Toast.makeText(ModifyPhoneActivity.this,
								"获取短信验证码失败，请重试", 1).show();
					} else {

						Editor editor = save_modifyinfo.edit();
						editor.putString("Authcode", json.getString("msgCode"));
						editor.putString("Authphone", telephone);
						editor.commit();
						Toast
								.makeText(ModifyPhoneActivity.this,
										"获取短信验证码成功", 1).show();
						
					}

				}
				
				else {
					System.out.println("+++++ww++++++++++"+jsonStr.trim()+"++++++++"+key);
					if (jsonStr.trim().equals("1")) {
						Toast.makeText(ModifyPhoneActivity.this,
								"绑定手机号失败", 1).show();
					} else {
						Toast.makeText(ModifyPhoneActivity.this,
								"绑定手机号成功", 1).show();
						ModifyPhoneActivity.this.finish();
					}
				}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}

}
