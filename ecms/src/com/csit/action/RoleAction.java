package com.csit.action;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.csit.model.Role;
import com.csit.model.Teacher;
import com.csit.service.RoleService;
import com.csit.vo.ServiceResult;
import com.opensymphony.xwork2.ModelDriven;
/**
 * @Description:角色Action
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-4-17
 * @Author lys
 */
@Controller
@Scope("prototype")
public class RoleAction extends BaseAction implements ModelDriven<Role> {

	private static final long serialVersionUID = -3899336650807315718L;
	private static final Logger logger = Logger.getLogger(RoleAction.class);
	private Role model = new Role();

	@Resource
	private RoleService roleService;

	public Role getModel() {
		return model;
	}
	
	/**
	 * @Description: 保存角色
	 * @Create: 2013-1-22 上午10:33:19
	 * @author lys
	 * @update logs
	 */
	public void save(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = roleService.save(model,getIntegerSession(Teacher.LOGIN_TEACHERID));
		} catch (Exception e) {
			result.setMessage("保存角色失败");
			logger.error("保存角色失败", e);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	
	/**
	 * @Description: 分页查询角色
	 * @Create: 2012-10-27 上午9:46:10
	 * @author lys
	 * @update logs
	 * @throws Exception
	 */
	public void query(){
		String jsonArray = roleService.query(page, rows, model);
		ajaxJson(jsonArray);
	}
	
	/**
	 * @Description: 批量角色删除
	 * @Create: 2012-10-27 下午12:00:30
	 * @author lys
	 * @update logs
	 * @throws Exception
	 */
	public void mulDelete(){
		ServiceResult result = new ServiceResult(false);	
		try {
			result = roleService.mulDelete(ids);
		} catch (Throwable e) {
			result.setMessage("批量角色删除失败");
			logger.error("批量角色删除失败", e);
		}
		ajaxJson(result.toJSON());
	}
	
	/**
	 * @Description: 批量修改角色状态
	 * @Created Time: 2013-2-28 下午10:57:47
	 * @Author lys
	 */
	public void mulUpdateState(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = roleService.mulUpdateState(ids,model);
		} catch (Exception e) {
			result.setMessage("批量修改角色状态失败");
			logger.error("批量修改角色状态失败", e);
			result.setIsSuccess(false);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
}
