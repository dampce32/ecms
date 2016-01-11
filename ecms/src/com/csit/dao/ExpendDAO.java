package com.csit.dao;

import java.util.List;

import com.csit.model.Expend;

/**
 * @Description:支出DAO
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-9
 * @author jcf
 * @vesion 1.0
 */
public interface ExpendDAO extends BaseDAO<Expend,Integer>{
	/**
	 * @Description: 分页查询支出
	 * @Create: 2013-6-9 上午08:58:56
	 * @author jcf
	 * @update logs
	 * @param model
	 * @param page
	 * @param rows
	 * @return
	 */
	List<Expend> query(Expend model,Integer page, Integer rows);
	/**
	 * @Description: 统计支出
	 * @Create: 2013-6-9 上午08:59:06
	 * @author jcf
	 * @update logs
	 * @param model
	 * @return
	 */
	Long getTotalCount(Expend model);
}
