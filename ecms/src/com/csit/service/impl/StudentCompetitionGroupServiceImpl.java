package com.csit.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.csit.dao.CompetitionGroupDAO;
import com.csit.dao.HistoryMessageDAO;
import com.csit.dao.MessageConfigDAO;
import com.csit.dao.MessageSendDAO;
import com.csit.dao.NextCompetitionStudentDAO;
import com.csit.dao.StudentCompetitionGroupDAO;
import com.csit.dao.StudentDAO;
import com.csit.model.CompetitionGroup;
import com.csit.model.HistoryMessage;
import com.csit.model.MessageConfig;
import com.csit.model.MessageSend;
import com.csit.model.NextCompetitionStudent;
import com.csit.model.Student;
import com.csit.model.StudentCompetitionGroup;
import com.csit.model.Teacher;
import com.csit.service.StudentCompetitionGroupService;
import com.csit.thread.MessageClient;
import com.csit.util.JSONUtil;
import com.csit.util.StringUtil;
import com.csit.vo.ServiceResult;
/**
 * 
 * @Description: 学生参赛组别Service实现类
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-4
 * @author yk
 * @vesion 1.0
 */
@Service
public class StudentCompetitionGroupServiceImpl extends
		BaseServiceImpl<StudentCompetitionGroup, Integer> implements
		StudentCompetitionGroupService {

	@Resource
	private StudentCompetitionGroupDAO studentCompetitionGroupDAO;
	@Resource
	private CompetitionGroupDAO competitionGroupDAO;
	@Resource
	private NextCompetitionStudentDAO nextCompetitionStudentDAO;
	@Resource
	private MessageConfigDAO messageConfigDAO;
	@Resource
	private StudentDAO studentDAO;
	@Resource
	private HistoryMessageDAO historyMessageDAO;
	@Resource
	private MessageSendDAO messageSendDAO;
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.StudentCompetitionGroupService#save(com.csit.model.StudentCompetitionGroup)
	 */
	@Override
	public ServiceResult save(StudentCompetitionGroup model,Integer teacherId) {
		ServiceResult result = new ServiceResult(false);
		result.setMessage("报名成功，等待审核");
		if(model==null){
			result.setMessage("请填写报名信息");
			return result;
		}
		if(model.getStudentCompetitionGroupId()==null){//新增
			String[] str = {"student","competitionGroup"};
			Object[] obj = {model.getStudent(),model.getCompetitionGroup()};
			StudentCompetitionGroup oldModel = studentCompetitionGroupDAO.load(str,obj);
			if(oldModel!=null){
				result.setMessage("已报名此赛事，不可重复报名");
				return result;
			}
			//查询赛事组别
			CompetitionGroup competitionGroup = competitionGroupDAO.load(model.getCompetitionGroup().getCompetitionGroupId());
			//上级赛事组别
			String[] str2 = {"competition","group"};
			Object[] obj2 = {competitionGroup.getCompetition().getParentCompetition(),competitionGroup.getGroup()};
			CompetitionGroup parentCompetitionGroup = competitionGroupDAO.load(str2, obj2);
			//上级赛事晋级名单
			obj[1] = parentCompetitionGroup;
			NextCompetitionStudent nextCompetitionStudent = nextCompetitionStudentDAO.load(str, obj);
			if(parentCompetitionGroup!=null){
				if(nextCompetitionStudent==null){
					result.setMessage(parentCompetitionGroup.getCompetition().getCompetitionName()+"未晋级，不可报名");
					return result;
				}else{
					model.setStatus(1);
					result.setMessage("报名成功");
				}
			}
			if(model.getStatus()==1){
				//准考证号
				String maxExamCode = studentCompetitionGroupDAO.getMaxExamCode(competitionGroup.getCompetitionGroupId());
				String headExamCode = competitionGroup.getCompetition().getCompetitionCode()+competitionGroup.getGroup().getGroupCode();
				if(maxExamCode==null){
					model.setExamCode(headExamCode+"0001");
				}else{
					String subCode = maxExamCode.substring(maxExamCode.length()-4);
					String newSubCode = String.format("%04d", (Integer.parseInt(subCode)+1));
					model.setExamCode(headExamCode+newSubCode);
				}
			}
			studentCompetitionGroupDAO.save(model);
		}else{
			StudentCompetitionGroup oldModel = studentCompetitionGroupDAO.load(model.getStudentCompetitionGroupId());
			if(oldModel == null) {
				result.setMessage("该报名信息已不存在");
				return result;
			}
			oldModel.setStudent(model.getStudent());
			oldModel.setArea(model.getArea());
			oldModel.setCompetitionGroup(model.getCompetitionGroup());
		}
		result.setIsSuccess(true);
		return result;
	}

	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.StudentCompetitionGroupService#mulUpdateStatus(java.lang.String, com.csit.model.StudentCompetitionGroup)
	 */
	@Override
	public ServiceResult mulUpdateStatus(String ids, StudentCompetitionGroup model,Integer teacherId) {
		ServiceResult result = new ServiceResult(false);
		if(StringUtils.isEmpty(ids)){
			result.setMessage("请选择要修改审核状态的报名信息");
			return result;
		}
		String[] idArray =StringUtil.split(ids);
		if(idArray.length==0){
			result.setMessage("请选择要修改审核状态的报名信息");
			return result;
		}
		if(model==null||model.getStatus()==null){
			result.setMessage("请选择要修改成的审核状态");
			return result;
		}
		boolean haveUpdateShzt = false;
		
		if(model.getStatus()==0||model.getStatus()==2){
			for (String id : idArray) {
				StudentCompetitionGroup oldModel = studentCompetitionGroupDAO.load(Integer.parseInt(id));
				if(oldModel!=null&&oldModel.getStatus()!=model.getStatus()){
					oldModel.setStatus(model.getStatus());
					oldModel.setExamCode(null);
					haveUpdateShzt = true;
				}
			}
		}else if(model.getStatus()==1){
			for (String id : idArray) {
				StudentCompetitionGroup oldModel = studentCompetitionGroupDAO.load(Integer.parseInt(id));
				if(oldModel!=null&&!oldModel.getStatus().equals(model.getStatus())){
					
					oldModel.setStatus(model.getStatus());
					//准考证号
					CompetitionGroup competitionGroup = oldModel.getCompetitionGroup();
					String maxExamCode = studentCompetitionGroupDAO.getMaxExamCode(competitionGroup.getCompetitionGroupId());
					String headExamCode = competitionGroup.getCompetition().getCompetitionCode()+competitionGroup.getGroup().getGroupCode();
					if(maxExamCode==null){
						oldModel.setExamCode(headExamCode+"0001");
					}else{
						String subCode = maxExamCode.substring(maxExamCode.length()-4);
						String newSubCode = String.format("%04d", (Integer.parseInt(subCode)+1));
						oldModel.setExamCode(headExamCode+newSubCode);
					}
					MessageConfig mainSwitch=messageConfigDAO.load(1);
					/**
					 * 判断总开关是否开启
					 */
					if(mainSwitch.getStatus()==1){
						MessageConfig enrollSwitch=messageConfigDAO.load(31);
						/**
						 * 判断报名审核开关是否开启
						 */
						if(enrollSwitch.getStatus()==1){
							Student student=studentDAO.load(oldModel.getStudent().getStudentId());
							String[] mobiles={student.getMobilePhone()};
							StringBuilder sb=new StringBuilder();
							if(StringUtils.isNotEmpty(enrollSwitch.getHead())){
								sb.append(enrollSwitch.getHead());
							}
							sb.append(student.getStudentName()+"同学，你报名参加的"+competitionGroup.getCompetition().getCompetitionName());
							sb.append(competitionGroup.getGroup().getGroupName()+"的申请已审核通过，准考证号是"+oldModel.getExamCode());
							if(competitionGroup.getPaper()!=null){
								sb.append("，请在"+competitionGroup.getCompetition().getBeginDate()+"~"+competitionGroup.getCompetition().getEndDate()+"期间登录系统在线考试");
							}
							if(StringUtils.isNotEmpty(enrollSwitch.getTail())){
								sb.append(enrollSwitch.getTail());
							}
							/**
							 * 发送短信
							 */
							int messageResult=MessageClient.sendSMS(mobiles,sb.toString());
							if(messageResult==0){
								HistoryMessage historyMessage=new HistoryMessage();
								historyMessage.setMessageContent(sb.toString());
								historyMessage.setMessageType(3);
								historyMessage.setReceiveIDs(student.getMobilePhone());
								historyMessage.setSendTime(new Date());
								Teacher teacher=new Teacher();
								teacher.setTeacherId(teacherId);
								historyMessage.setTeacher(teacher);
								historyMessageDAO.save(historyMessage);
							}else{
								MessageSend messageSend = new MessageSend();
								messageSend.setMessageContent(sb.toString());
								messageSend.setMessageType(2);
								messageSend.setReceiveIDs(student.getMobilePhone());
								messageSend.setSendTime(new Date());
								messageSend.setErrorCount(10);
								
								messageSendDAO.save(messageSend);
							}
						}
					}
					haveUpdateShzt = true;
				}
			}
		}
		if(!haveUpdateShzt){
			result.setMessage("没有可修改审核状态的报名信息");
			return result;
		}
		result.setIsSuccess(true);
		return result;
	}

	@Override
	public String query(StudentCompetitionGroup model, Integer page,
			Integer rows) {
		List<StudentCompetitionGroup> list = studentCompetitionGroupDAO.query(model,page,rows);
		Long total = studentCompetitionGroupDAO.getTotalCount(model);
		String[] properties = {"studentCompetitionGroupId","area.areaName","area.city.cityName",
				"area.province.provinceName","competitionGroup.competition.competitionName",
				"competitionGroup.group.groupName","student.studentId","student.studentName",
				"competitionGroup.group.groupId","competitionGroup.competition.competitionId",
				"competitionGroup.competitionGroupId","status","examCode"};
		return JSONUtil.toJson(list, properties, total);
	}

	@Override
	public ServiceResult getTotalCount(StudentCompetitionGroup model) {
		ServiceResult result = new ServiceResult(false);
		Long data = studentCompetitionGroupDAO.getTotalCount(model);
		result.addData("total", data);
		result.setIsSuccess(true);
		return result;
	}

	@Override
	public ServiceResult queryByStu(Integer studentId) {
		ServiceResult result = new ServiceResult(false);
		
		List<StudentCompetitionGroup> list = studentCompetitionGroupDAO.query("student.studentId",studentId);
		
		String[] properties = {"studentCompetitionGroupId","competitionGroup.competition.competitionName",
				"competitionGroup.group.groupName","status"};
		
		String data = JSONUtil.toJsonWithoutRows(list,properties);
		result.addData("datagridData", data);
		
		result.setIsSuccess(true);
		return result;
	}

	@Override
	public String query(Integer competitionId, Integer page, Integer rows,
			String stuIds) {

		Integer[] stuIdArr;
		if(StringUtils.isNotEmpty(stuIds)){
			String[] s = StringUtil.split(stuIds);
			stuIdArr=new Integer[s.length];
			for(int i=0;i<s.length;i++){
				stuIdArr[i]=Integer.parseInt(s[i]);
			}
		}else{
			stuIdArr=null;
		}
		List<StudentCompetitionGroup> list = studentCompetitionGroupDAO.query(competitionId, page, rows, stuIdArr);
		Long total = studentCompetitionGroupDAO.getTotalCount(competitionId, stuIdArr);
		String[] properties = { "studentCompetitionGroupId","student.studentName","student.studentId",
				"competitionGroup.group.groupName:groupName" };

		return JSONUtil.toJson(list, properties, total);
	}

	@Override
	public Map<String, Object> init(Integer studentId) {
		Map<String, Object> map = new HashMap<String, Object>();
		Student student = new Student();
		student.setStudentId(studentId);
		List<StudentCompetitionGroup> studentCompetitionGroupList = studentCompetitionGroupDAO.query("student", student);
		map.put("studentCompetitionGroupList", studentCompetitionGroupList);
		return map;
	}

}
