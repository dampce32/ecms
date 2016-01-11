package com.csit.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.csit.dao.StudentCompetitionGroupDAO;
import com.csit.model.StudentCompetitionGroup;
import com.csit.util.PageUtil;

/**
 * 
 * @Description: 学生参赛组别DAO实现类
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-4
 * @author yk
 * @vesion 1.0
 */
@Repository
public class StudentCompetitionGroupDAOImpl extends
		BaseDAOImpl<StudentCompetitionGroup, Integer> implements
		StudentCompetitionGroupDAO {

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.csit.dao.StudentCompetitionGroupDAO#query(com.csit.model.
	 * StudentCompetitionGroup, java.lang.Integer, java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<StudentCompetitionGroup> query(StudentCompetitionGroup model,
			Integer page, Integer rows) {

		Criteria criteria = getCurrentSession().createCriteria(
				StudentCompetitionGroup.class);

		criteria.createAlias("area", "area", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("area.city", "city", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("student", "student", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("competitionGroup", "competitionGroup", CriteriaSpecification.LEFT_JOIN);
		
		if(model!=null&&model.getCompetitionGroup()!=null&&model.getCompetitionGroup().getCompetition()!=null&&
				model.getCompetitionGroup().getCompetition().getCompetitionId()!=null){
			criteria.add(Restrictions.eq("competitionGroup.competition", model.getCompetitionGroup().getCompetition()));
		} 
		if(model!=null&&model.getStudent()!=null&&model.getStudent().getStudentName()!=null){
			criteria.add(Restrictions.like("student.studentName", model.getStudent().getStudentName(),MatchMode.ANYWHERE));
		} 
		if(model!=null&&model.getCompetitionGroup()!=null&&model.getCompetitionGroup().getGroup()!=null&&
				model.getCompetitionGroup().getGroup().getGroupId()!=null){
			criteria.add(Restrictions.eq("competitionGroup.group", model.getCompetitionGroup().getGroup()));
		}
		if(model!=null&&model.getStatus()!=null){
			criteria.add(Restrictions.eq("status", model.getStatus()));
		}
		
		criteria.setFirstResult(PageUtil.getPageBegin(page, rows));
		criteria.setMaxResults(rows);

		criteria.addOrder(Order.desc("studentCompetitionGroupId"));
		return criteria.list();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.csit.dao.StudentCompetitionGroupDAO#getTotalCount(com.csit.model.
	 * StudentCompetitionGroup)
	 */
	@Override
	public Long getTotalCount(StudentCompetitionGroup model) {

		Criteria criteria = getCurrentSession().createCriteria(
				StudentCompetitionGroup.class);
		
		criteria.createAlias("student", "student", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("competitionGroup", "competitionGroup", CriteriaSpecification.LEFT_JOIN);
		
		if(model!=null&&model.getCompetitionGroup()!=null&&model.getCompetitionGroup().getCompetition()!=null&&
				model.getCompetitionGroup().getCompetition().getCompetitionId()!=null){
			criteria.add(Restrictions.eq("competitionGroup.competition", model.getCompetitionGroup().getCompetition()));
		} 
		if(model!=null&&model.getStudent()!=null&&model.getStudent().getStudentName()!=null){
			criteria.add(Restrictions.like("student.studentName", model.getStudent().getStudentName(),MatchMode.ANYWHERE));
		} 
		if(model!=null&&model.getCompetitionGroup()!=null&&model.getCompetitionGroup().getGroup()!=null&&
				model.getCompetitionGroup().getGroup().getGroupId()!=null){
			criteria.add(Restrictions.eq("competitionGroup.group", model.getCompetitionGroup().getGroup()));
		}
		if(model!=null&&model.getStatus()!=null){
			criteria.add(Restrictions.eq("status", model.getStatus()));
		}

		criteria.setProjection(Projections.rowCount());

		return new Long(criteria.uniqueResult().toString());
	}

	@Override
	public Long getTotalCount(Integer competitionId, Integer[] stuIdArr) {

		Criteria criteria = getCurrentSession().createCriteria(StudentCompetitionGroup.class);
		criteria.createAlias("competitionGroup", "competitionGroup", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("student", "student", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("competitionGroup.group", "group", CriteriaSpecification.LEFT_JOIN);
		criteria.add(Restrictions.eq("competitionGroup.competition.competitionId", competitionId));
		criteria.add(Restrictions.eq("status", 1));
		if(stuIdArr!=null && stuIdArr.length!=0){
			criteria.add(Restrictions.not(Restrictions.in("student.studentId", stuIdArr)));
		}
		criteria.setProjection(Projections.rowCount());

		return new Long(criteria.uniqueResult().toString());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StudentCompetitionGroup> query(Integer competitionId,Integer page, Integer rows, Integer[] stuIdArr) {
		Criteria criteria = getCurrentSession().createCriteria(StudentCompetitionGroup.class);

		criteria.createAlias("competitionGroup", "competitionGroup", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("student", "student", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("competitionGroup.group", "group", CriteriaSpecification.LEFT_JOIN);
		criteria.add(Restrictions.eq("competitionGroup.competition.competitionId", competitionId));
		criteria.add(Restrictions.eq("status", 1));
		if(stuIdArr!=null && stuIdArr.length!=0){
			criteria.add(Restrictions.not(Restrictions.in("student.studentId", stuIdArr)));
		}
		criteria.setFirstResult(PageUtil.getPageBegin(page, rows));
		criteria.setMaxResults(rows);

		criteria.addOrder(Order.desc("studentCompetitionGroupId"));
		return criteria.list();
	}

	@Override
	public String getMaxExamCode(Integer competitionGroupId) {
		Criteria criteria  = getCurrentSession().createCriteria(StudentCompetitionGroup.class);
		
		criteria.createAlias("competitionGroup", "competitionGroup", CriteriaSpecification.LEFT_JOIN);
		
		criteria.add(Restrictions.eq("competitionGroup.competitionGroupId", competitionGroupId));
		
		String maxExamCode = (String) criteria.setProjection(Projections.max("examCode")).uniqueResult();
		
		return maxExamCode;
	}

}
