package com.csit.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.csit.dao.PayTypeDAO;
import com.csit.model.PayType;
import com.csit.util.PageUtil;
/**
 * @Description:缴费类型DAO实现类
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-9
 * @author jcf
 * @vesion 1.0
 */
@Repository
public class PayTypeDAOImp extends BaseDAOImpl<PayType, Integer> implements PayTypeDAO {
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.PayTypeDAO#query(java.lang.Integer, java.lang.Integer, com.csit.model.PayType)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PayType> query(PayType model,Integer page, Integer rows) {
		Criteria criteria = getCurrentSession().createCriteria(PayType.class);
		if(StringUtils.isNotEmpty(model.getPayTypeName())){
			criteria.add(Restrictions.like("payTypeName", model.getPayTypeName(),MatchMode.ANYWHERE));
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
	 * @see com.csit.dao.PayTypeDAO#count(com.csit.model.PayType)
	 */
	@Override
	public Long getTotalCount(PayType model) {
		Criteria criteria = getCurrentSession().createCriteria(PayType.class);
		if(StringUtils.isNotEmpty(model.getPayTypeName())){
			criteria.add(Restrictions.like("payTypeName", model.getPayTypeName(),MatchMode.ANYWHERE));
		} 
		if(model!=null&&model.getStatus()!=null){
			criteria.add(Restrictions.eq("status", model.getStatus()));
		}
		criteria.setProjection(Projections.rowCount());
		return new Long(criteria.uniqueResult().toString());
	}
	@Override
	public Integer getMaxArray() {
		Criteria criteria = getCurrentSession().createCriteria(PayType.class);
		criteria.setProjection(Projections.max("array"));
		return criteria.uniqueResult()==null?0:(Integer)criteria.uniqueResult();
	}
	@Override
	public void updateArray(Integer payTypeId, Integer updatePayTypeId) {
		StringBuffer sb = new StringBuffer();
		sb.append("update T_PayType	");
		sb.append("set Array = case ");
	    sb.append("		when PayTypeID = :payTypeId then (select Array from T_PayType where  PayTypeID=:updatePayTypeId) ");
	    sb.append("		else (select Array from T_PayType where  PayTypeID=:payTypeId) ");
	    sb.append("	end ");
	    sb.append("where PayTypeID in (:payTypeId,:updatePayTypeId)");
	    
	    getCurrentSession().createSQLQuery(sb.toString()).setInteger("payTypeId", payTypeId).setInteger("updatePayTypeId", updatePayTypeId).executeUpdate();
		
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<PayType> queryCombobox(PayType model) {
		Criteria criteria = getCurrentSession().createCriteria(PayType.class);
		criteria.add(Restrictions.eq("status", 1));
		criteria.addOrder(Order.asc("array"));
		return criteria.list();
	}

}
