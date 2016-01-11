package com.csit.action;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.csit.model.TeacherRole;
import com.csit.service.TeacherRoleService;
import com.csit.vo.ServiceResult;
import com.opensymphony.xwork2.ModelDriven;
/**
 * @Description:教师角色Action
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-4-17
 * @Author lys
 */
@Controller
@Scope("prototype")
public class TeacherRoleAction extends BaseAction implements
		ModelDriven<TeacherRole> {

	private static final long serialVersionUID = -2566642977498892773L;
	private static final Logger logger = Logger.getLogger(TeacherRoleAction.class);
	private TeacherRole model = new TeacherRole();

	@Resource
	private TeacherRoleService teacherRoleService;

	public TeacherRole getModel() {
		return model;
	}
	
	/**
	 * @Description: 查询教师的角色
	 * @Create: 2012-10-28 下午9:50:51
	 * @author lys
	 * @update logs
	 * @throws Exception
	 */
	public void queryRole(){
		String jsonArray = teacherRoleService.queryRole(model);
		ajaxJson(jsonArray);
	}
	/**
	 * @Description: 更新教师角色
	 * @Create: 2012-10-28 下午11:09:42
	 * @author lys
	 * @update logs
	 * @throws Exception
	 */
	public void updateRole(){
		ServiceResult result = new ServiceResult(false);
		try {
			result =teacherRoleService.updateRole(model,ids,oldIds);
		} catch (RuntimeException e) {
			result.setMessage("更新教师角色失败");
			logger.error("更新教师角色失败", e);
		}
		ajaxJson(result.toJSON());
	}
}
