package com.csit.service;

import java.util.Map;

import com.csit.model.CompetitionPrizeStudent;
import com.csit.vo.ServiceResult;
/**
 * @Description:赛事获奖学生Service
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-22
 * @Author lys
 */
public interface CompetitionPrizeStudentService extends BaseService<CompetitionPrizeStudent,Integer>{
	/**
	 * @Description: 分页查询赛事获奖学生
	 * @Created Time: 2013-6-22 下午8:18:12
	 * @Author lys
	 * @param model
	 * @param rows 
	 * @param page 
	 * @return
	 */
	String query(CompetitionPrizeStudent model, Integer page, Integer rows);
	/**
	 * @Description: 选择查询还没获得奖项的学生
	 * @Created Time: 2013-6-23 上午10:19:41
	 * @Author lys
	 * @param model
	 * @param page
	 * @param rows
	 * @return
	 */
	String selectQuery(CompetitionPrizeStudent model, Integer page, Integer rows);
	/**
	 * @Description: 
	 * @Created Time: 2013-6-23 上午11:09:33
	 * @Author lys
	 * @param model
	 * @param ids
	 * @return
	 */
	ServiceResult mulSave(CompetitionPrizeStudent model, String ids);
	/**
	 * @Description: 删除获奖学生
	 * @Created Time: 2013-6-23 下午12:34:55
	 * @Author lys
	 * @param ids
	 * @return
	 */
	ServiceResult mulDelete(String ids);
	Map<String, Object> init(Integer competitionId,String competitionGroupId, Integer page,
			Integer rows);

}
