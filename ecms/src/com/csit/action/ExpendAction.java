package com.csit.action;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.csit.model.Expend;
import com.csit.service.ExpendService;
import com.csit.vo.ServiceResult;
import com.opensymphony.xwork2.ModelDriven;

/**
 * @Description:支出Action
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-9
 * @author jcf
 * @vesion 1.0
 */
@Controller
@Scope("prototype")
public class ExpendAction extends BaseAction implements ModelDriven<Expend> {

	private static final long serialVersionUID = -4648466664583512735L;
	private static final Logger logger = Logger.getLogger(ExpendAction.class);
	private Expend model = new Expend();
	@Resource
	private ExpendService expendService;

	@Override
	public Expend getModel() {
		return model;
	}
	
	/**
	 * @Description: 保存支出
	 * @Create: 2013-6-9 上午09:12:55
	 * @author jcf
	 * @update logs
	 */
	public void save() {
		ServiceResult result = new ServiceResult(false);
		try {
			result = expendService.save(model);
		} catch (Exception e) {
			result.setMessage("保存支出失败");
			logger.error("保存支出失败", e);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}

	/**
	 * @Description: 分页查询支出
	 * @Create: 2013-6-9 上午09:13:05
	 * @author jcf
	 * @update logs
	 */
	public void query() {
		String jsonArray = expendService.query(model, page, rows);
		ajaxJson(jsonArray);
	}
	/**
	 * @Description: 批量删除支出
	 * @Create: 2013-6-9 上午09:13:13
	 * @author jcf
	 * @update logs
	 */
	public void mulDelete(){
		ServiceResult result = new ServiceResult(false);	
		try {
			result = expendService.mulDelete(ids);
		} catch (Throwable e) {
			result.setMessage("批量删除支出失败");
			logger.error("批量删除支出失败", e);
		}
		ajaxJson(result.toJSON());
	}
	/**
	 * @Description: 批量修改支出状态
	 * @Create: 2013-6-9 上午09:13:21
	 * @author jcf
	 * @update logs
	 */
	public void mulUpdateStatus(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = expendService.mulUpdateStatus(ids,model);
		} catch (Exception e) {
			result.setMessage("批量修改支出状态失败");
			logger.error("批量修改支出状态失败", e);
			result.setIsSuccess(false);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	
}
