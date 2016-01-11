package com.csit.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.csit.dao.PaperBigDAO;
import com.csit.model.Paper;
import com.csit.model.PaperBig;
import com.csit.model.PaperBigId;
/**
 * @Description:试卷大题DAO实现类
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-5-8
 * @Author lys
 */
@Repository
public class PaperBigDAOImpl extends BaseDAOImpl<PaperBig, PaperBigId>
		implements PaperBigDAO {
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.PaperBigDAO#query(com.csit.model.Paper)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PaperBig> query(Paper model) {
		Criteria criteria = getCurrentSession().createCriteria(PaperBig.class);
		criteria.add(Restrictions.eq("paper", model));
		criteria.addOrder(Order.asc("array"));
		return criteria.list();
	}

}
