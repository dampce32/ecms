package com.csit.service;

import java.sql.Timestamp;

import com.csit.model.TempArrange;
import com.csit.model.TempArrangeId;
import com.csit.vo.ServiceResult;

/**
 * @Description:考务安排临时表Service
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-5-14
 * @author jcf
 * @vesion 1.0
 */
public interface TempArrangeService extends BaseService<TempArrange, TempArrangeId>{
	/**
	 * @Description: 考务安排临时表
	 * @Create: 2013-5-14 下午04:43:58
	 * @author jcf
	 * @update logs
	 * @param teacherId
	 * @param operateTime
	 * @param model
	 * @return
	 */
	ServiceResult save(Integer teacherId, Timestamp operateTime,
			TempArrange model);
	/**
	 * @Description: 删除考务安排临时表
	 * @Create: 2013-5-14 下午04:49:20
	 * @author jcf
	 * @update logs
	 * @param teacherId
	 * @param operateTime
	 * @param model
	 * @return
	 */
	ServiceResult delete(Integer teacherId, Timestamp operateTime,
			Integer arrangeId);

}
