package com.csit.service.impl;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.csit.dao.PaperBigDAO;
import com.csit.dao.PaperBigSmallDAO;
import com.csit.dao.PaperDAO;
import com.csit.dao.SubjectSubDAO;
import com.csit.dao.TempPaperBigDAO;
import com.csit.dao.TempPaperBigSmallDAO;
import com.csit.dao.TempPaperDAO;
import com.csit.model.Paper;
import com.csit.model.PaperBig;
import com.csit.model.PaperBigSmall;
import com.csit.model.PaperBigSmallId;
import com.csit.model.Subject;
import com.csit.model.SubjectSub;
import com.csit.model.TempPaper;
import com.csit.model.TempPaperBig;
import com.csit.model.TempPaperBigId;
import com.csit.model.TempPaperBigSmall;
import com.csit.model.TempPaperBigSmallId;
import com.csit.model.TempPaperId;
import com.csit.service.PaperService;
import com.csit.service.SerialNumberService;
import com.csit.util.CommonUtil;
import com.csit.util.DateUtil;
import com.csit.util.JSONUtil;
import com.csit.util.StringUtil;
import com.csit.vo.SerialNumberTabelName;
import com.csit.vo.ServiceResult;
import com.csit.vo.StoreProcedureResult;
/**
 * @Description:试卷Service实现类
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-4-27
 * @Author lys
 */
@Service
public class PaperServiceImpl extends BaseServiceImpl<Paper, Integer> implements
		PaperService {
	@Resource
	private PaperDAO paperDAO;
	@Resource
	private SerialNumberService serialNumberService;
	@Resource
	private TempPaperDAO tempPaperDAO;
	@Resource
	private TempPaperBigDAO tempPaperBigDAO;
	@Resource
	private TempPaperBigSmallDAO tempPaperBigSmallDAO;
	@Resource
	private PaperBigDAO paperBigDAO;
	@Resource
	private PaperBigSmallDAO paperBigSmallDAO;
	@Resource
	private SubjectSubDAO subjectSubDAO;
	
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.PaperService#query(com.csit.model.Paper, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public String query(Paper model, Integer page, Integer rows) {
		List<Paper> list = paperDAO.query(model, page, rows);
		Long total = paperDAO.getTotalCount(model);
		String[] properties = { "paperId","state","paperType","paperName","limits","group.groupName",
							"score","publishDate","note","publishTeacher.teacherName"};
		return JSONUtil.toJson(list, properties, total);
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.PaperService#newPaper(java.lang.Integer, java.sql.Timestamp)
	 */
	@Override
	public ServiceResult newAdd(Integer teacherId, Timestamp operateTime) {
		ServiceResult result= new ServiceResult();
		//1.从序列表中取得，临时表的id
		Integer paperId = serialNumberService.getNextSerial(SerialNumberTabelName.PAPER);
		//2.往试卷临时表中插入临时试卷信息
		TempPaperId tempPaperId = new TempPaperId();
		tempPaperId.setPaperId(paperId);
		tempPaperId.setOperateTime(operateTime);
		tempPaperId.setTeacherId(teacherId);
		
		TempPaper tempPaper  = new TempPaper();
		tempPaper.setId(tempPaperId);
		tempPaper.setPaperType("统一试题打乱生成");
		tempPaper.setIsBigMix(false);
		tempPaper.setIsSmallContinue(true);
		tempPaper.setPublishTeacherId(teacherId);
		tempPaper.setPublishDate(DateUtil.getNowTimestamp());
		tempPaper.setState(false);
		tempPaper.setScore(0.0);
		tempPaper.setLimits(0);
		tempPaperDAO.save(tempPaper);
		
		//4.返回试卷临时试卷信息
		result.setIsSuccess(true);
		
		String[] propertiesTempPaper = {"id.paperId","paperType","paperName","groupId","limits","isBigMix","isSmallContinue",
				"score","publishTeacherId","publishDate","note"};
		String tempPaperData = JSONUtil.toJson(tempPaper,propertiesTempPaper);
		result.addData("tempPaper", tempPaperData);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.PaperService#clearTemp(java.lang.Integer, java.sql.Timestamp, com.csit.model.Paper)
	 */
	@Override
	public ServiceResult clearTemp(Integer teacherId, Timestamp operateTime,
			Paper model) {
		ServiceResult result = new ServiceResult(false);
		if(model==null||model.getPaperId()==null){
			result.setMessage("请选择试卷");
			return result;
		}
		paperDAO.clearTemp(teacherId,operateTime,model.getPaperId());
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.PaperService#save(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.Integer, java.sql.Timestamp, com.csit.model.Paper)
	 */
	@Override
	public ServiceResult save(String bigId, String smallIds,
			String isOptionMixs, String scores, String difficultys,
			String bigIds, String subjectTypes, String bigNames,
			String isSmallMixs, String scoreBigs, String saveType,
			Integer teacherId, Timestamp operateTime, Paper model) {
		ServiceResult result = new ServiceResult(false);
		StoreProcedureResult spResult = null;
		if(model==null||model.getPaperId()==null){
			result.setMessage("请填写试卷信息");
			return result;
		}
		if(StringUtils.isEmpty(model.getPaperName())){
			result.setMessage("请填写试卷名称");
			return result;
		}
		if(model.getGroup()==null||model.getGroup().getGroupId()==null){
			result.setMessage("请选择课程");
			return result;
		}
		if(StringUtils.isEmpty(model.getPaperName())){
			result.setMessage("请填写试卷名称");
			return result;
		}
		if(model.getLimits()==null){
			result.setMessage("请设置限时");
			return result;
		}
		if(0==model.getLimits().intValue()){
			result.setMessage("限时不能为0分钟");
			return result;
		}
		if(StringUtils.isEmpty(bigIds)){
			result.setMessage("大题为空，请添加大题");
			return result;
		}
		
		Integer[] smallIdArray = StringUtil.splitToInteger(smallIds);
		Boolean[] isOptionMixArray = StringUtil.splitToBoolean(isOptionMixs);
		Double[] scoreArray = StringUtil.splitToDouble(scores);
		String[] difficultyArray = StringUtil.split(difficultys);
		
		Integer[] bigIdArray = StringUtil.splitToInteger(bigIds);
		String[] subjectTypeArray = StringUtil.split(subjectTypes);
		String[] bigNameArray = StringUtil.split(bigNames);
		Boolean[] isSmallMixArray = StringUtil.splitToBoolean(isSmallMixs);
		Double[] scoreBigArray = StringUtil.splitToDouble(scoreBigs);
		
		//更新小题
		for (int i = 0; i < smallIdArray.length&&smallIdArray[i]!=null; i++) {
			Integer smallId = smallIdArray[i];
			Boolean isOptionMix = isOptionMixArray[i];
			Double score = scoreArray[i];
			String difficulty = difficultyArray[i];
			
			TempPaperBigSmallId  tempPaperBigSmallId = new TempPaperBigSmallId();
			tempPaperBigSmallId.setPaperId(model.getPaperId());
			tempPaperBigSmallId.setBigId(Integer.parseInt(bigId));
			tempPaperBigSmallId.setSmallId(smallId);
			tempPaperBigSmallId.setTeacherId(teacherId);
			tempPaperBigSmallId.setOperateTime(operateTime);
			
			TempPaperBigSmall oldTempPaperBigSmall = tempPaperBigSmallDAO.load(tempPaperBigSmallId);
			if(isOptionMix==null){
				isOptionMix = false;
			}
			oldTempPaperBigSmall.setIsOptionMix(isOptionMix);
			oldTempPaperBigSmall.setScore(score);
			oldTempPaperBigSmall.setDifficulty(difficulty);
		}
		tempPaperBigSmallDAO.flush();//为了后面执行本地Sql，即使更新数据库
		//更新大题
		for (int i = 0; i < bigIdArray.length; i++) {
			Integer bigIdInt = bigIdArray[i];
			String subjectType = subjectTypeArray[i];
			String bigName = bigNameArray[i];
			Boolean isSmallMix = isSmallMixArray[i];
			Double score = scoreBigArray[i];
			
			TempPaperBigId  tempPaperBigId = new TempPaperBigId();
			tempPaperBigId.setTeacherId(teacherId);
			tempPaperBigId.setOperateTime(operateTime);
			tempPaperBigId.setPaperId(model.getPaperId());
			tempPaperBigId.setBigId(bigIdInt);
			
			TempPaperBig oldTempPaperBig = tempPaperBigDAO.load(tempPaperBigId);
			oldTempPaperBig.setSubjectType(subjectType);
			oldTempPaperBig.setBigName(bigName);
			if(isSmallMix==null){
				isSmallMix = false;
			}
			oldTempPaperBig.setIsSmallMix(isSmallMix);
			oldTempPaperBig.setScore(score);
			oldTempPaperBig.setArray(i);
		}
		tempPaperBigDAO.flush();
		//更新试卷
		TempPaperId tempPaperId = new TempPaperId();
		tempPaperId.setPaperId(model.getPaperId());
		tempPaperId.setOperateTime(operateTime);
		tempPaperId.setTeacherId(teacherId);
		
		//处理Boolean型数据
		if(model.getIsBigMix()!=null){
			model.setIsBigMix(true);
		}else{
			model.setIsBigMix(false);
		}
		
		if(model.getIsSmallContinue()!=null){
			model.setIsSmallContinue(true);
		}else{
			model.setIsSmallContinue(false);
		}
		
		TempPaper oldTempPaper = tempPaperDAO.load(tempPaperId);
		oldTempPaper.setIsBigMix(model.getIsBigMix());
		oldTempPaper.setIsSmallContinue(model.getIsSmallContinue());
		oldTempPaper.setGroupId(model.getGroup().getGroupId());
		oldTempPaper.setLimits(model.getLimits()*60);
		oldTempPaper.setNote(model.getNote());
		oldTempPaper.setPaperName(model.getPaperName());
		oldTempPaper.setPaperType(model.getPaperType());
		oldTempPaper.setScore(model.getScore());
		
		tempPaperDAO.flush();
		if("Save_New".equals(saveType)){//新建保存
			oldTempPaper.setState(false);
			tempPaperDAO.flush();
			try {
				spResult = paperDAO.editPaperSP(teacherId,operateTime,model.getPaperId(),saveType,false);
				if(spResult.getReturnInt().intValue()==1){
					result.setMessage(spResult.getReturnValue());
					return result;
				}
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException();
			}
		}else if("Save_Modify".equals(saveType)){//修改保存
			try {
				spResult = paperDAO.editPaperSP(teacherId,operateTime,model.getPaperId(),saveType,false);
				if(spResult.getReturnInt().intValue()==1){
					result.setMessage(spResult.getReturnValue());
					return result;
				}
			} catch (SQLException e) {
				throw new RuntimeException();
			}
		}
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.PaperService#mulDelete(java.lang.String)
	 */
	@Override
	public ServiceResult mulDelete(String ids) {
		ServiceResult result = new ServiceResult(false);
		if(StringUtils.isEmpty(ids)){
			result.setMessage("请选择要删除的试卷");
			return result;
		}
		Integer[] idArray = StringUtil.splitToInteger(ids);
		if(idArray.length==0){
			result.setMessage("请选择要删除的试卷");
			return result;
		}
		boolean haveDel = false;
		for (Integer id : idArray) {
			Paper oldPaper = paperDAO.load(id);
			if(oldPaper==null||oldPaper.getState()==true){
				continue;
			}
			paperDAO.delete(id);
			haveDel = true;
		}
		if(!haveDel){
			result.setMessage("没有可删除的试卷");
			return result;
		}
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.PaperService#mulUpdateState(java.lang.String, com.csit.model.Paper)
	 */
	@Override
	public ServiceResult mulUpdateState(String ids, Paper model) {
		ServiceResult result = new ServiceResult(false);
		if(StringUtils.isEmpty(ids)){
			result.setMessage("请选择要更新状态的试卷");
			return result;
		}
		Integer[] idArray = StringUtil.splitToInteger(ids);
		if(idArray.length==0){
			result.setMessage("请选择要更新状态的试卷");
			return result;
		}
		if(model==null||model.getState()==null){
			result.setMessage("请选择要更新的状态");
			return result;
		}
		boolean haveUpdateState = false;
		for (Integer id : idArray) {
		/*	Paper oldPaper = paperDAO.load(id);
			if(oldPaper==null||oldPaper.getState()==model.getState()){
				continue;
			}
			oldPaper.setState(model.getState());*/
			try {
				paperDAO.editPaperSP(1,DateUtil.getNowTimestamp(),id,"State",model.getState());
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException();
			}
			haveUpdateState = true;
		}
		if(!haveUpdateState){
			result.setMessage("没有可更新状态的试卷");
			return result;
		}
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.PaperService#modify(com.csit.model.Paper, java.lang.Integer, java.sql.Timestamp)
	 */
	@Override
	public ServiceResult modify(Paper model, Integer teacherId,
			Timestamp operateTime) {
		return view(model,teacherId,operateTime);
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.PaperService#view(com.csit.model.Paper, java.lang.Integer, java.sql.Timestamp)
	 */
	@Override
	public ServiceResult view(Paper model, Integer teacherId,
			Timestamp operateTime) {
		ServiceResult result = new ServiceResult(false);
		if(model==null||model.getPaperId()==null){
			result.setMessage("请选择要打开的试卷");
			return result;
		}
		//将试卷中的内容复制到到临时试卷
		paperDAO.view(model.getPaperId(),teacherId,operateTime);
		
		TempPaperId tempPaperId = new TempPaperId();
		tempPaperId.setPaperId(model.getPaperId());
		tempPaperId.setTeacherId(teacherId);
		tempPaperId.setOperateTime(operateTime);
		
		TempPaper tempPaper = tempPaperDAO.load(tempPaperId);
		
		tempPaper.setLimits(tempPaper.getLimits()/60);
		
		String[] propertiesTempPaper = {"id.paperId","paperType","paperName","groupId","limits","isBigMix","isSmallContinue",
				"score","publishTeacherId","publishDate","note","state"};
		String tempPaperData = JSONUtil.toJson(tempPaper,propertiesTempPaper);
		result.addData("tempPaper", tempPaperData);
		
		List<TempPaperBig> tempPaperBigList  = tempPaperBigDAO.query(model.getPaperId(), teacherId,operateTime);
		
		String[] propertiesTempPaperBig = {"id.paperId","id.bigId","bigName","subjectType","isSmallMix","score","array"};
		String tempPaperBigData = JSONUtil.toJson(tempPaperBigList,propertiesTempPaperBig);
		result.addData("tempPaperBig", tempPaperBigData);
		
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.PaperService#copyAdd(java.lang.Integer, java.lang.Integer, java.sql.Timestamp)
	 */
	@Override
	public ServiceResult copyAdd(Integer paperId, Integer teacherId,
			Timestamp operateTime) {
		ServiceResult result= new ServiceResult();
		//1.从序列表中取得，临时表的id
		Integer paperIdTemp = serialNumberService.getNextSerial(SerialNumberTabelName.PAPER);
		paperDAO.copyAdd(paperId,paperIdTemp,teacherId,operateTime);
		
		TempPaperId tempPaperId = new TempPaperId();
		tempPaperId.setPaperId(paperIdTemp);
		tempPaperId.setTeacherId(teacherId);
		tempPaperId.setOperateTime(operateTime);
		
		TempPaper tempPaper = tempPaperDAO.load(tempPaperId);
		tempPaper.setLimits(tempPaper.getLimits()/60);
		
		String[] propertiesTempPaper = {"id.paperId","paperType","paperName","groupId","limits","isBigMix","isSmallContinue",
				"score","publishTeacherId","publishDate","note"};
		String tempPaperData = JSONUtil.toJson(tempPaper,propertiesTempPaper);
		result.addData("tempPaper", tempPaperData);
		
		List<TempPaperBig> tempPaperBigList  = tempPaperBigDAO.query(paperIdTemp, teacherId,operateTime);
		
		String[] propertiesTempPaperBig = {"id.paperId","id.bigId","bigName","subjectType","isSmallMix","score","array"};
		String tempPaperBigData = JSONUtil.toJson(tempPaperBigList,propertiesTempPaperBig);
		result.addData("tempPaperBig", tempPaperBigData);
		
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.PaperService#getViewPanel(com.csit.model.Paper)
	 */
	@Override
	public String getViewPanel(Paper model) {
		StringBuilder sb = new StringBuilder();
		Paper oldModel = paperDAO.loadDetail(model.getPaperId());
		sb.append("<div class=\"paperTitle\"> ");
		sb.append("		<p class=\"paperTitleP\">试卷类型："+oldModel.getPaperType()+"</p> ");
		sb.append("		<p class=\"paperTitleP\">试卷名称："+oldModel.getPaperName()+"；限时："+oldModel.getLimits()/60+"分钟</p> ");
		sb.append("		<p class=\"paperTitleP\">参赛组别："+oldModel.getGroup().getGroupName()+"；分值："+oldModel.getScore()+"分  </p> ");
		sb.append("		<p class=\"paperTitleP\">出卷教师："+oldModel.getPublishTeacher().getTeacherName()+"；出卷时间："+oldModel.getPublishDate()+"  </p> ");
		sb.append("		<p class=\"paperTitleP\">备注："+StringUtil.getEmptyToBlank(oldModel.getNote())+" </p> ");
		sb.append("</div> ");
		List<PaperBig> bigList = paperBigDAO.query(model);
		int bigSize = bigList.size();
		int smallNo = 0;
		Boolean isSmallContinue = oldModel.getIsSmallContinue();
		
		for (int i = 0; i < bigSize; i++) {
			PaperBig paperBig  = bigList.get(i);
			String subjectType =  paperBig.getSubjectType().trim();
			List<PaperBigSmall> smallList = paperBigSmallDAO.query(paperBig);
			int sizeSmall = smallList.size();
			sb.append("<div class=\"paperContent\"> ");
			sb.append("<div  class=\"paperBigItem\"> ");
			sb.append("	<div class=\"paperBigItemTitle\">"+CommonUtil.getBigNo((i+1))+"."+
					paperBig.getBigName()+"("+paperBig.getScore()+"分)"+"  </div> ");
				//取得大题下的小题
				Double totalScore=0d;
				for (int j = 0; j < sizeSmall; j++) {
					PaperBigSmall paperBigSmall = smallList.get(j);
					Subject subject = paperBigSmall.getSubject();
					int optionCount = subject.getOptionCount();
					String descript = subject.getDescript();
					sb.append("	<div class=\"paperBigSmallItem\"> ");
					//处理题号
					String smallNoStr = (++smallNo)+"";
					if("听力短文".equals(subjectType)||"填空".equals(subjectType)||"阅读理解".equals(subjectType)||"完型填空".equals(subjectType)){
						if(!isSmallContinue){
							smallNo = 1;
							smallNoStr =""+smallNo;
						}
						if(subject.getOptionCount()!=1){
							smallNoStr =smallNo+"-"+(smallNo+subject.getOptionCount()-1);
							smallNo = smallNo+subject.getOptionCount()-1;
						}
						
					}
					if("填空".equals(subjectType)){
						String[] smallNoArray =StringUtil.split(smallNoStr, "-");
						Integer beginSmallNo  = Integer.parseInt(smallNoArray[0]);
						for (int k = 0; k < optionCount; k++) {
							Pattern p = Pattern.compile("(【(\\d+)】)([.]*)", Pattern.CASE_INSENSITIVE);// 正则表达式，后面的参数指定忽略大小写
							Matcher m = p.matcher(descript);// 匹配的字符串
							if (m.find())
							{
								String group1 = m.group(1);
								String group2 = m.group(2);
								String group3 = m.group(3);
								Integer smallOrderNo = Integer.parseInt(group2)-1;
								if(StringUtils.isNotEmpty(group3)){
									descript =descript.replaceAll(group1, ""+((beginSmallNo+smallOrderNo)));
								}else{
									descript = descript.replaceAll(group1, "<u>"+((beginSmallNo+smallOrderNo))+"</u>");
								}
							}
						}
					}else if("完型填空".equals(subjectType)){
						String[] smallNoArray =StringUtil.split(smallNoStr, "-");
						Integer beginSmallNo  = Integer.parseInt(smallNoArray[0]);
						for (int k = 0; k < optionCount; k++) {
							Pattern p = Pattern.compile("(【(\\d+)】)", Pattern.CASE_INSENSITIVE);// 正则表达式，后面的参数指定忽略大小写
							Matcher m = p.matcher(descript);// 匹配的字符串
							if (m.find())
							{
								String group1 = m.group(1);
								String group2 = m.group(2);
								Integer smallOrderNo = Integer.parseInt(group2)-1;
								descript = descript.replaceAll(group1, "<u>"+((beginSmallNo+smallOrderNo))+"</u>");
							}
						}
					}
					//题号+题目描述
					sb.append(smallNoStr+descript);
					
					if("单项选择".equals(subjectType)||"多项选择".equals(subjectType)){
						sb.append("（<span class=\"paperBigSmallItemAnswer\">"+subject.getAnswer()+"</span>）分值：<span class=\"paperBigSmallItemScore\">"+paperBigSmall.getScore()+"</span> ");
					}
					totalScore+=paperBigSmall.getScore();
					sb.append("		</div> ");
					if("阅读理解".equals(subjectType)){
						Double subjectSubScore = subject.getScore()/subject.getOptionCount();
						sb.append("		<div  class=\"paperBigSmallItemQuestion\">  ");
						List<SubjectSub> subjectSubList = subjectSubDAO.query(subject);
						int sizeSubjectSub = subjectSubList.size();
						//处理题号
						String[] smallNoArray =StringUtil.split(smallNoStr, "-");
						Integer beginSmallNo  = Integer.parseInt(smallNoArray[0]);
						for (int k = 0; k < sizeSubjectSub; k++) {
							SubjectSub subjectSub = subjectSubList.get(k);
							sb.append("			<div  class=\"paperBigSmallItemQuestionTitle\"> ");
							sb.append("				"+(k+beginSmallNo)+"："+subjectSub.getDescript()+"（<span class=\"paperBigSmallItemAnswer\">"+subjectSub.getAnswer()+"</span>）分值：<span class=\"paperBigSmallItemScore\">"+subjectSubScore+"</span> ");
							sb.append("			</div> ");
							sb.append("			<div class=\"options\"> ");
							sb.append("			  	<p class=\"option\"> A："+subjectSub.getOption0()+"</p> ");
							sb.append("			  	<p class=\"option\"> B："+subjectSub.getOption1()+"</p> ");
							sb.append("			  	<p class=\"option\"> C："+subjectSub.getOption2()+"</p> ");
							sb.append("			  	<p class=\"option\"> D："+subjectSub.getOption3()+"</p> ");
							sb.append("			</div> ");
						}
						sb.append("		</div> ");
					}else if("单项选择".equals(subjectType)||"多项选择".equals(subjectType)){
						for (int k = 0; k < optionCount; k++) {
							switch (k) {
							case 0:
								sb.append("			  	<p class=\"option\"> A："+subject.getOption0()+"</p> ");
								break;
							case 1:
								sb.append("			  	<p class=\"option\"> B："+subject.getOption1()+"</p> ");
								break;
							case 2:
								sb.append("			  	<p class=\"option\"> C："+subject.getOption2()+"</p> ");
								break;
							case 3:
								sb.append("			  	<p class=\"option\"> D："+subject.getOption3()+"</p> ");
								break;
							case 4:
								sb.append("			  	<p class=\"option\"> E："+subject.getOption4()+"</p> ");
								break;
							case 5:
								sb.append("			  	<p class=\"option\"> F："+subject.getOption5()+"</p> ");
								break;
							case 6:
								sb.append("			  	<p class=\"option\"> G："+subject.getOption6()+"</p> ");
								break;
							case 7:
								sb.append("			  	<p class=\"option\"> H："+subject.getOption7()+"</p> ");
								break;
							case 8:
								sb.append("			  	<p class=\"option\"> I："+subject.getOption8()+"</p> ");
								break;
							case 9:
								sb.append("			  	<p class=\"option\"> J："+subject.getOption9()+"</p> ");
								break;
							default:
								break;
							}
						}
						sb.append("			</div> ");
					}else if("完型填空".equals(subjectType)){
						Double subjectSubScore = subject.getScore()/subject.getOptionCount();
						sb.append("		<div  class=\"paperBigSmallItemQuestion\">  ");
						List<SubjectSub> subjectSubList = subjectSubDAO.query(subject);
						int sizeSubjectSub = subjectSubList.size();
						//处理题号
						String[] smallNoArray =StringUtil.split(smallNoStr, "-");
						Integer beginSmallNo  = Integer.parseInt(smallNoArray[0]);
						for (int k = 0; k < sizeSubjectSub; k++) {
							SubjectSub subjectSub = subjectSubList.get(k);
							sb.append("			  	<p class=\"option\">"+(k+beginSmallNo)+"：  A："+subjectSub.getOption0());
							sb.append("			   		B："+subjectSub.getOption1());
							sb.append("			   		C："+subjectSub.getOption2());
							sb.append("			  		D："+subjectSub.getOption3()+"（<span class=\"paperBigSmallItemAnswer\">"+subjectSub.getAnswer()+"</span>）分值：<span class=\"paperBigSmallItemScore\">"+subjectSubScore+"</span> </p> ");
						}
						sb.append("		</div> ");
					}else if("填空".equals(subjectType)||"听力填空".equals(subjectType)){
						sb.append("答案： ");
						//处理题号
						String[] smallNoArray =StringUtil.split(smallNoStr, "-");
						Integer beginSmallNo  = Integer.parseInt(smallNoArray[0]);
						for (int k = 0; k < optionCount; k++) {
							switch (k) {
							case 0:
								sb.append("			  	<p class=\"option\"> "+(beginSmallNo+k)+":"+getMulAnswer(subject.getOption0())+"</p> ");
								break;
							case 1:
								sb.append("			  	<p class=\"option\"> "+(beginSmallNo+k)+":"+getMulAnswer(subject.getOption1())+"</p> ");
								break;
							case 2:
								sb.append("			  	<p class=\"option\"> "+(beginSmallNo+k)+":"+getMulAnswer(subject.getOption2())+"</p> ");
								break;
							case 3:
								sb.append("			  	<p class=\"option\"> "+(beginSmallNo+k)+":"+getMulAnswer(subject.getOption3())+"</p> ");
								break;
							case 4:
								sb.append("			  	<p class=\"option\"> "+(beginSmallNo+k)+":"+getMulAnswer(subject.getOption4())+"</p> ");
								break;
							case 5:
								sb.append("			  	<p class=\"option\"> "+(beginSmallNo+k)+":"+getMulAnswer(subject.getOption5())+"</p> ");
								break;
							case 6:
								sb.append("			  	<p class=\"option\"> "+(beginSmallNo+k)+":"+getMulAnswer(subject.getOption6())+"</p> ");
								break;
							case 7:
								sb.append("			  	<p class=\"option\"> "+(beginSmallNo+k)+":"+getMulAnswer(subject.getOption7())+"</p> ");
								break;
							case 8:
								sb.append("			  	<p class=\"option\"> "+(beginSmallNo+k)+":"+getMulAnswer(subject.getOption8())+"</p> ");
								break;
							case 9:
								sb.append("			  	<p class=\"option\"> "+(beginSmallNo+k)+":"+getMulAnswer(subject.getOption9())+"</p> ");
								break;
							default:
								break;
							}
						}
						sb.append("分值：<span class=\"paperBigSmallItemScore\">"+subject.getScore()+"</span> ");
						sb.append("			</div> ");
					}else if("判断".equals(subjectType)||"判断改错".equals(subjectType)){
						sb.append("答案： ");
						String answer = subject.getAnswer();
						if("True".equals(answer)){
							sb.append("对");
						}else if("False".equals(answer)){
							sb.append("错<br>");
							if("判断改错".equals(subjectType)){//加改错答案
								sb.append("正确答案："+subject.getOption0());
							}
						}
					}
					sb.append("</div> ");
				}
//				}
			sb.append("<div style=\"margin-left: 15px;font-size: 18;\">总分：<span class=\"paperBigSmallItemScore\">"+totalScore+"</span> </div> ");
			sb.append("</div> ");
			sb.append("</div> ");
		}
		
		return sb.toString();
	}
	/**
	 * @Description: 取得填空的多选答案
	 * @Created Time: 2013-5-8 下午11:19:26
	 * @Author lys
	 * @param optionStr
	 * @return
	 */
	private String getMulAnswer(String answer){
		String result = "";
		String[] answerArray = StringUtil.split(answer, "!@#");
		for (int i = 0; i < answerArray.length; i++) {
			String answerStr = answerArray[i];
			if(StringUtils.isNotEmpty(answerStr)){
				if(i!=0){
					result+="/"+answerStr;
				}else{
					result+=answerStr;
				}
			}
		}
		return result.substring(1);
	}
	@Override
	public String getViewHtml(Paper model) {
		String content = getViewPanel(model);
		StringBuilder sb = new StringBuilder();
		sb.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" > ");
		sb.append("<html> ");
		sb.append("  <head> ");
		sb.append("	<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"> ");
		sb.append("	<link rel=\"stylesheet\" type=\"text/css\" href=\"../style/common.css\"> ");
		sb.append("	<link rel=\"stylesheet\" type=\"text/css\" href=\"../style/v1/main.css\"> ");
			 
		sb.append("</head> ");
		sb.append("<body> ");
		sb.append(content);
		sb.append("</body> ");
		sb.append("</html> ");
	
		return sb.toString();
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.PaperService#getIndexStu()
	 */
	@Override
	public List<Paper> getIndexStu() {
		Paper model = new Paper();
		model.setState(true);
		
		
		List<Paper> paperList = paperDAO.query(model, 1, 10);
		
//		List<Paper> paperList = new ArrayList<Paper>();
//		paperList.add(model);
		
		return paperList;
	}
	/*
	 * 
	 */
	@Override
	public Paper load(Integer paperId) {
		return paperDAO.load(paperId);
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.PaperService#initSmall(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public ServiceResult initSmall(String paperId, String bigId, String smallId, String smallNo) {
		ServiceResult result = new ServiceResult(false);
		if(StringUtils.isEmpty(paperId)){
			result.setMessage("请选择要考试的试卷");
			return result;
		}
		if(StringUtils.isEmpty(bigId)){
			result.setMessage("请选择大题");
			return result;
		}
		if(StringUtils.isEmpty(smallId)){
			result.setMessage("请选择小题");
			return result;
		}
		
		PaperBigSmallId paperBigSmallId = new PaperBigSmallId();
		paperBigSmallId.setPaperId(Integer.parseInt(paperId));
		paperBigSmallId.setBigId(Integer.parseInt(bigId));
		paperBigSmallId.setSmallId(Integer.parseInt(smallId));
		
		PaperBigSmall paperBigSmall = paperBigSmallDAO.load(paperBigSmallId);
		Subject subject =  paperBigSmall.getSubject();
		String subjectType = subject.getSubjectType();
		
		Integer optionCount =  subject.getOptionCount();
		
		String descript = subject.getDescript();
		if("填空".equals(subjectType)){
			String[] smallNoArray =StringUtil.split(smallNo, "-");
			Integer beginSmallNo  = Integer.parseInt(smallNoArray[0]);
			for (int i = 0; i < optionCount; i++) {
				descript = descript.replace((i+1)+".", (beginSmallNo+i)+".");
			}
		}
		result.addData("subjectDesc",descript);
		
		StringBuffer optionsHtml = new StringBuffer();
		
		if("填空".equals(subjectType)){
			String[] smallNoArray =StringUtil.split(smallNo, "-");
			Integer beginSmallNo  = Integer.parseInt(smallNoArray[0]);
			optionsHtml.append("<table class=\"fillTable\"> ");
			optionsHtml.append("	<tr> ");
			optionsHtml.append("		<td>题号</td> ");
			optionsHtml.append("		<td>答案</td> ");
			optionsHtml.append("	</tr> ");
			for (int i = 0; i < optionCount; i++) {
				optionsHtml.append("<tr> ");
				optionsHtml.append("<td>"+(beginSmallNo+i)+"</td> ");
				optionsHtml.append("<td  class=\"fillAnswerTd\"> <input type=\"text\" class=\"fillAnswer\"></td> ");
				optionsHtml.append("</tr> ");
			}
			optionsHtml.append("</table> ");
		}else{
			for (int i = 0; i < optionCount; i++) {
				optionsHtml.append("<label for=\"option"+i+"\" class=\"labelOption\"> ");
				if(subjectType.endsWith("单项选择")){
					optionsHtml.append("<input type=\"radio\" name=\"answer\" 	class=\"answerRadio\" id=\"option"+i+"\">");
				}else if(subjectType.endsWith("多项选择")){
					optionsHtml.append("<input type=\"checkbox\" name=\"answer\" 	class=\"answerRadio\" id=\"option"+i+"\">");
				}
				switch (i) {
					case 0:
						optionsHtml.append("A:&nbsp;"+subject.getOption0());
						break;
					case 1:
						optionsHtml.append("B:&nbsp;"+subject.getOption1());
						break;
					case 2:
						optionsHtml.append("C:&nbsp;"+subject.getOption2());
						break;
					case 3:
						optionsHtml.append("D:&nbsp;"+subject.getOption3());
						break;
					case 4:
						optionsHtml.append("E:&nbsp;"+subject.getOption4());
						break;
					case 5:
						optionsHtml.append("F:&nbsp;"+subject.getOption5());
						break;
					case 6:
						optionsHtml.append("G:&nbsp;"+subject.getOption6());
						break;
					case 7:
						optionsHtml.append("H:&nbsp;"+subject.getOption7());
						break;
					case 8:
						optionsHtml.append("I:&nbsp;"+subject.getOption8());
						break;
					case 9:
						optionsHtml.append("J:&nbsp;"+subject.getOption9());
						break;
					default:
						break;
					}
				optionsHtml.append("</label><br>");
			}
		}
		result.addData("optionsHtml", optionsHtml.toString());
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.PaperService#updateState(com.csit.model.Paper)
	 */
	@Override
	public ServiceResult updateState(Paper model) {
		ServiceResult result = new ServiceResult(false);
		if(model==null||model.getPaperId()==null){
			result.setMessage("请选择要更新状态的试卷");
			return result;
		}
		if(model==null||model.getState()==null){
			result.setMessage("请选择要更新的状态");
			return result;
		}
		StoreProcedureResult spResult = new StoreProcedureResult();
		try {
			spResult = paperDAO.editPaperSP(1,DateUtil.getNowTimestamp(),model.getPaperId(),"State",model.getState());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		if(spResult.getReturnInt()==1){
			result.setMessage(spResult.getReturnValue());
			return result;
		}
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.PaperService#initExamView(com.csit.model.Paper)
	 */
	@Override
	public ServiceResult initExamView(Paper model) {
		ServiceResult result = new ServiceResult(false);
		if(model==null||model.getPaperId()==null){
			result.setMessage("请选择要初始化的答卷");
			return result;
		}
		Paper oldPaper = paperDAO.load(model.getPaperId());
		result.addData("distanceFinishLimits", oldPaper.getLimits()*1000L);
		result.addData("paperName", oldPaper.getPaperName()+"(总分"+oldPaper.getScore()+")");
		//生成答题卡
		StringBuffer subjectTableHtml = new StringBuffer();
		
		List<PaperBig> paperBigList =  paperBigDAO.query(model);
		int sizeBig = paperBigList.size();
		int smallArray = 0;
		int smallNo = 0;
		
		Boolean isSmallContinue = oldPaper.getIsSmallContinue();
		
		for (int i = 0; i < sizeBig; i++) {
			PaperBig paperBig = paperBigList.get(i);
			subjectTableHtml.append("<div class=\"bigItem\"> ");
			subjectTableHtml.append("<div class=\"bigName\" subjectType=\""+paperBig.getSubjectType().trim()+"\" bigId=\""+paperBig.getId().getBigId()+"\" bigName=\""+(CommonUtil.getBigNo(i+1))+" "+paperBig.getBigName()+"\">"+(CommonUtil.getBigNo(i+1))+" "+paperBig.getSubjectType()+"("+paperBig.getScore()+"分)"+"</div> ");
			//大题下的小题
			List<PaperBigSmall> smallList = paperBigSmallDAO.query(paperBig);
			int sizeSmall = smallList.size();
			subjectTableHtml.append("<div class=\"small\"> ");
			subjectTableHtml.append("	<table> ");
			for (int j = 0; j < sizeSmall; j++) {
				PaperBigSmall paperBigSmall = smallList.get(j);
				Subject subject = paperBigSmall.getSubject();
				String subjectType = subject.getSubjectType().trim();
				
				if(j%6==0||("听力短文".equals(subjectType)&&j%3==0)||("阅读理解".equals(subjectType)&&j%3==0)
						||("填空".equals(subjectType)&&j%3==0)||("完型填空".equals(subjectType)&&j%3==0)){
					if(j!=0){
						subjectTableHtml.append("		</tr> ");
					}
					subjectTableHtml.append("		<tr> ");
				}
				String smallNoStr = (++smallNo)+"";
				String smallStatus ="unAnswer";//都是未答题状态
				if("听力短文".equals(subjectType)||"填空".equals(subjectType)||"阅读理解".equals(subjectType)||"完型填空".equals(subjectType)){
					if(!isSmallContinue){
						smallNo = 1;
						smallNoStr =""+smallNo;
					}
					if(subject.getOptionCount()!=1){
						smallNoStr =smallNo+"-"+(smallNo+subject.getOptionCount()-1);
						smallNo = smallNo+subject.getOptionCount()-1;
					}
					
					subjectTableHtml.append("			<td class=\"mulTd "+smallStatus);
				}else{
					subjectTableHtml.append("			<td class=\"normalTd "+smallStatus);
				}
				subjectTableHtml.append("\"	smallStatus = \""+0+"\" bigId = \""+paperBigSmall.getId().getBigId()+"\" smallId = \""+paperBigSmall.getId().getSmallId()+"\" standardScore=\"("+paperBigSmall.getScore()+"分)"+"\" smallArray =\""+(++smallArray)+"\"> " +
						"<span>"+smallNoStr+"</span></td> ");
			}
			subjectTableHtml.append("	</table> ");	
			subjectTableHtml.append("</div> ");
			subjectTableHtml.append("</div> ");
		}
		result.addData("subjectTableHtml", subjectTableHtml.toString());
		result.addData("maxSmallArray", smallArray);
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.PaperService#initSmallView(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public ServiceResult initSmallView(String paperId, String bigId,
			String smallId, String smallNo) {
		ServiceResult result = new ServiceResult(false);
		if(StringUtils.isEmpty(paperId)){
			result.setMessage("请选择答卷");
			return result;
		}
		if(StringUtils.isEmpty(bigId)){
			result.setMessage("请选择大题");
			return result;
		}
		if(StringUtils.isEmpty(smallId)){
			result.setMessage("请选择小题");
			return result;
		}
		
		PaperBigSmallId paperBigSmallId = new PaperBigSmallId();
		paperBigSmallId.setPaperId(Integer.parseInt(paperId));
		paperBigSmallId.setBigId(Integer.parseInt(bigId));
		paperBigSmallId.setSmallId(Integer.parseInt(smallId));
		
		PaperBigSmall paperBigSmall = paperBigSmallDAO.load(paperBigSmallId);
		Subject subject = paperBigSmall.getSubject();
		String subjectType = subject.getSubjectType().trim();
		Integer optionCount =  subject.getOptionCount();
		String descript = subject.getDescript();
		String answer = subject.getAnswer();
		
		if("填空".equals(subjectType)){
			String[] smallNoArray =StringUtil.split(smallNo, "-");
			Integer beginSmallNo  = Integer.parseInt(smallNoArray[0].trim());
			for (int i = 0; i < optionCount; i++) {
				Pattern p = Pattern.compile("(【(\\d+)】)([.]*)", Pattern.CASE_INSENSITIVE);// 正则表达式，后面的参数指定忽略大小写
				Matcher m = p.matcher(descript);// 匹配的字符串
				if (m.find())
				{
					String group1 = m.group(1);
					String group2 = m.group(2);
					String group3 = m.group(3);
					Integer smallOrderNo = Integer.parseInt(group2)-1;
					if(StringUtils.isNotEmpty(group3)){
						descript = descript.replaceAll(group1, ""+((beginSmallNo+smallOrderNo)));
					}else{
						descript = descript.replaceAll(group1, "<u>"+((beginSmallNo+smallOrderNo))+"</u>");
					}
				}
//				descript = descript.replace((i+1)+".", (beginSmallNo+i)+".");
			}
		}else if("完型填空".equals(subjectType)){
			String[] smallNoArray =StringUtil.split(smallNo, "-");
			Integer beginSmallNo  = Integer.parseInt(smallNoArray[0].trim());
			for (int i = 0; i < optionCount; i++) {
				Pattern p = Pattern.compile("(【(\\d+)】)", Pattern.CASE_INSENSITIVE);// 正则表达式，后面的参数指定忽略大小写
				Matcher m = p.matcher(descript);// 匹配的字符串
				if (m.find())
				{
					String group1 = m.group(1);
					String group2 = m.group(2);
					Integer smallOrderNo = Integer.parseInt(group2)-1;
					descript = descript.replaceAll(group1, "<u>"+((beginSmallNo+smallOrderNo))+"</u>");
				}
			}
		}
		result.addData("subjectDesc",descript);
		
		StringBuffer optionsHtml = new StringBuffer();
		if("填空".equals(subjectType)){
			String[] smallNoArray =StringUtil.split(smallNo, "-");
			Integer beginSmallNo  = Integer.parseInt(smallNoArray[0].trim());
			optionsHtml.append("<table class=\"fillTable\"> ");
			optionsHtml.append("	<tr> ");
			optionsHtml.append("		<td class=\"fillSmallNoTd\">题号</td> ");
			optionsHtml.append("		<td class=\"fillAnswerTd\">答案</td> ");
			optionsHtml.append("	</tr> ");
			for (int i = 0; i < optionCount; i++) {
				optionsHtml.append("<tr> ");
				optionsHtml.append("<td class=\"optionInput\" style=\"font-weight: bold;\">"+(beginSmallNo+i)+"</td> ");
				//填写答案
				String optionAnswer = getOptionAnswer(subject,i);
				optionAnswer = optionAnswer.replaceAll("!@#", "/");
				if(optionAnswer.length()>2){
					optionAnswer = optionAnswer.substring(1, optionAnswer.length());
					optionAnswer = optionAnswer.substring(0, optionAnswer.length()-1);
				}
				optionsHtml.append("<td> <input type=\"text\" disabled=\"disabled\"  class=\"fillAnswerInput optionInput\" value=\""+optionAnswer+"\"  optionCount=\""+optionCount+"\" option=\"option"+i+"\"></td> ");
				optionsHtml.append("</tr> ");
			}
			optionsHtml.append("</table> ");
		}else if("阅读理解".equals(subjectType)){
			String[] smallNoArray =StringUtil.split(smallNo, "-");
			Integer beginSmallNo  = Integer.parseInt(smallNoArray[0].trim());
			
			List<SubjectSub> smallSubList = subjectSubDAO.query(subject);
			int smallSubSize = smallSubList.size();
			for (int i = 0; i < smallSubSize; i++) {
				SubjectSub smallSub = smallSubList.get(i);
				String answerSub = smallSub.getAnswer();
				optionsHtml.append("<table class=\"readAnswerTable\" id=\"id_"+smallSub.getId().getSubId()+"\" postAnswer=\""+answerSub+"\"> ");
				optionsHtml.append("<tr> ");
				optionsHtml.append("	<td class=\"readSubjectSubDesc\">"+(beginSmallNo+i)+"."+smallSub.getDescript()+"</td> ");
				optionsHtml.append("</tr> ");
				
				for (int j = 0; j < 4; j++) {
					optionsHtml.append("<tr> ");
					optionsHtml.append("	<td class=\"readTd\"> ");
					optionsHtml.append("		<label for=\""+smallSub.getId().getSubId()+"_"+CommonUtil.getOptionChar(j)+"\"   class=\"labelOption optionInput\">  ");
					optionsHtml.append("			<input class=\"readSubjectSubAnswer\"  disabled=\"disabled\" id=\""+smallSub.getId().getSubId()+"_"+CommonUtil.getOptionChar(j)+"\" name=\"answer_"+smallSub.getId().getSubId()+"\"   subId=\""+smallSub.getId().getSubId()+"\" option=\""+CommonUtil.getOptionChar(j)+"\" type=\"radio\" ");
					if((CommonUtil.getOptionChar(j)+"").equals(smallSub.getAnswer())){
						optionsHtml.append(" checked=\"checked\" ");
					}
					optionsHtml.append("/> "+CommonUtil.getOptionChar(j)+": ");
					String optionDes = "";
					switch (j) {
						case 0:
							optionDes= smallSub.getOption0();
							break;
						case 1:
							optionDes= smallSub.getOption1();
							break;
						case 2:
							optionDes= smallSub.getOption2();
							break;
						case 3:
							optionDes= smallSub.getOption3();
							break;
						default:
							break;
					}
					optionsHtml.append(optionDes);
					optionsHtml.append("		</label>	 ");
					optionsHtml.append("	</td> ");
					optionsHtml.append("</tr> ");
				}
				optionsHtml.append("</table> ");
			}
			result.addData("smallSubSize", smallSubSize);
		}else if("简答".equals(subjectType)){
			if(StringUtils.isEmpty(answer)){
				answer ="";
			}
			optionsHtml.append("<textarea class=\"optionInput\" style=\"width: 99%;height: 98%\" id=\"shortAnswer\" postAnswer=\""+answer+"\">"+answer+"</textarea> ");
		}else if("完型填空".equals(subjectType)){
			String[] smallNoArray =StringUtil.split(smallNo, "-");
			Integer beginSmallNo  = Integer.parseInt(smallNoArray[0].trim());
			
			List<SubjectSub> subjectSubList = subjectSubDAO.query(subject);
			int subjectSubSize = subjectSubList.size();
			optionsHtml.append("<table> ");
			for (int i = 0; i < subjectSubSize; i++) {
				SubjectSub subjectSub = subjectSubList.get(i);
				String answerSub = subjectSub.getAnswer();
				optionsHtml.append("<tr subId=\""+subjectSub.getId().getSubId()+"\"> ");
				optionsHtml.append("	<td  class=\"clozeTd optionInput\">"+(beginSmallNo+i)+".&nbsp;</td> ");
				for (int j = 0; j < 4; j++) {
					String optionDes = "";
					switch (j) {
						case 0:
							optionDes= subjectSub.getOption0();
							break;
						case 1:
							optionDes= subjectSub.getOption1();
							break;
						case 2:
							optionDes= subjectSub.getOption2();
							break;
						case 3:
							optionDes= subjectSub.getOption3();
							break;
						default:
							break;
					}
					optionsHtml.append("	<td class=\"clozeTd optionInput\"> ");
					optionsHtml.append("<input type=\"radio\"   disabled=\"disabled\" class=\"shortAnswerAnswer\" " +
							"option=\""+CommonUtil.getOptionChar(i)+"\"  " +
							"name=\"name_"+subjectSub.getId().getSubId()+"\"  " +
							"id=\"id_"+subjectSub.getId().getSubId()+"_"+CommonUtil.getOptionChar(j)+"\"");
					if((CommonUtil.getOptionChar(j)+"").equals(answerSub)){
						optionsHtml.append(" checked=\"checked\" ");
					}
					optionsHtml.append("\">");
					optionsHtml.append("<label for=\"id_"+subjectSub.getId().getSubId()+"_"+CommonUtil.getOptionChar(j)+"\">"
							+CommonUtil.getOptionChar(j)+":&nbsp; "+optionDes+"&nbsp;</label>");
					optionsHtml.append("	</td> ");
				}
				optionsHtml.append("</tr> ");
			}
			optionsHtml.append("</table> ");
			result.addData("subjectSubSize", subjectSubSize);
		}else{
			//如果学生已答题，则显示答题答案
			optionsHtml.append("<table id=\"postAnswer\" postAnswer=\""+answer+"\"> ");
			for (int i = 0; i < optionCount; i++) {
				optionsHtml.append("<tr> ");
				optionsHtml.append("	<td  class=\"radioTd optionInput\"> ");
				optionsHtml.append("<label for=\"id_"+CommonUtil.getOptionChar(i)+"\"> ");
				optionsHtml.append("<input disabled=\"disabled\" class=\"answerRadio\" option =\""+CommonUtil.getOptionChar(i)+"\" id=\"id_"+CommonUtil.getOptionChar(i)+"\"  name=\"answerRadio_"+smallId+"\" ");
				if(subjectType.equals("单项选择")){
					optionsHtml.append(" type=\"radio\" ");
				}else if(subjectType.equals("多项选择")){
					optionsHtml.append(" type=\"checkbox\" ");
				}
				if(StringUtils.isNotEmpty(answer)){
					char[] answerArray = answer.toCharArray();
					for (int j = 0; j < answerArray.length; j++) {
						char option =CommonUtil.getOptionChar(i);
						if(option==answerArray[j]){
							optionsHtml.append(" checked=\"checked\" ");
							break;
						}
					}
				}
				optionsHtml.append("/>");
				optionsHtml.append(CommonUtil.getOptionChar(i)+":&nbsp;");
				switch (i) {
					case 0:
						optionsHtml.append(subject.getOption0());
						break;
					case 1:
						optionsHtml.append(subject.getOption1());
						break;
					case 2:
						optionsHtml.append(subject.getOption2());
						break;
					case 3:
						optionsHtml.append(subject.getOption3());
						break;
					case 4:
						optionsHtml.append(subject.getOption4());
						break;
					case 5:
						optionsHtml.append(subject.getOption5());
						break;
					case 6:
						optionsHtml.append(subject.getOption6());
						break;
					case 7:
						optionsHtml.append(subject.getOption7());
						break;
					case 8:
						optionsHtml.append(subject.getOption8());
						break;
					case 9:
						optionsHtml.append(subject.getOption9());
						break;
					default:
						break;
					}
				optionsHtml.append("</label>");
				optionsHtml.append("</td> ");
				optionsHtml.append("	</tr> ");
			}
		}
		result.addData("optionsHtml", optionsHtml.toString());
		result.setIsSuccess(true);
		return result;
	}
	
	/**
	 * @Description: 取得填空选项答案
	 * @Created Time: 2013-5-15 下午4:09:11
	 * @Author lys
	 * @param examAnswerBigSmall
	 * @param optionIndex
	 * @return
	 */
	private String getOptionAnswer(Subject subject, int optionIndex) {
		String optionAnswer = "";
		switch (optionIndex) {
			case 0:
				optionAnswer = subject.getOption0();
				break;
			case 1:
				optionAnswer = subject.getOption1();
				break;
			case 2:
				optionAnswer = subject.getOption2();
				break;
			case 3:
				optionAnswer = subject.getOption3();
				break;
			case 4:
				optionAnswer = subject.getOption4();
				break;
			case 5:
				optionAnswer = subject.getOption5();
				break;
			case 6:
				optionAnswer = subject.getOption6();
				break;
			case 7:
				optionAnswer = subject.getOption7();
				break;
			case 8:
				optionAnswer = subject.getOption8();
				break;
			case 9:
				optionAnswer = subject.getOption9();
				break;
			default:
				break;
		}
		return optionAnswer;
	}
}
