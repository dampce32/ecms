package com.csit.dao;

import java.util.List;

import com.csit.model.Recrej;

/**
 * @Description:入库出库DAO
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-6
 * @author jcf
 * @vesion 1.0
 */
public interface RecrejDAO extends BaseDAO<Recrej,Integer>{
	/**
	 * @Description: 分页查询入库出库
	 * @Create: 2013-6-6 下午04:13:39
	 * @author jcf
	 * @update logs
	 * @param model
	 * @param page
	 * @param rows
	 * @return
	 */
	List<Recrej> query(Recrej model,Integer page, Integer rows);
	/**
	 * @Description: 统计入库出库
	 * @Create: 2013-6-6 下午04:13:49
	 * @author jcf
	 * @update logs
	 * @param model
	 * @return
	 */
	Long getTotalCount(Recrej model);

}
