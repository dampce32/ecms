package com.csit.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.csit.dao.ProvinceDAO;
import com.csit.model.Province;
import com.csit.service.ProvinceService;
import com.csit.util.JSONUtil;
import com.csit.util.StringUtil;
import com.csit.vo.ServiceResult;
/**
 * @Description: 省份Service实现类
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-5-29
 * @Author lys
 */
@Service
public class ProvinceServiceImpl extends BaseServiceImpl<Province, Integer>
		implements ProvinceService {
	@Resource
	private ProvinceDAO provinceDAO;
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.ProvinceService#save(com.csit.model.Province)
	 */
	@Override
	public ServiceResult save(Province model) {
		ServiceResult result = new ServiceResult(false);
		if(model==null){
			result.setMessage("请填写省份信息");
			return result;
		}
		if(StringUtils.isEmpty(model.getProvinceName())){
			result.setMessage("请填写省份名称");
			return result;
		}
		//新增
		if(model.getProvinceId()==null){
			Province oldModel = provinceDAO.load("provinceName", model.getProvinceName());
			if(oldModel != null){
				result.setMessage("该省份已存在");
				return result;
			}
			Integer array = provinceDAO.getMaxArray();
			model.setArray(array+1);
			provinceDAO.save(model);
		}else{
			Province oldModel = provinceDAO.load(model.getProvinceId());
			if (oldModel == null) {
				result.setMessage("该省份已不存在");
				return result;
			}
			Province oldModel2 = provinceDAO.load("provinceName", model.getProvinceName());
			if(oldModel2 != null && !(model.getProvinceId().equals(oldModel2.getProvinceId()))){
				result.setMessage("该省份已存在");
				return result;
			}
			oldModel.setProvinceName(model.getProvinceName());
			oldModel.setProvinceCode(model.getProvinceCode());
		}
		result.setIsSuccess(true);
		return result;
	}

	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.ProvinceService#mulDelete(java.lang.String)
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
				provinceDAO.delete(Integer.parseInt(id));
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
	 * @see com.csit.service.ProvinceService#updateArray(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public ServiceResult updateArray(Integer provinceId, Integer updateProvinceId) {
		ServiceResult result = new ServiceResult(false);
		if(provinceId==null||updateProvinceId==null){
			result.setMessage("请选择要改变排序的省份");
			return result;
		}
		provinceDAO.updateArray(provinceId,updateProvinceId);
		
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.ProvinceService#query(com.csit.model.Province, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public String query(Province model, Integer page, Integer rows) {
		List<Province> list = provinceDAO.query(model, page, rows);
		Long total = provinceDAO.getTotalCount(model);

		String[] properties = { "provinceId", "provinceName", "provinceCode","array" };

		return JSONUtil.toJson(list, properties, total);
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.ProvinceService#queryCombobox()
	 */
	@Override
	public String queryCombobox() {
		List<Province> list = provinceDAO.queryCombobox();
		String[] properties = {"provinceId","provinceName"};
		String jsonString = JSONUtil.toJsonWithoutRows(list,properties);
		return jsonString;
	}
	
	

}
