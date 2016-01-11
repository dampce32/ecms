package com.csit.service;


import com.csit.model.Province;
import com.csit.vo.ServiceResult;
/**
 * @Description:省份Service
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-5-29
 * @Author lys
 */

public interface ProvinceService extends BaseService<Province, Integer>{
	
	/**
	 * @Description:  保存省份
	 * @Create: 2013-5-31 下午2:34:53
	 * @author wxy
	 * @update logs
	 * @param model
	 * @return
	 */
	ServiceResult save(Province model);
	/**
	 * 
	 * @Description: 批量删除省份
	 * @Create: 2013-5-31 下午2:34:53
	 * @author wxy
	 * @update logs
	 * @param ids
	 * @return
	 */
	ServiceResult mulDelete(String ids);
	
	/**
	 * 
	 * @Description: 更新省份顺序 
	 * @Create: 2013-5-31 下午2:34:53
	 * @author wxy
	 * @update logs
	 * @param prizeId
	 * @param updateRightId
	 * @return
	 */
	ServiceResult updateArray(Integer prizeId, Integer updatePrizeId);
	/**
	 * @Description:  分页查询省份
	 * @Created Time: 2013-5-29 上午11:11:38
	 * @Author lys
	 * @param model
	 * @param page
	 * @param rows
	 * @return
	 */
	String query(Province model, Integer page, Integer rows);
	/**
	 * @Description: combobox查询
	 * @Created Time: 2013-5-29 下午2:56:03
	 * @Author lys
	 * @return
	 */
	String queryCombobox();

}
