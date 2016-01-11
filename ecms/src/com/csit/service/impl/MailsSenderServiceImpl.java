package com.csit.service.impl;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.csit.dao.ContactsDAO;
import com.csit.dao.MailsSenderDAO;
import com.csit.dao.PictureDAO;
import com.csit.model.Contacts;
import com.csit.model.MailsSender;
import com.csit.model.Picture;
import com.csit.service.MailsSenderService;
import com.csit.util.FileUtil;
import com.csit.util.JSONUtil;
import com.csit.vo.GobelConstants;
import com.csit.vo.ServiceResult;
/**
 * 
 * @Description: 邮件发送Service实现类
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-7-9
 * @author yk
 * @vesion 1.0
 */
@Service
public class MailsSenderServiceImpl extends
		BaseServiceImpl<MailsSender, Integer> implements MailsSenderService {

	@Resource
	private MailsSenderDAO mailsSenderDAO;
	@Resource
	private PictureDAO pictureDAO;
	@Resource
	private ContactsDAO contactsDAO;
	
	
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.MailsSenderService#save(com.csit.model.MailsSender)
	 */
	@Override
	public ServiceResult save(MailsSender mailsSender,String names,String emails) {
		ServiceResult result = new ServiceResult(false);
		if(StringUtils.isEmpty(mailsSender.getReceiveIDs())){
			result.setMessage("接收人不能为空");
			return result;
		}
		String[] receiveIDArray = mailsSender.getReceiveIDs().split(",");
		String[] nameArray = names.split(",");
		String[] emailArray = emails.split(",");
		
		Integer[] contactsIdArray = new Integer[receiveIDArray.length];
		int index = 0;
		for(String contactsId : receiveIDArray){
			Contacts model = new Contacts();
			model.setEmail(emailArray[index]);
			model.setName(nameArray[index]);
			model.setTeacher(mailsSender.getTeacher());
			//不可见的通讯录
			if("-1".equals(contactsId)){
				Contacts contacts = contactsDAO.getContacts(model);
				//如果不存在
				if(contacts==null){
					model.setVisible(0);
					contactsDAO.save(model);
					contactsIdArray[index]=model.getContactsId();
				}else{
					contactsIdArray[index]=contacts.getContactsId();
				}
			//可见的通讯录	
			}else if("0".equals(contactsId)){
				Contacts contacts = contactsDAO.getContacts(model);
				//如果不存在
				if(contacts==null){
					model.setVisible(1);
					contactsDAO.save(model);
					contactsIdArray[index]=model.getContactsId();
				}else{
					contactsIdArray[index]=contacts.getContactsId();
				}
			//通讯录发送	
			}else{
				contactsIdArray[index]=Integer.parseInt(contactsId);
			}
			index++;
		}
		
		StringBuilder receiveIDs = new StringBuilder();
		for(String receiveID : emailArray){
			receiveIDs.append(receiveID+",");
		}
		mailsSender.setReceiveIDs(receiveIDs.substring(0, receiveIDs.length()-1));
		mailsSender.setErrorCount(0);
		if(mailsSender.getSendTime()==null){
			mailsSender.setSendTime(new Date());
		}
		mailsSender.setMailType(1);
		mailsSenderDAO.save(mailsSender);
		
		result.setIsSuccess(true);
		return result;
	}

	@Override
	public String mulUpload(List<File> filedata,
			List<String> filedataFileName, List<String> filedataContentType,
			String fileBasePath) {
		
		String msg ="";
		int fileSize = filedata.size();
		if(filedata!=null&&fileSize!=0){
			for (int i = 0; i < fileSize; i++) {
				File file = filedata.get(i);
				//文件扩展名
				String extention = FilenameUtils.getExtension(filedataFileName.get(i));
				
				//创建文件夹
				File dirs = new File(fileBasePath);
				if(!dirs.exists()){
					dirs.mkdirs();
				}
				//保存路径
				Picture picture = new Picture();
				picture.setPicturePath(GobelConstants.MailAttachment_BASEPATH);
				pictureDAO.save(picture);
				
				String fileName = picture.getPictureId()+"."+extention;
				//保存文件名
				picture.setPictureName(fileName);
				
				//文件路径
				String targetPath=fileBasePath+File.separator+fileName;
				//创建文件
				File imageFile = new File(targetPath);
				//保存文件
				FileUtil.saveFile(file, imageFile);
				if(StringUtils.isEmpty(msg)){
					msg+= picture.getPictureId();
				}else{
					msg+= ","+picture.getPictureId();
				}
			}
		}
		return msg;
	}

	@Override
	public String query(MailsSender model, Integer page, Integer rows) {
		List<MailsSender> list = mailsSenderDAO.query(model,page,rows);
		Long total = mailsSenderDAO.getTotalCount(model);
		String[] properties = {"mailsSenderId","teacher.teacherName","receiveIDs",
				"title","content","sendTime"};
		return JSONUtil.toJson(list,properties,total);
	}

	@Override
	public String getReceiver(String receiveIDs) {
		List<Map<String, Object>> listMap = mailsSenderDAO.queryReceiver(receiveIDs);
		String jsonArray = JSONUtil.toJsonFromListMap(listMap);
		return jsonArray;
	}
	
}
