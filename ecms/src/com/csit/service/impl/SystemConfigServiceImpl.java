package com.csit.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import com.csit.dao.SystemConfigDAO;
import com.csit.model.SystemConfig;
import com.csit.service.SystemConfigService;
import com.csit.util.JSONUtil;
import com.csit.vo.ServiceResult;
@Service
public class SystemConfigServiceImpl extends
		BaseServiceImpl<SystemConfig, Integer> implements SystemConfigService {

	@Resource
	SystemConfigDAO systemConfigDAO;
	
	@Resource
	private JavaMailSenderImpl mailSender;
	
	@Override
	public ServiceResult save(SystemConfig model,String proPath) {
		ServiceResult result = new ServiceResult(false);
		
		SystemConfig oldModel = systemConfigDAO.load(1);
		
		
		if(StringUtils.isNotEmpty(model.getEmailHost())&&StringUtils.isNotEmpty(model.getEmailCode())&&
				StringUtils.isNotEmpty(model.getEmailPwd())&&model.getEmailExpireTime()!=null){
//			Properties props = new Properties();
//			InputStream fis = null;
//			OutputStream fos = null;
//	        try{
//	        	fis = new BufferedInputStream(new FileInputStream(proPath));
//	        	props.load(fis);
//	        	fis.close();
	        	
	        	mailSender.setHost(model.getEmailHost());
	        	mailSender.setUsername(model.getEmailCode());
	        	mailSender.setPassword(model.getEmailPwd());
//	        	props.setProperty("mail.host", model.getEmailHost());
//	        	props.setProperty("mail.username", model.getEmailCode());
//	        	props.setProperty("mail.password", model.getEmailPwd());
//	        	fos = new BufferedOutputStream(new FileOutputStream(proPath));
//	        	fos.flush();
//	        	props.store(fos, "写入到propertise文件");
//	        }catch (Exception e) {
//	        	result.setMessage("读取邮箱配置信息失败");
//	        	e.printStackTrace();
//	        }finally{
//	        	try {
//	        		fis.close();
//	        		fos.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//	        	
//	        }
	        oldModel.setEmailHost(model.getEmailHost());
			oldModel.setEmailCode(model.getEmailCode());
			oldModel.setEmailPwd(model.getEmailPwd());
			oldModel.setEmailExpireTime(model.getEmailExpireTime());
			oldModel.setRegisterEmailExpireTime(model.getRegisterEmailExpireTime());
		}
		
		oldModel.setCompanyName(model.getCompanyName());
		oldModel.setCompanyProfiles(model.getCompanyProfiles());
		oldModel.setHotline(model.getHotline());
		oldModel.setPicture(model.getPicture());
		result.setIsSuccess(true);
		return result;
	}

	@Override
	public ServiceResult init() {
		ServiceResult result = new ServiceResult(false);
		SystemConfig systemConfig = systemConfigDAO.load(1);
		String[] properties = {"emailHost","emailCode","emailPwd","registerEmailExpireTime","emailExpireTime","companyName","picture.pictureId","companyProfiles","hotline"};
		
		String data=JSONUtil.toJson(systemConfig, properties);
		result.addData("systemConfig", data);
		result.setIsSuccess(true);
		return result;
	}

}
