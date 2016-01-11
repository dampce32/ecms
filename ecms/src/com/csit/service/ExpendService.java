package com.csit.service;

import com.csit.model.Expend;
import com.csit.vo.ServiceResult;
/**
 * @Description:支出Service
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-9
 * @author jcf
 * @vesion 1.0
 */
public interface ExpendService extends BaseService<Expend, Integer> {

	/**
	 * @Description: 保存支出
	 * @Create: 2013-6-9 上午09:04:49
	 * @author jcf
	 * @update logs
	 * @param model
	 * @return
	 */
	ServiceResult save(Expend model);
	/**
	 * @Description: 分页查询支出
	 * @Create: 2013-6-9 上午09:04:57
	 * @author jcf
	 * @update logs
	 * @param model
	 * @param page
	 * @param rows
	 * @return
	 */
	String query(Expend model, Integer page, Integer rows);
	/**
	 * @Description: 批量删除支出
	 * @Create: 2013-6-9 上午09:05:39
	 * @author jcf
	 * @update logs
	 * @param ids
	 * @return
	 */
	ServiceResult mulDelete(String ids);
	/**
	 * @Description: 批量修改支出状态
	 * @Create: 2013-6-9 上午09:06:00
	 * @author jcf
	 * @update logs
	 * @param ids
	 * @param model
	 * @return
	 */
	ServiceResult mulUpdateStatus(String ids,Expend model);
}
