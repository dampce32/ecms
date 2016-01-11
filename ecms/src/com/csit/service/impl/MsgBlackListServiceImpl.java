package com.csit.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.csit.dao.MsgBlackListDAO;
import com.csit.model.MsgBlackList;
import com.csit.service.MsgBlackListService;
import com.csit.util.JSONUtil;
/**
 * @Description:短信黑字典Service实现类
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-7-22
 * @author lys
 * @vesion 1.0
 */
@Service
public class MsgBlackListServiceImpl extends
		BaseServiceImpl<MsgBlackList, Integer> implements MsgBlackListService {
	@Resource
	private MsgBlackListDAO msgBlackListDAO;
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.MsgBlackListService#uploadTxt(java.io.File)
	 */
	@Override
	public void uploadTxt(File file) {
		try {
			BufferedReader br=new BufferedReader(new FileReader(file));
			String r=br.readLine();
			while(r!=null){
				MsgBlackList msgBlackList = new MsgBlackList();
				msgBlackList.setBlackCode(r);
				msgBlackListDAO.save(msgBlackList);
				r=br.readLine();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.MsgBlackListService#query(com.csit.model.MsgBlackList, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public String query(MsgBlackList model, Integer page, Integer rows) {
		List<MsgBlackList> list = msgBlackListDAO.query(model, page, rows);
		Long total = msgBlackListDAO.getTotalCount(model);

		String[] properties = { "msgBlackListId", "blackCode"};
		String ajaxString = JSONUtil.toJson(list, properties, total);
		return ajaxString;
	}

}
