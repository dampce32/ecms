package com.csit.dao;

import java.util.List;

import com.csit.model.CompetitionPrize;
/**
 * @Description:赛事奖项DAO
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-4
 * @author jcf
 * @vesion 1.0
 */
public interface CompetitionPrizeDAO extends BaseDAO<CompetitionPrize,Integer>{
	/**
	 * @Description: 分页查询赛事奖项
	 * @Create: 2013-6-4 上午10:26:42
	 * @author jcf
	 * @update logs
	 * @param model
	 * @return
	 */
	List<CompetitionPrize> query(CompetitionPrize model);
	/**
	 * @Description: 获得最大顺序值
	 * @Create: 2013-6-4 上午08:53:35
	 * @author jcf
	 * @update logs
	 * @param competitionId
	 * @return
	 */
	Integer getMaxArray();
	/**
	 * @Description: 更新权限顺序
	 * @Create: 2013-6-5 下午05:18:29
	 * @author jcf
	 * @update logs
	 * @param competitionPrizeId
	 * @param updateCompetitionPrizeId
	 */
	void updateArray(Integer competitionPrizeId, Integer updateCompetitionPrizeId);
	/**
	 * @Description: combobox查询赛事奖项
	 * @Created Time: 2013-6-22 下午9:22:37
	 * @Author lys
	 * @param model
	 * @return
	 */
	List<CompetitionPrize> queryCombobox(CompetitionPrize model);
	void copyAdd(Integer newCompetitionId,Integer oldCompetitionId);

}
