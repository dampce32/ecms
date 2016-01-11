package com.csit.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.csit.dao.SubjectDAO;
import com.csit.dao.TempPaperBigSmallDAO;
import com.csit.model.Subject;
import com.csit.model.TempPaperBigSmall;
import com.csit.model.TempPaperBigSmallId;
import com.csit.service.SerialNumberService;
import com.csit.service.TempPaperBigSmallService;
import com.csit.util.JSONUtil;
import com.csit.util.StringUtil;
import com.csit.vo.SerialNumberTabelName;
import com.csit.vo.ServiceResult;
/**
 * @Description:
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-4-30
 * @Author lys
 */
@Service
public class TempPaperBigSmallServiceImpl extends
		BaseServiceImpl<TempPaperBigSmall, TempPaperBigSmallId> implements
		TempPaperBigSmallService {
	@Resource
	private TempPaperBigSmallDAO tempPaperBigSmallDAO;
	@Resource
	private SerialNumberService serialNumberService;
	@Resource
	private SubjectDAO subjectDAO;
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.TempPaperBigSmallService#saveMix(java.lang.Integer, java.sql.Timestamp, com.csit.model.TempPaperBigSmall)
	 */
	@Override
	public ServiceResult saveMix(Integer teacherId, Timestamp operateTime,
			TempPaperBigSmall model) {
		ServiceResult result = new ServiceResult(false);
		if(model==null||model.getId()==null){
			result.setMessage("请选择试题");
			return result;
		}
		if(model.getId().getPaperId()==null){
			result.setMessage("请选择试卷");
			return result;
		}
		if(model.getId().getBigId()==null){
			result.setMessage("请选择试卷大题");
			return result;
		}
		if(model.getArray()==null){
			result.setMessage("请指定排序");
			return result;
		}
		if(model.getScore()==null){
			result.setMessage("请指定分数");
			return result;
		}
		
		model.getId().setTeacherId(teacherId);
		model.getId().setOperateTime(operateTime);
		
		if(model.getId().getSmallId()==null){//新增
			//1.从序列表中取得，临时表的id
			Integer smallId = serialNumberService.getNextSerial(SerialNumberTabelName.PAPER_BIG_SMALL);
			model.getId().setSmallId(smallId);
			model.setIsOptionMix(true);
			model.setDifficulty("中");
			tempPaperBigSmallDAO.save(model);
		}
		result.addData("smallId", model.getId().getSmallId());
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.TempPaperBigSmallService#mulUpdate(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.Integer, java.sql.Timestamp)
	 */
	@Override
	public ServiceResult mulUpdate(String paperId, String bigId,String nextBigId,
			String smallIds, String isOptionMixs, String scores,
			String difficultys, Integer teacherId, Timestamp operateTime) {
		ServiceResult result = new ServiceResult(false);
		Integer[] smallIdArray = StringUtil.splitToInteger(smallIds);
		Boolean[] isOptionMixArray = StringUtil.splitToBoolean(isOptionMixs);
		Double[] scoreArray = StringUtil.splitToDouble(scores);
		String[] difficultyArray = StringUtil.split(difficultys);
		
		for (int i = 0; i < smallIdArray.length; i++) {
			Integer smallId = smallIdArray[i];
			Boolean isOptionMix = isOptionMixArray[i];
			Double score = scoreArray[i];
			String difficulty = difficultyArray[i];
			
			TempPaperBigSmallId  tempPaperBigSmallId = new TempPaperBigSmallId();
			tempPaperBigSmallId.setPaperId(Integer.parseInt(paperId));
			tempPaperBigSmallId.setBigId(Integer.parseInt(bigId));
			tempPaperBigSmallId.setSmallId(smallId);
			tempPaperBigSmallId.setTeacherId(teacherId);
			tempPaperBigSmallId.setOperateTime(operateTime);
			
			TempPaperBigSmall oldTempPaperBigSmall = tempPaperBigSmallDAO.load(tempPaperBigSmallId);
			if(isOptionMix==null){
				isOptionMix = false;
			}else{
				isOptionMix = true;
			}
			oldTempPaperBigSmall.setIsOptionMix(isOptionMix);
			oldTempPaperBigSmall.setScore(score);
			oldTempPaperBigSmall.setDifficulty(difficulty);
		}
		tempPaperBigSmallDAO.flush();
		//并返回当前选中的大题的小题
		if(StringUtils.isNotEmpty(nextBigId)){
			List<TempPaperBigSmall> tempPaperBigSmallList = tempPaperBigSmallDAO.query(Integer.parseInt(paperId),Integer.parseInt(nextBigId),teacherId, operateTime);
			
			String[] propertiesTempPaperBigSmall = {"id.paperId","id.bigId","id.smallId","subjectId","isOptionMix","score","difficulty","array"};
			String tempPaperBigSmallData = JSONUtil.toJson(tempPaperBigSmallList,propertiesTempPaperBigSmall);
			result.addData("tempPaperBigSmall", tempPaperBigSmallData);
		}
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.TempPaperBigSmallService#view(com.csit.model.TempPaperBigSmall, java.lang.Integer, java.sql.Timestamp)
	 */
	@Override
	public ServiceResult view(TempPaperBigSmall model, Integer teacherId,
			Timestamp operateTime) {
		ServiceResult result = new ServiceResult(false);
		//并返回当前选中的大题的小题
		List<TempPaperBigSmall> tempPaperBigSmallList = tempPaperBigSmallDAO.query(model.getId().getPaperId(),model.getId().getBigId(),teacherId, operateTime);
		
		String[] propertiesTempPaperBigSmall = {"id.paperId","id.bigId","id.smallId","subjectId","isOptionMix","score","difficulty","array","groupId"};
		String tempPaperBigSmallData = JSONUtil.toJson(tempPaperBigSmallList,propertiesTempPaperBigSmall);
		result.addData("tempPaperBigSmall", tempPaperBigSmallData);
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.TempPaperBigSmallService#delete(com.csit.model.TempPaperBigSmall, java.lang.Integer, java.sql.Timestamp)
	 */
	@Override
	public ServiceResult delete(TempPaperBigSmall model, Integer teacherId,
			Timestamp operateTime) {
		ServiceResult result = new ServiceResult(false);
		if(model==null||model.getId()==null||model.getId().getPaperId()==null
				||model.getId().getBigId()==null||model.getId().getSmallId()==null){
			result.setMessage("请选择要删除的小题");
			return result;
		}
		
		model.getId().setTeacherId(teacherId);
		model.getId().setOperateTime(operateTime);
		
		TempPaperBigSmall oldModel = tempPaperBigSmallDAO.load(model.getId());
		if(oldModel==null){
			result.setMessage("要删除的小题已不存在");
			return result;
		}
		tempPaperBigSmallDAO.delete(oldModel);
		
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.TempPaperBigSmallService#update(com.csit.model.TempPaperBigSmall, java.lang.Integer, java.sql.Timestamp)
	 */
	@Override
	public ServiceResult update(TempPaperBigSmall model, Integer teacherId,
			Timestamp operateTime) {
		ServiceResult result = new ServiceResult(false);
		if(model==null||model.getId()==null){
			result.setMessage("请选择试题");
			return result;
		}
		if(model.getId().getPaperId()==null){
			result.setMessage("请选择试卷");
			return result;
		}
		if(model.getId().getBigId()==null){
			result.setMessage("请选择试卷大题");
			return result;
		}
		if(model.getScore()==null){
			result.setMessage("请指定分数");
			return result;
		}
		
		model.getId().setTeacherId(teacherId);
		model.getId().setOperateTime(operateTime);
		
		if(model.getIsOptionMix()==null){
			model.setIsOptionMix(false);
		}else{
			model.setIsOptionMix(true);
		}
		
		TempPaperBigSmall oldModel = tempPaperBigSmallDAO.load(model.getId());
		if(oldModel==null){
			result.setMessage("要更新的小题已不存在");
			return result;
		}
		oldModel.setIsOptionMix(model.getIsOptionMix());
		oldModel.setScore(model.getScore());
		oldModel.setDifficulty(model.getDifficulty());
		oldModel.setGroupId(model.getGroupId());
		
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.TempPaperBigSmallService#moveArray(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.Integer, java.sql.Timestamp)
	 */
	@Override
	public ServiceResult moveArray(String paperId, String bigId,
			String smallIdFrom, String arrayFrom, String smallIdTo,
			String arrayTo, Integer teacherId, Timestamp operateTime) {
		ServiceResult result = new ServiceResult(false);
		if(StringUtils.isEmpty(paperId)){
			result.setMessage("请选择试卷");
			return result;
		}
		if(StringUtils.isEmpty(bigId)){
			result.setMessage("请选择大题");
			return result;
		}
		if(StringUtils.isEmpty(smallIdFrom)){
			result.setMessage("请选择要移动的小题");
			return result;
		}
		if(StringUtils.isEmpty(arrayFrom)){
			result.setMessage("请指定要移动的小题排序");
			return result;
		}
		if(StringUtils.isEmpty(smallIdTo)){
			result.setMessage("请选择要移动到的小题");
			return result;
		}
		if(StringUtils.isEmpty(arrayTo)){
			result.setMessage("请指定要移动的到小题排序");
			return result;
		}
		
		TempPaperBigSmallId tempPaperBigSmallId = new TempPaperBigSmallId();
		tempPaperBigSmallId.setPaperId(Integer.parseInt(paperId));
		tempPaperBigSmallId.setBigId(Integer.parseInt(bigId));
		tempPaperBigSmallId.setSmallId(Integer.parseInt(smallIdFrom));
		tempPaperBigSmallId.setTeacherId(teacherId);
		tempPaperBigSmallId.setOperateTime(operateTime);
		
		TempPaperBigSmall oldModel = tempPaperBigSmallDAO.load(tempPaperBigSmallId);
		oldModel.setArray(Integer.parseInt(arrayTo));
		tempPaperBigSmallDAO.flush();
		
		tempPaperBigSmallId.setSmallId(Integer.parseInt(smallIdTo));
		oldModel = tempPaperBigSmallDAO.load(tempPaperBigSmallId);
		oldModel.setArray(Integer.parseInt(arrayFrom));
		
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.TempPaperBigSmallService#mulAdd(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.Integer, java.sql.Timestamp)
	 */
	@Override
	public ServiceResult mulAdd(String paperId, String bigId,
			String countSmall, String scoreSmall, Integer teacherId,
			Timestamp operateTime, String array, String groupId) {
		ServiceResult result = new ServiceResult(false);
		if(StringUtils.isEmpty(paperId)){
			result.setMessage("请选择试题");
			return result;
		}
		if(StringUtils.isEmpty(bigId)){
			result.setMessage("请选择大题");
			return result;
		}
		if(StringUtils.isEmpty(countSmall)){
			result.setMessage("请填写题数");
			return result;
		}
		if(StringUtils.isEmpty(scoreSmall)){
			result.setMessage("请填写分数");
			return result;
		}
		if(StringUtils.isEmpty(array)){
			result.setMessage("请指定开始排序");
			return result;
		}
		if(StringUtils.isEmpty(groupId)){
			result.setMessage("请指定参赛组别");
			return result;
		}
		
		Integer countSmallInt = Integer.parseInt(countSmall);
		Double scoreSmallDouble = Double.parseDouble(scoreSmall);
		Integer arrayInt = Integer.parseInt(array);
		
		List<Map<String,Object>> smallIdList = new ArrayList<Map<String,Object>>();
		
		for (int i = 0; i < countSmallInt; i++) {
			TempPaperBigSmallId tempPaperBigSmallId = new TempPaperBigSmallId();
			tempPaperBigSmallId.setPaperId(Integer.parseInt(paperId));
			tempPaperBigSmallId.setBigId(Integer.parseInt(bigId));
			tempPaperBigSmallId.setOperateTime(operateTime);
			tempPaperBigSmallId.setTeacherId(teacherId);
			//1.从序列表中取得，临时表的id
			Integer smallId = serialNumberService.getNextSerial(SerialNumberTabelName.PAPER_BIG_SMALL);
			tempPaperBigSmallId.setSmallId(smallId);
			
			TempPaperBigSmall model = new TempPaperBigSmall();
			model.setId(tempPaperBigSmallId);
			model.setIsOptionMix(true);
			model.setDifficulty("中");
			model.setScore(scoreSmallDouble);
			model.setArray(arrayInt++);
			model.setGroupId(Integer.parseInt(groupId));
			tempPaperBigSmallDAO.save(model);
			
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("smallId", smallId);
			smallIdList.add(map);
		}
		String smallIdJsonString = JSONUtil.toJsonFromListMapWithOutRows(smallIdList);
		result.addData("smallIds", smallIdJsonString);
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.TempPaperBigSmallService#saveMulMix(java.lang.Integer, java.sql.Timestamp, com.csit.model.TempPaperBigSmall, java.lang.String)
	 */
	@Override
	public ServiceResult saveMulMix(Integer teacherId, Timestamp operateTime,
			TempPaperBigSmall model, String ids) {
		ServiceResult result = new ServiceResult(false);
		if(model==null||model.getId()==null){
			result.setMessage("请选择试题");
			return result;
		}
		if(model.getId().getPaperId()==null){
			result.setMessage("请选择试卷");
			return result;
		}
		if(model.getId().getBigId()==null){
			result.setMessage("请选择试卷大题");
			return result;
		}
		if(model.getArray()==null){
			result.setMessage("请指定排序");
			return result;
		}
		
		if(StringUtils.isEmpty(ids)){
			result.setMessage("请选择要添加的试题");
			return result;
		}
		
		Integer[] subjectIdArray = StringUtil.splitToInteger(ids);
		if(subjectIdArray==null||subjectIdArray.length==0){
			result.setMessage("请选择要添加的试题");
			return result;
		}
		int array = model.getArray();
		for (int i = 0; i < subjectIdArray.length; i++) {
			Subject subject =  subjectDAO.load(subjectIdArray[i]);
			if(subject!=null){
				//1.从序列表中取得，临时表的id
				Integer smallId = serialNumberService.getNextSerial(SerialNumberTabelName.PAPER_BIG_SMALL);
				TempPaperBigSmallId id = new TempPaperBigSmallId();
				id.setTeacherId(teacherId);
				id.setOperateTime(operateTime);
				id.setPaperId(model.getId().getPaperId());
				id.setBigId(model.getId().getBigId());
				id.setSmallId(smallId);
				
				TempPaperBigSmall tempPaperBigSmall = new TempPaperBigSmall();
				tempPaperBigSmall.setId(id);
				tempPaperBigSmall.setSubjectId(subjectIdArray[i]);
				tempPaperBigSmall.setGroupId(model.getGroupId());
				tempPaperBigSmall.setDifficulty(subject.getDifficulty());
				tempPaperBigSmall.setArray(array);
				tempPaperBigSmall.setIsOptionMix(true);
				tempPaperBigSmall.setScore(subject.getScore());
				tempPaperBigSmallDAO.save(tempPaperBigSmall);
				array++;
			}
		}
		result.setIsSuccess(true);
		return result;
	}

}
