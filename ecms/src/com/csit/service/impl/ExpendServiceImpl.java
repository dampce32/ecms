package com.csit.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.csit.dao.ExpendDAO;
import com.csit.model.Expend;
import com.csit.service.ExpendService;
import com.csit.util.JSONUtil;
import com.csit.util.StringUtil;
import com.csit.vo.GobelConstants;
import com.csit.vo.ServiceResult;

/**
 * @Description:支出Service实现类
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-9
 * @author jcf
 * @vesion 1.0
 */
@Service
public class ExpendServiceImpl extends BaseServiceImpl<Expend, Integer> implements ExpendService {

	@Resource
	private ExpendDAO expendDAO;
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.ExpendService#delete(com.csit.model.Expend)
	 */
	public ServiceResult delete(Expend model) {
		ServiceResult result = new ServiceResult(false);
		if (model == null || model.getExpendId() == null) {
			result.setMessage("请选择要删除的支出");
			return result;
		}
		Expend oldModel = expendDAO.load(model.getExpendId());
		if (oldModel == null) {
			result.setMessage("该支出已不存在");
			return result;
		} else {
			expendDAO.delete(oldModel);
		}
		result.setIsSuccess(true);
		return result;
	}

	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.ExpendService#query(com.csit.model.Expend, java.lang.Integer, java.lang.Integer)
	 */
	public String query(Expend model, Integer page, Integer rows) {

		List<Expend> list = expendDAO.query(model, page, rows);
		Long total = expendDAO.getTotalCount(model);

		String[] properties = { "expendId", "expendType.expendTypeName","status", "competition.competitionName"
								,"competition.competitionId","fee", "expendType.expendTypeId"
								,"expendDate", "expendName","expendCorporation", "handler","note"};

		return JSONUtil.toJson(list, properties, total);
	}

	public ServiceResult save(Expend model) {
		ServiceResult result = new ServiceResult(false);
		if (model == null) {
			result.setMessage("请填写支出信息");
			return result;
		}else if (model.getExpendDate()==null) {
			result.setMessage("请填写支出日期");
			return result;
			
		}else if (StringUtils.isEmpty(model.getExpendName())) {
			result.setMessage("请填写项目名称");
			return result;
		}else if(model.getCompetition()==null){
			result.setMessage("请选择赛事");
			return result;
			
		}else if (model.getExpendType()==null) {
			result.setMessage("请选择支出类型");
			return result;
			
		}else if (model.getFee()==null) {
			result.setMessage("请填写支出金额");
			return result;
		}
		if (model.getExpendId() == null) {// 新增
			model.setStatus(0);
			expendDAO.save(model);
		} else {
			Expend oldModel = expendDAO.load(model.getExpendId());
			if (oldModel == null) {
				result.setMessage("该支出已不存在");
				return result;
			}
			oldModel.setCompetition(model.getCompetition());
			oldModel.setExpendCorporation(model.getExpendCorporation());
			oldModel.setExpendDate(model.getExpendDate());
			oldModel.setExpendType(model.getExpendType());
			oldModel.setFee(model.getFee());
			oldModel.setHandler(model.getHandler());
			oldModel.setNote(model.getNote());
			oldModel.setExpendName(model.getExpendName());
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
			Expend oldModel = expendDAO.load(Integer.parseInt(id));
			if (oldModel == null) {
				continue;
			}else{
				expendDAO.delete(Integer.parseInt(id));
				haveDelete = true;
			}
		}
		if(!haveDelete){
			result.setMessage("没有可删除的支出");
			return result;
		}
		result.setIsSuccess(true);
		return result;
	}

	@Override
	public ServiceResult mulUpdateStatus(String ids, Expend model) {
		ServiceResult result = new ServiceResult(false);
		if(StringUtils.isEmpty(ids)){
			result.setMessage("请选择要修改状态的支出");
			return result;
		}
		String[] idArray =StringUtil.split(ids);
		if(idArray.length==0){
			result.setMessage("请选择要修改状态的支出");
			return result;
		}
		if(model==null||model.getStatus()==null){
			result.setMessage("请选择要修改成的状态");
			return result;
		}
		boolean haveUpdateShzt = false;
		for (String id : idArray) {
			Expend oldModel = expendDAO.load(Integer.parseInt(id));
			if(oldModel!=null&&oldModel.getStatus().intValue()!=model.getStatus().intValue()){
				oldModel.setStatus(model.getStatus());
				haveUpdateShzt = true;
			}
		}
		if(!haveUpdateShzt){
			result.setMessage("没有可修改状态的支出");
			return result;
		}
		result.setIsSuccess(true);
		return result;
	}

}
