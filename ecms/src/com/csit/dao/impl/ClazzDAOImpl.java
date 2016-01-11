package com.csit.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.csit.dao.ClazzDAO;
import com.csit.model.Clazz;
import com.csit.util.PageUtil;
/**
 * 
 * @Description: 班级DAO实现类
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-7-22
 * @author yk
 * @vesion 1.0
 */
@Repository
public class ClazzDAOImpl extends BaseDAOImpl<Clazz, Integer> implements
	ClazzDAO {

	
	@SuppressWarnings("unchecked")
	@Override
	public List<Clazz> query(Clazz model, Integer page, Integer rows) {
		Criteria criteria = getCurrentSession().createCriteria(Clazz.class);
		
		if(model!=null&&model.getClazzName()!=null){
			criteria.add(Restrictions.like("clazzName", model.getClazzName(), MatchMode.ANYWHERE));
		}
		if(model!=null&&model.getStatus()!=null){
			criteria.add(Restrictions.eq("status", model.getStatus()));
		}
		criteria.setFirstResult(PageUtil.getPageBegin(page, rows));
		criteria.setMaxResults(rows);
		
		criteria.addOrder(Order.asc("array"));
		return criteria.list();
	}

	@Override
	public Long getTotalCount(Clazz model) {
		Criteria criteria = getCurrentSession().createCriteria(Clazz.class);
		
		if(model!=null&&model.getClazzName()!=null){
			criteria.add(Restrictions.like("clazzName", model.getClazzName(), MatchMode.ANYWHERE));
		}
		if(model!=null&&model.getStatus()!=null){
			criteria.add(Restrictions.eq("status", model.getStatus()));
		}
		criteria.setProjection(Projections.rowCount());
		
		return new Long(criteria.uniqueResult().toString());
	}

	@Override
	public Integer getMaxArray() {
		Criteria criteria = getCurrentSession().createCriteria(Clazz.class);
		criteria.setProjection(Projections.max("array"));
		return criteria.uniqueResult()==null?0:(Integer)criteria.uniqueResult();
	}

	@Override
	public void updateArray(String clazzId, String updateClazzId) {
		StringBuffer sb = new StringBuffer();
		sb.append("update T_Clazz ");
		sb.append("set array = case ");
	    sb.append("		when clazzId = :clazzId then (select array from T_Clazz where clazzId=:updateClazzId) ");
	    sb.append("		else (select array from T_Clazz where clazzId=:clazzId) ");
	    sb.append("	end ");
	    sb.append("where clazzId in (:clazzId,:updateClazzId)");
	    
	    getCurrentSession().createSQLQuery(sb.toString()).setString("clazzId", clazzId).setString("updateClazzId", updateClazzId).executeUpdate();
	}

	@Override
	public Long getSelectTotalCount(Clazz model, Integer schoolGradeId) {
		StringBuffer sql = new StringBuffer();
		sql.append("select count(a.clazzId) from T_Clazz a left join ( ");
		sql.append("select * from T_SchoolGradeClazz a where a.schoolGradeId=:schoolGradeId) b ");
		sql.append("on b.clazzId=a.clazzId where b.clazzId is null and a.status=1");
		if(model!=null&&StringUtils.isNotEmpty(model.getClazzName())){
			sql.append("	and a.clazzName like :clazzName ");
		}
		Query query = getCurrentSession().createSQLQuery(sql.toString());
		query.setInteger("schoolGradeId", schoolGradeId);
		if(model!=null&&StringUtils.isNotEmpty(model.getClazzName())){
			query.setString("clazzName", "%"+model.getClazzName()+"%");
		}
		Object obj = query.uniqueResult();
		return new Long(obj.toString());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> selectQuery(Clazz model,
			Integer schoolGradeId, Integer page, Integer rows) {
		StringBuffer sql = new StringBuffer();
		sql.append("select a.clazzId,a.clazzName from T_Clazz a left join ( ");
		sql.append("select * from T_SchoolGradeClazz a where a.schoolGradeId=:schoolGradeId) b ");
		sql.append("on b.clazzId=a.clazzId where b.clazzId is null and a.status=1");
		if(model!=null&&StringUtils.isNotEmpty(model.getClazzName())){
			sql.append("	and a.clazzName like :clazzName ");
		}
		sql.append("order by a.clazzId asc");
		Query query = getCurrentSession().createSQLQuery(sql.toString());
		query.setInteger("schoolGradeId", schoolGradeId);
		query.setFirstResult(PageUtil.getPageBegin(page, rows));
		query.setMaxResults(rows);
		if(model!=null&&StringUtils.isNotEmpty(model.getClazzName())){
			query.setString("clazzName", "%"+model.getClazzName()+"%");
		}
		return query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}

}
