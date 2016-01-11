package com.csit.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.csit.dao.NationDAO;
import com.csit.model.Nation;
import com.csit.service.NationService;
import com.csit.util.JSONUtil;
import com.csit.util.StringUtil;
import com.csit.vo.ServiceResult;
/**
 * 
 * @Description: 民族Service实现类
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-5-28
 * @author yk
 * @vesion 1.0
 */
@Service
public class NationServiceImpl extends BaseServiceImpl<Nation, Integer>
		implements NationService {
	@Resource
	private NationDAO nationDAO;
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.NationService#save(com.csit.model.Nation, java.lang.Integer)
	 */
	@Override
	public ServiceResult save(Nation model) {
		ServiceResult result = new ServiceResult(false);
		if(model==null){
			result.setMessage("请填写民族信息");
			return result;
		}
		if(StringUtils.isEmpty(model.getNationName())){
			result.setMessage("请填写民族名称");
			return result;
		}
		//状态
		if(model.getStatus()==null){
			result.setMessage("请选择状态");
			return result;
		}
		
		if(model.getNationId()==null){//新增
			Nation oldModel = nationDAO.load("nationName", model.getNationName());
			if(oldModel != null){
				result.setMessage("该民族已存在");
				return result;
			}
			Integer array = nationDAO.getMaxArray();
			model.setArray(array+1);
			nationDAO.save(model);
		}else{
			Nation oldModel = nationDAO.load(model.getNationId());
			if(oldModel == null) {
				result.setMessage("该民族已不存在");
				return result;
			}
			Nation oldModel2 = nationDAO.load("nationName", model.getNationName());
			if( oldModel2 != null && !model.getNationId().equals(oldModel2.getNationId()) ){
				result.setMessage("该民族已存在");
				return result;
			}
			oldModel.setNationName(model.getNationName());
			oldModel.setStatus(model.getStatus());
		}
		result.setIsSuccess(true);
		return result;
	}

	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.NationService#query(com.csit.model.Nation, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public ServiceResult query(Nation model, Integer page, Integer rows) {
		ServiceResult result = new ServiceResult(false);
		List<Nation> list = nationDAO.query(model,page,rows);
		
		String[] properties = {"nationId","nationName","array","status"};
		
		String data = JSONUtil.toJson(list,properties);
		result.addData("datagridData", data);
		
		result.setIsSuccess(true);
		return result;
	}

	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.NationService#getTotalCount(com.csit.model.Nation)
	 */
	@Override
	public ServiceResult getTotalCount(Nation model) {
		ServiceResult result = new ServiceResult(false);
		Long data = nationDAO.getTotalCount(model);
		result.addData("total", data);
		result.setIsSuccess(true);
		return result;
	}

	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.NationService#mulDelete(java.lang.String)
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
				nationDAO.delete(Integer.parseInt(id));
				haveDelete = true;
			}
		}
		if(!haveDelete){
			result.setMessage("没有可删除的民族");
			return result;
		}
		result.setIsSuccess(true);
		return result;
	}

	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.NationService#mulUpdateState(java.lang.String, com.csit.model.Nation)
	 */
	@Override
	public ServiceResult mulUpdateState(String ids, Nation model) {
		ServiceResult result = new ServiceResult(false);
		if(StringUtils.isEmpty(ids)){
			result.setMessage("请选择要修改状态的民族");
			return result;
		}
		String[] idArray =StringUtil.split(ids);
		if(idArray.length==0){
			result.setMessage("请选择要修改状态的民族");
			return result;
		}
		if(model==null||model.getStatus()==null){
			result.setMessage("请选择要修改成的状态");
			return result;
		}
		boolean haveUpdateShzt = false;
		
		for (String id : idArray) {
			Nation oldModel = nationDAO.load(Integer.parseInt(id));
			if(oldModel!=null&&oldModel.getStatus()!=model.getStatus()){
				oldModel.setStatus(model.getStatus());
				
				haveUpdateShzt = true;
			}
		}
		if(!haveUpdateShzt){
			result.setMessage("没有可修改状态的民族");
			return result;
		}
		result.setIsSuccess(true);
		return result;
	}

	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.NationService#updateArray(java.lang.String, java.lang.String)
	 */
	@Override
	public ServiceResult updateArray(String nationId, String updateNationId) {
		ServiceResult result = new ServiceResult(false);
		if(nationId==null||updateNationId==null){
			result.setMessage("请选择要改变排序的民族");
			return result;
		}
		nationDAO.updateArray(nationId,updateNationId);
		
		result.setIsSuccess(true);
		return result;
	}

	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.NationService#queryCombobox()
	 */
	@Override
	public String queryCombobox() {
		List<Nation> list = nationDAO.queryCombobox();
		String[] properties = {"nationId","nationName"};
		String jsonString = JSONUtil.toJsonWithoutRows(list,properties);
		return jsonString;
	}

}
