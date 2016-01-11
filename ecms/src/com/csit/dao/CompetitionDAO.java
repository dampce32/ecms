package com.csit.dao;

import java.util.List;

import com.csit.model.Competition;
/**
 * @Description:赛事DAO
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-5-30
 * @author jcf
 * @vesion 1.0
 */
public interface CompetitionDAO extends BaseDAO<Competition,Integer>{
	/**
	 * @Description: 统计赛事
	 * @Create: 2013-5-30 上午09:59:14
	 * @author jcf
	 * @update logs
	 * @param model
	 * @return
	 */
	Long getTotalCount(Competition model);
	/**
	 * @Description: 分页查询赛事
	 * @Create: 2013-5-30 上午09:59:22
	 * @author jcf
	 * @update logs
	 * @param StudentId
	 * @param page
	 * @param rows
	 * @return
	 */
	List<Competition> query(Competition model, Integer page, Integer rows);
	/**
	 * @Description: combobox使用
	 * @Create: 2013-5-30 上午10:13:36
	 * @author jcf
	 * @update logs
	 * @param model
	 * @return
	 */
	List<Competition> queryCombobox(Competition model);
	/**
	 * @Description: 欢迎主界面表头赛事信息
	 * @Created Time: 2013-6-2 上午9:36:05
	 * @Author lys
	 * @return
	 */
	List<Competition> queryIndexTitle();

}
