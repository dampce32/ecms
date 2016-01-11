package com.csit.dao;

import java.util.List;

import com.csit.model.Subject;
import com.csit.model.SubjectSub;
/**
 * 
 * @Description: 试题子表DAO
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-5-6
 * @author yk
 * @vesion 1.0
 */
public interface SubjectSubDAO extends BaseDAO<SubjectSub, Integer> { 
	void delete(Integer subjectId);
	List<SubjectSub> query(SubjectSub model);
	/**
	 * @Description: 查询试题下的子表
	 * @Created Time: 2013-5-8 下午8:51:59
	 * @Author lys
	 * @param subject
	 * @return
	 */
	List<SubjectSub> query(Subject subject);
}
