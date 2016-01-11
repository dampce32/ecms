package com.csit.action;

import java.io.File;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.csit.model.MsgBlackList;
import com.csit.service.MsgBlackListService;
import com.opensymphony.xwork2.ModelDriven;
/**
 * @Description:短信黑字典Action
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-7-22
 * @author lys
 * @vesion 1.0
 */
@Controller
@Scope("prototype")
public class MsgBlackListAction extends BaseAction implements
		ModelDriven<MsgBlackList> {

	private static final long serialVersionUID = -3789111829337820723L;
	MsgBlackList model = new MsgBlackList();
	@Resource
	private MsgBlackListService msgBlackListService;
	
	private File file;

	private String fileFileName;

	private String fileContentType;
	
	@Override
	public MsgBlackList getModel() {
		return model;
	}
	/**
	 * @Description: 上传txt文件
	 * @Created: 2013-7-22 下午4:57:51
	 * @author lys
	 * @update logs
	 * @throws Exception
	 */
	public void uploadTxt(){
		
		msgBlackListService.uploadTxt(file);
	}
	/**
	 * @Description: 分页查询黑字
	 * @Created Time: 2013-5-29 上午11:11:09
	 * @Author lys
	 */
	public void query() {
		String jsonArray = msgBlackListService.query(model, page, rows);
		ajaxJson(jsonArray);
	}
	
	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	public String getFileContentType() {
		return fileContentType;
	}

	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}
	
	
	
}
