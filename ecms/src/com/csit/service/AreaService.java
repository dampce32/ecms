package com.csit.service;

import com.csit.model.Area;
import com.csit.model.City;
import com.csit.vo.ServiceResult;
/**
 * @Description:区县Service
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-5-29
 * @Author lys
 */
public interface AreaService extends BaseService<Area, Integer>{
	/**
	 * @Description: 保存区县
	 * @Create: 2013-5-31 上午9:10:49
	 * @author wxy
	 * @update logs
	 * @param model
	 * @return
	 */
	ServiceResult save(Area model);
	/**
	 * 
	 * @Description: 批量删除区县 
	 * @Create: 2013-5-31 上午9:10:49
	 * @author wxy
	 * @update logs
	 * @param ids
	 * @return
	 */
	ServiceResult mulDelete(String ids);
	/**
	 * 
	 * @Description: 更新区县顺序 
	 * @Create: 2013-5-31 上午9:10:49
	 * @author wxy
	 * @update logs
	 * @param prizeId
	 * @param updateRightId
	 * @return
	 */
	ServiceResult updateArray(Integer areaId, Integer updateAreaId);
	/**
	 * @Description: 分页查询区县
	 * @Created Time: 2013-5-29 下午3:36:59
	 * @Author lys
	 * @param model
	 * @param page
	 * @param rows
	 * @return
	 */
	String query(Area model, Integer page, Integer rows);
	/**
	 * @Description: combobox查询
	 * @Created Time: 2013-5-29 下午4:13:22
	 * @Author lys
	 * @param city
	 * @return
	 */
	String queryCombobox(City city);

}