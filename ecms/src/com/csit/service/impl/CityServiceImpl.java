package com.csit.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.csit.dao.CityDAO;
import com.csit.model.City;
import com.csit.model.Province;
import com.csit.service.CityService;
import com.csit.util.JSONUtil;
import com.csit.util.StringUtil;
import com.csit.vo.ServiceResult;
/**
 * @Description:城市Service实现类
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-5-29
 * @Author lys
 */
@Service
public class CityServiceImpl extends BaseServiceImpl<City, Integer> implements
		CityService {
	@Resource
	private CityDAO cityDAO;
	
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.CityService#save(com.csit.model.City)
	 */
	@Override
	public ServiceResult save(City model) {
		ServiceResult result = new ServiceResult(false);
		if(model==null){
			result.setMessage("请填写城市信息");
			return result;
		}
		if(StringUtils.isEmpty(model.getCityName())){
			result.setMessage("请填写城市名称");
			return result;
		}
		if(model.getProvince().getProvinceId()==null){
 			result.setMessage("请填写省份");
			return result;
		}
	
		if(model.getCityId()==null){//新增
			City oldModel = cityDAO.load("cityName", model.getCityName());
			if(oldModel != null){
				result.setMessage("该城市已存在");
				return result;
			}
			Integer array = cityDAO.getMaxArray();
			model.setArray(array+1);
			model.setCityId(33);
			cityDAO.save(model);
		}else{
			City oldModel = cityDAO.load(model.getCityId());
			if (oldModel == null) {
				result.setMessage("该城市已不存在");
				return result;
			}
			City oldModel2 = cityDAO.load("cityName", model.getCityName());
			if(oldModel2 != null && !(model.getCityId().equals(oldModel2.getCityId()))){
				result.setMessage("该城市已存在");
				return result;
			}
			oldModel.setProvince(model.getProvince());
			oldModel.setCityCode(model.getCityCode());
			oldModel.setCityName(model.getCityName());
			
		}
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.CityService#updateArray(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public ServiceResult updateArray(Integer cityId, Integer updateCityId){
		ServiceResult result = new ServiceResult(false);
		if(cityId==null||updateCityId==null){
			result.setMessage("请选择要改变排序的城市");
			return result;
		}
		cityDAO.updateArray(cityId,updateCityId);
		
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.CityService#mulDelete(java.lang.String)
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
				cityDAO.delete(Integer.parseInt(id));
				haveDelete = true;
			}
		}
		if(!haveDelete){
			result.setMessage("没有可删除的省份");
			return result;
		}
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.CityService#query(com.csit.model.City, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public String query(City model, Integer page, Integer rows) {
		List<City> list = cityDAO.query(model, page, rows);
		Long total = cityDAO.getTotalCount(model);

		String[] properties = { "province.provinceId","cityId","province.provinceCode","province.provinceName", "cityName", "cityCode","array" };
		String ajaxString = JSONUtil.toJson(list, properties, total);
		return ajaxString;
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.CityService#queryCombobox(com.csit.model.Province)
	 */
	@Override
	public String queryCombobox(Province province) {
		List<City> list = cityDAO.queryCombobox(province);
		String[] properties = {"cityId","cityName"};
		String jsonString = JSONUtil.toJsonWithoutRows(list,properties);
		return jsonString;
	}

}
