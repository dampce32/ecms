package com.csit.dao;

import java.util.List;

import com.csit.model.TrainingClass;
/**
 * @Description: 培训班级DAO
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-7
 * @author wxy
 * @vesion 1.0
 */
public interface TrainingClassDAO extends BaseDAO<TrainingClass,Integer>{

	/**
	 * @Description: 分页查询培训班级
	 * @Created Time: 2013-5-29 下午3:39:17
	 * @Author lys
	 * @param model
	 * @param page
	 * @param rows
	 * @return
	 */
	List<TrainingClass> query(TrainingClass model, Integer page, Integer rows);
	/**
	 * @Description: 统计培训班级
	 * @Created Time: 2013-5-29 下午3:39:39
	 * @Author lys
	 * @param model
	 * @return
	 */
	Long getTotalCount(TrainingClass model);
	/**
	 * @Description: 查询培训班级
	 * @Created Time: 2013-5-29 下午4:14:33
	 * @Author lys
	 * @param city
	 * @return
	 */
	List<TrainingClass> queryCombobox(TrainingClass trainingClass,Integer competitionId);

}
