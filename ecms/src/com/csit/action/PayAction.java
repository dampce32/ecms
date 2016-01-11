package com.csit.action;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.csit.model.Pay;
import com.csit.model.Teacher;
import com.csit.service.PayService;
import com.csit.vo.ServiceResult;
import com.opensymphony.xwork2.ModelDriven;

/**
 * @Description:缴费Action
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-9
 * @author jcf
 * @vesion 1.0
 */
@Controller
@Scope("prototype")
public class PayAction extends BaseAction implements ModelDriven<Pay> {

	private static final long serialVersionUID = -4648466664583512735L;
	private static final Logger logger = Logger.getLogger(PayAction.class);
	private Pay model = new Pay();
	@Resource
	private PayService payService;

	@Override
	public Pay getModel() {
		return model;
	}
	
	/**
	 * @Description: 保存缴费
	 * @Create: 2013-6-9 上午09:12:55
	 * @author jcf
	 * @update logs
	 */
	public void save() {
		ServiceResult result = new ServiceResult(false);
		try {
			Integer teacherId = getIntegerSession(Teacher.LOGIN_TEACHERID);
			Teacher teacher=new Teacher();
			teacher.setTeacherId(teacherId);
			model.setTeacher(teacher);
			String stuIds=getParameter("stuIds");
			result = payService.save(model,stuIds);
		} catch (Exception e) {
			result.setMessage("保存缴费失败");
			logger.error("保存缴费失败", e);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}

	/**
	 * @Description: 分页查询缴费
	 * @Create: 2013-6-9 上午09:13:05
	 * @author jcf
	 * @update logs
	 */
	public void query() {
		String jsonArray = payService.query(model, page, rows);
		ajaxJson(jsonArray);
	}
	/**
	 * @Description: 批量删除缴费
	 * @Create: 2013-6-9 上午09:13:13
	 * @author jcf
	 * @update logs
	 */
	public void mulDelete(){
		ServiceResult result = new ServiceResult(false);	
		try {
			result = payService.mulDelete(ids);
		} catch (Throwable e) {
			result.setMessage("批量删除缴费失败");
			logger.error("批量删除缴费失败", e);
		}
		ajaxJson(result.toJSON());
	}
	/**
	 * @Description: 批量修改缴费状态
	 * @Create: 2013-6-9 上午09:13:21
	 * @author jcf
	 * @update logs
	 */
	public void mulUpdateStatus(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = payService.mulUpdateStatus(ids,model);
		} catch (Exception e) {
			result.setMessage("批量修改缴费状态失败");
			logger.error("批量修改缴费状态失败", e);
			result.setIsSuccess(false);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	
}
