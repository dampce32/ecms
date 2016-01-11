package com.csit.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.csit.dao.DataDictionaryDAO;
import com.csit.model.DataDictionary;
import com.csit.model.Teacher;
import com.csit.service.DataDictionaryService;
import com.csit.util.JSONUtil;
import com.csit.util.StringUtil;
import com.csit.vo.GobelConstants;
import com.csit.vo.ServiceResult;
/**
 * @Description:数据字典Service实现类
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-4-18
 * @Author lys
 */
@Service
public class DataDictionaryServiceImpl extends
		BaseServiceImpl<DataDictionary, Integer> implements
		DataDictionaryService {
	@Resource
	private DataDictionaryDAO dataDictionaryDAO;
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.DataDictionaryService#save(com.csit.model.DataDictionary, java.lang.Integer)
	 */
	@Override
	public ServiceResult save(DataDictionary model, Integer teacherId) {
		ServiceResult result = new ServiceResult(false);
		if(StringUtils.isEmpty(model.getDataDictionaryCode())){
			result.setMessage("请填写数据字典编号");
			return result;
		}
		if(StringUtils.isEmpty(model.getDataDictionaryName())){
			result.setMessage("请填写数据字典名");
			return result;
		}
		
		Teacher teacher = new Teacher();
		teacher.setTeacherId(teacherId);
		model.setTeacher(teacher);
		model.setOperateTime(com.csit.util.DateUtil.getNowTimestamp());
		
		if(model.getDataDictionaryId()==null){//新增
			//判断是否该类型是否已有该数据字典编号
			String[] propertyNames={"dataDictionaryCode","dataDictionaryType"};
			Object[] values ={model.getDataDictionaryCode(),model.getDataDictionaryType()};
			DataDictionary oldDataDictionary = dataDictionaryDAO.load(propertyNames,values);
			if(oldDataDictionary!=null){
				String msg = "已存在该";
				if("Lesson".equals(model.getDataDictionaryType())){
					msg+="课程";
				}else if("Grade".equals(model.getDataDictionaryType())){
					msg+="年级";
				}else if("Major".equals(model.getDataDictionaryType())){
					msg+="专业";
				}else if("Room".equals(model.getDataDictionaryType())){
					msg+="考场";
				}else if("Class".equals(model.getDataDictionaryType())){
					msg+="班级";
				}
				msg+="编号";
				result.setMessage(msg);
				return result;
			}
			//取得相同类型的数据字典的最大排序值
			Integer maxArray = dataDictionaryDAO.getMaxArray(model.getDataDictionaryType());
			model.setArray(maxArray+1);
			dataDictionaryDAO.save(model);
		}else{
			DataDictionary oldModel = dataDictionaryDAO.load(model.getDataDictionaryId());
			oldModel.setDataDictionaryCode(model.getDataDictionaryCode());
			oldModel.setDataDictionaryName(model.getDataDictionaryName());
			oldModel.setState(model.getState());
			oldModel.setNote(model.getNote());
			oldModel.setTeacher(model.getTeacher());
			oldModel.setOperateTime(model.getOperateTime());
			if("room".equals(model.getDataDictionaryType())){
				oldModel.setRoomComputerCount(model.getRoomComputerCount());
			}
		}
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.DataDictionaryService#query(java.lang.Integer, java.lang.Integer, com.csit.model.DataDictionary)
	 */
	@Override
	public String query(Integer page, Integer rows, DataDictionary model) {
		List<DataDictionary> list = dataDictionaryDAO.query(page,rows,model);
		Long total = dataDictionaryDAO.count(model);
		String[] properties = {"dataDictionaryId","dataDictionaryCode","dataDictionaryName","state","note","dataDictionaryType","roomComputerCount"};
		return JSONUtil.toJson(list, properties, total);
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.DataDictionaryService#mulDelete(java.lang.String)
	 */
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
			DataDictionary item = dataDictionaryDAO.load(Integer.parseInt(idArray[0]));
			if("超级管理员".equals(item.getDataDictionaryName())){
				continue;
			}else{
				if(StringUtils.isNotEmpty(idArray[0])){
					dataDictionaryDAO.delete(Integer.parseInt(id));
					haveDelete = true;
				}
			}
		}
		if(!haveDelete){
			result.setMessage("没有可删除的数据字典");
			return result;
		}
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.DataDictionaryService#mulUpdateState(java.lang.String, com.csit.model.DataDictionary, java.lang.Integer)
	 */
	@Override
	public ServiceResult mulUpdateState(String ids, DataDictionary model, Integer teacherId) {
		ServiceResult result = new ServiceResult(false);
		if(StringUtils.isEmpty(ids)){
			result.setMessage("请选择要修改状态的数据字典");
			return result;
		}
		String[] idArray =StringUtil.split(ids);
		if(idArray.length==0){
			result.setMessage("请选择要修改状态的数据字典");
			return result;
		}
		if(model==null||model.getState()==null){
			result.setMessage("请选择要修改成的状态");
			return result;
		}
		boolean haveUpdateShzt = false;
		for (String id : idArray) {
			DataDictionary oldDataDictionary = dataDictionaryDAO.load(Integer.parseInt(id));
			if(oldDataDictionary!=null&&oldDataDictionary.getState()!=model.getState()){
				oldDataDictionary.setState(model.getState());
				haveUpdateShzt = true;
			}
		}
		if(!haveUpdateShzt){
			result.setMessage("没有可修改状态的数据字典");
			return result;
		}
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.DataDictionaryService#queryCombobox(com.csit.model.DataDictionary)
	 */
	@Override
	public String queryCombobox(DataDictionary model) {
		String jsonString="[]";
		if(model==null||StringUtils.isEmpty(model.getDataDictionaryType())){
			return jsonString;
		}
		List<DataDictionary> list = dataDictionaryDAO.queryCombobox(model);
		String[] properties = {"dataDictionaryId","dataDictionaryName"};
		jsonString = JSONUtil.toJsonWithoutRows(list,properties);
		return jsonString;
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.DataDictionaryService#queryTypeCombobox(com.csit.model.DataDictionary)
	 */
	@Override
	public String queryTypeCombobox(DataDictionary model) {
		String jsonString="[]";
		if(model==null||StringUtils.isEmpty(model.getDataDictionaryType())){
			return jsonString;
		}
		List<DataDictionary> list = dataDictionaryDAO.queryCombobox(model);
		String[] properties = {"dataDictionaryId:"+model.getDataDictionaryType().toLowerCase()+"Id","dataDictionaryName:"+model.getDataDictionaryType().toLowerCase()+"Name"};
		jsonString = JSONUtil.toJsonWithoutRows(list,properties);
		return jsonString;
	}

}
