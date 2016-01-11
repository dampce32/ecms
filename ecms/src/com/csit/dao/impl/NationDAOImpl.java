package com.csit.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.csit.dao.NationDAO;
import com.csit.model.Nation;
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
public class NationDAOImpl extends BaseDAOImpl<Nation, Integer> implements
		NationDAO {

	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.NationDAO#query(com.csit.model.Nation, java.lang.Integer, java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Nation> query(Nation model, Integer page, Integer rows) {
		Criteria criteria = getCurrentSession().createCriteria(Nation.class);
		
		if(model!=null&&model.getNationName()!=null){
			criteria.add(Restrictions.like("nationName", model.getNationName(), MatchMode.ANYWHERE));
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
	 * @see com.csit.dao.NationDAO#getTotalCount(com.csit.model.Nation)
	 */
	@Override
	public Long getTotalCount(Nation model) {
		Criteria criteria = getCurrentSession().createCriteria(Nation.class);
		
		if(model!=null&&model.getNationName()!=null){
			criteria.add(Restrictions.like("nationName", model.getNationName(), MatchMode.ANYWHERE));
		}
		if(model!=null&&model.getStatus()!=null){
			criteria.add(Restrictions.eq("status", model.getStatus()));
		}
		criteria.setProjection(Projections.rowCount());
		
		return new Long(criteria.uniqueResult().toString());
	}

	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.NationDAO#getMaxArray()
	 */
	@Override
	public Integer getMaxArray() {
		Criteria criteria = getCurrentSession().createCriteria(Nation.class);
		criteria.setProjection(Projections.max("array"));
		return criteria.uniqueResult()==null?0:(Integer)criteria.uniqueResult();
	}

	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.NationDAO#updateArray(java.lang.String, java.lang.String)
	 */
	@Override
	public void updateArray(String nationId, String updateNationId) {
		StringBuffer sb = new StringBuffer();
		sb.append("update T_Nation ");
		sb.append("set array = case ");
	    sb.append("		when nationId = :nationId then (select array from T_Nation where nationId=:updateNationId) ");
	    sb.append("		else (select array from T_Nation where nationId=:nationId) ");
	    sb.append("	end ");
	    sb.append("where nationId in (:nationId,:updateNationId)");
	    
	    getCurrentSession().createSQLQuery(sb.toString()).setString("nationId", nationId).setString("updateNationId", updateNationId).executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Nation> queryCombobox() {
		Criteria criteria = getCurrentSession().createCriteria(Nation.class);
		criteria.add(Restrictions.eq("status", 1));
		criteria.addOrder(Order.asc("array"));
		return criteria.list();
	}

}
