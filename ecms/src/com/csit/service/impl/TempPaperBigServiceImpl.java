package com.csit.service.impl;

import java.sql.Timestamp;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.csit.dao.TempPaperBigDAO;
import com.csit.model.TempPaperBig;
import com.csit.model.TempPaperBigId;
import com.csit.service.SerialNumberService;
import com.csit.service.TempPaperBigService;
import com.csit.vo.SerialNumberTabelName;
import com.csit.vo.ServiceResult;
/**
 * @Description:临时试卷大题Service实现类
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-5-4
 * @Author lys
 */
@Service
public class TempPaperBigServiceImpl extends
		BaseServiceImpl<TempPaperBig, TempPaperBigId> implements
		TempPaperBigService {
	@Resource
	private TempPaperBigDAO tempPaperBigDAO;
	@Resource
	private SerialNumberService serialNumberService;
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.TempPaperBigService#save(java.lang.Integer, java.sql.Timestamp, com.csit.model.TempPaperBig)
	 */
	@Override
	public ServiceResult save(Integer teacherId, Timestamp operateTime,
			TempPaperBig model) {
		ServiceResult result = new ServiceResult(false);
		
		if(model==null){
			result.setMessage("请添加大题信息");
			return result;
		}
		
		if(model.getId()==null||model.getId().getPaperId()==null){
			result.setMessage("请选择大题所属的试卷");
			return result;
		}
		
		if(model.getArray()==null){
			result.setMessage("请指定大题的排序");
			return result;
		}
		
		model.getId().setTeacherId(teacherId);
		model.getId().setOperateTime(operateTime);
		
		//1.从序列表中取得，临时表的id
		Integer bigId = serialNumberService.getNextSerial(SerialNumberTabelName.PAPER_BIG);
		model.getId().setBigId(bigId);
		tempPaperBigDAO.save(model);
		
		result.addData("bigId", bigId);
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.TempPaperBigService#delete(java.lang.Integer, java.sql.Timestamp, com.csit.model.TempPaperBig)
	 */
	@Override
	public ServiceResult delete(Integer teacherId, Timestamp operateTime,
			TempPaperBig model) {
		ServiceResult result = new ServiceResult(false);
		if(model==null){
			result.setMessage("请选择要删除的大题");
			return result;
		}
		if(model.getId()==null||model.getId().getPaperId()==null){
			result.setMessage("请选择大题所属的试卷");
			return result;
		}
		if(model.getId().getBigId()==null){
			result.setMessage("请选择要删除的大题");
			return result;
		}
		
		model.getId().setTeacherId(teacherId);
		model.getId().setOperateTime(operateTime);
		
		TempPaperBig oldModel = tempPaperBigDAO.load(model.getId());
		tempPaperBigDAO.delete(oldModel);
		
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.TempPaperBigService#clearSmall(java.lang.Integer, java.sql.Timestamp, com.csit.model.TempPaperBig)
	 */
	@Override
	public ServiceResult clearSmall(Integer teacherId, Timestamp operateTime,
			TempPaperBig model) {
		ServiceResult result = new ServiceResult(false);
		if(model==null){
			result.setMessage("请选择要清空小题的大题");
			return result;
		}
		if(model.getId()==null||model.getId().getPaperId()==null){
			result.setMessage("请选择大题所属的试卷");
			return result;
		}
		if(model.getId().getBigId()==null){
			result.setMessage("请选择清空小题的大题");
			return result;
		}
		
		model.getId().setTeacherId(teacherId);
		model.getId().setOperateTime(operateTime);
		
		tempPaperBigDAO.clearSmall(model);
		
		result.setIsSuccess(true);
		return result;
	}

}
