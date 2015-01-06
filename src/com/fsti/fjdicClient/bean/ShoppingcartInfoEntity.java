package com.fsti.fjdicClient.bean;

import java.io.Serializable;

/**
 * 商品单元格实体
 * @author 王久叶 
 *
 */
public class ShoppingcartInfoEntity implements Serializable {
	/**
	 *用户ID
	 */
	private String UID;
	public String getUID() {
		return UID;
	}
	public void setUID(String uID) {
		UID = uID;
	}
	/**
	 *商品ID
	 */
	private String ID;
	/**
	 * 商家ID
	 */
	private String shopsID;
	/**
	 * 商家名称
	 */
	private String shopsName;
	/**
	 *1:社区商城  2:团购
	 */
	private int type;
	/**
	 * 商品名称
	 */
	private String name;
	/**
	 * 商品简介
	 */
	private String intro;
	/**	
	 *购买数量
	 */
	private int Counts;
	/**	
	 *选中第几个运送类型
	 */
	private int freightTypeSelect = 0;//默认第一个
	/**	
	 *商品分类ID
	 */
	private String FID;
	public String getFID() {
		return FID;
	}
	public void setFID(String fID) {
		FID = fID;
	}
	public int getFreightTypeSelect() {
		return freightTypeSelect;
	}
	public void setFreightTypeSelect(int freightTypeSelect) {
		this.freightTypeSelect = freightTypeSelect;
	}
	/**
	 * 运费
	 */
	private String freight;
	/**
	 * 运费类型
	 */
	private String freightType;
	public String getFreightType() {
		return freightType;
	}
	public void setFreightType(String freightType) {
		this.freightType = freightType;
	}
	/**
	 *商品图片地址
	 */
	private String imageUrl;
	/**
	 *商品价格
	 */
	private String price;
	
	/**
	 *购物车中选商品，是否选中  true:选中  false:未选中
	 */
	private boolean isSelect = true;
	/**
	 *购物车中选商品，是否选中  true:选中  false:未选中
	 */
	private int ToSelectBoolean = 0;
	
	public int getToSelectBoolean() {
		return ToSelectBoolean;
	}
	public void setToSelectBoolean(int toSelectBoolean) {
		ToSelectBoolean = toSelectBoolean;
	}
	public boolean isSelect() {
		return isSelect;
	}
	public void setSelect(boolean isSelect) {
		this.isSelect = isSelect;
	}
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getShopsID() {
		return shopsID;
	}
	public void setShopsID(String shopsID) {
		this.shopsID = shopsID;
	}
	public String getShopsName() {
		return shopsName;
	}
	public void setShopsName(String shopsName) {
		this.shopsName = shopsName;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIntro() {
		return intro;
	}
	public void setIntro(String intro) {
		this.intro = intro;
	}
	public int getCounts() {
		return Counts;
	}
	public void setCounts(int counts) {
		Counts = counts;
	}
	public String getFreight() {
		return freight;
	}
	public void setFreight(String freight) {
		this.freight = freight;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	
	

}
