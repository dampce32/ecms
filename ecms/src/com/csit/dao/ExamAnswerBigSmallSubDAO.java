package com.csit.dao;

import java.util.List;

import com.csit.model.ExamAnswerBigSmall;
import com.csit.model.ExamAnswerBigSmallSub;
import com.csit.model.ExamAnswerBigSmallSubId;
/**
 * @Description: 答卷小题子小题
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-9
 * @Author lys
 */
public interface ExamAnswerBigSmallSubDAO extends BaseDAO<ExamAnswerBigSmallSub,ExamAnswerBigSmallSubId>{
	/**
	 * @Description: 查询小题下的子小题
	 * @Created Time: 2013-6-9 下午5:23:40
	 * @Author lys
	 * @param examAnswerBigSmall
	 * @return
	 */
	List<ExamAnswerBigSmallSub> query(ExamAnswerBigSmall examAnswerBigSmall);

}
