package com.fsti.fjdicClient.util;

import java.util.List;

import com.fsti.fjdicClient.bean.AccountInfo;
import com.fsti.fjdicClient.bean.CommunityGoodsCategoryEntity;

/**
 * 全局变量工具类
 * @author 金涛
 * 
 */
public class GlobalVarUtil {
	/**
	 * 初次更新
	 */
	public static String firstUpdateApp = "update";
	/**
	 * 最小包邮金额
	 */
	public static String minPurchasemoney = "-1.0";
	/**
	 * 登陆用户信息
	 */
	public static AccountInfo account = new AccountInfo();
	/**
	 * 商品分类列表
	 */
	public static List<CommunityGoodsCategoryEntity>  CommunityGoodsCategoryList;
	
	/**
	 * 编码
	 */
	public final static String ENCODING = "UTF-8";
	
	/**广告图片保存路径*/
	public final static String MAIN_ADV_IMAGE_SAVE_PATH = "main_adv_image";
	
	/**
	 * 文件下载成功
	 */
	public final static int HANDLER_FLAG_FILE_DOWNLOAD_SUCCESSED = 0x50000;
	
	/**
	 * 文件下载失败
	 */
	public final static int HANDLER_FLAG_FILE_DOWNLOAD_FAILED = 0x50001;
	
	/**
	 * 文件正在下载
	 */
	public final static int HANDLER_FLAG_FILE_DOWNLOAD_ING = 0x50002;
	
	/**
	 * 文件上传成功
	 */
	public final static int HANDLER_FLAG_FILE_UPLOAD_SUCCESSED = 0x51000;
	
	/**
	 * 文件上传失败
	 */
	public final static int HANDLER_FLAG_FILE_UPLOAD_FAILED = 0x51001;
	
	/**
	 * 文件正在上传
	 */
	public final static int HANDLER_FLAG_FILE_UPLOAD_ING = 0x51002;
}
