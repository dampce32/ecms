package com.csit.service;

import com.csit.model.PayType;
import com.csit.vo.ServiceResult;
/**
 * @Description:缴费类型Service
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-9
 * @author jcf
 * @vesion 1.0
 */
public interface PayTypeService extends BaseService<PayType, Integer> {

	/**
	 * @Description: 保存缴费类型
	 * @Create: 2013-6-9 上午09:04:49
	 * @author jcf
	 * @update logs
	 * @param model
	 * @return
	 */
	ServiceResult save(PayType model);
	/**
	 * @Description: 分页查询缴费类型
	 * @Create: 2013-6-9 上午09:04:57
	 * @author jcf
	 * @update logs
	 * @param model
	 * @param page
	 * @param rows
	 * @return
	 */
	String query(PayType model, Integer page, Integer rows);
	/**
	 * @Description: combobox查询
	 * @Create: 2013-6-9 上午09:05:30
	 * @author jcf
	 * @update logs
	 * @param model
	 * @return
	 */
	String queryCombobox(PayType model);
	/**
	 * @Description: 批量删除缴费类型
	 * @Create: 2013-6-9 上午09:05:39
	 * @author jcf
	 * @update logs
	 * @param ids
	 * @return
	 */
	ServiceResult mulDelete(String ids);
	/**
	 * @Description: 批量修改缴费类型状态
	 * @Create: 2013-6-9 上午09:06:00
	 * @author jcf
	 * @update logs
	 * @param ids
	 * @param model
	 * @return
	 */
	ServiceResult mulUpdateStatus(String ids,PayType model);
	/**
	 * @Description: 
	 * @Create: 2013-6-9 上午09:06:13
	 * @author jcf
	 * @update logs
	 * @param payTypeId
	 * @param updatePayTypeId
	 * @return
	 */
	ServiceResult updateArray(Integer payTypeId, Integer updatePayTypeId);
}
