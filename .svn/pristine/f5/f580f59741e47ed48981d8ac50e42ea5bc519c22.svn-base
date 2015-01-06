package com.fsti.fjdicClient.activity.more;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.fsti.fjdicClient.R;
import com.fsti.fjdicClient.activity.BaseActivity;
import com.fsti.fjdicClient.activity.groupBuy.GroupBuySortListActivity;
import com.fsti.fjdicClient.activity.home.HomeMainActivity;
import com.fsti.fjdicClient.activity.login.LoginActivity;
import com.fsti.fjdicClient.activity.mycenter.MycenterMainActivity;
import com.fsti.fjdicClient.activity.search.SearchMainActivity;
import com.fsti.fjdicClient.activity.shoppingcart.ShoppingcartMainListActivity;
import com.fsti.fjdicClient.bean.ExitApplication;
import com.fsti.fjdicClient.util.GlobalVarUtil;

/**
 * 更多
 * @author 王久叶
 *
 */

public class MoreActivity extends BaseActivity implements OnClickListener{
  
    private RelativeLayout rlMoreFeedback;	 
    private RelativeLayout rlMoreAboutus;
    private LinearLayout tohome;
	private LinearLayout tosearch;
	private LinearLayout toshoppingcart;
	private LinearLayout tomycenter;
	private LinearLayout tomore;
	private RelativeLayout bottommenu;
    public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {

			Builder builder = new AlertDialog.Builder(MoreActivity.this);
			builder.setTitle("是否退出");
			builder.setMessage("按确定键退出！");
			builder.setPositiveButton("确定",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface arg0, int arg1) {
							// TODO Auto-generated method stub
							GlobalVarUtil.firstUpdateApp="update";
							ExitApplication.getInstance().exit();
							android.os.Process.killProcess(android.os.Process.myPid());
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
	public void onCreate(Bundle saveInstanceState){
		super.onCreate(saveInstanceState);
		setContentView(R.layout.layout_activity_more_main);
		ExitApplication.getInstance().addActivity(this);
		init();
	}
	
	@Override
	public void bindEvent() {
		// TODO Auto-generated method stub
		rlMoreFeedback.setOnClickListener(this);
		rlMoreAboutus.setOnClickListener(this);
		tohome.setOnClickListener(this);
		tosearch.setOnClickListener(this);
		toshoppingcart.setOnClickListener(this);
		tomycenter.setOnClickListener(this);
		tomore.setOnClickListener(this);
	}

	@Override
	public void initValue() {
		// TODO Auto-generated method stub
		rlMoreFeedback = (RelativeLayout)findViewById(R.id.rl_more_feedback);
		rlMoreAboutus = (RelativeLayout)findViewById(R.id.rl_more_aboutus);
		LayoutInflater	mInflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    View view2  = mInflater.inflate(R.layout.layout_item_bottom_menu,null);
	    tohome=(LinearLayout)view2.findViewById(R.id.ll_tohome);
	    tosearch=(LinearLayout) view2.findViewById(R.id.ll_tosearch);
	    toshoppingcart=(LinearLayout) view2.findViewById(R.id.ll_toshoppingcart);
	    tomycenter=(LinearLayout) view2.findViewById(R.id.ll_tomycenter);
	    tomore=(LinearLayout) view2.findViewById(R.id.ll_tomore);
	    bottommenu=(RelativeLayout) findViewById(R.id.rl_more_moreactivity_bottommenu);
	    bottommenu.addView(view2);
	    ViewGroup.LayoutParams lp =  view2.getLayoutParams();
	    lp.width = lp.FILL_PARENT;
	    view2.setLayoutParams(lp);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent;
		switch(v.getId()){
		case R.id.rl_more_feedback:
			 intent = new Intent(MoreActivity.this,FeedbackActivity.class);
			 startActivity(intent);
			 break;
		case R.id.rl_more_aboutus:
			 intent = new Intent(MoreActivity.this,AboutUsActivity.class);
   	     	 startActivity(intent);
 	         break;	 
		case R.id.ll_tohome :
			Intent intenthome =new Intent(MoreActivity.this,HomeMainActivity.class);
			startActivity(intenthome);
			ExitApplication.getInstance().exit();
			break;
		case R.id.ll_tosearch :
			Intent intentsearch =new Intent(MoreActivity.this,SearchMainActivity.class);
			startActivity(intentsearch);
			ExitApplication.getInstance().exit();
			break;
		case R.id.ll_toshoppingcart :
			Intent intentshoppingcart =new Intent(MoreActivity.this,ShoppingcartMainListActivity.class);
			startActivity(intentshoppingcart);
			if(GlobalVarUtil.account.getUID()==null){
				Intent intentTolongin = new Intent(MoreActivity.this,LoginActivity.class);
				startActivity(intentTolongin);
			}
//			ExitApplication.getInstance().exit();//购物车可以返回
			break;
		case R.id.ll_tomycenter :
			Intent intentmycenter =new Intent(MoreActivity.this,MycenterMainActivity.class);
			startActivity(intentmycenter);
			if(GlobalVarUtil.account.getUID()==null){
				Intent intentTolongin = new Intent(MoreActivity.this,LoginActivity.class);
				startActivity(intentTolongin);
			}
			ExitApplication.getInstance().exit();
			break;
		case R.id.ll_tomore :
//			Intent intentmore =new Intent(MoreActivity.this,MoreActivity.class);
//			startActivity(intentmore);
//			ExitApplication.getInstance().exit();
			break;
		}
	}
}
