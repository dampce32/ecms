package com.csit.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.csit.dao.PayTypeDAO;
import com.csit.model.PayType;
import com.csit.service.PayTypeService;
import com.csit.util.JSONUtil;
import com.csit.util.StringUtil;
import com.csit.vo.GobelConstants;
import com.csit.vo.ServiceResult;

/**
 * @Description:缴费类型Service实现类
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-9
 * @author jcf
 * @vesion 1.0
 */
@Service
public class PayTypeServiceImpl extends BaseServiceImpl<PayType, Integer> implements PayTypeService {

	@Resource
	private PayTypeDAO payTypeDAO;
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.PayTypeService#delete(com.csit.model.PayType)
	 */
	public ServiceResult delete(PayType model) {
		ServiceResult result = new ServiceResult(false);
		if (model == null || model.getPayTypeId() == null) {
			result.setMessage("请选择要删除的缴费类型");
			return result;
		}
		PayType oldModel = payTypeDAO.load(model.getPayTypeId());
		if (oldModel == null) {
			result.setMessage("该缴费类型已不存在");
			return result;
		} else {
			payTypeDAO.delete(oldModel);
		}
		result.setIsSuccess(true);
		return result;
	}

	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.PayTypeService#query(com.csit.model.PayType, java.lang.Integer, java.lang.Integer)
	 */
	public String query(PayType model, Integer page, Integer rows) {

		List<PayType> list = payTypeDAO.query(model, page, rows);
		Long total = payTypeDAO.getTotalCount(model);

		String[] properties = { "payTypeId", "payTypeName","status", "array" };

		return JSONUtil.toJson(list, properties, total);
	}

	public String queryCombobox(PayType model) {
		List<PayType> list = payTypeDAO.queryCombobox(model);
		String[] properties = {"payTypeId","payTypeName"};
		String jsonString = JSONUtil.toJsonWithoutRows(list,properties);
		return jsonString;
	}

	public ServiceResult save(PayType model) {
		ServiceResult result = new ServiceResult(false);
		if (model == null) {
			result.setMessage("请填写缴费类型信息");
			return result;
		}else if (StringUtils.isEmpty(model.getPayTypeName())) {
			result.setMessage("请填写缴费类型名称");
			return result;
		}
		if (model.getPayTypeId() == null) {// 新增
			if(payTypeDAO.load("payTypeName", model.getPayTypeName())!=null){
				result.setMessage("缴费类型名称已存在");
				return result;
			}
			Integer maxArray=payTypeDAO.getMaxArray();
			model.setArray(maxArray+1);
			payTypeDAO.save(model);
		} else {
			PayType oldModel1=payTypeDAO.load("payTypeName", model.getPayTypeName());
			if(oldModel1!=null&&oldModel1.getPayTypeId().intValue()!=model.getPayTypeId().intValue()){
				result.setMessage("缴费类型名称已存在");
				return result;
			}
			PayType oldModel = payTypeDAO.load(model.getPayTypeId());
			if (oldModel == null) {
				result.setMessage("该缴费类型已不存在");
				return result;
			}
			oldModel.setPayTypeName(model.getPayTypeName());
			oldModel.setStatus(model.getStatus());
		}
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
		String[] idArray = StringUtil.split(ids, GobelConstants.SPLIT_SEPARATOR);
		if(idArray.length==0){
			result.setMessage("请选择要删除的记录");
			return result;
		}
		boolean haveDelete = false;
		for (String id : idArray) {
			PayType oldModel = payTypeDAO.load(Integer.parseInt(id));
			if (oldModel == null) {
				continue;
			}else{
				payTypeDAO.delete(Integer.parseInt(id));
				haveDelete = true;
			}
		}
		if(!haveDelete){
			result.setMessage("没有可删除的缴费类型");
			return result;
		}
		result.setIsSuccess(true);
		return result;
	}

	@Override
	public ServiceResult mulUpdateStatus(String ids, PayType model) {
		ServiceResult result = new ServiceResult(false);
		if(StringUtils.isEmpty(ids)){
			result.setMessage("请选择要修改状态的缴费类型");
			return result;
		}
		String[] idArray =StringUtil.split(ids);
		if(idArray.length==0){
			result.setMessage("请选择要修改状态的缴费类型");
			return result;
		}
		if(model==null||model.getStatus()==null){
			result.setMessage("请选择要修改成的状态");
			return result;
		}
		boolean haveUpdateShzt = false;
		for (String id : idArray) {
			PayType oldModel = payTypeDAO.load(Integer.parseInt(id));
			if(oldModel!=null&&oldModel.getStatus().intValue()!=model.getStatus().intValue()){
				oldModel.setStatus(model.getStatus());
				haveUpdateShzt = true;
			}
		}
		if(!haveUpdateShzt){
			result.setMessage("没有可修改状态的缴费类型");
			return result;
		}
		result.setIsSuccess(true);
		return result;
	}

	@Override
	public ServiceResult updateArray(Integer payTypeId, Integer updatePayTypeId) {
		ServiceResult result = new ServiceResult(false);
		if(payTypeId==null||updatePayTypeId==null){
			result.setMessage("请选择要改变权限排序的权限");
			return result;
		}
		payTypeDAO.updateArray(payTypeId,updatePayTypeId);
		
		result.setIsSuccess(true);
		return result;
	}

}
