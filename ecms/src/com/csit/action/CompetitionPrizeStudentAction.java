package com.csit.action;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.csit.dao.SystemConfigDAO;
import com.csit.model.CompetitionPrizeStudent;
import com.csit.model.SystemConfig;
import com.csit.service.CompetitionPrizeStudentService;
import com.csit.vo.GobelConstants;
import com.csit.vo.ServiceResult;
import com.opensymphony.xwork2.ModelDriven;
/**
 * @Description: 赛事获奖学生Action
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-22
 * @Author lys
 */
@Controller
@Scope("prototype")
public class CompetitionPrizeStudentAction extends BaseAction implements
		ModelDriven<CompetitionPrizeStudent> {
	
	private static final long serialVersionUID = -670041523237643419L;
	private static final Logger logger = Logger.getLogger(CompetitionPrizeStudentAction.class);
	private CompetitionPrizeStudent model = new CompetitionPrizeStudent();
	@Resource
	private CompetitionPrizeStudentService competitionPrizeStudentService;

	@Resource
	SystemConfigDAO systemConfigDAO;
	@Override
	public CompetitionPrizeStudent getModel() {
		return model;
	}
	/**
	 * @Description: 分页查询赛事获奖学生
	 * @Created Time: 2013-6-22 下午8:12:02
	 * @Author lys
	 */
	public void query(){
		String jsonArray = competitionPrizeStudentService.query(model,page,rows);
		ajaxJson(jsonArray);
	}
	/**
	 * @Description: 选择查询还没获得奖项的学生
	 * @Created Time: 2013-6-23 上午10:14:54
	 * @Author lys
	 */
	public void selectQuery(){
		String jsonArray = competitionPrizeStudentService.selectQuery(model,page,rows);
		ajaxJson(jsonArray);
	}
	/**
	 * @Description: 保存多个获奖学生
	 * @Created Time: 2013-6-23 上午11:07:52
	 * @Author lys
	 */
	public void mulSave(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = competitionPrizeStudentService.mulSave(model,ids);
		} catch (Exception e) {
			result.setMessage("保存多个获奖学生失败");
			logger.error("保存多个获奖学生失败", e);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	/**
	 * @Description: 删除获奖学生
	 * @Created Time: 2013-6-23 下午12:33:54
	 * @Author lys
	 */
	public void mulDelete(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = competitionPrizeStudentService.mulDelete(ids);
		} catch (Exception e) {
			result.setMessage("删除获奖学生失败");
			logger.error("删除获奖学生失败", e);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	
	public String init(){
		
		Integer competitionId = Integer.parseInt(getParameter("competitionId"));
		String competitionGroupId = getParameter("competitionGroupId");
		Map<String, Object> map = competitionPrizeStudentService.init(competitionId,
				competitionGroupId, page, GobelConstants.DEFAULTPAGESIZE);
		SystemConfig systemConfig = systemConfigDAO.load(1);
		request.setAttribute("competitionPrizeStudentList", map.get("competitionPrizeStudentList"));
		request.setAttribute("total", map.get("total"));
		request.setAttribute("currPage", page);
		request.setAttribute("competitionGroupList",map.get("competitionGroupList"));
		request.setAttribute("systemConfig", systemConfig);
		return SUCCESS;
	}
	
}
