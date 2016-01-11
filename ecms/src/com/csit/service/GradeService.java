package com.csit.service;

import com.csit.model.Grade;
import com.csit.vo.ServiceResult;

/**
 * 
 * @Description: 年级Service
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-7-22
 * @author yk
 * @vesion 1.0
 */
public interface GradeService extends BaseService<Grade, Integer> {
	/**
	 * 
	 * @Description: 保存年级 
	 * @Create: 2013-7-22 下午03:09:11
	 * @author yk
	 * @update logs
	 * @param model
	 * @return
	 */
	ServiceResult save(Grade model);
	/**
	 * 
	 * @Description: 分页查询年级 
	 * @Create: 2013-7-22 下午02:58:43
	 * @author yk
	 * @update logs
	 * @param model
	 * @param page
	 * @param rows
	 * @return
	 */
	ServiceResult query(Grade model, Integer page, Integer rows);
	/**
	 * 
	 * @Description: 统计年级 
	 * @Create: 2013-7-22 下午02:58:36
	 * @author yk
	 * @update logs
	 * @param model
	 * @return
	 */
	ServiceResult getTotalCount(Grade model);
	/**
	 * 
	 * @Description: 批量删除年级 
	 * @Create: 2013-7-22 下午02:58:24
	 * @author yk
	 * @update logs
	 * @param ids
	 * @return
	 */
	ServiceResult mulDelete(String ids);
	/**
	 * 
	 * @Description: 批量修改年级状态 
	 * @Create: 2013-7-22 下午02:58:04
	 * @author yk
	 * @update logs
	 * @param ids
	 * @param model
	 * @return
	 */
	ServiceResult mulUpdateStatus(String ids, Grade model);
	/**
	 * 
	 * @Description: 更新年级顺序 
	 * @Create: 2013-7-22 下午02:57:52
	 * @author yk
	 * @update logs
	 * @param gradeId
	 * @param updateGradeId
	 * @return
	 */
	ServiceResult updateArray(String gradeId, String updateGradeId);
	/**
	 * 
	 * @Description: 分页查询未被选择的年级
	 * @Create: 2013-7-22 下午10:13:15
	 * @author jcf
	 * @update logs
	 * @param model
	 * @param schoolId
	 * @param page
	 * @param rows
	 * @return
	 */
	String selectQuery(Grade model,Integer schoolId,Integer page,
			Integer rows);
}
