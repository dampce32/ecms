package com.csit.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.csit.dao.GroupDAO;
import com.csit.model.Group;
import com.csit.service.GroupService;
import com.csit.util.JSONUtil;
import com.csit.util.StringUtil;
import com.csit.vo.GobelConstants;
import com.csit.vo.ServiceResult;

/**
 * @Description:参赛组别Service实现类
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-5-28
 * @author jcf
 * @vesion 1.0
 */
@Service
public class GroupServiceImpl extends BaseServiceImpl<Group, Integer> implements GroupService {

	@Resource
	private GroupDAO groupDAO;
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.GroupService#delete(com.csit.model.Group)
	 */
	public ServiceResult delete(Group model) {
		ServiceResult result = new ServiceResult(false);
		if (model == null || model.getGroupId() == null) {
			result.setMessage("请选择要删除的参赛组别");
			return result;
		}
		Group oldModel = groupDAO.load(model.getGroupId());
		if (oldModel == null) {
			result.setMessage("该参赛组别已不存在");
			return result;
		} else {
			groupDAO.delete(oldModel);
		}
		result.setIsSuccess(true);
		return result;
	}

	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.GroupService#query(com.csit.model.Group, java.lang.Integer, java.lang.Integer)
	 */
	public String query(Group model, Integer page, Integer rows, String groupIds) {

		Integer[] groupIdArr;
		if(StringUtils.isNotEmpty(groupIds)){
			String[] s = StringUtil.split(groupIds);
			groupIdArr=new Integer[s.length];
			for(int i=0;i<s.length;i++){
				groupIdArr[i]=Integer.parseInt(s[i]);
			}
		}else{
			groupIdArr=null;
		}
		List<Group> list = groupDAO.query(model, page, rows,groupIdArr);
		Long total = groupDAO.getTotalCount(model,groupIdArr);

		String[] properties = { "groupId", "groupName","status", "groupCode","note","array" };

		return JSONUtil.toJson(list, properties, total);
	}

	public String queryCombobox(Group model) {
		List<Group> list = groupDAO.queryCombobox(model);
		String[] properties = {"groupId","groupName"};
		String jsonString = JSONUtil.toJsonWithoutRows(list,properties);
		return jsonString;
	}

	public ServiceResult save(Group model) {
		ServiceResult result = new ServiceResult(false);
		if (model == null) {
			result.setMessage("请填写参赛组别信息");
			return result;
		}else if(groupDAO.load("groupCode", model.getGroupCode())!=null){
			result.setMessage("该参赛组别编号已存在");
			return result;
		}else if (StringUtils.isEmpty(model.getGroupName())) {
			result.setMessage("请填写参赛组别名称");
			return result;
		}
		if (model.getGroupId() == null) {// 新增
			if (StringUtils.isEmpty(model.getGroupCode())) {
				result.setMessage("请填写参赛组别编号");
				return result;
			}
			Integer maxArray=groupDAO.getMaxArray();
			model.setArray(maxArray+1);
			groupDAO.save(model);
		} else {
			Group oldModel = groupDAO.load(model.getGroupId());
			if (oldModel == null) {
				result.setMessage("该参赛组别已不存在");
				return result;
			}
			oldModel.setGroupName(model.getGroupName());
			oldModel.setStatus(model.getStatus());
			oldModel.setNote(model.getNote());
		}
		result.setIsSuccess(true);
		result.addData("groupId", model.getGroupId());
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
			Group oldModel = groupDAO.load(Integer.parseInt(id));
			if (oldModel == null) {
				continue;
			}else{
				groupDAO.delete(Integer.parseInt(id));
				haveDelete = true;
			}
		}
		if(!haveDelete){
			result.setMessage("没有可删除的参赛组别");
			return result;
		}
		result.setIsSuccess(true);
		return result;
	}

	@Override
	public ServiceResult mulUpdateStatus(String ids, Group model) {
		ServiceResult result = new ServiceResult(false);
		if(StringUtils.isEmpty(ids)){
			result.setMessage("请选择要修改状态的参赛组别");
			return result;
		}
		String[] idArray =StringUtil.split(ids);
		if(idArray.length==0){
			result.setMessage("请选择要修改状态的参赛组别");
			return result;
		}
		if(model==null||model.getStatus()==null){
			result.setMessage("请选择要修改成的状态");
			return result;
		}
		boolean haveUpdateShzt = false;
		for (String id : idArray) {
			Group oldModel = groupDAO.load(Integer.parseInt(id));
			if(oldModel!=null&&oldModel.getStatus().intValue()!=model.getStatus().intValue()){
				oldModel.setStatus(model.getStatus());
				haveUpdateShzt = true;
			}
		}
		if(!haveUpdateShzt){
			result.setMessage("没有可修改状态的参赛组别");
			return result;
		}
		result.setIsSuccess(true);
		return result;
	}

	@Override
	public ServiceResult updateArray(Integer groupId, Integer updateGroupId) {
		ServiceResult result = new ServiceResult(false);
		if(groupId==null||updateGroupId==null){
			result.setMessage("请选择要改变权限排序的权限");
			return result;
		}
		groupDAO.updateArray(groupId,updateGroupId);
		
		result.setIsSuccess(true);
		return result;
	}

}
