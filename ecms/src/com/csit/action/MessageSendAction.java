package com.csit.action;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.csit.model.MessageSend;
import com.csit.model.Teacher;
import com.csit.service.MessageSendService;
import com.csit.thread.MessageClient;
import com.csit.vo.ServiceResult;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 
 * @Description:
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-26
 * @author longweier
 * @vesion 1.0
 */
@Controller("messageSendAction")
@Scope("prototype")
public class MessageSendAction extends BaseAction implements ModelDriven<MessageSend> {

	private static final long serialVersionUID = -2832227234868271556L;

	private static Logger logger = Logger.getLogger(MessageSendAction.class);
	
	@Resource
	private MessageSendService messageSendService;
	
	private MessageSend model = new MessageSend();
	
	public MessageSend getModel() {
		return model;
	}

	/**
	 * 
	 * @Description: 发送短信
	 * @param
	 * @Create: 2013-6-26 下午03:07:03
	 * @author longweier
	 * @update logs
	 * @return
	 * @throws Exception
	 */
	public void send(){
		ServiceResult result = new ServiceResult();
		try {
			Teacher teacher = new Teacher();
			teacher.setTeacherId((Integer) getSession(Teacher.LOGIN_TEACHERID));
			model.setTeacher(teacher);
			String names = getParameter("names");
			String mobilePhones = getParameter("mobilePhones");
			result = messageSendService.sendMessage(model,names,mobilePhones);
		} catch (Exception e) {
			logger.error("发送短信错误", e);
			result.setMessage("发送短信出错");
		}
		ajaxJson(result.toJSON());
	}
	
	/**
	 * 
	 * 
	 * @Description: 注册
	 * @param
	 * @Create: 2013-6-26 下午03:10:33
	 * @author longweier
	 * @update logs
	 * @return
	 * @throws Exception
	 */
	public void regist(){
		String softwareSerialNo = getParameter("softwareSerialNo");
		String key = getParameter("key");
		int ret = MessageClient.registClient(softwareSerialNo, key);
		ServiceResult result = new ServiceResult();
		if(ret==1){
			result.setIsSuccess(true);
		}else{
			result.setIsSuccess(false);
			result.setMessage("注册返回值为"+ret);
		}
		ajaxJson(result.toJSON());
	}
	
	/**
	 * 
	 * @Description: 注销
	 * @param
	 * @Create: 2013-6-28 上午10:29:13
	 * @author longweier
	 * @update logs
	 * @return
	 * @throws Exception
	 */
	public void logout(){
		String softwareSerialNo = getParameter("softwareSerialNo");
		String key = getParameter("key");
		int ret = MessageClient.logout(softwareSerialNo, key);
		ServiceResult result = new ServiceResult();
		if(ret==1){
			result.setIsSuccess(true);
		}else{
			result.setIsSuccess(false);
			result.setMessage("注销返回值为"+ret);
		}
		ajaxJson(result.toJSON());
	}
	
	/**
	 * 
	 * @Description: 查询失败记录
	 * @param
	 * @Create: 2013-6-28 上午10:43:56
	 * @author longweier
	 * @update logs
	 * @return
	 * @throws Exception
	 */
	public void queryFailed(){
		Teacher teacher = new Teacher();
		teacher.setTeacherId((Integer) getSession(Teacher.LOGIN_TEACHERID));
		model.setTeacher(teacher);
		String jsonString = messageSendService.queryFailed(model);
		ajaxJson(jsonString);
	}
	
	/**
	 * 
	 * @Description: 查询发送历史
	 * @param
	 * @Create: 2013-6-28 上午10:49:11
	 * @author longweier
	 * @update logs
	 * @return
	 * @throws Exception
	 */
	public void queryHistory(){
		Teacher teacher = new Teacher();
		teacher.setTeacherId((Integer) getSession(Teacher.LOGIN_TEACHERID));
		String jsonString = messageSendService.queryHistory(beginDate, endDate, teacher, page, rows);
		ajaxJson(jsonString);
	}
	
	/**
	 * 
	 * @Description: 获取接收
	 * @param
	 * @Create: 2013-6-28 上午10:50:37
	 * @author longweier
	 * @update logs
	 * @return
	 * @throws Exception
	 */
	public void getReceiver(){
		String jsonString = messageSendService.getReceiver(model.getReceiveIDs());
		ajaxJson(jsonString);
	}
	/**
	 * @Description: 查询提示短信发送历史
	 * @Created: 2013-7-20 下午10:55:16
	 * @author lys
	 * @update logs
	 * @throws Exception
	 */
	public void queryPromptHistory(){
		String jsonString = messageSendService.queryPromptHistory(beginDate, endDate,model.getMessageType(), page, rows);
		ajaxJson(jsonString);
	}
	
	public void queryPromptFail(){
		String jsonString = messageSendService.queryPromptFail();
		ajaxJson(jsonString);
	}
}
