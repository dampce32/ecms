package com.csit.service;

import com.csit.model.MessageSend;
import com.csit.model.Teacher;
import com.csit.vo.ServiceResult;

/**
 * 
 * 
 * @Description: 信息发送service
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-25
 * @author longweier
 * @vesion 1.0
 */
public interface MessageSendService extends BaseService<MessageSend, Integer> {
	
	/**
	 * 
	 * @Description: 发送短信
	 * @param
	 * @Create: 2013-6-26 上午10:33:10
	 * @author longweier
	 * @update logs
	 * @param messageSend
	 * @param names
	 * @param mobilePhones
	 * @return
	 * @return
	 * @throws Exception
	 */
	ServiceResult sendMessage(MessageSend messageSend,String names,String mobilePhones);

	/**
	 * 
	 * @Description: 查询发送历史
	 * @param
	 * @Create: 2013-6-28 上午09:36:41
	 * @author longweier
	 * @update logs
	 * @param beginDate
	 * @param endDate
	 * @param teacher
	 * @param page
	 * @param rows
	 * @return
	 * @return
	 * @throws Exception
	 */
	String queryHistory(String beginDate,String endDate,Teacher teacher,Integer page, Integer rows);
	
	/**
	 * 
	 * @Description: 发送失败记录
	 * @param
	 * @Create: 2013-6-28 上午09:37:46
	 * @author longweier
	 * @update logs
	 * @param messageSend
	 * @return
	 * @return
	 * @throws Exception
	 */
	String queryFailed(MessageSend messageSend);
	
	/**
	 * 
	 * @Description: 接收对象
	 * @param
	 * @Create: 2013-6-28 上午09:38:41
	 * @author longweier
	 * @update logs
	 * @param receiveIDs
	 * @return
	 * @return
	 * @throws Exception
	 */
	String getReceiver(String receiveIDs);
	/**
	 * 
	 * @Description: 查询提示短信发送历史
	 * @Create: 2013-7-18 下午03:13:34
	 * @author yk
	 * @update logs
	 * @return
	 */
	String queryPromptHistory(String beginDate,String endDate, Integer messageType,Integer page, Integer rows);

	/**
	 * 
	 * @Description: 查询提示短信失败记录
	 * @Create: 2013-7-19 上午09:55:15
	 * @author yk
	 * @update logs
	 * @param page
	 * @param rows
	 * @return
	 */
	String queryPromptFail();
}
