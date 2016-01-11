package com.csit.service;

import java.io.File;
import java.util.List;

import com.csit.model.MailsSender;
import com.csit.vo.ServiceResult;

/**
 * 
 * @Description: 邮件发送Service
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-7-9
 * @author yk
 * @vesion 1.0
 */
public interface MailsSenderService extends BaseService<MailsSender, Integer> {
	/**
	 * 
	 * @Description: 保存邮件
	 * @Create: 2013-7-9 上午10:36:53
	 * @author yk
	 * @update logs
	 * @param model
	 * @return
	 */
	ServiceResult save(MailsSender model,String names,String emails);
	/**
	 * 
	 * @Description: 批量上传附件 
	 * @Create: 2013-7-10 上午09:35:37
	 * @author yk
	 * @update logs
	 * @param filedata
	 * @param filedataFileName
	 * @param filedataContentType
	 * @param pictureBasePath
	 * @return
	 */
	String mulUpload(List<File> filedata, List<String> filedataFileName,
			List<String> filedataContentType, String fileBasePath);
	/**
	 * 
	 * @Description: 分页查询邮件发送
	 * @Create: 2013-7-10 下午05:51:36
	 * @author yk
	 * @update logs
	 * @param model
	 * @param page
	 * @param rows
	 * @return
	 */
	String query(MailsSender model, Integer page, Integer rows);
	/**
	 * 
	 * @Description: 获取收件人 
	 * @Create: 2013-7-12 上午10:21:29
	 * @author yk
	 * @update logs
	 * @param receiveIDs
	 * @return
	 */
	String getReceiver(String receiveIDs);
}
