package com.csit.dao;

import java.util.List;
import java.util.Map;

import com.csit.model.Clazz;
/**
 * 
 * @Description: 班级DAO
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-7-22
 * @author yk
 * @vesion 1.0
 */
public interface ClazzDAO extends BaseDAO<Clazz, Integer> {
	/**
	 * 
	 * @Description: 分页查询班级 
	 * @Create: 2013-7-22 下午03:10:41
	 * @author yk
	 * @update logs
	 * @param model
	 * @param page
	 * @param rows
	 * @return
	 */
	List<Clazz> query(Clazz model, Integer page, Integer rows);
	/**
	 * 
	 * @Description: 统计班级 
	 * @Create: 2013-7-22 下午03:10:54
	 * @author yk
	 * @update logs
	 * @param model
	 * @return
	 */
	Long getTotalCount(Clazz model);
	/**
	 * 
	 * @Description: 取得班级最大顺序值 
	 * @Create: 2013-7-22 下午03:11:03
	 * @author yk
	 * @update logs
	 * @return
	 */
	Integer getMaxArray();
	/**
	 * 
	 * @Description: 更新班级顺序 
	 * @Create: 2013-7-22 下午03:11:13
	 * @author yk
	 * @update logs
	 * @param clazzId
	 * @param updateClazzId
	 */
	void updateArray(String clazzId, String updateClazzId);
	/**
	 * 
	 * @Description: 分页查询未被选择的班级
	 * @Create: 2013-7-23 上午09:49:24
	 * @author jcf
	 * @update logs
	 * @param model
	 * @param schoolGradeId
	 * @param page
	 * @param rows
	 * @return
	 */
	List<Map<String, Object>> selectQuery(Clazz model,Integer schoolGradeId, Integer page, Integer rows);
	/**
	 * 
	 * @Description: 统计未被选择的年级
	 * @Create: 2013-7-23 上午09:49:40
	 * @author jcf
	 * @update logs
	 * @param model
	 * @param schoolGradeId
	 * @return
	 */
	Long getSelectTotalCount(Clazz model,Integer schoolGradeId);
}
