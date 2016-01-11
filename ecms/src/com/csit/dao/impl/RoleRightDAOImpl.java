package com.csit.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.csit.dao.RoleRightDAO;
import com.csit.model.RoleRight;
import com.csit.model.RoleRightId;
/**
 * @Description:角色权限DAO实现类
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-4-17
 * @Author lys
 */
@Repository
public class RoleRightDAOImpl extends BaseDAOImpl<RoleRight, RoleRightId>
		implements RoleRightDAO {
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.RoleRightDAO#updateState(java.lang.Integer, java.lang.String, boolean)
	 */
	@Override
	public void updateState(Integer roleId, String rightId, boolean state) {
		StringBuilder hql = new StringBuilder();
		hql.append("update RoleRight roleRight set state = :state where roleRight.id.roleId = :roleId and roleRight.id.rightId = :rightId");
		getCurrentSession().createQuery(hql.toString()).setInteger("roleId", roleId).setString("rightId", rightId).setBoolean("state", state).executeUpdate();
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.RoleRightDAO#getRoot(com.csit.model.RoleRight)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<RoleRight> getRoot(RoleRight model) {
		Criteria criteria = getCurrentSession().createCriteria(RoleRight.class);
		criteria.add(Restrictions.eq("role", model.getRole()));
		criteria.createAlias("right", "right");
		criteria.add(Restrictions.isNull("right.parentRight"));
		return criteria.list();
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.RoleRightDAO#queryChildren(com.csit.model.RoleRight)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<RoleRight> queryChildren(RoleRight roleRight) {
		Criteria criteria = getCurrentSession().createCriteria(RoleRight.class);
		criteria.add(Restrictions.eq("role", roleRight.getRole()));
		criteria.createAlias("right", "right");
		criteria.add(Restrictions.eq("right.parentRight",roleRight.getRight()));
		criteria.addOrder(Order.asc("right.array"));
		return criteria.list();
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.RoleRightDAO#countChildrenStateSameParent(com.csit.model.RoleRight, boolean)
	 */
	@Override
	public Integer countChildrenStateSameParent(RoleRight model, boolean state) {
		StringBuilder sql = new StringBuilder();
		sql.append( "select COUNT(1) ");
		sql.append( "from(select * ");
		sql.append( "	from T_Right  ");
		sql.append( "	where RightID = :rightId)a ");
		sql.append( "left join T_Right b on a.ParentRightID = b.ParentRightID ");
		sql.append( "left join (select * ");
		sql.append( "	from T_RoleRight  ");
		sql.append( "	where RoleID = :roleId)c on b.RightID = c.RightID ");
		sql.append( "where c.state = :state ");
		Object obj = getCurrentSession().createSQLQuery(sql.toString()).setInteger("roleId", model.getRole().getRoleId()).setString("rightId", model.getRight().getRightId())
				.setBoolean("state", state).uniqueResult();
		return Integer.parseInt(obj.toString());
	}
}
