package com.csit.service;

import com.csit.model.CompetitionGroup;
import com.csit.vo.ServiceResult;
/**
 * @Description:赛事组别Service
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-5-31
 * @author jcf
 * @vesion 1.0
 */
public interface CompetitionGroupService extends BaseService<CompetitionGroup, Integer> {

	/**
	 * @Description: 保存赛事组别
	 * @Create: 2013-5-31 上午10:21:55
	 * @author jcf
	 * @update logs
	 * @param model
	 * @return
	 */
	ServiceResult save(CompetitionGroup model,String groupIds);
	/**
	 * @Description: 分页查询赛事组别
	 * @Create: 2013-5-31 上午10:22:02
	 * @author jcf
	 * @update logs
	 * @param model
	 * @param page
	 * @param rows
	 * @return
	 */
	String query(CompetitionGroup model, Integer page, Integer rows);
	/**
	 * @Description: 批量删除赛事组别
	 * @Create: 2013-5-31 上午10:22:07
	 * @author jcf
	 * @update logs
	 * @param ids
	 * @return
	 */
	ServiceResult delete(CompetitionGroup model);
	/**
	 * @Description: 更新排序
	 * @Create: 2013-6-5 下午04:35:53
	 * @author jcf
	 * @update logs
	 * @param competitionGroupId
	 * @param updateCompetitionGroupId
	 * @return
	 */
	ServiceResult updateArray(Integer competitionGroupId, Integer updateCompetitionGroupId);
	/**
	 * 
	 * @Description: combobox查询
	 * @Create: 2013-6-6 上午10:18:36
	 * @author jcf
	 * @update logs
	 * @param model
	 * @return
	 */
	String queryCombobox(CompetitionGroup model);
	/**
	 * @Description: 大赛报名初始化
	 * @Created Time: 2013-6-19 上午11:09:38
	 * @Author lys
	 * @param model
	 * @param studentId
	 * @return
	 */
	ServiceResult initEnrollStu(CompetitionGroup model, Integer studentId);
	/**
	 * @Description: queryDatagrid查询
	 * @Created Time: 2013-6-23 上午1:19:38
	 * @Author lys
	 * @param model
	 * @return
	 */
	String queryDatagrid(CompetitionGroup model);
}
