package com.csit.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.csit.dao.CompetitionGroupDAO;
import com.csit.model.CompetitionGroup;
import com.csit.model.Group;
import com.csit.service.CompetitionGroupService;
import com.csit.util.JSONUtil;
import com.csit.util.StringUtil;
import com.csit.vo.ServiceResult;

/**
 * @Description:赛事组别Service实现类
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-5-31
 * @author jcf
 * @vesion 1.0
 */
@Service
public class CompetitionGroupServiceImpl extends BaseServiceImpl<CompetitionGroup, Integer> implements CompetitionGroupService {

	@Resource
	private CompetitionGroupDAO competitionGroupDAO;
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.CompetitionService#delete(com.csit.model.Competition)
	 */
	public ServiceResult delete(CompetitionGroup model) {
		ServiceResult result = new ServiceResult(false);
		if (model == null || model.getCompetitionGroupId() ==null) {
			result.setMessage("请选择要删除的赛事");
			return result;
		}
		CompetitionGroup oldModel = competitionGroupDAO.load(model.getCompetitionGroupId());
		if (oldModel == null) {
			result.setMessage("该赛事组别已不存在");
			return result;
		} else {
			competitionGroupDAO.delete(oldModel);
		}
		result.setIsSuccess(true);
		return result;
	}

	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.CompetitionService#query(com.csit.model.Competition, java.lang.Integer, java.lang.Integer)
	 */
	public String query(CompetitionGroup model, Integer page, Integer rows) {

		List<CompetitionGroup> list = competitionGroupDAO.query(model, page, rows);
		Long total = competitionGroupDAO.getTotalCount(model);

		String[] properties = { "competition.competitionId","paper.paperId", "paper.paperName"
				,"group.groupId","group.groupName", "array","competitionGroupId"};
		return JSONUtil.toJson(list, properties, total);
	}

	public ServiceResult save(CompetitionGroup model,String groupIds) {
		ServiceResult result = new ServiceResult(false);
		if (model == null) {
			result.setMessage("请填写赛事信息");
			return result;
		}
		if (model.getCompetitionGroupId()==null) {// 新增
			String[] ids = StringUtil.split(groupIds);
			if(ids.length==0){
				result.setMessage("请选择赛事分组");
				return result;
			}else {
				Integer array=competitionGroupDAO.getMaxArray(model.getCompetition().getCompetitionId());
				for(int i=0;i<ids.length;i++){
					
					Group group=new Group();
					group.setGroupId(Integer.parseInt(ids[i]));
					
					String[] propertyNames={"competition","group"};
					Object[] values={model.getCompetition(),group};
					CompetitionGroup competitionGroup1=competitionGroupDAO.load(propertyNames, values);
					if(competitionGroup1==null){
						CompetitionGroup competitionGroup = new CompetitionGroup();
						competitionGroup.setCompetition(model.getCompetition());
						competitionGroup.setGroup(group);
						competitionGroup.setArray(array+i+1);
						competitionGroupDAO.save(competitionGroup);
					}
				}
			}
		} else {
			CompetitionGroup oldModel = competitionGroupDAO.load(model.getCompetitionGroupId());
			oldModel.setPaper(model.getPaper());
		}
		result.setIsSuccess(true);
		return result;
	}

	@Override
	public ServiceResult updateArray(Integer competitionGroupId, Integer updateCompetitionGroupId) {
		ServiceResult result = new ServiceResult(false);
		if(competitionGroupId==null||updateCompetitionGroupId==null){
			result.setMessage("请选择要改变权限排序的权限");
			return result;
		}
		competitionGroupDAO.updateArray(competitionGroupId, updateCompetitionGroupId);
		
		result.setIsSuccess(true);
		return result;
	}

	@Override
	public String queryCombobox(CompetitionGroup model) {
		List<CompetitionGroup> list = competitionGroupDAO.queryCombobox(model);
		String[] properties = {"competitionGroupId","competition.competitionId","competition.competitionName","group.groupId","group.groupName"};
		String jsonString = JSONUtil.toJsonWithoutRows(list,properties);
		return jsonString;
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.CompetitionGroupService#initEnrollStu(com.csit.model.CompetitionGroup, java.lang.Integer)
	 */
	@Override
	public ServiceResult initEnrollStu(CompetitionGroup model,Integer studentId) {
		ServiceResult result = new ServiceResult(false);
		if(model==null||model.getCompetition()==null||model.getCompetition().getCompetitionId()==null){
			result.setMessage("请选择报名赛事");
			return result;
		}
		Map<String,Object> map =  competitionGroupDAO.initEnrollStu(model.getCompetition().getCompetitionId(),studentId);
		String initData = JSONUtil.toJson(map);
		
		List<CompetitionGroup> list = competitionGroupDAO.queryCombobox(model);
		String[] properties = {"competitionGroupId","competition.competitionName","group.groupName"};
		String groupData = JSONUtil.toJsonWithoutRows(list,properties);
		result.addData("initData", initData);
		result.addData("groupData", groupData);
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.CompetitionGroupService#queryDatagrid(com.csit.model.CompetitionGroup)
	 */
	@Override
	public String queryDatagrid(CompetitionGroup model) {
		List<CompetitionGroup> list = competitionGroupDAO.queryCombobox(model);
		String[] properties = {"competitionGroupId","competition.competitionName","group.groupName"};
		String jsonString = JSONUtil.toJson(list,properties);
		return jsonString;
	}

}
