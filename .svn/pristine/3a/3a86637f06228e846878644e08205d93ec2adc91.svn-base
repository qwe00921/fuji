package com.fsti.fjdicClient.activity.mycenter;



import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import com.fsti.fjdicClient.R;
import com.fsti.fjdicClient.activity.BaseActivity;
import com.fsti.fjdicClient.activity.groupBuy.GroupBuySortListActivity;
import com.fsti.fjdicClient.activity.home.HomeMainActivity;
import com.fsti.fjdicClient.activity.login.LoginActivity;
import com.fsti.fjdicClient.activity.login.RegisterActivity;
import com.fsti.fjdicClient.activity.more.MoreActivity;
import com.fsti.fjdicClient.activity.search.SearchMainActivity;
import com.fsti.fjdicClient.activity.shoppingcart.ShoppingcartMainListActivity;
import com.fsti.fjdicClient.bean.CommentEntity;
import com.fsti.fjdicClient.bean.ExitApplication;
import com.fsti.fjdicClient.dao.BusinessDao;
import com.fsti.fjdicClient.util.GlobalVarUtil;
import com.fsti.fjdicClient.util.HttpUtil;
import com.fsti.fjdicClient.util.asyncUtil.CallEarliest;
import com.fsti.fjdicClient.util.asyncUtil.Callable;
import com.fsti.fjdicClient.util.asyncUtil.Callback;
import com.google.gson.Gson;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Toast;

/**
 * 个人中心 评价商品详情
 * @author 
 *
 */
public class GoodsCommentsDetailActivity extends BaseActivity implements OnClickListener{
	
	private Button btnSubmit;
	private Button btnback;
	private int first_commodityPoint=0;//1表示第一个星亮着，0表示第一个星不亮
	private int first_servePoint=0;//1表示第一个星亮着，0表示第一个星不亮
	private int first_sellerPoint=0;//1表示第一个星亮着，0表示第一个星不亮
	private int first_logisticsPonit=0;//1表示第一个星亮着，0表示第一个星不亮
	
	private int commodityPoint_num=0; //表示星的数量
	private int servePoint_num=0;
	private int sellerPoint_num=0;
	private int logisticsPonit_num=0;
	
	private String ID="0";//商品ID
	private String type="1";//类型:1表示商城，2表示生活服务
	private String committer="";//评论者
	
	private ImageView  commodityPoint1;
	private ImageView  commodityPoint2;
	private ImageView  commodityPoint3;
	private ImageView  commodityPoint4;
	private ImageView  commodityPoint5;
	private ImageView  servePoint1;
	private ImageView  servePoint2;
	private ImageView  servePoint3;
	private ImageView  servePoint4;
	private ImageView  servePoint5;
	private ImageView  sellerPoint1;
	private ImageView  sellerPoint2;
	private ImageView  sellerPoint3;
	private ImageView  sellerPoint4;
	private ImageView  sellerPoint5;
	private ImageView  logisticsPonit1;
	private ImageView  logisticsPonit2;
	private ImageView  logisticsPonit3;
	private ImageView  logisticsPonit4;
	private ImageView  logisticsPonit5;
	
	
	private ImageView ivAnonymity;
	private LinearLayout tohome;
	private LinearLayout tosearch;
	private LinearLayout toshoppingcart;
	private LinearLayout tomycenter;
	private LinearLayout tomore;
	private RelativeLayout bottommenu;
	private int is_click=1;//用来判断是否匿名，1为匿名
	
	private EditText etComment;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_activity_mycenter_goodscommentsdetail);
		ExitApplication.getInstance().addActivity(this);
		init();
	}


	@Override
	public void bindEvent() {
		// TODO Auto-generated method stub
		commodityPoint1.setOnClickListener(this);
		commodityPoint2.setOnClickListener(this);
		commodityPoint3.setOnClickListener(this);
		commodityPoint4.setOnClickListener(this);
		commodityPoint5.setOnClickListener(this);
		servePoint1.setOnClickListener(this);
		servePoint2.setOnClickListener(this);
		servePoint3.setOnClickListener(this);
		servePoint4.setOnClickListener(this);
		servePoint5.setOnClickListener(this);
		sellerPoint1.setOnClickListener(this);
		sellerPoint2.setOnClickListener(this);
		sellerPoint3.setOnClickListener(this);
		sellerPoint4.setOnClickListener(this);
		sellerPoint5.setOnClickListener(this);
		logisticsPonit1.setOnClickListener(this);
		logisticsPonit2.setOnClickListener(this);
		logisticsPonit3.setOnClickListener(this);
		logisticsPonit4.setOnClickListener(this);
		logisticsPonit5.setOnClickListener(this);
		
		
		
		
		
		ivAnonymity.setOnClickListener(this);
		
		btnback.setOnClickListener(this);
		btnSubmit.setOnClickListener(this);
		etComment.setOnClickListener(this);
		tohome.setOnClickListener(this);
		tosearch.setOnClickListener(this);
		toshoppingcart.setOnClickListener(this);
		tomycenter.setOnClickListener(this);
		tomore.setOnClickListener(this);
	}

	@Override
	public void initValue() {
		// TODO Auto-generated method stub
System.out.println("comment initvalue go");

commodityPoint1 =(ImageView) findViewById(R.id.iv_mycenter_goodscommentsdetail_commodityPoint1);
commodityPoint2 =(ImageView) findViewById(R.id.iv_mycenter_goodscommentsdetail_commodityPoint2);
commodityPoint3 =(ImageView) findViewById(R.id.iv_mycenter_goodscommentsdetail_commodityPoint3);
commodityPoint4 =(ImageView) findViewById(R.id.iv_mycenter_goodscommentsdetail_commodityPoint4);
commodityPoint5 =(ImageView) findViewById(R.id.iv_mycenter_goodscommentsdetail_commodityPoint5);
servePoint1 =(ImageView) findViewById(R.id.iv_mycenter_goodscommentsdetail_servePoint1);
servePoint2 =(ImageView) findViewById(R.id.iv_mycenter_goodscommentsdetail_servePoint2);
servePoint3 =(ImageView) findViewById(R.id.iv_mycenter_goodscommentsdetail_servePoint3);
servePoint4 =(ImageView) findViewById(R.id.iv_mycenter_goodscommentsdetail_servePoint4);
servePoint5 =(ImageView) findViewById(R.id.iv_mycenter_goodscommentsdetail_servePoint5);
sellerPoint1 =(ImageView) findViewById(R.id.iv_mycenter_goodscommentsdetail_sellerPoint1);
sellerPoint2 =(ImageView) findViewById(R.id.iv_mycenter_goodscommentsdetail_sellerPoint2);
sellerPoint3 =(ImageView) findViewById(R.id.iv_mycenter_goodscommentsdetail_sellerPoint3);
sellerPoint4 =(ImageView) findViewById(R.id.iv_mycenter_goodscommentsdetail_sellerPoint4);
sellerPoint5 =(ImageView) findViewById(R.id.iv_mycenter_goodscommentsdetail_sellerPoint5);
logisticsPonit1 =(ImageView) findViewById(R.id.iv_mycenter_goodscommentsdetail_logisticsPonit1);
logisticsPonit2 =(ImageView) findViewById(R.id.iv_mycenter_goodscommentsdetail_logisticsPonit2);
logisticsPonit3 =(ImageView) findViewById(R.id.iv_mycenter_goodscommentsdetail_logisticsPonit3);
logisticsPonit4 =(ImageView) findViewById(R.id.iv_mycenter_goodscommentsdetail_logisticsPonit4);
logisticsPonit5 =(ImageView) findViewById(R.id.iv_mycenter_goodscommentsdetail_logisticsPonit5);

		ivAnonymity = (ImageView) findViewById(R.id.iv_mycenter_goodscommentsdetail_anonymity);
		
		btnback = (Button) findViewById(R.id.btn_mycenter_goodscommentsdetail_back);
		btnSubmit = (Button) findViewById(R.id.btn_mycenter_goodscommentsdetail_submit);
		
		etComment = (EditText) findViewById(R.id.et_mycenter_goodscommentsdetail_comments);
		LayoutInflater	mInflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    View view2  = mInflater.inflate(R.layout.layout_item_bottom_menu,null);
	    tohome=(LinearLayout)view2.findViewById(R.id.ll_tohome);
	    tosearch=(LinearLayout) view2.findViewById(R.id.ll_tosearch);
	    toshoppingcart=(LinearLayout) view2.findViewById(R.id.ll_toshoppingcart);
	    tomycenter=(LinearLayout) view2.findViewById(R.id.ll_tomycenter);
	    tomore=(LinearLayout) view2.findViewById(R.id.ll_tomore);
	    bottommenu=(RelativeLayout) findViewById(R.id.rl_mycenter_goodscommentsdetail_bottommenu);
	    bottommenu.addView(view2);
	    ViewGroup.LayoutParams lp =  view2.getLayoutParams();
	    lp.width = lp.FILL_PARENT;
	    view2.setLayoutParams(lp);
	  ID =this.getIntent().getStringExtra("ID");  
	  type = this.getIntent().getStringExtra("type");  
	    
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.et_mycenter_goodscommentsdetail_comments:
			
			break;
		case R.id.iv_mycenter_goodscommentsdetail_anonymity:
			if(is_click==0){
				is_click=1;
				ivAnonymity.setImageResource(R.drawable.btn_lable_off);
				committer=GlobalVarUtil.account.getNickName();
			}
			else{
				is_click=0;
				ivAnonymity.setImageResource(R.drawable.btn_lable_on);
				committer="";
			}
			
			break;
		case R.id.btn_mycenter_goodscommentsdetail_back:
			this.finish();
			break;
		case R.id.btn_mycenter_goodscommentsdetail_submit:
			postCommentsInfo();
			break;
		case R.id.ll_tohome :
			Intent intenthome =new Intent(GoodsCommentsDetailActivity.this,HomeMainActivity.class);
			startActivity(intenthome);
			ExitApplication.getInstance().exit();
			break;
		case R.id.ll_tosearch :
			Intent intentsearch =new Intent(GoodsCommentsDetailActivity.this,SearchMainActivity.class);
			startActivity(intentsearch);
			ExitApplication.getInstance().exit();
			break;
		case R.id.ll_toshoppingcart :
			Intent intentshoppingcart =new Intent(GoodsCommentsDetailActivity.this,ShoppingcartMainListActivity.class);
			startActivity(intentshoppingcart);
			if(GlobalVarUtil.account.getUID()==null){
				Intent intentTolongin = new Intent(GoodsCommentsDetailActivity.this,LoginActivity.class);
				startActivity(intentTolongin);
			}
//			ExitApplication.getInstance().exit();//购物车可以回退
			break;
		case R.id.ll_tomycenter :
			Intent intentmycenter =new Intent(GoodsCommentsDetailActivity.this,MycenterMainActivity.class);
			startActivity(intentmycenter);
			if(GlobalVarUtil.account.getUID()==null){
				Intent intentTolongin = new Intent(GoodsCommentsDetailActivity.this,LoginActivity.class);
				startActivity(intentTolongin);
			}
			ExitApplication.getInstance().exit();
			break;
		case R.id.ll_tomore :
			Intent intentmore =new Intent(GoodsCommentsDetailActivity.this,MoreActivity.class);
			startActivity(intentmore);
			ExitApplication.getInstance().exit();
			break;
		case R.id.iv_mycenter_goodscommentsdetail_commodityPoint5:
			commodityPoint_num=5;
			commodityPoint5.setImageResource(R.drawable.ic_comments_stars);
		case R.id.iv_mycenter_goodscommentsdetail_commodityPoint4:
			if(v.getId()==R.id.iv_mycenter_goodscommentsdetail_commodityPoint4){
				commodityPoint_num=4;
				commodityPoint5.setImageResource(R.drawable.ic_comments_nostars);}
			commodityPoint4.setImageResource(R.drawable.ic_comments_stars);
		case R.id.iv_mycenter_goodscommentsdetail_commodityPoint3:
			if(v.getId()==R.id.iv_mycenter_goodscommentsdetail_commodityPoint3){
				commodityPoint_num=3;
				commodityPoint5.setImageResource(R.drawable.ic_comments_nostars);
				commodityPoint4.setImageResource(R.drawable.ic_comments_nostars);}
			commodityPoint3.setImageResource(R.drawable.ic_comments_stars);
		case R.id.iv_mycenter_goodscommentsdetail_commodityPoint2:
			if(v.getId()==R.id.iv_mycenter_goodscommentsdetail_commodityPoint2){
				commodityPoint_num=2;
				commodityPoint5.setImageResource(R.drawable.ic_comments_nostars);
				commodityPoint4.setImageResource(R.drawable.ic_comments_nostars);
				commodityPoint3.setImageResource(R.drawable.ic_comments_nostars);}
			commodityPoint2.setImageResource(R.drawable.ic_comments_stars);
		case R.id.iv_mycenter_goodscommentsdetail_commodityPoint1:
			if(v.getId()==R.id.iv_mycenter_goodscommentsdetail_commodityPoint1){
				commodityPoint5.setImageResource(R.drawable.ic_comments_nostars);
				commodityPoint4.setImageResource(R.drawable.ic_comments_nostars);
				commodityPoint3.setImageResource(R.drawable.ic_comments_nostars);
				commodityPoint2.setImageResource(R.drawable.ic_comments_nostars);
				if(first_commodityPoint==0){
					first_commodityPoint=1;
					commodityPoint_num=1;
					commodityPoint1.setImageResource(R.drawable.ic_comments_stars);
				}
				else{
					first_commodityPoint=0;
					commodityPoint_num=0;
					commodityPoint1.setImageResource(R.drawable.ic_comments_nostars);
				}
			}
			else{
				first_commodityPoint=0;//假信号，留住第一个星
			commodityPoint1.setImageResource(R.drawable.ic_comments_stars);
			}
			break;
		case R.id.iv_mycenter_goodscommentsdetail_servePoint5:
			servePoint_num=5;
			servePoint5.setImageResource(R.drawable.ic_comments_stars);
		case R.id.iv_mycenter_goodscommentsdetail_servePoint4:
			if(v.getId()==R.id.iv_mycenter_goodscommentsdetail_servePoint4){
				servePoint_num=4;
				servePoint5.setImageResource(R.drawable.ic_comments_nostars);
			}
			servePoint4.setImageResource(R.drawable.ic_comments_stars);
		case R.id.iv_mycenter_goodscommentsdetail_servePoint3:
			if(v.getId()==R.id.iv_mycenter_goodscommentsdetail_servePoint3){
				servePoint_num=3;
				servePoint5.setImageResource(R.drawable.ic_comments_nostars);
				servePoint4.setImageResource(R.drawable.ic_comments_nostars);
			}
			servePoint3.setImageResource(R.drawable.ic_comments_stars);
		case R.id.iv_mycenter_goodscommentsdetail_servePoint2:
			if(v.getId()==R.id.iv_mycenter_goodscommentsdetail_servePoint2){
				servePoint_num=2;
				servePoint5.setImageResource(R.drawable.ic_comments_nostars);
				servePoint4.setImageResource(R.drawable.ic_comments_nostars);
				servePoint3.setImageResource(R.drawable.ic_comments_nostars);
			}
			servePoint2.setImageResource(R.drawable.ic_comments_stars);
		case R.id.iv_mycenter_goodscommentsdetail_servePoint1:
			if(v.getId()==R.id.iv_mycenter_goodscommentsdetail_servePoint1){
				servePoint5.setImageResource(R.drawable.ic_comments_nostars);
				servePoint4.setImageResource(R.drawable.ic_comments_nostars);
				servePoint3.setImageResource(R.drawable.ic_comments_nostars);
				servePoint2.setImageResource(R.drawable.ic_comments_nostars);
				if(first_servePoint==0){
					first_servePoint=1;
					servePoint_num=1;
					servePoint1.setImageResource(R.drawable.ic_comments_stars);
				}
				else{
					first_servePoint=0;
					servePoint_num=0;
					servePoint1.setImageResource(R.drawable.ic_comments_nostars);
				}
			}
			else{
				first_servePoint=0;
			servePoint1.setImageResource(R.drawable.ic_comments_stars);
			}
			break;
		case R.id.iv_mycenter_goodscommentsdetail_sellerPoint5:
			sellerPoint_num=5;
			sellerPoint5.setImageResource(R.drawable.ic_comments_stars);
		case R.id.iv_mycenter_goodscommentsdetail_sellerPoint4:
			if(v.getId()==R.id.iv_mycenter_goodscommentsdetail_sellerPoint4){
				sellerPoint_num=4;
				sellerPoint5.setImageResource(R.drawable.ic_comments_nostars);	
			}
			sellerPoint4.setImageResource(R.drawable.ic_comments_stars);
		case R.id.iv_mycenter_goodscommentsdetail_sellerPoint3:
			if(v.getId()==R.id.iv_mycenter_goodscommentsdetail_sellerPoint3){
				sellerPoint_num=3;
				sellerPoint5.setImageResource(R.drawable.ic_comments_nostars);	
				sellerPoint4.setImageResource(R.drawable.ic_comments_nostars);	
			}
			sellerPoint3.setImageResource(R.drawable.ic_comments_stars);
		case R.id.iv_mycenter_goodscommentsdetail_sellerPoint2:
			if(v.getId()==R.id.iv_mycenter_goodscommentsdetail_sellerPoint2){
				sellerPoint_num=2;
				sellerPoint5.setImageResource(R.drawable.ic_comments_nostars);	
				sellerPoint4.setImageResource(R.drawable.ic_comments_nostars);	
				sellerPoint3.setImageResource(R.drawable.ic_comments_nostars);	
			}
			sellerPoint2.setImageResource(R.drawable.ic_comments_stars);
		case R.id.iv_mycenter_goodscommentsdetail_sellerPoint1:
			if(v.getId()==R.id.iv_mycenter_goodscommentsdetail_sellerPoint1){
				sellerPoint5.setImageResource(R.drawable.ic_comments_nostars);	
				sellerPoint4.setImageResource(R.drawable.ic_comments_nostars);	
				sellerPoint3.setImageResource(R.drawable.ic_comments_nostars);	
				sellerPoint2.setImageResource(R.drawable.ic_comments_nostars);	
				if(first_sellerPoint==0){
					first_sellerPoint=1;
					sellerPoint_num=1;
					sellerPoint1.setImageResource(R.drawable.ic_comments_stars);
				}
				else{
					first_sellerPoint=0;
					sellerPoint_num=0;
					sellerPoint1.setImageResource(R.drawable.ic_comments_nostars);
				}
			}
			else{
				first_sellerPoint=0;
			sellerPoint1.setImageResource(R.drawable.ic_comments_stars);
			}
			break;
		case R.id.iv_mycenter_goodscommentsdetail_logisticsPonit5:
			logisticsPonit_num=5;
			logisticsPonit5.setImageResource(R.drawable.ic_comments_stars);
		case R.id.iv_mycenter_goodscommentsdetail_logisticsPonit4:
			if(v.getId()==R.id.iv_mycenter_goodscommentsdetail_logisticsPonit4){
				logisticsPonit_num=4;
				logisticsPonit5.setImageResource(R.drawable.ic_comments_nostars);	
			}
			logisticsPonit4.setImageResource(R.drawable.ic_comments_stars);
		case R.id.iv_mycenter_goodscommentsdetail_logisticsPonit3:
			if(v.getId()==R.id.iv_mycenter_goodscommentsdetail_logisticsPonit3){
				logisticsPonit_num=3;
				logisticsPonit5.setImageResource(R.drawable.ic_comments_nostars);	
				logisticsPonit4.setImageResource(R.drawable.ic_comments_nostars);	
			}
			logisticsPonit3.setImageResource(R.drawable.ic_comments_stars);
		case R.id.iv_mycenter_goodscommentsdetail_logisticsPonit2:
			if(v.getId()==R.id.iv_mycenter_goodscommentsdetail_logisticsPonit2){
				logisticsPonit_num=2;
				logisticsPonit5.setImageResource(R.drawable.ic_comments_nostars);	
				logisticsPonit4.setImageResource(R.drawable.ic_comments_nostars);	
				logisticsPonit3.setImageResource(R.drawable.ic_comments_nostars);	
			}
			logisticsPonit2.setImageResource(R.drawable.ic_comments_stars);
		case R.id.iv_mycenter_goodscommentsdetail_logisticsPonit1:
			if(v.getId()==R.id.iv_mycenter_goodscommentsdetail_logisticsPonit1){
				logisticsPonit5.setImageResource(R.drawable.ic_comments_nostars);	
				logisticsPonit4.setImageResource(R.drawable.ic_comments_nostars);	
				logisticsPonit3.setImageResource(R.drawable.ic_comments_nostars);	
				logisticsPonit2.setImageResource(R.drawable.ic_comments_nostars);	
				if(first_logisticsPonit==0){
					first_logisticsPonit=1;
					logisticsPonit_num=1;
					logisticsPonit1.setImageResource(R.drawable.ic_comments_stars);
				}
				else{
					first_logisticsPonit=0;
					logisticsPonit_num=0;
					logisticsPonit1.setImageResource(R.drawable.ic_comments_nostars);
				}
			}
			else{
				first_logisticsPonit=0;
			logisticsPonit1.setImageResource(R.drawable.ic_comments_stars);
			}
			break;
		
		}
	}


	private void postCommentsInfo() {
		// TODO Auto-generated method stub
		this.doAsync(new CallEarliest<Object>() {
			
			@Override
			public void onCallEarliest() throws Exception {
				// TODO Auto-generated method stub
				
			}
		}, new Callable<Object>() {
			
			@Override
			public Object call(){
				// TODO Auto-generated method stub
				String reqUrl =  getString(R.string.commitComment_http);
				CommentEntity commentEntity = new CommentEntity();
				String content = etComment.getText().toString();
				Gson gson = new Gson();  
				//评论时间
				Date now = new Date();
				SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String dateStr = sf.format(now);
System.out.println("comments time ------------>"+dateStr);

				commentEntity.setDate(dateStr);
				commentEntity.setGoodsID(ID);
				commentEntity.setContent(content);
				commentEntity.setGoodsType(Integer.parseInt(type));
				commentEntity.setCommodityPoint(commodityPoint_num);
				commentEntity.setServePoint(servePoint_num);
				commentEntity.setSellerPoint(sellerPoint_num);
				commentEntity.setLogisticsPonit(logisticsPonit_num);
				commentEntity.setCommitter(committer);

				//测试
//				commentEntity.setCommitter("王123");
//				commentEntity.setDate(dateStr);
//				commentEntity.setGoodsID("11");
//				commentEntity.setContent("890kdfjdlfkal");
//				commentEntity.setGoodsType(1);

				String commentJson = gson.toJson(commentEntity).toString();
				
System.out.println("comment detail----"+commentJson);
				Map<String, Object> parameters = new LinkedHashMap<String, Object>();
				parameters.put("CommentEntity", commentJson);
				String jsonStr = "";
				try {
					jsonStr = HttpUtil.postData(reqUrl, parameters,
							GlobalVarUtil.ENCODING);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return "-1";
				}
System.out.println("CommentsDetails"+jsonStr);
				return jsonStr;
			}

		}, new Callback<String>() {
			
			@Override
			public void onCallback(String jsonStr) {
				// TODO Auto-generated method stub
				jsonStr = RegisterActivity.replaceBlank(jsonStr);
						
				if(jsonStr.trim().equals("")||jsonStr==null){
					Toast.makeText(GoodsCommentsDetailActivity.this, "评论失败",1).show();
				}else if(jsonStr.equals("2")){
					Toast.makeText(GoodsCommentsDetailActivity.this, "评论成功",1).show();
				}else if(jsonStr.equals("1")){
					Toast.makeText(GoodsCommentsDetailActivity.this, "评论失败",1).show();
				}else{
					Toast.makeText(GoodsCommentsDetailActivity.this, jsonStr,1).show();
				}
			}
		});
	}
}