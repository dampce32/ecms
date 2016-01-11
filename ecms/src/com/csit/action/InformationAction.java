package com.csit.action;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.csit.dao.SystemConfigDAO;
import com.csit.model.Information;
import com.csit.model.SystemConfig;
import com.csit.model.Teacher;
import com.csit.service.InformationService;
import com.csit.vo.GobelConstants;
import com.csit.vo.ServiceResult;
import com.opensymphony.xwork2.ModelDriven;
/**
 * @Description: 资讯Action
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-3
 * @Author lys
 */
@Controller
@Scope("prototype")
public class InformationAction extends BaseAction implements
		ModelDriven<Information> {

	
	private static final long serialVersionUID = -6028172603349889789L;
	private static final Logger logger = Logger.getLogger(InformationAction.class);
	Information model = new Information();
	
	@Resource
	private InformationService informationService;
	@Resource
	SystemConfigDAO systemConfigDAO;
	@Override
	public Information getModel() {
		return model;
	}
	
	/**
	 * @Description: 保存资讯
	 * @Created Time: 2013-6-3 下午3:44:52
	 * @Author lys
	 */
	public void save() {
		ServiceResult result = new ServiceResult(false);
		try {
			Integer teacherId = getIntegerSession(Teacher.LOGIN_TEACHERID);
			result = informationService.save(model,teacherId);
		} catch (Exception e) {
			result.setMessage("保存资讯失败");
			logger.error("保存资讯失败", e);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	/**
	 * @Description: 分页查询资讯
	 * @Created Time: 2013-6-3 下午5:19:22
	 * @Author lys
	 */
	public void query() {
		String jsonArray = informationService.query(model, page, rows);
		ajaxJson(jsonArray);
	}
	/**
	 * @Description: 打开初始化资讯
	 * @Created Time: 2013-6-3 下午10:46:40
	 * @Author lys
	 */
	public void init(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = informationService.init(model);
		} catch (Exception e) {
			result.setMessage(" 打开初始化资讯失败");
			logger.error(" 打开初始化资讯失败", e);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	/**
	 * @Description: 批量删除资讯
	 * @Create: 2013-5-30 上午10:21:46
	 * @author jcf
	 * @update logs
	 */
	public void mulDelete(){
		ServiceResult result = new ServiceResult(false);	
		try {
			result = informationService.mulDelete(ids);
		} catch (Throwable e) {
			result.setMessage("批量删除资讯失败");
			logger.error("批量删除资讯失败", e);
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
			result = informationService.mulUpdateStatus(ids,model);
		} catch (Exception e) {
			result.setMessage("批量修改资讯状态失败");
			logger.error("批量修改资讯状态失败", e);
			result.setIsSuccess(false);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	/**
	 * @Description: 更新资讯排序
	 * @Created Time: 2013-6-3 下午11:27:06
	 * @Author lys
	 */
	public void updateArray(){
		String informationId = getParameter("informationId");
		String updateInformationId = getParameter("updateInformationId");
		ServiceResult result = new ServiceResult(false);
		try {
			result = informationService.updateArray(informationId,updateInformationId);
		} catch (Exception e) {
			result.setMessage("更新排序失败");
			logger.error("更新排序失败", e);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	/**
	 * @Description: 初始化赛事公告
	 * @Created Time: 2013-6-18 上午11:18:42
	 * @Author lys
	 * @return
	 */
	public String initNotice(){
		String competitionId = getParameter("competitionId");
		Map<String,Object> map = informationService.initNotice(competitionId,page,GobelConstants.DEFAULTPAGESIZE);
		SystemConfig systemConfig = systemConfigDAO.load(1);
		request.setAttribute("noticeList", map.get("noticeList"));
		request.setAttribute("total", map.get("total"));
		request.setAttribute("currPage", page);
		request.setAttribute("systemConfig", systemConfig);
		return SUCCESS;
	}
	/**
	 * @Description: 赛事公告明细
	 * @Created Time: 2013-6-18 上午11:19:11
	 * @Author lys
	 * @return
	 */
	public String noticeDetail(){
		String competitionId = getParameter("competitionId");
		request.removeAttribute("content");
		Information information;
		information = informationService.getInformationById(competitionId,model.getInformationId());
		SystemConfig systemConfig = systemConfigDAO.load(1);
		if(information!=null){
			request.setAttribute("content", information.getContent());
			request.setAttribute("informationTitle", information.getInformationTitle());
		}
		request.setAttribute("systemConfig", systemConfig);
		return SUCCESS;
	}
	
	/**
	 * @Description: 学生端展示大赛章程
	 * @Created Time: 2013-6-18 上午11:26:18
	 * @Author lys
	 * @return
	 */
	public String  initCompetitionRule(){
		String competitionId = getParameter("competitionId");
		String content = informationService.initCompetitionRule(competitionId);
		SystemConfig systemConfig = systemConfigDAO.load(1);
		request.setAttribute("content", content);
		request.setAttribute("systemConfig", systemConfig);
		return SUCCESS;
	}
}
