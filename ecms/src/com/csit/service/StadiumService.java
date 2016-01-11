package com.csit.service;

import java.util.Map;

import com.csit.model.Stadium;
import com.csit.vo.ServiceResult;
/**
 * @Description:赛场Service
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-5
 * @author jcf
 * @vesion 1.0
 */
public interface StadiumService extends BaseService<Stadium, Integer> {

	/**
	 * @Description: 保存赛场
	 * @Create: 2013-6-5 下午05:45:51
	 * @author jcf
	 * @update logs
	 * @param model
	 * @return
	 */
	ServiceResult save(Stadium model);
	/**
	 * @Description: 分页查询赛场
	 * @Create: 2013-6-5 下午05:45:59
	 * @author jcf
	 * @update logs
	 * @param model
	 * @param page
	 * @param rows
	 * @return
	 */
	String query(Stadium model, Integer page, Integer rows);
	/**
	 * @Description: 
	 * @Create: 2013-6-5 下午05:47:17
	 * @author jcf
	 * @update logs
	 * @param ids
	 * @return
	 */
	ServiceResult mulDelete(String ids);
	/**
	 * 
	 * @Description: 初始化赛事考场界面
	 * @Create: 2013-6-23 下午02:39:24
	 * @author jcf
	 * @update logs
	 * @param competitionId
	 * @param page
	 * @param rows
	 * @return
	 */
	Map<String, Object> initStadium(String competitionId, Integer page,Integer rows);
	/**
	 * @Description: combobox查询
	 * @Create: 2013-5-28 下午03:40:26
	 * @author jcf
	 * @update logs
	 * @return
	 */
	String queryCombobox(Integer competitionId);
	
}
