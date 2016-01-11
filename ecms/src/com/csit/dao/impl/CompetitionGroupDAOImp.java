package com.csit.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.csit.dao.CompetitionGroupDAO;
import com.csit.model.CompetitionGroup;
import com.csit.util.PageUtil;
/**
 * @Description:赛事组别DAO实现类
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-5-31
 * @author jcf
 * @vesion 1.0
 */
@Repository
public class CompetitionGroupDAOImp extends BaseDAOImpl<CompetitionGroup, Integer> implements CompetitionGroupDAO {
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.CompetitionDAO#query(java.lang.Integer, java.lang.Integer, com.csit.model.Competition)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CompetitionGroup> query(CompetitionGroup model,Integer page, Integer rows) {
		Criteria criteria = getCurrentSession().createCriteria(CompetitionGroup.class);
		criteria.createAlias("group", "group");
		if(model!=null&&model.getCompetition().getCompetitionId()!=null){
			criteria.add(Restrictions.eq("competition.competitionId", model.getCompetition().getCompetitionId()));
		}
		criteria.setFirstResult(PageUtil.getPageBegin(page, rows));
		criteria.setMaxResults(rows);
		criteria.addOrder(Order.asc("array"));
		return criteria.list();
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.CompetitionDAO#count(com.csit.model.Competition)
	 */
	@Override
	public Long getTotalCount(CompetitionGroup model) {
		Criteria criteria = getCurrentSession().createCriteria(CompetitionGroup.class);
		if(model!=null&&model.getCompetition().getCompetitionId()!=null){
			criteria.add(Restrictions.eq("competition.competitionId", model.getCompetition().getCompetitionId()));
		}
		criteria.setProjection(Projections.rowCount());
		return new Long(criteria.uniqueResult().toString());
	}
	@Override
	public Integer getMaxArray(Integer competitionId) {
		Criteria criteria = getCurrentSession().createCriteria(CompetitionGroup.class);
		criteria.add(Restrictions.eq("competition.competitionId", competitionId));
		criteria.setProjection(Projections.max("array"));
		return criteria.uniqueResult()==null?0:(Integer)criteria.uniqueResult();
	}
	@Override
	public void updateArray(Integer competitionGroupId,Integer updateCompetitionGroupId) {
		StringBuffer sb = new StringBuffer();
		sb.append("update T_CompetitionGroup	");
		sb.append("set Array = case ");
	    sb.append("		when CompetitionGroupID = :competitionGroupId then (select Array from T_CompetitionGroup where  CompetitionGroupID=:updateCompetitionGroupId) ");
	    sb.append("		else (select Array from T_CompetitionGroup where  CompetitionGroupID=:competitionGroupId) ");
	    sb.append("	end ");
	    sb.append("where CompetitionGroupID in (:competitionGroupId,:updateCompetitionGroupId)");
	    
	    getCurrentSession().createSQLQuery(sb.toString()).setInteger("competitionGroupId", competitionGroupId).setInteger("updateCompetitionGroupId", updateCompetitionGroupId).executeUpdate();
		
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<CompetitionGroup> queryCombobox(CompetitionGroup model) {
		Criteria criteria = getCurrentSession().createCriteria(CompetitionGroup.class);
		criteria.createAlias("group", "group");
		if(model!=null&&model.getCompetition()!=null&&model.getCompetition().getCompetitionId()!=null){
			criteria.add(Restrictions.eq("competition.competitionId", model.getCompetition().getCompetitionId()));
		}
		criteria.addOrder(Order.asc("array"));
		return criteria.list();
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.CompetitionGroupDAO#initEnrollStu(java.lang.Integer, java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> initEnrollStu(Integer competitionId,
			Integer studentId) {
		StringBuffer sb = new StringBuffer();
		sb.append("select a.studentId,a.areaId,b.cityId,b.provinceId,c.competitionName	");
		sb.append("from T_Student a ");
	    sb.append("left join T_Area b on a.areaId = b.areaId ");
	    sb.append("left join T_Competition c on c.competitionId = :competitionId ");
	    sb.append("where a.studentId = :studentId ");
	    
	    Query query =  getCurrentSession().createSQLQuery(sb.toString());
	    query =query.setInteger("competitionId", competitionId).setInteger("studentId", studentId);
	    Object obj =  query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).uniqueResult();
		return obj==null?null:(Map<String, Object>)obj;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<CompetitionGroup> query(Integer competitionId) {
		Criteria criteria = getCurrentSession().createCriteria(CompetitionGroup.class);
		criteria.createAlias("group", "group", CriteriaSpecification.LEFT_JOIN);
		if(competitionId!=null){
			criteria.add(Restrictions.eq("competition.competitionId", competitionId));
		}
		return criteria.list();
	}
	@Override
	public void copyAdd(Integer newCompetitionId, Integer oldCompetitionId) {

		StringBuffer sql = new StringBuffer();
		
		sql.append("insert into T_CompetitionGroup(CompetitionId, PaperID, GroupID,Array) ");
		sql.append("select :newCompetitionId, PaperID, GroupID,Array from T_CompetitionGroup where CompetitionId =:oldCompetitionId");
		
//		sql.append("insert into T_CompetitionPrize(CompetitionGroupID, PrizeID, Award,Array) ");
//		sql.append("select a.competitionGroupId,b.prizeId,b.award,b.array ");
//		sql.append("from(select * from T_CompetitionGroup a where a.competitionId = :newCompetitionId) a ");
//		sql.append("left join(select b.groupId,a.prizeId,a.award,a.array from T_CompetitionPrize a ");
//		sql.append("left join T_CompetitionGroup b on a.competitionGroupId = b.competitionGroupId ");
//		sql.append("where b.competitionId = :oldCompetitionId) b on a.groupId = b.groupId");
		
		Query query = getCurrentSession().createSQLQuery(sql.toString());
		query.setInteger("newCompetitionId", newCompetitionId);
		query.setInteger("oldCompetitionId", oldCompetitionId);
		query.executeUpdate();
	}

}
