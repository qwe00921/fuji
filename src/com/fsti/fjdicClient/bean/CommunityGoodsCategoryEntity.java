package com.fsti.fjdicClient.bean;

/**
 * 社区商品分类信息
 * @author 王久叶 
 *
 */
public class CommunityGoodsCategoryEntity {
	/**
	 * 商品类别ID
	 */
	private String ID;
	/**
	 * 1:一级类别  2:二级类别   3:三级类别
	 */
	private int level;
	/**
	 * 商品类别名称
	 */
	private String name;
	
	/**
	 * 上级类别ID
	 */
	private String parentID;
	
	/**
	 * 排序位置
	 */
	private int orderPosition;
	
	/**
	 * 分类图标下载地址
	 */
	//private String imgUrl;
	
	/**
	 * 更新时间
	 */
	private String updatetime;
	/**
	 * 该类的商品数量
	 */
	private int goodsCount;

	public CommunityGoodsCategoryEntity(){}
	public CommunityGoodsCategoryEntity(String ID,int level,String name,
			String parentID,int orderPosition,String updatetime,int goodsCount){
		this.goodsCount = goodsCount;
		this.ID = ID;
		this.level = level;
		this.name = name ;
		this.orderPosition = orderPosition;
		this.parentID = parentID;
		this.updatetime = updatetime;
	}
	public int getGoodsCount() {
		return goodsCount;
	}

	public void setGoodsCount(int goodsCount) {
		this.goodsCount = goodsCount;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParentID() {
		return parentID;
	}

	public void setParentID(String parentID) {
		this.parentID = parentID;
	}


//	public String getImgUrl() {
//		return imgUrl;
//	}
//
//	public void setImgUrl(String imgUrl) {
//		this.imgUrl = imgUrl;
//	}

	public String getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}

	public int getOrderPosition() {
		return orderPosition;
	}

	public void setOrderPosition(int orderPosition) {
		this.orderPosition = orderPosition;
	}

}
