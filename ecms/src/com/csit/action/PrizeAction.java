package com.csit.action;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.csit.model.Prize;
import com.csit.service.PrizeService;
import com.csit.vo.ServiceResult;
import com.opensymphony.xwork2.ModelDriven;
@Controller
@Scope("prototype")
public class PrizeAction extends BaseAction implements ModelDriven<Prize> {
	
	private static final long serialVersionUID = 2729067931190701690L;
	private static final Logger logger = Logger.getLogger(PrizeAction.class);
	Prize model = new Prize();
	
	@Resource
	private PrizeService prizeService;
	
	@Override
	public Prize getModel() {
		return model;
	}
	
	/**
	 * @Description:保存奖项 
	 * @Create: 2013-5-31 上午9:25:55
	 * @author wxy
	 * @update logs
	 */
	public void save(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = prizeService.save(model);
		} catch (Exception e) {
			result.setMessage("保存奖项失败");
			logger.error("保存奖项失败", e);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	/**
	 * 
	 * @Description: 分页查询奖项 
	 * @Create: 2013-5-31 上午9:25:55
	 * @author wxy
	 * @update logs
	 */
	public void query(){
		String jsonArray = prizeService.query(model, page, rows);
		ajaxJson(jsonArray);
	}
	/**
	 * 
	 * @Description: 批量删除奖项
	 * @Create: 2013-5-31 上午9:25:55
	 * @author wxy
	 * @update logs
	 */
	public void mulDelete(){
		ServiceResult result = new ServiceResult(false);	
		try {
			result = prizeService.mulDelete(ids);
		} catch (Throwable e) {
			result.setMessage("批量民族删除失败");
			logger.error("批量民族删除失败", e);
		}
		ajaxJson(result.toJSON());
	}
	/**
	 * 
	 * @Description: 批量修改奖项状态 
	 * @Create: 2013-5-31 上午9:25:55
	 * @author wxy
	 * @update logs
	 */
	public void mulUpdateState(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = prizeService.mulUpdateState(ids,model);
		} catch (Exception e) {
			result.setMessage("批量修改奖项状态失败");
			logger.error("批量修改奖项状态失败", e);
			result.setIsSuccess(false);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	/**
	 * 
	 * @Description: 更新奖项顺序
	 * @Create: 2013-5-31 上午9:25:55
	 * @author wxy
	 * @update logs
	 */
	public void updateArray(){
		Integer updatePrizeId = Integer.parseInt(getParameter("updatePrizeId"));
		ServiceResult result = new ServiceResult(false);
		try {
			result = prizeService.updateArray(model.getPrizeId(),updatePrizeId);
		} catch (Exception e) {
			result.setMessage("更新排序失败");
			logger.error("更新排序失败", e);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	/**
	 * 
	 * @Description: combobox查询奖项
	 * @Create: 2013-5-31 上午9:25:55
	 * @author wxy
	 * @update logs
	 */
	public void queryCombobox() {
		try {
			String jsonString = prizeService.queryCombobox();
			ajaxJson(jsonString);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void queryCompetition(){
		Integer competitionGroupId=Integer.parseInt(getParameter("competitionGroupId"));
		String jsonArray = prizeService.query(competitionGroupId, page, rows);
		ajaxJson(jsonArray);
	}
}
