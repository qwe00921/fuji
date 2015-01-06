package com.fsti.fjdicClient.bean;

import java.io.Serializable;

/**
 * 商品单元格实体
 * @author 王久叶 
 *
 */
public class CollectEntity implements Serializable {
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
	 *1:社区商城  2:团购
	 */
	private int type;
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}

}
