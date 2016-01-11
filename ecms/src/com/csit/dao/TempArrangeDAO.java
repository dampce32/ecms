package com.csit.dao;

import java.sql.Timestamp;

import com.csit.model.TempArrange;
import com.csit.model.TempArrangeId;


/**
 * @Description:考务安排临时表DAO
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-5-14
 * @author jcf
 * @vesion 1.0
 */
public interface TempArrangeDAO extends BaseDAO<TempArrange,TempArrangeId>{

	Integer copy(Integer arrangeId,Integer teacherId,Timestamp operateTime);
}
