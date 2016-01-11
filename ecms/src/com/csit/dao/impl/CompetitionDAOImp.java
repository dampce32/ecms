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

import com.csit.dao.CompetitionDAO;
import com.csit.model.Competition;
import com.csit.util.PageUtil;
/**
 * @Description:赛事DAO实现类
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-5-30
 * @author jcf
 * @vesion 1.0
 */
@Repository
public class CompetitionDAOImp extends BaseDAOImpl<Competition, Integer> implements CompetitionDAO {
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.CompetitionDAO#query(java.lang.Integer, java.lang.Integer, com.csit.model.Competition)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Competition> query(Competition model,Integer page, Integer rows) {
		Criteria criteria = getCurrentSession().createCriteria(Competition.class);
		criteria.createAlias("picture", "picture", CriteriaSpecification.LEFT_JOIN);
		if(StringUtils.isNotEmpty(model.getCompetitionCode())){
			criteria.add(Restrictions.like("competitionCode", model.getCompetitionCode(),MatchMode.ANYWHERE));
		} 
		if(StringUtils.isNotEmpty(model.getCompetitionName())){
			criteria.add(Restrictions.like("competitionName", model.getCompetitionName(),MatchMode.ANYWHERE));
		}
		if(model!=null&&model.getStatus()!=null){
			criteria.add(Restrictions.eq("status", model.getStatus()));
		}
		criteria.setFirstResult(PageUtil.getPageBegin(page, rows));
		criteria.setMaxResults(rows);
		criteria.addOrder(Order.desc("competitionId"));
		return criteria.list();
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.CompetitionDAO#count(com.csit.model.Competition)
	 */
	@Override
	public Long getTotalCount(Competition model) {
		Criteria criteria = getCurrentSession().createCriteria(Competition.class);
		if(StringUtils.isNotEmpty(model.getCompetitionCode())){
			criteria.add(Restrictions.like("competitionCode", model.getCompetitionCode(),MatchMode.ANYWHERE));
		} 
		if(StringUtils.isNotEmpty(model.getCompetitionName())){
			criteria.add(Restrictions.like("competitionName", model.getCompetitionName(),MatchMode.ANYWHERE));
		} 
		if(model!=null&&model.getStatus()!=null){
			criteria.add(Restrictions.eq("status", model.getStatus()));
		}
		criteria.setProjection(Projections.rowCount());
		return new Long(criteria.uniqueResult().toString());
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Competition> queryCombobox(Competition model) {
		Criteria criteria = getCurrentSession().createCriteria(Competition.class);
		if(model!=null&&model.getStatus()!=null){
			criteria.add(Restrictions.eq("status", model.getStatus()));
		}
		criteria.addOrder(Order.desc("competitionId"));
		return criteria.list();
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.CompetitionDAO#queryIndexTitle()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Competition> queryIndexTitle() {
		Criteria criteria = getCurrentSession().createCriteria(Competition.class);
		criteria.add(Restrictions.eq("status", 1));
		criteria.addOrder(Order.desc("competitionId"));
		return criteria.list();
	}

}
