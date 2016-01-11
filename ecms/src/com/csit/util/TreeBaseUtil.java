package com.csit.util;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import com.csit.vo.TreeNode;
/**
 * @Description:easyui 生产树的工具类
 * @Copyright: 福州骏华信息有限公司 (c)2012
 * @Created Date : 2012-10-14
 * @author lys
 * @vesion 1.0
 */
public class TreeBaseUtil {
	/**
	 * 空树对象
	 */
	public static final String EMPTY = "[]";
	/**
	 * @Description: 将树节点转化成JSONObject对象
	 * @Create: 2012-10-14 下午11:22:50
	 * @author lys
	 * @update logs
	 * @param treeNode
	 * @return
	 * @throws Exception
	 */
	public static JSONObject toJSONObject(TreeNode treeNode){
		JSONObject object = new JSONObject();
		try {
			object.put("id", treeNode.getId());
			object.put("text", treeNode.getText());
			object.put("iconCls", treeNode.getIconCls());
			if(treeNode.getChecked()!=null&&treeNode.getChecked()==true){
				object.put("checked", treeNode.getChecked());
			}
			object.put("state", treeNode.getState());
			if(treeNode.getAttributes()!=null && !treeNode.getAttributes().isEmpty()){
				object.put("attributes", treeNode.getAttributes());
			}
			if(treeNode.getChildren().size()>0){
				object.put("children", toJSONArray(treeNode.getChildren()));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return object;
	}
	/**
	 * @Description: 将多个树节点转化成JSONArray
	 * @Create: 2012-10-14 下午11:24:05
	 * @author lys
	 * @update logs
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public static JSONArray toJSONArray(List<TreeNode> list){
		JSONArray array = new JSONArray();
		for(TreeNode tree : list){
			array.add(toJSONObject(tree));
		}
		return array;
	}
	/**
	 * @Description: 生成单节点树
	 * @Create: 2012-10-14 下午11:25:13
	 * @author lys
	 * @update logs
	 * @param treeNode
	 * @return
	 * @throws Exception
	 */
	public static String toJSON(TreeNode treeNode){
		JSONObject object = toJSONObject(treeNode);
		return "["+object.toString()+"]";
	}
	/**
	 * @Description: 生成多节点树
	 * @Create: 2012-10-14 下午11:25:58
	 * @author lys
	 * @update logs
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public static String toJSON(List<TreeNode> list){
		JSONArray array = toJSONArray(list);
		return array.toString();
	}
}
