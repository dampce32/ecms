package com.csit.action;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.csit.dao.SystemConfigDAO;
import com.csit.model.Student;
import com.csit.model.SystemConfig;
import com.csit.model.TrainingClassStudent;
import com.csit.service.TrainingClassService;
import com.csit.service.TrainingClassStudentService;
import com.csit.vo.GobelConstants;
import com.csit.vo.ServiceResult;
import com.opensymphony.xwork2.ModelDriven;
/**
 * @Description:报名培训班级Action
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-21
 * @author jcf
 * @vesion 1.0
 */
@Controller
@Scope("prototype")
public class TrainingClassStudentAction extends BaseAction implements ModelDriven<TrainingClassStudent> {
	
	private static final long serialVersionUID = -2838387452372378492L;
	private static final Logger logger = Logger.getLogger(TrainingClassStudentAction.class);
	TrainingClassStudent model = new TrainingClassStudent();
	@Resource
	private TrainingClassStudentService trainingClassStudentService;
	@Resource
	private TrainingClassService trainingClassService;
	@Resource
	SystemConfigDAO systemConfigDAO;
	@Override
	public TrainingClassStudent getModel() {
		return model;
	}
	/**
	 * 
	 * @Description: 初始化培训班级界面
	 * @Create: 2013-6-22 上午08:44:20
	 * @author jcf
	 * @update logs
	 * @return
	 */
	public String initTrainingClass(){
		String competitionId=getParameter("competitionId");
		Map<String,Object> map = trainingClassService.initTrainingClass(competitionId, page, GobelConstants.DEFAULTPAGESIZE);
		SystemConfig systemConfig = systemConfigDAO.load(1);
		request.setAttribute("trainingClassList", map.get("trainingClassList"));
		request.setAttribute("total", map.get("total"));
		request.setAttribute("currPage", page);
		request.setAttribute("systemConfig", systemConfig);
		return SUCCESS;
	}
	/**
	 * @Description: 保存报名培训班级
	 * @Create: 2013-6-21 上午08:54:40
	 * @author jcf
	 * @update logs
	 */
	public void save(){
		ServiceResult result = new ServiceResult(false);
		try {
			Integer studentId = getIntegerSession(Student.LOGIN_ID);
			Student student = new Student();
			student.setStudentId(studentId);
			model.setStudent(student);
			result = trainingClassStudentService.save(model);
		} catch (Exception e) {
			result.setMessage("保存报名培训班级失败");
			logger.error("保存报名培训班级失败", e);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	public void saveByAdmin(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = trainingClassStudentService.save(model);
		} catch (Exception e) {
			result.setMessage("保存报名培训班级失败");
			logger.error("保存报名培训班级失败", e);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	/**
	 * @Description: 批量删除报名培训班级
	 * @Create: 2013-6-21 上午08:54:51
	 * @author jcf
	 * @update logs
	 */
	public void mulDelete(){
		ServiceResult result = new ServiceResult(false);	
		try {
			result = trainingClassStudentService.mulDelete(ids);
		} catch (Throwable e) {
			result.setMessage("批量报名培训班级删除失败");
			logger.error("批量报名培训班级删除失败", e);
		}
		ajaxJson(result.toJSON());
	}
	         
	/**
	 * @Description: 分页查询报名培训班级
	 * @Create: 2013-6-21 上午08:55:01
	 * @author jcf
	 * @update logs
	 */
	public void query() {
		Integer competitionId=Integer.parseInt(getParameter("competitionId"));
		String jsonArray = trainingClassStudentService.query(model,competitionId, page, rows);
		ajaxJson(jsonArray);
	}
	
	public String init(){
		Integer studentId = getIntegerSession(Student.LOGIN_ID);
		Map<String, Object> map;
		try {
			map = trainingClassStudentService.init(studentId);
			SystemConfig systemConfig = systemConfigDAO.load(1);
			request.setAttribute("infoType", 3);
			request.setAttribute("trainingClassStudentList", map.get("trainingClassStudentList"));
			request.setAttribute("systemConfig", systemConfig);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
}