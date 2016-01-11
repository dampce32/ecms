package com.csit.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.csit.dao.SerialNumberDAO;
import com.csit.model.SerialNumber;
import com.csit.service.SerialNumberService;
/**
 * @Description:序列表Service实现类
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-4-28
 * @Author lys
 */
@Service
public class SerialNumberServiceImpl extends
		BaseServiceImpl<SerialNumber, String> implements SerialNumberService {
	@Resource
	private SerialNumberDAO serialNumberDAO;
	
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.SerialNumberDAO#getNextSerial(java.lang.String)
	 */
	@Override
	public Integer getNextSerial(String tableName) {
		Integer nextSerial  = null;
//		  1.判断该表tableName是否存在--取出tableName的nextSerial
		SerialNumber oldModel = serialNumberDAO.load(tableName);
//		  	1.1如果不存在，在插入记录 tableName，2，并返回1
		if(oldModel==null){
			oldModel = new SerialNumber();
			oldModel.setTableName(tableName);
			oldModel.setNextSerial(2);
			serialNumberDAO.save(oldModel);
			nextSerial =  1;
		}else{
//		  	1.2如果存在，则取出nextSerial，并放回,更新下一个nextSerial
			nextSerial = oldModel.getNextSerial();
			oldModel.setNextSerial(oldModel.getNextSerial()+1);
		}
		return nextSerial;
	}
}
