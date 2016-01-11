package com.csit.action;

import java.io.File;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.csit.model.MailsSender;
import com.csit.model.Teacher;
import com.csit.service.MailsSenderService;
import com.csit.vo.GobelConstants;
import com.csit.vo.ServiceResult;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 
 * @Description: 邮件发送Action
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-7-9
 * @author yk
 * @vesion 1.0
 */
@Controller
@Scope("prototype")
public class MailsSenderAction extends BaseAction implements ModelDriven<MailsSender>{

	private static final long serialVersionUID = -6864053943657315939L;
	private static final Logger logger = Logger.getLogger(MailsSenderAction.class);

	MailsSender model = new MailsSender();
	
	@Resource
	private MailsSenderService mailsSenderService;
	
	/** 文件对象 */
	private List<File> Filedata;
	/** 文件名 */
	private List<String> FiledataFileName;
	/** 文件内容类型 */
	private List<String> FiledataContentType;
	/** 返回字符串 */
	private String returnValue = null;
	
	@Override
	public MailsSender getModel() {
		return model;
	}
	
	public void save(){
		ServiceResult result = new ServiceResult(false);
		try {
			Teacher teacher = new Teacher();
			teacher.setTeacherId((Integer) getSession(Teacher.LOGIN_TEACHERID));
			model.setTeacher(teacher);
			String names = getParameter("names");
			String emails = getParameter("emails");
			result = mailsSenderService.save(model,names,emails);
		} catch (Exception e) {
			logger.error("发送邮件出错", e);
			result.setMessage("发送邮件出错");
		}
		ajaxJson(result.toJSON());
	}
	/**
	 * 
	 * @Description: 批量上传附件
	 * @Create: 2013-7-10 上午09:35:52
	 * @author yk
	 * @update logs
	 */
	public void mulUpload(){
		String fileBasePath = getRequest().getSession().getServletContext()
			.getRealPath(GobelConstants.MailAttachment_BASEPATH);
		String msg = mailsSenderService.mulUpload(Filedata,FiledataFileName,FiledataContentType, fileBasePath);;
		ajaxJson(msg);
	}
	
	/**
	 * 
	 * @Description: 分页查询邮件发送
	 * @Create: 2013-7-10 下午05:50:14
	 * @author yk
	 * @update logs
	 */
	public void query(){
		Teacher teacher = new Teacher();
		teacher.setTeacherId((Integer) getSession(Teacher.LOGIN_TEACHERID));
		model.setTeacher(teacher);
		String jsonArray = mailsSenderService.query(model, page, rows);
		ajaxJson(jsonArray);
	}
	
	/**
	 * 
	 * @Description: 获取收件人 
	 * @Create: 2013-7-12 上午10:20:15
	 * @author yk
	 * @update logs
	 */
	public void getReceiver(){
		String jsonString = mailsSenderService.getReceiver(model.getReceiveIDs());
		ajaxJson(jsonString);
	}
	
	public List<File> getFiledata() {
		return Filedata;
	}

	public void setFiledata(List<File> filedata) {
		Filedata = filedata;
	}

	public List<String> getFiledataFileName() {
		return FiledataFileName;
	}

	public void setFiledataFileName(List<String> filedataFileName) {
		FiledataFileName = filedataFileName;
	}

	public List<String> getFiledataContentType() {
		return FiledataContentType;
	}

	public void setFiledataContentType(List<String> filedataContentType) {
		FiledataContentType = filedataContentType;
	}

	public String getReturnValue() {
		return returnValue;
	}

	public void setReturnValue(String returnValue) {
		this.returnValue = returnValue;
	}
	
}
