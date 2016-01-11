package com.csit.action;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.csit.model.Right;
import com.csit.model.Teacher;
import com.csit.service.RightService;
import com.csit.util.TreeUtil;
import com.csit.vo.ServiceResult;
import com.opensymphony.xwork2.ModelDriven;
/**
 * @Description:权限Action
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-4-16
 * @Author lys
 */
@Controller
@Scope("prototype")
public class RightAction extends BaseAction implements ModelDriven<Right> {
	private static final long serialVersionUID = 5144345768368157984L;
	private static final Logger logger = Logger.getLogger(RightAction.class);
	private Right model = new Right();

	@Resource
	private RightService rightService;

	public Right getModel() {
		return model;
	}
	/**
	 * @Description: 取得跟权限
	 * @Created Time: 2013-4-16 下午2:59:16
	 * @Author lys
	 */
	public void getRoot() {
		try {
			String jsonString = rightService.getRoot();
			ajaxJson(jsonString);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	/**
	 * @Description: 单击选择展开树节点
	 * @Create: 2012-10-27 下午3:21:25
	 * @author lys
	 * @update logs
	 * @throws Exception
	 */
	public void getTreeNode(){
		List<Right> children=rightService.getTreeNode(model);
		String jsonString = TreeUtil.toJSONRightList(children);
		ajaxJson(jsonString);
	}
	
	/**
	 * @Description: 取得树节点下的孩子节点
	 * @Create: 2012-10-27 上午9:46:10
	 * @author lys
	 * @update logs
	 * @throws Exception
	 */
	public void getTreeNodeChildren(){
		String jsonArray = rightService.getTreeNodeChildren(page, rows, model);
		ajaxJson(jsonArray);
	}
	
	/**
	 * @Description: 保存权限
	 * @Create: 2013-1-22 上午10:33:19
	 * @author lys
	 * @update logs
	 */
	public void save(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = rightService.save(model,getIntegerSession(Teacher.LOGIN_TEACHERID));
		} catch (Exception e) {
			result.setMessage("保存权限失败");
			logger.error("保存权限失败", e);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	
	/**
	 * @Description: 批量权限删除
	 * @Create: 2012-10-27 下午12:00:30
	 * @author lys
	 * @update logs
	 * @throws Exception
	 */
	public void mulDelete(){
		ServiceResult result = new ServiceResult(false);	
		try {
			result = rightService.mulDelete(ids);
		} catch (Throwable e) {
			result.setMessage("批量权限删除失败");
			logger.error("批量权限删除失败", e);
		}
		ajaxJson(result.toJSON());
	}
	
	/**
	 * @Description: 更新排序
	 * @Create: 2013-1-29 上午9:41:52
	 * @author lys
	 * @update logs
	 */
	public void updateArray(){
		String rightId = getParameter("rightId");
		String updateRightId = getParameter("updateRightId");
		ServiceResult result = new ServiceResult(false);
		try {
			result = rightService.updateArray(rightId,updateRightId);
		} catch (Exception e) {
			result.setMessage("更新排序失败");
			logger.error("更新排序失败", e);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	
	/**
	 * @Description: 批量修改权限状态
	 * @Created Time: 2013-2-28 下午10:57:47
	 * @Author lys
	 */
	public void mulUpdateState(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = rightService.mulUpdateState(ids,model);
		} catch (Exception e) {
			result.setMessage("批量修改权限状态失败");
			logger.error("批量修改权限状态失败", e);
			result.setIsSuccess(false);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
}
