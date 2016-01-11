package com.csit.service;

import com.csit.model.TeacherRole;
import com.csit.model.TeacherRoleId;
import com.csit.vo.ServiceResult;
/**
 * @Description:教师角色Service
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-4-17
 * @Author lys
 */
public interface TeacherRoleService extends BaseService<TeacherRole,TeacherRoleId>{
	/**
	 * @Description: 查询教师的角色
	 * @Created Time: 2013-4-17 下午4:22:19
	 * @Author lys
	 * @param model
	 * @return
	 */
	String queryRole(TeacherRole model);
	/**
	 * @Description: 更新教师角色
	 * @Created Time: 2013-4-17 下午4:22:40
	 * @Author lys
	 * @param model
	 * @param ids
	 * @param oldIds
	 * @return
	 */
	ServiceResult updateRole(TeacherRole model, String ids, String oldIds);

}
