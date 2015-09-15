package com.jeecg.p3.gzbargain.web.vo;

import java.io.Serializable;

public class BargainDto implements Serializable{
	private static final long serialVersionUID = -1935558318674922380L;
	/**
	 *活动id
	 */
	private String actId;
	/**
	 *分享人openid
	 */
	private String fxOpenid;
	/**
	 *分享人昵称
	 */
	private String fxNickname;
	/**
	 *访问人openid
	 */
	private String openid;
	/**
	 *访问人昵称
	 */
	private String nickname;
	
	/**
	 * 是否关注（ 用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息。）
	 */
	private String subscribe;
	public String getActId() {
		return actId;
	}
	public void setActId(String actId) {
		this.actId = actId;
	}
	public String getFxOpenid() {
		return fxOpenid;
	}
	public void setFxOpenid(String fxOpenid) {
		this.fxOpenid = fxOpenid;
	}
	public String getFxNickname() {
		return fxNickname;
	}
	public void setFxNickname(String fxNickname) {
		this.fxNickname = fxNickname;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getSubscribe() {
		return subscribe;
	}
	public void setSubscribe(String subscribe) {
		this.subscribe = subscribe;
	}
	@Override
	public String toString() {
		return "BargainDto [actId=" + actId + ", fxOpenid=" + fxOpenid
				+ ", fxNickname=" + fxNickname + ", openid=" + openid
				+ ", nickname=" + nickname + ", subscribe=" + subscribe + "]";
	}
	
}
