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
	
	
	/**
	 * 领奖状态0:未领奖;1:已领奖
	 */
	private String awardsStatus;
	
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
