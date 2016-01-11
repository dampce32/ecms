package com.csit.dao;

import java.util.List;

import com.csit.model.Nation;
/**
 * 
 * @Description: 民族DAO
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-5-28
 * @author yk
 * @vesion 1.0
 */
public interface NationDAO extends BaseDAO<Nation, Integer> {
	/**
	 * 
	 * @Description: 分页查询民族 
	 * @Create: 2013-5-28 下午05:27:37
	 * @author yk
	 * @update logs
	 * @param model
	 * @param page
	 * @param rows
	 * @return
	 */
	List<Nation> query(Nation model, Integer page, Integer rows);
	/**
	 * 
	 * @Description: 统计民族 
	 * @Create: 2013-5-28 下午05:27:51
	 * @author yk
	 * @update logs
	 * @param model
	 * @return
	 */
	Long getTotalCount(Nation model);
	/**
	 * 
	 * @Description: 取得民族的最大顺序值 
	 * @Create: 2013-5-29 上午09:16:33
	 * @author yk
	 * @update logs
	 * @param rightId
	 * @return
	 */
	Integer getMaxArray();
	/**
	 * 
	 * @Description: 更新民族顺序
	 * @Create: 2013-5-29 上午09:17:02
	 * @author yk
	 * @update logs
	 * @param nationId
	 * @param updateNationId
	 */
	void updateArray(String nationId, String updateNationId);
	/**
	 * 
	 * @Description: combobox查询民族
	 * @Create: 2013-7-3 上午10:41:45
	 * @author yk
	 * @update logs
	 * @return
	 */
	List<Nation> queryCombobox();
}
