package com.csit.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.csit.dao.CompetitionDAO;
import com.csit.dao.CompetitionGroupDAO;
import com.csit.dao.CompetitionPhotoDAO;
import com.csit.dao.CompetitionPrizeDAO;
import com.csit.dao.InformationDAO;
import com.csit.model.Competition;
import com.csit.service.CompetitionService;
import com.csit.util.JSONUtil;
import com.csit.util.StringUtil;
import com.csit.vo.GobelConstants;
import com.csit.vo.ServiceResult;

/**
 * @Description:赛事Service实现类
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-5-30
 * @author jcf
 * @vesion 1.0
 */
@Service
public class CompetitionServiceImpl extends BaseServiceImpl<Competition, Integer> implements CompetitionService {

	@Resource
	private CompetitionDAO competitionDAO;
	@Resource
	private InformationDAO informationDAO;
	@Resource
	private CompetitionPhotoDAO competitionPhotoDAO;
	@Resource
	private CompetitionGroupDAO competitionGroupDAO;
	@Resource
	private CompetitionPrizeDAO competitionPrizeDAO;
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.CompetitionService#delete(com.csit.model.Competition)
	 */
	public ServiceResult delete(Competition model) {
		ServiceResult result = new ServiceResult(false);
		if (model == null || model.getCompetitionId() == null) {
			result.setMessage("请选择要删除的赛事");
			return result;
		}
		Competition oldModel = competitionDAO.load(model.getCompetitionId());
		if (oldModel == null) {
			result.setMessage("该赛事已不存在");
			return result;
		} else {
			competitionDAO.delete(oldModel);
		}
		result.setIsSuccess(true);
		return result;
	}

	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.CompetitionService#query(com.csit.model.Competition, java.lang.Integer, java.lang.Integer)
	 */
	public String query(Competition model, Integer page, Integer rows) {

		List<Competition> list = competitionDAO.query(model, page, rows);
		Long total = competitionDAO.getTotalCount(model);

		String[] properties = { "competitionId", "competitionName","status", "competitionCode"
				,"note","competitionNote", "beginDate","endDate","picture.pictureId"
				, "parentCompetition.competitionId:parentCompetitionId","parentCompetition.competitionName:parentCompetitionName"};
		return JSONUtil.toJson(list, properties, total);
	}

	public String queryCombobox(Competition model) {
		List<Competition> list = competitionDAO.queryCombobox(model);
		String[] properties = {"competitionId","competitionName"};
		String jsonString = JSONUtil.toJsonWithoutRows(list,properties);
		return jsonString;
	}

	public ServiceResult save(Competition model) {
		ServiceResult result = new ServiceResult(false);
		if (model == null) {
			result.setMessage("请填写赛事信息");
			return result;
		}else if(competitionDAO.load("competitionCode", model.getCompetitionCode())!=null){
			result.setMessage("该赛事编号已存在");
			return result;
		}else if (StringUtils.isEmpty(model.getCompetitionName())) {
			result.setMessage("请填写赛事名称");
			return result;
		}else if (model.getPicture()==null) {
			result.setMessage("请上传图片");
			return result;
		}
		if(model.getParentCompetition()!=null&&model.getParentCompetition().getCompetitionId()!=null&&model.getCompetitionId()!=null){//上一级赛事
			if(model.getParentCompetition().getCompetitionId().intValue()==model.getCompetitionId().intValue()){
				result.setMessage("上一级赛事不能是当前赛事");
				return result;
			}
		}
		if (model.getCompetitionId() == null) {// 新增
			if (StringUtils.isEmpty(model.getCompetitionCode())) {
				result.setMessage("请填写赛事编号");
				return result;
			}
			competitionDAO.save(model);
		} else {
			Competition oldModel = competitionDAO.load(model.getCompetitionId());
			if (oldModel == null) {
				result.setMessage("该赛事已不存在");
				return result;
			}
			oldModel.setCompetitionName(model.getCompetitionName());
			oldModel.setCompetitionNote(model.getCompetitionNote());
			oldModel.setStatus(model.getStatus());
			oldModel.setNote(model.getNote());
			oldModel.setPicture(model.getPicture());
			oldModel.setParentCompetition(model.getParentCompetition());
			oldModel.setBeginDate(model.getBeginDate());
			oldModel.setEndDate(model.getEndDate());
		}
		result.setIsSuccess(true);
		result.addData("competitionId", model.getCompetitionId());
		return result;
	}

	@Override
	public ServiceResult mulDelete(String ids) {
		ServiceResult result = new ServiceResult(false);
		if(StringUtils.isEmpty(ids)){
			result.setMessage("请选择要删除的记录");
			return result;
		}
		String[] idArray = StringUtil.split(ids, GobelConstants.SPLIT_SEPARATOR);
		if(idArray.length==0){
			result.setMessage("请选择要删除的记录");
			return result;
		}
		boolean haveDelete = false;
		for (String id : idArray) {
			Competition oldModel = competitionDAO.load(Integer.parseInt(id));
			if (oldModel == null) {
				continue;
			}else{
				competitionDAO.delete(Integer.parseInt(id));
				haveDelete = true;
			}
		}
		if(!haveDelete){
			result.setMessage("没有可删除的赛事");
			return result;
		}
		result.setIsSuccess(true);
		return result;
	}

	@Override
	public ServiceResult mulUpdateStatus(String ids, Competition model) {
		ServiceResult result = new ServiceResult(false);
		if(StringUtils.isEmpty(ids)){
			result.setMessage("请选择要修改状态的赛事");
			return result;
		}
		String[] idArray =StringUtil.split(ids);
		if(idArray.length==0){
			result.setMessage("请选择要修改状态的赛事");
			return result;
		}
		if(model==null||model.getStatus()==null){
			result.setMessage("请选择要修改成的状态");
			return result;
		}
		boolean haveUpdateShzt = false;
		for (String id : idArray) {
			Competition oldModel = competitionDAO.load(Integer.parseInt(id));
			if(oldModel!=null&&oldModel.getStatus().intValue()!=model.getStatus().intValue()){
				oldModel.setStatus(model.getStatus());
				haveUpdateShzt = true;
			}
		}
		if(!haveUpdateShzt){
			result.setMessage("没有可修改状态的赛事");
			return result;
		}
		result.setIsSuccess(true);
		return result;
	}

	@Override
	public ServiceResult saveCopy(Competition model, String copyItems,String copyCompetitionId) {
		ServiceResult result = new ServiceResult(false);
		if (model == null) {
			result.setMessage("请填写赛事信息");
			return result;
		}else if (StringUtils.isEmpty(model.getCompetitionCode())) {
			result.setMessage("请填写赛事编号");
			return result;
		}else if(competitionDAO.load("competitionCode", model.getCompetitionCode())!=null){
			result.setMessage("该赛事编号已存在");
			return result;
		}else if (StringUtils.isEmpty(model.getCompetitionName())) {
			result.setMessage("请填写赛事名称");
			return result;
		}else if (model.getPicture()==null) {
			result.setMessage("请上传图片");
			return result;
		}else if(model.getParentCompetition()!=null&&model.getParentCompetition().getCompetitionId()!=null&&model.getCompetitionId()!=null){//上一级赛事
			if(model.getParentCompetition().getCompetitionId().intValue()==model.getCompetitionId().intValue()){
				result.setMessage("上一级赛事不能是当前赛事");
				return result;
			}
		}else {
			competitionDAO.save(model);
			if(StringUtils.isNotEmpty(copyItems)){
				String[] itemsArray =StringUtil.split(copyItems);
				for(int i=0;i<itemsArray.length;i++){
					switch (Integer.parseInt(itemsArray[i])) {
					case 1:
						informationDAO.copyAdd(model.getCompetitionId(), Integer.parseInt(copyCompetitionId), "大赛章程");
						break;
					case 2:
						competitionPhotoDAO.copyAdd(model.getCompetitionId(), Integer.parseInt(copyCompetitionId), "主持人风采");
						break;
					case 3:
						competitionPhotoDAO.copyAdd(model.getCompetitionId(), Integer.parseInt(copyCompetitionId), "选手风采");
						break;
					case 4:
						competitionGroupDAO.copyAdd(model.getCompetitionId(), Integer.parseInt(copyCompetitionId));
						competitionPrizeDAO.copyAdd(model.getCompetitionId(), Integer.parseInt(copyCompetitionId));
						break;
					}
				}
			}
		}
		
		result.setIsSuccess(true);
		result.addData("competitionId", model.getCompetitionId());
		return result;
	}

}
