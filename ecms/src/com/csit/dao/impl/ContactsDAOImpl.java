package com.csit.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.csit.dao.ContactsDAO;
import com.csit.model.Contacts;
import com.csit.util.PageUtil;

/**
 * 
 * @Description: 通讯录dao实现类
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-24
 * @author longweier
 * @vesion 1.0
 */
@Repository
public class ContactsDAOImpl extends BaseDAOImpl<Contacts, Integer> implements ContactsDAO {

	public Long getTotalCount(Contacts model) {
		Assert.notNull(model.getTeacher(), "teacher is required");
		Criteria criteria = getCurrentSession().createCriteria(Contacts.class);
		criteria.add(Restrictions.eq("teacher", model.getTeacher()));
		criteria.add(Restrictions.eq("visible", 1));
		if(StringUtils.isNotEmpty(model.getName())){
			criteria.add(Restrictions.like("name", model.getName(),MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(model.getMobilePhone())){
			criteria.add(Restrictions.like("mobilePhone", model.getMobilePhone(),MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(model.getEmail())){
			criteria.add(Restrictions.like("email", model.getEmail(),MatchMode.ANYWHERE));
		}
		criteria.setProjection(Projections.rowCount());
		return new Long(criteria.uniqueResult().toString());
	}

	@SuppressWarnings("unchecked")
	public List<Contacts> query(Contacts model, Integer page, Integer rows) {
		Assert.notNull(model.getTeacher(), "teacher is required");
		Criteria criteria = getCurrentSession().createCriteria(Contacts.class);
		criteria.add(Restrictions.eq("teacher", model.getTeacher()));
		criteria.add(Restrictions.eq("visible", 1));
		if(StringUtils.isNotEmpty(model.getName())){
			criteria.add(Restrictions.like("name", model.getName(),MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(model.getMobilePhone())){
			criteria.add(Restrictions.like("mobilePhone", model.getMobilePhone(),MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(model.getEmail())){
			criteria.add(Restrictions.like("email", model.getEmail(),MatchMode.ANYWHERE));
		}
		criteria.setFirstResult(PageUtil.getPageBegin(page, rows));
		criteria.setMaxResults(rows);
		criteria.addOrder(Order.asc("name"));
		return criteria.list();
	}

	public Contacts getContacts(Contacts model) {
		Assert.notNull(model.getTeacher(), "teacher is required");
		Criteria criteria = getCurrentSession().createCriteria(Contacts.class);
		criteria.add(Restrictions.eq("teacher", model.getTeacher()));
		criteria.add(Restrictions.eq("mobilePhone", model.getMobilePhone()));
		
		criteria.setFirstResult(0).setMaxResults(1);
		
		return (Contacts) criteria.uniqueResult();
	}

}
