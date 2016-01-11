package com.csit.service;

import com.csit.model.CompetitionPrize;
import com.csit.vo.ServiceResult;
/**
 * @Description:赛事奖项Service
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-4
 * @author jcf
 * @vesion 1.0
 */
public interface CompetitionPrizeService extends BaseService<CompetitionPrize, Integer> {

	/**
	 * @Description: 保存赛事奖项
	 * @Create: 2013-6-4 上午09:02:07
	 * @author jcf
	 * @update logs
	 * @param model
	 * @param grouoIds
	 * @return
	 */
	ServiceResult save(CompetitionPrize model,String prizeIds);
	/**
	 * @Description: 分页查询赛事奖项
	 * @Create: 2013-6-4 上午09:02:17
	 * @author jcf
	 * @update logs
	 * @param model
	 * @param page
	 * @param rows
	 * @return
	 */
	String query(CompetitionPrize model);
	/**
	 * @Description: 批量删除赛事奖项
	 * @Create: 2013-6-4 上午09:02:28
	 * @author jcf
	 * @update logs
	 * @param model
	 * @return
	 */
	ServiceResult delete(CompetitionPrize model);
	/**
	 * @Description: 更新排序
	 * @Create: 2013-6-5 下午05:16:57
	 * @author jcf
	 * @update logs
	 * @param competitionPrizeId
	 * @param updateCompetitionPrizeId
	 * @return
	 */
	ServiceResult updateArray(Integer competitionPrizeId, Integer updateCompetitionPrizeId);
	/**
	 * @Description: combobox查询
	 * @Created Time: 2013-6-22 下午9:21:13
	 * @Author lys
	 * @param model
	 * @return
	 */
	String queryCombobox(CompetitionPrize model);
	/**
	 * @Description: queryDatagrid查询
	 * @Created Time: 2013-6-23 上午1:33:43
	 * @Author lys
	 * @param model
	 * @return
	 */
	String queryDatagrid(CompetitionPrize model);
}
