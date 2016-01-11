package com.csit.action;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.csit.model.Goods;
import com.csit.model.Teacher;
import com.csit.service.GoodsService;
import com.csit.vo.ServiceResult;
import com.opensymphony.xwork2.ModelDriven;

/**
 * @Description:教材Action
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-6
 * @author jcf
 * @vesion 1.0
 */
@Controller
@Scope("prototype")
public class GoodsAction extends BaseAction implements ModelDriven<Goods> {

	private static final long serialVersionUID = -4648466664583512735L;
	private static final Logger logger = Logger.getLogger(GoodsAction.class);
	private Goods model = new Goods();
	@Resource
	private GoodsService goodsService;

	@Override
	public Goods getModel() {
		return model;
	}
	
	/**
	 * @Description: 保存教材
	 * @Create: 2013-6-6 下午02:55:33
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
			result = goodsService.save(model);
		} catch (Exception e) {
			result.setMessage("保存教材失败");
			logger.error("保存教材失败", e);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}

	/**
	 * @Description: 分页查询教材
	 * @Create: 2013-6-6 下午02:55:55
	 * @author jcf
	 * @update logs
	 */
	public void query() {
		String jsonArray = goodsService.query(model, page, rows);
		ajaxJson(jsonArray);
	}
	/**
	 * @Description: 批量删除教材
	 * @Create: 2013-6-6 下午02:56:04
	 * @author jcf
	 * @update logs
	 */
	public void mulDelete(){
		ServiceResult result = new ServiceResult(false);	
		try {
			result = goodsService.mulDelete(ids);
		} catch (Throwable e) {
			result.setMessage("批量删除教材失败");
			logger.error("批量删除教材失败", e);
		}
		ajaxJson(result.toJSON());
	}
	/**
	 * @Description: combobox查询
	 * @Create: 2013-6-6 下午02:56:17
	 * @author jcf
	 * @update logs
	 */
	public void queryCombobox() {
		try {
			String jsonString = goodsService.queryCombobox(model);
			ajaxJson(jsonString);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
