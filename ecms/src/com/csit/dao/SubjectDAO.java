package com.csit.dao;

import java.util.List;

import com.csit.model.Subject;
/**
 * 
 * @Description:试题Dao
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-4-26
 * @author jcf
 * @vesion 1.0
 */
public interface SubjectDAO extends BaseDAO<Subject,Integer>{

	/**
	 * 
	 * @Description: 分页查询试题
	 * @Create: 2013-4-26 下午01:57:02
	 * @author jcf
	 * @update logs
	 * @param model
	 * @param page
	 * @param rows
	 * @return
	 */
	List<Subject> query(Subject model, Integer page, Integer rows);
	/**
	 * 
	 * @Description: 统计试题
	 * @Create: 2013-4-26 下午01:56:58
	 * @author jcf
	 * @update logs
	 * @param model
	 * @return
	 */
	Long getTotalCount(Subject model);
	/**
	 * @Description: 试卷小题选择试题
	 * @Created Time: 2013-4-30 下午4:45:20
	 * @Author lys
	 * @param model
	 * @param idArray
	 * @param page
	 * @param rows
	 * @return
	 */
	List<Subject> selectPaperBigSmall(Subject model, Integer[] idArray,
			Integer page, Integer rows);
	/**
	 * @Description: 试卷小题选择试题
	 * @Created Time: 2013-4-30 下午4:45:33
	 * @Author lys
	 * @param model
	 * @param idArray
	 * @return
	 */
	Long getTotalCountSelectPaperBigSmall(Subject model, Integer[] idArray);
}
