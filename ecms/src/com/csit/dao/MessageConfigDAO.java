package com.csit.dao;

import java.util.List;

import com.csit.model.MessageConfig;
/**
 * 
 * @Description: 短信配置DAO
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-7-17
 * @author yk
 * @vesion 1.0
 */
public interface MessageConfigDAO extends BaseDAO<MessageConfig, Integer> {

	List<MessageConfig> query(MessageConfig model);
}
