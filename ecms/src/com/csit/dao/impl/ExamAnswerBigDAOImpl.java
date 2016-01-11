package com.csit.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.csit.dao.ExamAnswerBigDAO;
import com.csit.model.ExamAnswer;
import com.csit.model.ExamAnswerBig;
import com.csit.model.ExamAnswerBigId;
/**
 * @Description:答卷DAO实现类
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-9
 * @Author lys
 */
@Repository
public class ExamAnswerBigDAOImpl extends
		BaseDAOImpl<ExamAnswerBig, ExamAnswerBigId> implements ExamAnswerBigDAO {
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.ExamAnswerBigDAO#query(com.csit.model.ExamAnswer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ExamAnswerBig> query(ExamAnswer model) {
		Criteria criteria = getCurrentSession().createCriteria(ExamAnswerBig.class);
		criteria.add(Restrictions.eq("examAnswer", model));
		criteria.addOrder(Order.asc("array"));
		return criteria.list();
	}

}
