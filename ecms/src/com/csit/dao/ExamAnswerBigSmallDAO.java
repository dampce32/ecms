package com.csit.dao;

import java.util.List;

import com.csit.model.ExamAnswerBig;
import com.csit.model.ExamAnswerBigSmall;
import com.csit.model.ExamAnswerBigSmallId;
/**
 * @Description: 答卷小题DAO
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-9
 * @Author lys
 */
public interface ExamAnswerBigSmallDAO extends BaseDAO<ExamAnswerBigSmall,ExamAnswerBigSmallId>{
	/**
	 * @Description: 查询大题下的小题
	 * @Created Time: 2013-6-9 下午4:01:51
	 * @Author lys
	 * @param examAnswerBig
	 * @return
	 */
	List<ExamAnswerBigSmall> query(ExamAnswerBig examAnswerBig);
}
