package com.csit.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.csit.dao.PrizeDAO;
import com.csit.model.Prize;
import com.csit.service.PrizeService;
import com.csit.util.JSONUtil;
import com.csit.util.StringUtil;
import com.csit.vo.ServiceResult;
/**
 * @Description:
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-5-30
 * @author wxy
 * @vesion 1.0
 */
@Service
public class PrizeServiceImpl extends BaseServiceImpl<Prize, Integer>
		implements PrizeService {
	@Resource
	private PrizeDAO prizeDAO;
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.PrizeService#save(com.csit.model.Prize)
	 */
	@Override
	public ServiceResult save(Prize model) {
		ServiceResult result = new ServiceResult(false);
		if(model==null){
			result.setMessage("请填写奖项信息");
			return result;
		}
		if(StringUtils.isEmpty(model.getPrizeName())){
			result.setMessage("请填写奖项名称");
			return result;
		}
		//状态
		if(model.getStatus()==null){
			result.setMessage("请选择状态");
			return result;
		}
		if(model.getPrizeId()==null){//新增
			Prize oldModel = prizeDAO.load("prizeName", model.getPrizeName());
			if(oldModel != null){
				result.setMessage("该奖项已存在");
				return result;
			}
			Integer array = prizeDAO.getMaxArray();
			model.setArray(array+1);
			prizeDAO.save(model);
		}else{
			Prize oldModel = prizeDAO.load(model.getPrizeId());
			if (oldModel == null) {
				result.setMessage("该奖项已不存在");
				return result;
			}
			Prize oldModel2 = prizeDAO.load("prizeName", model.getPrizeName());
			if(oldModel2 != null && !(model.getPrizeId().equals(oldModel2.getPrizeId()))){
				result.setMessage("该奖项已存在");
				return result;
			}
			oldModel.setPrizeName(model.getPrizeName());
			oldModel.setStatus(model.getStatus());
		}
		result.setIsSuccess(true);
		return result;
	}

	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.PrizeService#query(com.csit.model.Prize, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public String query(Prize model, Integer page, Integer rows) {
		List<Prize> list = prizeDAO.query(model,page,rows);
		Long total = prizeDAO.getTotalCount(model);
		String[] properties = {"prizeId","prizeName","array","status"};
		return JSONUtil.toJson(list, properties, total);
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.PrizeService#query(java.lang.Integer, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public String query(Integer competitionGroupId, Integer page,Integer rows) {
		List<Prize> list = prizeDAO.query(competitionGroupId, page, rows);
		Long total = prizeDAO.getTotalCount(competitionGroupId);
		String[] properties = {"prizeId","prizeName","array","status"};
		return JSONUtil.toJson(list, properties, total);
	}

	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.PrizeService#mulDelete(java.lang.String)
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
				prizeDAO.delete(Integer.parseInt(id));
				haveDelete = true;
			}
		}
		if(!haveDelete){
			result.setMessage("没有可删除的奖项");
			return result;
		}
		result.setIsSuccess(true);
		return result;
	}

	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.PrizeService#mulUpdateState(java.lang.String, com.csit.model.Prize)
	 */
	@Override
	public ServiceResult mulUpdateState(String ids, Prize model) {
		ServiceResult result = new ServiceResult(false);
		if(StringUtils.isEmpty(ids)){
			result.setMessage("请选择要修改状态的奖项");
			return result;
		}
		String[] idArray =StringUtil.split(ids);
		if(idArray.length==0){
			result.setMessage("请选择要修改状态的奖项");
			return result;
		}
		if(model==null||model.getStatus()==null){
			result.setMessage("请选择要修改成的状态");
			return result;
		}
		boolean haveUpdateShzt = false;
		
		for (String id : idArray) {
			Prize oldModel = prizeDAO.load(Integer.parseInt(id));
			if(oldModel!=null&&oldModel.getStatus()!=model.getStatus()){
				oldModel.setStatus(model.getStatus());
				
				haveUpdateShzt = true;
			}
		}
		if(!haveUpdateShzt){
			result.setMessage("没有可修改状态的奖项");
			return result;
		}
		result.setIsSuccess(true);
		return result;
	}

	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.PrizeService#updateArray(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public ServiceResult updateArray(Integer prizeId, Integer updatePrizeId) {
		ServiceResult result = new ServiceResult(false);
		if(prizeId==null||updatePrizeId==null){
			result.setMessage("请选择要改变排序的奖项");
			return result;
		}
		prizeDAO.updateArray(prizeId,updatePrizeId);
		
		result.setIsSuccess(true);
		return result;
	}

	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.PrizeService#queryCombobox()
	 */
	@Override
	public String queryCombobox() {
		List<Prize> list = prizeDAO.query("status", 1);
		String[] properties = {"PrizeId","PrizeName"};
		String jsonString = JSONUtil.toJsonWithoutRows(list,properties);
		return jsonString;
	}
	


}
