package com.csit.dao;

import java.util.List;
import java.util.Map;

import com.csit.model.CompetitionGroup;
/**
 * @Description:赛事组别DAO
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-5-31
 * @author jcf
 * @vesion 1.0
 */
public interface CompetitionGroupDAO extends BaseDAO<CompetitionGroup,Integer>{
	/**
	 * @Description: 统计赛事组别
	 * @Create: 2013-5-31 上午10:14:34
	 * @author jcf
	 * @update logs
	 * @param model
	 * @return
	 */
	Long getTotalCount(CompetitionGroup model);
	/**
	 * @Description: 分页查询赛事组别
	 * @Create: 2013-5-31 上午10:14:38
	 * @author jcf
	 * @update logs
	 * @param model
	 * @param page
	 * @param rows
	 * @return
	 */
	List<CompetitionGroup> query(CompetitionGroup model, Integer page, Integer rows);
	/**
	 * 
	 * @Description: 获得最大顺序值
	 * @Create: 2013-5-28 下午03:42:27
	 * @author jcf
	 * @update logs
	 * @return
	 */
	Integer getMaxArray(Integer competitionId);
	/**
	 * @Description: 更新权限顺序
	 * @Create: 2013-5-31 下午03:37:19
	 * @author jcf
	 * @update logs
	 * @param groupId
	 * @param updateGroupId
	 */
	void updateArray(Integer competitionGroupId,Integer updateCompetitionGroupId);
	/**
	 * @Description: combobox使用
	 * @Create: 2013-6-6 上午10:16:10
	 * @author jcf
	 * @update logs
	 * @param model
	 * @return
	 */
	List<CompetitionGroup> queryCombobox(CompetitionGroup model);
	/**
	 * @Description: 初始化赛程报名
	 * @Created Time: 2013-6-19 下午2:22:25
	 * @Author lys
	 * @param parseInt
	 * @param studentId
	 * @return
	 */
	Map<String, Object> initEnrollStu(Integer competitionId, Integer studentId);
	List<CompetitionGroup> query(Integer competitionId);
	/**
	 * 
	 * @Description: 复制赛事使用
	 * @Create: 2013-7-16 上午11:24:30
	 * @author jcf
	 * @update logs
	 * @param newCompetitionId
	 * @param oldCompetitionId
	 */
	void copyAdd(Integer newCompetitionId,Integer oldCompetitionId);

}
