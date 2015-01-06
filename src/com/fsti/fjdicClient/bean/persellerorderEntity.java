package com.fsti.fjdicClient.bean;

import java.util.List;

public class persellerorderEntity {
	private String remarks; 
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	private String freight;
	private String shopsID;
	private List<orderInfoEntity> list;
	public String getFreight() {
		return freight;
	}
	public void setFreight(String freight) {
		this.freight = freight;
	}
	public String getShopsID() {
		return shopsID;
	}
	public void setShopsID(String shopsID) {
		this.shopsID = shopsID;
	}
	public List<orderInfoEntity> getList() {
		return list;
	}
	public void setList(List<orderInfoEntity> list) {
		this.list = list;
	}
	
	
}
