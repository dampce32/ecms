package com.csit.dao;

import java.util.List;

import com.csit.model.Goods;

/**
 * @Description:教材DAO
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-6
 * @author jcf
 * @vesion 1.0
 */
public interface GoodsDAO extends BaseDAO<Goods,Integer>{
	/**
	 * @Description: 分页查询教材
	 * @Create: 2013-6-6 下午02:45:23
	 * @author jcf
	 * @update logs
	 * @param model
	 * @param page
	 * @param rows
	 * @return
	 */
	List<Goods> query(Goods model,Integer page, Integer rows);
	/**
	 * @Description: 统计教材
	 * @Create: 2013-6-6 下午02:45:35
	 * @author jcf
	 * @update logs
	 * @param model
	 * @return
	 */
	Long getTotalCount(Goods model);
	/**
	 * @Description: combobox使用
	 * @Create: 2013-6-6 下午02:46:26
	 * @author jcf
	 * @update logs
	 * @param model
	 * @return
	 */
	List<Goods> queryCombobox(Goods model);

}
