package com.csit.service;

import com.csit.model.Contacts;
import com.csit.vo.ServiceResult;

/**
 * 
 * @Description: 通讯录service
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-24
 * @author longweier
 * @vesion 1.0
 */
public interface ContactsService extends BaseService<Contacts, Integer> {
	
	/**
	 * 
	 * @Description: 保存
	 * @param
	 * @Create: 2013-6-24 上午10:53:40
	 * @author longweier
	 * @update logs
	 * @param contacts
	 * @return
	 * @return
	 * @throws Exception
	 */
	ServiceResult saveContacts(Contacts contacts);
	
	/**
	 * 
	 * @Description: 删除
	 * @param
	 * @Create: 2013-6-24 上午10:54:20
	 * @author longweier
	 * @update logs
	 * @param contactsId
	 * @return
	 * @return
	 * @throws Exception
	 */
	ServiceResult delete(Integer contactsId);
	
	/**
	 * 
	 * @Description: 查询
	 * @param
	 * @Create: 2013-6-24 上午10:54:56
	 * @author longweier
	 * @update logs
	 * @param model
	 * @param page
	 * @param rows
	 * @return
	 * @return
	 * @throws Exception
	 */
	String query(Contacts model, Integer page, Integer rows);
}
