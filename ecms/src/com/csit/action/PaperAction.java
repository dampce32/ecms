package com.csit.action;

import java.sql.Timestamp;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.csit.model.Paper;
import com.csit.model.Teacher;
import com.csit.service.PaperService;
import com.csit.util.DateUtil;
import com.csit.vo.ServiceResult;
import com.csit.vo.TempTableOperateTime;
import com.opensymphony.xwork2.ModelDriven;
/**
 * @Description:试卷Action
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-4-27
 * @Author lys
 */
@Controller
@Scope("prototype")
public class PaperAction extends BaseAction implements ModelDriven<Paper> {

	
	private static final long serialVersionUID = -7486928493784756702L;
	private static final Logger logger = Logger.getLogger(PaperAction.class);
	private Paper model = new Paper();

	@Resource
	private PaperService paperService;

	public Paper getModel() {
		return model;
	}
	
	/**
	 * @Description: 分页查询试卷
	 * @Created Time: 2013-4-27 下午4:57:23
	 * @Author lys
	 */
	public void query() {
		String jsonArray = paperService.query(model, page, rows);
		ajaxJson(jsonArray);
	}
	/**
	 * @Description: 新建
	 * @Created Time: 2013-4-28 上午11:30:59
	 * @Author lys
	 */
	public void newAdd(){
		//新建
		ServiceResult result = new ServiceResult(false);
		try {
			//新建临时表操作时间
			Timestamp operateTime = DateUtil.getNowTimestampSqlServer();
			setSession(TempTableOperateTime.PAPER, operateTime);
			//取得操作教师Id
			Integer teacherId = getIntegerSession(Teacher.LOGIN_TEACHERID);
			result = paperService.newAdd(teacherId, operateTime);
		} catch (Throwable e) {
			logger.error("新建试卷失败", e);
			result.setMessage("新建试卷失败");
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	/**
	 * @Description: 复制新建
	 * @Created Time: 2013-4-28 上午11:30:59
	 * @Author lys
	 */
	public void copyAdd(){
		ServiceResult result = new ServiceResult(false);
		try {
			//新建临时表操作时间
			Timestamp operateTime = DateUtil.getNowTimestampSqlServer();
			setSession(TempTableOperateTime.PAPER, operateTime);
			//取得操作教师Id
			Integer teacherId = getIntegerSession(Teacher.LOGIN_TEACHERID);
			result = paperService.copyAdd(model.getPaperId(),teacherId, operateTime);
		} catch (Throwable e) {
			logger.error("复制新建失败", e);
			result.setMessage("复制新建失败");
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	/**
	 * @Description: 清空临时表
	 * @Created Time: 2013-4-30 下午7:28:39
	 * @Author lys
	 */
	public void clearTemp(){
		//取得操作教师Id
		Integer teacherId = getIntegerSession(Teacher.LOGIN_TEACHERID);
		Timestamp operateTime = getTimestampSession(TempTableOperateTime.PAPER);
		ServiceResult result = new ServiceResult(false);
		try {
			result = paperService.clearTemp(teacherId, operateTime,model);
		} catch (Exception e) {
			logger.error("清空临时表失败", e);
			result.setMessage("清空临时表失败");
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	/**
	 * @Description: 保存试卷
	 * @Created Time: 2013-5-1 下午8:48:58
	 * @Author lys
	 */
	public void save(){
		String bigId = request.getParameter("bigId");
		String smallIds = request.getParameter("smallIds");
		String isOptionMixs = request.getParameter("isOptionMixs");
		String scores = request.getParameter("scores");
		String difficultys = request.getParameter("difficultys");
		
		String bigIds = request.getParameter("bigIds");
		String subjectTypes = request.getParameter("subjectTypes");
		String bigNames = request.getParameter("bigNames");
		String isSmallMixs = request.getParameter("isSmallMixs");
		String scoreBigs = request.getParameter("scoreBigs");
		
		String saveType = request.getParameter("saveType");
		
		//取得操作教师Id
		Integer teacherId = getIntegerSession(Teacher.LOGIN_TEACHERID);
		Timestamp operateTime = getTimestampSession(TempTableOperateTime.PAPER);
		ServiceResult result = new ServiceResult(false);
		try {
			result = paperService.save(bigId,smallIds,isOptionMixs,scores,difficultys,
					bigIds,subjectTypes,bigNames,isSmallMixs,scoreBigs,saveType,
					teacherId, operateTime,model);
		} catch (Exception e) {
			logger.error("保存试卷失败", e);
			result.setMessage("保存试卷失败");
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
		
	}
	/**
	 * @Description: 批量删除试卷
	 * @Created Time: 2013-5-4 上午9:19:05
	 * @Author lys
	 */
	public void mulDelete(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = paperService.mulDelete(ids);
		} catch (Exception e) {
			logger.error("批量删除试卷失败", e);
			result.setMessage("批量删除试卷失败");
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	/**
	 * @Description: 批量修改试卷状态
	 * @Created Time: 2013-5-4 上午10:02:08
	 * @Author lys
	 */
	public void mulUpdateState(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = paperService.mulUpdateState(ids,model);
		} catch (Exception e) {
			logger.error("批量修改试卷状态失败", e);
			result.setMessage("批量修改试卷状态失败");
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	/**
	 * @Description: 修改试卷状态
	 * @Created Time: 2013-5-4 上午10:02:08
	 * @Author lys
	 */
	public void updateState(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = paperService.updateState(model);
		} catch (Exception e) {
			logger.error("修改试卷状态失败", e);
			result.setMessage("修改试卷状态失败");
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	/**
	 * @Description: 打开修改试卷
	 * @Created Time: 2013-5-4 上午10:37:12
	 * @Author lys
	 */
	public void modify(){
		ServiceResult result = new ServiceResult(false);
		try {
			//新建临时表操作时间
			Timestamp operateTime = DateUtil.getNowTimestampSqlServer();
			setSession(TempTableOperateTime.PAPER, operateTime);
			//取得操作教师Id
			Integer teacherId = getIntegerSession(Teacher.LOGIN_TEACHERID);
			
			result = paperService.modify(model,teacherId,operateTime);
		} catch (Exception e) {
			logger.error("打开修改试卷失败", e);
			result.setMessage("打开修改试卷失败");
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	/**
	 * @Description: 查看试卷
	 * @Created Time: 2013-5-8 上午9:57:56
	 * @Author lys
	 */
	public void getViewPanel(){
		try {
			String htmlStr = paperService.getViewPanel(model);
			ajaxHtml(htmlStr);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @Description: 查看试卷
	 * @Created Time: 2013-5-8 上午9:57:56
	 * @Author lys
	 */
	public void getViewHtml(){
		String htmlStr = paperService.getViewHtml(model);
		ajaxHtml(htmlStr);
	}
	/**
	 * @Description: 教师端查看学生端试卷
	 * @Created: 2013-7-14 上午10:00:22
	 * @author lys
	 * @update logs
	 * @throws Exception
	 */
	public void initExamView(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = paperService.initExamView(model);
		} catch (Throwable e) {
			result.setMessage("教师端查看学生端试卷失败");
			logger.error("教师端查看学生端试卷失败", e);
		}
		String ajaxString = result.toJSON();
		ajaxJson(ajaxString);
	}
	/**
	 * @Description: 教师端查看学生端试卷小题
	 * @Created: 2013-7-14 上午10:00:22
	 * @author lys
	 * @update logs
	 * @throws Exception
	 */
	public void initSmallView(){
		ServiceResult result = new ServiceResult(false);
		try {
			String paperId = getParameter("paperId");
			String bigId= getParameter("bigId");
			String smallId= getParameter("smallId");
			String smallNo= getParameter("smallNo");
			result = paperService.initSmallView(paperId,bigId,smallId,smallNo);
		} catch (Throwable e) {
			result.setMessage("教师端查看学生端试卷小题失败");
			logger.error("教师端查看学生端试卷小题失败", e);
		}
		String ajaxString = result.toJSON();
		ajaxJson(ajaxString);
	}
	
}
