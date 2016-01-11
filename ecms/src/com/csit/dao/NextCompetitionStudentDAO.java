package com.csit.dao;

import java.util.List;
import java.util.Map;

import com.csit.model.NextCompetitionStudent;
/**
 * @Description:赛事晋级DAO
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-23
 * @Author lys
 */
public interface NextCompetitionStudentDAO extends BaseDAO<NextCompetitionStudent,Integer>{
	/**
	 * @Description: 分页查询赛事晋级
	 * @Created Time: 2013-6-23 下午4:39:41
	 * @Author lys
	 * @param model
	 * @param page
	 * @param rows
	 * @return
	 */
	List<NextCompetitionStudent> query(NextCompetitionStudent model,
			Integer page, Integer rows);
	/**
	 * @Description: 统计赛事晋级
	 * @Created Time: 2013-6-23 下午4:39:59
	 * @Author lys
	 * @param model
	 * @return
	 */
	Long getTotalCount(NextCompetitionStudent model);
	/**
	 * @Description: 查询还没获得晋级的学生
	 * @Created Time: 2013-6-23 下午5:29:56
	 * @Author lys
	 * @param model
	 * @param page
	 * @param rows
	 * @return
	 */
	List<Map<String, Object>> selectQuery(NextCompetitionStudent model,
			Integer page, Integer rows);
	/**
	 * @Description: 统计还没获得晋级的学生
	 * @Created Time: 2013-6-23 下午5:30:09
	 * @Author lys
	 * @param model
	 * @return
	 */
	Long getTotalCountSelectQuery(NextCompetitionStudent model);

}
