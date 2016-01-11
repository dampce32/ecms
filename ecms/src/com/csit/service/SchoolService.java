package com.csit.service;

import com.csit.model.School;
import com.csit.vo.ServiceResult;
/**
 * @Description:学校Service
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-7-22
 * @author jcf
 * @vesion 1.0
 */
public interface SchoolService extends BaseService<School, Integer> {

	/**
	 * @Description: 保存学校
	 * @Create: 2013-7-22 下午03:13:10
	 * @author jcf
	 * @update logs
	 * @param model
	 * @return
	 */
	ServiceResult save(School model);
	/**
	 * @Description: 分页查询学校
	 * @Create: 2013-7-22 下午03:13:17
	 * @author jcf
	 * @update logs
	 * @param model
	 * @param page
	 * @param rows
	 * @return
	 */
	String query(School model, Integer page, Integer rows);
	/**
	 * @Description: 批量删除学校
	 * @Create: 2013-7-22 下午03:13:27
	 * @author jcf
	 * @update logs
	 * @param ids
	 * @return
	 */
	ServiceResult mulDelete(String ids);
	/**
	 * 
	 * @Description: 批量修改学校状态
	 * @Create: 2013-7-22 下午04:30:30
	 * @author jcf
	 * @update logs
	 * @param ids
	 * @param model
	 * @return
	 */
	ServiceResult mulUpdateStatus(String ids,School model);
	/**
	 * 
	 * @Description: combobx查询
	 * @Create: 2013-7-23 下午02:21:27
	 * @author jcf
	 * @update logs
	 * @param model
	 * @return
	 */
	String queryCombobox(School model);
}
