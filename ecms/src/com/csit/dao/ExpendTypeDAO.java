package com.csit.dao;

import java.util.List;

import com.csit.model.ExpendType;

/**
 * @Description:支出类型DAO
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-9
 * @author jcf
 * @vesion 1.0
 */
public interface ExpendTypeDAO extends BaseDAO<ExpendType,Integer>{
	/**
	 * @Description: 分页查询支出类型
	 * @Create: 2013-6-9 上午08:58:56
	 * @author jcf
	 * @update logs
	 * @param model
	 * @param page
	 * @param rows
	 * @return
	 */
	List<ExpendType> query(ExpendType model,Integer page, Integer rows);
	/**
	 * @Description: 统计支出类型
	 * @Create: 2013-6-9 上午08:59:06
	 * @author jcf
	 * @update logs
	 * @param model
	 * @return
	 */
	Long getTotalCount(ExpendType model);
	/**
	 * @Description: 获得最大顺序值
	 * @Create: 2013-6-9 上午08:59:20
	 * @author jcf
	 * @update logs
	 * @return
	 */
	Integer getMaxArray();
	/**
	 * @Description: 更新权限顺序
	 * @Create: 2013-6-9 上午08:59:35
	 * @author jcf
	 * @update logs
	 * @param expendTypeId
	 * @param updateExpendTypeId
	 */
	void updateArray(Integer expendTypeId, Integer updateExpendTypeId);
	/**
	 * @Description: combobox使用
	 * @Create: 2013-6-9 上午08:59:44
	 * @author jcf
	 * @update logs
	 * @param model
	 * @return
	 */
	List<ExpendType> queryCombobox(ExpendType model);

}
