package com.csit.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.csit.dao.PayDAO;
import com.csit.model.Pay;
import com.csit.util.PageUtil;
/**
 * @Description:缴费DAO实现类
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-9
 * @author jcf
 * @vesion 1.0
 */
@Repository
public class PayDAOImp extends BaseDAOImpl<Pay, Integer> implements PayDAO {
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.PayDAO#query(java.lang.Integer, java.lang.Integer, com.csit.model.Pay)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Pay> query(Pay model,Integer page, Integer rows) {
		Criteria criteria = getCurrentSession().createCriteria(Pay.class);
		criteria.createAlias("student", "student");
		criteria.createAlias("payType", "payType");
		criteria.createAlias("teacher", "teacher");
		criteria.createAlias("competition", "competition");
		if(model.getCompetition()!=null){
			criteria.add(Restrictions.eq("competition", model.getCompetition()));
		} 
		if(model.getPayType()!=null){
			criteria.add(Restrictions.eq("payType", model.getPayType()));
		}
		criteria.setFirstResult(PageUtil.getPageBegin(page, rows));
		criteria.setMaxResults(rows);
		criteria.addOrder(Order.desc("payId"));
		return criteria.list();
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.PayDAO#count(com.csit.model.Pay)
	 */
	@Override
	public Long getTotalCount(Pay model) {
		Criteria criteria = getCurrentSession().createCriteria(Pay.class);
		if(model.getCompetition()!=null){
			criteria.add(Restrictions.eq("competition", model.getCompetition()));
		} 
		if(model.getPayType()!=null){
			criteria.add(Restrictions.eq("payType", model.getPayType()));
		}
		criteria.setProjection(Projections.rowCount());
		return new Long(criteria.uniqueResult().toString());
	}
}
