package com.csit.dao;

import java.sql.Timestamp;
import java.util.List;

import com.csit.model.TempArrangeStudent;
import com.csit.model.TempArrangeStudentId;


/**
 * @Description:考务安排学生临时表DAO
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-5-14
 * @author jcf
 * @vesion 1.0
 */
public interface TempArrangeStudentDAO extends BaseDAO<TempArrangeStudent,TempArrangeStudentId>{

	List<TempArrangeStudent> query(Integer arrangeId,Integer teacherId,Timestamp operateTime, Integer page, Integer rows);
	Long getTotalCount(Integer arrangeId,Integer teacherId,Timestamp operateTime);
}
