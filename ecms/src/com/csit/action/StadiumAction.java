package com.csit.action;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.csit.model.Stadium;
import com.csit.service.StadiumService;
import com.csit.vo.ServiceResult;
import com.opensymphony.xwork2.ModelDriven;

/**
 * @Description:赛场Action
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-5-28
 * @author jcf
 * @vesion 1.0
 */
@Controller
@Scope("prototype")
public class StadiumAction extends BaseAction implements ModelDriven<Stadium> {

	private static final long serialVersionUID = -4648466664583512735L;
	private static final Logger logger = Logger.getLogger(StadiumAction.class);
	private Stadium model = new Stadium();
	@Resource
	private StadiumService stadiumService;

	@Override
	public Stadium getModel() {
		return model;
	}
	
	/**
	 * @Description: 保存赛场
	 * @Create: 2013-6-5 下午05:52:25
	 * @author jcf
	 * @update logs
	 */
	public void save() {
		ServiceResult result = new ServiceResult(false);
		try {
			result = stadiumService.save(model);
		} catch (Exception e) {
			result.setMessage("保存赛场失败");
			logger.error("保存赛场失败", e);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}

	/**
	 * @Description: 分页查询赛场
	 * @Create: 2013-6-5 下午05:52:36
	 * @author jcf
	 * @update logs
	 */
	public void query() {
		String jsonArray = stadiumService.query(model, page, rows);
		ajaxJson(jsonArray);
	}
	/**
	 * @Description: 批量删除赛场
	 * @Create: 2013-6-5 下午05:54:50
	 * @author jcf
	 * @update logs
	 */
	public void mulDelete(){
		ServiceResult result = new ServiceResult(false);	
		try {
			result = stadiumService.mulDelete(ids);
		} catch (Throwable e) {
			result.setMessage("批量删除赛场失败");
			logger.error("批量删除赛场失败", e);
		}
		ajaxJson(result.toJSON());
	}
	/**
	 * @Description: combobox查询
	 * @Create: 2013-5-29 上午10:23:52
	 * @author jcf
	 * @update logs
	 */
	public void queryCombobox() {
		try {
			Integer competitionId=Integer.parseInt(getParameter("competitionId"));
			String jsonString = stadiumService.queryCombobox(competitionId);
			ajaxJson(jsonString);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
