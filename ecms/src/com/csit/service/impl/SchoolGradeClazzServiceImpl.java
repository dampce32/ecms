package com.csit.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.csit.dao.SchoolGradeClazzDAO;
import com.csit.model.Clazz;
import com.csit.model.SchoolGradeClazz;
import com.csit.service.SchoolGradeClazzService;
import com.csit.util.JSONUtil;
import com.csit.util.StringUtil;
import com.csit.vo.ServiceResult;
/**
 * @Description:学校年级班级Service实现类
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-7-23
 * @author jcf
 * @vesion 1.0
 */
@Service
public class SchoolGradeClazzServiceImpl extends BaseServiceImpl<SchoolGradeClazz, Integer> implements SchoolGradeClazzService {

	@Resource
	private SchoolGradeClazzDAO schoolGradeClazzDAO;
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.CompetitionService#delete(com.csit.model.Competition)
	 */
	public ServiceResult delete(SchoolGradeClazz model) {
		ServiceResult result = new ServiceResult(false);
		if (model == null || model.getSchoolGradeClazzId()==null) {
			result.setMessage("请选择要删除的学校年级班级");
			return result;
		}
		SchoolGradeClazz oldModel = schoolGradeClazzDAO.load(model.getSchoolGradeClazzId());
		if (oldModel == null) {
			result.setMessage("该学校年级班级已不存在");
			return result;
		} else {
			schoolGradeClazzDAO.delete(oldModel);
		}
		result.setIsSuccess(true);
		return result;
	}

	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.CompetitionService#query(com.csit.model.Competition, java.lang.Integer, java.lang.Integer)
	 */
	public String query(SchoolGradeClazz model) {

		List<SchoolGradeClazz> list = schoolGradeClazzDAO.query(model);

		String[] properties = { "schoolGradeClazzId", "clazz.clazzName","array","schoolGrade.schoolGradeId"};
		return JSONUtil.toJson(list, properties);
	}

	public ServiceResult save(SchoolGradeClazz model,String clazzIds) {
		ServiceResult result = new ServiceResult(false);
		if (model == null) {
			result.setMessage("请选择年级");
			return result;
		}
		if (model.getSchoolGradeClazzId()==null) {// 新增
			String[] ids = StringUtil.split(clazzIds);
			if(ids.length==0){
				result.setMessage("请选择班级");
				return result;
			}else {
				Integer arrayInteger=schoolGradeClazzDAO.getMaxArray();
				for(int i=0;i<ids.length;i++){
					SchoolGradeClazz schoolGradeClazz = new SchoolGradeClazz();
					schoolGradeClazz.setArray(arrayInteger+i+1);
					Clazz clazz=new Clazz();
					clazz.setClazzId(Integer.parseInt(ids[i]));
					schoolGradeClazz.setClazz(clazz);
					schoolGradeClazz.setSchoolGrade(model.getSchoolGrade());
					schoolGradeClazzDAO.save(schoolGradeClazz);
				}
			}
		}
		result.setIsSuccess(true);
		return result;
	}

	@Override
	public ServiceResult updateArray(Integer schoolGradeClazzId, Integer updateSchoolGradeClazzId) {
		ServiceResult result = new ServiceResult(false);
		if(schoolGradeClazzId==null||updateSchoolGradeClazzId==null){
			result.setMessage("请选择要改变权限排序的权限");
			return result;
		}
		schoolGradeClazzDAO.updateArray(schoolGradeClazzId, updateSchoolGradeClazzId);
		
		result.setIsSuccess(true);
		return result;
	}

	@Override
	public String queryCombobox(Integer schoolGradeId) {
		List<SchoolGradeClazz> list = schoolGradeClazzDAO.queryCombobox(schoolGradeId);
		String[] properties = {"schoolGradeClazzId","clazz.clazzName"};
		String jsonString = JSONUtil.toJsonWithoutRows(list,properties);
		return jsonString;
	}
}
