package com.fsti.fjdicClient.bean;

import java.io.Serializable;

/**
 * 个人中心，待XX的订单中的商品信息  
 * @author yyy
 *
 */

//个人中心，待XX的订单中的商品信息   

public class OrderGoodsInfoEntity implements Serializable {
 private String ID;//商品ID
 private String imageUrl;//商品图片地址
 private String name;//商品名称
 private String price;//商品价格
 private int amount;//商品数量
public String getID() {
	return ID;
}
public void setID(String iD) {
	ID = iD;
}
public String getImageUrl() {
	return imageUrl;
}
public void setImageUrl(String imageUrl) {
	this.imageUrl = imageUrl;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getPrice() {
	return price;
}
public void setPrice(String price) {
	this.price = price;
}
public int getAmount() {
	return amount;
}
public void setAmount(int amount) {
	this.amount = amount;
}
	
	
	
	
	
	
	
}
