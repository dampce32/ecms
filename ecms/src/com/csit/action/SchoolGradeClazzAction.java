package com.csit.action;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.csit.model.SchoolGradeClazz;
import com.csit.service.SchoolGradeClazzService;
import com.csit.vo.ServiceResult;
import com.opensymphony.xwork2.ModelDriven;

/**
 * @Description:学校年级班级Action
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-7-23
 * @author jcf
 * @vesion 1.0
 */
@Controller
@Scope("prototype")
public class SchoolGradeClazzAction extends BaseAction implements ModelDriven<SchoolGradeClazz> {

	private static final long serialVersionUID = -4648466664583512735L;
	private static final Logger logger = Logger.getLogger(SchoolGradeClazzAction.class);
	private SchoolGradeClazz model = new SchoolGradeClazz();
	@Resource
	private SchoolGradeClazzService schoolGradeClazzService;

	@Override
	public SchoolGradeClazz getModel() {
		return model;
	}
	
	/**
	 * @Description: 保存学校年级班级
	 * @Create: 2013-6-4 上午09:26:27
	 * @author jcf
	 * @update logs
	 */
	public void save() {
		ServiceResult result = new ServiceResult(false);
		try {
			String clazzIds=getParameter("clazzIds");
			result = schoolGradeClazzService.save(model,clazzIds);
		} catch (Exception e) {
			result.setMessage("保存学校年级班级失败");
			logger.error("保存学校年级班级失败", e);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}

	/**
	 * @Description: 查询学校年级班级
	 * @Create: 2013-7-23 上午09:44:36
	 * @author jcf
	 * @update logs
	 */
	public void query() {
		String jsonArray = schoolGradeClazzService.query(model);
		ajaxJson(jsonArray);
	}
	/**
	 * @Description: 删除学校年级班级
	 * @Create: 2013-7-23 上午09:44:47
	 * @author jcf
	 * @update logs
	 */
	public void delete(){
		ServiceResult result = new ServiceResult(false);	
		try {
			result = schoolGradeClazzService.delete(model);
		} catch (Throwable e) {
			result.setMessage("批量删除学校年级班级失败");
			logger.error("批量删除学校年级班级失败", e);
		}
		ajaxJson(result.toJSON());
	}
	/**
	 * @Description: 更新排序
	 * @Create: 2013-7-23 上午09:44:59
	 * @author jcf
	 * @update logs
	 */
	public void updateArray(){
		Integer updateSchoolGradeClazzId = Integer.parseInt(getParameter("updateSchoolGradeClazzId"));
		ServiceResult result = new ServiceResult(false);
		try {
			result = schoolGradeClazzService.updateArray(model.getSchoolGradeClazzId(),updateSchoolGradeClazzId);
		} catch (Exception e) {
			result.setMessage("更新排序失败");
			logger.error("更新排序失败", e);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	/**
	 * 
	 * @Description: combobox根据学校年级查询班级 
	 * @Create: 2013-7-23 下午02:49:40
	 * @author yk
	 * @update logs
	 */
	public void queryCombobox() {
		try {
			String jsonString = schoolGradeClazzService.queryCombobox(model.getSchoolGrade().getSchoolGradeId());
			ajaxJson(jsonString);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
