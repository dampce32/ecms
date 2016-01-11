package com.csit.thread;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import org.apache.commons.lang.StringUtils;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.transaction.annotation.Transactional;

import com.csit.dao.MailsSenderDAO;
import com.csit.dao.PictureDAO;
import com.csit.dao.SystemConfigDAO;
import com.csit.model.MailsHistory;
import com.csit.model.MailsSender;
import com.csit.model.Picture;
import com.csit.model.SystemConfig;
import com.csit.vo.GobelConstants;

public class MailsSenderQuartz {
	
	private static ThreadPoolExecutor executor = new ThreadPoolExecutor(3, 6, 5000,
			TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
	
	@Resource
	private MailsSenderDAO mailsSenderDAO;
	@Resource
	private PictureDAO pictureDAO;
	@Resource
	private SystemConfigDAO systemConfigDAO;
	@Resource
	private JavaMailSender sender;
	
	public void send(final String[] mails,final MailsSender mailsSender){
		//执行发送方法
		executor.execute(new Runnable() {
			public void run() {
				Integer result = sendMails(mails,mailsSender);
				
				if(result==0){
					//发送成功
					MailsHistory mailsHistory = new MailsHistory();
					mailsHistory.setTeacher(mailsSender.getTeacher());
					mailsHistory.setReceiveIDs(mailsSender.getReceiveIDs());
					mailsHistory.setTitle(mailsSender.getTitle());
					mailsHistory.setContent(mailsSender.getContent());
					mailsHistory.setSendTime(mailsSender.getSendTime());
					mailsHistory.setMailType(mailsSender.getMailType());
					mailsSenderDAO.sendSuccess(mailsSender, mailsHistory);
				}else{
					//更新发送错误次数
					mailsSenderDAO.updateErrorCount(mailsSender.getMailsSenderId());
				}
			}
		});
	}
	
	public Integer sendMails(String[] mails,MailsSender mailsSender){
		
		Integer result = 0;
		
		MimeMessage mailMessage = sender.createMimeMessage();
		
		//设置utf-8或GBK编码，否则邮件会有乱码  
		try {
			MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage,true,"utf-8");
			//接受者 
			messageHelper.setTo(mails);
			//发送者
			SystemConfig systemConfig = systemConfigDAO.get(1);
			messageHelper.setFrom(systemConfig.getEmailCode(),systemConfig.getCompanyName());
			//主题
			if(StringUtils.isNotEmpty(mailsSender.getTitle())){
				messageHelper.setSubject(mailsSender.getTitle());
			}
			//邮件内容，html格式加参数true
			if(StringUtils.isNotEmpty(mailsSender.getContent())){
				messageHelper.setText(mailsSender.getContent(),true);
			}
			
			String attachments = mailsSender.getAttachment();
			if(StringUtils.isNotEmpty(attachments)){
				String[] attachmentList = attachments.split(",");
				
				//附件
				Picture picture = null;
				//新建文件
				File file = null;
				for(String attachment:attachmentList){
					picture = pictureDAO.get(Integer.parseInt(attachment));
					URL in = MailsSenderQuartz.class.getClassLoader().getResource("../../"+picture.getPicturePath()+"/"+picture.getPictureName());
					file = new File(in.getPath());
					// 这里的方法调用和插入图片是不同的，使用MimeUtility.encodeWord()来解决附件名称的中文问题
					messageHelper.addAttachment(MimeUtility.encodeWord(file.getName()), file);
				}
			}
			sender.send(mailMessage);

		} catch (Exception e){
			System.out.println("发送失败");
			e.printStackTrace();
			result = 1;
			return result;
		}
		return result;
	}
	
	@Transactional
	public void monitor(){
		List<MailsSender> list = mailsSenderDAO.querySender();
		for(MailsSender mailsSender : list){
			String[] mails = mailsSender.getReceiveIDs().split(GobelConstants.SPLIT);
			//调度发送
			send(mails,mailsSender);
		}
	}
}
