package com.csit.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.csit.dao.MessageConfigDAO;
import com.csit.model.MessageConfig;
/**
 * 
 * @Description: 短信配置DAO实现类
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-7-17
 * @author yk
 * @vesion 1.0
 */
@Repository
public class MessageConfigDAOImpl extends BaseDAOImpl<MessageConfig, Integer>
		implements MessageConfigDAO {

	@SuppressWarnings("unchecked")
	@Override
	public List<MessageConfig> query(MessageConfig model) {
		Criteria criteria = getCurrentSession().createCriteria(MessageConfig.class);
		criteria.add(Restrictions.eq("isMainSwitch", 0));
		criteria.addOrder(Order.asc("switchId"));
		return criteria.list();
	}

}
