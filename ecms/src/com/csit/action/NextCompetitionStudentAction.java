package com.csit.action;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.csit.dao.SystemConfigDAO;
import com.csit.model.NextCompetitionStudent;
import com.csit.model.SystemConfig;
import com.csit.service.NextCompetitionStudentService;
import com.csit.vo.GobelConstants;
import com.csit.vo.ServiceResult;
import com.opensymphony.xwork2.ModelDriven;
/**
 * @Description:赛事晋级Action
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-23
 * @Author lys
 */
@Controller
@Scope("prototype")
public class NextCompetitionStudentAction extends BaseAction implements
		ModelDriven<NextCompetitionStudent> {
	private static final long serialVersionUID = 3962748303112556426L;
	private static final Logger logger = Logger.getLogger(NextCompetitionStudentAction.class);
	private NextCompetitionStudent model = new NextCompetitionStudent();
	@Resource
	private NextCompetitionStudentService nextCompetitionStudentService;
	@Resource
	SystemConfigDAO systemConfigDAO;
	@Override
	public NextCompetitionStudent getModel() {
		return model;
	}
	/**
	 * @Description: 分页查询赛事晋级
	 * @Created Time: 2013-6-23 下午4:35:32
	 * @Author lys
	 */
	public void query(){
		String jsonArray = nextCompetitionStudentService.query(model,page,rows);
		ajaxJson(jsonArray);
	}
	/**
	 * @Description: 选择查询还没获得晋级的学生
	 * @Created Time: 2013-6-23 上午10:14:54
	 * @Author lys
	 */
	public void selectQuery(){
		try {
			String jsonArray = nextCompetitionStudentService.selectQuery(model,page,rows);
			ajaxJson(jsonArray);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * @Description: 保存多个晋级学生
	 * @Created Time: 2013-6-23 上午11:07:52
	 * @Author lys
	 */
	public void mulSave(){
		ServiceResult result = new ServiceResult(false);
		try {
			String scores = getParameter("scores");
			result = nextCompetitionStudentService.mulSave(model,ids,scores);
		} catch (Exception e) {
			result.setMessage("保存多个晋级学生失败");
			logger.error("保存多个晋级学生失败", e);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	/**
	 * @Description: 删除晋级学生
	 * @Created Time: 2013-6-23 下午12:33:54
	 * @Author lys
	 */
	public void mulDelete(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = nextCompetitionStudentService.mulDelete(ids);
		} catch (Exception e) {
			result.setMessage("删除晋级学生失败");
			logger.error("删除晋级学生失败", e);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	
	public String init(){
		
		Integer competitionId = Integer.parseInt(getParameter("competitionId"));
		String competitionGroupId = getParameter("competitionGroupId");
		Map<String, Object> map = nextCompetitionStudentService.init(competitionId,
				competitionGroupId, page, GobelConstants.DEFAULTPAGESIZE);
		SystemConfig systemConfig = systemConfigDAO.load(1);
		request.setAttribute("nextCompetitionStudentList", map.get("nextCompetitionStudentList"));
		request.setAttribute("total", map.get("total"));
		request.setAttribute("currPage", page);
		request.setAttribute("competitionGroupList",map.get("competitionGroupList"));
		request.setAttribute("systemConfig", systemConfig);
		return SUCCESS;
	}
	
}
