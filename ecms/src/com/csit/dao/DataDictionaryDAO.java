package com.csit.dao;

import java.util.List;

import com.csit.model.DataDictionary;
/**
 * @Description:数据字典DAO
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-4-18
 * @Author lys
 */
public interface DataDictionaryDAO extends BaseDAO<DataDictionary,Integer>{
	/**
	 * @Description: 分页查询数据字典
	 * @Created Time: 2013-4-18 下午5:06:36
	 * @Author lys
	 * @param page
	 * @param rows
	 * @param model
	 * @return
	 */
	List<DataDictionary> query(Integer page, Integer rows, DataDictionary model);
	/**
	 * @Description: 统计数据字典
	 * @Created Time: 2013-4-18 下午5:07:00
	 * @Author lys
	 * @param model
	 * @return
	 */
	Long count(DataDictionary model);
	/**
	 * @Description: 取得相同数据类型的最大顺序值
	 * @Created Time: 2013-4-18 下午5:26:48
	 * @Author lys
	 * @param dataDictionaryType
	 * @return
	 */
	Integer getMaxArray(String dataDictionaryType);
	/**
	 * @Description: combobox查询
	 * @Created Time: 2013-4-19 下午10:19:00
	 * @Author lys
	 * @param model
	 * @return
	 */
	List<DataDictionary> queryCombobox(DataDictionary model);

}
