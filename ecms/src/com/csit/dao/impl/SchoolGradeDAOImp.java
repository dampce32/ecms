package com.csit.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.csit.dao.SchoolGradeDAO;
import com.csit.model.SchoolGrade;
/**
 * @Description:学校年级DAO实现类
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-7-22
 * @author jcf
 * @vesion 1.0
 */
@Repository
public class SchoolGradeDAOImp extends BaseDAOImpl<SchoolGrade, Integer> implements SchoolGradeDAO {
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.CompetitionDAO#query(java.lang.Integer, java.lang.Integer, com.csit.model.Competition)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SchoolGrade> query(SchoolGrade model) {
		Criteria criteria = getCurrentSession().createCriteria(SchoolGrade.class);
		criteria.createAlias("grade", "grade",CriteriaSpecification.LEFT_JOIN);
		if(model!=null&&model.getSchool()!=null){
			criteria.add(Restrictions.eq("school", model.getSchool()));
		}
		criteria.addOrder(Order.asc("array"));
		return criteria.list();
	}
	@Override
	public Integer getMaxArray(Integer schoolId) {
		Criteria criteria = getCurrentSession().createCriteria(SchoolGrade.class);
		criteria.add(Restrictions.eq("school.schoolId", schoolId));
		criteria.setProjection(Projections.max("array"));
		return criteria.uniqueResult()==null?0:(Integer)criteria.uniqueResult();
	}
	@Override
	public void updateArray(Integer schoolGradeId,Integer updateSchoolGradeId) {
		StringBuffer sb = new StringBuffer();
		sb.append("update T_SchoolGrade	");
		sb.append("set Array = case ");
	    sb.append("		when SchoolGradeID = :schoolGradeId then (select Array from T_SchoolGrade where  SchoolGradeID=:updateSchoolGradeId) ");
	    sb.append("		else (select Array from T_SchoolGrade where  SchoolGradeID=:schoolGradeId) ");
	    sb.append("	end ");
	    sb.append("where SchoolGradeID in (:schoolGradeId,:updateSchoolGradeId)");
	    
	    getCurrentSession().createSQLQuery(sb.toString()).setInteger("schoolGradeId", schoolGradeId).setInteger("updateSchoolGradeId", updateSchoolGradeId).executeUpdate();
		
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.SchoolGradeDAO#queryCombobox(java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SchoolGrade> queryCombobox(Integer schoolId) {
		Criteria criteria = getCurrentSession().createCriteria(SchoolGrade.class);
		criteria.createAlias("grade", "grade",CriteriaSpecification.LEFT_JOIN);
		
		criteria.add(Restrictions.eq("grade.status", 1));
		criteria.add(Restrictions.eq("school.schoolId", schoolId));
		
		criteria.addOrder(Order.asc("array"));
		
		return criteria.list();
	}
}
