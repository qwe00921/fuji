package com.fsti.fjdicClient.bean;

public class orderInfoEntity {
	/**
	 *商品ID
	 */
	private String ID;

	/**
	 *商品名称
	 */
	private String name;
	/**
	 *商品数量
	 */
	private int counts;
	/**
	 *商品价格
	 */
	private String price;
	/**
	 *商品类别ID
	 */
	private String FID;
	
	
	
	public String getFID() {
		return FID;
	}
	public void setFID(String fID) {
		FID = fID;
	}
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getCounts() {
		return counts;
	}
	public void setCounts(int counts) {
		this.counts = counts;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	

}
