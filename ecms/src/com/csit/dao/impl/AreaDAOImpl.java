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

import com.csit.dao.AreaDAO;
import com.csit.model.Area;
import com.csit.model.City;
import com.csit.util.PageUtil;
/**
 * @Description:区县DAO实现类
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-5-29
 * @Author lys
 */
@Repository
public class AreaDAOImpl extends BaseDAOImpl<Area, Integer> implements AreaDAO {
	
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.AreaDAO#query(com.csit.model.Area, java.lang.Integer, java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Area> query(Area model, Integer page, Integer rows) {
		Criteria criteria = getCurrentSession().createCriteria(Area.class);
		criteria.createAlias("province", "province", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("city", "city", CriteriaSpecification.LEFT_JOIN);
		if(model!=null&&model.getProvince()!=null&&model.getProvince().getProvinceId()!=null){
			criteria.add(Restrictions.eq("province.provinceId", model.getProvince().getProvinceId()));
		}
		if(model!=null&&model.getCity()!=null&&model.getCity().getCityId()!=null){
			criteria.add(Restrictions.eq("city", model.getCity()));
		}
		if(StringUtils.isNotEmpty(model.getAreaCode())){
			criteria.add(Restrictions.like("areaCode", model.getAreaCode(),MatchMode.ANYWHERE));
		} 
		if(StringUtils.isNotEmpty(model.getAreaName())){
			criteria.add(Restrictions.like("areaName", model.getAreaName(),MatchMode.ANYWHERE));
		} 
		criteria.setFirstResult(PageUtil.getPageBegin(page, rows));
		criteria.setMaxResults(rows);
		criteria.addOrder(Order.asc("array"));
		return criteria.list();
	}
	
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.AreaDAO#getTotalCount(com.csit.model.Area)
	 */
	@Override
	public Long getTotalCount(Area model) {
		Criteria criteria = getCurrentSession().createCriteria(Area.class);
		if(model!=null&& model.getAreaId()!=null&& model.getProvince().getProvinceId()!=null){
			criteria.add(Restrictions.eq("province.provinceId", model.getProvince().getProvinceId()));
		}
		if(model!=null&&model.getCity()!=null&&model.getCity().getCityId()!=null){
			criteria.add(Restrictions.eq("city", model.getCity()));
		}
		if(StringUtils.isNotEmpty(model.getAreaCode())){
			criteria.add(Restrictions.like("areaCode", model.getAreaCode(),MatchMode.ANYWHERE));
		} 
		if(StringUtils.isNotEmpty(model.getAreaName())){
			criteria.add(Restrictions.like("areaName", model.getAreaName(),MatchMode.ANYWHERE));
		} 
		criteria.setProjection(Projections.rowCount());
		return new Long(criteria.uniqueResult().toString());
	}
	
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.AreaDAO#queryCombobox(com.csit.model.City)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Area> queryCombobox(City city) {
		Criteria criteria = getCurrentSession().createCriteria(Area.class);
		criteria.add(Restrictions.eq("city", city));
		criteria.addOrder(Order.asc("array"));
		return criteria.list();
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.AreaDAO#getMaxArray()
	 */
	@Override
	public Integer getMaxArray() {
		Criteria criteria = getCurrentSession().createCriteria(Area.class);
		criteria.setProjection(Projections.max("array"));
		return criteria.uniqueResult()==null?0:(Integer)criteria.uniqueResult();
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.AreaDAO#updateArray(java.lang.String, java.lang.String)
	 */
	@Override
	public void updateArray(Integer areaId, Integer updateAreaId) {
		StringBuffer sb = new StringBuffer();
		sb.append("update T_Area ");
		sb.append("set array = case ");
	    sb.append("		when areaId = :areaId then (select array from T_Area where areaId=:updateAreaId) ");
	    sb.append("		else (select array from T_Area where areaId=:areaId) ");
	    sb.append("	end ");
	    sb.append("where areaId in (:areaId,:updateAreaId)");
	    
	    getCurrentSession().createSQLQuery(sb.toString()).setInteger("areaId", areaId).setInteger("updateAreaId", updateAreaId).executeUpdate();
	}

}
