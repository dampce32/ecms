package com.csit.dao;

import java.util.List;
import java.util.Map;

import com.csit.model.CompetitionPrizeStudent;
/**
 * @Description:赛事获奖学生DAO
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-22
 * @Author lys
 */
public interface CompetitionPrizeStudentDAO extends BaseDAO<CompetitionPrizeStudent,Integer>{
	/**
	 * @Description: 分页赛事获奖学生
	 * @Created Time: 2013-6-22 下午8:31:57
	 * @Author lys
	 * @param model
	 * @param page
	 * @param rows
	 * @return
	 */
	List<CompetitionPrizeStudent> query(CompetitionPrizeStudent model,
			Integer page, Integer rows);
	/**
	 * @Description: 统计赛事获奖学生
	 * @Created Time: 2013-6-22 下午8:32:37
	 * @Author lys
	 * @param model
	 * @return
	 */
	Long getTotalCount(CompetitionPrizeStudent model);
	/**
	 * @Description: 分页查询还没获得奖项的学生
	 * @Created Time: 2013-6-23 上午10:26:14
	 * @Author lys
	 * @param model
	 * @param page
	 * @param rows
	 * @return
	 */
	List<Map<String, Object>> selectQuery(CompetitionPrizeStudent model,
			Integer page, Integer rows);
	/**
	 * @Description: 统计还没获得奖项的学生
	 * @Created Time: 2013-6-23 上午10:27:02
	 * @Author lys
	 * @param model
	 * @return
	 */
	Long getTotalCountSelectQuery(CompetitionPrizeStudent model);

}
