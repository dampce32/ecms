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

import com.csit.dao.TeacherDAO;
import com.csit.model.Teacher;
import com.csit.util.PageUtil;
/**
 * @Description:教师DAO实现类
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-4-17
 * @Author lys
 */
@Repository
public class TeacherDAOImpl extends BaseDAOImpl<Teacher, Integer> implements TeacherDAO{
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.TeacherDAO#query(java.lang.Integer, java.lang.Integer, com.csit.model.Teacher)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Teacher> query(Integer page, Integer rows, Teacher model) {
		Criteria criteria = getCurrentSession().createCriteria(Teacher.class);
		if(model!=null&&StringUtils.isNotEmpty(model.getTeacherCode())){
			criteria.add(Restrictions.like("teacherCode",model.getTeacherCode(),MatchMode.ANYWHERE));
		}
		if(model!=null&&StringUtils.isNotEmpty(model.getTeacherName())){
			criteria.add(Restrictions.like("teacherName",model.getTeacherName(),MatchMode.ANYWHERE));
		}
		criteria.setFirstResult(PageUtil.getPageBegin(page, rows));
		criteria.setMaxResults(rows);
		
		criteria.addOrder(Order.asc("teacherCode"));
		return criteria.list();
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.TeacherDAO#count(com.csit.model.Teacher)
	 */
	@Override
	public Long count(Teacher model) {
		Criteria criteria = getCurrentSession().createCriteria(Teacher.class);
		if(model!=null&&StringUtils.isNotEmpty(model.getTeacherCode())){
			criteria.add(Restrictions.like("teacherCode",model.getTeacherCode(),MatchMode.ANYWHERE));
		}
		if(model!=null&&StringUtils.isNotEmpty(model.getTeacherName())){
			criteria.add(Restrictions.like("teacherName",model.getTeacherName(),MatchMode.ANYWHERE));
		}
		
		criteria.setProjection(Projections.rowCount());
		return new Long(criteria.uniqueResult().toString());
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.TeacherDAO#getRootRight(java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getRootRight(Integer teacherId) {
		StringBuilder sql = new StringBuilder();
		sql.append( "select d.array,c.rightId,max(cast(c.state as int)) state,d.isLeaf,d.rightName ");
		sql.append( "from(select roleId from T_TeacherRole ");
		sql.append( "where teacherId = :teacherId) a ");
		sql.append( "left join T_Role b on a.roleId = b.roleId ");
		sql.append( "left join T_RoleRight c on a.roleId = c.roleId ");
		sql.append( "left join T_Right d on c.RightId = d.RightId ");
		sql.append( "where d.ParentRightId is null and b.state = 1 ");
		sql.append( "group by  d.array,c.RightID,d.IsLeaf,d.RightName,d.RightURL,c.state");
		
		return getCurrentSession().createSQLQuery(sql.toString()).setInteger("teacherId", teacherId).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.TeacherDAO#getChildrenRight(java.lang.Integer, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getChildrenRight(Integer teacherId,
			String rightId) {
		StringBuilder sql = new StringBuilder();
		sql.append( "select c.array,b.rightId,MAX(cast(b.State as int)) state,c.isLeaf,c.rightName,c.rightUrl ");
		sql.append( "from(select RoleId from T_TeacherRole ");
		sql.append( "where teacherId = :teacherId) a ");
		sql.append( "left join T_RoleRight b on a.RoleId = b.RoleID ");
		sql.append( "left join T_Right c on b.RightId = c.RightId ");
		sql.append( "left join T_Role d on b.RoleId = d.RoleId ");
		sql.append( "where c.ParentRightId = :rightId and c.State = 1 and d.State=1 ");
		sql.append( "group by  c.array,b.RightID,c.IsLeaf,c.RightName,c.RightURL ");
		sql.append( "having max(cast(b.State as int)) = 1 ");
		
		return getCurrentSession().createSQLQuery(sql.toString()).setInteger("teacherId", teacherId).setString("rightId", rightId).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.TeacherDAO#queryTeacherRight(java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> queryTeacherRight(Integer teacherId) {
		StringBuilder sql = new StringBuilder();
		sql.append( "select b.RightID,MAX(cast(b.State as int)) State ");
		sql.append( "from(select roleId from T_TeacherRole ");
		sql.append( "where teacherId = :teacherId) a ");
		sql.append( "left join T_RoleRight b on a.roleId = b.RoleID ");
		sql.append( "group by b.RightID");
		return getCurrentSession().createSQLQuery(sql.toString()).setInteger("teacherId", teacherId).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.TeacherDAO#getChildrenRight(java.lang.Integer, java.lang.String, java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getChildrenRight(Integer teacherId,
			String rightId, Integer kind) {
		StringBuilder sql = new StringBuilder();
		sql.append( "select a.array,a.rightId,a.state,a.rightName,a.rightUrl,case when b.isLeaf is null or b.isLeaf = 1 then 1 else 0 end isLeaf ");
		sql.append( "from (select c.array,b.rightId,max(cast(b.State as int)) state,c.rightName,c.rightUrl  ");
		sql.append( "	from(select RoleId from T_TeacherRole  ");
		sql.append( "	where teacherId = :teacherId) a  ");
		sql.append( "	left join T_RoleRight b on a.RoleId = b.RoleID  ");
		sql.append( "	left join T_Right c on b.RightId = c.RightId  ");
		sql.append( "	left join T_Role d on b.roleId = d.roleId  ");
		sql.append( "	where c.ParentRightId = :rightId  and d.state = 1 and c.state=1");
		if(kind!=null){
			sql.append( "		and c.kind = :kind ");
		}
		sql.append( "	group by  c.array,b.RightID,c.RightName,c.RightURL  ");
		sql.append( "	having max(cast(b.State as int)) = 1 )a ");
		sql.append( "left join (select a.rightId, case when  count(b.rightId)> 0 then 0 else 1 end isLeaf ");
		sql.append( "	from(select c.array,b.rightId,max(cast(b.State as int)) state,c.rightName,c.rightUrl  ");
		sql.append( "		from(select RoleId from T_TeacherRole  ");
		sql.append( "		where teacherId = :teacherId) a  ");
		sql.append( "		left join T_RoleRight b on a.RoleId = b.RoleID  ");
		sql.append( "		left join T_Right c on b.RightId = c.RightId  ");
		sql.append( "		left join T_Role d on b.roleId = d.roleId  ");
		sql.append( "		where c.ParentRightId = :rightId   and d.state = 1  ");
		if(kind!=null){
			sql.append( "		and c.kind = :kind ");
		}
		sql.append( "		group by  c.array,b.RightID,c.RightName,c.RightURL  ");
		sql.append( "		having max(cast(b.State as int)) = 1 )a ");
		sql.append( "	left join T_Right b on a.rightId = b.parentRightId ");
		if(kind!=null){
			sql.append( "		and b.kind = :kind ");
		}
		sql.append( "	left join (select RoleId from T_TeacherRole  ");
		sql.append( "		where teacherId = :teacherId)c on 1 = 1 ");
		sql.append( "	left join T_RoleRight d on b.rightId = d.rightId and c.roleId = d.roleId ");
		sql.append( "	left join T_Role e on c.roleId = e.roleId  ");
		sql.append( "where e.state = 1  ");
		sql.append( "	group by   a.rightId ");
		sql.append( "		having max(cast(b.State as int)) = 1 )b on a.rightId = b.rightId");
		
		Query query = getCurrentSession().createSQLQuery(sql.toString()).setInteger("teacherId", teacherId).setString("rightId", rightId);
		if(kind!=null){
			query = query.setInteger("kind", kind);
		}
		return query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> checkRight(Integer teacherId) {
		
		StringBuilder sql = new StringBuilder();
		sql.append( "select rr.rightId from T_RoleRight rr ");
		sql.append( "left join T_TeacherRole tr on tr.roleId=rr.roleId ");
		sql.append( "where tr.teacherId=:teacherId and (rr.rightId=:informationRight or rr.rightId=:enrollRight or rr.rightId=:trainingClassRight or rr.rightId=:stadiumRight) and rr.state=1 ");
		sql.append( "group by rr.rightId ");
		Query query = getCurrentSession().createSQLQuery(sql.toString()).setInteger("teacherId", teacherId).setString("informationRight", "000040000").setString("enrollRight", "000050010").setString("trainingClassRight", "000080010").setString("stadiumRight", "000060000");
		return query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.TeacherDAO#login(java.lang.String, java.lang.String)
	 */
	@Override
	public Teacher login(String teacherCode, String teacherPwd) {
		Criteria criteria = getCurrentSession().createCriteria(Teacher.class);
		if(StringUtils.isNotEmpty(teacherCode)){
			criteria.add(Restrictions.eq("teacherCode",teacherCode));
		}
		if(StringUtils.isNotEmpty(teacherPwd)){
			criteria.add(Restrictions.eq("passwords",teacherPwd));
		}
		Object obj = criteria.uniqueResult();
		if(obj==null){
			return null;
		}
		return (Teacher)obj;
	}

}
