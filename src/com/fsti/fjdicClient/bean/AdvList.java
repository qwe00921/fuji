package com.fsti.fjdicClient.bean;

import java.util.List;

/**
 * 活动信息列表
 * @author 金涛
 *
 */
public class AdvList {
	/**
	 * 更新时间
	 */
	private String update;
	
	/**
	 * 活动信息实体
	 */
	private List<AdvEntity> list;

	public String getUpdate() {
		return update;
	}

	public void setUpdate(String update) {
		this.update = update;
	}

	public List<AdvEntity> getList() {
		return list;
	}

	public void setList(List<AdvEntity> list) {
		this.list = list;
	}
	
	
}
