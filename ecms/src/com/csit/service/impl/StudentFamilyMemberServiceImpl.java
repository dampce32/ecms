package com.csit.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.csit.dao.StudentFamilyMemberDAO;
import com.csit.model.Student;
import com.csit.model.StudentFamilyMember;
import com.csit.service.StudentFamilyMemberService;
import com.csit.util.JSONUtil;

/**
 * 
 * @Description: 学生家庭成员Service实现类
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-13
 * @author yk
 * @vesion 1.0
 */
@Service
public class StudentFamilyMemberServiceImpl extends
		BaseServiceImpl<StudentFamilyMember, Integer> implements
		StudentFamilyMemberService {

	@Resource
	StudentFamilyMemberDAO studentFamilyMemberDAO;

	@Override
	public String init(Student student) {
		StudentFamilyMember model = studentFamilyMemberDAO.load("student",student);
		String[] properties = {"familyMember","familyMemberName","familyMemberAge",
				"workUnitsAndPosition","phone","email"};
		String jsonString = JSONUtil.toJson(model,properties);
		return jsonString;
	}


}
