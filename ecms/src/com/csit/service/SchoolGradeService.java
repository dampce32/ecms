package com.csit.service;

import com.csit.model.SchoolGrade;
import com.csit.vo.ServiceResult;
/**
 * @Description:学校年级Service
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-7-22
 * @author jcf
 * @vesion 1.0
 */
public interface SchoolGradeService extends BaseService<SchoolGrade, Integer> {

	/**
	 * @Description: 保存学校年级
	 * @Create: 2013-7-22 下午05:24:05
	 * @author jcf
	 * @update logs
	 * @param model
	 * @param groupIds
	 * @return
	 */
	ServiceResult save(SchoolGrade model,String gradeIds);
	/**
	 * @Description: 查询学校年级
	 * @Create: 2013-7-22 下午05:24:46
	 * @author jcf
	 * @update logs
	 * @param model
	 * @return
	 */
	String query(SchoolGrade model);
	/**
	 * @Description: 批量删除学校年级
	 * @Create: 2013-7-22 下午05:24:57
	 * @author jcf
	 * @update logs
	 * @param model
	 * @return
	 */
	ServiceResult delete(SchoolGrade model);
	/**
	 * @Description: 更新排序
	 * @Create: 2013-7-22 下午05:25:04
	 * @author jcf
	 * @update logs
	 * @param schoolGradeId
	 * @param updateSchoolGradeId
	 * @return
	 */
	ServiceResult updateArray(Integer schoolGradeId, Integer updateSchoolGradeId);
	/**
	 * 
	 * @Description: 根据学校查询年级 
	 * @Create: 2013-7-23 下午02:33:00
	 * @author yk
	 * @update logs
	 * @return
	 */
	String queryCombobox(Integer schoolId);
}
