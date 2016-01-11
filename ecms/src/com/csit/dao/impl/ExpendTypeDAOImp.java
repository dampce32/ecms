package com.csit.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.csit.dao.ExpendTypeDAO;
import com.csit.model.ExpendType;
import com.csit.util.PageUtil;
/**
 * @Description:支出类型DAO实现类
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-9
 * @author jcf
 * @vesion 1.0
 */
@Repository
public class ExpendTypeDAOImp extends BaseDAOImpl<ExpendType, Integer> implements ExpendTypeDAO {
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.ExpendTypeDAO#query(java.lang.Integer, java.lang.Integer, com.csit.model.ExpendType)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ExpendType> query(ExpendType model,Integer page, Integer rows) {
		Criteria criteria = getCurrentSession().createCriteria(ExpendType.class);
		if(StringUtils.isNotEmpty(model.getExpendTypeName())){
			criteria.add(Restrictions.like("expendTypeName", model.getExpendTypeName(),MatchMode.ANYWHERE));
		} 
		if(model!=null&&model.getStatus()!=null){
			criteria.add(Restrictions.eq("status", model.getStatus()));
		}
		criteria.setFirstResult(PageUtil.getPageBegin(page, rows));
		criteria.setMaxResults(rows);
		criteria.addOrder(Order.asc("array"));
		return criteria.list();
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.ExpendTypeDAO#count(com.csit.model.ExpendType)
	 */
	@Override
	public Long getTotalCount(ExpendType model) {
		Criteria criteria = getCurrentSession().createCriteria(ExpendType.class);
		if(StringUtils.isNotEmpty(model.getExpendTypeName())){
			criteria.add(Restrictions.like("expendTypeName", model.getExpendTypeName(),MatchMode.ANYWHERE));
		} 
		if(model!=null&&model.getStatus()!=null){
			criteria.add(Restrictions.eq("status", model.getStatus()));
		}
		criteria.setProjection(Projections.rowCount());
		return new Long(criteria.uniqueResult().toString());
	}
	@Override
	public Integer getMaxArray() {
		Criteria criteria = getCurrentSession().createCriteria(ExpendType.class);
		criteria.setProjection(Projections.max("array"));
		return criteria.uniqueResult()==null?0:(Integer)criteria.uniqueResult();
	}
	@Override
	public void updateArray(Integer expendTypeId, Integer updateExpendTypeId) {
		StringBuffer sb = new StringBuffer();
		sb.append("update T_ExpendType	");
		sb.append("set Array = case ");
	    sb.append("		when ExpendTypeID = :expendTypeId then (select Array from T_ExpendType where  ExpendTypeID=:updateExpendTypeId) ");
	    sb.append("		else (select Array from T_ExpendType where  ExpendTypeID=:expendTypeId) ");
	    sb.append("	end ");
	    sb.append("where ExpendTypeID in (:expendTypeId,:updateExpendTypeId)");
	    
	    getCurrentSession().createSQLQuery(sb.toString()).setInteger("expendTypeId", expendTypeId).setInteger("updateExpendTypeId", updateExpendTypeId).executeUpdate();
		
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<ExpendType> queryCombobox(ExpendType model) {
		Criteria criteria = getCurrentSession().createCriteria(ExpendType.class);
		criteria.add(Restrictions.eq("status", 1));
		criteria.addOrder(Order.asc("array"));
		return criteria.list();
	}

}
