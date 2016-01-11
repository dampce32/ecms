package com.csit.service;

import com.csit.model.SchoolGradeClazz;
import com.csit.vo.ServiceResult;
/**
 * @Description:学校年级班级Service
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-4
 * @author jcf
 * @vesion 1.0
 */
public interface SchoolGradeClazzService extends BaseService<SchoolGradeClazz, Integer> {

	/**
	 * @Description: 保存学校年级班级
	 * @Create: 2013-7-23 上午09:35:38
	 * @author jcf
	 * @update logs
	 * @param model
	 * @param clazzIds
	 * @return
	 */
	ServiceResult save(SchoolGradeClazz model,String clazzIds);
	/**
	 * @Description: 查询学校年级班级
	 * @Create: 2013-7-23 上午09:35:49
	 * @author jcf
	 * @update logs
	 * @param model
	 * @return
	 */
	String query(SchoolGradeClazz model);
	/**
	 * @Description: 批量删除学校年级班级
	 * @Create: 2013-7-23 上午09:35:58
	 * @author jcf
	 * @update logs
	 * @param model
	 * @return
	 */
	ServiceResult delete(SchoolGradeClazz model);
	/**
	 * @Description: 更新排序
	 * @Create: 2013-7-23 上午09:36:12
	 * @author jcf
	 * @update logs
	 * @param schoolGradeClazzId
	 * @param updateSchoolGradeClazzId
	 * @return
	 */
	ServiceResult updateArray(Integer schoolGradeClazzId, Integer updateSchoolGradeClazzId);
	/**
	 * 
	 * @Description: combobox根据学校年级查询班级 
	 * @Create: 2013-7-23 下午02:47:32
	 * @author yk
	 * @update logs
	 * @param schoolId
	 * @return
	 */
	String queryCombobox(Integer schoolGradeId);
}
