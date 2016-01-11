package com.csit.dao;

import java.sql.Timestamp;
import java.util.List;

import com.csit.model.TempPaperBig;
import com.csit.model.TempPaperBigId;
/**
 * @Description:临时试卷大题DAO
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-4-28
 * @Author lys
 */
public interface TempPaperBigDAO extends BaseDAO<TempPaperBig,TempPaperBigId>{
	/**
	 * @Description: 临时试卷下的大题
	 * @Created Time: 2013-5-4 下午11:24:08
	 * @Author lys
	 * @param paperId
	 * @param teacherId
	 * @param operateTime
	 * @return
	 */
	List<TempPaperBig> query(Integer paperId, Integer teacherId,
			Timestamp operateTime);
	/**
	 * @Description: 清空大题下的小题
	 * @Created Time: 2013-5-5 下午5:17:42
	 * @Author lys
	 * @param id
	 */
	void clearSmall(TempPaperBig model);

}
