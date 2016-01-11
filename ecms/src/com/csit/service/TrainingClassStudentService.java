package com.csit.service;


import java.util.Map;

import com.csit.model.TrainingClassStudent;
import com.csit.vo.ServiceResult;
/**
 * @Description: 报名培训班级Service
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-7
 * @author wxy
 * @vesion 1.0
 */
public interface TrainingClassStudentService extends BaseService<TrainingClassStudent, Integer>{
	/**
	 * @Description: 保存报名培训班级
	 * @Create: 2013-6-21 上午08:43:57
	 * @author jcf
	 * @update logs
	 * @param model
	 * @return
	 */
	ServiceResult save(TrainingClassStudent model);
	/**
	 * @Description: 批量删除报名培训班级
	 * @Create: 2013-6-21 上午08:44:07
	 * @author jcf
	 * @update logs
	 * @param ids
	 * @return
	 */
	ServiceResult mulDelete(String ids);

	/**
	 * @Description: 分页查询报名培训班级
	 * @Create: 2013-6-21 上午08:44:20
	 * @author jcf
	 * @update logs
	 * @param model
	 * @param page
	 * @param rows
	 * @return
	 */
	String query(TrainingClassStudent model,Integer competitionId, Integer page, Integer rows);
	
	Map<String, Object> init(Integer studentId);

}