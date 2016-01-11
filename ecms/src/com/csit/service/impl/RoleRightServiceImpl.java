package com.csit.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.csit.dao.RightDAO;
import com.csit.dao.RoleRightDAO;
import com.csit.model.Right;
import com.csit.model.RoleRight;
import com.csit.model.RoleRightId;
import com.csit.service.RoleRightService;
import com.csit.util.TreeBaseUtil;
import com.csit.util.TreeUtil;
import com.csit.vo.ServiceResult;
import com.csit.vo.TreeNode;
/**
 * @Description:角色权限Service实现类
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-4-17
 * @Author lys
 */
@Service
public class RoleRightServiceImpl extends
		BaseServiceImpl<RoleRight, RoleRightId> implements RoleRightService {
	@Resource
	private RoleRightDAO roleRightDAO;
	@Resource
	private RightDAO rightDAO;
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.RoleRightService#getRoot(com.csit.model.RoleRight)
	 */
	@Override
	public String getRoot(RoleRight model) {
		List<RoleRight> rootList = roleRightDAO.getRoot(model);
		List<TreeNode> rootNodeList = TreeUtil.toTreeNodeListRoleRight(rootList);
		
		if(rootList!=null){
			for (int i = 0; i < rootList.size(); i++) {
				RoleRight roleRight = rootList.get(i);
				if(!roleRight.getRight().getIsLeaf()){
					roleRight.setState(false);
					rootNodeList = TreeUtil.toTreeNodeListRoleRight(rootList);
					List<RoleRight> children = roleRightDAO.queryChildren(roleRight);
					List<TreeNode> childrenNodeList = TreeUtil.toTreeNodeListRoleRight(children);
					rootNodeList.get(i).setChildren(childrenNodeList);
				}
			}
		}
		String jsonString = TreeBaseUtil.toJSON(rootNodeList);
		return jsonString;
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.RoleRightService#getChildren(com.csit.model.RoleRight)
	 */
	@Override
	public String getChildren(RoleRight model) {
		List<RoleRight> children = roleRightDAO.queryChildren(model);
		List<TreeNode> childrenTreeNodeList  = new ArrayList<TreeNode>();
		childrenTreeNodeList = TreeUtil.toTreeNodeListRoleRight(children);
		
		String jsonString = TreeBaseUtil.toJSON(childrenTreeNodeList);
		return jsonString;
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.RoleRightService#updateState(com.csit.model.RoleRight)
	 */
	@Override
	public ServiceResult updateState(RoleRight model) {
		ServiceResult result = new ServiceResult(false);
		if(model==null||model.getRole().getRoleId()==null||model.getRight().getRightId()==null){
			result.setMessage("请选择你要更新的角色权限节点");
			return result;
		}
		if(model.getState()==null){
			result.setMessage("请选勾选你要修改的状态");
			return result;
		}
		/*
		 * 勾选状态
		 * true：表明从未勾选到勾选状态
		 *	如果是叶子节点，则	勾选勾选父亲节点中未勾选的节点 、本节点
		 *     不是叶子节点，则选勾选父亲节点中未勾选的节点 和、本节点、子节点
		 *	
		 * false: 从勾选到未勾选
		 *  更新本节点和子节点未未勾选状态
		 *  更新父辈节点中子节点中没有勾选的节点
		 */
		
		//更新本节点
		roleRightDAO.updateState(model.getRole().getRoleId(),model.getRight().getRightId(),model.getState());
		
		if(model.getState()){//从未勾选到勾选状态
			setParentTrue(model);
		}else{
			setParentFalse(model);
		}
		setChildrenState(model,model.getState());
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
			if(!parentRoleRight.getState()){
				roleRightDAO.updateState(model.getRole().getRoleId(),parentRight.getRightId(),true);
				setParentTrue(parentRoleRight);
			}
		}
	}
	
	/**
	 * @Description: 从勾选到未勾选情况下更新父节点的状态
	 * @Create: 2012-10-27 下午10:04:51
	 * @author lys
	 * @update logs
	 * @param model
	 * @throws Exception
	 */
	private void setParentFalse(RoleRight model){
		/*
		 * 取得当前权限节点的父节点，判断该节点下的子节点的状态是否都为未勾选，如果是修改父节点为未勾选，并查找递归父节点
		 * 如果父节点的子节点的孩子节点不是都为未勾选状态，则不操作
		 */
		try {
			Integer count = roleRightDAO.countChildrenStateSameParent(model,true);
			if(count==0){
				Right parentRight = rightDAO.getParentRight(model.getRight());
				if(parentRight!=null){
					RoleRight parentRoleRight = new RoleRight();
					parentRoleRight.setRole(model.getRole());
					parentRoleRight.setRight(parentRight);
					roleRightDAO.updateState(parentRoleRight.getRole().getRoleId(),parentRoleRight.getRight().getRightId(),false);
					
					setParentFalse(parentRoleRight);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @Description: 更新子节点的状态
	 * @Create: 2012-10-27 下午9:57:28
	 * @author lys
	 * @update logs
	 * @param model
	 * @param state
	 * @throws Exception
	 */
	private void setChildrenState(RoleRight model,Boolean state){
		Right currentRight = rightDAO.load(model.getRight().getRightId());
		if(!currentRight.getIsLeaf()){
			Set<Right> children = currentRight.getChildrenRights();
			for (Right right : children) {
				roleRightDAO.updateState(model.getRole().getRoleId(),right.getRightId(),state);
				
				RoleRight parentRoleRight = new RoleRight();
				parentRoleRight.setRole(model.getRole());
				parentRoleRight.setRight(right);
				
				setChildrenState(parentRoleRight,state);
			}
		}
	}

}
