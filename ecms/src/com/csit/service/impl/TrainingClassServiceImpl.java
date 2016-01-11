package com.csit.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.csit.dao.TrainingClassDAO;
import com.csit.model.Competition;
import com.csit.model.CompetitionGroup;
import com.csit.model.TrainingClass;
import com.csit.service.TrainingClassService;
import com.csit.util.JSONUtil;
import com.csit.util.StringUtil;
import com.csit.vo.ServiceResult;
/**
 * @Description: 培训班级Service实现类
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-7
 * @author wxy
 * @vesion 1.0
 */
@Service
public class TrainingClassServiceImpl extends BaseServiceImpl<TrainingClass, Integer> implements
        TrainingClassService {
	@Resource
	private TrainingClassDAO trainingClassDAO;
	

	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.TrainingClassServiceImpl#save(com.csit.model.TrainingClass)
	 */
	@Override
	public ServiceResult save(TrainingClass model) {
		ServiceResult result = new ServiceResult(false);
		if(model==null){
			result.setMessage("请填写班级信息");
			return result;
		}
		if(StringUtils.isEmpty(model.getTrainingClassName())){
			result.setMessage("请填写班级名称");
			return result;
		}
		if(model.getCompetitionGroup().getCompetition().getCompetitionId()==null){
 			result.setMessage("请选择所属赛事");
			return result;
		}
		if(model.getClassDate()==null){
			result.setMessage("请填写上课时间");
			return result;
		}
		if(model.getLimit()==null){
			result.setMessage("请填写上限人数");
			return result;
		}
		if(model.getFee()==null){
			result.setMessage("请填写培训费用");
			return result;
		}
		//新增
		if(model.getTrainingClassId()==null){
			TrainingClass oldModel = trainingClassDAO.load("trainingClassName", model.getTrainingClassName());
			if(oldModel != null){
				result.setMessage("该培训班级已存在");
				return result;
			}
			model.setStuCount(0);
			if(model.getClassTeacher()==null){
				model.setClassTeacher("待定");
			}
			trainingClassDAO.save(model);
		}else{
			TrainingClass oldModel = trainingClassDAO.load(model.getTrainingClassId());
			if (oldModel == null) {
				result.setMessage("该培训班级已不存在");
				return result;
			}
			TrainingClass oldModel2 = trainingClassDAO.load("trainingClassName", model.getTrainingClassName());
			if(oldModel2 != null && !(model.getTrainingClassId().equals(oldModel2.getTrainingClassId()))){
				result.setMessage("该培训班级已存在");
				return result;
			}
			oldModel.setTrainingClassName(model.getTrainingClassName());
			oldModel.setAddress(model.getAddress());
			oldModel.setCompetitionGroup(model.getCompetitionGroup());
			oldModel.setClassDate(model.getClassDate());
			oldModel.setClassTeacher(model.getClassTeacher());
			oldModel.setGoods(model.getGoods());
			oldModel.setLimit(model.getLimit());
			oldModel.setFee(model.getFee());
			oldModel.setStatus(model.getStatus());
			oldModel.setNote(model.getNote());
		}
		result.setIsSuccess(true);
		return result;
	}

	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.TrainingClassServiceImpl#mulDelete(java.lang.String)
	 */
	@Override
	public ServiceResult mulDelete(String ids) {
		ServiceResult result = new ServiceResult(false);
		if(StringUtils.isEmpty(ids)){
			result.setMessage("请选择要删除的记录");
			return result;
		}
		String[] idArray = StringUtil.split(ids);
		if(idArray.length==0){
			result.setMessage("请选择要删除的记录");
			return result;
		}
		boolean haveDelete = false;
		for (String id : idArray) {
			if(StringUtils.isNotEmpty(id)){
				trainingClassDAO.delete(Integer.parseInt(id));
				haveDelete = true;
			}
		}
		if(!haveDelete){
			result.setMessage("没有可删除的培训班级");
			return result;
		}
		result.setIsSuccess(true);
		return result;
	}
	
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.TrainingClassServiceImpl#query(com.csit.model.TrainingClass, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public String query(TrainingClass model, Integer page, Integer rows) {
		List<TrainingClass> list = trainingClassDAO.query(model, page, rows);
		Long total = trainingClassDAO.getTotalCount(model);

		String[] properties = { "competitionGroup.competitionGroupId","goods.goodsId","trainingClassId","address",
								"trainingClassName", "competitionGroup.competition.competitionName:competitionName",
								"competitionGroup.competition.competitionId:competitionId","status","competitionGroup.group.groupId:groupId",
								"competitionGroup.group.groupName:groupName","address","classDate","classTeacher",
								"goods.goodsName", "goods.sellingPrice", "fee", "stuCount","limit","note","goodsFee","total"};

		return JSONUtil.toJson(list, properties, total);
	}
	
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.TrainingClassServiceImpl#queryCombobox(com.csit.model.TrainingClass)
	 */
	@Override
	public String queryCombobox(TrainingClass trainingClass,Integer competitionId) {
		List<TrainingClass> list = trainingClassDAO.queryCombobox(trainingClass,competitionId);
		String[] properties = {"trainingClassId","trainingClassName"};
		String jsonString = JSONUtil.toJsonWithoutRows(list,properties);
		return jsonString;
	}
	
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.PrizeService#mulUpdateState(java.lang.String, com.csit.model.Prize)
	 */
	@Override
	public ServiceResult mulUpdateState(String ids, TrainingClass model) {
		ServiceResult result = new ServiceResult(false);
		if(StringUtils.isEmpty(ids)){
			result.setMessage("请选择要修改状态的培训班级");
			return result;
		}
		String[] idArray =StringUtil.split(ids);
		if(idArray.length==0){
			result.setMessage("请选择要修改状态的培训班级");
			return result;
		}
		if(model==null||model.getStatus()==null){
			result.setMessage("请选择要修改成的状态");
			return result;
		}
		boolean haveUpdateShzt = false;
		
		for (String id : idArray) {
			TrainingClass oldModel = trainingClassDAO.load(Integer.parseInt(id));
			if(oldModel!=null&&oldModel.getStatus()!=model.getStatus()){
				oldModel.setStatus(model.getStatus());
				
				haveUpdateShzt = true;
			}
		}
		if(!haveUpdateShzt){
			result.setMessage("没有可修改状态的培训班级");
			return result;
		}
		result.setIsSuccess(true);
		return result;
	}

	@Override
	public List<TrainingClass> loadAll(Integer competitionId) {
		Competition competition=new Competition();
		competition.setCompetitionId(competitionId);
		String[] propertyNames={"competitionGroup.competition","status"};
		Object[] values={competition,1};
		return trainingClassDAO.query(propertyNames, values);
	}

	@Override
	public Map<String, Object> initTrainingClass(String competitionId, Integer page,
			Integer rows) {
		Map<String, Object> map = new HashMap<String, Object>();

		TrainingClass model=new TrainingClass();
		CompetitionGroup competitionGroup=new CompetitionGroup();
		Competition competition = new Competition();
		competition.setCompetitionId(Integer.parseInt(competitionId));
		competitionGroup.setCompetition(competition);
		model.setCompetitionGroup(competitionGroup);
		
		List<TrainingClass> list = trainingClassDAO.query(model, page + 1, rows);
		Long total = trainingClassDAO.getTotalCount(model);

		map.put("trainingClassList", list);
		map.put("total", total);
		return map;
	}

}
