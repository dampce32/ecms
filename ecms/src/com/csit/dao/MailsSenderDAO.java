package com.csit.dao;

import java.util.List;
import java.util.Map;

import com.csit.model.MailsHistory;
import com.csit.model.MailsSender;

/**
 * 
 * @Description: 邮件发送DAO
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-7-9
 * @author yk
 * @vesion 1.0
 */
public interface MailsSenderDAO extends BaseDAO<MailsSender, Integer> {
	/**
	 * 
	 * @Description: 查询待发送的邮件 
	 * @Create: 2013-7-9 下午02:35:28
	 * @author yk
	 * @update logs
	 * @return
	 */
	List<MailsSender> querySender();
	/**
	 * 
	 * @Description: 发送成功，转存到历史记录 
	 * @Create: 2013-7-9 下午02:36:02
	 * @author yk
	 * @update logs
	 */
	void sendSuccess(MailsSender mailsSender, MailsHistory mailsHistory);
	/**
	 * 
	 * @Description: 更新发送错误次数 
	 * @Create: 2013-7-10 下午03:38:52
	 * @author yk
	 * @update logs
	 * @param mailsSenderId
	 */
	void updateErrorCount(Integer mailsSenderId);
	/**
	 * 
	 * @Description: 分页查询邮件发送 
	 * @Create: 2013-7-10 下午05:53:44
	 * @author yk
	 * @update logs
	 * @param model
	 * @param page
	 * @param rows
	 * @return
	 */
	public List<MailsSender> query(MailsSender model,Integer page, Integer rows);
	/**
	 * 
	 * @Description: 统计邮件发送 
	 * @Create: 2013-7-10 下午05:53:54
	 * @author yk
	 * @update logs
	 * @param model
	 * @return
	 */
	Long getTotalCount(MailsSender model);
	/**
	 * 
	 * @Description: 获取邮箱地址 
	 * @Create: 2013-7-11 上午11:00:06
	 * @author yk
	 * @update logs
	 * @param MailsSender
	 * @return
	 */
	List<String> getMails(MailsSender MailsSender);
	/**
	 * 
	 * @Description: 获取收件人 
	 * @Create: 2013-7-12 上午10:22:54
	 * @author yk
	 * @update logs
	 * @param receiveIDs
	 * @return
	 */
	List<Map<String, Object>> queryReceiver(String receiveIDs);
}
