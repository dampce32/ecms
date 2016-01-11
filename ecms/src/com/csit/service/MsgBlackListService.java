package com.csit.service;

import java.io.File;

import com.csit.model.MsgBlackList;
/**
 * @Description:黑字典Service
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-7-22
 * @author lys
 * @vesion 1.0
 */
public interface MsgBlackListService extends BaseService<MsgBlackList, Integer>{
	
	void uploadTxt(File file);
	/**
	 * @Description: 分页查询黑字
	 * @Created: 2013-7-22 下午8:04:46
	 * @author lys
	 * @update logs
	 * @throws Exception
	 * @param model
	 * @param page
	 * @param rows
	 * @return
	 */
	String query(MsgBlackList model, Integer page, Integer rows);

}
