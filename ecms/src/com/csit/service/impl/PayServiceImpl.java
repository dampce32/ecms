package com.csit.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.csit.dao.PayDAO;
import com.csit.model.Pay;
import com.csit.model.Student;
import com.csit.service.PayService;
import com.csit.util.JSONUtil;
import com.csit.util.StringUtil;
import com.csit.vo.GobelConstants;
import com.csit.vo.ServiceResult;

/**
 * @Description:缴费Service实现类
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-9
 * @author jcf
 * @vesion 1.0
 */
@Service
public class PayServiceImpl extends BaseServiceImpl<Pay, Integer> implements PayService {

	@Resource
	private PayDAO payDAO;
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.PayService#delete(com.csit.model.Pay)
	 */
	public ServiceResult delete(Pay model) {
		ServiceResult result = new ServiceResult(false);
		if (model == null || model.getPayId() == null) {
			result.setMessage("请选择要删除的缴费");
			return result;
		}
		Pay oldModel = payDAO.load(model.getPayId());
		if (oldModel == null) {
			result.setMessage("该缴费已不存在");
			return result;
		} else {
			payDAO.delete(oldModel);
		}
		result.setIsSuccess(true);
		return result;
	}

	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.PayService#query(com.csit.model.Pay, java.lang.Integer, java.lang.Integer)
	 */
	public String query(Pay model, Integer page, Integer rows) {

		List<Pay> list = payDAO.query(model, page, rows);
		Long total = payDAO.getTotalCount(model);

		String[] properties = { "payId", "payType.payTypeName","status", "competition.competitionName"
								,"competition.competitionId","fee", "payType.payTypeId", "teacher.teacherId","teacher.teacherName"
								,"payDate", "student.studentId","student.studentName","note"};

		return JSONUtil.toJson(list, properties, total);
	}

	public ServiceResult save(Pay model,String stuIds) {
		ServiceResult result = new ServiceResult(false);
		if (model == null) {
			result.setMessage("请填写缴费信息");
			return result;
		}else if (model.getPayDate()==null) {
			result.setMessage("请填写缴费日期");
			return result;
			
		}else if(model.getCompetition()==null){
			result.setMessage("请选择赛事");
			return result;
		}else if (model.getPayType()==null) {
			result.setMessage("请选择缴费类型");
			return result;
			
		}else if (model.getFee()==null) {
			result.setMessage("请填写缴费金额");
			return result;
		}
		if (model.getPayId() == null) {// 新增
			String[] s = StringUtil.split(stuIds);
			for(int i=0;i<s.length;i++){
				Pay pay=new Pay();
				pay.setCompetition(model.getCompetition());
				pay.setFee(model.getFee());
				pay.setNote(model.getNote());
				pay.setPayDate(model.getPayDate());
				pay.setPayType(model.getPayType());
				Student student=new Student();
				student.setStudentId(Integer.parseInt(s[i]));
				pay.setStudent(student);
				pay.setStatus(0);
				pay.setTeacher(model.getTeacher());
				payDAO.save(pay);
			}
		} else {
			Pay oldModel = payDAO.load(model.getPayId());
			if (oldModel == null) {
				result.setMessage("该缴费已不存在");
				return result;
			}
			oldModel.setPayDate(model.getPayDate());
			oldModel.setPayType(model.getPayType());
			oldModel.setFee(model.getFee());
			oldModel.setNote(model.getNote());
			oldModel.setTeacher(model.getTeacher());
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
			Pay oldModel = payDAO.load(Integer.parseInt(id));
			if (oldModel == null) {
				continue;
			}else{
				payDAO.delete(Integer.parseInt(id));
				haveDelete = true;
			}
		}
		if(!haveDelete){
			result.setMessage("没有可删除的缴费");
			return result;
		}
		result.setIsSuccess(true);
		return result;
	}

	@Override
	public ServiceResult mulUpdateStatus(String ids, Pay model) {
		ServiceResult result = new ServiceResult(false);
		if(StringUtils.isEmpty(ids)){
			result.setMessage("请选择要修改状态的缴费");
			return result;
		}
		String[] idArray =StringUtil.split(ids);
		if(idArray.length==0){
			result.setMessage("请选择要修改状态的缴费");
			return result;
		}
		if(model==null||model.getStatus()==null){
			result.setMessage("请选择要修改成的状态");
			return result;
		}
		boolean haveUpdateShzt = false;
		for (String id : idArray) {
			Pay oldModel = payDAO.load(Integer.parseInt(id));
			if(oldModel!=null&&oldModel.getStatus().intValue()!=model.getStatus().intValue()){
				oldModel.setStatus(model.getStatus());
				haveUpdateShzt = true;
			}
		}
		if(!haveUpdateShzt){
			result.setMessage("没有可修改状态的缴费");
			return result;
		}
		result.setIsSuccess(true);
		return result;
	}

}
