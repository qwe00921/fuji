package com.fsti.fjdicClient.bean;

public class CommodityList {
	private int orderType;
	private int perPage;	
	private int index; //1:人气；2：新品；3：价格；4：销量
//	private String commodityListUpdate;
	private String ID;//所属类别ID
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public int getOrderType() {
		return orderType;
	}
	public void setOrderType(int orderType) {
		this.orderType = orderType;
	}
	public int getPerPage() {
		return perPage;
	}
	public void setPerPage(int perPage) {
		this.perPage = perPage;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
//	public String getCommodityListUpdate() {
//		return commodityListUpdate;
//	}
//	public void setCommodityListUpdate(String commodityListUpdate) {
//		this.commodityListUpdate = commodityListUpdate;
//	}
}
