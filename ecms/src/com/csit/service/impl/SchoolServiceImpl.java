package com.csit.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.csit.dao.SchoolDAO;
import com.csit.model.School;
import com.csit.service.SchoolService;
import com.csit.util.JSONUtil;
import com.csit.util.StringUtil;
import com.csit.vo.GobelConstants;
import com.csit.vo.ServiceResult;

/**
 * @Description:学校Service实现类
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-7-22
 * @author jcf
 * @vesion 1.0
 */
@Service
public class SchoolServiceImpl extends BaseServiceImpl<School, Integer> implements
		SchoolService {

	@Resource
	private SchoolDAO schoolDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.csit.service.SchoolService#query(com.csit.model.School,
	 * java.lang.Integer, java.lang.Integer)
	 */
	public String query(School model, Integer page, Integer rows) {
		List<School> list = schoolDAO.query(model, page, rows);
		Long total = schoolDAO.getTotalCount(model);

		String[] properties = { "schoolId", "schoolName", "schoolCode",
				"status", "area.city.cityName:cityName", "area.province.provinceId:provinceId",
				"area.province.provinceName:provinceName","area.city.cityId:cityId",
				"area.areaId","area.areaName",};

		return JSONUtil.toJson(list, properties, total);
	}

	public ServiceResult save(School model) {
		ServiceResult result = new ServiceResult(false);
		if (model == null) {
			result.setMessage("请填写学校信息");
			return result;
		}else if(StringUtils.isEmpty(model.getSchoolCode())){
			result.setMessage("请填写学校编号");
			return result;
		}else if (StringUtils.isEmpty(model.getSchoolName())) {
			result.setMessage("请填写学校名称");
			return result;
		}else if (model.getArea()==null) {
			result.setMessage("请选择地区");
			return result;
		}
		if (model.getSchoolId() == null) {// 新增
			if (schoolDAO.load("schoolCode", model.getSchoolCode()) != null) {
				result.setMessage("请学校编号已存在");
				return result;
			}else if (schoolDAO.load("schoolName", model.getSchoolName()) != null) {
				result.setMessage("请学校名称已存在");
				return result;
			}
			schoolDAO.save(model);
		} else {
			School oldModel1 = schoolDAO.load("schoolName", model.getSchoolName());
			if (oldModel1 != null && oldModel1.getSchoolId().intValue() != model.getSchoolId().intValue()) {
				result.setMessage("该学校名称已存在");
				return result;
			}
			School oldModel3 = schoolDAO.load("schoolCode", model.getSchoolCode());
			if (oldModel3 != null && oldModel3.getSchoolId().intValue() != model.getSchoolId().intValue()) {
				result.setMessage("该学校编号已存在");
				return result;
			}
			School oldModel2 = schoolDAO.load(model.getSchoolId());
			if (oldModel2 == null) {
				result.setMessage("该学校已不存在");
				return result;
			}
			oldModel2.setSchoolName(model.getSchoolName());
			oldModel2.setArea(model.getArea());
			oldModel2.setSchoolCode(model.getSchoolCode());
			oldModel2.setStatus(model.getStatus());
		}
		result.setIsSuccess(true);
		result.addData("schoolId", model.getSchoolId());
		return result;
	}

	@Override
	public ServiceResult mulDelete(String ids) {
		ServiceResult result = new ServiceResult(false);
		if (StringUtils.isEmpty(ids)) {
			result.setMessage("请选择要删除的记录");
			return result;
		}
		String[] idArray = StringUtil.split(ids, GobelConstants.SPLIT_SEPARATOR);
		if (idArray.length == 0) {
			result.setMessage("请选择要删除的记录");
			return result;
		}
		boolean haveDelete = false;
		for (String id : idArray) {
			School oldModel = schoolDAO.load(Integer.parseInt(id));
			if (oldModel == null) {
				continue;
			} else {
				schoolDAO.delete(Integer.parseInt(id));
				haveDelete = true;
			}
		}
		if (!haveDelete) {
			result.setMessage("没有可删除的学校");
			return result;
		}
		result.setIsSuccess(true);
		return result;
	}

	@Override
	public ServiceResult mulUpdateStatus(String ids, School model) {
		ServiceResult result = new ServiceResult(false);
		if(StringUtils.isEmpty(ids)){
			result.setMessage("请选择要修改状态的学校");
			return result;
		}
		String[] idArray =StringUtil.split(ids);
		if(idArray.length==0){
			result.setMessage("请选择要修改状态的学校");
			return result;
		}
		if(model==null||model.getStatus()==null){
			result.setMessage("请选择要修改成的状态");
			return result;
		}
		boolean haveUpdateShzt = false;
		for (String id : idArray) {
			School oldModel = schoolDAO.load(Integer.parseInt(id));
			if(oldModel!=null&&oldModel.getStatus().intValue()!=model.getStatus().intValue()){
				oldModel.setStatus(model.getStatus());
				haveUpdateShzt = true;
			}
		}
		if(!haveUpdateShzt){
			result.setMessage("没有可修改状态的学校");
			return result;
		}
		result.setIsSuccess(true);
		return result;
	}

	@Override
	public String queryCombobox(School model) {
		List<School> list = schoolDAO.queryCombobox(model);
		String[] properties = {"schoolId","schoolName"};
		String jsonString = JSONUtil.toJsonWithoutRows(list,properties);
		return jsonString;
	}

}
