package com.fsti.fjdicClient.activity.more;

import java.util.LinkedHashMap;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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

import com.fsti.fjdicClient.R;
import com.fsti.fjdicClient.activity.BaseActivity;
import com.fsti.fjdicClient.activity.groupBuy.GroupBuySortListActivity;
import com.fsti.fjdicClient.activity.home.HomeMainActivity;
import com.fsti.fjdicClient.activity.mycenter.MycenterMainActivity;
import com.fsti.fjdicClient.activity.search.SearchMainActivity;
import com.fsti.fjdicClient.activity.shoppingcart.ShoppingcartMainListActivity;
import com.fsti.fjdicClient.bean.ExitApplication;
import com.fsti.fjdicClient.dao.BusinessDao;
import com.fsti.fjdicClient.util.GlobalVarUtil;
import com.fsti.fjdicClient.util.HttpUtil;
import com.fsti.fjdicClient.util.ViewUtil;
import com.fsti.fjdicClient.util.asyncUtil.CallEarliest;
import com.fsti.fjdicClient.util.asyncUtil.Callable;
import com.fsti.fjdicClient.util.asyncUtil.Callback;

/**
 * 使用反馈
 * @author 王久叶
 *
 */

public class FeedbackActivity extends BaseActivity implements OnClickListener,TextWatcher{
  
    private Button btnFeedbackBack;
    private Button btnFeedbackSubmit;
    private EditText etFeedbackSay;
    private TextView tvFeedbackRest;
    
    private final int MAX_LENGTH = 140;//设置可输入最大字数
    private int Rest_Length = MAX_LENGTH;
    
    private CharSequence words;//输入的文字
    private int editStart ;//输入光标开始位置
    private int editEnd ;//输入光标结束位置

    private LinearLayout tohome;
	private LinearLayout tosearch;
	private LinearLayout toshoppingcart;
	private LinearLayout tomycenter;
	private LinearLayout tomore;
	private RelativeLayout bottommenu;
    
	public void onCreate(Bundle saveInstanceState){
		super.onCreate(saveInstanceState);
		setContentView(R.layout.layout_activity_more_feedback);
		ExitApplication.getInstance().addActivity(this);
		init();
	}
	
	@Override
	public void bindEvent() {
		// TODO Auto-generated method stub
		btnFeedbackBack.setOnClickListener(this);
		btnFeedbackSubmit.setOnClickListener(this);
		etFeedbackSay.addTextChangedListener(this);
		tohome.setOnClickListener(this);
		tosearch.setOnClickListener(this);
		toshoppingcart.setOnClickListener(this);
		tomycenter.setOnClickListener(this);
		tomore.setOnClickListener(this);
	}

	@Override
	public void initValue() {
		// TODO Auto-generated method stub
		btnFeedbackBack = (Button)findViewById(R.id.btn_feedback_back);
		btnFeedbackSubmit=(Button)findViewById(R.id.btn_feedback_submit);
		tvFeedbackRest=(TextView) findViewById(R.id.tv_feedback_rest);
	    etFeedbackSay=(EditText) findViewById(R.id.et_feedback_say);
	    LayoutInflater	mInflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    View view2  = mInflater.inflate(R.layout.layout_item_bottom_menu,null);
	    tohome=(LinearLayout)view2.findViewById(R.id.ll_tohome);
	    tosearch=(LinearLayout) view2.findViewById(R.id.ll_tosearch);
	    toshoppingcart=(LinearLayout) view2.findViewById(R.id.ll_toshoppingcart);
	    tomycenter=(LinearLayout) view2.findViewById(R.id.ll_tomycenter);
	    tomore=(LinearLayout) view2.findViewById(R.id.ll_tomore);
	    bottommenu=(RelativeLayout) findViewById(R.id.rl_more_feedback_bottommenu);
	    bottommenu.addView(view2);
	    ViewGroup.LayoutParams lp =  view2.getLayoutParams();
	    lp.width = lp.FILL_PARENT;
	    view2.setLayoutParams(lp);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()){
		case R.id.btn_feedback_back:
	         this.finish();
			 break;
		case R.id.btn_feedback_submit:
			if(etFeedbackSay.getText().toString().equals("")){
				Toast.makeText(FeedbackActivity.this, "内容不能为空", Toast.LENGTH_SHORT).show();
			}else{
				postFeedback();
			}
			break;
		case R.id.ll_tohome :
			Intent intenthome =new Intent(FeedbackActivity.this,HomeMainActivity.class);
			startActivity(intenthome);
			ExitApplication.getInstance().exit();
			break;
		case R.id.ll_tosearch :
			Intent intentsearch =new Intent(FeedbackActivity.this,SearchMainActivity.class);
			startActivity(intentsearch);
			ExitApplication.getInstance().exit();
			break;
		case R.id.ll_toshoppingcart :
			Intent intentshoppingcart =new Intent(FeedbackActivity.this,ShoppingcartMainListActivity.class);
			startActivity(intentshoppingcart);
//			ExitApplication.getInstance().exit();//购物车可以返回
			break;
		case R.id.ll_tomycenter :
			Intent intentmycenter =new Intent(FeedbackActivity.this,MycenterMainActivity.class);
			startActivity(intentmycenter);
			ExitApplication.getInstance().exit();
			break;
		case R.id.ll_tomore :
			Intent intentmore =new Intent(FeedbackActivity.this,MoreActivity.class);
			startActivity(intentmore);
			ExitApplication.getInstance().exit();
			break;
		}
	}

	private void postFeedback() {
		// TODO Auto-generated method stub
		this.doAsync(new CallEarliest<Object>() {
			
			@Override
			public void onCallEarliest() throws Exception {
				// TODO Auto-generated method stub
				
			}
		}, new Callable<Object>() {
			
			@Override
			public Object call() {
				// TODO Auto-generated method stub
				String reqUrl =  getString(R.string.feedback_http);
				if(GlobalVarUtil.account.getIsLogin()!=1){
					String UID = GlobalVarUtil.account.getUID();
				}else{
					String UID = "0";//游客默认UID
				}
				String content = etFeedbackSay.getText().toString();
				Map<String, Object> parameters = new LinkedHashMap<String, Object>();
//				parameters.put("UID", UID);
//				parameters.put("content", content);
				String jsonStr = "";
				try {
					jsonStr = HttpUtil.postData(reqUrl, parameters,
							GlobalVarUtil.ENCODING);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					Log.e("FeedbackActivity",e.toString());
					e.printStackTrace();
					return "-1";
				}
System.out.println(jsonStr);
				return jsonStr;
			}
		}, new Callback<String>() {
			
			@Override
			public void onCallback(String jsonStr) {
				// TODO Auto-generated method stub
				if(jsonStr.equals("-1")){
					Toast.makeText(FeedbackActivity.this, "提交失败", Toast.LENGTH_SHORT).show();
				}else{
					Toast.makeText(FeedbackActivity.this, "提交成功", Toast.LENGTH_SHORT).show();
					tvFeedbackRest.setText("");
				}
			}
		});
	}

	@Override
	public void afterTextChanged(Editable s) {
		// TODO Auto-generated method stub
		editStart = etFeedbackSay.getSelectionStart();
		editEnd = etFeedbackSay.getSelectionEnd();
		if(words.length()>140){
			Toast.makeText(FeedbackActivity.this, "字数不能超过140个", Toast.LENGTH_SHORT).show();
			s.delete(editStart-1, editEnd);
			int temp = editEnd;
			etFeedbackSay.setText(s);
			etFeedbackSay.setSelection(temp);
		}
		tvFeedbackRest.setText(Rest_Length+"");
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub
		tvFeedbackRest.setText(Rest_Length+"");
		words=s;
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub
		Rest_Length = MAX_LENGTH - etFeedbackSay.getText().length();
		if(Rest_Length>=0)
			tvFeedbackRest.setText(Rest_Length+"");
	}
}
