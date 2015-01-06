/************************************************************
 *  文件名称	：BaseDomain.java<p>
 *
 *  创建时间	：2014年8月4日 上午10:34:50 
 *  当前版本号：v1.0
 ************************************************************/
package com.fsti.fjdicClient.bean;

import java.io.Serializable;
import java.util.Date;

/************************************************************
 * 内容摘要 ：
 * <p>
 * 作者 ：Q 创建时间 ：2014年8月4日 上午10:34:50 当前版本号：v1.0 历史记录 : 日期 : 2014年8月4日 上午10:34:50 修改人： 描述 :
 ************************************************************/

public class BaseDomain implements Serializable {

    /**
     * ID
     */
    private Long    id;
    /**
     * 名称
     */
    private String  name;
    /**
     * 是否启用
     */
    private Boolean usable;
    /**
     * 创建时间
     */
    private Date    createTime;

    /**
     * 查询第几页
     */
    private Integer page;
    /**
     * 要查询几条数据
     */
    private Integer rows;
    /**
     * 总数
     */
    private Integer total;

    public Integer getTotal() {
        if (total == null)
            return 0;
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getUsable() {
        return usable;
    }

    public void setUsable(Boolean usable) {
        this.usable = usable;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

}
