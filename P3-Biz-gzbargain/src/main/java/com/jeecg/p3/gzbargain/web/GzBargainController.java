package com.jeecg.p3.gzbargain.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.VelocityContext;
import org.jeecgframework.p3.core.common.exception.BargainException;
import org.jeecgframework.p3.core.common.exception.ExceptionEnum;
import org.jeecgframework.p3.core.common.utils.AjaxJson;
import org.jeecgframework.p3.core.common.utils.DateUtil;
import org.jeecgframework.p3.core.common.utils.RandomUtils;
import org.jeecgframework.p3.core.logger.Logger;
import org.jeecgframework.p3.core.logger.LoggerFactory;
import org.jeecgframework.p3.core.util.WeiXinHttpUtil;
import org.jeecgframework.p3.core.util.plugin.ViewVelocity;
import org.jeecgframework.p3.core.utils.common.StringUtils;
import org.jeecgframework.p3.core.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jeecg.p3.gzbargain.entity.GzWxActBargain;
import com.jeecg.p3.gzbargain.entity.GzWxActBargainAwards;
import com.jeecg.p3.gzbargain.entity.GzWxActBargainRecord;
import com.jeecg.p3.gzbargain.entity.GzWxActBargainRegistration;
import com.jeecg.p3.gzbargain.service.GzWxActBargainAwardsService;
import com.jeecg.p3.gzbargain.service.GzWxActBargainRecordService;
import com.jeecg.p3.gzbargain.service.GzWxActBargainRegistrationService;
import com.jeecg.p3.gzbargain.service.GzWxActBargainService;
import com.jeecg.p3.gzbargain.web.vo.BargainDto;

 /**
 * 描述：砍价活动
 * @author junfeng.zhou
 * @since：2015年08月06日 18时46分35秒 星期四 
 * @version:1.0
 */
@Controller
@RequestMapping("/gzbargain")
public class GzBargainController extends BaseController{
	
	  public final static Logger LOG = LoggerFactory.getLogger(GzBargainController.class);
	  @Autowired
	  private GzWxActBargainService gzWxActBargainService;
	  @Autowired
	  private GzWxActBargainRecordService gzWxActBargainRecordService;
	  @Autowired
	  private GzWxActBargainAwardsService gzWxActBargainAwardsService;
	  @Autowired
	  private GzWxActBargainRegistrationService gzWxActBargainRegistrationService;
	  
	 /**
	  * 跳转到活动首页
	  * @return
	 * @throws Exception 
	  */
	 @RequestMapping(value = "/toIndex",method ={RequestMethod.GET, RequestMethod.POST})
	 public void toIndex(@ModelAttribute BargainDto bargainDto,HttpServletRequest request,HttpServletResponse response,ModelMap model) throws Exception{
		 LOG.info(request, "toIndex parameter BargainDto={}.", new Object[]{bargainDto});
		 
		 //update-begin-----author:scott---------date:21050809------for:解码昵称------------------
		 if(bargainDto.getFxOpenid()!=null){
			 bargainDto.setFxNickname(WeiXinHttpUtil.getNickName(bargainDto.getFxOpenid()));
		 }
		 if(bargainDto.getOpenid()!=null){
			 bargainDto.setNickname(WeiXinHttpUtil.getNickName(bargainDto.getOpenid()));
		 }
		 //update-begin-----author:scott---------date:21050809------for:解码昵称------------------
		 VelocityContext velocityContext = new VelocityContext();
//		 ModelAndView mav = new ModelAndView("gzbargain/index");
		 String viewName = "gzbargain/vm/index.vm";
		 try {
			 //参数验证
			 validateBargainDtoParam(bargainDto);
			 //获取活动信息
			 GzWxActBargain gzWxActBargain = gzWxActBargainService.queryWxActBargain(bargainDto.getActId());
			 if(gzWxActBargain==null){
				 throw new BargainException(ExceptionEnum.DATA_NOT_EXIST_ERROR,"活动不存在");
			 }
//			 mav.addObject("bargain", gzWxActBargain);
			 velocityContext.put("bargain", gzWxActBargain);
			 //有效期内可参与  
			 Date currDate = new Date();
			 if(currDate.before(gzWxActBargain.getBegainTime())){
				  String begainTime = DateUtil.convertToShowTime(gzWxActBargain.getBegainTime());
				 throw new BargainException(ExceptionEnum.ACT_BARGAIN_NO_START,"活动未开始,开始时间为"+begainTime+",请耐心等待！");
			 }
			 if(currDate.after(gzWxActBargain.getEndTime())){
//				 mav.addObject("actStatus", "0");//活动结束
				 velocityContext.put("actStatus", "0");//活动结束
			 }else{
//				 mav.addObject("actStatus", "1");
				 velocityContext.put("actStatus", "1");
			 }
			 //获取奖品邮寄时间
			 Date postTime = DateUtil.addDays(gzWxActBargain.getEndTime(), 1);
			 String postTimeStr = DateUtil.date2Str(postTime, "M月d日");
//			 mav.addObject("postTimeStr", postTimeStr);
			 velocityContext.put("postTimeStr", postTimeStr);
			 //砍价规则
			 String actRuleMin = "0";
			 String actRuleMax = "0";
			 if(StringUtils.isNotEmpty(gzWxActBargain.getActRule())){
				 String[] rules = gzWxActBargain.getActRule().split(",");
				 if(rules.length==2){
					 actRuleMin = rules[0];
					 actRuleMax = rules[1];
				 }
			 }
//			 mav.addObject("actRuleMin", actRuleMin);
//			 mav.addObject("actRuleMax", actRuleMax);
			 velocityContext.put("actRuleMin", actRuleMin);
			 velocityContext.put("actRuleMax", actRuleMax);
			 
			 List<GzWxActBargainRecord> bargainRecordList = new ArrayList<GzWxActBargainRecord>();
			 //判断是否是分享活动
			 if(isShareAct(bargainDto)){
				 if(bargainDto.getFxOpenid().equals(bargainDto.getOpenid())){
//					 if("0".equals(bargainDto.getSubscribe())){
//						 throw new BargainException(ExceptionEnum.ACT_BARGAIN_NO_FOCUS,"非关注用户");
//					 }
//					 mav.setViewName("gzbargain/index");
					 viewName = "gzbargain/vm/index.vm";
				 }else{
//					 mav.setViewName("gzbargain/fxindex");
					 viewName = "gzbargain/vm/fxindex.vm";
				 }
				//根据分享人openid查询分享人的报名信息
				 GzWxActBargainRegistration gzWxActBargainRegistration =  gzWxActBargainRegistrationService.queryRegistrationByOpenidAndActId(bargainDto.getFxOpenid(), bargainDto.getActId());
				 if(gzWxActBargainRegistration==null){
					 throw new BargainException(ExceptionEnum.DATA_NOT_EXIST_ERROR,"活动无效");
				 }
//				 mav.addObject("bargainRegistration", gzWxActBargainRegistration);
				 velocityContext.put("bargainRegistration", gzWxActBargainRegistration);
				 //查询砍价记录
				 bargainRecordList =  gzWxActBargainRecordService.queryBargainRecordListByRegistrationId(gzWxActBargainRegistration.getId());
			 }else{
				 if("0".equals(bargainDto.getSubscribe())){
					 throw new BargainException(ExceptionEnum.ACT_BARGAIN_NO_FOCUS,"非关注用户");
				 }
				 //根据访问人openid查询访问人的报名信息 
				 GzWxActBargainRegistration gzWxActBargainRegistration =  gzWxActBargainRegistrationService.queryRegistrationByOpenidAndActId(bargainDto.getOpenid(), bargainDto.getActId());
				 if(gzWxActBargainRegistration==null){
					 gzWxActBargainRegistration = new GzWxActBargainRegistration();
					 gzWxActBargainRegistration.setId(RandomUtils.generateID());
					 gzWxActBargainRegistration.setActId(bargainDto.getActId());
					 gzWxActBargainRegistration.setOpenid(bargainDto.getOpenid());
					 gzWxActBargainRegistration.setNickname(bargainDto.getNickname());
					 gzWxActBargainRegistration.setProductName(gzWxActBargain.getProductName());
					 gzWxActBargainRegistration.setProductNewPrice(gzWxActBargain.getProductPrice());
					 gzWxActBargainRegistration.setProductPrice(gzWxActBargain.getProductPrice());
					 gzWxActBargainRegistration.setCreateTime(new Date());
					 gzWxActBargainRegistrationService.add(gzWxActBargainRegistration);
				 }else{
					//查询砍价记录
					bargainRecordList =  gzWxActBargainRecordService.queryBargainRecordListByRegistrationId(gzWxActBargainRegistration.getId());
				 }
//				 mav.addObject("bargainRegistration", gzWxActBargainRegistration);
				 velocityContext.put("bargainRegistration", gzWxActBargainRegistration);
			 }
//			 mav.addObject("recordListCount", bargainRecordList==null?0:bargainRecordList.size());
//			 mav.addObject("recordList", bargainRecordList);
//			 mav.addObject("bargainDto", bargainDto);
			 velocityContext.put("recordListCount", bargainRecordList==null?0:bargainRecordList.size());
			 velocityContext.put("recordList", bargainRecordList);
			 velocityContext.put("bargainDto", bargainDto);
			 
			 //update-begin-----author:scott---------date:21050809------for:获取分享signature------------------
			 String url = request.getRequestURL() + "?" + request.getQueryString();//.replace("&", "@");
			 if(url.indexOf("#")!=-1){
				 url = url.substring(0,url.indexOf("#"));
			 }
			 
			 System.out.println("--------------当前访问PageUrl---------------："+url);
//			 mav.addObject("nonceStr", WeiXinHttpUtil.nonceStr);
//			 mav.addObject("timestamp", WeiXinHttpUtil.timestamp);
//			 mav.addObject("hdUrl", hdUrl);
//			 mav.addObject("appId", WeiXinHttpUtil.appId);
//			 mav.addObject("signature", WeiXinHttpUtil.getSignature(url));
			 velocityContext.put("nonceStr", WeiXinHttpUtil.nonceStr);
			 velocityContext.put("timestamp", WeiXinHttpUtil.timestamp);
			 velocityContext.put("hdUrl", WeiXinHttpUtil.getLocalHdUrl("gzbargain"));
			 velocityContext.put("appId", WeiXinHttpUtil.appId);
			 velocityContext.put("signature", WeiXinHttpUtil.getSignature(url));
			//update-end-----author:scott---------date:21050809------for:获取分享signature------------------
			 
			 
		}catch (BargainException e) {
			LOG.error("toIndex error:{}", e.getMessage());
//			mav.setViewName("gzbargain/error");
			viewName = "gzbargain/vm/error.vm";
//			mav.addObject("errCode", e.getDefineCode());
//			mav.addObject("errMsg", e.getMessage());
			velocityContext.put("errCode", e.getDefineCode());
			velocityContext.put("errMsg", e.getMessage());
		} catch (Exception e) {
			 LOG.error("toIndex error:{}", e);
//			 mav.setViewName("gzbargain/error");
			 viewName = "gzbargain/vm/error.vm";
//			 mav.addObject("errCode", ExceptionEnum.SYS_ERROR.getErrCode());
//			 mav.addObject("errMsg", ExceptionEnum.SYS_ERROR.getErrChineseMsg());
			 velocityContext.put("errCode", ExceptionEnum.SYS_ERROR.getErrCode());
			 velocityContext.put("errMsg", ExceptionEnum.SYS_ERROR.getErrChineseMsg());
		} 
//		return mav;
		ViewVelocity.view(response,viewName,velocityContext);
	 }
  
	 private void validateBargainDtoParam(BargainDto bargainDto){
		 if(StringUtils.isEmpty(bargainDto.getActId())){
			 throw new BargainException(ExceptionEnum.ARGUMENT_ERROR,"活动ID不能为空");
		 }
		 if(StringUtils.isEmpty(bargainDto.getOpenid())){
			 throw new BargainException(ExceptionEnum.ARGUMENT_ERROR,"参与人openid不能为空");
		 }
//		 if(StringUtils.isEmpty(bargainDto.getNickname())){
//			 throw new BargainException(ExceptionEnum.ARGUMENT_ERROR,"参与人昵称不能为空");
//		 }
		 if(StringUtils.isEmpty(bargainDto.getSubscribe())){
			 throw new BargainException(ExceptionEnum.ARGUMENT_ERROR,"关注状态不能为空");
		 }
	 }
	 
	 private boolean isShareAct(BargainDto bargainDto){
		 boolean flag = false;
		 if(StringUtils.isNotEmpty(bargainDto.getFxOpenid())){
			 flag = true;
		 }
		 return flag;
	 }
 
	/**
	 * 去砍价
	 * @return
	 */
	@RequestMapping(value = "/goBargain",method ={RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public AjaxJson goBargain(@ModelAttribute GzWxActBargainRecord gzWxActBargainRecord,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		LOG.info(request, "goBargain parameter gzWxActBargainRecord={}.", new Object[]{gzWxActBargainRecord});
		try {
			//参数验证
			validateGoBargainRecordParam(gzWxActBargainRecord);
			//判断是否已经砍价
			List<GzWxActBargainRecord> bargainRecordList = gzWxActBargainRecordService.queryBargainRecordListByRegistrationIdAndOpenid(gzWxActBargainRecord.getRegistrationId(), gzWxActBargainRecord.getOpenid());
			if(bargainRecordList!=null&&bargainRecordList.size()>0){
				throw new BargainException(ExceptionEnum.DATA_EXIST_ERROR,"您已经砍过价了，不能再次砍价。");
			}
		} catch (BargainException e) {
			j.setSuccess(false);
			j.setMsg(e.getMessage());
			LOG.error("gzbargain error:{}", e.getMessage());
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("砍价异常!");
			LOG.error("gzbargain error:{}", e.getMessage());
		}
		return j;
	}
	
	private void validateGoBargainRecordParam(GzWxActBargainRecord gzWxActBargainRecord){
		 if(StringUtils.isEmpty(gzWxActBargainRecord.getRegistrationId())){
			 throw new BargainException(ExceptionEnum.ARGUMENT_ERROR,"报名ID不能为空");
		 }
		 if(StringUtils.isEmpty(gzWxActBargainRecord.getOpenid())){
			 throw new BargainException(ExceptionEnum.ARGUMENT_ERROR,"砍价人openid不能为空");
		 }
	 }
 
 	/**
	 * 砍价
	 * @return
	 */
	@RequestMapping(value = "/bargain",method ={RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public AjaxJson bargain(@ModelAttribute GzWxActBargainRecord gzWxActBargainRecord,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		LOG.info(request, "bargain parameter gzWxActBargainRecord={}.", new Object[]{gzWxActBargainRecord});
		try {
			//参数验证
			validateBargainRecordParam(gzWxActBargainRecord);
			//验证码验证
			if (!gzWxActBargainRecord.getRandCode().equalsIgnoreCase(String.valueOf(request.getSession().getAttribute("randCode")))){
				throw new BargainException(ExceptionEnum.VALIDATE_RANDCODE_ERROR,"验证码输入错误");
			}
			//判断是否已经砍价
			List<GzWxActBargainRecord> bargainRecordList = gzWxActBargainRecordService.queryBargainRecordListByRegistrationIdAndOpenid(gzWxActBargainRecord.getRegistrationId(), gzWxActBargainRecord.getOpenid());
			if(bargainRecordList!=null&&bargainRecordList.size()>0){
				throw new BargainException(ExceptionEnum.DATA_EXIST_ERROR,"您已经砍过价了，不能再次砍价。");
			}
			gzWxActBargainRecordService.bargain(gzWxActBargainRecord);
			j.setObj(gzWxActBargainRecord);
		} catch (BargainException e) {
			j.setSuccess(false);
			j.setMsg(e.getMessage());
			LOG.error("bargain error:{}", e.getMessage());
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("砍价失败!");
			LOG.error("bargain error:{}", e.getMessage());
		}
		return j;
	}
	
	private void validateBargainRecordParam(GzWxActBargainRecord gzWxActBargainRecord){
		 if(StringUtils.isEmpty(gzWxActBargainRecord.getRegistrationId())){
			 throw new BargainException(ExceptionEnum.ARGUMENT_ERROR,"报名ID不能为空");
		 }
		 if(StringUtils.isEmpty(gzWxActBargainRecord.getOpenid())){
			 throw new BargainException(ExceptionEnum.ARGUMENT_ERROR,"砍价人openid不能为空");
		 }
//		 if(StringUtils.isEmpty(gzWxActBargainRecord.getNickname())){
//			 throw new BargainException(ExceptionEnum.ARGUMENT_ERROR,"砍价人昵称不能为空");
//		 }
		 if(StringUtils.isEmpty(gzWxActBargainRecord.getSubscribe())){
			 throw new BargainException(ExceptionEnum.ARGUMENT_ERROR,"关注状态不能为空");
		 }
		 if("0".equals(gzWxActBargainRecord.getSubscribe())){
			 throw new BargainException(ExceptionEnum.ACT_BARGAIN_NO_FOCUS,"砍价人非关注用户");
		 }
		 if(StringUtils.isEmpty(gzWxActBargainRecord.getRandCode())){
			 throw new BargainException(ExceptionEnum.ARGUMENT_ERROR,"验证码不能为空");
		 }
	 }
	 
	/**
	 * 去兑奖
	 * @return
	 */
	@RequestMapping(value = "/goReceivePrize",method ={RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public AjaxJson goReceivePrize(@ModelAttribute GzWxActBargainAwards gzWxActBargainAwards,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		LOG.info(request, "goReceivePrize parameter gzWxActBargainAwards={}.", new Object[]{gzWxActBargainAwards});
		try {
			//参数验证
			validateGoReceivePrizeParam(gzWxActBargainAwards);
			//判断是否已领取奖品
			List<GzWxActBargainAwards> bargainAwardsList = gzWxActBargainAwardsService.queryBargainAwardsByActIdAndOpenid(gzWxActBargainAwards.getActId(), gzWxActBargainAwards.getOpenid());
			if(bargainAwardsList!=null&&bargainAwardsList.size()>0){
				gzWxActBargainAwards = bargainAwardsList.get(0);
				j.setObj(gzWxActBargainAwards);
				return j;
			}else{
				throw new BargainException(ExceptionEnum.ACT_BARGAIN_PRIZE_NO_GET,"未中奖");
			}
		}catch (BargainException e) {
			j.setSuccess(false);
			j.setMsg(e.getMessage());
			LOG.error("goReceivePrize error:{}", e.getMessage());
		}catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("奖品领取失败！");
			LOG.error("goReceivePrize error:{}", e.getMessage());
		}
		return j;
	}
	
	private void validateGoReceivePrizeParam(GzWxActBargainAwards gzWxActBargainAwards){
		if(StringUtils.isEmpty(gzWxActBargainAwards.getActId())){
			 throw new BargainException(ExceptionEnum.ARGUMENT_ERROR,"活动ID不能为空");
		 }
		 if(StringUtils.isEmpty(gzWxActBargainAwards.getOpenid())){
			 throw new BargainException(ExceptionEnum.ARGUMENT_ERROR,"兑奖人openid不能为空");
		 }
//		 if(StringUtils.isEmpty(gzWxActBargainAwards.getNickname())){
//			 throw new BargainException(ExceptionEnum.ARGUMENT_ERROR,"兑奖人昵称不能为空");
//		 }
		 if(StringUtils.isEmpty(gzWxActBargainAwards.getSubscribe())){
			 throw new BargainException(ExceptionEnum.ARGUMENT_ERROR,"关注状态不能为空");
		 }
		 if("0".equals(gzWxActBargainAwards.getSubscribe())){
			 throw new BargainException(ExceptionEnum.ACT_BARGAIN_NO_FOCUS,"兑奖人非关注用户");
		 }
	 }
	
	/**
	 * 兑奖
	 * @return
	 */
	@RequestMapping(value = "/receivePrize",method ={RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public AjaxJson receivePrize(@ModelAttribute GzWxActBargainAwards gzWxActBargainAwards,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		LOG.info(request, "receivePrize parameter gzWxActBargainAwards={}.", new Object[]{gzWxActBargainAwards});
		try {
			//参数验证
			validateReceivePrizeParam(gzWxActBargainAwards);
			//获取奖品邮寄时间
			GzWxActBargain gzWxActBargain = gzWxActBargainService.queryWxActBargain(gzWxActBargainAwards.getActId());
			Date postTime = DateUtil.addDays(gzWxActBargain.getEndTime(), 1);
			String postTimeStr = DateUtil.date2Str(postTime, "M月d日");
			j.setObj(postTimeStr);
			gzWxActBargainAwardsService.updateAwards(gzWxActBargainAwards);
		} catch (BargainException e) {
			j.setSuccess(false);
			j.setMsg(e.getMessage());
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("兑奖失败");
		}
		return j;
	}
	
	private void validateReceivePrizeParam(GzWxActBargainAwards gzWxActBargainAwards){
		 if(StringUtils.isEmpty(gzWxActBargainAwards.getId())){
			 throw new BargainException(ExceptionEnum.ARGUMENT_ERROR,"兑奖ID不能为空");
		 }
		 if(StringUtils.isEmpty(gzWxActBargainAwards.getRealName())){
			 throw new BargainException(ExceptionEnum.ARGUMENT_ERROR,"用户真实姓名不能为空");
		 }
		 if(StringUtils.isEmpty(gzWxActBargainAwards.getMobile())){
			 throw new BargainException(ExceptionEnum.ARGUMENT_ERROR,"用户手机号不能为空");
		 }
		 if(StringUtils.isEmpty(gzWxActBargainAwards.getAddress())){
			 throw new BargainException(ExceptionEnum.ARGUMENT_ERROR,"用户详细地址不能为空");
		 }
	 }
}

