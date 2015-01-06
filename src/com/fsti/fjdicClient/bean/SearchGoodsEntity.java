package com.fsti.fjdicClient.bean;

import java.io.Serializable;

/**
 * 搜索请求实体
 * @author 王久叶 
 *
 */
public class SearchGoodsEntity implements Serializable {
	/**
	 *查询关键词
	 */
	private String keyword;
	/**
	 *查询商品类别   1:社区商城   2:团购 0:全部商品
	 */
	private int type;
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
	
}
