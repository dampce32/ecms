package com.csit.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.csit.dao.CompetitionPhotoDAO;
import com.csit.model.Competition;
import com.csit.model.CompetitionPhoto;
import com.csit.util.PageUtil;
/**
 * @Description:赛事风采DAO实现类
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-11
 * @Author lys
 */
@Repository
public class CompetitionPhotoDAOImpl extends
		BaseDAOImpl<CompetitionPhoto, Integer> implements CompetitionPhotoDAO {
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.CompetitionPhotoDAO#getMaxArray(com.csit.model.Competition)
	 */
	@Override
	public Integer getMaxArray(Competition competition) {
		Criteria criteria = getCurrentSession().createCriteria(CompetitionPhoto.class);
		criteria.add(Restrictions.eq("competition", competition));
		criteria.setProjection(Projections.max("array"));
		Object obj = criteria.uniqueResult();
		return obj==null?0:(Integer)obj;
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.CompetitionPhotoDAO#query(com.csit.model.CompetitionPhoto, java.lang.Integer, java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CompetitionPhoto> query(CompetitionPhoto model, Integer page,
			Integer rows) {
		Criteria criteria = getCurrentSession().createCriteria(CompetitionPhoto.class);
		if(model!=null&&model.getCompetition()!=null&&model.getCompetition().getCompetitionId()!=null){
			criteria.add(Restrictions.eq("competition", model.getCompetition()));
		} 
		if(StringUtils.isNotEmpty(model.getPhotoType())){
			criteria.add(Restrictions.eq("photoType", model.getPhotoType()));
		} 
		criteria.setFirstResult(PageUtil.getPageBegin(page, rows));
		criteria.setMaxResults(rows);
		criteria.addOrder(Order.desc("array"));
		return criteria.list();
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.CompetitionPhotoDAO#getTotalCount(com.csit.model.CompetitionPhoto)
	 */
	@Override
	public Long getTotalCount(CompetitionPhoto model) {
		Criteria criteria = getCurrentSession().createCriteria(CompetitionPhoto.class);
		if(model!=null&&model.getCompetition()!=null&&model.getCompetition().getCompetitionId()!=null){
			criteria.add(Restrictions.eq("competition", model.getCompetition()));
		} 
		if(StringUtils.isNotEmpty(model.getPhotoType())){
			criteria.add(Restrictions.eq("photoType", model.getPhotoType()));
		} 
		criteria.setProjection(Projections.rowCount());
		return new Long(criteria.uniqueResult().toString());
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.CompetitionPhotoDAO#updateArray(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public void updateArray(Integer competitionPhotoId,
			Integer updateCompetitionPhotoId) {
		StringBuffer sb = new StringBuffer();
		sb.append("update T_CompetitionPhoto ");
		sb.append("set array = case ");
	    sb.append("		when competitionPhotoId = :competitionPhotoId then (select array from T_CompetitionPhoto where competitionPhotoId=:updateCompetitionPhotoId) ");
	    sb.append("		else (select array from T_CompetitionPhoto where competitionPhotoId=:competitionPhotoId) ");
	    sb.append("	end ");
	    sb.append("where competitionPhotoId in (:competitionPhotoId,:updateCompetitionPhotoId)");
	    
	    getCurrentSession().createSQLQuery(sb.toString()).setInteger("competitionPhotoId", competitionPhotoId).setInteger("updateCompetitionPhotoId", updateCompetitionPhotoId).executeUpdate();
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.CompetitionPhotoDAO#query(java.lang.Integer, java.lang.String, java.lang.Integer, java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CompetitionPhoto> query(Integer competitionId,
			String photoType, Integer page, Integer rows) {
		Criteria criteria = getCurrentSession().createCriteria(CompetitionPhoto.class);
		criteria.createAlias("picture", "picture", CriteriaSpecification.LEFT_JOIN);
		if(competitionId!=null){
			criteria.add(Restrictions.eq("competition.competitionId", competitionId));
		} 
		if(StringUtils.isNotEmpty(photoType)){
			criteria.add(Restrictions.eq("photoType", photoType));
		} 
		criteria.setFirstResult(PageUtil.getPageBegin(page, rows));
		criteria.setMaxResults(rows);
		criteria.addOrder(Order.asc("array"));
		return criteria.list();
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.CompetitionPhotoDAO#getTotalCount(java.lang.Integer, java.lang.String)
	 */
	@Override
	public Long getTotalCount(Integer competitionId, String photoType) {
		Criteria criteria = getCurrentSession().createCriteria(CompetitionPhoto.class);
		if(competitionId!=null){
			criteria.add(Restrictions.eq("competition.competitionId", competitionId));
		} 
		if(StringUtils.isNotEmpty(photoType)){
			criteria.add(Restrictions.eq("photoType", photoType));
		} 
		criteria.setProjection(Projections.rowCount());
		return new Long(criteria.uniqueResult().toString());
	}
	@Override
	public void copyAdd(Integer newCompetitionId, Integer oldCompetitionId,
			String photoType) {
		StringBuffer sql = new StringBuffer();
		
		sql.append("insert into T_CompetitionPhoto(PictureId, CompetitionId, PhotoType, Note,Array) ");
		sql.append("select PictureId, :newCompetitionId, :photoType, Note,Array from T_CompetitionPhoto where CompetitionId =:oldCompetitionId and PhotoType =:photoType");
		
		Query query = getCurrentSession().createSQLQuery(sql.toString());
		query.setInteger("newCompetitionId", newCompetitionId);
		query.setInteger("oldCompetitionId", oldCompetitionId);
		query.setString("photoType", photoType);
		query.executeUpdate();
		
	}

}
