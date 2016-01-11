package com.csit.dao;

import java.util.List;

import com.csit.model.MsgBlackList;
/**
 * @Description:短信黑字典DAO
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-7-22
 * @author lys
 * @vesion 1.0
 */
public interface MsgBlackListDAO extends BaseDAO<MsgBlackList,Integer>{
	/**
	 * @Description: 分页查询黑字
	 * @Created: 2013-7-22 下午8:06:20
	 * @author lys
	 * @update logs
	 * @throws Exception
	 * @param model
	 * @param page
	 * @param rows
	 * @return
	 */
	List<MsgBlackList> query(MsgBlackList model, Integer page, Integer rows);
	/**
	 * @Description: 统计黑字
	 * @Created: 2013-7-22 下午8:07:09
	 * @author lys
	 * @update logs
	 * @throws Exception
	 * @param model
	 * @return
	 */
	Long getTotalCount(MsgBlackList model);

}
