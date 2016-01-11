package com.csit.dao;

import java.util.Date;
import java.util.List;

import com.csit.model.MailsHistory;

/**
 * 
 * @Description: 邮件发送历史记录DAO
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-7-9
 * @author yk
 * @vesion 1.0
 */
public interface MailsHistoryDAO extends BaseDAO<MailsHistory, Integer> {
	/**
	 * 
	 * @Description: 分页查询邮件发送历史 
	 * @Create: 2013-7-10 下午04:46:34
	 * @author yk
	 * @update logs
	 * @param model
	 * @param page
	 * @param rows
	 * @return
	 */
	public List<MailsHistory> query(MailsHistory model,Integer page, Integer rows, Date startDate, Date endDate);
	/**
	 * 
	 * @Description: 统计邮件发送历史 
	 * @Create: 2013-7-10 下午04:46:45
	 * @author yk
	 * @update logs
	 * @param model
	 * @return
	 */
	Long getTotalCount(MailsHistory model, Date startDate, Date endDate);
}
