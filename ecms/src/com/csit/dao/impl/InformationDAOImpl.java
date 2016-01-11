package com.csit.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.csit.dao.InformationDAO;
import com.csit.model.Information;
import com.csit.util.PageUtil;
/**
 * @Description:资讯DAO实现类
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-3
 * @Author lys
 */
@Repository
public class InformationDAOImpl extends BaseDAOImpl<Information, Integer>
		implements InformationDAO {
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.InformationDAO#getMaxArray(java.lang.String)
	 */
	@Override
	public Integer getMaxArray(String category) {
		Criteria criteria = getCurrentSession().createCriteria(Information.class);
		criteria.add(Restrictions.eq("category", category));
		criteria.setProjection(Projections.max("array"));
		Object obj = criteria.uniqueResult();
		return obj==null?0:(Integer)obj;
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.InformationDAO#query(com.csit.model.Information, java.lang.Integer, java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Information> query(Information model, Integer page, Integer rows) {
		Criteria criteria = getCurrentSession().createCriteria(Information.class);
		if(model!=null&&model.getCompetition()!=null&&model.getCompetition().getCompetitionId()!=null){
			criteria.add(Restrictions.eq("competition", model.getCompetition()));
		} 
		if(StringUtils.isNotEmpty(model.getInformationTitle())){
			criteria.add(Restrictions.like("informationTitle", model.getInformationTitle(),MatchMode.ANYWHERE));
		} 
		if(StringUtils.isNotEmpty(model.getCategory())){
			criteria.add(Restrictions.like("category", model.getCategory(),MatchMode.ANYWHERE));
		} 
		if(model!=null&&model.getStatus()!=null){
			criteria.add(Restrictions.eq("status", model.getStatus()));
		}
		criteria.setFirstResult(PageUtil.getPageBegin(page, rows));
		criteria.setMaxResults(rows);
		criteria.addOrder(Order.desc("array"));
		return criteria.list();
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.InformationDAO#getTotalCount(com.csit.model.Information)
	 */
	@Override
	public Long getTotalCount(Information model) {
		Criteria criteria = getCurrentSession().createCriteria(Information.class);
		if(model!=null&&model.getCompetition()!=null&&model.getCompetition().getCompetitionId()!=null){
			criteria.add(Restrictions.eq("competition", model.getCompetition()));
		} 
		if(StringUtils.isNotEmpty(model.getInformationTitle())){
			criteria.add(Restrictions.like("informationTitle", model.getInformationTitle(),MatchMode.ANYWHERE));
		} 
		if(StringUtils.isNotEmpty(model.getCategory())){
			criteria.add(Restrictions.like("category", model.getCategory(),MatchMode.ANYWHERE));
		} 
		if(model!=null&&model.getStatus()!=null){
			criteria.add(Restrictions.eq("status", model.getStatus()));
		}
		criteria.setProjection(Projections.rowCount());
		return new Long(criteria.uniqueResult().toString());
	}
	/*
	 * 
	 */
	@Override
	public Information init(Information model) {
		Criteria criteria = getCurrentSession().createCriteria(Information.class);
		criteria.createAlias("teacher", "teacher", CriteriaSpecification.LEFT_JOIN);
		if(model!=null&&model.getInformationId()!=null){
			criteria.add(Restrictions.eq("informationId", model.getInformationId()));
		}
		if(model!=null&&model.getCategory()!=null){
			criteria.add(Restrictions.eq("category", model.getCategory()));
		}
		Object obj = criteria.uniqueResult();
		return obj==null?null:(Information)obj;
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.InformationDAO#updateArray(java.lang.String, java.lang.String)
	 */
	@Override
	public void updateArray(String informationId, String updateInformationId) {
		StringBuffer sb = new StringBuffer();
		sb.append("update T_Information ");
		sb.append("set array = case ");
	    sb.append("		when informationId = :informationId then (select array from T_Information where informationId=:updateInformationId) ");
	    sb.append("		else (select array from T_Information where informationId=:informationId) ");
	    sb.append("	end ");
	    sb.append("where informationId in (:informationId,:updateInformationId)");
	    
	    getCurrentSession().createSQLQuery(sb.toString()).setString("informationId", informationId).setString("updateInformationId", updateInformationId).executeUpdate();
	}
	@Override
	public void copyAdd(Integer newCompetitionId,Integer oldCompetitionId, String category) {
		StringBuffer sql = new StringBuffer();
		
		sql.append("insert into T_Information(TeacherID, CompetitionId, Category, InformationTitle,Content, PublishDate, Status, Array) ");
		sql.append("select TeacherID, :newCompetitionId, :category, InformationTitle,Content, PublishDate, Status, Array from T_Information where CompetitionId =:oldCompetitionId and Category =:category");
		
		Query query = getCurrentSession().createSQLQuery(sql.toString());
		query.setInteger("newCompetitionId", newCompetitionId);
		query.setInteger("oldCompetitionId", oldCompetitionId);
		query.setString("category", category);
		query.executeUpdate();
		
	}

}
