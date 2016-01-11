package com.csit.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.csit.dao.StudentFamilyMemberDAO;
import com.csit.model.StudentFamilyMember;
import com.csit.util.PageUtil;
/**
 * 
 * @Description:  学生家庭成员DAO实现类
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-5-31
 * @author yk
 * @vesion 1.0
 */
@Repository
public class StudentFamilyMemberDAOImpl extends
		BaseDAOImpl<StudentFamilyMember, Integer> implements
		StudentFamilyMemberDAO {

	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.StudentFamilyMemberDAO#query(com.csit.model.StudentFamilyMember, java.lang.Integer, java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<StudentFamilyMember> query(StudentFamilyMember model,
			Integer page, Integer rows) {
		
		Criteria criteria = getCurrentSession().createCriteria(StudentFamilyMember.class);
		
		criteria.add(Restrictions.eq("student.studentId", model.getStudent().getStudentId()));
		
		criteria.setFirstResult(PageUtil.getPageBegin(page, rows));
		criteria.setMaxResults(rows);
		
		criteria.addOrder(Order.asc("competitionFamilyMemberId"));
		return criteria.list();
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.StudentFamilyMemberDAO#getTotalCount(com.csit.model.StudentFamilyMember)
	 */
	@Override
	public Long getTotalCount(StudentFamilyMember model) {
		
		Criteria criteria = getCurrentSession().createCriteria(StudentFamilyMember.class);
		
		criteria.setProjection(Projections.rowCount());
		
		return new Long(criteria.uniqueResult().toString());
	}

}
