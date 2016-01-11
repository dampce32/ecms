package com.csit.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.csit.dao.CompetitionPrizeDAO;
import com.csit.model.CompetitionPrize;
/**
 * @Description:赛事奖项DAO实现类
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-4
 * @author jcf
 * @vesion 1.0
 */
@Repository
public class CompetitionPrizeDAOImp extends BaseDAOImpl<CompetitionPrize, Integer> implements CompetitionPrizeDAO {
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.CompetitionDAO#query(java.lang.Integer, java.lang.Integer, com.csit.model.Competition)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CompetitionPrize> query(CompetitionPrize model) {
		Criteria criteria = getCurrentSession().createCriteria(CompetitionPrize.class);
		criteria.createAlias("prize", "prize");
		criteria.add(Restrictions.eq("competitionGroup.competitionGroupId", model.getCompetitionGroup().getCompetitionGroupId()));
		criteria.addOrder(Order.asc("array"));
		return criteria.list();
	}
	@Override
	public Integer getMaxArray() {
		Criteria criteria = getCurrentSession().createCriteria(CompetitionPrize.class);
		criteria.setProjection(Projections.max("array"));
		return criteria.uniqueResult()==null?0:(Integer)criteria.uniqueResult();
	}
	@Override
	public void updateArray(Integer competitionPrizeId, Integer updateCompetitionPrizeId) {
		StringBuffer sb = new StringBuffer();
		sb.append("update T_CompetitionPrize	");
		sb.append("set Array = case ");
	    sb.append("		when CompetitionPrizeID = :competitionPrizeId then (select Array from T_CompetitionPrize where  CompetitionPrizeID=:updateCompetitionPrizeId) ");
	    sb.append("		else (select Array from T_CompetitionPrize where  CompetitionPrizeID=:competitionPrizeId) ");
	    sb.append("	end ");
	    sb.append("where CompetitionPrizeID in (:competitionPrizeId,:updateCompetitionPrizeId)");
	    
	    getCurrentSession().createSQLQuery(sb.toString()).setInteger("competitionPrizeId", competitionPrizeId).setInteger("updateCompetitionPrizeId", updateCompetitionPrizeId).executeUpdate();
		
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.CompetitionPrizeDAO#queryCombobox(com.csit.model.CompetitionPrize)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CompetitionPrize> queryCombobox(CompetitionPrize model) {
		Criteria criteria = getCurrentSession().createCriteria(CompetitionPrize.class);
		criteria.createAlias("prize", "prize",CriteriaSpecification.LEFT_JOIN);
		if(model!=null&&model.getCompetitionGroup()!=null&&model.getCompetitionGroup().getCompetitionGroupId()!=null){
			criteria.add(Restrictions.eq("competitionGroup", model.getCompetitionGroup()));
		}
		criteria.addOrder(Order.asc("array"));
		return criteria.list();
	}
	@Override
	public void copyAdd(Integer newCompetitionId, Integer oldCompetitionId) {

		StringBuffer sql = new StringBuffer();
		
		sql.append("insert into T_CompetitionPrize(CompetitionGroupID, PrizeID, Award,Array) ");
		sql.append("select a.competitionGroupId,b.prizeId,b.award,b.array ");
		sql.append("from(select * from T_CompetitionGroup a where a.competitionId = :newCompetitionId) a ");
		sql.append("left join(select b.groupId,a.prizeId,a.award,a.array from T_CompetitionPrize a ");
		sql.append("left join T_CompetitionGroup b on a.competitionGroupId = b.competitionGroupId ");
		sql.append("where b.competitionId = :oldCompetitionId) b on a.groupId = b.groupId ");
		sql.append("where b.prizeId is not null");
		
		Query query = getCurrentSession().createSQLQuery(sql.toString());
		query.setInteger("newCompetitionId", newCompetitionId);
		query.setInteger("oldCompetitionId", oldCompetitionId);
		query.executeUpdate();
	}

}
