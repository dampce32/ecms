package com.csit.service.impl;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.csit.dao.TempArrangeStudentDAO;
import com.csit.model.TempArrange;
import com.csit.model.TempArrangeId;
import com.csit.model.TempArrangeStudent;
import com.csit.model.TempArrangeStudentId;
import com.csit.service.TempArrangeStudentService;
import com.csit.util.JSONUtil;
import com.csit.util.StringUtil;
import com.csit.vo.ServiceResult;

/**
 * @Description:考务安排临时表
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-5-14
 * @author jcf
 * @vesion 1.0
 */
@Service
public class TempArrangeStudentServiceImpl extends
		BaseServiceImpl<TempArrangeStudent, TempArrangeStudentId> implements
		TempArrangeStudentService {
	@Resource
	private TempArrangeStudentDAO tempArrangeStudentDAO;
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.TempArrangeStudentService#save(java.lang.Integer, java.sql.Timestamp, com.csit.model.TempArrangeStudent)
	 */
	@Override
	public ServiceResult save(Integer teacherId, Timestamp operateTime,Integer arrangeId,String ids) {
		ServiceResult result = new ServiceResult(false);
		
		Integer[] studentIdArray = StringUtil.splitToInteger(ids);
		if(studentIdArray.length==0){
			result.setMessage("请选择学生");
			return result;
		}
		for(int i=0;i<studentIdArray.length;i++){
			TempArrangeStudent tempArrangeStudent=new TempArrangeStudent();
			TempArrange tempArrange=new TempArrange();
			TempArrangeId tempArrangeId=new TempArrangeId();
			tempArrangeId.setArrangeId(arrangeId);
			tempArrangeId.setOperateTime(operateTime);
			tempArrangeId.setTeacherId(teacherId);
			tempArrange.setId(tempArrangeId);
			TempArrangeStudentId tempArrangeStudentId=new TempArrangeStudentId();
			tempArrangeStudentId.setArrangeId(arrangeId);
			tempArrangeStudentId.setOperateTime(operateTime);
			tempArrangeStudentId.setStudentId(studentIdArray[i]);
			tempArrangeStudentId.setTeacherId(teacherId);
			tempArrangeStudent.setId(tempArrangeStudentId);
			tempArrangeStudent.setTempArrange(tempArrange);
			tempArrangeStudentDAO.save(tempArrangeStudent);
		}
		
		
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.TempArrangeStudentService#delete(java.lang.Integer, java.sql.Timestamp, com.csit.model.TempArrangeStudent)
	 */
	@Override
	public ServiceResult mulDelete(String ids,Integer arrangeId,Integer teacherId, Timestamp operateTime) {
		ServiceResult result = new ServiceResult(false);

		if(StringUtils.isEmpty(ids)){
			result.setMessage("请选择要删除的学生");
			return result;
		}
		String[] idArray =StringUtil.split(ids);
		if(idArray.length==0){
			result.setMessage("请选择要删除的学生");
			return result;
		}
		for (String id : idArray) {
			TempArrangeStudentId tempArrangeStudentId=new TempArrangeStudentId();
			tempArrangeStudentId.setArrangeId(arrangeId);
			tempArrangeStudentId.setOperateTime(operateTime);
			tempArrangeStudentId.setStudentId(Integer.parseInt(id));
			tempArrangeStudentId.setTeacherId(teacherId);
			tempArrangeStudentDAO.delete(tempArrangeStudentId);
		}
		
		result.setIsSuccess(true);
		return result;
	}
	@Override
	public String query(Integer arrangeId,Integer teacherId,Timestamp operateTime, Integer page, Integer rows) {
		List<TempArrangeStudent> list = tempArrangeStudentDAO.query(arrangeId, teacherId, operateTime, page, rows);

		Long total = tempArrangeStudentDAO.getTotalCount(arrangeId, teacherId, operateTime);
		String[] properties = { "studentId","studentName","studentCode","grade","major","clazz"};
		return JSONUtil.toJson(list, properties, total);
	}

}
