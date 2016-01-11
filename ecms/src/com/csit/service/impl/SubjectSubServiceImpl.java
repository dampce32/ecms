package com.csit.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.csit.dao.SubjectSubDAO;
import com.csit.model.SubjectSub;
import com.csit.service.SubjectSubService;
import com.csit.util.JSONUtil;
/**
 * 
 * @Description: 试题子表Service实现类
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-5-6
 * @author yk
 * @vesion 1.0
 */
@Service
public class SubjectSubServiceImpl extends BaseServiceImpl<SubjectSub, Integer>
		implements SubjectSubService {
	@Resource
	private SubjectSubDAO subjectSubDAO;
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.SubjectSubService#query(com.csit.model.Subject)
	 */
	@Override
	public String query(SubjectSub model) {
		List<SubjectSub> list = subjectSubDAO.query("subject",model.getSubject());

		String[] properties = {"subject.subjectId","id.subId","descript","answer","option0",
				"option1","option2","option3","note","teacher.teacherId","operateTime"};
		return JSONUtil.toJson(list, properties);
	}

}
