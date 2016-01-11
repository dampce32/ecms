package com.csit.dao;

import java.util.List;

import com.csit.model.Right;
/**
 * @Description:权限DAO
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-4-16
 * @Author lys
 */
public interface RightDAO extends BaseDAO<Right,String>{
	/**
	 * @Description: 取得跟权限
	 * @Created Time: 2013-4-16 下午3:07:02
	 * @Author lys
	 * @return
	 */
	List<Right> getRoot();
	/**
	 * @Description: 取得权限下的子权限
	 * @Created Time: 2013-4-16 下午3:07:24
	 * @Author lys
	 * @param right
	 * @return
	 */
	List<Right> getChildren(Right right);
	/**
	 * @Description: 查询权限列表
	 * @Created Time: 2013-4-16 下午3:16:11
	 * @Author lys
	 * @param page
	 * @param rows
	 * @param model
	 * @return
	 */
	List<Right> query(Integer page, Integer rows, Right model);
	/**
	 * @Description: 统计权限列表
	 * @Created Time: 2013-4-16 下午3:16:34
	 * @Author lys
	 * @param model
	 * @return
	 */
	Long count(Right model);
	/**
	 * @Description: 取得权限rightId下的最大顺序值
	 * @Created Time: 2013-4-16 下午4:15:23
	 * @Author lys
	 * @param rightId
	 * @return
	 */
	Integer getMaxArray(String rightId);
	/**
	 * @Description: 更新权限是否叶子状态
	 * @Created Time: 2013-4-16 下午4:17:03
	 * @Author lys
	 * @param rightId
	 * @param b
	 */
	void updateIsLeaf(String rightId, boolean isLeaf);
	/**
	 * @Description: 取得权限rightId下子节点的最大权限Id
	 * @Created Time: 2013-4-16 下午4:21:42
	 * @Author lys
	 * @param parentRight
	 * @return
	 */
	String getChildrenMaxRightId(Right parentRight);
	/**
	 * @Description: 统计权限rightId下的子权限个数
	 * @Created Time: 2013-4-16 下午5:10:29
	 * @Author lys
	 * @param parentID
	 * @return
	 */
	Long countChildren(String rightId);
	/**
	 * @Description: 更新权限顺序
	 * @Created Time: 2013-4-16 下午5:36:31
	 * @Author lys
	 * @param rightId
	 * @param updateRightId
	 */
	void updateArray(String rightId, String updateRightId);
	/**
	 * @Description: 取得父权限
	 * @Created Time: 2013-4-17 下午1:58:06
	 * @Author lys
	 * @param right
	 * @return
	 */
	Right getParentRight(Right right);

}
