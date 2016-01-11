package com.csit.dao;

import java.util.List;

import com.csit.model.StudentFamilyMember;

/**
 * 
 * @Description: 学生家庭成员DAO
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-5-31
 * @author yk
 * @vesion 1.0
 */
public interface StudentFamilyMemberDAO extends
		BaseDAO<StudentFamilyMember, Integer> {
	/**
	 * 
	 * @Description: 分页查询学生家庭成员
	 * @Create: 2013-6-13 上午08:56:25
	 * @author yk
	 * @update logs
	 * @param model
	 * @param page
	 * @param rows
	 * @return
	 */
	List<StudentFamilyMember> query(StudentFamilyMember model, Integer page, Integer rows);
	/**
	 * 
	 * @Description: 统计学生家庭成员
	 * @Create: 2013-6-13 上午08:56:38
	 * @author yk
	 * @update logs
	 * @param model
	 * @return
	 */
	Long getTotalCount(StudentFamilyMember model);
}
