package com.csit.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.csit.dao.RoleDAO;
import com.csit.model.Role;
import com.csit.util.PageUtil;
/**
 * @Description:角色DAO实现类
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-4-17
 * @Author lys
 */
@Repository
public class RoleDAOImp extends BaseDAOImpl<Role, Integer> implements RoleDAO {
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.RoleDAO#query(java.lang.Integer, java.lang.Integer, com.csit.model.Role)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Role> query(Integer page, Integer rows, Role model) {
		Criteria criteria = getCurrentSession().createCriteria(Role.class);
		if(StringUtils.isNotEmpty(model.getRoleCode())){
			criteria.add(Restrictions.like("roleCode", model.getRoleCode(),MatchMode.ANYWHERE));
		} 
		if(StringUtils.isNotEmpty(model.getRoleName())){
			criteria.add(Restrictions.like("roleName", model.getRoleName(),MatchMode.ANYWHERE));
		} 
		criteria.setFirstResult(PageUtil.getPageBegin(page, rows));
		criteria.setMaxResults(rows);
		return criteria.list();
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.RoleDAO#count(com.csit.model.Role)
	 */
	@Override
	public Long count(Role model) {
		Criteria criteria = getCurrentSession().createCriteria(Role.class);
		if(StringUtils.isNotEmpty(model.getRoleCode())){
			criteria.add(Restrictions.like("roleCode", model.getRoleCode(),MatchMode.ANYWHERE));
		} 
		if(StringUtils.isNotEmpty(model.getRoleName())){
			criteria.add(Restrictions.like("roleName", model.getRoleName(),MatchMode.ANYWHERE));
		} 
		criteria.setProjection(Projections.rowCount());
		return new Long(criteria.uniqueResult().toString());
	}

}
