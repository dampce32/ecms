package com.csit.filter;

import com.csit.model.Student;
import com.csit.model.Teacher;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
/**
 * @Description:学生教师都可以登录
 * 
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-3-29
 * @Author lys
 */
public class StuTeaLoginVerifyInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = -86246303854807787L;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		Integer studentLogin = (Integer) invocation.getInvocationContext().getSession().get(Student.LOGIN_ID);
		Integer userLogin = (Integer) invocation.getInvocationContext().getSession().get(Teacher.LOGIN_TEACHERID);
		
		if (userLogin == null && studentLogin==null) {
			return "loginStu";
		}
		return invocation.invoke();
	}

}