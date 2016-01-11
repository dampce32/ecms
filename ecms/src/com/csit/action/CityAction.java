package com.csit.action;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.csit.model.City;
import com.csit.service.CityService;
import com.csit.util.JSONUtil;
import com.csit.vo.ServiceResult;
import com.opensymphony.xwork2.ModelDriven;
/**
 * @Description:城市Action
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-5-29
 * @Author lys
 */
@Controller
@Scope("prototype")
public class CityAction extends BaseAction implements ModelDriven<City> {

	private static final long serialVersionUID = -3789111829337820723L;
	private static final Logger logger = Logger.getLogger(CityAction.class);
	City model = new City();
	@Resource
	private CityService cityService;
	
	@Override
	public City getModel() {
		return model;
	}
	
	/**
	 * @Description: 保存城市
	 * @Create: 2013-5-31 下午2:29:22
	 * @author wxy
	 * @update logs
	 * @return
	 */
	public void save(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = cityService.save(model);
		} catch (Exception e) {
			result.setMessage("保存城市失败");
			logger.error("保存城市失败", e);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	/**
	 * 
	 * @Description: 批量删除城市
	 * @Create: 2013-5-31 下午15:25:55
	 * @author wxy
	 * @update logs
	 */
	public void mulDelete(){
		ServiceResult result = new ServiceResult(false);	
		try {
			result = cityService.mulDelete(ids);
		} catch (Throwable e) {
			if(e instanceof org.springframework.dao.DataIntegrityViolationException){
				result.setMessage("已被其他模块使用，不能删除");
				logger.error("已被其他模块使用，不能删除", e);
			}else{
				result.setMessage("批量城市删除失败");
				logger.error("批量城市删除失败", e);
			}
		}
		ajaxJson(result.toJSON());
	}
	
	/**
	 * 
	 * @Description: 更新城市顺序
	 * @Create: 2013-5-31 下午15:25:55
	 * @author wxy
	 * @update logs
	 */
	public void updateArray(){
		Integer updateCityId = Integer.parseInt(getParameter("updateCityId"));
		ServiceResult result = new ServiceResult(false);
		try {
			result = cityService.updateArray(model.getCityId(),updateCityId);
		} catch (Exception e) {
			result.setMessage("更新排序失败");
			logger.error("更新排序失败", e);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}          
	
	/**
	 * @Description: 分页查询城市
	 * @Created Time: 2013-5-29 上午11:11:09
	 * @Author lys
	 */
	public void query() {
		String jsonArray = cityService.query(model, page, rows);
		ajaxJson(jsonArray);
	}
	
	/**
	 * @Description: combobox查询
	 * @Created Time: 2013-5-29 下午2:55:38
	 * @Author lys
	 */
	public void queryCombobox(){
		if(model==null||model.getProvince()==null||model.getProvince().getProvinceId()==null){
			ajaxJson(JSONUtil.EMPTY_COMBOBOX_JSON);
		}
		String jsonString = cityService.queryCombobox(model.getProvince());
		ajaxJson(jsonString);
	}
}
