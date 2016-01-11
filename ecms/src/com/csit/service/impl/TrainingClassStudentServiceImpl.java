package com.csit.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.csit.dao.TrainingClassDAO;
import com.csit.dao.TrainingClassStudentDAO;
import com.csit.model.Student;
import com.csit.model.TrainingClass;
import com.csit.model.TrainingClassStudent;
import com.csit.service.TrainingClassStudentService;
import com.csit.util.JSONUtil;
import com.csit.util.StringUtil;
import com.csit.vo.ServiceResult;
/**
 * @Description:报名培训班级Service实现类
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-21
 * @author jcf
 * @vesion 1.0
 */
@Service
public class TrainingClassStudentServiceImpl extends BaseServiceImpl<TrainingClassStudent, Integer> implements
        TrainingClassStudentService {
	@Resource
	private TrainingClassStudentDAO trainingClassStudentDAO;
	@Resource
	private TrainingClassDAO trainingClassDAO;

	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.TrainingClassStudentServiceImpl#save(com.csit.model.TrainingClassStudent)
	 */
	@Override
	public ServiceResult save(TrainingClassStudent model) {
		ServiceResult result = new ServiceResult(false);
		if (model == null) {
			result.setMessage("请选择培训班级信息");
			return result;
		}else if (model.getStudent()==null) {
			result.setMessage("请先登录");
			return result;
		}else if (model.getTrainingClass()==null) {
			result.setMessage("请选择培训班级");
			return result;
		}
		if (model.getTrainingClassStudentId() == null) {// 新增
			String[] propertyNames={"student","trainingClass"};
			Object[] values={model.getStudent(),model.getTrainingClass()};
			TrainingClassStudent trainingClassStudent=trainingClassStudentDAO.load(propertyNames, values);
			if(trainingClassStudent!=null){
				result.setMessage("你已选过该培训班级");
				return result;
			}
			TrainingClass trainingClass=trainingClassDAO.load(model.getTrainingClass().getTrainingClassId());
			if(trainingClass.getStuCount()+1>trainingClass.getLimit()){
				result.setMessage("该培训班级人数已达到上限");
				return result;
			}
			model.setStatus(0);
			trainingClassStudentDAO.save(model);
			trainingClass.setStuCount(trainingClass.getStuCount()+1);
		} else {
			TrainingClassStudent oldModel = trainingClassStudentDAO.load(model.getTrainingClassStudentId());
			if (oldModel == null) {
				result.setMessage("该培训班级学生已不存在");
				return result;
			}
			oldModel.setStudent(model.getStudent());
			oldModel.setTrainingClass(model.getTrainingClass());
		}
		result.setIsSuccess(true);
		result.addData("trainingClassStudentId", model.getTrainingClassStudentId());
		return result;
		
	}

	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.TrainingClassStudentServiceImpl#mulDelete(java.lang.String)
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
				TrainingClassStudent olderModel=trainingClassStudentDAO.load(Integer.parseInt(id));
				TrainingClass trainingClass=trainingClassDAO.load(olderModel.getTrainingClass().getTrainingClassId());
				trainingClass.setStuCount(trainingClass.getStuCount()-1);
				trainingClassStudentDAO.delete(olderModel);
				haveDelete = true;
			}
		}
		if(!haveDelete){
			result.setMessage("没有可删除的报名培训班级");
			return result;
		}
		result.setIsSuccess(true);
		return result;
	}
	
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.TrainingClassStudentServiceImpl#query(com.csit.model.TrainingClassStudent, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public String query(TrainingClassStudent model,Integer competitionId, Integer page, Integer rows) {
		List<TrainingClassStudent> list = trainingClassStudentDAO.query(model,competitionId, page, rows);
		Long total = trainingClassStudentDAO.getTotalCount(model,competitionId);

		String[] properties = { "trainingClass.trainingClassId","trainingClass.trainingClassName","trainingClassStudentId","student.studentId",
								"student.studentName", "status","trainingClass.competitionGroup.competition.competitionName:competitionName"
								,"trainingClass.competitionGroup.group.groupName:groupName"};
		return JSONUtil.toJson(list, properties, total);
	}

	@Override
	public Map<String, Object> init(Integer studentId) {
		Map<String, Object> map = new HashMap<String, Object>();
		Student student = new Student();
		student.setStudentId(studentId);
		List<TrainingClassStudent> trainingClassStudentList = trainingClassStudentDAO.query("student",student);
		map.put("trainingClassStudentList", trainingClassStudentList);
		return map;
	}
	
}
