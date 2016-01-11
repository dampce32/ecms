package com.csit.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.csit.dao.GoodsDAO;
import com.csit.model.Goods;
import com.csit.service.GoodsService;
import com.csit.util.JSONUtil;
import com.csit.util.StringUtil;
import com.csit.vo.GobelConstants;
import com.csit.vo.ServiceResult;

/**
 * @Description:教材Service实现类
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-6
 * @author jcf
 * @vesion 1.0
 */
@Service
public class GoodsServiceImpl extends BaseServiceImpl<Goods, Integer> implements
		GoodsService {

	@Resource
	private GoodsDAO goodsDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.csit.service.GoodsService#query(com.csit.model.Goods,
	 * java.lang.Integer, java.lang.Integer)
	 */
	public String query(Goods model, Integer page, Integer rows) {
		List<Goods> list = goodsDAO.query(model, page, rows);
		Long total = goodsDAO.getTotalCount(model);

		String[] properties = { "goodsId", "goodsName", "purchasePrice",
				"sellingPrice", "store", "pressName", "teacher.teacherName",
				"teacher.teacherId","price","amount" };

		return JSONUtil.toJson(list, properties, total);
	}

	public String queryCombobox(Goods model) {
		List<Goods> list = goodsDAO.queryCombobox(model);
		String[] properties = { "goodsId", "goodsName", "purchasePrice","sellingPrice","price" };
		String jsonString = JSONUtil.toJsonWithoutRows(list, properties);
		return jsonString;
	}

	public ServiceResult save(Goods model) {
		ServiceResult result = new ServiceResult(false);
		if (model == null) {
			result.setMessage("请填写教材信息");
			return result;
		}
		if (StringUtils.isEmpty(model.getGoodsName())) {
			result.setMessage("请填写教材名称");
			return result;
		}
		if (model.getPurchasePrice() == null) {
			model.setPurchasePrice(0d);
		}
		if (model.getSellingPrice() == null) {
			model.setSellingPrice(0d);
		}
		if (model.getGoodsId() == null) {// 新增
			if (goodsDAO.load("goodsName", model.getGoodsName()) != null) {
				result.setMessage("请教材名称已存在");
				return result;
			}
			model.setAmount(0d);
			model.setPrice(0d);
			model.setStore(0);
			goodsDAO.save(model);
		} else {
			Goods oldModel1 = goodsDAO.load("goodsName", model.getGoodsName());
			if (oldModel1 != null && oldModel1.getGoodsId().intValue() != model.getGoodsId().intValue()) {
				result.setMessage("请教材名称已存在");
				return result;
			}
			Goods oldModel2 = goodsDAO.load(model.getGoodsId());
			if (oldModel2 == null) {
				result.setMessage("该教材已不存在");
				return result;
			}
			oldModel2.setGoodsName(model.getGoodsName());
			oldModel2.setPressName(model.getPressName());
			oldModel2.setPurchasePrice(model.getPurchasePrice());
			oldModel2.setSellingPrice(model.getSellingPrice());
		}
		result.setIsSuccess(true);
		result.addData("goodsId", model.getGoodsId());
		return result;
	}

	@Override
	public ServiceResult mulDelete(String ids) {
		ServiceResult result = new ServiceResult(false);
		if (StringUtils.isEmpty(ids)) {
			result.setMessage("请选择要删除的记录");
			return result;
		}
		String[] idArray = StringUtil.split(ids, GobelConstants.SPLIT_SEPARATOR);
		if (idArray.length == 0) {
			result.setMessage("请选择要删除的记录");
			return result;
		}
		boolean haveDelete = false;
		for (String id : idArray) {
			Goods oldModel = goodsDAO.load(Integer.parseInt(id));
			if (oldModel == null) {
				continue;
			} else {
				goodsDAO.delete(Integer.parseInt(id));
				haveDelete = true;
			}
		}
		if (!haveDelete) {
			result.setMessage("没有可删除的教材");
			return result;
		}
		result.setIsSuccess(true);
		return result;
	}

}
