package com.csit.service;

import com.csit.model.Nation;
import com.csit.vo.ServiceResult;

/**
 * 
 * @Description: 民族Service
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-5-28
 * @author yk
 * @vesion 1.0
 */
public interface NationService extends BaseService<Nation, Integer> {
	/**
	 * 
	 * @Description: 保存民族
	 * @Create: 2013-5-28 下午05:33:39
	 * @author yk
	 * @update logs
	 * @param model
	 * @param teacherId
	 * @return
	 */
	ServiceResult save(Nation model);
	/**
	 * 
	 * @Description: 分页查询民族 
	 * @Create: 2013-5-28 下午05:33:50
	 * @author yk
	 * @update logs
	 * @param model
	 * @param page
	 * @param rows
	 * @return
	 */
	ServiceResult query(Nation model, Integer page, Integer rows);
	/**
	 * 
	 * @Description: 统计民族 
	 * @Create: 2013-5-28 下午05:34:02
	 * @author yk
	 * @update logs
	 * @param model
	 * @return
	 */
	ServiceResult getTotalCount(Nation model);
	/**
	 * 
	 * @Description: 批量删除民族 
	 * @Create: 2013-5-28 下午05:34:12
	 * @author yk
	 * @update logs
	 * @param ids
	 * @return
	 */
	ServiceResult mulDelete(String ids);
	/**
	 * 
	 * @Description: 批量修改民族状态 
	 * @Create: 2013-5-28 下午05:34:22
	 * @author yk
	 * @update logs
	 * @param ids
	 * @param model
	 * @return
	 */
	ServiceResult mulUpdateState(String ids, Nation model);
	/**
	 * 
	 * @Description: 更新民族顺序 
	 * @Create: 2013-5-29 上午09:23:17
	 * @author yk
	 * @update logs
	 * @param nationId
	 * @param updateRightId
	 * @return
	 */
	ServiceResult updateArray(String nationId, String updateNationId);
	/**
	 * 
	 * @Description: combobox查询民族 
	 * @Create: 2013-5-29 上午10:53:56
	 * @author yk
	 * @update logs
	 * @return
	 */
	String queryCombobox();
}
