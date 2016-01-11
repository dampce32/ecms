package com.csit.thread;

import java.util.Date;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.transaction.annotation.Transactional;

import com.csit.dao.MessageSendDAO;
import com.csit.model.HistoryMessage;
import com.csit.model.MessageSend;

/**
 * 
 * @Description: 发送消息线程
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-26
 * @author longweier
 * @vesion 1.0
 */
public class MessageSendQuartz {
	
	private static ThreadPoolExecutor executor = new ThreadPoolExecutor(3, 6, 5000,
			TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
	
	@Resource
	private MessageSendDAO messageSendDAO;
	
	public void send(final MessageSend messageSend){
		//执行发送方法
		executor.execute(new Runnable() {
			public void run() {
				String[] receiveIDs = messageSend.getReceiveIDs().split(",");
				Integer result = MessageClient.sendSMS(receiveIDs, messageSend.getMessageContent());
				//发送成功
				if(result==0){
					HistoryMessage historyMessage = new HistoryMessage();
					
					historyMessage.setMessageContent(messageSend.getMessageContent());
					historyMessage.setMessageType(messageSend.getMessageType());
					historyMessage.setReceiveIDs(messageSend.getReceiveIDs());
					historyMessage.setSendTime(new Date());
					historyMessage.setTeacher(messageSend.getTeacher());
					
					messageSendDAO.sendSuccess(messageSend, historyMessage);
				//发送失败	
				}else{
					//修改发送错误次数
					messageSendDAO.updateErrorCount(messageSend.getMessageId());
				}
			}
		});
	}
	@Transactional
	public void monitor(){
		List<MessageSend> list = messageSendDAO.querySend();
		for(MessageSend messageSend : list){
			//调度发送
			send(messageSend);
		}
	}
	
}
