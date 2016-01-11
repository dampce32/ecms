package com.csit.service;

import com.csit.model.SerialNumber;
/**
 * @Description:序列表Service
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-4-28
 * @Author lys
 */
public interface SerialNumberService extends BaseService<SerialNumber, String>{
	/**
	 * @Description: 取得表的下一个序列值
	 * @Created Time: 2013-4-28 上午9:25:01
	 * @Author lys
	 * @param tableName
	 * @return
	 */
	public Integer getNextSerial(String tableName);
}
