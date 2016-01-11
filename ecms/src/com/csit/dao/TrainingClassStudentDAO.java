package com.csit.dao;

import java.util.List;

import com.csit.model.TrainingClassStudent;
/**
 * @Description:报名报名培训班级DAO
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-21
 * @author jcf
 * @vesion 1.0
 */
public interface TrainingClassStudentDAO extends BaseDAO<TrainingClassStudent,Integer>{
	/**
	 * @Description: 分页查询报名培训班级
	 * @Create: 2013-6-21 上午08:38:22
	 * @author jcf
	 * @update logs
	 * @param model
	 * @param page
	 * @param rows
	 * @return
	 */
	List<TrainingClassStudent> query(TrainingClassStudent model,Integer competitionId, Integer page, Integer rows);
	/**
	 * @Description: 统计报名培训班级
	 * @Create: 2013-6-21 上午08:38:33
	 * @author jcf
	 * @update logs
	 * @param model
	 * @return
	 */
	Long getTotalCount(TrainingClassStudent model,Integer competitionId);

}
