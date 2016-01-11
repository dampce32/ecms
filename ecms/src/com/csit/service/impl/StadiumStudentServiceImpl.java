package com.csit.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.csit.dao.StadiumDAO;
import com.csit.dao.StadiumStudentDAO;
import com.csit.dao.StudentCompetitionGroupDAO;
import com.csit.model.Stadium;
import com.csit.model.StadiumStudent;
import com.csit.model.Student;
import com.csit.model.StudentCompetitionGroup;
import com.csit.service.StadiumStudentService;
import com.csit.util.JSONUtil;
import com.csit.util.StringUtil;
import com.csit.vo.ServiceResult;
/**
 * @Description:选择赛场Service实现类
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-21
 * @author jcf
 * @vesion 1.0
 */
@Service
public class StadiumStudentServiceImpl extends BaseServiceImpl<StadiumStudent, Integer> implements
        StadiumStudentService {
	@Resource
	private StadiumStudentDAO stadiumStudentDAO;
	@Resource
	private StadiumDAO stadiumDAO;
	@Resource
	private StudentCompetitionGroupDAO studentCompetitionGroupDAO;
	

	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.StadiumStudentServiceImpl#mulDelete(java.lang.String)
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
				StadiumStudent stadiumStudent = stadiumStudentDAO.load(Integer.parseInt(id));
				Stadium stadium=stadiumDAO.load(stadiumStudent.getStadium().getStadiumId());
				stadium.setArrangeNo(stadium.getArrangeNo()-1);
				stadiumStudentDAO.delete(stadiumStudent);
				haveDelete = true;
			}
		}
		if(!haveDelete){
			result.setMessage("没有可删除的选择赛场");
			return result;
		}
		result.setIsSuccess(true);
		return result;
	}
	
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.StadiumStudentServiceImpl#query(com.csit.model.StadiumStudent, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public String query(StadiumStudent model,Integer competitionId, Integer page, Integer rows) {
		List<StadiumStudent> list = stadiumStudentDAO.query(model,competitionId, page, rows);
		Long total = stadiumStudentDAO.getTotalCount(model,competitionId);

		String[] properties = { "stadium.stadiumId","stadium.stadiumName","stadiumStudentId","student.studentId",
								"student.studentName","stadium.competitionGroup.competition.competitionName:competitionName"
								,"stadium.competitionGroup.group.groupName:groupName"};
		return JSONUtil.toJson(list, properties, total);
	}

	@Override
	public ServiceResult save(StadiumStudent model) {
		ServiceResult result = new ServiceResult(false);
		if (model == null) {
			result.setMessage("请填写赛场考生信息");
			return result;
		}else if (model.getStadium()==null) {
			result.setMessage("请选择考场");
			return result;
		}else if (model.getStudent()==null) {
			result.setMessage("请选择考生");
			return result;
		}
		if (model.getStadiumStudentId() == null) {// 新增
			Stadium stadium=stadiumDAO.load(model.getStadium().getStadiumId());
			String[] propertys={"student","competitionGroup"};
			Object[] objs={model.getStudent(),stadium.getCompetitionGroup()};
			StudentCompetitionGroup studentCompetitionGroup=studentCompetitionGroupDAO.load(propertys, objs);
			if(studentCompetitionGroup==null){
				result.setMessage("对不起，你未报名该赛场");
				return result;
			}else if(studentCompetitionGroup.getStatus()==0){
				result.setMessage("对不起，你报名的该赛场还未审核");
				return result;
			}else if (studentCompetitionGroup.getStatus()==2) {
				result.setMessage("对不起，你报名的该赛场审核不通过");
				return result;
			}
			String[] propertyNames={"student","stadium"};
			Object[] values={model.getStudent(),model.getStadium()};
			StadiumStudent stadiumStudent=stadiumStudentDAO.load(propertyNames, values);
			
			if(stadiumStudent!=null){
				result.setMessage("已经选择过了");
				return result;
			}else if((stadium.getArrangeNo()+1)>stadium.getLimit()){
				result.setMessage("对不起，该赛场人数达到上限");
				return result;
			}else {
				stadiumStudentDAO.save(model);
				stadium.setArrangeNo(stadium.getArrangeNo()+1);
			}
		} else {
			String[] propertyNames={"student","stadium"};
			Object[] values={model.getStudent(),model.getStadium()};
			StadiumStudent stadiumStudent=stadiumStudentDAO.load(propertyNames, values);
			if(stadiumStudent!=null&&stadiumStudent.getStadiumStudentId().intValue()!=model.getStadiumStudentId().intValue()){
				result.setMessage("已经选择过了");
				return result;
			}
			StadiumStudent oldModel=stadiumStudentDAO.load(model.getStadiumStudentId());
			oldModel.setStadium(model.getStadium());
			oldModel.setStudent(model.getStudent());
		}
		result.setIsSuccess(true);
		result.addData("stadiumStudentId", model.getStadiumStudentId());
		return result;
	}

	@Override
	public Map<String, Object> init(Integer studentId) {
		Map<String, Object> map = new HashMap<String, Object>();
		Student student = new Student();
		student.setStudentId(studentId);
		StadiumStudent model=new StadiumStudent();
		model.setStudent(student);
		List<StadiumStudent> stadiumStudentList = stadiumStudentDAO.query(model);
		map.put("stadiumStudentList", stadiumStudentList);
		return map;
	}
	
}
