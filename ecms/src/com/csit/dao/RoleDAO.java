package com.csit.dao;

import java.util.List;

import com.csit.model.Role;
/**
 * @Description:角色DAO
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-4-17
 * @Author lys
 */
public interface RoleDAO extends BaseDAO<Role,Integer>{
	/**
	 * @Description: 分页查询角色
	 * @Created Time: 2013-4-17 上午10:50:58
	 * @Author lys
	 * @param page
	 * @param rows
	 * @param model
	 * @return
	 */
	List<Role> query(Integer page, Integer rows, Role model);
	/**
	 * @Description: 统计角色
	 * @Created Time: 2013-4-17 上午10:51:16
	 * @Author lys
	 * @param model
	 * @return
	 */
	Long count(Role model);

}
