package com.csit.action;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.csit.model.RoleRight;
import com.csit.service.RoleRightService;
import com.csit.vo.ServiceResult;
import com.opensymphony.xwork2.ModelDriven;

/**
 * @Description:角色权限Action
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-4-17
 * @Author lys
 */
@Controller
@Scope("prototype")
public class RoleRightAction extends BaseAction implements ModelDriven<RoleRight> {
	private static final long serialVersionUID = 1794034975530960227L;
	private static final Logger logger = Logger.getLogger(RightAction.class);
	private RoleRight model = new RoleRight();

	@Resource
	private RoleRightService roleRightService;

	public RoleRight getModel() {
		return model;
	}
	
	/**
	 * @Description: 查询角色第一层权限
	 * @Create: 2012-10-27 下午9:01:47
	 * @author lys
	 * @update logs
	 * @throws Exception
	 */
	public void getRoot(){
		String jsonString = roleRightService.getRoot(model);
		ajaxJson(jsonString);
	}
	/**
	 * @Description: 查询树某节点下的子节点
	 * @Create: 2012-10-27 下午9:34:45
	 * @author lys
	 * @update logs
	 * @throws Exception
	 */
	public void getChildren(){
		String jsonString = roleRightService.getChildren(model);
		ajaxJson(jsonString);
	}
	/**
	 * @Description: 更新角色权限状态
	 * @Create: 2012-10-27 下午9:43:34
	 * @author lys
	 * @update logs
	 * @throws Exception
	 */
	public void updateState(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = roleRightService.updateState(model);
		} catch (Exception e) {
			result.setMessage("更新角色权限状态失败");
			logger.error("更新角色权限状态失败", e);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
}
