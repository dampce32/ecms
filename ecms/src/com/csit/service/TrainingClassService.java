package com.csit.service;


import java.util.List;
import java.util.Map;

import com.csit.model.TrainingClass;
import com.csit.vo.ServiceResult;
/**
 * @Description: 培训班级Service
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-7
 * @author wxy
 * @vesion 1.0
 */
public interface TrainingClassService extends BaseService<TrainingClass, Integer>{
	/**
	 * @Description: 保存培训班级
	 * @Create: 2013-5-31 上午9:10:49
	 * @author wxy
	 * @update logs
	 * @param model
	 * @return
	 */
	ServiceResult save(TrainingClass model);
	/**
	 * 
	 * @Description: 批量删除培训班级
	 * @Create: 2013-5-31 上午9:10:49
	 * @author wxy
	 * @update logs
	 * @param ids
	 * @return
	 */
	ServiceResult mulDelete(String ids);

	/**
	 * @Description: 分页查询培训班级
	 * @Created Time: 2013-5-29 下午3:36:59
	 * @Author lys
	 * @param model
	 * @param page
	 * @param rows
	 * @return
	 */
	String query(TrainingClass model, Integer page, Integer rows);
	/**
	 * @Description: combobox查询
	 * @Created Time: 2013-5-29 下午4:13:22
	 * @Author wxy
	 * @param trainingClass
	 * @return
	 */
	String queryCombobox(TrainingClass trainingClass,Integer competitionId);
	/**
	 * 
	 * @Description: 批量修改奖项状态 
	 * @Create: 2013-5-31 上午9:10:49
	 * @author wxy
	 * @update logs
	 * @param ids
	 * @param model
	 * @return
	 */
	ServiceResult mulUpdateState(String ids, TrainingClass model);
	/**
	 * 
	 * @Description: 获得所以培训班级
	 * @Create: 2013-6-21 上午08:56:29
	 * @author jcf
	 * @update logs
	 * @return
	 */
	List<TrainingClass> loadAll(Integer competitionId);
	/**
	 * 
	 * @Description: 初始化培训班级界面
	 * @Create: 2013-6-22 上午07:50:07
	 * @author jcf
	 * @update logs
	 * @param competitionId
	 * @param page
	 * @param rows
	 * @return
	 */
	Map<String, Object> initTrainingClass(String competitionId, Integer page,Integer rows);

}