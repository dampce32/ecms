package com.csit.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.csit.dao.SchoolGradeClazzDAO;
import com.csit.model.SchoolGradeClazz;
/**
 * @Description:学校年级班级DAO实现类
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-7-23
 * @author jcf
 * @vesion 1.0
 */
@Repository
public class SchoolGradeClazzDAOImp extends BaseDAOImpl<SchoolGradeClazz, Integer> implements SchoolGradeClazzDAO {
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.CompetitionDAO#query(java.lang.Integer, java.lang.Integer, com.csit.model.Competition)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SchoolGradeClazz> query(SchoolGradeClazz model) {
		Criteria criteria = getCurrentSession().createCriteria(SchoolGradeClazz.class);
		criteria.createAlias("clazz", "clazz",CriteriaSpecification.LEFT_JOIN);
		criteria.add(Restrictions.eq("schoolGrade", model.getSchoolGrade()));
		criteria.addOrder(Order.asc("array"));
		return criteria.list();
	}
	@Override
	public Integer getMaxArray() {
		Criteria criteria = getCurrentSession().createCriteria(SchoolGradeClazz.class);
		criteria.setProjection(Projections.max("array"));
		return criteria.uniqueResult()==null?0:(Integer)criteria.uniqueResult();
	}
	@Override
	public void updateArray(Integer schoolGradeClazzId, Integer updateSchoolGradeClazzId) {
		StringBuffer sb = new StringBuffer();
		sb.append("update T_SchoolGradeClazz	");
		sb.append("set Array = case ");
	    sb.append("		when SchoolGradeClazzID = :schoolGradeClazzId then (select Array from T_SchoolGradeClazz where  SchoolGradeClazzID=:updateSchoolGradeClazzId) ");
	    sb.append("		else (select Array from T_SchoolGradeClazz where  SchoolGradeClazzID=:schoolGradeClazzId) ");
	    sb.append("	end ");
	    sb.append("where SchoolGradeClazzID in (:schoolGradeClazzId,:updateSchoolGradeClazzId)");
	    
	    getCurrentSession().createSQLQuery(sb.toString()).setInteger("schoolGradeClazzId", schoolGradeClazzId).setInteger("updateSchoolGradeClazzId", updateSchoolGradeClazzId).executeUpdate();
		
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.SchoolGradeClazzDAO#queryCombobox(java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SchoolGradeClazz> queryCombobox(Integer schoolGradeId) {
		Criteria criteria = getCurrentSession().createCriteria(SchoolGradeClazz.class);
		criteria.createAlias("clazz", "clazz",CriteriaSpecification.LEFT_JOIN);
		
		criteria.add(Restrictions.eq("clazz.status", 1));
		criteria.add(Restrictions.eq("schoolGrade.schoolGradeId", schoolGradeId));
		
		criteria.addOrder(Order.asc("array"));
		
		return criteria.list();
	}

}
