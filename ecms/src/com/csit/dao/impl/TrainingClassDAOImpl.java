package com.csit.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.csit.dao.TrainingClassDAO;
import com.csit.model.TrainingClass;
import com.csit.util.PageUtil;
/**
 * @Description: 培训班级DAO实现类
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-7
 * @author wxy
 * @vesion 1.0
 */
@Repository
public class TrainingClassDAOImpl extends BaseDAOImpl<TrainingClass, Integer> implements TrainingClassDAO {
	
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.TrainingClassDAOImp#query(com.csit.model.TrainingClass, java.lang.Integer, java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<TrainingClass> query(TrainingClass model, Integer page, Integer rows) {
		Criteria criteria = getCurrentSession().createCriteria(TrainingClass.class);
		criteria.createAlias("competitionGroup", "competitionGroup", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("goods", "goods", CriteriaSpecification.LEFT_JOIN);
		if(StringUtils.isNotEmpty(model.getAddress())){
			criteria.add(Restrictions.like("address", model.getAddress(),MatchMode.ANYWHERE));
		}
		if(model!=null&&model.getGoods()!=null&&model.getGoods().getGoodsId()!=null){
			criteria.add(Restrictions.eq("goods", model.getGoods()));
		}
		if(StringUtils.isNotEmpty(model.getTrainingClassName())){
			criteria.add(Restrictions.like("trainingClassName", model.getTrainingClassName(),MatchMode.ANYWHERE));
		}
		if(model.getCompetitionGroup()!=null&&model.getCompetitionGroup().getCompetition()!=null){
			criteria.add(Restrictions.eq("competitionGroup.competition", model.getCompetitionGroup().getCompetition()));
		}
		if(model!=null&&model.getStatus()!=null){
			criteria.add(Restrictions.eq("status", model.getStatus()));
		}
		criteria.setFirstResult(PageUtil.getPageBegin(page, rows));
		criteria.setMaxResults(rows);
		criteria.addOrder(Order.asc("trainingClassId"));
		return criteria.list();
	}
	
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.TrainingClassDAOImpl#getTotalCount(com.csit.model.TrainingClass)
	 */
	@Override
	public Long getTotalCount(TrainingClass model) {
		Criteria criteria = getCurrentSession().createCriteria(TrainingClass.class);
		criteria.createAlias("competitionGroup", "competitionGroup", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("goods", "goods", CriteriaSpecification.LEFT_JOIN);
		if(model!=null&&model.getAddress()!=null){
			criteria.add(Restrictions.eq("address", model.getAddress()));
		}
		if(model!=null&&model.getGoods()!=null&&model.getGoods().getGoodsId()!=null){
			criteria.add(Restrictions.eq("goods", model.getGoods()));
		}
		if(StringUtils.isNotEmpty(model.getTrainingClassName())){
			criteria.add(Restrictions.like("trainingClassName", model.getTrainingClassName(),MatchMode.ANYWHERE));
		}
		if(model.getCompetitionGroup()!=null&&model.getCompetitionGroup().getCompetition()!=null){
			criteria.add(Restrictions.eq("competitionGroup.competition", model.getCompetitionGroup().getCompetition()));
		}
		if(model!=null&&model.getStatus()!=null){
			criteria.add(Restrictions.eq("status", model.getStatus()));
		}
		criteria.setProjection(Projections.rowCount());
		return new Long(criteria.uniqueResult().toString());
	}
	
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.TrainingClassDAOImpl#queryCombobox(com.csit.model.TrainingClass)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<TrainingClass> queryCombobox(TrainingClass trainingclass,Integer competitionId) {
		Criteria criteria = getCurrentSession().createCriteria(TrainingClass.class);
		criteria.createAlias("competitionGroup", "competitionGroup", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("competitionGroup.competition", "competition", CriteriaSpecification.LEFT_JOIN);
		if(competitionId!=null){
			criteria.add(Restrictions.eq("competition.competitionId", competitionId));
		}
		criteria.add(Restrictions.eq("status", 1));
		criteria.addOrder(Order.asc("trainingClassId"));
		return criteria.list();
	}

}
