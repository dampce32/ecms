package com.csit.dao;

import java.util.List;

import com.csit.model.Group;

/**
 * @Description:参赛组别DAO
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-5-28
 * @author jcf
 * @vesion 1.0
 */
public interface GroupDAO extends BaseDAO<Group,Integer>{
	/**
	 * @Description: 分页查询参赛组别
	 * @Create: 2013-5-28 下午03:23:57
	 * @author jcf
	 * @update logs
	 * @param page
	 * @param rows
	 * @param model
	 * @return
	 */
	List<Group> query(Group model,Integer page, Integer rows, Integer[] groupIdArr);
	/**
	 * @Description: 分页查询参赛组别
	 * @Create: 2013-5-28 下午03:24:10
	 * @author jcf
	 * @update logs
	 * @param model
	 * @return
	 */
	Long getTotalCount(Group model, Integer[] groupIdArr);
	/**
	 * 
	 * @Description: 获得最大顺序值
	 * @Create: 2013-5-28 下午03:42:27
	 * @author jcf
	 * @update logs
	 * @return
	 */
	Integer getMaxArray();
	/**
	 * @Description: 更新权限顺序
	 * @Create: 2013-5-29 上午09:27:01
	 * @author jcf
	 * @update logs
	 * @param rightId
	 * @param updateRightId
	 */
	void updateArray(Integer groupId, Integer updateGroupId);
	/**
	 * 
	 * @Description: combobox使用
	 * @Create: 2013-5-29 下午03:25:09
	 * @author jcf
	 * @update logs
	 * @param model
	 * @return
	 */
	List<Group> queryCombobox(Group model);

}
