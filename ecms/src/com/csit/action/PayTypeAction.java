package com.csit.action;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.csit.model.PayType;
import com.csit.service.PayTypeService;
import com.csit.vo.ServiceResult;
import com.opensymphony.xwork2.ModelDriven;

/**
 * @Description:缴费类型Action
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-9
 * @author jcf
 * @vesion 1.0
 */
@Controller
@Scope("prototype")
public class PayTypeAction extends BaseAction implements ModelDriven<PayType> {

	private static final long serialVersionUID = -4648466664583512735L;
	private static final Logger logger = Logger.getLogger(PayTypeAction.class);
	private PayType model = new PayType();
	@Resource
	private PayTypeService payTypeService;

	@Override
	public PayType getModel() {
		return model;
	}
	
	/**
	 * @Description: 保存缴费类型
	 * @Create: 2013-6-9 上午09:12:55
	 * @author jcf
	 * @update logs
	 */
	public void save() {
		ServiceResult result = new ServiceResult(false);
		try {
			result = payTypeService.save(model);
		} catch (Exception e) {
			result.setMessage("保存缴费类型失败");
			logger.error("保存缴费类型失败", e);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}

	/**
	 * @Description: 分页查询缴费类型
	 * @Create: 2013-6-9 上午09:13:05
	 * @author jcf
	 * @update logs
	 */
	public void query() {
		String jsonArray = payTypeService.query(model, page, rows);
		ajaxJson(jsonArray);
	}
	/**
	 * @Description: 批量删除缴费类型
	 * @Create: 2013-6-9 上午09:13:13
	 * @author jcf
	 * @update logs
	 */
	public void mulDelete(){
		ServiceResult result = new ServiceResult(false);	
		try {
			result = payTypeService.mulDelete(ids);
		} catch (Throwable e) {
			result.setMessage("批量删除缴费类型失败");
			logger.error("批量删除缴费类型失败", e);
		}
		ajaxJson(result.toJSON());
	}
	/**
	 * @Description: 批量修改缴费类型状态
	 * @Create: 2013-6-9 上午09:13:21
	 * @author jcf
	 * @update logs
	 */
	public void mulUpdateStatus(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = payTypeService.mulUpdateStatus(ids,model);
		} catch (Exception e) {
			result.setMessage("批量修改缴费类型状态失败");
			logger.error("批量修改缴费类型状态失败", e);
			result.setIsSuccess(false);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	/**
	 * @Description: 更新排序
	 * @Create: 2013-6-9 上午09:13:42
	 * @author jcf
	 * @update logs
	 */
	public void updateArray(){
		Integer updatePayTypeId = Integer.parseInt(getParameter("updatePayTypeId"));
		ServiceResult result = new ServiceResult(false);
		try {
			result = payTypeService.updateArray(model.getPayTypeId(),updatePayTypeId);
		} catch (Exception e) {
			result.setMessage("更新排序失败");
			logger.error("更新排序失败", e);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	/**
	 * @Description: combobox查询
	 * @Create: 2013-6-9 上午09:13:51
	 * @author jcf
	 * @update logs
	 */
	public void queryCombobox() {
		try {
			String jsonString = payTypeService.queryCombobox(model);
			ajaxJson(jsonString);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
