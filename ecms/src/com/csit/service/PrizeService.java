package com.csit.service;

import com.csit.model.Prize;
import com.csit.vo.ServiceResult;

/**
 * @Description: 奖项Service
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-5-31
 * @author wxy
 * @vesion 1.0
 */
public interface PrizeService extends BaseService<Prize, Integer> {
	
	/**
	 * @Description: 保存奖项
	 * @Create: 2013-5-31 上午9:10:49
	 * @author wxy
	 * @update logs
	 * @param model
	 * @return
	 */
	ServiceResult save(Prize model);
	/**
	 * 
	 * @Description: 分页查询奖项
	 * @Create: 2013-5-31 上午9:10:49
	 * @author wxy
	 * @update logs
	 * @param model
	 * @param page
	 * @param rows
	 * @return
	 */
	String query(Prize model, Integer page, Integer rows);
	/**
	 * 
	 * @Description: 批量删除奖项 
	 * @Create: 2013-5-31 上午9:10:49
	 * @author wxy
	 * @update logs
	 * @param ids
	 * @return
	 */
	ServiceResult mulDelete(String ids);
	/**
	 * 
	 * @Description: 批量修改奖项状态 
	 * @Create: 2013-5-31 上午9:10:49
	 * @author wxy
	 * @update logs
	 * @param ids
	 * @param model
	 * @return
	 */
	ServiceResult mulUpdateState(String ids, Prize model);
	/**
	 * 
	 * @Description: 更新奖项顺序 
	 * @Create: 2013-5-31 上午9:10:49
	 * @author wxy
	 * @update logs
	 * @param prizeId
	 * @param updateRightId
	 * @return
	 */
	ServiceResult updateArray(Integer prizeId, Integer updatePrizeId);
	/**
	 * 
	 * @Description: combobox查询奖项 
	 * @Create: 2013-5-31 上午9:10:49
	 * @author wxy
	 * @update logs
	 * @return
	 */
	String queryCombobox();
	String query(Integer competitionGroupId, Integer page, Integer rows);
}
