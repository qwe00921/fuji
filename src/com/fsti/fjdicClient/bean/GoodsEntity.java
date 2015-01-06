package com.fsti.fjdicClient.bean;

import java.io.Serializable;

/**
 * 商品单元格实体
 * 
 * @author 王久叶
 */
public class GoodsEntity implements Serializable {
    /**
     * 商家ID，团购中使用
     */
    private String shopsID;

    @Override
    public String toString() {
        return "GoodsEntity [shopsID=" + shopsID + ", ID=" + ID + ", type=" + type + ", name=" + name + ", intro=" + intro + ", discountedPrice=" + discountedPrice + ", originalCost=" + originalCost
                + ", selledCount=" + selledCount + ", freight=" + freight + ", imageUrl=" + imageUrl + ", groupBuyingCount=" + groupBuyingCount + ", isPastDue=" + isPastDue + ", FID=" + FID
                + ", goodsAttribute=" + goodsAttribute + ", hits=" + hits + "]";
    }

    public String getShopsID() {
        return shopsID;
    }

    public void setShopsID(String shopsID) {
        this.shopsID = shopsID;
    }

    /**
     * 商品ID
     */
    private String ID;
    /**
     * 1:社区商城 2:团购
     */
    private int    type;
    /**
     * 商品名称
     */
    private String name;
    /**
     * 商品简介
     */
    private String intro;
    /**
     * 商品打折价
     */
    private String discountedPrice;
    /**
     * 商品原价
     */
    private String originalCost;
    /**
     * 最近的销售数量
     */
    private int    selledCount;
    /**
     * 运费
     */
    private String freight;
    /**
     * 商品图片地址
     */
    private String imageUrl;
    /**
     * 团购人数
     */
    private int    groupBuyingCount;
    /**
     * 是否过期 0:未过期 1:过期
     */
    private int    isPastDue;
    /**
     * 商品所属分类
     */
    private String FID;

    /**
     * 判断是实体商品还是虚拟商品，0表示实体，1表示虚拟，默认值为0
     */
    private String goodsAttribute;
    /**
     * 人气：点击
     */
    private String hits;
    private long   times_long;

    public long getTimes_long() {
        return times_long;
    }

    public void setTimes_long(long times_long) {
        this.times_long = times_long;
    }

    public String getHits() {
        return hits;
    }

    public void setHits(String hits) {
        this.hits = hits;
    }

    public String getGoodsAttribute() {
        return goodsAttribute;
    }

    public void setGoodsAttribute(String goodsAttribute) {
        this.goodsAttribute = goodsAttribute;
    }

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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getDiscountedPrice() {
        return discountedPrice;
    }

    public void setDiscountedPrice(String discountedPrice) {
        this.discountedPrice = discountedPrice;
    }

    public String getOriginalCost() {
        return originalCost;
    }

    public void setOriginalCost(String originalCost) {
        this.originalCost = originalCost;
    }

    public int getSelledCount() {
        return selledCount;
    }

    public void setSelledCount(int selledCount) {
        this.selledCount = selledCount;
    }

    public String getFreight() {
        return freight;
    }

    public void setFreight(String freight) {
        this.freight = freight;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getGroupBuyingCount() {
        return groupBuyingCount;
    }

    public void setGroupBuyingCount(int groupBuyingCount) {
        this.groupBuyingCount = groupBuyingCount;
    }

    public int getIsPastDue() {
        return isPastDue;
    }

    public void setIsPastDue(int isPastDue) {
        this.isPastDue = isPastDue;
    }

}
