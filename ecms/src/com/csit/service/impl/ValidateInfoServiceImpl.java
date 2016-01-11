package com.csit.service.impl;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.UUID;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang.StringUtils;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.csit.dao.StudentDAO;
import com.csit.dao.SystemConfigDAO;
import com.csit.dao.ValidateInfoDAO;
import com.csit.model.Student;
import com.csit.model.SystemConfig;
import com.csit.model.ValidateInfo;
import com.csit.service.ValidateInfoService;
import com.csit.thread.MessageClient;
import com.csit.vo.ServiceResult;
import com.octo.captcha.component.word.wordgenerator.RandomWordGenerator;
import com.octo.captcha.component.word.wordgenerator.WordGenerator;
/**
 * @Description:验证码信息类Service实现类
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-7-21
 * @author lys
 * @vesion 1.0
 */
@Service
public class ValidateInfoServiceImpl extends
		BaseServiceImpl<ValidateInfo, String> implements ValidateInfoService {
	@Resource
	private ValidateInfoDAO validateInfoDAO;
	@Resource
	private SystemConfigDAO systemConfigDAO;
	@Resource
	private StudentDAO studentDAO;
	@Resource
	private JavaMailSender sender;
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.ValidateInfoService#activeStu(com.csit.model.ValidateInfo)
	 */
	@Override
	public ServiceResult activeStu(ValidateInfo model) {
		ServiceResult result = new ServiceResult(false);
		if(model==null||StringUtils.isEmpty(model.getValidateInfoId())){
			result.setMessage("请选择要激活的邮件");
			return result;
		}
		ValidateInfo oldModel = validateInfoDAO.get(model.getValidateInfoId());
		if(oldModel==null){
			result.setMessage("该激活邮件已不能用");
			return result;
		}
		//验证时间是否过期
		SystemConfig oldSystemConfig = systemConfigDAO.load(1);
		Timestamp createTime = oldModel.getCreateTime();
		Integer registerEmailExpireTime = oldSystemConfig.getRegisterEmailExpireTime();
		
		Long distanceNow = Calendar.getInstance().getTimeInMillis() - createTime.getTime();
		if(distanceNow<=registerEmailExpireTime*1000L*60){//还在有效时间内
			//激活对应学生
			Student oldStudent = studentDAO.load(oldModel.getUserId());
			oldStudent.setStatus(1);
			result.addData("studentId", oldModel.getUserId());
			result.addData("studentName", oldStudent.getStudentName());
			//删除对应的验证码信息
			validateInfoDAO.delete(oldModel);
		}
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.ValidateInfoService#mobilePhoneReset(java.lang.String)
	 */
	@Override
	public ServiceResult mobilePhoneSend(String mobilePhone) {
		ServiceResult result = new ServiceResult(false);
		
		Student student = studentDAO.load("mobilePhone", mobilePhone);
		if(student==null){
			result.setMessage("此手机号码未注册");
			return result;
		}
		String randomWord = "1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		WordGenerator wordGenerator = new RandomWordGenerator(randomWord);
		String mobilePhoneCode = wordGenerator.getWord(6);
		
		String[] mobiles = {mobilePhone};
		String smsContent = "欢迎使用找回密码功能，您的验证码是："+mobilePhoneCode+"，请在30分钟内在找回密码页面填入此验证码。如非本人操作，请忽略本消息。";
		MessageClient.sendSMS(mobiles, smsContent);
		
		result.addData("mobilePhoneUser", student.getStudentId());
		result.addData("mobilePhoneCode", mobilePhoneCode);
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.ValidateInfoService#mobilePhoneConfirm(java.lang.String)
	 */
	@Override
	public ServiceResult mobilePhoneConfirm(String validateCode,String mobilePhoneCode,Integer mobilePhoneUser) {
		ServiceResult result = new ServiceResult(false);
		if(!StringUtils.equals(validateCode, mobilePhoneCode)){
			result.setMessage("验证码错误");
			return result;
		}
		
		result.addData("studentId", mobilePhoneUser);
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.ValidateInfoService#emailReset(java.lang.String)
	 */
	@Override
	public ServiceResult emailSend(String email) {
		ServiceResult result = new ServiceResult(false);
		
		Student student = studentDAO.load("email", email);
		if(student==null){
			result.setMessage("此邮箱地址未注册");
			return result;
		}
		String uuid = UUID.randomUUID().toString();
		Timestamp now = new Timestamp(System.currentTimeMillis()); 
		
		ValidateInfo validateInfo = new ValidateInfo();
		validateInfo.setUserId(student.getStudentId());
		validateInfo.setValidateInfoId(uuid);
		validateInfo.setCreateTime(now);
		
		validateInfoDAO.save(validateInfo);
		
		MimeMessage mailMessage = sender.createMimeMessage();
		try{
			MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage,true,"utf-8");
			messageHelper.setTo(email);
			SystemConfig systemConfig = systemConfigDAO.get(1);
			messageHelper.setFrom(systemConfig.getEmailCode(),systemConfig.getCompanyName());
			messageHelper.setSubject("找回密码");
			messageHelper.setText("欢迎使用找回密码功能，您的验证码是："+uuid+"，请在30分钟内在找回密码页面填入此验证码。如非本人操作，请忽略本消息。",true);
		}catch(MessagingException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		sender.send(mailMessage);
		
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.ValidateInfoService#emailConfirm(java.lang.String)
	 */
	@Override
	public ServiceResult emailConfirm(String emailCode) {
		ServiceResult result = new ServiceResult(false);
		
		ValidateInfo validateInfo = validateInfoDAO.load(emailCode);
		if(validateInfo==null){
			result.setMessage("无效的验证码");
			return result;
		}else{
			//链接存在的毫秒数
			Long distanceNow = Calendar.getInstance().getTimeInMillis() - validateInfo.getCreateTime().getTime();
			result.addData("studentId",validateInfo.getUserId());
			validateInfoDAO.delete(validateInfo);
			SystemConfig systemConfig = systemConfigDAO.load(1);
			if(distanceNow>systemConfig.getEmailExpireTime()*1000L*60){
				result.setMessage("验证码已过期");
				return result;
			}
		}
		
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.ValidateInfoService#newMobilePhoneSend(java.lang.String)
	 */
	@Override
	public ServiceResult newMobilePhoneSend(String mobilePhone) {
		ServiceResult result = new ServiceResult(false);
		
		Student student = studentDAO.load("mobilePhone", mobilePhone);
		if(student!=null){
			result.setMessage("此手机号码已被注册");
			return result;
		}
		String randomWord = "1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		WordGenerator wordGenerator = new RandomWordGenerator(randomWord);
		String mobilePhoneCode = wordGenerator.getWord(6);
		
		String[] mobiles = {mobilePhone};
		String smsContent = "您正在更改绑定手机，验证码是："+mobilePhoneCode+"。";
		MessageClient.sendSMS(mobiles, smsContent);
		
		result.addData("mobilePhoneCode", mobilePhoneCode);
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.ValidateInfoService#newEmailSend(java.lang.String)
	 */
	@Override
	public ServiceResult newEmailSend(String email) {
		ServiceResult result = new ServiceResult(false);
		
		Student student = studentDAO.load("email", email);
		if(student!=null){
			result.setMessage("此邮箱地址已被注册");
			return result;
		}
		String uuid = UUID.randomUUID().toString();
		Timestamp now = new Timestamp(System.currentTimeMillis()); 
		
		ValidateInfo validateInfo = new ValidateInfo();
		validateInfo.setValidateInfoId(uuid);
		validateInfo.setCreateTime(now);
		
		validateInfoDAO.save(validateInfo);
		
		MimeMessage mailMessage = sender.createMimeMessage();
		try{
			MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage,true,"utf-8");
			messageHelper.setTo(email);
			SystemConfig systemConfig = systemConfigDAO.get(1);
			messageHelper.setFrom(systemConfig.getEmailCode(),systemConfig.getCompanyName());
			messageHelper.setSubject("更改绑定邮箱");
			messageHelper.setText("您正在更改绑定邮箱，验证码是："+uuid+"。",true);
		}catch(MessagingException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		sender.send(mailMessage);
		
		result.setIsSuccess(true);
		return result;
	}

}
