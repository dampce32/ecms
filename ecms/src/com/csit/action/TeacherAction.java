package com.csit.action;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.csit.model.Teacher;
import com.csit.service.TeacherService;
import com.csit.vo.ServiceResult;
import com.opensymphony.xwork2.ModelDriven;

/**
 * @Description:教师Action
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-4-16
 * @Author lys
 */
@Controller
@Scope("prototype")
public class TeacherAction extends BaseAction implements ModelDriven<Teacher> {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(TeacherAction.class);
	private Teacher model = new Teacher();

	@Resource
	private TeacherService teacherService;

	public Teacher getModel() {
		return model;
	}
	
	/**
	 * @Description: 取得主界面下的跟Url权限
	 * @Create: 2012-11-15 下午11:33:47
	 * @author lys
	 * @update logs
	 */
	public void getRootUrlRight(){
		try {
			/*String path = "rightTree.json";
			String pathname = getRequest().getSession().getServletContext().getRealPath(path);
			File file = new File(pathname);
			String tree = "";
			try {
				tree = FileUtils.readFileToString(file, "utf-8");
			} catch (IOException e) {
				e.printStackTrace();
			}
			ajaxJson(tree);*/
			
			/**/String jsonArray = teacherService.getRootUrlRight(getIntegerSession(Teacher.LOGIN_TEACHERID));
			ajaxJson(jsonArray);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @Description: 取得权限下的子权限
	 * @Create: 2012-11-15 下午11:33:47
	 * @author lys
	 * @update logs
	 */
	public void getChildrenUrlRight(){
		try {
			String rightId = getParameter("rightId");
			String jsonArray = teacherService.getChildrenUrlRight(getIntegerSession(Teacher.LOGIN_TEACHERID),rightId);
			ajaxJson(jsonArray);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @Description: 保存教师
	 * @Created Time: 2013-2-28 上午9:51:26
	 * @Author lys
	 */
	public void save(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = teacherService.save(model);
		} catch (Throwable e) {
			result.setMessage("保存教师失败");
			logger.error("保存教师失败", e);
		}
		String ajaxString = result.toJSON();
		ajaxJson(ajaxString);
	}
	/**
	 * @Description: 分页教师列表
	 * @Create: 2012-10-28 上午9:14:13
	 * @author lys
	 * @update logs
	 * @throws Exception
	 */
	public void query(){
		String jsonArray = teacherService.query(page, rows, model);
		ajaxJson(jsonArray);
	}
	
	
	/**
	 * @Description: 批量教师删除
	 * @Create: 2012-10-27 下午12:00:30
	 * @author lys
	 * @update logs
	 * @throws Exception
	 */
	public void mulDelete(){
		ServiceResult result = new ServiceResult(false);	
		try {
			result = teacherService.mulDelete(ids);
		} catch (Throwable e) {
			if(e instanceof org.springframework.dao.DataIntegrityViolationException){
				result.setMessage("其他模块已使用要删除的教师信息了");
			}else{
				result.setMessage("批量教师删除失败");
			}
			logger.error("批量教师删除失败", e);
		}
		ajaxJson(result.toJSON());
	}
	/**
	 * @Description: 批量重置密码
	 * @Created Time: 2013-7-2 下午5:48:51
	 * @Author lys
	 */
	public void mulResetPwd(){
		ServiceResult result = new ServiceResult(false);	
		try {
			result = teacherService.mulResetPwd(ids);
		} catch (Throwable e) {
			result.setMessage("批量重置密码失败");
			logger.error("批量重置密码失败", e);
		}
		ajaxJson(result.toJSON());
	}
	
	
	/**
	 * @Description: 批量修改教师状态
	 * @Created Time: 2013-2-28 下午10:57:47
	 * @Author lys
	 */
	public void mulUpdateState(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = teacherService.mulUpdateState(ids,model);
		} catch (Exception e) {
			result.setMessage("批量修改教师状态失败");
			logger.error("批量修改教师状态失败", e);
			result.setIsSuccess(false);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	
	/**
	 * @Description: 查询教师的跟权限
	 * @Create: 2012-10-28 下午11:55:21
	 * @author lys
	 * @update logs
	 * @throws Exception
	 */
	public void queryRootRight(){
		try {
			String jsonArray = teacherService.queryRootRight(model);
			ajaxJson(jsonArray);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * @Description: 取得教师的子权限
	 * @Created Time: 2013-4-17 下午8:05:58
	 * @Author lys
	 */
	public void queryChildrenRight(){
		try {
			if(model==null||model.getTeacherId()==null){
				model.setTeacherId(getIntegerSession(Teacher.LOGIN_TEACHERID));
			}
			String rightId = getParameter("rightId");
			String jsonArray = teacherService.queryChildrenRight(model,rightId);
			ajaxJson(jsonArray);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @Description: 退出系统
	 * @Create: 2012-11-15 上午11:07:41
	 * @author lys
	 * @update logs
	 */
	public void logout(){
		getSession().clear();
		ServiceResult result = new ServiceResult(true);
		ajaxJson(result.toJSON());
	}
	/**
	 * @Description: 修改密码
	 * @Created: 2012-10-29 上午9:29:35
	 * @Author lys
	 */
	public void modifyPwd(){
		ServiceResult result = new ServiceResult(false);
		Integer teacherId = null;
		if(getSession(Teacher.LOGIN_TEACHERID)!=null){
			teacherId = getIntegerSession(Teacher.LOGIN_TEACHERID);
		}
		model.setTeacherId(teacherId);
		String newTeacherPwd = getParameter("newTeacherPwd");
		try {
			result = teacherService.modifyPwd(model,newTeacherPwd);
		} catch (Exception e) {
			result.setMessage("修改密码失败");
			logger.error("修改密码失败", e);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	
	/**
	 * @Description: 取得用户个人信息
	 * @Create: 2013-1-22 下午1:54:16
	 * @author lys
	 * @update logs
	 */
	public void getSelfInfor(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = teacherService.getSelfInfor(getIntegerSession(Teacher.LOGIN_TEACHERID));
		} catch (Throwable e) {
			result.setMessage("取得用户个人信息失败");
			logger.error("取得用户个人信息失败", e);
		}
		String ajaxString = result.toJSON();
		ajaxJson(ajaxString);
	}
	/**
	 * @Description: 更新用户个人信息
	 * @Create: 2013-1-22 下午2:06:57
	 * @author lys
	 * @update logs
	 */
	public void updateSelfInfo(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = teacherService.updateSelfInfo(getIntegerSession(Teacher.LOGIN_TEACHERID),model);
		} catch (Throwable e) {
			result.setMessage("更新用户个人信息失败");
			logger.error("更新用户个人信息失败", e);
		}
		String ajaxString = result.toJSON();
		ajaxJson(ajaxString);
	}
	/**
	 * 
	 * @Description: 用于Combobox查询
	 * @Create: 2013-4-26 下午04:23:21
	 * @author jcf
	 * @update logs
	 */
	public void queryCombobox(){
		try {
			String jsonString = teacherService.queryCombobox();
			ajaxJson(jsonString);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * @Description: 取得当前操作教师
	 * @Created Time: 2013-4-29 下午8:12:19
	 * @Author lys
	 */
	public void getCurr(){
		ServiceResult result = new ServiceResult(true);
		Integer teacherId = getIntegerSession(Teacher.LOGIN_TEACHERID);
		String teacherName=getSession().get(Teacher.LOGIN_TEACHERName).toString();
		result.addData("teacherName", teacherName);
		result.addData("teacherId", teacherId);
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	public void checkRight(){
		ServiceResult result = new ServiceResult(false);
		try {
			String competitionId=getParameter("competitionId");
			Integer teacherId = getIntegerSession(Teacher.LOGIN_TEACHERID);
			result = teacherService.checkRight(teacherId,competitionId);
		} catch (Throwable e) {
			result.setMessage("查询教师权限失败");
			logger.error("查询教师权限失败", e);
		}
		String ajaxString = result.toJSON();
		ajaxJson(ajaxString);
	}
}
