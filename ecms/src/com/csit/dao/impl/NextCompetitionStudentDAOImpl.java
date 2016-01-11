package com.csit.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.csit.dao.NextCompetitionStudentDAO;
import com.csit.model.NextCompetitionStudent;
import com.csit.util.PageUtil;
/**
 * @Description:赛事晋级DAO实现类
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-23
 * @Author lys
 */
@Repository
public class NextCompetitionStudentDAOImpl extends
		BaseDAOImpl<NextCompetitionStudent, Integer> implements
		NextCompetitionStudentDAO {
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.NextCompetitionStudentDAO#query(com.csit.model.NextCompetitionStudent, java.lang.Integer, java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<NextCompetitionStudent> query(NextCompetitionStudent model,
			Integer page, Integer rows) {
		Criteria criteria = getCurrentSession().createCriteria(NextCompetitionStudent.class);
		criteria.createAlias("competitionGroup", "competitionGroup",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("competitionGroup.competition", "competition",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("competitionGroup.group", "group",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("student", "student",CriteriaSpecification.LEFT_JOIN);
		//赛事
		if(model!=null&&model.getCompetitionGroup()!=null
				&&model.getCompetitionGroup().getCompetition()!=null&&model.getCompetitionGroup().getCompetition().getCompetitionId()!=null){
			criteria.add(Restrictions.eq("competitionGroup.competition", model.getCompetitionGroup().getCompetition()));
		}
		//组别
		if(model!=null&&model.getCompetitionGroup()!=null
				&&model.getCompetitionGroup().getCompetitionGroupId()!=null){
			criteria.add(Restrictions.eq("competitionGroup", model.getCompetitionGroup()));
		}
		//学生
		if(model!=null&&model.getStudent()!=null&&StringUtils.isNotEmpty(model.getStudent().getStudentName())){
			criteria.add(Restrictions.like("student.studentName", model.getStudent().getStudentName(),MatchMode.ANYWHERE));
		}
		criteria.setFirstResult(PageUtil.getPageBegin(page, rows));
		criteria.setMaxResults(rows);
		criteria.addOrder(Order.asc("competitionGroup.competition"));
		criteria.addOrder(Order.asc("competitionGroup.array"));
		criteria.addOrder(Order.asc("score"));
		return criteria.list();
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.NextCompetitionStudentDAO#getTotalCount(com.csit.model.NextCompetitionStudent)
	 */
	@Override
	public Long getTotalCount(NextCompetitionStudent model) {
		Criteria criteria = getCurrentSession().createCriteria(NextCompetitionStudent.class);
		criteria.createAlias("competitionGroup", "competitionGroup",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("competitionGroup.competition", "competition",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("competitionGroup.group", "group",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("student", "student",CriteriaSpecification.LEFT_JOIN);
		//赛事
		if(model!=null&&model.getCompetitionGroup()!=null
				&&model.getCompetitionGroup().getCompetition()!=null&&model.getCompetitionGroup().getCompetition().getCompetitionId()!=null){
			criteria.add(Restrictions.eq("competitionGroup.competition", model.getCompetitionGroup().getCompetition()));
		}
		//组别
		if(model!=null&&model.getCompetitionGroup()!=null
				&&model.getCompetitionGroup().getCompetitionGroupId()!=null){
			criteria.add(Restrictions.eq("competitionGroup", model.getCompetitionGroup()));
		}
		//学生
		if(model!=null&&model.getStudent()!=null&&StringUtils.isNotEmpty(model.getStudent().getStudentName())){
			criteria.add(Restrictions.like("student.studentName", model.getStudent().getStudentName(),MatchMode.ANYWHERE));
		}
		criteria.setProjection(Projections.rowCount());
		Object obj = criteria.uniqueResult();
		return new Long(obj.toString());
	}
	/*
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> selectQuery(NextCompetitionStudent model,
			Integer page, Integer rows) {
		StringBuffer sql = new StringBuffer();
		sql.append("select a.studentId,c.competitionName,d.groupName,e.studentName,g.score ");
		sql.append("from T_StudentCompetitionGroup a ");
		sql.append("left join T_CompetitionGroup b on a.competitionGroupId = b.competitionGroupId ");
		sql.append("left join T_Competition c on b.competitionId = c.competitionId ");
		sql.append("left join T_Group d on b.groupId = d.groupId ");
		sql.append("left join T_Student e on a.studentId = e.studentId ");
		sql.append("left join(select a.studentId ");
		sql.append("	from T_NextCompetitionStudent a ");
		sql.append("	where a.competitionGroupId = :competitionGroupId) f on a.studentId = f.studentId ");
		sql.append("left join T_Exam_Answer g on a.studentCompetitionGroupId = g.studentCompetitionGroupId ");
		sql.append("where a.status = 1 and a.competitionGroupId = :competitionGroupId and f.studentId is null ");
		if(model!=null&&model.getStudent()!=null&&StringUtils.isNotEmpty(model.getStudent().getStudentName())){
			sql.append("	and e.studentName like :studentName ");
		}
		sql.append("order by g.score desc ");
		
		Query query = getCurrentSession().createSQLQuery(sql.toString());
		query.setInteger("competitionGroupId", model.getCompetitionGroup().getCompetitionGroupId());
		if(model!=null&&model.getStudent()!=null&&StringUtils.isNotEmpty(model.getStudent().getStudentName())){
			query.setString("studentName", "%"+model.getStudent().getStudentName()+"%");
		}
		query.setFirstResult(PageUtil.getPageBegin(page, rows));
		query.setMaxResults(rows);
		return query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.NextCompetitionStudentDAO#getTotalCountSelectQuery(com.csit.model.NextCompetitionStudent)
	 */
	@Override
	public Long getTotalCountSelectQuery(NextCompetitionStudent model) {
		StringBuffer sql = new StringBuffer();
		sql.append("select count(a.studentId) ");
		sql.append("from T_StudentCompetitionGroup a ");
		sql.append("left join(select a.studentId ");
		sql.append("	from T_NextCompetitionStudent a ");
		sql.append("	where a.competitionGroupId = :competitionGroupId) b on a.studentId = b.studentId ");
		sql.append("left join T_Student c on a.studentId = c.studentId ");
		sql.append("where a.status = 1 and a.competitionGroupId = :competitionGroupId and b.studentId is null ");
		if(model!=null&&model.getStudent()!=null&&StringUtils.isNotEmpty(model.getStudent().getStudentName())){
			sql.append("	and c.studentName like :studentName ");
		}
		
		Query query = getCurrentSession().createSQLQuery(sql.toString());
		query.setInteger("competitionGroupId", model.getCompetitionGroup().getCompetitionGroupId());
		if(model!=null&&model.getStudent()!=null&&StringUtils.isNotEmpty(model.getStudent().getStudentName())){
			query.setString("studentName", "%"+model.getStudent().getStudentName()+"%");
		}
		Object obj = query.uniqueResult();
		return new Long(obj.toString());
	}


}
