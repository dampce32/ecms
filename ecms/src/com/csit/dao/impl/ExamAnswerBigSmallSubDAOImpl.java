package com.csit.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.csit.dao.ExamAnswerBigSmallSubDAO;
import com.csit.model.ExamAnswerBigSmall;
import com.csit.model.ExamAnswerBigSmallSub;
import com.csit.model.ExamAnswerBigSmallSubId;
/**
 * @Description: 答卷小题子小题DAO实现类
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-9
 * @Author lys
 */
@Repository
public class ExamAnswerBigSmallSubDAOImpl extends
		BaseDAOImpl<ExamAnswerBigSmallSub, ExamAnswerBigSmallSubId> implements
		ExamAnswerBigSmallSubDAO {
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.ExamAnswerBigSmallSubDAO#query(com.csit.model.ExamAnswerBigSmall)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ExamAnswerBigSmallSub> query(
			ExamAnswerBigSmall examAnswerBigSmall) {
		Criteria criteria = getCurrentSession().createCriteria(ExamAnswerBigSmallSub.class);
		criteria.add(Restrictions.eq("examAnswerBigSmall", examAnswerBigSmall));
		criteria.addOrder(Order.asc("id.subId"));
		return criteria.list();
	}

}
