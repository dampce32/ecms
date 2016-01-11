package com.csit.service;

import java.util.List;
import java.util.Map;

import com.csit.model.ExamAnswer;
import com.csit.model.ExamAnswerId;
import com.csit.vo.ServiceResult;
/**
 * @Description:答卷Service
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-8
 * @Author lys
 */
public interface ExamAnswerService extends BaseService<ExamAnswer, ExamAnswerId>{
	/**
	 * @Description: 浏览试卷
	 * @Created Time: 2013-6-8 下午8:04:32
	 * @Author lys
	 * @param competitionId
	 * @param studentId 
	 * @return
	 */
	List<Map<String, Object>> viewPaper(String competitionId, Integer studentId);
	/**
	 * @Description: 考试前准备
	 * @Created Time: 2013-6-9 上午8:54:53
	 * @Author lys
	 * @param groupId
	 * @param paperId
	 * @return
	 */
	Map<String, Object> readyExam(String groupId, String paperId);
	/**
	 * @Description: 开始考试
	 * @Created Time: 2013-6-9 上午9:27:57
	 * @Author lys
	 * @param competitionId
	 * @param groupId
	 * @param studentId
	 * @param paperId
	 * @param studentCompetitionGroupId
	 * @return
	 */
	Map<String, Object> exam(String competitionId, String groupId,
			Integer studentId, String paperId, String studentCompetitionGroupId);
	/**
	 * @Description: 初始化答卷
	 * @Created Time: 2013-6-9 下午2:33:33
	 * @Author lys
	 * @param model
	 * @param studentId
	 * @return
	 */
	ServiceResult initExamPaper(ExamAnswer model, Integer studentId);
	/**
	 * @Description: 取得答卷小题的答题状态
	 * @Created Time: 2013-6-9 下午4:33:38
	 * @Author lys
	 * @param competitionId
	 * @param groupId
	 * @param studentId
	 * @param bigId
	 * @param smallId
	 * @return
	 */
	ServiceResult getSmallStatus(String competitionId, String groupId,
			Integer studentId, String bigId, String smallId);
	/**
	 * @Description: 初始化答卷小题
	 * @Created Time: 2013-6-9 下午5:17:08
	 * @Author lys
	 * @param competitionId
	 * @param groupId
	 * @param studentId
	 * @param bigId
	 * @param smallId
	 * @param smallNo
	 * @return
	 */
	ServiceResult initSmall(String competitionId, String groupId,
			Integer studentId, String bigId, String smallId, String smallNo);
	/**
	 * @Description: 回答阅读理解
	 * @Created Time: 2013-6-9 下午9:53:10
	 * @Author lys
	 * @param competitionId
	 * @param groupId
	 * @param studentId
	 * @param bigId
	 * @param smallId
	 * @param subId
	 * @param answer
	 * @param smallStatus
	 * @return
	 */
	ServiceResult answerRead(String competitionId, String groupId,
			Integer studentId, String bigId, String smallId, String subId,
			String answer, String smallStatus);
	/**
	 * @Description: 回答填空题
	 * @Created Time: 2013-6-9 下午10:21:30
	 * @Author lys
	 * @param competitionId
	 * @param groupId
	 * @param studentId
	 * @param bigId
	 * @param smallId
	 * @param option
	 * @param answer
	 * @param smallStatus
	 * @return
	 */
	ServiceResult answerFill(String competitionId, String groupId,
			Integer studentId, String bigId, String smallId, String option,
			String answer, String smallStatus);
	/**
	 * @Description: 回答单项选择题
	 * @Created Time: 2013-6-9 下午10:31:08
	 * @Author lys
	 * @param competitionId
	 * @param groupId
	 * @param studentId
	 * @param bigId
	 * @param smallId
	 * @param answer
	 * @return
	 */
	ServiceResult answerUniterm(String competitionId, String groupId,
			Integer studentId, String bigId, String smallId, String answer);
	/**
	 * @Description: 结束答题
	 * @Created Time: 2013-6-10 下午4:28:13
	 * @Author lys
	 * @param competitionId
	 * @param groupId
	 * @param studentId
	 * @return
	 */
	ServiceResult finishExamPaper(String competitionId, String groupId,
			Integer studentId);

	
	Map<String, Object> init(Integer studentId);
}
