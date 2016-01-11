package com.csit.action;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.csit.model.Student;
import com.csit.model.Teacher;
import com.csit.service.StudentService;
import com.csit.service.TeacherService;
import com.csit.util.JCaptchaEngine;
import com.csit.util.MD5Util;
import com.csit.vo.LoginType;
import com.csit.vo.ServiceResult;
import com.octo.captcha.service.CaptchaService;
/**
 * @Description:用户登录
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-3-29
 * @Author lys
 */
@Controller
@Scope("prototype")
public class LoginAction extends BaseAction {

	private static final long serialVersionUID = -1379893197712674493L;
	
	private String userCode;
	private String userPwd;
	
	@Resource
	private CaptchaService captchaService;
	@Resource
	private TeacherService teacherService;
	@Resource
	private StudentService studentService;
	

	/**
	 * @Description: 用户端登录
	 * @Created Time: 2013-3-29 上午11:29:36
	 * @Author lys
	 */
	public void teacherLogin(){
		try {
			ServiceResult result = new ServiceResult(false);
			String captchaID = request.getSession().getId();
			String challengeResponse = StringUtils.upperCase(request.getParameter(JCaptchaEngine.CAPTCHA_INPUT_NAME));
			
			if (StringUtils.isEmpty(challengeResponse) || captchaService.validateResponseForID(captchaID, challengeResponse) == false) {
				result.setMessage("验证码错误");
				ajaxJson(result.toJSON());
				return;
			}
			// 根据用户名和密码判断是否允许登录
			Teacher loginTeacher = teacherService.login(userCode,MD5Util.getMD5(userPwd));
			if (null == loginTeacher) {
				result.setMessage("用户名或密码错误!!");
			}else{
				setSession(Teacher.LOGIN_TEACHERID,loginTeacher.getTeacherId());
				setSession(Teacher.LOGIN_TEACHERName,loginTeacher.getTeacherName());
				setSession(LoginType.LOGINTYPE,LoginType.TEACHER);
				result.setIsSuccess(true);
			}
			String ajaxString = result.toJSON();
			ajaxJson(ajaxString);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @Description: 学生登陆
	 * @Created Time: 2013-5-9 下午4:08:03
	 * @Author lys
	 */
	public void studentLogin(){
		try {
			ServiceResult result = new ServiceResult(false);
			String captchaID = request.getSession().getId();
			String challengeResponse = StringUtils.upperCase(request.getParameter(JCaptchaEngine.CAPTCHA_INPUT_NAME));
			
			if (StringUtils.isEmpty(challengeResponse) || captchaService.validateResponseForID(captchaID, challengeResponse) == false) {
				result.setMessage("验证码错误");
				ajaxJson(result.toJSON());
				return;
			}
			// 根据用户名和密码判断是否允许登录
			Student loginStudent = studentService.login(userCode,MD5Util.getMD5(userPwd));
			if (null == loginStudent) {
				result.setMessage("用户名或密码错误或未注册激活!!");
			}else{
				setSession(Student.LOGIN_ID,loginStudent.getStudentId());
				setSession(Student.LOGIN_NAME,loginStudent.getStudentName());
				setSession(LoginType.LOGINTYPE,LoginType.STUDENT);
				result.addData("studentId", loginStudent.getStudentId());
				result.addData("studentName", loginStudent.getStudentName());
				result.setIsSuccess(true);
			}
			String ajaxString = result.toJSON();
			ajaxJson(ajaxString);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public String getUserCode() {
		return userCode;
	}


	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}


	public String getUserPwd() {
		return userPwd;
	}


	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

}
