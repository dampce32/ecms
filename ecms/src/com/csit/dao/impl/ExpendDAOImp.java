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

import com.csit.dao.ExpendDAO;
import com.csit.model.Expend;
import com.csit.util.PageUtil;
/**
 * @Description:支出DAO实现类
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-9
 * @author jcf
 * @vesion 1.0
 */
@Repository
public class ExpendDAOImp extends BaseDAOImpl<Expend, Integer> implements ExpendDAO {
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.ExpendDAO#query(java.lang.Integer, java.lang.Integer, com.csit.model.Expend)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Expend> query(Expend model,Integer page, Integer rows) {
		Criteria criteria = getCurrentSession().createCriteria(Expend.class);
		criteria.createAlias("competition", "competition", CriteriaSpecification.LEFT_JOIN);
		if(StringUtils.isNotEmpty(model.getExpendName())){
			criteria.add(Restrictions.like("expendName", model.getExpendName(),MatchMode.ANYWHERE));
		}
		if(model!=null&&model.getCompetition()!=null){
			criteria.add(Restrictions.eq("competition", model.getCompetition()));
		}
		criteria.setFirstResult(PageUtil.getPageBegin(page, rows));
		criteria.setMaxResults(rows);
		criteria.addOrder(Order.asc("expendId"));
		return criteria.list();
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.ExpendDAO#count(com.csit.model.Expend)
	 */
	@Override
	public Long getTotalCount(Expend model) {
		Criteria criteria = getCurrentSession().createCriteria(Expend.class);
		if(StringUtils.isNotEmpty(model.getExpendName())){
			criteria.add(Restrictions.like("expendName", model.getExpendName(),MatchMode.ANYWHERE));
		} 
		if(model!=null&&model.getCompetition()!=null){
			criteria.add(Restrictions.eq("competition", model.getCompetition()));
		}
		criteria.setProjection(Projections.rowCount());
		return new Long(criteria.uniqueResult().toString());
	}
}
