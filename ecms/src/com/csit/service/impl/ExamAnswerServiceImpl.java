package com.csit.service.impl;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.csit.dao.ExamAnswerBigDAO;
import com.csit.dao.ExamAnswerBigSmallDAO;
import com.csit.dao.ExamAnswerBigSmallSubDAO;
import com.csit.dao.ExamAnswerDAO;
import com.csit.dao.GroupDAO;
import com.csit.dao.PaperDAO;
import com.csit.dao.SubjectDAO;
import com.csit.model.ExamAnswer;
import com.csit.model.ExamAnswerBig;
import com.csit.model.ExamAnswerBigSmall;
import com.csit.model.ExamAnswerBigSmallId;
import com.csit.model.ExamAnswerBigSmallSub;
import com.csit.model.ExamAnswerBigSmallSubId;
import com.csit.model.ExamAnswerId;
import com.csit.model.Group;
import com.csit.model.Paper;
import com.csit.model.Subject;
import com.csit.service.ExamAnswerService;
import com.csit.util.CommonUtil;
import com.csit.util.DateUtil;
import com.csit.util.StringUtil;
import com.csit.vo.ServiceResult;

/**
 * @Description: 答卷Service实现类
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-8
 * @Author lys
 */
@Service
public class ExamAnswerServiceImpl extends
		BaseServiceImpl<ExamAnswer, ExamAnswerId> implements ExamAnswerService {
	@Resource
	private ExamAnswerDAO examAnswerDAO;
	@Resource
	private GroupDAO groupDAO;
	@Resource
	private PaperDAO paperDAO;
	@Resource
	private ExamAnswerBigDAO examAnswerBigDAO;
	@Resource
	private ExamAnswerBigSmallDAO examAnswerBigSmallDAO;
	@Resource
	private SubjectDAO subjectDAO;
	@Resource
	private ExamAnswerBigSmallSubDAO examAnswerBigSmallSubDAO;
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.ExamAnswerService#viewPaper(java.lang.String, java.lang.Integer)
	 */
	@Override
	public List<Map<String, Object>> viewPaper(String competitionId, Integer studentId) {
		if(StringUtils.isEmpty(competitionId)){
			return null;
		}
		List<Map<String, Object>> list = examAnswerDAO.viewPaper(Integer.parseInt(competitionId),studentId);
		return list;
	}
	/*
	 * 
	 */
	@Override
	public Map<String, Object> readyExam(String groupId, String paperId) {
		Group oldGroup = groupDAO.load(Integer.parseInt(groupId));
		Paper oldPaper = paperDAO.load(Integer.parseInt(paperId));
		Map<String, Object> readyExamData = new HashMap<String, Object>();
		readyExamData.put("groupName", oldGroup.getGroupName());
		readyExamData.put("paperName", oldPaper.getPaperName());
		readyExamData.put("limits", oldPaper.getLimits()/60);		
		return readyExamData;
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.ExamAnswerService#exam(java.lang.String, java.lang.String, java.lang.Integer, java.lang.String, java.lang.String)
	 */
	@Override
	public Map<String, Object> exam(String competitionId, String groupId,
			Integer studentId, String paperId, String studentCompetitionGroupId) {
		//生成答卷
		try {
			examAnswerDAO.buildAnswer(Integer.parseInt(competitionId),Integer.parseInt(groupId),studentId
					,Integer.parseInt(paperId),Integer.parseInt(studentCompetitionGroupId));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			throw new RuntimeException();
		}
		Map<String, Object> examData = new HashMap<String, Object>();;
		//答卷操作的指导信息
		examData.put("competitionId", competitionId);
		examData.put("groupId", groupId);
		return examData;
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.ExamAnswerService#initExamPaper(com.csit.model.ExamAnswer, java.lang.Integer)
	 */
	@Override
	public ServiceResult initExamPaper(ExamAnswer model, Integer studentId) {
		ServiceResult result = new ServiceResult(false);
		if(model==null||model.getId()==null
			||model.getId().getCompetitionId()==null||model.getId().getGroupId()==null){
			result.setMessage("请选择要初始化的答卷");
			return result;
		}
		model.getId().setStudentId(studentId);
		
		ExamAnswer oldExamAnswer = examAnswerDAO.load(model.getId());
		Timestamp startDateTime = oldExamAnswer.getStartDateTime();
		Timestamp finishDateTime = oldExamAnswer.getFinishDateTime();
		Long distanceFinishLimits = null;
		if(startDateTime == null){//第一次初始化答卷
			/*计算考试结束时间
			 * 1.开始考试时间是当前时间，而结束时间是开始时间+考试时间
			 *  距离考试结束时间是 考试时间
			*/
			Long endTime = DateUtil.getNowTimestamp().getTime()+Long.parseLong(oldExamAnswer.getLimits()*1000+"");
			finishDateTime = new Timestamp(endTime);
			
			oldExamAnswer.setStartDateTime(DateUtil.getNowTimestamp());
			oldExamAnswer.setFinishDateTime(finishDateTime);
			
			distanceFinishLimits = oldExamAnswer.getLimits()*1000L;
			result.addData("distanceFinishLimits", distanceFinishLimits);
		}else{
			distanceFinishLimits = finishDateTime.getTime() -Calendar.getInstance().getTimeInMillis();
			if(distanceFinishLimits<=0){
				result.addData("isTimeOver", true);
				return result;
			}else{
				result.addData("distanceFinishLimits", distanceFinishLimits);
			}
		}
		result.addData("paperName", oldExamAnswer.getPaperName()+"(总分"+oldExamAnswer.getStandardScore()+")");
		result.addData("studentName", oldExamAnswer.getStudentName());
		result.addData("examCode", oldExamAnswer.getExamCode());
		//生成答题卡
		StringBuffer subjectTableHtml = new StringBuffer();
		
		List<ExamAnswerBig> examAnswerBigList =  examAnswerBigDAO.query(model);
		int sizeBig = examAnswerBigList.size();
		int smallArray = 0;
		int smallNo = 0;
		for (int i = 0; i < sizeBig; i++) {
			ExamAnswerBig examAnswerBig = examAnswerBigList.get(i);
			subjectTableHtml.append("<div class=\"bigItem\"> ");
			subjectTableHtml.append("<div class=\"bigName\" subjectType=\""+examAnswerBig.getSubjectType().trim()+"\" bigId=\""+examAnswerBig.getId().getBigId()+"\" bigName=\""+(CommonUtil.getBigNo(i+1))+" "+examAnswerBig.getBigName()+"\">"+(CommonUtil.getBigNo(i+1))+" "+examAnswerBig.getSubjectType()+"("+examAnswerBig.getStandardScore()+"分)"+"</div> ");
			//大题下的小题
			List<ExamAnswerBigSmall> smallList = examAnswerBigSmallDAO.query(examAnswerBig);
			int sizeSmall = smallList.size();
			subjectTableHtml.append("<div class=\"small\"> ");
			subjectTableHtml.append("	<table> ");
			for (int j = 0; j < sizeSmall; j++) {
				ExamAnswerBigSmall examAnswerBigSmall = smallList.get(j);
				Subject subject = subjectDAO.load(examAnswerBigSmall.getSubjectId());
				String subjectType = subject.getSubjectType().trim();
				
				if(j%6==0||("听力短文".equals(subjectType)&&j%3==0)||("阅读理解".equals(subjectType)&&j%3==0)
						||("填空".equals(subjectType)&&j%3==0)||("完型填空".equals(subjectType)&&j%3==0)){
					if(j!=0){
						subjectTableHtml.append("		</tr> ");
					}
					subjectTableHtml.append("		<tr> ");
				}
				String smallNoStr = (++smallNo)+"";
				
				String smallStatus = getSmallStatus(examAnswerBigSmall.getStatus());
				
				if("听力短文".equals(subjectType)||"填空".equals(subjectType)||"阅读理解".equals(subjectType)||"完型填空".equals(subjectType)){
					if(subject.getOptionCount()!=1){
						smallNoStr =smallNo+"-"+(smallNo+subject.getOptionCount()-1);
						smallNo = smallNo+subject.getOptionCount()-1;
					}
					
					subjectTableHtml.append("			<td class=\"mulTd "+smallStatus);
				}else{
					subjectTableHtml.append("			<td class=\"normalTd "+smallStatus);
				}
				subjectTableHtml.append("\"	smallStatus = \""+examAnswerBigSmall.getStatus()+"\" bigId = \""+examAnswerBigSmall.getId().getBigId()+"\" smallId = \""+examAnswerBigSmall.getId().getSmallId()+"\" standardScore=\"("+examAnswerBigSmall.getStandardScore()+"分)"+"\" smallArray =\""+(++smallArray)+"\"> " +
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
	/**
	 * @Description: 取得小题答题状态
	 * @Created Time: 2013-5-14 下午8:11:45
	 * @Author lys
	 * @param status
	 * @return
	 */
	private String getSmallStatus(Integer status) {
		String smallStatus="unAnswer";
		if(status.intValue()==0){
			smallStatus="unAnswer";
		}else if(status.intValue()==1){
			smallStatus="partAnswered";
		}else if(status.intValue()==2){
			smallStatus="answered";
		}
		return smallStatus;
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.ExamAnswerService#getSmallStatus(java.lang.String, java.lang.String, java.lang.Integer, java.lang.String, java.lang.String)
	 */
	@Override
	public ServiceResult getSmallStatus(String competitionId, String groupId,
			Integer studentId, String bigId, String smallId) {
		ServiceResult result = new ServiceResult(false);
		if(StringUtils.isEmpty(competitionId)||StringUtils.isEmpty(groupId)){
			result.setMessage("请选择要答题的考试安排");
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
		
		ExamAnswerBigSmallId examAnswerBigSmallId = new ExamAnswerBigSmallId();
		examAnswerBigSmallId.setCompetitionId(Integer.parseInt(competitionId));
		examAnswerBigSmallId.setGroupId(Integer.parseInt(groupId));
		examAnswerBigSmallId.setBigId(Integer.parseInt(bigId));
		examAnswerBigSmallId.setSmallId(Integer.parseInt(smallId));
		examAnswerBigSmallId.setStudentId(studentId);
		
		ExamAnswerBigSmall examAnswerBigSmall = examAnswerBigSmallDAO.load(examAnswerBigSmallId);
		result.addData("smallStatus", examAnswerBigSmall.getStatus());
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.ExamAnswerService#initSmall(java.lang.String, java.lang.String, java.lang.Integer, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public ServiceResult initSmall(String competitionId, String groupId,
			Integer studentId, String bigId, String smallId, String smallNo) {
		ServiceResult result = new ServiceResult(false);
		if(StringUtils.isEmpty(competitionId)||StringUtils.isEmpty(groupId)){
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
		
		ExamAnswerBigSmallId examAnswerBigSmallId = new ExamAnswerBigSmallId();
		examAnswerBigSmallId.setCompetitionId(Integer.parseInt(competitionId));
		examAnswerBigSmallId.setGroupId(Integer.parseInt(groupId));
		examAnswerBigSmallId.setStudentId(studentId);
		examAnswerBigSmallId.setBigId(Integer.parseInt(bigId));
		examAnswerBigSmallId.setSmallId(Integer.parseInt(smallId));
		
		ExamAnswerBigSmall examAnswerBigSmall = examAnswerBigSmallDAO.load(examAnswerBigSmallId);
		String answer = examAnswerBigSmall.getAnswer();
		Subject subject = subjectDAO.load(examAnswerBigSmall.getSubjectId());
		String subjectType = subject.getSubjectType().trim();
		Integer optionCount =  subject.getOptionCount();
		String descript = subject.getDescript();
		
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
				String optionAnswer =getOptionAnswer(examAnswerBigSmall, i); 
				optionsHtml.append("<tr> ");
				optionsHtml.append("<td class=\"optionInput\" style=\"font-weight: bold;\">"+(beginSmallNo+i)+"</td> ");
				optionsHtml.append("<td> <input type=\"text\" class=\"fillAnswerInput optionInput\"  optionCount=\""+optionCount+"\" option=\"option"+i+"\" value=\""+optionAnswer+"\" postAnswer=\""+optionAnswer+"\"></td> ");
				optionsHtml.append("</tr> ");
			}
			optionsHtml.append("</table> ");
		}else if("阅读理解".equals(subjectType)){
			String[] smallNoArray =StringUtil.split(smallNo, "-");
			Integer beginSmallNo  = Integer.parseInt(smallNoArray[0].trim());
			
			List<ExamAnswerBigSmallSub> smallSubList = examAnswerBigSmallSubDAO.query(examAnswerBigSmall);
			int smallSubSize = smallSubList.size();
			for (int i = 0; i < smallSubSize; i++) {
				ExamAnswerBigSmallSub smallSub = smallSubList.get(i);
				String answerSub = smallSub.getAnswer();
				optionsHtml.append("<table class=\"readAnswerTable\" id=\"id_"+smallSub.getId().getSubId()+"\" postAnswer=\""+answerSub+"\"> ");
				optionsHtml.append("<tr> ");
				optionsHtml.append("	<td class=\"readSubjectSubDesc\">"+(beginSmallNo+i)+"."+smallSub.getDescript()+"</td> ");
				optionsHtml.append("</tr> ");
				
				//处理打乱
				String[] optionArraysArray = new String[4];
				optionArraysArray[0] = smallSub.getOption0array();
				optionArraysArray[1] = smallSub.getOption1array();
				optionArraysArray[2] = smallSub.getOption2array();
				optionArraysArray[3] = smallSub.getOption3array();
				
				String[] optionArraysTempArray = Arrays.copyOf(optionArraysArray, optionArraysArray.length);
				Arrays.sort(optionArraysTempArray);
				for (int j = 0; j < 4; j++) {
					int optionArrayNo = getOptionArrayNo(optionArraysArray,optionArraysTempArray[j]);
					
					optionsHtml.append("<tr> ");
					optionsHtml.append("	<td class=\"readTd\"> ");
					optionsHtml.append("		<label for=\""+smallSub.getId().getSmallId()+"_"+smallSub.getId().getSubId()+"_"+CommonUtil.getOptionChar(optionArrayNo)+"\"   class=\"labelOption optionInput\">  ");
					optionsHtml.append("			<input class=\"readSubjectSubAnswer\" id=\""+smallSub.getId().getSmallId()+"_"+smallSub.getId().getSubId()+"_"+CommonUtil.getOptionChar(optionArrayNo)+"\" name=\"answer_"+smallSub.getId().getSmallId()+"_"+smallSub.getId().getSubId()+"\"   subId=\""+smallSub.getId().getSubId()+"\" option=\""+CommonUtil.getOptionChar(optionArrayNo)+"\" type=\"radio\" ");
					if((CommonUtil.getOptionChar(optionArrayNo)+"").equals(smallSub.getAnswer())){
						optionsHtml.append(" checked=\"checked\" ");
					}
					optionsHtml.append("/> "+CommonUtil.getOptionChar(j)+": ");
					String optionDes = "";
					switch (optionArrayNo) {
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
			
			List<ExamAnswerBigSmallSub> smallSubList = examAnswerBigSmallSubDAO.query(examAnswerBigSmall);
			int smallSubSize = smallSubList.size();
			optionsHtml.append("<table> ");
			for (int i = 0; i < smallSubSize; i++) {
				ExamAnswerBigSmallSub smallSub = smallSubList.get(i);
				String postAnswer = "";
				if(StringUtils.isNotEmpty(smallSub.getAnswer())){
					postAnswer = smallSub.getAnswer();
				}
				optionsHtml.append("<tr postAnswer=\""+postAnswer+"\" subId=\""+smallSub.getId().getSubId()+"\"> ");
				optionsHtml.append("	<td  class=\"clozeTd optionInput\">"+(beginSmallNo+i)+".&nbsp;</td> ");
				//处理打乱
				String[] optionArraysArray = new String[4];
				optionArraysArray[0] = smallSub.getOption0array();
				optionArraysArray[1] = smallSub.getOption1array();
				optionArraysArray[2] = smallSub.getOption2array();
				optionArraysArray[3] = smallSub.getOption3array();
				String[] optionArraysTempArray = Arrays.copyOf(optionArraysArray, optionArraysArray.length);
				Arrays.sort(optionArraysTempArray);
				for (int j = 0; j < 4; j++) {
					int optionArrayNo = getOptionArrayNo(optionArraysArray,optionArraysTempArray[j]);
					String optionDes = "";
					switch (optionArrayNo) {
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
					optionsHtml.append("	<td class=\"clozeTd optionInput\"> ");
					optionsHtml.append("<input type=\"radio\" class=\"shortAnswerAnswer\" " +
							"option=\""+CommonUtil.getOptionChar(optionArrayNo)+"\"  " +
							"name=\"name_"+smallSub.getId().getSubId()+"\"  " +
							"id=\"id_"+smallSub.getId().getSubId()+"_"+CommonUtil.getOptionChar(j)+"\"");
					if((CommonUtil.getOptionChar(optionArrayNo)+"").equals(smallSub.getAnswer())){
						optionsHtml.append(" checked=\"checked\" ");
					}
					optionsHtml.append("\">");
					optionsHtml.append("<label for=\"id_"+smallSub.getId().getSubId()+"_"+CommonUtil.getOptionChar(j)+"\">"
							+CommonUtil.getOptionChar(j)+":&nbsp; "+optionDes+"&nbsp;</label>");
					optionsHtml.append("	</td> ");
				}
				optionsHtml.append("</tr> ");
			}
			optionsHtml.append("</table> ");
			result.addData("smallSubSize", smallSubSize);
		}else{
			//处理打乱
			String[] optionArraysArray = new String[optionCount];
			for (int i = 0; i < optionCount; i++) {
				switch (i) {
				case 0:
					optionArraysArray[i]=subject.getOption0();
					break;
				case 1:
					optionArraysArray[i]=subject.getOption1();
					break;
				case 2:
					optionArraysArray[i]=subject.getOption2();
					break;
				case 3:
					optionArraysArray[i]=subject.getOption3();
					break;
				case 4:
					optionArraysArray[i]=subject.getOption4();
					break;
				case 5:
					optionArraysArray[i]=subject.getOption5();
					break;
				case 6:
					optionArraysArray[i]=subject.getOption6();
					break;
				case 7:
					optionArraysArray[i]=subject.getOption7();
					break;
				case 8:
					optionArraysArray[i]=subject.getOption8();
					break;
				case 9:
					optionArraysArray[i]=subject.getOption9();
					break;
				default:
					break;
				}
			}
			String[] optionArraysTempArray = Arrays.copyOf(optionArraysArray, optionArraysArray.length);
			Arrays.sort(optionArraysTempArray);
			
			//如果学生已答题，则显示答题答案
			optionsHtml.append("<table id=\"postAnswer\" postAnswer=\""+answer+"\"> ");
			for (int i = 0; i < optionCount; i++) {
				int optionArrayNo = getOptionArrayNo(optionArraysArray,optionArraysTempArray[i]);
				optionsHtml.append("<tr> ");
				optionsHtml.append("	<td  class=\"radioTd optionInput\"> ");
				optionsHtml.append("<label for=\"id_"+CommonUtil.getOptionChar(optionArrayNo)+"\"> ");
				optionsHtml.append("<input class=\"answerRadio\" option =\""+CommonUtil.getOptionChar(optionArrayNo)+"\" id=\"id_"+CommonUtil.getOptionChar(optionArrayNo)+"\"  name=\"answerRadio_"+smallId+"\" ");
				if(subjectType.equals("单项选择")){
					optionsHtml.append(" type=\"radio\" ");
				}else if(subjectType.equals("多项选择")){
					optionsHtml.append(" type=\"checkbox\" ");
				}
				if(StringUtils.isNotEmpty(answer)){
					char[] answerArray = answer.toCharArray();
					for (int j = 0; j < answerArray.length; j++) {
						char option =CommonUtil.getOptionChar(optionArrayNo);
						if(option==answerArray[j]){
							optionsHtml.append(" checked=\"checked\" ");
							break;
						}
					}
				}
				optionsHtml.append("/>");
				
				optionsHtml.append(CommonUtil.getOptionChar(i)+":&nbsp;");
				switch (optionArrayNo) {
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
	private String getOptionAnswer(ExamAnswerBigSmall examAnswerBigSmall, int optionIndex) {
		String optionAnswer = "";
		switch (optionIndex) {
			case 0:
				optionAnswer = examAnswerBigSmall.getOption0();
				break;
			case 1:
				optionAnswer = examAnswerBigSmall.getOption1();
				break;
			case 2:
				optionAnswer = examAnswerBigSmall.getOption2();
				break;
			case 3:
				optionAnswer = examAnswerBigSmall.getOption3();
				break;
			case 4:
				optionAnswer = examAnswerBigSmall.getOption4();
				break;
			case 5:
				optionAnswer = examAnswerBigSmall.getOption5();
				break;
			case 6:
				optionAnswer = examAnswerBigSmall.getOption6();
				break;
			case 7:
				optionAnswer = examAnswerBigSmall.getOption7();
				break;
			case 8:
				optionAnswer = examAnswerBigSmall.getOption8();
				break;
			case 9:
				optionAnswer = examAnswerBigSmall.getOption9();
				break;
			default:
				break;
		}
		return optionAnswer;
	}
	/**
	 * @Description: 取得选项排序值
	 * @Created Time: 2013-5-14 下午10:26:30
	 * @Author lys
	 * @param array
	 * @param key
	 * @return
	 */
	private int getOptionArrayNo(Object[] array,Object key){
		for (int i = 0; i < array.length; i++) {
			if(key.equals(array[i])){
				return i;
			}
		}
		return -1;
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.ExamAnswerService#answerRead(java.lang.String, java.lang.String, java.lang.Integer, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public ServiceResult answerRead(String competitionId, String groupId,
			Integer studentId, String bigId, String smallId, String subId,
			String answer, String smallStatus) {
		ServiceResult result = new ServiceResult(false);
		if(StringUtils.isEmpty(competitionId)||StringUtils.isEmpty(groupId)){
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
		if(StringUtils.isEmpty(subId)){
			result.setMessage("请选择小题下的子小题");
			return result;
		}
		if(StringUtils.isEmpty(smallStatus)){
			result.setMessage("请指定小题状态");
			return result;
		}
		if(StringUtils.isEmpty(answer)){
			result.setMessage("请指定答案");
			return result;
		}
		
		ExamAnswerBigSmallSubId examAnswerBigSmallSubId = new ExamAnswerBigSmallSubId();
		examAnswerBigSmallSubId.setCompetitionId(Integer.parseInt(competitionId));
		examAnswerBigSmallSubId.setGroupId(Integer.parseInt(groupId));
		examAnswerBigSmallSubId.setBigId(Integer.parseInt(bigId));
		examAnswerBigSmallSubId.setSmallId(Integer.parseInt(smallId));
		examAnswerBigSmallSubId.setSubId(Integer.parseInt(subId));
		examAnswerBigSmallSubId.setStudentId(studentId);
		
		ExamAnswerBigSmallSub answerBigSmallSub = examAnswerBigSmallSubDAO.load(examAnswerBigSmallSubId);
		//如有原来的答题情况和现在的答题情况不一样，则需要改变子小题的答题情况，并更新小题的得分情况
		Double oldScore = 0.0;
		String oldAnswer = answerBigSmallSub.getAnswer()==null?null:answerBigSmallSub.getAnswer().trim();
		if(!answer.equals(oldAnswer)){//答案发生改变
			oldScore = answerBigSmallSub.getScore();
			if(answerBigSmallSub.getStandardAnswer().trim().equals(answer)){
				answerBigSmallSub.setScore(answerBigSmallSub.getStandardScore());
			}else{
				answerBigSmallSub.setScore(0.0);
			}
			answerBigSmallSub.setAnswer(answer);
			answerBigSmallSub.setStatus(2);
			//更新小题
			ExamAnswerBigSmallId examAnswerBigSmallId = new ExamAnswerBigSmallId();
			examAnswerBigSmallId.setCompetitionId(Integer.parseInt(competitionId));
			examAnswerBigSmallId.setGroupId(Integer.parseInt(groupId));
			examAnswerBigSmallId.setBigId(Integer.parseInt(bigId));
			examAnswerBigSmallId.setSmallId(Integer.parseInt(smallId));
			examAnswerBigSmallId.setStudentId(studentId);
			
			ExamAnswerBigSmall examAnswerBigSmall = examAnswerBigSmallDAO.load(examAnswerBigSmallId);
			examAnswerBigSmall.setScore(examAnswerBigSmall.getScore()-oldScore+answerBigSmallSub.getScore());
			examAnswerBigSmall.setStatus(Integer.parseInt(smallStatus));
		}
		
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.ExamAnswerService#answerFill(java.lang.String, java.lang.String, java.lang.Integer, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public ServiceResult answerFill(String competitionId, String groupId,
			Integer studentId, String bigId, String smallId, String option,
			String answer, String smallStatus) {
		ServiceResult result = new ServiceResult(false);
		if(StringUtils.isEmpty(competitionId)||StringUtils.isEmpty(groupId)){
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
		if(StringUtils.isEmpty(option)){
			result.setMessage("请指定填写的选项");
			return result;
		}
		if(StringUtils.isEmpty(smallStatus)){
			result.setMessage("请指定小题状态");
			return result;
		}
		
		ExamAnswerBigSmallId examAnswerBigSmallId = new ExamAnswerBigSmallId();
		examAnswerBigSmallId.setCompetitionId(Integer.parseInt(competitionId));
		examAnswerBigSmallId.setGroupId(Integer.parseInt(groupId));
		examAnswerBigSmallId.setBigId(Integer.parseInt(bigId));
		examAnswerBigSmallId.setSmallId(Integer.parseInt(smallId));
		examAnswerBigSmallId.setStudentId(studentId);
		
		ExamAnswerBigSmall examAnswerBigSmall = examAnswerBigSmallDAO.load(examAnswerBigSmallId);
		int optionIndex =Integer.parseInt(option.substring(6, option.length()));
		boolean isGoalScore = false;
		boolean isOldGoalScore = false;
		switch (optionIndex) {
			case 0:
				isOldGoalScore =  CommonUtil.isGoalScore(examAnswerBigSmall.getStandardOption0(), examAnswerBigSmall.getOption0());
				examAnswerBigSmall.setOption0(answer);
				isGoalScore = CommonUtil.isGoalScore(examAnswerBigSmall.getStandardOption0(), answer);
				break;
			case 1:
				isOldGoalScore =  CommonUtil.isGoalScore(examAnswerBigSmall.getStandardOption1(), examAnswerBigSmall.getOption1());
				examAnswerBigSmall.setOption1(answer);
				isGoalScore = CommonUtil.isGoalScore(examAnswerBigSmall.getStandardOption1(), answer);
				break;
			case 2:
				isOldGoalScore =  CommonUtil.isGoalScore(examAnswerBigSmall.getStandardOption2(), examAnswerBigSmall.getOption2());
				examAnswerBigSmall.setOption2(answer);
				isGoalScore = CommonUtil.isGoalScore(examAnswerBigSmall.getStandardOption2(), answer);
				break;
			case 3:
				isOldGoalScore =  CommonUtil.isGoalScore(examAnswerBigSmall.getStandardOption3(), examAnswerBigSmall.getOption3());
				examAnswerBigSmall.setOption3(answer);
				isGoalScore = CommonUtil.isGoalScore(examAnswerBigSmall.getStandardOption3(), answer);
				break;
			case 4:
				isOldGoalScore =  CommonUtil.isGoalScore(examAnswerBigSmall.getStandardOption4(), examAnswerBigSmall.getOption4());
				examAnswerBigSmall.setOption4(answer);
				isGoalScore = CommonUtil.isGoalScore(examAnswerBigSmall.getStandardOption4(), answer);
				break;
			case 5:
				isOldGoalScore =  CommonUtil.isGoalScore(examAnswerBigSmall.getStandardOption5(), examAnswerBigSmall.getOption5());
				examAnswerBigSmall.setOption5(answer);
				isGoalScore = CommonUtil.isGoalScore(examAnswerBigSmall.getStandardOption5(), answer);
				break;
			case 6:
				isOldGoalScore =  CommonUtil.isGoalScore(examAnswerBigSmall.getStandardOption6(), examAnswerBigSmall.getOption6());
				examAnswerBigSmall.setOption6(answer);
				isGoalScore = CommonUtil.isGoalScore(examAnswerBigSmall.getStandardOption6(), answer);
				break;
			case 7:
				isOldGoalScore =  CommonUtil.isGoalScore(examAnswerBigSmall.getStandardOption7(), examAnswerBigSmall.getOption7());
				examAnswerBigSmall.setOption7(answer);
				isGoalScore = CommonUtil.isGoalScore(examAnswerBigSmall.getStandardOption7(), answer);
				break;
			case 8:
				isOldGoalScore =  CommonUtil.isGoalScore(examAnswerBigSmall.getStandardOption8(), examAnswerBigSmall.getOption8());
				examAnswerBigSmall.setOption8(answer);
				isGoalScore = CommonUtil.isGoalScore(examAnswerBigSmall.getStandardOption8(), answer);
				break;
			case 9:
				isOldGoalScore =  CommonUtil.isGoalScore(examAnswerBigSmall.getStandardOption9(), examAnswerBigSmall.getOption9());
				examAnswerBigSmall.setOption9(answer);
				isGoalScore = CommonUtil.isGoalScore(examAnswerBigSmall.getStandardOption9(), answer);
				break;
			default:
				break;
		}
		Double perOptionScore = examAnswerBigSmall.getStandardScore()/examAnswerBigSmall.getOptionCount();
		Double otherScore = examAnswerBigSmall.getScore();
		if(isOldGoalScore){
			otherScore = examAnswerBigSmall.getScore()-perOptionScore;
			otherScore = otherScore<0?0:otherScore;
		}
		if(isGoalScore){
			otherScore += perOptionScore;
		}
		examAnswerBigSmall.setScore(otherScore<0?0:otherScore);
		examAnswerBigSmall.setStatus(Integer.parseInt(smallStatus));
		
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.ExamAnswerService#answerUniterm(java.lang.String, java.lang.String, java.lang.Integer, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public ServiceResult answerUniterm(String competitionId, String groupId,
			Integer studentId, String bigId, String smallId, String answer) {
		ServiceResult result = new ServiceResult(false);
		if(StringUtils.isEmpty(competitionId)||StringUtils.isEmpty(groupId)){
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
		if(StringUtils.isEmpty(answer)){
			result.setMessage("请选择答案");
			return result;
		}
		
		ExamAnswerBigSmallId examAnswerBigSmallId = new ExamAnswerBigSmallId();
		examAnswerBigSmallId.setCompetitionId(Integer.parseInt(competitionId));
		examAnswerBigSmallId.setGroupId(Integer.parseInt(groupId));
		examAnswerBigSmallId.setBigId(Integer.parseInt(bigId));
		examAnswerBigSmallId.setSmallId(Integer.parseInt(smallId));
		examAnswerBigSmallId.setStudentId(studentId);
		
		ExamAnswerBigSmall answerBigSmall = examAnswerBigSmallDAO.load(examAnswerBigSmallId);
		
		answerBigSmall.setAnswer(answer);
		if(answerBigSmall.getAnswer().equals(answerBigSmall.getStandardAnswer())){
			answerBigSmall.setScore(answerBigSmall.getStandardScore());
		}else{
			answerBigSmall.setScore(0.0);
		}
		answerBigSmall.setStatus(2);
		
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.ExamAnswerService#finishExamPaper(java.lang.String, java.lang.String, java.lang.Integer)
	 */
	@Override
	public ServiceResult finishExamPaper(String competitionId, String groupId,
			Integer studentId) {
		ServiceResult result = new ServiceResult(false);
		if(StringUtils.isEmpty(competitionId)||StringUtils.isEmpty(groupId)){
			result.setMessage("请选择答卷");
			return result;
		}
		ExamAnswerId examAnswerId = new ExamAnswerId();
		examAnswerId.setCompetitionId(Integer.parseInt(competitionId));
		examAnswerId.setGroupId(Integer.parseInt(groupId));
		examAnswerId.setStudentId(studentId);
		
		ExamAnswer examAnswer = new ExamAnswer();
		examAnswer.setId(examAnswerId);
		
		//统计得分
		Double totalScore = examAnswerDAO.countScore(examAnswer);
		ExamAnswer oldExamAnswer = examAnswerDAO.load(examAnswerId);
		oldExamAnswer.setScore(totalScore);
		oldExamAnswer.setStatus(1);
		result.setIsSuccess(true);
		return result;
	}
	@Override
	public Map<String, Object> init(Integer studentId) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<ExamAnswer> examAnswerList = examAnswerDAO.init(studentId);
		map.put("examAnswerList", examAnswerList);
		return map;
	}
}
