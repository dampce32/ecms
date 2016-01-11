package com.csit.service;

import java.util.List;

import com.csit.model.Right;
import com.csit.vo.ServiceResult;
/**
 * @Description:权限Service
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-4-16
 * @Author lys
 */
public interface RightService extends BaseService<Right,String>{
	/**
	 * @Description: 取得跟权限
	 * @Created Time: 2013-4-16 下午3:04:22
	 * @Author lys
	 * @return
	 */
	String getRoot();
	/**
	 * @Description: 单击选择展开树节点
	 * @Created Time: 2013-4-16 下午3:04:38
	 * @Author lys
	 * @param model
	 * @return
	 */
	List<Right> getTreeNode(Right model);
	/**
	 * @Description: 取得树节点下的孩子节点
	 * @Created Time: 2013-4-16 下午3:11:08
	 * @Author lys
	 * @param page
	 * @param rows
	 * @param model
	 * @return
	 */
	String getTreeNodeChildren(Integer page, Integer rows, Right model);
	/**
	 * @Description: 保存权限
	 * @Created Time: 2013-4-16 下午4:12:49
	 * @Author lys
	 * @param model
	 * @param teacherId 
	 * @return
	 */
	ServiceResult save(Right model, Integer teacherId);
	/**
	 * @Description: 批量删除权限
	 * @Created Time: 2013-4-16 下午5:09:05
	 * @Author lys
	 * @param ids
	 * @return
	 */
	ServiceResult mulDelete(String ids);
	/**
	 * @Description: 更新排序
	 * @Created Time: 2013-4-16 下午5:20:34
	 * @Author lys
	 * @param rightId
	 * @param updateRightId
	 * @return
	 */
	ServiceResult updateArray(String rightId, String updateRightId);
	/**
	 * @Description: 批量修改权限状态
	 * @Created Time: 2013-4-16 下午11:14:04
	 * @Author lys
	 * @param ids
	 * @param model
	 * @return
	 */
	ServiceResult mulUpdateState(String ids, Right model);

}
