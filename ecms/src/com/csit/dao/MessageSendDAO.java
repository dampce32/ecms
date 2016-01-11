package com.csit.dao;

import java.util.List;
import java.util.Map;

import com.csit.model.HistoryMessage;
import com.csit.model.MessageSend;

/**
 * 
 * @Description: 消息发送
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-25
 * @author longweier
 * @vesion 1.0
 */
public interface MessageSendDAO extends BaseDAO<MessageSend, Integer> {
	
	/**
	 * 
	 * @Description: 查询
	 * @param
	 * @Create: 2013-6-26 下午02:18:26
	 * @author longweier
	 * @update logs
	 * @param messageSend
	 * @return
	 * @return
	 * @throws Exception
	 */
	List<String> getMobiles(MessageSend messageSend);
	
	/**
	 * 
	 * @Description: 查询需要发送的数据
	 * @param
	 * @Create: 2013-6-26 下午05:49:03
	 * @author longweier
	 * @update logs
	 * @return
	 * @return
	 * @throws Exception
	 */
	List<MessageSend> querySend();
	
	/**
	 * 
	 * @Description: 修改发送错误的次数
	 * @param
	 * @Create: 2013-6-26 下午05:51:35
	 * @author longweier
	 * @update logs
	 * @param messageId
	 * @return
	 * @throws Exception
	 */
	void updateErrorCount(Integer messageId);
	
	/**
	 * 
	 * @Description: 发送成功,操作新表
	 * @param
	 * @Create: 2013-6-27 上午11:18:01
	 * @author longweier
	 * @update logs
	 * @param messageSend
	 * @param historyMessage
	 * @return
	 * @throws Exception
	 */
	void sendSuccess(MessageSend messageSend,HistoryMessage historyMessage);
	
	/**
	 * 
	 * 
	 * @Description: 查询发送失败的短信
	 * @param
	 * @Create: 2013-6-28 上午09:07:47
	 * @author longweier
	 * @update logs
	 * @param messageSend
	 * @return
	 * @return
	 * @throws Exception
	 */
	List<MessageSend> queryFailed(MessageSend messageSend);
	
	/**
	 * 
	 * @Description: 查询发送对象
	 * @param
	 * @Create: 2013-6-28 上午09:44:13
	 * @author longweier
	 * @update logs
	 * @param receiveIDs
	 * @return
	 * @return
	 * @throws Exception
	 */
	List<Map<String, Object>> queryReceiver(String receiveIDs);
	/**
	 * 
	 * @Description: 查询短信提示失败记录 
	 * @Create: 2013-7-19 上午08:40:27
	 * @author yk
	 * @update logs
	 * @return
	 */
	List<MessageSend> queryPromptFail();
}
