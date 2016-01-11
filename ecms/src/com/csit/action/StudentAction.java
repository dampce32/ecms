package com.csit.action;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.csit.dao.SystemConfigDAO;
import com.csit.model.Paper;
import com.csit.model.Picture;
import com.csit.model.Student;
import com.csit.model.SystemConfig;
import com.csit.service.StudentService;
import com.csit.util.FileUtil;
import com.csit.vo.ServiceResult;
import com.octo.captcha.service.CaptchaService;
import com.opensymphony.xwork2.ModelDriven;
/**
 * @Description:学生Action
 * @Copystudent: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-4-19
 * @Author lys
 */
@Controller
@Scope("prototype")
public class StudentAction extends BaseAction implements ModelDriven<Student> {

	private static final long serialVersionUID = -1156289632473110452L;
	private static final Logger logger = Logger.getLogger(StudentAction.class);
	private Student model = new Student();

	@Resource
	private StudentService studentService;
	@Resource
	private CaptchaService captchaService;
	@Resource
	SystemConfigDAO systemConfigDAO;
	private Paper paper;
	
	
	private File upload; //上传的文件
    private String uploadFileName; //文件名称
    private String uploadContentType; //文件类型
    
	public Student getModel() {
		return model;
	}
	
	/**
	 * @Description: 分页查询学生
	 * @Create: 2012-10-28 上午9:14:13
	 * @author lys
	 * @update logs
	 * @throws Exception
	 */
	public void query(){
		String jsonArray = studentService.query(model, page, rows);
		ajaxJson(jsonArray);
	}
	
	/**
	 * @Description: 保存学生
	 * @Create: 2013-1-22 上午10:33:19
	 * @author lys
	 * @update logs
	 */
	public void save(){
		ServiceResult result = new ServiceResult(false);
		String kindSave = getParameter("kindSave");
		String familyMemberInfo = getParameter("familyMemberInfo");
		String validateCode = getParameter("validateCode");
		
		String baseWebPath = getBaseWebPath();
		//验证验证码
		if("email".equals(kindSave)){
			String captchaID = request.getSession().getId();
			String challengeResponse = StringUtils.upperCase(validateCode);
			if (StringUtils.isEmpty(challengeResponse) || captchaService.validateResponseForID(captchaID, challengeResponse) == false) {
				result.setMessage("验证码错误");
				ajaxJson(result.toJSON());
				return;
			}
		}else if("mobile".equals(kindSave)){
			Object msgValidateCode = getSession("msgValidateCode");
			if(StringUtils.isNotEmpty(validateCode)&&msgValidateCode!=null&&
					!StringUtils.equals(msgValidateCode.toString(), validateCode)){
				result.setMessage("短信验证码错误");
				ajaxJson(result.toJSON());
				return;
			}
		}
		try {
			if(upload!=null){
				//文件夹路径
				String picturePath = getRequest().getSession().getServletContext().getRealPath("/upload/image/studentPicture");
				//扩展名
				String extention = FilenameUtils.getExtension(uploadFileName);	
				if(!"jpg".equalsIgnoreCase(extention)||"png".equalsIgnoreCase(extention)||"jpeg".equalsIgnoreCase(extention)){
					result.setMessage("上传文件格式错误");
				}
				//创建文件夹
				File dirs = new File(picturePath);
				if(!dirs.exists()){
					dirs.mkdirs();
				}
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
				String today = sdf.format(new Date());
				
				//文件名
				String pictureName = model.getUserCode()+today+"."+extention;
				//文件路径
				String targetPath=picturePath+File.separator+pictureName;
				//创建文件
				File imageFile = new File(targetPath);
				//保存文件
				FileUtil.saveFile(upload, imageFile);
				
				Picture picture = new Picture();
				picture.setPictureName(pictureName);
				picture.setPicturePath("upload/image/studentPicture");
				
				model.setPicture(picture);
			}
			
			result = studentService.save(model,familyMemberInfo,kindSave,baseWebPath);
			
		} catch (Exception e) {
			result.setMessage("保存学生失败");
			logger.error("保存学生失败", e);
		}
		if(result.getIsSuccess()){
			if("mobile".equals(kindSave)||"free".equals(kindSave)){
				setSession(Student.LOGIN_ID,result.getData().get("studentId"));
				setSession(Student.LOGIN_NAME,result.getData().get("studentName"));
			}
			getSession().remove("msgValidateCode");
		}
		
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	/**
	 * @Description: 批量学生删除
	 * @Create: 2012-10-27 下午12:00:30
	 * @author lys
	 * @update logs
	 * @throws Exception
	 */
	public void mulDelete(){
		ServiceResult result = new ServiceResult(false);	
		try {
			result = studentService.mulDelete(ids);
		} catch (Throwable e) {
			result.setMessage("批量学生删除失败");
			logger.error("批量学生删除失败", e);
		}
		ajaxJson(result.toJSON());
	}
	
	/**
	 * @Description: 批量修改学生状态
	 * @Created Time: 2013-2-28 下午10:57:47
	 * @Author lys
	 */
	public void mulUpdateState(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = studentService.mulUpdateState(ids,model);
		} catch (Exception e) {
			result.setMessage("批量修改学生状态失败");
			logger.error("批量修改学生状态失败", e);
			result.setIsSuccess(false);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	
	public String loginValidate(){
		System.out.println(getSession(Student.LOGIN_ID));
		return SUCCESS;
	}
	/**
	 * @Description: 取得学生的照片
	 * @Created Time: 2013-4-20 下午8:10:41
	 * @Author lys
	 */
	public void getPicture(){
		ServiceResult result = new ServiceResult(false);
		Integer studentId = null;
		if(model.getStudentId()!=null){
			studentId = model.getStudentId();
		}else{
			studentId = getIntegerSession(Student.LOGIN_ID);
		}
		try {
			result = studentService.getPicture(studentId);
		} catch (Exception e) {
			result.setMessage("取得学生照片失败");
			logger.error("取得学生照片失败", e);
			result.setIsSuccess(false);
		}
		ajaxJson(result.toJSON());
	}

	/**
	 * 
	 * @Description: 导入照片
	 * @Create: 2013-5-22 下午05:28:44
	 * @author yk
	 * @update logs
	 */
	public void uploadPicture(){
		ServiceResult result = new ServiceResult(false);
		if(model.getStudentId()==null){
			model.setStudentId(getIntegerSession(Student.LOGIN_ID));
		}
		try {
			if(upload==null){
				result.setMessage("请选择照片文件");
			}else{
				//文件夹路径
				String picturePath = getRequest().getSession().getServletContext().getRealPath("/upload/image/studentPicture");
				result = studentService.uploadPicture(model.getStudentId(),picturePath,uploadFileName,upload);
			}
		} catch (Exception e) {
			result.setMessage("导入照片失败");
			logger.error("导入照片失败", e);
		}
		String ajaxString = result.toJSON();
		ajaxJson(ajaxString);
	}
	/**
	 * 
	 * @Description: 下载Excel模板 
	 * @Create: 2013-5-24 上午10:12:31
	 * @author yk
	 * @update logs
	 */
	public void downloadTemplate(){
		String rootPath = getRequest().getSession().getServletContext().getRealPath("/template");
		try {
			FileUtil.downloadFile(getResponse(),rootPath,uploadFileName);
		} catch (Exception e) {
			logger.error("下载模板失败", e);
		}
	}
	
	public void logout(){
		getSession().clear();
		ServiceResult result = new ServiceResult(true);
		ajaxJson(result.toJSON());
	}
	
	public void getStudentName(){
		ServiceResult result = new ServiceResult(true);
		String studentName=getSession().get(Student.LOGIN_NAME).toString();
		model.setStudentName(studentName);
		result.addData("studentName",studentName);
		String ajaxString = result.toJSON();
		ajaxJson(ajaxString);
	}
	
	/**
	 * 
	 * @Description: 初始化基本信息
	 * @Create: 2013-6-14 下午04:40:29
	 * @author yk
	 * @update logs
	 * @return
	 */
	public String init(){
		Integer studentId = getIntegerSession(Student.LOGIN_ID);
		Map<String, Object> map;
		try {
			map = studentService.init(studentId);
			SystemConfig systemConfig = systemConfigDAO.load(1);
			request.setAttribute("student", map.get("student"));
			request.setAttribute("infoType", 0);
			request.setAttribute("nationList", map.get("nationList"));
			request.setAttribute("provinceList", map.get("provinceList"));
			request.setAttribute("familyMemberList", map.get("familyMemberList"));
			request.setAttribute("systemConfig", systemConfig);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public void resetPwd(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = studentService.resetPwd(model.getStudentId(),model.getUserPwd());
		} catch (Exception e) {
			result.setMessage("修改密码失败");
			e.printStackTrace();
		}
		ajaxJson(result.toJSON());
	}
	
	public void queryCombobox(){
		model.setStudentName(q);
		String jsonArray = studentService.query(model, page, rows);
		ajaxJson(jsonArray);
	}

	public Paper getPaper() {
		return paper;
	}

	public void setPaper(Paper paper) {
		this.paper = paper;
	}

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public String getUploadContentType() {
		return uploadContentType;
	}

	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}


}
