package com.csit.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.csit.dao.ExamAnswerDAO;
import com.csit.model.ExamAnswer;
import com.csit.model.ExamAnswerBigSmall;
import com.csit.model.ExamAnswerId;
import com.csit.vo.StoreProcedureResult;
/**
 * @Description: 答卷DAO实现类
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-8
 * @Author lys
 */
@Repository
public class ExamAnswerDAOImpl extends BaseDAOImpl<ExamAnswer, ExamAnswerId>
		implements ExamAnswerDAO {
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.ExamAnswerDAO#viewPaper(java.lang.Integer, java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> viewPaper(Integer competitionId, Integer studentId) {
		StringBuffer sb = new StringBuffer();
		sb.append("select b.competitionId,b.paperId,b.groupId,d.paperName,e.groupName,a.studentCompetitionGroupId ");
		sb.append("from T_StudentCompetitionGroup a ");
		sb.append("left join T_CompetitionGroup b on a.competitionGroupId = b.competitionGroupId ");
		sb.append("left join T_Exam_Answer c on a.studentCompetitionGroupId = c.studentCompetitionGroupId ");
		sb.append("left join T_Paper d on b.paperId = d.paperId ");
		sb.append("left join T_Group e on b.groupId = e.groupId ");
		sb.append("left join T_Competition f on b.competitionId = f.competitionId ");
		sb.append("where a.status = 1 and b.competitionId = :competitionId and b.paperId is not null and f.beginDate <=GETDATE() and f.endDate >= GETDATE() and a.studentId = :studentId and ((c.status = 0 and c.finishDateTime > getdate()) or c.status is null) ");
		sb.append("order by e.array,b.paperId");
		
		Query query = getCurrentSession().createSQLQuery(sb.toString());
		query.setInteger("competitionId", competitionId);
		query.setInteger("studentId", studentId);
		return query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.ExamAnswerDAO#buildAnswer(java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.Integer)
	 */
	@SuppressWarnings("deprecation")
	@Override
	public StoreProcedureResult buildAnswer(Integer competitionId, Integer groupId,
			Integer studentId, Integer paperId,
			Integer studentCompetitionGroupId) throws SQLException {
		
        Connection conn = getCurrentSession().connection();   
        CallableStatement call;
               call = conn.prepareCall("{?=Call P_BuildAnswer(?,?,?,?,?,?)}");
               call.registerOutParameter(1, java.sql.Types.INTEGER);
               call.setInt(2, competitionId);
	       		call.setInt(3, groupId);
	       		call.setInt(4, studentId);
	       		call.setInt(5, paperId);
	       		call.setInt(6, studentCompetitionGroupId);
	       		call.registerOutParameter(7, java.sql.Types.VARCHAR);
               call.execute();
               StoreProcedureResult  spResult = new StoreProcedureResult();
               spResult.setReturnInt(call.getInt(1));
               spResult.setReturnValue(call.getString(7));
		return spResult; 
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.ExamAnswerDAO#countScore(com.csit.model.ExamAnswer)
	 */
	@Override
	public Double countScore(ExamAnswer examAnswer) {
		Criteria criteria = getCurrentSession().createCriteria(ExamAnswerBigSmall.class);
		criteria.add(Restrictions.eq("id.competitionId", examAnswer.getId().getCompetitionId()));
		criteria.add(Restrictions.eq("id.groupId", examAnswer.getId().getGroupId()));
		criteria.add(Restrictions.eq("id.studentId", examAnswer.getId().getStudentId()));
		criteria.setProjection(Projections.sum("score"));
		Object sumObj = criteria.uniqueResult();
		return sumObj==null?0L:new Double(sumObj.toString());
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<ExamAnswer> init(Integer studentId) {
		Criteria criteria = getCurrentSession().createCriteria(ExamAnswer.class);
		
		criteria.createAlias("studentCompetitionGroup", "studentCompetitionGroup", CriteriaSpecification.LEFT_JOIN);
		
		if(studentId!=null){
			criteria.add(Restrictions.eq("studentCompetitionGroup.student.studentId",studentId));
		}
		
		return criteria.list();
	}

}
