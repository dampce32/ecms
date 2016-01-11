package com.csit.dao;

import java.util.List;

import com.csit.model.City;
import com.csit.model.Province;

/**
 * @Description:城市DAO
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-5-29
 * @Author lys
 */
public interface CityDAO extends BaseDAO<City,Integer>{
	/**
	 * @Description: 取得城市的最大顺序值 
	 * @Create: 2013-5-31 下午2:55:25
	 * @author wxy
	 * @update logs
	 * @return
	 * @param
	 */
	Integer getMaxArray();
	/**
	 * 
	 * @Description: 更新城市顺序
	 * @Create: 2013-5-31 下午2:55:25
	 * @author wxy
	 * @update logs
	 * @param provinceId
	 * @param updateProvinceId
	 */
	void updateArray(Integer cityId, Integer updateCityId);
	/**
	 * @Description: 分页查询城市
	 * @Created Time: 2013-5-29 下午2:17:43
	 * @Author lys
	 * @param model
	 * @param page
	 * @param rows
	 * @return
	 */
	List<City> query(City model, Integer page, Integer rows);
	/**
	 * @Description: 统计城市
	 * @Created Time: 2013-5-29 下午2:18:09
	 * @Author lys
	 * @param model
	 * @return
	 */
	Long getTotalCount(City model);
	/**
	 * @Description: 查询指定省份下的城市名单
	 * @Created Time: 2013-5-29 下午4:03:11
	 * @Author lys
	 * @param province
	 * @return
	 */
	List<City> queryCombobox(Province province);

}
