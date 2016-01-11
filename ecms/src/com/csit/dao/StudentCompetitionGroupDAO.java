package com.csit.dao;

import java.util.List;

import com.csit.model.StudentCompetitionGroup;
/**
 * 
 * @Description: 学生参赛组别DAO
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-4
 * @author yk
 * @vesion 1.0
 */
public interface StudentCompetitionGroupDAO extends
		BaseDAO<StudentCompetitionGroup, Integer> {
	/**
	 * 
	 * @Description: 分页查询学生参赛组别
	 * @Create: 2013-6-7 下午04:34:27
	 * @author yk
	 * @update logs
	 * @param model
	 * @param page
	 * @param rows
	 * @return
	 */
	List<StudentCompetitionGroup> query(StudentCompetitionGroup model, Integer page, Integer rows);
	/**
	 * 
	 * @Description: 统计学生参赛组别
	 * @Create: 2013-6-7 下午04:34:48
	 * @author yk
	 * @update logs
	 * @param model
	 * @return
	 */
	Long getTotalCount(StudentCompetitionGroup model);
	
	List<StudentCompetitionGroup> query(Integer competitionId,Integer page, Integer rows, Integer[] stuIdArr);
	
	Long getTotalCount(Integer competitionId, Integer[] stuIdArr);
	
	String getMaxExamCode(Integer competitionGroupId);

}
