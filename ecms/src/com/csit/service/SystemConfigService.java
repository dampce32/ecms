package com.csit.service;

import com.csit.model.SystemConfig;
import com.csit.vo.ServiceResult;

public interface SystemConfigService extends BaseService<SystemConfig, Integer> {

	ServiceResult save(SystemConfig model,String proPath);
	ServiceResult init();
}
