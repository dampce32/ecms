package com.csit.service;

import java.sql.Timestamp;

import com.csit.model.TempPaperBig;
import com.csit.model.TempPaperBigId;
import com.csit.vo.ServiceResult;

/**
 * @Description:临时试卷大题Service
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-5-4
 * @Author lys
 */
public interface TempPaperBigService extends BaseService<TempPaperBig, TempPaperBigId>{
	/**
	 * @Description: 保存临时试卷大题
	 * @Created Time: 2013-5-4 下午8:57:21
	 * @Author lys
	 * @param teacherId
	 * @param operateTime
	 * @param model
	 * @return
	 */
	ServiceResult save(Integer teacherId, Timestamp operateTime,
			TempPaperBig model);
	/**
	 * @Description: 删除大题
	 * @Created Time: 2013-5-4 下午10:15:42
	 * @Author lys
	 * @param teacherId
	 * @param operateTime
	 * @param model
	 * @return
	 */
	ServiceResult delete(Integer teacherId, Timestamp operateTime,
			TempPaperBig model);
	/**
	 * @Description: 清空大题下的小题
	 * @Created Time: 2013-5-5 下午4:30:27
	 * @Author lys
	 * @param teacherId
	 * @param operateTime
	 * @param model
	 * @return
	 */
	ServiceResult clearSmall(Integer teacherId, Timestamp operateTime,
			TempPaperBig model);

}
