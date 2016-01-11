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

import com.csit.dao.GradeDAO;
import com.csit.model.Grade;
import com.csit.util.PageUtil;
/**
 * 
 * @Description: 年级DAO实现类
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-7-22
 * @author yk
 * @vesion 1.0
 */
@Repository
public class GradeDAOImpl extends BaseDAOImpl<Grade, Integer> implements
	GradeDAO {

	
	@SuppressWarnings("unchecked")
	@Override
	public List<Grade> query(Grade model, Integer page, Integer rows) {
		Criteria criteria = getCurrentSession().createCriteria(Grade.class);
		
		if(model!=null&&model.getGradeName()!=null){
			criteria.add(Restrictions.like("gradeName", model.getGradeName(), MatchMode.ANYWHERE));
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
	public Long getTotalCount(Grade model) {
		Criteria criteria = getCurrentSession().createCriteria(Grade.class);
		
		if(model!=null&&model.getGradeName()!=null){
			criteria.add(Restrictions.like("gradeName", model.getGradeName(), MatchMode.ANYWHERE));
		}
		if(model!=null&&model.getStatus()!=null){
			criteria.add(Restrictions.eq("status", model.getStatus()));
		}
		criteria.setProjection(Projections.rowCount());
		
		return new Long(criteria.uniqueResult().toString());
	}

	@Override
	public Integer getMaxArray() {
		Criteria criteria = getCurrentSession().createCriteria(Grade.class);
		criteria.setProjection(Projections.max("array"));
		return criteria.uniqueResult()==null?0:(Integer)criteria.uniqueResult();
	}

	@Override
	public void updateArray(String gradeId, String updateGradeId) {
		StringBuffer sb = new StringBuffer();
		sb.append("update T_Grade ");
		sb.append("set array = case ");
	    sb.append("		when gradeId = :gradeId then (select array from T_Grade where gradeId=:updateGradeId) ");
	    sb.append("		else (select array from T_Grade where gradeId=:gradeId) ");
	    sb.append("	end ");
	    sb.append("where gradeId in (:gradeId,:updateGradeId)");
	    
	    getCurrentSession().createSQLQuery(sb.toString()).setString("gradeId", gradeId).setString("updateGradeId", updateGradeId).executeUpdate();
	}

	@Override
	public Long getTotalCountUsedSchool(Grade model, Integer schoolId) {
		StringBuffer sql = new StringBuffer();
		sql.append("select count(a.gradeId) from T_Grade a left join (");
		sql.append("select a.gradeId from T_SchoolGrade a where a.schoolId=:schoolId) b ");
		sql.append("on a.gradeId=b.gradeId where b.gradeId is null and a.status=1");
		if(model!=null&&StringUtils.isNotEmpty(model.getGradeName())){
			sql.append("	and a.gradeName like :gradeName ");
		}
		
		Query query = getCurrentSession().createSQLQuery(sql.toString());
		query.setInteger("schoolId", schoolId);
		if(model!=null&&StringUtils.isNotEmpty(model.getGradeName())){
			query.setString("gradeName", "%"+model.getGradeName()+"%");
		}
		Object obj = query.uniqueResult();
		return new Long(obj.toString());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> queryUsedSchool(Grade model,
			Integer schoolId, Integer page, Integer rows) {
		StringBuffer sql = new StringBuffer();
		sql.append("select a.gradeId,a.gradeName from T_Grade a left join (");
		sql.append("select a.gradeId from T_SchoolGrade a where a.schoolId=:schoolId) b ");
		sql.append("on a.gradeId=b.gradeId where b.gradeId is null and a.status=1 ");
		if(model!=null&&StringUtils.isNotEmpty(model.getGradeName())){
			sql.append("	and a.gradeName like :gradeName ");
		}
		sql.append("order by a.gradeId asc");
		Query query = getCurrentSession().createSQLQuery(sql.toString());
		query.setInteger("schoolId", schoolId);
		query.setFirstResult(PageUtil.getPageBegin(page, rows));
		query.setMaxResults(rows);
		if(model!=null&&StringUtils.isNotEmpty(model.getGradeName())){
			query.setString("gradeName", "%"+model.getGradeName()+"%");
		}
		return query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}

}
