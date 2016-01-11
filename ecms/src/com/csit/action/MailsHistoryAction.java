package com.csit.action;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.csit.model.MailsHistory;
import com.csit.model.Teacher;
import com.csit.service.MailsHistoryService;
import com.opensymphony.xwork2.ModelDriven;

@Controller
@Scope("prototype")
public class MailsHistoryAction extends BaseAction implements
		ModelDriven<MailsHistory> {
	
	private static final long serialVersionUID = 6047629186844279114L;
	
	MailsHistory model = new MailsHistory();
	
	@Resource
	private MailsHistoryService mailsHistoryService;
	
	@Override
	public MailsHistory getModel() {
		return model;
	}
	
	private Date start;
	private Date end;

	/**
	 * 
	 * @Description: 分页查询邮件发送历史记录 
	 * @Create: 2013-7-10 下午05:14:37
	 * @author yk
	 * @update logs
	 */
	public void query(){
		Teacher teacher = new Teacher();
		teacher.setTeacherId((Integer) getSession(Teacher.LOGIN_TEACHERID));
		model.setTeacher(teacher);
		String jsonArray = mailsHistoryService.query(model, page, rows,start,end);
		ajaxJson(jsonArray);
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}
	
}
