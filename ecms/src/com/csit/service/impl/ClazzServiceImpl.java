package com.csit.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.csit.dao.ClazzDAO;
import com.csit.model.Clazz;
import com.csit.service.ClazzService;
import com.csit.util.JSONUtil;
import com.csit.util.StringUtil;
import com.csit.vo.ServiceResult;
@Service
public class ClazzServiceImpl extends BaseServiceImpl<Clazz, Integer>
		implements ClazzService {
	@Resource
	private ClazzDAO clazzDAO;
	@Override
	public ServiceResult save(Clazz model) {
		ServiceResult result = new ServiceResult(false);
		if(model==null){
			result.setMessage("请填写班级信息");
			return result;
		}
		if(StringUtils.isEmpty(model.getClazzName())){
			result.setMessage("请填写班级名称");
			return result;
		}
		//状态
		if(model.getStatus()==null){
			result.setMessage("请选择状态");
			return result;
		}
		
		if(model.getClazzId()==null){//新增
			Clazz oldModel = clazzDAO.load("clazzName", model.getClazzName());
			if(oldModel != null){
				result.setMessage("该班级已存在");
				return result;
			}
			Integer array = clazzDAO.getMaxArray();
			model.setArray(array+1);
			clazzDAO.save(model);
		}else{
			Clazz oldModel = clazzDAO.load(model.getClazzId());
			if(oldModel == null) {
				result.setMessage("该班级已不存在");
				return result;
			}
			Clazz oldModel2 = clazzDAO.load("clazzName", model.getClazzName());
			if( oldModel2 != null && !model.getClazzId().equals(oldModel2.getClazzId()) ){
				result.setMessage("该班级已存在");
				return result;
			}
			oldModel.setClazzName(model.getClazzName());
			oldModel.setStatus(model.getStatus());
		}
		result.setIsSuccess(true);
		return result;
	}

	@Override
	public ServiceResult query(Clazz model, Integer page, Integer rows) {
		ServiceResult result = new ServiceResult(false);
		List<Clazz> list = clazzDAO.query(model,page,rows);
		
		String[] properties = {"clazzId","clazzName","array","status"};
		
		String data = JSONUtil.toJson(list,properties);
		result.addData("datagridData", data);
		
		result.setIsSuccess(true);
		return result;
	}

	@Override
	public ServiceResult getTotalCount(Clazz model) {
		ServiceResult result = new ServiceResult(false);
		Long data = clazzDAO.getTotalCount(model);
		result.addData("total", data);
		result.setIsSuccess(true);
		return result;
	}

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
				clazzDAO.delete(Integer.parseInt(id));
				haveDelete = true;
			}
		}
		if(!haveDelete){
			result.setMessage("没有可删除的班级");
			return result;
		}
		result.setIsSuccess(true);
		return result;
	}

	@Override
	public ServiceResult mulUpdateStatus(String ids, Clazz model) {
		ServiceResult result = new ServiceResult(false);
		if(StringUtils.isEmpty(ids)){
			result.setMessage("请选择要修改状态的班级");
			return result;
		}
		String[] idArray =StringUtil.split(ids);
		if(idArray.length==0){
			result.setMessage("请选择要修改状态的班级");
			return result;
		}
		if(model==null||model.getStatus()==null){
			result.setMessage("请选择要修改成的状态");
			return result;
		}
		boolean haveUpdateShzt = false;
		
		for (String id : idArray) {
			Clazz oldModel = clazzDAO.load(Integer.parseInt(id));
			if(oldModel!=null&&oldModel.getStatus()!=model.getStatus()){
				oldModel.setStatus(model.getStatus());
				
				haveUpdateShzt = true;
			}
		}
		if(!haveUpdateShzt){
			result.setMessage("没有可修改状态的班级");
			return result;
		}
		result.setIsSuccess(true);
		return result;
	}

	@Override
	public ServiceResult updateArray(String clazzId, String updateClazzId) {
		ServiceResult result = new ServiceResult(false);
		if(clazzId==null||updateClazzId==null){
			result.setMessage("请选择要改变排序的班级");
			return result;
		}
		clazzDAO.updateArray(clazzId,updateClazzId);
		
		result.setIsSuccess(true);
		return result;
	}

	@Override
	public String selectQuery(Clazz model, Integer schoolGradeId, Integer page,
			Integer rows) {
		List<Map<String,Object>> listMap = clazzDAO.selectQuery(model, schoolGradeId, page, rows);
		Long total = clazzDAO.getSelectTotalCount(model, schoolGradeId);
		return JSONUtil.toJsonFromListMap(listMap,total);
	}

}
