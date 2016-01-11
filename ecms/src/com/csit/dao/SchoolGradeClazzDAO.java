package com.csit.dao;

import java.util.List;

import com.csit.model.SchoolGradeClazz;
/**
 * @Description:学校年级班级DAO
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-7-23
 * @author jcf
 * @vesion 1.0
 */
public interface SchoolGradeClazzDAO extends BaseDAO<SchoolGradeClazz,Integer>{
	/**
	 * @Description: 查询学校年级班级
	 * @Create: 2013-7-23 上午09:30:17
	 * @author jcf
	 * @update logs
	 * @param model
	 * @return
	 */
	List<SchoolGradeClazz> query(SchoolGradeClazz model);
	/**
	 * @Description: 获得最大顺序值
	 * @Create: 2013-7-23 上午09:30:27
	 * @author jcf
	 * @update logs
	 * @return
	 */
	Integer getMaxArray();
	/**
	 * @Description: 更新权限顺序
	 * @Create: 2013-7-23 上午09:30:37
	 * @author jcf
	 * @update logs
	 * @param schoolGradeClazzId
	 * @param updateSchoolGradeClazzId
	 */
	void updateArray(Integer schoolGradeClazzId, Integer updateSchoolGradeClazzId);
	/**
	 * 
	 * @Description: combobox根据学校年级查询班级 
	 * @Create: 2013-7-23 下午02:44:31
	 * @author yk
	 * @update logs
	 * @param schoolGradeId
	 * @return
	 */
	List<SchoolGradeClazz> queryCombobox(Integer schoolGradeId);
}
