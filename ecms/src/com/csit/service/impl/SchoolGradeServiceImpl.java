package com.csit.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.csit.dao.SchoolGradeDAO;
import com.csit.model.Grade;
import com.csit.model.SchoolGrade;
import com.csit.service.SchoolGradeService;
import com.csit.util.JSONUtil;
import com.csit.util.StringUtil;
import com.csit.vo.ServiceResult;

/**
 * @Description:学校年级Service实现类
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-5-31
 * @author jcf
 * @vesion 1.0
 */
@Service
public class SchoolGradeServiceImpl extends BaseServiceImpl<SchoolGrade, Integer> implements SchoolGradeService {

	@Resource
	private SchoolGradeDAO schoolGradeDAO;
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.CompetitionService#delete(com.csit.model.Competition)
	 */
	public ServiceResult delete(SchoolGrade model) {
		ServiceResult result = new ServiceResult(false);
		if (model == null || model.getSchoolGradeId() ==null) {
			result.setMessage("请选择要删除的年级");
			return result;
		}
		SchoolGrade oldModel = schoolGradeDAO.load(model.getSchoolGradeId());
		if (oldModel == null) {
			result.setMessage("该学校年级已不存在");
			return result;
		} else {
			schoolGradeDAO.delete(oldModel);
		}
		result.setIsSuccess(true);
		return result;
	}

	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.CompetitionService#query(com.csit.model.Competition, java.lang.Integer, java.lang.Integer)
	 */
	public String query(SchoolGrade model) {

		List<SchoolGrade> list = schoolGradeDAO.query(model);

		String[] properties = { "school.schoolId","grade.gradeId", "grade.gradeName"
				,"array","schoolGradeId"};
		return JSONUtil.toJson(list, properties);
	}

	public ServiceResult save(SchoolGrade model,String gradeIds) {
		ServiceResult result = new ServiceResult(false);
		if (model == null) {
			result.setMessage("请填写赛事信息");
			return result;
		}
		if (model.getSchoolGradeId()==null) {// 新增
			String[] ids = StringUtil.split(gradeIds);
			if(ids.length==0){
				result.setMessage("请选择赛事分组");
				return result;
			}else {
				Integer array=schoolGradeDAO.getMaxArray(model.getSchool().getSchoolId());
				for(int i=0;i<ids.length;i++){
					Grade grade=new Grade();
					grade.setGradeId(Integer.parseInt(ids[i]));
					
					String[] propertyNames={"school","grade"};
					Object[] values={model.getSchool(),grade};
					SchoolGrade SchoolGrade1=schoolGradeDAO.load(propertyNames, values);
					if(SchoolGrade1==null){
						SchoolGrade schoolGrade = new SchoolGrade();
						schoolGrade.setArray(array+i+1);
						schoolGrade.setGrade(grade);
						schoolGrade.setSchool(model.getSchool());
						schoolGradeDAO.save(schoolGrade);
					}
				}
			}
		}
		result.setIsSuccess(true);
		return result;
	}

	@Override
	public ServiceResult updateArray(Integer schoolGradeId, Integer updateSchoolGradeId) {
		ServiceResult result = new ServiceResult(false);
		if(schoolGradeId==null||updateSchoolGradeId==null){
			result.setMessage("请选择要改变权限排序的权限");
			return result;
		}
		schoolGradeDAO.updateArray(schoolGradeId, updateSchoolGradeId);
		
		result.setIsSuccess(true);
		return result;
	}

	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.SchoolGradeService#queryCombobox(java.lang.Integer)
	 */
	@Override
	public String queryCombobox(Integer schoolId) {
		List<SchoolGrade> list = schoolGradeDAO.queryCombobox(schoolId);
		String[] properties = {"schoolGradeId","grade.gradeName"};
		String jsonString = JSONUtil.toJsonWithoutRows(list,properties);
		return jsonString;
	}

}
