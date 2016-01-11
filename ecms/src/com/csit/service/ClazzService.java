package com.csit.service;

import com.csit.model.Clazz;
import com.csit.vo.ServiceResult;

/**
 * 
 * @Description: 年级Service
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-7-22
 * @author yk
 * @vesion 1.0
 */
public interface ClazzService extends BaseService<Clazz, Integer> {
	/**
	 * 
	 * @Description: 保存班级 
	 * @Create: 2013-7-22 下午03:16:30
	 * @author yk
	 * @update logs
	 * @param model
	 * @return
	 */
	ServiceResult save(Clazz model);
	/**
	 * 
	 * @Description: 分页查询班级 
	 * @Create: 2013-7-22 下午03:16:38
	 * @author yk
	 * @update logs
	 * @param model
	 * @param page
	 * @param rows
	 * @return
	 */
	ServiceResult query(Clazz model, Integer page, Integer rows);
	/**
	 * 
	 * @Description: 统计班级 
	 * @Create: 2013-7-22 下午03:16:47
	 * @author yk
	 * @update logs
	 * @param model
	 * @return
	 */
	ServiceResult getTotalCount(Clazz model);
	/**
	 * 
	 * @Description: 批量删除班级 
	 * @Create: 2013-7-22 下午03:17:00
	 * @author yk
	 * @update logs
	 * @param ids
	 * @return
	 */
	ServiceResult mulDelete(String ids);
	/**
	 * 
	 * @Description: 批量修改班级状态 
	 * @Create: 2013-7-22 下午03:17:09
	 * @author yk
	 * @update logs
	 * @param ids
	 * @param model
	 * @return
	 */
	ServiceResult mulUpdateStatus(String ids, Clazz model);
	/**
	 * 
	 * @Description: 更新班级顺序 
	 * @Create: 2013-7-22 下午03:17:23
	 * @author yk
	 * @update logs
	 * @param clazzId
	 * @param updateClazzId
	 * @return
	 */
	ServiceResult updateArray(String clazzId, String updateClazzId);
	
	String selectQuery(Clazz model,Integer schoolGradeId,Integer page,
			Integer rows);
}
