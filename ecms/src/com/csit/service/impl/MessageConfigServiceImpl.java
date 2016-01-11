package com.csit.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.csit.dao.HistoryMessageDAO;
import com.csit.dao.MessageConfigDAO;
import com.csit.dao.MessageSendDAO;
import com.csit.dao.RightDAO;
import com.csit.dao.StudentDAO;
import com.csit.model.HistoryMessage;
import com.csit.model.MessageConfig;
import com.csit.model.MessageSend;
import com.csit.model.Right;
import com.csit.model.Student;
import com.csit.service.MessageConfigService;
import com.csit.thread.MessageClient;
import com.csit.util.JSONUtil;
import com.csit.util.StringUtil;
import com.csit.vo.ServiceResult;
import com.octo.captcha.component.word.wordgenerator.RandomWordGenerator;
import com.octo.captcha.component.word.wordgenerator.WordGenerator;

/**
 * @Description:短信配置Service实现类
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-7-17
 * @author jcf
 * @vesion 1.0
 */
@Service
public class MessageConfigServiceImpl extends BaseServiceImpl<MessageConfig, Integer> implements
		MessageConfigService {

	@Resource
	private MessageConfigDAO messageConfigDAO;
	@Resource
	private HistoryMessageDAO historyMessageDAO;
	@Resource
	private StudentDAO studentDAO;
	@Resource
	private MessageSendDAO messageSendDAO;
	@Resource
	private RightDAO rightDAO;

	/*
	 * (non-Javadoc)
	 * @see com.csit.service.MessageConfigService#query(com.csit.model.MessageConfig,
	 * java.lang.Integer, java.lang.Integer)
	 */
	public String query(MessageConfig model) {
		List<MessageConfig> list = messageConfigDAO.query(model);

		String[] properties = { "switchId", "switchName", "status",
				"head", "fixedPart", "tail", "isMainSwitch","note"};

		return JSONUtil.toJson(list, properties);
	}

	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.MessageConfigService#switchStatus(java.lang.Integer)
	 */
	@Override
	public ServiceResult switchStatus(Integer switchId) {
		ServiceResult result = new ServiceResult(false);
		MessageConfig mainSwitch = messageConfigDAO.load(1);
		MessageConfig theSwitch = messageConfigDAO.load(switchId);
		if(mainSwitch.getStatus()==1&&theSwitch.getStatus()==1){
			result.setIsSuccess(true);
		}
		return result;
	}

	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.MessageConfigService#sendValidateCode(java.lang.String)
	 */
	@Override
	public ServiceResult sendValidateCode(String mobilePhone) {
		ServiceResult result = new ServiceResult(false);
		
		Student oldStudentByMobilePhone = studentDAO.load("mobilePhone", mobilePhone);
		if(oldStudentByMobilePhone!=null&&StringUtils.isNotEmpty(oldStudentByMobilePhone.getMobilePhone())){
			result.setMessage("手机号码已被注册");
			return result;
		}
		
		String[] sendQueue = {mobilePhone};
		
		//获得内容为0-9的6位随机数
		WordGenerator wordGenerator = new RandomWordGenerator("0123456789");
		String validateCode = wordGenerator.getWord(6);
		
		MessageConfig messageConfig = messageConfigDAO.load(21);
		StringBuilder contentSB = new StringBuilder();
		if(StringUtils.isNotEmpty(messageConfig.getHead())){
			contentSB.append(messageConfig.getHead());
		}
		contentSB.append("您的系统注册验证码是："+validateCode + "。");
		if(StringUtils.isNotEmpty(messageConfig.getTail())){
			contentSB.append(messageConfig.getTail());
		}
		String content = contentSB.toString();
		
		Integer info = MessageClient.sendSMS(sendQueue, content);
		if(info==0){
			HistoryMessage historyMessage = new HistoryMessage();
			
			historyMessage.setMessageContent(content);
			historyMessage.setMessageType(2);
			historyMessage.setReceiveIDs(mobilePhone);
			historyMessage.setSendTime(new Date());
			
			historyMessageDAO.save(historyMessage);
			
		}else{
			MessageSend messageSend = new MessageSend();
			
			messageSend.setMessageContent(content);
			messageSend.setMessageType(2);
			messageSend.setReceiveIDs(mobilePhone);
			messageSend.setSendTime(new Date());
			messageSend.setErrorCount(10);
			
			messageSendDAO.save(messageSend);
		}
		
		result.addData("msgValidateCode", validateCode);
		result.setIsSuccess(true);
		return result;
	}

	@Override
	public ServiceResult getMainSwitch() {
		ServiceResult result = new ServiceResult(false);
		List<MessageConfig> messageConfigList = messageConfigDAO.query("isMainSwitch", 1);
		
		String[] properties = { "switchId", "switchName", "status"};
		String data=JSONUtil.toJsonWithoutRows(messageConfigList, properties);
		result.addData("messageConfig", data);
		result.setIsSuccess(true);
		return result;
	}

	@Override
	public ServiceResult save(String swithIds,String statuss, String heads, String tails) {
		ServiceResult result = new ServiceResult(false);
		String[] swithIdArray=StringUtil.split(swithIds);
		String[] statusArrs=StringUtil.split(statuss);
		String[] headArrs=StringUtil.split(heads);
		String[] tailArrs=StringUtil.split(tails);
		
		for (int i = 0; i < swithIdArray.length; i++) {
			String swithId = swithIdArray[i];
			String status = statusArrs[i];
			String head = headArrs[i];
			String tail = tailArrs[i];
			
			MessageConfig oldMessageConfig = messageConfigDAO.load(Integer.parseInt(swithId));
			oldMessageConfig.setStatus(Integer.parseInt(status));
			oldMessageConfig.setHead(head);
			oldMessageConfig.setTail(tail);
			if("11".equals(swithId)){//普通短信开头
				if("1".equals(status)&&"1".equals(statusArrs[0])){//总开关和普通短信开关都开启
					Right oldRight = rightDAO.load("000100010");
					oldRight.setState(true);
				}else {
					Right oldRight = rightDAO.load("000100010");
					oldRight.setState(false);
				}
			}else if("12".equals(swithId)){//普通邮件开头
				if("1".equals(status)&&"1".equals(statusArrs[1])){
					Right oldRight = rightDAO.load("000100020");
					oldRight.setState(true);
				}else {
					Right oldRight = rightDAO.load("000100020");
					oldRight.setState(false);
				}
			}
		}
		result.setIsSuccess(true);
		return result;
	}

	@Override
	public ServiceResult initAccount() {
		ServiceResult result = new ServiceResult(false);
		Properties props = new Properties();
		InputStream is = this.getClass().getClassLoader().getResourceAsStream("systemConfig.properties");
		try {
			props.load(is);
		} catch (IOException e) {
			e.printStackTrace();
			result.setMessage("获取序列号失败");
			return result;
		}
		String softwareSerialNo = props.get("softwareSerialNo").toString();
		
		int remainingCount = MessageClient.getRemainingCount();
		
		result.addData("softwareSerialNo", softwareSerialNo);
		result.addData("remainingCount", remainingCount);
		result.setIsSuccess(true);
		return result;
	}

	@Override
	public ServiceResult recharge(String cardNo, String cardPass) {
		ServiceResult result = new ServiceResult(false);
		
		int info = MessageClient.recharge(cardNo, cardPass);
		if(info == 0){
			result.setIsSuccess(true);  
		}else{
			result.setMessage("充值失败");
		}
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.MessageConfigService#getRegisterSwitchStatuss()
	 */
	@Override
	public ServiceResult getRegisterSwitchStatuss() {
		ServiceResult result = new ServiceResult(false);
		MessageConfig msgMainSwitch = messageConfigDAO.load(1);
		MessageConfig mailMainSwitch = messageConfigDAO.load(2);
		MessageConfig msgSwitch = messageConfigDAO.load(21);
		MessageConfig mailSwitch = messageConfigDAO.load(22);
		
		if(msgMainSwitch.getStatus()==1&&msgSwitch.getStatus()==1){
			result.addData("msgSwitch", 1);
		}else{
			result.addData("msgSwitch", 0);
		}
		if(mailMainSwitch.getStatus()==1&&mailSwitch.getStatus()==1){
			result.addData("mailSwitch", 1);
		}else{
			result.addData("mailSwitch", 0);
		}
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.MessageConfigService#getResetSwitchStatus()
	 */
	@Override
	public ServiceResult getResetSwitchStatus() {
		ServiceResult result = new ServiceResult(false);
		MessageConfig msgMainSwitch = messageConfigDAO.load(1);
		MessageConfig mailMainSwitch = messageConfigDAO.load(2);
		MessageConfig msgSwitch = messageConfigDAO.load(41);
		MessageConfig mailSwitch = messageConfigDAO.load(42);
		
		if(msgMainSwitch.getStatus()==1&&msgSwitch.getStatus()==1){
			result.addData("msgSwitch", 1);
		}else{
			result.addData("msgSwitch", 0);
		}
		if(mailMainSwitch.getStatus()==1&&mailSwitch.getStatus()==1){
			result.addData("mailSwitch", 1);
		}else{
			result.addData("mailSwitch", 0);
		}
		result.setIsSuccess(true);
		return result;
	}

}
