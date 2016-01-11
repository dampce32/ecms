package com.csit.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.csit.dao.SubjectDAO;
import com.csit.model.Subject;
import com.csit.util.PageUtil;
import com.csit.vo.GobelConstants;
/**
 * 
 * @Description:试题Dao实现
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-4-26
 * @author jcf
 * @vesion 1.0
 */
@Repository
public class SubjectDAOImpl extends BaseDAOImpl<Subject, Integer> implements SubjectDAO {

	@Override
	public Long getTotalCount(Subject model) {
		Criteria criteria = getCurrentSession().createCriteria(Subject.class);
		if(StringUtils.isNotEmpty(model.getSubjectType())){
			criteria.add(Restrictions.eq("subjectType", model.getSubjectType()));
		}
		if(model.getGroup()!=null&&model.getGroup().getGroupId()!=null){
			criteria.add(Restrictions.eq("group.groupId", model.getGroup().getGroupId()));
		}
		if(model.getPublishTeacher()!=null&&model.getPublishTeacher().getTeacherId()!=null){
			criteria.add(Restrictions.eq("publishTeacher.teacherId", model.getPublishTeacher().getTeacherId()));
		}
		if(model!=null&&model.getStatus()!=null){
			criteria.add(Restrictions.eq("status", model.getStatus()));
		}
		if(model!=null&&StringUtils.isNotEmpty(model.getDifficulty())){
			criteria.add(Restrictions.eq("difficulty", model.getDifficulty()));
		}
		criteria.setProjection(Projections.rowCount());
		return new Long(criteria.uniqueResult().toString());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Subject> query(Subject model, Integer page, Integer rows) {
		Criteria criteria = getCurrentSession().createCriteria(Subject.class);

		criteria.createAlias("publishTeacher", "publishTeacher", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("group", "group", CriteriaSpecification.LEFT_JOIN);
		if(StringUtils.isNotEmpty(model.getSubjectType())){
			criteria.add(Restrictions.eq("subjectType", model.getSubjectType()));
		}
		if(model.getGroup()!=null&&model.getGroup().getGroupId()!=null){
			criteria.add(Restrictions.eq("group.groupId", model.getGroup().getGroupId()));
		}
		if(model.getPublishTeacher()!=null&&model.getPublishTeacher().getTeacherId()!=null){
			criteria.add(Restrictions.eq("publishTeacher.teacherId", model.getPublishTeacher().getTeacherId()));
		}
		if(model!=null&&model.getStatus()!=null){
			criteria.add(Restrictions.eq("status", model.getStatus()));
		}
		if(model!=null&&StringUtils.isNotEmpty(model.getDifficulty())){
			criteria.add(Restrictions.eq("difficulty", model.getDifficulty()));
		}
		if (rows == null || rows < 0) {
			rows = GobelConstants.DEFAULTPAGESIZE;
		}

		criteria.setFirstResult(PageUtil.getPageBegin(page, rows));
		criteria.setMaxResults(rows);

		criteria.addOrder(Order.desc("subjectId"));
		return criteria.list();
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.SubjectDAO#selectPaperBigSmall(com.csit.model.Subject, java.lang.Integer[], java.lang.Integer, java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Subject> selectPaperBigSmall(Subject model, Integer[] idArray,
			Integer page, Integer rows) {
		Criteria criteria = getCurrentSession().createCriteria(Subject.class);

		criteria.createAlias("publishTeacher", "publishTeacher", CriteriaSpecification.LEFT_JOIN);
		if(StringUtils.isNotEmpty(model.getSubjectType())){
			criteria.add(Restrictions.eq("subjectType", model.getSubjectType()));
		}
		if(model.getGroup()!=null&&model.getGroup().getGroupId()!=null){
			criteria.add(Restrictions.eq("group.groupId", model.getGroup().getGroupId()));
		}
		if(model.getPublishTeacher()!=null&&model.getPublishTeacher().getTeacherId()!=null){
			criteria.add(Restrictions.eq("publishTeacher.teacherId", model.getPublishTeacher().getTeacherId()));
		}
		if(model!=null&&StringUtils.isNotEmpty(model.getDifficulty())){
			criteria.add(Restrictions.eq("difficulty", model.getDifficulty()));
		}
		criteria.add(Restrictions.eq("status", 1));
		if(idArray.length>0){
			criteria.add(Restrictions.not(Restrictions.in("subjectId", idArray)));
		}
		if (rows == null || rows < 0) {
			rows = GobelConstants.DEFAULTPAGESIZE;
		}

		criteria.setFirstResult(PageUtil.getPageBegin(page, rows));
		criteria.setMaxResults(rows);

		criteria.addOrder(Order.asc("subjectId"));
		return criteria.list();
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.SubjectDAO#getTotalCountSelectPaperBigSmall(com.csit.model.Subject, java.lang.Integer[])
	 */
	@Override
	public Long getTotalCountSelectPaperBigSmall(Subject model, Integer[] idArray) {
		Criteria criteria = getCurrentSession().createCriteria(Subject.class);

		criteria.createAlias("publishTeacher", "publishTeacher", CriteriaSpecification.LEFT_JOIN);
		if(StringUtils.isNotEmpty(model.getSubjectType())){
			criteria.add(Restrictions.eq("subjectType", model.getSubjectType()));
		}
		if(model.getGroup()!=null&&model.getGroup().getGroupId()!=null){
			criteria.add(Restrictions.eq("group.groupId", model.getGroup().getGroupId()));
		}
		if(model.getPublishTeacher()!=null&&model.getPublishTeacher().getTeacherId()!=null){
			criteria.add(Restrictions.eq("publishTeacher.teacherId", model.getPublishTeacher().getTeacherId()));
		}
		if(model!=null&&StringUtils.isNotEmpty(model.getDifficulty())){
			criteria.add(Restrictions.eq("difficulty", model.getDifficulty()));
		}
		criteria.add(Restrictions.eq("status", 1));
		if(idArray.length>0){
			criteria.add(Restrictions.not(Restrictions.in("subjectId", idArray)));
		}
		criteria.setProjection(Projections.rowCount());
		return new Long(criteria.uniqueResult().toString());
	}

}
