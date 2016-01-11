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

import com.csit.dao.CityDAO;
import com.csit.model.City;
import com.csit.model.Province;
import com.csit.util.PageUtil;
/**
 * @Description: 城市DAO实现类
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-5-29
 * @Author lys
 */
@Repository
public class CityDAOImpl extends BaseDAOImpl<City, Integer> implements CityDAO {
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.CityDAO#query(com.csit.model.City, java.lang.Integer, java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<City> query(City model, Integer page, Integer rows) {
		Criteria criteria = getCurrentSession().createCriteria(City.class);
		criteria.createAlias("province", "province", CriteriaSpecification.LEFT_JOIN);
		if(model!=null&&model.getProvince()!=null&&model.getProvince().getProvinceId()!=null){
			criteria.add(Restrictions.eq("province", model.getProvince()));
		}
		if(StringUtils.isNotEmpty(model.getCityCode())){
			criteria.add(Restrictions.like("cityCode", model.getCityCode(),MatchMode.ANYWHERE));
		} 
		if(StringUtils.isNotEmpty(model.getCityName())){
			criteria.add(Restrictions.like("cityName", model.getCityName(),MatchMode.ANYWHERE));
		} 
		criteria.setFirstResult(PageUtil.getPageBegin(page, rows));
		criteria.setMaxResults(rows);
		criteria.addOrder(Order.asc("array"));
		return criteria.list();
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.CityDAO#getTotalCount(com.csit.model.City)
	 */
	@Override
	public Long getTotalCount(City model) {
		Criteria criteria = getCurrentSession().createCriteria(City.class);
		if(model!=null&&model.getProvince()!=null&&model.getProvince().getProvinceId()!=null){
			criteria.add(Restrictions.eq("province", model.getProvince()));
		}
		if(StringUtils.isNotEmpty(model.getCityCode())){
			criteria.add(Restrictions.like("cityCode", model.getCityCode(),MatchMode.ANYWHERE));
		} 
		if(StringUtils.isNotEmpty(model.getCityName())){
			criteria.add(Restrictions.like("cityName", model.getCityName(),MatchMode.ANYWHERE));
		} 
		criteria.setProjection(Projections.rowCount());
		return new Long(criteria.uniqueResult().toString());
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.CityDAO#queryCombobox(com.csit.model.Province)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<City> queryCombobox(Province province) {
		Criteria criteria = getCurrentSession().createCriteria(City.class);
		criteria.add(Restrictions.eq("province", province));
		criteria.addOrder(Order.asc("array"));
		return criteria.list();
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.CityDAO#getMaxArray()
	 */
	@Override
	public Integer getMaxArray() {
		Criteria criteria = getCurrentSession().createCriteria(City.class);
		criteria.setProjection(Projections.max("array"));
		return criteria.uniqueResult()==null?0:(Integer)criteria.uniqueResult();
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.CityDAO#updateArray(java.lang.String, java.lang.String)
	 */
	@Override
	public void updateArray(Integer cityId, Integer updateCityId) {
		StringBuffer sb = new StringBuffer();
		sb.append("update T_City ");
		sb.append("set array = case ");
	    sb.append("		when cityId = :cityId then (select array from T_City where cityId=:updateCityId) ");
	    sb.append("		else (select array from T_City where cityId=:cityId) ");
	    sb.append("	end ");
	    sb.append("where cityId in (:cityId,:updateCityId)");
	    
	    getCurrentSession().createSQLQuery(sb.toString()).setInteger("cityId", cityId).setInteger("updateCityId", updateCityId).executeUpdate();
	}
}
