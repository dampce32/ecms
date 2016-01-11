package com.csit.action;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.csit.model.Student;
import com.csit.service.IndexService;
import com.csit.vo.GobelConstants;
import com.csit.vo.ServiceResult;
/**
 * @Description: 欢迎Action
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-1
 * @Author lys
 */
@Controller
@Scope("prototype")
public class IndexAction extends BaseAction {
	
	private static final long serialVersionUID = 2173538452681493600L;
	private static final Logger logger = Logger.getLogger(IndexAction.class);
	@Resource
	private IndexService indexService;
	
	/**
	 * @Description: 欢迎界面表头信息
	 * @Created Time: 2013-6-1 下午10:43:16
	 * @Author lys
	 * @return
	 */
	public void indexTitle(){
		ServiceResult result = new ServiceResult(false);
		try {
			String competitionId = request.getParameter("competitionId");
			Integer studentId = getIntegerSession(Student.LOGIN_ID);
			String studentName = getStringSession(Student.LOGIN_NAME);
			//取得访问路径
			result = indexService.indexTitle(competitionId,studentId);
			if(StringUtils.isNotEmpty(studentName)){
				result.addData("studentName", studentName);
			}
		} catch (Exception e) {
			result.setMessage("欢迎界面表头信息失败");
			logger.error("欢迎界面表头信息失败", e);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
//	/**
//	 * @Description: 欢迎界面
//	 * @Created Time: 2013-6-2 下午12:18:44
//	 * @Author lys
//	 */
//	public void index(){
//		ServiceResult result = new ServiceResult(false);
//		try {
//			String competitionId = request.getParameter("competitionId");
//			result = indexService.index(competitionId);
//		} catch (Exception e) {
//			result.setMessage("欢迎界面失败");
//			logger.error("欢迎界面失败", e);
//		}
//		String jsonString = result.toJSON();
//		ajaxJson(jsonString);
//	}
	/**
	 * @Description: 验证是否登录
	 * @Created Time: 2013-6-7 下午5:01:07
	 * @Author lys
	 */
	public void validateLogin(){
		Object obj = getSession(Student.LOGIN_ID);
		ServiceResult result = new ServiceResult(true);
		if(obj==null){
			result.addData("valid", false);
		}else{
			result.addData("valid", true);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	/**
	 * @Description: 主页
	 * @Created Time: 2013-6-15 下午12:12:09
	 * @Author lys
	 * @return
	 */
	public String homePage(){
		String studentName = getStringSession(Student.LOGIN_NAME);
		String competitionId = request.getParameter("competitionId");
		Map<String,Object> map = indexService.homePage(competitionId);
		if(StringUtils.isNotEmpty(studentName)){
			map.put("studentName", studentName);
		}
		request.setAttribute("pictureBasePath", GobelConstants.COMPETITIONPHOTO_BASEPATH);
		request.setAttribute("homePageMap", map);
		request.setAttribute("systemConfig", map.get("systemConfig"));
		return SUCCESS;
	}
}
