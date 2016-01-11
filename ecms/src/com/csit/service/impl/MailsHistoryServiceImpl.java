package com.csit.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.csit.dao.MailsHistoryDAO;
import com.csit.dao.SystemConfigDAO;
import com.csit.model.MailsHistory;
import com.csit.model.SystemConfig;
import com.csit.service.MailsHistoryService;
import com.csit.util.JSONUtil;
import com.csit.vo.ServiceResult;
/**
 * 
 * @Description: 邮件发送历史记录Service实现类
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-7-10
 * @author yk
 * @vesion 1.0
 */
@Service
public class MailsHistoryServiceImpl extends
		BaseServiceImpl<MailsHistory, Integer> implements MailsHistoryService {
	@Resource
	private JavaMailSenderImpl mailSender;
	@Resource
	private SystemConfigDAO systemConfigDAO;
	@Resource
	private MailsHistoryDAO mailsHistoryDAO;
	
	@Override
	public ServiceResult send(String toMailAddr, String subject,
			String content, Integer mailType) {
		ServiceResult result = new ServiceResult(false);
		MimeMessage mailMessage = mailSender.createMimeMessage();
		//设置utf-8或GBK编码，否则邮件会有乱码
		MimeMessageHelper messageHelper = null;   
		//设置邮件参数
		SystemConfig systemConfig = systemConfigDAO.load(1);
		try {
			messageHelper = new MimeMessageHelper(mailMessage,true,"utf-8"); 
			messageHelper.setFrom(systemConfig.getEmailCode(),systemConfig.getCompanyName());//发送者,这里还可以另起Email别名，不用和xml里的username一致 
			messageHelper.setTo(toMailAddr);//接受者  
			messageHelper.setSubject(subject);//主题
			messageHelper.setText(content, true);//邮件内容  
			mailSender.send(mailMessage);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		} 
		
		MailsHistory mailsHistory = new MailsHistory();
		mailsHistory.setTitle(subject);
		mailsHistory.setContent(content);
		mailsHistory.setReceiveIDs(toMailAddr);
		mailsHistory.setMailType(mailType);
		mailsHistoryDAO.save(mailsHistory);
		
		result.setIsSuccess(true);
		return result;
	}

	@Override
	public String query(MailsHistory model, Integer page, Integer rows, Date startDate, Date endDate) {
		List<MailsHistory> list = mailsHistoryDAO.query(model,page,rows,startDate,endDate);
		Long total = mailsHistoryDAO.getTotalCount(model,startDate,endDate);
		String[] properties = {"mailsHistoryId","teacher.teacherName","receiveIDs",
				"title","content","sendTime"};
		return JSONUtil.toJson(list,properties,total);
	}

}
