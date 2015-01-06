package com.fsti.fjdicClient.bean;

/**
 * 评论实体
 * @author 王久叶 
 *
 */
public class CommentEntity {
	/**
	 *评论ID
	 */
	private String ID;
	/**
	 *提交者
	 */
	private String committer;
	/**
	 * 评论日期，格式为“yyyy-MM-dd HH:mm:ss”
	 */
	private String date;
	/**
	 * 评论内容
	 */
	private String content;
	/**
	 * 评论商品ID
	 */
	private String goodsID;
	/**
	 * 评论商品类型  1:社区商城   2:团购
	 */
	private int goodsType;
	/**
	 * 商品与描述相符0~5
	 */
	private int commodityPoint;
	/**
	 * 卖家服务态度0~5
	 */
	private int servePoint;
	/**
	 * 卖家发货速度0~5
	 */
	private int sellerPoint;
	/**
	 * 物流发货速度0~5
	 */
	private int logisticsPonit;
//	/**
//	 *综合评分0~5
//	 */
//	private float ponit;
	public int getCommodityPoint() {
		return commodityPoint;
	}
	public void setCommodityPoint(int commodityPoint) {
		this.commodityPoint = commodityPoint;
	}
	public int getServePoint() {
		return servePoint;
	}
	public void setServePoint(int servePoint) {
		this.servePoint = servePoint;
	}
	public int getSellerPoint() {
		return sellerPoint;
	}
	public void setSellerPoint(int sellerPoint) {
		this.sellerPoint = sellerPoint;
	}
	public int getLogisticsPonit() {
		return logisticsPonit;
	}
	public void setLogisticsPonit(int logisticsPonit) {
		this.logisticsPonit = logisticsPonit;
	}
//	public float getPonit() {
//		return ponit;
//	}
//	public void setPonit(float ponit) {
//		this.ponit = ponit;
//	}
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getCommitter() {
		return committer;
	}
	public void setCommitter(String committer) {
		this.committer = committer;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getGoodsID() {
		return goodsID;
	}
	public void setGoodsID(String goodsID) {
		this.goodsID = goodsID;
	}
	public int getGoodsType() {
		return goodsType;
	}
	public void setGoodsType(int goodsType) {
		this.goodsType = goodsType;
	}

	
}
