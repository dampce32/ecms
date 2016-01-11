package com.csit.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.csit.model.ExamAnswer;
import com.csit.model.ExamAnswerId;
import com.csit.vo.StoreProcedureResult;
/**
 * @Description: 答卷DAO
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-8
 * @Author lys
 */
public interface ExamAnswerDAO extends BaseDAO<ExamAnswer,ExamAnswerId>{
	/**
	 * @Description: 浏览试卷
	 * @Created Time: 2013-6-8 下午8:07:03
	 * @Author lys
	 * @param competitionId
	 * @param studentId 
	 * @return
	 */
	List<Map<String, Object>> viewPaper(Integer competitionId, Integer studentId);
	/**
	 * 生成答卷
	 * @throws SQLException 
	 */
	StoreProcedureResult buildAnswer(Integer competitionId, Integer groupId, Integer studentId,
			Integer paperId, Integer studentCompetitionGroupId) throws SQLException;
	/**
	 * @Description: 统计答卷的分数
	 * @Created Time: 2013-6-10 下午4:40:27
	 * @Author lys
	 * @param examAnswer
	 * @return
	 */
	Double countScore(ExamAnswer examAnswer);
	
	List<ExamAnswer> init(Integer studentId);

}
