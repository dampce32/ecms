package com.csit.dao.impl;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.csit.dao.TempPaperBigDAO;
import com.csit.model.TempPaperBig;
import com.csit.model.TempPaperBigId;
/**
 * @Description:临时试卷大题DAO实现类
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-4-28
 * @Author lys
 */
@Repository
public class TempPaperBigDAOImpl extends
		BaseDAOImpl<TempPaperBig, TempPaperBigId> implements TempPaperBigDAO {
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.TempPaperBigDAO#query(java.lang.Integer, java.lang.Integer, java.sql.Timestamp)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<TempPaperBig> query(Integer paperId, Integer teacherId,
			Timestamp operateTime) {
		Criteria criteria = getCurrentSession().createCriteria(TempPaperBig.class);
		criteria.add(Restrictions.eq("id.paperId",paperId));
		criteria.add(Restrictions.eq("id.teacherId",teacherId));
		criteria.add(Restrictions.eq("id.operateTime",operateTime));
		criteria.addOrder(Order.asc("array"));
		return criteria.list();
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.TempPaperBigDAO#clearSmall(com.csit.model.TempPaperBig)
	 */
	@Override
	public void clearSmall(TempPaperBig model) {
		StringBuffer hql = new StringBuffer();
		hql.append("delete from TempPaperBigSmall where tempPaperBig = :tempPaperBig");
		getCurrentSession().createQuery(hql.toString()).setEntity("tempPaperBig", model).executeUpdate();
		
	}
	
}
