package com.csit.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.csit.dao.StadiumDAO;
import com.csit.model.Stadium;
import com.csit.util.PageUtil;
/**
 * @Description:赛场DAO实现类
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-5
 * @author jcf
 * @vesion 1.0
 */
@Repository
public class StadiumDAOImp extends BaseDAOImpl<Stadium, Integer> implements StadiumDAO {
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.StadiumDAO#query(java.lang.Integer, java.lang.Integer, com.csit.model.Stadium)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Stadium> query(Stadium model,Integer page, Integer rows) {
		Criteria criteria = getCurrentSession().createCriteria(Stadium.class);
		criteria.createAlias("competitionGroup", "competitionGroup", CriteriaSpecification.LEFT_JOIN);
		if(model.getStadiumName()!=null){
			criteria.add(Restrictions.eq("stadiumName", model.getStadiumName()));
		}
		if(model!=null&&model.getCompetitionGroup()!=null&&model.getCompetitionGroup().getCompetition()!=null){
			criteria.add(Restrictions.eq("competitionGroup.competition", model.getCompetitionGroup().getCompetition()));
		}
		criteria.setFirstResult(PageUtil.getPageBegin(page, rows));
		criteria.setMaxResults(rows);
		return criteria.list();
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.StadiumDAO#count(com.csit.model.Stadium)
	 */
	@Override
	public Long getTotalCount(Stadium model) {
		Criteria criteria = getCurrentSession().createCriteria(Stadium.class);
		criteria.createAlias("competitionGroup", "competitionGroup", CriteriaSpecification.LEFT_JOIN);
		if(model.getStadiumName()!=null){
			criteria.add(Restrictions.eq("stadiumName", model.getStadiumName()));
		}
		if(model!=null&&model.getCompetitionGroup()!=null&&model.getCompetitionGroup().getCompetition()!=null){
			criteria.add(Restrictions.eq("competitionGroup.competition", model.getCompetitionGroup().getCompetition()));
		}
		criteria.setProjection(Projections.rowCount());
		return new Long(criteria.uniqueResult().toString());
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Stadium> query(Integer competitionId) {
		Criteria criteria = getCurrentSession().createCriteria(Stadium.class);
		criteria.createAlias("competitionGroup", "competitionGroup", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("competitionGroup.competition", "competition", CriteriaSpecification.LEFT_JOIN);
		if(competitionId!=null){
			criteria.add(Restrictions.eq("competition.competitionId", competitionId));
		}
		return criteria.list();
	}

}
