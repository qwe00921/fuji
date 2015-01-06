package com.fsti.fjdicClient.dao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.content.ContentValues;
import android.content.Entity;
import android.database.Cursor;
import android.util.Log;
import android.widget.Toast;
import com.fsti.fjdicClient.ApplicationUtil;
import com.fsti.fjdicClient.bean.AccountInfo;
import com.fsti.fjdicClient.bean.AdvEntity;
import com.fsti.fjdicClient.bean.CollectEntity;
import com.fsti.fjdicClient.bean.CommunityGoodsCategoryEntity;
import com.fsti.fjdicClient.bean.ConsigneeAddressEntity;
import com.fsti.fjdicClient.bean.GoodsEntity;
import com.fsti.fjdicClient.bean.HomemaininfoEntity;
import com.fsti.fjdicClient.bean.SearchGoodsEntity;
import com.fsti.fjdicClient.bean.ShoppingcartInfoEntity;
import com.fsti.fjdicClient.util.DBHelper;
import com.fsti.fjdicClient.util.GlobalVarUtil;
import com.fsti.fjdicClient.util.timecount.Timecount;

/**
 * 数据库相关业务操作类
 * @author 金涛
 *
 */
public class BusinessDao {
	
	
	public static String Table_Name_CollectionInfo = "CollectionInfo";
	public static String Table_Name_GoodsInfo = "GoodsInfo";
	public static String Table_Name_AdvInfo = "AdvInfo";
	public static String Table_Name_AccountInfo = "AccountInfo";
	public static String Table_Name_ShoppingMallSortInfo = "ShoppingMallSortInfo";
	public static String Table_Name_SearchHistoryInfo = "SearchHistoryInfo";
	public static String Table_Name_ShoppingcartInfo = "ShoppingcartInfo";
	
	public static String Table_Name_HomeMainInfo = "HomeMainInfo";//首页内容yyy
	public static String Table_Name_AddressInfo = "AddressInfo";//收货地址信息 yyy
	
	public static final String SQL_Create_Table_HomeMainInfo = "CREATE TABLE " + Table_Name_HomeMainInfo
	+ " ( "  + " ID    TEXT,   " +
	"  sm_img    TEXT,   " + // 商城图片链接
	"  sm_text  TEXT, "+ //商城文本内容
	"  sm_text2  TEXT, "+ //商城文本内容
	"  gb_img  TEXT, "+ //团购图片链接
	"  gb_text  TEXT, "+ //团购文本内容
	"  gb_text2  TEXT, "+ //团购文本内容
	"  online_img  TEXT, "+ //在线服务图片链接
	"  online_text  TEXT, "+ //在线服务文本内容
	"  online_text2  TEXT, "+ //在线服务文本内容
	"  online_num  TEXT "+ //在线服务号码
	")";
	public static final String SQL_Create_Table_SearchHistoryInfo = "CREATE TABLE " + Table_Name_SearchHistoryInfo
	+ " ( " + " keyword    TEXT,   " + // 关键字
	"  type  integer "+ //0：全部，1：
	")";
	public static final String SQL_Create_Table_ShoppingcartInfo = "CREATE TABLE " + Table_Name_ShoppingcartInfo
	+ " ( " + " ID    TEXT,   " + // 商品ID
	"  UID    TEXT, " + // 用户ID
	"  FID    TEXT, " + // 用户ID
	"  shopsID    TEXT, " + // 商家ID
	"  shopsName    TEXT," + // 商家名称
	"  type   integer," + // 类型
	"  name   TEXT," + // 商品名称
	"  intro   TEXT," + // 商品简介
	"  counts   integer," + // 购买数量
	"  freight   TEXT," + //运费
	"  imageUrl   TEXT, " + // 商品图片地址
	"  ToSelectBoolean  integer, "+ // 0表示选中true  1表示选中false
	"  freightType  TEXT, "+ // 
	"  freightTypeSelect  integer, "+ //运费类型，0：表示第一个被选中， 以此类推
	"  price  TEXT "+ //商品价格
	")";
	public static final String SQL_Create_Table_CollectionInfo = "CREATE TABLE " + Table_Name_CollectionInfo
	+ " ( " + " ID   TEXT  , " + // 商品ID
	"  type   integer, "+ //类型
	"UID  TEXT"+
	")";
	public static final String SQL_Create_Table_GoodsInfo = "CREATE TABLE " + Table_Name_GoodsInfo
	+ " ( " + " ID    TEXT,   " + // 商品ID
	"  type    integer, " + // 商品类型 	1:社区商城 2:团购
	"  UID    TEXT, " + // yonhuID
	"  name    TEXT," + // 商品名称
	"  intro   TEXT," + // 商品简介
	"  discountePrice   TEXT," + //商品打折价 
	"  originalCost   TEXT, " + 			// 商品原价
	"  selledCount   TEXT, " + 			// 最近的销售数量
	"  freight   TEXT, " + 			// 运费
	"  imageUrl   TEXT, " + 			// 商品图片地址
	"  groupBuyingCount   integer, " + 			// 团购人数
	"  isPastDue   integer , " + 			// 是否过期  0：未过期  1：过期
	"  FID  TEXT ,"+ //商品分类
	"sellerID TEXT,"+
	"sellerName TEXT"+
	")";
	public static final String SQL_Create_Table_AccountInfo = "CREATE TABLE " + Table_Name_AccountInfo
	+ " ( " + " isLogin integer,   " + // 0:登录成功，昵称、电话号码、电子邮件需要填写  1:登录失败
	"  nickName    TEXT, " + // 昵称
	"  telephone    TEXT," + // 电话号码
	"  email   TEXT " + 			// 电子邮件
	")";
	public static final String SQL_Create_Table_AdvInfo = "CREATE TABLE " + Table_Name_AdvInfo
	+ " ( " + " type integer,   " + // 1:首页广告2:社区商品促销3:团购商品促销4:活动介绍信息5:单个商品展示
	"  goodsID    TEXT, " + // 商品ID，仅type=5时填写
	"  imageUrl    TEXT," + // 图片Url地址
	"  title   TEXT," + // 标题
	"  updatetime   TEXT," + 			// 更新时间
	"  linkUrl   TEXT " + 			// 链接地址
	")";
	public static final String SQL_Create_Table_ShoppingMallSortInfo = "CREATE TABLE " + Table_Name_ShoppingMallSortInfo
	+ " ( " + " ID TEXT,   " + // 商品类别ID
	"  level    integer, " + // 类别等级 1:一级类别 2:二级类别 3:三级类别
	"  name    TEXT," + // 商品类别名称
	"  parentID   TEXT," + // 上级类别ID
	"  orderPosition   integer," + // 排序位置
	"  goodsCount   integer," + // 商品数量
//	"  imgUrl   TEXT," + // 分类图标下载地址
	"  updatetime   TEXT" + // 更新时间
	")";
	
	//新增收货地址相关的数据库操作，yyy
	public static final String SQL_Create_Table_AddressInfo  = "CREATE TABLE " +Table_Name_AddressInfo
	+ " ( " + " ID   TEXT ," + // 收货地址ID
	"  name    TEXT, " + //收货人名称
	"  area    TEXT," + // 区域
	" telephone   TEXT, " + //收货人手机号
	"  postCode  TEXT, "+
	"  Address   TEXT," + // 收货地址
	" isDefault   integer," + // 是否是默认收货地址，0表示是，1表示否
	"  updatetime   TEXT," + // 更新时间
	"  UID  TEXT"+
	")";
	//判断收货地址信息表是否存在yyy
//	public static Boolean is_table_AddressInfo_exit(){
//		DBHelper dbHelper = null;
//		dbHelper = new DBHelper(ApplicationUtil.myContext);
//		dbHelper.open();
//	return 	dbHelper.isTableExist(Table_Name_AddressInfo);
//		
//	}
	
	//判断之前是否存储有数据
	public static Boolean find_oldHomemaininfo(){
		
		 Cursor cursor=CommonDao.select("select * from "+Table_Name_HomeMainInfo+" where ID = '1';");
			//System.out.println("--------------------------"+cursor.moveToFirst());

		 if(!cursor.moveToFirst()){
			 new DBHelper(ApplicationUtil.myContext).closeDb();
			return false;
		}
		 new DBHelper(ApplicationUtil.myContext).closeDb();
		return true;
	}
	//获取某个字段的值
	public static String find_from_Homemaininfo(String parameter){
		
		 Cursor cursor=CommonDao.select("select "+parameter+" from "+Table_Name_HomeMainInfo+" where ID = '1';");
		
          String result="";
          cursor.moveToFirst();
		 if(cursor.isAfterLast()){
			 new DBHelper(ApplicationUtil.myContext).closeDb();
			return result;
		}
		 else{
				System.out.println(cursor.getColumnCount()+"--------------------------"+parameter);
			 result = cursor.getString(cursor.getColumnIndex(parameter));
			 cursor.moveToNext();
		 }
		 new DBHelper(ApplicationUtil.myContext).closeDb();
		return result;
	}
	//获取HomemaininfoEntity实体类
	public static HomemaininfoEntity get_HomemaininfoEntity(){
		HomemaininfoEntity entity=new HomemaininfoEntity();
		 Cursor cursor=CommonDao.select("select * from "+Table_Name_HomeMainInfo+" where ID = '1';");
         cursor.moveToFirst();
		 if(cursor.isAfterLast()){
			 new DBHelper(ApplicationUtil.myContext).closeDb();
			return entity;
		}
		 else{
			entity.setSm_img(cursor.getString(cursor.getColumnIndex("sm_img"))) ;
			entity.setGb_img(cursor.getString(cursor.getColumnIndex("gb_img"))) ;
			entity.setOnline_img(cursor.getString(cursor.getColumnIndex("online_img"))) ;
			entity.setSm_text(cursor.getString(cursor.getColumnIndex("sm_text"))) ;
			entity.setSm_text2(cursor.getString(cursor.getColumnIndex("sm_text2"))) ;
			entity.setGb_text(cursor.getString(cursor.getColumnIndex("gb_text"))) ;
			entity.setGb_text2(cursor.getString(cursor.getColumnIndex("gb_text2"))) ;
			entity.setOnline_text(cursor.getString(cursor.getColumnIndex("online_text"))) ;
			entity.setOnline_text2(cursor.getString(cursor.getColumnIndex("online_text2"))) ;
			entity.setOnline_num(cursor.getString(cursor.getColumnIndex("online_num"))) ;
			
			 cursor.moveToNext();
		 }
		 new DBHelper(ApplicationUtil.myContext).closeDb();
		return entity;
	}
	
	
	//添加一条新的首页内容 yyy
	public static  void insert_newHomemaininfo(HomemaininfoEntity homemaininfo ){
		
		ContentValues contentvalue = new ContentValues();
		contentvalue.put("ID", "1");
		contentvalue.put("sm_img", homemaininfo.getSm_img());
		contentvalue.put("sm_text", homemaininfo.getSm_text());
		contentvalue.put("sm_text2", homemaininfo.getSm_text2());
		contentvalue.put("gb_img", homemaininfo.getGb_img());
		contentvalue.put("gb_text", homemaininfo.getGb_text());
		contentvalue.put("gb_text2", homemaininfo.getGb_text2());
		contentvalue.put("online_img", homemaininfo.getOnline_img());
		contentvalue.put("online_text", homemaininfo.getOnline_text());
		contentvalue.put("online_text2", homemaininfo.getOnline_text2());
		contentvalue.put("online_num", homemaininfo.getOnline_num());
		CommonDao.insert(Table_Name_HomeMainInfo, contentvalue);
		new DBHelper(ApplicationUtil.myContext).closeDb();
	} 
	
	//更新首页内容
	public static void update_Homemaininfo( String parameter[],String values[]){
		
		
		ContentValues setdefaultaddress = new ContentValues();
		for(int q=0;q<parameter.length&&parameter[q]!=null;q++){
			setdefaultaddress.put(parameter[q], values[q]);
		}
	CommonDao.update(Table_Name_HomeMainInfo,setdefaultaddress, "ID = '1';");
	new DBHelper(ApplicationUtil.myContext).closeDb();
	}
	
	
	
	
	//新建收货地址信息表yyy
	public static void creat_table_addressinfo(){
		CommonDao.creatTable(SQL_Create_Table_AddressInfo, Table_Name_AddressInfo);
	}
	//添加新地址 yyy
	public static  void insert_newaddress(ConsigneeAddressEntity addressinfoentity){
		ContentValues addressinfo = new ContentValues();
		addressinfo.put("ID",addressinfoentity.getID() );
		addressinfo.put("name",addressinfoentity.getName() );
		addressinfo.put("telephone",addressinfoentity.getTelephone());
		addressinfo.put("postCode",addressinfoentity.getPostCode() );
		addressinfo.put("Address",addressinfoentity.getAddress());
		addressinfo.put("isDefault", 1);
		//addressinfo.put( "updatetime",new  Timecount().timeformat(new Date(System.currentTimeMillis())));
		addressinfo.put("UID",addressinfoentity.getUID());
		
		
		List<ConsigneeAddressEntity>  addressList = get_addresslist();
		
		for(int q=0;q<addressList.size();q++){
			System.out.println(addressList.get(q).getID()+"======================="+addressinfo.get("ID"));
			if(addressList.get(q).getID().equals(addressinfo.get("ID"))){
				return ;
			}
			
		}
		CommonDao.insert(Table_Name_AddressInfo, addressinfo);	
		new DBHelper(ApplicationUtil.myContext).closeDb();
	return	;
	} 
	//清空收货地址列表数据
	public static void clear_addressdata(){
		CommonDao.deleteTableData(Table_Name_AddressInfo);
	}
	//获取收货地址列表 yyy
	public static List<ConsigneeAddressEntity> get_addresslist(){		
		
		String[] columns = new String[7];
		columns[0]="name";
		columns[1]="telephone";
		columns[2]="Address";
		columns[3]="ID";
		columns[4]="UID";
		columns[5]="postCode";
		columns[6]="isDefault";
		 Cursor cursor=CommonDao.findList(Table_Name_AddressInfo, columns, "UID='"+GlobalVarUtil.account.getUID()+"'", null, null, null, null);
		
		 
		List<ConsigneeAddressEntity> addressList = new ArrayList<ConsigneeAddressEntity>();
		ConsigneeAddressEntity addressEntity;
		if(cursor!=null){
		cursor.moveToFirst();
		 while(!cursor.isAfterLast()){
			    addressEntity = new ConsigneeAddressEntity();
			    addressEntity.setID(cursor.getString(cursor.getColumnIndex("ID")));
				addressEntity.setName(cursor.getString(cursor.getColumnIndex("name")));
				addressEntity.setTelephone(cursor.getString(cursor.getColumnIndex("telephone")));
				addressEntity.setAddress(cursor.getString(cursor.getColumnIndex("Address")));
				addressEntity.setUID(cursor.getString(cursor.getColumnIndex("UID")));
				addressEntity.setPostCode(cursor.getString(cursor.getColumnIndex("postCode")));
				addressEntity.setIsDefault(cursor.getInt(cursor.getColumnIndex("isDefault")));
				addressList.add(addressEntity);
				cursor.moveToNext();
			 
		 }
		 cursor.close();
		 new DBHelper(ApplicationUtil.myContext).closeDb();
		}
		 return addressList;
		 
	   
	}
	
	//设置默认收货地址 yyy
	public static void set_defaultaddress(String ID){
		
		ContentValues setdefaultaddress ;
		setdefaultaddress = new ContentValues();
		setdefaultaddress.put("isDefault ", 1);
		CommonDao.update(Table_Name_AddressInfo,setdefaultaddress, "isDefault = '0' AND UID='"+GlobalVarUtil.account.getUID()+"';");
		
		setdefaultaddress = new ContentValues();
		setdefaultaddress.put("isDefault ", 0);
		CommonDao.update(Table_Name_AddressInfo,setdefaultaddress, "ID = '"+ID+"' AND UID='"+GlobalVarUtil.account.getUID()+"';");
	}
	
	//通过ID获取收货地址信息 yyy
	public static ConsigneeAddressEntity get_addressinfo(String ID){
		
		 Cursor cursor=CommonDao.select("select * from "+Table_Name_AddressInfo+" where ID = '"+ID+"' AND UID='"+GlobalVarUtil.account.getUID()+"';");
		 ConsigneeAddressEntity addressEntity = new ConsigneeAddressEntity();
		  cursor.moveToFirst();
		  if(!cursor.isAfterLast()){
			addressEntity.setName(cursor.getString(cursor.getColumnIndex("name")));
			addressEntity.setTelephone(cursor.getString(cursor.getColumnIndex("telephone")));
			addressEntity.setAddress(cursor.getString(cursor.getColumnIndex("Address")));
		   addressEntity.setPostCode(cursor.getString(cursor.getColumnIndex("postCode")));
		   cursor.moveToNext();
		  }
		  cursor.close();
		  new DBHelper(ApplicationUtil.myContext).closeDb();
		 return addressEntity;
	}
	//获取默认地址信息 yyy
	public static ConsigneeAddressEntity get_default_addressinfo(){
	
		 Cursor cursor=CommonDao.select("select * from "+Table_Name_AddressInfo+" where isDefault= '0' AND UID='"+GlobalVarUtil.account.getUID()+"';");
		 ConsigneeAddressEntity addressEntity = new ConsigneeAddressEntity();
		  cursor.moveToFirst();
		  if(!cursor.isAfterLast()){
			addressEntity.setName(cursor.getString(cursor.getColumnIndex("name")));
			addressEntity.setTelephone(cursor.getString(cursor.getColumnIndex("telephone")));
			addressEntity.setAddress(cursor.getString(cursor.getColumnIndex("Address")));
		   addressEntity.setPostCode(cursor.getString(cursor.getColumnIndex("postCode")));
		   cursor.moveToNext();
		  }
		  cursor.close();
		  new DBHelper(ApplicationUtil.myContext).closeDb();
		 return addressEntity;
	}
	//获取默认地址信息ID yyy
	public static String get_default_addressID(){
		
		String ID="0";
		
		 Cursor cursor=CommonDao.select("select ID from "+Table_Name_AddressInfo+" where isDefault= '0' AND UID='"+GlobalVarUtil.account.getUID()+"';");
		 if(cursor!=null){
		 cursor.moveToFirst();
		  if(!cursor.isAfterLast()){
			ID =cursor.getString(cursor.getColumnIndex("ID"));
			cursor.close();
		   
		  }
		if(ID.equals("0")){
			  ContentValues	  setdefaultaddress = new ContentValues();
				setdefaultaddress.put("isDefault", 0);
				ID=get_nodefaultaddress_first_ID();
				CommonDao.update(Table_Name_AddressInfo,setdefaultaddress, "ID = '"+ID+"' AND UID='"+GlobalVarUtil.account.getUID()+"';"); 
		  }
		  
		new DBHelper(ApplicationUtil.myContext).closeDb();
		 }
		 return ID;
	}
	//或得非默认地址的第一个地址的ID yyy
	private static String get_nodefaultaddress_first_ID(){
		
		String ID="0";
		
		 Cursor cursor=CommonDao.select("select ID from "+Table_Name_AddressInfo+" where isDefault= '1' AND UID='"+GlobalVarUtil.account.getUID()+"';");
		 if(cursor!=null){
		 cursor.moveToFirst();
		  if(!cursor.isAfterLast()){
			ID =cursor.getString(cursor.getColumnIndex("ID"));
			
		  }
		  cursor.close();
		  new DBHelper(ApplicationUtil.myContext).closeDb();
		 }
		return ID;
	}
	
	//删除一个地址信息yyy
	public static Boolean delete_one_address(String ID){
		
		return CommonDao.delete(Table_Name_AddressInfo, "ID='"+ID+"' AND UID='"+GlobalVarUtil.account.getUID()+"';");
	}
	
	//修改收货地址 yyy
	public static void updata_address(String ID,ContentValues setdefaultaddress){
		
		
		CommonDao.update(Table_Name_AddressInfo,setdefaultaddress, "ID = '"+ID+"' AND UID='"+GlobalVarUtil.account.getUID()+"';");
		new DBHelper(ApplicationUtil.myContext).closeDb();
	}
	
	
	

//	//判断收藏夹表是否存在yyy
//	public static Boolean is_table_CollectionInfo_exit(){
//		DBHelper dbHelper = null;
//		dbHelper = new DBHelper(ApplicationUtil.myContext);
//		dbHelper.open();
//	return 	dbHelper.isTableExist(Table_Name_CollectionInfo );
//		
//	}
	//新建收藏夹表yyy
	public static void creat_table_CollectionInfo (){
		CommonDao.creatTable(SQL_Create_Table_CollectionInfo , Table_Name_CollectionInfo);
		new DBHelper(ApplicationUtil.myContext).closeDb();
	}
	
//	//新增一条收藏夹数据yyy
//	public static long insert_CollectionInfo(ContentValues addressinfo){
//		DBHelper dbHelper = null;
//		dbHelper = new DBHelper(ApplicationUtil.myContext);
//		dbHelper.open();
//	return	dbHelper.insert(Table_Name_CollectionInfo, addressinfo);
//	}
//	//删除一条收藏夹数据yyy
//	public static boolean delete_CollectionInfo(String ID){
//		DBHelper dbHelper = null;
//		dbHelper = new DBHelper(ApplicationUtil.myContext);
//		dbHelper.open();
//		return dbHelper.delete(Table_Name_AddressInfo, "ID='"+ID+"';");
//		
//	}
//	//删除多条收藏夹数据yyy
//	public static void delete_more_CollectionInfo(String ID){
//		delete_CollectionInfo(ID);
//		
//	}
	
	
//	//判断团购商品信息表是否存在yyy
//	public static Boolean is_table_goodsInfo_exit(){
//		DBHelper dbHelper = null;
//		dbHelper = new DBHelper(ApplicationUtil.myContext);
//		dbHelper.open();
//	return 	dbHelper.isTableExist(Table_Name_GoodsInfo);
//		
//	}
	//新建团购商品信息表yyy
	public static void creat_table_goodsInfo (){
		CommonDao.creatTable(SQL_Create_Table_GoodsInfo ,Table_Name_GoodsInfo);
		new DBHelper(ApplicationUtil.myContext).closeDb();
	}
	//批量插入团购商品信息yyy
	public static void insert_more_goodsInfo(List<ContentValues> list){
		
		List<ContentValues> list2 = new ArrayList<ContentValues>();
		for(ContentValues entity: list){
			if(get_goodsinfo(entity.getAsString("ID"),entity.getAsInteger("type")).getID()==null){
				list2.add(entity);
			}
		}
		CommonDao.insert(Table_Name_GoodsInfo, list2);
		new DBHelper(ApplicationUtil.myContext).closeDb();
	}
	//根据商品ID查询商品信息yyy
	public static GoodsEntity get_goodsinfo(String ID, int type){
	
		 GoodsEntity goodsEntity = new GoodsEntity();
		 
	Cursor cursor =	CommonDao.select("select * from "+Table_Name_GoodsInfo+" where ID = '"+ID+"' AND UID = '"+GlobalVarUtil.account.getUID()+"' AND type = "+type);
         cursor.moveToFirst();
         if(!cursor.isAfterLast()){
        	 goodsEntity.setID(cursor.getString(cursor.getColumnIndex("ID")));
        	 goodsEntity.setType(cursor.getInt(cursor.getColumnIndex("type")));
        	goodsEntity.setName(cursor.getString(cursor.getColumnIndex("name")));
        	goodsEntity.setIntro(cursor.getString(cursor.getColumnIndex("intro")));
        	goodsEntity.setDiscountedPrice(cursor.getString(cursor.getColumnIndex("discountePrice")));
        	goodsEntity.setOriginalCost(cursor.getString(cursor.getColumnIndex("originalCost")));
        	goodsEntity.setSelledCount(cursor.getInt(cursor.getColumnIndex("selledCount")));
        	goodsEntity.setFreight(cursor.getString(cursor.getColumnIndex("freight")));
        	goodsEntity.setImageUrl(cursor.getString(cursor.getColumnIndex("imageUrl")));
        	goodsEntity.setGroupBuyingCount(cursor.getInt(cursor.getColumnIndex("groupBuyingCount")));
        	goodsEntity.setIsPastDue(cursor.getInt(cursor.getColumnIndex("isPastDue")));
        	goodsEntity.setFID(cursor.getString(cursor.getColumnIndex("FID")));
        	
        cursor.moveToNext();
         }
         cursor.close();
         new DBHelper(ApplicationUtil.myContext).closeDb();
         return goodsEntity;
	}
	//根据商品ID ，更新团购商品数量yyy
	public static void updata_groupBuyingCount(String ID,int type ,int groupBuyingCount){
		
		ContentValues setdefaultaddress = new ContentValues();
		setdefaultaddress.put("groupBuyingCount", groupBuyingCount);
		
		CommonDao.update(Table_Name_GoodsInfo,setdefaultaddress, "ID = '"+ID+"'  AND UID = '"+GlobalVarUtil.account.getUID()+"' AND type = "+ type +";");
		new DBHelper(ApplicationUtil.myContext).closeDb();
	}
	//根据商品ID，获取商家信息
	public static String[] get_sellerinfo(String ID,int type){
		String[] sellerinfo = new String[2];
		
	Cursor cursor =	CommonDao.select("select * from "+Table_Name_GoodsInfo+" where ID = '"+ID+"' AND UID =  '"+GlobalVarUtil.account.getUID()+"' AND type = " +type);
        cursor.moveToFirst();
        if(!cursor.isAfterLast()){
       	sellerinfo[0]=cursor.getString(cursor.getColumnIndex("sellerID"));
     	sellerinfo[1]=cursor.getString(cursor.getColumnIndex("sellerName"));
       	
       cursor.moveToNext();
        }
        cursor.close();
        new DBHelper(ApplicationUtil.myContext).closeDb();
        return sellerinfo;
	}
public static void updata_sellerinfo(GoodsEntity goodsEntity,String sellerID,String sellerName){
		

        if(get_goodsinfo(goodsEntity.getID(),goodsEntity.getType()).getID()==null){
        	ContentValues setdefaultaddress = new ContentValues();
        	setdefaultaddress.put("ID", goodsEntity.getID());
        	setdefaultaddress.put("UID", GlobalVarUtil.account.getUID());
        	setdefaultaddress.put("type", goodsEntity.getType());
        	setdefaultaddress.put("name", goodsEntity.getName());
        	setdefaultaddress.put("discountePrice", goodsEntity.getDiscountedPrice());
        	setdefaultaddress.put("originalCost", goodsEntity.getOriginalCost());
        	setdefaultaddress.put("selledCount", goodsEntity.getSelledCount());
        	setdefaultaddress.put("freight", goodsEntity.getFreight());
        	setdefaultaddress.put("imageUrl", goodsEntity.getImageUrl());
        	setdefaultaddress.put("groupBuyingCount", goodsEntity.getGroupBuyingCount());
        	setdefaultaddress.put("isPastDue", goodsEntity.getIsPastDue());
        	setdefaultaddress.put("FID", goodsEntity.getFID());
        	
    		setdefaultaddress.put("sellerID", sellerID);
    		setdefaultaddress.put("sellerName", sellerName);
			CommonDao.insert(Table_Name_GoodsInfo, setdefaultaddress);
		}
        else{
        	ContentValues  values = new ContentValues();
        	values.put("sellerID", sellerID);
        	values.put("sellerName", sellerName);
		CommonDao.update(Table_Name_GoodsInfo,values,"ID = '"+goodsEntity.getID()+"' AND type = "+ goodsEntity.getType()+ " AND UID = '"+GlobalVarUtil.account.getUID()+"'"+";");
        }
		new DBHelper(ApplicationUtil.myContext).closeDb();
	}
	
//根据商家ID查询商品信息yyy
public static List<GoodsEntity> get_goodsinfo2(String sellerID){

	 GoodsEntity goodsEntity ;
	 List<GoodsEntity> data = new ArrayList<GoodsEntity>();
Cursor cursor =	CommonDao.select("select * from "+Table_Name_GoodsInfo+" where sellerID = '"+sellerID+"' AND UID = '"+GlobalVarUtil.account.getUID()+"' ;");
     cursor.moveToFirst();
     while(!cursor.isAfterLast()){
    	 goodsEntity = new GoodsEntity();
    	 goodsEntity.setID(cursor.getString(cursor.getColumnIndex("ID")));
    	 goodsEntity.setType(cursor.getInt(cursor.getColumnIndex("type")));
    	goodsEntity.setName(cursor.getString(cursor.getColumnIndex("name")));
    	goodsEntity.setIntro(cursor.getString(cursor.getColumnIndex("intro")));
    	goodsEntity.setDiscountedPrice(cursor.getString(cursor.getColumnIndex("discountePrice")));
    	goodsEntity.setOriginalCost(cursor.getString(cursor.getColumnIndex("originalCost")));
    	goodsEntity.setSelledCount(cursor.getInt(cursor.getColumnIndex("selledCount")));
    	goodsEntity.setFreight(cursor.getString(cursor.getColumnIndex("freight")));
    	goodsEntity.setImageUrl(cursor.getString(cursor.getColumnIndex("imageUrl")));
    	goodsEntity.setGroupBuyingCount(cursor.getInt(cursor.getColumnIndex("groupBuyingCount")));
    	goodsEntity.setIsPastDue(cursor.getInt(cursor.getColumnIndex("isPastDue")));
    	goodsEntity.setFID(cursor.getString(cursor.getColumnIndex("FID")));
    	data.add(goodsEntity);
        cursor.moveToNext();
     }
     cursor.close();
     new DBHelper(ApplicationUtil.myContext).closeDb();
     return data;
}
	
	
	
//	/**
//	 * 获取广告列表信息
//	 * @return 
//	 */
//	public static List<AdvEntity> getAdvList(int type,String goodsID){
//		DBHelper dbHelper = null;
//		List<AdvEntity> list = new ArrayList<AdvEntity>();
//		//String update = "";
//		try {
//			String sql = "";
//			if(goodsID != null){
//				sql = "select * from " + BusinessDao.Table_Name_AdvInfo + " where type= " + type + " and goodsID = '" + goodsID + "'";
//			}
//			else{
//				sql = "select * from " + BusinessDao.Table_Name_AdvInfo + " where type= " + type;
//			}
//			dbHelper = new DBHelper(ApplicationUtil.myContext);
//			dbHelper.open();
//			Cursor c= dbHelper.select(sql);
//			c.moveToFirst();
//			while(!c.isAfterLast()){
//				AdvEntity entity = new AdvEntity();
//				entity.setType(c.getInt(c.getColumnIndex("type")));
//				entity.setGoodsID(c.getString(c.getColumnIndex("goodsID")));
//				entity.setTitle(c.getString(c.getColumnIndex("title")));
//				entity.setImageUrl(c.getString(c.getColumnIndex("imageUrl")));
//				entity.setLinkUrl(c.getString(c.getColumnIndex("linkUrl")));
//				entity.setUpdatetime(c.getString(c.getColumnIndex("updatetime")));
//				list.add(entity);
//				c.moveToNext();
//			}
//			c.close();
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}finally{
//			if(dbHelper!=null){
//				dbHelper.closeDb();
//			}
//		}
//		return list;
//	}
//	
	/**
	 * 获取更新时间
	 * @param tableName 表名
	 * @return
	 */
	public static String getUpdatetime(String tableName){
		DBHelper dbHelper = null;
		String updatetime = "";
		try {
			String sql = "select updatetime from " + tableName;
			dbHelper = new DBHelper(ApplicationUtil.myContext);
			dbHelper.open();
			Cursor c= dbHelper.select(sql);
			c.moveToFirst();
			if(!c.isAfterLast()){
				updatetime = c.getString(c.getColumnIndex("updatetime"));
			}
			c.close();
			
			
		} catch (Exception e) {
			Log.e("BusinessDao", e.toString());
			e.printStackTrace();
		}finally{
			if(dbHelper!=null){
				dbHelper.closeDb();
			}
		}
		return updatetime;
	}
	
	/**
	 * 获取广告更新时间
	 * @param tableName 表名
	 * @param type 广告类型
	 * @param goodsID 商品ID
	 * @return
	 */
	public static String getAdvUpdatetime(int type,String goodsID){
		DBHelper dbHelper = null;
		String updatetime = "";
		try {
			String sql = "";
			if(goodsID != null){
				sql = "select updatetime from " + BusinessDao.Table_Name_AdvInfo + " where type= " + type + " and goodsID = '" + goodsID + "'";
			}
			else{
				sql = "select updatetime from " + BusinessDao.Table_Name_AdvInfo + " where type= " + type;
			}
			dbHelper = new DBHelper(ApplicationUtil.myContext);
			dbHelper.open();
			Cursor c= dbHelper.select(sql);
			c.moveToFirst();
			if(!c.isAfterLast()){
				updatetime = c.getString(c.getColumnIndex("updatetime"));
			}
			c.close();
			
			
		} catch (Exception e) {
			Log.e("BusinessDao", e.toString());
			e.printStackTrace();
		}finally{
			if(dbHelper!=null){
				dbHelper.closeDb();
			}
		}
		return updatetime;
	}
	
	/**
	 * 获取社区商城分类列表
	 * @param level 分类等级 0表示查询所有分类
	 * @return 社区商城分类列表
	 */
	public static List<CommunityGoodsCategoryEntity> getShoppingMallSortList(int level){
		DBHelper dbHelper = null;
		List<CommunityGoodsCategoryEntity> list = new ArrayList<CommunityGoodsCategoryEntity>();
		//String update = "";
		try {
			String sql = "";
			
			if(level == 0){
				sql = "select * from " + BusinessDao.Table_Name_ShoppingMallSortInfo;
			}
			else{
				sql = "select * from " + BusinessDao.Table_Name_ShoppingMallSortInfo + " where level = " + level;
			}
			dbHelper = new DBHelper(ApplicationUtil.myContext);
			dbHelper.open();
			Cursor c= dbHelper.select(sql);
			c.moveToFirst();
			while(!c.isAfterLast()){
				CommunityGoodsCategoryEntity entity = new CommunityGoodsCategoryEntity();
				entity.setID(c.getString(c.getColumnIndex("ID")));
				entity.setLevel(c.getInt(c.getColumnIndex("level")));
				entity.setName(c.getString(c.getColumnIndex("name")));
				entity.setParentID(c.getString(c.getColumnIndex("parentID")));
				entity.setOrderPosition(c.getInt(c.getColumnIndex("orderPosition")));
				entity.setUpdatetime(c.getString(c.getColumnIndex("updatetime")));
				entity.setGoodsCount(c.getInt(c.getColumnIndex("goodsCount")));
//				entity.setImgUrl(c.getString(c.getColumnIndex("imgUrl")));
				list.add(entity);
				c.moveToNext();
			}
			c.close();
			
		} catch (Exception e) {
			Log.e("BusinessDao", e.toString());
			e.printStackTrace();
		}finally{
			if(dbHelper!=null){
				dbHelper.closeDb();
			}
		}
		return list;
	}
	
	/**
	 * 删除广告表数据
	 * @param type 类型 1:首页广告2:社区商品促销3:团购商品促销4:活动介绍信息5:单个商品展示
	 * @param goodsID 商品ID
	 */
	public static void deleteAdvTableData(int type,String goodsID){
		DBHelper dbHelper = null;
		
		try {
			dbHelper = new DBHelper(ApplicationUtil.myContext);
			dbHelper.open();
			if(dbHelper.isTableExist(Table_Name_AdvInfo)){
				String deleteSql = "";
				if(goodsID != null){
					deleteSql = "delete from " + Table_Name_AdvInfo + " where type=" + type + " and goodsID ='" + goodsID + "'";
				}else{
					deleteSql = "delete from " + Table_Name_AdvInfo + " where type=" + type;
				}
				dbHelper.execSQL(deleteSql);
				//Toast.makeText(myContext, TABLE_USERS+"表存在，删除成功!", Toast.LENGTH_SHORT).show();
			}else{
				//Toast.makeText(myContext, TABLE_USERS+"表不存在，不用删除!", Toast.LENGTH_SHORT).show();
			}
		} catch (Exception e) {
			Log.e("BusinessDao", e.toString());
			e.printStackTrace();
		}finally{
			if(dbHelper!=null){
				dbHelper.closeDb();
			}
		}
	}
	/**
	 * 删除一条收藏表数据
	 * @param ID 商品ID
	 */
	public static void deleteCollectionTableData(String ID , int type){
		DBHelper dbHelper = null;
		
		try {
			dbHelper = new DBHelper(ApplicationUtil.myContext);
			dbHelper.open();
			if(dbHelper.isTableExist(Table_Name_CollectionInfo)){
				String deleteSql = "";
				deleteSql = "delete from " + Table_Name_CollectionInfo + " where ID='" + ID + "' AND UID='"+GlobalVarUtil.account.getUID()+"' AND type ="+type;
				dbHelper.execSQL(deleteSql);
				//Toast.makeText(myContext, TABLE_USERS+"表存在，删除成功!", Toast.LENGTH_SHORT).show();
			}else{
				//Toast.makeText(myContext, TABLE_USERS+"表不存在，不用删除!", Toast.LENGTH_SHORT).show();
			}
		} catch (Exception e) {
			Log.e("BusinessDao", e.toString());
			e.printStackTrace();
		}finally{
			if(dbHelper!=null){
				dbHelper.closeDb();
			}
		}
	}
	/**
	 * 插入收藏表数据
	 * @param ID 商品ID
	 */
	public static boolean insertCollectionTableData(String ID , int type){
		DBHelper dbHelper = null;
		boolean state = false;
		try {
			dbHelper = new DBHelper(ApplicationUtil.myContext);
			dbHelper.open();
			if(dbHelper.isTableExist(Table_Name_CollectionInfo)){
				List<String> data = getTheCollectionData(ID,type);
				if(!data.contains(ID)){//判断是否已存在
					ContentValues iValue = new ContentValues();
					iValue.put("ID", ID);
					iValue.put("type", type);
					iValue.put("UID", GlobalVarUtil.account.getUID());
					CommonDao.insert(Table_Name_CollectionInfo,iValue);
					state = true;
				}
				//Toast.makeText(myContext, TABLE_USERS+"表存在，删除成功!", Toast.LENGTH_SHORT).show();
			}else{
				//Toast.makeText(myContext, TABLE_USERS+"表不存在，不用删除!", Toast.LENGTH_SHORT).show();
			}
		} catch (Exception e) {
			Log.e("BusinessDao", e.toString());
			e.printStackTrace();
		}finally{
			if(dbHelper!=null){
				dbHelper.closeDb();
			}
		}
		return state;
	}
	//清空收货地址列表数据
	public static void clear_collectdata(){
		CommonDao.deleteTableData(Table_Name_CollectionInfo);
	}
	
	/**
	 * 获取指定的收藏表数据
	 */
	public static List<String> getTheCollectionData(String ID , int type){
		DBHelper dbHelper = null;
		List<String> IDs = new ArrayList<String>();
		try {
			dbHelper = new DBHelper(ApplicationUtil.myContext);
			dbHelper.open();
			String selSql="";
			selSql = "select *  from " + Table_Name_CollectionInfo + " where ID='" + ID + "' AND UID ='"+GlobalVarUtil.account.getUID()+"' AND type ="+type+";";
			Cursor selSet = dbHelper.select(selSql);
			selSet.moveToFirst();
			while(!selSet.isAfterLast()){
				String str = selSet.getString(selSet.getColumnIndex("ID"));
				IDs.add(str);
				selSet.moveToNext();
			}
			selSet.close();
		} catch (Exception e) {
			Log.e("BusinessDao", e.toString());
			e.printStackTrace();
		}finally{
			if(dbHelper!=null){
				dbHelper.closeDb();
			}
		}
		return IDs;
	}
	
	/**
	 * 获取收藏表数据
	 */
	public static List<CollectEntity> getCollectionTableData(){
		DBHelper dbHelper = null;
		List<CollectEntity> IDs = new ArrayList();
		try {
			dbHelper = new DBHelper(ApplicationUtil.myContext);
			dbHelper.open();
			String selSql="";
			selSql = "select * from "+Table_Name_CollectionInfo +" where UID='"+GlobalVarUtil.account.getUID()+"';";
			Cursor selSet = dbHelper.select(selSql);
			selSet.moveToFirst();
			while(!selSet.isAfterLast()){
				CollectEntity map = new CollectEntity();
				String str = selSet.getString(selSet.getColumnIndex("ID"));
				int type = selSet.getInt(selSet.getColumnIndex("type"));
				map.setID(str);
				map.setType(type);
				map.setUID(GlobalVarUtil.account.getUID());
				IDs.add(map);
				selSet.moveToNext();
			}
			selSet.close();
		} catch (Exception e) {
			Log.e("BusinessDao", e.toString());
			e.printStackTrace();
		}finally{
			if(dbHelper!=null){
				dbHelper.closeDb();
			}
		}
		return IDs;
	}
	/**
	 * 删除商品信息表数据
	 * @param ID 商品ID
	 */
	public static void deleteGoodsTableData(String ID,int type){
		DBHelper dbHelper = null;
		
		try {
			dbHelper = new DBHelper(ApplicationUtil.myContext);
			dbHelper.open();
			if(dbHelper.isTableExist(Table_Name_GoodsInfo)){
				String deleteSql = "";
				if(ID == null){
					deleteSql = "delete from " + Table_Name_GoodsInfo + " where  UID = '"+GlobalVarUtil.account.getUID()+"' AND type = "+type;
				}else{
					deleteSql = "delete from " + Table_Name_GoodsInfo + " where ID = '" + ID + "' AND UID = '"+GlobalVarUtil.account.getUID()+"' AND type = "+type;
				}
				
				dbHelper.execSQL(deleteSql);
				//Toast.makeText(myContext, TABLE_USERS+"表存在，删除成功!", Toast.LENGTH_SHORT).show();
			}else{
				//Toast.makeText(myContext, TABLE_USERS+"表不存在，不用删除!", Toast.LENGTH_SHORT).show();
			}
		} catch (Exception e) {
			Log.e("BusinessDao", e.toString());
			e.printStackTrace();
		}finally{
			if(dbHelper!=null){
				dbHelper.closeDb();
			}
		}
	}
	/**
	 * 获取商品信息表中的用户所有收集商品信息
	 */
	public static List<CollectEntity> getUserGoodsData(){
		DBHelper dbHelper = null;
		List<CollectEntity> IDs = new ArrayList();
		try {
			dbHelper = new DBHelper(ApplicationUtil.myContext);
			dbHelper.open();
			String selSql="";
			selSql = "select * from "+Table_Name_GoodsInfo +" where UID='"+GlobalVarUtil.account.getUID()+"';";
			Cursor selSet = dbHelper.select(selSql);
			selSet.moveToFirst();
			while(!selSet.isAfterLast()){
				CollectEntity map = new CollectEntity();
				String str = selSet.getString(selSet.getColumnIndex("ID"));
				int type = selSet.getInt(selSet.getColumnIndex("type"));
				map.setID(str);
				map.setType(type);
				map.setUID(GlobalVarUtil.account.getUID());
				IDs.add(map);
				selSet.moveToNext();
			}
			selSet.close();
		} catch (Exception e) {
			Log.e("BusinessDao", e.toString());
			e.printStackTrace();
		}finally{
			if(dbHelper!=null){
				dbHelper.closeDb();
			}
		}
		return IDs;
	}
	/**
	 * 获取账户信息表数据
	 */
	public static List<String> getAccountInfoTableData(){
		DBHelper dbHelper = null;
		List<String> nickNames = new ArrayList();
		try {
			dbHelper = new DBHelper(ApplicationUtil.myContext);
			dbHelper.open();
			String selSql="";
			selSql = "select * from "+Table_Name_AccountInfo;
			Cursor selSet = dbHelper.select(selSql);
			selSet.moveToFirst();
			while(!selSet.isAfterLast()){
				String str = selSet.getString(selSet.getColumnIndex("nickName"));
				nickNames.add(str);
				selSet.moveToNext();
			}
			selSet.close();
		} catch (Exception e) {
			Log.e("BusinessDao", e.toString());
			e.printStackTrace();
		}finally{
			if(dbHelper!=null){
				dbHelper.closeDb();
			}
		}
		return nickNames;
	}
	/**
	 * 检索账户信息表数据
	 */
	public Boolean checkAccountInfoTableData(String nickName){
		DBHelper dbHelper = null;
//		List<String> nickNames = new ArrayList();
		boolean exist = false;
		try {
			dbHelper = new DBHelper(ApplicationUtil.myContext);
			dbHelper.open();
			String selSql="";
			selSql = "select * from "+Table_Name_AccountInfo +" where nickName = '" + nickName + "'";
			Cursor selSet = dbHelper.select(selSql);
			if(selSet!=null){
				exist = true;
			}
			selSet.close();
		} catch (Exception e) {
			Log.e("BusinessDao", e.toString());
			e.printStackTrace();
		}finally{
			if(dbHelper!=null){
				dbHelper.closeDb();
			}
		}
		return exist;
	}
	/**
	 * 插入账户信息表数据
	 * @param ID 商品ID
	 */
	public static boolean insertAccountInfoTableData(AccountInfo accountInfo){
		DBHelper dbHelper = null;
		boolean state = false;
		try {
			dbHelper = new DBHelper(ApplicationUtil.myContext);
			dbHelper.open();
			if(dbHelper.isTableExist(Table_Name_AccountInfo)){
				List<String> data = getAccountInfoTableData();
				if(!data.contains(accountInfo.getNickName())){//判断是否已存在
					ContentValues iValue = new ContentValues();
					iValue.put("nickName", accountInfo.getNickName());
					iValue.put("telephone", accountInfo.getTelephone());
					iValue.put("email", accountInfo.getEmail());
					iValue.put("isLogin", accountInfo.getIsLogin());
					
					CommonDao.insert(Table_Name_AccountInfo,iValue);
					state = true;
				}
				//Toast.makeText(myContext, TABLE_USERS+"表存在，删除成功!", Toast.LENGTH_SHORT).show();
			}else{
				//Toast.makeText(myContext, TABLE_USERS+"表不存在，不用删除!", Toast.LENGTH_SHORT).show();
			}
		} catch (Exception e) {
			Log.e("BusinessDao", e.toString());
			e.printStackTrace();
		}finally{
			if(dbHelper!=null){
				dbHelper.closeDb();
			}
		}
		return state;
	}
	/**
	 * 插入搜索历史信息表数据
	 * @param ID 商品ID
	 */
	public static boolean insertSearchHistoryTableData(SearchGoodsEntity entity){
		DBHelper dbHelper = null;
		boolean state = false;
		try {
			dbHelper = new DBHelper(ApplicationUtil.myContext);
			dbHelper.open();
			if(dbHelper.isTableExist(Table_Name_SearchHistoryInfo)){
				String keyword = entity.getKeyword();
				int type = entity.getType();
				String selSql = "select * from "+Table_Name_SearchHistoryInfo +" where type = " + type + " and keyword = '" + keyword + "'";
				Cursor selSet = dbHelper.select(selSql);
				if(selSet.getCount() == 0){
					ContentValues iValue = new ContentValues();
					iValue.put("keyword", entity.getKeyword());
					iValue.put("type", entity.getType());
					
					CommonDao.insert(Table_Name_SearchHistoryInfo,iValue);
					state = true;
				}
				//Toast.makeText(myContext, TABLE_USERS+"表存在，删除成功!", Toast.LENGTH_SHORT).show();
			}else{
				//Toast.makeText(myContext, TABLE_USERS+"表不存在，不用删除!", Toast.LENGTH_SHORT).show();
			}
		} catch (Exception e) {
			Log.e("BusinessDao", e.toString());
			e.printStackTrace();
		}finally{
			if(dbHelper!=null){
				dbHelper.closeDb();
			}
		}
		return state;
	}
	
	/**
	 * 获取历史搜索信息表数据
	 */
	public static List<SearchGoodsEntity> getSearchHistoryTableData(){
		DBHelper dbHelper = null;
		List <SearchGoodsEntity> list = new ArrayList<SearchGoodsEntity>();
		try {
			dbHelper = new DBHelper(ApplicationUtil.myContext);
			dbHelper.open();
			if(dbHelper.isTableExist(Table_Name_SearchHistoryInfo)){
				String selSql = "select * from "+Table_Name_SearchHistoryInfo ;
				Cursor selSet = dbHelper.select(selSql);
				selSet.moveToFirst();
				while(!selSet.isAfterLast()){
					SearchGoodsEntity entity = new SearchGoodsEntity();
					entity.setKeyword(selSet.getString(selSet.getColumnIndex("keyword")));
					entity.setType(selSet.getInt(selSet.getColumnIndex("type")));
					list.add(entity);
					selSet.moveToNext();
				}
				selSet.close();
				//Toast.makeText(myContext, TABLE_USERS+"表存在，删除成功!", Toast.LENGTH_SHORT).show();
			}else{
				//Toast.makeText(myContext, TABLE_USERS+"表不存在，不用删除!", Toast.LENGTH_SHORT).show();
			}
		} catch (Exception e) {
			Log.e("BusinessDao", e.toString());
			e.printStackTrace();
		}finally{
			if(dbHelper!=null){
				dbHelper.closeDb();
			}
		}
		return list;
	}
	/**
	 * 删除搜索历史信息表数据
	 * @param entity SearchGoodsEntity
	 */
	public static void deleteSearchHistoryTableData(SearchGoodsEntity entity){
		DBHelper dbHelper = null;
		
		try {
			dbHelper = new DBHelper(ApplicationUtil.myContext);
			dbHelper.open();
			if(dbHelper.isTableExist(Table_Name_SearchHistoryInfo)){
				String deleteSql = "";
				String keyword = entity.getKeyword();
				int type = entity.getType();
				deleteSql = "delete from " + Table_Name_SearchHistoryInfo + " where keyword = '" + keyword + "' and type = "+type;
				dbHelper.execSQL(deleteSql);
				//Toast.makeText(myContext, TABLE_USERS+"表存在，删除成功!", Toast.LENGTH_SHORT).show();
			}else{
				//Toast.makeText(myContext, TABLE_USERS+"表不存在，不用删除!", Toast.LENGTH_SHORT).show();
			}
		} catch (Exception e) {
			Log.e("BusinessDao", e.toString());
			e.printStackTrace();
		}finally{
			if(dbHelper!=null){
				dbHelper.closeDb();
			}
		}
	}
	/**
	 * 插入购物车信息表数据
	 * @param ID 商品ID
	 */
	public static boolean insertShoppingcartTableData(ShoppingcartInfoEntity entity){
		DBHelper dbHelper = null;
		boolean state = false;
		try {
			dbHelper = new DBHelper(ApplicationUtil.myContext);
			dbHelper.open();
			if(dbHelper.isTableExist(Table_Name_ShoppingcartInfo)){
				String ID = entity.getID();
				String UID = entity.getUID();
				String selSql = "select * from "+Table_Name_ShoppingcartInfo +" where ID = '" + ID + "'"+" and UID = '" + UID + "'";
				Cursor selSet = dbHelper.select(selSql);
				if(selSet.getCount() == 0){
					ContentValues iValue = new ContentValues();
					iValue.put("ID", entity.getID());
					iValue.put("UID", entity.getUID());
					iValue.put("FID", entity.getFID());
					iValue.put("shopsID", entity.getShopsID());
					iValue.put("type", entity.getType());
					iValue.put("name", entity.getName());
					iValue.put("intro", entity.getIntro());
					iValue.put("price", entity.getPrice());
					iValue.put("counts", entity.getCounts());
					iValue.put("freightTypeSelect", entity.getFreightTypeSelect());
					iValue.put("shopsName", entity.getShopsName());
					iValue.put("freight", entity.getFreight());
					iValue.put("freightType", entity.getFreightType());
					iValue.put("imageUrl", entity.getImageUrl());
					iValue.put("ToSelectBoolean", entity.getToSelectBoolean());
					
					CommonDao.insert(Table_Name_ShoppingcartInfo,iValue);
					state = true;
				}
				//Toast.makeText(myContext, TABLE_USERS+"表存在，删除成功!", Toast.LENGTH_SHORT).show();
			}else{
				//Toast.makeText(myContext, TABLE_USERS+"表不存在，不用删除!", Toast.LENGTH_SHORT).show();
			}
		} catch (Exception e) {
			Log.e("BusinessDao", e.toString());
			e.printStackTrace();
		}finally{
			if(dbHelper!=null){
				dbHelper.closeDb();
			}
		}
		return state;
	}
	/**
	 * 获取购物车信息表数据
	 */
	public static List<ShoppingcartInfoEntity> getShoppingcartTableData(){
		DBHelper dbHelper = null;
		List <ShoppingcartInfoEntity> list = new ArrayList<ShoppingcartInfoEntity>();
		try {
			dbHelper = new DBHelper(ApplicationUtil.myContext);
			dbHelper.open();
			if(dbHelper.isTableExist(Table_Name_ShoppingcartInfo)){
				String selSql = "select * from "+Table_Name_ShoppingcartInfo ;
				Cursor selSet = dbHelper.select(selSql);
				selSet.moveToFirst();
				while(!selSet.isAfterLast()){
					ShoppingcartInfoEntity entity = new ShoppingcartInfoEntity();
					entity.setID(selSet.getString(selSet.getColumnIndex("ID")));
					entity.setUID(selSet.getString(selSet.getColumnIndex("UID")));
					entity.setFID(selSet.getString(selSet.getColumnIndex("FID")));
					entity.setShopsID(selSet.getString(selSet.getColumnIndex("shopsID")));
					entity.setName(selSet.getString(selSet.getColumnIndex("name")));
					entity.setShopsName(selSet.getString(selSet.getColumnIndex("shopsName")));
					entity.setIntro(selSet.getString(selSet.getColumnIndex("intro")));
					entity.setPrice(selSet.getString(selSet.getColumnIndex("price")));
					entity.setFreight(selSet.getString(selSet.getColumnIndex("freight")));
					entity.setFreightType(selSet.getString(selSet.getColumnIndex("freightType")));
					entity.setImageUrl(selSet.getString(selSet.getColumnIndex("imageUrl")));
					entity.setType(selSet.getInt(selSet.getColumnIndex("type")));
					entity.setCounts(selSet.getInt(selSet.getColumnIndex("counts")));
					entity.setFreightTypeSelect(selSet.getInt(selSet.getColumnIndex("freightTypeSelect")));
					entity.setToSelectBoolean(selSet.getInt(selSet.getColumnIndex("ToSelectBoolean")));
					
					list.add(entity);
					selSet.moveToNext();
				}
				selSet.close();
				//Toast.makeText(myContext, TABLE_USERS+"表存在，删除成功!", Toast.LENGTH_SHORT).show();
			}else{
				//Toast.makeText(myContext, TABLE_USERS+"表不存在，不用删除!", Toast.LENGTH_SHORT).show();
			}
		} catch (Exception e) {
			Log.e("BusinessDao", e.toString());
			e.printStackTrace();
		}finally{
			if(dbHelper!=null){
				dbHelper.closeDb();
			}
		}
		return list;
	}
	/**
	 * 获取指定ID购物车中信息表数据
	 */
	public static ShoppingcartInfoEntity getGoodsIDShoppingcartTableData(String ID){
		DBHelper dbHelper = null;
		ShoppingcartInfoEntity entity = new ShoppingcartInfoEntity();
		try {
			dbHelper = new DBHelper(ApplicationUtil.myContext);
			dbHelper.open();
			if(dbHelper.isTableExist(Table_Name_ShoppingcartInfo)){
				String selSql = "select * from "+Table_Name_ShoppingcartInfo + " where ID = '" + ID + "'";
				Cursor selSet = dbHelper.select(selSql);
				selSet.moveToFirst();
				while(!selSet.isAfterLast()){
					entity.setID(selSet.getString(selSet.getColumnIndex("ID")));
					entity.setUID(selSet.getString(selSet.getColumnIndex("UID")));
					entity.setFID(selSet.getString(selSet.getColumnIndex("FID")));
					entity.setShopsID(selSet.getString(selSet.getColumnIndex("shopsID")));
					entity.setName(selSet.getString(selSet.getColumnIndex("name")));
					entity.setShopsName(selSet.getString(selSet.getColumnIndex("shopsName")));
					entity.setIntro(selSet.getString(selSet.getColumnIndex("intro")));
					entity.setPrice(selSet.getString(selSet.getColumnIndex("price")));
					entity.setFreight(selSet.getString(selSet.getColumnIndex("freight")));
					entity.setFreightType(selSet.getString(selSet.getColumnIndex("freightType")));
					entity.setImageUrl(selSet.getString(selSet.getColumnIndex("imageUrl")));
					entity.setType(selSet.getInt(selSet.getColumnIndex("type")));
					entity.setCounts(selSet.getInt(selSet.getColumnIndex("counts")));
					entity.setFreightTypeSelect(selSet.getInt(selSet.getColumnIndex("freightTypeSelect")));
					entity.setToSelectBoolean(selSet.getInt(selSet.getColumnIndex("ToSelectBoolean")));
					
					selSet.moveToNext();
				}
				selSet.close();
				//Toast.makeText(myContext, TABLE_USERS+"表存在，删除成功!", Toast.LENGTH_SHORT).show();
			}else{
				//Toast.makeText(myContext, TABLE_USERS+"表不存在，不用删除!", Toast.LENGTH_SHORT).show();
			}
		} catch (Exception e) {
			Log.e("BusinessDao", e.toString());
			e.printStackTrace();
		}finally{
			if(dbHelper!=null){
				dbHelper.closeDb();
			}
		}
		return entity;
	}
	/**
	 * 获取指定用户购物车中信息表数据
	 */
	public static List<ShoppingcartInfoEntity> getShoppingcartTableData(String UID){
		DBHelper dbHelper = null;
		List <ShoppingcartInfoEntity> list = new ArrayList<ShoppingcartInfoEntity>();
		try {
			dbHelper = new DBHelper(ApplicationUtil.myContext);
			dbHelper.open();
			if(dbHelper.isTableExist(Table_Name_ShoppingcartInfo)){
				String selSql = "select * from "+Table_Name_ShoppingcartInfo + " where UID = '" + UID + "'";
				Cursor selSet = dbHelper.select(selSql);
				selSet.moveToFirst();
				while(!selSet.isAfterLast()){
					ShoppingcartInfoEntity entity = new ShoppingcartInfoEntity();
					entity.setID(selSet.getString(selSet.getColumnIndex("ID")));
					entity.setUID(selSet.getString(selSet.getColumnIndex("UID")));
					entity.setFID(selSet.getString(selSet.getColumnIndex("FID")));
					entity.setShopsID(selSet.getString(selSet.getColumnIndex("shopsID")));
					entity.setName(selSet.getString(selSet.getColumnIndex("name")));
					entity.setShopsName(selSet.getString(selSet.getColumnIndex("shopsName")));
					entity.setIntro(selSet.getString(selSet.getColumnIndex("intro")));
					entity.setPrice(selSet.getString(selSet.getColumnIndex("price")));
					entity.setFreight(selSet.getString(selSet.getColumnIndex("freight")));
					entity.setFreightType(selSet.getString(selSet.getColumnIndex("freightType")));
					entity.setImageUrl(selSet.getString(selSet.getColumnIndex("imageUrl")));
					entity.setType(selSet.getInt(selSet.getColumnIndex("type")));
					entity.setCounts(selSet.getInt(selSet.getColumnIndex("counts")));
					entity.setFreightTypeSelect(selSet.getInt(selSet.getColumnIndex("freightTypeSelect")));
					entity.setToSelectBoolean(selSet.getInt(selSet.getColumnIndex("ToSelectBoolean")));
					
					list.add(entity);
					selSet.moveToNext();
				}
				selSet.close();
				//Toast.makeText(myContext, TABLE_USERS+"表存在，删除成功!", Toast.LENGTH_SHORT).show();
			}else{
				//Toast.makeText(myContext, TABLE_USERS+"表不存在，不用删除!", Toast.LENGTH_SHORT).show();
			}
		} catch (Exception e) {
			Log.e("BusinessDao", e.toString());
			e.printStackTrace();
		}finally{
			if(dbHelper!=null){
				dbHelper.closeDb();
			}
		}
		return list;
	}
	/**
	 * 删除购物车信息表数据
	 * @param ID 商品ID
	 */
	public static void deleteShoppingcartTableData(ShoppingcartInfoEntity entity){
		DBHelper dbHelper = null;
		
		try {
			dbHelper = new DBHelper(ApplicationUtil.myContext);
			dbHelper.open();
			if(dbHelper.isTableExist(Table_Name_ShoppingcartInfo)){
				String deleteSql = "";
				String ID = entity.getID();
				String UID = entity.getUID();
				deleteSql = "delete from " + Table_Name_ShoppingcartInfo + " where ID = '" + ID + "'"+" and UID = '" + UID + "'";
				dbHelper.execSQL(deleteSql);
				//Toast.makeText(myContext, TABLE_USERS+"表存在，删除成功!", Toast.LENGTH_SHORT).show();
			}else{
				//Toast.makeText(myContext, TABLE_USERS+"表不存在，不用删除!", Toast.LENGTH_SHORT).show();
			}
		} catch (Exception e) {
			Log.e("BusinessDao", e.toString());
			e.printStackTrace();
		}finally{
			if(dbHelper!=null){
				dbHelper.closeDb();
			}
		}
	}
	/**
	 * 更新购物车信息表数据
	 * @param ID 商品ID
	 */
	public static void updateShoppingcartTableData(String table ,ShoppingcartInfoEntity entity , String UID){
		DBHelper dbHelper = null;
		
		try {
			dbHelper = new DBHelper(ApplicationUtil.myContext);
			dbHelper.open();
			if(dbHelper.isTableExist(Table_Name_ShoppingcartInfo)){
				ContentValues cValue = new ContentValues();
				cValue.put("imageUrl", entity.getImageUrl());
				cValue.put("ID", entity.getID());
				cValue.put("UID", entity.getUID());
				cValue.put("FID", entity.getFID());
				cValue.put("name", entity.getName());
				cValue.put("intro", entity.getIntro());
				cValue.put("price", entity.getPrice());
				cValue.put("freight", entity.getFreight());
				cValue.put("freightType", entity.getFreightType());
				cValue.put("type", entity.getType());
				cValue.put("counts", entity.getCounts());
				cValue.put("freightTypeSelect", entity.getFreightTypeSelect());
				cValue.put("ToSelectBoolean", entity.getToSelectBoolean());
				String ID = entity.getID(); 
				dbHelper.update(Table_Name_ShoppingcartInfo, cValue, " ID = '"+ID+"' and "+" UID = '"+UID+"';");
				//Toast.makeText(myContext, TABLE_USERS+"表存在，删除成功!", Toast.LENGTH_SHORT).show();
			}else{
				//Toast.makeText(myContext, TABLE_USERS+"表不存在，不用删除!", Toast.LENGTH_SHORT).show();
			}
		} catch (Exception e) {
			Log.e("BusinessDao", e.toString());
			e.printStackTrace();
		}finally{
			if(dbHelper!=null){
				dbHelper.closeDb();
			}
		}
	}
}
