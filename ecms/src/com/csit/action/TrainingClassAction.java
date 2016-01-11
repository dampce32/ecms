package com.csit.action;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.csit.model.TrainingClass;
import com.csit.service.TrainingClassService;
import com.csit.vo.ServiceResult;
import com.opensymphony.xwork2.ModelDriven;
/**
 * @Description: 培训班级Action
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-7
 * @author wxy
 * @vesion 1.0
 */
@Controller
@Scope("prototype")
public class TrainingClassAction extends BaseAction implements ModelDriven<TrainingClass> {
	
	private static final long serialVersionUID = -2838387452372378492L;
	private static final Logger logger = Logger.getLogger(TrainingClassAction.class);
	TrainingClass model = new TrainingClass();
	@Resource
	private TrainingClassService trainingClassService;
	
	@Override
	public TrainingClass getModel() {
		return model;
	}
	
	/**
	 * @Description: 保存培训班级
	 * @Create: 2013-5-31 下午2:29:22
	 * @author wxy
	 * @update logs
	 * @return
	 */
	public void save(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = trainingClassService.save(model);
		} catch (Exception e) {
			result.setMessage("保存培训班级失败");
			logger.error("保存培训班级失败", e);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	/**
	 * 
	 * @Description: 批量删除培训班级
	 * @Create: 2013-5-31 下午15:25:55
	 * @author wxy
	 * @update logs
	 */
	public void mulDelete(){
		ServiceResult result = new ServiceResult(false);	
		try {
			result = trainingClassService.mulDelete(ids);
		} catch (Throwable e) {
			result.setMessage("批量培训班级删除失败");
			logger.error("批量培训班级删除失败", e);
		}
		ajaxJson(result.toJSON());
	}
	         
	
	/**
	 * @Description: 分页查询培训班级
	 * @Created Time: 2013-5-29 上午11:11:09
	 * @Author lys
	 */
	public void query() {
		String jsonArray = trainingClassService.query(model, page, rows);
		ajaxJson(jsonArray);
	}
	
	/**
	 * @Description: combobox查询
	 * @Created Time: 2013-5-29 下午2:55:38
	 * @Author lys
	 */
	public void queryCombobox(){
		Integer competitionId=Integer.parseInt(getParameter("competitionId"));
		String jsonString = trainingClassService.queryCombobox(model,competitionId);
		ajaxJson(jsonString);
	}
	
	/**
	 * 
	 * @Description: 批量修改培训班级状态 
	 * @Create: 2013-5-31 上午9:25:55
	 * @author wxy
	 * @update logs
	 */
	public void mulUpdateState(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = trainingClassService.mulUpdateState(ids,model);
		} catch (Exception e) {
			result.setMessage("批量修改培训班级状态失败");
			logger.error("批量修改培训班级状态失败", e);
			result.setIsSuccess(false);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
}