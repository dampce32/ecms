package com.csit.service;

import com.csit.model.RoleRight;
import com.csit.model.RoleRightId;
import com.csit.vo.ServiceResult;
/**
 * @Description:角色权限Service
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-4-17
 * @Author lys
 */
public interface RoleRightService extends BaseService<RoleRight,RoleRightId>{
	/**
	 * @Description: 查询角色第一层权限
	 * @Created Time: 2013-4-17 下午2:24:15
	 * @Author lys
	 * @param model
	 * @return
	 */
	String getRoot(RoleRight model);
	/**
	 * @Description: 查询树某节点下的子节点
	 * @Created Time: 2013-4-17 下午2:24:58
	 * @Author lys
	 * @param model
	 * @return
	 */
	String getChildren(RoleRight model);
	/**
	 * @Description: 更新角色权限状态
	 * @Created Time: 2013-4-17 下午2:25:21
	 * @Author lys
	 * @param model
	 * @return
	 */
	ServiceResult updateState(RoleRight model);
	
	

}
