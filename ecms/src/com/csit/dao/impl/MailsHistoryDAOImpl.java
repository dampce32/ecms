package com.csit.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.csit.dao.MailsHistoryDAO;
import com.csit.model.MailsHistory;
import com.csit.util.PageUtil;

/**
 * 
 * @Description: 邮件发送历史记录DAO实现类
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-7-9
 * @author yk
 * @vesion 1.0
 */
@Repository
public class MailsHistoryDAOImpl extends BaseDAOImpl<MailsHistory, Integer>
		implements MailsHistoryDAO {

	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.MailsHistoryDAO#query(com.csit.model.MailsHistory, java.lang.Integer, java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<MailsHistory> query(MailsHistory model, Integer page,
			Integer rows, Date startDate, Date endDate) {
		Criteria criteria = getCurrentSession().createCriteria(MailsHistory.class);
		
		criteria.createAlias("teacher", "teacher", CriteriaSpecification.LEFT_JOIN);
		
		criteria.add(Restrictions.eq("teacher", model.getTeacher()));

		if(startDate!=null){
			criteria.add(Restrictions.ge("sendTime", startDate));
		}
		if(endDate!=null){
			criteria.add(Restrictions.le("sendTime", endDate));
		}
		
		criteria.setFirstResult(PageUtil.getPageBegin(page, rows));
		criteria.setMaxResults(rows);
		
		criteria.addOrder(Order.desc("sendTime"));
		return criteria.list();
	}

	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.MailsHistoryDAO#getTotalCount(com.csit.model.MailsHistory)
	 */
	@Override
	public Long getTotalCount(MailsHistory model, Date startDate, Date endDate) {
		Criteria criteria = getCurrentSession().createCriteria(MailsHistory.class);
		
		criteria.add(Restrictions.eq("teacher", model.getTeacher()));
		
		if(startDate!=null){
			criteria.add(Restrictions.ge("sendTime", startDate));
		}
		if(endDate!=null){
			criteria.add(Restrictions.le("sendTime", endDate));
		}
		
		criteria.setProjection(Projections.rowCount());
		
		return new Long(criteria.uniqueResult().toString());
	}

}
