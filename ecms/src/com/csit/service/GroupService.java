package com.csit.service;

import com.csit.model.Group;
import com.csit.vo.ServiceResult;
/**
 * @Description:参赛组别Service
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-5-28
 * @author jcf
 * @vesion 1.0
 */
public interface GroupService extends BaseService<Group, Integer> {

	/**
	 * @Description: 保存参赛组别
	 * @Create: 2013-5-28 下午03:39:48
	 * @author jcf
	 * @update logs
	 * @param model
	 * @return
	 */
	ServiceResult save(Group model);
	/**
	 * @Description: 分页查询参赛组别
	 * @Create: 2013-5-28 下午03:40:15
	 * @author jcf
	 * @update logs
	 * @param model
	 * @param page
	 * @param rows
	 * @return
	 */
	String query(Group model, Integer page, Integer rows, String feeItemIds);
	/**
	 * @Description: combobox查询
	 * @Create: 2013-5-28 下午03:40:26
	 * @author jcf
	 * @update logs
	 * @return
	 */
	String queryCombobox(Group model);
	/**
	 * 
	 * @Description: 批量删除参赛组别
	 * @Create: 2013-5-28 下午05:13:46
	 * @author jcf
	 * @update logs
	 * @param ids
	 * @return
	 */
	ServiceResult mulDelete(String ids);
	/**
	 * 
	 * @Description: 批量修改参赛组别状态
	 * @Create: 2013-5-28 下午05:13:46
	 * @author jcf
	 * @update logs
	 * @param ids
	 * @return
	 */
	ServiceResult mulUpdateStatus(String ids,Group model);
	/**
	 * @Description: 更新排序
	 * @Create: 2013-5-29 上午09:24:13
	 * @author jcf
	 * @update logs
	 * @param rightId
	 * @param updateRightId
	 * @return
	 */
	ServiceResult updateArray(Integer groupId, Integer updateGroupId);
}
