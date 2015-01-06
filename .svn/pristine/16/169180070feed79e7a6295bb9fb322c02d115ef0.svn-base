package com.fsti.fjdicClient.activity.mycenter;


import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.fsti.fjdicClient.R;
import com.fsti.fjdicClient.ApplicationUtil;
import com.fsti.fjdicClient.activity.BaseActivity;
import com.fsti.fjdicClient.activity.groupBuy.GroupBuyDetailActivity;
import com.fsti.fjdicClient.activity.groupBuy.GroupBuyGoodsDetailActivity;
import com.fsti.fjdicClient.activity.groupBuy.GroupBuySortListActivity;
import com.fsti.fjdicClient.activity.home.HomeMainActivity;
import com.fsti.fjdicClient.activity.login.LoginActivity;
import com.fsti.fjdicClient.activity.more.MoreActivity;
import com.fsti.fjdicClient.activity.search.SearchMainActivity;
//import com.fsti.fjdicClient.activity.shoppingMall.ShoppingmallGoodsDetailActivity;
import com.fsti.fjdicClient.activity.shoppingcart.ShoppingcartMainListActivity;
import com.fsti.fjdicClient.adapter.MycenterCollectMainAdapter;
import com.fsti.fjdicClient.bean.CollectEntity;
import com.fsti.fjdicClient.bean.ExitApplication;
import com.fsti.fjdicClient.bean.GoodsEntity;
import com.fsti.fjdicClient.dao.BusinessDao;
import com.fsti.fjdicClient.util.GlobalVarUtil;
import com.fsti.fjdicClient.util.HttpUtil;
import com.fsti.fjdicClient.util.ViewUtil;
import com.fsti.fjdicClient.util.asyncUtil.CallEarliest;
import com.fsti.fjdicClient.util.asyncUtil.Callable;
import com.fsti.fjdicClient.util.asyncUtil.Callback;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 个人中心  收藏列表
 * @author 
 *
 */
public class CollectMainListActivity extends BaseActivity implements OnClickListener, OnItemClickListener  {
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		if(thread1!=null&&thread1.isAlive()){
			thread1.interrupt();
		}
		if(thread2!=null&&thread2.isAlive()){
			thread2.interrupt();
		}
		super.onPause();
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	private Thread thread1;
	private Thread thread2;
	private Button btnMycenterCollectMainBack;
	private Button btnMycenterCollectMainModify;
	private Button btnMycenterCollectModifyBack;
	private Button btnMycenterCollectModifymodfy;
	private Button btnMycenterCollectModifyremove;
	private Button btnMycenterCollectModifyselectall;
	private ListView listMycenterCollect;
	private TextView selleritem;
	private ImageView ivLoading;
	private List<GoodsEntity> goodsList;
	private RelativeLayout rlMycenterCollectMain;
	private RelativeLayout rlMycenterCollectModify;
	private LinearLayout llMycenterCollectModifyBottom;
	private int run_key=0;//用来区分将从网络获取什么数据，0表示获取收藏夹列表数据,1表示删除
	private MycenterCollectMainAdapter adapter;
	private int is_writting= 0; //是否处于编辑状态，0表示非编辑状态
	private int is_select[];//是否被选中
	private int select[];//备份is_sselect
	private int is_allselect=1;//是否已经全选，1表示已全选
	private String delete_collect_id;
	private static int is_wait_for_delete=0;//遍历收藏商品
	private View footer;
	private int Q=0;//逐条查询，是否有商家信息
	private int M=0;//商家id列表的下 标
	private int point_move=0;//定义删除的指针偏移
	private LinearLayout tohome;
	private LinearLayout tosearch;
	private LinearLayout toshoppingcart;
	private LinearLayout tomycenter;
	private LinearLayout tomore;
	private RelativeLayout bottommenu;
	
	private int title_num=0;//记录当前位置前面有几个标题
	
	private String sellerID[];
	public static String sellerName[];
	private List<CollectEntity> list ;
	private int typedelete;
	
	private boolean isOver = false;
	
	private int deletCount= 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_list_mycenter_collect_main);
		ExitApplication.getInstance().addActivity(this);
		ivLoading=(ImageView) findViewById(R.id.iv_collectmainlist_loading);
		ivLoading.setVisibility(View.VISIBLE);
		ViewUtil.addLoadingAnimation(ApplicationUtil.myContext,
				ivLoading);
		LayoutInflater	mInflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    View view2  = mInflater.inflate(R.layout.layout_item_bottom_menu,null);
	    tohome=(LinearLayout)view2.findViewById(R.id.ll_tohome);
	    tosearch=(LinearLayout) view2.findViewById(R.id.ll_tosearch);
	    toshoppingcart=(LinearLayout) view2.findViewById(R.id.ll_toshoppingcart);
	    tomycenter=(LinearLayout) view2.findViewById(R.id.ll_tomycenter);
	    tomore=(LinearLayout) view2.findViewById(R.id.ll_tomore);
	    bottommenu=(RelativeLayout) findViewById(R.id.rl_mycenter_collectmainlist_bottommenu);
	    bottommenu.addView(view2);
	    ViewGroup.LayoutParams lp =  view2.getLayoutParams();
	    lp.width = lp.FILL_PARENT;
	    view2.setLayoutParams(lp);
	    btnMycenterCollectMainModify = (Button) findViewById(R.id.btn_list_mycenter_collect_main_modify);
		postcollectlistInfo();
		
	}


 	@Override
	public void bindEvent() {
		// TODO Auto-generated method stub
		btnMycenterCollectMainBack.setOnClickListener(this);
		btnMycenterCollectMainModify.setOnClickListener(this);
		btnMycenterCollectModifyBack.setOnClickListener(this);
		btnMycenterCollectModifyremove.setOnClickListener(this);
		btnMycenterCollectModifyselectall.setOnClickListener(this);
		btnMycenterCollectModifymodfy.setOnClickListener(this);
		listMycenterCollect.setOnItemClickListener(this);
		tohome.setOnClickListener(this);
		tosearch.setOnClickListener(this);
		toshoppingcart.setOnClickListener(this);
		tomycenter.setOnClickListener(this);
		tomore.setOnClickListener(this);
	}

	@Override
	public void initValue() {
		// TODO Auto-generated method stub
		btnMycenterCollectMainBack = (Button) findViewById(R.id.btn_list_mycenter_collect_main_back);
		btnMycenterCollectMainModify = (Button) findViewById(R.id.btn_list_mycenter_collect_main_modify);
		btnMycenterCollectModifyBack = (Button) findViewById(R.id.btn_list_mycenter_collect_modify_back);
		btnMycenterCollectModifymodfy = (Button) findViewById(R.id.btn_list_mycenter_collect_modify_modify);
		btnMycenterCollectModifyselectall = (Button) findViewById(R.id.btn_list_mycenter_collect_modify_selectall);
		btnMycenterCollectModifyremove = (Button) findViewById(R.id.btn_list_mycenter_collect_modify_remove);
		btnMycenterCollectMainModify = (Button) findViewById(R.id.btn_list_mycenter_collect_main_modify);
		listMycenterCollect = (ListView) findViewById(R.id.list_mycenter_collect_main);
		listMycenterCollect.setDivider(null);
		rlMycenterCollectMain = (RelativeLayout) findViewById(R.id.rl_list_mycenter_collect_mian_title);
		rlMycenterCollectModify = (RelativeLayout) findViewById(R.id.rl_list_mycenter_collect_modify_title);
		llMycenterCollectModifyBottom = (LinearLayout) findViewById(R.id.ll_list_mycenter_collect_modify_bottom);
		footer = getLayoutInflater().inflate(R.layout.layout_activity_item_more, null);
		selleritem = (TextView) footer.findViewById(R.id.tv_goodslist_more);
		
	    
		update_goodslist();
		
	
	}
private void update_goodslist(){
	  M=0;
	  Q=0;
	 list = BusinessDao.getCollectionTableData();
	
	 System.out.println( "...........test==="+list.size());
	//goodsList = new ArrayList<GoodsEntity>();
	if(list.size()==0){
		 selleritem.setText("没有收藏的商品");
		
        goodsList=new ArrayList<GoodsEntity>();
        is_select=new int[0];
        if(listMycenterCollect.getCount()==goodsList.size()){
        	 listMycenterCollect .addFooterView(footer);	
		}
        ViewUtil.removeLoadingAnimation(ivLoading);
        adapter = new MycenterCollectMainAdapter(getBaseContext(),goodsList,is_select,is_writting);
		 adapter.setVisible(false);
	     listMycenterCollect.setAdapter(adapter);	
	     isOver = true;
	}
	else{

		sellerID=new String[list.size()];
		sellerName=new String[list.size()];
		add_to_list();
	}
}

private void add_to_list(){
	
	if(Q<list.size()){
		String data[] = BusinessDao.get_sellerinfo(list.get(Q).getID(),list.get(Q).getType());
		if(data[0]==null){
			if(run_key==0){
				insert_sellerinfo();
			}
			else{
				Q=Q+1;
				add_to_list();
			}
		}else{
			int j=0;
			if(M>0){
				for(String str:sellerID){
					if(str==null){break;}
					if(str.equals(data[0])){j=1;break;}
				}
			}
			if(j==0){
				sellerID[M]=data[0];
				sellerName[M]=data[1];
				M=M+1;
			}
			Q=Q+1;
			add_to_list();
		}
	}else{
		goodsList = new ArrayList<GoodsEntity>();
		is_select=new int[list.size()+M+1];
		int n=0;		
		for(int p=0;p<M;p++){
			selleritem.setText(sellerName[p]);
			is_select[n]=2;
			n=n+1;
			String str=sellerID[p];
			List<GoodsEntity> new_goodsEntity =BusinessDao.get_goodsinfo2(str);
			for(int j=0;j<new_goodsEntity.size();j++){
				GoodsEntity g =  new_goodsEntity.get(j);
			  for(int i=0;i<list.size();i++){
				String s=list.get(i).getID();	
				int type = list.get(i).getType();
				if(s.equals(g.getID())&&type==g.getType()){
					goodsList.add(g);
					if(is_writting==1){	is_select[n]=select[n];}
					else{
					is_select[n]=0;
					}
					n=n+1;
				}
			  }	
			}
			
		}
		if(list.size()>goodsList.size()){
			sellerName[M]= "其他商家";
			selleritem.setText(sellerName[M]);
			is_select[n]=2;
			n=n+1;
			
			for(int j=0;j<list.size();j++){
				
				GoodsEntity g =BusinessDao.get_goodsinfo(list.get(j).getID(),list.get(j).getType());
			int mar= 0;
			for(int i=0;i<goodsList.size();i++){
				String s=goodsList.get(i).getID();	
				int type = goodsList.get(i).getType();
				System.out.println(s+ "........====="+g.getID());
				if(s.equals(g.getID())&&type==g.getType()){
					mar=1;
					break;
				}
			}
			if(mar==0){
				goodsList.add(g);
				if(is_writting==1){	is_select[n]=select[n];}
				else{
					is_select[n]=0;
				}
				n=n+1;
			}	
			}
		}
		
		 if(is_writting==1){ 
			 adapter.setVisible(true);
			 adapter.updata(goodsList, is_select, 1);
			 }
		 else{
				ViewUtil.removeLoadingAnimation(ivLoading);
				btnMycenterCollectMainModify.setVisibility(View.VISIBLE);
		adapter = new MycenterCollectMainAdapter(getBaseContext(),goodsList,is_select,is_writting);
		 adapter.setVisible(false);
		  listMycenterCollect.setAdapter(adapter);	
		 }
	   
		
	}
	isOver = true;
}

	

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()){
		case R.id.btn_list_mycenter_collect_main_back:
			this.finish();
			break;
		case R.id.btn_list_mycenter_collect_main_modify:
//			Intent intentToModify = new Intent(CollectMainListActivity.this, CollectModifyListActivity.class);
//			startActivity(intentToModify);
			is_writting =1;
			rlMycenterCollectMain.setVisibility(View.GONE);
			rlMycenterCollectModify.setVisibility(View.VISIBLE);
			llMycenterCollectModifyBottom.setVisibility(View.VISIBLE);
			btnMycenterCollectModifyselectall.setText("全 选");
			
			
		// adapter = new MycenterCollectMainAdapter(getBaseContext(),goodsList,is_select,is_writting);
		 adapter.setVisible(true);
		//	listMycenterCollect.setAdapter(adapter);
		  adapter.updata(goodsList, is_select, is_writting);
			break;
		case R.id.btn_list_mycenter_collect_modify_back:
			
			is_writting=0;
			rlMycenterCollectMain.setVisibility(View.VISIBLE);
			rlMycenterCollectModify.setVisibility(View.GONE);
			llMycenterCollectModifyBottom.setVisibility(View.GONE);
			
			for(int q=0;q<is_select.length;q++){if(is_select[q]==1){
				is_select[q]=0;
			}}
			//adapter = new MycenterCollectMainAdapter(getBaseContext(),goodsList,is_select,is_writting);
			adapter.setVisible(false);
			//listMycenterCollect.setAdapter(adapter);
			  adapter.updata(goodsList, is_select, is_writting);
			break;
			
		case R.id.btn_list_mycenter_collect_modify_modify:
			if(isOver){
				is_writting=0;
				rlMycenterCollectMain.setVisibility(View.VISIBLE);
				rlMycenterCollectModify.setVisibility(View.GONE);
				llMycenterCollectModifyBottom.setVisibility(View.GONE);
				
				
				for(int q=0;q<is_select.length;q++){
					if(is_select[q]==1){is_select[q]=0;}
				}
				// adapter = new MycenterCollectMainAdapter(getBaseContext(),goodsList,is_select,is_writting);
				adapter.setVisible(false);
				//listMycenterCollect.setAdapter(adapter);
				adapter.updata(goodsList, is_select, is_writting);
			}
			break;
		case R.id.btn_list_mycenter_collect_modify_selectall:
			//is_allselected();
			btnMycenterCollectModifyselectall.setText("反 选");
			for(int q=0;q<is_select.length;q++){
				if(is_select[q]==1){is_select[q]=0;}
				else if(is_select[q]==0){is_select[q]=1;}}
			
//			 adapter = new MycenterCollectMainAdapter(getBaseContext(),goodsList,is_select,is_writting);
//			    adapter.setVisible(true);
//				listMycenterCollect.setAdapter(adapter);
				 adapter.setVisible(true);
				    adapter.updata(goodsList, is_select, is_writting);
			break;
		case R.id.btn_list_mycenter_collect_modify_remove:
				run_key=1;
				is_wait_for_delete=0;
					wait_for_delete();
		
			
			break;
		case R.id.ll_tohome :
			Intent intenthome =new Intent(CollectMainListActivity.this,HomeMainActivity.class);
			startActivity(intenthome);
			ExitApplication.getInstance().exit();
			break;
		case R.id.ll_tosearch :
			Intent intentsearch =new Intent(CollectMainListActivity.this,SearchMainActivity.class);
			startActivity(intentsearch);
			ExitApplication.getInstance().exit();
			break;
		case R.id.ll_toshoppingcart :
			Intent intentshoppingcart =new Intent(CollectMainListActivity.this,ShoppingcartMainListActivity.class);
			startActivity(intentshoppingcart);
			if(GlobalVarUtil.account.getUID()==null){
				Intent intentTolongin = new Intent(CollectMainListActivity.this,LoginActivity.class);
				startActivity(intentTolongin);
			}
//			ExitApplication.getInstance().exit();//购物车可以返回
			break;
		case R.id.ll_tomycenter :
			Intent intentmycenter =new Intent(CollectMainListActivity.this,MycenterMainActivity.class);
			startActivity(intentmycenter);
			if(GlobalVarUtil.account.getUID()==null){
				Intent intentTolongin = new Intent(CollectMainListActivity.this,LoginActivity.class);
				startActivity(intentTolongin);
			}
			ExitApplication.getInstance().exit();
			break;
		case R.id.ll_tomore :
			Intent intentmore =new Intent(CollectMainListActivity.this,MoreActivity.class);
			startActivity(intentmore);
			ExitApplication.getInstance().exit();
			break;
		}
	}
 
	private void wait_for_delete(){
		
		int mm=0;
		for(int q=0;q<is_select.length;q++){//为标题添加占位数据，该数据不做显示
			if(is_select[q]==2){mm=mm+1;}
			  System.out.println(q+".......qq...."+is_select[q]);
		}
		if(is_wait_for_delete<goodsList.size()+mm){
			int m=0;
			for(int q=0;q<is_wait_for_delete;q++){//在当前位置之前有几个标题
				if(is_select[q]==2){m=m+1;}
			}
			System.out.println(m+ ".......="+is_wait_for_delete+"===___"+run_key+"__-==="+is_select[is_wait_for_delete]);
			if(is_select[is_wait_for_delete]==1){
				delete_collect_id = goodsList.get(is_wait_for_delete-m).getID();
				typedelete = goodsList.get(is_wait_for_delete-m).getType();
				title_num=m;
				if(is_wait_for_delete==0){
					Toast.makeText(myContext, "开始删除，请稍候。", 1).show();
				}
				postcollectlistInfo();
			}
			else{
				is_wait_for_delete=is_wait_for_delete+1;
				wait_for_delete();
			}
		}
		else{ 
		 run_key=0;
		 is_wait_for_delete=0;
		 Toast.makeText(myContext, "删除结束，共删除"+deletCount+"件商品", 1).show();
	 }
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		if(goodsList.size()==0){}
		else{
		if(is_writting==0){
			int m=0;
			for(int q=0;q<arg2;q++){//为标题添加占位数据，该数据不做显示
				if(is_select[q]==2){m=m+1;}
			}
			if(is_select[arg2]==2){
				
			}
			else{
				GoodsEntity goodsEntity= goodsList.get(arg2-m);
				if(goodsEntity.getType()==2){
					 Intent intent = new Intent(CollectMainListActivity.this,GroupBuyDetailActivity.class);
						intent.putExtra("goodsEntity", goodsEntity);
						 startActivity(intent);
				}
//				else{
//					 Intent intent = new Intent(CollectMainListActivity.this,ShoppingmallGoodsDetailActivity.class);
//						intent.putExtra("ID", goodsEntity.getID());
//						intent.putExtra("type", 1);
//						 startActivity(intent);
//				}
			}
		}
		else{
			
			
			if(is_select[arg2]==2){}
			else{
		
				
			btnMycenterCollectModifyselectall.setText("反 选");
			
			   if(is_select[arg2]!=1){
			    is_select[arg2]=1;
			   }
			   else{
				   is_select[arg2]=0;
			   }
			   int p=0;
				 while(p<is_select.length){
						System.out.println(arg2+"tttttttttttttt"+is_select[p]);
					 p++;
				 }
				 
			  // is_allselected();
			//   System.out.println("...........yy."+is_allselect);
//			   if(is_allselect==1){
//					btnMycenterCollectModifyselectall.setText("反 选");
//					
//				}
//			   else{
//				   btnMycenterCollectModifyselectall.setText("全 选"); 
//			   }
				
			   // adapter = new MycenterCollectMainAdapter(getBaseContext(),goodsList,is_select,is_writting);
			    adapter.setVisible(true);
			    adapter.updata(goodsList, is_select, is_writting);
				//listMycenterCollect.setAdapter(adapter);
			}
		 }
		}
	}
	


	// 获取收藏夹列表
	private void postcollectlistInfo() {
		this.doAsync(new CallEarliest<Object>() {
			@Override
			public void onCallEarliest() throws Exception {
				// TODO Auto-generated method stub

			}

		}, new Callable<Object>() {
			@Override
			public String call() {
				thread1=Thread.currentThread();
				String reqUrl ;
				Map<String, Object> parameters = new LinkedHashMap<String, Object>();
				if(run_key==0){
					 reqUrl =getString(R.string.getCollectGoods_http);
					parameters.put("UID", GlobalVarUtil.account.getUID());
				}
				else{
					reqUrl = getString(R.string.deleteCollectGoods_http);
					// reqUrl = "";
					parameters.put("UID",GlobalVarUtil.account.getUID());
					parameters.put("ID", delete_collect_id);
					parameters.put("type",typedelete);
				}
				
                
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
			thread2=Thread.currentThread();
				System.out.println("code jsonStr=" + jsonStr);

				if (jsonStr.equals("-1")) {
					ViewUtil.showToast(myContext, myContext
							.getString(R.string.net_exception));
					if(run_key==1){
					  Toast.makeText(CollectMainListActivity.this,"删除失败",1).show();
						 is_wait_for_delete=is_wait_for_delete+1;
					   wait_for_delete();
					}
					
				} else {
					if(run_key==0){
					deal_list_data(jsonStr);
					}
					else{
						if(jsonStr.equals("1")){
//						   Toast.makeText(CollectMainListActivity.this,"删除失败",1).show();
						   is_wait_for_delete=is_wait_for_delete+1;
						     wait_for_delete();
						}
						else{
//							Toast.makeText(CollectMainListActivity.this,"删除成功",1).show();
							 System.out.println("...........tttttttttttt."+delete_collect_id);
							

									goodsList.remove(is_wait_for_delete-title_num);
									
									if(goodsList.size()==0){
										listMycenterCollect .addFooterView(footer);
//										
									}
									else{
										if(is_wait_for_delete<is_select.length-1){
											if(is_select[is_wait_for_delete+1]==2&&is_select[is_wait_for_delete-1]==2){
												point_move=1;
											}
											else{
												point_move=0;
											}
										}else{
											if(is_select[is_wait_for_delete-1]==2){
												point_move=1;
											}
											else{
												point_move=0;
											}
										}
										
										select=new int[is_select.length-1-point_move];
										
									for(int q=is_wait_for_delete-point_move;q<is_select.length-1-point_move;q++){
									  
										  select[q]=is_select[q+1+point_move];
										
										 
									  }	
									}
									deletCount++;
									 BusinessDao.deleteCollectionTableData(delete_collect_id,typedelete);
										update_goodslist();
//									 is_wait_for_delete=is_wait_for_delete+1;
								     wait_for_delete();
							   
						}
						
						
					}
					
				}
			
			}
		});
		
	}
private void deal_list_data(String jsonStr){
	
	try {
		
		
		if(jsonStr.equals("{\"list\":[\"\"]}\n")||jsonStr.trim().equals("")){
		}
		else{
			JSONObject jsonObject ;
			jsonObject = new JSONObject(jsonStr);
				String jsonList = jsonObject.getString("list");
				Type listType = new TypeToken<List<GoodsEntity>>(){}.getType();
				goodsList = new Gson().fromJson(jsonList, listType);
             
				BusinessDao.clear_collectdata();
			for(GoodsEntity gEntity : goodsList){	 
				BusinessDao.insertCollectionTableData(gEntity.getID(),gEntity.getType());
				BusinessDao.updata_sellerinfo(gEntity, null, null);
			}
			List<CollectEntity> userGoodsData = BusinessDao.getUserGoodsData();
			for(CollectEntity entity : userGoodsData){//删除旧的收藏商品数据
				if(entity.getID()!=null){
					if(BusinessDao.getTheCollectionData(entity.getID(),entity.getType()).size() == 0){
						BusinessDao.deleteGoodsTableData(entity.getID(), entity.getType());
					}
				}else{
					BusinessDao.deleteGoodsTableData(null, 0);
					Log.e("-=-=", "1-----");
				}
			}
		}
			
	} catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		ViewUtil.showToast(myContext, "返回数据异常，请重试。");
	}	
	 init();	
	
	
}
//获取商家详情

private void insert_sellerinfo() {
	this.doAsync(new CallEarliest<Object>() {
		@Override
		public void onCallEarliest() throws Exception {
			// TODO Auto-generated method stub
			
		}

	}, new Callable<Object>() {
		@Override
		public String call() {
thread1= Thread.currentThread();
			String reqUrl = getString(R.string.getShopsDetailInfo_php);

			Map<String, Object> parameters = new LinkedHashMap<String, Object>();
			
			
			parameters.put("ID",list.get(Q).getID());
			parameters.put("type",String.valueOf(goodsList.get(Q).getType()));
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
			thread2=Thread.currentThread();
        System.out.println("code jsonStr=" + jsonStr);
		
			if (jsonStr.trim().equals("-1")) {
				ViewUtil.showToast(myContext, myContext.getString(R.string.net_exception));
			}
			else if(jsonStr.trim().equals("{\"list\":[\"\"]}")||jsonStr==null||jsonStr.equals("null")){
				
			}
			else if(jsonStr.trim().equals("")||jsonStr.trim().equals("\n")||jsonStr.trim().equals(" \n")){
				
			}
			else {
				try {
					JSONObject json = new JSONObject(jsonStr);
					System.out.println("收藏夹 商家===================test"+json.getString("name"));
				    BusinessDao.updata_sellerinfo(goodsList.get(Q), json.getString("ID"), json.getString("name"));
				    int j=0;
				  
					if(M>0){
					for(String str:sellerID){
						if(str==null){break;}
						if(str.equals(json.getString("ID"))){j=1;break;}
					}
					}
					if(j==0){
					sellerID[M]=json.getString("ID");
					sellerName[M]=json.getString("name");
					M=M+1;
					}
					
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			Q=Q+1;
			add_to_list();
		}
	});
}
	

}


