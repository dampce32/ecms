package com.csit.service;

import com.csit.model.City;
import com.csit.model.Province;
import com.csit.vo.ServiceResult;
/**
 * @Description:城市Service
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-5-29
 * @Author lys
 */
public interface CityService extends BaseService<City, Integer>{
	/**
	 * @Description: 保存城市
	 * @Create: 2013-5-31 上午9:10:49
	 * @author wxy
	 * @update logs
	 * @param model
	 * @return
	 */
	ServiceResult save(City model);
	/**
	 * 
	 * @Description: 批量删除城市 
	 * @Create: 2013-5-31 上午9:10:49
	 * @author wxy
	 * @update logs
	 * @param ids
	 * @return
	 */
	ServiceResult mulDelete(String ids);
	/**
	 * 
	 * @Description: 更新城市顺序 
	 * @Create: 2013-5-31 上午9:10:49
	 * @author wxy
	 * @update logs
	 * @param prizeId
	 * @param updateRightId
	 * @return
	 */
	ServiceResult updateArray(Integer cityId, Integer updateCityId);
	/**
	 * @Description: 分页查询城市
	 * @Created Time: 2013-5-29 下午2:15:19
	 * @Author lys
	 * @param model
	 * @param page
	 * @param rows
	 * @return
	 */
	String query(City model, Integer page, Integer rows);
	/**
	 * @Description: combobox查询
	 * @Created Time: 2013-5-29 下午4:02:01
	 * @Author lys
	 * @param province
	 * @return
	 */
	String queryCombobox(Province province);
	
}
