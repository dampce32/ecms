package com.csit.dao;

import java.util.List;

import com.csit.model.ExamAnswer;
import com.csit.model.ExamAnswerBig;
import com.csit.model.ExamAnswerBigId;

/**
 * @Description: 答卷大题DAO
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-9
 * @Author lys
 */
public interface ExamAnswerBigDAO extends BaseDAO<ExamAnswerBig,ExamAnswerBigId>{
	/**
	 * @Description:查询答卷下的大题 
	 * @Created Time: 2013-6-9 下午3:57:34
	 * @Author lys
	 * @param model
	 * @return
	 */
	List<ExamAnswerBig> query(ExamAnswer model);

}
