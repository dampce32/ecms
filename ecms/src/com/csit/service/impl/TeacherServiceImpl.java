package com.csit.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.csit.dao.InformationDAO;
import com.csit.dao.StadiumDAO;
import com.csit.dao.StudentCompetitionGroupDAO;
import com.csit.dao.TeacherDAO;
import com.csit.dao.TrainingClassDAO;
import com.csit.model.Competition;
import com.csit.model.CompetitionGroup;
import com.csit.model.Information;
import com.csit.model.Right;
import com.csit.model.Stadium;
import com.csit.model.StudentCompetitionGroup;
import com.csit.model.Teacher;
import com.csit.model.TrainingClass;
import com.csit.service.TeacherService;
import com.csit.util.JSONUtil;
import com.csit.util.MD5Util;
import com.csit.util.StringUtil;
import com.csit.util.TreeBaseUtil;
import com.csit.util.TreeUtil;
import com.csit.vo.GobelConstants;
import com.csit.vo.ServiceResult;
import com.csit.vo.TreeNode;
/**
 * @Description:教师Service实现类
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-4-16
 * @Author lys
 */
@Service
public class TeacherServiceImpl extends BaseServiceImpl<Teacher, Integer> implements TeacherService{
	@Resource
	private TeacherDAO teacherDAO;
	@Resource
	private InformationDAO informationDAO;
	@Resource
	private StudentCompetitionGroupDAO studentCompetitionGroupDAO;
	@Resource
	private StadiumDAO stadiumDAO;
	@Resource
	private TrainingClassDAO trainingClassDAO;
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.TeacherService#login(java.lang.String, java.lang.String)
	 */
	@Override
	public Teacher login(String teacherCode, String teacherPwd) {
		return teacherDAO.login(teacherCode, teacherPwd);
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.TeacherService#save(com.csit.model.Teacher)
	 */
	@Override
	public ServiceResult save(Teacher model) {
		ServiceResult result = new ServiceResult(false);
		if(model==null){
			result.setMessage("请填写用户信息");
			return result;
		}
		if(StringUtils.isEmpty(model.getTeacherName())){
			result.setMessage("请填写用户名称");
			return result;
		}
		if(StringUtils.isEmpty(model.getTeacherCode())){
			result.setMessage("请填写登录名称");
			return result;
		}
		if(model.getTeacherId()==null){//新增
			Teacher oldModel = teacherDAO.load("teacherCode", model.getTeacherCode());
			if(oldModel!=null){
				result.setMessage("该教师代码已存在，请换个教师代码");
				return result;
			}
			String passwords = MD5Util.getMD5(GobelConstants.DEFAULT_TEACHCER_PWD);
			model.setPasswords(passwords);
			teacherDAO.save(model);
		}else{
			Teacher oldModel = teacherDAO.load("teacherCode", model.getTeacherCode());
			if(oldModel!=null&&oldModel.getTeacherId().intValue()!=model.getTeacherId().intValue()){
				result.setMessage("该教师代码已存在，请换个教师代码");
				return result;
			}
			if(oldModel!=null&&oldModel.getTeacherId().intValue()!=model.getTeacherId().intValue()){
				oldModel.setTeacherCode(model.getTeacherCode());
				oldModel.setTeacherName(model.getTeacherName());
				oldModel.setState(model.getState());
				oldModel.setNote(model.getNote());
			}else{
				oldModel = teacherDAO.load(model.getTeacherId());
				oldModel.setTeacherCode(model.getTeacherCode());
				oldModel.setTeacherName(model.getTeacherName());
				oldModel.setState(model.getState());
				oldModel.setNote(model.getNote());
			}
		}
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.TeacherService#query(java.lang.Integer, java.lang.Integer, com.csit.model.Teacher)
	 */
	@Override
	public String query(Integer page, Integer rows, Teacher model) {
		List<Teacher> list = teacherDAO.query(page,rows,model);
		Long total = teacherDAO.count(model);
		String[] properties = {"teacherId","teacherName","teacherCode","state","note"};
		String ajaxString = JSONUtil.toJson(list, properties, total);
		return ajaxString;
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.TeacherService#mulDelete(java.lang.String)
	 */
	@Override
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
		for (String id : idArray) {
			if(StringUtils.isNotEmpty(idArray[0])){
				teacherDAO.delete(Integer.parseInt(id));
			}
		}
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.TeacherService#mulUpdateState(java.lang.String, com.csit.model.Teacher)
	 */
	@Override
	public ServiceResult mulUpdateState(String ids, Teacher model) {
		ServiceResult result = new ServiceResult(false);
		if(StringUtils.isEmpty(ids)){
			result.setMessage("请选择要修改状态的权限");
			return result;
		}
		String[] idArray =StringUtil.split(ids);
		if(idArray.length==0){
			result.setMessage("请选择要修改状态的权限");
			return result;
		}
		if(model==null||model.getState()==null){
			result.setMessage("请选择要修改成的状态");
			return result;
		}
		boolean haveUpdateShzt = false;
		for (String id : idArray) {
			Teacher oldTeacher = teacherDAO.load(Integer.parseInt(id));
			if(oldTeacher!=null&&oldTeacher.getState()!=model.getState()){
				oldTeacher.setState(model.getState());
				haveUpdateShzt = true;
			}
		}
		if(!haveUpdateShzt){
			result.setMessage("没有可修改状态的权限");
			return result;
		}
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.TeacherService#queryRootRight(com.csit.model.Teacher)
	 */
	@Override
	public String queryRootRight(Teacher model) {
		String result = "[]";
		if(model!=null&&model.getTeacherId()!=null){
			List<Map<String,Object>> rootList = teacherDAO.getRootRight(model.getTeacherId());
			List<Right> rootRightList = toRightList(rootList);
			List<TreeNode> rootTreeNodeList = TreeUtil.toTreeNodeList(rootRightList);
			if(rootRightList!=null){
				for (int i =0;i<rootRightList.size();i++) {
					Right rootRight = rootRightList.get(i);
					if(!rootRight.getIsLeaf()){
						List<Map<String,Object>> children = teacherDAO.getChildrenRight(model.getTeacherId(),rootRight.getRightId());
						List<Right> childrenRightList = toRightList(children);
						List<TreeNode> childrenTreeNodeList = TreeUtil.toTreeNodeList(childrenRightList);
						rootTreeNodeList.get(i).setChildren(childrenTreeNodeList);
					}
				}
			}
			result = TreeBaseUtil.toJSON(rootTreeNodeList);
		}
		
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.TeacherService#queryChildrenRight(com.csit.model.Teacher, java.lang.String)
	 */
	@Override
	public String queryChildrenRight(Teacher model, String rightId) {
		String result = "[]";
		if(model!=null&&model.getTeacherId()!=null){
			List<Map<String,Object>> children = teacherDAO.getChildrenRight(model.getTeacherId(),rightId);
			List<Right> childrenRightList = toRightList(children);
			result = TreeUtil.toJSONRightList(childrenRightList);
		}
		return result;
	}
	
	/**
	 * @Description: 将ListMap<String,Object>转化成List<Right>
	 * @Create: 2012-10-29 上午12:20:39
	 * @author lys
	 * @update logs
	 * @param list
	 * @return
	 * @throws Exception
	 */
	private List<Right> toRightList(List<Map<String,Object>> list){
		if(list==null){
			return null;
		}
		List<Right> rightList = new ArrayList<Right>();
		for (Map<String, Object> map : list) {
			Right right = toRight(map);
			if(right!=null){
				rightList.add(right);
			}
		}
		return rightList;
	}
	
	/**
	 * @Description:  将Map装化成Right
	 * @Create: 2012-10-29 上午12:10:38
	 * @author lys
	 * @update logs
	 * @param treeNodeMap
	 * @return
	 * @throws Exception
	 */
	private Right toRight(Map<String,Object> treeNodeMap){
		if(treeNodeMap==null){
			return null;
		}
		Right right = new Right();
		right.setRightId(treeNodeMap.get("rightId").toString());
		right.setRightName(treeNodeMap.get("rightName").toString());
		if(treeNodeMap.get("rightUrl")!=null&&StringUtils.isNotEmpty(treeNodeMap.get("rightUrl").toString())){
			right.setRightUrl(treeNodeMap.get("rightUrl").toString());
		}
		String isLeaf = treeNodeMap.get("isLeaf").toString();
		if("true".equals(isLeaf)||"1".equals(isLeaf)){
			right.setIsLeaf(new Boolean(true));
		}else{
			right.setIsLeaf(new Boolean(false));
		}
		
		String state = treeNodeMap.get("state").toString();
		if("1".equals(state)){
			right.setState(new Boolean(true));
		}else{
			right.setState(new Boolean(false));
		}
		return right;
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.TeacherService#getUrlRightAll(java.lang.Integer)
	 */
	@Override
	public String getUrlRightAll(Integer teacherId) {
		String result = "[]";
		List<Map<String,Object>> rootList = teacherDAO.getRootRight(teacherId); 
		if(rootList!=null&&rootList.size()!=0){
			String rightId = rootList.get(0).get("rightId").toString();
			//取得孩子节点
			List<Right> childrenRightList = getChildrenRight(teacherId,rightId,1);
			result = TreeUtil.toJSONRightList(childrenRightList);
		}
		return result;
	}
	
	/**
	 * @Description: 根据类型取得子权限
	 * @Create: 2013-1-29 上午10:37:00
	 * @author lys
	 * @update logs
	 * @param teacherId
	 * @param rightId
	 * @return
	 */
	private List<Right> getChildrenRight(Integer teacherId,String rightId,Integer kind){
		List<Map<String,Object>> children = teacherDAO.getChildrenRight(teacherId,rightId,kind);
		List<Right> childrenRightList = toRightList(children);
		return childrenRightList;
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.TeacherService#modifyPwd(com.csit.model.Teacher, java.lang.String)
	 */
	@Override
	public ServiceResult modifyPwd(Teacher model, String newTeacherPwd) {
		ServiceResult result = new ServiceResult(false);
		if(model==null||model.getTeacherId()==null){
			result.setMessage("对不起你还没登陆系统");
			return result;
		}
		if(StringUtils.isEmpty(model.getPasswords())){
			result.setMessage("请输入原密码");
			return result;
		}
		if(StringUtils.isEmpty(newTeacherPwd)){
			result.setMessage("请输入新密码");
			return result;
		}
		
		Teacher oldModel = teacherDAO.load(model.getTeacherId());
		String teacherPwdMD5  = MD5Util.getMD5(model.getPasswords());
		String oldTeacherPwd = oldModel.getPasswords();
		if(!oldTeacherPwd.equals(teacherPwdMD5)){
			result.setMessage("你输入的原密码不正确");
			return result;
		}
		String newTeacherPwdMD5 = MD5Util.getMD5(newTeacherPwd);
		oldModel.setPasswords(newTeacherPwdMD5);
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.TeacherService#getSelfInfor(java.lang.Integer)
	 */
	@Override
	public ServiceResult getSelfInfor(Integer teacherId) {
		ServiceResult result = new ServiceResult(false);
		if(teacherId==null){
			result.setMessage("对不起，您还没登录");
			return result;
		}
		Teacher oldModel = teacherDAO.load(teacherId);
		result.addData("teacherId", oldModel.getTeacherId());
		result.addData("teacherCode", oldModel.getTeacherCode());
		result.addData("teacherName", oldModel.getTeacherName());
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.TeacherService#updateSelfInfo(java.lang.Integer, com.csit.model.Teacher)
	 */
	@Override
	public ServiceResult updateSelfInfo(Integer teacherId, Teacher model) {
		ServiceResult result = new ServiceResult(false);
		if(model==null){
			result.setMessage("请填写个人信息");
			return result;
		}
		if(StringUtils.isEmpty(model.getTeacherCode())){
			result.setMessage("请填写教师代码");
			return result;
		}
		if(StringUtils.isEmpty(model.getTeacherName())){
			result.setMessage("请填写教师名称");
			return result;
		}
		Teacher oldModel = teacherDAO.load("teacherCode", model.getTeacherCode());
		if(oldModel!=null&&oldModel.getTeacherId().intValue()!=teacherId.intValue()){
			result.setMessage("该登录名已存在，请换个登录名");
			return result;
		}
		if(oldModel!=null&&oldModel.getTeacherId().intValue()==teacherId.intValue()){
			oldModel.setTeacherName(model.getTeacherName());
			teacherDAO.update(oldModel);
		}else{
			oldModel = teacherDAO.load(teacherId);
			oldModel.setTeacherCode(model.getTeacherCode());
			oldModel.setTeacherName(model.getTeacherName());
		}
		result.setIsSuccess(true);
		return result;
	}
	@Override
	public String queryCombobox() {
		
		List<Teacher> list = teacherDAO.query("state", true);
		String[] properties = {"teacherId","teacherName"};
		String jsonString = JSONUtil.toJsonWithoutRows(list,properties);
		return jsonString;
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.TeacherService#getRootUrlRight(java.lang.Integer)
	 */
	@Override
	public String getRootUrlRight(Integer teacherId) {
		String result = "[]";
		List<Map<String,Object>> rootList = teacherDAO.getRootRight(teacherId); 
		if(rootList!=null&&rootList.size()!=0){
			String rightId = rootList.get(0).get("rightId").toString();
			//取得孩子节点
			List<Right> childrenRightList = getChildrenRight(teacherId,rightId,1);
			result = TreeUtil.toJSONRightList(childrenRightList);
		}
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.TeacherService#getChildrenUrlRight(java.lang.Integer, java.lang.String)
	 */
	@Override
	public String getChildrenUrlRight(Integer teacherId, String rightId) {
		String result = "[]";
		//取得孩子节点
		List<Right> childrenRightList = getChildrenRight(teacherId,rightId,1);
		result = TreeUtil.toJSONRightList(childrenRightList);
		return result;
	}
	@Override
	public ServiceResult checkRight(Integer teacherId,String competitionId) {
		ServiceResult result = new ServiceResult(false);
		List<Map<String,Object>> list = teacherDAO.checkRight(teacherId);
		
		
		for(int i=0;i<list.size();i++){
			if(list.get(i).get("rightId").toString().equals("000040000")){//赛事公告
				Information information=new Information();
				information.setStatus(1);
				information.setCategory("大赛公告");
				if(competitionId!=null&&!"".equals(competitionId)){
					Competition competition=new Competition();
					competition.setCompetitionId(Integer.parseInt(competitionId));
					information.setCompetition(competition);
				}
				List<Information> noticeList=informationDAO.query(information, 1, 5);
				String[] properties = { "informationId", "informationTitle",
						"competition.competitionName", "status", "category" };
				String data = JSONUtil.toJson(noticeList, properties);
				result.addData("noticeList", data);
			}else if (list.get(i).get("rightId").toString().equals("000050010")) {
				StudentCompetitionGroup studentCompetitionGroup=new StudentCompetitionGroup();
				studentCompetitionGroup.setStatus(1);
				if(competitionId!=null&&!"".equals(competitionId)){
					Competition competition=new Competition();
					competition.setCompetitionId(Integer.parseInt(competitionId));
					CompetitionGroup competitionGroup=new CompetitionGroup();
					competitionGroup.setCompetition(competition);
					studentCompetitionGroup.setCompetitionGroup(competitionGroup);
				}
				List<StudentCompetitionGroup> studentCompetitionGroupList=studentCompetitionGroupDAO.query(studentCompetitionGroup, 1, 5);
				String[] properties = {"studentCompetitionGroupId","area.areaName","area.city.cityName",
						"area.province.provinceName","competitionGroup.competition.competitionName",
						"competitionGroup.group.groupName","student.studentName","status"};
				String data = JSONUtil.toJson(studentCompetitionGroupList, properties);
				result.addData("stuList", data);
			}else if (list.get(i).get("rightId").toString().equals("000060000")) {
				Stadium stadium =new Stadium();
				if(competitionId!=null&&!"".equals(competitionId)){
					Competition competition=new Competition();
					competition.setCompetitionId(Integer.parseInt(competitionId));
					CompetitionGroup competitionGroup=new CompetitionGroup();
					competitionGroup.setCompetition(competition);
					stadium.setCompetitionGroup(competitionGroup);
				}
				List<Stadium> stadiumList=stadiumDAO.query(stadium, 1, 5);
				String[] properties = { "stadiumId", "stadiumName","stadiumAddr", "competitionDate","note","limit","arrangeNo","competitionGroup.competition.competitionName:competitionName",
						"competitionGroup.group.groupName:groupName","competitionGroup.competition.competitionId:competitionId","competitionGroup.competitionGroupId"};
				String data = JSONUtil.toJson(stadiumList, properties);
				result.addData("stadiumList", data);
			}else if (list.get(i).get("rightId").toString().equals("000080010")) {
				TrainingClass trainingClass=new TrainingClass();
				trainingClass.setStatus(1);
				if(competitionId!=null&&!"".equals(competitionId)){
					Competition competition=new Competition();
					competition.setCompetitionId(Integer.parseInt(competitionId));
					CompetitionGroup competitionGroup=new CompetitionGroup();
					competitionGroup.setCompetition(competition);
					trainingClass.setCompetitionGroup(competitionGroup);
				}
				List<TrainingClass> trainingClasseList=trainingClassDAO.query(trainingClass, 1, 5);
				String[] properties = { "competitionGroup.competitionGroupId","goods.goodsId","trainingClassId","address",
						"trainingClassName", "competitionGroup.competition.competitionName:competitionName",
						"competitionGroup.competition.competitionId:competitionId","status","competitionGroup.group.groupId:groupId",
						"competitionGroup.group.groupName:groupName","address","classDate","classTeacher",
						"goods.goodsName", "goods.sellingPrice", "fee", "stuCount","limit","note"};
				String data = JSONUtil.toJson(trainingClasseList, properties);
				result.addData("trainingClassList", data);
			}
		}
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.TeacherService#mulResetPwd(java.lang.String)
	 */
	@Override
	public ServiceResult mulResetPwd(String ids) {
		ServiceResult result = new ServiceResult(false);
		if(StringUtils.isEmpty(ids)){
			result.setMessage("请选择要重置密码的记录");
			return result;
		}
		String[] idArray = StringUtil.split(ids);
		if(idArray.length==0){
			result.setMessage("请选择要重置密码的记录");
			return result;
		}
		for (String id : idArray) {
			if(StringUtils.isNotEmpty(idArray[0])){
				Teacher oldTeacher = teacherDAO.load(Integer.parseInt(id));
				String passwords = MD5Util.getMD5(GobelConstants.DEFAULT_TEACHCER_PWD);
				oldTeacher.setPasswords(passwords);
			}
		}
		result.setIsSuccess(true);
		return result;
	}

}
