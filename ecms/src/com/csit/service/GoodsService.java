package com.csit.service;

import com.csit.model.Goods;
import com.csit.vo.ServiceResult;
/**
 * @Description:教材Service
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-6
 * @author jcf
 * @vesion 1.0
 */
public interface GoodsService extends BaseService<Goods, Integer> {

	/**
	 * @Description: 保存教材
	 * @Create: 2013-6-6 下午02:50:13
	 * @author jcf
	 * @update logs
	 * @param model
	 * @return
	 */
	ServiceResult save(Goods model);
	/**
	 * @Description: 分页查询教材
	 * @Create: 2013-6-6 下午02:50:32
	 * @author jcf
	 * @update logs
	 * @param model
	 * @param page
	 * @param rows
	 * @return
	 */
	String query(Goods model, Integer page, Integer rows);
	/**
	 * @Description: combobox查询
	 * @Create: 2013-6-6 下午02:50:41
	 * @author jcf
	 * @update logs
	 * @param model
	 * @return
	 */
	String queryCombobox(Goods model);
	/**
	 * @Description: 批量删除教材
	 * @Create: 2013-6-6 下午02:50:50
	 * @author jcf
	 * @update logs
	 * @param ids
	 * @return
	 */
	ServiceResult mulDelete(String ids);
}
