package com.csit.action;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.csit.dao.SystemConfigDAO;
import com.csit.model.StadiumStudent;
import com.csit.model.Student;
import com.csit.model.SystemConfig;
import com.csit.service.StadiumService;
import com.csit.service.StadiumStudentService;
import com.csit.vo.GobelConstants;
import com.csit.vo.ServiceResult;
import com.opensymphony.xwork2.ModelDriven;
/**
 * @Description:选择赛场Action
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-21
 * @author jcf
 * @vesion 1.0
 */
@Controller
@Scope("prototype")
public class StadiumStudentAction extends BaseAction implements ModelDriven<StadiumStudent> {
	
	private static final long serialVersionUID = -2838387452372378492L;
	private static final Logger logger = Logger.getLogger(StadiumStudentAction.class);
	StadiumStudent model = new StadiumStudent();
	@Resource
	private StadiumStudentService stadiumStudentService;
	@Resource
	private StadiumService stadiumService;
	@Resource
	SystemConfigDAO systemConfigDAO;
	@Override
	public StadiumStudent getModel() {
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
	public String initStadium(){
		String competitionId=getParameter("competitionId");
		Map<String,Object> map = stadiumService.initStadium(competitionId, page, GobelConstants.DEFAULTPAGESIZE);
		SystemConfig systemConfig = systemConfigDAO.load(1);
		request.removeAttribute("err");
		request.setAttribute("stadiumList", map.get("stadiumList"));
		request.setAttribute("total", map.get("total"));
		request.setAttribute("currPage", page);
		request.setAttribute("systemConfig", systemConfig);
		return SUCCESS;
	}
	/**
	 * @Description: 批量删除选择赛场
	 * @Create: 2013-6-21 上午08:54:51
	 * @author jcf
	 * @update logs
	 */
	public void mulDelete(){
		ServiceResult result = new ServiceResult(false);	
		try {
			result = stadiumStudentService.mulDelete(ids);
		} catch (Throwable e) {
			result.setMessage("批量选择赛场删除失败");
			logger.error("批量选择赛场删除失败", e);
		}
		ajaxJson(result.toJSON());
	}
	         
	/**
	 * @Description: 分页查询选择赛场
	 * @Create: 2013-6-21 上午08:55:01
	 * @author jcf
	 * @update logs
	 */
	public void query() {
		Integer competitionId=Integer.parseInt(getParameter("competitionId"));
		String jsonArray = stadiumStudentService.query(model,competitionId, page, rows);
		ajaxJson(jsonArray);
	}
	/**
	 * 
	 * @Description: 管理员添加赛场学生
	 * @Create: 2013-6-23 下午09:13:47
	 * @author jcf
	 * @update logs
	 */
	public void saveByAdmin() {
		ServiceResult result = new ServiceResult(false);
		try {
			result = stadiumStudentService.save(model);
		} catch (Exception e) {
			result.setMessage("保存赛场学生失败");
			logger.error("保存赛场学生失败", e);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	/**
	 * 
	 * @Description: 学生添加赛场学生
	 * @Create: 2013-6-23 下午09:13:47
	 * @author jcf
	 * @update logs
	 */
	public void saveByStu() {
		ServiceResult result = new ServiceResult(false);
		try {
			Integer studentId = getIntegerSession(Student.LOGIN_ID);
			Student student = new Student();
			student.setStudentId(studentId);
			model.setStudent(student);
			result = stadiumStudentService.save(model);
		} catch (Exception e) {
			result.setMessage("保存赛场学生失败");
			logger.error("保存赛场学生失败", e);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	public String init(){
		Integer studentId = getIntegerSession(Student.LOGIN_ID);
		Map<String, Object> map;
		try {
			map = stadiumStudentService.init(studentId);
			SystemConfig systemConfig = systemConfigDAO.load(1);
			request.setAttribute("infoType", 4);
			request.setAttribute("stadiumStudentList", map.get("stadiumStudentList"));
			request.setAttribute("systemConfig", systemConfig);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
}