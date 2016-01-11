package com.csit.dao.impl;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.csit.dao.TempPaperBigSmallDAO;
import com.csit.model.TempPaperBigSmall;
import com.csit.model.TempPaperBigSmallId;
/**
 * @Description:试卷小题临时表DAO实现类
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-4-30
 * @Author lys
 */
@Repository
public class TempPaperBigSmallDAOImpl extends
		BaseDAOImpl<TempPaperBigSmall, TempPaperBigSmallId> implements
		TempPaperBigSmallDAO {
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.TempPaperBigSmallDAO#query(java.lang.Integer, java.lang.Integer, java.lang.Integer, java.sql.Timestamp)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<TempPaperBigSmall> query(Integer paperId, Integer bigId,
			Integer teacherId, Timestamp operateTime) {
		Criteria criteria = getCurrentSession().createCriteria(TempPaperBigSmall.class);
		criteria.add(Restrictions.eq("id.paperId",paperId));
		criteria.add(Restrictions.eq("id.bigId",bigId));
		criteria.add(Restrictions.eq("id.teacherId",teacherId));
		criteria.add(Restrictions.eq("id.operateTime",operateTime));
		criteria.addOrder(Order.asc("array"));
		return criteria.list();
	}

}
