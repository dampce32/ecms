package com.csit.action;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.csit.model.SchoolGrade;
import com.csit.service.SchoolGradeService;
import com.csit.vo.ServiceResult;
import com.opensymphony.xwork2.ModelDriven;

/**
 * @Description:学校年级Action
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-7-22
 * @author jcf
 * @vesion 1.0
 */
@Controller
@Scope("prototype")
public class SchoolGradeAction extends BaseAction implements ModelDriven<SchoolGrade> {

	private static final long serialVersionUID = -4648466664583512735L;
	private static final Logger logger = Logger.getLogger(SchoolGradeAction.class);
	private SchoolGrade model = new SchoolGrade();
	@Resource
	private SchoolGradeService schoolGradeService;

	@Override
	public SchoolGrade getModel() {
		return model;
	}
	
	/**
	 * @Description: 保存学校年级
	 * @Create: 2013-7-22 下午05:36:17
	 * @author jcf
	 * @update logs
	 */
	public void save() {
		ServiceResult result = new ServiceResult(false);
		try {
			String gradeIds=getParameter("gradeIds");
			result = schoolGradeService.save(model,gradeIds);
		} catch (Exception e) {
			result.setMessage("保存学校年级失败");
			logger.error("保存学校年级失败", e);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}

	/**
	 * @Description: 分页查询学校年级
	 * @Create: 2013-7-22 下午05:36:58
	 * @author jcf
	 * @update logs
	 */
	public void query() {
		String jsonArray = schoolGradeService.query(model);
		ajaxJson(jsonArray);
	}
	/**
	 * @Description: 删除学校年级
	 * @Create: 2013-7-22 下午05:37:09
	 * @author jcf
	 * @update logs
	 */
	public void delete(){
		ServiceResult result = new ServiceResult(false);	
		try {
			result = schoolGradeService.delete(model);
		} catch (Throwable e) {
			result.setMessage("批量删除学校年级失败");
			logger.error("批量删除学校年级失败", e);
		}
		ajaxJson(result.toJSON());
	}
	/**
	 * @Description: 更新排序
	 * @Create: 2013-7-22 下午05:37:17
	 * @author jcf
	 * @update logs
	 */
	public void updateArray(){
		Integer updateSchoolGradeId = Integer.parseInt(getParameter("updateSchoolGradeId"));
		ServiceResult result = new ServiceResult(false);
		try {
			result = schoolGradeService.updateArray(model.getSchoolGradeId(),updateSchoolGradeId);
		} catch (Exception e) {
			result.setMessage("更新排序失败");
			logger.error("更新排序失败", e);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	/**
	 * 
	 * @Description: combobox根据学校查询年级 
	 * @Create: 2013-7-23 下午02:31:54
	 * @author yk
	 * @update logs
	 */
	public void queryCombobox() {
		try {
			String jsonString = schoolGradeService.queryCombobox(model.getSchool().getSchoolId());
			ajaxJson(jsonString);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
