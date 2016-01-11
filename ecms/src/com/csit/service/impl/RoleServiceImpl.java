package com.csit.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.csit.dao.RoleDAO;
import com.csit.dao.RoleRightDAO;
import com.csit.dao.TeacherDAO;
import com.csit.model.Role;
import com.csit.model.RoleRight;
import com.csit.model.RoleRightId;
import com.csit.model.Teacher;
import com.csit.service.RoleService;
import com.csit.util.JSONUtil;
import com.csit.util.StringUtil;
import com.csit.vo.GobelConstants;
import com.csit.vo.ServiceResult;
/**
 * @Description:角色Service实现类
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-4-17
 * @Author lys
 */
@Service
public class RoleServiceImpl extends BaseServiceImpl<Role, Integer> implements
		RoleService {
	@Resource
	private RoleDAO roleDAO;
	@Resource
	private TeacherDAO teacherDAO;
	@Resource
	private RoleRightDAO roleRightDAO;
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.RoleService#save(com.csit.model.Role, java.lang.Integer)
	 */
	@Override
	public ServiceResult save(Role model, Integer teacherId) {
		/*
		 *新增角色，
		 *将当前用户的权限，赋予新增的角色 
		 */
		ServiceResult result = new ServiceResult(false);
		if(StringUtils.isEmpty(model.getRoleCode())){
			result.setMessage("请填写角色编号");
			return result;
		}
		if(StringUtils.isEmpty(model.getRoleName())){
			result.setMessage("请填写角色名");
			return result;
		}
		
		Teacher teacher = new Teacher();
		teacher.setTeacherId(teacherId);
		model.setTeacher(teacher);
		model.setOperateTime(com.csit.util.DateUtil.getNowTimestamp());
		Role oldRole = roleDAO.load("roleCode", model.getRoleCode());
		if(model.getRoleId()==null){//新增
			if(oldRole!=null){
				result.setMessage("角色编号已存在，请重新输入角色编号");
				return result;
			}
			roleDAO.save(model);
			/*
			 * 新增角色，并将当前教师的权限赋值给新增的角色
			 */
			List<Map<String,Object>> list = teacherDAO.queryTeacherRight(teacherId);
			//取出当前教师整合后的角色权限
			for (Map<String, Object> map : list) {
				RoleRight roleRight = new RoleRight();
				
				RoleRightId roleRightId = new RoleRightId();
				roleRightId.setRoleId(model.getRoleId());
				
				String rightId = map.get("RightID").toString();
				roleRightId.setRightId(rightId);
				
				String state = map.get("State").toString();
				if("1".equals(state)){
					roleRight.setState(true);
				}else{
					roleRight.setState(false);
				}
				
				roleRight.setId(roleRightId);
				roleRightDAO.save(roleRight);
			}
		}else{
			if(oldRole==null){
				Role oldModel = roleDAO.load(model.getRoleId());
				oldModel.setRoleCode(model.getRoleCode());
				oldModel.setRoleName(model.getRoleName());
				oldModel.setState(model.getState());
				oldModel.setNote(model.getNote());
				oldModel.setTeacher(model.getTeacher());
				oldModel.setOperateTime(model.getOperateTime());
				
			}else{
				if(oldRole.getRoleId().equals(model.getRoleId())){//编号没有修改
					oldRole.setRoleName(model.getRoleName());
					oldRole.setState(model.getState());
					oldRole.setNote(model.getNote());
					oldRole.setTeacher(model.getTeacher());
					oldRole.setOperateTime(model.getOperateTime());
				}else{
					result.setMessage("角色编号已存在，请重新输入角色编号");
					return result;
				}
			}
		}
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.RoleService#query(java.lang.Integer, java.lang.Integer, com.csit.model.Role)
	 */
	@Override
	public String query(Integer page, Integer rows, Role model) {
		List<Role> list = roleDAO.query(page,rows,model);
		Long total = roleDAO.count(model);
		String[] properties = {"roleId","roleCode","roleName","state","note"};
		return JSONUtil.toJson(list, properties, total);
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.RoleService#mulDelete(java.lang.String)
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
		boolean haveDelete = false;
		for (String id : idArray) {
			Role item = roleDAO.load(Integer.parseInt(idArray[0]));
			if("超级管理员".equals(item.getRoleName())){
				continue;
			}else{
				if(StringUtils.isNotEmpty(idArray[0])){
					roleDAO.delete(Integer.parseInt(id));
					haveDelete = true;
				}
			}
		}
		if(!haveDelete){
			result.setMessage("没有可删除的角色");
			return result;
		}
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.RoleService#mulUpdateState(java.lang.String, com.csit.model.Role)
	 */
	@Override
	public ServiceResult mulUpdateState(String ids, Role model) {
		ServiceResult result = new ServiceResult(false);
		if(StringUtils.isEmpty(ids)){
			result.setMessage("请选择要修改状态的角色");
			return result;
		}
		String[] idArray =StringUtil.split(ids);
		if(idArray.length==0){
			result.setMessage("请选择要修改状态的角色");
			return result;
		}
		if(model==null||model.getState()==null){
			result.setMessage("请选择要修改成的状态");
			return result;
		}
		boolean haveUpdateShzt = false;
		for (String id : idArray) {
			Role oldRole = roleDAO.get(Integer.parseInt(id));
			if(oldRole!=null&&oldRole.getState()!=model.getState()){
				oldRole.setState(model.getState());
				haveUpdateShzt = true;
			}
		}
		if(!haveUpdateShzt){
			result.setMessage("没有可修改状态的角色");
			return result;
		}
		result.setIsSuccess(true);
		return result;
	}

}
