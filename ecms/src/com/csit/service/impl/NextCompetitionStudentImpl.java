package com.csit.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.csit.dao.CompetitionGroupDAO;
import com.csit.dao.NextCompetitionStudentDAO;
import com.csit.model.Competition;
import com.csit.model.CompetitionGroup;
import com.csit.model.NextCompetitionStudent;
import com.csit.model.Student;
import com.csit.service.NextCompetitionStudentService;
import com.csit.util.JSONUtil;
import com.csit.util.StringUtil;
import com.csit.vo.ServiceResult;
/**
 * @Description:赛事晋级Service实现类
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-23
 * @Author lys
 */
@Service
public class NextCompetitionStudentImpl extends
		BaseServiceImpl<NextCompetitionStudent, Integer> implements
		NextCompetitionStudentService {
	@Resource
	private NextCompetitionStudentDAO nextCompetitionStudentDAO;
	@Resource
	private CompetitionGroupDAO competitionGroupDAO;
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.NextCompetitionStudentService#query(com.csit.model.NextCompetitionStudent, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public String query(NextCompetitionStudent model, Integer page, Integer rows) {
		List<NextCompetitionStudent> list = nextCompetitionStudentDAO.query(model,page,rows);
		String[] properties = { "nextCompetitionStudentId", "competitionGroup.competition.competitionName","competitionGroup.group.groupName",
					"student.studentName","score"};
		Long total = nextCompetitionStudentDAO.getTotalCount(model);
		return JSONUtil.toJson(list, properties,total);
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.NextCompetitionStudentService#selectQuery(com.csit.model.NextCompetitionStudent, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public String selectQuery(NextCompetitionStudent model, Integer page,
			Integer rows) {
		List<Map<String,Object>> listMap = nextCompetitionStudentDAO.selectQuery(model,page,rows);
		Long total = nextCompetitionStudentDAO.getTotalCountSelectQuery(model);
		return JSONUtil.toJsonFromListMap(listMap,total);
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.NextCompetitionStudentService#mulSave(com.csit.model.NextCompetitionStudent, java.lang.String)
	 */
	@Override
	public ServiceResult mulSave(NextCompetitionStudent model, String ids, String scores) {
		ServiceResult result = new ServiceResult(false);
		if(model==null){
			result.setMessage("请填写要保存的晋级名单");
			return result;
		}
		if(model.getCompetitionGroup()==null||model.getCompetitionGroup().getCompetitionGroupId()==null){
			result.setMessage("请选择赛事奖项");
			return result;
		}
		if(StringUtils.isEmpty(ids)){
			result.setMessage("请选择晋级学生");
			return result;
		}
		
		Integer[] studentIdArray = StringUtil.splitToInteger(ids);
		Double[] scoreArray = StringUtil.splitToDouble(scores);
		if(studentIdArray==null||studentIdArray.length==0){
			result.setMessage("请选择晋级学生");
			return result;
		}
		for (int i = 0; i < studentIdArray.length; i++) {
			Student student = new Student();
			student.setStudentId(studentIdArray[i]);
			
			NextCompetitionStudent nextCompetitionStudent = new NextCompetitionStudent();
			nextCompetitionStudent.setCompetitionGroup(model.getCompetitionGroup());
			nextCompetitionStudent.setStudent(student);
			nextCompetitionStudent.setScore(scoreArray[i]);
			nextCompetitionStudentDAO.save(nextCompetitionStudent);
		}
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.NextCompetitionStudentService#mulDelete(java.lang.String)
	 */
	@Override
	public ServiceResult mulDelete(String ids) {
		ServiceResult result = new ServiceResult(false);
		if(StringUtils.isEmpty(ids)){
			result.setMessage("请选择删除的晋级学生");
			return result;
		}
		
		Integer[] idArray = StringUtil.splitToInteger(ids);
		if(idArray==null||idArray.length==0){
			result.setMessage("请选择删除的晋级学生");
			return result;
		}
		for (int i = 0; i < idArray.length; i++) {
			NextCompetitionStudent oldNextCompetitionStudent = nextCompetitionStudentDAO.load(idArray[i]);
			if(oldNextCompetitionStudent==null){
				continue;
			}else{
				nextCompetitionStudentDAO.delete(oldNextCompetitionStudent);
			}
		}
		result.setIsSuccess(true);
		return result;
	}
	@Override
	public Map<String, Object> init(Integer competitionId,String competitionGroupId, Integer page,
			Integer rows) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<CompetitionGroup> competitionGroupList=competitionGroupDAO.query(competitionId);
		NextCompetitionStudent model=new NextCompetitionStudent();
		CompetitionGroup competitionGroup=new CompetitionGroup();
		Competition competition=new Competition();
		competition.setCompetitionId(competitionId);
		competitionGroup.setCompetition(competition);
		model.setCompetitionGroup(competitionGroup);
		if(competitionGroupId==null){
			if(competitionGroupList.size()!=0){
				model.setCompetitionGroup(competitionGroupList.get(0));
			}
		}else {
			competitionGroup.setCompetitionGroupId(Integer.parseInt(competitionGroupId));
			model.setCompetitionGroup(competitionGroup);
		}
		List<NextCompetitionStudent> nextCompetitionStudentList=nextCompetitionStudentDAO.query(model, page+1, rows);
		Long total = nextCompetitionStudentDAO.getTotalCount(model);
		map.put("nextCompetitionStudentList", nextCompetitionStudentList);
		map.put("total", total);
		map.put("competitionGroupList", competitionGroupList);
		return map;
	}

}
