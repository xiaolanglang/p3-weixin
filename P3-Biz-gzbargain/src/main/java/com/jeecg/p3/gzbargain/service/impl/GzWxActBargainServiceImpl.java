package com.jeecg.p3.gzbargain.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jeecg.p3.gzbargain.dao.GzWxActBargainDao;
import com.jeecg.p3.gzbargain.entity.GzWxActBargain;
import com.jeecg.p3.gzbargain.service.GzWxActBargainService;

@Service("gzWxActBargainService")
public class GzWxActBargainServiceImpl implements GzWxActBargainService {
	@Resource
	private GzWxActBargainDao gzWxActBargainDao;
	
	
	@Override
	public GzWxActBargain queryWxActBargain(String actId) {
		GzWxActBargain gzWxActBargain  = gzWxActBargainDao.get(actId);
		return gzWxActBargain;
	}

	
}
