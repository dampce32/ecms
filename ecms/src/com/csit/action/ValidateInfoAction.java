package com.csit.action;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.csit.model.Student;
import com.csit.model.ValidateInfo;
import com.csit.service.ValidateInfoService;
import com.csit.util.JCaptchaEngine;
import com.csit.vo.ServiceResult;
import com.octo.captcha.service.CaptchaService;
import com.opensymphony.xwork2.ModelDriven;
/**
 * @Description:验证码信息类Action
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-7-21
 * @author lys
 * @vesion 1.0
 */
@Controller
@Scope("prototype")
public class ValidateInfoAction extends BaseAction implements
		ModelDriven<ValidateInfo> {
	private static final long serialVersionUID = 7684474215749492338L;
	private static final Logger logger = Logger.getLogger(ValidateInfoAction.class);
	ValidateInfo model = new ValidateInfo();
	@Resource
	private ValidateInfoService validateInfoService;
	@Resource
	private CaptchaService captchaService;
	/**
	 * 失败信息
	 */
	private String failMsg;
	
	
	@Override
	public ValidateInfo getModel() {
		return model;
	}
	/**
	 * @Description: 邮箱激化学生，并调整到主界面，完成登录
	 * @Created: 2013-7-21 下午10:04:49
	 * @author lys
	 * @update logs
	 * @throws Exception
	 * @return
	 */
	public String activeStu(){
		ServiceResult result = new ServiceResult(false);
		result = validateInfoService.activeStu(model);
		if(result.getIsSuccess()){
			setSession(Student.LOGIN_ID, result.getData().get("studentId"));
			setSession(Student.LOGIN_NAME, result.getData().get("studentName"));
			return SUCCESS;
		}else{
			failMsg = result.getMessage();
			return "fail";
		}
	}
	public String getFailMsg() {
		return failMsg;
	}
	/**
	 * 
	 * @Description: 发送验证短信（找回密码） 
	 * @Create: 2013-7-24 上午10:08:23
	 * @author yk
	 * @update logs
	 */
	public void mobilePhoneSend(){
		ServiceResult result = new ServiceResult(false);
		String mobilePhone = getParameter("mobilePhone");
		String isAgain = getParameter("isAgain");
		
		
		try {
			if(StringUtils.isEmpty(isAgain)){
				String captchaID = request.getSession().getId();
				String challengeResponse = StringUtils.upperCase(request.getParameter(JCaptchaEngine.CAPTCHA_INPUT_NAME));
				if (StringUtils.isEmpty(challengeResponse) || captchaService.validateResponseForID(captchaID, challengeResponse) == false) {
					result.setMessage("验证码错误");
					ajaxJson(result.toJSON());
					return;
				}
			}
			result = validateInfoService.mobilePhoneSend(mobilePhone);
		} catch (Exception e) {
			result.setMessage("发送验证短信错误");
			logger.error("发送验证短信错误", e);
		}
		if(result.getIsSuccess()){
			setSession("mobilePhoneUser", result.getData().get("mobilePhoneUser"));
			setSession("mobilePhoneCode", result.getData().get("mobilePhoneCode"));
		}
		ajaxJson(result.toJSON());
	}
	/**
	 * 
	 * @Description: 短信验证码确认（找回密码）
	 * @Create: 2013-7-24 下午04:20:29
	 * @author yk
	 * @update logs
	 */
	public void mobilePhoneConfirm(){
		ServiceResult result = new ServiceResult(false);
		Object mobilePhoneUserSession = getSession("mobilePhoneUser");
		Object mobilePhoneCodeSession = getSession("mobilePhoneCode");
		String validateCode = getParameter("validateCode");
		
		try {
			if(mobilePhoneUserSession==null||mobilePhoneCodeSession==null){
				result.setMessage("无验证记录，请重新验证");
			}else if(StringUtils.isEmpty(validateCode)){
				result.setMessage("验证码不能为空");
			}else{
				String mobilePhoneCode = mobilePhoneCodeSession.toString();
				Integer mobilePhoneUser = (Integer)mobilePhoneUserSession;
				result = validateInfoService.mobilePhoneConfirm(validateCode,mobilePhoneCode,mobilePhoneUser);
			}
		} catch (Exception e) {
			result.setMessage("验证错误");
			logger.error("验证错误", e);
		}
		if(result.getIsSuccess()){
			getSession().remove("mobilePhoneUser");
			getSession().remove("mobilePhoneCode");
		}
		ajaxJson(result.toJSON());
	}
	/**
	 * 
	 * @Description: 发送验证邮件 （找回密码）
	 * @Create: 2013-7-24 上午10:08:02
	 * @author yk
	 * @update logs
	 */
	public void emailSend(){
		ServiceResult result = new ServiceResult(false);
		String email = getParameter("email");
		String isAgain = getParameter("isAgain");
		
		try {
			if(StringUtils.isEmpty(isAgain)){
				String captchaID = request.getSession().getId();
				String challengeResponse = StringUtils.upperCase(request.getParameter(JCaptchaEngine.CAPTCHA_INPUT_NAME));
				if (StringUtils.isEmpty(challengeResponse) || captchaService.validateResponseForID(captchaID, challengeResponse) == false) {
					result.setMessage("验证码错误");
					ajaxJson(result.toJSON());
					return;
				}
			}
			result = validateInfoService.emailSend(email);
		} catch (com.octo.captcha.service.CaptchaServiceException e){
			result.setMessage("无效的验证码，请点击图片刷新");
			logger.error("无效的验证码，请点击图片刷新", e);
		} catch (Exception e) {
			result.setMessage("发送验证邮件失败");
			logger.error("发送验证邮件失败", e);
		}
		ajaxJson(result.toJSON());
	}
	/**
	 * 
	 * @Description: 邮件验证码确认 （找回密码）
	 * @Create: 2013-7-24 上午10:07:53
	 * @author yk
	 * @update logs
	 */
	public void emailConfirm(){
		ServiceResult result = new ServiceResult(false);
		String validateCode = getParameter("validateCode");
		try {
			result = validateInfoService.emailConfirm(validateCode);
		} catch (org.hibernate.ObjectNotFoundException e){
			result.setMessage("无验证记录");
			logger.error("无验证记录", e);
		} catch (Exception e) {
			result.setMessage("验证错误");
			logger.error("验证错误", e);
		}
		ajaxJson(result.toJSON());
	}
	/**
	 * 
	 * @Description: 发送验证短信到新手机 （更改绑定手机）
	 * @Create: 2013-7-24 下午04:22:42
	 * @author yk
	 * @update logs
	 */
	public void newMobilePhoneSend(){
		ServiceResult result = new ServiceResult(false);
		String mobilePhone = getParameter("mobilePhone");
		try {
			result = validateInfoService.newMobilePhoneSend(mobilePhone);
		} catch (Exception e) {
			result.setMessage("发送验证短信错误");
			logger.error("发送验证短信错误", e);
		}
		if(result.getIsSuccess()){
			setSession("mobilePhoneCode", result.getData().get("mobilePhoneCode"));
		}
		ajaxJson(result.toJSON());
	}
	/**
	 * 
	 * @Description: 短信验证码确认（更改绑定手机） 
	 * @Create: 2013-7-24 下午04:42:26
	 * @author yk
	 * @update logs
	 */
	public void newMobilePhoneConfirm(){
		ServiceResult result = new ServiceResult(false);
		Object mobilePhoneCodeSession = getSession("mobilePhoneCode");
		String validateCode = getParameter("validateCode");
		
		try {
			if(mobilePhoneCodeSession==null){
				result.setMessage("无验证记录，请重新验证");
			}else if(StringUtils.isEmpty(validateCode)){
				result.setMessage("验证码不能为空");
			}else if(StringUtils.equals(validateCode,mobilePhoneCodeSession.toString())){
				result.setIsSuccess(true);
			}
		} catch (Exception e) {
			result.setMessage("验证错误");
			logger.error("验证错误", e);
		}
		if(result.getIsSuccess()){
			getSession().remove("mobilePhoneCode");
		}
		ajaxJson(result.toJSON());
	}
	/**
	 * 
	 * @Description: 发送验证邮件到新邮箱 （更改绑定手机）
	 * @Create: 2013-7-24 下午04:24:12
	 * @author yk
	 * @update logs
	 */
	public void newEmailSend(){
		ServiceResult result = new ServiceResult(false);
		String email = getParameter("email");
		try {
			result = validateInfoService.newEmailSend(email);
		} catch (Exception e) {
			result.setMessage("发送验证邮件失败");
			logger.error("发送验证邮件失败", e);
		}
		ajaxJson(result.toJSON());
	}
	/**
	 * 
	 * @Description: 邮件验证码确认 （更改绑定手机）
	 * @Create: 2013-7-24 下午04:47:12
	 * @author yk
	 * @update logs
	 */
	public void newEmailConfirm(){
		ServiceResult result = new ServiceResult(false);
		String validateCode = getParameter("validateCode");
		try {
			result = validateInfoService.emailConfirm(validateCode);
		} catch (org.hibernate.ObjectNotFoundException e){
			result.setMessage("无验证记录");
			logger.error("无验证记录", e);
		} catch (Exception e) {
			result.setMessage("验证错误");
			logger.error("验证错误", e);
		}
		ajaxJson(result.toJSON());
	}
}
