package com.csit.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.csit.dao.DataDictionaryDAO;
import com.csit.model.DataDictionary;
import com.csit.util.PageUtil;
/**
 * @Description:数据字典DAO实现类
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-4-18
 * @Author lys
 */
@Repository
public class DataDictionaryDAOImpl extends BaseDAOImpl<DataDictionary, Integer>
		implements DataDictionaryDAO {
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.DataDictionaryDAO#query(java.lang.Integer, java.lang.Integer, com.csit.model.DataDictionary)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<DataDictionary> query(Integer page, Integer rows,
			DataDictionary model) {
		Criteria criteria = getCurrentSession().createCriteria(DataDictionary.class);
		if(StringUtils.isNotEmpty(model.getDataDictionaryType())){
			criteria.add(Restrictions.eq("dataDictionaryType", model.getDataDictionaryType()));
		} 
		if(StringUtils.isNotEmpty(model.getDataDictionaryCode())){
			criteria.add(Restrictions.like("dataDictionaryCode", model.getDataDictionaryCode(),MatchMode.ANYWHERE));
		} 
		if(StringUtils.isNotEmpty(model.getDataDictionaryName())){
			criteria.add(Restrictions.like("dataDictionaryName", model.getDataDictionaryName(),MatchMode.ANYWHERE));
		} 
		criteria.setFirstResult(PageUtil.getPageBegin(page, rows));
		criteria.setMaxResults(rows);
		
		criteria.addOrder(Order.desc("array"));
		return criteria.list();
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.DataDictionaryDAO#count(com.csit.model.DataDictionary)
	 */
	@Override
	public Long count(DataDictionary model) {
		Criteria criteria = getCurrentSession().createCriteria(DataDictionary.class);
		if(StringUtils.isNotEmpty(model.getDataDictionaryType())){
			criteria.add(Restrictions.eq("dataDictionaryType", model.getDataDictionaryType()));
		} 
		if(StringUtils.isNotEmpty(model.getDataDictionaryCode())){
			criteria.add(Restrictions.like("dataDictionaryCode", model.getDataDictionaryCode(),MatchMode.ANYWHERE));
		} 
		if(StringUtils.isNotEmpty(model.getDataDictionaryName())){
			criteria.add(Restrictions.like("dataDictionaryName", model.getDataDictionaryName(),MatchMode.ANYWHERE));
		} 
		criteria.setProjection(Projections.rowCount());
		return new Long(criteria.uniqueResult().toString());
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.DataDictionaryDAO#getMaxArray(java.lang.String)
	 */
	@Override
	public Integer getMaxArray(String dataDictionaryType) {
		Criteria criteria = getCurrentSession().createCriteria(DataDictionary.class);
		criteria.add(Restrictions.eq("dataDictionaryType", dataDictionaryType));
		criteria.setProjection(Projections.max("array"));
		Object obj = criteria.uniqueResult();
		return obj==null?0:new Integer(obj.toString());
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.DataDictionaryDAO#queryCombobox(com.csit.model.DataDictionary)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<DataDictionary> queryCombobox(DataDictionary model) {
		Criteria criteria = getCurrentSession().createCriteria(DataDictionary.class);
		criteria.add(Restrictions.eq("dataDictionaryType", model.getDataDictionaryType()));
		criteria.add(Restrictions.eq("state", true));
		criteria.addOrder(Order.desc("array"));
		return criteria.list();
	}

}
