package com.csit.service;


import java.util.Map;

import com.csit.model.StadiumStudent;
import com.csit.vo.ServiceResult;
/**
 * @Description:选择赛场Service
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-23
 * @author jcf
 * @vesion 1.0
 */
public interface StadiumStudentService extends BaseService<StadiumStudent, Integer>{
	/**
	 * 
	 * @Description: 保存选择赛场学生
	 * @Create: 2013-6-23 下午09:06:46
	 * @author jcf
	 * @update logs
	 * @param model
	 * @return
	 */
	ServiceResult save(StadiumStudent model);
	/**
	 * @Description: 批量删除选择赛场
	 * @Create: 2013-6-21 上午08:44:07
	 * @author jcf
	 * @update logs
	 * @param ids
	 * @return
	 */
	ServiceResult mulDelete(String ids);

	/**
	 * @Description: 分页查询选择赛场
	 * @Create: 2013-6-21 上午08:44:20
	 * @author jcf
	 * @update logs
	 * @param model
	 * @param page
	 * @param rows
	 * @return
	 */
	String query(StadiumStudent model,Integer competitionId, Integer page, Integer rows);
	Map<String, Object> init(Integer studentId);

}