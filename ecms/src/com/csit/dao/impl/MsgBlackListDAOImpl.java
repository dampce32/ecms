package com.csit.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.csit.dao.MsgBlackListDAO;
import com.csit.model.MsgBlackList;
import com.csit.util.PageUtil;
/**
 * @Description:短信黑字典DAO实现类
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-7-22
 * @author lys
 * @vesion 1.0
 */
@Repository
public class MsgBlackListDAOImpl extends BaseDAOImpl<MsgBlackList, Integer>
		implements MsgBlackListDAO {
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.MsgBlackListDAO#query(com.csit.model.MsgBlackList, java.lang.Integer, java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<MsgBlackList> query(MsgBlackList model, Integer page,
			Integer rows) {
		Criteria criteria = getCurrentSession().createCriteria(MsgBlackList.class);
		if(StringUtils.isNotEmpty(model.getBlackCode())){
			criteria.add(Restrictions.like("blackCode", model.getBlackCode(),MatchMode.ANYWHERE));
		} 
		criteria.setFirstResult(PageUtil.getPageBegin(page, rows));
		criteria.setMaxResults(rows);
		criteria.addOrder(Order.asc("msgBlackListId"));
		return criteria.list();
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.MsgBlackListDAO#getTotalCount(com.csit.model.MsgBlackList)
	 */
	@Override
	public Long getTotalCount(MsgBlackList model) {
		Criteria criteria = getCurrentSession().createCriteria(MsgBlackList.class);
		if(StringUtils.isNotEmpty(model.getBlackCode())){
			criteria.add(Restrictions.like("blackCode", model.getBlackCode(),MatchMode.ANYWHERE));
		} 
		criteria.setProjection(Projections.rowCount());
		return new Long(criteria.uniqueResult().toString());
	}
	
	
}
