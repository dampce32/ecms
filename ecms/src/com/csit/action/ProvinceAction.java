package com.csit.action;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.csit.model.Province;
import com.csit.service.ProvinceService;
import com.csit.vo.ServiceResult;
import com.opensymphony.xwork2.ModelDriven;
/**
 * @Description: 省份Action
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-5-29
 * @Author lys
 */
@Controller
@Scope("prototype")
public class ProvinceAction extends BaseAction implements ModelDriven<Province> {

	private static final long serialVersionUID = 7684474215749492338L;
	private static final Logger logger = Logger.getLogger(ProvinceAction.class);
	Province model = new Province();
	@Resource
	private ProvinceService provinceService;
	
	@Override
	public Province getModel() {
		return model;
	}
	
	
	/**
	 * @Description: 保存省份
	 * @Create: 2013-5-31 下午2:29:22
	 * @author wxy
	 * @update logs
	 * @return
	 */
	public void save(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = provinceService.save(model);
		} catch (Exception e) {
			result.setMessage("保存省份失败");
			logger.error("保存省份失败", e);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	
	/**
	 * 
	 * @Description: 批量删除省份
	 * @Create: 2013-5-31 下午15:25:55
	 * @author wxy
	 * @update logs
	 */
	public void mulDelete(){
		ServiceResult result = new ServiceResult(false);	
		try {
			result = provinceService.mulDelete(ids);
		} catch (Throwable e) {
			result.setMessage("批量省份删除失败");
			logger.error("批量省份删除失败", e);
		}
		ajaxJson(result.toJSON());
	}
	
	/**
	 * 
	 * @Description: 更新省份顺序
	 * @Create: 2013-5-31 下午15:25:55
	 * @author wxy
	 * @update logs
	 */
	public void updateArray(){
		Integer updateProvinceId = Integer.parseInt(getParameter("updateProvinceId"));
		ServiceResult result = new ServiceResult(false);
		try {
			result = provinceService.updateArray(model.getProvinceId(),updateProvinceId);
		} catch (Exception e) {
			result.setMessage("更新排序失败");
			logger.error("更新排序失败", e);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	
	
	/**
	 * @Description: 分页查询省份
	 * @Created Time: 2013-5-29 上午11:11:09
	 * @Author lys
	 */
	public void query() {
		String jsonArray = provinceService.query(model, page, rows);
		ajaxJson(jsonArray);
	}
	/**
	 * @Description: combobox查询
	 * @Created Time: 2013-5-29 下午2:55:38
	 * @Author lys
	 */
	public void queryCombobox(){
		String jsonString = provinceService.queryCombobox();
		ajaxJson(jsonString);
	}
}
