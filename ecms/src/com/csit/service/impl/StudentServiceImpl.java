package com.csit.service.impl;

import java.io.File;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.csit.dao.MessageConfigDAO;
import com.csit.dao.NationDAO;
import com.csit.dao.PictureDAO;
import com.csit.dao.ProvinceDAO;
import com.csit.dao.StudentDAO;
import com.csit.dao.StudentFamilyMemberDAO;
import com.csit.dao.SystemConfigDAO;
import com.csit.dao.ValidateInfoDAO;
import com.csit.model.MessageConfig;
import com.csit.model.Nation;
import com.csit.model.Picture;
import com.csit.model.Province;
import com.csit.model.Student;
import com.csit.model.StudentFamilyMember;
import com.csit.model.SystemConfig;
import com.csit.model.ValidateInfo;
import com.csit.service.MailsHistoryService;
import com.csit.service.StudentService;
import com.csit.util.FileUtil;
import com.csit.util.JSONUtil;
import com.csit.util.MD5Util;
import com.csit.util.StringUtil;
import com.csit.vo.ServiceResult;
/**
 * @Description:学生Service实现类
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-4-19
 * @Author lys
 */
@Service
public class StudentServiceImpl extends BaseServiceImpl<Student, Integer> implements StudentService {
	
	@Resource
	private StudentDAO studentDAO;
	@Resource
	private PictureDAO pictureDAO;
	@Resource
	private StudentFamilyMemberDAO studentFamilyMemberDAO;
	@Resource
	private NationDAO nationDAO;
	@Resource
	private ProvinceDAO provinceDAO;
	@Resource
	private SystemConfigDAO systemConfigDAO;
	@Resource
	private MailsHistoryService mailsHistoryService;
	@Resource
	private ValidateInfoDAO validateInfoDAO;
	@Resource
	private MessageConfigDAO messageConfigDAO;
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.StudentService#query(com.csit.model.Student, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public String query(Student model, Integer page, Integer rows) {
		List<Student> list = studentDAO.query(model,page,rows);
		Long total = studentDAO.getTotalCount(model);
		String[] properties = {"studentId","studentName","userCode","status","nation.nationId",
				"nation.nationName","area.areaId","area.areaName","area.city.cityId:cityId",
				"area.city.cityName","area.province.provinceId:provinceId","area.province.provinceName",
				"birthday","idNumber","phone","mobilePhone","sex","email","address",
				"picture.pictureId","schoolGradeClazz.schoolGrade.school.schoolId:schoolId","schoolGradeClazz.schoolGrade.school.schoolName:schoolName",
				"schoolGradeClazz.schoolGrade.schoolGradeId:schoolGradeId","schoolGradeClazz.schoolGradeClazzId:schoolGradeClazzId",
				"schoolGradeClazz.schoolGrade.grade.gradeName:gradeName","schoolGradeClazz.clazz.clazzName:clazzName"};
		return JSONUtil.toJson(list,properties,total);
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.StudentService#getTotalCount(com.csit.model.Student)
	 */
	@Override
	public ServiceResult getTotalCount(Student model) {
		ServiceResult result = new ServiceResult(false);
		Long data = studentDAO.getTotalCount(model);
		result.addData("total", data);
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.StudentService#save(com.csit.model.Student, java.lang.String, java.lang.String)
	 */
	@Override
	public ServiceResult save(Student model, String familyMemberInfo, String kindSave,String baseWebPath) {
		ServiceResult result = new ServiceResult(false);
		if(StringUtils.isEmpty(kindSave)){
			result.setMessage("请选择保存学生信息类型");
			return result;
		}
		if(model==null){
			result.setMessage("请填写学生信息");
			return result;
		}
		if(StringUtils.isEmpty(model.getStudentName())){
			result.setMessage("请填写学生姓名");
			return result;
		}
		if(model.getSex()==null){
			result.setMessage("请选择性别");
			return result;
		}
		if(model.getBirthday()==null){
			result.setMessage("请选择出生日期");
			return result;
		}
		if(model.getNation()==null||model.getNation().getNationId()==null){
			result.setMessage("请选择民族");
			return result;
		}
		if("mobile".equals(kindSave)){
			if(StringUtils.isEmpty(model.getMobilePhone())){
				result.setMessage("请填写手机号码");
				return result;
			}
		}else if("email".equals(kindSave)){
			if(StringUtils.isEmpty(model.getEmail())){
				result.setMessage("请填写E-Mail");
				return result;
			}
		}
		if(model.getArea()==null||model.getArea().getAreaId()==null){
			result.setMessage("请选择所在地区");
			return result;
		}
		if(model.getSchoolGradeClazz()==null||model.getSchoolGradeClazz().getSchoolGradeClazzId()==null){
			result.setMessage("请选择所在班级");
			return result;
		}
		if(StringUtils.isEmpty(model.getAddress())){
			result.setMessage("请填写家庭地址");
			return result;
		}
		//判断编号是否已存在
		Student oldStudent = studentDAO.load("userCode", model.getUserCode());
		
		if(model.getStudentId()==null){//新增
			if(oldStudent!=null&&oldStudent.getStudentId()!=null){
				result.setMessage("登录名已注册");
				return result;
			}
			Student oldStudentByMobilePhone = null;
			if(StringUtils.isNotEmpty(model.getMobilePhone())){
				oldStudentByMobilePhone = studentDAO.load("mobilePhone", model.getMobilePhone());
				if(oldStudentByMobilePhone!=null&&StringUtils.isNotEmpty(oldStudentByMobilePhone.getMobilePhone())){
					result.setMessage("手机号码已注册");
					return result;
				}
			}
			Student oldStudentByEmail = null;
			if(StringUtils.isNotEmpty(model.getEmail())){
				oldStudentByEmail = studentDAO.load("email", model.getEmail());
				if(oldStudentByEmail!=null&&StringUtils.isNotEmpty(oldStudentByEmail.getEmail())){
					result.setMessage("邮箱已注册");
					return result;
				}
			}
			if(model.getPicture()!=null){
				pictureDAO.save(model.getPicture());
			}
			
			model.setUserPwd(MD5Util.getMD5(model.getUserPwd()));
			if("free".equals(kindSave)||"freeIn".equals(kindSave)){
				model.setStatus(1);
			}else if("mobile".equals(kindSave)){
				model.setStatus(1);
			}else if("email".equals(kindSave)){
				model.setStatus(0);
			}else if ("saveByAdmin".equals(kindSave)) {
				model.setStatus(1);
			}
			studentDAO.save(model);
			
			String[] familyMember =StringUtil.split(familyMemberInfo);
			StudentFamilyMember studentFamilyMember = new StudentFamilyMember();
			studentFamilyMember.setFamilyMember(familyMember[0]);
			studentFamilyMember.setFamilyMemberName(familyMember[1]);
			if( !("-1".equals(familyMember[2])) ){
				studentFamilyMember.setFamilyMemberAge(Integer.parseInt(familyMember[2]));
			}
			if( !("-1".equals(familyMember[3])) ){
				studentFamilyMember.setWorkUnitsAndPosition(familyMember[3]);
			}
			studentFamilyMember.setPhone(familyMember[4]);
			if( !("-1".equals(familyMember[5])) ){
				studentFamilyMember.setEmail(familyMember[5]);
			}
			studentFamilyMember.setStudent(model);
			studentFamilyMemberDAO.save(studentFamilyMember);
			//如果是邮箱注册，需要登录到邮箱启用
			if("email".equals(kindSave)){
				SystemConfig systemConfig = systemConfigDAO.load(1);
				String companyName = systemConfig.getCompanyName();
				if(StringUtils.isEmpty(companyName)){
					companyName="";
				}
				//取得开关的配置头尾信息
				MessageConfig oldMessageConfig = messageConfigDAO.load(22);
				String msgHead = oldMessageConfig.getHead();
				if(StringUtils.isEmpty(msgHead)){
					msgHead="";
				}
				String msgTail = oldMessageConfig.getTail();
				if(StringUtils.isEmpty(msgTail)){
					msgTail="";
				}
				
				StringBuilder sb = new StringBuilder();
				sb.append(msgHead+"亲爱的 ： "+model.getEmail()+"<br>");
				sb.append("感谢您申请"+companyName+"注册报名系统！请点击链接完成注册（该链接在"+systemConfig.getRegisterEmailExpireTime()+"分钟内有效） ");
				String uuid = UUID.randomUUID().toString();
				String href = baseWebPath+"activeStu.do?validateInfoId="+uuid;
				sb.append("<a href=\""+href+"\">"+href+"</a><br>");
				
				sb.append("如果您没有申请注册"+companyName+"报名系统，请忽略此邮件"+msgTail);
				mailsHistoryService.send(model.getEmail(), "注册验证邮件", sb.toString(),2);
				//保存验证信息
				ValidateInfo valdateInfo = new ValidateInfo();
				valdateInfo.setValidateInfoId(uuid);
				valdateInfo.setUserId(model.getStudentId());
				valdateInfo.setCreateTime(new Timestamp(Calendar.getInstance().getTimeInMillis()));
				validateInfoDAO.save(valdateInfo);
			}if("mobile".equals(kindSave)||"free".equals(kindSave)){
				result.addData("studentId", model.getStudentId());
				result.addData("studentName", model.getStudentName());
			}
			
		}else{
			Student oldModel = studentDAO.load(model.getStudentId());
			if(oldModel == null) {
				result.setMessage("该学生已不存在");
				return result;
			}
			Student oldModel2 = studentDAO.load("userCode",model.getUserCode());
			if (oldModel2 != null && oldModel2.getStudentId().intValue() != model.getStudentId().intValue()) {
				result.setMessage("该用户名已存在");
				return result;
			}
			Student oldModel3 = studentDAO.load("mobilePhone",model.getMobilePhone());
			if (oldModel3 != null && oldModel3.getStudentId().intValue() != model.getStudentId().intValue()) {
				result.setMessage("该手机号已存在");
				return result;
			}
			if(model.getEmail()!=null){
				Student oldModel4 = studentDAO.load("email",model.getEmail());
				if (oldModel4 != null && oldModel4.getStudentId().intValue() != model.getStudentId().intValue()) {
					result.setMessage("该邮箱已存在");
					return result;
				}
				oldModel.setEmail(model.getEmail());
			}
			oldModel.setMobilePhone(model.getMobilePhone());
			oldModel.setPhone(model.getPhone());
			oldModel.setBirthday(model.getBirthday());
			oldModel.setSex(model.getSex());
			oldModel.setStudentName(model.getStudentName());
			oldModel.setIdNumber(model.getIdNumber());
			oldModel.setNation(model.getNation());
			oldModel.setArea(model.getArea());
			oldModel.setAddress(model.getAddress());
			oldModel.setSchoolGradeClazz(model.getSchoolGradeClazz());
			oldModel.setUserCode(model.getUserCode());
			if(model.getUserPwd()!=null){
				oldModel.setUserPwd(MD5Util.getMD5(model.getUserPwd()));
			}
			
			String[] familyMember =StringUtil.split(familyMemberInfo);
			StudentFamilyMember oldFamilyMember = studentFamilyMemberDAO.load("student",model);
			oldFamilyMember.setFamilyMember(familyMember[0]);
			oldFamilyMember.setFamilyMemberName(familyMember[1]);
			if( !("-1".equals(familyMember[2])) ){
				oldFamilyMember.setFamilyMemberAge(Integer.parseInt(familyMember[2]));
			}else{
				oldFamilyMember.setFamilyMemberAge(null);
			}
			if( !("-1".equals(familyMember[3])) ){
				oldFamilyMember.setWorkUnitsAndPosition(familyMember[3]);
			}else{
				oldFamilyMember.setWorkUnitsAndPosition(null);
			}
			oldFamilyMember.setPhone(familyMember[4]);
			if( !("-1".equals(familyMember[5])) ){
				oldFamilyMember.setEmail(familyMember[5]);
			}else{
				oldFamilyMember.setEmail(null);
			}
		}
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.StudentService#mulDelete(java.lang.String)
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
				studentDAO.delete(Integer.parseInt(id));
				haveDelete = true;
			}
		}
		if(!haveDelete){
			result.setMessage("没有可删除的学生");
			return result;
		}
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.StudentService#mulUpdateState(java.lang.String, com.csit.model.Student, java.lang.Integer)
	 */
	@Override
	public ServiceResult mulUpdateState(String ids, Student model) {
		ServiceResult result = new ServiceResult(false);
		if(StringUtils.isEmpty(ids)){
			result.setMessage("请选择要修改状态的学生");
			return result;
		}
		String[] idArray =StringUtil.split(ids);
		if(idArray.length==0){
			result.setMessage("请选择要修改状态的学生");
			return result;
		}
		if(model==null||model.getStatus()==null){
			result.setMessage("请选择要修改成的状态");
			return result;
		}
		boolean haveUpdateShzt = false;
		
		for (String id : idArray) {
			Student oldStudent = studentDAO.load(Integer.parseInt(id));
			if(oldStudent!=null&&oldStudent.getStatus()!=model.getStatus()){
				oldStudent.setStatus(model.getStatus());
				
				haveUpdateShzt = true;
			}
		}
		if(!haveUpdateShzt){
			result.setMessage("没有可修改状态的学生");
			return result;
		}
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.StudentService#getPhoto(java.lang.Integer, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public ServiceResult getPicture(Integer studentId){
		ServiceResult result = new ServiceResult(false);
		Student student = studentDAO.load(studentId);
		String path = null;
		//输出图片
		if(student!=null&&student.getPicture()!=null&&
				student.getPicture().getPictureName()!=null&&
				student.getPicture().getPicturePath()!=null){
			path = student.getPicture().getPicturePath()+File.separator+
			student.getPicture().getPictureName();
			
		}
		
		result.addData("picturePath", path);
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.StudentService#login(java.lang.String, java.lang.String)
	 */
	@Override
	public Student login(String userCode, String userPwd) {
		String[] propertyNames = {"userCode","userPwd","status"};
		Object[] values = {userCode,userPwd,1};
		return studentDAO.load(propertyNames, values);
	}

	@Override
	public ServiceResult uploadPicture(Integer studentId,String picturePath,String uploadFileName,File upload) {
		ServiceResult result = new ServiceResult(false);
		
		Student student = studentDAO.load(studentId);
		
		//扩展名
		String extention = FilenameUtils.getExtension(uploadFileName);	
		if(!"jpg".equalsIgnoreCase(extention)||"png".equalsIgnoreCase(extention)||"jpeg".equalsIgnoreCase(extention)){
			result.setMessage("上传文件格式错误");
			return result;
		}
		//创建文件夹
		File dirs = new File(picturePath);
		if(!dirs.exists()){
			dirs.mkdirs();
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String today = sdf.format(new Date());
		//文件名
		String pictureName = student.getUserCode()+today+"."+extention;
		//文件路径
		String targetPath=picturePath+File.separator+pictureName;
		//创建文件
		File imageFile = new File(targetPath);
		//保存文件
		FileUtil.saveFile(upload, imageFile);
		
		if(student.getPicture()==null||student.getPicture().getPictureId()==null){
			Picture picture = new Picture();
			picture.setPictureName(pictureName);
			picture.setPicturePath("upload/image/studentPicture");
			pictureDAO.save(picture);
			student.setPicture(picture);
		}else{
			Picture oldPicture = pictureDAO.load(student.getPicture().getPictureId());
			oldPicture.setPictureName(pictureName);
			oldPicture.setPicturePath("upload/image/studentPicture");
		}
		
		result.setIsSuccess(true);
		return result;
	}
	
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.StudentService#init(java.lang.Integer)
	 */
	@Override
	public Map<String, Object> init(Integer studentId) {
		Map<String, Object> map = new HashMap<String, Object>();
		Student student = studentDAO.load(studentId);
		List<Nation> nationList = nationDAO.queryCombobox();
		List<Province> provinceList = provinceDAO.queryCombobox();
		List<StudentFamilyMember> familyMemberList = studentFamilyMemberDAO.query("student", student);
		map.put("student", student);
		map.put("nationList", nationList);
		map.put("provinceList", provinceList);
		map.put("familyMemberList", familyMemberList);
		return map;
	}

	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.StudentService#updatePwd(java.lang.String, java.lang.String)
	 */
	@Override
	public ServiceResult resetPwd(Integer studentId,String userPwd) {
		ServiceResult result = new ServiceResult(false);
		Student student = studentDAO.load("studentId",studentId);
		if(student==null){
			result.setMessage("登录名不存在");
		}else{
			student.setUserPwd(MD5Util.getMD5(userPwd));
		}
		result.setIsSuccess(true);
		return result;
	}
}