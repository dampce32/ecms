package com.csit.action;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.csit.model.Competition;
import com.csit.service.CompetitionService;
import com.csit.vo.ServiceResult;
import com.opensymphony.xwork2.ModelDriven;

/**
 * @Description:赛事Action
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-5-30
 * @author jcf
 * @vesion 1.0
 */
@Controller
@Scope("prototype")
public class CompetitionAction extends BaseAction implements ModelDriven<Competition> {

	private static final long serialVersionUID = -4648466664583512735L;
	private static final Logger logger = Logger.getLogger(CompetitionAction.class);
	private Competition model = new Competition();
	@Resource
	private CompetitionService competitionService;

	@Override
	public Competition getModel() {
		return model;
	}
	
	/**
	 * @Description: 保存赛事
	 * @Create: 2013-5-30 上午10:21:01
	 * @author jcf
	 * @update logs
	 */
	public void save() {
		ServiceResult result = new ServiceResult(false);
		try {
			result = competitionService.save(model);
		} catch (Exception e) {
			result.setMessage("保存赛事失败");
			logger.error("保存赛事失败", e);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}

	/**
	 * @Description: 分页查询赛事
	 * @Create: 2013-5-30 上午10:21:38
	 * @author jcf
	 * @update logs
	 */
	public void query() {
		String jsonArray = competitionService.query(model, page, rows);
		ajaxJson(jsonArray);
	}
	/**
	 * @Description: 批量删除赛事
	 * @Create: 2013-5-30 上午10:21:46
	 * @author jcf
	 * @update logs
	 */
	public void mulDelete(){
		ServiceResult result = new ServiceResult(false);	
		try {
			result = competitionService.mulDelete(ids);
		} catch (Throwable e) {
			result.setMessage("批量删除赛事失败");
			logger.error("批量删除赛事失败", e);
		}
		ajaxJson(result.toJSON());
	}
	/**
	 * @Description: 批量修改状态
	 * @Create: 2013-5-30 上午10:21:57
	 * @author jcf
	 * @update logs
	 */
	public void mulUpdateStatus(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = competitionService.mulUpdateStatus(ids,model);
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
	 * @Create: 2013-5-30 上午10:22:13
	 * @author jcf
	 * @update logs
	 */
	public void queryCombobox() {
		try {
			String jsonString = competitionService.queryCombobox(model);
			ajaxJson(jsonString);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * @Description: 保存复制赛事
	 * @Create: 2013-5-30 上午10:21:01
	 * @author jcf
	 * @update logs
	 */
	public void saveCopy() {
		ServiceResult result = new ServiceResult(false);
		try {
			String copyItems=getParameter("copyItems");
			String copyCompetitionId=getParameter("copyCompetitionId");
			result = competitionService.saveCopy(model, copyItems, copyCompetitionId);
		} catch (Exception e) {
			result.setMessage("保存赛事失败");
			logger.error("保存赛事失败", e);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	
}
