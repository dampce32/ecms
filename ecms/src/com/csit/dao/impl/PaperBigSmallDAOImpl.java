package com.csit.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.csit.dao.PaperBigSmallDAO;
import com.csit.model.PaperBig;
import com.csit.model.PaperBigSmall;
import com.csit.model.PaperBigSmallId;
/**
 * @Description:试卷小题DAO实现类
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-5-8
 * @Author lys
 */
@Repository
public class PaperBigSmallDAOImpl extends
		BaseDAOImpl<PaperBigSmall, PaperBigSmallId> implements PaperBigSmallDAO {
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.PaperBigSmallDAO#query(com.csit.model.PaperBig)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PaperBigSmall> query(PaperBig paperBig) {
		Criteria criteria = getCurrentSession().createCriteria(PaperBigSmall.class);
		criteria.createAlias("subject", "subject", CriteriaSpecification.LEFT_JOIN);
		criteria.add(Restrictions.eq("paperBig", paperBig));
		criteria.addOrder(Order.asc("array"));
		return criteria.list();
	}

}
