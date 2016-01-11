package com.csit.action;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.csit.model.DataDictionary;
import com.csit.model.Teacher;
import com.csit.service.DataDictionaryService;
import com.csit.vo.ServiceResult;
import com.opensymphony.xwork2.ModelDriven;
/**
 * @Description:数据字典Action
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-4-18
 * @Author lys
 */
@Controller
@Scope("prototype")
public class DataDictionaryAction extends BaseAction implements
		ModelDriven<DataDictionary> {

	private static final long serialVersionUID = 4748242180514154024L;
	private static final Logger logger = Logger.getLogger(DataDictionaryAction.class);
	private DataDictionary model = new DataDictionary();

	@Resource
	private DataDictionaryService dataDictionaryService;

	public DataDictionary getModel() {
		return model;
	}
	
	/**
	 * @Description: 保存数据字典
	 * @Create: 2013-1-22 上午10:33:19
	 * @author lys
	 * @update logs
	 */
	public void save(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = dataDictionaryService.save(model,getIntegerSession(Teacher.LOGIN_TEACHERID));
		} catch (Exception e) {
			result.setMessage("保存数据字典失败");
			logger.error("保存数据字典失败", e);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	
	/**
	 * @Description: 分页查询数据字典
	 * @Create: 2012-10-27 上午9:46:10
	 * @author lys
	 * @update logs
	 * @throws Exception
	 */
	public void query(){
		String jsonArray = dataDictionaryService.query(page, rows, model);
		ajaxJson(jsonArray);
	}
	
	/**
	 * @Description: 批量数据字典删除
	 * @Create: 2012-10-27 下午12:00:30
	 * @author lys
	 * @update logs
	 * @throws Exception
	 */
	public void mulDelete(){
		ServiceResult result = new ServiceResult(false);	
		try {
			result = dataDictionaryService.mulDelete(ids);
		} catch (Throwable e) {
			result.setMessage("批量数据字典删除失败");
			logger.error("批量数据字典删除失败", e);
		}
		ajaxJson(result.toJSON());
	}
	
	/**
	 * @Description: 批量修改数据字典状态
	 * @Created Time: 2013-2-28 下午10:57:47
	 * @Author lys
	 */
	public void mulUpdateState(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = dataDictionaryService.mulUpdateState(ids,model,getIntegerSession(Teacher.LOGIN_TEACHERID));
		} catch (Exception e) {
			result.setMessage("批量修改数据字典状态失败");
			logger.error("批量修改数据字典状态失败", e);
			result.setIsSuccess(false);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	/**
	 * 
	 * @Description: combobox查询
	 * @param
	 * @Create: 2013-3-1 下午01:53:40
	 * @author yk
	 * @update logs
	 * @return
	 * @throws Exception
	 */
	public void queryCombobox() {
		try {
			String jsonString = dataDictionaryService.queryCombobox(model);
			ajaxJson(jsonString);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @Description: combobox查询
	 * @param
	 * @Create: 2013-3-1 下午01:53:40
	 * @author yk
	 * @update logs
	 * @return
	 * @throws Exception
	 */
	public void queryTypeCombobox() {
		try {
			String jsonString = dataDictionaryService.queryTypeCombobox(model);
			ajaxJson(jsonString);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
