package com.csit.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.csit.dao.ExpendTypeDAO;
import com.csit.model.ExpendType;
import com.csit.service.ExpendTypeService;
import com.csit.util.JSONUtil;
import com.csit.util.StringUtil;
import com.csit.vo.GobelConstants;
import com.csit.vo.ServiceResult;

/**
 * @Description:支出类型Service实现类
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-9
 * @author jcf
 * @vesion 1.0
 */
@Service
public class ExpendTypeServiceImpl extends BaseServiceImpl<ExpendType, Integer> implements ExpendTypeService {

	@Resource
	private ExpendTypeDAO expendTypeDAO;
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.ExpendTypeService#delete(com.csit.model.ExpendType)
	 */
	public ServiceResult delete(ExpendType model) {
		ServiceResult result = new ServiceResult(false);
		if (model == null || model.getExpendTypeId() == null) {
			result.setMessage("请选择要删除的支出类型");
			return result;
		}
		ExpendType oldModel = expendTypeDAO.load(model.getExpendTypeId());
		if (oldModel == null) {
			result.setMessage("该支出类型已不存在");
			return result;
		} else {
			expendTypeDAO.delete(oldModel);
		}
		result.setIsSuccess(true);
		return result;
	}

	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.ExpendTypeService#query(com.csit.model.ExpendType, java.lang.Integer, java.lang.Integer)
	 */
	public String query(ExpendType model, Integer page, Integer rows) {

		List<ExpendType> list = expendTypeDAO.query(model, page, rows);
		Long total = expendTypeDAO.getTotalCount(model);

		String[] properties = { "expendTypeId", "expendTypeName","status", "array" };

		return JSONUtil.toJson(list, properties, total);
	}

	public String queryCombobox(ExpendType model) {
		List<ExpendType> list = expendTypeDAO.queryCombobox(model);
		String[] properties = {"expendTypeId","expendTypeName"};
		String jsonString = JSONUtil.toJsonWithoutRows(list,properties);
		return jsonString;
	}

	public ServiceResult save(ExpendType model) {
		ServiceResult result = new ServiceResult(false);
		if (model == null) {
			result.setMessage("请填写支出类型信息");
			return result;
		}else if (StringUtils.isEmpty(model.getExpendTypeName())) {
			result.setMessage("请填写支出类型名称");
			return result;
		}
		if (model.getExpendTypeId() == null) {// 新增
			if(expendTypeDAO.load("expendTypeName", model.getExpendTypeName())!=null){
				result.setMessage("支出类型名称已存在");
				return result;
			}
			Integer maxArray=expendTypeDAO.getMaxArray();
			model.setArray(maxArray+1);
			expendTypeDAO.save(model);
		} else {
			ExpendType oldModel1=expendTypeDAO.load("expendTypeName", model.getExpendTypeName());
			if(oldModel1!=null&&oldModel1.getExpendTypeId().intValue()!=model.getExpendTypeId().intValue()){
				result.setMessage("支出类型名称已存在");
				return result;
			}
			ExpendType oldModel = expendTypeDAO.load(model.getExpendTypeId());
			if (oldModel == null) {
				result.setMessage("该支出类型已不存在");
				return result;
			}
			oldModel.setExpendTypeName(model.getExpendTypeName());
			oldModel.setStatus(model.getStatus());
		}
		result.setIsSuccess(true);
		result.addData("expendTypeId", model.getExpendTypeId());
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
			ExpendType oldModel = expendTypeDAO.load(Integer.parseInt(id));
			if (oldModel == null) {
				continue;
			}else{
				expendTypeDAO.delete(Integer.parseInt(id));
				haveDelete = true;
			}
		}
		if(!haveDelete){
			result.setMessage("没有可删除的支出类型");
			return result;
		}
		result.setIsSuccess(true);
		return result;
	}

	@Override
	public ServiceResult mulUpdateStatus(String ids, ExpendType model) {
		ServiceResult result = new ServiceResult(false);
		if(StringUtils.isEmpty(ids)){
			result.setMessage("请选择要修改状态的支出类型");
			return result;
		}
		String[] idArray =StringUtil.split(ids);
		if(idArray.length==0){
			result.setMessage("请选择要修改状态的支出类型");
			return result;
		}
		if(model==null||model.getStatus()==null){
			result.setMessage("请选择要修改成的状态");
			return result;
		}
		boolean haveUpdateShzt = false;
		for (String id : idArray) {
			ExpendType oldModel = expendTypeDAO.load(Integer.parseInt(id));
			if(oldModel!=null&&oldModel.getStatus().intValue()!=model.getStatus().intValue()){
				oldModel.setStatus(model.getStatus());
				haveUpdateShzt = true;
			}
		}
		if(!haveUpdateShzt){
			result.setMessage("没有可修改状态的支出类型");
			return result;
		}
		result.setIsSuccess(true);
		return result;
	}

	@Override
	public ServiceResult updateArray(Integer expendTypeId, Integer updateExpendTypeId) {
		ServiceResult result = new ServiceResult(false);
		if(expendTypeId==null||updateExpendTypeId==null){
			result.setMessage("请选择要改变权限排序的权限");
			return result;
		}
		expendTypeDAO.updateArray(expendTypeId,updateExpendTypeId);
		
		result.setIsSuccess(true);
		return result;
	}

}
