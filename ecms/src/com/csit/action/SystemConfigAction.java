package com.csit.action;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.csit.model.SystemConfig;
import com.csit.service.SystemConfigService;
import com.csit.vo.ServiceResult;
import com.opensymphony.xwork2.ModelDriven;
@Controller
@Scope("prototype")
public class SystemConfigAction extends BaseAction implements
		ModelDriven<SystemConfig> {
	private static final long serialVersionUID = -5402863707963171477L;
	private static final Logger logger = Logger.getLogger(SystemConfigAction.class);
	private SystemConfig model = new SystemConfig();
	@Resource
	private SystemConfigService systemConfigService;
	@Override
	public SystemConfig getModel() {
		return model;
	}
	
	public void save() {
		ServiceResult result = new ServiceResult(false);
		String proPath = getRequest().getSession().getServletContext().getRealPath("/WEB-INF/classes/application.properties");
		try {
			result = systemConfigService.save(model,proPath);
		} catch (Exception e) {
			result.setMessage("保存系统配置失败");
			logger.error("保存系统配置失败", e);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	
	public void init() {
		ServiceResult result = new ServiceResult(false);
		try {
			result = systemConfigService.init();
		} catch (Exception e) {
			result.setMessage("初始化系统配置失败");
			logger.error("初始化系统配置失败", e);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	
}
