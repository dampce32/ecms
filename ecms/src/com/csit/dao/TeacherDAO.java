package com.csit.dao;

import java.util.List;
import java.util.Map;

import com.csit.model.Teacher;
/**
 * @Description:教师（用户）DAO
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-4-16
 * @Author lys
 */
public interface TeacherDAO extends BaseDAO<Teacher,Integer>{
	/**
	 * @Description: 分页查询教师
	 * @Created Time: 2013-4-17 下午3:35:08
	 * @Author lys
	 * @param page
	 * @param rows
	 * @param model
	 * @return
	 */
	List<Teacher> query(Integer page, Integer rows, Teacher model);
	/**
	 * @Description: 统计教师
	 * @Created Time: 2013-4-17 下午3:35:31
	 * @Author lys
	 * @param model
	 * @return
	 */
	Long count(Teacher model);
	/**
	 * @Description: 取得教师teacherId的跟权限
	 * @Created Time: 2013-4-17 下午8:41:19
	 * @Author lys
	 * @param teacherId
	 * @return
	 */
	List<Map<String, Object>> getRootRight(Integer teacherId);
	/**
	 * @Description: 取得教师teacherId的权限rightId下的子权限
	 * @Created Time: 2013-4-17 下午8:43:31
	 * @Author lys
	 * @param teacherId
	 * @param rightId
	 * @return
	 */
	List<Map<String, Object>> getChildrenRight(Integer teacherId, String rightId);
	/**
	 * @Description: 查询教师teacherId所具有的权限
	 * @Created Time: 2013-4-17 下午11:47:45
	 * @Author lys
	 * @param teacherId
	 * @return
	 */
	List<Map<String, Object>> queryTeacherRight(Integer teacherId);
	/**
	 * @Description: 根据类型取得子权限
	 * @Created Time: 2013-4-18 上午12:01:22
	 * @Author lys
	 * @param teacherId
	 * @param rightId
	 * @param kind
	 * @return
	 */
	List<Map<String, Object>> getChildrenRight(Integer teacherId,
			String rightId, Integer kind);
	List<Map<String, Object>> checkRight(Integer teacherId);
	/**
	 * @Description: 教师验证登录
	 * @Create: 2013-7-4 下午03:55:05
	 * @author yk
	 * @update logs
	 * @param teacherCode
	 * @param teacherPwd
	 * @return
	 */
	Teacher login(String teacherCode, String teacherPwd);

}
