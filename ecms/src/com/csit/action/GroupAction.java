package com.csit.action;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.csit.model.Group;
import com.csit.service.GroupService;
import com.csit.vo.ServiceResult;
import com.opensymphony.xwork2.ModelDriven;

/**
 * @Description:参赛组别Action
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-5-28
 * @author jcf
 * @vesion 1.0
 */
@Controller
@Scope("prototype")
public class GroupAction extends BaseAction implements ModelDriven<Group> {

	private static final long serialVersionUID = -4648466664583512735L;
	private static final Logger logger = Logger.getLogger(GroupAction.class);
	private Group model = new Group();
	@Resource
	private GroupService groupService;

	@Override
	public Group getModel() {
		return model;
	}
	
	/**
	 * @Description: 保存参赛组别
	 * @Create: 2013-5-28 下午03:56:27
	 * @author jcf
	 * @update logs
	 */
	public void save() {
		ServiceResult result = new ServiceResult(false);
		try {
			result = groupService.save(model);
		} catch (Exception e) {
			result.setMessage("保存参赛组别失败");
			logger.error("保存参赛组别失败", e);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}

	/**
	 * @Description: 分页查询参赛组别
	 * @Create: 2013-5-28 下午03:56:16
	 * @author jcf
	 * @update logs
	 */
	public void query() {
		String groupIds=getParameter("groupIds");
		String jsonArray = groupService.query(model, page, rows,groupIds);
		ajaxJson(jsonArray);
	}
	/**
	 * @Description: 批量删除参赛组别
	 * @Create: 2013-5-28 下午05:12:27
	 * @author jcf
	 * @update logs
	 */
	public void mulDelete(){
		ServiceResult result = new ServiceResult(false);	
		try {
			result = groupService.mulDelete(ids);
		} catch (Throwable e) {
			result.setMessage("批量删除参赛组别失败");
			logger.error("批量删除参赛组别失败", e);
		}
		ajaxJson(result.toJSON());
	}
	/**
	 * @Description: 批量修改数据字典状态
	 * @Created Time: 2013-2-28 下午10:57:47
	 * @Author lys
	 */
	public void mulUpdateStatus(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = groupService.mulUpdateStatus(ids,model);
		} catch (Exception e) {
			result.setMessage("批量修改参赛组别状态失败");
			logger.error("批量修改参赛组别状态失败", e);
			result.setIsSuccess(false);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	/**
	 * @Description: 更新排序
	 * @Create: 2013-5-29 上午09:21:53
	 * @author jcf
	 * @update logs
	 */
	public void updateArray(){
		Integer updateGroupId = Integer.parseInt(getParameter("updateGroupId"));
		ServiceResult result = new ServiceResult(false);
		try {
			result = groupService.updateArray(model.getGroupId(),updateGroupId);
		} catch (Exception e) {
			result.setMessage("更新排序失败");
			logger.error("更新排序失败", e);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	/**
	 * @Description: combobox查询
	 * @Create: 2013-5-29 上午10:23:52
	 * @author jcf
	 * @update logs
	 */
	public void queryCombobox() {
		try {
			String jsonString = groupService.queryCombobox(model);
			ajaxJson(jsonString);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
