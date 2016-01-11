package com.csit.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.csit.dao.ContactsDAO;
import com.csit.model.Contacts;
import com.csit.service.ContactsService;
import com.csit.util.JSONUtil;
import com.csit.vo.ServiceResult;

/**
 * 
 * @Description: 通讯录service
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-24
 * @author longweier
 * @vesion 1.0
 */
@Service
public class ContactsServiceImpl extends BaseServiceImpl<Contacts, Integer> implements ContactsService {

	@Resource
	private ContactsDAO contactsDAO;
	
	public ServiceResult delete(Integer contactsId) {
		ServiceResult result = new ServiceResult(false);
		if(contactsId==null){
			result.setMessage("请选择要删除的记录");
			return result;
		}
		contactsDAO.delete(contactsId);
		result.setIsSuccess(true);
		return result;
	}

	public String query(Contacts model, Integer page, Integer rows) {
		List<Contacts> list = contactsDAO.query(model, page, rows);
		Long total = contactsDAO.getTotalCount(model);

		String[] properties = { "contactsId","name","mobilePhone","email", "qqNumber", "msn","note" };

		return JSONUtil.toJson(list, properties, total);
	}

	public ServiceResult saveContacts(Contacts model) {
		ServiceResult result = new ServiceResult(false);
		if(StringUtils.isEmpty(model.getName())){
			result.setMessage("姓名不能为空");
			return result;
		}
		if(StringUtils.isEmpty(model.getMobilePhone())){
			result.setMessage("电话不能为空");
			return result;
		}
		if(model.getMobilePhone().indexOf("1")!=0){
			result.setMessage("手机号码第一位只能是1");
			return result;
		}
		if(model.getMobilePhone().length()!=11){
			result.setMessage("手机号码只能是11位");
			return result;
		}
		Contacts contacts = contactsDAO.getContacts(model);
		if(contacts!=null){
			if(model.getContactsId()==null){
				result.setMessage("通讯录已存在该记录");
				return result;
			}else if(model.getContactsId()!=null && model.getContactsId().intValue()!=contacts.getContactsId().intValue()){
				result.setMessage("通讯录已存在该记录");
				return result;
			}else if(model.getContactsId()!=null && model.getContactsId().intValue()==contacts.getContactsId().intValue()){
				contactsDAO.evict(contacts);
			}
		}
		//新增
		if(model.getContactsId()==null){
			model.setVisible(1);
			contactsDAO.save(model);
		}else{
			model.setVisible(1);
			contactsDAO.update(model);
		}
		result.setIsSuccess(true);
		result.addData("contactsId", model.getContactsId());
		return result;
	}

	
}
