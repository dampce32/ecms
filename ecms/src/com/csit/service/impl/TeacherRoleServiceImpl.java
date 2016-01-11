package com.csit.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.csit.dao.RoleDAO;
import com.csit.dao.TeacherRoleDAO;
import com.csit.model.Role;
import com.csit.model.TeacherRole;
import com.csit.model.TeacherRoleId;
import com.csit.service.TeacherRoleService;
import com.csit.util.JSONUtil;
import com.csit.util.StringUtil;
import com.csit.vo.ServiceResult;
/**
 * @Description:教师角色Service实现类
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-4-17
 * @Author lys
 */
@Service
public class TeacherRoleServiceImpl extends
		BaseServiceImpl<TeacherRole, TeacherRoleId> implements
		TeacherRoleService {
	@Resource
	private TeacherRoleDAO teacherRoleDAO;
	@Resource
	private RoleDAO roleDAO;
	
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.TeacherRoleService#queryRole(com.csit.model.TeacherRole)
	 */
	@Override
	public String queryRole(TeacherRole model) {
		List<TeacherRole> list = teacherRoleDAO.queryRole(model);
		List<Role> roleList = roleDAO.query("state", true);
		for (Role role : roleList) {
			for (TeacherRole teacherRole : list) {
				Integer roleId = role.getRoleId();
				if(roleId.equals(teacherRole.getId().getRoleId())){
					role.setChecked("checked");
					break;
				}
			}
		}
		
		String[] properties = {"roleId","roleCode","roleName","checked"};
		
		String ajaxString = JSONUtil.toJson(roleList,properties);
		return ajaxString;
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.TeacherRoleService#updateRole(com.csit.model.TeacherRole, java.lang.String, java.lang.String)
	 */
	@Override
	public ServiceResult updateRole(TeacherRole model, String ids, String oldIds) {
		ServiceResult result = new ServiceResult(false);
		if(model==null||model.getTeacher()==null||model.getTeacher().getTeacherId()==null){
			result.setMessage("请选择教师");
			return result;
		}
		String[] idArray = StringUtil.split(ids);
		String[] oldIdArray = {} ;
		if(StringUtils.isNotEmpty(oldIds)){
			oldIdArray = StringUtil.split(oldIds);
		}
		
		List<String> deleteIdList = new ArrayList<String>();
		List<String> addIdList = new ArrayList<String>();
		for (String oldId : oldIdArray) {
			boolean isDel = true;
			for (String id : idArray) {
				if(oldId.equals(id)){
					isDel =false;
					break;
				}
			}
			if(StringUtils.isNotEmpty(oldId)&&isDel){
				deleteIdList.add(oldId);
			}
		}
		for (String id : idArray) {
			boolean isAdd = true;
			for (String oldId : oldIdArray) {
				if(oldId.equals(id)){
					isAdd =false;
					break;
				}
			}
			if(StringUtils.isNotEmpty(id)&&isAdd){
				addIdList.add(id);
			}
		}
		
		if(addIdList.size()==0&&deleteIdList.size()==0){
			result.setMessage("角色没修改");
			return result;
		}
		Integer teacherId = model.getTeacher().getTeacherId();
		for (String id : deleteIdList) {
			TeacherRoleId teacherRoleId = new TeacherRoleId();
			teacherRoleId.setTeacherId(teacherId);
			teacherRoleId.setRoleId(Integer.parseInt(id));
			teacherRoleDAO.delete(teacherRoleId);
		}
		for (String id : addIdList) {
			TeacherRoleId teacherRoleId = new TeacherRoleId();
			teacherRoleId.setTeacherId(teacherId);
			teacherRoleId.setRoleId(Integer.parseInt(id));
			
			TeacherRole teacherRole = new TeacherRole();
			teacherRole.setId(teacherRoleId);
			teacherRoleDAO.save(teacherRole);
		}
		
		result.setIsSuccess(true);
		return result;
	}

}
