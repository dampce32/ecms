package com.csit.service;

import com.csit.model.DataDictionary;
import com.csit.vo.ServiceResult;
/**
 * @Description:数据字典Service
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-4-18
 * @Author lys
 */
public interface DataDictionaryService extends BaseService<DataDictionary,Integer>{
	/**
	 * @Description: 保存数据字典
	 * @Created Time: 2013-4-18 下午4:51:45
	 * @Author lys
	 * @param model
	 * @param integerSesion
	 * @return
	 */
	ServiceResult save(DataDictionary model, Integer teacherId);
	/**
	 * @Description: 分页查询数据字典
	 * @Created Time: 2013-4-18 下午4:52:03
	 * @Author lys
	 * @param page
	 * @param rows
	 * @param model
	 * @return
	 */
	String query(Integer page, Integer rows, DataDictionary model);
	/**
	 * @Description: 批量数据字典删除
	 * @Created Time: 2013-4-18 下午4:52:17
	 * @Author lys
	 * @param ids
	 * @return
	 */
	ServiceResult mulDelete(String ids);
	/**
	 * @Description: 批量修改数据字典状态
	 * @Created Time: 2013-4-18 下午4:52:30
	 * @Author lys
	 * @param ids
	 * @param model
	 * @param integer 
	 * @return
	 */
	ServiceResult mulUpdateState(String ids, DataDictionary model, Integer teacherId);
	/**
	 * @Description: combobox查询
	 * @Created Time: 2013-4-18 下午9:09:29
	 * @Author lys
	 * @param model
	 * @return
	 */
	String queryCombobox(DataDictionary model);
	/**
	 * @Description: 根据类别的Commobox查询
	 * @Created Time: 2013-5-5 下午9:40:49
	 * @Author lys
	 * @param model
	 * @return
	 */
	String queryTypeCombobox(DataDictionary model);

}
