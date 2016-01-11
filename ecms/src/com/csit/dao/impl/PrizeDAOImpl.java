package com.csit.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.csit.dao.PrizeDAO;
import com.csit.model.Prize;
import com.csit.util.PageUtil;
/**
 * 
 * @Description: 民族DAO实现类
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-5-28
 * @author yk
 * @vesion 1.0
 */
@Repository
public class PrizeDAOImpl extends BaseDAOImpl<Prize, Integer> implements
		PrizeDAO {

	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.PrizeDAO#query(com.csit.model.Prize, java.lang.Integer, java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Prize> query(Prize model, Integer page, Integer rows) {
		Criteria criteria = getCurrentSession().createCriteria(Prize.class);
		
		if(model!=null&&model.getPrizeName()!=null){
			criteria.add(Restrictions.like("prizeName", model.getPrizeName(), MatchMode.ANYWHERE));
		}
		if(model!=null&&model.getStatus()!=null){
			criteria.add(Restrictions.eq("status", model.getStatus()));
		}
		criteria.setFirstResult(PageUtil.getPageBegin(page, rows));
		criteria.setMaxResults(rows);
		
		criteria.addOrder(Order.asc("array"));
		return criteria.list();
	}

	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.PrizeDAO#getTotalCount(com.csit.model.Prize)
	 */
	@Override
	public Long getTotalCount(Prize model) {
		Criteria criteria = getCurrentSession().createCriteria(Prize.class);
		
		if(model!=null&&model.getPrizeName()!=null){
			criteria.add(Restrictions.like("prizeName", model.getPrizeName(), MatchMode.ANYWHERE));
		}
		if(model!=null&&model.getStatus()!=null){
			criteria.add(Restrictions.eq("status", model.getStatus()));
		}
		criteria.setProjection(Projections.rowCount());
		
		return new Long(criteria.uniqueResult().toString());
	}

	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.PrizeDAO#getMaxArray()
	 */
	@Override
	public Integer getMaxArray() {
		Criteria criteria = getCurrentSession().createCriteria(Prize.class);
		criteria.setProjection(Projections.max("array"));
		return criteria.uniqueResult()==null?0:(Integer)criteria.uniqueResult();
	}

	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.PrizeDAO#updateArray(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public void updateArray(Integer PrizeId, Integer updatePrizeId) {
		StringBuffer sb = new StringBuffer();
		sb.append("update T_Prize ");
		sb.append("set array = case ");
	    sb.append("		when prizeId = :prizeId then (select array from T_Prize where prizeId=:updatePrizeId) ");
	    sb.append("		else (select array from T_Prize where prizeId=:prizeId) ");
	    sb.append("	end ");
	    sb.append("where prizeId in (:prizeId,:updatePrizeId)");
	    
	    getCurrentSession().createSQLQuery(sb.toString()).setInteger("prizeId", PrizeId).setInteger("updatePrizeId", updatePrizeId).executeUpdate();
	}
	
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.PrizeDAO#query(java.lang.Integer, java.lang.Integer,java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Prize> query(Integer competitionGroupId,Integer page, Integer rows) {

		StringBuffer sb = new StringBuffer();
		sb.append("select * from T_Prize p ");
		sb.append("where p.status=1 and p.prizeId not in( ");
		sb.append("select cp.prizeId from T_CompetitionPrize cp where cp.competitionGroupId=:competitionGroupId) ");
		sb.append("order by p.array");
		Query query = getCurrentSession().createSQLQuery(sb.toString());
		query.setInteger("competitionGroupId", competitionGroupId);
		query.setFirstResult((page-1)*rows);
		query.setMaxResults(rows);
		return query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}

	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.PrizeDAO#getTotalCount(java.lang.Integer)
	 */
	@Override
	public Long getTotalCount(Integer competitionGroupId) {
		StringBuffer sb = new StringBuffer();
		sb.append("select count(*) from T_Prize p ");
		sb.append("where p.prizeId not in( ");
		sb.append("select cp.prizeId from T_CompetitionPrize cp where cp.competitionGroupId=:competitionGroupId)");
		Query query = getCurrentSession().createSQLQuery(sb.toString());
		query.setInteger("competitionGroupId", competitionGroupId);
		return Long.parseLong(query.uniqueResult().toString()) ;
	}

}
