package com.csit.util;

import java.util.ArrayList;
import java.util.List;

import com.csit.model.Right;
import com.csit.model.RoleRight;
import com.csit.vo.TreeNode;
import com.csit.vo.TreeNode.StateType;

public class TreeUtil extends TreeBaseUtil{
	
	/**
	 * @Description: 将List<Right>生成JSON字符串
	 * @Create: 2012-10-14 下午11:46:04
	 * @author lys
	 * @update logs
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public static String toJSONRightList(List<Right> list){
		List<TreeNode> treeNodeList = toTreeNodeList(list);
		return toJSON(treeNodeList);
	}
	/**
	 * @Description: 将List<Right>转化成List<TreeNode>
	 * @Create: 2012-10-14 下午11:43:15
	 * @author lys
	 * @update logs
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public static List<TreeNode> toTreeNodeList(List<Right> list){
		if(list==null){
			return null;
		}
		List<TreeNode> treeNodeList = new ArrayList<TreeNode>();
		for (Right right : list) {
			treeNodeList.add(toTreeNode(right));
		}
		return treeNodeList;
	}
	
	/**
	 * @Description: 将权限Right转化成TreeNode
	 * @Create: 2012-10-14 下午11:34:39
	 * @author lys
	 * @update logs
	 * @param right
	 * @return
	 * @throws Exception
	 */
	public static TreeNode toTreeNode(Right right){
		if(right==null){
			return null;
		}
		TreeNode treeNode = new TreeNode();
		treeNode.setId(right.getRightId());
		treeNode.setText(right.getRightName());
		treeNode.setChecked(right.getState());
		if(!right.getIsLeaf()&&!"所有权限".equals(right.getRightName())){
			treeNode.setState(StateType.closed);
		}
		treeNode.getAttributes().put("rightUrl", right.getRightUrl());
		if(!right.getIsLeaf()){
			List<TreeNode> childrenNode = new ArrayList<TreeNode>();
			List<Right> children = right.getChildrenRightList();
			for (Right right2 : children) {
				childrenNode.add(toTreeNode(right2));
			}
			treeNode.setChildren(childrenNode);
		}
		return treeNode;
	}
	
	/**
	 * @Description: 角色权限List转化成树节点List
	 * @Create: 2012-10-27 下午9:17:45
	 * @author lys
	 * @update logs
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public static List<TreeNode> toTreeNodeListRoleRight(List<RoleRight> list) {
		if(list==null){
			return null;
		}
		List<TreeNode> treeNodeList = new ArrayList<TreeNode>();
		for (RoleRight roleRight : list) {
			treeNodeList.add(toTreeNode(roleRight));
		}
		return treeNodeList;
	}
	
	/**
	 * @Description: 将角色权限RoleRight转化成树节点TreeNode
	 * @Create: 2012-10-27 下午9:20:45
	 * @author lys
	 * @update logs
	 * @param roleRight
	 * @return
	 * @throws Exception
	 */
	private static TreeNode toTreeNode(RoleRight roleRight) {
		if(roleRight==null){
			return null;
		}
		TreeNode treeNode = new TreeNode();
		treeNode.setId(roleRight.getId().getRoleId()+"_"+roleRight.getId().getRightId());
		treeNode.setText(roleRight.getRight().getRightName());
		
		if(roleRight.getState()){
			treeNode.setChecked(true);
		}else{
			treeNode.setChecked(false);
		}
		if(!roleRight.getRight().getIsLeaf()){
			treeNode.setState(StateType.closed);
		}
		return treeNode;
	}

}
