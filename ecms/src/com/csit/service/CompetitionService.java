package com.csit.service;

import com.csit.model.Competition;
import com.csit.vo.ServiceResult;
/**
 * @Description:赛事Service
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-5-30
 * @author jcf
 * @vesion 1.0
 */
public interface CompetitionService extends BaseService<Competition, Integer> {

	/**
	 * @Description: 保存赛事
	 * @Create: 2013-5-30 上午10:07:45
	 * @author jcf
	 * @update logs
	 * @param model
	 * @return
	 */
	ServiceResult save(Competition model);
	/**
	 * @Description: 分页查询赛事
	 * @Create: 2013-5-30 上午10:07:54
	 * @author jcf
	 * @update logs
	 * @param model
	 * @param page
	 * @param rows
	 * @return
	 */
	String query(Competition model, Integer page, Integer rows);
	/**
	 * @Description: combobox查询
	 * @Create: 2013-5-30 上午10:08:02
	 * @author jcf
	 * @update logs
	 * @return
	 */
	String queryCombobox(Competition model);
	/**
	 * @Description: 批量删除赛事
	 * @Create: 2013-5-30 上午10:09:58
	 * @author jcf
	 * @update logs
	 * @param ids
	 * @return
	 */
	ServiceResult mulDelete(String ids);
	/**
	 * @Description: 批量修改赛事状态
	 * @Create: 2013-5-30 上午10:10:09
	 * @author jcf
	 * @update logs
	 * @param ids
	 * @param model
	 * @return
	 */
	ServiceResult mulUpdateStatus(String ids,Competition model);
	/**
	 * 
	 * @Description: 复制新建
	 * @Create: 2013-7-15 下午05:11:09
	 * @author jcf
	 * @update logs
	 * @param model
	 * @param copyItems
	 * @return
	 */
	ServiceResult saveCopy(Competition model,String copyItems,String copyCompetitionId);
}
