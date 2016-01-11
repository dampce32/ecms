package com.csit.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.csit.dao.StadiumStudentDAO;
import com.csit.model.StadiumStudent;
import com.csit.util.PageUtil;
/**
 * @Description:选择赛场DAO实现类
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-21
 * @author jcf
 * @vesion 1.0
 */
@Repository
public class StadiumStudentDAOImpl extends BaseDAOImpl<StadiumStudent, Integer> implements StadiumStudentDAO {
	
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.StadiumStudentDAOImp#query(com.csit.model.StadiumStudent, java.lang.Integer, java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<StadiumStudent> query(StadiumStudent model, Integer competitionId, Integer page, Integer rows) {
		Criteria criteria = getCurrentSession().createCriteria(StadiumStudent.class);
		criteria.createAlias("student", "student", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("stadium", "stadium", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("stadium.competitionGroup", "competitionGroup", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("competitionGroup.competition", "competition", CriteriaSpecification.LEFT_JOIN);
		if(model!=null&&model.getStadium()!=null){
			criteria.add(Restrictions.eq("stadium", model.getStadium()));
		}
		if(competitionId!=null){
			criteria.add(Restrictions.eq("competition.competitionId", competitionId));
		}
		criteria.setFirstResult(PageUtil.getPageBegin(page, rows));
		criteria.setMaxResults(rows);
		criteria.addOrder(Order.asc("stadiumStudentId"));
		return criteria.list();
	}
	
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.StadiumStudentDAOImpl#getTotalCount(com.csit.model.StadiumStudent)
	 */
	@Override
	public Long getTotalCount(StadiumStudent model, Integer competitionId) {
		Criteria criteria = getCurrentSession().createCriteria(StadiumStudent.class);
		criteria.createAlias("stadium", "stadium", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("stadium.competitionGroup", "competitionGroup", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("competitionGroup.competition", "competition", CriteriaSpecification.LEFT_JOIN);
		if(model!=null&&model.getStadium()!=null){
			criteria.add(Restrictions.eq("stadium", model.getStadium()));
		}
		if(competitionId!=null){
			criteria.add(Restrictions.eq("competition.competitionId", competitionId));
		}
		criteria.setProjection(Projections.rowCount());
		return new Long(criteria.uniqueResult().toString());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StadiumStudent> query(StadiumStudent model) {
		Criteria criteria = getCurrentSession().createCriteria(StadiumStudent.class);
		criteria.createAlias("student", "student", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("stadium", "stadium", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("stadium.competitionGroup", "competitionGroup", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("competitionGroup.competition", "competition", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("competitionGroup.group", "group", CriteriaSpecification.LEFT_JOIN);
		if(model!=null&&model.getStudent()!=null){
			criteria.add(Restrictions.eq("student", model.getStudent()));
		}
		criteria.addOrder(Order.asc("stadiumStudentId"));
		return criteria.list();
	}
	
}
