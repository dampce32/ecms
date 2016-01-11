package com.csit.dao;

import java.util.List;

import com.csit.model.Student;

/**
 * @Description:学生DAO
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-4-19
 * @Author lys
 */
public interface StudentDAO extends BaseDAO<Student,Integer>{
	/**
	 * @Description: 分页查询学生
	 * @Created Time: 2013-4-19 下午9:01:19
	 * @Author lys
	 * @param model
	 * @param page
	 * @param rows
	 * @return
	 */
	List<Student> query(Student model, Integer page, Integer rows);
	/**
	 * @Description: 统计学生
	 * @Created Time: 2013-4-19 下午9:01:35
	 * @Author lys
	 * @param model
	 * @return
	 */
	Long getTotalCount(Student model);
	/**
	 * 
	 * @Description: combobox查询学生
	 * @Create: 2013-7-3 上午10:42:17
	 * @author yk
	 * @update logs
	 * @return
	 */
	List<Student> queryCombobox(String studentName);
//	/**
//	 * 
//	 * @Description: 考务安排查询学生
//	 * @Create: 2013-5-14 上午11:26:14
//	 * @author jcf
//	 * @update logs
//	 * @param model
//	 * @param teacherId
//	 * @param operateTime
//	 * @param arrangeId
//	 * @param page
//	 * @param rows
//	 * @return
//	 */
//	List<Student> queryArrange(Student model,Integer teacherId, Timestamp operateTime,
//			Integer arrangeId, Integer page, Integer rows);
//	/**
//	 * 
//	 * @Description: 考务安排统计学生
//	 * @Create: 2013-5-14 上午11:26:39
//	 * @author jcf
//	 * @update logs
//	 * @param model
//	 * @param teacherId
//	 * @param operateTime
//	 * @param arrangeId
//	 * @return
//	 */
//	Long getTotalCountArrange(Student model,Integer teacherId, Timestamp operateTime,
//			Integer arrangeId);

}
