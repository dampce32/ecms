package com.csit.dao;

import java.util.List;

import com.csit.model.School;

/**
 * @Description:学校DAO
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-7-22
 * @author jcf
 * @vesion 1.0
 */
public interface SchoolDAO extends BaseDAO<School,Integer>{
	/**
	 * @Description: 分页查询学校
	 * @Create: 2013-7-22 下午03:08:27
	 * @author jcf
	 * @update logs
	 * @param model
	 * @param page
	 * @param rows
	 * @return
	 */
	List<School> query(School model,Integer page, Integer rows);
	/**
	 * @Description: 统计学校
	 * @Create: 2013-7-22 下午03:08:35
	 * @author jcf
	 * @update logs
	 * @param model
	 * @return
	 */
	Long getTotalCount(School model);
	/**
	 * 
	 * @Description: combobox使用
	 * @Create: 2013-7-23 下午02:20:30
	 * @author jcf
	 * @update logs
	 * @param model
	 * @return
	 */
	List<School> queryCombobox(School model);

}
