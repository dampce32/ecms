package com.csit.dao;

import java.util.List;

import com.csit.model.StadiumStudent;
/**
 * @Description:选择赛场DAO
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-21
 * @author jcf
 * @vesion 1.0
 */
public interface StadiumStudentDAO extends BaseDAO<StadiumStudent,Integer>{
	/**
	 * @Description: 分页查询选择赛场
	 * @Create: 2013-6-21 上午08:38:22
	 * @author jcf
	 * @update logs
	 * @param model
	 * @param page
	 * @param rows
	 * @return
	 */
	List<StadiumStudent> query(StadiumStudent model, Integer competitionId,Integer page, Integer rows);
	/**
	 * @Description: 统计选择赛场
	 * @Create: 2013-6-21 上午08:38:33
	 * @author jcf
	 * @update logs
	 * @param model
	 * @return
	 */
	Long getTotalCount(StadiumStudent model, Integer competitionId);
	/**
	 * 
	 * @Description: 学生查询自己的赛场
	 * @Create: 2013-7-5 下午02:31:42
	 * @author jcf
	 * @update logs
	 * @param model
	 * @return
	 */
	List<StadiumStudent> query(StadiumStudent model);

}
