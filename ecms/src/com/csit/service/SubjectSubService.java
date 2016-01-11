package com.csit.service;

import com.csit.model.SubjectSub;
/**
 * 
 * @Description: 试题子表Service
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-5-6
 * @author yk
 * @vesion 1.0
 */
public interface SubjectSubService extends BaseService<SubjectSub, Integer> {
	String query(SubjectSub model);
}
