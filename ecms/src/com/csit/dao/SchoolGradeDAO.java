package com.csit.dao;

import java.util.List;

import com.csit.model.SchoolGrade;
/**
 * @Description:学校年级
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-7-22
 * @author jcf
 * @vesion 1.0
 */
public interface SchoolGradeDAO extends BaseDAO<SchoolGrade,Integer>{
	/**
	 * @Description: 查询学校年级
	 * @Create: 2013-7-22 下午05:15:33
	 * @author jcf
	 * @update logs
	 * @param model
	 * @return
	 */
	List<SchoolGrade> query(SchoolGrade model);
	/**
	 * @Description: 获得最大顺序值
	 * @Create: 2013-7-22 下午05:16:06
	 * @author jcf
	 * @update logs
	 * @param schoolId
	 * @return
	 */
	Integer getMaxArray(Integer schoolId);
	/**
	 * @Description: 更新权限顺序
	 * @Create: 2013-7-22 下午05:16:14
	 * @author jcf
	 * @update logs
	 * @param schoolGradeId
	 * @param updateSchoolGradeId
	 */
	void updateArray(Integer schoolGradeId,Integer updateSchoolGradeId);
	/**
	 * 
	 * @Description: 根据学校查询年级 
	 * @Create: 2013-7-23 下午02:33:54
	 * @author yk
	 * @update logs
	 * @return
	 */
	List<SchoolGrade> queryCombobox(Integer schoolId);
}
