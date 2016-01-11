package com.csit.action;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.csit.model.Clazz;
import com.csit.service.ClazzService;
import com.csit.vo.ServiceResult;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 
 * @Description: 班级Action
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-7-22
 * @author yk
 * @vesion 1.0
 */
@Controller
@Scope("prototype")
public class ClazzAction extends BaseAction implements ModelDriven<Clazz> {
	
	private static final long serialVersionUID = -1565048795095432810L;
	private static final Logger logger = Logger.getLogger(ClazzAction.class);
	Clazz model = new Clazz();
	
	@Resource
	private ClazzService clazzService;
	
	@Override
	public Clazz getModel() {
		return model;
	}
	/**
	 * 
	 * @Description: 保存班级 
	 * @Create: 2013-7-22 下午03:23:30
	 * @author yk
	 * @update logs
	 */
	public void save(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = clazzService.save(model);
		} catch (Exception e) {
			result.setMessage("保存班级失败");
			logger.error("保存班级失败", e);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	/**
	 * 
	 * @Description: 分页查询班级 
	 * @Create: 2013-7-22 下午03:23:42
	 * @author yk
	 * @update logs
	 */
	public void query(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = clazzService.query(model,page,rows);
		} catch (Exception e) {
			result.setMessage("分页查询班级失败");
			logger.error("分页查询班级失败", e);
			result.setIsSuccess(false);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	/**
	 * 
	 * @Description: 统计班级 
	 * @Create: 2013-7-22 下午03:23:51
	 * @author yk
	 * @update logs
	 */
	public void getTotalCount(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = clazzService.getTotalCount(model);
		} catch (Exception e) {
			result.setMessage("统计班级失败");
			logger.error("统计班级失败", e);
			result.setIsSuccess(false);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	/**
	 * 
	 * @Description: 批量删除班级 
	 * @Create: 2013-7-22 下午03:24:01
	 * @author yk
	 * @update logs
	 */
	public void mulDelete(){
		ServiceResult result = new ServiceResult(false);	
		try {
			result = clazzService.mulDelete(ids);
		} catch (Throwable e) {
			result.setMessage("批量删除班级失败");
			logger.error("批量删除班级失败", e);
		}
		ajaxJson(result.toJSON());
	}
	/**
	 * 
	 * @Description: 批量修改班级状态 
	 * @Create: 2013-7-22 下午03:24:15
	 * @author yk
	 * @update logs
	 */
	public void mulUpdateState(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = clazzService.mulUpdateStatus(ids,model);
		} catch (Exception e) {
			result.setMessage("批量修改班级状态失败");
			logger.error("批量修改班级状态失败", e);
			result.setIsSuccess(false);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	/**
	 * 
	 * @Description: 更新班级顺序 
	 * @Create: 2013-7-22 下午03:24:28
	 * @author yk
	 * @update logs
	 */
	public void updateArray(){
		String clazzId = getParameter("clazzId");
		String updateClazzId = getParameter("updateClazzId");
		ServiceResult result = new ServiceResult(false);
		try {
			result = clazzService.updateArray(clazzId,updateClazzId);
		} catch (Exception e) {
			result.setMessage("更新排序失败");
			logger.error("更新排序失败", e);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}

	public void selectQuery(){
		Integer schoolGradeId=Integer.parseInt(getParameter("schoolGradeId"));
		String jsonArray = clazzService.selectQuery(model, schoolGradeId, page, rows);
		ajaxJson(jsonArray);
	}
}
