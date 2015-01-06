package com.fsti.fjdicClient.bean;

import java.io.Serializable;
import java.util.List;


public class Order implements Serializable {
	//订单ID["1231","125125"]
	private String orderID;
	//订单类型
	private String orderType;
	//运费价格
	private String freightPrice;
	//运费类型
	private String freightType;
	
	private String OrderTime;
	
	private List<OrderGoodsInfoEntity> list;
	
	//代卖家付款订单是否选中
	private boolean isCheck = false;
public boolean isCheck() {
		return isCheck;
	}
	public void setCheck(boolean isCheck) {
		this.isCheck = isCheck;
	}
public String getOrderID() {
		return orderID;
	}
	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getFreightPrice() {
		return freightPrice;
	}
	public void setFreightPrice(String freightPrice) {
		this.freightPrice = freightPrice;
	}
	public String getFreightType() {
		return freightType;
	}
	public void setFreightType(String freightType) {
		this.freightType = freightType;
	}
	public String getOrderTime() {
		return OrderTime;
	}
	public void setOrderTime(String orderTime) {
		OrderTime = orderTime;
	}
	public List<OrderGoodsInfoEntity> getList() {
		return list;
	}
	public void setList(List<OrderGoodsInfoEntity> list) {
		this.list = list;
	}

	 
	
}
