package com.csit.dao;

import java.util.List;

import com.csit.model.Pay;

/**
 * @Description:缴费DAO
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-9
 * @author jcf
 * @vesion 1.0
 */
public interface PayDAO extends BaseDAO<Pay,Integer>{
	/**
	 * @Description: 分页查询缴费
	 * @Create: 2013-6-9 上午08:58:56
	 * @author jcf
	 * @update logs
	 * @param model
	 * @param page
	 * @param rows
	 * @return
	 */
	List<Pay> query(Pay model,Integer page, Integer rows);
	/**
	 * @Description: 统计缴费
	 * @Create: 2013-6-9 上午08:59:06
	 * @author jcf
	 * @update logs
	 * @param model
	 * @return
	 */
	Long getTotalCount(Pay model);
}
