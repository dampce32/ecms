package com.csit.dao;

import java.util.List;

import com.csit.model.Province;
/**
 * @Description:省份DAO
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-5-29
 * @Author lys
 */
public interface ProvinceDAO extends BaseDAO<Province,Integer>{
	/**
	 * @Description: 分页查询省份
	 * @Created Time: 2013-5-29 上午11:13:46
	 * @Author lys
	 * @param model
	 * @param page
	 * @param rows
	 * @return
	 */
	List<Province> query(Province model, Integer page, Integer rows);
	/**
	 * @Description: 统计省份
	 * @Created Time: 2013-5-29 上午11:14:17
	 * @Author lys
	 * @param model
	 * @return
	 */
	Long getTotalCount(Province model);
	/**
	 * @Description: combobox查询
	 * @Created Time: 2013-5-29 下午2:59:47
	 * @Author lys
	 * @return
	 */
	List<Province> queryCombobox();
	
	/**
	 * @Description: 取得省份的最大顺序值 
	 * @Create: 2013-5-31 下午2:55:25
	 * @author wxy
	 * @update logs
	 * @return
	 * @param
	 */
	Integer getMaxArray();
	/**
	 * 
	 * @Description: 更新省份顺序
	 * @Create: 2013-5-31 下午2:55:25
	 * @author wxy
	 * @update logs
	 * @param provinceId
	 * @param updateProvinceId
	 */
	void updateArray(Integer provinceId, Integer updateProvinceId);
}
