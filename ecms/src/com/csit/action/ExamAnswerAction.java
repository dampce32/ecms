package com.csit.action;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.csit.dao.SystemConfigDAO;
import com.csit.model.ExamAnswer;
import com.csit.model.Student;
import com.csit.model.SystemConfig;
import com.csit.service.ExamAnswerService;
import com.csit.vo.ServiceResult;
import com.opensymphony.xwork2.ModelDriven;
/**
 * @Description:学生答卷
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-8
 * @Author lys
 */
@Controller
@Scope("prototype")
public class ExamAnswerAction extends BaseAction implements
		ModelDriven<ExamAnswer> {
	
	private static final long serialVersionUID = -2294391208345706958L;
	private static final Logger logger = Logger.getLogger(ExamAnswerAction.class);
	ExamAnswer model = new ExamAnswer();
	@Resource
	private ExamAnswerService examAnswerService;
	
	@Resource
	SystemConfigDAO systemConfigDAO;
	
	@Override
	public ExamAnswer getModel() {
		return model;
	}
	
	/**
	 * @Description: 浏览试卷
	 * @Created Time: 2013-6-7 下午3:41:28
	 * @Author lys
	 * @return
	 */
	public String  viewPaper(){
		String competitionId = getParameter("competitionId");
		Integer studentId = getIntegerSession(Student.LOGIN_ID);
		List<Map<String,Object>> examPaperList = examAnswerService.viewPaper(competitionId,studentId);
		SystemConfig systemConfig = systemConfigDAO.load(1);
		request.setAttribute("examPaperList", examPaperList);
		request.setAttribute("systemConfig", systemConfig);
		return SUCCESS;
	}
	/**
	 * @Description: 考试前准备
	 * @Created Time: 2013-6-8 下午9:24:26
	 * @Author lys
	 * @return
	 */
	public String readyExam(){
		String competitionId = getParameter("competitionId");
		String groupId = getParameter("groupId");
		String paperId = getParameter("paperId");
		String studentCompetitionGroupId = getParameter("studentCompetitionGroupId");
		Map<String,Object> readyExamMap = examAnswerService.readyExam(groupId,paperId);
		SystemConfig systemConfig = systemConfigDAO.load(1);
		readyExamMap.put("competitionId", competitionId);
		readyExamMap.put("groupId", groupId);
		readyExamMap.put("paperId", paperId);
		readyExamMap.put("studentCompetitionGroupId", studentCompetitionGroupId);
		request.setAttribute("readyExamMap", readyExamMap);
		request.setAttribute("systemConfig", systemConfig);
		return SUCCESS;
	}
	/**
	 * @Description: 考试
	 * @Created Time: 2013-6-9 上午9:11:02
	 * @Author lys
	 * @return
	 */
	public String exam(){
		//生成答卷并返回页面指导信息
		String competitionId = getParameter("competitionId");
		String groupId = getParameter("groupId");
		String paperId = getParameter("paperId");
		String studentCompetitionGroupId = getParameter("studentCompetitionGroupId");
		Integer studentId = getIntegerSession(Student.LOGIN_ID);
		Map<String,Object> examMap = examAnswerService.exam(competitionId,groupId,studentId,paperId,studentCompetitionGroupId);
		request.setAttribute("examMap", examMap);
		if(examMap.get("returnValue")!=null){
			return "error";
		}else{
			return SUCCESS;
		}
	}
	
	/**
	 * @Description: 初始化答卷
	 * @Created Time: 2013-5-14 下午5:21:22
	 * @Author lys
	 */
	public void initExamPaper(){
		ServiceResult result = new ServiceResult(false);
		try {
			Integer studentId = getIntegerSession(Student.LOGIN_ID);
			result = examAnswerService.initExamPaper(model,studentId);
		} catch (Throwable e) {
			result.setMessage("初始化答卷失败");
			logger.error("初始化答卷失败", e);
		}
		String ajaxString = result.toJSON();
		ajaxJson(ajaxString);
	}
	
	/**
	 * @Description: 取得答卷小题的答题状态
	 * @Created Time: 2013-5-24 下午4:49:16
	 * @Author lys
	 */
	public void getSmallStatus(){
		ServiceResult result = new ServiceResult(false);
		try {
			Integer studentId = getIntegerSession(Student.LOGIN_ID);
			String competitionId = getParameter("competitionId");
			String groupId = getParameter("groupId");
			String bigId= getParameter("bigId");
			String smallId= getParameter("smallId");
			result = examAnswerService.getSmallStatus(competitionId,groupId,studentId,bigId,smallId);
		} catch (Throwable e) {
			result.setMessage("取得答卷小题的答题状态失败");
			logger.error("取得答卷小题的答题状态失败", e);
		}
		String ajaxString = result.toJSON();
		ajaxJson(ajaxString);
	}
	
	/**
	 * @Description: 初始化小题
	 * @Created Time: 2013-5-14 下午9:06:12
	 * @Author lys
	 */
	public void initSmall(){
		ServiceResult result = new ServiceResult(false);
		try {
			Integer studentId = getIntegerSession(Student.LOGIN_ID);
			String competitionId = getParameter("competitionId");
			String groupId = getParameter("groupId");
			String bigId= getParameter("bigId");
			String smallId= getParameter("smallId");
			String smallNo= getParameter("smallNo");
			result = examAnswerService.initSmall(competitionId,groupId,studentId,bigId,smallId,smallNo);
		} catch (Throwable e) {
			result.setMessage("初始化小题失败");
			logger.error("初始化小题失败", e);
		}
		String ajaxString = result.toJSON();
		ajaxJson(ajaxString);
	}
	
	/**
	 * @Description: 回答阅读理解
	 * @Created Time: 2013-6-9 下午9:52:35
	 * @Author lys
	 */
	public void answerRead(){
		ServiceResult result = new ServiceResult(false);
		try {
			Integer studentId = getIntegerSession(Student.LOGIN_ID);
			String competitionId = getParameter("competitionId");
			String groupId = getParameter("groupId");
			String bigId= getParameter("bigId");
			String smallId= getParameter("smallId");
			String subId= getParameter("subId");
			String answer= getParameter("answer");
			String smallStatus= getParameter("smallStatus");
			result = examAnswerService.answerRead(competitionId,groupId,studentId,bigId,smallId,subId,answer,smallStatus);
		} catch (Throwable e) {
			result.setMessage("回答阅读理解失败");
			logger.error("回答阅读理解失败", e);
		}
		String ajaxString = result.toJSON();
		ajaxJson(ajaxString);
	}
	/**
	 * @Description: 回答填空题
	 * @Created Time: 2013-6-9 下午10:21:11
	 * @Author lys
	 */
	public void answerFill(){
		ServiceResult result = new ServiceResult(false);
		try {
			Integer studentId = getIntegerSession(Student.LOGIN_ID);
			String competitionId = getParameter("competitionId");
			String groupId = getParameter("groupId");
			String bigId= getParameter("bigId");
			String smallId= getParameter("smallId");
			String option= getParameter("option");
			String answer= getParameter("answer");
			String smallStatus= getParameter("smallStatus");
			result = examAnswerService.answerFill(competitionId,groupId,studentId,bigId,smallId,option,answer,smallStatus);
		} catch (Throwable e) {
			result.setMessage("回答填空题失败");
			logger.error("回答填空题失败", e);
		}
		String ajaxString = result.toJSON();
		ajaxJson(ajaxString);
	}
	/**
	 * @Description: 回答单项选择题
	 * @Created Time: 2013-6-9 下午10:30:17
	 * @Author lys
	 */
	public void answerUniterm(){
		ServiceResult result = new ServiceResult(false);
		try {
			Integer studentId = getIntegerSession(Student.LOGIN_ID);
			String competitionId = getParameter("competitionId");
			String groupId = getParameter("groupId");
			String bigId= getParameter("bigId");
			String smallId= getParameter("smallId");
			String answer= getParameter("answer");
			result = examAnswerService.answerUniterm(competitionId,groupId,studentId,bigId,smallId,answer);
		} catch (Throwable e) {
			result.setMessage("回答单项选择题失败");
			logger.error("回答单项选择题失败", e);
		}
		String ajaxString = result.toJSON();
		ajaxJson(ajaxString);
	}
	/**
	 * @Description: 回答完形填空
	 * @Created Time: 2013-6-9 下午10:52:27
	 * @Author lys
	 */
	public void answerCloze(){
		ServiceResult result = new ServiceResult(false);
		try {
			Integer studentId = getIntegerSession(Student.LOGIN_ID);
			String competitionId = getParameter("competitionId");
			String groupId = getParameter("groupId");
			String bigId= getParameter("bigId");
			String smallId= getParameter("smallId");
			String subId= getParameter("subId");
			String answer= getParameter("answer");
			String smallStatus= getParameter("smallStatus");
			result = examAnswerService.answerRead(competitionId,groupId,studentId,bigId,smallId,subId,answer,smallStatus);
		} catch (Throwable e) {
			result.setMessage("回答完形填空失败");
			logger.error("回答完形填空失败", e);
		}
		String ajaxString = result.toJSON();
		ajaxJson(ajaxString);
	}
	
	/**
	 * @Description: 结束答题
	 * @Created Time: 2013-6-10 下午4:24:18
	 * @Author lys
	 */
	public void finishExamPaper(){
		ServiceResult result = new ServiceResult(false);
		try {
			Integer studentId = getIntegerSession(Student.LOGIN_ID);
			String competitionId = getParameter("competitionId");
			String groupId = getParameter("groupId");
			
			result = examAnswerService.finishExamPaper(competitionId,groupId,studentId);
			result.addData("isTimeOver", true);
		} catch (Throwable e) {
			result.setMessage("结束答题失败");
			logger.error("结束答题失败", e);
		}
		String ajaxString = result.toJSON();
		ajaxJson(ajaxString);
	}
	
	public String init(){
		Integer studentId = getIntegerSession(Student.LOGIN_ID);
		Map<String, Object> map;
		try {
			map = examAnswerService.init(studentId);
			SystemConfig systemConfig = systemConfigDAO.load(1);
			request.setAttribute("infoType", 2);
			request.setAttribute("examAnswerList", map.get("examAnswerList"));
			request.setAttribute("systemConfig", systemConfig);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
}
