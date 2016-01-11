package com.csit.action;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.csit.model.CompetitionGroup;
import com.csit.model.Student;
import com.csit.service.CompetitionGroupService;
import com.csit.vo.ServiceResult;
import com.opensymphony.xwork2.ModelDriven;

/**
 * @Description:赛事组别Action
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-5-31
 * @author jcf
 * @vesion 1.0
 */
@Controller
@Scope("prototype")
public class CompetitionGroupAction extends BaseAction implements ModelDriven<CompetitionGroup> {

	private static final long serialVersionUID = -4648466664583512735L;
	private static final Logger logger = Logger.getLogger(CompetitionGroupAction.class);
	private CompetitionGroup model = new CompetitionGroup();
	@Resource
	private CompetitionGroupService competitionGroupService;

	@Override
	public CompetitionGroup getModel() {
		return model;
	}
	
	/**
	 * @Description: 保存赛事组别
	 * @Create: 2013-5-31 上午10:49:43
	 * @author jcf
	 * @update logs
	 */
	public void save() {
		ServiceResult result = new ServiceResult(false);
		try {
			String groupIds=getParameter("groupIds");
			result = competitionGroupService.save(model,groupIds);
		} catch (Exception e) {
			result.setMessage("保存赛事组别失败");
			logger.error("保存赛事组别失败", e);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}

	/**
	 * @Description: 分页查询赛事组别
	 * @Create: 2013-5-31 上午10:49:54
	 * @author jcf
	 * @update logs
	 */
	public void query() {
		String jsonArray = competitionGroupService.query(model, page, rows);
		ajaxJson(jsonArray);
	}
	/**
	 * @Description: 删除赛事组别
	 * @Create: 2013-5-31 上午10:50:06
	 * @author jcf
	 * @update logs
	 */
	public void delete(){
		ServiceResult result = new ServiceResult(false);	
		try {
			result = competitionGroupService.delete(model);
		} catch (Throwable e) {
			result.setMessage("批量删除赛事组别失败");
			logger.error("批量删除赛事组别失败", e);
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
		Integer updateCompetitionGroupId = Integer.parseInt(getParameter("updateCompetitionGroupId"));
		ServiceResult result = new ServiceResult(false);
		try {
			result = competitionGroupService.updateArray(model.getCompetitionGroupId(),updateCompetitionGroupId);
		} catch (Exception e) {
			result.setMessage("更新排序失败");
			logger.error("更新排序失败", e);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	/**
	 * @Description: combobox查询
	 * @Create: 2013-6-6 上午10:22:57
	 * @author jcf
	 * @update logs
	 */
	public void queryCombobox() {
		try {
			String jsonString = competitionGroupService.queryCombobox(model);
			ajaxJson(jsonString);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * @Description: 大赛报名初始化
	 * @Created Time: 2013-6-19 上午10:51:00
	 * @Author lys
	 */
	public void initEnrollStu(){
		ServiceResult result = new ServiceResult(false);
		try {
			Integer studentId = getIntegerSession(Student.LOGIN_ID);
			result = competitionGroupService.initEnrollStu(model,studentId);
		
		} catch (Exception e) {
			result.setMessage("大赛报名初始化失败");
			logger.error("大赛报名初始化失败", e);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	
	/**
	 * @Description: queryDatagrid查询
	 * @Create: 2013-6-6 上午10:22:57
	 * @author jcf
	 * @update logs
	 */
	public void queryDatagrid() {
		try {
			String jsonString = competitionGroupService.queryDatagrid(model);
			ajaxJson(jsonString);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
