package com.csit.action;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.csit.model.Nation;
import com.csit.service.NationService;
import com.csit.vo.ServiceResult;
import com.opensymphony.xwork2.ModelDriven;
/**
 * @Description:民族Action
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-5-30
 * @Author lys
 */
@Controller
@Scope("prototype")
public class NationAction extends BaseAction implements ModelDriven<Nation> {
	
	private static final long serialVersionUID = 2729067931190701690L;
	private static final Logger logger = Logger.getLogger(NationAction.class);
	Nation model = new Nation();
	
	@Resource
	private NationService nationService;
	
	@Override
	public Nation getModel() {
		return model;
	}
	
	/**
	 * 
	 * @Description: 保存民族 
	 * @Create: 2013-5-28 下午05:48:36
	 * @author yk
	 * @update logs
	 */
	public void save(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = nationService.save(model);
		} catch (Exception e) {
			result.setMessage("保存民族失败");
			logger.error("保存民族失败", e);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	/**
	 * 
	 * @Description: 分页查询民族 
	 * @Create: 2013-5-28 下午05:50:14
	 * @author yk
	 * @update logs
	 */
	public void query(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = nationService.query(model,page,rows);
		} catch (Exception e) {
			result.setMessage("分页查询民族失败");
			logger.error("分页查询民族失败", e);
			result.setIsSuccess(false);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	/**
	 * 
	 * @Description: 统计民族
	 * @Create: 2013-5-28 下午05:50:25
	 * @author yk
	 * @update logs
	 */
	public void getTotalCount(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = nationService.getTotalCount(model);
		} catch (Exception e) {
			result.setMessage("统计民族失败");
			logger.error("统计民族失败", e);
			result.setIsSuccess(false);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	/**
	 * 
	 * @Description: 批量删除民族
	 * @Create: 2013-5-28 下午05:50:32
	 * @author yk
	 * @update logs
	 */
	public void mulDelete(){
		ServiceResult result = new ServiceResult(false);	
		try {
			result = nationService.mulDelete(ids);
		} catch (Throwable e) {
			result.setMessage("批量民族删除失败");
			logger.error("批量民族删除失败", e);
		}
		ajaxJson(result.toJSON());
	}
	/**
	 * 
	 * @Description: 批量修改民族状态 
	 * @Create: 2013-5-28 下午05:50:40
	 * @author yk
	 * @update logs
	 */
	public void mulUpdateState(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = nationService.mulUpdateState(ids,model);
		} catch (Exception e) {
			result.setMessage("批量修改民族状态失败");
			logger.error("批量修改民族状态失败", e);
			result.setIsSuccess(false);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	/**
	 * 
	 * @Description: 更新民族顺序
	 * @Create: 2013-5-29 上午09:28:38
	 * @author yk
	 * @update logs
	 */
	public void updateArray(){
		String nationId = getParameter("nationId");
		String updateNationId = getParameter("updateNationId");
		ServiceResult result = new ServiceResult(false);
		try {
			result = nationService.updateArray(nationId,updateNationId);
		} catch (Exception e) {
			result.setMessage("更新排序失败");
			logger.error("更新排序失败", e);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	/**
	 * 
	 * @Description: combobox查询民族
	 * @Create: 2013-5-29 上午10:53:12
	 * @author yk
	 * @update logs
	 */
	public void queryCombobox() {
		try {
			String jsonString = nationService.queryCombobox();
			ajaxJson(jsonString);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
