package com.csit.dao;

import java.sql.Timestamp;
import java.util.List;

import com.csit.model.TempPaperBigSmall;
import com.csit.model.TempPaperBigSmallId;
/**
 * @Description:试卷小题临时表DAO
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-4-30
 * @Author lys
 */
public interface TempPaperBigSmallDAO extends BaseDAO<TempPaperBigSmall,TempPaperBigSmallId>{
	/**
	 * @Description: 查询大题下的小题
	 * @Created Time: 2013-5-4 下午5:55:34
	 * @Author lys
	 * @param paperId
	 * @param bigId
	 * @param teacherId
	 * @param operateTime
	 * @return
	 */
	List<TempPaperBigSmall> query(Integer paperId, Integer bigId,
			Integer teacherId, Timestamp operateTime);

}
