package com.csit.action;

import java.sql.Timestamp;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.csit.model.Teacher;
import com.csit.model.TempArrangeStudent;
import com.csit.service.TempArrangeStudentService;
import com.csit.vo.ServiceResult;
import com.csit.vo.TempTableOperateTime;
import com.opensymphony.xwork2.ModelDriven;
/**
 * @Description:考务安排临时学生Action
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-5-14
 * @author jcf
 * @vesion 1.0
 */
@Controller
@Scope("prototype")
public class TempArrangeStudentAction extends BaseAction implements
		ModelDriven<TempArrangeStudent> {
	
	private static final long serialVersionUID = -6135581180504560724L;
	private static final Logger logger = Logger.getLogger(TempArrangeStudent.class);
	private TempArrangeStudent model = new TempArrangeStudent();

	@Resource
	private TempArrangeStudentService tempArrangeStudentService;

	public TempArrangeStudent getModel() {
		return model;
	}
	
	/**
	 * @Description: 保存考务临时学生
	 * @Create: 2013-5-14 下午05:36:19
	 * @author jcf
	 * @update logs
	 */
	public void save(){
		Integer teacherId = getIntegerSession(Teacher.LOGIN_TEACHERID);
		Timestamp operateTime = getTimestampSession(TempTableOperateTime.ARRANGE);
		Integer arrangeId=Integer.parseInt(getParameter("arrangeId"));
		ServiceResult result = new ServiceResult(false);
		try {
			result = tempArrangeStudentService.save(teacherId, operateTime,arrangeId,ids);
		} catch (Exception e) {
			logger.error("保存考务临时学生失败", e);
			result.setMessage("保存考务临时学生失败");
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	/**
	 * @Description: 删除考务临时学生
	 * @Create: 2013-5-14 下午05:54:13
	 * @author jcf
	 * @update logs
	 */
	public void delete(){
		ServiceResult result = new ServiceResult(false);
		try {
			Integer teacherId = getIntegerSession(Teacher.LOGIN_TEACHERID);
			Timestamp operateTime = getTimestampSession(TempTableOperateTime.ARRANGE);
			Integer arrangeId=Integer.parseInt(getParameter("arrangeId"));
			result = tempArrangeStudentService.mulDelete(ids, arrangeId, teacherId, operateTime);
		} catch (Exception e) {
			logger.error("删除考务临时学生失败", e);
			result.setMessage("删除考务临时学生失败");
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	
	public void query(){
		Timestamp operateTime = getTimestampSession(TempTableOperateTime.ARRANGE);
		Integer arrangeId=Integer.parseInt(getParameter("arrangeId"));
		Integer teacherId = getIntegerSession(Teacher.LOGIN_TEACHERID);
		String jsonArray = tempArrangeStudentService.query(arrangeId, teacherId, operateTime, page, rows);
		ajaxJson(jsonArray);
	}

}
