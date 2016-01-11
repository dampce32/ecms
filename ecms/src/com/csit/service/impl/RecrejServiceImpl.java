package com.csit.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.csit.dao.GoodsDAO;
import com.csit.dao.RecrejDAO;
import com.csit.model.Goods;
import com.csit.model.Recrej;
import com.csit.service.RecrejService;
import com.csit.util.JSONUtil;
import com.csit.util.StringUtil;
import com.csit.vo.GobelConstants;
import com.csit.vo.ServiceResult;

/**
 * @Description:入库出库Service实现类
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-6
 * @author jcf
 * @vesion 1.0
 */
@Service
public class RecrejServiceImpl extends BaseServiceImpl<Recrej, Integer> implements
		RecrejService {

	@Resource
	private RecrejDAO recrejDAO;
	@Resource
	private GoodsDAO goodsDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.csit.service.RecrejService#query(com.csit.model.Recrej,
	 * java.lang.Integer, java.lang.Integer)
	 */
	public String query(Recrej model, Integer page, Integer rows) {
		List<Recrej> list = recrejDAO.query(model, page, rows);
		Long total = recrejDAO.getTotalCount(model);

		String[] properties = { "recrejId", "qty", "price","goods.goodsId","goods.goodsName",
				"recrejDate", "recrejType", "teacher.teacherId", "teacher.teacherName","totalPrice"};

		return JSONUtil.toJson(list, properties, total);
	}

	public ServiceResult save(Recrej model) {
		ServiceResult result = new ServiceResult(false);
		if (model == null) {
			result.setMessage("请填写入库出库信息");
			return result;
		}else if (model.getGoods()==null) {
			result.setMessage("请选择教材");
			return result;
		}else if (model.getQty()==null) {
			result.setMessage("请填写数量");
			return result;
		}else if (model.getPrice()==null) {
			result.setMessage("请填写单价");
			return result;
		}
		if (model.getRecrejId() == null) {// 新增
			recrejDAO.save(model);
			Goods goods=goodsDAO.load(model.getGoods().getGoodsId());
			if(model.getRecrejType()==1){
				if((goods.getStore()-model.getQty())<0){
					throw new RuntimeException(" 原因：教材数量不足");
				}
				goods.setStore(goods.getStore()-model.getQty());
				goods.setAmount(goods.getAmount()-goods.getPrice()*model.getQty());
			}else {
				goods.setStore(goods.getStore()+model.getQty());
				goods.setAmount(goods.getAmount()+model.getPrice()*model.getQty());
				goods.setPrice(goods.getAmount()/goods.getStore());
			}
		} else {
			Recrej oldModel = recrejDAO.load(model.getRecrejId());
			if (oldModel == null) {
				result.setMessage("该入库出库已不存在");
				return result;
			}
			Goods goods=goodsDAO.load(oldModel.getGoods().getGoodsId());
			/*
			 * 一、修改入库单
			 * 二、修改出库单
			 * */
			if(model.getRecrejType()==0){
				if(goods.getStore()+(model.getQty()-oldModel.getQty())<0){
					throw new RuntimeException(" 原因：教材数量不足");
				}
				goods.setStore(goods.getStore()+(model.getQty()-oldModel.getQty()));
				goods.setAmount(goods.getAmount()+model.getPrice()*model.getQty()-oldModel.getQty()*oldModel.getPrice());
				goods.setPrice(goods.getAmount()/goods.getStore());
			}else {
				if(goods.getStore()-(model.getQty()-oldModel.getQty())<0){
					throw new RuntimeException(" 原因：教材数量不足");
				}
				goods.setStore(goods.getStore()-(model.getQty()-oldModel.getQty()));
				goods.setAmount(goods.getAmount()-goods.getPrice()*model.getQty()+oldModel.getQty()*oldModel.getPrice());
			}
			oldModel.setRecrejDate(model.getRecrejDate());
			oldModel.setGoods(model.getGoods());
			oldModel.setPrice(model.getPrice());
			oldModel.setQty(model.getQty());
			oldModel.setTeacher(model.getTeacher());
			oldModel.setRecrejType(model.getRecrejType());
		}
		result.setIsSuccess(true);
		result.addData("recrejId", model.getRecrejId());
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
			Recrej oldModel = recrejDAO.load(Integer.parseInt(id));
			if (oldModel == null) {
				continue;
			} else {
				Goods goods=oldModel.getGoods();
				if(oldModel.getRecrejType()==0){
					if(goods.getStore()-oldModel.getQty()<0){
						throw new RuntimeException(" 教材："+goods.getGoodsName()+"数量不足");
					}
					goods.setAmount(goods.getAmount()-goods.getPrice()*oldModel.getQty());
					if(goods.getStore()-oldModel.getQty()==0){
						goods.setPrice(0d);
					}else {
						goods.setPrice(goods.getAmount()/goods.getStore());
					}
					goods.setStore(goods.getStore()-oldModel.getQty());
				}else {
					goods.setStore(goods.getStore()+oldModel.getQty());
					goods.setAmount(goods.getAmount()+goods.getPrice()*oldModel.getQty());
				}
				recrejDAO.delete(Integer.parseInt(id));
				haveDelete = true;
			}
		}
		if (!haveDelete) {
			result.setMessage("没有可删除的入库出库");
			return result;
		}
		result.setIsSuccess(true);
		return result;
	}

}
