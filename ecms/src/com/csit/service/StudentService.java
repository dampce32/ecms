package com.csit.service;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import com.csit.model.Student;
import com.csit.vo.ServiceResult;
/**
 * @Description:学生Service
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-4-19
 * @Author lys
 */
public interface StudentService extends BaseService<Student,Integer>{
	/**
	 * @Description: 分页查询学生
	 * @Created Time: 2013-4-19 下午8:57:51
	 * @Author lys
	 * @param model
	 * @param page
	 * @param rows
	 * @return
	 */
	String query(Student model, Integer page, Integer rows);
	/**
	 * @Description: 统计学生
	 * @Created Time: 2013-4-19 下午8:58:02
	 * @Author lys
	 * @param model
	 * @return
	 */
	ServiceResult getTotalCount(Student model);
	/**
	 * @Description: 保存学生
	 * @Created Time: 2013-4-19 下午10:24:56
	 * @Author lys
	 * @param model
	 * @param kindSave 
	 * @param baseWebPath 
	 * @param integerSesion
	 * @return
	 */
	ServiceResult save(Student model, String familyMemberInfo, String kindSave, String baseWebPath);
	/**
	 * @Description: 批量学生删除
	 * @Created Time: 2013-4-19 下午10:25:13
	 * @Author lys
	 * @param ids
	 * @return
	 */
	ServiceResult mulDelete(String ids);
	/**
	 * @Description: 批量修改学生状态
	 * @Created Time: 2013-4-19 下午10:25:34
	 * @Author lys
	 * @param ids
	 * @param model
	 * @param teacherId 
	 * @return
	 */
	ServiceResult mulUpdateState(String ids, Student model);
	/**
	 * @Description: 取得学生的照片
	 * @Created Time: 2013-4-20 下午8:41:32
	 * @Author lys
	 * @param studentId
	 * @param response 
	 * @throws IOException 
	 * @throws Exception 
	 */
	ServiceResult getPicture(Integer studentId);
	/**
	 * @Description: 学生登陆
	 * @Created Time: 2013-5-9 下午4:09:04
	 * @Author lys
	 * @param userCode
	 * @param md5
	 * @return
	 */
	Student login(String userCode, String md5);
	
	Map<String, Object> init(Integer studentId);
	
	/**
	 * 
	 * @Description: 导入学生照片
	 * @Create: 2013-5-22 下午03:55:04
	 * @author yk
	 * @update logs
	 * @param file
	 * @param studentId
	 * @return
	 */
	ServiceResult uploadPicture(Integer studentId,String picturePath,String uploadFileName,File upload);
	
	/**
	 * 
	 * @Description: 修改密码 
	 * @Create: 2013-7-5 上午10:24:06
	 * @author yk
	 * @update logs
	 * @param userCode
	 * @param userPwd
	 * @return
	 */
	ServiceResult resetPwd(Integer studentId,String userPwd);
}
