package com.csit.action;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.csit.model.Subject;
import com.csit.model.Teacher;
import com.csit.service.SubjectService;
import com.csit.vo.ServiceResult;
import com.opensymphony.xwork2.ModelDriven;

/**
 * @Description:试题Action
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-4-26
 * @author jcf
 * @vesion 1.0
 */
@Controller
@Scope("prototype")
public class SubjectAction extends BaseAction implements ModelDriven<Subject> {

	private static final long serialVersionUID = -4648466664583512735L;
	private static final Logger logger = Logger.getLogger(SubjectAction.class);
	private Subject model = new Subject();
	@Resource
	private SubjectService subjectService;

	@Override
	public Subject getModel() {
		return model;
	}

	/**
	 * @Description: 保存试题
	 * @Create: 2013-4-26 下午02:13:21
	 * @author jcf
	 * @update logs
	 */
	public void save() {
		ServiceResult result = new ServiceResult(false);
		try {
			Integer teacherId=(Integer) getSession().get(Teacher.LOGIN_TEACHERID);
			Teacher teacher = new Teacher();
			teacher.setTeacherId(teacherId);
			if(model.getSubjectId()==null){
				model.setPublishTeacher(teacher);
			}
			model.setTeacher(teacher);
			String optionsStr=getParameter("optionsStr");
			result = subjectService.save(model,optionsStr);
		} catch (Exception e) {
			result.setMessage("保存试题失败");
			logger.error("保存试题失败", e);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}

	/**
	 * 
	 * @Description: 批量删除试题
	 * @Create: 2013-5-3 上午10:30:07
	 * @author yk
	 * @update logs
	 */
	public void mulDelete(){
		ServiceResult result = new ServiceResult(false);	
		try {
			result = subjectService.mulDelete(ids);
		} catch (Throwable e) {
			result.setMessage("批量删除试题失败");
			logger.error("批量删除试题失败", e);
		}
		ajaxJson(result.toJSON());
	}

	/**
	 * @Description: 分页查询试题
	 * @Create: 2013-4-26 下午02:13:50
	 * @author jcf
	 * @update logs
	 */
	public void query() {
		String jsonArray = subjectService.query(model, page, rows);
		ajaxJson(jsonArray);
	}
	
	public void getTeacherName(){
		ServiceResult result = new ServiceResult(true);
		String teacherName=getSession().get(Teacher.LOGIN_TEACHERName).toString();
		result.addData("teacherName", teacherName);
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	/**
	 * @Description: 试卷小题选择试题
	 * @Created Time: 2013-4-30 下午4:40:12
	 * @Author lys
	 */
	public void selectPaperBigSmall(){
		try {
			String subjectIds = request.getParameter("subjectIds");
			String jsonArray = subjectService.selectPaperBigSmall(model,subjectIds, page, rows);
			ajaxJson(jsonArray);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * @Description: 批量修改试题状态 
	 * @Create: 2013-5-3 上午11:05:05
	 * @author yk
	 * @update logs
	 */
	public void mulUpdateState(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = subjectService.mulUpdateState(ids,model,getIntegerSession(Teacher.LOGIN_TEACHERID));
		} catch (Exception e) {
			result.setMessage("批量修改试题状态失败");
			logger.error("批量修改试题状态失败", e);
			result.setIsSuccess(false);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
}
