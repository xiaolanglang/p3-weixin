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
	
	/**
	 *活动规则
	 */
	private String actRule;
	/**
	 *产品剩余数量
	 */
	private Integer productRemainNum;
		return actRule;
	}
	public void setActRule(String actRule) {
		this.actRule = actRule;
	}
	public String getActContent() {
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
