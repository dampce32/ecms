package com.csit.dao;

import java.util.List;

import com.csit.model.Stadium;

/**
 * @Description:赛场DAO
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-5-28
 * @author jcf
 * @vesion 1.0
 */
public interface StadiumDAO extends BaseDAO<Stadium,Integer>{
	/**
	 * @Description: 分页查询赛场
	 * @Create: 2013-6-5 上午09:42:50
	 * @author jcf
	 * @update logs
	 * @param model
	 * @param page
	 * @param rows
	 * @return
	 */
	List<Stadium> query(Stadium model,Integer page, Integer rows);
	/**
	 * @Description: 分页查询赛场
	 * @Create: 2013-6-5 上午09:43:03
	 * @author jcf
	 * @update logs
	 * @param model
	 * @return
	 */
	Long getTotalCount(Stadium model);
	/**
	 * 
	 * @Description: 查询赛事下的赛场
	 * @Create: 2013-7-3 下午02:52:23
	 * @author jcf
	 * @update logs
	 * @param competitionId
	 * @return
	 */
	List<Stadium> query(Integer competitionId);

}
