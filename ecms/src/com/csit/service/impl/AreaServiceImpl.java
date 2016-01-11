package com.csit.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.csit.dao.AreaDAO;
import com.csit.model.Area;
import com.csit.model.City;
import com.csit.service.AreaService;
import com.csit.util.JSONUtil;
import com.csit.util.StringUtil;
import com.csit.vo.ServiceResult;
/**
 * @Description:区县Service实现类
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-5-29
 * @Author lys
 */
@Service
public class AreaServiceImpl extends BaseServiceImpl<Area, Integer> implements
		AreaService {
	@Resource
	private AreaDAO areaDAO;
	

	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.AreaService#save(com.csit.model.Prize)
	 */
	@Override
	public ServiceResult save(Area model) {
		ServiceResult result = new ServiceResult(false);
		if(model==null){
			result.setMessage("请填写区县信息");
			return result;
		}
		if(StringUtils.isEmpty(model.getAreaName())){
			result.setMessage("请填写区县名称");
			return result;
		}
		if(model.getProvince().getProvinceId()==null){
 			result.setMessage("请填写省份");
			return result;
		}
		if(model.getCity().getCityId()==null){
 			result.setMessage("请填写城市");
			return result;
		}
	
		if(model.getAreaId()==null){//新增
			Area oldModel = areaDAO.load("areaName", model.getAreaName());
			if(oldModel != null){
				result.setMessage("该区县已存在");
				return result;
			}
			Integer array = areaDAO.getMaxArray();
			model.setArray(array+1);
			areaDAO.save(model);
		}else{
			Area oldModel = areaDAO.load(model.getAreaId());
			if (oldModel == null) {
				result.setMessage("该区县已不存在");
				return result;
			}
			Area oldModel2 = areaDAO.load("areaName", model.getAreaName());
			if(oldModel2 != null && !(model.getAreaId().equals(oldModel2.getAreaId()))){
				result.setMessage("该区县已存在");
				return result;
			}
			oldModel.setProvince(model.getProvince());
			oldModel.setCity(model.getCity());
			oldModel.setAreaCode(model.getAreaCode());
			oldModel.setAreaName(model.getAreaName());
			
		}
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.AreaService#updateArray(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public ServiceResult updateArray(Integer areaId, Integer updateAreaId) {
		ServiceResult result = new ServiceResult(false);
		if(areaId==null||updateAreaId==null){
			result.setMessage("请选择要改变排序的区县");
			return result;
		}
		areaDAO.updateArray(areaId,updateAreaId);
		
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.AreaService#mulDelete(java.lang.String)
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
				areaDAO.delete(Integer.parseInt(id));
				haveDelete = true;
			}
		}
		if(!haveDelete){
			result.setMessage("没有可删除的区县");
			return result;
		}
		result.setIsSuccess(true);
		return result;
	}
	
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.AreaService#query(com.csit.model.Area, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public String query(Area model, Integer page, Integer rows) {
		List<Area> list = areaDAO.query(model, page, rows);
		Long total = areaDAO.getTotalCount(model);

		String[] properties = { "province.provinceId","city.cityId","areaId","province.provinceCode","province.provinceName", "city.cityName", "city.cityCode", "areaName", "areaCode","array" };

		return JSONUtil.toJson(list, properties, total);
	}
	
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.AreaService#queryCombobox(com.csit.model.City)
	 */
	@Override
	public String queryCombobox(City city) {
		List<Area> list = areaDAO.queryCombobox(city);
		String[] properties = {"areaId","areaName"};
		String jsonString = JSONUtil.toJsonWithoutRows(list,properties);
		return jsonString;
	}

}
