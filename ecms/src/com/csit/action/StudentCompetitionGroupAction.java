package com.csit.action;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.csit.dao.SystemConfigDAO;
import com.csit.model.Student;
import com.csit.model.StudentCompetitionGroup;
import com.csit.model.SystemConfig;
import com.csit.model.Teacher;
import com.csit.service.StudentCompetitionGroupService;
import com.csit.vo.ServiceResult;
import com.opensymphony.xwork2.ModelDriven;
/**
 * 
 * @Description: 学生参赛组别Action
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-4
 * @author yk
 * @vesion 1.0
 */
@Controller
@Scope("prototype")
public class StudentCompetitionGroupAction extends BaseAction implements
		ModelDriven<StudentCompetitionGroup> {

	private static final long serialVersionUID = -7759889762529921850L;
	private static final Logger logger = Logger.getLogger(StudentCompetitionGroupAction.class);
	StudentCompetitionGroup model = new StudentCompetitionGroup();
	
	@Resource
	private StudentCompetitionGroupService studentCompetitionGroupService;
	@Resource
	SystemConfigDAO systemConfigDAO;
	@Override
	public StudentCompetitionGroup getModel() {
		return model;
	}

	/**
	 * 
	 * @Description: 分页查询学生参赛组别 
	 * @Create: 2013-6-7 下午04:44:51
	 * @author yk
	 * @update logs
	 */
	public void query(){
		String jsonArray = studentCompetitionGroupService.query(model, page, rows);
		ajaxJson(jsonArray);
	}
	/**
	 * 
	 * @Description: 统计学生参赛组别 
	 * @Create: 2013-6-7 下午04:45:02
	 * @author yk
	 * @update logs
	 */
	public void getTotalCount(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = studentCompetitionGroupService.getTotalCount(model);
		} catch (Exception e) {
			result.setMessage("统计学生参赛组别失败");
			logger.error("统计学生参赛组别失败", e);
			result.setIsSuccess(false);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	
	/**
	 * 
	 * @Description: 保存学生参赛组别
	 * @Create: 2013-6-4 下午03:13:38
	 * @author yk
	 * @update logs
	 */
	public void save(){
		ServiceResult result = new ServiceResult(false);
		Integer teacherId=-1;
		if(model.getStudent()==null){
			Integer studentId = getIntegerSession(Student.LOGIN_ID);
			Student student = new Student();
			student.setStudentId(studentId);
			model.setStudent(student);
		}else {
			teacherId = getIntegerSession(Teacher.LOGIN_TEACHERID);
		}
		try {
			result = studentCompetitionGroupService.save(model,teacherId);
		} catch (Exception e) {
			result.setMessage("保存学生参赛组别失败");
			logger.error("保存学生参赛组别失败", e);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	/**
	 * 
	 * @Description: 修改审核装状态
	 * @Create: 2013-6-6 下午05:25:39
	 * @author yk
	 * @update logs
	 */
	public void mulUpdateStatus(){
		ServiceResult result = new ServiceResult(false);
		try {
			Integer teacherId = getIntegerSession(Teacher.LOGIN_TEACHERID);
			result = studentCompetitionGroupService.mulUpdateStatus(ids,model,teacherId);
		} catch (Exception e) {
			result.setMessage("批量修改报名审核状态失败");
			logger.error("批量修改报名审核状态失败", e);
			result.setIsSuccess(false);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	/**
	 * 
	 * @Description: 查询登录学生的学生参赛组别
	 * @Create: 2013-6-8 下午05:27:00
	 * @author yk
	 * @update logs
	 */
	public void queryByStu(){
		ServiceResult result = new ServiceResult(false);
		Integer studentId = getIntegerSession("studentId");
		try {
			result = studentCompetitionGroupService.queryByStu(studentId);
		} catch (Exception e) {
			result.setMessage("查询学生参赛组别失败");
			logger.error("查询学生参赛组别失败", e);
			result.setIsSuccess(false);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	public void queryByPay(){
		String stuIds=getParameter("stuIds");
		Integer competitionId=Integer.parseInt(getParameter("competitionId"));
		String jsonArray = studentCompetitionGroupService.query(competitionId, page, rows,stuIds);
		ajaxJson(jsonArray);
	}
	
	public String init(){
		Integer studentId = getIntegerSession(Student.LOGIN_ID);
		Map<String, Object> map;
		try {
			map = studentCompetitionGroupService.init(studentId);
			SystemConfig systemConfig = systemConfigDAO.load(1);
			request.setAttribute("infoType", 1);
			request.setAttribute("studentCompetitionGroupList", map.get("studentCompetitionGroupList"));
			request.setAttribute("systemConfig", systemConfig);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
}
