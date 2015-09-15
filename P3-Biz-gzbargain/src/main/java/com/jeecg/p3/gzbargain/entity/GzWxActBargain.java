package com.jeecg.p3.gzbargain.entity;

import java.math.BigDecimal;
import java.util.Date;

import org.jeecgframework.p3.core.utils.persistence.Entity;

/**
 * 描述：</b>WxActBargain:砍价活动表<br>
 * @author junfeng.zhou
 * @since：2015年08月06日 18时46分35秒 星期四 
 * @version:1.0
 */
public class GzWxActBargain implements Entity<String> {
	private static final long serialVersionUID = 1L;
		/**	 *ID	 */	private String id;	/**	 *砍价活动名称	 */	private String actName;	/**	 *活动详情	 */	private String actDetail;
	/**
	 *活动规则
	 */
	private String actRule;	/**	 *活动内容	 */	private String actContent;	/**	 *活动开始时间	 */	private Date begainTime;	/**	 * 活动结束时间	 */	private Date endTime;	/**	 *产品数量	 */	private Integer productNum;	/**	 *产品单位	 */	private String productUnit;	/**	 *产品名称	 */	private String productName;	/**	 *产品价格	 */	private BigDecimal productPrice;	/**	 *是否关注用户参与	 */	private String foucsUserCanJoin;	/**	 *展示比例	 */	private Integer showRate;	/**	 *创建时间	 */	private Date createTime;
	/**
	 *产品剩余数量
	 */
	private Integer productRemainNum;	public String getId() {	    return this.id;	}	public void setId(String id) {	    this.id=id;	}	public String getActName() {	    return this.actName;	}	public void setActName(String actName) {	    this.actName=actName;	}	public String getActDetail() {	    return this.actDetail;	}	public void setActDetail(String actDetail) {	    this.actDetail=actDetail;	}	public String getActRule() {
		return actRule;
	}
	public void setActRule(String actRule) {
		this.actRule = actRule;
	}
	public String getActContent() {	    return this.actContent;	}	public void setActContent(String actContent) {	    this.actContent=actContent;	}	public Date getBegainTime() {	    return this.begainTime;	}	public void setBegainTime(Date begainTime) {	    this.begainTime=begainTime;	}	public Date getEndTime() {	    return this.endTime;	}	public void setEndTime(Date endTime) {	    this.endTime=endTime;	}	public Integer getProductNum() {	    return this.productNum;	}	public void setProductNum(Integer productNum) {	    this.productNum=productNum;	}	public String getProductUnit() {	    return this.productUnit;	}	public void setProductUnit(String productUnit) {	    this.productUnit=productUnit;	}	public String getProductName() {	    return this.productName;	}	public void setProductName(String productName) {	    this.productName=productName;	}	public BigDecimal getProductPrice() {	    return this.productPrice;	}	public void setProductPrice(BigDecimal productPrice) {	    this.productPrice=productPrice;	}	public String getFoucsUserCanJoin() {	    return this.foucsUserCanJoin;	}	public void setFoucsUserCanJoin(String foucsUserCanJoin) {	    this.foucsUserCanJoin=foucsUserCanJoin;	}	public Integer getShowRate() {	    return this.showRate;	}	public void setShowRate(Integer showRate) {	    this.showRate=showRate;	}	public Date getCreateTime() {	    return this.createTime;	}	public void setCreateTime(Date createTime) {	    this.createTime=createTime;	}
	public Integer getProductRemainNum() {
		return productRemainNum;
	}
	public void setProductRemainNum(Integer productRemainNum) {
		this.productRemainNum = productRemainNum;
	}
	@Override
	public String toString() {
		return "WxActBargain [id=" + id + ", actName=" + actName
				+ ", actDetail=" + actDetail + ", actRule=" + actRule
				+ ", actContent=" + actContent + ", begainTime=" + begainTime
				+ ", endTime=" + endTime + ", productNum=" + productNum
				+ ", productUnit=" + productUnit + ", productName="
				+ productName + ", productPrice=" + productPrice
				+ ", foucsUserCanJoin=" + foucsUserCanJoin + ", showRate="
				+ showRate + ", createTime=" + createTime
				+ ", productRemainNum=" + productRemainNum + "]";
	}
	
	
}

