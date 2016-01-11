package com.csit.service;

import com.csit.model.Teacher;
import com.csit.vo.ServiceResult;

/**
 * @Description:教师Service
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-4-16
 * @Author lys
 */
public interface TeacherService extends BaseService<Teacher,Integer>{
	/**
	 * @Description: 教师登陆
	 * @Created Time: 2013-4-16 上午11:32:38
	 * @Author lys
	 * @param userCode
	 * @param md5
	 * @return
	 */
	Teacher login(String userCode, String userPwd);
	/**
	 * @Description: 保存教师
	 * @Created Time: 2013-4-17 下午3:28:50
	 * @Author lys
	 * @param model
	 * @return
	 */
	ServiceResult save(Teacher model);
	/**
	 * @Description: 分页教师列表
	 * @Created Time: 2013-4-17 下午3:29:03
	 * @Author lys
	 * @param page
	 * @param rows
	 * @param model
	 * @return
	 */
	String query(Integer page, Integer rows, Teacher model);
	/**
	 * @Description: 批量教师删除
	 * @Created Time: 2013-4-17 下午3:48:37
	 * @Author lys
	 * @param ids
	 * @return
	 */
	ServiceResult mulDelete(String ids);
	/**
	 * @Description: 批量修改教师状态
	 * @Created Time: 2013-4-17 下午3:48:52
	 * @Author lys
	 * @param ids
	 * @param model
	 * @return
	 */
	ServiceResult mulUpdateState(String ids, Teacher model);
	/**
	 * @Description: 查询教师的跟权限
	 * @Created Time: 2013-4-17 下午8:38:58
	 * @Author lys
	 * @param model
	 * @return
	 */
	String queryRootRight(Teacher model);
	/**
	 * @Description: 取得教师的子权限
	 * @Created Time: 2013-4-17 下午8:39:13
	 * @Author lys
	 * @param model
	 * @param rightId
	 * @return
	 */
	String queryChildrenRight(Teacher model, String rightId);
	/**
	 * @Description: 取得主界面下的所有Url权限
	 * @Created Time: 2013-4-17 下午11:58:21
	 * @Author lys
	 * @param integerSesion
	 * @return
	 */
	String getUrlRightAll(Integer teacherId);
	/**
	 * @Description: 修改密码
	 * @Created Time: 2013-4-18 上午9:10:47
	 * @Author lys
	 * @param model
	 * @param newUserPwd
	 * @return
	 */
	ServiceResult modifyPwd(Teacher model, String newTeacherPwd);
	/**
	 * @Description: 取得用户个人信息
	 * @Created Time: 2013-4-18 上午9:33:55
	 * @Author lys
	 * @param integerSesion
	 * @return
	 */
	ServiceResult getSelfInfor(Integer teacherId);
	/**
	 * @Description: 更新用户个人信息
	 * @Created Time: 2013-4-18 上午9:34:08
	 * @Author lys
	 * @param integerSesion
	 * @param model
	 * @return
	 */
	ServiceResult updateSelfInfo(Integer teacherId, Teacher model);
	/**
	 * 
	 * @Description: combobx使用
	 * @Create: 2013-4-26 下午04:19:25
	 * @author jcf
	 * @update logs
	 * @return
	 */
	String queryCombobox();
	/**
	 * @Description: 取得主界面下的跟Url权限
	 * @Created Time: 2013-6-11 下午4:02:47
	 * @Author lys
	 * @param integerSession
	 * @return
	 */
	String getRootUrlRight(Integer teacherId);
	/**
	 * @Description: 取得权限right下的子权限
	 * @Created Time: 2013-6-11 下午4:06:30
	 * @Author lys
	 * @param integerSession
	 * @param rightId
	 * @return
	 */
	String getChildrenUrlRight(Integer teacherId, String rightId);
	
	ServiceResult checkRight(Integer teacherId,String competitionId);
	/**
	 * @Description: 批量重置密码
	 * @Created Time: 2013-7-2 下午5:49:46
	 * @Author lys
	 * @param ids
	 * @return
	 */
	ServiceResult mulResetPwd(String ids);

}
