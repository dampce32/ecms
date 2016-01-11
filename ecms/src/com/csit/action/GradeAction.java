package com.csit.action;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.csit.model.Grade;
import com.csit.service.GradeService;
import com.csit.vo.ServiceResult;
import com.opensymphony.xwork2.ModelDriven;
/**
 * 
 * @Description: 年级Action
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-7-22
 * @author yk
 * @vesion 1.0
 */
@Controller
@Scope("prototype")
public class GradeAction extends BaseAction implements ModelDriven<Grade> {
	
	private static final long serialVersionUID = -6579064809086594094L;
	private static final Logger logger = Logger.getLogger(GradeAction.class);
	Grade model = new Grade();
	
	@Resource
	private GradeService gradeService;
	
	@Override
	public Grade getModel() {
		return model;
	}
	
	/**
	 * 
	 * @Description: 保存年级 
	 * @Create: 2013-7-22 下午03:06:36
	 * @author yk
	 * @update logs
	 */
	public void save(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = gradeService.save(model);
		} catch (Exception e) {
			result.setMessage("保存年级失败");
			logger.error("保存年级失败", e);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	/**
	 * 
	 * @Description:  分页查询年级
	 * @Create: 2013-7-22 下午03:08:38
	 * @author yk
	 * @update logs
	 */
	public void query(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = gradeService.query(model,page,rows);
		} catch (Exception e) {
			result.setMessage("分页查询年级失败");
			logger.error("分页查询年级失败", e);
			result.setIsSuccess(false);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	/**
	 * 
	 * @Description: 统计年级 
	 * @Create: 2013-7-22 下午03:08:31
	 * @author yk
	 * @update logs
	 */
	public void getTotalCount(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = gradeService.getTotalCount(model);
		} catch (Exception e) {
			result.setMessage("统计年级失败");
			logger.error("统计年级失败", e);
			result.setIsSuccess(false);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	/**
	 * 
	 * @Description: 批量删除年级 
	 * @Create: 2013-7-22 下午03:08:04
	 * @author yk
	 * @update logs
	 */
	public void mulDelete(){
		ServiceResult result = new ServiceResult(false);	
		try {
			result = gradeService.mulDelete(ids);
		} catch (Throwable e) {
			result.setMessage("批量删除年级失败");
			logger.error("批量删除年级失败", e);
		}
		ajaxJson(result.toJSON());
	}
	/**
	 * 
	 * @Description: 批量修改年级状态 
	 * @Create: 2013-7-22 下午03:07:49
	 * @author yk
	 * @update logs
	 */
	public void mulUpdateState(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = gradeService.mulUpdateStatus(ids,model);
		} catch (Exception e) {
			result.setMessage("批量修改年级状态失败");
			logger.error("批量修改年级状态失败", e);
			result.setIsSuccess(false);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	/**
	 * 
	 * @Description: 更新排序 
	 * @Create: 2013-7-22 下午03:07:38
	 * @author yk
	 * @update logs
	 */
	public void updateArray(){
		String gradeId = getParameter("gradeId");
		String updateGradeId = getParameter("updateGradeId");
		ServiceResult result = new ServiceResult(false);
		try {
			result = gradeService.updateArray(gradeId,updateGradeId);
		} catch (Exception e) {
			result.setMessage("更新排序失败");
			logger.error("更新排序失败", e);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}

	public void selectQuery(){
		Integer schoolId=Integer.parseInt(getParameter("schoolId"));
		String jsonArray = gradeService.selectQuery(model, schoolId, page, rows);
		ajaxJson(jsonArray);
	}
}
