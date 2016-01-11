package com.csit.service;

import java.util.Date;

import com.csit.model.MailsHistory;
import com.csit.vo.ServiceResult;
/**
 * 
 * @Description: 邮件发送历史记录Service
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-7-10
 * @author yk
 * @vesion 1.0
 */
public interface MailsHistoryService extends BaseService<MailsHistory, Integer> {
	/**
	 * 
	 * @Description: 分页查询邮件发送历史记录
	 * @Create: 2013-7-10 下午05:02:46
	 * @author yk
	 * @update logs
	 * @param model
	 * @param page
	 * @param rows
	 * @return
	 */
	String query(MailsHistory model, Integer page, Integer rows, Date startDate, Date endDate);
	/**
	 * @Description: 发送邮件
	 * @Created: 2013-7-21 下午7:16:30
	 * @author lys
	 * @update logs
	 * @throws Exception
	 * @return
	 */
	ServiceResult send(String toMailAddr,String subject,String content,Integer mailType);
}
