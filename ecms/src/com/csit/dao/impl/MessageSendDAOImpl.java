package com.csit.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.csit.dao.MessageSendDAO;
import com.csit.model.HistoryMessage;
import com.csit.model.MessageSend;

/**
 * 
 * @Description: 消息发送dao实现类
 * @Copyright: 福州骏华信息有限公司 (c)2013 
 * @Created Date : 2013-6-25
 * @author longweier
 * @vesion 1.0
 */
@Repository
public class MessageSendDAOImpl extends BaseDAOImpl<MessageSend, Integer> implements MessageSendDAO {

	@SuppressWarnings("unchecked")
	public List<String> getMobiles(MessageSend messageSend) {
		Assert.notNull(messageSend.getReceiveIDs(), "receiveIDs is required");
		StringBuilder hql = new StringBuilder();
		hql.append("select c.mobilePhone from T_MessageSend a ");
		hql.append("outer apply dbo.F_SplitIds(a.ReceiveIDs)  b ");
		hql.append("left join T_Contacts c on b.ID = c.contactsId ");
		hql.append(" where a.messageId=:messageId ");
		
		Query query = getCurrentSession().createSQLQuery(hql.toString());
		query.setInteger("messageId", messageSend.getMessageId());
		
		return query.list();
	}

	@SuppressWarnings("unchecked")
	public List<MessageSend> querySend() {
		Criteria criteria = getCurrentSession().createCriteria(MessageSend.class);
		
		criteria.add(Restrictions.le("sendTime", new Date()));
		criteria.add(Restrictions.lt("errorCount", 10));
		
		return criteria.list();
	}

	public void updateErrorCount(Integer messageId) {
		Assert.notNull(messageId, "messageId is required");
		String hql ="update MessageSend set errorCount=errorCount+1 where messageId=:messageId";
		Session session =hibernateTemplate.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		Query query = session.createQuery(hql);
		query.setInteger("messageId", messageId);
		query.executeUpdate();
		tx.commit();
		session.close();
	}

	public void sendSuccess(MessageSend messageSend,HistoryMessage historyMessage) {
		Assert.notNull(messageSend, "messageSend is required");
		Assert.notNull(historyMessage, "historyMessage is required");
		Session session =hibernateTemplate.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		//先把数据移到历史记录
		session.save(historyMessage);
		//删除当前数据
		session.delete(messageSend);
		tx.commit();
		session.close();
	}

	@SuppressWarnings("unchecked")
	public List<MessageSend> queryFailed(MessageSend messageSend) {
		Assert.notNull(messageSend, "messageSend is required");
		Assert.notNull(messageSend.getTeacher(), "teacher is required");
		Criteria criteria = getCurrentSession().createCriteria(MessageSend.class);
		criteria.add(Restrictions.eq("teacher", messageSend.getTeacher()));
		criteria.add(Restrictions.eq("messageType", 1));
		
		if(messageSend!=null&&messageSend.getErrorCount()==1){
			criteria.add(Restrictions.ge("errorCount", 10));
		}else if(messageSend!=null&&messageSend.getErrorCount()==0){
			criteria.add(Restrictions.lt("errorCount", 10));
		}
		
		
		criteria.addOrder(Order.desc("sendTime"));
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> queryReceiver(String receiveIDs) {
		Assert.notNull(receiveIDs, "receiveIDs is required");
		StringBuilder hql = new StringBuilder();
		hql.append("select b.name as name,b.mobilePhone as mobilePhone from dbo.F_SplitIds(:receiveIDs) a ");
		hql.append("left join T_Contacts b on a.ID = b.mobilePhone ");
		
		Query query = getCurrentSession().createSQLQuery(hql.toString());
		query.setString("receiveIDs", receiveIDs);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MessageSend> queryPromptFail() {
		Criteria criteria = getCurrentSession().createCriteria(MessageSend.class);
		
		criteria.add(Restrictions.or(Restrictions.eq("messageType", 2),Restrictions.eq("messageType", 3)));
		
		criteria.add(Restrictions.ge("errorCount", 10));
		
		criteria.addOrder(Order.desc("sendTime"));
		return criteria.list();
	}

	
}
