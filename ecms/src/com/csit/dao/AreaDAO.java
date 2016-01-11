package com.csit.dao;

import java.util.List;

import com.csit.model.Area;
import com.csit.model.City;
/**
 * @Description:区县DAO
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-5-29
 * @Author lys
 */
public interface AreaDAO extends BaseDAO<Area,Integer>{
	/**
	 * @Description: 取得区县的最大顺序值 
	 * @Create: 2013-5-31 下午2:55:25
	 * @author wxy
	 * @update logs
	 * @return
	 * @param
	 */
	Integer getMaxArray();
	/**
	 * 
	 * @Description: 更新区县顺序
	 * @Create: 2013-5-31 下午2:55:25
	 * @author wxy
	 * @update logs
	 * @param provinceId
	 * @param updateProvinceId
	 */
	void updateArray(Integer areaId, Integer updateAreaId);
	/**
	 * @Description: 分页查询区县
	 * @Created Time: 2013-5-29 下午3:39:17
	 * @Author lys
	 * @param model
	 * @param page
	 * @param rows
	 * @return
	 */
	List<Area> query(Area model, Integer page, Integer rows);
	/**
	 * @Description: 统计区县
	 * @Created Time: 2013-5-29 下午3:39:39
	 * @Author lys
	 * @param model
	 * @return
	 */
	Long getTotalCount(Area model);
	/**
	 * @Description: 查询城市下的区县
	 * @Created Time: 2013-5-29 下午4:14:33
	 * @Author lys
	 * @param city
	 * @return
	 */
	List<Area> queryCombobox(City city);

}
