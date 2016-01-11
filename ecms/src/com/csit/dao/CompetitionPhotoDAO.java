package com.csit.dao;

import java.util.List;

import com.csit.model.Competition;
import com.csit.model.CompetitionPhoto;
/**
 * @Description:赛事风采DAO
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-11
 * @Author lys
 */
public interface CompetitionPhotoDAO extends BaseDAO<CompetitionPhoto,Integer>{
	/**
	 * @param competition 
	 * @Description: 取得赛事下的最大排序
	 * @Created Time: 2013-6-11 下午10:55:49
	 * @Author lys
	 * @return
	 */
	Integer getMaxArray(Competition competition);
	/**
	 * @Description: 分页查询赛事风采
	 * @Created Time: 2013-6-12 上午10:19:24
	 * @Author lys
	 * @param model
	 * @param page
	 * @param rows
	 * @return
	 */
	List<CompetitionPhoto> query(CompetitionPhoto model, Integer page,
			Integer rows);
	/**
	 * @Description: 统计赛事风采
	 * @Created Time: 2013-6-12 上午10:19:46
	 * @Author lys
	 * @param model
	 * @return
	 */
	Long getTotalCount(CompetitionPhoto model);
	/**
	 * @Description: 改变赛事风采的排序
	 * @Created Time: 2013-6-12 下午5:19:55
	 * @Author lys
	 * @param competitionPhotoId
	 * @param updateCompetitionPhotoId
	 */
	void updateArray(Integer competitionPhotoId, Integer updateCompetitionPhotoId);
	/**
	 * @Description: 分页查询初始化赛事风采
	 * @Created Time: 2013-6-13 下午7:28:35
	 * @Author lys
	 * @param parseInt
	 * @param photoType
	 * @param page
	 * @param rows
	 * @return
	 */
	List<CompetitionPhoto> query(Integer competitionId, String photoType, Integer page,
			Integer rows);
	/**
	 * @Description: 统计初始化赛事风采
	 * @Created Time: 2013-6-13 下午7:32:01
	 * @Author lys
	 * @param competitionId
	 * @param photoType
	 * @return
	 */
	Long getTotalCount(Integer competitionId, String photoType);
	/**
	 * 
	 * @Description: 复制赛事使用
	 * @Create: 2013-7-15 下午10:01:57
	 * @author jcf
	 * @update logs
	 * @param newCompetitionId
	 * @param oldCompetitionId
	 * @param photoType
	 */
	
	void copyAdd(Integer newCompetitionId,Integer oldCompetitionId,String photoType);

}
