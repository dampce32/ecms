package com.csit.service;

import java.util.Map;

import com.csit.model.StudentCompetitionGroup;
import com.csit.vo.ServiceResult;
/**
 * 
 * @Description: 学生参赛组别Service
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-4
 * @author yk
 * @vesion 1.0
 */
public interface StudentCompetitionGroupService extends
		BaseService<StudentCompetitionGroup, Integer> {
	/**
	 * 
	 * @Description: 分页查询学生参赛组别 
	 * @Create: 2013-6-7 下午04:38:31
	 * @author yk
	 * @update logs
	 * @param model
	 * @param page
	 * @param rows
	 * @return
	 */
	String query(StudentCompetitionGroup model, Integer page, Integer rows);
	/**
	 * 
	 * @Description: 统计学生参赛组别 
	 * @Create: 2013-6-7 下午04:38:42
	 * @author yk
	 * @update logs
	 * @param model
	 * @return
	 */
	ServiceResult getTotalCount(StudentCompetitionGroup model);
	/**
	 * 
	 * @Description: 保存学生参赛组别 
	 * @Create: 2013-6-4 下午03:15:36
	 * @author yk
	 * @update logs
	 * @param model
	 * @return
	 */
	ServiceResult save(StudentCompetitionGroup model,Integer teacherId);
	/**
	 * 
	 * @Description: 批量修改审核状态 
	 * @Create: 2013-6-6 下午05:19:54
	 * @author yk
	 * @update logs
	 * @param ids
	 * @param model
	 * @return
	 */
	ServiceResult mulUpdateStatus(String ids, StudentCompetitionGroup model,Integer teacherId);
	/**
	 * 
	 * @Description: 查询登录学生的学生参赛组别 
	 * @Create: 2013-6-8 下午05:31:13
	 * @author yk
	 * @update logs
	 * @param model
	 * @param studentId
	 * @return
	 */
	ServiceResult queryByStu(Integer studentId);
	
	String query(Integer competitionId,Integer page, Integer rows, String stuIds);
	
	Map<String, Object> init(Integer studentId);
}
