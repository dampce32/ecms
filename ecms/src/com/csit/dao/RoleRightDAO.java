package com.csit.dao;

import java.util.List;

import com.csit.model.RoleRight;
import com.csit.model.RoleRightId;
/**
 * @Description:角色权限DAO
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-4-17
 * @Author lys
 */
public interface RoleRightDAO extends BaseDAO<RoleRight,RoleRightId>{
	/**
	 * @Description: 更新角色权限状态
	 * @Created Time: 2013-4-17 下午2:04:12
	 * @Author lys
	 * @param roleId
	 * @param rightId
	 * @param b
	 */
	void updateState(Integer roleId, String rightId, boolean state);
	/**
	 * @Description: 取得跟角色权限
	 * @Created Time: 2013-4-17 下午2:27:05
	 * @Author lys
	 * @param model
	 * @return
	 */
	List<RoleRight> getRoot(RoleRight model);
	/**
	 * @Description: 查询树某节点下的子节点
	 * @Created Time: 2013-4-17 下午2:30:01
	 * @Author lys
	 * @param roleRight
	 * @return
	 */
	List<RoleRight> queryChildren(RoleRight roleRight);
	/**
	 * @Description: 统计和角色权限model相同父亲的节点下状态时state的孩子个数
	 * @Created Time: 2013-4-17 下午2:35:37
	 * @Author lys
	 * @param model
	 * @param state
	 * @return
	 */
	Integer countChildrenStateSameParent(RoleRight model, boolean state);

}
