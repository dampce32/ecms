package com.csit.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.csit.dao.ExamAnswerBigSmallDAO;
import com.csit.model.ExamAnswerBig;
import com.csit.model.ExamAnswerBigSmall;
import com.csit.model.ExamAnswerBigSmallId;
/**
 * @Description:答卷小题DAO实现类
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-9
 * @Author lys
 */
@Repository
public class ExamAnswerBigSmallDAOImpl extends
		BaseDAOImpl<ExamAnswerBigSmall, ExamAnswerBigSmallId> implements
		ExamAnswerBigSmallDAO {
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.ExamAnswerBigSmallDAO#query(com.csit.model.ExamAnswerBig)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ExamAnswerBigSmall> query(ExamAnswerBig examAnswerBig) {
		Criteria criteria = getCurrentSession().createCriteria(ExamAnswerBigSmall.class);
		criteria.add(Restrictions.eq("examAnswerBig", examAnswerBig));
		criteria.addOrder(Order.asc("array"));
		return criteria.list();
	}

}
