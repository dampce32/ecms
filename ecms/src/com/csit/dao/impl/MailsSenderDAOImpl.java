package com.csit.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.csit.dao.MailsSenderDAO;
import com.csit.model.MailsHistory;
import com.csit.model.MailsSender;
import com.csit.util.PageUtil;

/**
 * 
 * @Description: 邮件发送DAO实现类
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-7-9
 * @author yk
 * @vesion 1.0
 */
@Repository
public class MailsSenderDAOImpl extends BaseDAOImpl<MailsSender, Integer>
		implements MailsSenderDAO {

	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.MailsSenderDAO#querySender()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<MailsSender> querySender() {
		Criteria criteria = getCurrentSession().createCriteria(MailsSender.class);
		
		criteria.add(Restrictions.le("sendTime", new Date()));
		criteria.add(Restrictions.lt("errorCount", 3));
		
		return criteria.list();
	}

	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.MailsSenderDAO#sendSuccess(com.csit.model.MailsSender, com.csit.model.MailsHistory)
	 */
	@Override
	public void sendSuccess(MailsSender mailsSender, MailsHistory mailsHistory) {
		Assert.notNull(mailsSender, "messageSend is required");
		Assert.notNull(mailsHistory, "historyMessage is required");
		Session session =hibernateTemplate.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		//先把数据移到历史记录
		session.save(mailsHistory);
		//删除当前数据
		session.delete(mailsSender);
		tx.commit();
		session.close();
	}

	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.MailsSenderDAO#updateErrorCount(java.lang.Integer)
	 */
	@Override
	public void updateErrorCount(Integer mailsSenderId) {
		Assert.notNull(mailsSenderId, "mailsSenderId is required");
		String hql ="update MailsSender set errorCount=errorCount+1 where mailsSenderId=:mailsSenderId";
		Session session =hibernateTemplate.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		Query query = session.createQuery(hql);
		query.setInteger("mailsSenderId", mailsSenderId);
		query.executeUpdate();
		tx.commit();
		session.close();
	}

	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.MailsSenderDAO#query(com.csit.model.MailsSender, java.lang.Integer, java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<MailsSender> query(MailsSender model, Integer page, Integer rows) {
		Criteria criteria = getCurrentSession().createCriteria(MailsSender.class);
		
		criteria.createAlias("teacher", "teacher", CriteriaSpecification.LEFT_JOIN);
		
		criteria.add(Restrictions.eq("teacher", model.getTeacher()));
		
		if(model!=null&&model.getErrorCount()==1){
			criteria.add(Restrictions.ge("errorCount", 3));
		}else if(model!=null&&model.getErrorCount()==0){
			criteria.add(Restrictions.lt("errorCount", 3));
		}
		
		criteria.setFirstResult(PageUtil.getPageBegin(page, rows));
		criteria.setMaxResults(rows);
		
		criteria.addOrder(Order.desc("sendTime"));
		return criteria.list();
	}

	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.MailsSenderDAO#getTotalCount(com.csit.model.MailsSender)
	 */
	@Override
	public Long getTotalCount(MailsSender model) {
		Criteria criteria = getCurrentSession().createCriteria(MailsSender.class);
		
		criteria.createAlias("teacher", "teacher", CriteriaSpecification.LEFT_JOIN);
		
		criteria.add(Restrictions.eq("teacher", model.getTeacher()));
		
		if(model!=null&&model.getErrorCount()==1){
			criteria.add(Restrictions.ge("errorCount", 3));
		}else if(model!=null&&model.getErrorCount()==0){
			criteria.add(Restrictions.lt("errorCount", 3));
		}
		
		criteria.setProjection(Projections.rowCount());
		
		return new Long(criteria.uniqueResult().toString());
	}

	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.MailsSenderDAO#getMails(com.csit.model.MailsSender)
	 */
	@SuppressWarnings("unchecked")
	public List<String> getMails(MailsSender MailsSender) {
		Assert.notNull(MailsSender.getReceiveIDs(), "receiveIDs is required");
		StringBuilder hql = new StringBuilder();
		hql.append("select c.email from T_MailsSender a ");
		hql.append("outer apply dbo.F_SplitIds(a.ReceiveIDs)  b ");
		hql.append("left join T_Contacts c on b.ID = c.contactsId ");
		hql.append("where a.mailsSenderId=:mailsSenderId");
		
		Query query = getCurrentSession().createSQLQuery(hql.toString());
		query.setInteger("mailsSenderId", MailsSender.getMailsSenderId());
		
		return query.list();
	}

	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.MailsSenderDAO#queryReceiver(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> queryReceiver(String receiveIDs) {
		Assert.notNull(receiveIDs, "receiveIDs is required");
		StringBuilder hql = new StringBuilder();
		hql.append("select b.name as name,b.email as email from dbo.F_SplitIds(:receiveIDs) a ");
		hql.append("left join T_Contacts b on a.ID = b.contactsId ");
		
		Query query = getCurrentSession().createSQLQuery(hql.toString());
		query.setString("receiveIDs", receiveIDs);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return query.list();
	}
}
