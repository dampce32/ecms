package com.csit.service;

import com.csit.model.Recrej;
import com.csit.vo.ServiceResult;
/**
 * @Description:入库出库Service
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-6
 * @author jcf
 * @vesion 1.0
 */
public interface RecrejService extends BaseService<Recrej, Integer> {

	/**
	 * @Description: 保存入库出库
	 * @Create: 2013-6-6 下午04:29:46
	 * @author jcf
	 * @update logs
	 * @param model
	 * @return
	 */
	ServiceResult save(Recrej model);
	/**
	 * @Description: 分页查询入库出库
	 * @Create: 2013-6-6 下午04:29:55
	 * @author jcf
	 * @update logs
	 * @param model
	 * @param page
	 * @param rows
	 * @return
	 */
	String query(Recrej model, Integer page, Integer rows);
	/**
	 * @Description: 批量删除入库出库
	 * @Create: 2013-6-6 下午04:30:06
	 * @author jcf
	 * @update logs
	 * @param ids
	 * @return
	 */
	ServiceResult mulDelete(String ids);
}
