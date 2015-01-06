package com.fsti.fjdicClient.bean;

import java.util.List;

/**
 * 社区商品更新分类
 * @author 王久叶 
 *
 */
public class CommunityGoodsUpdateCategoryList {
	/**
	 * 更新时间，以商品分类最新的更新时间为准
	 */
	private String updatetime;
	/**
	 * 社区商品分类信息,如果为空，返回空集合list[]
	 */
	private List<CommunityGoodsCategoryEntity> list;
	public String getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}
//	public String getUpdate() {
//		return updatetime;
//	}
//	public void setUpdate(String update) {
//		this.updatetime = update;
//	}
	public List<CommunityGoodsCategoryEntity> getList() {
		return list;
	}
	public void setList(List<CommunityGoodsCategoryEntity> list) {
		this.list = list;
	}

}
