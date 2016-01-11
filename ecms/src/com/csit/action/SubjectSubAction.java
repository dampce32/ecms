package com.csit.action;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.csit.model.SubjectSub;
import com.csit.service.SubjectSubService;
import com.opensymphony.xwork2.ModelDriven;
/**
 * 
 * @Description: 试题子表Action
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-5-6
 * @author yk
 * @vesion 1.0
 */
@Controller
@Scope("prototype")
public class SubjectSubAction extends BaseAction implements
		ModelDriven<SubjectSub> {
	
	private static final long serialVersionUID = 4632153971338202685L;
//	private static final Logger logger = Logger.getLogger(SubjectSubAction.class);
	private SubjectSub model = new SubjectSub();
	
	@Resource
	private SubjectSubService subjectSubService;
	
	@Override
	public SubjectSub getModel() {
		return model;
	}

	public void query() {
		String jsonArray = subjectSubService.query(model);
		ajaxJson(jsonArray);
	}
}
