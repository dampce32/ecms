package com.csit.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.csit.dao.RightDAO;
import com.csit.dao.RoleDAO;
import com.csit.dao.RoleRightDAO;
import com.csit.model.Right;
import com.csit.model.Role;
import com.csit.model.RoleRight;
import com.csit.model.RoleRightId;
import com.csit.model.Teacher;
import com.csit.service.RightService;
import com.csit.util.JSONUtil;
import com.csit.util.StringUtil;
import com.csit.util.TreeBaseUtil;
import com.csit.util.TreeUtil;
import com.csit.vo.GobelConstants;
import com.csit.vo.ServiceResult;
import com.csit.vo.TreeNode;
/**
 * @Description:权限Service实现类
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-4-16
 * @Author lys
 */
@Service
public class RightServiceImpl extends BaseServiceImpl<Right, String> implements
		RightService {
	@Resource
	private RightDAO rightDAO;
	@Resource
	private RoleDAO roleDAO;
	@Resource
	private RoleRightDAO roleRightDAO;
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.RightService#getRoot()
	 */
	@Override
	public String getRoot() {
		List<Right> rootList = rightDAO.getRoot();
		List<TreeNode> rootNodeList = TreeUtil.toTreeNodeList(rootList);
		if(rootList!=null){
			for (int i = 0; i < rootList.size(); i++) {
				Right right = rootList.get(i);
				if(!right.getIsLeaf()){
					List<Right> children = rightDAO.getChildren(right);
					List<TreeNode> childrenNodeList = TreeUtil.toTreeNodeList(children);
					rootNodeList.get(i).setChildren(childrenNodeList);
				}
			}
		}
		return TreeBaseUtil.toJSON(rootNodeList);
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.RightService#getTreeNode(com.csit.model.Right)
	 */
	@Override
	public List<Right> getTreeNode(Right model) {
		return rightDAO.getChildren(model);
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.RightService#getTreeNodeChildren(java.lang.Integer, java.lang.Integer, com.csit.model.Right)
	 */
	@Override
	public String getTreeNodeChildren(Integer page, Integer rows, Right model) {
		if(model.getRightId()==null){
			List<Right> rootList = rightDAO.getRoot();
			model.setRightId(rootList.get(0).getRightId());
		}
		
		List<Right> list = rightDAO.query(page,rows,model);
		Long total=rightDAO.count(model);
		
		String[] properties = {"rightId","rightCode","rightName","rightUrl","kind","state","note"};
		
		return JSONUtil.toJson(list, properties, total);
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.RightService#save(com.csit.model.Right, java.lang.Integer)
	 */
	@Override
	public ServiceResult save(Right model, Integer teacherId) {
		ServiceResult result = new ServiceResult(false);
		if(model==null){
			result.setMessage("请填写权限信息");
			return result;
		}
		if(StringUtils.isEmpty(model.getRightCode())){
			result.setMessage("请填写权限编号");
			return result;
		}
		if(StringUtils.isEmpty(model.getRightName())){
			result.setMessage("请填写权限名称");
			return result;
		}
		Teacher teacher = new Teacher();
		teacher.setTeacherId(teacherId);
		model.setTeacher(teacher);
		model.setOperateTime(com.csit.util.DateUtil.getNowTimestamp());
		
		//判断权限编号是否已存在
		Right oldRight = rightDAO.load("rightCode", model.getRightCode());
		
		if(model.getRightId()==null){//新增
			if(model.getParentRight()==null||model.getParentRight().getRightId()==null){
				result.setMessage("请选择父权限");
				return result;
			}
			
			if(oldRight!=null){
				result.setMessage("权限编号已存在");
				return result;
			}
			//计算新增权限的ID
			String maxRightId = rightDAO.getChildrenMaxRightId(model.getParentRight());
			String newRightId = "";
			if("".equals(maxRightId)){
				Right parentRight = rightDAO.load(model.getParentRight().getRightId());
				newRightId = parentRight.getRightId()+String.format("%03d", 0);
			}else{
				String parentRighId = maxRightId.substring(0,maxRightId.length()-3);
				newRightId = parentRighId+String.format("%03d", Integer.parseInt(maxRightId.substring(maxRightId.length()-3))+10);
			}
			model.setRightId(newRightId);
			//查找该父权限下的权限排序最大值
			Integer maxArray = rightDAO.getMaxArray(model.getParentRight().getRightId());
			model.setArray(maxArray+1);
			model.setIsLeaf(true);
			
			rightDAO.save(model);
			if(model.getParentRight()!=null){
				rightDAO.updateIsLeaf(model.getParentRight().getRightId(),false);
				//为所有角色添加权限
				List<Role> allRole = roleDAO.queryAll();
				for (Role role : allRole) {
					RoleRight roleRight = new RoleRight();
					RoleRightId roleRightId = new RoleRightId();
					roleRightId.setRoleId(role.getRoleId());
					roleRightId.setRightId(model.getRightId());
					roleRight.setId(roleRightId);
					
					roleRight.setRole(role);
					roleRight.setRight(model);
					
					roleRight.setState(true);
					roleRightDAO.save(roleRight);
					//更新权限树
					setParentTrue(roleRight);
				}
			}
			result.getData().put("rightId", model.getRightId());
		}else{
			Right oldModel = null;
			if(oldRight==null){//新的编号
				oldModel = rightDAO.load(model.getRightId());
			}else if(oldRight.getRightId().equals(model.getRightId())){//编号没有变化
				oldModel = oldRight;
			}else{//其他权限已使用该编号
				result.setMessage("其他权限已使用该权限编号");
				return result;
			}
			oldModel.setRightCode(model.getRightCode());
			oldModel.setRightName(model.getRightName());
			oldModel.setRightUrl(model.getRightUrl());
			oldModel.setKind(model.getKind());
			oldModel.setState(model.getState());
			oldModel.setNote(model.getNote());
			oldModel.setTeacher(model.getTeacher());
			oldModel.setOperateTime(model.getOperateTime());
		}
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.RightService#mulDelete(java.lang.String)
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
		String parentID = null;
		Right item = null;
		if(StringUtils.isNotEmpty(idArray[0])){
			item = rightDAO.load(idArray[0]);
		}
		for (String id : idArray) {
			if(StringUtils.isNotEmpty(idArray[0])){
				rightDAO.delete(id);
			}
		}
		if(idArray.length>0){
			if(item!=null&&item.getParentRight()!=null){
				parentID = item.getParentRight().getRightId();
				Long countChildren = rightDAO.countChildren(parentID);
				if(countChildren==0){
					rightDAO.updateIsLeaf(parentID, true);
				}
			}
		}
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.RightService#updateArray(java.lang.String, java.lang.String)
	 */
	@Override
	public ServiceResult updateArray(String rightId, String updateRightId) {
		ServiceResult result = new ServiceResult(false);
		if(rightId==null||updateRightId==null){
			result.setMessage("请选择要改变权限排序的权限");
			return result;
		}
		rightDAO.updateArray(rightId,updateRightId);
		
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.RightService#mulUpdateState(java.lang.String, com.csit.model.Right)
	 */
	@Override
	public ServiceResult mulUpdateState(String ids, Right model) {
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
			Right oldRight = rightDAO.load(id);
			if(oldRight!=null&&oldRight.getState()!=model.getState()){
				oldRight.setState(model.getState());
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

	/**
	 * @Description: 从未勾选到勾选情况下更新父节点的状态
	 * @Create: 2012-10-27 下午10:27:01
	 * @author lys
	 * @update logs
	 * @param model
	 * @throws Exception
	 */
	private void setParentTrue(RoleRight model){
		Right parentRight = rightDAO.getParentRight(model.getRight());
		if(parentRight!=null){
			RoleRightId id = new RoleRightId();
			id.setRoleId(model.getRole().getRoleId());
			id.setRightId(parentRight.getRightId());
			
			RoleRight parentRoleRight = roleRightDAO.load(id);
			if(parentRoleRight!=null&&!parentRoleRight.getState()){
				roleRightDAO.updateState(model.getRole().getRoleId(),parentRight.getRightId(),true);
				setParentTrue(parentRoleRight);
			}
		}
	}
}
