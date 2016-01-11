package com.csit.service;


import com.csit.model.Student;
import com.csit.model.StudentFamilyMember;

/**
 * 
 * @Description: 学生家庭成员 
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-13
 * @author yk
 * @vesion 1.0
 */
public interface StudentFamilyMemberService extends
		BaseService<StudentFamilyMember, Integer> {
	
	/**
	 * 
	 * @Description: 查询学生家庭成员 
	 * @Create: 2013-6-13 上午09:05:50
	 * @author yk
	 * @update logs
	 * @param model
	 * @param page
	 * @param rows
	 * @return
	 */
	String init(Student student);
	
}
