package com.csit.action;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.csit.model.CompetitionPrize;
import com.csit.service.CompetitionPrizeService;
import com.csit.vo.ServiceResult;
import com.opensymphony.xwork2.ModelDriven;

/**
 * @Description:赛事奖项Action
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-4
 * @author jcf
 * @vesion 1.0
 */
@Controller
@Scope("prototype")
public class CompetitionPrizeAction extends BaseAction implements ModelDriven<CompetitionPrize> {

	private static final long serialVersionUID = -4648466664583512735L;
	private static final Logger logger = Logger.getLogger(CompetitionPrizeAction.class);
	private CompetitionPrize model = new CompetitionPrize();
	@Resource
	private CompetitionPrizeService competitionPrizeService;

	@Override
	public CompetitionPrize getModel() {
		return model;
	}
	
	/**
	 * @Description: 保存赛事奖项
	 * @Create: 2013-6-4 上午09:26:27
	 * @author jcf
	 * @update logs
	 */
	public void save() {
		ServiceResult result = new ServiceResult(false);
		try {
			String prizeIds=getParameter("prizeIds");
			result = competitionPrizeService.save(model,prizeIds);
		} catch (Exception e) {
			result.setMessage("保存赛事奖项失败");
			logger.error("保存赛事奖项失败", e);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}

	/**
	 * @Description: 分页查询赛事奖项
	 * @Create: 2013-6-4 上午09:26:37
	 * @author jcf
	 * @update logs
	 */
	public void query() {
		String jsonArray = competitionPrizeService.query(model);
		ajaxJson(jsonArray);
	}
	/**
	 * @Description: 删除赛事奖项
	 * @Create: 2013-6-4 上午09:27:17
	 * @author jcf
	 * @update logs
	 */
	public void delete(){
		ServiceResult result = new ServiceResult(false);	
		try {
			result = competitionPrizeService.delete(model);
		} catch (Throwable e) {
			result.setMessage("批量删除赛事奖项失败");
			logger.error("批量删除赛事奖项失败", e);
		}
		ajaxJson(result.toJSON());
	}
	/**
	 * 
	 * @Description: 更新排序
	 * @Create: 2013-5-31 下午03:45:41
	 * @author jcf
	 * @update logs
	 */
	public void updateArray(){
		Integer updateCompetitionPrizeId = Integer.parseInt(getParameter("updateCompetitionPrizeId"));
		ServiceResult result = new ServiceResult(false);
		try {
			result = competitionPrizeService.updateArray(model.getCompetitionPrizeId(),updateCompetitionPrizeId);
		} catch (Exception e) {
			result.setMessage("更新排序失败");
			logger.error("更新排序失败", e);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	/**
	 * @Description: combobox查询
	 * @Created Time: 2013-6-22 下午9:20:50
	 * @Author lys
	 */
	public void queryCombobox() {
		try {
			String jsonString = competitionPrizeService.queryCombobox(model);
			ajaxJson(jsonString);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * @Description: queryDatagrid查询
	 * @Created Time: 2013-6-22 下午9:20:50
	 * @Author lys
	 */
	public void queryDatagrid() {
		try {
			String jsonString = competitionPrizeService.queryDatagrid(model);
			ajaxJson(jsonString);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
