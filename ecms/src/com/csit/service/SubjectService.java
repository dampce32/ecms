package com.csit.service;

import com.csit.model.Subject;
import com.csit.vo.ServiceResult;

/**
 * @Description:试题Service
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-4-26
 * @author jcf
 * @vesion 1.0
 */
public interface SubjectService extends BaseService<Subject, Integer> {

	/**
	 * 
	 * @Description: 保存试题
	 * @Create: 2013-4-26 下午02:03:50
	 * @author jcf
	 * @update logs
	 * @param model
	 * @return
	 */
	ServiceResult save(Subject model,String optionsStr);
	/**
	 * 
	 * @Description: 批量删除试题 
	 * @Create: 2013-5-3 上午10:31:40
	 * @author yk
	 * @update logs
	 * @param ids
	 * @return
	 */
	ServiceResult mulDelete(String ids);
	/**
	 * @Description: 分页查询试题
	 * @Create: 2013-4-26 下午02:04:09
	 * @author jcf
	 * @update logs
	 * @param model
	 * @param page
	 * @param rows
	 * @return
	 */
	String query(Subject model, Integer page, Integer rows);
	/**
	 * @Description: 试卷小题选择试题
	 * @Created Time: 2013-4-30 下午4:42:08
	 * @Author lys
	 * @param model
	 * @param subjectIds
	 * @param page
	 * @param rows
	 * @return
	 */
	String selectPaperBigSmall(Subject model, String subjectIds, Integer page,
			Integer rows);
	/**
	 * 
	 * @Description: 批量修改试题状态
	 * @Create: 2013-5-3 上午11:04:40
	 * @author yk
	 * @update logs
	 * @param ids
	 * @param model
	 * @param teacherId
	 * @return
	 */
	ServiceResult mulUpdateState(String ids, Subject model, Integer teacherId);
}
