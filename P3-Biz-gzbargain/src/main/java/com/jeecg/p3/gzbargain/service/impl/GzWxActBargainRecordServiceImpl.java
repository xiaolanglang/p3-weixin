package com.jeecg.p3.gzbargain.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.jeecgframework.p3.core.common.exception.BargainException;
import org.jeecgframework.p3.core.common.exception.ExceptionEnum;
import org.jeecgframework.p3.core.common.utils.RandomUtils;
import org.jeecgframework.p3.core.logger.Logger;
import org.jeecgframework.p3.core.logger.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeecg.p3.gzbargain.dao.GzWxActBargainRecordDao;
import com.jeecg.p3.gzbargain.dao.GzWxActBargainRegistrationDao;
import com.jeecg.p3.gzbargain.entity.GzWxActBargain;
import com.jeecg.p3.gzbargain.entity.GzWxActBargainAwards;
import com.jeecg.p3.gzbargain.entity.GzWxActBargainRecord;
import com.jeecg.p3.gzbargain.entity.GzWxActBargainRegistration;
import com.jeecg.p3.gzbargain.service.GzWxActBargainAwardsService;
import com.jeecg.p3.gzbargain.service.GzWxActBargainRecordService;
import com.jeecg.p3.gzbargain.service.GzWxActBargainService;

@Service("gzWxActBargainRecordService")
public class GzWxActBargainRecordServiceImpl implements GzWxActBargainRecordService {
	public final static Logger LOG = LoggerFactory.getLogger(GzWxActBargainRecordServiceImpl.class);
	@Resource
	private GzWxActBargainRecordDao gzWxActBargainRecordDao;
	
	@Resource
	private GzWxActBargainRegistrationDao gzWxActBargainRegistrationDao;
	
	@Autowired
	private GzWxActBargainService gzWxActBargainService;
	
	@Autowired
	private GzWxActBargainAwardsService gzWxActBargainAwardsService;
	
	
	@Override
	public List<GzWxActBargainRecord> queryBargainRecordListByRegistrationId(
			String registrationId) {
		return gzWxActBargainRecordDao.queryBargainRecordListByRegistrationId(registrationId);
	}
	
	@Override
	public List<GzWxActBargainRecord> queryBargainRecordListByRegistrationIdAndOpenid(
			String registrationId, String openid) {
		return gzWxActBargainRecordDao.queryBargainRecordListByRegistrationIdAndOpenid(registrationId, openid);
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public void bargain(GzWxActBargainRecord gzWxActBargainRecord) {
		GzWxActBargainRegistration bargainRegistration = gzWxActBargainRegistrationDao.get(gzWxActBargainRecord.getRegistrationId());
		if(bargainRegistration.getProductNewPrice().compareTo(BigDecimal.ZERO)<=0){
			throw new BargainException(ExceptionEnum.ACT_BARGAIN_FINISH);
		}
		GzWxActBargain gzWxActBargain = gzWxActBargainService.queryWxActBargain(bargainRegistration.getActId());
		BigDecimal cutPrice = getCutPrice(gzWxActBargain.getActRule());
		Date currDate = new Date();
		if(currDate.after(gzWxActBargain.getEndTime())){
			throw new BargainException(ExceptionEnum.ACT_BARGAIN_END,"活动已结束");
		}
		//更新报名砍价信息
		gzWxActBargainRegistrationDao.updateBargainPrice(gzWxActBargainRecord.getRegistrationId(), cutPrice);
		//获取报名信息
		bargainRegistration = gzWxActBargainRegistrationDao.get(gzWxActBargainRecord.getRegistrationId());
		if(bargainRegistration.getProductNewPrice().compareTo(BigDecimal.ZERO)<0){
			bargainRegistration.setProductNewPrice(BigDecimal.ZERO);
			gzWxActBargainRegistrationDao.update(bargainRegistration);
		}
		LOG.info("update gzWxActBargainRecord after:{}", bargainRegistration);
		
		if(bargainRegistration.getProductNewPrice().compareTo(BigDecimal.ZERO)==0){
			GzWxActBargainAwards gzWxActBargainAwards = new GzWxActBargainAwards();
			Integer maxAwardsSeq = gzWxActBargainAwardsService.getMaxAwardsSeq(bargainRegistration.getActId());
			Integer nextAwardsSeq = maxAwardsSeq+1;
			//获取活动信息
			if(nextAwardsSeq>gzWxActBargain.getProductNum()){
				throw new BargainException(ExceptionEnum.ACT_BARGAIN_PRIZE_NONE,"奖品已抢光！");
			}
			gzWxActBargainAwards.setAwardsSeq(nextAwardsSeq);
			gzWxActBargainAwards.setActId(bargainRegistration.getActId());
			gzWxActBargainAwards.setOpenid(bargainRegistration.getOpenid());
			gzWxActBargainAwards.setNickname(bargainRegistration.getNickname());
			gzWxActBargainAwardsService.createAwards(gzWxActBargainAwards);
		}
		//插入砍价记录
		gzWxActBargainRecord.setId(RandomUtils.generateID());
		gzWxActBargainRecord.setCutPrice(cutPrice);
		gzWxActBargainRecord.setCurrPrice(bargainRegistration.getProductNewPrice());
		gzWxActBargainRecord.setCreateTime(new Date());
		LOG.info("insert gzWxActBargainRecord:{}", gzWxActBargainRecord);
		gzWxActBargainRecordDao.add(gzWxActBargainRecord);
	}
	
	private BigDecimal getCutPrice(String actRule){
		try {
			String [] rules = actRule.split(",");
			if(rules.length==2){
				float min=Float.valueOf(rules[0]);
				float max=Float.valueOf(rules[1]);
			    BigDecimal db = new BigDecimal(Math.random() * (max - min) + min);  
			    return db.setScale(2, BigDecimal.ROUND_HALF_UP);
			}else{
				throw new BargainException(ExceptionEnum.ACT_BARGAIN_RULE_ERROR);
			}
		} catch (Exception e) {
			throw new BargainException(ExceptionEnum.ACT_BARGAIN_RULE_ERROR);
		}
	}

	
}
