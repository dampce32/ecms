package com.csit.action;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.csit.model.Contacts;
import com.csit.model.Teacher;
import com.csit.service.ContactsService;
import com.csit.vo.ServiceResult;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 
 * 
 * @Description: 通讯录Action
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-24
 * @author longweier
 * @vesion 1.0
 */
@Controller
@Scope("prototype")
public class ContactsAction extends BaseAction implements ModelDriven<Contacts> {

	private static final long serialVersionUID = 2720918258760223180L;

	private static Logger logger = Logger.getLogger(ContactsAction.class); 
	
	private Contacts model = new Contacts();
	
	@Resource
	private ContactsService contactsService;
	
	public Contacts getModel() {
		return model;
	}

	/**
	 * 
	 * @Description: 保存
	 * @param
	 * @Create: 2013-6-24 上午11:22:16
	 * @author longweier
	 * @update logs
	 * @return
	 * @throws Exception
	 */
	public void save(){
		ServiceResult result = new ServiceResult(false);
		try {
			Teacher teacher = new Teacher();
			teacher.setTeacherId((Integer) getSession(Teacher.LOGIN_TEACHERID));
			model.setTeacher(teacher);
			result = contactsService.saveContacts(model);
		} catch (Exception e) {
			result.setMessage("保存通讯录失败");
			logger.error("保存通讯录失败", e);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	
	/**
	 * 
	 * @Description: 批量删除
	 * @param
	 * @Create: 2013-6-24 上午11:22:35
	 * @author longweier
	 * @update logs
	 * @return
	 * @throws Exception
	 */
	public void delete(){
		ServiceResult result = new ServiceResult(false);	
		try {
			result = contactsService.delete(model.getContactsId());
		} catch (Exception e) {
			result.setMessage("删除失败");
			logger.error("删除通讯录失败", e);
		}
		ajaxJson(result.toJSON());
	}
	
	/**
	 * 
	 * @Description: 查询
	 * @param
	 * @Create: 2013-6-24 上午11:23:27
	 * @author longweier
	 * @update logs
	 * @return
	 * @throws Exception
	 */
	public void query() {
		Teacher teacher = new Teacher();
		teacher.setTeacherId((Integer) getSession(Teacher.LOGIN_TEACHERID));
		model.setTeacher(teacher);
		String jsonArray = contactsService.query(model, page, rows);
		ajaxJson(jsonArray);
	}
}
