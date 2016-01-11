package com.csit.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.csit.dao.RecrejDAO;
import com.csit.model.Recrej;
import com.csit.util.PageUtil;
/**
 * @Description:入库出库DAO实现类
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-5-28
 * @author jcf
 * @vesion 1.0
 */
@Repository
public class RecrejDAOImp extends BaseDAOImpl<Recrej, Integer> implements RecrejDAO {
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.RecrejDAO#query(java.lang.Integer, java.lang.Integer, com.csit.model.Recrej)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Recrej> query(Recrej model,Integer page, Integer rows) {
		Criteria criteria = getCurrentSession().createCriteria(Recrej.class);
		if(model.getRecrejType()!=null){
			criteria.add(Restrictions.eq("recrejType", model.getRecrejType()));
		} 
		criteria.setFirstResult(PageUtil.getPageBegin(page, rows));
		criteria.setMaxResults(rows);
		criteria.addOrder(Order.asc("recrejId"));
		return criteria.list();
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.RecrejDAO#count(com.csit.model.Recrej)
	 */
	@Override
	public Long getTotalCount(Recrej model) {
		Criteria criteria = getCurrentSession().createCriteria(Recrej.class);
		if(model.getRecrejType()!=null){
			criteria.add(Restrictions.eq("recrejType", model.getRecrejType()));
		} 
		criteria.setProjection(Projections.rowCount());
		return new Long(criteria.uniqueResult().toString());
	}

}
