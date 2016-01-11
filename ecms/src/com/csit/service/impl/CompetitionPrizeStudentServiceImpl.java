package com.csit.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.csit.dao.CompetitionGroupDAO;
import com.csit.dao.CompetitionPrizeStudentDAO;
import com.csit.model.Competition;
import com.csit.model.CompetitionGroup;
import com.csit.model.CompetitionPrize;
import com.csit.model.CompetitionPrizeStudent;
import com.csit.model.Student;
import com.csit.service.CompetitionPrizeStudentService;
import com.csit.util.JSONUtil;
import com.csit.util.StringUtil;
import com.csit.vo.ServiceResult;
/**
 * @Description:赛事获奖学生Service实现类
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-22
 * @Author lys
 */
@Service
public class CompetitionPrizeStudentServiceImpl extends
		BaseServiceImpl<CompetitionPrizeStudent, Integer> implements
		CompetitionPrizeStudentService {
	@Resource
	private CompetitionPrizeStudentDAO competitionPrizeStudentDAO;
	@Resource
	private CompetitionGroupDAO competitionGroupDAO;
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.CompetitionPrizeStudentService#query(com.csit.model.CompetitionPrizeStudent, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public String query(CompetitionPrizeStudent model, Integer page, Integer rows) {
		List<CompetitionPrizeStudent> list = competitionPrizeStudentDAO.query(model,page,rows);

		String[] properties = { "competitionPrizeStudentId", "competitionPrize.competitionGroup.competition.competitionName","competitionPrize.competitionGroup.group.groupName",
					"competitionPrize.prize.prizeName","student.studentName"};
		
		Long total = competitionPrizeStudentDAO.getTotalCount(model);
		return JSONUtil.toJson(list, properties,total);
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.CompetitionPrizeStudentService#selectQuery(com.csit.model.CompetitionPrizeStudent, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public String selectQuery(CompetitionPrizeStudent model, Integer page,
			Integer rows) {
		List<Map<String,Object>> listMap = competitionPrizeStudentDAO.selectQuery(model,page,rows);
		Long total = competitionPrizeStudentDAO.getTotalCountSelectQuery(model);
		return JSONUtil.toJsonFromListMap(listMap,total);
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.CompetitionPrizeStudentService#mulSave(com.csit.model.CompetitionPrizeStudent, java.lang.String)
	 */
	@Override
	public ServiceResult mulSave(CompetitionPrizeStudent model, String ids) {
		ServiceResult result = new ServiceResult(false);
		if(model==null){
			result.setMessage("请填写要保存的获奖名单");
			return result;
		}
		if(model.getCompetitionPrize()==null||model.getCompetitionPrize().getCompetitionPrizeId()==null){
			result.setMessage("请选择赛事奖项");
			return result;
		}
		if(StringUtils.isEmpty(ids)){
			result.setMessage("请选择获奖学生");
			return result;
		}
		
		Integer[] studentIdArray = StringUtil.splitToInteger(ids);
		if(studentIdArray==null||studentIdArray.length==0){
			result.setMessage("请选择获奖学生");
			return result;
		}
		for (int i = 0; i < studentIdArray.length; i++) {
			Student student = new Student();
			student.setStudentId(studentIdArray[i]);
			
			CompetitionPrizeStudent competitionPrizeStudent = new CompetitionPrizeStudent();
			competitionPrizeStudent.setCompetitionPrize(model.getCompetitionPrize());
			competitionPrizeStudent.setStudent(student);
			
			competitionPrizeStudentDAO.save(competitionPrizeStudent);
		}
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.CompetitionPrizeStudentService#mulDelete(java.lang.String)
	 */
	@Override
	public ServiceResult mulDelete(String ids) {
		ServiceResult result = new ServiceResult(false);
		if(StringUtils.isEmpty(ids)){
			result.setMessage("请选择删除的获奖学生");
			return result;
		}
		
		Integer[] idArray = StringUtil.splitToInteger(ids);
		if(idArray==null||idArray.length==0){
			result.setMessage("请选择删除的获奖学生");
			return result;
		}
		for (int i = 0; i < idArray.length; i++) {
			CompetitionPrizeStudent oldCompetitionPrizeStudent = competitionPrizeStudentDAO.load(idArray[i]);
			if(oldCompetitionPrizeStudent==null){
				continue;
			}else{
				competitionPrizeStudentDAO.delete(oldCompetitionPrizeStudent);
			}
		}
		result.setIsSuccess(true);
		return result;
	}
	@Override
	public Map<String, Object> init(Integer competitionId,
			String competitionGroupId, Integer page, Integer rows) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		List<CompetitionGroup> competitionGroupList=competitionGroupDAO.query(competitionId);
		CompetitionPrizeStudent model=new CompetitionPrizeStudent();
		CompetitionPrize competitionPrize=new CompetitionPrize();
		CompetitionGroup competitionGroup=new CompetitionGroup();
		Competition competition=new Competition();
		competition.setCompetitionId(competitionId);
		competitionGroup.setCompetition(competition);
		competitionPrize.setCompetitionGroup(competitionGroup);
		model.setCompetitionPrize(competitionPrize);
		if(competitionGroupId==null){
			if(competitionGroupList.size()!=0){
				competitionGroup.setCompetitionGroupId(competitionGroupList.get(0).getCompetitionGroupId());
			}
		}else {
			competitionGroup.setCompetitionGroupId(Integer.parseInt(competitionGroupId));
		}
		List<CompetitionPrizeStudent> list=competitionPrizeStudentDAO.query(model, page+1, rows);
		Long total = competitionPrizeStudentDAO.getTotalCount(model);
		map.put("competitionPrizeStudentList", list);
		map.put("total", total);
		map.put("competitionGroupList", competitionGroupList);
		return map;
	}

}
