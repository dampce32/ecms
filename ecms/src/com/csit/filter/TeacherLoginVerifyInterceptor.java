package com.csit.filter;

import java.util.Map;

import com.csit.model.Teacher;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * @Description:教师登陆拦截器
 * 在要访问教师可访问资源前，判断教师是否已登陆
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-3-29
 * @Author lys
 */
public class TeacherLoginVerifyInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = -86246303854807787L;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		Map<String, Object > session =  invocation.getInvocationContext().getSession();
		
		
		Integer teacherLogin = (Integer) session.get(Teacher.LOGIN_TEACHERID);
		if (teacherLogin == null) {
			return "loginTea";
		}
		return invocation.invoke();
	}

}