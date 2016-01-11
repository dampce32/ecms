package com.csit.dao;

import java.util.List;

import com.csit.model.Contacts;

/**
 * 
 * @Description: 通讯录dao
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-24
 * @author longweier
 * @vesion 1.0
 */
public interface ContactsDAO extends BaseDAO<Contacts, Integer> {
	
	List<Contacts> query(Contacts model, Integer page, Integer rows);
	/**
	 * @Description: 统计城市
	 * @Created Time: 2013-5-29 下午2:18:09
	 * @Author lys
	 * @param model
	 * @return
	 */
	Long getTotalCount(Contacts model);
	
	/**
	 * 
	 * @Description: 根据教师和电话号码查询该通讯录是否存在
	 * @param
	 * @Create: 2013-6-28 下午03:52:34
	 * @author longweier
	 * @update logs
	 * @param model
	 * @return
	 * @return
	 * @throws Exception
	 */
	Contacts getContacts(Contacts model);
}
