package com.csit.service;

import java.sql.Timestamp;

import com.csit.model.TempPaperBigSmall;
import com.csit.model.TempPaperBigSmallId;
import com.csit.vo.ServiceResult;
/**
 * @Description:试卷小题临时表Service
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-4-30
 * @Author lys
 */
public interface TempPaperBigSmallService extends BaseService<TempPaperBigSmall,TempPaperBigSmallId>{
	/**
	 * @Description: 保存试卷临时小题(统一试题打乱生成)
	 * @Created Time: 2013-4-30 下午11:00:21
	 * @Author lys
	 * @param teacherId
	 * @param operateTime
	 * @param model
	 * @return
	 */
	ServiceResult saveMix(Integer teacherId, Timestamp operateTime,
			TempPaperBigSmall model);
	/**
	 * @Description: 批量更新小题信息
	 * @Created Time: 2013-5-1 下午4:41:20
	 * @Author lys
	 * @param paperId
	 * @param bigId
	 * @param smallIds
	 * @param isOptionMixs
	 * @param scores
	 * @param difficultys
	 * @param nextBigId 
	 * @param operateTime 
	 * @param teacherId 
	 * @return
	 */
	ServiceResult mulUpdate(String paperId, String bigId, String smallIds,
			String isOptionMixs, String scores, String difficultys, String nextBigId, Integer teacherId, Timestamp operateTime);
	/**
	 * @Description: 查看大题下的小题
	 * @Created Time: 2013-5-1 下午5:32:49
	 * @Author lys
	 * @param model
	 * @param teacherId
	 * @param operateTime
	 * @return
	 */
	ServiceResult view(TempPaperBigSmall model, Integer teacherId,
			Timestamp operateTime);
	/**
	 * @Description: 删除大题下的小题
	 * @Created Time: 2013-5-4 下午4:11:36
	 * @Author lys
	 * @param model
	 * @param teacherId
	 * @param operateTime
	 * @return
	 */
	ServiceResult delete(TempPaperBigSmall model, Integer teacherId,
			Timestamp operateTime);
	/**
	 * @Description: 更新小题
	 * @Created Time: 2013-5-5 上午9:19:34
	 * @Author lys
	 * @param model
	 * @param teacherId
	 * @param operateTime
	 * @return
	 */
	ServiceResult update(TempPaperBigSmall model, Integer teacherId,
			Timestamp operateTime);
	/**
	 * @Description: 移动排序
	 * @Created Time: 2013-5-5 下午3:18:15
	 * @Author lys
	 * @param paperId
	 * @param bigId
	 * @param smallIdFrom
	 * @param arrayFrom
	 * @param smallIdTo
	 * @param arrayTo
	 * @param operateTime 
	 * @param teacherId 
	 * @return
	 */
	ServiceResult moveArray(String paperId, String bigId, String smallIdFrom,
			String arrayFrom, String smallIdTo, String arrayTo, Integer teacherId, Timestamp operateTime);
	/**
	 * @Description: 批量添加小题
	 * @Created Time: 2013-5-6 下午8:48:09
	 * @Author lys
	 * @param paperId
	 * @param bigId
	 * @param countSmall
	 * @param scoreSmall
	 * @param teacherId
	 * @param operateTime
	 * @param array 
	 * @param groupId 
	 * @return
	 */
	ServiceResult mulAdd(String paperId, String bigId, String countSmall,
			String scoreSmall, Integer teacherId, Timestamp operateTime, String array, String groupId);
	/**
	 * @Description: 保存多个试卷临时小题(统一试题打乱生成)
	 * @Created: 2013-7-13 下午6:45:59
	 * @author lys
	 * @update logs
	 * @throws Exception
	 * @param teacherId
	 * @param operateTime
	 * @param model
	 * @param ids
	 * @return
	 */
	ServiceResult saveMulMix(Integer teacherId, Timestamp operateTime,
			TempPaperBigSmall model, String ids);

}
