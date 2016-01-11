package com.csit.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.csit.dao.TeacherRoleDAO;
import com.csit.model.TeacherRole;
import com.csit.model.TeacherRoleId;
/**
 * @Description:教师角色DAO实现类
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-4-17
 * @Author lys
 */
@Repository
public class TeacherRoleDAOImpl extends BaseDAOImpl<TeacherRole, TeacherRoleId>
		implements TeacherRoleDAO {
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.TeacherRoleDAO#queryRole(com.csit.model.TeacherRole)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<TeacherRole> queryRole(TeacherRole model) {
		Criteria criteria = getCurrentSession().createCriteria(TeacherRole.class);
		if(model!=null&&model.getTeacher()!=null&&model.getTeacher().getTeacherId()!=null){
			criteria.add(Restrictions.eq("teacher",model.getTeacher()));
		}
		return criteria.list();
	}

}
