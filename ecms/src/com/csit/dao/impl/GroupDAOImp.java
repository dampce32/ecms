package com.csit.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.csit.dao.GroupDAO;
import com.csit.model.Group;
import com.csit.util.PageUtil;
/**
 * @Description:参赛组别DAO实现类
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-5-28
 * @author jcf
 * @vesion 1.0
 */
@Repository
public class GroupDAOImp extends BaseDAOImpl<Group, Integer> implements GroupDAO {
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.GroupDAO#query(java.lang.Integer, java.lang.Integer, com.csit.model.Group)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Group> query(Group model,Integer page, Integer rows, Integer[] groupIdArr) {
		Criteria criteria = getCurrentSession().createCriteria(Group.class);
		if(StringUtils.isNotEmpty(model.getGroupCode())){
			criteria.add(Restrictions.like("groupCode", model.getGroupCode(),MatchMode.ANYWHERE));
		} 
		if(StringUtils.isNotEmpty(model.getGroupName())){
			criteria.add(Restrictions.like("groupName", model.getGroupName(),MatchMode.ANYWHERE));
		} 
		if(model!=null&&model.getStatus()!=null){
			criteria.add(Restrictions.eq("status", model.getStatus()));
		}
		if(groupIdArr!=null && groupIdArr.length!=0){
			criteria.add(Restrictions.not(Restrictions.in("groupId", groupIdArr)));
		}
		criteria.setFirstResult(PageUtil.getPageBegin(page, rows));
		criteria.setMaxResults(rows);
		criteria.addOrder(Order.asc("array"));
		return criteria.list();
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.GroupDAO#count(com.csit.model.Group)
	 */
	@Override
	public Long getTotalCount(Group model, Integer[] groupIdArr) {
		Criteria criteria = getCurrentSession().createCriteria(Group.class);
		if(StringUtils.isNotEmpty(model.getGroupCode())){
			criteria.add(Restrictions.like("groupCode", model.getGroupCode(),MatchMode.ANYWHERE));
		} 
		if(StringUtils.isNotEmpty(model.getGroupName())){
			criteria.add(Restrictions.like("groupName", model.getGroupName(),MatchMode.ANYWHERE));
		} 
		if(model!=null&&model.getStatus()!=null){
			criteria.add(Restrictions.eq("status", model.getStatus()));
		}
		if(groupIdArr!=null && groupIdArr.length!=0){
			criteria.add(Restrictions.not(Restrictions.in("groupId", groupIdArr)));
		}
		criteria.setProjection(Projections.rowCount());
		return new Long(criteria.uniqueResult().toString());
	}
	@Override
	public Integer getMaxArray() {
		Criteria criteria = getCurrentSession().createCriteria(Group.class);
		criteria.setProjection(Projections.max("array"));
		return criteria.uniqueResult()==null?0:(Integer)criteria.uniqueResult();
	}
	@Override
	public void updateArray(Integer groupId, Integer updateGroupId) {
		StringBuffer sb = new StringBuffer();
		sb.append("update T_Group	");
		sb.append("set Array = case ");
	    sb.append("		when GroupID = :groupId then (select Array from T_Group where  GroupID=:updateGroupId) ");
	    sb.append("		else (select Array from T_Group where  GroupID=:groupId) ");
	    sb.append("	end ");
	    sb.append("where GroupID in (:groupId,:updateGroupId)");
	    
	    getCurrentSession().createSQLQuery(sb.toString()).setInteger("groupId", groupId).setInteger("updateGroupId", updateGroupId).executeUpdate();
		
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Group> queryCombobox(Group model) {
		Criteria criteria = getCurrentSession().createCriteria(Group.class);
		criteria.add(Restrictions.eq("status", 1));
		criteria.addOrder(Order.asc("array"));
		return criteria.list();
	}

}
