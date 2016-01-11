package com.csit.action;

import java.sql.Timestamp;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.csit.model.Teacher;
import com.csit.model.TempPaperBig;
import com.csit.service.TempPaperBigService;
import com.csit.vo.ServiceResult;
import com.csit.vo.TempTableOperateTime;
import com.opensymphony.xwork2.ModelDriven;
/**
 * @Description: 临时试卷大题Action
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-5-4
 * @Author lys
 */
@Controller
@Scope("prototype")
public class TempPaperBigAction extends BaseAction implements
		ModelDriven<TempPaperBig> {
	
	private static final long serialVersionUID = -6135581180504560724L;
	private static final Logger logger = Logger.getLogger(TempPaperBig.class);
	private TempPaperBig model = new TempPaperBig();

	@Resource
	private TempPaperBigService tempPaperBigService;

	public TempPaperBig getModel() {
		return model;
	}
	
	/**
	 * @Description: 保存临时试卷大题
	 * @Created Time: 2013-5-4 下午8:56:24
	 * @Author lys
	 */
	public void save(){
		Integer teacherId = getIntegerSession(Teacher.LOGIN_TEACHERID);
		Timestamp operateTime = getTimestampSession(TempTableOperateTime.PAPER);
		ServiceResult result = new ServiceResult(false);
		try {
			result = tempPaperBigService.save(teacherId, operateTime,model);
		} catch (Exception e) {
			logger.error("保存临时试卷大题失败", e);
			result.setMessage("保存临时试卷大题失败");
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	/**
	 * @Description: 删除大题
	 * @Created Time: 2013-5-4 下午10:15:01
	 * @Author lys
	 */
	public void delete(){
		Integer teacherId = getIntegerSession(Teacher.LOGIN_TEACHERID);
		Timestamp operateTime = getTimestampSession(TempTableOperateTime.PAPER);
		ServiceResult result = new ServiceResult(false);
		try {
			result = tempPaperBigService.delete(teacherId, operateTime,model);
		} catch (Exception e) {
			logger.error("删除大题失败", e);
			result.setMessage("删除大题失败");
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	/**
	 * @Description: 清空大题下的小题
	 * @Created Time: 2013-5-5 下午4:29:21
	 * @Author lys
	 */
	public void clearSmall(){
		Integer teacherId = getIntegerSession(Teacher.LOGIN_TEACHERID);
		Timestamp operateTime = getTimestampSession(TempTableOperateTime.PAPER);
		ServiceResult result = new ServiceResult(false);
		try {
			result = tempPaperBigService.clearSmall(teacherId, operateTime,model);
		} catch (Exception e) {
			logger.error("清空大题下的小题失败", e);
			result.setMessage("清空大题下的小题失败");
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}

}
