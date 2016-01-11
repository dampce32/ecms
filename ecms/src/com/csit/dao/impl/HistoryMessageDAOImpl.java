package com.csit.dao.impl;

import java.text.ParseException;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.csit.dao.HistoryMessageDAO;
import com.csit.model.HistoryMessage;
import com.csit.model.Teacher;
import com.csit.util.DateUtil;
import com.csit.util.PageUtil;

/**
 * 
 * @Description:
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-26
 * @author longweier
 * @vesion 1.0
 */
@Repository
public class HistoryMessageDAOImpl extends BaseDAOImpl<HistoryMessage, Integer> implements HistoryMessageDAO {

	@SuppressWarnings("unchecked")
	public List<String> getMobiles(HistoryMessage historyMessage) {
		Assert.notNull(historyMessage.getReceiveIDs(), "receiveIDs is required");
		StringBuilder hql = new StringBuilder();
		hql.append("select c.mobilePhone from T_HistoryMessage a ");
		hql.append("outer apply dbo.F_SplitIds(a.ReceiveIDs)  b ");
		hql.append("left join T_Contacts c on b.ID = c.contactsId ");
		hql.append(" where a.historyId=:historyId");
		
		Query query = getCurrentSession().createSQLQuery(hql.toString());
		query.setInteger("historyId", historyMessage.getHistoryId());
		return query.list();
	}

	public Long getTotalCount(String beginDate,String endDate,Teacher teacher) {
		Assert.notNull(teacher,"teacher is required");
		Criteria criteria = getCurrentSession().createCriteria(HistoryMessage.class);
		criteria.add(Restrictions.eq("teacher", teacher));
		criteria.add(Restrictions.eq("messageType", 1));
		if(beginDate!=null && !"".equals(beginDate)){
			try {
				criteria.add(Restrictions.ge("sendTime", DateUtil.toDate(beginDate)));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if(endDate!=null && !"".equals(endDate)){
			try {
				criteria.add(Restrictions.le("sendTime", DateUtil.toDate(endDate)));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		criteria.setProjection(Projections.rowCount());
		Long total = new Long(criteria.uniqueResult().toString());
		return total;
	}

	@SuppressWarnings("unchecked")
	public List<HistoryMessage> query(String beginDate,String endDate,Teacher teacher,Integer page, Integer rows) {
		Assert.notNull(teacher,"teacher is required");
		Criteria criteria = getCurrentSession().createCriteria(HistoryMessage.class);
		criteria.add(Restrictions.eq("teacher", teacher));
		criteria.add(Restrictions.eq("messageType", 1));
		if(beginDate!=null && !"".equals(beginDate)){
			try {
				criteria.add(Restrictions.ge("sendTime", DateUtil.toDate(beginDate)));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if(endDate!=null && !"".equals(endDate)){
			try {
				criteria.add(Restrictions.le("sendTime", DateUtil.toDate(endDate)));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		criteria.setFirstResult(PageUtil.getPageBegin(page, rows));
		criteria.setMaxResults(rows);
		criteria.addOrder(Order.desc("sendTime"));
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<HistoryMessage> queryPromptHistory(String beginDate,
			String endDate, Integer messageType, Integer page, Integer rows) {
		Criteria criteria = getCurrentSession().createCriteria(HistoryMessage.class);
		if(beginDate!=null && !"".equals(beginDate)){
			try {
				criteria.add(Restrictions.ge("sendTime", DateUtil.toDate(beginDate)));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if(endDate!=null && !"".equals(endDate)){
			try {
				criteria.add(Restrictions.le("sendTime", DateUtil.toDate(endDate)));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if(messageType!=null){
			criteria.add(Restrictions.eq("messageType", messageType));
		}
		criteria.setFirstResult(PageUtil.getPageBegin(page, rows));
		criteria.setMaxResults(rows);
		criteria.addOrder(Order.desc("sendTime"));
		return criteria.list();
	}

	@Override
	public Long getPromptTotalCount(String beginDate, String endDate, Integer messageType) {
		Criteria criteria = getCurrentSession().createCriteria(HistoryMessage.class);
		if(beginDate!=null && !"".equals(beginDate)){
			try {
				criteria.add(Restrictions.ge("sendTime", DateUtil.toDate(beginDate)));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if(endDate!=null && !"".equals(endDate)){
			try {
				criteria.add(Restrictions.le("sendTime", DateUtil.toDate(endDate)));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		criteria.setProjection(Projections.rowCount());
		Long total = new Long(criteria.uniqueResult().toString());
		return total;
	}

}
