package com.jeecg.p3.gzbargain.entity;

import java.math.BigDecimal;
import java.util.Date;

import org.jeecgframework.p3.core.utils.persistence.Entity;

/**
 * 描述：</b>WxActBargainRegistration:砍价报名表<br>
 * @author junfeng.zhou
 * @since：2015年08月06日 18时46分36秒 星期四 
 * @version:1.0
 */
public class GzWxActBargainRegistration implements Entity<String> {
	private static final long serialVersionUID = 1L;
		/**	 *	 */	private String id;	/**	 *活动id	 */	private String actId;	/**	 *活动所属人openid	 */	private String openid;	/**	 *活动所属人昵称	 */	private String nickname;	/**	 *产品名称	 */	private String productName;	/**	 *产品价格	 */	private BigDecimal productPrice;	/**	 *砍后价格	 */	private BigDecimal productNewPrice;	/**	 *创建时间	 */	private Date createTime;
	
	/**
	 * 领奖状态0:未领奖;1:已领奖
	 */
	private String awardsStatus;
		public String getId() {	    return this.id;	}	public void setId(String id) {	    this.id=id;	}	public String getActId() {	    return this.actId;	}	public void setActId(String actId) {	    this.actId=actId;	}	public String getOpenid() {	    return this.openid;	}	public void setOpenid(String openid) {	    this.openid=openid;	}	public String getNickname() {	    return this.nickname;	}	public void setNickname(String nickname) {	    this.nickname=nickname;	}	public String getProductName() {	    return this.productName;	}	public void setProductName(String productName) {	    this.productName=productName;	}	public BigDecimal getProductPrice() {	    return this.productPrice;	}	public void setProductPrice(BigDecimal productPrice) {	    this.productPrice=productPrice;	}	public BigDecimal getProductNewPrice() {	    return this.productNewPrice;	}	public void setProductNewPrice(BigDecimal productNewPrice) {	    this.productNewPrice=productNewPrice;	}	public Date getCreateTime() {	    return this.createTime;	}	public void setCreateTime(Date createTime) {	    this.createTime=createTime;	}
	public String getAwardsStatus() {
		return awardsStatus;
	}
	public void setAwardsStatus(String awardsStatus) {
		this.awardsStatus = awardsStatus;
	}
	@Override
	public String toString() {
		return "WxActBargainRegistration [id=" + id + ", actId=" + actId
				+ ", openid=" + openid + ", nickname=" + nickname
				+ ", productName=" + productName + ", productPrice="
				+ productPrice + ", productNewPrice=" + productNewPrice
				+ ", createTime=" + createTime + ", awardsStatus="
				+ awardsStatus + "]";
	}
	
}

