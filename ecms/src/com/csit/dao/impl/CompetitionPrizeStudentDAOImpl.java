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

import com.csit.dao.CompetitionPrizeStudentDAO;
import com.csit.model.CompetitionPrizeStudent;
import com.csit.util.PageUtil;
/**
 * @Description:赛事获奖学生DAO实现类
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-22
 * @Author lys
 */
@Repository
public class CompetitionPrizeStudentDAOImpl extends
		BaseDAOImpl<CompetitionPrizeStudent, Integer> implements
		CompetitionPrizeStudentDAO {
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.CompetitionPrizeStudentDAO#query(com.csit.model.CompetitionPrizeStudent, java.lang.Integer, java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CompetitionPrizeStudent> query(CompetitionPrizeStudent model,
			Integer page, Integer rows) {
		Criteria criteria = getCurrentSession().createCriteria(CompetitionPrizeStudent.class);
		criteria.createAlias("competitionPrize", "competitionPrize",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("competitionPrize.competitionGroup", "competitionGroup",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("competitionGroup.competition", "competition",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("competitionGroup.group", "group",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("competitionPrize.prize", "prize",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("student", "student",CriteriaSpecification.LEFT_JOIN);
		//赛事
		if(model!=null&&model.getCompetitionPrize()!=null&&model.getCompetitionPrize().getCompetitionGroup()!=null
				&&model.getCompetitionPrize().getCompetitionGroup().getCompetition()!=null&&model.getCompetitionPrize().getCompetitionGroup().getCompetition().getCompetitionId()!=null){
			criteria.add(Restrictions.eq("competitionGroup.competition", model.getCompetitionPrize().getCompetitionGroup().getCompetition()));
		}
		//组别
		if(model!=null&&model.getCompetitionPrize()!=null&&model.getCompetitionPrize().getCompetitionGroup()!=null
				&&model.getCompetitionPrize().getCompetitionGroup().getCompetitionGroupId()!=null){
			criteria.add(Restrictions.eq("competitionPrize.competitionGroup", model.getCompetitionPrize().getCompetitionGroup()));
		}
		//奖项
		if(model!=null&&model.getCompetitionPrize()!=null&&model.getCompetitionPrize().getCompetitionPrizeId()!=null){
			criteria.add(Restrictions.eq("competitionPrize", model.getCompetitionPrize()));
		}
		//学生
		if(model!=null&&model.getStudent()!=null&&StringUtils.isNotEmpty(model.getStudent().getStudentName())){
			criteria.add(Restrictions.like("student.studentName", model.getStudent().getStudentName(),MatchMode.ANYWHERE));
		}
		criteria.setFirstResult(PageUtil.getPageBegin(page, rows));
		criteria.setMaxResults(rows);
		criteria.addOrder(Order.asc("competitionGroup.competition"));
		criteria.addOrder(Order.asc("competitionGroup.array"));
		criteria.addOrder(Order.asc("competitionPrize.array"));
		return criteria.list();
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.CompetitionPrizeStudentDAO#getTotalCount(com.csit.model.CompetitionPrizeStudent)
	 */
	@Override
	public Long getTotalCount(CompetitionPrizeStudent model) {
		Criteria criteria = getCurrentSession().createCriteria(CompetitionPrizeStudent.class);
		criteria.createAlias("competitionPrize", "competitionPrize",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("competitionPrize.competitionGroup", "competitionGroup",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("competitionGroup.competition", "competition",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("competitionGroup.group", "group",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("competitionPrize.prize", "prize",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("student", "student",CriteriaSpecification.LEFT_JOIN);
		//赛事
		if(model!=null&&model.getCompetitionPrize()!=null&&model.getCompetitionPrize().getCompetitionGroup()!=null
				&&model.getCompetitionPrize().getCompetitionGroup().getCompetition()!=null&&model.getCompetitionPrize().getCompetitionGroup().getCompetition().getCompetitionId()!=null){
			criteria.add(Restrictions.eq("competitionGroup.competition", model.getCompetitionPrize().getCompetitionGroup().getCompetition()));
		}
		//组别
		if(model!=null&&model.getCompetitionPrize()!=null&&model.getCompetitionPrize().getCompetitionGroup()!=null
				&&model.getCompetitionPrize().getCompetitionGroup().getCompetitionGroupId()!=null){
			criteria.add(Restrictions.eq("competitionPrize.competitionGroup", model.getCompetitionPrize().getCompetitionGroup()));
		}
		//奖项
		if(model!=null&&model.getCompetitionPrize()!=null&&model.getCompetitionPrize().getCompetitionPrizeId()!=null){
			criteria.add(Restrictions.eq("competitionPrize", model.getCompetitionPrize()));
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
	 * (non-Javadoc)   
	 * @see com.csit.dao.CompetitionPrizeStudentDAO#selectQuery(com.csit.model.CompetitionPrizeStudent, java.lang.Integer, java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> selectQuery(CompetitionPrizeStudent model,
			Integer page, Integer rows) {
		StringBuffer sql = new StringBuffer();
		sql.append("select a.studentId,d.studentName,f.competitionName,g.groupName,e.score ");
		sql.append("from T_StudentCompetitionGroup a ");
		sql.append("left join T_CompetitionGroup b on a.competitionGroupId = b.competitionGroupId ");
		sql.append("left join (select a.studentId ");
		sql.append("	from T_CometitionPrizeStudent a ");
		sql.append("	left join T_CompetitionPrize b on a.competitionPrizeId = b.competitionPrizeId ");
		sql.append("	where  b.competitionGroupId =  :competitionGroupId) c on a.studentId = c.studentId ");
		sql.append("left join T_Student d on a.studentId = d.studentId ");
		sql.append("left join T_Exam_Answer e on a.studentCompetitionGroupId = e.studentCompetitionGroupId ");
		sql.append("left join T_Competition f on b.competitionId = f.competitionId ");
		sql.append("left join T_Group g on b.groupId = g.groupId ");
		sql.append("where a.status = 1 and a.competitionGroupId =  :competitionGroupId and c.studentId is null ");
		if(model!=null&&model.getStudent()!=null&&StringUtils.isNotEmpty(model.getStudent().getStudentName())){
			sql.append("	and d.studentName like :studentName ");
		}
		sql.append("order by e.score desc ");
		
		Query query = getCurrentSession().createSQLQuery(sql.toString());
		query.setInteger("competitionGroupId", model.getCompetitionPrize().getCompetitionGroup().getCompetitionGroupId());
		if(model!=null&&model.getStudent()!=null&&StringUtils.isNotEmpty(model.getStudent().getStudentName())){
			query.setString("studentName", "%"+model.getStudent().getStudentName()+"%");
		}
		query.setFirstResult(PageUtil.getPageBegin(page, rows));
		query.setMaxResults(rows);
		return query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.CompetitionPrizeStudentDAO#getTotalCountSelectQuery(com.csit.model.CompetitionPrizeStudent)
	 */
	@Override
	public Long getTotalCountSelectQuery(CompetitionPrizeStudent model) {
		StringBuffer sql = new StringBuffer();
		sql.append("select count(a.studentId) ");
		sql.append("from T_StudentCompetitionGroup a ");
		sql.append("left join T_CompetitionGroup b on a.competitionGroupId = b.competitionGroupId ");
		sql.append("left join (select a.studentId ");
		sql.append("	from T_CometitionPrizeStudent a ");
		sql.append("	left join T_CompetitionPrize b on a.competitionPrizeId = b.competitionPrizeId ");
		sql.append("	where  b.competitionGroupId =  :competitionGroupId) c on a.studentId = c.studentId ");
		sql.append("left join T_Student d on a.studentId = d.studentId ");
		sql.append("where a.status = 1 and a.competitionGroupId =  :competitionGroupId and c.studentId is null ");
		if(model!=null&&model.getStudent()!=null&&StringUtils.isNotEmpty(model.getStudent().getStudentName())){
			sql.append("	and d.studentName like :studentName ");
		}
		
		Query query = getCurrentSession().createSQLQuery(sql.toString());
		query.setInteger("competitionGroupId", model.getCompetitionPrize().getCompetitionGroup().getCompetitionGroupId());
		if(model!=null&&model.getStudent()!=null&&StringUtils.isNotEmpty(model.getStudent().getStudentName())){
			query.setString("studentName", "%"+model.getStudent().getStudentName()+"%");
		}
		Object obj = query.uniqueResult();
		return new Long(obj.toString());
	}

}
