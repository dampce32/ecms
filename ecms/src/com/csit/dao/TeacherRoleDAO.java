package com.csit.dao;

import java.util.List;

import com.csit.model.TeacherRole;
import com.csit.model.TeacherRoleId;
/**
 * @Description:教师角色
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-4-17
 * @Author lys
 */
public interface TeacherRoleDAO extends BaseDAO<TeacherRole,TeacherRoleId>{
	/**
	 * @Description: 查询教师角色
	 * @Created Time: 2013-4-17 下午4:31:46
	 * @Author lys
	 * @param model
	 * @return
	 */
	List<TeacherRole> queryRole(TeacherRole model);

}
