package com.csit.action;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.csit.model.Area;
import com.csit.service.AreaService;
import com.csit.util.JSONUtil;
import com.csit.vo.ServiceResult;
import com.opensymphony.xwork2.ModelDriven;
/**
 * @Description : 区县Action
 * @Copyright : 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-5-29
 * @Author lys
 */
@Controller
@Scope("prototype")
public class AreaAction extends BaseAction implements ModelDriven<Area> {
	
	private static final long serialVersionUID = -2838387452372378492L;
	private static final Logger logger = Logger.getLogger(AreaAction.class);
	Area model = new Area();
	@Resource
	private AreaService areaService;
	
	@Override
	public Area getModel() {
		return model;
	}
	
	/**
	 * @Description: 保存区县
	 * @Create: 2013-5-31 下午2:29:22
	 * @author wxy
	 * @update logs
	 * @return
	 */
	public void save(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = areaService.save(model);
		} catch (Exception e) {
			result.setMessage("保存区县失败");
			logger.error("保存区县失败", e);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	/**
	 * 
	 * @Description: 批量删除区县
	 * @Create: 2013-5-31 下午15:25:55
	 * @author wxy
	 * @update logs
	 */
	public void mulDelete(){
		ServiceResult result = new ServiceResult(false);	
		try {
			result = areaService.mulDelete(ids);
		} catch (Throwable e) {
			result.setMessage("批量区县删除失败");
			logger.error("批量区县删除失败", e);
		}
		ajaxJson(result.toJSON());
	}
	
	/**
	 * 
	 * @Description: 更新区县顺序
	 * @Create: 2013-5-31 下午15:25:55
	 * @author wxy
	 * @update logs
	 */
	public void updateArray(){
		Integer updateAreaId = Integer.parseInt(getParameter("updateAreaId"));
		ServiceResult result = new ServiceResult(false);
		try {
			result = areaService.updateArray(model.getAreaId(),updateAreaId);
		} catch (Exception e) {
			result.setMessage("更新排序失败");
			logger.error("更新排序失败", e);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}          
	
	
	/**
	 * @Description: 分页查询区县
	 * @Created Time: 2013-5-29 上午11:11:09
	 * @Author lys
	 */
	public void query() {
		String jsonArray = areaService.query(model, page, rows);
		ajaxJson(jsonArray);
	}
	
	/**
	 * @Description: combobox查询
	 * @Created Time: 2013-5-29 下午2:55:38
	 * @Author lys
	 */
	public void queryCombobox(){
		if(model==null||model.getCity()==null||model.getCity().getCityId()==null){
			ajaxJson(JSONUtil.EMPTY_COMBOBOX_JSON);
		}
		String jsonString = areaService.queryCombobox(model.getCity());
		ajaxJson(jsonString);
	}
}