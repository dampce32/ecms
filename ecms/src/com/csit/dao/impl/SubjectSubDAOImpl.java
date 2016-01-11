package com.csit.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.csit.dao.SubjectSubDAO;
import com.csit.model.Subject;
import com.csit.model.SubjectSub;
/**
 * 
 * @Description: 试题子表DAO实现类
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-5-6
 * @author yk
 * @vesion 1.0
 */
@Repository
public class SubjectSubDAOImpl extends BaseDAOImpl<SubjectSub, Integer>
		implements SubjectSubDAO {

	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.SubjectSubDAO#query(com.csit.model.SubjectSub, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List<SubjectSub> query(SubjectSub model) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Integer subjectId) {
		List<SubjectSub> subjectSubList=this.query("subject.subjectId", subjectId);
		super.hibernateTemplate.deleteAll(subjectSubList);
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.SubjectSubDAO#query(com.csit.model.Subject)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SubjectSub> query(Subject subject) {
		Criteria criteria = getCurrentSession().createCriteria(SubjectSub.class);
		criteria.add(Restrictions.eq("subject", subject));
		criteria.addOrder(Order.asc("id.subId"));
		return criteria.list();
	}

}
