package com.csit.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.csit.dao.RightDAO;
import com.csit.model.Right;
import com.csit.util.PageUtil;
/**
 * @Description:权限DAO实现类
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-4-16
 * @Author lys
 */
@Repository
public class RightDAOImpl extends BaseDAOImpl<Right,String> implements RightDAO{
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.RightDAO#getRoot()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Right> getRoot() {
		Criteria criteria = getCurrentSession().createCriteria(Right.class);
		criteria.add(Restrictions.isNull("parentRight"));
		List<Right> list = criteria.list();
		criteria.addOrder(Order.asc("array"));
		return list;
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.RightDAO#getChildren(com.csit.model.Right)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Right> getChildren(Right model) {
		Criteria criteria = getCurrentSession().createCriteria(Right.class);
		criteria.createAlias("parentRight", "model").add(Restrictions.eq("model.rightId", model.getRightId()));
		criteria.addOrder(Order.asc("array"));
		return criteria.list();
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.RightDAO#query(java.lang.Integer, java.lang.Integer, com.csit.model.Right)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Right> query(Integer page, Integer rows, Right model) {
		Criteria criteria = getCurrentSession().createCriteria(Right.class);
		if(model.getRightId()==null){
			criteria.add(Restrictions.isNull("parentRight"));
		}else{
			criteria.createAlias("parentRight", "model").add(Restrictions.eq("model.rightId",model.getRightId()));
		}
		if(StringUtils.isNotEmpty(model.getRightCode())){
			criteria.add(Restrictions.like("rightCode", model.getRightCode(),MatchMode.ANYWHERE));
		} 
		if(StringUtils.isNotEmpty(model.getRightName())){
			criteria.add(Restrictions.like("rightName", model.getRightName(),MatchMode.ANYWHERE));
		} 
		
		criteria.setFirstResult(PageUtil.getPageBegin(page, rows));
		criteria.setMaxResults(rows);
		criteria.addOrder(Order.asc("array"));
		return criteria.list();
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.RightDAO#count(com.csit.model.Right)
	 */
	@Override
	public Long count(Right model) {
		Criteria criteria = getCurrentSession().createCriteria(Right.class);
		if(model.getRightId()==null){
			criteria.add(Restrictions.isNull("parentRight"));
		}else{
			criteria.createAlias("parentRight", "model").add(Restrictions.eq("model.rightId",model.getRightId()));
		}
		if(StringUtils.isNotEmpty(model.getRightName())){
			criteria.add(Restrictions.like("rightName", model.getRightName(),MatchMode.ANYWHERE));
		} 
		criteria.setProjection(Projections.rowCount());
		return new Long(criteria.uniqueResult().toString());
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.RightDAO#getMaxArray(java.lang.String)
	 */
	@Override
	public Integer getMaxArray(String rightId) {
		Criteria criteria = getCurrentSession().createCriteria(Right.class);
		criteria.createAlias("parentRight", "model").add(Restrictions.eq("model.rightId", rightId));
		criteria.setProjection(Projections.max("array"));
		return criteria.uniqueResult()==null?0:(Integer)criteria.uniqueResult();
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.RightDAO#updateIsLeaf(java.lang.String, boolean)
	 */
	@Override
	public void updateIsLeaf(String rightId, boolean isLeaf) {
		StringBuilder hql = new StringBuilder("update Right set isLeaf = :isLeaf where rightId = :rightId");
		getCurrentSession().createQuery(hql.toString()).setBoolean("isLeaf", isLeaf).setString("rightId", rightId).executeUpdate();
	
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.RightDAO#getChildrenMaxRightId(com.csit.model.Right)
	 */
	@Override
	public String getChildrenMaxRightId(Right parentRight) {
		Criteria criteria = getCurrentSession().createCriteria(Right.class);
		criteria.add(Restrictions.eq("parentRight", parentRight));
		criteria.setProjection(Projections.max("rightId"));
		Object obj = criteria.uniqueResult();
		return obj==null?"":obj.toString();
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.RightDAO#countChildren(java.lang.String)
	 */
	@Override
	public Long countChildren(String rightId) {
		StringBuilder hql = new StringBuilder("select count(*) from Right model where model.parentRight.rightId =:rightId ");
		return (Long) getCurrentSession().createQuery(hql.toString()).setString("rightId", rightId).uniqueResult();
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.RightDAO#updateArray(java.lang.String, java.lang.String)
	 */
	@Override
	public void updateArray(String rightId, String updateRightId) {
		StringBuffer sb = new StringBuffer();
		sb.append("update T_Right	");
		sb.append("set Array = case ");
	    sb.append("		when RightID = :rightId then (select Array from T_Right where  RightID=:updateRightId) ");
	    sb.append("		else (select Array from T_Right where  RightID=:rightId) ");
	    sb.append("	end ");
	    sb.append("where RightID in (:rightId,:updateRightId)");
	    
	    getCurrentSession().createSQLQuery(sb.toString()).setString("rightId", rightId).setString("updateRightId", updateRightId).executeUpdate();
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.RightDAO#getParentRight(com.csit.model.Right)
	 */
	@Override
	public Right getParentRight(Right right) {
		Criteria criteria = getCurrentSession().createCriteria(Right.class);
		criteria.add(Restrictions.eq("rightId", right.getRightId()));
		criteria.setProjection(Projections.max("parentRight"));
		Object obj = criteria.uniqueResult();
		return obj==null?null:(Right)obj;
	}
}
