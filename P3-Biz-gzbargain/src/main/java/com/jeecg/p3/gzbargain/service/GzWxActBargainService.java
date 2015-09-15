package com.jeecg.p3.gzbargain.service;

import com.jeecg.p3.gzbargain.entity.GzWxActBargain;

/**
 * 描述：</b>WxActBargainService<br>
 * @author：junfeng.zhou
 * @since：2015年08月06日 18时46分35秒 星期四 
 * @version:1.0
 */
public interface GzWxActBargainService {
	
	/**
	 * 根据活动id查询活动信息
	 * @param actId
	 * @return
	 */
	public GzWxActBargain queryWxActBargain(String actId);
	
}

