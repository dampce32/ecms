package com.csit.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.csit.dao.StadiumDAO;
import com.csit.model.Competition;
import com.csit.model.CompetitionGroup;
import com.csit.model.Stadium;
import com.csit.service.StadiumService;
import com.csit.util.JSONUtil;
import com.csit.util.StringUtil;
import com.csit.vo.GobelConstants;
import com.csit.vo.ServiceResult;

/**
 * @Description:赛场Service实现类
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-5
 * @author jcf
 * @vesion 1.0
 */
@Service
public class StadiumServiceImpl extends BaseServiceImpl<Stadium, Integer> implements StadiumService {

	@Resource
	private StadiumDAO stadiumDAO;
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.StadiumService#delete(com.csit.model.Stadium)
	 */
	public ServiceResult delete(Stadium model) {
		ServiceResult result = new ServiceResult(false);
		if (model == null || model.getStadiumId() == null) {
			result.setMessage("请选择要删除的赛场");
			return result;
		}
		Stadium oldModel = stadiumDAO.load(model.getStadiumId());
		if (oldModel == null) {
			result.setMessage("该赛场已不存在");
			return result;
		} else {
			stadiumDAO.delete(oldModel);
		}
		result.setIsSuccess(true);
		return result;
	}

	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.StadiumService#query(com.csit.model.Stadium, java.lang.Integer, java.lang.Integer)
	 */
	public String query(Stadium model, Integer page, Integer rows) {

		List<Stadium> list = stadiumDAO.query(model, page, rows);
		Long total = stadiumDAO.getTotalCount(model);

		String[] properties = { "stadiumId", "stadiumName","stadiumAddr", "competitionDate","note","limit","arrangeNo","competitionGroup.competition.competitionName:competitionName",
				"competitionGroup.group.groupName:groupName","competitionGroup.competition.competitionId:competitionId","competitionGroup.competitionGroupId"};

		return JSONUtil.toJson(list, properties, total);
	}

	public ServiceResult save(Stadium model) {
		ServiceResult result = new ServiceResult(false);
		if (model == null) {
			result.setMessage("请填写赛场信息");
			return result;
		}else if (StringUtils.isEmpty(model.getStadiumName())) {
			result.setMessage("请填写赛场名称");
			return result;
		}else if (StringUtils.isEmpty(model.getStadiumAddr())) {
			result.setMessage("请填写比赛地点");
			return result;
		}else if (model.getCompetitionGroup()==null) {
			result.setMessage("请选择参赛组别");
			return result;
		}else if (model.getCompetitionDate()==null) {
			result.setMessage("请选择比赛日期");
			return result;
		}else if (model.getLimit()==null) {
			result.setMessage("请填写人数上限");
			return result;
		}
		if (model.getStadiumId() == null) {// 新增
			model.setArrangeNo(0);
			stadiumDAO.save(model);
		} else {
			Stadium oldModel = stadiumDAO.load(model.getStadiumId());
			if(oldModel == null) {
				result.setMessage("该赛场已不存在");
				return result;
			}else if(oldModel.getArrangeNo()>model.getLimit()){
				result.setMessage("赛场上限不可少于参赛人数");
				return result;
			}
			
			oldModel.setCompetitionGroup(model.getCompetitionGroup());
			oldModel.setCompetitionDate(model.getCompetitionDate());
			oldModel.setLimit(model.getLimit());
			oldModel.setStadiumAddr(model.getStadiumAddr());
			oldModel.setStadiumName(model.getStadiumName());
			oldModel.setNote(model.getNote());
		}
		result.setIsSuccess(true);
		result.addData("stadiumId", model.getStadiumId());
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
			Stadium oldModel = stadiumDAO.load(Integer.parseInt(id));
			if (oldModel == null) {
				continue;
			}else{
				stadiumDAO.delete(Integer.parseInt(id));
				haveDelete = true;
			}
		}
		if(!haveDelete){
			result.setMessage("没有可删除的赛场");
			return result;
		}
		result.setIsSuccess(true);
		return result;
	}

	@Override
	public Map<String, Object> initStadium(String competitionId,
			Integer page, Integer rows) {
		Map<String, Object> map = new HashMap<String, Object>();

		Stadium model=new Stadium();
		CompetitionGroup competitionGroup=new CompetitionGroup();
		Competition competition = new Competition();
		competition.setCompetitionId(Integer.parseInt(competitionId));
		competitionGroup.setCompetition(competition);
		model.setCompetitionGroup(competitionGroup);
		
		List<Stadium> list = stadiumDAO.query(model, page + 1, rows);
		Long total = stadiumDAO.getTotalCount(model);

		map.put("stadiumList", list);
		map.put("total", total);
		return map;
	}

	@Override
	public String queryCombobox(Integer competitionId) {
		List<Stadium> list = stadiumDAO.query(competitionId);
		String[] properties = {"stadiumId","stadiumName"};
		String jsonString = JSONUtil.toJsonWithoutRows(list,properties);
		return jsonString;
	}

}
