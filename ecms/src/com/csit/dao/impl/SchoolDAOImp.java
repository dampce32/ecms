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

import com.csit.dao.SchoolDAO;
import com.csit.model.School;
import com.csit.util.PageUtil;
/**
 * @Description:学校DAO实现类
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-7-22
 * @author jcf
 * @vesion 1.0
 */
@Repository
public class SchoolDAOImp extends BaseDAOImpl<School, Integer> implements SchoolDAO {
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.SchoolDAO#query(java.lang.Integer, java.lang.Integer, com.csit.model.School)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<School> query(School model,Integer page, Integer rows) {
		Criteria criteria = getCurrentSession().createCriteria(School.class);
		criteria.createAlias("area", "area", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("area.city", "city", CriteriaSpecification.LEFT_JOIN);
		if(StringUtils.isNotEmpty(model.getSchoolName())){
			criteria.add(Restrictions.like("schoolName", model.getSchoolName(),MatchMode.ANYWHERE));
		}
		if(model!=null&&model.getStatus()!=null){
			criteria.add(Restrictions.eq("status", model.getStatus()));
		}
		criteria.setFirstResult(PageUtil.getPageBegin(page, rows));
		criteria.setMaxResults(rows);
		criteria.addOrder(Order.asc("schoolId"));
		return criteria.list();
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.SchoolDAO#count(com.csit.model.School)
	 */
	@Override
	public Long getTotalCount(School model) {
		Criteria criteria = getCurrentSession().createCriteria(School.class);
		if(StringUtils.isNotEmpty(model.getSchoolName())){
			criteria.add(Restrictions.like("schoolName", model.getSchoolName(),MatchMode.ANYWHERE));
		}
		if(model!=null&&model.getStatus()!=null){
			criteria.add(Restrictions.eq("status", model.getStatus()));
		}
		criteria.setProjection(Projections.rowCount());
		return new Long(criteria.uniqueResult().toString());
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<School> queryCombobox(School model) {
		Criteria criteria = getCurrentSession().createCriteria(School.class);
		criteria.add(Restrictions.eq("area", model.getArea()));
		criteria.add(Restrictions.eq("status", 1));
		return criteria.list();
	}

}
