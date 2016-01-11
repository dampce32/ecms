package com.csit.dao;

import java.util.List;

import com.csit.model.Information;
/**
 * @Description:资讯DAO
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-3
 * @Author lys
 */
public interface InformationDAO extends BaseDAO<Information,Integer>{
	/**
	 * @Description: 取得最大排序
	 * @Created Time: 2013-6-3 下午4:02:26
	 * @Author lys
	 * @param category
	 * @return
	 */
	Integer getMaxArray(String category);
	/**
	 * @Description: 分页查询资讯
	 * @Created Time: 2013-6-3 下午5:20:55
	 * @Author lys
	 * @param model
	 * @param page
	 * @param rows
	 * @return
	 */
	List<Information> query(Information model, Integer page, Integer rows);
	/**
	 * @Description: 统计资讯
	 * @Created Time: 2013-6-3 下午5:21:17
	 * @Author lys
	 * @param model
	 * @return
	 */
	Long getTotalCount(Information model);
	/**
	 * @Description: 打开初始化资讯
	 * @Created Time: 2013-6-3 下午10:52:12
	 * @Author lys
	 * @param model
	 * @return
	 */
	Information init(Information model);
	/**
	 * @Description: 更新资讯排序
	 * @Created Time: 2013-6-3 下午11:30:07
	 * @Author lys
	 * @param informationId
	 * @param updateInformationId
	 */
	void updateArray(String informationId, String updateInformationId);
	/**
	 * 
	 * @Description: 复制赛事使用
	 * @Create: 2013-7-15 下午09:59:42
	 * @author jcf
	 * @update logs
	 * @param newCompetitionId
	 * @param oldCompetitionId
	 * @param category
	 */
	void copyAdd(Integer newCompetitionId,Integer oldCompetitionId,String category);

}
