package com.csit.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.csit.dao.ProvinceDAO;
import com.csit.model.Province;
import com.csit.util.PageUtil;
/**
 * @Description:省份实现类
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-5-29
 * @Author lys
 */
@Repository
public class ProvinceDAOImpl extends BaseDAOImpl<Province, Integer> implements
		ProvinceDAO {
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.ProvinceDAO#query(com.csit.model.Province, java.lang.Integer, java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Province> query(Province model, Integer page, Integer rows) {
		Criteria criteria = getCurrentSession().createCriteria(Province.class);
		if(StringUtils.isNotEmpty(model.getProvinceCode())){
			criteria.add(Restrictions.like("provinceCode", model.getProvinceCode(),MatchMode.ANYWHERE));
		} 
		if(StringUtils.isNotEmpty(model.getProvinceName())){
			criteria.add(Restrictions.like("provinceName", model.getProvinceName(),MatchMode.ANYWHERE));
		} 
		criteria.setFirstResult(PageUtil.getPageBegin(page, rows));
		criteria.setMaxResults(rows);
		criteria.addOrder(Order.asc("array"));
		return criteria.list();
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.ProvinceDAO#getTotalCount(com.csit.model.Province)
	 */
	@Override
	public Long getTotalCount(Province model) {
		Criteria criteria = getCurrentSession().createCriteria(Province.class);
		if(StringUtils.isNotEmpty(model.getProvinceCode())){
			criteria.add(Restrictions.like("provinceCode", model.getProvinceCode(),MatchMode.ANYWHERE));
		} 
		if(StringUtils.isNotEmpty(model.getProvinceName())){
			criteria.add(Restrictions.like("provinceName", model.getProvinceName(),MatchMode.ANYWHERE));
		} 
		criteria.setProjection(Projections.rowCount());
		return new Long(criteria.uniqueResult().toString());
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.ProvinceDAO#queryCombobox()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Province> queryCombobox() {
		Criteria criteria = getCurrentSession().createCriteria(Province.class);
		criteria.addOrder(Order.asc("array"));
		return criteria.list();
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.ProvinceDAO#getMaxArray()
	 */
	@Override
	public Integer getMaxArray() {
		Criteria criteria = getCurrentSession().createCriteria(Province.class);
		criteria.setProjection(Projections.max("array"));
		return criteria.uniqueResult()==null?0:(Integer)criteria.uniqueResult();
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.ProvinceDAO#updateArray(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public void updateArray(Integer provinceId, Integer updateProvinceId) {
		StringBuffer sb = new StringBuffer();
		sb.append("update T_Province ");
		sb.append("set array = case ");
	    sb.append("		when provinceId = :provinceId then (select array from T_Province where provinceId=:updateProvinceId) ");
	    sb.append("		else (select array from T_Province where provinceId=:provinceId) ");
	    sb.append("	end ");
	    sb.append("where provinceId in (:provinceId,:updateProvinceId)");
	    
	    getCurrentSession().createSQLQuery(sb.toString()).setInteger("provinceId", provinceId).setInteger("updateProvinceId", updateProvinceId).executeUpdate();
	}

}
