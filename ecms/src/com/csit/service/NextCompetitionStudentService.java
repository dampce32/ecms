package com.csit.service;

import java.util.Map;

import com.csit.model.NextCompetitionStudent;
import com.csit.vo.ServiceResult;
/**
 * @Description:赛事晋级Service
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-23
 * @Author lys
 */
public interface NextCompetitionStudentService extends BaseService<NextCompetitionStudent, Integer>{
	/**
	 * @Description: 分页查询赛事晋级
	 * @Created Time: 2013-6-23 下午4:36:50
	 * @Author lys
	 * @param model
	 * @param page
	 * @param rows
	 * @return
	 */
	String query(NextCompetitionStudent model, Integer page, Integer rows);
	/**
	 * @Description: 选择查询还没获得晋级的学生
	 * @Created Time: 2013-6-23 下午5:28:18
	 * @Author lys
	 * @param model
	 * @param page
	 * @param rows
	 * @return
	 */
	String selectQuery(NextCompetitionStudent model, Integer page, Integer rows);
	/**
	 * @Description: 保存多个晋级学生
	 * @Created Time: 2013-6-23 下午5:56:09
	 * @Author lys
	 * @param model
	 * @param ids
	 * @param scores 
	 * @return
	 */
	ServiceResult mulSave(NextCompetitionStudent model, String ids, String scores);
	/**
	 * @Description: 删除晋级学生
	 * @Created Time: 2013-6-23 下午7:30:01
	 * @Author lys
	 * @param ids
	 * @return
	 */
	ServiceResult mulDelete(String ids);
	Map<String, Object> init(Integer competitionId,String competitionGroupId, Integer page,Integer rows);

}
