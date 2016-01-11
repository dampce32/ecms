package com.csit.action;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.csit.model.School;
import com.csit.service.SchoolService;
import com.csit.vo.ServiceResult;
import com.opensymphony.xwork2.ModelDriven;
/**
 * @Description:学校Action
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-7-22
 * @author jcf
 * @vesion 1.0
 */
@Controller
@Scope("prototype")
public class SchoolAction extends BaseAction implements ModelDriven<School> {

	private static final long serialVersionUID = -4648466664583512735L;
	private static final Logger logger = Logger.getLogger(SchoolAction.class);
	private School model = new School();
	@Resource
	private SchoolService schoolService;

	@Override
	public School getModel() {
		return model;
	}
	
	/**
	 * @Description: 保存学校
	 * @Create: 2013-7-22 下午03:24:25
	 * @author jcf
	 * @update logs
	 */
	public void save() {
		ServiceResult result = new ServiceResult(false);
		try {
			result = schoolService.save(model);
		} catch (Exception e) {
			result.setMessage("保存学校失败");
			logger.error("保存学校失败", e);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}

	/**
	 * @Description: 分页查询学校
	 * @Create: 2013-7-22 下午03:24:59
	 * @author jcf
	 * @update logs
	 */
	public void query() {
		String jsonArray = schoolService.query(model, page, rows);
		ajaxJson(jsonArray);
	}
	/**
	 * @Description: 批量删除学校
	 * @Create: 2013-7-22 下午03:25:06
	 * @author jcf
	 * @update logs
	 */
	public void mulDelete(){
		ServiceResult result = new ServiceResult(false);	
		try {
			result = schoolService.mulDelete(ids);
		} catch (Throwable e) {
			result.setMessage("批量删除学校失败");
			logger.error("批量删除学校失败", e);
		}
		ajaxJson(result.toJSON());
	}
	/**
	 * 
	 * @Description: 批量修改学校状态
	 * @Create: 2013-7-22 下午04:34:57
	 * @author jcf
	 * @update logs
	 */
	public void mulUpdateStatus(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = schoolService.mulUpdateStatus(ids,model);
		} catch (Exception e) {
			result.setMessage("批量修改赛事状态失败");
			logger.error("批量修改赛事状态失败", e);
			result.setIsSuccess(false);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	/**
	 * @Description: combobox查询
	 * @Create: 2013-7-23 下午02:23:31
	 * @author jcf
	 * @update logs
	 */
	public void queryCombobox() {
		try {
			String jsonString = schoolService.queryCombobox(model);
			ajaxJson(jsonString);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
