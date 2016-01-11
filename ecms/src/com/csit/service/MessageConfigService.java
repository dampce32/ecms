package com.csit.service;

import com.csit.model.MessageConfig;
import com.csit.vo.ServiceResult;
/**
 * @Description:短信配置Service
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-7-17
 * @author jcf
 * @vesion 1.0
 */
public interface MessageConfigService extends BaseService<MessageConfig, Integer> {

	/**
	 * @Description: 查询短信配置
	 * @Create: 2013-7-17 下午03:57:57
	 * @author jcf
	 * @update logs
	 * @param model
	 * @return
	 */
	String query(MessageConfig model);
	/**
	 * 
	 * @Description: 获取开关状态 
	 * @Create: 2013-7-17 下午04:34:51
	 * @author yk
	 * @update logs
	 * @return
	 */
	ServiceResult switchStatus(Integer switchId);
	/**
	 * 
	 * @Description: 发送验证短信
	 * @Create: 2013-7-17 下午05:21:57
	 * @author yk
	 * @update logs
	 * @param mobilePhone
	 * @return
	 */
	ServiceResult sendValidateCode(String mobilePhone);
	ServiceResult getMainSwitch();
	/**
	 * @Description: 保存系统开关
	 * @Created: 2013-7-22 下午11:16:30
	 * @author lys
	 * @update logs
	 * @throws Exception
	 * @param swithIds
	 * @param statuss
	 * @param heads
	 * @param tails
	 * @return
	 */
	ServiceResult save(String swithIds,String statuss, String heads, String tails);
	/**
	 * 
	 * @Description: 初始化账户信息 
	 * @Create: 2013-7-18 下午03:44:10
	 * @author yk
	 * @update logs
	 * @return
	 */
	ServiceResult initAccount();
	/**
	 * 
	 * @Description: 账户充值 
	 * @Create: 2013-7-19 上午09:25:23
	 * @author yk
	 * @update logs
	 * @return
	 */
	ServiceResult recharge(String cardNo, String cardPass);
	/**
	 * @Description: 取得注册系统开关
	 * @Created: 2013-7-23 上午1:08:09
	 * @author lys
	 * @update logs
	 * @throws Exception
	 * @return
	 */
	ServiceResult getRegisterSwitchStatuss();
	/**
	 * 
	 * @Description: 取得找回密码系统开关 
	 * @Create: 2013-7-24 下午05:53:24
	 * @author yk
	 * @update logs
	 * @return
	 */
	ServiceResult getResetSwitchStatus();
}
