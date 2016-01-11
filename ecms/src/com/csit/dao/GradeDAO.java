package com.csit.dao;

import java.util.List;
import java.util.Map;

import com.csit.model.Grade;
/**
 * 
 * @Description: 年级DAO
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-7-22
 * @author yk
 * @vesion 1.0
 */
public interface GradeDAO extends BaseDAO<Grade, Integer> {
	/**
	 * 
	 * @Description: 分页查询年级 
	 * @Create: 2013-7-22 下午02:49:02
	 * @author yk
	 * @update logs
	 * @param model
	 * @param page
	 * @param rows
	 * @return
	 */
	List<Grade> query(Grade model, Integer page, Integer rows);
	/**
	 *  
	 * @Description: 统计年级 
	 * @Create: 2013-7-22 下午02:49:13
	 * @author yk
	 * @update logs
	 * @param model
	 * @return
	 */
	Long getTotalCount(Grade model);
	/**
	 * 
	 * @Description: 取得年级的最大顺序值 
	 * @Create: 2013-7-22 下午02:49:23
	 * @author yk
	 * @update logs
	 * @return
	 */
	Integer getMaxArray();
	/**
	 * 
	 * @Description: 更新年级顺序
	 * @Create: 2013-7-22 下午02:49:45
	 * @author yk
	 * @update logs
	 * @param gradeId
	 * @param updateGradeId
	 */
	void updateArray(String gradeId, String updateGradeId);
	/**
	 * @Description: 分页查询未被选择的年级
	 * @Create: 2013-7-22 下午09:40:43
	 * @author jcf
	 * @update logs
	 * @param model
	 * @param schoolId
	 * @param page
	 * @param rows
	 * @return
	 */
	List<Map<String, Object>> queryUsedSchool(Grade model,Integer schoolId, Integer page, Integer rows);
	/**
	 * @Description: 统计未被选择的年级
	 * @Create: 2013-7-22 下午09:40:53
	 * @author jcf
	 * @update logs
	 * @param model
	 * @param schoolId
	 * @return
	 */
	Long getTotalCountUsedSchool(Grade model,Integer schoolId);
}
