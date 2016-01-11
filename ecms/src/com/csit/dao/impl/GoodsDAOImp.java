package com.csit.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.csit.dao.GoodsDAO;
import com.csit.model.Goods;
import com.csit.util.PageUtil;
/**
 * @Description:教材DAO实现类
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-5-28
 * @author jcf
 * @vesion 1.0
 */
@Repository
public class GoodsDAOImp extends BaseDAOImpl<Goods, Integer> implements GoodsDAO {
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.GoodsDAO#query(java.lang.Integer, java.lang.Integer, com.csit.model.Goods)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Goods> query(Goods model,Integer page, Integer rows) {
		Criteria criteria = getCurrentSession().createCriteria(Goods.class);
		if(StringUtils.isNotEmpty(model.getGoodsName())){
			criteria.add(Restrictions.like("goodsName", model.getGoodsName(),MatchMode.ANYWHERE));
		} 
		criteria.setFirstResult(PageUtil.getPageBegin(page, rows));
		criteria.setMaxResults(rows);
		criteria.addOrder(Order.asc("goodsId"));
		return criteria.list();
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.GoodsDAO#count(com.csit.model.Goods)
	 */
	@Override
	public Long getTotalCount(Goods model) {
		Criteria criteria = getCurrentSession().createCriteria(Goods.class);
		if(StringUtils.isNotEmpty(model.getGoodsName())){
			criteria.add(Restrictions.like("goodsName", model.getGoodsName(),MatchMode.ANYWHERE));
		} 
		criteria.setProjection(Projections.rowCount());
		return new Long(criteria.uniqueResult().toString());
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Goods> queryCombobox(Goods model) {
		Criteria criteria = getCurrentSession().createCriteria(Goods.class);
		criteria.addOrder(Order.asc("goodsId"));
		return criteria.list();
	}

}
