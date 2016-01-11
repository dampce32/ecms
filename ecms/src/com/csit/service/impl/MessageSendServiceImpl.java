package com.csit.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.csit.dao.ContactsDAO;
import com.csit.dao.HistoryMessageDAO;
import com.csit.dao.MessageSendDAO;
import com.csit.model.Contacts;
import com.csit.model.HistoryMessage;
import com.csit.model.MessageSend;
import com.csit.model.Teacher;
import com.csit.service.MessageSendService;
import com.csit.util.JSONUtil;
import com.csit.vo.ServiceResult;

/**
 * 
 * @Description: 消息发送实现类
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-26
 * @author longweier
 * @vesion 1.0
 */
@Service
public class MessageSendServiceImpl extends BaseServiceImpl<MessageSend, Integer> implements MessageSendService {

	@Resource
	private MessageSendDAO messageSendDAO;
	@Resource
	private HistoryMessageDAO historyMessageDAO;
	@Resource
	private ContactsDAO contactsDAO;
	
	public ServiceResult sendMessage(MessageSend messageSend,String names,String mobilePhones) {
		ServiceResult result = new ServiceResult(false);
		if(StringUtils.isEmpty(messageSend.getMessageContent())){
			result.setMessage("消息不能为空");
			return result;
		}
		if(StringUtils.isEmpty(messageSend.getReceiveIDs())){
			result.setMessage("接收人不能为空");
			return result;
		}
		String[] receiveIDArray = messageSend.getReceiveIDs().split(",");
		String[] nameArray = names.split(",");
		String[] mobilePhoneArray = mobilePhones.split(",");
		
		//保存联系人
		String[] contactsArray = new String[receiveIDArray.length];
		int index = 0;
		for(String contactsId : receiveIDArray){
			Contacts model = new Contacts();
			model.setMobilePhone(mobilePhoneArray[index]);
			model.setName(nameArray[index]);
			model.setTeacher(messageSend.getTeacher());
			//不可见的通讯录
			if("-1".equals(contactsId)){
				Contacts contacts = contactsDAO.getContacts(model);
				//如果不存在
				if(contacts==null){
					model.setVisible(0);
					contactsDAO.save(model);
					contactsArray[index] = mobilePhoneArray[index];
				}else{
					contactsArray[index] = mobilePhoneArray[index];
				}
			//可见的通讯录	
			}else if("0".equals(contactsId)){
				Contacts contacts = contactsDAO.getContacts(model);
				//如果不存在
				if(contacts==null){
					model.setVisible(1);
					contactsDAO.save(model);
					contactsArray[index] = mobilePhoneArray[index];
				}else{
					contactsArray[index] = mobilePhoneArray[index];
				}
			}else{
				contactsArray[index] = mobilePhoneArray[index];
			}
			index++;
		}
		
		//去掉重复的号码
		Set<String> set = new TreeSet<String>();
		for (int i = 0; i < contactsArray.length; i++) {
			set.add(contactsArray[i]);
		}
		contactsArray = set.toArray(new String[0]);
		
		StringBuilder receives = new StringBuilder();
		for(String receive : contactsArray){
			receives.append(receive+",");
		}
		
		messageSend.setReceiveIDs(receives.substring(0, receives.length()-1));
		messageSend.setErrorCount(0);
		messageSend.setMessageType(1);
		if(messageSend.getSendTime()==null){
			messageSend.setSendTime(new Date());
		}
		messageSendDAO.save(messageSend);
		
		result.setIsSuccess(true);
		return result;
	}

	public String getReceiver(String receiveIDs) {
		List<Map<String, Object>> listMap = messageSendDAO.queryReceiver(receiveIDs);
		String jsonArray = JSONUtil.toJsonFromListMap(listMap);
		return jsonArray;
	}

	public String queryFailed(MessageSend messageSend) {
		List<MessageSend> list = messageSendDAO.queryFailed(messageSend);
		String[] properties = {"messageId","messageContent","receiveIDs","sendTime"};
		String jsonArray = JSONUtil.toJson(list, properties);
		return jsonArray;
	}

	public String queryHistory(String beginDate, String endDate,Teacher teacher, Integer page, Integer rows) {
		List<HistoryMessage> list = historyMessageDAO.query(beginDate, endDate, teacher, page, rows);
		Long total = historyMessageDAO.getTotalCount(beginDate, endDate, teacher);
		String[] properties = {"historyId","messageContent","receiveIDs","sendTime"};
		String jsonArray = JSONUtil.toJson(list, properties, total);
		return jsonArray;
	}

	public static void main(String[] args) {
		Integer[] contactsIdArray ={1,2,3,4,5,6};
		StringBuilder receiveIDs = new StringBuilder();
		for(Integer receiveID : contactsIdArray){
			receiveIDs.append(receiveID+",");
		}
		System.out.println(receiveIDs.substring(0, receiveIDs.length()-1));
		System.out.println(contactsIdArray.toString());
	}

	public String queryPromptHistory(String beginDate, String endDate, Integer messageType, Integer page, Integer rows) {
		List<HistoryMessage> list = historyMessageDAO.queryPromptHistory(beginDate, endDate,messageType, page, rows);
		Long total = historyMessageDAO.getPromptTotalCount(beginDate, endDate,messageType);
		String[] properties = {"historyId","messageContent","receiveIDs","sendTime","messageType","teacher.teacherName"};
		String jsonArray = JSONUtil.toJson(list, properties, total);
		return jsonArray;
	}
	
	public String queryPromptFail(){
		List<MessageSend> list = messageSendDAO.queryPromptFail();
		String[] properties = {"messageId","messageContent","receiveIDs","sendTime","messageType","teacher.teacherName"};
		String jsonArray = JSONUtil.toJson(list, properties);
		return jsonArray;
	}
}
