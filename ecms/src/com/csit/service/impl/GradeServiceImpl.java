package com.csit.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.csit.dao.GradeDAO;
import com.csit.model.Grade;
import com.csit.service.GradeService;
import com.csit.util.JSONUtil;
import com.csit.util.StringUtil;
import com.csit.vo.ServiceResult;
/**
 * 
 * @Description: 年级Service实现类
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-7-22
 * @author yk
 * @vesion 1.0
 */
@Service
public class GradeServiceImpl extends BaseServiceImpl<Grade, Integer>
		implements GradeService {
	@Resource
	private GradeDAO gradeDAO;
	@Override
	public ServiceResult save(Grade model) {
		ServiceResult result = new ServiceResult(false);
		if(model==null){
			result.setMessage("请填写年级信息");
			return result;
		}
		if(StringUtils.isEmpty(model.getGradeName())){
			result.setMessage("请填写年级名称");
			return result;
		}
		//状态
		if(model.getStatus()==null){
			result.setMessage("请选择状态");
			return result;
		}
		
		if(model.getGradeId()==null){//新增
			Grade oldModel = gradeDAO.load("gradeName", model.getGradeName());
			if(oldModel != null){
				result.setMessage("该年级已存在");
				return result;
			}
			Integer array = gradeDAO.getMaxArray();
			model.setArray(array+1);
			gradeDAO.save(model);
		}else{
			Grade oldModel = gradeDAO.load(model.getGradeId());
			if(oldModel == null) {
				result.setMessage("该年级已不存在");
				return result;
			}
			Grade oldModel2 = gradeDAO.load("gradeName", model.getGradeName());
			if( oldModel2 != null && !model.getGradeId().equals(oldModel2.getGradeId()) ){
				result.setMessage("该年级已存在");
				return result;
			}
			oldModel.setGradeName(model.getGradeName());
			oldModel.setStatus(model.getStatus());
		}
		result.setIsSuccess(true);
		return result;
	}

	@Override
	public ServiceResult query(Grade model, Integer page, Integer rows) {
		ServiceResult result = new ServiceResult(false);
		List<Grade> list = gradeDAO.query(model,page,rows);
		
		String[] properties = {"gradeId","gradeName","array","status"};
		
		String data = JSONUtil.toJson(list,properties);
		result.addData("datagridData", data);
		
		result.setIsSuccess(true);
		return result;
	}

	@Override
	public ServiceResult getTotalCount(Grade model) {
		ServiceResult result = new ServiceResult(false);
		Long data = gradeDAO.getTotalCount(model);
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
				gradeDAO.delete(Integer.parseInt(id));
				haveDelete = true;
			}
		}
		if(!haveDelete){
			result.setMessage("没有可删除的年级");
			return result;
		}
		result.setIsSuccess(true);
		return result;
	}

	@Override
	public ServiceResult mulUpdateStatus(String ids, Grade model) {
		ServiceResult result = new ServiceResult(false);
		if(StringUtils.isEmpty(ids)){
			result.setMessage("请选择要修改状态的年级");
			return result;
		}
		String[] idArray =StringUtil.split(ids);
		if(idArray.length==0){
			result.setMessage("请选择要修改状态的年级");
			return result;
		}
		if(model==null||model.getStatus()==null){
			result.setMessage("请选择要修改成的状态");
			return result;
		}
		boolean haveUpdateShzt = false;
		
		for (String id : idArray) {
			Grade oldModel = gradeDAO.load(Integer.parseInt(id));
			if(oldModel!=null&&oldModel.getStatus()!=model.getStatus()){
				oldModel.setStatus(model.getStatus());
				
				haveUpdateShzt = true;
			}
		}
		if(!haveUpdateShzt){
			result.setMessage("没有可修改状态的年级");
			return result;
		}
		result.setIsSuccess(true);
		return result;
	}

	@Override
	public ServiceResult updateArray(String gradeId, String updateGradeId) {
		ServiceResult result = new ServiceResult(false);
		if(gradeId==null||updateGradeId==null){
			result.setMessage("请选择要改变排序的年级");
			return result;
		}
		gradeDAO.updateArray(gradeId,updateGradeId);
		
		result.setIsSuccess(true);
		return result;
	}

	@Override
	public String selectQuery(Grade model, Integer schoolId, Integer page,
			Integer rows) {
		List<Map<String,Object>> listMap = gradeDAO.queryUsedSchool(model, schoolId, page, rows);
		Long total = gradeDAO.getTotalCountUsedSchool(model, schoolId);
		return JSONUtil.toJsonFromListMap(listMap,total);
	}

}
