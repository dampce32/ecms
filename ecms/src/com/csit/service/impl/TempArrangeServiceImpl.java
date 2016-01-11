package com.csit.service.impl;

import java.sql.Timestamp;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.csit.dao.TempArrangeDAO;
import com.csit.model.TempArrange;
import com.csit.model.TempArrangeId;
import com.csit.service.TempArrangeService;
import com.csit.vo.ServiceResult;
/**
 * @Description:考务安排临时表
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-5-14
 * @author jcf
 * @vesion 1.0
 */
@Service
public class TempArrangeServiceImpl extends
		BaseServiceImpl<TempArrange, TempArrangeId> implements
		TempArrangeService {
	@Resource
	private TempArrangeDAO tempArrangeDAO;
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.TempArrangeService#save(java.lang.Integer, java.sql.Timestamp, com.csit.model.TempArrange)
	 */
	@Override
	public ServiceResult save(Integer teacherId, Timestamp operateTime,
			TempArrange model) {
		ServiceResult result = new ServiceResult(false);
		
		if(model==null){
			result.setMessage("初始化临时表失败");
			return result;
		}
		model.getId().setTeacherId(teacherId);
		model.getId().setOperateTime(operateTime);
		tempArrangeDAO.save(model);
		
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.TempArrangeService#delete(java.lang.Integer, java.sql.Timestamp, com.csit.model.TempArrange)
	 */
	@Override
	public ServiceResult delete(Integer teacherId, Timestamp operateTime,
			Integer arrangeId) {
		ServiceResult result = new ServiceResult(false);
		
		TempArrangeId tempArrangeId=new TempArrangeId();
		tempArrangeId.setArrangeId(arrangeId);
		tempArrangeId.setOperateTime(operateTime);
		tempArrangeId.setTeacherId(teacherId);
		TempArrange oldModel = tempArrangeDAO.load(tempArrangeId);
		if(oldModel==null){
			result.setMessage("该临时表已不存在");
			return result;
		}
		tempArrangeDAO.delete(oldModel);
		
		result.setIsSuccess(true);
		return result;
	}

}
