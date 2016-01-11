package com.csit.service;

import com.csit.model.ValidateInfo;
import com.csit.vo.ServiceResult;
/**
 * @Description:验证码信息类Service
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-7-21
 * @author lys
 * @vesion 1.0
 */
public interface ValidateInfoService extends BaseService<ValidateInfo,String>{
	/**
	 * @Description: 激活学生
	 * @Created: 2013-7-21 下午10:07:12
	 * @author lys
	 * @update logs
	 * @throws Exception
	 * @param model
	 * @return
	 */
	ServiceResult activeStu(ValidateInfo model);
	/**
	 * 
	 * @Description: 发送验证短信（找回密码）
	 * @Create: 2013-7-21 下午10:58:32
	 * @author yk
	 * @update logs
	 * @return
	 */
	ServiceResult mobilePhoneSend(String mobilePhone);
	/**
	 * 
	 * @Description: 短信验证码确认（找回密码）
	 * @Create: 2013-7-24 上午10:03:33
	 * @author yk
	 * @update logs
	 * @param validateCode
	 * @return
	 */
	ServiceResult mobilePhoneConfirm(String validateCode,String mobilePhoneCode,Integer mobilePhoneUser);
	/**
	 * 
	 * @Description: 发送验证邮件（找回密码）
	 * @Create: 2013-7-21 下午10:58:47
	 * @author yk
	 * @update logs
	 * @return
	 */
	ServiceResult emailSend(String email);
	/**
	 * 
	 * @Description: 邮件验证码确认（找回密码）
	 * @Create: 2013-7-22 上午10:46:58
	 * @author yk
	 * @update logs
	 * @param mobilePhone
	 * @return
	 */
	ServiceResult emailConfirm(String emailCode);
	/**
	 * 
	 * @Description: 发送验证短信到新手机 （更改绑定手机）
	 * @Create: 2013-7-24 下午04:28:00
	 * @author yk
	 * @update logs
	 * @param mobilePhone
	 * @return
	 */
	ServiceResult newMobilePhoneSend(String mobilePhone);
	/**
	 * 
	 * @Description: 发送验证邮件到新邮箱 （更改绑定手机）
	 * @Create: 2013-7-24 下午04:28:22
	 * @author yk
	 * @update logs
	 * @param email
	 * @return
	 */
	ServiceResult newEmailSend(String email);
}
