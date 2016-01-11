package com.csit.action;

import java.sql.Timestamp;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.csit.model.Teacher;
import com.csit.model.TempPaperBigSmall;
import com.csit.service.TempPaperBigSmallService;
import com.csit.vo.ServiceResult;
import com.csit.vo.TempTableOperateTime;
import com.opensymphony.xwork2.ModelDriven;
/**
 * @Description:试卷临时小题Action
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-4-30
 * @Author lys
 */
@Controller
@Scope("prototype")
public class TempPaperBigSmallAction extends BaseAction implements
		ModelDriven<TempPaperBigSmall> {
	
	private static final long serialVersionUID = 3848298827023117555L;
	private static final Logger logger = Logger.getLogger(TempPaperBigSmallAction.class);
	private TempPaperBigSmall model = new TempPaperBigSmall();

	@Resource
	private TempPaperBigSmallService tempPaperBigSmallService;

	public TempPaperBigSmall getModel() {
		return model;
	}
	/**
	 * @Description: 保存试卷临时小题(统一试题打乱生成)
	 * @Created Time: 2013-4-30 下午10:39:39
	 * @Author lys
	 */
	public void saveMix(){
		Integer teacherId = getIntegerSession(Teacher.LOGIN_TEACHERID);
		Timestamp operateTime = getTimestampSession(TempTableOperateTime.PAPER);
		ServiceResult result = new ServiceResult(false);
		try {
			result = tempPaperBigSmallService.saveMix(teacherId, operateTime,model);
		} catch (Exception e) {
			logger.error("保存试卷临时小题(统一试题打乱生成)失败", e);
			result.setMessage("保存试卷临时小题(统一试题打乱生成)失败");
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	/**
	 * @Description: 保存多个试卷临时小题(统一试题打乱生成)
	 * @Created Time: 2013-4-30 下午10:39:39
	 * @Author lys
	 */
	public void saveMulMix(){
		Integer teacherId = getIntegerSession(Teacher.LOGIN_TEACHERID);
		Timestamp operateTime = getTimestampSession(TempTableOperateTime.PAPER);
		ServiceResult result = new ServiceResult(false);
		try {
			result = tempPaperBigSmallService.saveMulMix(teacherId, operateTime,model,ids);
		} catch (Exception e) {
			logger.error("保存多个试卷临时小题(统一试题打乱生成)失败", e);
			result.setMessage("保存多个试卷临时小题(统一试题打乱生成)失败");
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	/**
	 * @Description: 批量更新小题信息
	 * @Created Time: 2013-5-1 下午3:39:15
	 * @Author lys
	 */
	public void mulUpdate(){
		Integer teacherId = getIntegerSession(Teacher.LOGIN_TEACHERID);
		Timestamp operateTime = getTimestampSession(TempTableOperateTime.PAPER);
		String paperId = request.getParameter("paperId");
		String bigId = request.getParameter("bigId");
		String nextBigId = request.getParameter("nextBigId");
		String smallIds = request.getParameter("smallIds");
		String isOptionMixs = request.getParameter("isOptionMixs");
		String scores = request.getParameter("scores");
		String difficultys = request.getParameter("difficultys");
		ServiceResult result = new ServiceResult(false);
		try {
			result = tempPaperBigSmallService.mulUpdate(paperId,bigId,nextBigId,smallIds,isOptionMixs,scores,
					difficultys,teacherId, operateTime);
		} catch (Exception e) {
			logger.error("批量更新小题信息失败", e);
			result.setMessage("批量更新小题信息失败");
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString); 
	}
	
	/**
	 * @Description: 查看大题下的小题
	 * @Created Time: 2013-5-1 下午3:39:15
	 * @Author lys
	 */
	public void view(){
		Integer teacherId = getIntegerSession(Teacher.LOGIN_TEACHERID);
		Timestamp operateTime = getTimestampSession(TempTableOperateTime.PAPER);
		ServiceResult result = new ServiceResult(false);
		try {
			result = tempPaperBigSmallService.view(model,teacherId, operateTime);
		} catch (Exception e) {
			logger.error("查看大题下的小题失败", e);
			result.setMessage("查看大题下的小题失败");
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString); 
	}
	/**
	 * @Description: 删除大题下的小题
	 * @Created Time: 2013-5-4 下午4:10:40
	 * @Author lys
	 */
	public void delete(){
		Integer teacherId = getIntegerSession(Teacher.LOGIN_TEACHERID);
		Timestamp operateTime = getTimestampSession(TempTableOperateTime.PAPER);
		ServiceResult result = new ServiceResult(false);
		try {
			result = tempPaperBigSmallService.delete(model,teacherId, operateTime);
		} catch (Exception e) {
			logger.error("删除大题下的小题失败", e);
			result.setMessage("删除大题下的小题失败");
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	
	/**
	 * @Description: 更新小题
	 * @Created Time: 2013-5-4 下午4:10:40
	 * @Author lys
	 */
	public void update(){
		Integer teacherId = getIntegerSession(Teacher.LOGIN_TEACHERID);
		Timestamp operateTime = getTimestampSession(TempTableOperateTime.PAPER);
		ServiceResult result = new ServiceResult(false);
		try {
			result = tempPaperBigSmallService.update(model,teacherId, operateTime);
		} catch (Exception e) {
			logger.error("更新小题失败", e);
			result.setMessage("更新小题失败");
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	
	/**
	 * @Description: 移动排序
	 * @Created Time: 2013-5-4 下午4:10:40
	 * @Author lys
	 */
	public void moveArray(){
		Integer teacherId = getIntegerSession(Teacher.LOGIN_TEACHERID);
		Timestamp operateTime = getTimestampSession(TempTableOperateTime.PAPER);
		String paperId = getParameter("paperId");
		String bigId = getParameter("bigId");
		String smallIdFrom = getParameter("smallIdFrom");
		String arrayFrom = getParameter("arrayFrom");
		String smallIdTo = getParameter("smallIdTo");
		String arrayTo = getParameter("arrayTo");
		
		
		ServiceResult result = new ServiceResult(false);
		try {
			result = tempPaperBigSmallService.moveArray(paperId,bigId,smallIdFrom,arrayFrom,smallIdTo,arrayTo,
					teacherId,operateTime);
		} catch (Exception e) {
			logger.error("移动排序失败", e);
			result.setMessage("移动排序失败");
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	/**
	 * @Description: 批量添加小题
	 * @Created Time: 2013-5-6 下午8:21:17
	 * @Author lys
	 */
	public void mulAdd(){
		Integer teacherId = getIntegerSession(Teacher.LOGIN_TEACHERID);
		Timestamp operateTime = getTimestampSession(TempTableOperateTime.PAPER);
		String paperId = getParameter("paperId");
		String bigId = getParameter("bigId");
		String countSmall = getParameter("countSmall");
		String scoreSmall = getParameter("scoreSmall");
		String array = getParameter("array");
		String groupId = getParameter("groupId");
		
		
		ServiceResult result = new ServiceResult(false);
		try {
			result = tempPaperBigSmallService.mulAdd(paperId,bigId,countSmall,scoreSmall,
					teacherId,operateTime,array,groupId);
		} catch (Exception e) {
			logger.error("批量添加小题失败", e);
			result.setMessage("批量添加小题失败");
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	
}
