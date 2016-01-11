package com.csit.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.csit.dao.TrainingClassStudentDAO;
import com.csit.model.TrainingClassStudent;
import com.csit.util.PageUtil;
/**
 * @Description:报名培训班级DAO实现类
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-21
 * @author jcf
 * @vesion 1.0
 */
@Repository
public class TrainingClassStudentDAOImpl extends BaseDAOImpl<TrainingClassStudent, Integer> implements TrainingClassStudentDAO {
	
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.TrainingClassStudentDAOImp#query(com.csit.model.TrainingClassStudent, java.lang.Integer, java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<TrainingClassStudent> query(TrainingClassStudent model,Integer competitionId, Integer page, Integer rows) {
		Criteria criteria = getCurrentSession().createCriteria(TrainingClassStudent.class);
		criteria.createAlias("student", "student", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("trainingClass", "trainingClass", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("trainingClass.competitionGroup", "competitionGroup", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("competitionGroup.competition", "competition", CriteriaSpecification.LEFT_JOIN);
		if(model!=null&&model.getTrainingClass()!=null){
			criteria.add(Restrictions.eq("trainingClass", model.getTrainingClass()));
		}
		if(competitionId!=null){
			criteria.add(Restrictions.eq("competition.competitionId", competitionId));
		}
		criteria.setFirstResult(PageUtil.getPageBegin(page, rows));
		criteria.setMaxResults(rows);
		criteria.addOrder(Order.asc("trainingClassStudentId"));
		return criteria.list();
	}
	
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.TrainingClassStudentDAOImpl#getTotalCount(com.csit.model.TrainingClassStudent)
	 */
	@Override
	public Long getTotalCount(TrainingClassStudent model,Integer competitionId) {
		Criteria criteria = getCurrentSession().createCriteria(TrainingClassStudent.class);
		criteria.createAlias("trainingClass", "trainingClass", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("trainingClass.competitionGroup", "competitionGroup", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("competitionGroup.competition", "competition", CriteriaSpecification.LEFT_JOIN);
		if(model!=null&&model.getTrainingClass()!=null){
			criteria.add(Restrictions.eq("trainingClass", model.getTrainingClass()));
		}
		if(competitionId!=null){
			criteria.add(Restrictions.eq("competition.competitionId", competitionId));
		}
		criteria.setProjection(Projections.rowCount());
		return new Long(criteria.uniqueResult().toString());
	}
	
}
