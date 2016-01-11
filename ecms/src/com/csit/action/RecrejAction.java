package com.csit.action;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.csit.model.Recrej;
import com.csit.model.Teacher;
import com.csit.service.RecrejService;
import com.csit.vo.ServiceResult;
import com.opensymphony.xwork2.ModelDriven;

/**
 * @Description:入库出库Action
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-6
 * @author jcf
 * @vesion 1.0
 */
@Controller
@Scope("prototype")
public class RecrejAction extends BaseAction implements ModelDriven<Recrej> {

	private static final long serialVersionUID = -4648466664583512735L;
	private static final Logger logger = Logger.getLogger(RecrejAction.class);
	private Recrej model = new Recrej();
	@Resource
	private RecrejService recrejService;

	@Override
	public Recrej getModel() {
		return model;
	}
	
	/**
	 * @Description: 保存入库出库
	 * @Create: 2013-6-6 下午04:48:34
	 * @author jcf
	 * @update logs
	 */
	public void save() {
		ServiceResult result = new ServiceResult(false);
		try {
			Integer teacherId = getIntegerSession(Teacher.LOGIN_TEACHERID);
			Teacher teacher=new Teacher();
			teacher.setTeacherId(teacherId);
			model.setTeacher(teacher);
			result = recrejService.save(model);
		} catch (Exception e) {
			result.setMessage("保存入库出库失败"+e.getMessage());
			logger.error("保存入库出库失败", e);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}

	/**
	 * @Description: 分页查询入库出库
	 * @Create: 2013-6-6 下午04:48:42
	 * @author jcf
	 * @update logs
	 */
	public void query() {
		String jsonArray = recrejService.query(model, page, rows);
		ajaxJson(jsonArray);
	}
	/**
	 * @Description: 批量删除入库出库
	 * @Create: 2013-6-6 下午04:48:50
	 * @author jcf
	 * @update logs
	 */
	public void mulDelete(){
		ServiceResult result = new ServiceResult(false);	
		try {
			result = recrejService.mulDelete(ids);
		} catch (Throwable e) {
			result.setMessage("批量删除入库出库失败");
			logger.error("批量删除入库出库失败", e);
		}
		ajaxJson(result.toJSON());
	}
	
}
