package com.csit.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.csit.dao.SubjectDAO;
import com.csit.dao.SubjectSubDAO;
import com.csit.model.Subject;
import com.csit.model.SubjectSub;
import com.csit.model.SubjectSubId;
import com.csit.service.SerialNumberService;
import com.csit.service.SubjectService;
import com.csit.util.JSONUtil;
import com.csit.util.StringUtil;
import com.csit.vo.GobelConstants;
import com.csit.vo.SerialNumberTabelName;
import com.csit.vo.ServiceResult;

/**
 * @Description:试题Service实现类
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-4-26
 * @author jcf
 * @vesion 1.0
 */
@Service
public class SubjectServiceImpl extends BaseServiceImpl<Subject, Integer> implements SubjectService {

	@Resource
	private SubjectDAO subjectDAO;
	@Resource
	private SubjectSubDAO subjectSubDAO;
	@Resource
	private SerialNumberService serialNumberService;
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.SubjectService#mulDelete(java.lang.String)
	 */
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
			Subject oldModel = subjectDAO.load(Integer.parseInt(id));
			if (oldModel == null) {
				continue;
			}else{
				subjectDAO.delete(Integer.parseInt(id));
				haveDelete = true;
			}
		}
		if(!haveDelete){
			result.setMessage("没有可删除的试题");
			return result;
		}
		result.setIsSuccess(true);
		return result;
	}

	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.SubjectService#query(com.csit.model.Subject, java.lang.Integer, java.lang.Integer)
	 */
	public String query(Subject model, Integer page, Integer rows) {

		List<Subject> list = subjectDAO.query(model, page, rows);

		Long total = subjectDAO.getTotalCount(model);
		String[] properties = { "subjectId","group.groupId","group.groupName"
								,"publishTeacher.teacherId","publishTeacher.teacherName"
								,"subjectType","descript","descriptPlain","memo","difficulty","score","publishDate"
								,"publishTeacherName","answer","optionCount","option0","option1","option2","option3","option4"
								,"option5","option6","option7","option8","option9","status","note","operateTime"};
		return JSONUtil.toJson(list, properties, total);
	}

	public ServiceResult save(Subject model,String optionsStr) {
		ServiceResult result = new ServiceResult(false);
		Integer subjectId = serialNumberService.getNextSerial(SerialNumberTabelName.SUBJECT);
		if (model==null) {
			result.setMessage("请填写试题信息");
			return result;
		}
		if(model.getPublishDate()==null){
			result.setMessage("请填写出题日期");
			return result;
		}
		if(model.getScore()==null){
			result.setMessage("请填写默认分值");
			return result;
		}
		if(model.getDescript()==null){
			result.setMessage("请填写试题描述");
			return result;
		}
		
		if("单项选择".equals(model.getSubjectType())){
			if(model.getAnswer()==null){
				result.setMessage("请选择正确答案");
				return result;
			}
			if(StringUtils.isEmpty(optionsStr)){
				result.setMessage("请填写答案信息");
				return result;
			}
			
			String str = optionsStr.substring(0,optionsStr.length()-1);
			String[] options = StringUtil.split(str);
			model.setOptionCount(options.length);
			for (int i = 0; i < options.length; i++) {
				switch (i) {
					case 0:
						model.setOption0(options[i]);
						break;
					case 1:
						model.setOption1(options[i]);
						break;
					case 2:
						model.setOption2(options[i]);
						break;
					case 3:
						model.setOption3(options[i]);
						break;
					case 4:
						model.setOption4(options[i]);
						break;
					case 5:
						model.setOption5(options[i]);
						break;
					case 6:
						model.setOption6(options[i]);
						break;
					case 7:
						model.setOption7(options[i]);
						break;
					case 8:
						model.setOption8(options[i]);
						break;
					case 9:
						model.setOption9(options[i]);
						break;
					default:
						break;
				}
			}
		}else if("多项选择".equals(model.getSubjectType())){
			if(model.getAnswer()==null){
				result.setMessage("请选择正确答案");
				return result;
			}
			if(StringUtils.isEmpty(optionsStr)){
				result.setMessage("请填写答案信息");
				return result;
			}
			
			String str = optionsStr.substring(0,optionsStr.length()-1);
			String[] options = StringUtil.split(str);
			model.setOptionCount(options.length);
			for (int i = 0; i < options.length; i++) {
				switch (i) {
					case 0:
						model.setOption0(options[i]);
						break;
					case 1:
						model.setOption1(options[i]);
						break;
					case 2:
						model.setOption2(options[i]);
						break;
					case 3:
						model.setOption3(options[i]);
						break;
					case 4:
						model.setOption4(options[i]);
						break;
					case 5:
						model.setOption5(options[i]);
						break;
					case 6:
						model.setOption6(options[i]);
						break;
					case 7:
						model.setOption7(options[i]);
						break;
					case 8:
						model.setOption8(options[i]);
						break;
					case 9:
						model.setOption9(options[i]);
						break;
					default:
						break;
				}
			}
		}else if("判断".equals(model.getSubjectType())){
			if(model.getAnswer()==null){
				result.setMessage("请选择正确答案");
				return result;
			}
		}else if("判断改错".equals(model.getSubjectType())){
			if(model.getAnswer()==null){
				result.setMessage("请选择正确答案");
				return result;
			}
			if("False".equals(model.getAnswer())){
				if(model.getOption0()==null){
					result.setMessage("请填写改错内容");
					return result;
				}
			}
			
		}else if("简答".equals(model.getSubjectType())){
			if(model.getAnswer()==null){
				result.setMessage("请填写正确答案");
				return result;
			}
			model.setOptionCount(1);
		}else if("填空".equals(model.getSubjectType())){
			if(StringUtils.isEmpty(optionsStr)){
				result.setMessage("请填写答案信息");
				return result;
			}
			String str = optionsStr.substring(0,optionsStr.length()-1);
			String[] options = StringUtil.split(str);
			model.setOptionCount(options.length);
			for (int i = 0; i < options.length; i++) {
				options[i] = "!@#"+options[i].replace("/","!@#")+"!@#";
				switch (i) {
					case 0:
						model.setOption0(options[i]);
						break;
					case 1:
						model.setOption1(options[i]);
						break;
					case 2:
						model.setOption2(options[i]);
						break;
					case 3:
						model.setOption3(options[i]);
						break;
					case 4:
						model.setOption4(options[i]);
						break;
					case 5:
						model.setOption5(options[i]);
						break;
					case 6:
						model.setOption6(options[i]);
						break;
					case 7:
						model.setOption7(options[i]);
						break;
					case 8:
						model.setOption8(options[i]);
						break;
					case 9:
						model.setOption9(options[i]);
						break;
					default:
						break;
				}
			}
		}else if ("完型填空".equals(model.getSubjectType())) {
			if(model.getAnswer()==null){
				result.setMessage("请选择正确答案");
				return result;
			}
			if(StringUtils.isEmpty(optionsStr)){
				result.setMessage("请填写答案信息");
				return result;
			}
			String[] answers = StringUtil.split(model.getAnswer());
			String[] options = StringUtil.split(optionsStr);
			if(answers.length!=options.length){
				result.setMessage("答案个数与选项个数不合");
				return result;
			}
			
			model.setOptionCount(options.length);
		}else if("阅读理解".equals(model.getSubjectType())){
			if(StringUtils.isEmpty(optionsStr)){
				result.setMessage("请填选问题相关信息");
				return result;
			}
		}
		
		model.setOperateTime(new java.sql.Timestamp(new Date().getTime()));
		
		if(model.getSubjectId() == null) {// 新增
			
			model.setSubjectId(subjectId);
			model.setStatus(1);
			subjectDAO.save(model);
			if("完型填空".equals(model.getSubjectType())){
				String[] answers = StringUtil.split(model.getAnswer());
				String[] options = StringUtil.split(optionsStr);
				
				for(int i = 0; i < options.length; i++){
					String[] option=options[i].split("!@#");
					SubjectSub subjectSub=new SubjectSub();
					subjectSub.setOption0(option[0]);
					subjectSub.setOption1(option[1]);
					subjectSub.setOption2(option[2]);
					subjectSub.setOption3(option[3]);
					subjectSub.setAnswer(answers[i]);
					Integer subId = serialNumberService.getNextSerial(SerialNumberTabelName.SUBJECTSUB);
					subjectSub.setSubject(model);
					SubjectSubId subjectSubId=new SubjectSubId();
					subjectSubId.setSubId(subId);
					subjectSubId.setSubjectId(model.getSubjectId());
					subjectSub.setId(subjectSubId);
					subjectSub.setTeacher(model.getPublishTeacher());
					subjectSub.setOperateTime(new java.sql.Timestamp(new Date().getTime()));
					subjectSubDAO.save(subjectSub);
				}
				model.setAnswer(null);
			}else if("阅读理解".equals(model.getSubjectType())){
				String[] subjectSubListAll = optionsStr.split("\\^");
				for(int i=0;i<subjectSubListAll.length;i++){
					String[] subjectSubList = subjectSubListAll[i].split("!@#");
					SubjectSub subjectSub=new SubjectSub();
					subjectSub.setSubject(model);
					subjectSub.setDescript(subjectSubList[0]);
					subjectSub.setAnswer(subjectSubList[1]);
					subjectSub.setOption0(subjectSubList[2]);
					subjectSub.setOption1(subjectSubList[3]);
					subjectSub.setOption2(subjectSubList[4]);
					subjectSub.setOption3(subjectSubList[5]);
					
					SubjectSubId subjectSubId=new SubjectSubId();
					subjectSubId.setSubjectId(model.getSubjectId());
					Integer subId = serialNumberService.getNextSerial(SerialNumberTabelName.SUBJECTSUB);
					subjectSubId.setSubId(subId);
					subjectSub.setId(subjectSubId);
					
					subjectSub.setTeacher(model.getPublishTeacher());
					subjectSub.setOperateTime(new java.sql.Timestamp(new Date().getTime()));
					
					subjectSubDAO.save(subjectSub);
				}
			}
		}else{
			Subject oldModel = subjectDAO.load(model.getSubjectId());
			if (oldModel == null) {
				result.setMessage("该试题已不存在");
				return result;
			}
			oldModel.setGroup(model.getGroup());
			oldModel.setAnswer(model.getAnswer());
			oldModel.setDescript(model.getDescript());
			oldModel.setDifficulty(model.getDifficulty());
			oldModel.setMemo(model.getMemo());
			oldModel.setNote(model.getNote());
			oldModel.setPublishDate(model.getPublishDate());
			oldModel.setOperateTime(new java.sql.Timestamp(new Date().getTime()));
			oldModel.setScore(model.getScore());
			oldModel.setDescriptPlain(model.getDescriptPlain());
			if("单项选择".equals(model.getSubjectType())||"多项选择".equals(model.getSubjectType())||
					"填空".equals(model.getSubjectType())){
				oldModel.setOptionCount(model.getOptionCount());
				for (int i = 0; i < model.getOptionCount(); i++) {
					switch (i) {
						case 0:
							oldModel.setOption0(model.getOption0());
							break;
						case 1:
							oldModel.setOption1(model.getOption1());
							break;
						case 2:
							oldModel.setOption2(model.getOption2());
							break;
						case 3:
							oldModel.setOption3(model.getOption3());
							break;
						case 4:
							oldModel.setOption4(model.getOption4());
							break;
						case 5:
							oldModel.setOption5(model.getOption5());
							break;
						case 6:
							oldModel.setOption6(model.getOption6());
							break;
						case 7:
							oldModel.setOption7(model.getOption7());
							break;
						case 8:
							oldModel.setOption8(model.getOption8());
							break;
						case 9:
							oldModel.setOption9(model.getOption9());
							break;
						default:
							break;
					}
				}
			}else if("完型填空".equals(model.getSubjectType())){
				
				subjectSubDAO.delete(oldModel.getSubjectId());
				String[] answers = StringUtil.split(model.getAnswer());
				String[] options = StringUtil.split(optionsStr);
				
				oldModel.setOptionCount(options.length);
				for(int i = 0; i < options.length; i++){
					String[] option=options[i].split("!@#");
					SubjectSub subjectSub=new SubjectSub();
					subjectSub.setOption0(option[0]);
					subjectSub.setOption1(option[1]);
					subjectSub.setOption2(option[2]);
					subjectSub.setOption3(option[3]);
					subjectSub.setAnswer(answers[i]);
					Integer subId = serialNumberService.getNextSerial(SerialNumberTabelName.SUBJECTSUB);
					subjectSub.setSubject(oldModel);
					SubjectSubId subjectSubId=new SubjectSubId();
					subjectSubId.setSubId(subId);
					subjectSubId.setSubjectId(oldModel.getSubjectId());
					subjectSub.setId(subjectSubId);
					subjectSub.setTeacher(oldModel.getPublishTeacher());
					subjectSub.setOperateTime(new java.sql.Timestamp(new Date().getTime()));
					subjectSubDAO.save(subjectSub);
				}
				oldModel.setAnswer(null);
			}else if("阅读理解".equals(model.getSubjectType())){
				subjectSubDAO.delete(oldModel.getSubjectId());
				String[] subjectSubListAll = optionsStr.split("\\^");
				oldModel.setOptionCount(subjectSubListAll.length);
				
				for(int i=0;i<subjectSubListAll.length;i++){
					String[] subjectSubList = subjectSubListAll[i].split("!@#");
					SubjectSub subjectSub=new SubjectSub();
					subjectSub.setSubject(oldModel);
					subjectSub.setDescript(subjectSubList[0]);
					subjectSub.setAnswer(subjectSubList[1]);
					subjectSub.setOption0(subjectSubList[2]);
					subjectSub.setOption1(subjectSubList[3]);
					subjectSub.setOption2(subjectSubList[4]);
					subjectSub.setOption3(subjectSubList[5]);
					
					SubjectSubId subjectSubId=new SubjectSubId();
					subjectSubId.setSubjectId(oldModel.getSubjectId());
					Integer subId = serialNumberService.getNextSerial(SerialNumberTabelName.SUBJECTSUB);
					subjectSubId.setSubId(subId);
					subjectSub.setId(subjectSubId);
					
					subjectSub.setTeacher(oldModel.getPublishTeacher());
					subjectSub.setOperateTime(new java.sql.Timestamp(new Date().getTime()));
					
					subjectSubDAO.save(subjectSub);
				}
			}
		}
		result.setIsSuccess(true);
		result.addData("subjectId", model.getSubjectId());
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.SubjectService#selectPaperBigSmall(com.csit.model.Subject, java.lang.String, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public String selectPaperBigSmall(Subject model, String subjectIds,
			Integer page, Integer rows) {
		Integer[] idArray = StringUtil.splitToIntegerTrimNull(subjectIds);
		List<Subject> list = subjectDAO.selectPaperBigSmall(model,idArray, page, rows);

		Long total = subjectDAO.getTotalCountSelectPaperBigSmall(model,idArray);
		String[] properties = { "subjectId","group.groupName:groupName"
								,"publishTeacher.teacherName:publishTeacherName","descript"
								,"subjectType","difficulty","score","publishDate"
							  };
		return JSONUtil.toJson(list, properties, total);
	}

	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.SubjectService#mulUpdateState(java.lang.String, com.csit.model.DataDictionary, java.lang.Integer)
	 */
	@Override
	public ServiceResult mulUpdateState(String ids, Subject model, Integer teacherId) {
		ServiceResult result = new ServiceResult(false);
		if(StringUtils.isEmpty(ids)){
			result.setMessage("请选择要修改状态的试题");
			return result;
		}
		String[] idArray =StringUtil.split(ids);
		if(idArray.length==0){
			result.setMessage("请选择要修改状态的试题");
			return result;
		}
		if(model==null||model.getStatus()==null){
			result.setMessage("请选择要修改成的状态");
			return result;
		}
		boolean haveUpdateShzt = false;
		for (String id : idArray) {
			Subject oldModel = subjectDAO.load(Integer.parseInt(id));
			if(oldModel!=null&&oldModel.getStatus()!=model.getStatus()){
				oldModel.setStatus(model.getStatus());
				haveUpdateShzt = true;
			}
		}
		if(!haveUpdateShzt){
			result.setMessage("没有可修改状态的试题");
			return result;
		}
		result.setIsSuccess(true);
		return result;
	}
}
