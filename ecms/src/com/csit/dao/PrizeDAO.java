package com.csit.dao;

import java.util.List;

import com.csit.model.Prize;
/**
 * @Description:奖项DAO
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-5-30
 * @author wxy
 * @vesion 1.0
 */
public interface PrizeDAO extends BaseDAO<Prize, Integer> {
	/**
	 * @Description: 
	 * @param
	 * @Create: 2013-5-30 下午12:58:10
	 * @author wxy
	 * @update logs
	 * @param model
	 * @param page
	 * @param rows
	 * @return
	 * @return
	 * @throws Exception
	 */
	List<Prize> query(Prize model, Integer page, Integer rows);
	/**
	 * 
	 * @Description: 统计奖项
	 * @Create: 2013-5-28 下午05:27:51
	 * @author wxy
	 * @update logs
	 * @param model
	 * @return
	 */
	Long getTotalCount(Prize model);
	/**
	 * 
	 * @Description: 取得奖项的最大顺序值 
	 * @Create: 2013-5-29 上午09:16:33
	 * @author wxy
	 * @update logs
	 * @param rightId
	 * @return
	 */
	Integer getMaxArray();
	/**
	 * 
	 * @Description: 更新奖项顺序
	 * @Create: 2013-5-29 上午09:17:02
	 * @author wxy
	 * @update logs
	 * @param PrizeId
	 * @param updatePrizeId
	 */
	void updateArray(Integer prizeId, Integer updatePrizeId);
	List<Prize> query(Integer competitionGroupId, Integer page, Integer rows);
	Long getTotalCount(Integer competitionGroupId);
}
