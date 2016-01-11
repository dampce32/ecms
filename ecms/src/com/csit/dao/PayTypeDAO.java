package com.csit.dao;

import java.util.List;

import com.csit.model.PayType;

/**
 * @Description:缴费类型DAO
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-9
 * @author jcf
 * @vesion 1.0
 */
public interface PayTypeDAO extends BaseDAO<PayType,Integer>{
	/**
	 * @Description: 分页查询缴费类型
	 * @Create: 2013-6-9 上午08:58:56
	 * @author jcf
	 * @update logs
	 * @param model
	 * @param page
	 * @param rows
	 * @return
	 */
	List<PayType> query(PayType model,Integer page, Integer rows);
	/**
	 * @Description: 统计缴费类型
	 * @Create: 2013-6-9 上午08:59:06
	 * @author jcf
	 * @update logs
	 * @param model
	 * @return
	 */
	Long getTotalCount(PayType model);
	/**
	 * @Description: 获得最大顺序值
	 * @Create: 2013-6-9 上午08:59:20
	 * @author jcf
	 * @update logs
	 * @return
	 */
	Integer getMaxArray();
	/**
	 * @Description: 更新权限顺序
	 * @Create: 2013-6-9 上午08:59:35
	 * @author jcf
	 * @update logs
	 * @param payTypeId
	 * @param updatePayTypeId
	 */
	void updateArray(Integer payTypeId, Integer updatePayTypeId);
	/**
	 * @Description: combobox使用
	 * @Create: 2013-6-9 上午08:59:44
	 * @author jcf
	 * @update logs
	 * @param model
	 * @return
	 */
	List<PayType> queryCombobox(PayType model);

}
