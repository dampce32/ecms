package com.csit.service;

import java.sql.Timestamp;

import com.csit.model.TempArrangeStudent;
import com.csit.model.TempArrangeStudentId;
import com.csit.vo.ServiceResult;

/**
 * @Description:考务安排学生临时表Service
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-5-14
 * @author jcf
 * @vesion 1.0
 */
public interface TempArrangeStudentService extends BaseService<TempArrangeStudent, TempArrangeStudentId>{
	/**
	 * @Description: 考务安排学生临时表
	 * @Create: 2013-5-14 下午04:43:58
	 * @author jcf
	 * @update logs
	 * @param teacherId
	 * @param operateTime
	 * @param model
	 * @return
	 */
	ServiceResult save(Integer teacherId, Timestamp operateTime,Integer arrangeId,String ids);
	/**
	 * @Description: 删除考务安排学生临时表
	 * @Create: 2013-5-14 下午04:49:20
	 * @author jcf
	 * @update logs
	 * @param teacherId
	 * @param operateTime
	 * @param model
	 * @return
	 */
	ServiceResult mulDelete(String ids,Integer arrangeId,Integer teacherId, Timestamp operateTime);
	/**
	 * 
	 * @Description: 查询考务安排学生临时表
	 * @Create: 2013-5-15 上午09:10:37
	 * @author jcf
	 * @update logs
	 * @param model
	 * @param page
	 * @param rows
	 * @return
	 */
	String query(Integer arrangeId,Integer teacherId,Timestamp operateTime, Integer page, Integer rows);

}
