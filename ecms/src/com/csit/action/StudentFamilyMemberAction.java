package com.csit.action;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.csit.model.Student;
import com.csit.model.StudentFamilyMember;
import com.csit.service.StudentFamilyMemberService;
import com.opensymphony.xwork2.ModelDriven;
/**
 * 
 * @Description: 学生家庭成员Action
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-13
 * @author yk
 * @vesion 1.0
 */
@Controller
@Scope("prototype")
public class StudentFamilyMemberAction extends BaseAction implements ModelDriven<StudentFamilyMember> {

	private static final long serialVersionUID = -1271646513736275235L;
	private static final Logger logger = Logger.getLogger(CityAction.class);
	StudentFamilyMember model = new StudentFamilyMember();
	
	@Resource
	private StudentFamilyMemberService studentFamilyMemberService;
	
	@Override
	public StudentFamilyMember getModel() {
		return model;
	}

	/**
	 * 
	 * @Description: 查询学生家庭成员 
	 * @Create: 2013-6-13 上午09:16:35
	 * @author yk
	 * @update logs
	 */
	public void init(){
		if(model.getStudent().getStudentId()==null){
			Integer studentId = getIntegerSession(Student.LOGIN_ID);
			Student student = new Student();
			student.setStudentId(studentId);
			model.setStudent(student);
		}
		String familyString = null;
		try {
			familyString = studentFamilyMemberService.init(model.getStudent());
		} catch (Exception e) {
			logger.error("查询学生家庭成员失败", e);
		}
		ajaxJson(familyString);
	}
	
}
