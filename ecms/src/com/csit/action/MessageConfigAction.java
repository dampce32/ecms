package com.csit.action;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.csit.model.MessageConfig;
import com.csit.service.MessageConfigService;
import com.csit.vo.ServiceResult;
import com.opensymphony.xwork2.ModelDriven;

/**
 * @Description:短信配置Action
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-7-17
 * @author jcf
 * @vesion 1.0
 */
@Controller
@Scope("prototype")
public class MessageConfigAction extends BaseAction implements ModelDriven<MessageConfig> {

	private static final long serialVersionUID = -4648466664583512735L;
	private static final Logger logger = Logger.getLogger(MessageConfigAction.class);
	private MessageConfig model = new MessageConfig();
	@Resource
	private MessageConfigService messageConfigService;

	@Override
	public MessageConfig getModel() {
		return model;
	}

	/**
	 * @Description: 分页查询短信配置
	 * @Create: 2013-6-6 下午02:55:55
	 * @author jcf
	 * @update logs
	 */
	public void query() {
		String jsonArray = messageConfigService.query(model);
		ajaxJson(jsonArray);
	}
	
	public void getMainSwitch() {
		ServiceResult result = new ServiceResult(false);
		try {
			result = messageConfigService.getMainSwitch();
		} catch (Exception e) {
			result.setMessage("获取短信总开关失败");
			logger.error("获取短信总开关失败", e);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	/**
	 * 
	 * @Description: 保存短信配置
	 * @Create: 2013-7-17 下午05:21:13
	 * @author jcf
	 * @update logs
	 */
	public void save() {
		ServiceResult result = new ServiceResult(false);
		try {
			String switchIds=getParameter("switchIds");
			String statuss=getParameter("statuss");
			String heads=getParameter("heads");
			String tails=getParameter("tails");
			result = messageConfigService.save(switchIds,statuss,heads,tails);
		} catch (Exception e) {
			result.setMessage("保存短信配置");
			logger.error("保存短信配置", e);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	
	/**
	 * 
	 * @Description: 获取开关状态 
	 * @Create: 2013-7-17 下午05:22:48
	 * @author yk
	 * @update logs
	 */
	public void switchStatus(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = messageConfigService.switchStatus(model.getSwitchId());
		} catch (Exception e) {
			result.setMessage("获取短信开关状态失败");
			logger.error("获取短信开关状态失败", e);
		}
		ajaxJson(result.toJSON());
	}
	/**
	 * @Description: 取得注册系统开关
	 * @Created: 2013-7-23 上午12:57:39
	 * @author lys
	 * @update logs
	 * @throws Exception
	 */
	public void getRegisterSwitchStatuss(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = messageConfigService.getRegisterSwitchStatuss();
		} catch (Exception e) {
			result.setMessage("取得注册系统开关失败");
			logger.error("取得注册系统开关失败", e);
		}
		ajaxJson(result.toJSON());
	}
	/**
	 * 
	 * @Description: 取得找回密码系统开关 
	 * @Create: 2013-7-24 下午05:51:59
	 * @author yk
	 * @update logs
	 */
	public void getResetSwitchStatus(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = messageConfigService.getResetSwitchStatus();
		} catch (Exception e) {
			result.setMessage("取得找回密码系统开关失败");
			logger.error("取得找回密码系统开关失败", e);
		}
		ajaxJson(result.toJSON());
	}
	/**
	 * 
	 * @Description: 发送验证短信 
	 * @Create: 2013-7-17 下午05:22:36
	 * @author yk
	 * @update logs
	 */
	public void sendValidateCode(){
		ServiceResult result = new ServiceResult(false);
		String mobilePhone = getParameter("mobilePhone");
		try {
			result = messageConfigService.sendValidateCode(mobilePhone);
		} catch (Exception e) {
			result.setMessage("发送验证短信失败");
			logger.error("发送验证短信失败", e);
		}
		setSession("msgValidateCode",result.getData().get("msgValidateCode"));
		
		ajaxJson(result.toJSON());
	}
	/**
	 * 
	 * @Description: 初始化账户信息 
	 * @Create: 2013-7-18 下午03:44:32
	 * @author yk
	 * @update logs
	 */
	public void initAccount(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = messageConfigService.initAccount();
		} catch (Exception e) {
			result.setMessage("获取账号信息失败");
			logger.error("获取账号信息失败", e);
		}
		ajaxJson(result.toJSON());
	}

	/**
	 * 
	 * @Description: 账户充值 
	 * @Create: 2013-7-19 上午09:25:52
	 * @author yk
	 * @update logs
	 */
	public void recharge(){
		ServiceResult result = new ServiceResult(false);
		String cardNo = getParameter("rechargeCode");
		String cardPass = getParameter("rechargePwd");
		try {
			result = messageConfigService.recharge(cardNo,cardPass);
		} catch (Exception e) {
			e.printStackTrace();
			result.setMessage("充值失败");
			logger.error("充值失败", e);
		}
		ajaxJson(result.toJSON());
	}
}
